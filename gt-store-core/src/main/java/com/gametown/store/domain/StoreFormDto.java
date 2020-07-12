package com.gametown.store.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoreFormDto {
    private String storeCode;
    private String name;
    private String address;
}
