<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>		
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
			function displayGwjbdm() {
				var sfksc = $('#sfksc').val();
				if (sfksc =='1') {
					$('#gwjbdm').attr("disabled",true);
				}
			}
		</script>
</head>

<s:form action="/xtgl/jsgl_xgJsxx.html" method="post" theme="simple">
	<input type="hidden" name="jsdm" value="<e:forHtmlAttribute value="${model.jsdm }"/>"/>
	<input type="hidden" name="sfksc" id="sfksc" value="<e:forHtmlAttribute value="${model.sfksc }"/>"/>
	<input type="hidden" name="bkscgwjbdm" value="<e:forHtmlAttribute value="${model.gwjbdm }"/>"/>
	<body onload="displayGwjbdm()">

     <input type="hidden" name="doType" value="save"/>       
	 <div class="tab">
	  <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>修改角色</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="4"><div class="bz">"<span class="red">*</span>"为必填项
	        <s:if test="model.yhnum != null && model.yhnum !='' && model.yhnum !=0"><font color="red">该角色下面已分配用户，不能再进行修改</font></s:if>
	        </div>
	          <div class="btn">
	          	<s:if test="model.yhnum ==0">
	            	<button name="btn_tj" onclick="if (inputResult() &&checkInputNotNull('jsmc')) subForm('jsgl_xgBcJsxx.html');return false;">保 存</button>
	            </s:if>	
	            <button name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	        <th width="20%"><span class="red">*</span>角色名称</th>
	        <td width="80%"><s:textfield maxlength="20" name="jsmc" id="jsmc" cssStyle="width:145px"  onkeydown="if(pressEnter(event)){return false};" ></s:textfield> 
	        </td>
	      </tr>
	      <tr>
	        <th>角色说明</th>
	        <td><s:textarea name="jssm" id="jssm" rows="4" onfocus="showRightPrompt(this,'对角色进行描述，最大不能超过1000字符');" 
	        onblur="checkTextareaLength('jssm','角色说明',2000);" cssStyle="width:220px;height:80px;"></s:textarea><br/>
			&nbsp; </td>
	      </tr>
	      <s:if test="flag==true">
	      		<input type="hidden" id="sfejsq" name="sfejsq" value="0"/>
	      </s:if>
	      <s:else>
		      <tr>
		        <th width="20%">二级授权${test }</th>
		        <td width="80%">
					<s:radio list="#{'0':'不允许','1':'允许'}" id="sfejsq" name="sfejsq" ></s:radio>
				</td>
		      </tr>
	      </s:else>	      
		  <tr id="_gnmkdm">
		      <th width="20%">功能模块</th>
		      <td width="80%">
			 	<s:select id="gnmkdm" name="gnmkdm" list="gnmkList" listKey="gnmkdm" listValue="gnmkmc" headerKey="" headerValue="全部" theme="simple" cssStyle="width:120px;"></s:select>
			  </td>
		  </tr>
	    </tbody>
	  </table>
  </div>
  <s:if test="result != null && result != ''">
  <script>
	 alert('${result}', '', {
		'clkFun' : function() {
			refershParent();
		}
	});
  </script>
 </s:if>
</body>
</s:form>
</html>