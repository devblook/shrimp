create table if not exists shrimp_homes
(
  home_id       varchar(36) primary key,
  home_name     varchar(255) not null,
  home_position varchar(255) not null,
  created_at    timestamp    not null
);

create table if not exists shrimp_users
(
  user_id   varchar(36) primary key,
  user_name varchar(255) not null
);

create table if not exists shrimp_homes_users
(
  home_id varchar(36) not null,
  user_id varchar(36) not null,
  primary key (home_id, user_id),
  foreign key (home_id) references shrimp_homes (home_id),
  foreign key (user_id) references shrimp_users (user_id)
);


