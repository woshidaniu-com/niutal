package com.woshidaniu.workflow.enumobject;

/**
 * 
 * 类描述：业务枚举类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:36:46
 */
public enum BusinessEnum {
    // 职务聘任模块使用
    JH_GJZW("高级职务计划审核", "JH_GJZW"), // 高级职务计划审核
    PS_ZWRD("职务认定审核", "PS_ZWRD"), // 职务认定审核
    PS_JSZJZWYS("晋升中级职务预审核", "PS_JSZJZWYS"), // 晋升中级职务预审核
    PS_JSZJZW("晋升中级职务审核", "PS_JSZJZW"), // 晋升中级职务审核
    PS_JSGJZW("晋升高级职务审核", "PS_JSGJZW"), // 晋升高级职务审核
    
    PS_JSGJZWYS("晋升高级职务预审核", "PS_JSGJZWYS"), // 晋升高级职务预审核
    
    PS_WKFGYS("晋升文科副高职务预审核", "PS_WKFGYS"), // 晋升文科副高职务预审核
    PS_LKFGYS("晋升理科副高职务预审核", "PS_LKFGYS"), // 晋升理科副高职务预审核
    PS_WKZGYS("晋升文科正高职务预审核", "PS_WKZGYS"), // 晋升文科正高职务预审核
    PS_LKZGYS("晋升理科正高职务预审核", "PS_LKZGYS"), // 晋升理科正高职务预审核
    
    // 社会福利模块使用
    FL_TQJ("探亲假审核", "FL_TQJ"), // 探亲假审核
    FL_SZFXJ("丧葬抚恤金审核", "FL_SZFXJ"), // 丧葬抚恤金审核
    FL_ZGDBBZ("职工大病补助审核", "FL_ZGDBBZ"), // 职工大病补助审核
    FL_DSZNFY("独生子女费用审核", "FL_DSZNFY"), // 独生子女费用审核
    FL_TFBX("托费报销审核", "FL_TFBX"), // 托费报销审核
    
    // 【个人信息】模块使用
    SH_GRXX("个人信息审核", "SH_GRXX"), // 个人信息审核
    
    //科级定级使用
    KJ_KJDJ("科级定级审核", "KJ_KJDJ"), // 科级定级审核
    
    //考勤管理使用
    KQ_YDKQ("月度考勤审核", "KQ_YDKQ"), // 月度考勤审核
    
    // 培训进修模块使用
    PX_PXJX("培训进修审核", "PX_PXJX"), // 培训进修审核
    
    // 人才招聘模块使用
    JH_BZJH("编制计划审核", "JH_BZJH"), // 编制计划审核
    YP_JXKY("教学科研类人员应聘审核", "YP_JXKY"), // 教学科研类人员应聘审核
    YP_FDY("辅导员类人员应聘审核", "YP_FDY"), // 辅导员类人员应聘审核
    YP_QT("其他类人员审核", "YP_QT"), // 其他类人员审核
    YP_JL("个人简历", "YP_JL"), // 个人简历
    
    ST_ZZSH("社团组织审核", "ST_ZZSH"),
    ST_ZXSH("社团注销审核", "ST_ZXSH"),
    ST_NDYJSH("社团年度院级审核", "ST_NDYJSH"),
    ST_NDXJSH("社团年度校级审核", "ST_NDXJSH"),
    HD_YJZZSH("院级活动组织审核", "HD_YJZZSH"),
    HD_XJZZSH("校级活动组织审核", "HD_XJZZSH"),
    PY_TYPYSH("团员评优审核", "PY_TYPYSH"),
    PY_TZBPYSH("团支部评优审核", "PY_TZBPYSH"),
    PY_STPYSH("社团评优审核", "PY_STPYSH"),
    PY_XSZZPYSH("学生组织评优审核", "PY_XSZZPYSH"),
    PY_ZZPYSH("组织评优审核", "PY_ZZPYSH");//--默认为团总支

    /**
     * 定义枚举类型自己的属性
     */
    private final String text;
    private final String key;

    private BusinessEnum(String text, String key) {
        this.text = text;
        this.key = key;
    }

    /**
     * 展示文本
     * 
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * 代码编号
     * 
     * @return
     */
    public String getKey() {
        return key;
    }
}
