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
<style type="text/css">

*:before, *:after {
    -moz-box-sizing: border-box;
}

div, h1, h2, h3, h4, h5, h6, strong {
    color: #717171;
    font-family: microsoft YaHei,SimSun;
    font-weight: normal;
    vertical-align: middle;
}
* {
    -moz-box-sizing: border-box;
}
.media:first-child {
    margin-top: 0;
}
.media, .media .media {
    margin-top: 15px;
}
.media, .media-body {
    overflow: hidden;
}

.media > .pull-left {
    margin-right: 10px;
}
.pull-left {
    float: left;
}
.pull-left {
    float: left !important;
}
a {
    color: #428BCA;
    text-decoration: none;
}
a {
    background: none repeat scroll 0 0 rgba(0, 0, 0, 0);
}
* {
    -moz-box-sizing: border-box;
}

element.style {
    height: 110px;
    width: 110px;
}

img, button {
    border: medium none;
}
img {
    vertical-align: middle;
}
img {
    border: 0 none;
}

dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, pre, form, fieldset, lable, textarea, select, p, blockquote, button, tr, td, th, thead, caption {
    font-weight: normal;
    margin: 0;
    padding: 0;
}
h4, .h4 {
    font-size: 18px;
}
h4, .h4, h5, .h5, h6, .h6 {
    margin-bottom: 10px;
    margin-top: 10px;
}
h1, h2, h3, h4, h5, h6, .h1, .h2, .h3, .h4, .h5, .h6 {
    color: inherit;
    font-family: inherit;
    font-weight: 500;
    line-height: 1.1;
}

p {
    margin: 0 0 10px;
}
.index_grxx {
    background: none repeat scroll 0 0 #FFFFFF;
    height: auto !important;
    margin-bottom: 15px;
    margin-left: -15px;
    min-height: 159px;
    overflow: hidden;
}

.index_grxx .media .media-object {
    border-radius: 55px 55px 55px 55px;
    float: left;
    margin: 20px 10px 20px 5px;
}
.index_grxx .media-object {
    display: block;
}
.index_grxx .media-body {
    margin-top: 20px;
}
.index_grxx .media-body p {
    color: #7A7A7A;
    font: 12px/22px "microsoft YaHei";
}
.index_grxx .media, .media-body {
    overflow: hidden;
}
.index_grxx .media-body .media-heading {
    color: #333333;
    font: bold 15px/25px "microsoft YaHei";
}
.index_grxx .media-heading {
    margin: 0 0 5px;
}
.index_grxx .media-body p.fs1 {
    color: #ABABAB;
    font: 12px/22px "microsoft YaHei";
}
.index_grxx .media-body .progress {
    background: none repeat scroll 0 0 #DFDFDF;
    height: 8px;
    margin: 8px 40px 8px 0;
    overflow: initial;
    position: relative;
    z-index: 1;
}
.index_grxx .media-body .progress .progress-bar {
    background: none repeat scroll 0 0 #05D91E;
    border-radius: 4px 4px 4px 4px;
    height: 8px;
    line-height: 8px;
}
.index_grxx .media-body .progress a {
    color: #1059C7;
    font-family: SimSun;
    font-size: 12px;
    position: absolute;
    right: -40px;
    top: -3px;
    z-index: 2;
}

.index_grxx .media-body p.fs2 {
    color: #ABABAB;
    font: 12px/22px "microsoft YaHei";
    text-align: right;
}
.index_grxx .progress {
    background-color: #F5F5F5;
    border-radius: 4px 4px 4px 4px;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1) inset;
    height: 20px;
    margin-bottom: 20px;
    overflow: hidden;
}
.index_grxx .progress-bar-success {
    background-color: #5CB85C;
}
.index_grxx .progress-bar {
    background-color: #428BCA;
    box-shadow: 0 -1px 0 rgba(0, 0, 0, 0.15) inset;
    color: #FFFFFF;
    float: left;
    font-size: 12px;
    height: 100%;
    line-height: 20px;
    text-align: center;
    transition: width 0.6s ease 0s;
    width: 0;
}
.index_grxx .sr-only {
    border: 0 none;
    clip: rect(0px, 0px, 0px, 0px);
    height: 1px;
    margin: -1px;
    overflow: hidden;
    padding: 0;
    position: absolute;
    width: 1px;
}
</style>
</head>
<body>
<div class="index_grxx">
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
</div>
</body>
</html>