
/*功能菜单初始化*/

update niutal_xtgl_jsgnmkdmb t set t.sfxs = '1' where t.sfxs is null;         

/*机构信息维护*/
update niutal_xtgl_jsgnmkdmb t set t.dyym = '/xtgl/jgxx_cxJgxx.html' where t.gnmkdm = 'N010515';
/*专业信息维护*/
update niutal_xtgl_jsgnmkdmb t set t.dyym = '/xtgl/zyxx_cxZyxx.html' where t.gnmkdm = 'N010525';
/*学信专业维护*/
update niutal_xtgl_jsgnmkdmb t set t.dyym = '/xtgl/xxzy_cxXxzyxx.html' where t.gnmkdm = 'N010526';
/*校区信息维护*/
update niutal_xtgl_jsgnmkdmb t set t.dyym = '/xtgl/xqxx_cxXqxx.html' where t.gnmkdm = 'N010527';
 

delete niutal_xtgl_jstopmkmcb;

insert into niutal_xtgl_jstopmkmcb (JSTOPMKMCB_ID, JSDM, MKMC, XSXH, YWMKMC)
values ('01', '06', '报名申请', 1, 'Apply for Signing up');

insert into niutal_xtgl_jstopmkmcb (JSTOPMKMCB_ID, JSDM, MKMC, XSXH, YWMKMC)
values ('02', '06', '信息维护', 2, 'Information Maintenance');

insert into niutal_xtgl_jstopmkmcb (JSTOPMKMCB_ID, JSDM, MKMC, XSXH, YWMKMC)
values ('03', '06', '选课', 3, 'Curricula-Variable');

insert into niutal_xtgl_jstopmkmcb (JSTOPMKMCB_ID, JSDM, MKMC, XSXH, YWMKMC)
values ('04', '06', '信息查询', 4, 'Information Query');

insert into niutal_xtgl_jstopmkmcb (JSTOPMKMCB_ID, JSDM, MKMC, XSXH, YWMKMC)
values ('05', '06', '教学评价', 5, '');

insert into niutal_xtgl_jstopmkmcb (JSTOPMKMCB_ID, JSDM, MKMC, XSXH, YWMKMC)
values ('61', '05', '申请', 1, 'Application');

insert into niutal_xtgl_jstopmkmcb (JSTOPMKMCB_ID, JSDM, MKMC, XSXH, YWMKMC)
values ('62', '05', '信息维护', 2, 'Information Maintenance');

insert into niutal_xtgl_jstopmkmcb (JSTOPMKMCB_ID, JSDM, MKMC, XSXH, YWMKMC)
values ('63', '05', '成绩', 3, 'Scores');

insert into niutal_xtgl_jstopmkmcb (JSTOPMKMCB_ID, JSDM, MKMC, XSXH, YWMKMC)
values ('64', '05', '信息查询', 4, 'Information Query');

insert into niutal_xtgl_jstopmkmcb (JSTOPMKMCB_ID, JSDM, MKMC, XSXH, YWMKMC)
values ('65', '05', '教学评价', 5, 'Teaching Evaluation');


/*数据范围初始化*/
 
delete niutal_xtgl_sjfwdxb;

insert into niutal_xtgl_sjfwdxb (SJFWDX_ID, BM, ZDDM, ZDMC, ZWMC, XSSX, SFQY, ZDCX, KZDX)
values ('6', 'niutal_xtgl_njdmb', 'njdm_id', 'njmc', '年级', '2', '0', '1', 'xs');

insert into niutal_xtgl_sjfwdxb (SJFWDX_ID, BM, ZDDM, ZDMC, ZWMC, XSSX, SFQY, ZDCX, KZDX)
values ('1', 'SCHOOL', '', '', '全校', '1', '1', '0', 'xs');

insert into niutal_xtgl_sjfwdxb (SJFWDX_ID, BM, ZDDM, ZDMC, ZWMC, XSSX, SFQY, ZDCX, KZDX)
values ('2', 'niutal_xtgl_jgdmb', 'jg_id', 'jgmc', '部门', '5', '1', '1', 'xs');

