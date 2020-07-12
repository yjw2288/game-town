package com.gametown.store.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StoreFormDto {
    private String storeCode;
    private String name;
    private String address;
}
