<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
<script type="text/javascript"
	src="<%=systemPath%>/js/globalweb/comm/validate.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript"
	src="<%=systemPath%>/js/globalweb/comm/inputPrompt.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript">
	function save() {
		if (checkInputNotNull('mc')) {
			subForm('jcsj_xgBcJcsj.html')
		}
	}
</script>
</head>


<s:form action="/xtgl/jcsj_bcxgJcsj.html" id="myForm" method="post"
	theme="simple">
	<body>
		<input type="hidden" id="pkValue" name="pkValue"
			value="<e:forHtmlAttribute value="${lxdm}"/><e:forHtmlAttribute value="${dm}"/>" />
		<input type="hidden" id="lxdm" name="lxdm"
			value="<e:forHtmlAttribute value="${lxdm}"/>" />
		<input type="hidden" id="dm" name="dm"
			value="<e:forHtmlAttribute value="${dm}"/>" />
		<input type="hidden" id="lxmc" name="lxmc"
			value="<e:forHtmlAttribute value="${lxmc}"/>" />
		<div class="tab">
			<table width="100%" border="0" class=" formlist" cellpadding="0"
				cellspacing="0">
				<thead>
					<tr>
						<th colspan="4"><span>修改基础数据</span></th>
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
						<th width="30%"><span class="red">*</span>类型</th>
						<td width="70%"><e:forHtmlAttribute value="${lxmc}" /></td>
					</tr>
					<tr>
						<th width="30%"><span class="red">*</span>代码</th>
						<td width="70%"><e:forHtmlAttribute value="${dm}" /></td>
					</tr>
					<tr>
						<th width="30%"><span class="red">*</span>名称</th>
						<td width="70%"><s:textfield maxlength="20" name="mc" id="mc"
								cssStyle="width:154px"></s:textfield></td>
					</tr>

				</tbody>
			</table>
		</div>
		<s:if test="result != null && result != ''">
			<script>
				var api = frameElement.api, W = api.opener;
				alertMessage('${result}', function() {
					this.close();
					W.jQuery("#search_go").click();
					api.close();

				});
			</script>
		</s:if>
	</body>
</s:form>
</html>