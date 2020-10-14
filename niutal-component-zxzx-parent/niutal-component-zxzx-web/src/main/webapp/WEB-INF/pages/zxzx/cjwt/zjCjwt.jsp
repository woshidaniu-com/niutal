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
			 if (inputResult() && checkInputNotNull('wtbt!!wtnr!!wthf')){
				 ajaxSubFormWithFun('myForm', _path+'/zxzx/cjwt_zjBcCjwt.html',{},function(data){
						var api = frameElement.api, W = api.opener;
						alertMessage(data, function() {
							this.close();
							W.jQuery("#search_go").click();
							frameElement.api.close();
						});
					});
			 }
		}
		</script>
	</head>
	<body>
	<s:form id="myForm" namespace="/zxzx/cjwt" action="zjBcCjwt" theme="simple">
	 <div class="tab">
	  <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>常见问题</span></th>
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
	      	 <th width="15%"><span class="red">*</span>咨询主题</th>
	         <td>
		        <s:textfield maxlength="250" name="wtbt" id="wtbt" cssStyle="width:76%" theme="simple"></s:textfield> 
	         </td>
	      </tr>
	      <tr>
	      	 <th width="15%"><span class="red">*</span>咨询版块</th>
	         <td>
		        <s:select list="bkdmList" listKey="bkdm" listValue="bkmc" name="bkdm" theme="simple" cssStyle="width:38%"></s:select>
	         </td>
	      </tr>
	      <tr>
	      	 <th width="15%"><span class="red">*</span>启用状态</th>
	         <td>
				<s:radio name="sffb" list="#{'1':'开启','0':'关闭'}" value="1" theme="simple"></s:radio>
	         </td>
	      </tr>
	      <tr>
	        <th colspan="1"><span><span class="red">*</span>咨询内容<font color="red">(限1000字)</font></span></th>
	    	<td colspan="3">
	    		<s:textarea name="wtnr" id="wtnr" cssStyle="width:100%;height:100px" maxlength="1000"></s:textarea>
	    	</td>
	       </tr>
	       <tr>
	          <th colspan="1"><span><span class="red">*</span>回复内容<font color="red">(限1000字)</font></span></th>
	    	  <td colspan="3">
	    		<s:textarea name="wthf" id="wthf" cssStyle="width:100%;height:100px" maxlength="1000"></s:textarea>
	    	  </td>
	        </tr>
	    </tbody>
	  </table>
  </div>
</s:form>
</body>
</html>