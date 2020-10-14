--create ACT_RE_PROPERTY_EXT 扩展属性表
create table ACT_RE_PROPERTY_EXT
(
	id_ 				NVARCHAR2(64) not null,
	process_def_id_ 	NVARCHAR2(64) not null,
	task_def_key_		NVARCHAR2(64),
	name_				NVARCHAR2(64) not null,
	value_				NVARCHAR2(300) not null,
	rev_				INTEGER not null
	
);
-- Add comments to the table 
comment on table ACT_RE_PROPERTY_EXT
  is '属性扩展表（扩展表，activiti不提供该表）';
-- Add comments to the columns 
comment on column ACT_RE_PROPERTY_EXT.id_
  is '主键';
comment on column ACT_RE_PROPERTY_EXT.process_def_id_
  is '流程定义id';
comment on column ACT_RE_PROPERTY_EXT.task_def_key_
  is '流程任务定义id';
comment on column ACT_RE_PROPERTY_EXT.name_
  is '属性名';
comment on column ACT_RE_PROPERTY_EXT.value_
  is '属性值';
comment on column ACT_RE_PROPERTY_EXT.rev_
  is '版本';

alter table ACT_RE_PROPERTY_EXT add primary key (ID_);