insert into niutal_xtgl_sjfwdxb (SJFWDX_ID, BM, ZDDM, ZDMC, ZWMC, XSSX, SFQY, ZDCX, KZDX)
values ('3', 'niutal_xtgl_zydmb', 'zyh_id', 'zymc', '专业', '6', '1', '0', 'xs');

insert into niutal_xtgl_sjfwdxb (SJFWDX_ID, BM, ZDDM, ZDMC, ZWMC, XSSX, SFQY, ZDCX, KZDX)
values ('4', 'niutal_xtgl_bjdmb', 'bh', 'bj', '班级', '7', '1', '0', 'xs');

insert into niutal_xtgl_sjfwdxb (SJFWDX_ID, BM, ZDDM, ZDMC, ZWMC, XSSX, SFQY, ZDCX, KZDX)
values ('7', 'view_xjgl_xsjbxxb', 'xh_id', 'xm', '学生', '8', '1', '0', 'xs');

insert into niutal_xtgl_sjfwdxb (SJFWDX_ID, BM, ZDDM, ZDMC, ZWMC, XSSX, SFQY, ZDCX, KZDX)
values ('8', 'niutal_xtgl_xqdmb', 'xqh', 'xqmc', '校区', '4', '1', '1', 'xs');

insert into niutal_xtgl_sjfwdxb (SJFWDX_ID, BM, ZDDM, ZDMC, ZWMC, XSSX, SFQY, ZDCX, KZDX)
values ('9', 'view_xtgl_kkbm', 'kkbm_id', 'kkbmmc', '开课部门', '1', '1', '1', 'kc');

insert into niutal_xtgl_sjfwdxb (SJFWDX_ID, BM, ZDDM, ZDMC, ZWMC, XSSX, SFQY, ZDCX, KZDX)
values ('10', 'view_xjgl_xsxzdmb', 'xsxzm', 'xsxzmc', '学生性质', '9', '1', '1', 'xs');

insert into niutal_xtgl_sjfwdxb (SJFWDX_ID, BM, ZDDM, ZDMC, ZWMC, XSSX, SFQY, ZDCX, KZDX)
values ('11', 'view_xtgl_jsbm', 'jsbm_id', 'jsbmmc', '教师部门', '10', '1', '1', 'js');

insert into niutal_xtgl_sjfwdxb (SJFWDX_ID, BM, ZDDM, ZDMC, ZWMC, XSSX, SFQY, ZDCX, KZDX)
values ('12', 'view_xtgl_cdbm', 'cdbm_id', 'cdbmmc', '场地托管部门', '1', '1', '1', 'cd'); 


/*系统设置初始化*/

