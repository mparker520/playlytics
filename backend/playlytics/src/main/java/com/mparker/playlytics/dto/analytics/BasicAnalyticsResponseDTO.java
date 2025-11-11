package com.mparker.playlytics.dto.analytics;

import java.util.List;

public record BasicAnalyticsResponseDTO(String label, List<String> labels, List<Long> data) {
}
