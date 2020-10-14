
-- 高级报表基本信息表：指自定义报表的基本信息
-- Create table
create table niutal_report_details(
  	report_guid      		VARCHAR2(32) default sys_guid() not null,  
  	report_name    			VARCHAR2(50) not null,
  	report_alias    		VARCHAR2(50) not null,   
  	report_desc    			VARCHAR2(200) not null, 
  	report_file				BLOB
);
-- Add comments to the table 
comment on table niutal_report_details is '高级报表基本信息表 ';
-- Add comments to the columns 
comment on column niutal_report_details.report_guid is '高级报表基本信息表ID ';
comment on column niutal_report_details.report_name  is '高级报表名称（如：补课申请表）';
comment on column niutal_report_details.report_alias  is '高级报表别名;用于请求报表服务器的报表文件名（如：ttksqd_02,请求报表时自动添加.cpt后缀）';
comment on column niutal_report_details.report_desc  is '高级报表描述';
comment on column niutal_report_details.report_file is '报表模板文件';


-- 自定义高级报表设计表：指定自定义功能与高级报表关联关系和报表查询条件
-- Create table
create table niutal_designer_reports(
	func_guid				VARCHAR2(32) not null,
  	report_guid      		VARCHAR2(32) not null
);
-- Add comments to the table 
comment on table niutal_designer_reports is '自定义高级报表设计表 ';
-- Add comments to the columns 
comment on column niutal_designer_reports.func_guid is '系统自定义功能信息表ID（niutal_designer_func.func_guid）';
comment on column niutal_designer_reports.report_guid is '自定义高级报表设计表ID ';
