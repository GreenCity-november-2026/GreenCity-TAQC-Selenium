package com.greencity.api.models.econews_entity;

import java.util.List;

public record EcoNewsDetailView(
        EcoNewsCard ecoNewsCard,
        String body,
        List<EcoNewsCard> relatedNews) {
}
