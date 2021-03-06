package com.example.Swapyard.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    private String username;
    private String bio;
    private String firstName;

    @OneToOne(cascade = CascadeType.MERGE)
    private Post profilepic;

    private String lastName;
    private String phoneNo;


    @Column(unique = true)
    private String email;
    private String password;

    private boolean enabled;

    @ElementCollection
    private List<Long> dislikes;

    public List<Long> getDislikes() {
        return dislikes;
    }

    public String getBio() {
        return bio;
    }

    public Post getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(Post profilepic) {
        this.profilepic = profilepic;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setDislikes(List<Long> dislikes) {
        this.dislikes = dislikes;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<UserMatches> matches;

    public List<UserMatches> getMatches() {
        return matches;
    }

    public void setMatches(List<UserMatches> matches) {
        this.matches = matches;
    }

    @OneToOne(cascade = CascadeType.ALL)
    private Role role;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "users")
    @JsonManagedReference
    private List<Items> items;

    @OneToOne(cascade = {CascadeType.ALL})
    private Subscriptions subs;

    public Subscriptions getSubs() {
        return subs;
    }

    public void setSubs(Subscriptions subs) {
        this.subs = subs;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



}
