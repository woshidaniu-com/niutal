<%@ page contentType="text/html; charset=UTF-8"%>
<HTML>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%
          response.setCharacterEncoding("UTF-8");
          response.setContentType("application/vnd.ms-excel;");
          response.setHeader("Content-Disposition", "attachment;filename=\""+new String("sttj_export.xls".getBytes(),"ISO-8859-1")+"\"");   
     %>
	<body>
		${exportContent}
	</body>
</HTML>
