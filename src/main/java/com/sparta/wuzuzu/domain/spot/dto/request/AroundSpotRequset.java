package com.sparta.wuzuzu.domain.spot.dto.request;


import lombok.Getter;

@Getter
public class AroundSpotRequset {

    private String location;
    private String category;

    public AroundSpotRequset(String location, String category) {
        this.location = location;
        this.category = category;

    }

}
