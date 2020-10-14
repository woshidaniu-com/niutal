<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/v5_url.ini"%>	
	<style type="text/css">
		.ui-jqgrid tr.jqgrow td{
			white-space:normal;
		}
	</style>
</head>
<body>
	<s:form action="/sp/spSetting_zjBcSp.html"  method="post" id="ajaxForm" cssClass="form-horizontal sl_all_form" theme="simple">
		<s:hidden id="pid" name="pid"></s:hidden>
     	<input type="hidden" name="doType" value="save"/>
     	<div class="row">
			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">
						<span class="red">*</span>业务类型
					</label>
					<div class="col-sm-8">
						<div class="input-group">
							<s:hidden id="businessType" name="businessType"/>
							<s:textfield id="businessName" name="businessName"
								readonly="true" cssClass="form-control" validate="{required:true}"/>
							<span class="input-group-btn">
								<button class="btn btn-default" data-toggle="modal" onclick="chooseBusiness()" type="button">></button>
							</span>
						</div>							
					</div>
				</div>
			</div>
			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">
						<span class="red">*</span>是否启用
					</label>
					<div class="col-sm-8">
						<s:select list="#{1:'启用',0:'停用'}" name="pstatus" id="pstatus" cssClass="form-control width-90 chosen-select" validate="{required:true}"/>
						<SCRIPT type="text/javascript">
				    		jQuery('#pstatus').trigger("chosen");
				    	</SCRIPT>
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-2 control-label">
						<span class="red">*</span>流程描述
					</label>
					<div class="col-sm-10">
						<s:textarea name="pdesc" id="pdesc" cssClass="form-control" cssStyle="height:auto" validate="{required:true,stringMaxLength:160}"></s:textarea>
					</div>
				</div>
			</div>
		</div>
     	<div class="alert alert-warning alert-dismissible" id="addRowDiv">
     		<span>步骤信息</span>&nbsp;&nbsp;&nbsp;&nbsp;
        	<a href="#" onclick="addRow();"><img src="<%=systemPath %>/images/add-icons-1.gif" alt="增加" /></a>&nbsp;
     	</div>
     	
     	<!-- 步骤grid  -->
    	<!-- table-start  -->
	   	<div class="ui-grid-handle" id="bzGridResizeHandle">&nbsp;</div>
		<table id="bzGrid"></table>
		<!-- table-end  -->
		
     	     	
  </s:form>
  <div id="nodeBjHtml" style="display:none">
	<select id="node_bj" name="node_bj" class="form-control">
		<option value=""></option>
		<s:if test="nodeBjList!=null && nodeBjList.size()>0">
			<s:iterator value="nodeBjList">
				<option value="${BJ}">${MC}</option>
			</s:iterator>
		</s:if>
	</select>
  </div>
</body>
<script type="text/javascript" src="<%=systemPath %>/js/sp/spAdd.js?ver=<%=jsVersion%>"></script>
<script type="text/javascript">
	jQuery(function($) {
		//修改时 赋予步骤信息
		<s:if test="spNodeList!=null && spNodeList!=''">
			var spNodeList = ${spNodeList};
			$.each(spNodeList,function(index,item){
				$("#bzGrid").addRowData("", item);
			});
		</s:if>
	});
</script>
</html>