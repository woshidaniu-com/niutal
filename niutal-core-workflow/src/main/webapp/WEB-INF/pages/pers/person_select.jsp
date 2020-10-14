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
	        	<th colspan="4"><span>经办人员信息</span></th>
	        </tr>
	    </thead>
	    <tbody>
	    	<tr>
				<th>
					<input type="radio" id="cdbm" onclick="showSpan('abm');" name="condition1" value="abm" checked="true"/>按部门
					<input type="radio" id="cdjs" onclick="showSpan('ajs');" value="ajs" name="condition1"/>按角色
					<input type="radio" id="cdgh" onclick="showSpan('agh');" value="agh" name="condition1"/>按工号/学号
					<input type="radio" id="cdxm" onclick="showSpan('axm');" value="axm" name="condition1"/>按姓名
				</th>
				<td colspan="2" valign="center">
					<span id="bmspan">
						<s:select list="bmList" name="bm" id="bm" listKey="bmdm_id" listValue="bmmc" headerKey="" headerValue="" theme="simple" ></s:select>
					</span>
					<span id="jsspan" style="display:none">
						<s:select list="jsList" name="js" id="js" listKey="jsdm" listValue="jsmc" headerKey="" headerValue="" theme="simple"></s:select>
					</span>
					<span id="ghxmspan" style="display:none">
						<s:textfield maxlength="25" name="ghxm" id="ghxm"></s:textfield>
					</span>
                   <button type="button" id="search_go" onclick="queryUser();return false;">
						查询
				   </button>
	            </td>
			</tr>
	    </tbody>
	    <tbody>
	      <tr>
	        <td width="45%" align="center">
	        	<select id="fromRole" name="fromRole" size="20" multiple="true" style="width:180px;"></select>
	        </td>
	        <td width="10%" align="center">
	        	<button type="button" id="btn_lft" name="btn_lft" onclick="toLeft();">-&gt;</button>
	        	<br/><br/>
	        	<button type="button" id="btn_rgt" name="btn_rgt" onclick="toRight();">&lt;-</button>
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
	    <tfoot>
	      <tr>
	        <td colspan="4"><div class="bz">点击条目时，可以组合CTRL或SHIFT键进行多选
	        </div>
	          <div class="btn">
	            <button type="button" id="btn_bc" name="btn_bc" onclick="toSave();">保 存</button>
	            <button type="button" id="btn_gb" name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	  </table>
  </div>
</body>
</s:form>
</html>