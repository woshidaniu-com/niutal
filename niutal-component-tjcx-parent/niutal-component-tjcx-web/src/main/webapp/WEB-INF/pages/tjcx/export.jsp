<%@ page contentType="text/html; charset=UTF-8"%>
<HTML>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%
          response.setCharacterEncoding("UTF-8");
          response.setContentType("application/vnd.ms-excel;");
          response.setHeader("Content-Disposition", "attachment;filename=\""+new String("export.xls".getBytes(),"ISO-8859-1")+"\"");   
     %>
	<head>
		<title>export</title>
	</head>
	<body>
		${tableHtml}
	</body>
</HTML>
