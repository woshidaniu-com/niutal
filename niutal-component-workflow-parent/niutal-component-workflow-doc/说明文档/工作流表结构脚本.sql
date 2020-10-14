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
  is '���������־��';
comment on column HRM_V4_1.SP_AUDITING_LOG.LOG_ID
  is '��־ID';
comment on column HRM_V4_1.SP_AUDITING_LOG.LOG_TYPE
  is '��־����';
comment on column HRM_V4_1.SP_AUDITING_LOG.LOG_TIME
  is '��־ʱ��';
comment on column HRM_V4_1.SP_AUDITING_LOG.W_ID
  is '����ID';
comment on column HRM_V4_1.SP_AUDITING_LOG.B_TYPE
  is 'ҵ������';
comment on column HRM_V4_1.SP_AUDITING_LOG.O_TYPE
  is '��������';
comment on column HRM_V4_1.SP_AUDITING_LOG.O_STATUS
  is '����״̬';
comment on column HRM_V4_1.SP_AUDITING_LOG.O_RESULT
  is '�������';
comment on column HRM_V4_1.SP_AUDITING_LOG.O_CONTENT
  is '��������';
comment on column HRM_V4_1.SP_AUDITING_LOG.O_ROLE
  is '������ɫ';
comment on column HRM_V4_1.SP_AUDITING_LOG.OPERATOR
  is '������';
comment on column HRM_V4_1.SP_AUDITING_LOG.O_SUGGESTION
  is '�������';
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
  is '������̬�����ñ�';
comment on column HRM_V4_1.SP_BILL_CONFIG.ID
  is '���';
comment on column HRM_V4_1.SP_BILL_CONFIG.NAME
  is '������';
comment on column HRM_V4_1.SP_BILL_CONFIG.STATUS
  is '��״̬';
comment on column HRM_V4_1.SP_BILL_CONFIG.CONTENT
  is '����������';
comment on column HRM_V4_1.SP_BILL_CONFIG.CREATE_DATE
  is '��������';
comment on column HRM_V4_1.SP_BILL_CONFIG.ID_NAME
  is 'ʵ���洢��';
comment on column HRM_V4_1.SP_BILL_CONFIG.BILL_TYPE
  is '������';
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
  is '�����������¼����ñ�';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.ID
  is '���';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.BILL_CONFIG_ID
  is '�����ñ��';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.BILL_CLASS_ID
  is '������';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.EVENT_TYPE
  is '�¼�����';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.INFO_CLASS_ID
  is '��Ϣ����';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.EVENT_OP_TYPE
  is '�¼���������';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.WHERE_EXPRESSION
  is '�������ʽ';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.LOCAL_TABLE
  is '���ر�';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_EVENT_CONFIG.NAME
  is '�¼�����';
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
  is '�����������¼��������Ա�';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_PROPERTY.ID
  is '';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_PROPERTY.EVENT_CONFIG_ID
  is '�¼����ñ��';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_PROPERTY.LOCAL_PROPERTY_ID
  is '���ػ���Ϣ������';
comment on column HRM_V4_1.SP_BILL_DATAPUSH_PROPERTY.BILL_PROPERTY_ID
  is '�����Ա��';
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
  is '������̬��ʵ����';
comment on column HRM_V4_1.SP_BILL_INSTANCE.ID
  is '���';
comment on column HRM_V4_1.SP_BILL_INSTANCE.STATUS
  is '��״̬';
comment on column HRM_V4_1.SP_BILL_INSTANCE.CONTENT
  is '������';
comment on column HRM_V4_1.SP_BILL_INSTANCE.CREATE_DATE
  is '����ʱ��';
comment on column HRM_V4_1.SP_BILL_INSTANCE.COMMIT_DATE
  is '�ύʱ��';
comment on column HRM_V4_1.SP_BILL_INSTANCE.BILL_CONFIG_ID
  is '�����ñ��';
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
  is 'ҵ������';
comment on column HRM_V4_1.SP_BUSINESS.B_ID
  is 'ҵ��ID';
comment on column HRM_V4_1.SP_BUSINESS.B_NAME
  is 'ҵ������';
comment on column HRM_V4_1.SP_BUSINESS.B_TYPE
  is 'ҵ������';
comment on column HRM_V4_1.SP_BUSINESS.B_STATUS
  is 'ҵ��״̬';
comment on column HRM_V4_1.SP_BUSINESS.BELONG_TO_SYS
  is '����ϵͳ';
comment on column HRM_V4_1.SP_BUSINESS.B_CODE
  is 'ҵ�����';
comment on column HRM_V4_1.SP_BUSINESS.P_ID
  is '����ID';
comment on column HRM_V4_1.SP_BUSINESS.REL_DETAIL
  is '������ϸ';
