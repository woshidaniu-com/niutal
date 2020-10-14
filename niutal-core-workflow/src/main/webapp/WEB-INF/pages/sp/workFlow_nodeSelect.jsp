<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="t" uri="/xg-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
		<%@ include file="/commons/validate.jsp"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>		
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/dateformat.js"></script>
		
		<script type="text/javascript">
			function toSave(type){
				var stid = jQuery("#id").val();
				var url =_path+'/twGrgl/TwSt_bcshxx.html?tgzt=th';
				if (inputResult() &&checkInputNotNull('nodeId')){
					ajaxSubFormWithFun("myForm",url,{},function(data){
						alert(data,{},{"clkFun":function(){
							if(data==''){
								return false;
							}else{
								refershParent();
							}
						}});
					});
				}
			}
			
		</script>
</head>

<s:form  method="post" id="myForm" theme="simple">
	<s:hidden id="id" name="id"></s:hidden>
	<body>
	 <div class="tab">
	  <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
	    <tbody>
	    	<tr>
	        <th><span class="red">*</span>退回节点</th>
	        <td colspan="3">
				<s:select list="resultList" name="nodeId" id="nodeId" listKey="nodeId" listValue="nodeName" headerKey="" headerValue="" cssStyle="width:160px;" theme="simple"></s:select>
			</td>
	      </tr>
	    </tbody>
	    <tfoot>
	      <tr>
	        <td colspan="4"><div class="bz">
	        </div>
	          <div class="btn">
	            <button type="button" id="btn_tg" name="btn_tg" onclick="toSave('pass');">确 定</button>
	            <button type="button" id="btn_gb" name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	  </table>
  </div>
  
</body>
</s:form>
</html>