<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript">
			jQuery(function() {
				jQuery("#form1").submit();
			});
		</script>
	</head>
	<body>
	<s:form namespace="/zfxg/tjcx" action="tjbb_tjlb" method="post" id="form1" target="_blank">
        <input type="hidden" id="kzszid" name="kzszid" value="${kzszid}"/>
    </s:form>
    
    <br/><br/><br/><br/><br/>
    <p align="center">
		<font size="5"><b>请在弹出页面中查看统计报表详情</b></font>
	</p>
	</body>
</html>