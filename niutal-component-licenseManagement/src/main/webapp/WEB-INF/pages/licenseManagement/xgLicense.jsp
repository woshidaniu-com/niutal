<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
		function save(){
			if(jQuery.trim($("input[name=startDate]").val()) == ''){
				alertMessage('请输入开始时间！');
				return false;
			}
			if(jQuery.trim($("input[name=expireDate]").val()) == ''){
				alertMessage('请输入过期时间！');
				return false;
			}
			
			if(jQuery.trim($("input[name=usage]").val()) == ''){
				alertMessage('请输入授权使用天数！');
				return false;
			}else{
				if(jQuery.trim($("input[name=usage]").val()).replace(/[^(\d]/g,'') != $.trim($("input[name=usage]").val())){
					alertMessage('授权使用天数必须为整数！');
					return false;
				}
			}
			
			if(jQuery.trim($("input[name=usageCount]").val()) == ''){
				alertMessage('请输入已使用天数！');
				return false;
			}else{
				if(jQuery.trim($("input[name=usageCount]").val()).replace(/[^(\d]/g,'') != $.trim($("input[name=usageCount]").val())){
					alertMessage('已使用必须为整数！');
					return false;
				}
			}
			
			if(jQuery.trim($("input[name=alert]").val()) == ''){
				alertMessage('请输入过期提示天数！');
				return false;
			}else{
				if(jQuery.trim($("input[name=alert]").val()).replace(/[^(\d]/g,'') != $.trim($("input[name=alert]").val())){
					alertMessage('过期提示天数必须为整数！');
					return false;
				}
			}
			ajaxSubFormWithFun("myForm","<%=systemPath%>/license-management/authdata_xgBcLicense.html",{},function(data){
				var api = frameElement.api, W = api.opener;
				alertMessage(data, function() {
					this.close();
					W.jQuery("#search_go").click();
					frameElement.api.close();
				});
			});
		}
		</script>
	</head>
	<body>
	<s:form id="myForm" namespace="/license-management" action="authdata_xgBcLicense" theme="simple">
	 <div class="tab">
	  <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>授权信息</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="4"><div class="bz">"<span class="red">*</span>"为必填项</div>
	          <div class="btn">
	            <button name="btn_tj" onclick="save();" type="button">保 存</button>
	            <button name="btn_fh" onclick="iFClose()" type="button">关闭</button>
	          </div>
	        </td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	      	 <th width="20%">授权编号</th>
	         <td>
	         	<s:hidden name="authId"></s:hidden>
	         	<s:label name="authId"></s:label>
	         </td>
	         <th width="20%">授权时间</th>
	         <td>
	         	<s:label name="authDate"></s:label>
	         </td>
	      </tr>
	      <tr>
	      	 <th width="20%"><span class="red">*</span>机构代码</th>
	         <td>
	         	<s:label name="authUserCode"></s:label>
	         </td>
	          <th width="20%"><span class="red">*</span>机构名称</th>
	         <td>
	         	<s:label name="authUser"></s:label>
	         </td>
	      </tr>
	      <tr>
	      	 <th width="20%"><span class="red">*</span>业务系统</th>
	         <td colspan="3">
	         	<s:label name="productName"></s:label>
	         </td>
	      </tr>
	      <tr>
	      	 <th width="20%"><span class="red">*</span>开始时间</th>
	         <td>
				<s:textfield name="startDate" id="startDate" cssStyle="width:76%" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\\'expireDate\\')}'})" theme="simple"></s:textfield> 
	         </td>
	         <th width="20%"><span class="red">*</span>过期时间</th>
	         <td>
				<s:textfield name="expireDate" id="expireDate" cssStyle="width:76%" onfocus="WdatePicker({minDate:'#F{$dp.$D(\\'startDate\\')}'})" theme="simple"></s:textfield> 
	         </td>
	      </tr>
	      <tr>
	      	 <th width="20%"><span class="red">*</span>授权有效时间(天)</th>
	         <td>
				<s:textfield name="usage" id="usage" cssStyle="width:50px" theme="simple"></s:textfield> 
	         </td>
	         <th width="20%"><span class="red">*</span>已用(天)</th>
	         <td>
	         	<s:textfield name="usageCount" id="usageCount" cssStyle="width:50px" theme="simple"></s:textfield> 
	         </td>
	      </tr>
	      <tr>
	      	<th width="20%"><span class="red">*</span>过期提示(天)</th>
	          <td colspan="3">
				<s:textfield name="alert" id="alert" cssStyle="width:50px" theme="simple"></s:textfield> 
	         </td>
	      </tr>
	      <tr>
	      	<th width="20%"><span class="red">*</span>开发模式</th>
	          <td colspan="3">
				<s:radio list="#{'1':'是','0':'否'}" name="devMode" id="devMode" theme="simple"></s:radio>
	         </td>
	      </tr>
	      <tr>
	      	<th width="20%">备注信息</th>
	         <td colspan="3">
				<s:textfield name="bzxx" id="bzxx" cssStyle="width:76%" theme="simple"></s:textfield> 
	         </td>
	      </tr>
	    </tbody>
	  </table>
  </div>
</s:form>
</body>
</html>