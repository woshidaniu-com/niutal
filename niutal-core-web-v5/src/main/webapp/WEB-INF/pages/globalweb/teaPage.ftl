[#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] /]
<!doctype html>
<html>
<head>
 	<script type="text/javascript" src="${base}/js/globalweb/comp/xtgl/index.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	<script type="text/javascript">
		jQuery(function($) {
			initMenu();
			//绑定退出按钮
			$("#exit").click(function() {
				document.location.href = "${base}/logout";
			});
			//绑定修改密码
			$("#updatePassword").click(function() {
				$.showDialog(_path + "/xtgl/yhgl/xgMm.zf", '修改密码', modifyConfig);
			});
		});
	</script>
</head>
<body style="background:#fafafa;" >
    	<!-- top -->
	<div class="navbar navbar-default navbar-static-top top1">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand logo_2" href="#">
				<img src="${messageUtil("system.stylePath")}/assets/images/logo/logo_jw_w.png" style="margin-top: -3px" />${messageUtil("system.title")}</a>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<li>
						<a href="#" class="dropdown-toggle grxx" data-toggle="dropdown">
						<img src="${messageUtil("system.stylePath")}/assets/images/user_logo.jpg" width="28" height="28"></a>
						<ul class="dropdown-menu">
							[#if jsxxList != null &&  (jsxxList?size>1)]
								<li class="dropdown-submenu">
									<a href="javascript:void(0);"><i class="top_png qh"></i>切换角色</a>
									<ul class="dropdown-menu left">
										[#list jsxxList as jsxx]
											<li>
												[#if Session.user.jsdm == jsxx.jsdm]
													<a tabindex="-1" href="#">
									                	<span class="glyphicon glyphicon-ok float_r"></span>
									                	${jsxx.jsmc}
							                		</a>
												[#else]
													<a tabindex="-1" href="${base}/xtgl/login/switchRole.zf?jsdm=${jsxx.jsdm}">
									                	${jsxx.jsmc}
							                		</a>
												[/#if]
							                </li>
										[/#list]
									</ul>
								</li>
								<li class="divider"></li>
							[/#if]
							[#if Session.user.yhlx=='teacher']
							<li>
								<a href="javascript:void(0);" id="updatePassword"><i class="top_png mm"></i>修改密码</a>
							</li>
							[/#if]
							 [@shiro.hasRole name="admin"]
							 	<li class="divider"></li>
								<li><a href="${base}/druid" target="_blank"><i class="top_png jk"></i>系统监控</a></li>
							 [/@shiro.hasRole]
							<li class="divider"></li>
							<li><a href="javascript:void(0);" id="exit"><i class="top_png tc"></i>退出</a></li>
						</ul>
				</li>
			</ul>
<!-- 			<form class="navbar-form navbar-right"> -->
<!-- 				<input type="text" class="form-control" placeholder="搜索服务功能"> -->
<!-- 			</form> -->
				<form id="requestMap" style="display:none;">
					<input type="hidden" id="sessionUserKey" value=" ${Session.user.yhm} " />
					<input type="hidden" id="gnmkdmKey" value="index" />
				</form>
		</div>
	</div>
	<!-- top-end -->
	<div class="navbar_index" style="">
		<div class="container">
			<nav class="navbar navbar-default navbar-static" role="navigation">
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						[#list topMenuList as menu]
							<li class="dropdown">
									<a id="drop_${menu.GNMKDM }"  name="topMenu"  gnmkdm="${menu.GNMKDM }"  href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">
										${menu.GNMKMC}<b class="caret"></b>
									</a>
								</li>
						[/#list]
					</ul>
				</div>
			</nav>
		</div>
	</div>
	<div class="container padding-150">
		<div class="row">
			<div class="col-md-3 col-sm-4">
				<div class="index_zjsy show-grid-15">
					<h3>
						<span>最近使用</span>
					</h3>
					<ul class="list-unstyled">
						<li class="col-md-6 col-sm-6 col-xs-6"><a href="../sql.jsp"
							target="_blank"><img
								src="${messageUtil("system.stylePath")}/assets/images/ico/ico_but1.png" alt="成绩查询">
							<h5>SQL翻译</h5></a></li>
						<li class="col-md-6 col-sm-6 col-xs-6"><a
							onclick="clickMenu('N253505','/xsxk/tjxk_cxTjxkIndex.html','推荐选课'); return false;"
							href="#" target="_blank"><img
								src="${messageUtil("system.stylePath")}/assets/images/ico/ico_but2.png" alt="选课">
							<h5>推荐选课</h5></a></li>
						<li class="col-md-6 col-sm-6 col-xs-6"><a
							href="../error4.jsp" target="_blank"><img
								src="${messageUtil("system.stylePath")}/assets/images/ico/ico_but3.png" alt="学业情况">
							<h5>学业情况</h5></a></li>
						<li class="col-md-6 col-sm-6 col-xs-6"><a
							href="../error4.jsp" target="_blank"><img
								src="${messageUtil("system.stylePath")}/assets/images/ico/ico_but4.png" alt="项目报名">
							<h5>项目报名</h5></a></li>
					</ul>
				</div>

				<div class="index_wdyy">
					<h3>
						<span>我的应用</span><a href="#" class="sz"></a>
					</h3>
					<ul class="list-unstyled">
						<li class="col-md-6 col-sm-6 col-xs-6"><a
							onclick="clickMenu('N011001','/xtgl/xwgl_cxXw.html','通知发布');return false;"
							href="#" target="_blank"><img
								src="${messageUtil("system.stylePath")}/assets/images/ico/ico_but5.png" alt="新闻管理">
							<h5>通知发布</h5></a></li>
						<li class="col-md-6 col-sm-6 col-xs-6"><a
							onclick="clickMenu('N210505','/pkgl/xlgl_cxXlIndex.html','校历管理');return false;"
							href="#" target="_blank"><img
								src="${messageUtil("system.stylePath")}/assets/images/ico/ico_but6.png" alt="校历维护">
							<h5>校历维护</h5></a></li>
						<li class="col-md-6 col-sm-6 col-xs-6"><a
							href="../error4.jsp" target="_blank"><img
								src="${messageUtil("system.stylePath")}/assets/images/ico/ico_but7.png" alt="项目报名">
							<h5>项目报名</h5></a></li>
						<li class="col-md-6 col-sm-6 col-xs-6"><a
							href="../error4.jsp" target="_blank"><img
								src="${messageUtil("system.stylePath")}/assets/images/ico/ico_but8.png" alt="重修报名">
							<h5>重修报名</h5></a></li>
						<li class="col-md-6 col-sm-6 col-xs-6"><a
							href="../error4.jsp" target="_blank"><img
								src="${messageUtil("system.stylePath")}/assets/images/ico/ico_but9.png" alt="教学质量评价">
							<h5>教学质量评价</h5></a></li>
						<li class="col-md-6 col-sm-6 col-xs-6"><a
							href="../xtgl/init_cxGnPage.html?gnmkdm=N0105444&jsName=dddddddddddd"
							target="_blank"><img
								src="${messageUtil("system.stylePath")}/assets/images/ico/ico_but10.png" alt="教室借用">
							<h5>教室借用</h5></a></li>
					</ul>
				</div>

			</div>
			<div class="col-md-9 col-sm-8">
				<div class="row">
					<div class="col-md-6">
						<div class="index_rctx">
							<h5 class="index_title">
								<span class="title">日常提醒</span>
							</h5>
							<div class="list-group">
								<a href="#" class="list-group-item"> <i class="index_png i1"></i><span
									class="time">5月08日-6月06日</span><span class="title">开始选课了！</span><span
									class="but1 float_r">开始选课</span>
								</a> <a href="#" class="list-group-item"> <i
									class="index_png i2"></i><span class="time">5月08日-6月06日</span><span
									class="title">可以查英语等级考试成绩了！</span><span class="but2 float_r">详情</span>
								</a> <a href="#" class="list-group-item"> <i
									class="index_png i1"></i><span class="time">5月08日-6月06日</span><span
									class="title">开始选课了！</span><span class="but1 float_r">开始选课</span>
								</a> <a href="#" class="list-group-item"> <i
									class="index_png i1"></i><span class="time">5月08日-6月06日</span><span
									class="title">开始选课了！</span><span class="but1 float_r">开始选课</span>
								</a>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="index_kbcx">
							<h5 class="index_title">
								<span class="title">课表查询</span><span class="float_r"><a
									href="#" class="cur">今日课表</a><a href="#">本周课表</a></span>
							</h5>
							<div class="list-group">
								<a href="#" class="list-group-item"> <span class="time">09:00-09:45</span><span
									class="title">机电控制系统设计技术 </span><span class="address">2号教学楼
										302室</span>
								</a> <a href="#" class="list-group-item"> <span class="time">10:00-10:45</span><span
									class="title">社会主义理论与实践研究 </span><span class="address">2号教学楼
										302室</span>
								</a> <a href="#" class="list-group-item"> <span class="time">11:00-11:45</span><span
									class="title">中国特色社会主义 </span><span class="address">2号教学楼
										302室</span>
								</a> <a href="#" class="list-group-item"> <span class="time">14:00-14:45</span><span
									class="title">中国特色社会主义理论与实践研究</span><span class="address">2号教学楼
										302室</span>
								</a> <a href="#" class="list-group-item"> <span class="time">15:00-15:45</span><span
									class="title">生命科学与现代生物技术 </span><span class="address">2号教学楼
										302室</span>
								</a>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="index_kscj">
							<h5 class="index_title">
								<span class="title">考试/成绩</span><span class="float_r"><a
									href="#">其他学期</a></span>
							</h5>
							<p class="text-center">2012-2013学年第二学期</p>
							<div class="list-group">
								<a href="#" class="list-group-item"> <span class="title">大学物理（乙）Ⅰ（导修）</span><span
									class="fraction float_r">85</span>
								</a> <a href="#" class="list-group-item"> <span class="title">环境资源调查与评价实习</span><span
									class="fraction float_r">85</span>
								</a> <a href="#" class="list-group-item"> <span class="title">低温与人工环境课程设计</span><span
									class="fraction float_r">85</span>
								</a> <a href="#" class="list-group-item"> <span class="title">生命科学导论实验</span><span
									class="fraction float_r">85</span>
								</a> <a href="#" class="list-group-item"> <span class="title">中国特色社会主义理论与实践研究</span><span
									class="fraction float_r">85</span>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	[#include "/globalweb/head/niutal-ui-validation.ftl" /]
</body>
</html>

















