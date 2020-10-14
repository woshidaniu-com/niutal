--自定义导出增加基于角色控制导出展示列功能-平滑迁移脚本-------------start

--旧值幂等处理
DELETE niutal_GTGL_DCZDPZB_AUTH;

--找到所有((导出配置，字段)，角色) 的全部组合，赋值给niutal_GTGL_DCZDPZB_AUTH表
INSERT INTO niutal_GTGL_DCZDPZB_AUTH(DCCLBH,ZD,SQZ) SELECT DISTINCT A.DCCLBH,A.ZD,B.JSDM FROM niutal_GTGL_DCZDPZB A,niutal_XTGL_JSXXB B

COMMIT;

--自定义导出增加基于角色控制导出展示列功能-平滑迁移脚本-------------end