<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
<script type="text/javascript"
	src="<%=systemPath%>/js/globalweb/comm/validate.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript"
	src="<%=systemPath%>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript"
	src="<%=systemPath%>/js/globalweb/comm/inputPrompt.js?_rv_=<%=resourceVersion%>"></script>

<script type="text/javascript">
	function save() {
		var bkmc = $('#bkmc').val();
		var sfqy = $('input:radio[name=sfqy]:checked').val();
		if (inputResult() && checkInputNotNull('bkmc')) {
			ajaxSubFormWithFun('myForm', _path+'/zxzx/kzdk_zjBckzdk.html',{},function(data){
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


<s:form action="kzdk_zjBckzdk" id="myForm" method="post" namespace="/zxzx" theme="simple">
	<body>
		<div class="tab">
			<table width="100%" border="0" class=" formlist" cellpadding="0"
				cellspacing="0">
				<thead>
					<tr>
						<th colspan="4"><span>增加咨询板块</span></th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="4"><div class="bz">
								"<span class="red">*</span>"为必填项
							</div>
							<div class="btn">
								<button name="btn_tj" onclick="save();return false;">保
									存</button>
								<button name="btn_gb" onclick="iFClose();return false;">关
									闭</button>
							</div></td>
					</tr>
				</tfoot>
				<tbody>
					<tr>
						<th width="30%"><span class="red">*</span>板块名称</th>
						<td width="70%"><s:textfield maxlength="100" name="bkmc" id="bkmc" cssStyle="width:254px"></s:textfield></td>
					</tr>
					<tr>
						<th width="30%"><span class="red">*</span>是否启用</th>
						<td width="70%">
							<s:radio list="#{'1':'开启','0':'关闭'}" name="sfqy" id="sfqy" value="1" theme="simple"></s:radio>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</body>
</s:form>
</html>