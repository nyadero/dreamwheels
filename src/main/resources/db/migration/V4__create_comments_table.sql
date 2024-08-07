create table comments (
created_at timestamp(6),
 updated_at timestamp(6),
  comment varchar(255),
  garage_id varchar(255),
  id varchar(255) not null,
   parent_comment_id varchar(255),
   user_id varchar(255),
    primary key (id)
    );