/*防恶意请求频繁刷新过滤器参数配置*/
delete niutal_xtgl_xtszb t where t.zdm in('refresh_on','refresh_interval','refresh_count');
/*指定【判断为恶意刷新】过滤器是否开启，默认： 1*/
insert into niutal_xtgl_xtszb (XTSZ_ID, ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('refresh_on', 'refresh_on', '0', 'GGMK', '【防恶意刷新】过滤器是否开启', '如果选择是，则请求会被【判断为恶意刷新】防恶意刷新过滤器拦截；判断访问频率', '', '{required:true}', 3, 'basedata:isStart');
/*指定【判断为恶意刷新】的周期时间；单位秒，默认： 10s*/
insert into niutal_xtgl_xtszb (XTSZ_ID, ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('refresh_interval', 'refresh_interval', '25', 'GGMK', '认定恶意刷新【周期时间】', '单位秒，默认： 10s，周期越短越容易被认定为恶意刷新，请谨慎设置!', '', '{required:true,PositiveInteger:true,min:10}', 2, '');
/*指定【判断为恶意刷新】周期内刷新的次数【注意因为框架中主页数据查询URL根据doType参数重复利用，这里至少设置3次】，默认： 100*/
insert into niutal_xtgl_xtszb (XTSZ_ID, ZDM, ZDZ, SSMK, ZS, BZ, SSGNMKDM, ZDZYQ, ZDLX, ZDLY)
values ('refresh_count', 'refresh_count', '100', 'GGMK', '认定恶意刷新【同一URL周期内刷新的次数】', '因为框架中主页数据查询URL根据doType参数重复利用，这里至少设置3次，默认： 100', '', '{required:true,PositiveInteger:true,min:3}', 2, '');
  
/*基本角色初始化*/

insert into niutal_xtgl_jsxxb (JSDM, JSMC, JSSM, SFKSC, GWJBDM, SFEJSQ, GNMKDM, FJSDM, JSJB, JSLXDM, JGH, JSGYBJ, ZGH)
values ('xs', '学生', '学生角色', '0', '', '0', '', '', '1', '02', '20063586', '1', '');

insert into niutal_xtgl_jsxxb (JSDM, JSMC, JSSM, SFKSC, GWJBDM, SFEJSQ, GNMKDM, FJSDM, JSJB, JSLXDM, JGH, JSGYBJ, ZGH)
values ('admin', '超级管理员', '超级管理员', '0', '', '1', '', '', '1', '01', '', '', '');

insert into niutal_xtgl_jsxxb (JSDM, JSMC, JSSM, SFKSC, GWJBDM, SFEJSQ, GNMKDM, FJSDM, JSJB, JSLXDM, JGH, JSGYBJ, ZGH)
values ('js', '教师', '任课教师角色', '0', '', '1', '', '', '1', '', '20063586', '1', '');


/*基础数据类型初始化*/

delete niutal_xtgl_jcsjlxb;

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0016', '学生类别代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0001', '学期代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0002', '审核状态代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0003', '审核结果代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0004', '是否对照表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0005', '证件类型代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0006', '性别代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0007', '民族代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0008', '政治面貌代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0009', '血型代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0010', '健康状况代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0011', '婚姻状况代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0012', '港澳台侨外代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0013', '高考科目代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0014', '培养层次代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0015', '培养方式代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0017', '学生来源代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0018', '处分名称代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0019', '奖励方式代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0020', '奖励级别代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0021', '奖励等级代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0022', '学籍异动原因代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0023', '家庭成员称呼表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0026', '学生获奖类别代码', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0027', '学科门类代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0029', '违纪类别代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0031', '授课方式代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0032', '教学模式', 'yw');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0033', '考试形式', 'yw');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0034', '考试性质', 'yw');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0035', '考试方式', 'yw');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0036', '星期代码表', 'yw');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0037', '考核方式代码表', 'yw');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0025', '角色类型代码表', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0024', '奖励类型', 'xt');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0028', '学籍状态代码表', 'yw');

insert into niutal_xtgl_jcsjlxb (LXDM, LXMC, XTJB)
values ('0038', '园区代码表', 'yw');

/*基础数据初始化*/

