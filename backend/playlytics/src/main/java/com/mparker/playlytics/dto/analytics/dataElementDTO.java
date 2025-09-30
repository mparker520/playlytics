package com.mparker.playlytics.dto.analytics;

import java.util.List;

public record dataElementDTO(String label, List<Integer> playCounts) {

}
