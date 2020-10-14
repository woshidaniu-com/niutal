<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>		
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/dateformat.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/sp/selectPer.js"></script>
		
</head>

<s:form  method="post" id="myForm" theme="simple">
	<s:hidden id="id" name="id"></s:hidden>
	<body>
     <input type="hidden" name="doType" value="save"/>       
	 <div class="tab">
	  <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>经办角色信息</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="4"><div class="bz">"<span class="red">*</span>"为必填项
	        </div>
	          <div class="btn">
	            <button type="button" id="btn_bc" name="btn_bc" onclick="toSave();">保 存</button>
	            <button type="button" id="btn_gb" name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	        <td width="45%" align="center">
	        	<s:select list="jsList" name="fromRole" id="fromRole" listKey="jsdm" listValue="jsmc"  theme="simple" cssStyle="width:180px;" size="20" multiple="true"></s:select>
	        </td>
	        <td width="10%" align="center">
	        	<button type="button" id="btn_lft" name="btn_lft" onclick="toLeft();">-></button>
	        	<br/><br/>
	        	<button type="button" id="btn_rgt" name="btn_rgt" onclick="toRight();"><-</button>
	        </td>
	        <td width="45%" align="center">
	        	<select id="toRole" name="toRole" size="20" multiple="true" style="width:180px;">
	        		<s:iterator id="e" value="sltedList" status="sta">
	        			<s:if test="#e.xm!=''">
	        				<option value="${e.zgh}">${e.xm}</option>
	        			</s:if>
	        		</s:iterator>
	        	</select>
	        </td>
	      </tr>
	      
	    </tbody>
	  </table>
  </div>
</body>
</s:form>
</html>