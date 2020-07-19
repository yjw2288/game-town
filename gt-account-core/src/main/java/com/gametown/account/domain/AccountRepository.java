package com.gametown.account.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface AccountRepository extends
        JpaRepository<Account, Long>, QuerydslPredicateExecutor<Account> {
    Optional<Account> findByEmail(String email);
}
