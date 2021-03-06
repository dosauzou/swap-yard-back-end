package com.example.Swapyard.controllers;

import com.example.Swapyard.models.*;
import com.example.Swapyard.repositories.MatchRepository;
import com.example.Swapyard.repositories.SwapRepository;
import com.example.Swapyard.repositories.SwipeRepository;
import com.example.Swapyard.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "https://localhost:4200", allowedHeaders={"x-auth-token", "x-requested-with", "x-xsrf-token"})
@RequestMapping("/api/v1")
public class NotificationController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MatchRepository matchRepository;
    @Autowired
    SwipeRepository swipeRepository;

@Autowired
private SwapRepository swapRepository;


    public NotificationController() throws IOException {
    }

    @PostMapping("/notifications/{id}")
    public Subscriptions storeNotifiation(@RequestBody String notification, @PathVariable String id) throws JsonProcessingException, ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        org.json.simple.parser.JSONParser parser = new JSONParser();
        org.json.simple.JSONObject jobj = (JSONObject) parser.parse(notification);
        org.json.simple.JSONObject key = (JSONObject) jobj.get("keys");
        Subscriptions subscription = objectMapper.readValue(notification, Subscriptions.class);
        SubKeys keys = objectMapper.readValue(key.toString(), SubKeys.class);
        subscription.setKey(keys);
        Users user = userRepository.findByUsername(id);
        user.setSubs(subscription);
        userRepository.save(user);
        System.out.println(keys.getP256dh());
//        Subscription subscription = objectMapper.readValue(notification, Subscription.class);
//        System.out.println(subscription.getAuth() + subscription.getEndpoint() + subscription.getExpirationTime());
        return subscription;
    }

    @GetMapping("/notifications/{id}")
    public List<MultiMap> loadNotifs(@PathVariable String id) {
        Users b = userRepository.findByUsername(id);
        List<UserMatches> allMatches = b.getMatches();
        List<MultiMap> userMatches = new ArrayList<>();
        List<Swipes> swipes = swipeRepository.findByUserId(b.getId());
        List <Swap> swaps = swapRepository.findAll();
        List<Items> swappedItems = new ArrayList<>();
        swaps.forEach(p-> swappedItems.addAll(p.getSwapItems()));

        MultiMap multiMap;
        for (UserMatches o : allMatches) {
            List<Swipes> swipesRec = swipeRepository.findByUserId(o.getMatch().getId());

            Object[] itemsList;
            HashSet<Items> set = new HashSet<Items>();
            HashSet<Items> swipesRec1 = new HashSet<Items>();

            for (Swipes s : swipes) {
                if (s.getItems().getUsers().getId().equals(o.getMatch().getId())) {
                    set.add(s.getItems());
                }
            }
            for (Swipes j : swipesRec) {

                if (j.getItems().getUsers().getId()==b.getId()) {
                    System.out.println(j.getItems().getId());

                    swipesRec1.add(j.getItems());
                }
            }

            set.removeAll(swappedItems);

            System.out.println(swipesRec1.size());


            ///find the items swiped on.
            //find the items swiped on where the item id = r
            //i each userswipe.getitem.getuserid=bo.getuserid
            itemsList = set.toArray();
            multiMap = new MultiMap(o.getMatch(), itemsList, o.getSwap(), o.getChatId(), swipesRec1);
            userMatches.add(multiMap);



            //findwhattheuser had like
        }


        return userMatches;
    }
}
