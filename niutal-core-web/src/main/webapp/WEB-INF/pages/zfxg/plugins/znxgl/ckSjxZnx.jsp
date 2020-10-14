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
			//回复
			function hf(){
				jQuery("#ckSjxFrom").submit();
				return false;
			}
		</script>
	</head>

<body>
<s:form action="/zfxg!plugins/znxgl_hfZnx.html" id="ckSjxFrom" name="newForm" method="post" theme="simple">
	 <s:hidden name="fsbh" id="fsbh"/>
	 <input type="hidden" value="${fsrzgh }" name="jsrzghs"/>
	 <div class="tab">
	  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="2"><span>查看消息</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="2">
	          <div class="btn">
	            <button name="btn_tj" type="button" onclick="hf();">回复</button>
	            <button name="btn_gb" type="button" onclick="iFClose();return false;">关 闭</button>
	          </div>
	        </td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	        <th width="15%"><span class="red"></span>发送人</th>
	        <td width="85%">
	        	${fsrxm } 
	       </td>
	     
	      </tr>
	      
	       <tr>
	        <th><span class="red"></span>主题</th>
	        <td>${zt }
	        	<s:hidden name="zt"></s:hidden>
	        </td>
	    
	      </tr>
	      
	       <tr>
	        <th><span class="red"></span>发送内容</th>
	        <td style="word-break:break-all;">
	        	<div style="width: 660px; height: 400px; overflow: auto;">
	        	${fsnr }
	        	</div>
	        </td>
	        </tr>
	      
	    </tbody>
	  </table>
  </div>
</s:form>
</body>
</html>