drop table if exists public.sys_user;
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
  version int not null default 0
);

insert into public.sys_user(id, name, username, password, status, create_time, creator)
values(423441402547015680, 'administrator', 'admin', '$2a$10$8CDOiERjl1Y08dA.IbkaZuBYmrlkCEi.9sbrVaGYyeYKoQe87aYNi', 1, now(), 423441402547015680);

drop table if exists public.sys_menu;
create table public.sys_menu (
  id bigint primary key,
  code varchar(64) not null unique,
  name varchar(64) not null,
  icon varchar(64),
  url varchar(256),
  description varchar(512),
  parent bigint not null default 0,
  seq_order int,
  create_time timestamp,
  creator bigint,
  update_time timestamp,
  updater bigint,
  version int not null default 0
);

insert into public.sys_menu(id, code, name, url, seq_order, create_time, creator, parent, icon)
values(323441402547015680, 'system', 'Settings', null, 99999, now(), 423441402547015680, 0, 'ios-settings');
insert into public.sys_menu(id, code, name, url, seq_order, create_time, creator, parent)
values(323441402547015681, 'menu', 'Menus', '/system/menu', 8, now(), 423441402547015680, 323441402547015680);
insert into public.sys_menu(id, code, name, url, seq_order, create_time, creator, parent)
values(323441402547015684, 'user', 'Users', '/system/users', 5, now(), 423441402547015680, 323441402547015680);
insert into public.sys_menu(id, code, name, url, seq_order, create_time, creator, parent)
values(483941363017056256, 'org', 'Organizations', '/system/org', 3, now(), 423441402547015680, 323441402547015680);


drop table if exists public.sys_role;
create table public.sys_role (
  id bigint primary key,
  code varchar(64) not null unique,
  name varchar(64) not null,
  description varchar(512),
  create_time timestamp,
  creator bigint,
  update_time timestamp,
  updater bigint,
  version int not null default 0
);

insert into public.sys_role(id, code, name, create_time, creator)
values(323441402547015682, 'admin', 'Administrator', now(), 423441402547015680);

drop table if exists public.sys_menu_func;
create table public.sys_menu_func (
  id bigint primary key,
  menu_id bigint,
  code varchar(64) not null unique,
  name varchar(64) not null,
  create_time timestamp,
  creator bigint,
  update_time timestamp,
  updater bigint,
  version int not null default 0
);

insert into sys_menu_func(id, menu_id, code, name, create_time, creator)
  values(323441402547015693, 323441402547015681, 'menu:add', 'New', now(), 423441402547015680);
insert into sys_menu_func(id, menu_id, code, name, create_time, creator)
  values(323441402547015694, 323441402547015681, 'menu:remove', 'Remove', now(), 423441402547015680);


drop sequence if exists auto_increment_id cascade;
create sequence auto_increment_id
  START WITH 10000
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

drop table if exists public.link_user_role;
create table link_user_role (
  id bigint primary key,
  user_id bigint,
  role_id bigint
);
alter table link_user_role alter column id set default nextval('auto_increment_id');
insert into link_user_role(user_id, role_id)
values(423441402547015680, 323441402547015682);

drop table if exists public.link_role_menu;
create table link_role_menu (
  id bigint primary key,
  role_id bigint,
  menu_id bigint
);
alter table link_role_menu alter column id set default nextval('auto_increment_id');

insert into link_role_menu(role_id, menu_id)
values(323441402547015682, 323441402547015680),
(323441402547015682, 323441402547015681),
(323441402547015682, 323441402547015684),
(323441402547015682, 483941363017056256);

drop table if exists public.link_role_func;
create table link_role_func (
  id bigint primary key,
  role_id bigint,
  func_id bigint
);
alter table link_role_func alter column id set default nextval('auto_increment_id');

drop table if exists public.sys_organization;
create table sys_organization (
  id bigint primary key,
  code varchar(64) not null unique,
  name varchar(64) not null,
  parent bigint not null default 0,
  remark varchar(512),
  telephone varchar(16),
  category int not null,
  create_time timestamp,
  creator bigint,
  update_time timestamp,
  updater bigint,
  version int not null default 0
)