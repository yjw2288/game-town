package com.gametown.store.service;

import com.gametown.store.domain.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(value = "storeTransactionManager")
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    @Transactional(value = "storeTransactionManager", readOnly = true)
    public List<StoreDto> stores() {
        return storeRepository.findAll()
                .stream().map(store -> {
                    StoreDto dto = new StoreDto();
                    dto.setStoreId(store.getStoreId());
                    dto.setName(store.getName());
                    return dto;
                }).collect(Collectors.toList());
    }
}
