create table public.sys_user (
  id bigint primary key,
  name varchar(64) not null,
  username varchar(16) not null unique,
  password varchar(64) not null,
  status int not null default 1,
  brithday date,
  education int,
  email varchar(512),
  qq varchar(16),
  wechart varchar(32),
  telephone varchar(16),
  cellphone varchar(16),
  avatar bigint,
  remark varchar(512),
  create_time timestamp,
  creator bigint,
  update_time timestamp,
  updater bigint,
  version int
);

insert into public.sys_user(id, name, username, password, status, create_time, creator, version)
    values(423441402547015680, 'administrator', 'admin', '$2a$10$8CDOiERjl1Y08dA.IbkaZuBYmrlkCEi.9sbrVaGYyeYKoQe87aYNi', 1, now(), 423441402547015680, 0);


create table public.sys_menu (
  id bigint primary key,
  code varchar(64) not null unique,
  name varchar(64) not null,
  method varchar(8),
  url varchar(256),
  description varchar(512),
  parent bigint not null default 0,
  seq_order int,
  create_time timestamp,
  creator bigint,
  update_time timestamp,
  updater bigint,
  version int
)