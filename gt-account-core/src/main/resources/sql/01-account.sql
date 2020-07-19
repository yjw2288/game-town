create table accounts (
    account_id bigint auto_increment,
    name varchar(1024) not null,
    password varchar(1024) not null,
    email varchar(50) not null,
    primary key(account_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create unique index unique_accounts_user_id
on accounts(email);