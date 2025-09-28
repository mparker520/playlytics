package com.mparker.playlytics.dto.analytics;

import java.util.List;

public record WinLossResponseDTO(String label, List<String> labels, List<Long> data) {
}
