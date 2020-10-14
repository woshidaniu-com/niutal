<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
	<!--按钮 开始 -->
	<div class="row sl_add_btn">
	    <div class="col-sm-12">
	      	<!-- 加载当前菜单栏目下操作     -->
      		<%=IndexAction.cxButtons("N011505")%>
			<!-- 加载当前菜单栏目下操作 -->
	    </div>
	</div>
	<!--按钮 结束  -->
	<s:form theme="simple" cssClass="form-horizontal sl_all_form">
		<div class="row" id="fixWidth">
			<div class="col-sm-5">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">业务大类</label>
					<div class="col-sm-8">
						<s:select name="ywdl" id="selectYwdl" cssClass="form-control chosen-select"  headerKey="" headerValue="全部"
								list="ywdlList" listKey="YWDL" listValue="YWDLMC"></s:select>
						<SCRIPT type="text/javascript">
				    		jQuery('#selectYwdl').trigger("chosen");
				    	</SCRIPT>
					</div>
				</div>
			</div>
		
			<div class="col-sm-5">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">业务名称</label>
					<div class="col-sm-8">
						<s:textfield id="selectBusinessName" name="ywmc" cssClass="form-control" />
					</div>
				</div>
			</div>			
		</div>
	</s:form>
	<div class="row sl_aff_btn">
		<div class="col-sm-12">
			<button type="button" id="search_go" onclick="searchResult()"class="btn btn-primary btn-sm">查询</button>
		</div>
	</div>
	
	<table id="tabGrid"></table>
	<div id="pager"></div>
</body>
	
<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid4.6.ini"%>
<script type="text/javascript" src="<%=systemPath %>/js/sp/spSetting.js?ver=<%=jsVersion%>"></script>
<%@ include file="/WEB-INF/pages/globalweb/head/validation.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/selectPanel.ini"%>	

<script type="text/javascript">
	//特殊审批流 需要有环节标记的(转专业，辅修报名)
	var txyw = '${txyw}';
	//是否发送审批流短信
	var sendSms = ${sendSms};
	//是否发送审批流邮件
	var sendMail = ${sendMail};
</script>

</html>