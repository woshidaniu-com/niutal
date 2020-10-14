<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.woshidaniu.common.log.User"%>
<%@page import="JsglModel"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String appLogo = MessageUtil.getText("system.logo.app");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
	<div class="mainbody type_mainbody">
		<!-- TOP菜单的加载 -->
		<div class="topframe" id="topframe">
			<!-- 一级菜单选中标志 -->
			<input type="hidden" name="classbz" id="classbz" value="${gnmkdm}" />
			<div class="head">
				<div class="logo">
					<h2 class="floatleft">
						<img src="<%=stylePath%>/images/logo/logo_school.png" />
					</h2>
					<h3 class="floatleft">
						<img src="<%=stylePath%>/images/logo/<%=appLogo%>" />
					</h3>
				</div>
			</div>
			<div class="menu">
				<div class="nav">
					<ul class="ul_find">
						<li>
							<a href="<%=systemPath%>/xtgl/jsgl_view.html?jsdm=${sessionScope.v_jsdm}"id="li_page">首页</a>
						</li>
						<s:if test="topMenuList != null && topMenuList.size() > 0">
							<s:iterator value="topMenuList">
								<li>
									<a style="cursor: pointer;" onclick="document.location.href='<%=systemPath%>/xtgl/jsgl_view.html?jsdm=${sessionScope.v_jsdm}&gnmkdm=<s:property value="GNMKDM"/>'" id="li_<s:property value="GNMKDM"/>"><s:property value="GNMKMC"/></a>
								</li>
							</s:iterator>
						</s:if>
						<s:else>
							<div>
								<b><font color="red" size="2">该用户尚未开放任何功能模块权限，请联系管理员！</font> </b>
							</div>
						</s:else>
					</ul>
				</div>
			</div>
		</div>
		<div class="type_mainframe">
			<!-- 菜单区域 -->
			<div class="typeleft floatleft" id="left">
				<s:if test="menuList != null && menuList.size() > 0">
					<s:iterator id="menu" value="menuList" status="sta">
						<div class="textlink" id="">
							<h3 onclick="showhidediv(this);" class="open">
								<span>${menu.GNMKMC}</span>
							</h3>
							<ul style="display: none" class="hierarchy_03">
								<s:iterator id="sub_menu" value="#menu.sjMenu">
									<li>
										<a class="none_03"><span>${sub_menu.GNMKMC}</span></a>
									</li>
								</s:iterator>
							</ul>
						</div>
					</s:iterator>
				</s:if>
			</div>
			<div class="btn_hide_on" id="leftBotton">
				<button onclick="changeWin();return false;" id="changeid"
					type="button"></button>
			</div>
			<div class="btn_hide_off" style="display: none;" id="rightBotton">
				<button onclick="changeWin();return false;" type="button"></button>
			</div>
			<div class="typeright floatright" id="right">
				<div class="typecon" id="rightContent">
					<!--内容区主体-->
					<iframe id="content_frame" name="content_frame" marginwidth="0"
						marginheight="0" frameborder="0" width="800px" height="620"
						style="overflow-x: hidden; overflow-y: auto;"></iframe>
				</div>
			</div>
		</div>
		<!-- 底部页面加载 -->
		<div class="botframe" id="botframe">
			<jsp:include page="../../../bottom.jsp" flush="true"></jsp:include>
		</div>
	</div>
	<!-- 新消息提示div start -->
	<div id="znxContent" style="display: none;">
		<div id="pendingtask" class="openmessage"
			style="width: 240px; height: 127px; position: absolute; bottom: 0; z-index: 9999; right: 0px;">
			<div class="messagecon">
				<p class="mesnum">
					您有 <em id="tasktotaldiv">0</em>条新消息
				</p>
				<p>
					<a href="#" onclick="refRightContent('<%=systemPath%>/zfxg!plugins/znxgl_sjxZnx.html');">马上处理</a>
					<a href="#" onclick= "ymPrompt.close();" >暂不处理</a>
				</p>
			</div>
		</div>
	</div>
</body>
<%@ include file="/WEB-INF/pages/globalweb/head/wdatePicker.ini"%>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/wdyy.js?ver=<%=jsVersion%>"></script>
<script type="text/javascript">
	function changeWin() {
		var left = jQuery("#left");
	
		if (left.attr("class") != "hide") {
			jQuery("#left").attr("class","hide");
			jQuery("#leftBotton").css("display","none");
			jQuery("#rightBotton").css("display","");
			jQuery("#right").css("width","100%");
			
		} else {
			jQuery("#left").attr("class","typeleft floatleft");
			jQuery("#leftBotton").css("display","");
			jQuery("#rightBotton").css("display","none");
			jQuery("#right").css("width","803px");
		}
	}
	
	//二级菜单切换
	function showhidediv(o,id){
		
		jQuery(".close ~ul").css("display","none");
		jQuery(".close").attr("class","open");
		
		jQuery(o).attr("class","close");
		jQuery(".close ~ul").css("display","");
	}	
</script>
</html>
