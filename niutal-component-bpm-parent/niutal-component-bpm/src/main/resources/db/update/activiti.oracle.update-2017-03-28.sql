create table ACT_RE_COMMON_MSG
(
	id_ 			NVARCHAR2(64) not null,
	user_id_		NVARCHAR2(64) not null,
	common_msg_     BLOB
);
-- Add comments to the table 
comment on table ACT_RE_COMMON_MSG
  is '常用意见表（扩展表，activiti不提供该表）';
-- Add comments to the columns 
comment on column ACT_RE_COMMON_MSG.id_
  is '主键';
comment on column ACT_RE_COMMON_MSG.user_id_
  is '用户id';
comment on column ACT_RE_COMMON_MSG.common_msg_
  is '常用意见信息';

alter table ACT_RE_COMMON_MSG add primary key (ID_);
  
--Create table
--流程业务定义表，用于分组业务字段，便于使用
create table ACT_RE_BIZ
(
  id_          NVARCHAR2(64) not null,
  name_        NVARCHAR2(255) not null,
  description_ NVARCHAR2(2000)
);
-- Add comments to the table 
comment on table ACT_RE_BIZ
  is '业务定义表（扩展表，activiti不提供该表）';
-- Add comments to the columns 
comment on column ACT_RE_BIZ.id_
  is '主键';
comment on column ACT_RE_BIZ.name_
  is '业务名称';
comment on column ACT_RE_BIZ.description_
  is '业务描述';

-- Create/Recreate primary, unique and foreign key constraints 
alter table ACT_RE_BIZ add primary key (ID_);
alter table ACT_RE_BIZ
  add constraint ACT_UNIQ_RE_BIZ_NAME unique (name_);

-- Create table
--流程业务字段配置，用于条件判断使用
create table ACT_RE_BIZFIELD
(
  id_          NVARCHAR2(64) not null,
  name_        NVARCHAR2(255) not null,
  label_       NVARCHAR2(255) not null,
  type_        NVARCHAR2(255) not null,
  values_      BLOB,
  required_    NUMBER(1) not null,
  description_ NVARCHAR2(2000),
  biz_id_      NVARCHAR2(64) not null
);
-- Add comments to the table 
comment on table ACT_RE_BIZFIELD
  is '业务字段定义表（扩展表，activiti不提供该表）';
-- Add comments to the columns 
comment on column ACT_RE_BIZFIELD.id_
  is '主键';
comment on column ACT_RE_BIZFIELD.name_
  is '字段名称';
comment on column ACT_RE_BIZFIELD.label_
  is '字段显示名称';
comment on column ACT_RE_BIZFIELD.description_
  is '字段描述';
comment on column ACT_RE_BIZFIELD.type_
  is '字段类型';
comment on column ACT_RE_BIZFIELD.values_
  is '字段值【如果字段是枚举类型，该字段定义枚举值】';
comment on column ACT_RE_BIZFIELD.required_
  is '是否必须【流程启动时需要检查调用则是否传递了该参数】';
comment on column ACT_RE_BIZFIELD.biz_id_
  is '业务对象ID';


-- Create/Recreate indexes 
create index ACT_IDX_RE_BIZFIELD_BIZID on ACT_RE_BIZFIELD (biz_id_);
-- Create/Recreate primary, unique and foreign key constraints 
alter table ACT_RE_BIZFIELD add primary key (ID_);
alter table ACT_RE_BIZFIELD add check (required_ IN (1,0));
alter table ACT_RE_BIZFIELD
  add constraint ACT_FK_RE_BIZFIELD_BIZID foreign key (biz_id_)
  references ACT_RE_BIZ (ID_);
  
  
--加入executionId 字段
CREATE OR REPLACE VIEW V_HIS_TASKLIST AS
SELECT a.proc_inst_id_ P_PROC_INST_ID,
				 a.proc_def_id_ P_PROC_DEF_ID,
				 a.execution_id_ P_EXECUTION_ID,
				 a.task_def_key_ P_TASK_DEF_ID,
				 a.id_ P_TASK_ID,
				 a.name_ P_TASK_NAME,
				 a.assignee_ P_ASSIGNEE,
				 a.owner_ P_OWNER,
				 a.description_ P_DESCRIPTION,
				 a.category_ P_TASK_CATEGORY,
				 a.duration_ P_DURATION,
				 TO_CHAR(A.CLAIM_TIME_,'YYYY-MM-DD HH24:MI:SS') AS P_CLAIM_DATE,
				 TO_CHAR(A.START_TIME_, 'YYYY-MM-DD HH24:MI:SS') AS P_START_TIME,
				 TO_CHAR(A.END_TIME_,'YYYY-MM-DD HH24:MI:SS') AS P_END_TIME,
				 b.message_ as P_TASK_MESSAGE,
				 b.full_msg_ as P_TASK_FULL_MESSAGE
  FROM (SELECT row_number() OVER(PARTITION BY proc_inst_id_, assignee_ ORDER BY end_time_ DESC) LEV, t.*
          FROM act_hi_taskinst t
         WHERE t.assignee_ IS NOT NULL and t.end_time_ IS NOT NULL) a
				 LEFT JOIN act_hi_comment b ON a.proc_inst_id_=b.proc_inst_id_ and a.id_ = b.task_id_ and b.type_='process_log'
 WHERE LEV = 1 ORDER BY a.END_TIME_;

--updated 2017/4/7/12
alter table act_re_assignment add priority_ integer;
comment on column act_re_assignment.priority_ is '优先级';