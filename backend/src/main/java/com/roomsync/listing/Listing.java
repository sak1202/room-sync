package com.roomsync.listing;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.persistence.*;

@Entity @Table(name="listings", indexes={@Index(name="idx_listing_city_active", columnList="city, active"), @Index(name="idx_listing_rent", columnList="monthly_rent")})
public class Listing {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, length=120) private String title;
    @Column(nullable=false, length=80) private String city;
    @Column(nullable=false, length=120) private String locality;
    @Column(name="monthly_rent", nullable=false, precision=12, scale=2) private BigDecimal monthlyRent;
    @Column(nullable=false) private Integer availableSlots;
    @Column(nullable=false) private boolean active = true;
    @Column(name="created_at", nullable=false) private Instant createdAt = Instant.now();
    protected Listing() {}
    public Listing(String title, String city, String locality, BigDecimal monthlyRent, Integer availableSlots) { this.title=title; this.city=city; this.locality=locality; this.monthlyRent=monthlyRent; this.availableSlots=availableSlots; }
    public Long getId(){return id;} public String getTitle(){return title;} public String getCity(){return city;} public String getLocality(){return locality;} public BigDecimal getMonthlyRent(){return monthlyRent;} public Integer getAvailableSlots(){return availableSlots;} public boolean isActive(){return active;} public Instant getCreatedAt(){return createdAt;}
}
