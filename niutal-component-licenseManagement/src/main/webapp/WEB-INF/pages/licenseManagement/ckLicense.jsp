<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	</head>
	<body>
	<s:form id="myForm" namespace="/license-management" action="authdata_ckLicense" theme="simple">
	 <div class="tab">
	  <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>授权信息</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="4">
	          <div class="btn">
	            <button name="btn_fh" onclick="iFClose()" type="button">关闭</button>
	          </div>
	        </td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	      	 <th width="20%">授权编号</th>
	         <td>
	         	<s:label name="authId"></s:label>
	         </td>
	         <th width="20%">授权时间</th>
	         <td>
	         	<s:label name="authDate"></s:label>
	         </td>
	      </tr>
	      <tr>
	      	 <th width="20%">机构代码</th>
	         <td>
	         	<s:label name="authUserCode"></s:label>
	         </td>
	          <th width="20%">机构名称</th>
	         <td>
	         	<s:label name="authUser"></s:label>
	         </td>
	      </tr>
	      <tr>
	      	 <th width="20%">机构代码</th>
	         <td colspan="3">
	         	<s:label name="productName"></s:label>
	         </td>
	      </tr>
	      
	      <tr>
	      	 <th width="20%">开始时间</th>
	         <td>
	         	<s:label name="startDate"></s:label>
	         </td>
	         <th width="20%">过期时间</th>
	         <td>
				<s:label name="expireDate"></s:label>
	         </td>
	      </tr>
	      <tr>
	      	 <th width="20%">授权有效时间(天)</th>
	         <td>
	         	<s:label name="usage"></s:label>
	         </td>
	         <th width="20%">已用(天)</th>
	         <td>
	         	<s:label name="usageCount"></s:label>
	         </td>
	      </tr>
	      <tr>
	      	<th width="20%">过期提示(天)</th>
	          <td colspan="3">
	          	<s:label name="alert"></s:label>
	         </td>
	      </tr>
	      <tr>
	      	<th width="20%">开发模式</th>
	          <td colspan="3">
	          	 <s:if test='devMode == "1"'>
    			是
	    		</s:if>
	    		<s:elseif test='devMode == "0"'>
	    		否
	    		</s:elseif>
	         </td>
	      </tr>
	      <tr>
	      	<th width="20%">备注信息</th>
	         <td colspan="3">
	         	<s:label name="bzxx"></s:label>
	         </td>
	      </tr>
	    </tbody>
	  </table>
  </div>
</s:form>
</body>
</html>