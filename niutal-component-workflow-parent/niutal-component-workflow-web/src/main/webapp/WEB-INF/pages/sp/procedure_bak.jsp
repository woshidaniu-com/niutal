<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/v5_url.ini"%>	
</head>
<body>
<s:form method="post" id="myForm" theme="simple">
	<s:hidden id="pid" name="pid"></s:hidden>
	<input type="hidden" name="doType" value="save" />
	<div class="tab">
		<table width="100%" border="0" class="formlist" cellpadding="0"
			cellspacing="0">
			<thead>
				<tr>
					<th colspan="4">
						<span>流程信息</span>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th width="20%">
						业务类型
					</th>
					<td width="30%">
						${businessName}
					</td>
					<th width="20%">
						是否启用
					</th>
					<td width="30%">
						${pstatus==1 ? "启用" : "停用" }
					</td>
				</tr>
				<tr>
					<th>
						流程描述
					</th>
					<td colspan="3">
						${pdesc}
					</td>
				</tr>
			</tbody>
		</table>
		<br>
		<table width="100%" border="0" class="formlist" cellpadding="0"
			cellspacing="0">
			<thead>
				<tr>
					<th colspan="13">
						<span>步骤信息</span>&nbsp;&nbsp;&nbsp;&nbsp;
				</tr>
			</thead>
			<tr>
				<td width="25%" style="text-align: center">
					步骤名称
				</td>
				<td width="25%" style="text-align: center">
					经办人选
				</td>
				<td width="25%" style="text-align: center" class="bj">环节标记</td>
				<td width="25%" style="text-align: center" class="">数据范围值条件</td>
				<td width="25%" style="text-align: center" class="">数据范围值条件取值</td>
				<td width="25%" style="text-align: center" class="sendMail">发送邮件</td>
				<td width="25%" style="text-align: center" class="sendSms">发送短信</td>
				<td width="25%" style="text-align: center" class="">站内信内容</td>
				<td width="25%" style="text-align: center" class="sendMail">邮件内容</td>
				<td width="25%" style="text-align: center" class="sendSms">短信内容</td>
				<td width="25%" style="text-align: center" class="">站内信内容(结束)</td>
				<td width="25%" style="text-align: center" class="sendMail">邮件内容(结束)</td>
				<td width="25%" style="text-align: center" class="sendSms">短信内容(结束)</td>
			</tr>
			<tbody id="tbbbb">
				<s:iterator id="e" value="model.spNodeList" status="sta">
					<tr name='aaaa'>
						<td>
							${e.nodeName}
						</td>
						<td>
							${e.bzlx=="jbry"?"经办人员":"经办角色"}：
							<s:if test="#e.roleId!=null&&#e.roleId!=''">
								${e.roleName}
							</s:if>
							<s:else>
								${e.userName}
							</s:else>
						</td>
						<td class="bj">${e.node_bjmc}</td>
						<td class="">${e.sjfwztj}</td>
						<td class="">${e.sjfwztj_qz}</td>
						<td class="sendMail">${e.send_mail=="1"?"是":"否"}</td>
						<td class="sendSms">${e.send_sms=="1"?"是":"否"}</td>
						<td class="">${e.content_mes}</td>
						<td class="sendMail">${e.content_mail}</td>
						<td class="sendSms">${e.content_sms}</td>
						<td class="">${e.content_mes_end}</td>
						<td class="sendMail">${e.content_mail_end}</td>
						<td class="sendSms">${e.content_sms_end}</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
</s:form>
</body>
<script type="text/javascript">
jQuery(function($) {
	//var txyw = ',XJYD_ZZY,FXBM_FX,FXBM_DEZY,FXBM_DEXW,';
	if(txyw.indexOf(",${businessType},") < 0){
		$(".bj").hide();
	}
	if(sendSms){
		$(".sendSms").hide();
	}
	if(sendMail){
		$(".sendMail").hide();
	}
	
});
</script>
</html>