comment on column HRM_V4_1.SP_BUSINESS.CLASSES_PRIVILEGE
  is '����Ȩ�޴�';
comment on column HRM_V4_1.SP_BUSINESS.BILL_ID
  is '��ID';
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
  is '�ڵ��';
comment on column HRM_V4_1.SP_NODE.NODE_ID
  is '�ڵ�ID';
comment on column HRM_V4_1.SP_NODE.P_ID
  is '����ID';
comment on column HRM_V4_1.SP_NODE.NODE_NAME
  is '�ڵ�����';
comment on column HRM_V4_1.SP_NODE.NODE_TYPE
  is '�ڵ����ͣ���㣻��������֧���ϲ����յ㣩';
comment on column HRM_V4_1.SP_NODE.NODE_STATUS
  is '�ڵ�״̬��1���ã�0�رգ�';
comment on column HRM_V4_1.SP_NODE.NODE_DESC
  is '�ڵ�����';
comment on column HRM_V4_1.SP_NODE.ROLE_ID
  is '��ɫID';
comment on column HRM_V4_1.SP_NODE.OUT_TYPE
  is '�������ͣ����У�ѡ��';
comment on column HRM_V4_1.SP_NODE.IN_TYPE
  is '�������ͣ�ȫ��ִ�У�����ִ�У�';
comment on column HRM_V4_1.SP_NODE.IS_AUTO
  is '�Ƿ��Զ���1�ǣ�0��';
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
  is '���̱�';
comment on column HRM_V4_1.SP_PROCEDURE.P_ID
  is '����ID';
comment on column HRM_V4_1.SP_PROCEDURE.P_NAME
  is '��������';
comment on column HRM_V4_1.SP_PROCEDURE.P_TYPE
  is '�������ͣ������̣������̣�';
comment on column HRM_V4_1.SP_PROCEDURE.P_STATUS
  is '����״̬';
comment on column HRM_V4_1.SP_PROCEDURE.P_DESC
  is '��������';
comment on column HRM_V4_1.SP_PROCEDURE.BELONG_TO_SYS
  is '����ϵͳ';
comment on column HRM_V4_1.SP_PROCEDURE.LINK
  is '���ҳ������';
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
  is '�ڵ����߱�';
comment on column HRM_V4_1.SP_LINE.LINE_ID
  is '����ID';
comment on column HRM_V4_1.SP_LINE.P_ID
  is '����ID';
comment on column HRM_V4_1.SP_LINE.U_NODE_ID
  is '�Ͻڵ�ID';
comment on column HRM_V4_1.SP_LINE.D_NODE_ID
  is '�½ڵ�ID';
comment on column HRM_V4_1.SP_LINE.EXPRESSION
  is '�������ʽ';
comment on column HRM_V4_1.SP_LINE.LINE_DESC
  is '��������';
comment on column HRM_V4_1.SP_LINE.IS_MUST_PASS
  is '�Ƿ���뾭����1�ǣ�0��';
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
  is '�ڵ㶯̬��������';
comment on column HRM_V4_1.SP_NODE_BILL.ID
  is 'ID';
comment on column HRM_V4_1.SP_NODE_BILL.NODE_ID
  is '�ڵ�ID';
comment on column HRM_V4_1.SP_NODE_BILL.BILL_ID
  is '��ID';
comment on column HRM_V4_1.SP_NODE_BILL.CLASSES_PRIVILEGE
  is '����Ȩ�޴�';
comment on column HRM_V4_1.SP_NODE_BILL.CLASS_ID
  is '����ID';
comment on column HRM_V4_1.SP_NODE_BILL.CLASS_MODE_TYPE
  is '����ģʽ����';
comment on column HRM_V4_1.SP_NODE_BILL.BILL_TYPE
  is '������';
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
  is '���̽ڵ��е������';
comment on column HRM_V4_1.SP_TASK.TASK_ID
  is '����ID';
comment on column HRM_V4_1.SP_TASK.TASK_NAME
  is '��������';
comment on column HRM_V4_1.SP_TASK.TASK_TYPE
  is '��������';
comment on column HRM_V4_1.SP_TASK.TASK_STATUS
  is '����״̬';
comment on column HRM_V4_1.SP_TASK.TASK_DESC
  is '��������';
comment on column HRM_V4_1.SP_TASK.TASK_CODE
  is '�������';
comment on column HRM_V4_1.SP_TASK.BELONG_TO_SYS
  is '����ϵͳ';
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
  is '����ڵ������';
comment on column HRM_V4_1.SP_NODE_TASK.TASK_ID
  is '����ID';
comment on column HRM_V4_1.SP_NODE_TASK.NODE_ID
  is '�ڵ�ID';
comment on column HRM_V4_1.SP_NODE_TASK.IS_MUST
  is '�Ƿ����ִ�У�Y�ǣ�N��';
