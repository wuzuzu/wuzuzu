package com.sparta.wuzuzu.domain.favorite.service;

import com.sparta.wuzuzu.domain.favorite.dto.request.FavoriteRequest;
import com.sparta.wuzuzu.domain.favorite.dto.response.FavoriteResponse;
import com.sparta.wuzuzu.domain.favorite.entity.Favorite;
import com.sparta.wuzuzu.domain.favorite.repository.FavoriteQueryRepository;
import com.sparta.wuzuzu.domain.favorite.repository.FavoriteRepository;
import com.sparta.wuzuzu.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final FavoriteQueryRepository favoriteQueryRepository;

    public void createFavorite(FavoriteRequest request, User user) {
        Boolean res = existFavorite(request);
        if (!res) {
            favoriteRepository.save(new Favorite(request, user));
        }
    }

    public void deleteFavorite(Long favoriteId, User user) {
        Favorite findFavorite = favoriteRepository.findById(favoriteId)
            .orElseThrow(() -> new IllegalArgumentException("선택한 favorite는 존재하지 않습니다."));

        if (!findFavorite.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("자신의 favorite가 아닙니다.");
        }
        favoriteRepository.delete(findFavorite);
    }

    private Boolean existFavorite(FavoriteRequest request) {
        return favoriteQueryRepository.existFavorite(request);
    }

    public List<FavoriteResponse> getAllFavorite(User user) {
        return favoriteRepository.findAllByUser(user).stream().map(Favorite::createResponseFavorite)
            .toList();
    }

    public List<FavoriteResponse> getFavoritecategory(String category, User user) {
        return favoriteQueryRepository.getFavoritecategory(category, user);
    }
}
