

declare

  tb_name VARCHAR2(50);
  cursor table_cursor is
    select t.table_name
      from all_tables t
     where (upper(t.table_name) like '${table_pre}%' or
           upper(t.table_name) in (${table_names}))
       and upper(t.owner) = upper((select username from user_users));

begin

  open table_cursor;
  LOOP
    exit when table_cursor%notfound;
    tb_name := null;
    FETCH table_cursor into tb_name;
    if tb_name is null then
      dbms_output.put_line('Basic tables have been cleared. ');
      exit;
    end if;
    dbms_output.put_line('drop table ' || tb_name);
    Execute Immediate ('drop table ' || tb_name);
  
  end LOOP;
  close table_cursor;

end;
/
 
commit;

/*部分表结构初始化*/

-- Create table
create table niutal_XTGL_BJDMB
(
  NJDM_ID   VARCHAR2(32),
  JBNY      VARCHAR2(20),
  BZXH_ID   VARCHAR2(20),
  FDYJGH_ID VARCHAR2(20),
  ZXRS      VARCHAR2(32),
  BZ        VARCHAR2(100),
  ZJR       VARCHAR2(50),
  ZJSJ      VARCHAR2(20),
  XGR       VARCHAR2(50),
  XGSJ      VARCHAR2(20),
  BJJC      VARCHAR2(20),
  BH        VARCHAR2(20),
  BJ        VARCHAR2(50),
  BZRJGH_ID VARCHAR2(32),
  JG_ID     VARCHAR2(32),
  XQH_ID    VARCHAR2(32),
  BH_ID     VARCHAR2(32) default sys_guid() not null,
  ZYH_ID    VARCHAR2(32)
);
-- Add comments to the table 
comment on table niutal_XTGL_BJDMB  is '班级代码表';
-- Add comments to the columns 
comment on column niutal_XTGL_BJDMB.NJDM_ID  is '年级代码';
comment on column niutal_XTGL_BJDMB.JBNY  is '建班年月';
comment on column niutal_XTGL_BJDMB.BZXH_ID  is '';
comment on column niutal_XTGL_BJDMB.FDYJGH_ID  is '辅导员号';
comment on column niutal_XTGL_BJDMB.ZXRS  is '在校人数';
comment on column niutal_XTGL_BJDMB.BZ  is '备注';
comment on column niutal_XTGL_BJDMB.ZJR  is '增加人';
comment on column niutal_XTGL_BJDMB.ZJSJ  is '增加时间';
comment on column niutal_XTGL_BJDMB.XGR  is '修改人';
comment on column niutal_XTGL_BJDMB.XGSJ  is '修改时间';
comment on column niutal_XTGL_BJDMB.BJJC  is '班级简称';
comment on column niutal_XTGL_BJDMB.BH  is '班号';
comment on column niutal_XTGL_BJDMB.BJ  is '班级';
comment on column niutal_XTGL_BJDMB.BZRJGH_ID  is '班主任教工号ID';
comment on column niutal_XTGL_BJDMB.JG_ID  is '机构代码ID';
comment on column niutal_XTGL_BJDMB.XQH_ID  is '校区号ID';
comment on column niutal_XTGL_BJDMB.BH_ID  is '班号id';
comment on column niutal_XTGL_BJDMB.ZYH_ID  is '专业号id'; 
-- Create/Recreate indexes 
create index niutal_XTGL_BJDMB_INDEX on niutal_XTGL_BJDMB (NJDM_ID, ZYH_ID);

-- Create table
create table niutal_XTGL_BZRFDYSJFWB
(
  GWJBDM VARCHAR2(20) not null,
  BJDM   VARCHAR2(30) not null,
  JGH_ID VARCHAR2(32) not null,
  BH_ID  VARCHAR2(32),
  primary key (JGH_ID)
);
-- Add comments to the table 
comment on table niutal_XTGL_BZRFDYSJFWB  is '班主任辅导员数据范围表';
-- Add comments to the columns 
comment on column niutal_XTGL_BZRFDYSJFWB.GWJBDM  is '岗位级别(bzr,fdy)';
comment on column niutal_XTGL_BZRFDYSJFWB.BJDM  is '班级代码';
comment on column niutal_XTGL_BZRFDYSJFWB.JGH_ID  is '教工号id';
comment on column niutal_XTGL_BZRFDYSJFWB.BH_ID  is '班号id'; 

-- Create table
create table niutal_XTGL_CYDMB
(
  CYDM_ID VARCHAR2(32) not null,
  DMLX    VARCHAR2(20) not null,
  DMMC    VARCHAR2(50) not null,
  DMBH    VARCHAR2(20) not null,
  SFYX    CHAR(1),
  BZ      VARCHAR2(100),
  primary key (CYDM_ID)
);
-- Add comments to the table 
comment on table niutal_XTGL_CYDMB  is '常用代码表';
-- Add comments to the columns 
comment on column niutal_XTGL_CYDMB.CYDM_ID  is '代码ID（自增长）';
comment on column niutal_XTGL_CYDMB.DMLX  is '代码类型';
comment on column niutal_XTGL_CYDMB.DMMC  is '代码名称';
comment on column niutal_XTGL_CYDMB.DMBH  is '代码编号';
comment on column niutal_XTGL_CYDMB.SFYX  is '是否有效（1有效，0无效）';
comment on column niutal_XTGL_CYDMB.BZ  is '备注'; 

