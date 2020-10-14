
--资源键值内容信息表
create table niutal_xtgl_i18n
(
  RES_KEY  VARCHAR2(50) not null,
  ZH_CN    VARCHAR2(300) not null,
  EN_US    VARCHAR2(300)
);
alter table niutal_xtgl_i18n add primary key (RES_KEY);
comment on table niutal_xtgl_i18n is '资源键值内容信息表';
comment on column niutal_xtgl_i18n.RES_KEY is '资源主键';
comment on column niutal_xtgl_i18n.ZH_CN is '中文描述';
comment on column niutal_xtgl_i18n.EN_US is '英文描述';
--资源文件信息表
create table niutal_xtgl_resource
(
  RES_OID    	VARCHAR2(50) not null,
  RES_CODE    	VARCHAR2(50) not null,
  RES_NAME     	VARCHAR2(100),
  RES_XXDM    	VARCHAR2(30),
  RES_TYPE    	VARCHAR2(20) not null,
  RES_PATH    	VARCHAR2(100),
  RES_ZJSJ    	VARCHAR2(30),
  RES_ZJYH    	VARCHAR2(50),
  RES_FBBJ    	VARCHAR2(2) default '0',
  RES_FBSJ    	VARCHAR2(30),
  RES_FBYH    	VARCHAR2(50),
  RES_BZ		VARCHAR2(200)
);
alter table niutal_xtgl_resource add primary key (RES_OID);
comment on table niutal_xtgl_resource is '资源文件信息表';
comment on column niutal_xtgl_resource.RES_OID is '资源文件主键';
comment on column niutal_xtgl_resource.RES_CODE is '资源文件代码';
comment on column niutal_xtgl_resource.RES_NAME is '资源文件名称';
comment on column niutal_xtgl_resource.RES_XXDM is '资源文件学校代码';
comment on column niutal_xtgl_resource.RES_TYPE is '资源文件类型：js, properties';
comment on column niutal_xtgl_resource.RES_PATH is '资源文件路径';
comment on column niutal_xtgl_resource.RES_ZJSJ is '资源文件增加时间';
comment on column niutal_xtgl_resource.RES_ZJYH is '资源文件增加用户';
comment on column niutal_xtgl_resource.RES_FBBJ is '资源文件发布标记：0未发布，1已发布';
comment on column niutal_xtgl_resource.RES_FBSJ is '资源文件发布时间';
comment on column niutal_xtgl_resource.RES_FBYH is '资源文件发布用户';
comment on column niutal_xtgl_resource.RES_BZ is '资源文件备注';
--资源文件与键值关系表
create table niutal_xtgl_resource_i18n
(
  RES_OID  VARCHAR2(50) not null,
  RES_KEY  VARCHAR2(50) not null
);
alter table niutal_xtgl_resource_i18n add primary key (RES_OID, RES_KEY);
comment on table niutal_xtgl_resource_i18n is '资源文件与键值关系表';
comment on column niutal_xtgl_resource_i18n.RES_OID is '资源文件主键';
comment on column niutal_xtgl_resource_i18n.RES_KEY is '资源内容主键';

-- Add/modify columns 
alter table niutal_XTGL_RESOURCE_I18N add ZH_CN VARCHAR2(300);
alter table niutal_XTGL_RESOURCE_I18N add EN_US VARCHAR2(300);
-- Add comments to the columns 
comment on column niutal_XTGL_RESOURCE_I18N.ZH_CN  is '自定义中文描述';
comment on column niutal_XTGL_RESOURCE_I18N.EN_US  is '自定义英文描述';

  
insert into niutal_xtgl_jsgnmkdmb (GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX, CDJB, GNSYDX, YYTPLJ, MHGNMKDM, TBMHDZ, SFXS, SFZDYMK, GNMKYWMC, TBLJ, GNMKJC, GNMKYWJC, XSLX)
values ('N0150', '国际化资源配置', 'N01', null, '50', 2, null, null, null, null, '1', '0', null, null, null, null, '1');
insert into niutal_xtgl_jsgnmkdmb (GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX, CDJB, GNSYDX, YYTPLJ, MHGNMKDM, TBMHDZ, SFXS, SFZDYMK, GNMKYWMC, TBLJ, GNMKJC, GNMKYWJC, XSLX)
values ('N015005', '资源内容维护', 'N0150', '/i18n/i18n_cxI18nIndex.html', '05', 3, null, null, null, null, '1', '0', null, null, null, null, '0');
insert into niutal_xtgl_jsgnmkdmb (GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX, CDJB, GNSYDX, YYTPLJ, MHGNMKDM, TBMHDZ, SFXS, SFZDYMK, GNMKYWMC, TBLJ, GNMKJC, GNMKYWJC, XSLX)
values ('N015010', '资源文件维护', 'N0150', '/i18n/i18n_cxI18nResIndex.html', '10', 3, null, null, null, null, '1', '0', null, null, null, null, '0');

