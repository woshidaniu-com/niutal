<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=stylePath %>/js/lhgdialog/lhgdialog.min.js?_rv_=<%=resourceVersion%>"></script>
		<script src="<%=systemPath%>/js/My97DatePicker/WdatePicker.js?_rv_=<%=resourceVersion%>" defer="defer"></script>
		<script type="text/javascript">
			function setCurrentMenu(obj){
				jQuery(".textlink").find("li").removeClass();
				jQuery(obj).parent("li").addClass("current");
				var menuUrl = jQuery(obj).find("input[name='menuUrl']").val();
				refRightContent(menuUrl);
			}
		</script>
	</head>
	<body>
		<s:form action="/xtgl/login_pageIndex.html"  theme="simple">
			<div class="mainbody type_mainbody">
				<!-- TOP菜单的加载 -->
				<div class="topframe" id="topframe">
				     <jsp:include page="top.jsp" flush="true"></jsp:include>
				</div>
				
			    <div class="mainframe" id="mainframe">
					<div class="leftframe" id="leftframe">
			        	<!-- LEFT 菜单的加载 -->
						<jsp:include page="leftMenu.jsp" flush="true"></jsp:include>
			        </div>
					<!-- RIGHT页面加载 -->
					<div class="rightframe" id="rightframe">
						<jsp:include page="rightContent.jsp" flush="true"></jsp:include>
					</div>
			    </div>
			    
			    <!-- 底部页面加载 -->
			    <div class="botframe" id="botframe">
			      	<jsp:include page="bottom.jsp" flush="true"></jsp:include>
			    </div>
			</div>
		</s:form>
	</body>
</html>
