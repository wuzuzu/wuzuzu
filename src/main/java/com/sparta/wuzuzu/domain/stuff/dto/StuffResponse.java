package com.sparta.wuzuzu.domain.stuff.dto;

import com.sparta.wuzuzu.domain.stuff.entity.Stuff;
import com.sparta.wuzuzu.domain.stuff.entity.StuffType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StuffResponse {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Long stock;
    private StuffType category;

    public StuffResponse(Stuff stuff) {
        this.id = stuff.getStuffId();
        this.name = stuff.getName();
        this.description = stuff.getDescription();
        this.price = stuff.getPrice();
        this.stock = stuff.getStock();
        this.category = stuff.getCategory();
    }
}
