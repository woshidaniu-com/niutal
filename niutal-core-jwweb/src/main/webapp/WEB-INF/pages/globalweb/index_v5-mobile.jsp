<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html lang="zh-CN">
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/indexhead_v5.ini"%>
	<script type="text/javascript" src="<%=systemPath%>/js/browse/browse-judge.js?ver=<%=jsVersion%>"></script>
	<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/index_v5.js?ver=<%=jsVersion%>"></script>
</head>
<body>
<!-- requestMap中的参数为系统级别控制参数，请勿删除 -->
<form id="requestMap" style="display:none;">
	<input type="hidden" id="sessionUserKey" value="<s:property value="#session.user.yhm"/>" />
	<input type="hidden" id="gnmkdmKey" value="index" />
</form>
<div class="jw-shade"></div>
<!-- top -->
<div class="navbar navbar-default navbar-static-top top1">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand logo_2" href="#"><img src="<%=stylePath %>/assets/images/logo/logo_jw_w.png" style="margin-top:-3px" /><%=MessageUtil.getLocaleText("system.title")%></a>
		</div>
		 <%-- <ul class="nav navbar-nav navbar-right hidden-xs">
			<li>
				<a href="#" class="dropdown-toggle grxx" data-toggle="dropdown"><img src="<%=stylePath %>/assets/images/user_logo.jpg" width="28" height="28"></a>
				<ul class="dropdown-menu">
					<li class="dropdown-submenu">
						<a href="javascript:void(0);"><i class="top_png qh"></i><s:text name="N00002.002"/></a>
						<ul class="dropdown-menu left">
							 <s:iterator value="#session.user.jsList" var="js" status="stat">
				                <li>
				                	<s:if test="#session.user.jsdm==jsdm">
				                		<a tabindex="-1" href="#">
						                	<span class="glyphicon glyphicon-ok float_r"></span>
						                	<s:property value="jsmc"/>
				                		</a>
			                			<label>
			                				dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd<s:property value="jsmc"/>
			                				<i class="glyphicon glyphicon-ok float_r "></i>
			                			</label>
				                	</s:if>
				                	<s:else>
				                		
				                		<a tabindex="-1" href="<%=systemPath%>/xtgl/index_initMenu.html?jsdm=<s:property value="jsdm"/>&t=<%=new Date().getTime()%>">
						                	<s:property value="jsmc"/>
				                		</a>
				                	</s:else>
				                </li>
				            </s:iterator>
						</ul>
					</li>
					<li class="divider"></li>
					<li><a href="javascript:void(0);" id="updatePassword"><i class="top_png mm"></i><s:text name="N00002.003"/></a></li>
					<li class="divider"></li>
					<li><a href="javascript:void(0);" id="exit"><i class="top_png tc"></i><s:text name="N00002.004"/></a></li>
				</ul>
			</li>
		</ul> 
		 --%>
		
		<ul class="nav navbar-nav navbar-right  hidden-lg hidden-md hidden-xs nav-phone">
			<li>
				<a href="#" class="dropdown-toggle2 grxx"><img src="<%=stylePath %>/assets/images/user_logo.jpg" width="28" height="28"></a>
				<ul class="dropdown-menu2">
					<li class="dropdown-submenu">
						<a href="javascript:void(0);"><i class="top_png qh"></i><s:text name="N00002.002"/></a>
						<ul class="dropdown-menu2 left">
							 <s:iterator value="#session.user.jsList" var="js" status="stat">
				                <li>
				                	<s:if test="#session.user.jsdm==jsdm">
				                		<a tabindex="-1" href="#">
						                	<span class="glyphicon glyphicon-ok float_r"></span>
						                	<s:property value="jsmc"/>
				                		</a>
			                			<%-- <label>
			                				dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd<s:property value="jsmc"/>
			                				<i class="glyphicon glyphicon-ok float_r "></i>
			                			</label> --%>
				                	</s:if>
				                	<s:else>
				                		
				                		<a tabindex="-1" href="<%=systemPath%>/xtgl/index_initMenu.html?jsdm=<s:property value="jsdm"/>&t=<%=new Date().getTime()%>">
						                	<s:property value="jsmc"/>
				                		</a>
				                	</s:else>
				                </li>
				            </s:iterator>
						</ul>
					</li>
					<li class="divider"></li>
					<li><a href="javascript:void(0);" id="updatePassword"><i class="top_png mm"></i><s:text name="N00002.003"/></a></li>
					<li class="divider"></li>
					<li><a href="javascript:void(0);" id="exit"><i class="top_png tc"></i><s:text name="N00002.004"/></a></li>
				</ul>
			</li>
		</ul> 
		<form class="navbar-form navbar-right">
			<%--<input type="text" class="form-control" placeholder="搜索服务功能">
		  	--%>
		  	<s:if test="#session.user.jsdm == 'js' || #session.user.jsdm == 'xs'">
		  	</s:if>
		  		<s:select id="localChange" name="language" cssClass="language-select chosen-select" list="languageList" listKey="key" listValue="value" ></s:select>
		</form>
	</div>
</div>
<!-- top-end -->
<!-- 菜单 -->
<div class="navbar_index" style="">
	<div class="container">
	  <nav class="navbar navbar-default navbar-static" role="navigation">
	    <div class="collapse navbar-collapse">
	      <ul class="nav navbar-nav">
	     	${RoleMenu}
	      </ul>
	    </div>
	  </nav>
	</div>
