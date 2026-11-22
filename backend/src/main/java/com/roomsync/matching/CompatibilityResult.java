package com.roomsync.matching;

import java.util.List;

/** Score is an explainable heuristic, not a promise of roommate success. */
public record CompatibilityResult(int score, List<String> reasons, List<String> differences) {}
