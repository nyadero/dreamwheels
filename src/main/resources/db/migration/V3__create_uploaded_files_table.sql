CREATE TABLE IF NOT EXISTS uploaded_files(
 id varchar(255) not null,
 file_name varchar(255),
 file_size integer,
 url varchar(255),
 file_tags varchar(255),
 file_type varchar(255),
 user_id varchar(255) not null,
 garage_id varchar(255) null,
 created_at timestamp(6),
 updated_at timestamp(6),
 primary key(id)
)