delete niutal_xtgl_jcsjb;

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0001', '16', '3', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0001', '3', '1', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0001', '12', '2', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0002', '1', '待审核', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0002', '2', '审核中', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0002', '3', '已审核', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0003', '1', '审核通过', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0003', '2', '审核未通过', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0003', '3', '审核退回', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0004', 'T', '是', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0004', 'F', '否', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0005', '1', '居民身份证', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0005', '2', '军官证', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0005', '3', '士兵证', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0005', '4', '文职干部证', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0005', '5', '部队离退休证', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0005', '6', '香港特区护照/身份证明', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0005', '7', '澳门特区护照/身份证明', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0005', '8', '台湾居民来往大陆通行证', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0005', '9', '境外永久居住证', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0005', 'A', '护照', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0005', 'B', '户口簿', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0005', 'Z', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0006', '0', '未知的性别', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0006', '1', '男', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0006', '2', '女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0006', '9', '未说明的性别', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '01', '汉族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '02', '蒙古族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '03', '回族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '04', '藏族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '05', '维吾尔族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '06', '苗族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '07', '彝族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '08', '壮族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '09', '布依族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '10', '朝鲜族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '11', '满族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '12', '侗族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '13', '瑶族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '14', '白族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '15', '土家族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '16', '哈尼族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '17', '哈萨克族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '18', '傣族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '19', '黎族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '20', '傈僳族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '21', '佤族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '22', '畲族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '23', '高山族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '24', '拉祜族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '25', '水族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '26', '东乡族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '27', '纳西族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '28', '景颇族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '29', '柯尔克孜族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '30', '土族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '31', '达斡尔族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '32', '仫佬族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '33', '羌族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '34', '布朗族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '35', '撒拉族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '36', '毛难族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '37', '仡佬族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '38', '锡伯族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '39', '阿昌族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '40', '普米族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '41', '塔吉克族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '42', '怒族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '43', '乌孜别克族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '44', '俄罗斯族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '45', '鄂温克族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '46', '德昂族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '47', '保安族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '48', '裕固族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '49', '京族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '50', '塔塔尔族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '51', '独龙族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '52', '鄂伦春族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '53', '赫哲族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '54', '门巴族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '55', '珞巴族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '56', '基诺族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '81', '穿青人族', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '97', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '98', '外国血统中国籍人士', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0007', '99', '未说明', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0008', '01', '中国共产党党员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0008', '02', '中国共产党预备党员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0008', '03', '中国共产主义青年团团员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0008', '04', '中国国民党革命委员会会员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0008', '05', '中国民主同盟盟员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0008', '06', '中国民主建国会会员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0008', '07', '中国民主促进会会员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0008', '08', '中国农工民主党党员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0008', '09', '中国致公党党员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0008', '10', '九三学社社员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0008', '11', '台湾民主自治同盟盟员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0008', '12', '无党派民主人士', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0008', '13', '群众', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0009', '0', '未知血型', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0009', '1', 'A血型', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0009', '2', 'B血型', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0009', '3', 'AB血型', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0009', '4', 'O血型', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0009', '5', 'RH阳性血型', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0009', '6', 'RH阴性血型', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0009', '7', 'HLA血型', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0009', '8', '未定血型', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0009', '9', '其他血型', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0010', '1', '健康或良好', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0010', '2', '一般或较弱', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0010', '3', '有慢性病', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0010', '6', '残疾', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0011', '10', '未婚', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0011', '20', '已婚', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0011', '21', '初婚', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0011', '22', '再婚', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0011', '23', '复婚', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0011', '30', '丧偶', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0011', '40', '离婚', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0011', '90', '未说明的婚姻', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0012', '01', '香港同胞', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0012', '03', '澳门同胞', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0012', '04', '澳门同胞亲属', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0012', '05', '台湾同胞', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0012', '06', '台湾同胞亲属', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0012', '11', '华侨', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0012', '12', '侨眷', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0012', '13', '归侨', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0012', '14', '归侨子女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0012', '21', '归国留学人员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0012', '31', '非华裔中国人', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0012', '41', '外籍华裔人', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0012', '51', '外国人', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0012', '99', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0013', '01', '语文', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0013', '02', '数学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0013', '03', '外语', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0013', '04', '物理', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0013', '05', '化学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0013', '06', '生物', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0013', '07', '政治', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0013', '08', '历史', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0013', '09', '地理', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0013', '10', '综合', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0013', '11', '文科综合', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0013', '12', '理科综合', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0013', '99', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0014', '1', '博士', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0014', '2', '硕士', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0014', '3', '本科', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0014', '4', '专科', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0014', '9', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0015', '10', '国家任务', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0015', '11', '非定向', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0015', '12', '定向', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0015', '20', '非国家任务', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0015', '21', '自筹经费', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0015', '22', '委托培养', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0015', '23', '联合培养', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0015', '30', '留学生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0015', '31', '国际组织资助', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0015', '32', '中国政府资助', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0015', '33', '本国政府资助', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0015', '34', '学校间交换', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0015', '35', '自费留学生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0015', '99', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0016', '411', '普通专科生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0016', '412', '成人专科生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0016', '413', '网络专科生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0016', '421', '普通本科生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0016', '422', '成人本科生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0016', '423', '网络本科生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0016', '431', '硕士研究生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0016', '432', '博士研究生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0016', '441', '学士学位学生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0016', '442', '硕士学位学生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0016', '443', '博士学位学生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '000', '研究生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '001', '科技人员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '002', '高校教师', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '003', '中学教师', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '004', '其他在职', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '005', '应届本科', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '006', '成人应届本科', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '007', '网络教育应届本科', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '009', '其他人员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '100', '普通高校本专科/中等职业学校', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '101', '城镇应届', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '102', '农村应届', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '103', '城镇往届', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '104', '农村往届', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '105', '工人', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '106', '干部', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '107', '复退军人', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '108', '现役军人', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '109', '香港生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '110', '澳门生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '111', '台湾生', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '112', '归国华侨/港澳台侨', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '113', '留学生/外籍', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '199', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '200', '成人高校', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '201', '国家机关、党群组织、企业、事业单位负责人', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '209', '军人', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '211', '专业技术人员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '231', '办事人员和有关人员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '241', '商业、服务业人员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '251', '农、林、牧、渔、水利业生产人员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '261', '生产、运输设备操作人员及有关人员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0017', '299', '不便分类的其他从业人员', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0018', '1', '警告', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0018', '2', '严重警告', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0018', '3', '记过', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0018', '4', '留校察看', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0018', '6', '开除学籍', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0018', '9', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0019', '1', '奖状', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0019', '2', '荣誉称号', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0019', '3', '奖金', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0019', '4', '实物', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0019', '9', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '10', '国家级', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '20', '省、部委级', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '21', '教育部', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '22', '中央其他部委', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '23', '省(自治区、直辖市)级', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '30', '省部门级、地(市、州)级', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '31', '省级教育行政部门', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '32', '省级其他部门', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '33', '地(市、州)级', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '40', '地(市、州)部门级、县(区、旗)级', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '41', '地级教育行政部门', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '42', '地级其他部门', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '43', '县级', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '50', '县部门级、乡(镇)级', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '51', '县级教育行政部门', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '52', '县级其他部门', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '53', '乡(镇)级', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '60', '学校级', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '70', '国际', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0020', '99', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0021', '1', '特等', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0021', '2', '一等', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0021', '3', '二等', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0021', '4', '三等', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0021', '5', '四等', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0021', '6', '未评等级', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0021', '9', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '01', '成绩优秀', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '10', '疾病', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '11', '精神疾病', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '12', '传染疾病', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '19', '其他疾病', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '21', '自动退学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '22', '成绩太差', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '23', '触犯刑法', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '24', '休学创业', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '25', '停学实践（求职）', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '26', '家长病重', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '27', '贫困', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '28', '非留学出国（境）', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '29', '自费出国退学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '30', '自费留学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '31', '休学期满未办理复学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '32', '恶意欠缴学费', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '33', '超过最长学习期限未完成学业', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '34', '应征入伍', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '39', '其他原因退学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '48', '死亡', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '40', '事故灾难致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '41', '溺水死亡', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '42', '交通事故致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '43', '拥挤踩踏致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '44', '房屋倒塌致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '45', '坠楼坠崖致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '46', '中毒致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '47', '爆炸致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '50', '社会安全事件致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '51', '打架斗殴致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '52', '校园伤害致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '53', '刑事案件致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '54', '火灾致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '60', '自然灾害致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '61', '山体滑坡致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '62', '泥石流致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '63', '洪水致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '64', '地震致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '65', '暴雨致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '66', '冰雹致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '67', '雪灾致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '68', '龙卷风致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '70', '因病死亡', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '71', '传染病致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '72', '猝死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '79', '其他疾病致死', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '81', '自杀死亡', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '89', '其他原因死亡', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0022', '99', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '01', '本人', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '02', '户主', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '10', '配偶', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '11', '夫', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '12', '妻', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '20', '子', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '21', '独生子', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '22', '长子', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '23', '次子', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '24', '三子', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '25', '四子', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '26', '五子', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '27', '养子或继子', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '28', '女婿', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '29', '其他子', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '30', '女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '31', '独生女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '32', '长女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '33', '二女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '34', '三女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '35', '四女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '36', '五女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '37', '养女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '38', '儿媳', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '39', '其他女儿', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '40', '孙子、孙女或外孙子、外孙女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '41', '孙子', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '42', '孙女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '43', '外孙子', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '44', '外孙女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '45', '孙媳妇或外孙媳妇', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '46', '孙女婿或外孙女婿', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '47', '曾孙子或外曾孙子', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '48', '曾孙女或外曾孙女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '49', '其他孙子、孙女或外孙子、外孙女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '50', '父母', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '51', '父亲', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '52', '母亲', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '53', '公公', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '54', '婆婆', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '55', '岳父', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '56', '岳母', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '57', '继父或养父', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '58', '继母或养母', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '59', '其他父母关系', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '60', '祖父母或外祖父母', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '61', '祖父', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '62', '祖母', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '63', '外祖父', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '64', '外祖母', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '65', '配偶的祖父母或外祖父母', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '66', '曾祖父', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '67', '曾祖母', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '68', '配偶的曾祖父母', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '69', '其他祖父母或外祖父母关系', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '70', '兄弟姐妹', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '71', '兄', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '72', '嫂', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '73', '弟', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '74', '弟媳', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '75', '姐姐', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '76', '姐夫', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '77', '妹妹', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '78', '妹夫', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '79', '其他兄弟姐妹', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '80/99', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '81', '伯父', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '82', '伯母', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '83', '叔父', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '84', '婶母', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '85', '舅父', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '86', '舅母', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '87', '姨父', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '88', '姨母', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '89', '姑父', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '90', '姑母', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '91', '堂兄弟、堂姐妹', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '92', '表兄弟、表姐妹', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '93', '侄子', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '94', '侄女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '95', '外甥', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '96', '外甥女', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '97', '其他亲属', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0023', '99', '非亲属', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0024', '3', '个人单项', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0026', '1', '学科获奖', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0026', '2', '科技获奖', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0026', '3', '文艺获奖', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0026', '4', '体育获奖', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0026', '5', '综合获奖', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0026', '6', '社会工作获奖', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0026', '7', '公益事业获奖', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0026', '9', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0027', '01', '哲学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0027', '02', '经济学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0027', '03', '法学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0027', '04', '教育学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0027', '05', '文学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0027', '06', '历史学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0027', '07', '理学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0027', '08', '工学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0027', '09', '农学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0027', '10', '医学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0027', '11', '军事学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0027', '12', '管理学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0027', '13', '艺术类', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0029', '01', '触犯刑法', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0029', '02', '违反治安管理条例', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0029', '03', '学业违纪(考试作弊、旷课)', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0029', '04', '涂改、伪造证件或证明', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0029', '05', '侵犯他人人身权利', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0029', '06', '侵犯公私财物', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0029', '07', '侵犯学校权益', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0029', '08', '危害公共安全', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0029', '09', '扰乱公共场所秩序', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0029', '10', '违反社团管理规定', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0029', '11', '违反课外活动规定', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0029', '12', '违反宿舍管理规定', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0029', '13', '违反网络管理规定', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0029', '99', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0031', '1', '面授讲课', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0031', '2', '辅导', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0031', '3', '录像讲课', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0031', '4', '网络教学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0031', '5', '实验', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0031', '6', '实习', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0031', '9', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0032', '1', '双语教学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0032', '2', '中文教学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0033', '1', '统一', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0033', '2', '随堂', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0034', '01', '正常考试', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0034', '02', '缓考', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0034', '03', '旷考', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0034', '11', '补考一', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0034', '12', '补考二', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0034', '16', '重修', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0034', '17', '免修', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0034', '21', '结业后回校补考', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0034', '41', '国家级考试', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0034', '99', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0035', '1', '笔试', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0035', '2', '口试', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0035', '3', '面试', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0035', '4', '操作', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0035', '5', '机考', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0035', '6', '大作业', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0035', '7', '实验报告', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0035', '9', '其他', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0036', '1', '星期一', '0', 'Mon');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0036', '2', '星期二', '0', 'Tue');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0036', '3', '星期三', '0', 'Wed');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0036', '4', '星期四', '0', 'Thu');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0036', '5', '星期五', '0', 'Fri');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0036', '6', '星期六', '0', 'Sat');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0036', '7', '星期日', '0', 'Sun');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0037', '1', '考试', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0037', '2', '考查', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0024', '1', '集体', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0025', '01', '校级', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0025', '02', '院级', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0028', '01', '在读', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0028', '02', '休学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0028', '03', '退学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0028', '04', '停学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0028', '05', '复学', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0028', '06', '流失', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0028', '07', '毕业', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0028', '08', '结业', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0028', '09', '肄业', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0028', '10', '转学(转出)', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0028', '11', '死亡', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0028', '12', '保留入学资格', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0028', '13', '公派出国', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0028', '14', '开除', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0028', '15', '下落不明', '0', '');

