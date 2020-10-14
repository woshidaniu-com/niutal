<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
	<head>
		<%@include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	</head>
	<body>
		<s:form id="systemConfigForm" action="/xtgl/xtsz_xtszXg.html" theme="simple">
			<div class="tab">
			<table class="formlist" width="93%">
				<thead>
					<tr>
						<th colspan="2">
							<span>系统信息<font color="#0f5dc2"
								style="font-weight: normal;"> </font> </span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="2">
							<div class="btn">
								<button id="bnt0a" type="button" onclick="save()">
									<span>保 存</span>
								</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<tr>
						<th >
							学校代码
						</th>
						<td>
							<s:label name="xxdm"></s:label>
						</td>
					</tr>
					<tr>
						<th width="30%">
							学校名称
						</th>
						<td width="70%">
							<s:label name="xxmc"></s:label>
						</td>
					</tr>
					<tr>
						<th>
							当前学年
						</th>
						<td>
							<s:select name="dqxn" list="curStudyYear" listKey="value"
								listValue="text">
							</s:select>
						</td>
					</tr>
					<tr>
						<th>
							当前学期
						</th>
						<td>
							<s:select name="dqxq" list="curStudyScope" listKey="dm"
								listValue="mc">
							</s:select>
						</td>
					</tr>
					<tr>
						<th>
							当前年度
						</th>
						<td>
							<s:select name="dqnd" list="curYear" listKey="value"
								listValue="text">
							</s:select>
						</td>
					</tr>
				</tbody>
			</table>
			</div>
		</s:form>
	</body>
	<script type="text/javascript">
		//扩展自己的方法
		jQuery(function($) {
			$("#ajaxForm").validateForm();
		})
</script>
</html>
