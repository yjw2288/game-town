package com.gametown.api.store;

import com.gametown.store.domain.StoreDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreView {
    private String name;
    private String storeCode;
    private String address;

    public static StoreView from(StoreDto store) {
        StoreView view = new StoreView();
        view.setName(store.getName());
        view.setStoreCode(store.getStoreCode());
        view.setAddress(store.getAddress());
        return view;
    }
}
