create table accounts (
    account_id bigint auto_increment,
    user_id varchar(20) not null,
    name varchar(1024) not null,
    password varchar(1024) not null,
    email varchar(1024) not null,
    primary key(account_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create unique index unique_accounts_user_id
on accounts(user_id);