comment on column HRM_V4_1.SP_NODE_TASK.IS_AUTO
  is '�Ƿ��Զ�ִ�У�Y�ǣ�N��';
comment on column HRM_V4_1.SP_NODE_TASK.BILL_ID
  is '��ID';
comment on column HRM_V4_1.SP_NODE_TASK.CLASS_ID
  is '����ID';
comment on column HRM_V4_1.SP_NODE_TASK.CLASS_MODE_TYPE
  is '����ģʽ���';
comment on column HRM_V4_1.SP_NODE_TASK.EXE_CONDITION
  is 'ִ���������ʽ';
comment on column HRM_V4_1.SP_NODE_TASK.CLASSES_PRIVILEGE
  is '����Ȩ�޴�';
comment on column HRM_V4_1.SP_NODE_TASK.DEC_CONDITION
  is 'ִ����������';
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
  is '���̱������';
comment on column HRM_V4_1.SP_PROCEDURE_BILL.ID
  is 'ID';
comment on column HRM_V4_1.SP_PROCEDURE_BILL.P_ID
  is '����ID';
comment on column HRM_V4_1.SP_PROCEDURE_BILL.BILL_ID
  is '��ID';
comment on column HRM_V4_1.SP_PROCEDURE_BILL.BILL_TYPE
  is '������';
comment on column HRM_V4_1.SP_PROCEDURE_BILL.CLASSES_PRIVILEGE
  is '����Ȩ�޴�';
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
  is '������˽ڵ����߱�';
comment on column HRM_V4_1.SP_WORK_LINE.ID
  is 'ID';
comment on column HRM_V4_1.SP_WORK_LINE.W_ID
  is '����ID';
comment on column HRM_V4_1.SP_WORK_LINE.P_ID
  is '����ID';
comment on column HRM_V4_1.SP_WORK_LINE.U_NODE_ID
  is '�Ͻڵ�ID';
comment on column HRM_V4_1.SP_WORK_LINE.D_NODE_ID
  is '�½ڵ�ID';
comment on column HRM_V4_1.SP_WORK_LINE.EXPRESSION
  is '�������ʽ';
comment on column HRM_V4_1.SP_WORK_LINE.LINE_DESC
  is '��������';
comment on column HRM_V4_1.SP_WORK_LINE.IS_MUST_PASS
  is '�Ƿ���뾭����1�ǣ�0��';
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
  is '������˽ڵ��';
comment on column HRM_V4_1.SP_WORK_NODE.ID
  is 'ID';
comment on column HRM_V4_1.SP_WORK_NODE.W_ID
  is '����ID';
comment on column HRM_V4_1.SP_WORK_NODE.NODE_ID
  is '�ڵ�ID';
comment on column HRM_V4_1.SP_WORK_NODE.P_ID
  is '����ID';
comment on column HRM_V4_1.SP_WORK_NODE.NODE_NAME
  is '�ڵ�����';
comment on column HRM_V4_1.SP_WORK_NODE.NODE_TYPE
  is '�ڵ����ͣ���㣻��������֧���ϲ����յ㣩';
comment on column HRM_V4_1.SP_WORK_NODE.NODE_DESC
  is '�ڵ�����';
comment on column HRM_V4_1.SP_WORK_NODE.ROLE_ID
  is '��ɫID';
comment on column HRM_V4_1.SP_WORK_NODE.OUT_TYPE
  is '�������ͣ����У�ѡ��';
comment on column HRM_V4_1.SP_WORK_NODE.IN_TYPE
  is '�������ͣ�ȫ��ִ�У�����ִ�У�';
comment on column HRM_V4_1.SP_WORK_NODE.E_STATUS
  is 'ִ��״̬���رգ���ִ�У���ִ�У�';
comment on column HRM_V4_1.SP_WORK_NODE.STATUS
  is '���״̬������ˣ�ͨ������ͨ����';
comment on column HRM_V4_1.SP_WORK_NODE.AUDITOR_ID
  is '�����';
comment on column HRM_V4_1.SP_WORK_NODE.AUDIT_TIME
  is '���ʱ��';
comment on column HRM_V4_1.SP_WORK_NODE.AUDIT_RESULT
  is '��˽��';
comment on column HRM_V4_1.SP_WORK_NODE.SUGGESTION
  is '������';
comment on column HRM_V4_1.SP_WORK_NODE.IS_AUTO
  is '�Ƿ��Զ���1�ǣ�0��';
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
  is '������˽ڵ㶯̬��������';
comment on column HRM_V4_1.SP_WORK_NODE_BILL.ID
  is 'ID';
comment on column HRM_V4_1.SP_WORK_NODE_BILL.W_ID
  is '����ID';
