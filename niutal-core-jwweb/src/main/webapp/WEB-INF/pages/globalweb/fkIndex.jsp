<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html lang="zh-CN">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/indexhead_v5.ini"%>
		<script type="text/javascript">
			function toFkGnPage(gnmkdm,gnmkmc,dyym){
				openBlank('../xtgl/init_cxFkGnPage.html',{'gnmkdm':gnmkdm,'dyym':dyym,'gnmkmc':gnmkmc},true);
			}
			
			//通过POST提交，打开一个新的页面
			function openBlank(action,data,n){
			     var form = jQuery("<form/>").attr('action',action).attr('method','post');
			     if(n){
			    	 form.attr('target','_blank');
			     }
			     var input = '';
			     jQuery.each(data, function(i,n){
			          input += '<input type="hidden" name="'+ i +'" value="'+ n +'" />';
			     });
			     form.append(input).appendTo("body").css('display','none').submit();
			 }
		</script>
	</head>
	<body>
		<!-- top -->
		<div class="navbar navbar-default navbar-static-top top1">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">
						<img src="<%=stylePath%>/images/logo/logo.png" width="290" height="30" />
					</a>
				</div>
				<div class="navbar-right show-padtop-15 color-white">
					欢迎你，游客
				</div>
			</div>
		</div>
		<!-- top-end -->

		<div class="container padding-150 index_main">
			<div class="row">
				<div class="col-md-7 col-sm-12">
					<div class="index_zjsy show-grid-15">
						<h3><span>我的应用</span></h3>
						<ul class="list-unstyled row">
							<s:iterator value="gnList">
								<li class="col-md-3 col-sm-4 col-xs-6">
									<a href="#" onclick="toFkGnPage('${gnmkdm}','${gnmkmc}','${dyym}')">
										<img src="<%=stylePath%>/${icon}" alt="${gnmkmc}">
										<h5>${gnmkmc}</h5>
									</a>
								</li>
							</s:iterator>							
						</ul>
					</div>
				</div>
				
				<div class="col-md-5 col-sm-12">
					<div class="index_grxx">
						<h5 class="index_title">
							<span class="title">通知</span>
						</h5>
						<div class="list-group" style="padding: 10px; height: 216px;">
							<a href="#" class="list-group-item"> <span class="title">关于2013级学生跨大类确认专业后申请退选前置课程的...</span><i
								class="index_png new"></i><span class="time float_r">05-29</span>
							</a>
							<a href="#" class="list-group-item"> <span class="title">关于2014-2015学年秋冬学期本科课程选课安排的通知</span><i
								class="index_png hot"></i><span class="time float_r">05-29</span>
							</a>
							<a href="#" class="list-group-item"> <span class="title">浙江省大学生数学竞赛(微积分)大纲</span><span
								class="time float_r">05-29</span> </a>
							<a href="#" class="list-group-item"> <span class="title">浙江省大学生数学竞赛(微积分)大纲</span><span
								class="time float_r">05-29</span> </a>
							<a href="#" class="list-group-item"> <span class="title">浙江省大学生数学竞赛(微积分)大纲</span><span
								class="time float_r">05-29</span> </a>
							<a href="#" class="list-group-item"> <span class="title">浙江省大学生数学竞赛(微积分)大纲</span><span
								class="time float_r">05-29</span> </a>
							<a href="#" class="list-group-item"> <span class="title">浙江省大学生数学竞赛(微积分)大纲</span><span
								class="time float_r">05-29</span> </a>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- footer -->
		<jsp:include page="/WEB-INF/pages/globalweb/bottom.jsp" />
		<!-- footer-end -->
	</body>
</html>