<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  </head>
  <body>
  	<shiro:authenticated>
	  	<div class="buttonbox">
			<ul id="but_ancd">
				<s:if test="ancdModelList !=null">
					<s:iterator value="ancdModelList" id="list" status="s">
						<li>
							<a href="javascript:void(0);" id="btn_${czdm}" class="${anys}" >
								${czmc} </a>
						</li>
					</s:iterator>
				</s:if>
			</ul>
		</div>
	</shiro:authenticated>
 </body>
</html>
