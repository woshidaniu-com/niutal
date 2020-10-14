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
		
</head>

<s:form  method="post" id="myForm" theme="simple">
	<s:hidden id="pid" name="pid"></s:hidden>
	<body>
     <input type="hidden" name="doType" value="save"/>       
	 <div class="tab">
	  <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>流程信息</span></th>
	        </tr>
	    </thead>
	    <tbody>
	      <tr>
	        <th width="20%">流程名称</th>
	        <td width="80%" colspan="3"><s:property value="pname"/></td>
	      </tr>
	      <tr>
	        <th width="20%">业务类型</th>
	        <td width="30%"><s:property value="businessName"/></td>
	        <th width="20%">是否启用</th>
	        <td width="30%">
	        	<s:if test="pstatus==1">
	        		启用
	        	</s:if>
	        	<s:if test="pstatus==0">
	        		停用
	        	</s:if>
	        </td>
	      </tr>
	      <tr>
	        <th>流程描述</th>
	        <td colspan="3"><s:property value="pdesc"/></td>
	      </tr>
	    </tbody>
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>步骤信息</span>&nbsp;&nbsp;&nbsp;&nbsp;
	        </tr>
	    </thead>
	    <tbody>
	    	<tr>
	    		<td colspan="4">
	    			<table>
						<thead>
							<tr>
								<td width="200px">步骤名称</td>
								<td width="480px">
									<s:if test="bzlx=='jbry'">
										经办人员
									</s:if>
									<s:if test="bzlx=='jbjs'">
										经办角色
									</s:if>
								</td>
							</tr>
						</thead>
	   				 	<tbody id="tbbbb">
	   				 		<s:iterator id="e" value="model.spNodeList" status="sta">
								<tr name='aaaa'>
									<td>${e.nodeName}</td>
									<td>
										<s:if test="#e.roleId!=null&&#e.roleId!=''">
										${e.roleName}
										</s:if>
										<s:else>
										${e.userName}
										</s:else>
									</td>
								</tr>
							</s:iterator>
	   				 	</tbody>
					</table>
	    		</td>
	    	</tr>	
	    </tbody>
	   
	    <tfoot>
	      <tr>
	        <td colspan="4"><div class="bz">
	        </div>
	          <div class="btn">
	            <button type="button" id="btn_gb" name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	  </table>
  </div>
  
</body>
</s:form>
</html>