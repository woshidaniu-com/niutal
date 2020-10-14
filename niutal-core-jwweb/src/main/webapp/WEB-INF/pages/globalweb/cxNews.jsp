<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String systemPath = request.getContextPath();
%>
<div class="list-group">
<s:iterator value="newsList">
	<a href="<%=systemPath%>/xtgl/xwck_ckXw.html?xwbh=<s:property value='xwbh'/>&doType=save" target="_blank" class="list-group-item">
		<span class="title">
		<s:if test="sfzd==1">
			<font color="red">【<s:text name="i18n.top"/>】</font>
		</s:if>
		<s:property value="xwbt"/>
		</span>
		<s:if test="sfyd == 0">
			<i class="index_png new"></i>
		</s:if>
		<span class="time float_r"><s:property value="fbsj"/></span>
	</a>
</s:iterator>
<s:iterator value="emptylist">
	<a href="#" class="list-group-item">&nbsp;<span class="time float_r">&nbsp;</span></a>
</s:iterator>
<s:if test="newsList.size() > 5">
<div>
	<a href="<%=systemPath%>/xtgl/xwck_cxMoreXwList.html" target="_blank"><p align="right">...更多</p></a>
</div>
</s:if>
</div>