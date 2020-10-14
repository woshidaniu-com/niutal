package com.woshidaniu.workflow.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.entities.YhglModel;
import com.woshidaniu.format.fastjson.FastJSONObject;
import com.woshidaniu.service.common.ICommonSqlService;
import com.woshidaniu.service.svcinterface.IYhglService;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.util.base.StringUtil;
import com.woshidaniu.web.WebContext;
import com.woshidaniu.workflow.enumobject.NodeTypeEnum;
import com.woshidaniu.workflow.enumobject.PtypeEnum;
import com.woshidaniu.workflow.enumobject.WorkNodeStatusEnum;
import com.woshidaniu.workflow.model.SpAuditingLog;
import com.woshidaniu.workflow.model.SpNode;
import com.woshidaniu.workflow.model.SpProcedure;
import com.woshidaniu.workflow.model.SpWorkNode;
import com.woshidaniu.workflow.model.SpWorkProcedure;
import com.woshidaniu.workflow.service.ISpBusinessService;
import com.woshidaniu.workflow.service.ISpProcedureService;
import com.woshidaniu.workflow.service.ISpWorkFlowService;
import com.woshidaniu.workflow.util.ParamUtil;

/**
 * Create on 2013-8-21 下午03:44:28 All CopyRight By http://www.woshidaniu.com/ AT 2013 Version 1.0
 * 
 * @author : HJ [945]
 */
public class SpSettingAction extends WorkFlowBaseAction implements ModelDriven<SpProcedure> {
    private static final long   serialVersionUID = 9999999999999001L;
    public  final String SEND_SMS = "sendSms";
    public  final String SEND_MAIL = "sendMail";
    private SpProcedure         model            = new SpProcedure();

    private ISpProcedureService spProcedureService;
    @Resource
    private IYhglService        yhglService;
    private ISpBusinessService  spBusinessService;
    private ISpWorkFlowService spWorkFlowService;
    /**公共service*/
	@Resource
	protected ICommonSqlService commonSqlService;
	
    public SpProcedure getModel() {
        return model;
    }

    /**
     * 获取工作流列表
     * 
     * @return String
     */
    public String cxSpList() {
        try {
            if (QUERY.equals(model.getDoType())) {
                QueryModel queryModel = model.getQueryModel();
                queryModel.setItems(spProcedureService.getPagedAllSpList(model));
                getValueStack().set(DATA, queryModel);
                return DATA;
            }
            getValueStack().set("ywdlList", spBusinessService.getYwdl());
            getValueStack().set("txyw", spBusinessService.getTxyw());
            getValueStack().set("sendSms", "1".equals(getCommonSqlService().getInnerParam(this.SEND_SMS))?false:true);
            getValueStack().set("sendMail", "1".equals(getCommonSqlService().getInnerParam(this.SEND_MAIL))?false:true);
        } catch (Exception e) {
            logException(e);
            return ERROR;
        }
        return "spList";
    }

    /**
     * 新增流程
     * 
     * @return String
     */
    public String zjSp() {
    	ValueStack vs = getValueStack();
        try {
            SpProcedure sp = new SpProcedure();
            if (model.getPid() == null || model.getPid().equals("")) {
//                List<SpBusiness> bTypeList = spBusinessService.getBusinessType(new SpBusiness());
//                getValueStack().set("bTypeList", bTypeList);
                
                BeanUtils.copyProperties(model, sp);
                vs.set("bzlx", "jbry");
            } else {
                sp = spProcedureService.findSpProcedureByPid(model.getPid(), true);
                //查询审批环节标记
                vs.set("nodeBjList", spProcedureService.findSpNodeBjList(sp.getBusinessType()));
//                SpBusiness busi = new SpBusiness();
//                busi.setBcode(sp.getBusinessType()==null?"":sp.getBusinessType());
//                List<SpBusiness> bTypeList = spBusinessService.getBusinessType(busi);
//                getValueStack().set("bTypeList", bTypeList);
                
                List<SpNode> tmp = sp.getSpNodeList();
                for (SpNode e : tmp) {
                    if (e.getRoleId() == null || e.getRoleId().equals("")) {
                        e.setBzlx("jbry");
                    } else {
                    	e.setBzlx("jbjs");
                    }
                }
                BeanUtils.copyProperties(model, sp);
                getValueStack().set("spNodeList", FastJSONObject.toCleanJSONString(tmp));
            }
        } catch (Exception e) {
            logException(e);
            return ERROR;
        }        
        return "addSp";
    }
    
    /**
     *@描述：根据业务代码查询节点标记
     *@创建人:"huangrz"
     *@创建时间:2014-10-24上午09:58:51
     *@return
     */
    public String cxNodeBjList() {
        getValueStack().set(DATA, spProcedureService.findSpNodeBjList(model.getBusinessType()));
        return DATA;
    }
    
