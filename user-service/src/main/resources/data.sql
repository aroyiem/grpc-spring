DROP TABLE IF EXISTS user_table;
CREATE TABLE user_table as  SELECT * FROM CSVREAD('classpath:user_table.csv');