-- motorbike garages
create table motorbike_garages (
id varchar(255) not null,
motorbike_make varchar(255),
motorbike_model varchar(255),
motorbike_category varchar(255),
primary key (id)
);

-- password reset tokens
create table passwordresettokens (
expiration_time timestamp(6),
id varchar(255) not null,
token varchar(255),
user_id varchar(255) not null unique,
primary key (id)
);

-- users table
create table users (
is_enabled boolean not null,
role smallint check (role between 0 and 3) default 0,
created_at timestamp(6),
updated_at timestamp(6),
about TEXT,
email varchar(255),
id varchar(255) not null,
name varchar(255),
password varchar(255),
user_name varchar(255),
primary key (id)
);

-- vehicle garages
create table vehicle_garages (
id varchar(255) not null,
vehicle_make varchar(255),
vehicle_model varchar(255),
drive_train varchar(255),
engine_layout varchar(255),
engine_position varchar(255),
body_type varchar(255),
primary key (id)
);

-- verification tokens
create table verificationtokens (
expiration_time timestamp(6),
id varchar(255) not null,
token varchar(255),
user_id varchar(255) not null unique,
primary key (id)
);

-- garages
create table garages (
buying_price float(53) not null,
previous_owners_count integer not null default 0,
acceleration integer not null default 0,
top_speed integer not null default 0,
mileage integer not null default 0,
engine_power integer not null default 0,
torque integer not null default 0,
created_at timestamp(6),
updated_at timestamp(6),
description TEXT,
id varchar(255) not null,
name varchar(255),
user_id varchar(255),
engine_aspiration varchar(255),
transmission_type varchar(255),
fuel_type varchar(255),
primary key (id)
);