comment on column HRM_V4_1.SP_WORK_NODE_BILL.NODE_ID
  is '�ڵ�ID';
comment on column HRM_V4_1.SP_WORK_NODE_BILL.BILL_ID
  is '��ID';
comment on column HRM_V4_1.SP_WORK_NODE_BILL.CLASSES_PRIVILEGE
  is '����Ȩ�޴�';
comment on column HRM_V4_1.SP_WORK_NODE_BILL.CLASS_ID
  is '����ID';
comment on column HRM_V4_1.SP_WORK_NODE_BILL.CLASS_MODE_TYPE
  is '����ģʽ����';
comment on column HRM_V4_1.SP_WORK_NODE_BILL.BILL_TYPE
  is '������';
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
  is '����������̱�';
comment on column HRM_V4_1.SP_WORK_PROCEDURE.ID
  is 'ID';
comment on column HRM_V4_1.SP_WORK_PROCEDURE.W_ID
  is '����ID';
comment on column HRM_V4_1.SP_WORK_PROCEDURE.P_ID
  is '����ID';
comment on column HRM_V4_1.SP_WORK_PROCEDURE.P_NAME
  is '��������';
comment on column HRM_V4_1.SP_WORK_PROCEDURE.P_TYPE
  is '�������ͣ������̣������̣�';
comment on column HRM_V4_1.SP_WORK_PROCEDURE.P_DESC
  is '��������';
comment on column HRM_V4_1.SP_WORK_PROCEDURE.B_CODE
  is 'ҵ�����';
comment on column HRM_V4_1.SP_WORK_PROCEDURE.LINK
  is '���ҳ������';
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
  is '����������̱���Ϣ��';
comment on column HRM_V4_1.SP_WORK_PROCEDURE_BILL.ID
  is 'ID';
comment on column HRM_V4_1.SP_WORK_PROCEDURE_BILL.W_ID
  is '����ID';
comment on column HRM_V4_1.SP_WORK_PROCEDURE_BILL.P_ID
  is '����ID';
comment on column HRM_V4_1.SP_WORK_PROCEDURE_BILL.BILL_ID
  is '��ID';
comment on column HRM_V4_1.SP_WORK_PROCEDURE_BILL.BILL_TYPE
  is '������';
comment on column HRM_V4_1.SP_WORK_PROCEDURE_BILL.CLASSES_PRIVILEGE
  is '����Ȩ���ַ���';
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
  is '������������';
comment on column HRM_V4_1.SP_WORK_TASK.ID
  is 'ID';
comment on column HRM_V4_1.SP_WORK_TASK.W_ID
  is '����ID';
comment on column HRM_V4_1.SP_WORK_TASK.NODE_ID
  is '�ڵ�ID';
comment on column HRM_V4_1.SP_WORK_TASK.TASK_NAME
  is '��������';
comment on column HRM_V4_1.SP_WORK_TASK.TASK_TYPE
  is '��������';
comment on column HRM_V4_1.SP_WORK_TASK.TASK_DESC
  is '��������';
comment on column HRM_V4_1.SP_WORK_TASK.E_STATUS
  is 'ִ��״̬��δִ�У���ִ�У�';
comment on column HRM_V4_1.SP_WORK_TASK.OPERATOR
  is '������';
comment on column HRM_V4_1.SP_WORK_TASK.OPREATE_TIME
  is '����ʱ��';
comment on column HRM_V4_1.SP_WORK_TASK.RESULT
  is '�������';
comment on column HRM_V4_1.SP_WORK_TASK.TASK_ID
  is '����ID';
comment on column HRM_V4_1.SP_WORK_TASK.TASK_CODE
  is '�������';
comment on column HRM_V4_1.SP_WORK_TASK.IS_MUST
  is '�Ƿ����ִ�У�Y�ǣ�N��';
comment on column HRM_V4_1.SP_WORK_TASK.BILL_ID
  is '��ID';
comment on column HRM_V4_1.SP_WORK_TASK.CLASS_ID
  is '����ID';
comment on column HRM_V4_1.SP_WORK_TASK.CLASS_MODE_TYPE
  is '����ģʽ���';
comment on column HRM_V4_1.SP_WORK_TASK.EXE_CONDITION
  is 'ִ���������ʽ';
comment on column HRM_V4_1.SP_WORK_TASK.CLASSES_PRIVILEGE
  is '����Ȩ�޴�';
comment on column HRM_V4_1.SP_WORK_TASK.INSTANCE_ID
  is '��ʵ��ID';
comment on column HRM_V4_1.SP_WORK_TASK.IS_AUTO
  is '�Ƿ��Զ�ִ�У�Y�ǣ�N��';
comment on column HRM_V4_1.SP_WORK_TASK.DEC_CONDITION
  is 'ִ����������';
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