insert into niutal_xtgl_jcsjb (LX, DM, MC, SFMR, YWMC)
values ('0028', '99', '其他', '0', '');




insert into niutal_xtgl_yhb (XM, DZYX, SFQY, SJLY, JGDM, YHLX, YHM, KL, SJHM)
values ('admin', '', '1', 'jw_jg_jzgxxb', '321', 'teacher', 'admin', '{MD5}wYOkMucG7a9A/0G1sqVI2Q==', '');

insert into niutal_xtgl_yhjsb (JSDM, YHM)
values ('admin', 'admin');


insert into JW_JCDML_XTNZB (XTNZ_ID, ZDM, ZDZ, ZS, BZ)
values ('JGPXZD', 'JGPXZD', '1', '机构的排序字段', '1:按机构代码排序；2:按机构名称排序');

update niutal_xtgl_jsgnmkdmb t set t.cdjb = '1' where t.fjgndm = 'N';

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EBC0F8AE050007F010016D5', 'smtp', 'smtp.sina.com.cn', '25', '1', '0', '25000', 'sina.com.cn', '新浪邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EBD0F8AE050007F010016D5', 'pop3', 'pop3.sina.com.cn', '110', '1', '0', '25000', 'sina.com.cn', '新浪邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EBE0F8AE050007F010016D5', 'pop3', 'pop3.vip.sina.com', '110', '1', '0', '25000', 'vip.sina.com', '新浪VIP邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EBF0F8AE050007F010016D5', 'smtp', 'smtp.vip.sina.com', '25', '1', '0', '25000', 'vip.sina.com', '新浪VIP邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EC00F8AE050007F010016D5', 'pop3', 'pop3.sohu.com', '110', '1', '0', '25000', 'sohu.com', '搜狐邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EC10F8AE050007F010016D5', 'smtp', 'smtp.sohu.com', '25', '1', '0', '25000', 'sohu.com', '搜狐邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EC20F8AE050007F010016D5', 'pop3', 'pop.126.com', '110', '1', '0', '25000', '126.com', '126邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EC30F8AE050007F010016D5', 'smtp', 'smtp.126.com', '25', '1', '0', '25000', '126.com', '126邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EC40F8AE050007F010016D5', 'pop3', 'pop.139.com', '110', '1', '0', '25000', '139.com', '139邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EC50F8AE050007F010016D5', 'smtp', 'smtp.139.com', '25', '1', '0', '25000', '139.com', '139邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EC60F8AE050007F010016D5', 'pop3', 'pop.163.com', '110', '1', '0', '25000', '163.com', '163邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EC70F8AE050007F010016D5', 'smtp', 'smtp.163.com', '25', '1', '0', '25000', '163.com', '163邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EC80F8AE050007F010016D5', 'pop3', 'pop.qq.com', '995', '1', '1', '25000', 'qq.com', 'QQ邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EC90F8AE050007F010016D5', 'smtp', 'smtp.qq.com', '465', '1', '1', '25000', 'qq.com', 'QQ邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453ECA0F8AE050007F010016D5', 'pop3', 'pop.exmail.qq.com', '995', '1', '1', '25000', 'exmail.qq.com', 'QQ企业邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453ECB0F8AE050007F010016D5', 'smtp', 'smtp.exmail.qq.com', '465', '1', '1', '25000', 'exmail.qq.com', 'QQ企业邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453ECC0F8AE050007F010016D5', 'pop3', 'pop.mail.yahoo.com', '110', '1', '0', '25000', 'mail.yahoo.com', '雅虎邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453ECD0F8AE050007F010016D5', 'smtp', 'smtp.mail.yahoo.com', '25', '1', '0', '25000', 'mail.yahoo.com', '雅虎邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453ECE0F8AE050007F010016D5', 'pop3', 'pop.mail.yahoo.com.cn', '995', '1', '0', '25000', 'mail.yahoo.com.cn', '雅虎邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453ECF0F8AE050007F010016D5', 'smtp', 'smtp.mail.yahoo.com.cn', '587', '1', '0', '25000', 'mail.yahoo.com.cn', '雅虎邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453ED00F8AE050007F010016D5', 'pop3', 'pop3.live.com', '995', '1', '0', '25000', 'live.com', 'HotMail邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453ED10F8AE050007F010016D5', 'smtp', 'smtp.live.com', '587', '1', '0', '25000', 'live.com', 'HotMail邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453ED20F8AE050007F010016D5', 'pop3', 'pop.gmail.com', '995', '1', '1', '25000', 'gmail.com', 'Google邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453ED30F8AE050007F010016D5', 'smtp', 'smtp.gmail.com', '587', '1', '1', '25000', 'gmail.com', 'Google邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453ED40F8AE050007F010016D5', 'smtp', 'smtp.sogou.com', '25', '1', '0', '25000', 'sogou.com', '搜狗邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453ED50F8AE050007F010016D5', 'pop3', 'pop3.sogou.com', '110', '1', '0', '25000', 'sogou.com', '搜狗邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453ED60F8AE050007F010016D5', 'smtp', 'smtp.ymail.net', '25', '1', '0', '25000', 'ymail.net', '');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453ED70F8AE050007F010016D5', 'pop3', 'pop3.ymail.net', '110', '1', '0', '25000', 'ymail.net', '');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453ED80F8AE050007F010016D5', 'smtp', 'smtp.yeah.net', '25', '1', '0', '25000', 'yeah.net', '网易YEAH免费邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453ED90F8AE050007F010016D5', 'pop3', 'pop3.yeah.net', '110', '1', '0', '25000', 'yeah.net', '网易YEAH免费邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EDA0F8AE050007F010016D5', 'smtp', 'smtp.263.net', '25', '1', '0', '25000', '263.net ', '云通信邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EDB0F8AE050007F010016D5', 'pop3', '263.net', '110', '1', '0', '25000', '263.net ', '云通信邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EDC0F8AE050007F010016D5', 'smtp', '263.net.cn', '25', '1', '0', '25000', '263.net.cn', '云通信邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EDD0F8AE050007F010016D5', 'pop3', '263.net.cn', '110', '1', '0', '25000', '263.net.cn', '云通信邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EDE0F8AE050007F010016D5', 'smtp', 'smtp.21cn.com', '25', '1', '0', '25000', '21cn.com', '电信企业邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EDF0F8AE050007F010016D5', 'pop3', 'pop.21cn.com', '110', '1', '0', '25000', '21cn.com', '电信企业邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EE00F8AE050007F010016D5', 'pop3', 'pop.foxmail.com', '110', '1', '0', '25000', 'foxmail.com', 'Foxmail邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EE10F8AE050007F010016D5', 'smtp', 'smtp.foxmail.com', '25', '1', '0', '25000', 'foxmail.com', 'Foxmail邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EE20F8AE050007F010016D5', 'pop3', 'pop.china.com', '110', '1', '0', '25000', 'china.com', '中华邮邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EE30F8AE050007F010016D5', 'smtp', 'smtp.china.com', '25', '1', '0', '25000', 'china.com', '中华邮邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EE40F8AE050007F010016D5', 'smtp', 'smtp.swu.edu.cn', '25', '1', '0', '25000', 'swu.edu.cn', '西南大学邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EE50F8AE050007F010016D5', 'pop3', 'pop3.swu.edu.cn', '110', '1', '0', '25000', 'swu.edu.cn', '西南大学邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EEE0F8AE050007F010016D5', 'smtp', 'smtp.263xmail.com', '25', '1', '0', '25000', '263xmail.com', '云通信邮箱-炫我型');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EEF0F8AE050007F010016D5', 'pop3', 'pop.263xmail.com', '110', '1', '0', '25000', '263xmail.com', '云通信邮箱-炫我型');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EE80F8AE050007F010016D5', 'smtp', 'smtp.tom.com', '25', '1', '0', '25000', 'tom.com', 'TOM邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EE90F8AE050007F010016D5', 'pop3', 'pop.tom.com', '110', '1', '0', '25000', 'tom.com', 'TOM邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EEC0F8AE050007F010016D5', 'smtp', 'smtp.x263.net', '25', '1', '0', '25000', 'x263.net ', '云通信邮箱');

insert into niutal_XTGL_YJFWSZB (YJFWSZ_ID, YJXY, YJFWDZ, YJFWDK, SFYZ, SYSSL, CSSJ, YJGYM, BZ)
values ('282879453EED0F8AE050007F010016D5', 'pop3', 'pop.x263.net', '110', '1', '0', '25000', 'x263.net ', '云通信邮箱');

commit;

