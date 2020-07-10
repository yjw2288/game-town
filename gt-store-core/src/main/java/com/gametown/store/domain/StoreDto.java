package com.gametown.store.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class StoreDto {
    private long storeId;
    private String name;
    private String storeCode;
    private String address;

    public static StoreDto from(Store store) {
        StoreDto dto = new StoreDto();
        dto.setName(store.getName());
        dto.setStoreCode(store.getStoreCode());
        dto.setAddress(store.getAddress());
        return dto;
    }
}
