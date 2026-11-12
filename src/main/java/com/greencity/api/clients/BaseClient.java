package com.greencity.api.clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;

public class BaseClient {
    protected final String baseAPIUrl;
    protected final ContentType contentType;
    @Getter
    @Setter
    protected String token;


    public BaseClient(String baseUrl) {
        this(baseUrl, ContentType.JSON);
    }

    public BaseClient(String baseUrl, ContentType contentType) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException("Base API URL cannot be empty");
        }

        if (contentType == null) {
            throw new IllegalArgumentException("Content type cannot be null");
        }

        this.baseAPIUrl = baseUrl.trim();
        this.contentType = contentType;
    }

    public BaseClient(String baseUrl, String contentType) {
        this(baseUrl, ContentType.valueOf(contentType));
    }


    protected RequestSpecification preparedRequest() {
        RequestSpecification request = RestAssured.given()
//                .log()
//                .body()
                .baseUri(baseAPIUrl)
                .contentType(contentType);
        if (token != null && !token.isBlank()) {
            request.header("Authorization", "Bearer " + token);
        }
        return request;
    }

}
