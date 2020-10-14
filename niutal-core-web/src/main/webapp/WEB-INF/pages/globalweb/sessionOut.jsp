<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String tzurl = request.getParameter("tzurl");
if(path!=null && !"".equals(path)){
	tzurl = path+"/"+tzurl;
}
%>
<script type="text/javascript">
 	//用户当前页面框架  跳出
	if (top.location != self.location)  
	{
		top.location=self.location;
	}
</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
  </head>
  <body>
   <center>
   <s:form action="" theme="simple">
   	<input type="hidden" name="tzurl" id="tzurl" value="<%=tzurl %>"/>
   	<div class="page_prompt_yx">
	  <p>尊敬的用户，您长时间未操作本系统，为保证其安全性，<font color="red"><span  id="time">10</span></font>秒后系统将自动返回登录页面<br/>
	 <a onclick="logout()" style="cursor: pointer;" target="_top" class="bold underline">如果您的浏览器没有自动跳转,请点击这里</a></p>
	</div>
   	
  </s:form>
   </center>
   <script type="text/javascript">
	
 	 function logout(){
		var url = document.getElementById("tzurl").value;
		document.location.href = url;
	}
    var time=document.getElementById("time").innerHTML;
    var timeNum= parseInt(time);
    minusTime(timeNum);
	function minusTime( time){
		
        var interval = setInterval(function(){
       			document.getElementById("time").innerHTML=time+"";
       			if(time == 0){
					clearInterval(interval);
		    		logout();
           		}
       			time--;
       			
            },1000);
		return ;
	}
  </script>
  </body>
</html>
