<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
<%-- 		<script type="text/javascript" src="<%=systemPath %>/js/jquery/jquery-1.6.2.min.js?_rv_=<%=resourceVersion%>"></script> --%>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjcxUtils.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/kzszUpdate.js?_rv_=<%=resourceVersion%>"></script>
	</head>
	<body>
		<s:form namespace="/zfxg/tjcx" action="kzsz_saveUpdate" id="myForm" method="post" theme="simple">
    	<input type="hidden" id="kzszid" name="kzszid" value="${kzszid}"/>
    	<table width="100%" border="0" class="formlist">
			<thead>
				<tr>
					<th colspan="2">
						<span>快照修改</span>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr name="zmeTr">
					<th width="16%">
						<font color="red">*</font>名称
					</th>
					<td width="34%" >
						<input id="szmc" type="text" name="szmc"  maxlength="20" class="text_nor" value="${szmc}"/>
					</td>
				</tr>
				<tr id="sfgy" >
					<th>
						<font color="red">*</font>是否共享
					</th>
					<td>
						<s:if test="model.sfgy == '1'.toString()">
							<label><input type="radio" name="sfgy" value="0" />私有</label>&nbsp;
							<label><input type="radio" name="sfgy" value="1" checked="checked"/>共享</label>
						</s:if>
						<s:else>
							<label><input type="radio" name="sfgy" value="0" checked="checked"/>私有</label>&nbsp;
							<label><input type="radio" name="sfgy" value="1" />共享</label>
						</s:else>
					</td>
				</tr>
				<s:if test="model.sfgy == '1'.toString()">
					<tr id="kzjsrTr">
						<th width="16%">
							快照接收人
						</th>
						<td width="34%" >
							<textarea name="kzjsr" id="kzjsr" rows="4" style="word-break:break-all;width:97%">${kzjsr}</textarea>
							<font color="red">请输入职工号，多个以逗号分割。若为空，则所有职工均可查看</font>
						</td>
					</tr>
				</s:if>
				<s:else>
					<tr id="kzjsrTr" style="display:none">
						<th width="16%">
							快照接收人
						</th>
						<td width="34%" >
							<textarea name="kzjsr" id="kzjsr" rows="4" style="word-break:break-all;width:97%">${kzjsr}</textarea>
							<font color="red">请输入职工号，多个以逗号分割。若为空，则所有职工均可查看</font>
						</td>
					</tr>
				</s:else>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="2">
						<div class="bz">"<span class="red">*</span>"为必填项</div>
						<div class="btn">
							<button type="button" onclick="updateCxkz();">
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
		</s:form>
</div>

 <s:if test="result != null && result != ''">
  	<script>
  	    var parentPage = getParentDialogWin();
		alertMessage('${result}',function(){
				parentPage.jQuery("#tabGrid").jqGrid().trigger('reloadGrid');
				iFClose();
	  	  		});
  	</script>
  </s:if>
</body>
</html>