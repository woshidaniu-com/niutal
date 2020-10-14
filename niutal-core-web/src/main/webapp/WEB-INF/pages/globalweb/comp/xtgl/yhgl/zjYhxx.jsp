<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comm/checkPassword.js?_rv_=<%=resourceVersion%>"></script>
</head>
<script type="text/javascript">
	//保存
	function save(){
		if (inputResult() && checkInputNotNull('zgh!!xm!!mm!!nmm')){
 			 var jgdm = $('#jgdm').val();
 			 var jgmc = $('#jgmc').val();
 			 if(jgdm==''){
 			 	alert('请选择所属机构！');
 			 	return false;
 			 }
 			 if(jgmc==''){
 			 	alert('请选择所属机构！');
 			 	return false;
 			 }
			 subForm('yhgl_zjBcYhxx.html')
		}
	}

	//验证职工号错误
	function checkZgh(obj){
		if(!checkZghOnly(obj)){
			//验证错误返回
			return false;
		}
		if(!checkYhmAndMm()){
			//验证错误返回
			return false;
		}
		return true;
	}
	
	//验证职工号唯一
	function checkZghOnly(obj){
		var isExists = true;
		var pkValue = jQuery('#zgh').val();
		jQuery.ajaxSetup({async : false});
		if (pkValue != ''){
			jQuery.ajax({
					url:"yhgl_valideZgh.html",
					type:"post",
					dataType:"json",
					data:{pkValue:pkValue},
					success:function(data){
					if(data!=null){
						showDownError(obj,'职工号"'+pkValue+'"已存在，不能使用！');
						isExists =  false;
					}
				}
				});
			}
		jQuery.ajaxSetup({async : true});
		return isExists;
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

		return true;
	}
	
	//验证密码
	function checkMmStrength(obj){
		var isNotnulll = true;

		 var mm = $('#mm').val();
		if (mm == null || mm == '') {
			hideDownError();
		} else {
			var S_level = checkStrong(mm);
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
	function checkNmm(obj){
		var obj = document.getElementById("nmm");
		var isNotnulll = true;
		var mm = jQuery('#mm').val();
		var nmm = jQuery('#nmm').val();
		if(mm!=nmm){
			showRightError(obj,'两次输入密码不一致！',1);
			isNotnulll =  false;	
		}else{
			hideRightError(obj);
			isNotnulll =  true;	
		}
		return isNotnulll;
	}
	
	//验证邮箱
	function checkEmail(obj){
		var isRight = true;
		var email = jQuery('#dzyx').val();
		if (email!=''&&!isEmail(email)){
			showDownError(obj,'邮箱格式不正确');
			isRight =  false;
		}else{
			hideDownError(obj);
			}
		return isRight;
	}

	//选择部门
	function popuWindow(){
		var url="yhgl_cxJgdms.html"; 
		showWindow("选择部门",680,550,"yhgl_cxJgdms.html");
		//showWindow('增加用户',500,610,'yhgl_cxJgdms.html');
	}

	//验证用户名称和密码
	function checkYhmAndMm(){
		var jqZgh = jQuery("#zgh");
		var jqMm = jQuery("#mm"); 
		
		var zgh=jqZgh.val();
		var mm=jqMm.val();
		if(zgh == mm){
			showDownError(jqMm,"用户名和密码不能相同,请修改!",'2');
			return false;
		}else{
			hideDownError(jqMm);
			return true;
		}
	}
	
	//隐藏密码错误提示信息
	function hideMmError(obj){
		showDownPrompt(obj,'密码长度为6~16位，至少包含数字、特殊符号、英文字母(大、小写)中的两类!',2);
		//解决光标获取位置
		setTimeout(function(){
			var mmObj=document.getElementById("mm");
			var mm = mmObj.value;
			mmObj.value="";
			mmObj.focus();
			mmObj.value=mm;
		}, 500);
	}

	//隐藏密码错误提示信息
	function hideNmmError(obj){
		showRightPrompt(obj,'请重新输入密码!',1);
		//解决光标获取位置
		setTimeout(function(){
			var nmmObj=document.getElementById("nmm");
			var nmm = nmmObj.value;
			nmmObj.value="";
			nmmObj.focus();
			nmmObj.value=nmm;
		}, 500);
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


<body>
	<s:form action="/xtgl/yhgl_zjBcYhxx.html" method="post" theme="simple">
	 <div class="tab">
	  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>增加用户</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="4"><div class="bz">"<span class="red">*</span>"为必填项</div>
	          <div class="btn">
	            <button type="button" name="btn_tj" onclick="save();return false;">保 存</button>
	            <button type="button" name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	        <th width="20%"><span class="red">*</span>职工号</th>
	        <td width="30%">
	       
	        <s:textfield maxlength="20" name="zgh" id="zgh" onkeyup="isNotChar(this);" cssStyle="width:154px" onblur="checkZgh(this);"
	         onfocus="showDownPrompt(this,'只能输入字母或数字');"></s:textfield> 
	       </td>
	     
	     	<th width="20%"><span class="red">*</span>姓名</th>
	        <td width="30%">
	        <s:textfield maxlength="20" name="xm" id="xm" cssStyle="width:154px" 
	      
	       	>
	        </s:textfield>
	         </td>
	      </tr>
	      

	       <tr>
	        <th width="20%">联系电话</th>
	        <td width="30%"><s:textfield maxlength="20" onfocus="showDownPrompt(this,'只能输入数字');" onkeyup="onyInt(this);" name="lxdh" id="lxdh" cssStyle="width:154px" ></s:textfield> </td>
	    
	   		<th width="20%">Email</th>
	        <td width="30%">
	        <s:textfield maxlength="40" name="dzyx" id="dzyx" onblur="checkEmail(this);" 
	        cssStyle="width:154px" ></s:textfield>

	        </div>
	         </td>
	      </tr>
	      
	       <tr>
	        <th ><span class="red">*</span>登录密码</th>
	        <td colspan="3">
	        <s:password maxlength="16" name="mm" id="mm" cssStyle="width:154px"  onblur="checkMm(this);" onkeyup="CreateRatePasswdReq(this);"
	         onfocus="hideMmError(this);"></s:password> 
	         <!-- ***********密码强度*********** -->
       		 <table cellSpacing=0 cellPadding=0 border=0 class="resetCss" style="margin-right:220px;float: right; ">
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
	         <th><span class="red">*</span>重复密码</th>
	        <td colspan="3">
	        <s:password maxlength="16" name="nmm" id="nmm" cssStyle="width:154px"  onblur="checkNmm();"
	         onfocus="hideNmmError(this);"></s:password> 
	        
	        </td>
	        </tr>
	        <tr>
	    	
	    	<th width="20%"><span class="red">*</span>所属机构</th>
	        <td width="80%" colspan="3">
	         <s:hidden name="jgdm" id="jgdm"></s:hidden>
	         <s:hidden name="cydm_id_bmlb" id="cydm_id_bmlb"></s:hidden>
	         <s:textfield maxlength="20" name="jgmc" id="jgmc" cssStyle="width:130px"></s:textfield>
	         <button type="button" onclick="popuWindow();return false;">选择</button>
			</td>
	      </tr>
	      <tr>
	    	<th width="20%">是否启用</th>
	        <td width="80%" colspan="3">
				<s:radio list="#{'0':'否','1':'是'}" id="sfqy" name="sfqy" value="'1'"></s:radio>
			</td>
	      </tr>
	       <tr>
	        <th width="20%" rowspan="${col}">所属角色</th>
	       
	   		<td width="80%" colspan="3">
	   		  <div style="height:200px; overflow-y:auto;">
				<table  cellspacing="0" height="100px">
						<s:iterator value="jsxxList" id="s" status="substa">
									<div>
										<input type="checkbox" class="cbvclass" style="cursor: pointer;" id="cbvjsxx" name="cbvjsxx" value="${s.jsdm}" />
										<e:forHtmlContent value="${s.jsmc}"/>
									</div>
						</s:iterator>
				</table>
			  </div>
	       </td>
	        
	      </tr>
	    </tbody>
	  </table>
  </div>
  <s:if test="result != null && result != ''">
  	<script>
  		alertMessage('${result}',function(){this.close();refershParent()});
  	</script>
  </s:if>
 </s:form>
</body>
</html>