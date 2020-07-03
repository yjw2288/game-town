-- root
create database gametown;

GRANT DELETE, INSERT, SELECT, UPDATE on
gametown.* to 'gametown'@'localhost';

flush privileges;

-- 이 이후는 bear 계정