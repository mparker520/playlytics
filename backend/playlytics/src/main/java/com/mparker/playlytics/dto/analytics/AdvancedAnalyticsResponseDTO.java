package com.mparker.playlytics.dto.analytics;

import java.util.List;
import java.util.Map;

public record AdvancedAnalyticsResponseDTO(String label, List<String> labels, Map<String, List<Integer>> data) {
}
