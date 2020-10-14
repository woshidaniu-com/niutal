<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.woshidaniu.common.log.User"%>
<%@page import="JsglModel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String appLogo = MessageUtil.getText("system.logo.app");
%>
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript">
		jQuery(function() {
			viewMenu();
			//初始化密码
			checkPassStrength();
			navigateInit();//导航事件初始化
		});
		
		function logout(){
			showConfirmDivLayer('确定注销？',{'okFun':function(){
				document.location.href="logout";
			 }
			});
		}
		//将当前选中的菜单样式突出
		function viewMenu() {
			jQuery('ul.ul_find > li').removeClass();
			var classbz = jQuery('#classbz').val();
			if (classbz != null && classbz != "") {
				classbz = "li_"+classbz;
				jQuery('#'+classbz).parent().addClass('current');
			} else {
				jQuery('#li_page').parent().addClass('current');
			}
		}
		
		//修改密码
		function xgMm(){
			showWindow('修改密码',450,320,'<%=systemPath %>/xtgl/yhgl_xgMm.html');
		}

		//切换角色
		function switchRole(){
			showWindow('切换角色',450,160,'<%=systemPath %>/xtgl/yhgl_qhjs.html');
		}
		
		//验证密码强度
		function checkPassStrength(){
			var yhmmdj="${user.yhmmdj}";
			//用户密码等级小于等于1  ,也就是弱密码
			if(parseInt(yhmmdj) <= 1){
				alert('您的密码过于简单,为了系统安全,请先修改密码!','',{'clkFun':function(){
					xgMm();
			  	}});
			}
		}
		//导航事件 初始化
		function navigateInit(){
			jQuery(".menu .nav .ul_find a[id^='li_']").click(function(){
				var mkdm = jQuery(this).attr("id").substr(3);
				navigate(mkdm);				
			});			
		}

		//导航事件 
		function navigate(gnmkdm,params){
			var mkdm = "";
			var curmkdm = "";
			if(gnmkdm == null || gnmkdm == ""){
				return;
			}else if(gnmkdm.length <= 3 || gnmkdm == "page"){
				mkdm = gnmkdm;
			}else{
				mkdm = gnmkdm.substr(0,3);
				curmkdm = gnmkdm;
			}
			var url = "<%=systemPath %>/xtgl/";
			jQuery('ul.ul_find > li').removeClass();
			if(mkdm == "page"){
				jQuery('#li_page').parent().addClass('current');
				url += "index_initMenu.html";
				location.href = url;	
			}else{
				jQuery("#li_" + mkdm).parent().addClass('current');
				url += "index_content.html?gnmkdm=" + mkdm;
				if(curmkdm != ""){
					url += "&curmkdm=" + curmkdm;
				}
				if(params != null && params != ""){
					url += "&params=" + params;
				}
				jQuery(parent.document).find("#mainframe").load(url);
			}
		}
	</script>
	</head>
	<body >
		<!-- 一级菜单选中标志 -->
    <input type="hidden" name="classbz" id="classbz" value="${gnmkdm }"/>
      <div class="head">
        <div class="logo">
          <h2 class="floatleft"><img src="<%=stylePath%>logo/logo_school.png"/></h2>
          <h3 class="floatleft"><img src="<%=stylePath%>logo/<%=appLogo%>" /></h3>
        </div>
        <div class="info">
        <div class="welcome">
        <div class="tool"  onmouseover="javascript:document.getElementById('downmenu').style.display='block'" onmouseout="javascript:document.getElementById('downmenu').style.display='none'">
       	 	<shiro:authenticated>
       	 		<a class="tool_btn" href="#" onclick="showhid('downmenu');" ><shiro:principal property="xm" /></a>
       	 	</shiro:authenticated>
        	<div class="downmenu" id="downmenu" style="display:none;">
				<s:if test="#session.user.jsdms.size > 1">
		        	<a href="#" class="passw" onclick="switchRole()">角色切换</a>
				</s:if>
				<shiro:hasRole name="admin">
	        		<a href="${pageContext.request.contextPath}/druid" class="passw" target="_blank" style="color:red">系统监控</a>
	        	</shiro:hasRole>
	        	<a href="#" class="passw" onclick="xgMm()">修改密码</a>
	        </div>
         </div>
		<span>您好！</span><a href="${pageContext.request.contextPath}/logout" id="tologin" title="注销" class="logout"></a></div> 
      </div>
    </div>
      <div class="menu">
    	<div class="nav">
			<ul class="ul_find">
				<li>
					<a href="#" id="li_page">首页</a>
				</li>
				<s:if test="topMenuList != null && topMenuList.size() > 0">
					<s:iterator id="cdlist" value="topMenuList">
						<li>
							<a style="cursor: pointer;" id="li_${GNMKDM }">${GNMKMC}</a>
						</li>
					</s:iterator>
				</s:if>
				<s:else>
					<div>
						<b><font color="red" size="2">该用户尚未开放任何功能模块权限，请联系管理员！</font>
						</b>
					</div>
				</s:else>
			</ul>
      </div>
    </div>
  </body>
</html>
