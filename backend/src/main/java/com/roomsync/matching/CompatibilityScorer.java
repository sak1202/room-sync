package com.roomsync.matching;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Deterministic, explainable baseline scorer. Hard constraints are checked before scoring.
 * Tune weights only with user research and offline evaluation; never silently infer sensitive traits.
 */
@Component
public class CompatibilityScorer {
    private static final int SLEEP = 20, FOOD = 15, SMOKING = 15, CLEANLINESS = 10;
    private static final int SOCIAL = 10, BUDGET = 15, LOCATION = 10, WORK = 5;

    public CompatibilityResult score(LifestyleProfile a, LifestyleProfile b) {
        List<String> reasons = new ArrayList<>();
        List<String> differences = new ArrayList<>();
        int total = 0;
        total += exact(a.sleepSchedule(), b.sleepSchedule(), SLEEP, "Similar sleep schedules", "Different sleep schedules", reasons, differences);
        total += exact(a.foodPreference(), b.foodPreference(), FOOD, "Compatible food preferences", "Food preferences may need discussion", reasons, differences);
        total += exact(a.smokingPreference(), b.smokingPreference(), SMOKING, "Smoking preferences align", "Smoking preferences differ", reasons, differences);
        total += proximity(a.cleanlinessLevel(), b.cleanlinessLevel(), 4, CLEANLINESS, "Similar cleanliness expectations", "Cleanliness expectations differ", reasons, differences);
        total += proximity(a.socialLevel(), b.socialLevel(), 4, SOCIAL, "Similar social/guest preferences", "Social preferences differ", reasons, differences);
        total += proximity(a.monthlyBudget(), b.monthlyBudget(), Math.max(a.monthlyBudget(), b.monthlyBudget()), BUDGET, "Rent budgets are close", "Rent budgets differ", reasons, differences);
        total += exactIgnoreCase(a.preferredLocality(), b.preferredLocality(), LOCATION, "Preferred locality aligns", "Preferred localities differ", reasons, differences);
        total += exact(a.workMode(), b.workMode(), WORK, "Work/study routines align", "Work/study routines differ", reasons, differences);
        return new CompatibilityResult(Math.max(0, Math.min(100, total)), List.copyOf(reasons), List.copyOf(differences));
    }

    private int exact(String a, String b, int weight, String yes, String no, List<String> reasons, List<String> differences) {
        boolean match = a != null && a.equals(b);
        (match ? reasons : differences).add(match ? yes : no);
        return match ? weight : 0;
    }
    private int exactIgnoreCase(String a, String b, int weight, String yes, String no, List<String> reasons, List<String> differences) {
        boolean match = a != null && b != null && a.equalsIgnoreCase(b);
        (match ? reasons : differences).add(match ? yes : no);
        return match ? weight : 0;
    }
    private int proximity(int a, int b, int range, int weight, String yes, String no, List<String> reasons, List<String> differences) {
        int distance = Math.abs(a - b);
        int points = Math.max(0, weight - (int) Math.ceil((double) distance / Math.max(1, range) * weight));
        boolean close = distance <= 1;
        (close ? reasons : differences).add(close ? yes : no);
        return points;
    }
}
