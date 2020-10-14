<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.woshidaniu.util.base.MessageUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
		String stylePath = MessageUtil.getText("system.stylePath");
		String systemPath = request.getContextPath();
		String jsPath = request.getContextPath();
%>	
<!doctype html>
<html lang="zh-CN">
<head>
</head>
<body>
<div class="media">
	<a class="pull-left" href="#">
		<s:if test="yhlx=='teacher'">
				<img class="media-object" data-src="holder.js/110x110" alt="110x110" src="<%=systemPath%>/xtgl/photo_cxJzgzp.html?jgh=${yhm}" style="width: 110px; height: 110px;">
		</s:if>
		<s:if test="yhlx=='student'">
			<s:if test="hasXsRxhzp">
				<img class="media-object" data-src="holder.js/110x110" alt="110x110" src="<%=systemPath%>/xtgl/photo_cxXszp.html?xh_id=${xh_id}&zplx=rxhzp" style="width: 110px; height: 110px;">
			</s:if>
			<s:elseif test="hasXsRxqzp">
				<img class="media-object" data-src="holder.js/110x110" alt="110x110" src="<%=systemPath%>/xtgl/photo_cxXszp.html?xh_id=${xh_id}&zplx=rxqzp" style="width: 110px; height: 110px;">
			</s:elseif>
			<s:else>
				<img class="media-object" data-src="holder.js/110x110" alt="110x110" src="<%=stylePath %>/assets/images/user_logo.jpg" style="width: 110px; height: 110px;">
			</s:else>
		</s:if>
	</a>
	<div class="media-body">
		<h4 class="media-heading">${map.XM}</h4>
		<s:if test="yhlx=='teacher'">
			<p>${map.JGMC}</p>
			<s:if test="yhm!='admin'">
				<p class="fs1">${map.ZCMC}</p>
			</s:if>
		</s:if>
		<s:if test="yhlx=='student'">
			<p>${map.JGMC} ${map.BJ}</p>
			<p class="fs1">已获学分 ${map.YHXF}分</p>
			
			<div class="progress">
				<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: ${map.BL}%">
					<span class="sr-only">${map.BL}% Complete (success)</span>
				</div>
				<a href="#">详细</a>
			</div>
			
			<p class="fs2">毕业所需学分 ${map.SXXF}分</p>
		</s:if>
	</div>
</div>
</body>
</html>