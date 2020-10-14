package com.woshidaniu.workflow.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.woshidaniu.workflow.enumobject.PtypeEnum;

/**
 * 流程对象 代码自动生成(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public class SpProcedure extends BaseObject implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    /* @property:流程ID */
    private String            pid;
    /* @property:流程名称 */
    private String            pname;
    /* @property:流程类型 */
    private String            ptype;
    /* @property:流程状态 */
    private String            pstatus;
    /* @property:流程描述 */
    private String            pdesc;
    /* @property:所属系统 */
    private String            belongToSys      = "";
    /* @property:所属系统名称 */
    private String            belongToSysName  = "";
    /* @property:审核页面链接 */
    private String            link;

    /* @property:连线对象集合 */
    private List<SpLine>      spLineList;
    /* @property:节点对象集合 */
    private List<SpNode>      spNodeList;

    // 业务类型
    private String            businessType;
    private String            businessName;

    //业务大类
    private String ywdl;
    private String ywdlmc;
    private String cxAll;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPstatus() {
        return pstatus;
    }

    public void setPstatus(String pstatus) {
        this.pstatus = pstatus;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getPtypeStr() {
        return PtypeEnum.valueOf(ptype).getText();
    }

    /* {@inheritDoc} */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpProcedure)) {
            return false;
        }
        final SpProcedure spprocedure = (SpProcedure) o;
        return this.hashCode() == spprocedure.hashCode();
    }

    /* {@inheritDoc} */
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("pid", this.pid).append("pname",
                                                                                                                   this.pname)
                .append("ptype", this.ptype).append("pstatus", this.pstatus).append("pdesc", this.pdesc);
        return sb.toString();
    }

    private Map<String, SpNode> spNodeMap;

    public Map<String, SpNode> getSpNodeMap() {
        if (spNodeMap != null) {
            return spNodeMap;
        }
        spNodeMap = new HashMap<String, SpNode>();
        if (spNodeList != null) {
            for (SpNode spNode : spNodeList) {
                spNodeMap.put(spNode.getNodeId(), spNode);
            }
        }
        return spNodeMap;
    }

    public void setSpLineList(List<SpLine> spLineList) {
        this.spLineList = spLineList;
    }

    public List<SpLine> getSpLineList() {
        return spLineList;
    }

    public void setSpNodeList(List<SpNode> spNodeList) {
        this.spNodeList = spNodeList;
    }

    public List<SpNode> getSpNodeList() {
        return spNodeList;
    }

    /**
     * @param belongToSys : set the property belongToSys.
     */

    public void setBelongToSys(String belongToSys) {
        this.belongToSys = belongToSys;
    }

    /**
     * @return belongToSys : return the property belongToSys.
     */

    public String getBelongToSys() {
        return belongToSys;
    }

    public String getBelongToSysName() {
        return belongToSysName;
    }

    public void setBelongToSysName(String belongToSysName) {
        this.belongToSysName = belongToSysName;
    }

    /**
     * @return link : return the property link.
     */

    public String getLink() {
        return link;
    }

    /**
     * @param link : set the property link.
     */

    public void setLink(String link) {
        this.link = link;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

	public void setYwdl(String ywdl) {
		this.ywdl = ywdl;
	}

	public String getYwdl() {
		return ywdl;
	}

	public void setYwdlmc(String ywdlmc) {
		this.ywdlmc = ywdlmc;
	}

	public String getYwdlmc() {
		return ywdlmc;
	}

	public void setCxAll(String cxAll) {
		this.cxAll = cxAll;
	}

	public String getCxAll() {
		return cxAll;
	}

}
