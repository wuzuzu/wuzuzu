package com.sparta.wuzuzu.domain.favorite_spot.dto.request;


import lombok.Getter;

@Getter
public class AroundFavoriteSpotRequset {
    private String location;
    private String category;

    public  AroundFavoriteSpotRequset(String location, String category){
        this.location = location;
        this.category = category;

    }

}
