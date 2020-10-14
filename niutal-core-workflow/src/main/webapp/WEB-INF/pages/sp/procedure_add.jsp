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
		<script type="text/javascript" src="<%=systemPath %>/js/sp/spSetting.js"></script>
		
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
	        <th width="20%"><span class="red">*</span>流程名称</th>
	        <td width="80%" colspan="3"><s:textfield maxlength="25" name="pname" id="pname" cssStyle="width:420px" ></s:textfield></td>
	      </tr>
	      <tr>
	        <th width="20%">业务类型</th>
	        <td width="30%">
	        	<s:select list="bTypeList" name="businessType" id="businessType" listKey="YWDM" listValue="YWMC" headerKey="" headerValue="" theme="simple"></s:select>
	        </td>
	        <th width="20%"><span class="red">*</span>是否启用</th>
	        <td width="30%">
	        	<s:select list="#{1:'启用',0:'停用'}" name="pstatus" id="pstatus" listKey="key" listValue="value"  theme="simple"></s:select>
	        </td>
	      </tr>
	      <tr>
	        <th><span class="red">*</span>流程描述</th>
	        <td colspan="3"><s:textarea name="pdesc" id="pdesc" rows="3" onfocus="showRightPrompt(this,'填写流程描述，最大不能超过80个字');" 
	        onblur="checkTextareaLength('pdesc','流程描述',160);" cssStyle="width:500px;height:60px;"></s:textarea><br/>
			&nbsp; </td>
	      </tr>
	    </tbody>
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>步骤信息</span>&nbsp;&nbsp;&nbsp;&nbsp;
	        	<a href="#" onclick="addRow();"><img src="<%=systemPath %>/images/add-icons-1.gif" alt="增加" /></a>&nbsp;
	        	<a href="#" onclick="delRow();"><img src="<%=systemPath %>/images/delete-icons-2.gif" alt="删除" /></a>&nbsp;
	        	<a href="#" onclick="upRow();"><img src="<%=systemPath %>/images/up-icons-3.gif" alt="上移" /></a>&nbsp;
	        	<a href="#" onclick="downRow();"><img src="<%=systemPath %>/images/down-icons-4.gif" alt="下移" /></a>
	        </tr>
	    </thead>
	    <tbody>
	    	<tr>
	    		<td colspan="4">
	    			<table>
						<thead>
							<tr>
								<td width="20px"></td>
								<td width="200px">步骤名称</td>
								<td width="480px">
									<s:if test="bzlx=='jbry'">
										<input type="radio" value='jbry' name="bzlx" onclick="resetRy();" checked="true"/>经办人员
										<input type="radio" value='jbjs' name="bzlx" onclick="resetRy();"/>经办角色
									</s:if>
									<s:if test="bzlx=='jbjs'">
										<input type="radio" value='jbry' name="bzlx" onclick="resetRy();"/>经办人员
										<input type="radio" value='jbjs' name="bzlx" onclick="resetRy();" checked="true"/>经办角色
									</s:if>
								</td>
							</tr>
						</thead>
	   				 	<tbody id="tbbbb">
	   				 		<s:iterator id="e" value="model.spNodeList" status="sta">
								<tr name='aaaa'>
									<td align='center'><input type='checkbox' name='ckr' /></td>
									<td><input type='text' name='bzname' size='26' value="${e.nodeName}"/><font color='red'>*</font></td>
									<td>
										<s:if test="#e.roleId!=null&&#e.roleId!=''">
										<input type='hidden' name='zhuangtai' value='0' /><input type='hidden' name='personIds' value='${e.roleId}' /><input type='text' name='persons' value='${e.roleName}' size='65' onclick='showPerson(this);' readonly='true'/><font color='red'>*</font>
										</s:if>
										<s:else>
										<input type='hidden' name='zhuangtai' value='0' /><input type='hidden' name='personIds' value='${e.userId}' /><input type='text' name='persons' value='${e.userName}' size='65' onclick='showPerson(this);' readonly='true'/><font color='red'>*</font>
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
	        <td colspan="4"><div class="bz">"<span class="red">*</span>"为必填项，步骤信息不能为空
	        </div>
	          <div class="btn">
	            <button type="button" id="btn_bc" name="btn_bc" onclick="toSaveSp();">保 存</button>
	            <button type="button" id="btn_gb" name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	  </table>
  </div>
  
</body>
</s:form>
</html>