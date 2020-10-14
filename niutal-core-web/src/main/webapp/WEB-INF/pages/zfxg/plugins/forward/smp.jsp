<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
  </head>
  
  <body>
	  <div class="tab_cur">
		<p class="location">
			<em>您的当前位置:</em><a>${currentMenu}</a>	
		</p>
	  </div>
    <iframe src="${url }"
			scrolling="no"
			frameborder="0" marginwidth="0" marginheight="0"
			width="100%;"
			style="align:center;height:600px"
	></iframe>
  </body>
</html>

