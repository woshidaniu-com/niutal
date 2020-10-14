

-- �޸�ʱ�䣺2013-08-23
-- �޸����ݣ��ڵ�����������ֶ�
alter table SP_NODE modify ROLE_ID VARCHAR2(4000);
alter table SP_NODE modify USER_ID VARCHAR2(4000);
alter table SP_NODE add role_name VARCHAR2(4000);
alter table SP_NODE add user_name VARCHAR2(4000);
-- Add comments to the columns 
comment on column SP_NODE.role_name
  is '��ɫ��������';
comment on column SP_NODE.user_name
  is '�û���������';

update sp_procedure t set t.belong_to_sys='XG-TWXT';
commit;
update sp_node t set t.user_id='admin',t.user_name='����Ա';
commit;



--�޸�ʱ�䣺2013-08-26
--�޸����ݣ��û���-�û�����Ĭ��Ϊ��ʦ
alter table niutal_XTGL_YHB modify YHLX default 'teacher';

--�������ݣ���ɾ��
delete from sp_business ;
commit;

--��ϵ��
-- Create table
create table SP_BUSINESS_CODE
(
  YWDM VARCHAR2(32) not null,
  YWMC VARCHAR2(64),
  YWDL VARCHAR2(32),
  TZLJ VARCHAR2(120)
) ;
-- Add comments to the table 
comment on table SP_BUSINESS_CODE
  is 'ҵ������';
-- Add comments to the columns 
comment on column SP_BUSINESS_CODE.YWDM
  is 'ҵ�����';
comment on column SP_BUSINESS_CODE.YWMC
  is 'ҵ������';
comment on column SP_BUSINESS_CODE.YWDL
  is 'ҵ�����';
comment on column SP_BUSINESS_CODE.TZLJ
  is '��ת·��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SP_BUSINESS_CODE
  add constraint PK_YWDM_YWDM primary key (YWDM) ;

delete from SP_BUSINESS_CODE;
commit;
insert into SP_BUSINESS_CODE (YWDM, YWMC, YWDL, TZLJ)
values ('HD_YJZZSH', '�Ժ�����', 'TW_HD_TYPE', '/twGrgl/TwHd_cxdshlb.html');
insert into SP_BUSINESS_CODE (YWDM, YWMC, YWDL, TZLJ)
values ('HD_XJZZSH', '�У�����', 'TW_HD_TYPE', '/twGrgl/TwHd_cxdshlb.html');
insert into SP_BUSINESS_CODE (YWDM, YWMC, YWDL, TZLJ)
values ('PY_TYPYSH', '��Ա�������', 'TW_PY_TYPE', '/twJcgl/PyDetailAudit_toPersonAuditPage.html');
insert into SP_BUSINESS_CODE (YWDM, YWMC, YWDL, TZLJ)
values ('PY_ZZPYSH', '����֧�������', 'TW_PY_TYPE', '/twJcgl/PyDetailAudit_toGroupAuditPage.html');
insert into SP_BUSINESS_CODE (YWDM, YWMC, YWDL, TZLJ)
values ('PY_STPYSH', '�����������', 'TW_PY_TYPE', '/twJcgl/PyDetail_cxPyDetailSocial.html');
insert into SP_BUSINESS_CODE (YWDM, YWMC, YWDL, TZLJ)
values ('PY_XSZZPYSH', 'ѧ����֯�������', 'TW_PY_TYPE', '/twJcgl/PyDetailAudit_toPersonAuditPage.html');
insert into SP_BUSINESS_CODE (YWDM, YWMC, YWDL, TZLJ)
values ('PY_TZBPYSH', '��֧���������', 'TW_PY_TYPE', '/twJcgl/PyDetailAudit_toClassAuditPage.html');
insert into SP_BUSINESS_CODE (YWDM, YWMC, YWDL, TZLJ)
values ('ST_NDXJSH', '����У��������', 'TW_ST_TYPE', '/twGrgl/TwStns_cxdshlb.html');
insert into SP_BUSINESS_CODE (YWDM, YWMC, YWDL, TZLJ)
values ('ST_NDYJSH', '����Ժ��������', 'TW_ST_TYPE', '/twGrgl/TwStns_cxdshlb.html');
insert into SP_BUSINESS_CODE (YWDM, YWMC, YWDL, TZLJ)
values ('ST_ZZSH', '������֯���', 'TW_ST_TYPE', '/twGrgl/TwSt_cxdshlb.html');
insert into SP_BUSINESS_CODE (YWDM, YWMC, YWDL, TZLJ)
values ('ST_ZXSH', '����ע�����', 'TW_ST_TYPE', '/twGrgl/TwSt_cxdshlb.html');
commit;


--Ϊ�������ж��û����ϻ��߽�ɫ���ϴ���
CREATE OR REPLACE type mytype as table of varchar2(4000);
/

create or replace function my_split(piv_str in varchar2, piv_delimiter in varchar2)
  --piv_str Ϊ�ַ�����piv_delimiter Ϊ�ָ���
  return mytype is
  j        int := 0;
  i        int := 1;
  len      int := 0;
  len1     int := 0;
  str      varchar2(4000);
  my_split mytype := mytype();
begin
  len  := length(piv_str);
  len1 := length(piv_delimiter);
  while j < len loop
    j := instr(piv_str, piv_delimiter, i);
    if j = 0 then
      j   := len;
      str := substr(piv_str, i);
      my_split.extend;
      my_split(my_split.count) := str;
      if i >= len then
        exit;
      end if;
    else
      str := substr(piv_str, i, j - i);
      i   := j + len1;
      my_split.extend;
      my_split(my_split.count) := str;
    end if;
  end loop;
  return my_split;
end my_split;
/

create or replace function array_compare(piv_str1 in varchar2,
                                         piv_str2 in varchar2)
  RETURN VARCHAR2 AS
  V_COMPARE number;
  v_nbr     number;
begin

  select count(*)
    into v_nbr
    from (select column_value as a1 from table(my_split(piv_str1, ','))) a,
         (select column_value as a2 from table(my_split(piv_str2, ','))) b
   where a.a1 = b.a2;

  if v_nbr > 0 then
    V_COMPARE := 1;
  else
    V_COMPARE := 0;
  end if;

  return V_COMPARE;
end array_compare;
/

alter table sp_auditing_log add operator_cn varchar2(200);
