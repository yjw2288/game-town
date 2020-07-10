package com.gametown.store.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class StoreForm {
    private String storeCode;
    private String name;
    private String address;
}
