<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>缓存更新</title>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" >
			function gjcxUpate(){
				jQuery.ajax( {
					type : "post",
					// async: false,
					url : _path + "/zfxg!plugins/cache_gjcxUpdate.html?timestamp=" + new Date().getTime(),
					data : {
					},
					dataType : "json",
					success : function(data) {
						var message = "";
						if(data == "success"){
							message = "操作成功！";
						}else{
							message = "异常，请检查程序：" + data ;	
						}
						alert(message);
					}
				});
			}		
		</script>
  </head>  
  <body>  
	<div class="demo_xxxx">
	<h3 class="college_title">
		<span class="title_name">缓存更新<font color="red"> 缓存代码在zfxg-core下，请确认缓存已经开启</font></span>
	</h3>
    <table width="100%" border="0" class=" formlist" cellpadding="0" cellspacing="0">
    <tbody>
      <tr>
      	<th width="100px">高级查询</th>
        <td>&nbsp;<button onclick="gjcxUpate();return false;">缓存更新</button><br/>&nbsp;<font color="red">注：当高级查询缓存数据库配置做过调整，在不重启应用服务器情况下，可点击此按钮进行缓存更新</font></td>
      </tr>
    </tbody>
  </table>
</div>
  </body>
</html>
