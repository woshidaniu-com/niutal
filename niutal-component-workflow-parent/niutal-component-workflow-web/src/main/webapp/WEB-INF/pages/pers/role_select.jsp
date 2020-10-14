<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String systemPath = request.getContextPath();
%>
<!doctype html>
<html>
	<head>
		<script type="text/javascript" src="<%=systemPath %>/js/sp/selectPer.js"></script>
	</head>


	<body>
	<s:form  method="post" id="myForm" theme="simple">
	<s:hidden id="id" name="id"></s:hidden>
     <input type="hidden" name="doType" value="save"/>       
	 <div class="tab">
	  <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>经办角色信息</span></th>
	        </tr>
	    </thead>
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
	        				<option value="${e.yhm}">${e.xm}</option>
	        			</s:if>
	        		</s:iterator>
	        	</select>
	        </td>
	      </tr>
	    </tbody>
	  </table>
  </div>
  </s:form>
</body>
</html>