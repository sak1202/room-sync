package com.roomsync.listing;

import java.math.BigDecimal;
import java.time.Instant;

public record ListingResponse(Long id, String title, String city, String locality, BigDecimal monthlyRent, Integer availableSlots, Instant createdAt) {
    static ListingResponse from(Listing l) { return new ListingResponse(l.getId(), l.getTitle(), l.getCity(), l.getLocality(), l.getMonthlyRent(), l.getAvailableSlots(), l.getCreatedAt()); }
}
