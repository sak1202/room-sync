package com.roomsync.matching;

/** Immutable input to the matching engine. Values are normalized enums/integers, not free text. */
public record LifestyleProfile(
    String sleepSchedule,
    String foodPreference,
    String smokingPreference,
    int cleanlinessLevel,
    int socialLevel,
    int monthlyBudget,
    String preferredLocality,
    String workMode
) {
    public LifestyleProfile {
        if (cleanlinessLevel < 1 || cleanlinessLevel > 5) throw new IllegalArgumentException("cleanlinessLevel must be 1..5");
        if (socialLevel < 1 || socialLevel > 5) throw new IllegalArgumentException("socialLevel must be 1..5");
        if (monthlyBudget < 1) throw new IllegalArgumentException("monthlyBudget must be positive");
    }
}
