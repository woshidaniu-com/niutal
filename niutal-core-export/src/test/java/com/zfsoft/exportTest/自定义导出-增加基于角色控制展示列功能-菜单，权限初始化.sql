--自定义导出增加基于角色控制导出展示列功能-------------start

create table niutal_GTGL_DCZDPZB_AUTH
(
  DCCLBH VARCHAR2(32) not null,
  SQZ    VARCHAR2(32) not null,
  ZD     VARCHAR2(32) not null
);
comment on table niutal_GTGL_DCZDPZB_AUTH is '自定义导出导出字段权限表';
comment on column niutal_GTGL_DCZDPZB_AUTH.DCCLBH is '导出列';
comment on column niutal_GTGL_DCZDPZB_AUTH.SQZ is '授权值';
comment on column niutal_GTGL_DCZDPZB_AUTH.ZD is '字段名称';
  
alter table niutal_GTGL_DCZDPZB_AUTH add constraint PK primary key (DCCLBH, SQZ, ZD)using index ;

--菜单幂等处理
delete niutal_xtgl_jsgnmkdmb where GNMKDM = 'N010204' and GNMKMC = '自定义导出管理';

delete niutal_xtgl_gnmkczb where GNMKDM = (select GNMKDM from niutal_xtgl_jsgnmkdmb where GNMKMC = '自定义导出管理')

--加菜单
insert into niutal_xtgl_jsgnmkdmb (GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX, GNJC, TBLJ, SFQY)
values ('N010204', '自定义导出管理', 'N0102', '/xtgl/dcpz/cx.zf', '4', '', '', '1');

insert into niutal_xtgl_gnmkczb (GNMKDM, CZDM, GNCZID, QXDM, XSSX)
values ('N010204', '#', 'N010204_#', 'dcpz:cx', '0');

insert into niutal_xtgl_gnmkczb (GNMKDM, CZDM, GNCZID, QXDM, XSSX)
values ('N010204', 'xg', 'N010204_xg', 'dcpz:xg', '2');

--配权限
insert into niutal_xtgl_jsgnmkczb (JSGNCZID, GNCZID, JSDM)
values ('871D0A593E137009E0538713470A2F16', 'N010204_#', 'admin');

insert into niutal_xtgl_jsgnmkczb (JSGNCZID, GNCZID, JSDM)
values ('871D0A593E137009E0538713470A2F18', 'N010204_xg', 'admin');

commit;

--自定义导出增加基于角色控制导出展示列功能-------------end