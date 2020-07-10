create table store_accounts (
    store_account_id bigint auto_increment,
    nickname varchar(20) not null,
    account_id bigint not null,
    store_id bigint not null,
    primary key(store_account_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create unique index unique_store_accounts_store_id_and_account_id
on store_accounts(store_id, account_id);