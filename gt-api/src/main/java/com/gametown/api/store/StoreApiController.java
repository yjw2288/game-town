package com.gametown.api.store;

import com.gametown.store.service.StoreDto;
import com.gametown.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreApiController {

    @Autowired
    private StoreService storeService;

    @GetMapping
    public List<StoreDto> stores() {
        return storeService.stores();
    }
}
