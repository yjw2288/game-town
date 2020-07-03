package com.gametown.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StoreRepository extends JpaRepository<Store, Long>, QuerydslPredicateExecutor<Store> {

}
