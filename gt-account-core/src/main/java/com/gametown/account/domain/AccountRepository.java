package com.gametown.account.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AccountRepository extends
        JpaRepository<Account, Long>, QuerydslPredicateExecutor<Account> {
}
