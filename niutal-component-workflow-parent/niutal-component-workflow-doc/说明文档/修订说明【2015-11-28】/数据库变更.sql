--修订脚本
alter table SP_AUDITING_LOG add node_id varchar2(32);
alter table SP_AUDITING_LOG add role_id varchar2(32);
alter table SP_AUDITING_LOG add user_id varchar2(32);
alter table SP_AUDITING_LOG add p_id varchar2(32);
alter table SP_AUDITING_LOG add node_bj varchar2(10);

comment on column SP_AUDITING_LOG.node_id is '节点ID';
comment on column SP_AUDITING_LOG.role_id is '角色ID';
comment on column SP_AUDITING_LOG.user_id is '用户ID';
comment on column SP_AUDITING_LOG.p_id is '流程ID';
comment on column SP_AUDITING_LOG.node_bj is '节点标记';

--2015-12-04 qph
alter table sp_auditing_log add r_node varchar2(32);
alter table sp_auditing_log add r_node_cn varchar2(64);

comment on column sp_auditing_log.r_node is '退回节点'; 
comment on column sp_auditing_log.r_node_cn is '退回节点名称'; 