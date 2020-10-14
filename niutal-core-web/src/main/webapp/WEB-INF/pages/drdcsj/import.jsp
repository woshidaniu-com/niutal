<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript"
			src="<%=systemPath %>/js/drdcsj/import.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript"
			src="<%=systemPath %>/js/globalweb/comm/message.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript"
			src="<%=systemPath %>/js/jquery/upload/ajaxfileupload.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
			jQuery(function(){
				showRule();
				//隐藏导入结果
				jQuery('#dr_result').hide();
			});
		</script>
	</head>
	<body>
		<s:form name="importForm" id="importForm"
			action="niutal/dr/import_importData.html" method="post"
			enctype="multipart/form-data" theme="simple">
			<input type="hidden" id="drlpzs" name="drlpzs" />
			<input type="hidden" id="drlpzids" name="drlpzids" />
			<input type="hidden" id="crfs" name="crfs" />
			<input type="hidden" id="drzx" name="drzx" />
			<input type="hidden" id="cwts" name="cwts" />
			<div class="tab">
				<table width="100%" border="0" class=" formlist" cellpadding="0"
					cellspacing="0">
					<thead>
						<tr>
							<td colspan="4">
								<span>导入数据</span>
							</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th width="20%">
								模板名称
							</th>
							<td colspan="3">
								<s:if test="importList != null">
									<s:if test="%{importList.size() == 1}">
										<s:property value="importList[0].drmkmc" />
										<s:hidden id="drmkdm" name="drmkdm" type="hidden"
											value="%{importList[0].drmkdm}" />
									</s:if>
									<s:else>
										<s:select list="importList" listKey="drmkdm"
											listValue="drmkmc" name="drmkdm" id="drmkdm" headerKey=""
											headerValue="请选择" cssStyle="width:150px;"></s:select>
									</s:else>
								</s:if>
							</td>
						</tr>
						<tr>
							<th>
								模板下载
							</th>
							<td colspan="3">
								<a href="javascript:void(0);" class="name"
									onclick="downloadTemplate();">下载EXCEL模板</a> &nbsp;&nbsp;
								<a href="javascript:void(0);" class="name" style="display:none;"
									onclick="downloadTemplate('dbf');">下载DBF模板</a>
							</td>
						</tr>
						<tr>
							<th>
								上传数据
							</th>
							<td colspan="3">
								<input type="file" name="importFile" id="importFile"
									style="width: 200px;" />
								<span style="color:red;font-style:normal"><span>提示：</span><span>允许导入Excel和DBF文件</span></span>
							</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="4">
								<div class="btn">
									<button id="drBtn" onclick="selectImport();" type="button" class="ui_state_highlight">
										下一步
									</button>
								</div>
							</td>
						</tr>
					</tfoot>
				</table>
				<table width="100%" border="0" class=" formlist" cellpadding="0" id="dr_result"
					style="margin-top: 2px;" cellspacing="0">
					<%--<thead>
						<tr>
							<td colspan="4">
								<span>导入结果</span>
							</td>
						</tr>
					</thead>
					--%><tbody>
						<tr>
							<th width="20%">
								导入结果
							</th>
							<td colspan="3" id="importResult" style="font-weight: bold">
								&nbsp;&nbsp;无
							</td>
						</tr>
						<tr>
							<th>
								异常数据
							</th>
							<td colspan="3" id="cwsj">
								<font>无错误数据<font/>
							</td>
						</tr>
					</tbody>
				</table>
				<div id="drts">
					<div id="rule_header">
						<table width="100%" border="0" class=" formlist" cellpadding="0" style="margin-top: 2px;" 
						cellspacing="0" align="center">
						<thead>
							<tr>
								<td colspan="6">
									<span>导入规则</span>
								</td>
							</tr>
							<tr style="background-color: #FFFACD;font-weight: bold">
								<td align='right'  style="width: 15%" id="rule_header_1">
									列名称
								</td>
								<td align='center' style="width:10%"  id="rule_header_2">
									主键
								</td>
								<td align='center'  style="width:10%"  id="rule_header_3">
									是否唯一
								</td>
								<td align='center'  style="width:10%"  id="rule_header_4">
									不可为空
								</td>
								<td align='center'  style="width:10%"  id="rule_header_5">
									最大长度
								</td>
								<td align='center'  style="width:55%"  id="rule_header_6">
									数据格式
								</td>
							</tr>
						</thead>
					</table>
					</div>
					<div id="rule_div" style="">
						<table width="100%" border="0" class=" formlist" cellpadding="0" style frame="vsides"
						cellspacing="0" align="center">
							<tbody></tbody>
						</table>
					</div>
				</div>
			</div>
			<s:if test="#parameters.closeBtn == null or #parameters.closeBtn[0] == null or #parameters.closeBtn[0] == 'yes'">
				<div style="height: 30px;"></div>
				<div>
					<table width="100%" border="0" class="formlist" id="below"
						style="position: fixed; _position: absolute; bottom: 0;">
						<tfoot>
							<tr>
								<td colspan="2">
									<div class="btn">
										<button name="btn_tj" onclick="iFClose();" type="button">
											关 闭
										</button>
									</div>
								</td>
							</tr>
						</tfoot>
					</table>
				</div>
			</s:if>
		</s:form>
	</body>
</html>
