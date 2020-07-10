create table stores (
    store_id bigint auto_increment,
    store_code varchar(20) not null,
    name varchar(20) not null,
    address varchar(1024) not null,
    master_account_id bigint not null,
    primary key(store_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create unique index unique_stores_store_code
on stores(store_code);

create index stores_master_account_id
on stores(master_account_id);