--表单定义表达
create table ACT_RE_FORMDEF (
    ID_ NVARCHAR2(64) NOT NULL,
    REV_ INTEGER,
    CATEGORY_ NVARCHAR2(255),
    NAME_ NVARCHAR2(255),
    KEY_ NVARCHAR2(255) NOT NULL,
    VERSION_ INTEGER NOT NULL,
    DEPLOYMENT_ID_ NVARCHAR2(64),
    DESCRIPTION_ NVARCHAR2(2000),
    SUSPENSION_STATE_ INTEGER,
    TENANT_ID_ NVARCHAR2(255) DEFAULT '',
    primary key (ID_)
);

-- Add comments to the table 
comment on table ACT_RE_FORMDEF
  is '表单定义表（扩展表，activiti不提供该表）';

alter table ACT_RE_FORMDEF
    add constraint ACT_UNIQ_FORMDEF
    unique (KEY_,VERSION_, TENANT_ID_);