</div>
<!-- 主体 -->
<div class="container padding-150">
	<div class="row">
		<div class="col-md-3 col-sm-4">
			<div class="index_zjsy show-grid-15" >
				<h3><span><s:text name="N00002.005"/></span></h3>
				<ul class="list-unstyled" id="index_zjsy" style="height: 350px;">
					<s:if test="#session.user.jsdm == 'js' ">
						<%=IndexAction.cxLinks(new String[]{"N3035","N255005","N2150","N1551","N211205","N2122","N154610","N153010"})%>
					</s:if>
					<s:elseif test="#session.user.jsdm == 'xs' ">
						<li id="sqltool" class="col-md-6 col-sm-6 col-xs-6" style="display:none"><a href="../sql.jsp" target="_blank"><img src="<%=stylePath %>/assets/images/ico/ico_but1.png" alt="成绩查询"><h5>SQL翻译</h5></a></li>
						<%=IndexAction.cxLinks(new String[]{"N253508","N253512","N105505","N305005","N2510","N1056","N4010"})%>
					</s:elseif>
					<s:else>
						<li id="sqltool" class="col-lg-4 col-md-4 col-sm-6 col-xs-6" style="display:none"><a href="../sql.jsp" target="_blank"><img src="<%=stylePath %>/assets/images/ico/ico_but1.png" alt="成绩查询"><h5>SQL翻译</h5></a></li>
						<li class="col-lg-4 col-md-4 col-sm-6 col-xs-6"><a onclick="clickMenu('N253508','/kbcx/xskbcx_cxXskbcxIndex.html','课表查询'); return false;" href="#" target="_blank"><img src="<%=stylePath %>/assets/images/ico/ico_but1.png" alt="课表查询"><h5>课表查询</h5></a></li>
						<li class="col-lg-4 col-md-4 col-sm-6 col-xs-6"><a onclick="clickMenu('N253512','/xsxk/zzxkyzb_cxZzxkYzbIndex.html','自主选课'); return false;" href="#" target="_blank"><img src="<%=stylePath %>/assets/images/ico/ico_but2.png" alt="选课"><h5>自主选课</h5></a></li>
						<li class="col-lg-4 col-md-4 col-sm-6 col-xs-6"><a onclick="clickMenu('N105505','/xjyj/xjyj_cxXjyjIndex.html','学业情况'); return false;" href="#" target="_blank"><img src="<%=stylePath %>/assets/images/ico/ico_but3.png" alt="学业情况"><h5>学业情况</h5></a></li>
						<li class="col-lg-4 col-md-4 col-sm-6 col-xs-6"><a onclick="clickMenu('N2510','/kjgl/kjbm_cxXskjbm.html','报名'); return false;" href="#" target="_blank"><img src="<%=stylePath %>/assets/images/ico/ico_but4.png" alt="报名"><h5>报名</h5></a></li>
						<li class="col-lg-4 col-md-4 col-sm-6 col-xs-6"><a onclick="clickMenu('N1056','/cxbm/cxbm_cxXscxbmIndex.html','重修报名'); return false;" href="#" target="_blank"><img src="<%=stylePath %>/assets/images/ico/ico_but8.png" alt="重修报名"><h5>重修报名</h5></a></li>
						<li class="col-lg-4 col-md-4 col-sm-6 col-xs-6"><a onclick="clickMenu('N4010','/xspjgl/xspj_cxXspjIndex.html','教学质量评价'); return false;" href="#" target="_blank"><img src="<%=stylePath %>/assets/images/ico/ico_but9.png" alt="教学质量评价"><h5>教学质量评价</h5></a></li>
					</s:else>
				</ul>
			</div>
			
			<div class="index_wdyy">
				<h3><span><s:text name="N00002.006"/></span><a href="#" class="sz"></a></h3>
				<ul class="list-unstyled"  id="index_wdyy" style="height: 350px;">
					<%=IndexAction.cxLinks(null)%>
				</ul>
			</div>
			
		</div>
		<div class="col-md-9 col-sm-8">
			<div class="row">
				<div class="col-md-12">
					<div class="index_grxx">
						
						<div class="col-md-5" id="yhxxIndex">
													
						</div>
						<div class="col-md-7" id="newsnotice"  style="height: 150px;">
							<!-- 新闻通知区域 -->
						</div>
						
					</div>
				</div>
				<div class="col-md-6">
					<div class="index_rctx">
						 
					</div>
				</div>
				<div class="col-md-6">
					<div class="index_kbcx">
						 
					</div>
				</div>
				<div class="col-md-6">
					<div class="index_kscj">
						 
					</div>
				</div>
				<div class="col-md-6">
					<div class="index_xl">
						 
					</div>
				</div>
				
			</div>
		</div>
	</div>
</div>
<!-- footer --> 
<jsp:include page="bottom.jsp"/>
<!-- footer-end -->
<script type="text/javascript">
	$(function(){
		$('.dropdown-toggle2').click(function(){
			$(this).parent().toggleClass('open');
			$('.jw-shade').toggle(); 
		});
		$('.jw-shade,.dropdown-menu2 li:not(".dropdown-submenu"),.dropdown-submenu .dropdown-menu2').click(function(){
			$('.nav-phone').find('.open').removeClass('open');
			$('.jw-shade').hide();
		});
		$('.dropdown-submenu').click(function(){
			$(this).find('.dropdown-menu2').toggle();
		});
	});
</script>
</body>
<%@ include file="/WEB-INF/pages/globalweb/head/validation.ini"%>
</html>