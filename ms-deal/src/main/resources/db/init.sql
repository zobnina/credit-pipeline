-- Создание ролей
-- на чтение и запись
CREATE ROLE ms_deal_user NOSUPERUSER NOCREATEDB NOCREATEROLE;
-- только на чтение
CREATE ROLE ms_deal_reader NOSUPERUSER NOCREATEDB NOCREATEROLE;

-- Создание пользователей
CREATE USER ms_deal_admin NOSUPERUSER NOCREATEDB NOCREATEROLE PASSWORD 'ms_deal_admin';
CREATE USER ms_deal_svc NOSUPERUSER NOCREATEDB NOCREATEROLE PASSWORD 'ms_deal_svc';
CREATE USER ms_deal_test NOSUPERUSER NOCREATEDB NOCREATEROLE PASSWORD 'ms_deal_test';

-- Включение ролей/пользователей в роли с определяемыми далее правами доступа
GRANT ms_deal_user TO ms_deal_svc;
GRANT ms_deal_reader TO ms_deal_test;

-- Создание БД
CREATE DATABASE ms_deal OWNER ms_deal_admin ENCODING 'UTF8' CONNECTION LIMIT 100;

-- Установление соединения
\c ms_deal

-- Создание схем
CREATE SCHEMA ms_deal AUTHORIZATION ms_deal_admin;
CREATE SCHEMA ms_deal_liquibase AUTHORIZATION ms_deal_admin;

-- Выдача прав
ALTER DEFAULT PRIVILEGES FOR ROLE ms_deal_admin GRANT USAGE ON SCHEMAS TO ms_deal_user;
ALTER DEFAULT PRIVILEGES FOR ROLE ms_deal_admin GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE ON TABLES TO ms_deal_user;

ALTER DEFAULT PRIVILEGES FOR ROLE ms_deal_admin IN SCHEMA ms_deal GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE ON TABLES TO ms_deal_user;
ALTER DEFAULT PRIVILEGES FOR ROLE ms_deal_admin IN SCHEMA ms_deal GRANT EXECUTE ON FUNCTIONS TO ms_deal_user;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA ms_deal TO ms_deal_user;
GRANT USAGE ON SCHEMA ms_deal TO ms_deal_user;

ALTER DEFAULT PRIVILEGES FOR ROLE ms_deal_admin IN SCHEMA ms_deal_liquibase GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE ON TABLES TO ms_deal_user;
ALTER DEFAULT PRIVILEGES FOR ROLE ms_deal_admin IN SCHEMA ms_deal_liquibase GRANT EXECUTE ON FUNCTIONS TO ms_deal_user;
GRANT USAGE ON SCHEMA ms_deal_liquibase TO ms_deal_user;

ALTER DEFAULT PRIVILEGES FOR ROLE ms_deal_admin GRANT USAGE ON SCHEMAS TO ms_deal_reader;
ALTER DEFAULT PRIVILEGES FOR ROLE ms_deal_admin GRANT SELECT ON TABLES TO ms_deal_reader;

ALTER DEFAULT PRIVILEGES FOR ROLE ms_deal_admin IN SCHEMA ms_deal GRANT SELECT ON TABLES TO ms_deal_reader;
ALTER DEFAULT PRIVILEGES FOR ROLE ms_deal_admin IN SCHEMA ms_deal_liquibase GRANT SELECT ON TABLES TO ms_deal_reader;
GRANT USAGE ON SCHEMA ms_deal TO ms_deal_reader;
GRANT USAGE ON SCHEMA ms_deal_liquibase TO ms_deal_reader;

ALTER DATABASE ms_deal set search_path TO 'ms_deal';







