<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
			<script type="text/javascript" src="<%=systemPath %>/js/validate/talent-validate-all-min.js?_rv_=<%=resourceVersion%>"></script>
			<link rel="stylesheet" type="text/css" href="<%=systemPath %>/js/validate/css/validate.css?_rv_=<%=resourceVersion%>"></link>
			<script type="text/javascript">
				function save(){
					if (zfValidate.validate()){
						 ajaxSubFormWithFun("xydmForm",'<%=systemPath%>/zfxg/xydm/xgBcXydm.html',{},function(data){
						 	alert(data,{},{"clkFun":function(){
						 		refershParent();
					 	 }});
					 });
					}
				}
	
				jQuery(function(){
					zfValidate.vf.req.addId("bmmc");
				});
			</script>
	</head>

	<body >
		<s:form action="/zfxg/xydm/xgBcXydm.html" method="post" theme="simple" id="xydmForm">
	     <input type="hidden" name="doType" value="save"/>   
	     <input type="hidden" id="bmdm_id" name="bmdm_id" value="${model.bmdm_id}"/>  
		 <div class="tab">
		  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
		    <thead>
		    	<tr>
		        	<th colspan="4"><span>修改学院</span></th>
		        </tr>
		    </thead>
		    <tfoot>
		      <tr>
		        <td colspan="4"><div class="bz">"<span class="red">*</span>"为必填项</div>
		          <div class="btn">
		            <button name="btn_tj" onclick=" save();return false;">保 存</button>
		            <button name="btn_gb" onclick="iFClose();return false;">关 闭</button>
		          </div></td>
		      </tr>
		    </tfoot>
		    <tbody>
		      <tr>
		        <th width="20%">部门代码</th>
		        <td>
		       <s:label name="bmdm_id"></s:label>
		         </td>
		         </tr>
		         <tr>
		         <th width="30%"><span class="red">*</span>学院名称</th>
		        <td width="70%">
		        <s:textfield maxlength="25" name="bmmc" id="bmmc">
		        </s:textfield>
		         </td>
		      </tr>
		      <tr>
	     	<th width="30%"><span class="red">*</span>部门类型</th>
	        <td width="70%">
				<s:select list="bmlxList" name="bmlx" listKey="dm" listValue="mc"></s:select>
	         </td>
	      </tr>
		    </tbody>
		  </table>
	  </div>
	  <s:if test="result != null && result != ''">
	  	<script>
	  		alert('${result}','',{'clkFun':function(){refershParent();}});
	  	</script>
	  </s:if>
	</s:form>
	</body>
</html>