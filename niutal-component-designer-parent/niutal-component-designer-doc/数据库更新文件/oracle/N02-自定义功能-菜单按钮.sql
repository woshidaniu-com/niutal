
-- Add/modify columns 
alter table niutal_xtgl_jsgnmkdmb add sfzdymk VARCHAR2(1) default 0;
-- Add comments to the columns 
comment on column niutal_xtgl_jsgnmkdmb.sfzdymk is '是否自定义模块：默认否；(1：是；0：否)';

-- Add/modify columns 
alter table niutal_xtgl_gnmkczb add sfzdycz VARCHAR2(1) default 0;
-- Add comments to the columns 
comment on column niutal_xtgl_gnmkczb.sfzdycz is '是否自定义操作代码：默认否；(1：是；0：否)';

update niutal_xtgl_jsgnmkdmb t set t.sfxs = '1',t.sfzdymk = '0';
update niutal_xtgl_jsgnmkdmb t set t.sfxs = '0' where t.fjgndm = 'N3047';
update niutal_xtgl_jsgnmkdmb t
   set t.sfzdymk = '1'
 where t.gnmkdm like 'N03%'
   and t.gnmkdm not in ('N0305','N0335','N0310','N0315','N0320');
   
update niutal_xtgl_gnmkczb t
   set t.sfzdycz = '0'
 where t.gnmkdm not in (select distinct x.gnmkdm
                          from niutal_xtgl_gnmkczb x
                         where x.gnmkdm like 'N03%');
update niutal_xtgl_gnmkczb t
   set t.sfzdycz = '0'
 where t.gnmkdm in ('N0305','N0335','N0310','N0315','N0320');

delete niutal_xtgl_jsgnmkdmb where gnmkdm = 'N03';
insert into niutal_xtgl_jsgnmkdmb (GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX, CDJB, GNSYDX)
values('N03','自定义功能管理','N','','2',1,'gl');

/*-----创建时间：2015-05-05--创建人：大康 --备注： 基础自动完成字段维护-菜单按钮------------*/ 

delete niutal_xtgl_jsgnmkdmb where gnmkdm = 'N0305';
delete niutal_xtgl_gnmkczb where gnmkdm = 'N0305';
delete niutal_xtgl_jsgnmkczb where gnmkdm = 'N0305';
insert into niutal_xtgl_jsgnmkdmb (GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX, CDJB, GNSYDX)
values ('N0305', '基础自动完成字段维护', 'N03', '/design/baseAuto_cxAutoCompleteFieldIndex.html', '1', 2, 'gl');

insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0305','cx','0','0');
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz) values('N0305','zj','1','0');
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz) values('N0305','xg','1','0');
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz) values('N0305','sc','1','0');
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0305','cx','');
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0305','zj','');
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0305','xg','');
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0305','sc','');
 
/*-----创建时间：2015-05-05--创建人：大康 --备注： 基础查询字段维护-菜单按钮------------*/ 

delete niutal_xtgl_jsgnmkdmb where gnmkdm = 'N0310';
delete niutal_xtgl_gnmkczb where gnmkdm = 'N0310';
delete niutal_xtgl_jsgnmkczb where gnmkdm = 'N0310';
insert into niutal_xtgl_jsgnmkdmb (GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX, CDJB, GNSYDX)
values ('N0310', '基础查询字段维护', 'N03', '/design/baseField_cxQueryFieldBaseIndex.html', '2', 2, 'gl');

insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0310','cx','0','0');
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz) values('N0310','zj','1','0');
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz) values('N0310','xg','1','0');
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz) values('N0310','sc','1','0');
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0310','cx','');
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0310','zj','');
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0310','xg','');
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0310','sc','');

/*-----创建时间：2015-05-05--创建人：大康 --备注： 基础功能js组件信息维护-菜单按钮------------*/ 

delete niutal_xtgl_jsgnmkdmb where gnmkdm = 'N0315';
delete niutal_xtgl_gnmkczb where gnmkdm = 'N0315';
delete niutal_xtgl_jsgnmkczb where gnmkdm = 'N0315';
insert into niutal_xtgl_jsgnmkdmb (GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX, CDJB, GNSYDX)
values ('N0315', '基础JQuery组件维护', 'N03', '/design/baseWidget_cxWidgetDetailIndex.html', '3', 2, 'gl');

insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0315','cx','0','0');
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz) values('N0315','zj','1','0');
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz) values('N0315','xg','1','0');
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz) values('N0315','sc','1','0');
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0315','cx','');
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0315','zj','');
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0315','xg','');
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0315','sc','');


/*-----创建时间：2015-05-05--创建人：大康 --备注： 自定义功能页面-菜单按钮------------*/ 


delete niutal_xtgl_jsgnmkdmb where gnmkdm = 'N0320';
delete niutal_xtgl_gnmkczb where gnmkdm = 'N0320';
delete niutal_xtgl_jsgnmkczb where gnmkdm = 'N0320';
delete niutal_xtgl_czdmb where czdm in('zjzdycd','xgzdycd','sczdycd','xzzdycd','sczdyym','zjzdyan','xgzdyan','bjzdyanz',
'sczdyan','zjzdyys','xgzdyys','sczdyys','zjysst','scysst');
 

