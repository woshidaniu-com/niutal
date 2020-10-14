--Create table
--流程类别表
create table ACT_RE_PROCAT
(
  id_          NVARCHAR2(64) not null,
  name_        NVARCHAR2(255) not null,
  description_ NVARCHAR2(2000)
);
-- Add comments to the table 
comment on table ACT_RE_PROCAT
  is '类别定义表（扩展表，activiti不提供该表）';
-- Add comments to the columns 
comment on column ACT_RE_PROCAT.id_
  is '主键';
comment on column ACT_RE_PROCAT.name_
  is '类别名称';
comment on column ACT_RE_PROCAT.description_
  is '类别描述';
  
  
create table ACT_SI_ACTIVITY
(
	 id_          	NVARCHAR2(64) not null,
	 proc_def_id_ 	NVARCHAR2(64) not null,
	 business_key_ 	NVARCHAR2(255) not null,
	 act_id_		NVARCHAR2(255) not null,
	 act_name_		NVARCHAR2(255),
	 act_type_		NVARCHAR2(255),
	 order_			number(3) not null
);

-- Add comments to the table 
comment on table ACT_SI_ACTIVITY
  is '流程模拟表（扩展表，activiti不提供该表）';
-- Add comments to the columns 
comment on column ACT_SI_ACTIVITY.id_
  is '主键';
comment on column ACT_SI_ACTIVITY.proc_def_id_
  is '流程定义ID';
comment on column ACT_SI_ACTIVITY.business_key_
  is '业务数据ID';
comment on column ACT_SI_ACTIVITY.act_id_
  is 'activityId';
comment on column ACT_SI_ACTIVITY.act_name_
  is 'activityName';
comment on column ACT_SI_ACTIVITY.act_type_
  is 'activityType';
comment on column ACT_SI_ACTIVITY.order_
  is '顺序';