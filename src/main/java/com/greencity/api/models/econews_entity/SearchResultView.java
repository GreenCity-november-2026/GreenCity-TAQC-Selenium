package com.greencity.api.models.econews_entity;

import java.util.List;

public record SearchResultView(
        String title,
        List<String> tags,
        String cards_href) {
}
