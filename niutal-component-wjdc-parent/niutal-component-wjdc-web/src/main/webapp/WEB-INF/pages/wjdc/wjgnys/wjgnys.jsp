<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/textClue.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/select.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/kindeditor-min.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/editorParams.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/zh_CN.js?_rv_=<%=resourceVersion%>"></script>
		<link rel="stylesheet" href="<%=systemPath %>/css/plugins/textClue.css?_rv_=<%=resourceVersion%>" type="text/css" media="all" />
		<script type="text/javascript">
		//var editor;
		
		function save(){
			 subForm(_path+'/zfxg/wjdc/wjgl_wjgnys.html');
		}
		
		//jQuery(function(){
		//}); 
		</script>
	</head>
	<body>
	<div class="tab_cur">
		<p class="location">
			<em>您的当前位置:</em><a>问卷调查 - 问卷信息 - 问卷功能约束</a>
		</p>
	</div>
	<s:form method="post" theme="simple">
	<s:hidden name="wjid"></s:hidden>
	<input type="hidden"  name="doType" value="update"/>
	 <div class="tab">
	  <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="2"><span>问卷功能约束</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="2"><div class="bz"></div>
	          <div class="btn">
	            <button name="btn_tj" onclick="save();" type="button">保 存</button>
	            <button name="btn_fh" onclick="iFClose()" type="button">关闭</button>
	          </div>
	        </td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <s:iterator value="wjgnysList" id="wjgnys">
	      	<tr>
	      		<td width="10%"><input type="checkbox" name="ysgndms" value="${YSGNDM}" ${CHECKED}/></td>
	      		<td>${YSGNMC}</td>
	      	</tr>
	      </s:iterator>
	    </tbody>
	  </table>
  </div>
  <s:if test="result != null && result != ''">
  	<script defer="defer">
  		alert('${result}','',{'clkFun':function(){
  			refershParent();
  		}});
  	</script>
  </s:if>
</s:form>
</body>
</html>