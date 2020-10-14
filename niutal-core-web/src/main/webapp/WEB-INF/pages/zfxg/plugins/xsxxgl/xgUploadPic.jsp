<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.woshidaniu.util.base.MessageUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-1.6.4.min.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.form.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/upload/ajaxfileupload.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=MessageUtil.getText("system.stylePath") %>/js/lhgdialog/lhgdialog.min.js?skin=discuz?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/lhgdialog/dialogUtil.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
		var _path = "<%=request.getContextPath() %>";
		function uploadPic() {
			jQuery.ajaxFileUpload( {
				url : _path + '/zfxg!plugins/fileUpload_fileUpload.html',//服务器端程序
				secureuri : false,
				fileElementId : 'file',//input框的ID
				async : false,
				dataType : 'json',//返回数据类型
				success : function(data) {//上传成功
					var parentPage = getParentDialogWin();
					parentPage.jQuery("#xszp").attr("src",data.url);
					parentPage.jQuery("#xszpPath").val(data.url);
					closeDialog();
				}
			});
		}
		</script>
	</head>
	<body>
		<s:form action="/zfxg/xsxxgl/xgXsxx.html" method="post"
			theme="simple" enctype="multipart/form-data">
			
			<table style="width: 99%;">
				<tbody>
					<tr>
						<td>
							<input type="file" id="file" name="file" style="width: 96% ;height: 30px;"
								onchange='uploadPic();' />
						</td>
					</tr>
				</tbody>
			</table>
		</s:form>
	</body>

</html>