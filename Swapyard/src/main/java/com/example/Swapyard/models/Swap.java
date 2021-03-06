package com.example.Swapyard.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Swap {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    public SwapDetails getDetails() {
        return details;
    }

    public void setDetails(SwapDetails details) {
        this.details = details;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    private SwapDetails details;

    @OneToMany(cascade = CascadeType.MERGE)
    private Set<Items> swapItems;

    public Set<Items> getSwapItems() {
        return swapItems;
    }

    public void setSwapItems(Set<Items> swapItems) {
        this.swapItems = swapItems;
    }

    public String swapStatus;

    public Long getId() {
        return id;
    }

    public String getSwapStatus() {
        return swapStatus;
    }

    public void setSwapStatus(String swapStatus) {
        this.swapStatus = swapStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Entity
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static
    class SwapDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id", nullable = false)
        private Long id;

        String date;
        String location;
        String time;

        public String getDate() {
            return date;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
