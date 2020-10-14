<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	</head>
	<body >
		<input type="hidden" id="fjgndm" name="fjgndm" value="${gnmkdm}" />
		<input type="hidden" id="curmkdm" name="curmkdm" value="${curmkdm}" />
		<div class="textlink" >
			<h2>
				<span onclick="xsWdyy()" style="cursor:pointer;">我的应用</span>
			</h2> 
	 		<ul style="display:block;" class="hierarchy_03" id="wdyyCd">
	 			<li>
	 				 <span style="cursor:pointer">应用加载中...</span></a>
	 			</li>
			</ul>
			<div class="bot_cygn"></div>
		</div>
		<s:if test="menuList != null && menuList.size() > 0">
			<s:iterator id="menu" value="menuList" status="sta">
				<s:if test="#menu.sjMenu==null || #menu.sjMenu.isEmpty()">
					<div class="textlink" >
		 				<h3 onclick="showhidediv(this);">
		 				 		<a href="<%=systemPath %>${menu.DYYM}">
			 				 	   <span>${menu.GNMKMC}</span>
			 				 	</a>
		 				</h3>
		 				
		 				<ul style="display:none;" class="hierarchy_03"></ul>
		 			</div>
		 		</s:if>
		 		<s:if test="#menu.sjMenu!=null && !#menu.sjMenu.isEmpty()">
					 <div class="textlink" >
					 		<h3 onclick="showhidediv(this);" class="close"><span>${menu.GNMKMC}</span></h3>
					 		<ul style="display:none;" class="hierarchy_03">
					 			<s:iterator id="zmenu" value="#menu.sjMenu">
					 				 <li>
						 				 <a href="javascript:void(0);" onclick="setCurrentMenu(this);" class="open_03">
						 				 	 <span>${zmenu.GNMKMC}</span>
						 				 	 <input type="hidden" name="menuUrl" value="<%=systemPath %>${zmenu.DYYM}"/>
						 				 </a>
						 				 <a href="#" id="zj_${zmenu.GNMKDM}" class="cygn_add" 
						 				 onclick="zjAn(this);return false;"></a>
						 			</li>
					 			</s:iterator>
							</ul>
				      </div>
				</s:if>
			</s:iterator>
		</s:if>
		<s:else>
			<div class="textlink" id="">
				<br/>
				<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;暂无任何功能模块信息！</font>
			</div>
		</s:else>
	</body>
  </html>
