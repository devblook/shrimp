select su.user_id, su.user_name, sh.home_id, sh.home_name, sh.home_position, sh.created_at
from shrimp_users as su
         inner join shrimp.shrimp_homes_users shu on su.user_id = shu.user_id
         inner join shrimp.shrimp_homes sh on shu.home_id = sh.home_id
where su.user_id = 1;


select count(shu.home_id) user_homes
from shrimp_users as su
         inner join shrimp.shrimp_homes_users shu on su.user_id = shu.user_id
         inner join shrimp.shrimp_homes sh on shu.home_id = sh.home_id
where su.user_id = 1;

select count(shu.user_id) home_users
from shrimp_users as su
         inner join shrimp.shrimp_homes_users shu on su.user_id = shu.user_id
         inner join shrimp.shrimp_homes sh on shu.home_id = sh.home_id
where sh.home_id = 1;