package com.gametown.api.store;

import com.gametown.store.domain.StoreFormDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StoreForm {
    @NotBlank
    private String storeCode;
    @NotBlank
    private String name;
    @NotBlank
    private String address;

    public StoreFormDto toDto() {
        return StoreFormDto.builder()
                .storeCode(storeCode)
                .name(name)
                .address(address)
                .build();
    }
}
