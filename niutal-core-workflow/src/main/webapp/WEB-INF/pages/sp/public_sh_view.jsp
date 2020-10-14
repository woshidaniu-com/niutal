<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
<s:form  method="post" id="myForm" theme="simple">
	<s:hidden id="id" name="id"></s:hidden>
	<s:hidden id="nodeId" name="nodeId"></s:hidden>
	<s:hidden id="ckUrl" name="ckUrl"></s:hidden>
	<s:hidden id="shUrl" name="shUrl"></s:hidden>
	<s:hidden id="sfzs" name="sfzs"></s:hidden><!--是否终审用户 0不是终审 1:是终审 -->
	 <div class="tab" style="margin:10px" id="fixtop">
   		<div class="row">
   			<div class="col-sm-7 col-lg-9">
   				<div class="input-group">
         			<div class="input-group-btn">
         				<select id="tgzt" name="tgzt" onchange="showNode(this.value);" class="form-control" style="width:15%">
							<option value="pass">通过</option>
							<option value="unpass">不通过</option>
							<option value="th">退回</option>
						</select>
						<s:select list="resultList" name="returnNodeId" id="returnNodeId" 
							listKey="nodeId" listValue="nodeName" headerKey="" headerValue="请选择退回节点" 
							cssClass="form-control" cssStyle="width:25%;display:none"></s:select>
						<s:textfield name="suggestion" id="suggestion" placeholder="审核意见" cssClass="form-control" cssStyle="width:85%"/>
         			</div>
	        	</div>
	        </div>
	        <div class="col-sm-5 col-lg-3 text-right">
	           <div class="btn-group">
		            <button id="btn_save" type="button" class="btn btn-primary">确定</button>
		            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">审核历史</button>
		        	<div class="dropdown-menu sl_flpop_con">
		        		<table class="table">
		        			<thead>
						    	<tr>
						          <th>审核人</th>
						          <th>审核时间</th>
						          <th>审核状态</th>
						          <th>审核意见</th>
						        </tr>
						    </thead>
							<s:iterator id="e" value="logList" status="sta">
								<tr>
									<td>${e.operatorCn}</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${e.logTime}"/></td>
									<td>${e.ostatusCn}</td>
									<td>${e.osuggestion}</td>
								</tr>
							</s:iterator>
						</table>
		        	</div>
	        	</div>
	        </div>    
   		</div>
  </div> 
  <div id="ckxx" style="margin-top: 10px"></div>
</s:form>
</body>
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?ver=<%=jsVersion%>"></script>
<script type="text/javascript">
	function toSave(){
		var tgzt = jQuery("#tgzt").val();
		if(tgzt=='th'){
			if(jQuery("#returnNodeId").val()==''){
				$.alert('请选择退回节点！');
				return false;
			}
		}
		if(tgzt!='pass'){
			if(jQuery("#suggestion").val()==''){
				$.alert('请填写审核意见！');
				return false;
			}
		}
		var url = jQuery("#shUrl").val();
		ajaxSubFormWithFun("myForm",url,{},function(data){
			if(data.indexOf("成功") > -1){						
				$.success(data,function() {
					$.closeModal("shModal");//关闭窗口
					refershGrid("tabGrid");//刷新页面
				});
			}else if(data.indexOf("失败") > -1){
				$.error(data);
			}
		});
	}
	
	//显示隐藏退回节点
	function showNode(tmpValue){
		if(tmpValue=='th'){
			jQuery("#returnNodeId").show();
			$("#returnNodeId").setSelected("");
			jQuery("#suggestion").css("width","60%");
		}else{
			jQuery("#returnNodeId").hide();
			jQuery("#suggestion").css("width","85%");
		}
	}
	
	//关闭窗口
	function toClose(){
		$.closeModal("shModal");
	}			
	
	jQuery(function($){
		$("#myForm").validateForm();
		//查看页面加载信息
		jQuery("#ckxx").load(jQuery("#ckUrl").val());
		//绑定
		$("#btn_save").bind("click", toSave);
	});
</script>
</html>