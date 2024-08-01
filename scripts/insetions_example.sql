insert into shrimp_users (user_id, user_name)
values ('1', 'Alice')
on duplicate key update user_name = 'Alice';

insert into shrimp_homes (home_id, home_name, home_position)
values ('1', 'Alice Home', 'Alice Position')
on duplicate key update home_name = 'Alice Home' and home_position = 'Alice Position';

insert into shrimp_homes_users (home_id, user_id)
values ('1', '1');