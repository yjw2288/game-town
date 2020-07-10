package com.gametown.store.domain.account;

import com.gametown.store.domain.Store;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "store_accounts")
@Getter
@Setter
@ToString(exclude = "store")
public class StoreAccount {
    private long storeAccountId;
    private Store store;
    private long accountId;
    private String nickname;
}

