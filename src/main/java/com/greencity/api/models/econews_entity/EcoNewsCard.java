package com.greencity.api.models.econews_entity;

import java.util.List;

public record EcoNewsCard(
        String title,
        String subtitle,
        String date,
        String author,
        List<String> tags,
        Integer likes,
        Integer comments,
        String cards_href) {
}

