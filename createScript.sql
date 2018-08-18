create table FRIEND
(
  USER1_USERNAME VARCHAR(255) not null
    references USER,
  USER2_USERNAME VARCHAR(255) not null
    references USER,
  primary key (USER1_USERNAME, USER2_USERNAME)
);


