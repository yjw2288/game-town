module game.town.gt.account.core.main {
    requires static querydsl.core;

    requires static java.annotation;
    requires static java.persistence;

    requires static spring.data.jpa;
    requires static spring.data.commons;

    requires static spring.context;
    requires static spring.tx;
    requires static spring.boot;
    requires static spring.orm;
    requires static spring.jdbc;

    requires static spring.beans;
    requires static java.sql;
    requires static com.zaxxer.hikari;

    requires static lombok;

    exports com.gametown.account.service;
    exports com.gametown.page;
}