insert into niutal_xtgl_czdmb (CZDM, CZMC, XSSX, ANYS)
values ('zjzdycd', '增加自定义菜单/功能', '103', 'glyphicon glyphicon-plus');
insert into niutal_xtgl_czdmb (CZDM, CZMC, XSSX, ANYS)
values ('xgzdycd', '修改自定义菜单/功能', '104', 'glyphicon glyphicon-edit');
insert into niutal_xtgl_czdmb (CZDM, CZMC, XSSX, ANYS)
values ('sczdycd', '删除自定义菜单/功能', '108', 'glyphicon glyphicon-remove'); 
insert into niutal_xtgl_czdmb (CZDM, CZMC, XSSX, ANYS)
values ('xzzdycd', '下载自定义功能升级SQL', '110', 'glyphicon glyphicon-cloud-download'); 
insert into niutal_xtgl_czdmb (CZDM, CZMC, XSSX, ANYS)
values ('sczdyym', '生成自定义界面', '112', 'glyphicon glyphicon-repeat'); 

insert into niutal_xtgl_czdmb (CZDM, CZMC, XSSX, ANYS)
values ('zjzdyan', '增加自定义按钮', '103', 'glyphicon glyphicon-plus');
insert into niutal_xtgl_czdmb (CZDM, CZMC, XSSX, ANYS)
values ('xgzdyan', '修改自定义按钮', '104', 'glyphicon glyphicon-edit');
insert into niutal_xtgl_czdmb (CZDM, CZMC, XSSX, ANYS)
values ('bjzdyanz', '修改自定义按钮组', '105', 'glyphicon glyphicon-edit');
insert into niutal_xtgl_czdmb (CZDM, CZMC, XSSX, ANYS)
values ('sczdyan', '删除自定义按钮', '106', 'glyphicon glyphicon-remove');  

insert into niutal_xtgl_czdmb (CZDM, CZMC, XSSX, ANYS)
values ('zjzdyys', '增加自定义元素', '108', 'glyphicon glyphicon-plus');
insert into niutal_xtgl_czdmb (CZDM, CZMC, XSSX, ANYS)
values ('xgzdyys', '修改自定义元素', '110', 'glyphicon glyphicon-edit');
insert into niutal_xtgl_czdmb (CZDM, CZMC, XSSX, ANYS)
values ('sczdyys', '删除自定义元素', '115', 'glyphicon glyphicon-remove');  

insert into niutal_xtgl_czdmb (CZDM, CZMC, XSSX, ANYS)
values ('zjysst', '绑定元素实体', '120', 'glyphicon glyphicon-edit');
insert into niutal_xtgl_czdmb (CZDM, CZMC, XSSX, ANYS)
values ('scysst', '删除元素实体', '125', 'glyphicon glyphicon-remove');  


insert into niutal_xtgl_jsgnmkdmb (GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX, CDJB, GNSYDX)
values ('N0320', '自定义功能设计', 'N03', '/design/designFunc_cxDesignFuncIndex.html', '4', 2, 'gl');

insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz) values('N0320','cx','0','0'); 
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0320','cx',''); 

insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0320','zjzdycd','1','0'); 
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0320','zjzdycd',''); 
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0320','xgzdycd','1','0'); 
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0320','xgzdycd',''); 
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0320','xzzdycd','1','0'); 
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0320','xzzdycd',''); 
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0320','sczdycd','1','0'); 
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0320','sczdycd',''); 
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0320','sczdyym','1','0'); 
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0320','sczdyym','');


insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0320','zjzdyan','1','0'); 
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0320','zjzdyan',''); 
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0320','xgzdyan','1','0'); 
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0320','xgzdyan','');
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0320','bjzdyanz','1','0'); 
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0320','bjzdyanz',''); 
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0320','sczdyan','1','0'); 
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0320','sczdyan','');  

insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0320','zjzdyys','1','0'); 
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0320','zjzdyys',''); 
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0320','xgzdyys','1','0'); 
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0320','xgzdyys',''); 
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0320','sczdyys','1','0'); 
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0320','sczdyys','');    
  
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0320','zjysst','1','0'); 
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0320','zjysst',''); 
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0320','scysst','1','0'); 
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0320','scysst','');    

   
/*-----创建时间：2015-05-05--创建人：大康 --备注： 自定义功能Demo-菜单按钮----------

delete niutal_xtgl_jsgnmkdmb where gnmkdm = 'N0325';
delete niutal_xtgl_gnmkczb where gnmkdm = 'N0325';
delete niutal_xtgl_jsgnmkczb where gnmkdm = 'N0325';
insert into niutal_xtgl_jsgnmkdmb (GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX, CDJB, GNSYDX,SFZDYMK)
values ('N0325', '自定义功能Demo', 'N03', '/design/designFunc_cxDesignFuncPageIndex.html', '5', 2, 'gl', '1');
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0325','cx','0','0');
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0325','cx','');
--*/ 

delete niutal_xtgl_czdmb where czdm in('bjfunczy');
insert into niutal_xtgl_czdmb (CZDM, CZMC, XSSX, ANYS)
values ('bjfunczy', '编辑功能自定义资源', '110', 'glyphicon glyphicon-edit');
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('N0320','bjfunczy','1','0'); 
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,dyym) values('admin','N0320','bjfunczy',''); 

/*-----打包时间：--打包人：--版本号：-----此标记作为打包分割线---------*/ 

/*-----创建时间：2014-12-12--创建人：--备注： ------------*/ 

commit;