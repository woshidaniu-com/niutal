<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
		function save(){
			 ajaxSubFormWithFun('myForm', _path+'/zxzx/cjwt_qytyBcCjwt.html',{},function(data){
					var api = frameElement.api, W = api.opener;
					alertMessage(data, function() {
						this.close();
						W.jQuery("#search_go").click();
						frameElement.api.close();
					});
				});

		}
		</script>
	</head>
	<body>
	<s:form id="myForm" namespace="/zxzx/cjwt" action="qytyBcCjwt" theme="simple">
	 <s:hidden name="wtid" />
	 <div class="tab">
	  <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>常见问题【启用/停用】</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="4"><div class="bz">"<span class="red">*</span>"为必填项</div>
	          <div class="btn">
	            <button name="btn_tj" onclick="save();" type="button">保 存</button>
	            <button name="btn_fh" onclick="iFClose()" type="button">关闭</button>
	          </div>
	        </td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	      	 <th width="25%">咨询主题</th>
	         <td>
		        <s:label name="wtbt"></s:label>
	         </td>
	      </tr>
	      <tr>
	      	 <th width="25%"><span class="red">*</span>启用状态</th>
	         <td>
				<s:radio name="sffb" list="#{'1':'开启','0':'关闭'}" theme="simple"></s:radio>
	         </td>
	      </tr>
	    </tbody>
	  </table>
  </div>
</s:form>
</body>
</html>