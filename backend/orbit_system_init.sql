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
  wechat varchar(32),
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
);

create table public.sys_role (
  id bigint primary key,
  code varchar(64) not null unique,
  name varchar(64) not null,
  description varchar(512),
  create_time timestamp,
  creator bigint,
  update_time timestamp,
  updater bigint,
  version int
);

create table public.sys_menu_func (
  id bigint primary key,
  menu_id bigint,
  code varchar(64) not null unique,
  name varchar(64) not null,
  method varchar(8),
  url varchar(256),
  description varchar(512),
  seq_order int,
  create_time timestamp,
  creator bigint,
  update_time timestamp,
  updater bigint,
  version int
);

create sequence auto_increment_id
START WITH 10000
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

create table link_user_role (
  id bigint primary key,
  user_id bigint,
  role_id bigint
);
alter table link_user_role alter column id set default nextval('auto_increment_id');

create table link_role_menu (
  id bigint primary key,
  role_id bigint,
  menu_id bigint
);
alter table link_role_menu alter column id set default nextval('auto_increment_id');

create table link_role_func (
  id bigint primary key,
  role_id bigint,
  func_id bigint
);
alter table link_role_func alter column id set default nextval('auto_increment_id');