    public String cxSelectBusiness() {
    	try {
            if (QUERY.equals(model.getDoType())) {
                QueryModel queryModel = model.getQueryModel();                
                queryModel.setItems(spBusinessService.getPagedBusinessType(model));
                getValueStack().set(DATA, queryModel);
                return DATA;
            }
            getValueStack().set("ywdlList", spBusinessService.getYwdl());
        } catch (Exception e) {
            logException(e);
            return ERROR;
        }
        return "chooseBusiness";
    }

    /**
     * 保存流程配置
     * 
     * @return String
     */
    public String zjBcSp() {
        try {
           
            String[] bzname = getRequest().getParameterValues("bzname");
            String[] node_bj = getRequest().getParameterValues("node_bj");
            String[] bzlx = getRequest().getParameterValues("bzlx");
            String[] persons = getRequest().getParameterValues("persons");
            String[] personIds = getRequest().getParameterValues("personIds");
            
            String[] sjfwztj = getRequest().getParameterValues("sjfwztj");
            String[] sjfwztj_qz = getRequest().getParameterValues("sjfwztj_qz");
            String[] send_mes = getRequest().getParameterValues("send_mes");
            String[] send_mail = getRequest().getParameterValues("send_mail");
            String[] send_sms = getRequest().getParameterValues("send_sms");
            String[] content_mes = getRequest().getParameterValues("content_mes");
            String[] content_mail = getRequest().getParameterValues("content_mail");
            String[] content_sms = getRequest().getParameterValues("content_sms");
            String[] content_mes_end = getRequest().getParameterValues("content_mes_end");
            String[] content_mail_end = getRequest().getParameterValues("content_mail_end");
            String[] content_sms_end = getRequest().getParameterValues("content_sms_end");

            model.setPtype(PtypeEnum.MAIN_FLOW.getKey());
            model.setBelongToSys("XG-TWXT");
            boolean result = spProcedureService.saveSpSetting(model, bzname,node_bj,bzlx, persons, personIds,sjfwztj,
            		sjfwztj_qz,send_mes,send_mail,send_sms,content_mes,content_mail,content_sms,content_mes_end,content_mail_end,content_sms_end);
            String key = result ? "I99001" : "I99002";
            getValueStack().set(DATA, getText(key));
        } catch (Exception e) {
            getValueStack().set(DATA, e.getMessage());
        }
        return DATA;
    }

    /**
     * 查看工作流配置
     * 
     * @return String
     */
    public String ckSp() {
        try {
            SpProcedure sp = new SpProcedure();
            sp = spProcedureService.findSpProcedureByPid(model.getPid(), true);
            List<SpNode> tmp = sp.getSpNodeList();
            for (SpNode e : tmp) {
                if (e.getRoleId() == null || e.getRoleId().equals("")) {
                    e.setBzlx("jbry");
                } else {
                	e.setBzlx("jbjs");
                }
            }
            BeanUtils.copyProperties(model, sp);
            getValueStack().set("sendSms", "1".equals(getCommonSqlService().getInnerParam(this.SEND_SMS))?false:true);
            getValueStack().set("sendMail", "1".equals(getCommonSqlService().getInnerParam(this.SEND_MAIL))?false:true);
        } catch (Exception e) {
            logException(e);
            return ERROR;
        }
        return "viewSp";
    }

    /**
     * 删除流程
     * 
     * @return String
     */
    public String scSp() {
        try {
            String pks = getRequest().getParameter("ids");
            model.setPkValue(pks);
            spProcedureService.deleteSp(model);
            getValueStack().set(DATA, getText("I99005"));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            getValueStack().set(DATA, getText("I99006"));
        }
        return DATA;
    }

    /**
     * 填充经办人员/角色选中值 void
     */
    private void fillSelect() {
        String perIds = getRequest().getParameter("perIds");
        String pers = getRequest().getParameter("pers");
        List<YhglModel> sltedList = new ArrayList<YhglModel>();
        if (perIds != null && !perIds.equals("")) {
            String[] sperIds = perIds.split(",");
            String[] spers = pers.split(",");
            for (int i = 0; i < sperIds.length; i++) {
                YhglModel e = new YhglModel();
                //e.setZgh(sperIds[i]);
                e.setYhm(spers[i]);
                e.setXm(ParamUtil.decodeDouble(spers[i], new String[] {}));
                sltedList.add(e);
            }
            getValueStack().set("sltedList", sltedList);
        }
    }

