--更新代办查询，去除流程状态异常的数据
CREATE OR REPLACE VIEW V_TASKLIST AS
 SELECT A.ID_ AS P_TASK_ID,
       A.PROC_INST_ID_ AS P_PROC_INST_ID,
			 A.EXECUTION_ID_ AS P_EXECUTION_ID_,
       A.TASK_DEF_KEY_ AS P_ACT_ID,
       A.NAME_ AS P_ACT_NAME,
       A.OWNER_ AS P_OWNER,
       A.ASSIGNEE_ AS P_ASSIGNEE,
       A.DELEGATION_ AS P_DELEGATION,
       A.DESCRIPTION_ AS P_DESCRIPTION,
       TO_CHAR(A.CREATE_TIME_, 'YYYY-MM-DD HH24:MI:SS') AS P_CREATE_TIME,
       TO_CHAR(A.DUE_DATE_,'YYYY-MM-DD HH24:MI:SS') AS P_DUE_DATE,
       I.USER_ID P_CANDIDATE
  FROM ACT_RU_TASK A
  INNER JOIN (SELECT DISTINCT * FROM (SELECT TASK_ID_, TO_CHAR(USER_ID_) USER_ID
                    FROM ACT_RU_IDENTITYLINK I, ACT_RU_TASK T
                      WHERE TASK_ID_ IS NOT NULL
                        AND USER_ID_ IS NOT NULL
                        AND I.TASK_ID_ = T.ID_
                        AND T.ASSIGNEE_ IS NULL
                        AND TYPE_ = 'candidate'
                     UNION
                     SELECT TASK_ID_, R.USER_ID_
                       FROM ACT_RU_IDENTITYLINK I,act_id_membership R,ACT_RU_TASK T
                      WHERE I.TASK_ID_ IS NOT NULL
                        AND I.GROUP_ID_ IS NOT NULL
                        AND I.TASK_ID_ = T.ID_
                        AND T.ASSIGNEE_ IS NULL
                        AND TYPE_ = 'candidate'
                        AND I.GROUP_ID_ = R.GROUP_ID_
            UNION
            SELECT
             T.ID_  TASK_ID_,
             TO_CHAR(T.ASSIGNEE_) USER_ID
             FROM ACT_RU_TASK T
             WHERE T.ASSIGNEE_ IS NOT NULL
            )U) I
    ON A.ID_ = I.TASK_ID_
    INNER JOIN ACT_RU_EXECUTION E
    on A.PROC_INST_ID_ = E.PROC_INST_ID_ AND A.EXECUTION_ID_ = E.ID_
    WHERE E.SUSPENSION_STATE_ = '1';