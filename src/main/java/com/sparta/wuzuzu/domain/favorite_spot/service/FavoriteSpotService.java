package com.sparta.wuzuzu.domain.favorite_spot.service;

import com.sparta.wuzuzu.domain.favorite_spot.dto.request.AroundFavoriteSpotRequset;
import com.sparta.wuzuzu.domain.favorite_spot.dto.response.SpotAddressResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
//@RequiredArgsConstructor
public class FavoriteSpotService {

    private final RestTemplate restTemplate;

    // RestTemplateBuilder의 build()를 사용하여 RestTemplate을 생성
    public FavoriteSpotService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public List<SpotAddressResponse> getAroundFavoriteSpot(AroundFavoriteSpotRequset request) {
        String keyword = request.getLocation() + request.getCategory();

        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
            .fromUriString("https://openapi.naver.com")
            .path("/v1/search/local.json")
            .queryParam("display", 5)
            .queryParam("query", keyword)
            .encode()
            .build()
            .toUri();

        RequestEntity<Void> requestEntity = RequestEntity
            .get(uri)
            .header("X-Naver-Client-Id", "XGxx5_LDbFCQfTgcuT6n")
            .header("X-Naver-Client-Secret", "3d7127DILN")
            .build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        List<SpotAddressResponse> response = fromJSONtoItems(responseEntity.getBody());

        return response;
    }

    public List<SpotAddressResponse> fromJSONtoItems(String responseEntity) {
        JSONObject jsonObject = new JSONObject(responseEntity);
        JSONArray items = jsonObject.getJSONArray("items");
        List<SpotAddressResponse> itemDtoList = new ArrayList<>();

        for (Object item : items) {
            SpotAddressResponse itemDto = new SpotAddressResponse((JSONObject) item);
            itemDtoList.add(itemDto);
        }

        return itemDtoList;
    }


}
