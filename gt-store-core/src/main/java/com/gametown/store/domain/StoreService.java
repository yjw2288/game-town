package com.gametown.store.domain;

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
                .stream().map(StoreDto::from).collect(Collectors.toList());
    }

    @Transactional(value = "storeTransactionManager")
    public StoreDto create(long accountId, StoreForm storeForm) {
        Store store = new Store();
        store.setStoreCode(storeForm.getStoreCode());
        store.setAddress(storeForm.getAddress());
        store.setName(storeForm.getName());
        store.setMasterAccountId(accountId);
        Store savedStore = storeRepository.save(store);
        return StoreDto.from(savedStore);
    }
}
