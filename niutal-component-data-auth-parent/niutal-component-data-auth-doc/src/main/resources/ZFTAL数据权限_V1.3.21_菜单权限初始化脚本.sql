delete from niutal_xtgl_jsgnmkdmb where gnmkdm = 'N0130';
insert into niutal_xtgl_jsgnmkdmb (GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX, GNJC, TBLJ, SFQY)values ('N0130', '数据权限管理', 'N01', '', '1', '', 'fa fa-cogs', '1');

delete from niutal_xtgl_jsgnmkdmb where gnmkdm = 'N013001';
insert into niutal_xtgl_jsgnmkdmb(gnmkdm,gnmkmc,fjgndm,dyym,xssx,gnjc,tblj,sfqy) values ('N013001','数据权限','N0130','/dataauth/data/index.zf','3',null,null,'1');

delete from niutal_xtgl_gnmkczb where gnmkdm = 'N013001';
insert into niutal_xtgl_gnmkczb(gnczid,gnmkdm,czdm,qxdm,xssx) values ('N013001_#','N013001','#','dataauth:dataauth:cx','1');
insert into niutal_xtgl_gnmkczb(gnczid,gnmkdm,czdm,qxdm,xssx) values ('N013001_bc','N013001','bc','dataauth:dataauth:bc','2');

delete from niutal_xtgl_jsgnmkdmb where gnmkdm = 'N013002';
insert into niutal_xtgl_jsgnmkdmb(gnmkdm,gnmkmc,fjgndm,dyym,xssx,gnjc,tblj,sfqy) values ('N013002','规则设计','N0130','/dataauth/data/rule/group/index.zf','4',null,null,'1');

delete from niutal_xtgl_gnmkczb where gnmkdm = 'N013002';
insert into niutal_xtgl_gnmkczb(gnczid,gnmkdm,czdm,qxdm,xssx) values ('N013002_#','N013002','#','dataauth:datarule:cx','1');
insert into niutal_xtgl_gnmkczb(gnczid,gnmkdm,czdm,qxdm,xssx) values ('N013002_zj','N013002','zj','dataauth:datarule:zj','2');
insert into niutal_xtgl_gnmkczb(gnczid,gnmkdm,czdm,qxdm,xssx) values ('N013002_xg','N013002','xg','dataauth:datarule:xg','3');
insert into niutal_xtgl_gnmkczb(gnczid,gnmkdm,czdm,qxdm,xssx) values ('N013002_sc','N013002','sc','dataauth:datarule:sc','4');