insert into niutal_xtgl_jsgnmkczb (JSDM, GNMKDM, CZDM, DYYM) values ('admin', 'N0150', 'cx', null);
insert into niutal_xtgl_gnmkczb (GNMKDM, CZDM, SFXS, SFZDYCZ, CZMC, YWMC) values ('N0150', 'cx', '0', '0', null, null);

insert into niutal_xtgl_jsgnmkczb (JSDM, GNMKDM, CZDM, DYYM) values ('admin', 'N015005', 'cx', null);
insert into niutal_xtgl_jsgnmkczb (JSDM, GNMKDM, CZDM, DYYM) values ('admin', 'N015005', 'xg', null);
insert into niutal_xtgl_jsgnmkczb (JSDM, GNMKDM, CZDM, DYYM) values ('admin', 'N015005', 'sc', null);
insert into niutal_xtgl_jsgnmkczb (JSDM, GNMKDM, CZDM, DYYM) values ('admin', 'N015005', 'zj', null);
insert into niutal_xtgl_gnmkczb (GNMKDM, CZDM, SFXS, SFZDYCZ, CZMC, YWMC) values ('N015005', 'cx', '0', '0', null, null);
insert into niutal_xtgl_gnmkczb (GNMKDM, CZDM, SFXS, SFZDYCZ, CZMC, YWMC) values ('N015005', 'zj', '1', '0', null, null);
insert into niutal_xtgl_gnmkczb (GNMKDM, CZDM, SFXS, SFZDYCZ, CZMC, YWMC) values ('N015005', 'sc', '1', '0', null, null);
insert into niutal_xtgl_gnmkczb (GNMKDM, CZDM, SFXS, SFZDYCZ, CZMC, YWMC) values ('N015005', 'xg', '1', '0', null, null);

insert into niutal_xtgl_jsgnmkczb (JSDM, GNMKDM, CZDM, DYYM) values ('admin', 'N015010', 'cx', null);
insert into niutal_xtgl_jsgnmkczb (JSDM, GNMKDM, CZDM, DYYM) values ('admin', 'N015010', 'xg', null);
insert into niutal_xtgl_jsgnmkczb (JSDM, GNMKDM, CZDM, DYYM) values ('admin', 'N015010', 'sc', null);
insert into niutal_xtgl_jsgnmkczb (JSDM, GNMKDM, CZDM, DYYM) values ('admin', 'N015010', 'zj', null);
insert into niutal_xtgl_jsgnmkczb (JSDM, GNMKDM, CZDM, DYYM) values ('admin', 'N015010', 'xz', null);
insert into niutal_xtgl_jsgnmkczb (JSDM, GNMKDM, CZDM, DYYM) values ('admin', 'N015010', 'fb', null);
insert into niutal_xtgl_gnmkczb (GNMKDM, CZDM, SFXS, SFZDYCZ, CZMC, YWMC) values ('N015010', 'cx', '0', '0', null, null);
insert into niutal_xtgl_gnmkczb (GNMKDM, CZDM, SFXS, SFZDYCZ, CZMC, YWMC) values ('N015010', 'zj', '1', '0', null, null);
insert into niutal_xtgl_gnmkczb (GNMKDM, CZDM, SFXS, SFZDYCZ, CZMC, YWMC) values ('N015010', 'sc', '1', '0', null, null);
insert into niutal_xtgl_gnmkczb (GNMKDM, CZDM, SFXS, SFZDYCZ, CZMC, YWMC) values ('N015010', 'xg', '1', '0', null, null);
insert into niutal_xtgl_gnmkczb (GNMKDM, CZDM, SFXS, SFZDYCZ, CZMC, YWMC) values ('N015010', 'xz', '1', '0', null, null);
insert into niutal_xtgl_gnmkczb (GNMKDM, CZDM, SFXS, SFZDYCZ, CZMC, YWMC) values ('N015010', 'fb', '1', '0', null, null);

insert into niutal_xtgl_czdmb (CZDM, CZMC, XSSX, ANYS, YWMC) values ('xz', '下载', '15', 'glyphicon glyphicon-cloud-download', 'Download');
commit;