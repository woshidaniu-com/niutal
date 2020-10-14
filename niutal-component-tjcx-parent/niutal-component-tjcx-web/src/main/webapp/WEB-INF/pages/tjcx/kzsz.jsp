<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
<%-- 		<script type="text/javascript" src="<%=systemPath %>/js/jquery/jquery-1.6.2.min.js?_rv_=<%=resourceVersion%>"></script> --%>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/kindeditor-min.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/editorParams.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/editor/zh_CN.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjcxUtils.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/kzsz.js?_rv_=<%=resourceVersion%>"></script>
	</head>
	<body>
    	<table width="100%" border="0" class="formlist">
			<thead>
				<tr>
					<th colspan="2">
						<span>快照保存</span>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr name="zmeTr">
					<th width="16%">
						<font color="red">*</font>名称
					</th>
					<td width="50%" >
						<input type="text" name="szmcView"  maxlength="20" class="text_nor" />
					</td>
				</tr>
				<tr id="sfgy" style="display:none">
					<th>
						<font color="red">*</font>是否共享
					</th>
					<td>
						<label><input type="radio" name="sfgyView" value="0" checked="checked"/>私有</label>&nbsp;
						<label><input type="radio" name="sfgyView" value="1" />共享</label>
					</td>
				</tr>
				<tr id="kzjsrTr" style="display:none">
					<th width="16%">
						快照接收人
					</th>
					<td width="50%" >
						<textarea name="kzjsr" id="kzjsr" rows="4" style="word-break:break-all;width:97%"></textarea>
						<font color="red">请输入职工号，多个以逗号分割。若为空，则所有职工均可查看</font>
					</td>
				</tr>
				<tr id="kzmsTr">
					<th width="16%">
						快照描述
					</th>
					<td width="34%" >
						<textarea name="kzms" id="kzms" rows="15" style="word-break:break-all;width:97%"></textarea>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="2">
						<div class="bz">"<span class="red">*</span>"为必填项</div>
						<div class="btn">
							<button type="button" onclick="saveCxkz();">
								保 存
							</button>
							<button type="button" onclick="iFClose();">
								关 闭
							</button>
						</div>
					</td>
				</tr>
			</tfoot>
		</table>
    
</div>

</body>
</html>