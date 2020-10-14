<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html lang="zh-CN">
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/indexhead_v5.ini"%>
</head>
<body class="body-container nav-menu-push" data-class="cbp-pull-left" >
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
			<a class="navbar-brand logo_2" href="#"><img src="<%=stylePath %>/images/logo/logo_jw_w.png" style="margin-top:-3px" /><%=MessageUtil.getLocaleText("system.title")%></a>
		</div>
		<ul class="nav navbar-nav navbar-right hidden-xs">
			<li>
				<a href="#" class="dropdown-toggle grxx" data-toggle="dropdown"><img src="<%=stylePath %>/images/user_logo.jpg" width="28" height="28"></a>
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
					<li class="dropdown-submenu">
						<a href="javascript:void(0);"><i class="top_png qh"></i>导航切换</a>
						<ul class="dropdown-menu left nav-menu-switch">
							<li data-direction="left" data-freely="vertical" data-push="" data-class=""><a href="javascript:void(0);">导航左侧放置</a></li>
							<li data-direction="right" data-freely="vertical" data-push="" data-class=""><a href="javascript:void(0);">导航右侧放置</a></li>
							<li data-direction="left" data-freely="vertical" data-push="push" data-class="nav-menu-push-toleft"><a href="javascript:void(0);">导航向右推出</a></li>
							<li data-direction="right" data-freely="vertical" data-push="push" data-class="nav-menu-push-toright"><a href="javascript:void(0);">导航向左推出</a></li>
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
	     	${RoleMenu}
	    	</div>
	  	</nav> 
	  	
	  <%-- 	<nav class="nav-menu nav-menu-left nav-menu-vertical">
			<div class="nav-menu-content">
				<div class="nav-menu-item fl">
					<div class="nav-title shake"><span class="glyphicon glyphicon-cloud" aria-hidden="true" data-icon="cloud"></span><span>系统管理</span><span class="fa fa-fw fa-angle-right fa-sm"></span></div>
					<div class="content2">
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>系统设置</div>
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span><span>权限管理</span><span class="fa fa-fw fa-angle-right fa-sm"></span></div>
						<div class="content2">
							<div class="nav-title" data-addtab="test1" data-src="/newLeftNav/test1.html"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>角色管理</div>
							<div class="nav-title" data-addtab="test2" data-src="/newLeftNav/test2.html"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>用户管理</div>
							<div class="nav-title" data-addtab="test3" data-src="/newLeftNav/test3.html"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>学生数据归属范围</div>
							<div class="nav-title" data-addtab="test4" data-src="/newLeftNav/test4.html"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>课程数据归属范围</div>
						</div>
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span><span>基础数据</span><span class="fa fa-fw fa-angle-right fa-sm"></span></div>
						<div class="content2">
							<div class="nav-title" data-addtab="test5" data-src="/newLeftNav/test4.html" ><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>基础数据</div>
							<div class="nav-title" data-addtab="test6" data-src="/newLeftNav/test4.html" ><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>学校信息</div>
							<div class="nav-title" data-addtab="test4d" data-src="/newLeftNav/test4.html" ><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>机构信息维护</div>
							<div class="nav-title" data-addtab="test41" data-src="/newLeftNav/test4.html" ><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>年级代码维护</div>
							<div class="nav-title" data-addtab="test42" data-src="/newLeftNav/test4.html" ><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>专业信息维护</div>
							<div class="nav-title" data-addtab="test43" data-src="/newLeftNav/test4.html" ><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>学信专业维护</div>
						</div>
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>功能操作描述</div>
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>批量数据修改设置</div>
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>日志管理</div>
						<div class="content2">
							<div class="nav-title" data-addtab="test44" data-src="/newLeftNav/test4.html" ><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>日志查看</div>
						</div>
						<div class="nav-title" data-addtab="test45" data-src="/newLeftNav/test4.html" ><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>通知管理</div>
						<div class="content2">
							<div class="nav-title" data-addtab="test46" data-src="/newLeftNav/test4.html" ><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>通知发布</div>
						</div>
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>流程配置</div>
						<div class="content2">
							<div class="nav-title" data-addtab="test47" data-src="/newLeftNav/test4.html" ><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>流程配置</div>
						</div>
					</div>
					<div class="nav-title"><span class="glyphicon glyphicon-cloud" aria-hidden="true" data-icon="cloud"></span><span>自定义功能管理</span><span class="fa fa-fw fa-angle-right fa-sm"></span></div>
					<div class="content2">
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>基础自动完成字段维护</div>
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>基础查询字段维护</div>
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>基础JQuery组件维护</div>
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>自定义功能设计</div>
					</div>
					<div class="nav-title"><span class="glyphicon glyphicon-cloud" aria-hidden="true" data-icon="cloud"></span><span>学籍管理</span><span class="fa fa-fw fa-angle-right fa-sm"></span></div>
					<div class="content2">
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>系统设置</div>
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>学籍基础数据</div>
						<div class="content2">
							<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>学年信息</div>
							<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>奖励项目</div>
							<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>交流地区</div>
							<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>交流项目类型</div>
						</div>
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>班级信息</div>
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>学生信息管理</div>
						<div class="content2">
							<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>学生信息修改授权</div>
							<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>学生信息维护</div>
							<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>学生信息修改审核</div>
							<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>学生信息查询</div>
						</div>
					</div>
					<div class="nav-title"><span class="glyphicon glyphicon-cloud" aria-hidden="true" data-icon="cloud"></span><span>教学计划管理</span><span class="fa fa-fw fa-angle-right fa-sm"></span></div>
					<div class="content2">
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>系统设置</div>
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>计划基础数据</div>
						<div class="content2">
							<div class="nav-title" data-src="test1"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>大类维护</div>
							<div class="nav-title" data-src="test2"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>课程类别维护</div>
							<div class="nav-title" data-src="test3"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>课程归属维护</div>
							<div class="nav-title" data-src="test4"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>课程性质代码维护</div>
						</div>
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>课程组管理</div>
						<div class="content2">
							<div class="nav-title" data-src="test1"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>课程组维护</div>
							<div class="nav-title" data-src="test2"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>课程教师任课资格</div>
							<div class="nav-title" data-src="test3"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>课程任课资格申请</div>
						</div>
						<div class="nav-title"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>教学安排</div>
					</div>
				</div>

				<!--图标显示-->
				<div class="nav-menu-catalog fl">
					<div class="item">
						<!--<span class="glyphicon glyphicon-cloud" aria-hidden="true" data-icon="cloud"></span>-->
					</div>
				</div>
			</div>
			<div class="nav-menu-toggle"><span class="fa fa-fw fa-angle-double-right"></span></div>
		</nav>
		 --%>
	</div>
	<div class="nav-tab-top"></div>
	<div class="">
		<div class="row no-margin">
			<div class="col-md-12 no-padding"  id="tabs">
				 <div class="nav-container">
					<!-- Nav tabs -->
					<ul class="nav nav-tabs" role="tablist">
						<li role="presentation" class="active" id="tab_tab_home">
							<a href="#home" aria-controls="home" role="tab" data-toggle="tab">
								<span class="fa fa-fw fa-home fa-sm" aria-hidden="true"></span>
								主页
							</a>
						</li>
					</ul>
					<!-- Tab panes -->
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active embed-responsive embed-responsive-16by9" id="home">
							<!-- 主体 -->
								<div class="container-iframe padding-150">
									<div class="row">
										<div class="col-md-3 col-sm-4">
											<div class="index_zjsy show-grid-15" >
												<h3><span><s:text name="N00002.005"/></span></h3>
												<ul class="list-unstyled" id="index_zjsy" style="height: 350px;">
													<s:if test="#session.user.jsdm == 'js' ">
														<%=IndexAction.cxLinks(new String[]{"N3035","N255005","N2150","N1551","N211205","N2122","N154610","N153010"})%>
													</s:if>
													<s:elseif test="#session.user.jsdm == 'xs' ">
														<li id="sqltool" class="col-md-6 col-sm-6 col-xs-6" style="display:none"><a href="../sql.jsp" target="_blank"><img src="<%=stylePath %>/images/ico/ico_but1.png" alt="成绩查询"><h5>SQL翻译</h5></a></li>
														<%=IndexAction.cxLinks(new String[]{"N253508","N253512","N105505","N305005","N2510","N1056","N4010"})%>
													</s:elseif>
													<s:else>
														<li id="sqltool" class="col-lg-4 col-md-4 col-sm-6 col-xs-6" style="display:none"><a href="../sql.jsp" target="_blank"><img src="<%=stylePath %>/images/ico/ico_but1.png" alt="成绩查询"><h5>SQL翻译</h5></a></li>
														<li class="col-lg-4 col-md-4 col-sm-6 col-xs-6"><a onclick="clickMenu('N253508','/kbcx/xskbcx_cxXskbcxIndex.html','课表查询'); return false;" href="#" target="_blank"><img src="<%=stylePath %>/images/ico/ico_but1.png" alt="课表查询"><h5>课表查询</h5></a></li>
														<li class="col-lg-4 col-md-4 col-sm-6 col-xs-6"><a onclick="clickMenu('N253512','/xsxk/zzxkyzb_cxZzxkYzbIndex.html','自主选课'); return false;" href="#" target="_blank"><img src="<%=stylePath %>/images/ico/ico_but2.png" alt="选课"><h5>自主选课</h5></a></li>
														<li class="col-lg-4 col-md-4 col-sm-6 col-xs-6"><a onclick="clickMenu('N105505','/xjyj/xjyj_cxXjyjIndex.html','学业情况'); return false;" href="#" target="_blank"><img src="<%=stylePath %>/images/ico/ico_but3.png" alt="学业情况"><h5>学业情况</h5></a></li>
														<li class="col-lg-4 col-md-4 col-sm-6 col-xs-6"><a onclick="clickMenu('N2510','/kjgl/kjbm_cxXskjbm.html','报名'); return false;" href="#" target="_blank"><img src="<%=stylePath %>/images/ico/ico_but4.png" alt="报名"><h5>报名</h5></a></li>
														<li class="col-lg-4 col-md-4 col-sm-6 col-xs-6"><a onclick="clickMenu('N1056','/cxbm/cxbm_cxXscxbmIndex.html','重修报名'); return false;" href="#" target="_blank"><img src="<%=stylePath %>/images/ico/ico_but8.png" alt="重修报名"><h5>重修报名</h5></a></li>
														<li class="col-lg-4 col-md-4 col-sm-6 col-xs-6"><a onclick="clickMenu('N4010','/xspjgl/xspj_cxXspjIndex.html','教学质量评价'); return false;" href="#" target="_blank"><img src="<%=stylePath %>/images/ico/ico_but9.png" alt="教学质量评价"><h5>教学质量评价</h5></a></li>
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
														<div class="row">
															<div class="col-md-5" id="yhxxIndex">
																						
															</div>
															<div class="col-md-7" id="newsnotice"  style="height: 150px;">
																<!-- 新闻通知区域 -->
															</div>
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
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- footer --> 
<jsp:include page="bottom.jsp"/>
<!-- footer-end -->
</body>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/index_v5.js?ver=<%=jsVersion%>"></script>
<%@ include file="/WEB-INF/pages/globalweb/head/validation.ini"%>
</html>