    /**
     * 跳转至用户选择页
     * 
     * @return String
     */
    /*public String cxSelectPer() {
        try {
            fillSelect();
            
            YhglModel yhglModel = new YhglModel();
            yhglModel.setYhm(SessionFactory.getUser().getYhm());
            
            //List<BmdmModel> bmList = yhglService.getModelList();            
            List<JsglModel> jsList = yhglService.cxJsdmList(yhglModel);
            //getValueStack().set("bmList", bmList);
            getValueStack().set("jsList", jsList);
        } catch (Exception e) {
            logException(e);
            return ERROR;
        }
        return "choosePer";
    }*/

    /**
     * 跳转至角色选择页
     * 
     * @return String
     */
    public String cxSelectRole() {
        try {
            fillSelect();
            YhglModel yhglModel = new YhglModel();
            yhglModel.setYhm(WebContext.getUser().getYhm());
            List<JsglModel> jsList = yhglService.cxJsdmList(yhglModel);
            getValueStack().set("jsList", jsList);
        } catch (Exception e) {
            logException(e);
            return ERROR;
        }
        return "chooseRole";
    }

    /**
     * 根据条件得到用户列表信息
     * 
     * @return String
     */
    public String cxUserList() {
        try {
            String aType = getRequest().getParameter("aType");
            String aValue = getRequest().getParameter("aValue");

            YhglModel yh = new YhglModel();
            List<YhglModel> yhList = new ArrayList<YhglModel>();

            if (aType.equals("ajs")) {
                yh.setJsdm(aValue);
                yhList = yhglService.cxYhByJsdm(yh);
            } else {
                if (aType.equals("abm")) {
                    yh.setJgdm(aValue);
                } else if (aType.equals("agh")) {
                    yh.setYhm(aValue);
                } else if (aType.equals("axm")) {
                    yh.setXm(aValue);
                }
                yhList = yhglService.cxYhByJgdm(yh);
            }

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < yhList.size(); i++) {
                YhglModel e = (YhglModel) yhList.get(i);
                sb.append("<option value='" + e.getYhm() + "'>");
                sb.append(e.getXm());
                sb.append("</option>");
            }

            getValueStack().set(DATA, sb.toString());
        } catch (Exception e) {
            logException(e);
            return ERROR;
        }
        return DATA;
    }
    
    /**
     * 流程跟踪
     * 
     * @return String
     */
    public String cxLcgz() {
        try {
            String ywid = getRequest().getParameter("id");
            String lcgzHtml = spWorkFlowService.viewWorkFlowLogHtmlByWorkId(ywid);
            lcgzHtml = lcgzHtml.replaceAll("<%=stylePath %>", MessageUtil.getText("system.stylePath"));
            getValueStack().set("lcgzHtml", lcgzHtml);
        } catch (Exception e) {
            getValueStack().set("lcgzHtml", e.getMessage());
        }
        return "lcgz";
    }

    /**
     * 退回选择退回的节点
     * 
     * @return String
     */
    public String thxzjd() {
        try {
            String ywid = getRequest().getParameter("id");
            SpWorkProcedure p = spWorkFlowService.queryWorkFlowByWorkId(ywid);
            List<SpWorkNode> resultList = new ArrayList<SpWorkNode>();
            SpWorkNode topNode = new SpWorkNode();
            topNode.setNodeId("-1");
            topNode.setNodeName("申请人");
            resultList.add(topNode);

            if (p != null) {
                List<SpWorkNode> list = p.getSpWorkNodeList();
                for (SpWorkNode n : list) {
                    if (n.getStatus().equals("PASS_AUDITING")) {
                        resultList.add(n);
                    }
                }
            }
            getValueStack().set("resultList", resultList);
        } catch (Exception e) {
            logException(e);
            return ERROR;
        }
        return "thxzjd";
    }

    /**
     * 判断是否可以撤销
     * 
     * @return String
     */
    public String sfkcx() {
        try {
            String ywid = getRequest().getParameter("id");
            String cuNodeId = getRequest().getParameter("nodeId");
            SpWorkProcedure p = spWorkFlowService.queryWorkFlowByWorkId(ywid);
            if(p==null) {
                throw new Exception("出错");
            }
            List<SpWorkNode> list = p.getSpWorkNodeList();
            SpWorkNode cNode = null;// 当前节点
            SpWorkNode nextNode = null;// 下级节点
            SpWorkNode startNode = null;// 开始节点
            
            // 申请人的撤销
            if (cuNodeId.equals("-1")) {
                for (SpWorkNode n : list) {
                    if (n.getNodeType().equals(NodeTypeEnum.START_NODE.getKey())) {
                        startNode = n;
                        break;
                    }
                }
                if (startNode == null) {
                    throw new Exception("节点未找到或者已删除");
                }
                // 开始节点未审核，可以撤销
                if (startNode.getStatus().equals(WorkNodeStatusEnum.WAIT_AUDITING.getKey())) {
                    getValueStack().set(DATA, "ok");
                } else {
                    getValueStack().set(DATA, "撤销失败");
                }
            } else {// 审核人的撤销
                // 得到当前节点信息
                cNode = spWorkFlowService.queryWorkNodeByWidAndNodeId(ywid,cuNodeId);
                // 得到下级节点信息
                nextNode = spWorkFlowService.queryNextWorkNodeByWidAndNodeId(ywid, cuNodeId);
                // 判断当前节点是否为审核通过，下级节点状态是否为待审核，此两状态并集可删
                if (cNode.getStatus().equals(WorkNodeStatusEnum.PASS_AUDITING.getKey())
                    && nextNode!=null&&nextNode.getStatus().equals(WorkNodeStatusEnum.WAIT_AUDITING.getKey())) {
                    getValueStack().set(DATA, "ok");
                } else {
                    getValueStack().set(DATA, "撤销失败");
                }
            }
        } catch (Exception e) {
            getValueStack().set(DATA, e.getMessage());
        }
        return DATA;
    }
    
    /**
     * 公用的审核页面
     * 
     * @return String
     */
    public String cxToShPage() {
        try {
        	//参数处理
        	String id = getRequest().getParameter("id");
        	getValueStack().set("id", id);
        	//节点id
        	String nodeId = getRequest().getParameter("nodeId");
        	getValueStack().set("nodeId", nodeId);
        	//页面查看跳转url
        	String ckUrl = URLDecoder.decode(StringUtil.getSafeStr(getRequest().getParameter("ckUrl"), ""),"UTF-8");
        	getValueStack().set("ckUrl", ckUrl);
        	//审核后处理url
        	String shUrl = URLDecoder.decode(StringUtil.getSafeStr(getRequest().getParameter("shUrl"), ""),"UTF-8");
        	getValueStack().set("shUrl", shUrl);
        	
        	//判断是否是终审用户
        	String sfzs = "0";//0不是终审 1:是终审
        	SpWorkProcedure p = spWorkFlowService.queryWorkFlowByWorkId(id);
            List<SpWorkNode> list = p.getSpWorkNodeList();
            SpWorkNode cNode = spWorkFlowService.queryWorkNodeByWidAndNodeId(id, nodeId);           
            if (list.size() == 1 || cNode.getNodeType().equals(NodeTypeEnum.END_NODE.getKey())) {
               	//如果是罪后一个用户 则通过时要填写"终审弹出框"
            	sfzs = "1";
            }
            getValueStack().set("sfzs", sfzs);
        	
            // 工作流退回节点等信息
            List<SpWorkNode> resultList = new ArrayList<SpWorkNode>();
            SpWorkNode topNode = new SpWorkNode();
            topNode.setNodeId("-1");
            topNode.setNodeName("申请人");
            resultList.add(topNode);
            if (p != null) {
                for (SpWorkNode n : list) {
                    if (n.getStatus().equals(WorkNodeStatusEnum.PASS_AUDITING.getKey())) {
                        resultList.add(n);
                    }
                }
            }
            getValueStack().set("resultList", resultList);
            // 工作流审核历史日志
            List<SpAuditingLog> logList = spWorkFlowService.querySpAuditingLog(id, null);
            getValueStack().set("logList", logList);
            
            //调课审批控制，1:可中途“通过并结束”流程；2:不能中途“通过并结束”流程'
			String ttkspkz = getCommonSqlService().cxXtszxz("TTKSPKZ");
			getValueStack().set( "ttkspkz" , ttkspkz );
			
        } catch (Exception e) {
            logException(e);
            return ERROR;
        }
        return "pub_sh_view";
    }
    
    /**
     *@描述：公用批量审核页面
     *@创建人:huangrz
     *@创建时间:2014-7-29上午09:42:54
     *@修改人:
     *@修改时间:
     *@修改描述:
     *@return
     */
    public String cxPlshPage(){    	
    	return SUCCESS;
    }

    /**
     * set/get method
     */

    public void setSpProcedureService(ISpProcedureService spProcedureService) {
        this.spProcedureService = spProcedureService;
    }

    public void setYhglService(IYhglService yhglService) {
        this.yhglService = yhglService;
    }

    public void setSpBusinessService(ISpBusinessService spBusinessService) {
        this.spBusinessService = spBusinessService;
    }

	public ISpWorkFlowService getSpWorkFlowService() {
		return spWorkFlowService;
	}

	public void setSpWorkFlowService(ISpWorkFlowService spWorkFlowService) {
		this.spWorkFlowService = spWorkFlowService;
	}

	public ICommonSqlService getCommonSqlService() {
		return commonSqlService;
	}

	public void setCommonSqlService(ICommonSqlService commonSqlService) {
		this.commonSqlService = commonSqlService;
	}    
	
	
}
