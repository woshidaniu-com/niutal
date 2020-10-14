<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String appLogo = MessageUtil.getText("system.logo.app");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	</head>
	<body>
		<div class="mainbody_yx">
			<div class="topframe_hq">

				<div class="head_yx">
					<div class="logo">
						<h2 class="floatleft">
							<img src="<%=stylePath%>logo/logo_school.png" />
						</h2>
						<s:if test="result.gglx==1">
							<h3 class="floatleft">
								<img src="<%=stylePath%>logo/logo_xszz.png" />
							</h3>
						</s:if>
						<s:else>
							<h3 class="floatleft">
								<img src="<%=stylePath%>logo/<%=appLogo%>" />
							</h3>
						</s:else>
					</div>
				</div>
			</div>
			<div class="news_mainframe_yx">
				<div class="newscontent_yx">
					<div class="title" style="text-align: center;">
						<h2>
							<e:forHtmlContent value="${xwbt}"/>
						</h2>
						<h4>
							<s:if test="result.gglx!=1">
								<span>发布人：${fbrxm}</span>&nbsp;&nbsp;&nbsp;&nbsp;
							</s:if>
							<span>发布时间：${fbsj}</span>
						</h4>
					</div>
					<div class="con" style="line-height: 250%">
						<p>
							${fbnr}
						</p>
					</div>
				</div>
			</div>
		</div>
	<div class="botframe_yx">
      	<div class="copy"><span> <%=MessageUtil.getText("niutal_copy") %> <a href="<%=MessageUtil.getText("niutal_link") %> " target="_blank">
      	<%=MessageUtil.getText("niutal_bottom") %> </a> 版权所有<br/>
      	
		</div>
    </div>
	</body>
</html>
