-----------------------------------------------------
-- Export file for user HRM_V4_1                   --
-- Created by Administrator on 2013-8-23, 10:06:28 --
-----------------------------------------------------

spool 11111.log

prompt
prompt Creating table SP_AUDITING_LOG
prompt ==============================
prompt
create table HRM_V4_1.SP_AUDITING_LOG
(
  LOG_ID       VARCHAR2(32) not null,
  LOG_TYPE     VARCHAR2(32),
  LOG_TIME     DATE,
  W_ID         VARCHAR2(32),
  B_TYPE       VARCHAR2(100),
  O_TYPE       VARCHAR2(200),
  O_STATUS     VARCHAR2(32),
  O_RESULT     VARCHAR2(4000),
  O_CONTENT    VARCHAR2(4000),
  O_ROLE       VARCHAR2(100),
  OPERATOR     VARCHAR2(100),
  O_SUGGESTION VARCHAR2(1000)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_AUDITING_LOG
  is '工作审核日志表';
comment on column HRM_V4_1.SP_AUDITING_LOG.LOG_ID
  is '日志ID';
comment on column HRM_V4_1.SP_AUDITING_LOG.LOG_TYPE
  is '日志类型';
comment on column HRM_V4_1.SP_AUDITING_LOG.LOG_TIME
  is '日志时间';
comment on column HRM_V4_1.SP_AUDITING_LOG.W_ID
  is '工作ID';
comment on column HRM_V4_1.SP_AUDITING_LOG.B_TYPE
  is '业务类型';
comment on column HRM_V4_1.SP_AUDITING_LOG.O_TYPE
  is '操作类型';
comment on column HRM_V4_1.SP_AUDITING_LOG.O_STATUS
  is '操作状态';
comment on column HRM_V4_1.SP_AUDITING_LOG.O_RESULT
  is '操作结果';
comment on column HRM_V4_1.SP_AUDITING_LOG.O_CONTENT
  is '操作内容';
comment on column HRM_V4_1.SP_AUDITING_LOG.O_ROLE
  is '操作角色';
comment on column HRM_V4_1.SP_AUDITING_LOG.OPERATOR
  is '操作人';
comment on column HRM_V4_1.SP_AUDITING_LOG.O_SUGGESTION
  is '操作意见';
alter table HRM_V4_1.SP_AUDITING_LOG
  add constraint PK_SP_AUDITING_LOG primary key (LOG_ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SP_BILL_CONFIG
prompt =============================
prompt
create table HRM_V4_1.SP_BILL_CONFIG
(
  ID          VARCHAR2(32) not null,
  NAME        VARCHAR2(32),
  STATUS      VARCHAR2(32),
  CONTENT     CLOB,
  CREATE_DATE DATE,
  ID_NAME     VARCHAR2(32),
  BILL_TYPE   VARCHAR2(32)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_BILL_CONFIG
  is '审批动态表单配置表';
comment on column HRM_V4_1.SP_BILL_CONFIG.ID
  is '序号';
comment on column HRM_V4_1.SP_BILL_CONFIG.NAME
  is '表单名称';
comment on column HRM_V4_1.SP_BILL_CONFIG.STATUS
  is '表单状态';
comment on column HRM_V4_1.SP_BILL_CONFIG.CONTENT
  is '表单配置内容';
comment on column HRM_V4_1.SP_BILL_CONFIG.CREATE_DATE
  is '创建日期';
comment on column HRM_V4_1.SP_BILL_CONFIG.ID_NAME
  is '实例存储表';
comment on column HRM_V4_1.SP_BILL_CONFIG.BILL_TYPE
  is '表单类型';
alter table HRM_V4_1.SP_BILL_CONFIG
  add constraint BILL_CONFIG_P_KEY primary key (ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table HRM_V4_1.SP_BILL_CONFIG
  add constraint BILL_CONFIG_U_KEY unique (NAME)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SP_BILL_DATAPUSH_EVENT_CONFIG
prompt ============================================
prompt
create table HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG
(
  ID               VARCHAR2(32) not null,
  BILL_CONFIG_ID   VARCHAR2(32),
  BILL_CLASS_ID    NUMBER,
  EVENT_TYPE       VARCHAR2(32),
  INFO_CLASS_ID    VARCHAR2(32),
  EVENT_OP_TYPE    VARCHAR2(32),
  WHERE_EXPRESSION VARCHAR2(2000),
  LOCAL_TABLE      VARCHAR2(255),
  NAME             VARCHAR2(32)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG
  is '表单数据推送事件配置表';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.ID
  is '编号';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.BILL_CONFIG_ID
  is '表单配置编号';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.BILL_CLASS_ID
  is '表单类编号';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.EVENT_TYPE
  is '事件类型';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.INFO_CLASS_ID
  is '信息类编号';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.EVENT_OP_TYPE
  is '事件操作类型';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.WHERE_EXPRESSION
  is '条件表达式';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.LOCAL_TABLE
  is '本地表';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.NAME
  is '事件名称';
alter table HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG
  add constraint EVENT_CONFIG_P_ID primary key (ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG
  add constraint BILL_CONFIG_ID foreign key (BILL_CONFIG_ID)
  references HRM_V4_1.SP_BILL_CONFIG (ID);

prompt
prompt Creating table SP_BILL_DATAPUSH_PROPERTY
prompt ========================================
prompt
create table HRM_V4_1.SP_BILL_DATAPUSH_PROPERTY
(
  ID                VARCHAR2(32) not null,
  EVENT_CONFIG_ID   VARCHAR2(32),
  LOCAL_PROPERTY_ID VARCHAR2(32),
  BILL_PROPERTY_ID  VARCHAR2(32)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_BILL_DATAPUSH_PROPERTY
  is '表单数据推送事件配置属性表';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_PROPERTY.ID
  is '';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_PROPERTY.EVENT_CONFIG_ID
  is '事件配置编号';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_PROPERTY.LOCAL_PROPERTY_ID
  is '本地或信息类属性';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_PROPERTY.BILL_PROPERTY_ID
  is '表单属性编号';
alter table HRM_V4_1.SP_BILL_DATAPUSH_PROPERTY
  add constraint EVENT_PROPERTY_P primary key (ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table HRM_V4_1.SP_BILL_DATAPUSH_PROPERTY
  add constraint EVENT_PROPERTY_F foreign key (EVENT_CONFIG_ID)
  references HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG (ID);

prompt
prompt Creating table SP_BILL_INSTANCE
prompt ===============================
prompt
create table HRM_V4_1.SP_BILL_INSTANCE
(
  ID             VARCHAR2(32) not null,
  STATUS         VARCHAR2(32),
  CONTENT        CLOB,
  CREATE_DATE    DATE,
  COMMIT_DATE    DATE,
  BILL_CONFIG_ID VARCHAR2(32)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_BILL_INSTANCE
  is '审批动态表单实例表';
comment on column HRM_V4_1.SP_BILL_INSTANCE.ID
  is '序号';
comment on column HRM_V4_1.SP_BILL_INSTANCE.STATUS
  is '表单状态';
comment on column HRM_V4_1.SP_BILL_INSTANCE.CONTENT
  is '表单内容';
comment on column HRM_V4_1.SP_BILL_INSTANCE.CREATE_DATE
  is '创建时间';
comment on column HRM_V4_1.SP_BILL_INSTANCE.COMMIT_DATE
  is '提交时间';
comment on column HRM_V4_1.SP_BILL_INSTANCE.BILL_CONFIG_ID
  is '表单配置编号';
alter table HRM_V4_1.SP_BILL_INSTANCE
  add constraint BILL_INSTANCE_P_KEY primary key (ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table HRM_V4_1.SP_BILL_INSTANCE
  add constraint BILL_CONFIG_F_KEY foreign key (BILL_CONFIG_ID)
  references HRM_V4_1.SP_BILL_CONFIG (ID);

prompt
prompt Creating table SP_BUSINESS
prompt ==========================
prompt
create table HRM_V4_1.SP_BUSINESS
(
  B_ID              VARCHAR2(32) not null,
  B_NAME            VARCHAR2(64),
  B_TYPE            VARCHAR2(32),
  B_STATUS          CHAR(1),
  BELONG_TO_SYS     VARCHAR2(32),
  B_CODE            VARCHAR2(100),
  P_ID              VARCHAR2(32),
  REL_DETAIL        VARCHAR2(1000),
  CLASSES_PRIVILEGE VARCHAR2(4000),
  BILL_ID           VARCHAR2(32)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_BUSINESS
  is '业务管理表';
comment on column HRM_V4_1.SP_BUSINESS.B_ID
  is '业务ID';
comment on column HRM_V4_1.SP_BUSINESS.B_NAME
  is '业务名称';
comment on column HRM_V4_1.SP_BUSINESS.B_TYPE
  is '业务类型';
comment on column HRM_V4_1.SP_BUSINESS.B_STATUS
  is '业务状态';
comment on column HRM_V4_1.SP_BUSINESS.BELONG_TO_SYS
  is '所属系统';
comment on column HRM_V4_1.SP_BUSINESS.B_CODE
  is '业务编码';
comment on column HRM_V4_1.SP_BUSINESS.P_ID
  is '流程ID';
comment on column HRM_V4_1.SP_BUSINESS.REL_DETAIL
  is '关联明细';
comment on column HRM_V4_1.SP_BUSINESS.CLASSES_PRIVILEGE
  is '表单类权限串';
comment on column HRM_V4_1.SP_BUSINESS.BILL_ID
  is '表单ID';
alter table HRM_V4_1.SP_BUSINESS
  add constraint PK_SP_BUSINESS primary key (B_ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SP_NODE
prompt ======================
prompt
create table HRM_V4_1.SP_NODE
(
  NODE_ID     VARCHAR2(32) not null,
  P_ID        VARCHAR2(32),
  NODE_NAME   VARCHAR2(64),
  NODE_TYPE   VARCHAR2(32),
  NODE_STATUS CHAR(1) default 1,
  NODE_DESC   VARCHAR2(200),
  ROLE_ID     VARCHAR2(32),
  OUT_TYPE    VARCHAR2(32),
  IN_TYPE     VARCHAR2(32),
  IS_AUTO     CHAR(1) default 0
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_NODE
  is '节点表';
comment on column HRM_V4_1.SP_NODE.NODE_ID
  is '节点ID';
comment on column HRM_V4_1.SP_NODE.P_ID
  is '流程ID';
comment on column HRM_V4_1.SP_NODE.NODE_NAME
  is '节点名称';
comment on column HRM_V4_1.SP_NODE.NODE_TYPE
  is '节点类型（起点；正常；分支；合并；终点）';
comment on column HRM_V4_1.SP_NODE.NODE_STATUS
  is '节点状态（1启用；0关闭）';
comment on column HRM_V4_1.SP_NODE.NODE_DESC
  is '节点描述';
comment on column HRM_V4_1.SP_NODE.ROLE_ID
  is '角色ID';
comment on column HRM_V4_1.SP_NODE.OUT_TYPE
  is '流出类型（并行；选择）';
comment on column HRM_V4_1.SP_NODE.IN_TYPE
  is '流入类型（全部执行；部分执行）';
comment on column HRM_V4_1.SP_NODE.IS_AUTO
  is '是否自动（1是；0否）';
alter table HRM_V4_1.SP_NODE
  add constraint PK_SP_NODE primary key (NODE_ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SP_PROCEDURE
prompt ===========================
prompt
create table HRM_V4_1.SP_PROCEDURE
(
  P_ID          VARCHAR2(32) not null,
  P_NAME        VARCHAR2(64),
  P_TYPE        VARCHAR2(32),
  P_STATUS      CHAR(1),
  P_DESC        VARCHAR2(200),
  BELONG_TO_SYS VARCHAR2(32),
  LINK          VARCHAR2(200)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_PROCEDURE
  is '流程表';
comment on column HRM_V4_1.SP_PROCEDURE.P_ID
  is '流程ID';
comment on column HRM_V4_1.SP_PROCEDURE.P_NAME
  is '流程名称';
comment on column HRM_V4_1.SP_PROCEDURE.P_TYPE
  is '流程类型（主流程；子流程）';
comment on column HRM_V4_1.SP_PROCEDURE.P_STATUS
  is '流程状态';
comment on column HRM_V4_1.SP_PROCEDURE.P_DESC
  is '流程描述';
comment on column HRM_V4_1.SP_PROCEDURE.BELONG_TO_SYS
  is '所属系统';
comment on column HRM_V4_1.SP_PROCEDURE.LINK
  is '审核页面链接';
alter table HRM_V4_1.SP_PROCEDURE
  add constraint PK_SP_PROCEDURE primary key (P_ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SP_LINE
prompt ======================
prompt
create table HRM_V4_1.SP_LINE
(
  LINE_ID      VARCHAR2(32) not null,
  P_ID         VARCHAR2(32),
  U_NODE_ID    VARCHAR2(32),
  D_NODE_ID    VARCHAR2(32),
  EXPRESSION   VARCHAR2(200),
  LINE_DESC    VARCHAR2(200),
  IS_MUST_PASS CHAR(1) default 1
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_LINE
  is '节点连线表';
comment on column HRM_V4_1.SP_LINE.LINE_ID
  is '连线ID';
comment on column HRM_V4_1.SP_LINE.P_ID
  is '流程ID';
comment on column HRM_V4_1.SP_LINE.U_NODE_ID
  is '上节点ID';
comment on column HRM_V4_1.SP_LINE.D_NODE_ID
  is '下节点ID';
comment on column HRM_V4_1.SP_LINE.EXPRESSION
  is '条件表达式';
comment on column HRM_V4_1.SP_LINE.LINE_DESC
  is '连线描述';
comment on column HRM_V4_1.SP_LINE.IS_MUST_PASS
  is '是否必须经过（1是；0否）';
alter table HRM_V4_1.SP_LINE
  add constraint PK_SP_LINE primary key (LINE_ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table HRM_V4_1.SP_LINE
  add constraint FK_SP_LINE_REFERENCE_SP_NODE_D foreign key (D_NODE_ID)
  references HRM_V4_1.SP_NODE (NODE_ID) on delete cascade;
alter table HRM_V4_1.SP_LINE
  add constraint FK_SP_LINE_REFERENCE_SP_NODE_U foreign key (U_NODE_ID)
  references HRM_V4_1.SP_NODE (NODE_ID) on delete cascade;
alter table HRM_V4_1.SP_LINE
  add constraint FK_SP_LINE_REFERENCE_SP_PROCE foreign key (P_ID)
  references HRM_V4_1.SP_PROCEDURE (P_ID) on delete cascade;

prompt
prompt Creating table SP_NODE_BILL
prompt ===========================
prompt
create table HRM_V4_1.SP_NODE_BILL
(
  ID                VARCHAR2(32) not null,
  NODE_ID           VARCHAR2(32),
  BILL_ID           VARCHAR2(32),
  CLASSES_PRIVILEGE VARCHAR2(1000),
  CLASS_ID          VARCHAR2(32),
  CLASS_MODE_TYPE   VARCHAR2(32),
  BILL_TYPE         VARCHAR2(32)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_NODE_BILL
  is '节点动态表单关联表';
comment on column HRM_V4_1.SP_NODE_BILL.ID
  is 'ID';
comment on column HRM_V4_1.SP_NODE_BILL.NODE_ID
  is '节点ID';
comment on column HRM_V4_1.SP_NODE_BILL.BILL_ID
  is '表单ID';
comment on column HRM_V4_1.SP_NODE_BILL.CLASSES_PRIVILEGE
  is '表单类权限串';
comment on column HRM_V4_1.SP_NODE_BILL.CLASS_ID
  is '表单类ID';
comment on column HRM_V4_1.SP_NODE_BILL.CLASS_MODE_TYPE
  is '表单类模式类型';
comment on column HRM_V4_1.SP_NODE_BILL.BILL_TYPE
  is '表单类型';
alter table HRM_V4_1.SP_NODE_BILL
  add constraint PK_SP_NODE_BILL primary key (ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SP_TASK
prompt ======================
prompt
create table HRM_V4_1.SP_TASK
(
  TASK_ID       VARCHAR2(32) not null,
  TASK_NAME     VARCHAR2(32),
  TASK_TYPE     VARCHAR2(32),
  TASK_STATUS   CHAR(1),
  TASK_DESC     VARCHAR2(200),
  TASK_CODE     VARCHAR2(32),
  BELONG_TO_SYS VARCHAR2(32)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_TASK
  is '流程节点中的任务表';
comment on column HRM_V4_1.SP_TASK.TASK_ID
  is '任务ID';
comment on column HRM_V4_1.SP_TASK.TASK_NAME
  is '任务名称';
comment on column HRM_V4_1.SP_TASK.TASK_TYPE
  is '任务类型';
comment on column HRM_V4_1.SP_TASK.TASK_STATUS
  is '任务状态';
comment on column HRM_V4_1.SP_TASK.TASK_DESC
  is '任务描述';
comment on column HRM_V4_1.SP_TASK.TASK_CODE
  is '任务编码';
comment on column HRM_V4_1.SP_TASK.BELONG_TO_SYS
  is '所属系统';
alter table HRM_V4_1.SP_TASK
  add constraint PK_SP_TASK primary key (TASK_ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SP_NODE_TASK
prompt ===========================
prompt
create table HRM_V4_1.SP_NODE_TASK
(
  TASK_ID           VARCHAR2(32),
  NODE_ID           VARCHAR2(32),
  IS_MUST           CHAR(1),
  IS_AUTO           CHAR(1),
  BILL_ID           VARCHAR2(32),
  CLASS_ID          VARCHAR2(32),
  CLASS_MODE_TYPE   VARCHAR2(32),
  EXE_CONDITION     VARCHAR2(200),
  CLASSES_PRIVILEGE VARCHAR2(200),
  DEC_CONDITION     VARCHAR2(500)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_NODE_TASK
  is '任务节点关联表';
comment on column HRM_V4_1.SP_NODE_TASK.TASK_ID
  is '任务ID';
comment on column HRM_V4_1.SP_NODE_TASK.NODE_ID
  is '节点ID';
comment on column HRM_V4_1.SP_NODE_TASK.IS_MUST
  is '是否必须执行（Y是；N否）';
comment on column HRM_V4_1.SP_NODE_TASK.IS_AUTO
  is '是否自动执行（Y是；N否）';
comment on column HRM_V4_1.SP_NODE_TASK.BILL_ID
  is '表单ID';
comment on column HRM_V4_1.SP_NODE_TASK.CLASS_ID
  is '表单类ID';
comment on column HRM_V4_1.SP_NODE_TASK.CLASS_MODE_TYPE
  is '表单类模式类别';
comment on column HRM_V4_1.SP_NODE_TASK.EXE_CONDITION
  is '执行条件表达式';
comment on column HRM_V4_1.SP_NODE_TASK.CLASSES_PRIVILEGE
  is '表单类权限串';
comment on column HRM_V4_1.SP_NODE_TASK.DEC_CONDITION
  is '执行条件描述';
alter table HRM_V4_1.SP_NODE_TASK
  add constraint FK_SP_NODE__REFERENCE_SP_TASK foreign key (TASK_ID)
  references HRM_V4_1.SP_TASK (TASK_ID);

prompt
prompt Creating table SP_PROCEDURE_BILL
prompt ================================
prompt
create table HRM_V4_1.SP_PROCEDURE_BILL
(
  ID                VARCHAR2(32) not null,
  P_ID              VARCHAR2(32),
  BILL_ID           VARCHAR2(32),
  BILL_TYPE         VARCHAR2(32),
  CLASSES_PRIVILEGE VARCHAR2(4000)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_PROCEDURE_BILL
  is '流程表单管理表';
comment on column HRM_V4_1.SP_PROCEDURE_BILL.ID
  is 'ID';
comment on column HRM_V4_1.SP_PROCEDURE_BILL.P_ID
  is '流程ID';
comment on column HRM_V4_1.SP_PROCEDURE_BILL.BILL_ID
  is '表单ID';
comment on column HRM_V4_1.SP_PROCEDURE_BILL.BILL_TYPE
  is '表单类型';
comment on column HRM_V4_1.SP_PROCEDURE_BILL.CLASSES_PRIVILEGE
  is '表单类权限串';
alter table HRM_V4_1.SP_PROCEDURE_BILL
  add constraint PK_SP_PROCEDURE_BILL primary key (ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SP_WORK_LINE
prompt ===========================
prompt
create table HRM_V4_1.SP_WORK_LINE
(
  ID           VARCHAR2(32) not null,
  W_ID         VARCHAR2(32),
  P_ID         VARCHAR2(32),
  U_NODE_ID    VARCHAR2(32),
  D_NODE_ID    VARCHAR2(32),
  EXPRESSION   VARCHAR2(200),
  LINE_DESC    VARCHAR2(200),
  IS_MUST_PASS CHAR(1)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_WORK_LINE
  is '工作审核节点连线表';
comment on column HRM_V4_1.SP_WORK_LINE.ID
  is 'ID';
comment on column HRM_V4_1.SP_WORK_LINE.W_ID
  is '工作ID';
comment on column HRM_V4_1.SP_WORK_LINE.P_ID
  is '流程ID';
comment on column HRM_V4_1.SP_WORK_LINE.U_NODE_ID
  is '上节点ID';
comment on column HRM_V4_1.SP_WORK_LINE.D_NODE_ID
  is '下节点ID';
comment on column HRM_V4_1.SP_WORK_LINE.EXPRESSION
  is '条件表达式';
comment on column HRM_V4_1.SP_WORK_LINE.LINE_DESC
  is '连线描述';
comment on column HRM_V4_1.SP_WORK_LINE.IS_MUST_PASS
  is '是否必须经过（1是；0否）';
alter table HRM_V4_1.SP_WORK_LINE
  add constraint PK_SP_WORK_LINE primary key (ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SP_WORK_NODE
prompt ===========================
prompt
create table HRM_V4_1.SP_WORK_NODE
(
  ID           VARCHAR2(32) not null,
  W_ID         VARCHAR2(32),
  NODE_ID      VARCHAR2(32),
  P_ID         VARCHAR2(32),
  NODE_NAME    VARCHAR2(64),
  NODE_TYPE    VARCHAR2(32),
  NODE_DESC    VARCHAR2(200),
  ROLE_ID      VARCHAR2(32),
  OUT_TYPE     VARCHAR2(32),
  IN_TYPE      VARCHAR2(32),
  E_STATUS     VARCHAR2(32),
  STATUS       VARCHAR2(32),
  AUDITOR_ID   VARCHAR2(32),
  AUDIT_TIME   DATE,
  AUDIT_RESULT VARCHAR2(4000),
  SUGGESTION   VARCHAR2(1000),
  IS_AUTO      CHAR(1)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_WORK_NODE
  is '工作审核节点表';
comment on column HRM_V4_1.SP_WORK_NODE.ID
  is 'ID';
comment on column HRM_V4_1.SP_WORK_NODE.W_ID
  is '工作ID';
comment on column HRM_V4_1.SP_WORK_NODE.NODE_ID
  is '节点ID';
comment on column HRM_V4_1.SP_WORK_NODE.P_ID
  is '流程ID';
comment on column HRM_V4_1.SP_WORK_NODE.NODE_NAME
  is '节点名称';
comment on column HRM_V4_1.SP_WORK_NODE.NODE_TYPE
  is '节点类型（起点；正常；分支；合并；终点）';
comment on column HRM_V4_1.SP_WORK_NODE.NODE_DESC
  is '节点描述';
comment on column HRM_V4_1.SP_WORK_NODE.ROLE_ID
  is '角色ID';
comment on column HRM_V4_1.SP_WORK_NODE.OUT_TYPE
  is '流出类型（并行；选择）';
comment on column HRM_V4_1.SP_WORK_NODE.IN_TYPE
  is '流入类型（全部执行；部分执行）';
comment on column HRM_V4_1.SP_WORK_NODE.E_STATUS
  is '执行状态（关闭；待执行；已执行）';
comment on column HRM_V4_1.SP_WORK_NODE.STATUS
  is '审核状态（待审核；通过；不通过）';
comment on column HRM_V4_1.SP_WORK_NODE.AUDITOR_ID
  is '审核人';
comment on column HRM_V4_1.SP_WORK_NODE.AUDIT_TIME
  is '审核时间';
comment on column HRM_V4_1.SP_WORK_NODE.AUDIT_RESULT
  is '审核结果';
comment on column HRM_V4_1.SP_WORK_NODE.SUGGESTION
  is '审核意见';
comment on column HRM_V4_1.SP_WORK_NODE.IS_AUTO
  is '是否自动（1是；0否）';
alter table HRM_V4_1.SP_WORK_NODE
  add constraint PK_SP_WORK_NODE primary key (ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SP_WORK_NODE_BILL
prompt ================================
prompt
create table HRM_V4_1.SP_WORK_NODE_BILL
(
  ID                VARCHAR2(32) not null,
  W_ID              VARCHAR2(32),
  NODE_ID           VARCHAR2(32),
  BILL_ID           VARCHAR2(32),
  CLASSES_PRIVILEGE VARCHAR2(1000),
  CLASS_ID          VARCHAR2(32),
  CLASS_MODE_TYPE   VARCHAR2(32),
  BILL_TYPE         VARCHAR2(32)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_WORK_NODE_BILL
  is '工作审核节点动态表单关联表';
comment on column HRM_V4_1.SP_WORK_NODE_BILL.ID
  is 'ID';
comment on column HRM_V4_1.SP_WORK_NODE_BILL.W_ID
  is '工作ID';
comment on column HRM_V4_1.SP_WORK_NODE_BILL.NODE_ID
  is '节点ID';
comment on column HRM_V4_1.SP_WORK_NODE_BILL.BILL_ID
  is '表单ID';
comment on column HRM_V4_1.SP_WORK_NODE_BILL.CLASSES_PRIVILEGE
  is '表单类权限串';
comment on column HRM_V4_1.SP_WORK_NODE_BILL.CLASS_ID
  is '表单类ID';
comment on column HRM_V4_1.SP_WORK_NODE_BILL.CLASS_MODE_TYPE
  is '表单类模式类型';
comment on column HRM_V4_1.SP_WORK_NODE_BILL.BILL_TYPE
  is '表单类型';
alter table HRM_V4_1.SP_WORK_NODE_BILL
  add constraint PK_SP_WORK_NODE_BILL primary key (ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SP_WORK_PROCEDURE
prompt ================================
prompt
create table HRM_V4_1.SP_WORK_PROCEDURE
(
  ID     VARCHAR2(32) not null,
  W_ID   VARCHAR2(32),
  P_ID   VARCHAR2(32),
  P_NAME VARCHAR2(64),
  P_TYPE VARCHAR2(32),
  P_DESC VARCHAR2(200),
  B_CODE VARCHAR2(100),
  LINK   VARCHAR2(200)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_WORK_PROCEDURE
  is '工作审核流程表';
comment on column HRM_V4_1.SP_WORK_PROCEDURE.ID
  is 'ID';
comment on column HRM_V4_1.SP_WORK_PROCEDURE.W_ID
  is '工作ID';
comment on column HRM_V4_1.SP_WORK_PROCEDURE.P_ID
  is '流程ID';
comment on column HRM_V4_1.SP_WORK_PROCEDURE.P_NAME
  is '流程名称';
comment on column HRM_V4_1.SP_WORK_PROCEDURE.P_TYPE
  is '流程类型（主流程；子流程）';
comment on column HRM_V4_1.SP_WORK_PROCEDURE.P_DESC
  is '流程描述';
comment on column HRM_V4_1.SP_WORK_PROCEDURE.B_CODE
  is '业务编码';
comment on column HRM_V4_1.SP_WORK_PROCEDURE.LINK
  is '审核页面链接';
alter table HRM_V4_1.SP_WORK_PROCEDURE
  add constraint PK_SP_WORK_PROCEDURE primary key (ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SP_WORK_PROCEDURE_BILL
prompt =====================================
prompt
create table HRM_V4_1.SP_WORK_PROCEDURE_BILL
(
  ID                VARCHAR2(32) not null,
  W_ID              VARCHAR2(32),
  P_ID              VARCHAR2(32),
  BILL_ID           VARCHAR2(32),
  BILL_TYPE         VARCHAR2(32),
  CLASSES_PRIVILEGE VARCHAR2(4000)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_WORK_PROCEDURE_BILL
  is '工作审核流程表单信息表';
comment on column HRM_V4_1.SP_WORK_PROCEDURE_BILL.ID
  is 'ID';
comment on column HRM_V4_1.SP_WORK_PROCEDURE_BILL.W_ID
  is '工作ID';
comment on column HRM_V4_1.SP_WORK_PROCEDURE_BILL.P_ID
  is '流程ID';
comment on column HRM_V4_1.SP_WORK_PROCEDURE_BILL.BILL_ID
  is '表单ID';
comment on column HRM_V4_1.SP_WORK_PROCEDURE_BILL.BILL_TYPE
  is '表单类型';
comment on column HRM_V4_1.SP_WORK_PROCEDURE_BILL.CLASSES_PRIVILEGE
  is '表单类权限字符串';
alter table HRM_V4_1.SP_WORK_PROCEDURE_BILL
  add constraint PK_SP_WORK_PROCEDURE_BILL primary key (ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SP_WORK_TASK
prompt ===========================
prompt
create table HRM_V4_1.SP_WORK_TASK
(
  ID                VARCHAR2(32) not null,
  W_ID              VARCHAR2(32),
  NODE_ID           VARCHAR2(32),
  TASK_NAME         VARCHAR2(64),
  TASK_TYPE         VARCHAR2(32),
  TASK_DESC         VARCHAR2(200),
  E_STATUS          VARCHAR2(32),
  OPERATOR          VARCHAR2(32),
  OPREATE_TIME      DATE,
  RESULT            VARCHAR2(1000),
  TASK_ID           VARCHAR2(32),
  TASK_CODE         VARCHAR2(32),
  IS_MUST           CHAR(1),
  BILL_ID           VARCHAR2(32),
  CLASS_ID          VARCHAR2(32),
  CLASS_MODE_TYPE   VARCHAR2(32),
  EXE_CONDITION     VARCHAR2(200),
  CLASSES_PRIVILEGE VARCHAR2(200),
  INSTANCE_ID       VARCHAR2(32),
  IS_AUTO           CHAR(1),
  DEC_CONDITION     VARCHAR2(500)
)
tablespace ZF
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table HRM_V4_1.SP_WORK_TASK
  is '工作审核任务表';
comment on column HRM_V4_1.SP_WORK_TASK.ID
  is 'ID';
comment on column HRM_V4_1.SP_WORK_TASK.W_ID
  is '工作ID';
comment on column HRM_V4_1.SP_WORK_TASK.NODE_ID
  is '节点ID';
comment on column HRM_V4_1.SP_WORK_TASK.TASK_NAME
  is '任务名称';
comment on column HRM_V4_1.SP_WORK_TASK.TASK_TYPE
  is '任务类型';
comment on column HRM_V4_1.SP_WORK_TASK.TASK_DESC
  is '任务描述';
comment on column HRM_V4_1.SP_WORK_TASK.E_STATUS
  is '执行状态（未执行；已执行）';
comment on column HRM_V4_1.SP_WORK_TASK.OPERATOR
  is '操作人';
comment on column HRM_V4_1.SP_WORK_TASK.OPREATE_TIME
  is '操作时间';
comment on column HRM_V4_1.SP_WORK_TASK.RESULT
  is '操作结果';
comment on column HRM_V4_1.SP_WORK_TASK.TASK_ID
  is '任务ID';
comment on column HRM_V4_1.SP_WORK_TASK.TASK_CODE
  is '任务编码';
comment on column HRM_V4_1.SP_WORK_TASK.IS_MUST
  is '是否必须执行（Y是；N否）';
comment on column HRM_V4_1.SP_WORK_TASK.BILL_ID
  is '表单ID';
comment on column HRM_V4_1.SP_WORK_TASK.CLASS_ID
  is '表单类ID';
comment on column HRM_V4_1.SP_WORK_TASK.CLASS_MODE_TYPE
  is '表单类模式类别';
comment on column HRM_V4_1.SP_WORK_TASK.EXE_CONDITION
  is '执行条件表达式';
comment on column HRM_V4_1.SP_WORK_TASK.CLASSES_PRIVILEGE
  is '表单类权限串';
comment on column HRM_V4_1.SP_WORK_TASK.INSTANCE_ID
  is '表单实例ID';
comment on column HRM_V4_1.SP_WORK_TASK.IS_AUTO
  is '是否自动执行（Y是；N否）';
comment on column HRM_V4_1.SP_WORK_TASK.DEC_CONDITION
  is '执行条件描述';
alter table HRM_V4_1.SP_WORK_TASK
  add constraint PK_SP_WORK_TASK primary key (ID)
  using index 
  tablespace ZF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );


spool off
