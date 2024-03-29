package com.sparta.wuzuzu.domain.favorite_spot.controller;

import com.sparta.wuzuzu.domain.common.dto.CommonResponse;
import com.sparta.wuzuzu.domain.favorite_spot.dto.request.AroundFavoriteSpotRequset;
import com.sparta.wuzuzu.domain.favorite_spot.dto.response.SpotAddressResponse;
import com.sparta.wuzuzu.domain.favorite_spot.service.FavoriteSpotService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/favoriteSpots")
public class FavoriteSpotController {

    private final FavoriteSpotService favoriteSpotService;

    // 상세조회
    @GetMapping("/around")
    public ResponseEntity<CommonResponse<List<SpotAddressResponse>>> getAroundFavoriteSpot(
        @RequestBody AroundFavoriteSpotRequset request) {
        List<SpotAddressResponse> spotAddressResponseList = favoriteSpotService.getAroundFavoriteSpot(
            request);
        return CommonResponse.ofDataWithHttpStatus(spotAddressResponseList, HttpStatus.OK);
    }

}
