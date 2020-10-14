<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="s" uri="/struts-tags"%>
  <head>
    <title>角色切换</title>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
	<script type="text/javascript">
	</script>
  </head>
  <body>
	<s:form method="post" action="yhgl_bcQhjs.html" theme="simple">
	<s:token />
  	<div class="tab">
  	<table width="100%" border="0" class="formlist">	
		<thead>
			<tr>
				<th colspan="2"><span>角色切换</span></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th align="right" style="width:23%">
					当前角色
				</th>
				<td>
				 	<e:forHtmlContent value="${curJsglModel.jsmc}"/>
				</td>
			</tr>
			<tr>
				<th align="right">
					您要切换为
				</th>
				<td>
					<s:select list="yhjsxxList" id="jsdm" name="jsdm" listKey="jsdm" listValue="jsmc"></s:select>
				</td>
			</tr>
	    </tbody>
	    <tfoot>
	    	<tr>
	    		<td colspan="2">
	    			<div class="btn">
					<button type="button" class="" onclick="subForm('yhgl_bcQhjs.html');return false;">
							切换
					</button>
					<button type="button" class="" onclick="iFClose();return false;">
							关闭
					</button>
	    			</div>
	    		</td>
	    	</tr>
	    </tfoot>
	    </table>
    </div>
   </s:form>
  </body>
</html>
<s:if test="result != null && result != ''">
  	<script>
  		var api = frameElement.api,W = api.opener;
		W.document.location.href = "${pageContext.request.contextPath}/xtgl/index_initMenu.html";
  	</script>
  </s:if>