-- Create table
create table niutal_XTGL_CZDMB
(
  CZDM VARCHAR2(10) not null,
  CZMC VARCHAR2(30) not null,
  XSSX VARCHAR2(5),
  ANYS VARCHAR2(60),
  YWMC VARCHAR2(200),
   primary key (CZDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_CZDMB  is '操作代码表';
-- Add comments to the columns 
comment on column niutal_XTGL_CZDMB.CZDM  is '操作代码';
comment on column niutal_XTGL_CZDMB.CZMC  is '操作名称';
comment on column niutal_XTGL_CZDMB.XSSX  is '显示顺序';
comment on column niutal_XTGL_CZDMB.ANYS  is '按钮样式';
comment on column niutal_XTGL_CZDMB.YWMC  is '英文名称'; 

  
-- Create table
create table niutal_XTGL_CZRZB
(
  CZBH    VARCHAR2(32) not null,
  CZRQ    VARCHAR2(30) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
  CZMK    VARCHAR2(50),
  YWMC    VARCHAR2(1000),
  CZLX    VARCHAR2(50),
  CZMS    VARCHAR2(2000),
  IPDZ    VARCHAR2(20),
  ZJM     VARCHAR2(100),
  YHM     VARCHAR2(20),
  RZZH_ID VARCHAR2(32) default sys_guid(),
  primary key (CZBH)
);
-- Add comments to the table 
comment on table niutal_XTGL_CZRZB  is '操作日志表';
-- Add comments to the columns 
comment on column niutal_XTGL_CZRZB.CZBH  is '操作编号';
comment on column niutal_XTGL_CZRZB.CZRQ  is '操作日期';
comment on column niutal_XTGL_CZRZB.CZMK  is '操作模块';
comment on column niutal_XTGL_CZRZB.YWMC  is '业务名称';
comment on column niutal_XTGL_CZRZB.CZLX  is '操作类型';
comment on column niutal_XTGL_CZRZB.CZMS  is '操作描述';
comment on column niutal_XTGL_CZRZB.IPDZ  is 'IP地址';
comment on column niutal_XTGL_CZRZB.ZJM  is '主机名';
comment on column niutal_XTGL_CZRZB.YHM  is '用户名';
comment on column niutal_XTGL_CZRZB.RZZH_ID  is '日志组号'; 

-- Create table
create table niutal_XTGL_CZRZB1
(
  CZBH    VARCHAR2(32) default sys_guid() not null,
  CZRQ    VARCHAR2(30) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
  CZMK    VARCHAR2(50),
  YWMC    VARCHAR2(1000),
  CZLX    VARCHAR2(50),
  CZMS    CLOB,
  IPDZ    VARCHAR2(20),
  ZJM     VARCHAR2(100),
  YHM     VARCHAR2(20),
  RZZH_ID VARCHAR2(32) default sys_guid()
);
-- Add comments to the table 
comment on table niutal_XTGL_CZRZB1  is '操作日志表';
-- Add comments to the columns 
comment on column niutal_XTGL_CZRZB1.CZBH  is '操作编号';
comment on column niutal_XTGL_CZRZB1.CZRQ  is '操作日期';
comment on column niutal_XTGL_CZRZB1.CZMK  is '操作模块';
comment on column niutal_XTGL_CZRZB1.YWMC  is '业务名称';
comment on column niutal_XTGL_CZRZB1.CZLX  is '操作类型';
comment on column niutal_XTGL_CZRZB1.CZMS  is '操作描述';
comment on column niutal_XTGL_CZRZB1.IPDZ  is 'IP地址';
comment on column niutal_XTGL_CZRZB1.ZJM  is '主机名';
comment on column niutal_XTGL_CZRZB1.YHM  is '用户名';
comment on column niutal_XTGL_CZRZB1.RZZH_ID  is '日志组号';
  
-- Create table
create table niutal_XTGL_GLJG
(
  GLJG_ID NUMBER not null,
  JGMC    VARCHAR2(20),
  XGSJ    VARCHAR2(8),
  XGR     VARCHAR2(50),
  ZJSJ    VARCHAR2(8),
  ZJR     VARCHAR2(50),
  primary key (GLJG_ID)
);
-- Add comments to the table 
comment on table niutal_XTGL_GLJG  is '管理机构表';
-- Add comments to the columns 
comment on column niutal_XTGL_GLJG.GLJG_ID  is '机构代码ID（自增长）';
comment on column niutal_XTGL_GLJG.JGMC  is '机构名称';
comment on column niutal_XTGL_GLJG.XGSJ  is '修改时间';
comment on column niutal_XTGL_GLJG.XGR  is '修改人';
comment on column niutal_XTGL_GLJG.ZJSJ  is '增加时间';
comment on column niutal_XTGL_GLJG.ZJR  is '增加人'; 

-- Create table
create table niutal_XTGL_GNCZSJKZB
(
  GNMKDM VARCHAR2(10) not null,
  CZDM   VARCHAR2(30) not null,
  KSSJ   VARCHAR2(30),
  JSSJ   VARCHAR2(100),
  SFSY   VARCHAR2(30) default '1' not null,
  TSXX   VARCHAR2(500),
  BZ     VARCHAR2(1000),
  primary key (GNMKDM, CZDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_GNCZSJKZB  is '功能操作时间控制表';
-- Add comments to the columns 
comment on column niutal_XTGL_GNCZSJKZB.GNMKDM  is '功能模块代码';
comment on column niutal_XTGL_GNCZSJKZB.CZDM  is '操作代码';
comment on column niutal_XTGL_GNCZSJKZB.KSSJ  is '开始时间';
comment on column niutal_XTGL_GNCZSJKZB.JSSJ  is '结束时间';
comment on column niutal_XTGL_GNCZSJKZB.SFSY  is '是否使用（1：使用；0：停用）';
comment on column niutal_XTGL_GNCZSJKZB.TSXX  is '提示信息';
comment on column niutal_XTGL_GNCZSJKZB.BZ  is '备注信息'; 

-- Create table
create table niutal_XTGL_GNMKCZB
(
  GNMKDM  VARCHAR2(10) not null,
  CZDM    VARCHAR2(10) not null,
  SFXS    VARCHAR2(1) default 1,
  SFZDYCZ VARCHAR2(1) default 0,
  CZMC    VARCHAR2(30),
  YWMC    VARCHAR2(200),
  primary key (GNMKDM, CZDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_GNMKCZB  is '功能模块操作表';
-- Add comments to the columns 
comment on column niutal_XTGL_GNMKCZB.GNMKDM  is '功能模块代码';
comment on column niutal_XTGL_GNMKCZB.CZDM  is '操作代码';
comment on column niutal_XTGL_GNMKCZB.SFXS  is '是否显示(1:显示0:不显示)';
comment on column niutal_XTGL_GNMKCZB.SFZDYCZ  is '是否自定义操作代码：默认否；(1：是；0：否)';
comment on column niutal_XTGL_GNMKCZB.CZMC  is '操作名称;如果该值为空则取对应niutal_xtgl_czdmb的操作名称';
comment on column niutal_XTGL_GNMKCZB.YWMC  is '英文名称'; 

-- Create table
create table niutal_XTGL_GNPXXXB
(
  GNMKDM VARCHAR2(10) not null,
  YXJ    VARCHAR2(30) default '1' not null,
  ZDMC   VARCHAR2(30) not null,
  ZDMS   VARCHAR2(100) not null,
  PXFS   VARCHAR2(30) default 'asc' not null,
  primary key (GNMKDM, ZDMC)
);
-- Add comments to the table 
comment on table niutal_XTGL_GNPXXXB  is '业务数据排序信息表';
-- Add comments to the columns 
comment on column niutal_XTGL_GNPXXXB.GNMKDM  is '功能模块代码';
comment on column niutal_XTGL_GNPXXXB.YXJ  is '优先级：排序的先后顺序';
comment on column niutal_XTGL_GNPXXXB.ZDMC  is '排序字段;如：xh';
comment on column niutal_XTGL_GNPXXXB.ZDMS  is '排序字段描述;如：学号';
comment on column niutal_XTGL_GNPXXXB.PXFS  is '排序方式：asc 或者 desc'; 

-- Create table
create table niutal_XTGL_GWJBDMB
(
  GWJBDM VARCHAR2(20) not null,
  GWJBMC VARCHAR2(30) not null,
  primary key (GWJBDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_GWJBDMB  is '岗位级别代码表';
-- Add comments to the columns 
comment on column niutal_XTGL_GWJBDMB.GWJBDM  is '岗位级别代码';
comment on column niutal_XTGL_GWJBDMB.GWJBMC  is '岗位级别名称'; 

-- Create table
create table niutal_XTGL_GYGLYSJFWB
(
  ZGH  VARCHAR2(40) not null,
  BMLX VARCHAR2(20) not null,
  BMDM VARCHAR2(30) not null,
  primary key (ZGH, BMLX, BMDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_GYGLYSJFWB  is '公寓管理员数据范围表';
-- Add comments to the columns 
comment on column niutal_XTGL_GYGLYSJFWB.ZGH  is '职工号';
comment on column niutal_XTGL_GYGLYSJFWB.BMLX  is '部门类型(nj,xy,zy,bj)';
comment on column niutal_XTGL_GYGLYSJFWB.BMDM  is '部门代码'; 

-- Create table
create table niutal_XTGL_JCSJB
(
  LX   VARCHAR2(20) not null,
  DM   VARCHAR2(20) not null,
  MC   VARCHAR2(200) not null,
  SFMR VARCHAR2(2) default '0',
  YWMC VARCHAR2(200),
  primary key (LX, DM)
);
-- Add comments to the table 
comment on table niutal_XTGL_JCSJB  is '基础数据表';
-- Add comments to the columns 
comment on column niutal_XTGL_JCSJB.LX  is '类型';
comment on column niutal_XTGL_JCSJB.DM  is '代码';
comment on column niutal_XTGL_JCSJB.MC  is '名称';
comment on column niutal_XTGL_JCSJB.SFMR  is '是否默认 1表示默认；0表示不默认';
comment on column niutal_XTGL_JCSJB.YWMC  is '英文名称'; 

-- Create table
create table niutal_XTGL_JCSJLXB
(
  LXDM VARCHAR2(20) not null,
  LXMC VARCHAR2(60) not null,
  XTJB VARCHAR2(2) default 'yw',
   primary key (LXDM)
) ;
-- Add comments to the table 
comment on table niutal_XTGL_JCSJLXB  is '基础数据类型表';
-- Add comments to the columns 
comment on column niutal_XTGL_JCSJLXB.LXDM  is '类型代码';
comment on column niutal_XTGL_JCSJLXB.LXMC  is '类型名称';
comment on column niutal_XTGL_JCSJLXB.XTJB  is '系统级别,xt:系统,yw:业务'; 

-- Create table
create table niutal_XTGL_JGDMB
(
  JG_ID    VARCHAR2(32) default sys_guid() not null,
  JGDM     VARCHAR2(10),
  JGMC     VARCHAR2(100),
  JGYWMC   VARCHAR2(100),
  JGJC     VARCHAR2(50),
  JGJP     VARCHAR2(20),
  JGDZ     VARCHAR2(100),
  LSSJJGID VARCHAR2(320),
  LSXQID   VARCHAR2(32),
  JGYXBS   VARCHAR2(2),
  SFJXBM   VARCHAR2(2),
  SFST     VARCHAR2(2),
  JLNY     VARCHAR2(10),
  JGYZBM   VARCHAR2(7),
  FZRJGH   VARCHAR2(20),
  KKXY     VARCHAR2(20),
  JGLB     VARCHAR2(20),
  JGJJ     CLOB,
  primary key (JG_ID)
);
-- Add comments to the table 
comment on table niutal_XTGL_JGDMB  is '机构代码表';
-- Add comments to the columns 
comment on column niutal_XTGL_JGDMB.JG_ID  is '机构id';
comment on column niutal_XTGL_JGDMB.JGDM  is '机构代码';
comment on column niutal_XTGL_JGDMB.JGMC  is '机构名称';
comment on column niutal_XTGL_JGDMB.JGYWMC  is '机构英文名称';
comment on column niutal_XTGL_JGDMB.JGJC  is '机构简称';
comment on column niutal_XTGL_JGDMB.JGJP  is '机构简拼';
comment on column niutal_XTGL_JGDMB.JGDZ  is '机构地址';
comment on column niutal_XTGL_JGDMB.LSSJJGID  is '隶属上级机构id';
comment on column niutal_XTGL_JGDMB.LSXQID  is '隶属校区id';
comment on column niutal_XTGL_JGDMB.JGYXBS  is '机构有效标识';
comment on column niutal_XTGL_JGDMB.SFJXBM  is '是否教学部门（1：教学部门；0非教学部门）';
comment on column niutal_XTGL_JGDMB.SFST  is '是否实体';
comment on column niutal_XTGL_JGDMB.JLNY  is '建立年月';
comment on column niutal_XTGL_JGDMB.JGYZBM  is '机构邮政编码';
comment on column niutal_XTGL_JGDMB.FZRJGH  is '负责人教工号';
comment on column niutal_XTGL_JGDMB.KKXY  is '开课学院或学生学院（1，开课学院     2，学生学院；3既开课学院又学生学院）';
comment on column niutal_XTGL_JGDMB.JGLB  is '机构类别1 教学院系
2 科研机构
3 公共服务 指图书馆、档案馆、分析测试中心、计算/网络/信息/电教/教育技术中心等学术支撑单位
4 党务部门 含工会、团委、妇委会
5 行政机构
6 附属单位
7 后勤部门
8 校办产业
9 其他
基础代码表DMLX=BMLB
允许多选，分号隔离；
';
comment on column niutal_XTGL_JGDMB.JGJJ
  is '机构简介'; 

-- Create table
create table niutal_XTGL_JSGNMKCZB
(
  JSDM   VARCHAR2(32) not null,
  GNMKDM VARCHAR2(10) not null,
  CZDM   VARCHAR2(10) default '#' not null,
  DYYM   VARCHAR2(100),
  primary key (JSDM, GNMKDM, CZDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_JSGNMKCZB  is '角色功能模块操作表';
-- Add comments to the columns 
comment on column niutal_XTGL_JSGNMKCZB.JSDM  is '角色代码';
comment on column niutal_XTGL_JSGNMKCZB.GNMKDM  is '功能模块代码';
comment on column niutal_XTGL_JSGNMKCZB.CZDM  is '操作代码';
comment on column niutal_XTGL_JSGNMKCZB.DYYM  is '对应页面'; 


-- Create table
create table niutal_XTGL_XTGNMKDMB
(
  XTGNMKDM   VARCHAR2(50) not null,
  GNMKDM   VARCHAR2(10) not null,
  primary key (XTGNMKDM,GNMKDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_XTGNMKDMB is '系统功能模块代码表：当前运行系统拥有的一级模块代码';
-- Add comments to the columns 
comment on column niutal_XTGL_XTGNMKDMB.XTGNMKDM is '功能模块代码'; 
comment on column niutal_XTGL_XTGNMKDMB.GNMKDM is '功能模块代码'; 

-- Create table
create table niutal_XTGL_JSGNMKDMB
(
  GNMKDM   VARCHAR2(10) not null,
  GNMKMC   VARCHAR2(50) not null,
  FJGNDM   VARCHAR2(10) not null,
  DYYM     VARCHAR2(100),
  XSSX     VARCHAR2(2),
  CDJB     NUMBER(2),
  GNSYDX   VARCHAR2(2),
  YYTPLJ   VARCHAR2(200),
  MHGNMKDM VARCHAR2(20),
  SFXS     VARCHAR2(1) default 1,
  SFZDYMK  VARCHAR2(1) default 0,
  TBMHDZ   VARCHAR2(100),
  GNMKYWMC VARCHAR2(200),
  TBLJ     VARCHAR2(200),
  GNMKJC   VARCHAR2(50),
  GNMKYWJC VARCHAR2(50),
  primary key (GNMKDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_JSGNMKDMB  is '教师功能模块代码表';
-- Add comments to the columns 
comment on column niutal_XTGL_JSGNMKDMB.GNMKDM  is '功能模块代码';
comment on column niutal_XTGL_JSGNMKDMB.GNMKMC  is '功能模块名称';
comment on column niutal_XTGL_JSGNMKDMB.FJGNDM  is '父级功能代码';
comment on column niutal_XTGL_JSGNMKDMB.DYYM  is '对应页面';
comment on column niutal_XTGL_JSGNMKDMB.XSSX  is '显示顺序';
comment on column niutal_XTGL_JSGNMKDMB.CDJB  is '菜单级别';
comment on column niutal_XTGL_JSGNMKDMB.GNSYDX  is '功能使用对象(学生:xs;教师:js;管理:gl;空:面向所有用户)';
comment on column niutal_XTGL_JSGNMKDMB.MHGNMKDM  is '提供给门户的功能模块代码';
comment on column niutal_XTGL_JSGNMKDMB.SFXS  is '是否显示(1:显示0:不显示)';
comment on column niutal_XTGL_JSGNMKDMB.SFZDYMK  is '是否自定义模块：默认否；(1：是；0：否)';
comment on column niutal_XTGL_JSGNMKDMB.TBMHDZ  is '同步门户地址';
comment on column niutal_XTGL_JSGNMKDMB.GNMKYWMC  is '功能模块英文名称';
comment on column niutal_XTGL_JSGNMKDMB.TBLJ  is '菜单图标路径，在最近使用或者我的应用中会用到';
comment on column niutal_XTGL_JSGNMKDMB.GNMKJC  is '功能模块简称，在最近使用或者我的应用中会用到';
comment on column niutal_XTGL_JSGNMKDMB.GNMKYWJC  is '功能模块英文简称，在最近使用或者我的应用中会用到'; 

-- Create table
create table niutal_XTGL_JSGNMKGXB
(
  JSTOPMKMCB_ID VARCHAR2(32) not null,
  GNMKDM        VARCHAR2(10) not null,
  XSXH          NUMBER not null,
  primary key (JSTOPMKMCB_ID, GNMKDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_JSGNMKGXB  is '角色功能模块关系表';
-- Add comments to the columns 
comment on column niutal_XTGL_JSGNMKGXB.JSTOPMKMCB_ID  is '角色一级模块名称表Id';
comment on column niutal_XTGL_JSGNMKGXB.GNMKDM  is '功能模块代码';
comment on column niutal_XTGL_JSGNMKGXB.XSXH  is '显示顺序'; 


-- Create table
create table niutal_XTGL_JSKCGLFWB
(
  JSDM  VARCHAR2(32) not null,
  JG_ID VARCHAR2(32) not null,
  ZJR   VARCHAR2(50),
  ZJSJ  VARCHAR2(20),
  XGR   VARCHAR2(50),
  XGSJ  VARCHAR2(20),
  primary key (JSDM, JG_ID)
);
-- Add comments to the table 
comment on table niutal_XTGL_JSKCGLFWB  is '角色课程管理范围表';
-- Add comments to the columns 
comment on column niutal_XTGL_JSKCGLFWB.JSDM  is '角色代码';
comment on column niutal_XTGL_JSKCGLFWB.JG_ID  is '部门ID';
comment on column niutal_XTGL_JSKCGLFWB.ZJR  is '增加人(用户名：XXX，姓名 ：XXX)';
comment on column niutal_XTGL_JSKCGLFWB.ZJSJ  is '增加时间';
comment on column niutal_XTGL_JSKCGLFWB.XGR  is '修改人(用户名：XXX，姓名 ：XXX)';
comment on column niutal_XTGL_JSKCGLFWB.XGSJ  is '修改时间'; 

-- Create table
create table niutal_XTGL_JSTOPMKMCB
(
  JSTOPMKMCB_ID VARCHAR2(32) not null,
  JSDM          VARCHAR2(32) not null,
  MKMC          VARCHAR2(200) not null,
  XSXH          NUMBER not null,
  YWMKMC        VARCHAR2(200),
  primary key (JSTOPMKMCB_ID)
);
-- Add comments to the table 
comment on table niutal_XTGL_JSTOPMKMCB  is '角色一级模块名称表';
-- Add comments to the columns 
comment on column niutal_XTGL_JSTOPMKMCB.JSTOPMKMCB_ID  is '角色一级模块名称表Id';
comment on column niutal_XTGL_JSTOPMKMCB.JSDM  is '角色代码';
comment on column niutal_XTGL_JSTOPMKMCB.MKMC  is '模块名称';
comment on column niutal_XTGL_JSTOPMKMCB.XSXH  is '显示顺序';
comment on column niutal_XTGL_JSTOPMKMCB.YWMKMC  is '英文模块名称';

-- Create table
create table niutal_XTGL_JSXSGLFWB
(
  JSDM      VARCHAR2(32) not null,
  XSGLFW_ID VARCHAR2(32) not null,
  ZJR       VARCHAR2(50),
  ZJSJ      VARCHAR2(20),
  XGR       VARCHAR2(50),
  XGSJ      VARCHAR2(20),
  primary key (JSDM, XSGLFW_ID)
);
-- Add comments to the table 
comment on table niutal_XTGL_JSXSGLFWB  is '角色学生管理范围表';
-- Add comments to the columns 
comment on column niutal_XTGL_JSXSGLFWB.JSDM  is '角色代码';
comment on column niutal_XTGL_JSXSGLFWB.XSGLFW_ID  is '学生管理范围代码';
comment on column niutal_XTGL_JSXSGLFWB.ZJR  is '增加人(用户名：XXX，姓名 ：XXX)';
comment on column niutal_XTGL_JSXSGLFWB.ZJSJ  is '增加时间';
comment on column niutal_XTGL_JSXSGLFWB.XGR  is '修改人(用户名：XXX，姓名 ：XXX)';
comment on column niutal_XTGL_JSXSGLFWB.XGSJ  is '修改时间'; 

-- Create table
create table niutal_XTGL_JSXXB
(
  JSDM   VARCHAR2(32) default sys_guid() not null,
  JSMC   VARCHAR2(50) not null,
  JSSM   VARCHAR2(2000),
  SFKSC  VARCHAR2(2) default '0',
  GWJBDM VARCHAR2(2),
  SFEJSQ VARCHAR2(2),
  GNMKDM VARCHAR2(10),
  FJSDM  VARCHAR2(32),
  JSJB   VARCHAR2(2) default 1,
  JSLXDM VARCHAR2(32),
  JGH    VARCHAR2(32),
  JSGYBJ VARCHAR2(1),
  ZGH    VARCHAR2(32),
  primary key (JSDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_JSXXB  is '角色信息表';
-- Add comments to the columns 
comment on column niutal_XTGL_JSXXB.JSDM  is '角色代码';
comment on column niutal_XTGL_JSXXB.JSMC  is '角色名称';
comment on column niutal_XTGL_JSXXB.JSSM  is '角色说明';
comment on column niutal_XTGL_JSXXB.SFKSC  is '是否可删除(1是0否,默认为0)';
comment on column niutal_XTGL_JSXXB.GWJBDM  is '岗位级别';
comment on column niutal_XTGL_JSXXB.SFEJSQ  is '是否可二级授权(1是0否,默认为0)';
comment on column niutal_XTGL_JSXXB.GNMKDM  is '功能模块代码(二级授权关联的功能模块代码,NULL:为全部功能模块)';
comment on column niutal_XTGL_JSXXB.FJSDM  is '父级角色代码';
comment on column niutal_XTGL_JSXXB.JSJB  is '角色级别';
comment on column niutal_XTGL_JSXXB.JSLXDM  is '角色类型代码';
comment on column niutal_XTGL_JSXXB.JGH  is '角色创建人教工号';
comment on column niutal_XTGL_JSXXB.JSGYBJ  is '角色公用标记1公用;0非公用角色';
comment on column niutal_XTGL_JSXXB.ZGH  is '角色创建人职工号'; 

-- Create table
create table niutal_XTGL_JWMHYWDMDZB
(
  GNMKDM VARCHAR2(50) not null,
  MHYWDM VARCHAR2(50),
  primary key (GNMKDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_JWMHYWDMDZB  is '教务与门户业务代码对照表';
-- Add comments to the columns 
comment on column niutal_XTGL_JWMHYWDMDZB.GNMKDM  is '教务功能模块代码';
comment on column niutal_XTGL_JWMHYWDMDZB.MHYWDM  is '门户业务代码'; 

-- Create table
create table niutal_XTGL_NJDMB
(
  NJDM_ID VARCHAR2(32) default sys_guid() not null,
  NJDM    VARCHAR2(4),
  NJMC    VARCHAR2(20),
  ZJR     VARCHAR2(50),
  ZJSJ    VARCHAR2(20),
  XGR     VARCHAR2(50),
  XGSJ    VARCHAR2(20),
  SFSY    VARCHAR2(2),
  primary key (NJDM_ID)
);
-- Add comments to the table 
comment on table niutal_XTGL_NJDMB  is '年级代码表';
-- Add comments to the columns 
comment on column niutal_XTGL_NJDMB.NJDM_ID  is '年级代码ID（自增长）';
comment on column niutal_XTGL_NJDMB.NJDM  is '年级代码';
comment on column niutal_XTGL_NJDMB.NJMC  is '年级名称';
comment on column niutal_XTGL_NJDMB.ZJR  is '增加人';
comment on column niutal_XTGL_NJDMB.ZJSJ  is '增加时间';
comment on column niutal_XTGL_NJDMB.XGR  is '修改人';
comment on column niutal_XTGL_NJDMB.XGSJ  is '修改时间';
comment on column niutal_XTGL_NJDMB.SFSY  is '是否使用（1：使用；2：停用）'; 


-- Create table
create table niutal_XTGL_PLXGXXB
(
  GNMKDM VARCHAR2(10) not null,
  ZDDM   VARCHAR2(50) not null,
  ZDMC   VARCHAR2(50) not null,
  ZDBM   VARCHAR2(50) not null,
  XSSX   NUMBER default 1 not null,
  SFQY   CHAR(1) default '0' not null,
  ZDCD   VARCHAR2(4),
  ZDZYQ  VARCHAR2(255),
  ZDLX   NUMBER,
  ZDLY   VARCHAR2(1000)
);
-- Add comments to the table 
comment on table niutal_XTGL_PLXGXXB  is '功能数据批量修改配置表';
-- Add comments to the columns 
comment on column niutal_XTGL_PLXGXXB.GNMKDM  is '功能模块代码';
comment on column niutal_XTGL_PLXGXXB.ZDDM  is '字段代码:表结构的字段列名称';
comment on column niutal_XTGL_PLXGXXB.ZDMC  is '字段名称:该字段的文本名称；如：姓名';
comment on column niutal_XTGL_PLXGXXB.ZDBM  is '字段所属表的名称';
comment on column niutal_XTGL_PLXGXXB.XSSX  is '字段显示的顺序';
comment on column niutal_XTGL_PLXGXXB.SFQY  is '是否启用,默认 0 ; 1:启用, 0 :关闭';
comment on column niutal_XTGL_PLXGXXB.ZDCD  is '字段长度';
comment on column niutal_XTGL_PLXGXXB.ZDZYQ  is '字段值要求;  例如 required:true;stringMaxLength:30;range:[0,100] (多个用;隔开)';
comment on column niutal_XTGL_PLXGXXB.ZDLX  is '字段类型： 1 ：表示 下拉框，2：表示输入框，3：表示单选框，4：表示多选框';
comment on column niutal_XTGL_PLXGXXB.ZDLY  is '字段来源：数据库 ：格式如 database:查询SQL 例如 database:select jg_id as key,jg_mc as value from niutal_xtgl_jgdmb  xml数据       ：格式如 basedata:(baseData.xml)文件中的id 例如 basedata:isValid    固定值        ：格式如 fixed:固定值1,固定值2,...(多个用,隔开) 例如  fixed:aaa,bbb,ccc   空            ：';

-- Create table
create table niutal_XTGL_SJFWDXB
(
  SJFWDX_ID VARCHAR2(32) not null,
  BM        VARCHAR2(200),
  ZDDM      VARCHAR2(50),
  ZDMC      VARCHAR2(50),
  ZWMC      VARCHAR2(20),
  XSSX      VARCHAR2(2),
  SFQY      CHAR(1) default 1,
  ZDCX      CHAR(1),
  KZDX      VARCHAR2(6),
  primary key (SJFWDX_ID)
);
-- Add comments to the table 
comment on table niutal_XTGL_SJFWDXB  is '数据范围对象表';
-- Add comments to the columns 
comment on column niutal_XTGL_SJFWDXB.SJFWDX_ID  is '数据范围对象ID';
comment on column niutal_XTGL_SJFWDXB.BM  is '表名';
comment on column niutal_XTGL_SJFWDXB.ZDDM  is '字段代码';
comment on column niutal_XTGL_SJFWDXB.ZDMC  is '字段名称';
comment on column niutal_XTGL_SJFWDXB.ZWMC  is '中文名称';
comment on column niutal_XTGL_SJFWDXB.XSSX  is '显示顺序';
comment on column niutal_XTGL_SJFWDXB.SFQY  is '是否启用(1是0否)';
comment on column niutal_XTGL_SJFWDXB.ZDCX  is '是否自动查询(1是0否)';
comment on column niutal_XTGL_SJFWDXB.KZDX  is '控制对象'; 


-- Create table
create table niutal_XTGL_SJFWZB
(
  SJFWZ_ID VARCHAR2(32) not null,
  SJFWZMC  VARCHAR2(1000),
  SJFWZTJ  VARCHAR2(500),
  KZDX     VARCHAR2(6),
  primary key (SJFWZ_ID)
);
-- Add comments to the table 
comment on table niutal_XTGL_SJFWZB  is '数据范围组表';
-- Add comments to the columns 
comment on column niutal_XTGL_SJFWZB.SJFWZ_ID  is '数据范围组ID';
comment on column niutal_XTGL_SJFWZB.SJFWZMC  is '数据范围名称';
comment on column niutal_XTGL_SJFWZB.SJFWZTJ  is '数据范围条件(特殊含义:bmdm_id=-1代表超级管理员,bmdm_id=-2代表全校,bmdm_id=-3代表所有学院数据访问权限)';
comment on column niutal_XTGL_SJFWZB.KZDX  is '控制对象'; 


-- Create table
create table niutal_XTGL_SPJDJSPZB
(
  SPJDBH VARCHAR2(32) not null,
  JSDM   VARCHAR2(2000) not null,
  JGH_ID VARCHAR2(2000) not null,
  primary key (SPJDBH, JSDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_SPJDJSPZB  is '审批阶段角色配置表';
-- Add comments to the columns 
comment on column niutal_XTGL_SPJDJSPZB.SPJDBH  is '审批阶段编号';
comment on column niutal_XTGL_SPJDJSPZB.JSDM  is '角色代码';
comment on column niutal_XTGL_SPJDJSPZB.JGH_ID  is '教工号ID'; 


-- Create table
create table niutal_XTGL_SPJDPZB
(
  SPJDBH VARCHAR2(32) default sys_guid() not null,
  SPBH   VARCHAR2(32) not null,
  SPJDMC VARCHAR2(30) not null,
  SX     VARCHAR2(2),
  SFQSH  VARCHAR2(2),
  SFZZSH VARCHAR2(2),
  SFQZSH VARCHAR2(2),
  SFRSKZ VARCHAR2(2),
  SFJDKZ VARCHAR2(2),
  primary key (SPJDBH)
);
-- Add comments to the table 
comment on table niutal_XTGL_SPJDPZB  is '审批阶段配置表';
-- Add comments to the columns 
comment on column niutal_XTGL_SPJDPZB.SPJDBH  is '审批阶段编号';
comment on column niutal_XTGL_SPJDPZB.SPBH  is '审批编号';
comment on column niutal_XTGL_SPJDPZB.SPJDMC  is '审批阶段名称';
comment on column niutal_XTGL_SPJDPZB.SX  is '顺序';
comment on column niutal_XTGL_SPJDPZB.SFQSH  is '是否全审核';
comment on column niutal_XTGL_SPJDPZB.SFZZSH  is '是否最终审核';
comment on column niutal_XTGL_SPJDPZB.SFQZSH  is '是否强制审核';
comment on column niutal_XTGL_SPJDPZB.SFRSKZ  is '是否人数控制';
comment on column niutal_XTGL_SPJDPZB.SFJDKZ  is '是否兼得控制'; 

-- Create table
create table niutal_XTGL_SPPZB
(
  SPBH VARCHAR2(32) default sys_guid() not null,
  SPMC VARCHAR2(100) not null,
  SSMK VARCHAR2(30),
  SHJB VARCHAR2(2),
  SHMS VARCHAR2(2000),
  primary key (SPBH)
);
-- Add comments to the table 
comment on table niutal_XTGL_SPPZB  is '审批配置表';
-- Add comments to the columns 
comment on column niutal_XTGL_SPPZB.SPBH  is '审批流ID';
comment on column niutal_XTGL_SPPZB.SPMC  is '审批流名称';
comment on column niutal_XTGL_SPPZB.SSMK  is '所属模块';
comment on column niutal_XTGL_SPPZB.SHJB  is '审核级别';
comment on column niutal_XTGL_SPPZB.SHMS  is '审核描述'; 

-- Create table
create table niutal_XTGL_TZMXFXSDXB
(
  MXDXB_ID  VARCHAR2(32) default sys_guid() not null,
  XNM       VARCHAR2(4),
  XQM       VARCHAR2(2),
  XZLB      VARCHAR2(4),
  JSDM      VARCHAR2(32),
  XQH_ID    VARCHAR2(32),
  SZBMJG_ID VARCHAR2(32),
  KKBMJG_ID VARCHAR2(32),
  XBM       VARCHAR2(4),
  JGH_ID    VARCHAR2(32),
  MXDXBH    VARCHAR2(32),
  primary key (MXDXB_ID)
);
-- Add comments to the table 
comment on table niutal_XTGL_TZMXFXSDXB  is '通知面向非学生对象表';
-- Add comments to the columns 
comment on column niutal_XTGL_TZMXFXSDXB.MXDXB_ID  is '面向对象ID';
comment on column niutal_XTGL_TZMXFXSDXB.XNM  is '学年码';
comment on column niutal_XTGL_TZMXFXSDXB.XQM  is '学期码';
comment on column niutal_XTGL_TZMXFXSDXB.XZLB  is '限制类别,mx表示面向对象,xz表示限制对象';
comment on column niutal_XTGL_TZMXFXSDXB.JSDM  is '角色代码';
comment on column niutal_XTGL_TZMXFXSDXB.XQH_ID  is '校区号ID';
comment on column niutal_XTGL_TZMXFXSDXB.SZBMJG_ID  is '教师所在部门机构ID';
comment on column niutal_XTGL_TZMXFXSDXB.KKBMJG_ID  is '开课部门机构ID';
comment on column niutal_XTGL_TZMXFXSDXB.XBM  is '性别码';
comment on column niutal_XTGL_TZMXFXSDXB.JGH_ID  is '教工号ID';
comment on column niutal_XTGL_TZMXFXSDXB.MXDXBH  is '面向对象编号'; 

-- Create table
create table niutal_XTGL_TZMXXSDXB
(
  MXDXB_ID VARCHAR2(32) default sys_guid() not null,
  JXB_ID   VARCHAR2(32),
  XZLB     VARCHAR2(4),
  XQH_ID   VARCHAR2(32),
  JG_ID    VARCHAR2(32),
  ZYH_ID   VARCHAR2(32),
  ZYFX_ID  VARCHAR2(32),
  NJDM_ID  VARCHAR2(32),
  BH_ID    VARCHAR2(32),
  XBM      VARCHAR2(4),
  XSLBM    VARCHAR2(4),
  CCDM     VARCHAR2(32),
  XH_ID    VARCHAR2(32),
  MXDXBH   VARCHAR2(32),
  primary key (MXDXB_ID)
);
-- Add comments to the table 
comment on table niutal_XTGL_TZMXXSDXB  is '通知面向学生对象表';
-- Add comments to the columns 
comment on column niutal_XTGL_TZMXXSDXB.MXDXB_ID  is '面向对象ID';
comment on column niutal_XTGL_TZMXXSDXB.JXB_ID  is '教学班ID';
comment on column niutal_XTGL_TZMXXSDXB.XZLB  is '限制类别,mx表示面向对象,xz表示限制对象';
comment on column niutal_XTGL_TZMXXSDXB.XQH_ID  is '校区号ID';
comment on column niutal_XTGL_TZMXXSDXB.JG_ID  is '机构ID';
comment on column niutal_XTGL_TZMXXSDXB.ZYH_ID  is '专业号ID';
comment on column niutal_XTGL_TZMXXSDXB.ZYFX_ID  is '专业方向ID';
comment on column niutal_XTGL_TZMXXSDXB.NJDM_ID  is '年级代码';
comment on column niutal_XTGL_TZMXXSDXB.BH_ID  is '班号';
comment on column niutal_XTGL_TZMXXSDXB.XBM  is '性别码';
comment on column niutal_XTGL_TZMXXSDXB.XSLBM  is '学生类别码';
comment on column niutal_XTGL_TZMXXSDXB.CCDM  is '层次代码';
comment on column niutal_XTGL_TZMXXSDXB.XH_ID  is '学号ID';
comment on column niutal_XTGL_TZMXXSDXB.MXDXBH  is '面向对象编号'; 


-- Create table
create table niutal_XTGL_WDYYGNMKCZB
(
  YHDM   VARCHAR2(40) not null,
  GNMKDM VARCHAR2(10) not null,
  XSSX   VARCHAR2(6),
  FJGNDM VARCHAR2(10) not null,
  primary key (YHDM, GNMKDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_WDYYGNMKCZB  is '我的应用功能模块操作表';
-- Add comments to the columns 
comment on column niutal_XTGL_WDYYGNMKCZB.YHDM  is '用户代码（职工号或学号）';
comment on column niutal_XTGL_WDYYGNMKCZB.GNMKDM  is '功能模块代码';
comment on column niutal_XTGL_WDYYGNMKCZB.XSSX  is '显示顺序';
comment on column niutal_XTGL_WDYYGNMKCZB.FJGNDM  is '父一级模块代码'; 
  
-- Create table
create table niutal_XTGL_XQDMB
(
  XQH        VARCHAR2(20),
  XQMC       VARCHAR2(50),
  XQDZ       VARCHAR2(100),
  ZJR        VARCHAR2(50),
  ZJSJ       VARCHAR2(20),
  XGR        VARCHAR2(50),
  XGSJ       VARCHAR2(20),
  XQYZBM     VARCHAR2(6),
  XQFZRZGH   VARCHAR2(20),
  XQH_ID     VARCHAR2(32) default sys_guid() not null,
  KKHBXQH_ID VARCHAR2(1000),
  KKXKXQ_ID  VARCHAR2(1000),
  primary key (XQH_ID)
);
-- Add comments to the table 
comment on table niutal_XTGL_XQDMB  is '校区代码表';
-- Add comments to the columns 
comment on column niutal_XTGL_XQDMB.XQH  is '校区号';
comment on column niutal_XTGL_XQDMB.XQMC  is '校区名称';
comment on column niutal_XTGL_XQDMB.XQDZ  is '校区地址';
comment on column niutal_XTGL_XQDMB.ZJR  is '增加人';
comment on column niutal_XTGL_XQDMB.ZJSJ  is '增加时间';
comment on column niutal_XTGL_XQDMB.XGR  is '修改人';
comment on column niutal_XTGL_XQDMB.XGSJ  is '修改时间';
comment on column niutal_XTGL_XQDMB.XQYZBM  is '校区邮政编码';
comment on column niutal_XTGL_XQDMB.XQFZRZGH  is '校区负责人职工号';
comment on column niutal_XTGL_XQDMB.XQH_ID  is '校区号ID';
comment on column niutal_XTGL_XQDMB.KKHBXQH_ID  is '可跨校区合班';
comment on column niutal_XTGL_XQDMB.KKXKXQ_ID  is '可跨校区选课';

-- Create table
create table niutal_XTGL_XTCZMSB
(
  XTCZMS_ID VARCHAR2(32) default sys_guid() not null,
  GNMKDM    VARCHAR2(10) not null,
  CZDM      VARCHAR2(30) default '00' not null,
  CZMS      CLOB,
  BZ        VARCHAR2(200) not null,
  SFSY      VARCHAR2(2) default 1
);
-- Add comments to the table 
comment on table niutal_XTGL_XTCZMSB  is '系统操作表描述表';
-- Add comments to the columns 
comment on column niutal_XTGL_XTCZMSB.XTCZMS_ID  is '系统操作表描述ID';
comment on column niutal_XTGL_XTCZMSB.GNMKDM  is '功能模块代码';
comment on column niutal_XTGL_XTCZMSB.CZDM  is '操作代码';
comment on column niutal_XTGL_XTCZMSB.CZMS  is '操作描述信息';
comment on column niutal_XTGL_XTCZMSB.BZ  is '备注信息';
comment on column niutal_XTGL_XTCZMSB.SFSY  is '是否使用（1：使用；2：停用）';


-- Create table
create table niutal_XTGL_XTSZB
(
  XTSZ_ID  VARCHAR2(32) default sys_guid() not null,
  ZDM      VARCHAR2(30) not null,
  ZDZ      VARCHAR2(50),
  SSMK     VARCHAR2(300),
  ZS       VARCHAR2(300),
  BZ       VARCHAR2(240),
  SSGNMKDM VARCHAR2(30),
  ZDZYQ    VARCHAR2(255),
  ZDLX     NUMBER default 2 not null,
  ZDLY     VARCHAR2(1000),
   primary key (ZDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_XTSZB  is '系统设置表';
-- Add comments to the columns 
comment on column niutal_XTGL_XTSZB.XTSZ_ID  is '系统设置ID';
comment on column niutal_XTGL_XTSZB.ZDM  is '字段名';
comment on column niutal_XTGL_XTSZB.ZDZ  is '字段值';
comment on column niutal_XTGL_XTSZB.SSMK  is '所属模块';
comment on column niutal_XTGL_XTSZB.ZS  is '注释';
comment on column niutal_XTGL_XTSZB.BZ  is '备注';
comment on column niutal_XTGL_XTSZB.SSGNMKDM  is '所属功能模块代码';
comment on column niutal_XTGL_XTSZB.ZDZYQ  is '字段值要求;  例如 required:true;stringMaxLength:30;range:[0,100] (多个用;隔开)';
comment on column niutal_XTGL_XTSZB.ZDLX  is '字段类型： 1 ：表示 下拉框，2：表示输入框，3：表示单选框，4：表示多选框';
comment on column niutal_XTGL_XTSZB.ZDLY  is '字段来源：
     数据库        ：格式如 database:查询SQL 例如 database:select jg_id as key,jg_mc as value from niutal_xtgl_jgdmb
     xml数据       ：格式如 basedata:(baseData.xml)文件中的id 例如 basedata:isValid
     固定值        ：格式如 fixed:固定值1,固定值2,...(多个用,隔开) 例如  fixed:aaa,bbb,ccc
     空            ：'; 
 


-- Create table
create table niutal_XTGL_XWCKJLB
(
  XWBH VARCHAR2(32) not null,
  YDR  VARCHAR2(40) not null,
  YDSJ VARCHAR2(20) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') not null
);
-- Add comments to the table 
comment on table niutal_XTGL_XWCKJLB  is '新闻查看记录表';
-- Add comments to the columns 
comment on column niutal_XTGL_XWCKJLB.XWBH  is '新闻编号';
comment on column niutal_XTGL_XWCKJLB.YDR  is '阅读人(niutal_xtgl_yhb.zgh)';
comment on column niutal_XTGL_XWCKJLB.YDSJ  is '阅读时间';

-- Create table
create table niutal_XTGL_XWFBB
(
  XWBH   VARCHAR2(32) not null,
  XWBT   VARCHAR2(200) not null,
  FBSJ   VARCHAR2(30) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
  FBR    VARCHAR2(40) not null,
  FBNR   CLOB,
  SFFB   VARCHAR2(2),
  SFZD   VARCHAR2(2),
  TZZYJB VARCHAR2(1),
  TZTXTJ VARCHAR2(1),
  MXDXBH VARCHAR2(32),
  MXDXLB VARCHAR2(1),
  primary key (XWBH)
);
-- Add comments to the table 
comment on table niutal_XTGL_XWFBB  is '新闻发布表';
-- Add comments to the columns 
comment on column niutal_XTGL_XWFBB.XWBH  is '新闻编号';
comment on column niutal_XTGL_XWFBB.XWBT  is '新闻标题';
comment on column niutal_XTGL_XWFBB.FBSJ  is '发布时间';
comment on column niutal_XTGL_XWFBB.FBR  is '发布人';
comment on column niutal_XTGL_XWFBB.FBNR  is '发布内容';
comment on column niutal_XTGL_XWFBB.SFFB  is '是否发布';
comment on column niutal_XTGL_XWFBB.SFZD  is '是否置顶';
comment on column niutal_XTGL_XWFBB.TZZYJB  is '通知重要级别';
comment on column niutal_XTGL_XWFBB.TZTXTJ  is '通知提醒途径';
comment on column niutal_XTGL_XWFBB.MXDXBH  is '面向对象编号';
comment on column niutal_XTGL_XWFBB.MXDXLB  is '面向对象类别1学生;2教师;3岗位'; 

-- Create table
create table niutal_XTGL_XXXXSZB
(
  XXDM     VARCHAR2(10) not null,
  XXMC     VARCHAR2(50),
  XXYWMC   VARCHAR2(180),
  XXDZ     VARCHAR2(180),
  YZBM     VARCHAR2(6),
  XZQHM    VARCHAR2(6),
  JXNY     VARCHAR2(6),
  XQR      VARCHAR2(60),
  XXBXLXM  VARCHAR2(3),
  XXJBZM   VARCHAR2(3),
  XXZGBMM  VARCHAR2(3),
  XXZGBMMC VARCHAR2(60),
  LSYG     VARCHAR2(500),
  ZCM      VARCHAR2(64),
  JWCZYWZ  VARCHAR2(100),
  primary key (XXDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_XXXXSZB  is '学校信息设置表';
-- Add comments to the columns 
comment on column niutal_XTGL_XXXXSZB.XXDM  is '学校代码';
comment on column niutal_XTGL_XXXXSZB.XXMC  is '学校名称';
comment on column niutal_XTGL_XXXXSZB.XXYWMC  is '学校英文名称';
comment on column niutal_XTGL_XXXXSZB.XXDZ  is '学校地址';
comment on column niutal_XTGL_XXXXSZB.YZBM  is '邮政编码';
comment on column niutal_XTGL_XXXXSZB.XZQHM  is '行政区划码';
comment on column niutal_XTGL_XXXXSZB.JXNY  is '建校年月';
comment on column niutal_XTGL_XXXXSZB.XQR  is '校庆日';
comment on column niutal_XTGL_XXXXSZB.XXBXLXM  is '学校办学类型码';
comment on column niutal_XTGL_XXXXSZB.XXJBZM  is '学校举办者码';
comment on column niutal_XTGL_XXXXSZB.XXZGBMM  is '学校主管部门码';
comment on column niutal_XTGL_XXXXSZB.XXZGBMMC  is '学校主管部门名称';
comment on column niutal_XTGL_XXXXSZB.LSYG  is '历史沿革';
comment on column niutal_XTGL_XXXXSZB.ZCM  is '注册码';
comment on column niutal_XTGL_XXXXSZB.JWCZYWZ  is '教务处主页网址';

-- Create table
create table niutal_XTGL_XXZYDMB
(
  PCDM       VARCHAR2(4) not null,
  ZYDM       VARCHAR2(10) not null,
  ZYMC       VARCHAR2(100),
  XKDM       VARCHAR2(10),
  XKMC       VARCHAR2(100),
  MLDM       VARCHAR2(10),
  MLMC       VARCHAR2(100),
  XXZYDMB_ID VARCHAR2(32),
  primary key (PCDM, ZYDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_XXZYDMB
  is '学信专业代码表';
-- Add comments to the columns 
comment on column niutal_XTGL_XXZYDMB.PCDM  is '批次代码';
comment on column niutal_XTGL_XXZYDMB.ZYDM  is '专业代码';
comment on column niutal_XTGL_XXZYDMB.ZYMC  is '专业名称';
comment on column niutal_XTGL_XXZYDMB.XKDM  is '学科代码';
comment on column niutal_XTGL_XXZYDMB.XKMC  is '学科名称';
comment on column niutal_XTGL_XXZYDMB.MLDM  is '门类代码';
comment on column niutal_XTGL_XXZYDMB.MLMC  is '门类名称'; 

-- Create table
create table niutal_XTGL_YHB
(
  XM     VARCHAR2(50) not null,
  DZYX   VARCHAR2(40),
  SFQY   VARCHAR2(1),
  SJLY   VARCHAR2(40),
  JGDM   VARCHAR2(40),
  YHLX   VARCHAR2(20) default 'teacher ',
  YHM    VARCHAR2(20) not null,
  KL     VARCHAR2(70),
  SJHM   VARCHAR2(40),
  YHMMDJ VARCHAR2(2) default 1,
  primary key (YHM)
);
-- Add comments to the table 
comment on table niutal_XTGL_YHB  is '用户表';
-- Add comments to the columns 
comment on column niutal_XTGL_YHB.XM  is '姓名';
comment on column niutal_XTGL_YHB.DZYX  is '电子邮箱';
comment on column niutal_XTGL_YHB.SFQY  is '是否启用(1为启用,0为不启用)';
comment on column niutal_XTGL_YHB.SJLY  is '数据来源表名';
comment on column niutal_XTGL_YHB.JGDM  is '机构代码';
comment on column niutal_XTGL_YHB.YHLX  is '老师：teacher，学生：student';
comment on column niutal_XTGL_YHB.YHM  is '用户名';
comment on column niutal_XTGL_YHB.KL  is '口令';
comment on column niutal_XTGL_YHB.SJHM  is '手机号码';
comment on column niutal_XTGL_YHB.YHMMDJ  is '用户密码等级状态【1：弱，2：中，3：高，4：超高】'; 

-- Create table
create table niutal_XTGL_YHJSB
(
  JSDM VARCHAR2(32) not null,
  YHM  VARCHAR2(20) not null,
  primary key (YHM, JSDM)
);
-- Add comments to the table 
comment on table niutal_XTGL_YHJSB  is '用户角色表';
-- Add comments to the columns 
comment on column niutal_XTGL_YHJSB.JSDM  is '角色代码';
comment on column niutal_XTGL_YHJSB.YHM  is '用户名'; 

-- Create table
create table niutal_XTGL_YHSJFWB
(
  YHSJFWB_ID VARCHAR2(32) default SYS_GUID() not null,
  JS_ID      VARCHAR2(32),
  SJFWZ_ID   VARCHAR2(32),
  SFQY       CHAR(1),
  YHM        VARCHAR2(20),
  primary key (YHSJFWB_ID)
);
-- Add comments to the table 
comment on table niutal_XTGL_YHSJFWB  is '用户数据范围表';
-- Add comments to the columns 
comment on column niutal_XTGL_YHSJFWB.YHSJFWB_ID  is '用户数据范围ID';
comment on column niutal_XTGL_YHSJFWB.JS_ID  is '角色ID';
comment on column niutal_XTGL_YHSJFWB.SJFWZ_ID  is '数据范围组ID';
comment on column niutal_XTGL_YHSJFWB.SFQY  is '是否启用(0否，1是)';
comment on column niutal_XTGL_YHSJFWB.YHM  is '用户名'; 

-- Create table
create table niutal_XTGL_YJFSZHXXB
(
  YJFSZHXX_ID VARCHAR2(32) default sys_guid() not null,
  YJZH        VARCHAR2(100) not null,
  YJZHMM      VARCHAR2(100) not null,
  FSLX        VARCHAR2(8)
);
-- Add comments to the table 
comment on table niutal_XTGL_YJFSZHXXB  is '邮件发送账号信息表';
-- Add comments to the columns 
comment on column niutal_XTGL_YJFSZHXXB.YJFSZHXX_ID  is '邮件发送账号信息ID';
comment on column niutal_XTGL_YJFSZHXXB.YJZH  is '登陆SMTP邮件发送服务器的用户名';
comment on column niutal_XTGL_YJFSZHXXB.YJZHMM  is '登陆SMTP邮件发送服务器的用户密码 ';
comment on column niutal_XTGL_YJFSZHXXB.FSLX  is '发送类型cjmmfs成绩密码发送';

-- Create table
create table niutal_XTGL_YJFWSZB
(
  YJFWSZ_ID VARCHAR2(32) default sys_guid() not null,
  YJXY      VARCHAR2(32) default 'stmp',
  YJGYM     VARCHAR2(100),
  YJFWDZ    VARCHAR2(100),
  YJFWDK    VARCHAR2(20),  
  SFYZ      VARCHAR2(1) default '1',
  SYSSL     VARCHAR2(1) default '0',
  CSSJ      VARCHAR2(10) default '25000',
  BZ        VARCHAR2(100)
);
-- Add comments to the table 
comment on table niutal_XTGL_YJFWSZB is '邮件服务设置表';
-- Add comments to the columns 
comment on column niutal_XTGL_YJFWSZB.YJFWSZ_ID is '邮件服务设置ID';
comment on column niutal_XTGL_YJFWSZB.YJXY is '邮件协议 smtp、nntp、pop3、imap';
comment on column niutal_XTGL_YJFWSZB.YJGYM is '邮件服务根域名';
comment on column niutal_XTGL_YJFWSZB.YJFWDZ is '邮件服务地址';
comment on column niutal_XTGL_YJFWSZB.YJFWDK is '邮件服务端口'; 
comment on column niutal_XTGL_YJFWSZB.SFYZ is '是否验证(1:验证;0:不验证)';
comment on column niutal_XTGL_YJFWSZB.SYSSL is '使用SSL加密通信(1:使用;0:不使用)'; 
comment on column niutal_XTGL_YJFWSZB.CSSJ is '发送超时时间，默认25000';
comment on column niutal_XTGL_YJFWSZB.BZ is '邮箱服务备注信息';

-- Create table
create table niutal_XTGL_YWSJPXXXB
(
  GNMKDM  VARCHAR2(10) not null,
  YWSJ_ID VARCHAR2(32) not null,
  YH_ID   VARCHAR2(32) not null,
  YXJ     VARCHAR2(30) default '1' not null,
  ZDMC    VARCHAR2(30) not null,
  ZDMS    VARCHAR2(100) not null,
  PXFS    VARCHAR2(30) default 'asc' not null
);
-- Add comments to the table 
comment on table niutal_XTGL_YWSJPXXXB  is '业务数据排序信息表';
-- Add comments to the columns 
comment on column niutal_XTGL_YWSJPXXXB.GNMKDM  is '功能模块代码';
comment on column niutal_XTGL_YWSJPXXXB.YWSJ_ID  is '业务数据ID';
comment on column niutal_XTGL_YWSJPXXXB.YH_ID  is '用户ID:以便排序排序信息与用户挂钩';
comment on column niutal_XTGL_YWSJPXXXB.YXJ  is '优先级：排序的先后顺序';
comment on column niutal_XTGL_YWSJPXXXB.ZDMC  is '排序字段;如：xh';
comment on column niutal_XTGL_YWSJPXXXB.ZDMS  is '排序字段描述;如：学号';
comment on column niutal_XTGL_YWSJPXXXB.PXFS  is '排序方式：asc 或者 desc';

-- Create table
create table niutal_XTGL_ZDPZB
(
  ZDPZ_ID      VARCHAR2(32) default sys_guid(),
  ZD_FZDM      VARCHAR2(50) not null,
  ZD_LABEL     VARCHAR2(100) not null,
  ZD_NAME      VARCHAR2(50) not null,
  ZD_INDEX     VARCHAR2(50) not null,
  ZD_ALIGN     VARCHAR2(50) default 'center',
  ZD_HIDDEN    CHAR(2) default 0,
  ZD_RESIZABLE CHAR(2) default 1,
  ZD_SORTABLE  CHAR(2) default 1,
  ZD_NUMBER    VARCHAR2(2) default 1,
  YHM          VARCHAR2(20),
  ZD_KEY       CHAR(2) default 1
);
-- Add comments to the table 
comment on table niutal_XTGL_ZDPZB  is '数据列表字段信息配置表';
-- Add comments to the columns 
comment on column niutal_XTGL_ZDPZB.ZDPZ_ID  is '数据列表字段配置信息表ID';
comment on column niutal_XTGL_ZDPZB.ZD_FZDM  is '字段配置信息分组代码';
comment on column niutal_XTGL_ZDPZB.ZD_LABEL  is '当jqGrid的colNames选项数组为空时，为各列指定题头。如果colNames和此项都为空时，则name选项值会成为题头。';
comment on column niutal_XTGL_ZDPZB.ZD_NAME  is '为Grid中的每个列设置唯一的名称，这是一个必需选项，其中保留字包括subgrid、cb、rn。';
comment on column niutal_XTGL_ZDPZB.ZD_INDEX  is '设置排序时所使用的索引名称，这个index名称会作为sidx参数（prmNames中设置的）传递到Server。';
comment on column niutal_XTGL_ZDPZB.ZD_ALIGN  is '定义单元格对齐方式；可选值：left, center, right.  left';
comment on column niutal_XTGL_ZDPZB.ZD_HIDDEN  is '设置此列初始化时是否为隐藏状态，1:true,0:false;默认为 0';
comment on column niutal_XTGL_ZDPZB.ZD_RESIZABLE  is '设置列是否可以变更尺寸，1:true,0:false;默认为 1';
comment on column niutal_XTGL_ZDPZB.ZD_SORTABLE  is '设置该列是否可以排序，1:true,0:false;默认为 1';
comment on column niutal_XTGL_ZDPZB.ZD_NUMBER  is '列在字段中显示的顺序';
comment on column niutal_XTGL_ZDPZB.YHM  is '用户名';
comment on column niutal_XTGL_ZDPZB.ZD_KEY  is '设置列是否主键列，1:true,0:false;默认为 1';

-- Create table
create table niutal_XTGL_ZYDMB
(
  ZYH_ID       VARCHAR2(32) default sys_guid() not null,
  ZYH          VARCHAR2(10),
  ZYMC         VARCHAR2(60),
  ZYJC         VARCHAR2(20),
  ZYYWMC       VARCHAR2(100),
  JG_ID        VARCHAR2(32),
  CYDM_ID_XKML VARCHAR2(32),
  XKMLM        VARCHAR2(8),
  XZ           VARCHAR2(2),
  CCDM         VARCHAR2(32),
  DLBS         VARCHAR2(4) default 'zy',
  SFTY         CHAR(1),
  BZ           VARCHAR2(100),
  ZJR          VARCHAR2(50),
  ZJSJ         VARCHAR2(20),
  XGR          VARCHAR2(50),
  XGSJ         VARCHAR2(20),
  BZKZYM       VARCHAR2(28),
  JLNY         VARCHAR2(10),
  YJSZYM       VARCHAR2(10),
  YXJ          CHAR(1),
  ZYJJ         CLOB,
  primary key (ZYH_ID)
);
-- Add comments to the table 
comment on table niutal_XTGL_ZYDMB is '专业代码表';
-- Add comments to the columns 
comment on column niutal_XTGL_ZYDMB.ZYH_ID is '专业号ID';
comment on column niutal_XTGL_ZYDMB.ZYH is '专业号';
comment on column niutal_XTGL_ZYDMB.ZYMC is '专业名称';
comment on column niutal_XTGL_ZYDMB.ZYJC is '专业简称';
comment on column niutal_XTGL_ZYDMB.ZYYWMC is '专业英文名称';
comment on column niutal_XTGL_ZYDMB.JG_ID is '机构ID';
comment on column niutal_XTGL_ZYDMB.CYDM_ID_XKML is '学科门类(关联常用代码表ID)';
comment on column niutal_XTGL_ZYDMB.XKMLM is '学科门类码';
comment on column niutal_XTGL_ZYDMB.XZ is '学制';
comment on column niutal_XTGL_ZYDMB.CCDM is '层次（关联常用代码表ID）';
comment on column niutal_XTGL_ZYDMB.DLBS is '大类标识';
comment on column niutal_XTGL_ZYDMB.SFTY is '是否停用（1是 0否）';
comment on column niutal_XTGL_ZYDMB.BZ is '备注';
comment on column niutal_XTGL_ZYDMB.ZJR is '增加人';
comment on column niutal_XTGL_ZYDMB.ZJSJ  is '增加时间';
comment on column niutal_XTGL_ZYDMB.XGR is '修改人';
comment on column niutal_XTGL_ZYDMB.XGSJ is '修改时间';
comment on column niutal_XTGL_ZYDMB.BZKZYM  is '国家本专科专业码';
comment on column niutal_XTGL_ZYDMB.JLNY is '建立年月';
comment on column niutal_XTGL_ZYDMB.YJSZYM is '研究生专业码';
comment on column niutal_XTGL_ZYDMB.YXJ  is '排课优先级1:特别 2:优先 3:普通  4:最后';
comment on column niutal_XTGL_ZYDMB.ZYJJ is '专业简介'; 

 -- Create table
create table niutal_XTGL_ZYFXDMB
(
  ZYFX_ID  VARCHAR2(32) default sys_guid() not null,
  ZYH_ID   VARCHAR2(32),
  ZYFXDM   VARCHAR2(12),
  ZYFXMC   VARCHAR2(100),
  ZYFXYWM  VARCHAR2(100),
  ZYFXYWSX VARCHAR2(100),
  FZR      VARCHAR2(62),
  SXXNXQ   VARCHAR2(15),
  TYXNXQ   VARCHAR2(15),
  TYSJ     VARCHAR2(30),
  SXSJ     VARCHAR2(30),
  CZSJ     VARCHAR2(30),
  SFQY     VARCHAR2(2) default '1',
  NJDM_ID  VARCHAR2(32),
  ZYFXJJ   CLOB,
  primary key (ZYFX_ID)
);
-- Add comments to the table 
comment on table niutal_XTGL_ZYFXDMB is '专业方向代码表';
-- Add comments to the columns 
comment on column niutal_XTGL_ZYFXDMB.ZYFX_ID is '专业方向代码表ID';
comment on column niutal_XTGL_ZYFXDMB.ZYH_ID is '专业号ID';
comment on column niutal_XTGL_ZYFXDMB.ZYFXDM is '专业方向代码';
comment on column niutal_XTGL_ZYFXDMB.ZYFXMC is '专业方向名称';
comment on column niutal_XTGL_ZYFXDMB.ZYFXYWM is '专业方向英文名';
comment on column niutal_XTGL_ZYFXDMB.ZYFXYWSX is '专业方向英文缩写';
comment on column niutal_XTGL_ZYFXDMB.FZR is '负责人';
comment on column niutal_XTGL_ZYFXDMB.SXXNXQ  is '生效学年学期';
comment on column niutal_XTGL_ZYFXDMB.TYXNXQ is '停用学年学期';
comment on column niutal_XTGL_ZYFXDMB.TYSJ is '停用时间';
comment on column niutal_XTGL_ZYFXDMB.SXSJ is '生效时间';
comment on column niutal_XTGL_ZYFXDMB.CZSJ is '操作时间';
comment on column niutal_XTGL_ZYFXDMB.SFQY is '1表示启用，0表示停用';
comment on column niutal_XTGL_ZYFXDMB.NJDM_ID is '年级代码ID';
comment on column niutal_XTGL_ZYFXDMB.ZYFXJJ is '专业方向简介'; 
-- Create/Recreate indexes 
create index RELATIONSHIP_75_FK on niutal_XTGL_ZYFXDMB (ZYH_ID) ;

-- Create table
create table ZFXG_DR_DRBMPZB
(
  DRYZBH   VARCHAR2(40) not null,
  DRMKDM   VARCHAR2(50) not null,
  DRMKMC   VARCHAR2(100) not null,
  DRMKYZSM VARCHAR2(500),
  DRBM     VARCHAR2(200) not null,
  DRBZWMC  VARCHAR2(200) not null,
  ZDM      VARCHAR2(50) not null,
  ZDMC     VARCHAR2(100) not null,
  ZDLX     VARCHAR2(20) not null,
  YZCS     VARCHAR2(200) default '?' not null,
  XSSX     VARCHAR2(10) not null,
  DRMBZDMC VARCHAR2(100) not null,
  SFHBYZ   VARCHAR2(1) default '0',
  primary key (DRMKDM, DRBM, ZDM, DRYZBH, DRMBZDMC)
);
-- Add comments to the table 
comment on table ZFXG_DR_DRBMPZB  is '导入表名配置表';
-- Add comments to the columns 
comment on column ZFXG_DR_DRBMPZB.DRYZBH  is '导入验证编号';
comment on column ZFXG_DR_DRBMPZB.DRMKDM  is '导入模块代码';
comment on column ZFXG_DR_DRBMPZB.DRMKMC  is '导入模块名称';
comment on column ZFXG_DR_DRBMPZB.DRMKYZSM  is '导入模块验证说明';
comment on column ZFXG_DR_DRBMPZB.DRBM  is '导入表名';
comment on column ZFXG_DR_DRBMPZB.DRBZWMC  is '导入表中文名称';
comment on column ZFXG_DR_DRBMPZB.ZDM  is '字段名';
comment on column ZFXG_DR_DRBMPZB.ZDMC  is '字段名称';
comment on column ZFXG_DR_DRBMPZB.ZDLX  is '字段类型';
comment on column ZFXG_DR_DRBMPZB.YZCS  is '????[????'';''??]';
comment on column ZFXG_DR_DRBMPZB.XSSX  is '显示顺序';
comment on column ZFXG_DR_DRBMPZB.DRMBZDMC  is '导入模板字段名';
comment on column ZFXG_DR_DRBMPZB.SFHBYZ  is '是否合并验证,[1:是,0:否]'; 
alter table ZFXG_DR_DRBMPZB
  add constraint ZFXG_DR_DRBMPZB_DDDZX unique (DRMKDM, DRBM, XSSX);

-- Create table
create table ZFXG_DR_DRYZB
(
  DRYZBH VARCHAR2(40) not null,
  YZMC   VARCHAR2(50) not null,
  YZLMC  VARCHAR2(100) not null,
  YZSM   VARCHAR2(200) not null,
  YZLBM  VARCHAR2(100) not null,
  XSSX   VARCHAR2(10) not null,
  primary key (DRYZBH)
);
-- Add comments to the table 
comment on table ZFXG_DR_DRYZB  is '导入验证表';
-- Add comments to the columns 
comment on column ZFXG_DR_DRYZB.DRYZBH  is '导入验证编号';
comment on column ZFXG_DR_DRYZB.YZMC  is '验证名称';
comment on column ZFXG_DR_DRYZB.YZLMC  is '验证类名称';
comment on column ZFXG_DR_DRYZB.YZSM  is '验证说明,用于描述验证行为';
comment on column ZFXG_DR_DRYZB.YZLBM  is '验证类包名';
comment on column ZFXG_DR_DRYZB.XSSX  is '显示顺序'; 

-- Create table
create table ZFXG_ZDYBD_FLSZB
(
  FLSZID   VARCHAR2(36) not null,
  GNDM     VARCHAR2(36),
  FLFLSZID VARCHAR2(36),
  FLMC     VARCHAR2(50),
  FLSM     VARCHAR2(200),
  BDLS     VARCHAR2(2),
  BDMS     VARCHAR2(2),
  SFQY     VARCHAR2(2),
  SFZK     VARCHAR2(2),
  BDSZZ    VARCHAR2(500),
  XSXX     VARCHAR2(2),
  primary key (FLSZID)
);
-- Add comments to the table 
comment on table ZFXG_ZDYBD_FLSZB  is '分类设置表';
-- Add comments to the columns 
comment on column ZFXG_ZDYBD_FLSZB.FLSZID  is '分类设置id ';
comment on column ZFXG_ZDYBD_FLSZB.GNDM  is '功能代码 ';
comment on column ZFXG_ZDYBD_FLSZB.FLFLSZID  is '父类id ';
comment on column ZFXG_ZDYBD_FLSZB.FLMC  is '分类名称 ';
comment on column ZFXG_ZDYBD_FLSZB.FLSM  is '分类说明 ';
comment on column ZFXG_ZDYBD_FLSZB.BDLS  is '表单列数 为表单整体字段列数设置，不包含照片列(由程序自动计算)';
comment on column ZFXG_ZDYBD_FLSZB.BDMS  is '表单模式 1:单条记录模式:2:多条记录模式;3:功能自定义代码';
comment on column ZFXG_ZDYBD_FLSZB.SFQY  is '0停用  1启用  3查看';
comment on column ZFXG_ZDYBD_FLSZB.SFZK  is '是否展开 1:是,0:否';
comment on column ZFXG_ZDYBD_FLSZB.BDSZZ  is '表单设置值';
comment on column ZFXG_ZDYBD_FLSZB.XSXX  is '显示顺序 '; 

-- Create table
create table ZFXG_ZDYBD_GNSZB
(
  GNDM VARCHAR2(36) not null,
  GNMC VARCHAR2(36) not null,
  GNLX VARCHAR2(2) not null,
  YZSZ VARCHAR2(200),
  BHMK VARCHAR2(2),
  primary key (GNDM)
);
-- Add comments to the table 
comment on table ZFXG_ZDYBD_GNSZB  is '功能设置表';
-- Add comments to the columns 
comment on column ZFXG_ZDYBD_GNSZB.GNDM  is '功能代码 ';
comment on column ZFXG_ZDYBD_GNSZB.GNMC  is '功能名称 ';
comment on column ZFXG_ZDYBD_GNSZB.GNLX  is '???? 1:??2:??3:??';
comment on column ZFXG_ZDYBD_GNSZB.YZSZ  is '验证设置 js方法';
comment on column ZFXG_ZDYBD_GNSZB.BHMK  is '包含模块 1:包含模块;0:不包含模块'; 


-- Create table
create table ZFXG_ZDYBD_ZDDYB
(
  ZDDYID   VARCHAR2(36) not null,
  FLSZID   VARCHAR2(36),
  ZD       VARCHAR2(50),
  BDMC     VARCHAR2(50),
  ZDSM     VARCHAR2(200),
  ZDLX     VARCHAR2(2),
  SZLX     VARCHAR2(2),
  YZLX     VARCHAR2(100),
  SZZ      VARCHAR2(500),
  ZBWZ     VARCHAR2(20),
  SZLS     VARCHAR2(20),
  KDBL     VARCHAR2(20),
  MCSQL    VARCHAR2(500),
  FJZDDYID VARCHAR2(36),
  primary key (ZDDYID)
);
-- Add comments to the table 
comment on table ZFXG_ZDYBD_ZDDYB  is '字段定义表';
-- Add comments to the columns 
comment on column ZFXG_ZDYBD_ZDDYB.ZDDYID  is '????id ';
comment on column ZFXG_ZDYBD_ZDDYB.FLSZID  is '分类设置id ';
comment on column ZFXG_ZDYBD_ZDDYB.ZD  is '字段 ';
comment on column ZFXG_ZDYBD_ZDDYB.BDMC  is '表单名称 ';
comment on column ZFXG_ZDYBD_ZDDYB.ZDSM  is '字段说明 ';
comment on column ZFXG_ZDYBD_ZDDYB.ZDLX  is '字段类型
0:仅显示
1:隐藏域
2:下拉框
3:单选框
4:复选框
10:文本域
11:字符文本框
13:日期文本框
20:文件
21:照片
22:省市县选择
98:元素分组
99:功能自定义';
comment on column ZFXG_ZDYBD_ZDDYB.SZLX  is '设置类型 当“字段类型”为：2:下拉框、3:单选框、4:复选框三种类型时，设置类型字段有效
1.固定值，格式为：1:男,2:女
2:数据库取值,“表名:代码,名称”,
3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个string类型；
4:基础数据获取，值为基础数据lxdm,根据lxdm获取相应类别的基础数据列表
5:直接在页面上制定list、key、value
';
comment on column ZFXG_ZDYBD_ZDDYB.YZLX  is '验证类型';
comment on column ZFXG_ZDYBD_ZDDYB.SZZ  is '设置值 该字段与“字段类型”、“设置类型”配合使用
当“字段类型”为：2:下拉框、3:单选框、4:复选框三种类型时，设置值与设置类型的关系如下：
1.固定值，格式为：1:男,2:女
2:数据库取值,“表名:代码,名称”
3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个string类型
4:基础数据获取，值为基础数据lxdm,根据lxdm获取相应类别的基础数据列表
5:直接在页面上制定list、key、value

当“字段类型”非：2:下拉框、3:单选框、4:复选框三种类型时，设置值与字段类型的关系如下：
13:日期文本框，格式为：yyyymmdd等
99:功能自定义，格式为：定义代码，与界面中div的id关联
';
comment on column ZFXG_ZDYBD_ZDDYB.ZBWZ  is '坐标位置 ';
comment on column ZFXG_ZDYBD_ZDDYB.SZLS  is '所占列数 ';
comment on column ZFXG_ZDYBD_ZDDYB.KDBL  is '宽度比例';
comment on column ZFXG_ZDYBD_ZDDYB.MCSQL  is '获取名称sql';
comment on column ZFXG_ZDYBD_ZDDYB.FJZDDYID  is '父级字段定义id :解决多层嵌套问题'; 

-- 以下非框架表结构应在后期的框架调整中逐渐去除
  
-- Create table
create table JW_JCDML_XTNZB
(
  XTNZ_ID VARCHAR2(32) default sys_guid() not null,
  ZDM     VARCHAR2(30) not null,
  ZDZ     VARCHAR2(30),
  ZS      VARCHAR2(300),
  BZ      VARCHAR2(100),
  primary key (ZDM)
);
-- Add comments to the table 
comment on table JW_JCDML_XTNZB  is '系统内置表';
-- Add comments to the columns 
comment on column JW_JCDML_XTNZB.XTNZ_ID  is '系统内置ID';
comment on column JW_JCDML_XTNZB.ZDM  is '字段名';
comment on column JW_JCDML_XTNZB.ZDZ  is '字段值';
comment on column JW_JCDML_XTNZB.ZS  is '注释';
comment on column JW_JCDML_XTNZB.BZ  is '备注'; 

-- Create table
create table JW_JG_JZGXXB
(
  JGH_ID    VARCHAR2(32) not null,
  JGH       VARCHAR2(20),
  DWH       VARCHAR2(100),
  XM        VARCHAR2(60),
  XMPY      VARCHAR2(60),
  CYM       VARCHAR2(60),
  XBMC      VARCHAR2(10),
  CSRQ      VARCHAR2(10),
  CSD       VARCHAR2(200),
  JG        VARCHAR2(50),
  MZ        VARCHAR2(50),
  GJ        VARCHAR2(50),
  SFZJLX    VARCHAR2(20),
  SFZH      VARCHAR2(20),
  HYZK      VARCHAR2(10),
  GATQW     VARCHAR2(20),
  JKZK      VARCHAR2(20),
  XYZJ      VARCHAR2(30),
  XXMC      VARCHAR2(10),
  ZGXL      VARCHAR2(30),
  WHCD      VARCHAR2(30),
  CJGZNY    VARCHAR2(10),
  LXRQ      VARCHAR2(10),
  QXRQ      VARCHAR2(10),
  CJNY      VARCHAR2(10),
  BZLB      VARCHAR2(30),
  JZGLB     VARCHAR2(30),
  RKZK      VARCHAR2(30),
  DABH      VARCHAR2(30),
  DAWB      VARCHAR2(1000),
  DQZT      VARCHAR2(6),
  YZBM      VARCHAR2(6),
  TXDZ      VARCHAR2(100),
  JTZZ      VARCHAR2(100),
  XZZ       VARCHAR2(100),
  HKSZD     VARCHAR2(100),
  HKLBMC    VARCHAR2(30),
  BGDH      VARCHAR2(30),
  SJHM      VARCHAR2(40),
  CZDH      VARCHAR2(30),
  DZYX      VARCHAR2(50),
  TC        VARCHAR2(100),
  XQMC      VARCHAR2(30),
  YJFX      VARCHAR2(30),
  YJXKMC    VARCHAR2(30),
  EJXKMC    VARCHAR2(30),
  KSMC      VARCHAR2(100),
  ZCMC      VARCHAR2(100),
  JGMC      VARCHAR2(100),
  JG_ID     VARCHAR2(32),
  XBM       VARCHAR2(2),
  JSJJ      CLOB,
  RYTYZC_ID VARCHAR2(32),
  SKZG      VARCHAR2(2) default '1',
  MZM       VARCHAR2(30),
  WHCDM     VARCHAR2(30),
  primary key (JGH_ID)
);
-- Add comments to the table 
comment on table JW_JG_JZGXXB  is '教职工信息表';
-- Add comments to the columns 
comment on column JW_JG_JZGXXB.JGH_ID  is '教工号ID';
comment on column JW_JG_JZGXXB.JGH  is '教工号';
comment on column JW_JG_JZGXXB.DWH  is '单位号';
comment on column JW_JG_JZGXXB.XM  is '姓名';
comment on column JW_JG_JZGXXB.XMPY  is '姓名拼音';
comment on column JW_JG_JZGXXB.CYM  is '曾用名';
comment on column JW_JG_JZGXXB.XBMC  is '性别';
comment on column JW_JG_JZGXXB.CSRQ  is '出生日期';
comment on column JW_JG_JZGXXB.CSD  is '出生地';
comment on column JW_JG_JZGXXB.JG  is '籍贯';
comment on column JW_JG_JZGXXB.MZ  is '民族';
comment on column JW_JG_JZGXXB.GJ  is '国籍/地区码';
comment on column JW_JG_JZGXXB.SFZJLX  is '身份证件类型';
comment on column JW_JG_JZGXXB.SFZH  is '身份证号';
comment on column JW_JG_JZGXXB.HYZK  is '婚姻状况';
comment on column JW_JG_JZGXXB.GATQW  is '港澳台侨外';
comment on column JW_JG_JZGXXB.JKZK  is '健康状况';
comment on column JW_JG_JZGXXB.XYZJ  is '信仰宗教';
comment on column JW_JG_JZGXXB.XXMC  is '血型';
comment on column JW_JG_JZGXXB.ZGXL  is '最高学历';
comment on column JW_JG_JZGXXB.WHCD  is '文化程度';
comment on column JW_JG_JZGXXB.CJGZNY  is '参加工作年月';
comment on column JW_JG_JZGXXB.LXRQ  is '来校日期';
comment on column JW_JG_JZGXXB.QXRQ  is '起薪日期';
comment on column JW_JG_JZGXXB.CJNY  is '从教年月';
comment on column JW_JG_JZGXXB.BZLB  is '编制类别';
comment on column JW_JG_JZGXXB.JZGLB  is '教职工类别';
comment on column JW_JG_JZGXXB.RKZK  is '任课状况';
comment on column JW_JG_JZGXXB.DABH  is '档案编号';
comment on column JW_JG_JZGXXB.DAWB  is '档案文本';
comment on column JW_JG_JZGXXB.DQZT  is '当前状态0：不在职；1：在职';
comment on column JW_JG_JZGXXB.YZBM  is '邮政编码';
comment on column JW_JG_JZGXXB.TXDZ  is '通讯地址';
comment on column JW_JG_JZGXXB.JTZZ  is '家庭住址';
comment on column JW_JG_JZGXXB.XZZ  is '现住址';
comment on column JW_JG_JZGXXB.HKSZD  is '户口所在地';
comment on column JW_JG_JZGXXB.HKLBMC  is '户口类别';
comment on column JW_JG_JZGXXB.BGDH  is '办公电话';
comment on column JW_JG_JZGXXB.SJHM  is '手机号码';
comment on column JW_JG_JZGXXB.CZDH  is '传真电话';
comment on column JW_JG_JZGXXB.DZYX  is '电子邮箱';
comment on column JW_JG_JZGXXB.TC  is '特长';
comment on column JW_JG_JZGXXB.XQMC  is '校区名称';
comment on column JW_JG_JZGXXB.YJFX  is '研究方向';
comment on column JW_JG_JZGXXB.YJXKMC  is '一级学科';
comment on column JW_JG_JZGXXB.EJXKMC  is '二级学科';
comment on column JW_JG_JZGXXB.KSMC  is '科室名称';
comment on column JW_JG_JZGXXB.ZCMC  is '职称名称';
comment on column JW_JG_JZGXXB.JGMC  is '机构名称';
comment on column JW_JG_JZGXXB.JG_ID  is '机构id';
comment on column JW_JG_JZGXXB.JSJJ  is '教师简介';
comment on column JW_JG_JZGXXB.RYTYZC_ID  is '人员统一注册Id';
comment on column JW_JG_JZGXXB.SKZG  is '上课资格1表示有资格；0表示无资格';
comment on column JW_JG_JZGXXB.MZM  is '民族码';
comment on column JW_JG_JZGXXB.WHCDM  is '文化程度码'; 

  -- Create table
create table JW_XJGL_XSJBXXB
(
  XH_ID     VARCHAR2(32) default sys_guid() not null,
  XH        VARCHAR2(20),
  XM        VARCHAR2(60),
  XMPY      VARCHAR2(60),
  YWXM      VARCHAR2(60),
  CYM       VARCHAR2(60),
  XBM       VARCHAR2(1),
  MZM       VARCHAR2(2),
  ZZMMM     VARCHAR2(2),
  CSRQ      VARCHAR2(10),
  RXRQ      VARCHAR2(10),
  CSD       VARCHAR2(200),
  SYD       VARCHAR2(200),
  ZJLXM     VARCHAR2(2),
  ZJHM      VARCHAR2(20),
  JG        VARCHAR2(50),
  HKSZD     VARCHAR2(200),
  XXM       VARCHAR2(30),
  GATQWM    VARCHAR2(2),
  GDDH      VARCHAR2(50),
  SJHM      VARCHAR2(20),
  DZYX      VARCHAR2(40),
  QQHM      VARCHAR2(20),
  TXDZ      VARCHAR2(100),
  YZBM      VARCHAR2(6),
  JTDZ      VARCHAR2(100),
  JTDH      VARCHAR2(20),
  XSZH      VARCHAR2(20),
  YHMC      VARCHAR2(50),
  YHKH      VARCHAR2(30),
  SG        NUMBER(4,2),
  TZ        NUMBER(6,2),
  TC        VARCHAR2(200),
  JKZK      VARCHAR2(20),
  HJXZ      VARCHAR2(20),
  RXFS      VARCHAR2(20),
  RXZF      NUMBER(7,2),
  ZKZH      VARCHAR2(30),
  KSH       VARCHAR2(30),
  BDH       VARCHAR2(20),
  KSLBM     VARCHAR2(20),
  LYM       VARCHAR2(4),
  SFZDS     VARCHAR2(6),
  FDYJGH    VARCHAR2(20),
  HYZKDM    VARCHAR2(10),
  DSJGH     VARCHAR2(20),
  HCPQJZ    VARCHAR2(20),
  XYZJ      VARCHAR2(50),
  BYZX      VARCHAR2(100),
  XXNX      VARCHAR2(8),
  YLZD1     VARCHAR2(50),
  YLZD2     VARCHAR2(50),
  YLZD3     VARCHAR2(50),
  YLZD4     VARCHAR2(50),
  YLZD5     VARCHAR2(50),
  YLZD6     VARCHAR2(50),
  YLZD7     VARCHAR2(50),
  YLZD8     VARCHAR2(50),
  YLZD9     VARCHAR2(50),
  YLZD10    VARCHAR2(50),
  BZ        VARCHAR2(100),
  GJ        VARCHAR2(50),
  XSBJ      VARCHAR2(24),
  RYTYZC_ID VARCHAR2(32),
  WPDW      VARCHAR2(100),
  XSLXDM    VARCHAR2(4),
  primary key (XH_ID)
);
-- Add comments to the table 
comment on table JW_XJGL_XSJBXXB  is '学生基本信息表';
-- Add comments to the columns 
comment on column JW_XJGL_XSJBXXB.XH_ID  is '学号id';
comment on column JW_XJGL_XSJBXXB.XH  is '学号';
comment on column JW_XJGL_XSJBXXB.XM  is '姓名';
comment on column JW_XJGL_XSJBXXB.XMPY  is '姓名拼音';
comment on column JW_XJGL_XSJBXXB.YWXM  is '英文姓名';
comment on column JW_XJGL_XSJBXXB.CYM  is '曾用名';
comment on column JW_XJGL_XSJBXXB.XBM  is '性别码';
comment on column JW_XJGL_XSJBXXB.MZM  is '民族码';
comment on column JW_XJGL_XSJBXXB.ZZMMM  is '政治面貌码';
comment on column JW_XJGL_XSJBXXB.CSRQ  is '出生日期';
comment on column JW_XJGL_XSJBXXB.RXRQ  is '入学日期';
comment on column JW_XJGL_XSJBXXB.CSD  is '出生地';
comment on column JW_XJGL_XSJBXXB.SYD  is '生源地';
comment on column JW_XJGL_XSJBXXB.ZJLXM  is '证件类型码';
comment on column JW_XJGL_XSJBXXB.ZJHM  is '证件号码';
comment on column JW_XJGL_XSJBXXB.JG  is '籍贯';
comment on column JW_XJGL_XSJBXXB.HKSZD  is '户口所在地';
comment on column JW_XJGL_XSJBXXB.XXM  is '血型码';
comment on column JW_XJGL_XSJBXXB.GATQWM  is '港澳台侨外码';
comment on column JW_XJGL_XSJBXXB.GDDH  is '固定电话';
comment on column JW_XJGL_XSJBXXB.SJHM  is '手机号码';
comment on column JW_XJGL_XSJBXXB.DZYX  is '电子邮箱';
comment on column JW_XJGL_XSJBXXB.QQHM  is 'QQ号码';
comment on column JW_XJGL_XSJBXXB.TXDZ  is '通讯地址';
comment on column JW_XJGL_XSJBXXB.YZBM  is '邮政编码';
comment on column JW_XJGL_XSJBXXB.JTDZ  is '家庭地址';
comment on column JW_XJGL_XSJBXXB.JTDH  is '家庭电话';
comment on column JW_XJGL_XSJBXXB.XSZH  is '学生证号';
comment on column JW_XJGL_XSJBXXB.YHMC  is '银行名称';
comment on column JW_XJGL_XSJBXXB.YHKH  is '银行卡号';
comment on column JW_XJGL_XSJBXXB.SG  is '身高';
comment on column JW_XJGL_XSJBXXB.TZ  is '体重';
comment on column JW_XJGL_XSJBXXB.TC  is '特长';
comment on column JW_XJGL_XSJBXXB.JKZK  is '健康状况';
comment on column JW_XJGL_XSJBXXB.HJXZ  is '户籍性质';
comment on column JW_XJGL_XSJBXXB.RXFS  is '入学方式';
comment on column JW_XJGL_XSJBXXB.RXZF  is '入学总分';
comment on column JW_XJGL_XSJBXXB.ZKZH  is '准考证号';
comment on column JW_XJGL_XSJBXXB.KSH  is '考生号';
comment on column JW_XJGL_XSJBXXB.BDH  is '报到号';
comment on column JW_XJGL_XSJBXXB.KSLBM  is '考生类别码';
comment on column JW_XJGL_XSJBXXB.LYM  is '来源码';
comment on column JW_XJGL_XSJBXXB.SFZDS  is '是否走读生1:走读；0:住校';
comment on column JW_XJGL_XSJBXXB.FDYJGH  is '辅导员教工号';
comment on column JW_XJGL_XSJBXXB.HYZKDM  is '婚姻状况代码';
comment on column JW_XJGL_XSJBXXB.DSJGH  is '导师教工号';
comment on column JW_XJGL_XSJBXXB.HCPQJZ  is '火车票区间站';
comment on column JW_XJGL_XSJBXXB.XYZJ  is '信仰宗教';
comment on column JW_XJGL_XSJBXXB.BYZX  is '毕业中学';
comment on column JW_XJGL_XSJBXXB.XXNX  is '学习年限';
comment on column JW_XJGL_XSJBXXB.YLZD1  is '预留字段1';
comment on column JW_XJGL_XSJBXXB.YLZD2  is '预留字段2';
comment on column JW_XJGL_XSJBXXB.YLZD3  is '预留字段3';
comment on column JW_XJGL_XSJBXXB.YLZD4  is '预留字段4';
comment on column JW_XJGL_XSJBXXB.YLZD5  is '预留字段5';
comment on column JW_XJGL_XSJBXXB.YLZD6  is '预留字段6';
comment on column JW_XJGL_XSJBXXB.YLZD7  is '预留字段7';
comment on column JW_XJGL_XSJBXXB.YLZD8  is '预留字段8';
comment on column JW_XJGL_XSJBXXB.YLZD9  is '预留字段9';
comment on column JW_XJGL_XSJBXXB.YLZD10  is '预留字段10';
comment on column JW_XJGL_XSJBXXB.BZ  is '备注';
comment on column JW_XJGL_XSJBXXB.GJ  is '国籍';
comment on column JW_XJGL_XSJBXXB.XSBJ  is '学生标记';
comment on column JW_XJGL_XSJBXXB.RYTYZC_ID  is '人员统一注册Id';
comment on column JW_XJGL_XSJBXXB.WPDW  is '委培单位';
comment on column JW_XJGL_XSJBXXB.XSLXDM  is '学生类型代码'; 
-- Create/Recreate indexes 
create index PK_JW_XJGL_XSJBXXB_XH on JW_XJGL_XSJBXXB (XH) ;

-- Create table
create table JW_XJGL_XSXJXXB
(
  XH_ID    VARCHAR2(32) not null,
  ZYH_ID   VARCHAR2(32),
  X_ID     VARCHAR2(10),
  JG_ID    VARCHAR2(32),
  ZSJG_ID  VARCHAR2(32),
  ZSZYH_ID VARCHAR2(32),
  NJDM_ID  VARCHAR2(32),
  ZSNDDM   VARCHAR2(4),
  ZSJD     VARCHAR2(4),
  BH_ID    VARCHAR2(32),
  SFZX     VARCHAR2(2),
  XJZTDM   VARCHAR2(2),
  BDZCBJ   VARCHAR2(2),
  XLCCDM   VARCHAR2(2),
  XZ       VARCHAR2(8),
  XSLBDM   VARCHAR2(3),
  PYCCDM   VARCHAR2(2),
  PYFSDM   VARCHAR2(2),
  BDSJ     VARCHAR2(20),
  ZCSJ     VARCHAR2(20),
  WZCYY    VARCHAR2(50),
  CXYY     VARCHAR2(50),
  WBDYY    VARCHAR2(50),
  XNM      VARCHAR2(4) not null,
  XQM      VARCHAR2(2) not null,
  BDZCBZ   VARCHAR2(100),
  ZYFX_ID  VARCHAR2(40),
  primary key (XH_ID, XNM, XQM)
);
-- Add comments to the table 
comment on table JW_XJGL_XSXJXXB is '学生学籍信息表';
-- Add comments to the columns 
comment on column JW_XJGL_XSXJXXB.XH_ID  is '学号id';
comment on column JW_XJGL_XSXJXXB.ZYH_ID  is '专业id';
comment on column JW_XJGL_XSXJXXB.X_ID  is '系id';
comment on column JW_XJGL_XSXJXXB.JG_ID  is '学院id';
comment on column JW_XJGL_XSXJXXB.ZSJG_ID  is '招生学院id';
comment on column JW_XJGL_XSXJXXB.ZSZYH_ID  is '招生专业号id';
comment on column JW_XJGL_XSXJXXB.NJDM_ID  is '年级代码id';
comment on column JW_XJGL_XSXJXXB.ZSNDDM  is '招生年度代码';
comment on column JW_XJGL_XSXJXXB.ZSJD  is '招生季度';
comment on column JW_XJGL_XSXJXXB.BH_ID  is '班级id';
comment on column JW_XJGL_XSXJXXB.SFZX  is '是否在校1:在校；0:不在校';
comment on column JW_XJGL_XSXJXXB.XJZTDM  is '学籍状态代码1:有学籍；0:无学籍';
comment on column JW_XJGL_XSXJXXB.BDZCBJ  is '报到注册标记1:报到;2:报到注册;0:未报到';
comment on column JW_XJGL_XSXJXXB.XLCCDM  is '学历层次';
comment on column JW_XJGL_XSXJXXB.XZ  is '学制';
comment on column JW_XJGL_XSXJXXB.XSLBDM  is '学生类别代码';
comment on column JW_XJGL_XSXJXXB.PYCCDM  is '培养层次代码';
comment on column JW_XJGL_XSXJXXB.PYFSDM  is '培养方式代码';
comment on column JW_XJGL_XSXJXXB.BDSJ  is '报到时间';
comment on column JW_XJGL_XSXJXXB.ZCSJ  is '注册时间';
comment on column JW_XJGL_XSXJXXB.WZCYY  is '未注册原因';
comment on column JW_XJGL_XSXJXXB.CXYY  is '撤销原因';
comment on column JW_XJGL_XSXJXXB.WBDYY  is '未报到原因';
comment on column JW_XJGL_XSXJXXB.XNM  is '学年码';
comment on column JW_XJGL_XSXJXXB.XQM  is '学期码';
comment on column JW_XJGL_XSXJXXB.BDZCBZ  is '报到注册备注';
comment on column JW_XJGL_XSXJXXB.ZYFX_ID  is '专业方向'; 
-- Create/Recreate indexes 
create index PK_JW_XSXJXXB_XNM on JW_XJGL_XSXJXXB (XNM, XQM);
create index PK_JW_XSXJXXB_XQM on JW_XJGL_XSXJXXB (XNM);

/* 
 * 创建时间：2016-05-24
 * 创建人：743
 * 备注：增加字段“xslx显示类型”，控制子菜单的显示样式 
 */
alter table niutal_XTGL_JSGNMKDMB add xslx VARCHAR2(1) default '0';
comment on column niutal_XTGL_JSGNMKDMB.xslx is '显示类型：0：菜单样式，1：页签样式';

-- Create table
create table niutal_GTGL_DCZDPZB
(
  DCCLBH VARCHAR2(300) not null,
  DCCLMC VARCHAR2(100),
  ZD     VARCHAR2(30) not null,
  ZDMC   VARCHAR2(100),
  XSSX   VARCHAR2(2),
  ZGH    VARCHAR2(40) not null,
  SFMRZD VARCHAR2(2),
  ZT     VARCHAR2(2)
);
-- Add comments to the table 
comment on table niutal_GTGL_DCZDPZB  is '导出字段配置表';
-- Add comments to the columns 
comment on column niutal_GTGL_DCZDPZB.DCCLBH  is '导出处理编号';
comment on column niutal_GTGL_DCZDPZB.DCCLMC  is '导出处理名称';
comment on column niutal_GTGL_DCZDPZB.ZD  is '字段';
comment on column niutal_GTGL_DCZDPZB.ZDMC  is '字段名称';
comment on column niutal_GTGL_DCZDPZB.XSSX  is '显示顺序';
comment on column niutal_GTGL_DCZDPZB.ZGH  is '职工号(提供一份以public命名用户的初始数据)';
comment on column niutal_GTGL_DCZDPZB.SFMRZD  is '是否默认字段(如果是，则不能更改是否可导出的状态)';
comment on column niutal_GTGL_DCZDPZB.ZT  is '状态(1：选中；0：未选)';
-- Create/Recreate primary, unique and foreign key constraints 
alter table niutal_GTGL_DCZDPZB  add constraint XG_GTGL_DCZDPZB_PK primary key (DCCLBH, ZD, ZGH);

  