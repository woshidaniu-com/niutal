<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/WEB-INF/pages/zxzx/web/zxzx_pagehead.ini"%>
<script type="text/javascript">
	function refreshCode(){
		document.getElementById("yzmPic").src = _path + '/kaptcha?time=' + new Date().getTime();
	}
	
	jQuery(function(){
		jQuery("#askQuestionSubmitBtn").click(function(){
			var kzdt = jQuery("#kzdt"),zxnr = jQuery("#zxnr"), bkdm = jQuery("#bkdm"), yzm = jQuery("#yzm");
			var valid = true;
			var kzdtval = jQuery.trim(kzdt.val());
			var zxnrval = jQuery.trim(zxnr.val());
			var bkdmval = jQuery.trim(bkdm.val());
			var yzmval = jQuery.trim(yzm.val());
			if(kzdtval == ""){
				kzdt.css("background-color","#FBE7E7");
				valid = false;
			}
			if(zxnrval == ""){
				zxnr.css("background-color","#FBE7E7");
				valid = false;
			}
			if(bkdmval == ""){
				bkdm.css("background-color","#FBE7E7");
				valid = false;
			}
			if(yzmval == ""){
				yzm.css("background-color","#FBE7E7");
				valid = false;
			}
			return valid;
		});
	});
	
</script>
<s:head />
</head>
<body style="">
	<div class="mainbody-newtopic">
		<div class="mainframe mainframe_qz">
			<div class="establish_qz">
				<s:actionerror />
				<s:actionmessage />
				<h3>
					<span><em>创建问题</em></span>
					<p>
						<font color="red">*</font>为必填
					</p>
				</h3>
				<s:form name="newtopicForm" namespace="/zxzx/web" action="newTopicSubmit" theme="simple" id="newtopicForm">
					<s:token />
					<table width="100%" border="0">
						<tr>
							<th width="20%"><font color="red">*</font>标题</th>
							<td>
							<s:textfield cssClass="input" name="kzdt" id="kzdt" maxLength="200"></s:textfield>
							<span class="prompt"><font color="orange">1-200</font>字符</span></td>
						</tr>
						<tr>
							<th><font color="red">*</font>版块类别</th>
							<td>
							<s:select list="kzdkList" id="bkdm" name="bkdm" listKey="bkdm" listValue="bkmc" headerKey="" headerValue="======  选	  择	 =====" ></s:select>
							</td>
						</tr>
						<tr>
							<th><font color="red">*</font>咨询内容</th>
							<td>
								<s:textarea name="zxnr" id="zxnr" cols="" rows="5"></s:textarea>
							</td>
						</tr>
						<tr>
							<th><font color="red">*</font>验证码</th>
							<td>
								<s:textfield cssClass="yzm" name="yzm" id="yzm"></s:textfield>
								<img border="0" align="absmiddle" width="75px" height="25px" id="yzmPic" class="yzmPic" onclick="javascript:refreshCode();" name="yzmPic" src="<%=systemPath%>/kaptcha" >
							</td>
						</tr>
					</table>
					<div class="btn">
						<button id="askQuestionSubmitBtn" style="cursor: pointer">提交</button>
					</div>
				</s:form>
			</div>
		</div>
	</div>
</body>
</html>
