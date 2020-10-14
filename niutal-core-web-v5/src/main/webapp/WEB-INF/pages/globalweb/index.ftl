[#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] /]
<!DOCTYPE html>
<html lang="zh_CN">
	<head>
		[#include "/globalweb/head/niutal-ui-meta.ftl" /]
		[#include "/globalweb/head/niutal-ui-index.ftl" /]
		<style>
			/**针对上下文菜单的样式调整**/
			a.list-group-item:hover, a.list-group-item:focus {
				background-color:rgb(221,221,221);
			}	
		</style>
	</head>

	<body class="left-nav ${navType}">
		<div id="container" class="effect aside-float aside-bright mainnav-lg mainnav-fixed navbar-fixed">

			<header id="navbar">
				<div id="navbar-container" class="boxed">
					<!--LOGO位置-->
					<div class="navbar-header">
						<a href="initMenu.zf" class="navbar-brand">
							<img src="${messageUtil("system.stylePath")}/assets/images/logo.png" alt="Logo" class="brand-icon">
							<div class="brand-title">
								<span class="brand-text">${messageUtil("system.title")}</span>
							</div>
						</a>
					</div>
					<!--顶部导航-->
					<div class="navbar-content clearfix">
						<ul class="nav navbar-top-links pull-left">

							<li class="tgl-menu-btn">
								<a class="mainnav-toggle" href="javascript:void(0);">
									<i class="demo-pli-view-list"></i>
								</a>
							</li>

							

						</ul>
						<ul class="nav pull-left top-nav clearfix">
							
						</ul>
						<div class="more-menu pull-left">
							<div class="btn-group">
								<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							    	<i class="glyphicon glyphicon-fast-forward"></i>
							  </button>
								<ul class="dropdown-menu">
									<li>
										<a href="javascript:void(0);">Action</a>
									</li>
									<li>
										<a href="javascript:void(0);">Another action</a>
									</li>
									<li>
										<a href="javascript:void(0);">Something else here</a>
									</li>
								</ul>
							</div>
						</div>
						<ul class="nav navbar-top-links pull-right">

							<!--User dropdown-->
							<li id="dropdown-user" class="dropdown">
								<a href="javascript:void(0);" data-toggle="dropdown" class="dropdown-toggle text-right">
									<span class="pull-right">
                                    <img class="img-circle img-user media-object" src="${messageUtil("system.stylePath")}/assets/images/profile-photos/1.png" alt="Profile Picture">
                                    <!--<i class="demo-pli-male ic-user"></i>-->
                                </span>
									<div class="username hidden-xs">欢迎你，${user.xm?default("管理员")}</div>
								</a>

								<div class="dropdown-menu dropdown-menu-md dropdown-menu-right panel-default">

									[#if jsxxList?exists && jsxxList?size != 0]
									<!-- User Roles -->
									<div class="pad-all bord-btm">
										<p class="text-main">
											<span class="text-bold"> 
												[#if isMutileRoleContext]
													[@spring.message code="home.role.mutileContext"/]
												[#else]
													[@spring.message code="home.role.switch"/]
												[/#if]
											</span>
										</p>
										<ul class="head-list">
											[#-- 多角色上下文 --]
											[#if isMutileRoleContext]
												[#list jsxxList as jsxx]
												<li>
												  	<a href="javascript:void(0)" class="text-primary">
														<i class="demo-pli-male icon-lg icon-fw"></i> ${jsxx.jsmc}
														<span class="glyphicon glyphicon-ok pull-right"></span>
													</a>
												</li>
												[/#list]
											[#else]
												[#-- 单一角色上下文 --]
												[#list jsxxList as jsxx]
												<li>
													[#if jsxx.jsdm == userCurrentRole]
													  	<a href="${base}/xtgl/login/switchRole.zf?jsdm=${jsxx.jsdm}" class="${(jsxx.jsdm == userCurrentRole)?string('text-primary','')}">
															<i class="demo-pli-male icon-lg icon-fw"></i> ${jsxx.jsmc}
															<span class="glyphicon glyphicon-ok pull-right"></span>
														</a>
													[#else]
													  	<a href="${base}/xtgl/login/switchRole.zf?jsdm=${jsxx.jsdm}">
															<i class="demo-pli-male icon-lg icon-fw"></i> ${jsxx.jsmc}
														</a>
													[/#if]
												</li>
												[/#list]
											[/#if]
										</ul>
									</div>
									[/#if]
									
									<!-- User dropdown menu -->
									<ul class="head-list bord-btm">
										<li>
											<a href="javascript:void(0);" id="xgmm">
												<i class="demo-pli-information icon-lg icon-fw"></i> [@spring.message code="home.resetpwd"/]
											</a>
										</li>
									</ul>

									<!-- Dropdown footer -->
									<div class="pad-all text-right">
										<a href="${base}/logout" class="btn btn-primary">
	                                        <i class="demo-pli-unlock"></i> [@spring.message code="home.logout"/]
	                                    </a>
									</div>
								</div>
							</li>
							<!--End user dropdown-->
							[#if usedMenuList?exists && usedMenuList?size != 0]
							<li>
								<a href="javascript:void(0);" class="aside-toggle navbar-aside-icon">
									<i class="pci-ver-dots"></i>
								</a>
							</li>
							[/#if]
						</ul>
					</div>
				</div>
			</header>

			<div class="boxed" id="boxed">
				<!--中间内容-->
				<div id="content-container" class="content-container">

					<div class="zf-content">
						<div class="page-content">
							<div id="tab-general">
								<div id="tabs">
									<div class="nav-container tabs-below">
										<ul class="nav nav-tabs" role="tablist">
											<li role="presentation" class="active no-sort" id="tab_tab_home">
												<a href="#iframe_home" aria-controls="home" role="tab" data-toggle="tab"> 主页
												</a>
											</li>
										</ul>
									</div>
								</div>
								<div class="tab-content">
									<div role="tabpanel" class="tab-pane active embed-responsive embed-responsive-16by9" id="tab_home">
										<iframe id="iframe_home" class="iframeClass embed-responsive-item" style="height:400px" height="400"></iframe>
										
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>
				[#if usedMenuList?exists && usedMenuList?size != 0]
				<!--右侧侧边栏-->
				<aside id="aside-container">
					<div id="aside">
						<div class="nano">
							<div class="nano-content">

								<!--Nav tabs-->
								<!--================================-->
								<ul class="nav nav-tabs nav-justified">
									<li class="active">
										<a href="#demo-asd-tab-1" data-toggle="tab">
											<i class="demo-pli-speech-bubble-7"></i> [@spring.message code="home.visit"/]  
										</a>
									</li>
								</ul>
								<!--================================-->
								<!--End nav tabs-->

								<!-- Tabs Content -->
								<!--================================-->
								<div class="tab-content">
									
									[#if usedMenuList?exists && usedMenuList?size != 0]
									<!--First tab (Contact list)-->
									<div class="tab-pane fade in active" id="demo-asd-tab-1">
										<p class="pad-hor mar-top text-semibold text-main">
											<span class="pull-right badge badge-success">${usedMenuList.size()}</span> [@spring.message code="home.visit.lastest"/]
										</p>
										<!--Access Lastest-->
										<div class="list-group bg-trans" id="usedMenu">
											[#list usedMenuList as used]
											<a href="javascript:void(0);" data-addtab="${used.gnmkdm}" data-src="" data-tab-layout="default" data-blank-layout="default-tab" title=""
											  class="list-group-item">
												<div class="media-left pos-rel">
													<i class="fa fa-th-list"></i>
												</div>
												<div class="media-body">
													<p class="mar-no"></p>
													<small class="text-muted">${used.lastTime?datetime}</small>
												</div>
											</a>
											[/#list]
										</div>

									</div>
									<!--End first tab (Contact list)-->
 									[/#if]
								</div>
							</div>
						</div>
					</div>
				</aside>
				[/#if]
				<!--左侧侧边栏-->
				<nav id="mainnav-container">
					<div id="mainnav">
						<div id="mainnav-menu-wrap">
							<div class="nano">
								<div class="nano-content">
									<!--个人快速链接，默认隐藏-->
									<div id="mainnav-shortcut" class="hidden">
										<ul class="list-unstyled">
											<li class="col-xs-3" data-content="My Profile">
												<a class="shortcut-grid" href="javascript:void(0);">
													<i class="demo-psi-male"></i>
												</a>
											</li>
											<li class="col-xs-3" data-content="Messages">
												<a class="shortcut-grid" href="javascript:void(0);">
													<i class="demo-psi-speech-bubble-3"></i>
												</a>
											</li>
											<li class="col-xs-3" data-content="Activity">
												<a class="shortcut-grid" href="javascript:void(0);">
													<i class="demo-psi-thunder"></i>
												</a>
											</li>
											<li class="col-xs-3" data-content="Lock Screen">
												<a class="shortcut-grid" href="javascript:void(0);">
													<i class="demo-psi-lock-2"></i>
												</a>
											</li>
										</ul>
									</div>
									<!--导航正式内容-->
									<ul id="mainnav-menu" class="list-group">
									</ul>
								</div>
							</div>
						</div>

					</div>
				</nav>

			</div>
			<!--底部版权-->
			<footer id="footer">

				<div class="hide-fixed pull-right pad-rgt">${messageUtil("niutal.info.addr")}</div>

				<p class="pad-lft">${messageUtil("niutal.info.copyright")} ${messageUtil("niutal.info.company")}</p>

			</footer>
			<!--置顶icon
			<button class="scroll-top btn"><i class="pci-chevron chevron-up"></i></button>-->

		</div>
		<!--换肤功能，暂不能使用-->
		<div id="demo-set" class="demo-set hidden">
			<div id="demo-set-body" class="demo-set-body collapse">
				<div id="demo-set-alert"></div>
				<div class="pad-hor bord-btm clearfix">
					<div class="pull-right pad-top">
						<button id="demo-btn-close-settings" class="btn btn-trans">
                        <i class="pci-cross pci-circle icon-lg"></i>
                    </button>
					</div>
					<div class="media">
						<div class="media-left">
							<i class="demo-pli-gear icon-2x"></i>
						</div>
						<div class="media-body">
							<span class="text-semibold text-lg text-main">页面设定</span>
							<p class="text-muted text-sm">能按照您的喜好排版出个性化的界面。</p>
						</div>
					</div>
				</div>
				<div class="demo-set-content clearfix">
					<div class="col-xs-6 col-md-3">
						<div class="pad-all bg-gray-light">
							<p class="text-semibold text-main text-lg">布局</p>
							<p class="text-semibold text-main">盒式布局</p>
							<div class="pad-btm">
								<div class="pull-right">
									<input id="demo-box-lay" class="toggle-switch" type="checkbox">
									<label for="demo-box-lay"></label>
								</div>
								盒式布局
							</div>
							<div class="pad-btm">
								<div class="pull-right">
									<button id="demo-box-img" class="btn btn-sm btn-trans" disabled><i class="pci-hor-dots"></i>
                                </button>
								</div>
								设置背景图 <span class="label label-primary">New</span>
							</div>

							<hr class="new-section-xs bord-no">
							<p class="text-semibold text-main">动画</p>
							<div class="pad-btm">
								<div class="pull-right">
									<input id="demo-anim" class="toggle-switch" type="checkbox" checked>
									<label for="demo-anim"></label>
								</div>
								启用动画
							</div>
							<div class="pad-btm">
								<div class="select pull-right">
									<select id="demo-ease">
										<option value="effect" selected>ease (Default)</option>
										<option value="easeInQuart">easeInQuart</option>
										<option value="easeOutQuart">easeOutQuart</option>
										<option value="easeInBack">easeInBack</option>
										<option value="easeOutBack">easeOutBack</option>
										<option value="easeInOutBack">easeInOutBack</option>
										<option value="steps">Steps</option>
										<option value="jumping">Jumping</option>
										<option value="rubber">Rubber</option>
									</select>
								</div>
								动画效果
							</div>

							<hr class="new-section-xs bord-no">

							<p class="text-semibold text-main text-lg">顶部LOGO / 导航栏</p>
							<div>
								<div class="pull-right">
									<input id="demo-navbar-fixed" class="toggle-switch" type="checkbox">
									<label for="demo-navbar-fixed"></label>
								</div>
								固定位置
							</div>

							<hr class="new-section-xs bord-no">

							<p class="text-semibold text-main text-lg">底部导航</p>
							<div class="pad-btm">
								<div class="pull-right">
									<input id="demo-footer-fixed" class="toggle-switch" type="checkbox">
									<label for="demo-footer-fixed"></label>
								</div>
								固定位置
							</div>
						</div>
					</div>
					<div class="col-lg-9 pos-rel">
						<div class="row">
							<div class="col-lg-4">
								<div class="pad-all">
									<p class="text-semibold text-main text-lg">侧边栏</p>
									<p class="text-semibold text-main">导航</p>
									<div class="mar-btm">
										<div class="pull-right">
											<input id="demo-nav-fixed" class="toggle-switch" type="checkbox" checked>
											<label for="demo-nav-fixed"></label>
										</div>
										固定位置
									</div>
									<div class="mar-btm">
										<div class="pull-right">
											<input id="demo-nav-profile" class="toggle-switch" type="checkbox" checked>
											<label for="demo-nav-profile"></label>
										</div>
										个人信息组件
										<small class="label label-primary m-l-5">最新</small>
									</div>
									<div class="mar-btm">
										<div class="pull-right">
											<input id="demo-nav-shortcut" class="toggle-switch" type="checkbox">
											<label for="demo-nav-shortcut"></label>
										</div>
										快捷按钮
									</div>
									<div class="mar-btm">
										<div class="pull-right">
											<input id="demo-nav-coll" class="toggle-switch" type="checkbox">
											<label for="demo-nav-coll"></label>
										</div>
										收缩侧边栏
									</div>

									<div class="clearfix">
										<div class="select pad-btm pull-right">
											<select id="demo-nav-offcanvas">
												<option value="none" selected disabled="disabled">-- 筛选模式 --</option>
												<option value="push">推送效果</option>
												<option value="slide">顶部淡入</option>
												<option value="reveal">最大化模式</option>
											</select>
										</div>
										关闭侧边栏
									</div>

									<p class="text-semibold text-main">右侧边栏</p>
									<div class="mar-btm">
										<div class="pull-right">
											<input id="demo-asd-vis" class="toggle-switch" type="checkbox">
											<label for="demo-asd-vis"></label>
										</div>
										默认打开<span class="label label-primary m-l-5">待更新</span>
									</div>
									<div class="mar-btm">
										<div class="pull-right">
											<input id="demo-asd-fixed" class="toggle-switch" type="checkbox" checked>
											<label for="demo-asd-fixed"></label>
										</div>
										固定位置
									</div>
									<div class="mar-btm">
										<div class="pull-right">
											<input id="demo-asd-float" class="toggle-switch" type="checkbox">
											<label for="demo-asd-float"></label>
										</div>
										浮动效果 <span class="label label-primary m-l-5">最新</span>
									</div>
									<div class="mar-btm">
										<div class="pull-right">
											<input id="demo-asd-align" class="toggle-switch" type="checkbox">
											<label for="demo-asd-align"></label>
										</div>
										固定在左侧
									</div>
									<div>
										<div class="pull-right">
											<input id="demo-asd-themes" class="toggle-switch" type="checkbox">
											<label for="demo-asd-themes"></label>
										</div>
										夜间模式<span class="label label-primary m-l-5">待更新</span>
									</div>
								</div>
							</div>
							<div class="col-lg-8">
								<div id="demo-theme">
									<div class="row bg-gray-light pad-top">
										<p class="text-semibold text-main text-lg pad-lft">颜色主题（待更新，暂不能使用）</p>
										<div class="demo-theme-btn col-md-4 pad-ver">
											<p class="text-semibold text-main">顶部导航</p>
											<div class="demo-justify-theme">
												<a href="javascript:void(0);" class="demo-theme demo-a-light add-tooltip" data-theme="theme-light" data-type="a" data-title="(A). Light">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-a-navy add-tooltip" data-theme="theme-navy" data-type="a" data-title="(A). Navy Blue">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-a-ocean add-tooltip" data-theme="theme-ocean" data-type="a" data-title="(A). Ocean">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
											</div>
											<div class="demo-justify-theme">
												<a href="javascript:void(0);" class="demo-theme demo-a-lime add-tooltip" data-theme="theme-lime" data-type="a" data-title="(A). Lime">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-a-purple add-tooltip" data-theme="theme-purple" data-type="a" data-title="(A). Purple">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-a-dust add-tooltip" data-theme="theme-dust" data-type="a" data-title="(A). Dust">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
											</div>
											<div class="demo-justify-theme">
												<a href="javascript:void(0);" class="demo-theme demo-a-mint add-tooltip" data-theme="theme-mint" data-type="a" data-title="(A). Mint">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-a-yellow add-tooltip" data-theme="theme-yellow" data-type="a" data-title="(A). Yellow">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-a-well-red add-tooltip" data-theme="theme-well-red" data-type="a" data-title="(A). Well Red">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
											</div>
											<div class="demo-justify-theme">
												<a href="javascript:void(0);" class="demo-theme demo-a-coffee add-tooltip" data-theme="theme-coffee" data-type="a" data-title="(A). Coffee">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-a-prickly-pear add-tooltip" data-theme="theme-prickly-pear" data-type="a" data-title="(A). Prickly pear">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-a-dark add-tooltip" data-theme="theme-dark" data-type="a" data-title="(A). Dark">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
											</div>
										</div>
										<div class="demo-theme-btn col-md-4 pad-ver">
											<p class="text-semibold text-main">顶部LOGO</p>
											<div class="demo-justify-theme">
												<a href="javascript:void(0);" class="demo-theme demo-b-light add-tooltip" data-theme="theme-light" data-type="b" data-title="(B). Light">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-b-navy add-tooltip" data-theme="theme-navy" data-type="b" data-title="(B). Navy Blue">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-b-ocean add-tooltip" data-theme="theme-ocean" data-type="b" data-title="(B). Ocean">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
											</div>
											<div class="demo-justify-theme">
												<a href="javascript:void(0);" class="demo-theme demo-b-lime add-tooltip" data-theme="theme-lime" data-type="b" data-title="(B). Lime">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-b-purple add-tooltip" data-theme="theme-purple" data-type="b" data-title="(B). Purple">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-b-dust add-tooltip" data-theme="theme-dust" data-type="b" data-title="(B). Dust">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
											</div>
											<div class="demo-justify-theme">
												<a href="javascript:void(0);" class="demo-theme demo-b-mint add-tooltip" data-theme="theme-mint" data-type="b" data-title="(B). Mint">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-b-yellow add-tooltip" data-theme="theme-yellow" data-type="b" data-title="(B). Yellow">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-b-well-red add-tooltip" data-theme="theme-well-red" data-type="b" data-title="(B). Well red">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
											</div>
											<div class="demo-justify-theme">
												<a href="javascript:void(0);" class="demo-theme demo-b-coffee add-tooltip" data-theme="theme-coffee" data-type="b" data-title="(B). Coofee">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-b-prickly-pear add-tooltip" data-theme="theme-prickly-pear" data-type="b" data-title="(B). Prickly pear">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-b-dark add-tooltip" data-theme="theme-dark" data-type="b" data-title="(B). Dark">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
											</div>
										</div>
										<div class="demo-theme-btn col-md-4 pad-ver">
											<p class="text-semibold text-main">左侧侧边栏</p>
											<div class="demo-justify-theme">
												<a href="javascript:void(0);" class="demo-theme demo-c-light add-tooltip" data-theme="theme-light" data-type="c" data-title="(C). Light">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-c-navy add-tooltip" data-theme="theme-navy" data-type="c" data-title="(C). Navy Blue">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-c-ocean add-tooltip" data-theme="theme-ocean" data-type="c" data-title="(C). Ocean">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
											</div>
											<div class="demo-justify-theme">
												<a href="javascript:void(0);" class="demo-theme demo-c-lime add-tooltip" data-theme="theme-lime" data-type="c" data-title="(C). Lime">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-c-purple add-tooltip" data-theme="theme-purple" data-type="c" data-title="(C). Purple">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-c-dust add-tooltip" data-theme="theme-dust" data-type="c" data-title="(C). Dust">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
											</div>
											<div class="demo-justify-theme">
												<a href="javascript:void(0);" class="demo-theme demo-c-mint add-tooltip" data-theme="theme-mint" data-type="c" data-title="(C). Mint">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-c-yellow add-tooltip" data-theme="theme-yellow" data-type="c" data-title="(C). Yellow">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-c-well-red add-tooltip" data-theme="theme-well-red" data-type="c" data-title="(C). Well Red">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
											</div>
											<div class="demo-justify-theme">
												<a href="javascript:void(0);" class="demo-theme demo-c-coffee add-tooltip" data-theme="theme-coffee" data-type="c" data-title="(C). Coffee">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-c-prickly-pear add-tooltip" data-theme="theme-prickly-pear" data-type="c" data-title="(C). Prickly pear">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
												<a href="javascript:void(0);" class="demo-theme demo-c-dark add-tooltip" data-theme="theme-dark" data-type="c" data-title="(C). Dark">
													<div class="demo-theme-brand"></div>
													<div class="demo-theme-head"></div>
													<div class="demo-theme-nav"></div>
												</a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div id="demo-bg-boxed" class="demo-bg-boxed">
							<div class="demo-bg-boxed-content">
								<p class="text-semibold text-main text-lg mar-no">Background Images</p>
								<p class="text-sm text-muted">Add an image to replace the solid background color</p>
								<div class="row">
									<div class="col-lg-4 text-justify">
										<p class="text-semibold text-main">Blurred</p>
										<div id="demo-blurred-bg" class="text-justify">
											<!--Blurred Backgrounds-->
										</div>
									</div>
									<div class="col-lg-4 text-justify">
										<p class="text-semibold text-main">Polygon &amp; Geometric</p>
										<div id="demo-polygon-bg" class="text-justify">
											<!--Polygon Backgrounds-->
										</div>
									</div>
									<div class="col-lg-4 text-justify">
										<p class="text-semibold text-main">Abstract</p>
										<div id="demo-abstract-bg" class="text-justify">
											<!--Abstract Backgrounds-->
										</div>
									</div>
								</div>
							</div>
							<div class="demo-bg-boxed-footer">
								<button id="demo-close-boxed-img" class="btn btn-primary">Close</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<button id="demo-set-btn" class="btn" data-toggle="collapse" data-target="#demo-set-body"><i class="demo-psi-gear icon-lg"></i></button>
		</div>
		[#include "/globalweb/head/niutal-ui-strength.ftl" /] 
		[#if gnmkdm??]
		<script type="text/javascript">
			$(function() {
				$(".zf-left-nav-ul a[data-addtab='${gnmkdm}']").click();
			});
		</script>
		[/#if]
		[#include "/globalweb/head/niutal-ui-validation.ftl" /]
	</body>

</html>