<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>密码修改</title>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comm/checkPassword.js?_rv_=<%=resourceVersion%>"></script>
	<script type="text/javascript">
		//保存
		function formSubmit(){
			var jqYmm = jQuery("#ymm");
			var jqMm = jQuery("#mm");
			var jqNmm = jQuery("#nmm");
			
			var ymm = jqYmm.val();
			var mm = jqMm.val();
			var nmm = jqNmm.val();
			
			if (jQuery.trim(ymm) ==''){
				showRightError(jqYmm,'请输入原密码!',3);
				return false;
			}
			
			if (jQuery.trim(mm) ==''){
				showDownError(jqMm,'请输入新密码!',2);
				return false;
			}
			
			if (jQuery.trim(nmm) ==''){
				showRightError(jqNmm,'请输入重复密码!',1);
				return false;n
			}
			
			if (inputResult() && checkInputNotNull('ymm!!mm!!nmm')){ 
				jQuery('#xgMmForm').ajaxSubmit({
					type     : "post",
					dataType : "json",
					success  : function (data) {
			            alert(data.message, function(){
			            	if(data.ok == 'true'){
			            		window.parent.document.getElementById("tologin").click();
			            	}
			            });
			        }
				});
			}
		}

		//验证密码
		function checkMm(obj){
			if(!checkMmStrength(obj)){
				//验证错误返回
				return false;
			}

			if(!checkYhmAndMm()){
				//验证错误返回
				return false;
			}

			if(!checkNmm(obj)){
				//验证错误返回
				return false;
			}
			
		}

		//验证密码
		function checkMmStrength(obj){
			var isNotnulll = true;
			var mm = jQuery('#mm').val();
			if (mm == null || mm == '') {
				hideDownError();
			} else {
				var S_level = checkStrong(mm);
				jQuery("#yhmmdj").val(S_level);
				
				switch (S_level) {
				case 0:
					showDownError(obj,"密码长度不能为空并且不小于6位！!",2);
					isNotnulll =  false;
					break;
				case 1:
					showDownError(obj,"密码强度太弱,请修改!",2);
					isNotnulll =  false;
					break;
				default:
					hideDownError();
				}
			}
			return isNotnulll;
		}
		
		//验证重复输入密码
		function checkNmm(){
			var obj = document.getElementById("nmm");
			var mm = jQuery('#mm').val();
			var nmm = jQuery('#nmm').val();
			 if(mm!=nmm){
				showRightError(obj,'两次密码输入不一致！',1);
				return false;
			}else{
				hideRightError(obj);
				return true;
			}
		}
		

		
		//显示错误信息
		function showErrMsg(errorMsg){
			var mm = document.getElementById("login_errorMsg");
			mm.style.display="block";
			mm.innerHTML=errorMsg;
		}

		//隐藏错误信息
		function hideErrMsg(){
			var mm = document.getElementById("login_errorMsg");
			mm.style.display="none";
			mm.innerHTML="";
		}


		//验证用户密码 ,用户名和密码正确 ：true，不正确false
		function checkYhMm(zgh,mm){
			var url = _path+"/xtgl/yhgl_ajaxCheckYhMm.html";
			var map = {"zgh":zgh,"mm":mm};
			var result = null;
			jQuery.ajaxSetup({async : false});
			jQuery.post(url,map,function(data){
				result = data;
			},"json");
			jQuery.ajaxSetup({async : true});
			return result;
		}

		//验证密码
		function checkYmm(){
			var jqYmm = jQuery("#ymm");
			var zgh = jQuery("#zgh").val();
			var result = checkYhMm(zgh,jqYmm.val());
			if(!result){
				showRightError(jqYmm,"用户原密码不正确!");
			}else{
				hideRightError(jqYmm);
			}
		}

		//隐藏密码错误提示信息
		function hideMmError(obj){
			hideDownError(obj);
			setTimeout(function(){
				var mmObj=document.getElementById("mm");
				var mm = mmObj.value;
				mmObj.value="";
				mmObj.focus();
				mmObj.value=mm;
			}, 500);
		}

		//验证用户名称和密码
		function checkYhmAndMm(){
			var jqZgh = jQuery("#zgh");
			var jqMm = jQuery("#mm"); 
			
			var zgh=jqZgh.val();
			var mm=jqMm.val();
			if(zgh == mm){
				showDownError(jqMm,"用户名和密码不能相同,请修改!",2);
				return false;
			}else{
				hideDownError(jqMm);
				return true;
			}
		}

		/*****************弱密码验证 start*****************/
		//密码强度验证
		var ratingMsgs = new Array(6);
		var ratingMsgColors = new Array(6);
		var barColors = new Array(6);
		ratingMsgs[0] = "弱";
		ratingMsgs[1] = "弱";
		ratingMsgs[2] = "中";
		ratingMsgs[3] = "强";
		ratingMsgs[4] = "强";
		ratingMsgs[5] = "未评级"; // 假如出现无法检测的状况
		ratingMsgColors[0] = "#aa0033";
		ratingMsgColors[1] = "#aa0033";
		ratingMsgColors[2] = "#f5ac00";
		// ratingMsgColors[3] = "#6699cc";
		ratingMsgColors[3] = "#093";
		ratingMsgColors[4] = "#093";
		ratingMsgColors[5] = "#676767";
		barColors[0] = "#aa0033";
		barColors[1] = "#aa0033";
		barColors[2] = "#ffcc33";
		// barColors[3] = "#6699cc";
		barColors[3] = "#093";
		barColors[4] = "#093";
		barColors[5] = "#676767";
		var che=0;
		var min_passwd_len = 6; 

		//弱密码验证
		function CreateRatePasswdReq(pwd) {
			if (!isBrowserCompatible) {
				return;
			}

			// if(!document.getElementById) return false;
			// var pwd = document.getElementById("xkl");
			if (!pwd)
				return false;
			passwd = pwd.value;
			if (passwd.length < min_passwd_len) {
				if (passwd.length > 0) {
					DrawBar(0);
				} else {
					ResetBar();
				}
			} else {
				// 在长度检测后，检测密码组成复杂度
				rating = checkStrong(passwd);
				che = rating;
				DrawBar(rating);

			}
		}

		function getElement(name) {
			if (document.all) {
				return document.all(name);
			}
			return document.getElementById(name);
		}

		function DrawBar(rating) {
			var posbar = getElement('posBar');
			var negbar = getElement('negBar');
			var passwdRating = getElement('passwdRating');
			var barLength = getElement('passwdBar').width;
			if (rating >= 0 && rating <= 4) { // We successfully got a rating
				if (rating == 0) {
					posbar.style.width = barLength / 4 + "px";
					negbar.style.width = barLength / 4 * (3 - rating) + "px";
				} else {
					posbar.style.width = barLength / 4 * rating + "px";
					negbar.style.width = barLength / 4 * (4 - rating) + "px";
				}
			} else {
				posbar.style.width = "0px";
				negbar.style.width = barLength + "px";
				rating = 5; // Not rated Rating
			}
			posbar.style.background = barColors[rating];
			passwdRating.innerHTML = "<font color='" + ratingMsgColors[rating] + "'>"
					+ ratingMsgs[rating] + "</font>";
		}

		/* Checks Browser Compatibility */
		var agt = navigator.userAgent.toLowerCase();
		var is_op = (agt.indexOf("opera") != -1);
		var is_ie = (agt.indexOf("msie") != -1) && document.all && !is_op;
		var is_mac = (agt.indexOf("mac") != -1);
		var is_gk = (agt.indexOf("gecko") != -1);
		var is_sf = (agt.indexOf("safari") != -1);
		// Resets the password strength bar back to its initial state without any
		// message showing.
		function ResetBar() {
			var posbar = getElement('posBar');
			var negbar = getElement('negBar');
			var passwdRating = getElement('passwdRating');
			var barLength = getElement('passwdBar').width;
			posbar.style.width = "0px";
			negbar.style.width = barLength + "px";
			passwdRating.innerHTML = "";
		}

		function gff(str, pfx) {
			var i = str.indexOf(pfx);
			if (i != -1) {
				var v = parseFloat(str.substring(i + pfx.length));
				if (!isNaN(v)) {
					return v;
				}
			}
			return null;
		}	

		function Compatible() {
			if (is_ie && !is_op && !is_mac) {
				var v = gff(agt, "msie ");
				if (v != null) {
					return (v >= 6.0);
				}
			}
			if (is_gk && !is_sf) {
				var v = gff(agt, "rv:");
				if (v != null) {
					return (v >= 1.4);
				} else {
					v = gff(agt, "galeon/");
					if (v != null) {
						return (v >= 1.3);
					}
				}
			}
			if (is_sf) {
				var v = gff(agt, "applewebkit/");
				if (v != null) {
					return (v >= 124);
				}
			}
			return false;
		}	

		/* We also try to create an xmlhttp object to see if the browser supports it */
		var isBrowserCompatible = Compatible();
		/*****************弱密码验证 end*****************/
	</script>
	
	<style>
	.resetCss tr{
		border-collapse: collapse;
		border-spacing: 0;
		font:inherit;
		list-style: none;
	}
	
	.resetCss tr td{
		border: 0;
		padding: 0;
		vertical-align: 0px;
		
	}
	</style>
  </head>
  
  <body>
  	<div class="prompt">
		<h3>
			<span>系统提示：</span>
		</h3>
		<p>
			<font color="red">
				新密码长度为6~16位，至少包含数字、特殊符号、<br />英文字母(大、小写)中的两类。
			</font>
		</p>
		<a class="close" title="隐藏" onclick="this.parentNode.style.display='none';"></a>
	</div>
	<s:form method="post" action="yhgl_xgMm.html" theme="simple" id="xgMmForm">
	 <input type="hidden" name="zgh" id="zgh" value="${zgh }"/>
	 <input type="hidden" name="doType" value="save"/>  
	 <input type="hidden" name="yhmmdj" id="yhmmdj"/> 
  	<table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">	
		<thead>
			<tr>
				<th colspan="2"><span>密码修改</span></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th align="right" style="width:23%">
					<span class="red">*</span>原密码
				</th>
				<td>
				 	<s:password maxlength="20" name="ymm" id="ymm" cssStyle="width:154px" onblur="checkYmm();" onfocus="showRightPrompt(this,'请输入原密码!',3)"></s:password> 
				</td>
			</tr>
			<tr>
				<th align="right">
					<span class="red">*</span>新密码
				</th>
				<td>
					 <s:password maxlength="16" name="mm" id="mm" cssStyle="width:154px;" onkeyup="CreateRatePasswdReq(this);"
	         			onblur="checkMm(this);" onfocus="hideMmError(this);"></s:password>
	         		<!-- ***********密码强度*********** -->
	         		 <table cellSpacing=0 cellPadding=0 border=0 class="resetCss" style="margin-right:4px 0;float: right;">
						<tbody>
							<tr>
								<td style="padding:3px 0;">密码强度：</td>
								<td style="color:#808080;font-weight:bold"><font id="passwdRating"></font></td>
							</tr>
							<tr>
								<td colSpan=2>
									<table id=passwdBar cellSpacing=0 cellPadding=0 width=160
										bgColor=#ffffff border=0>
										<tbody>
											<tr>
												<td id=posBar width=0% bgColor=#e0e0e0 height=4></td>
												<td id=negBar width=100%; bgColor=#e0e0e0 height=4></td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- ***********密码强度*********** -->
				</td>
			</tr>
			<tr>
				<th align="right">
		    		<span class="red">*</span>重复新密码
				</th>
				<td>
		    		 <s:password maxlength="16" name="nmm" id="nmm" cssStyle="width:154px"
	        	onblur="checkNmm(this);"  onfocus="showRightPrompt(this,'请重新输入密码',1);"></s:password> 
				</td>
			</tr>
			 <tr>
	      <td colspan="3" >
	      &nbsp;
	      </td>
	      </tr>
	    </tbody>
	    <tfoot>
	    	<tr>
	    		<td colspan="2">
	    		<div class="bz">"<span class="red">*</span>"为必填项</div>
	    			<div class="btn">
					<button type="button" class="" onclick="formSubmit();return false;">
							修改
					</button>
					<button type="button" class="" onclick="iFClose();return false;">
							关闭
					</button>
	    			</div>
	    		</td>
	    	</tr>
	    </tfoot>
	    </table>
   </s:form>
  </body>
</html>
