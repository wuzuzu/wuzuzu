package com.sparta.wuzuzu.domain.spot.controller;

import com.sparta.wuzuzu.domain.common.dto.CommonResponse;
import com.sparta.wuzuzu.domain.spot.dto.request.AroundSpotRequset;
import com.sparta.wuzuzu.domain.spot.dto.response.SpotAddressResponse;
import com.sparta.wuzuzu.domain.spot.service.SpotService;
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
@RequestMapping("/api/v1/spots")
public class SpotController {

    private final SpotService SpotService;

    // 상세조회
    @GetMapping("/detail")
    public ResponseEntity<CommonResponse<List<SpotAddressResponse>>> getAroundFavoriteSpot(
        @RequestBody AroundSpotRequset request) {
        List<SpotAddressResponse> spotAddressResponseList = SpotService.getAroundFavoriteSpot(
            request);
        return CommonResponse.ofDataWithHttpStatus(spotAddressResponseList, HttpStatus.OK);
    }

}
