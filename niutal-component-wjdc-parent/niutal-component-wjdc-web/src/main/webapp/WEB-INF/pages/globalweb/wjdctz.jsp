<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=stylePath %>/js/lhgdialog/lhgdialog.min.js?_rv_=<%=resourceVersion%>"></script>
		<style type="text/css">
			.btn,.btn:hover{
				font-size: 20px;
				height:30px;
				color:#fff;
				border:none;
				background:url(<%=request.getContextPath()%>/images/wjdc_btn.gif) repeat-x left top;
			}
		</style>
	</head>

	<body>
		<center>
			<s:form action="" theme="simple">
				<input type="button" style="display:none;" onclick="alert(1)"/>
				<div class="page_prompt_yx" style="height: 300px;">
					<p>
						您好，为帮助我们改善工作有问卷
						<s:iterator value="wjList" id="w" status="k">
							"<a style="cursor: pointer;" class="bold underline"
							   onclick="window.open('<%=request.getContextPath()%>/zfxg/wjdc/stgl_yhdj.html?wjModel.wjid=${w.wjid}');"
							>${w.wjmc }</a>"
							<s:if test="!#k.last">,</s:if>
						</s:iterator>
						需要您填写后才可以继续操作！
						<br />
					</p>
					<p style="margin-top: 25px;">
						<button type="button" class="btn" onclick="window.open('<%=request.getContextPath()%>${tzurl}');">
							&nbsp;&nbsp;&nbsp;&nbsp;答&nbsp;&nbsp;&nbsp;&nbsp;卷&nbsp;&nbsp;&nbsp;&nbsp;
						</button>
					</p>
				</div>
			</s:form>
		</center>
	</body>
</html>
