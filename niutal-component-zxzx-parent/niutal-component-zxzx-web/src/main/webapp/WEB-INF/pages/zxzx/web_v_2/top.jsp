<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/pages/zxzx/web_v_2/zxzx_pagehead.ini"%>
<script type="text/javascript">
	jQuery(function() {
		var clickData = {};
		
		<s:if test="openStatus.isOpen == true">
		/***/
		jQuery("#askQuestion").click(function() {
			jQuery.dialog({
				title : '',
				width : '750px',
				height : '540px',
				content : 'url:' + _path + '/zxzx/web/newTopic.html',
				max : false,
				min : false,
				resize : false
			});
		});
		</s:if>

		/**版块点击事件*/
		jQuery("span[datatype='search-bkdm']").click(
				function() {
					jQuery("input[name='webSearchBkdmValue']").val(
							jQuery(this).attr("datavalue"));
					jQuery("#form_index").submit();
				});

		jQuery("#pagingSubmit").click(function() {
			return false;
		});

	});
</script>
</head>
<body>
	<div class="mainframe">
		<div class="container">
			<div class="panel panel-default">
				<div class="panel-body" id="inside">
					<ul class="nav " role="tablist" id="leftnav">
						<li class="active">
							<div class="text-center margin_t15 panel-body" id="wytw">
								<s:if test="openStatus.isOpen == true">
									<button id="askQuestion"  class="btn btn-warning btn-lg btn-block" type="button">我要提问</button>
								</s:if>
								<s:else>
									<button id="askQuestion_disabled"  class="btn btn-warning btn-lg btn-block disabled" type="button">我要提问</button>
									<div>
										<span style="color:red;">(<s:property value="openStatus.messageValue"/>)</span>
									</div>
								</s:else>
								
							</div> 
							<a href="<%=systemPath%>/zxzx/web/index.html">讨论区</a>
						</li>
						<li><a href="<%=systemPath%>/zxzx/web/top.html">热门资讯</a></li>
						<li><a href="<%=systemPath%>/zxzx/web/faq.html">常见问题</a></li>
						<shiro:authenticated>
							<li><a href="<%=systemPath%>/zxzx/web/myTopic.html">我的咨询</a></li>
						</shiro:authenticated>
						<div class="panel-body" id="lxfs">
							<h4>联系方式</h4>
							<p>
								联系老师：<s:property value='zxzxConfig.CSSZ_LXR_DM'/><br> 
								电话：<s:property value='zxzxConfig.CSSZ_DH_DM'/><br>
								邮箱：<s:property value='zxzxConfig.CSSZ_DZYX_DM'/><br> 
								地址:<s:property value='zxzxConfig.CSSZ_DZ_DM'/>
							</p>
							
						</div>
					</ul>
					<div id="myTabContent" class="tab-content ">
						<div role="tabpanel" class="tab-pane fade active in" id="tab01"
							aria-labelledby="home-tab">
							<form id="zxzxSearchFormId" method="post">
							<div class="panel-body" style="border-bottom: 1px solid #ddd;margin-top:15px;padding:15px"">
							  <div class="col-sm-6">
								<i><span><img src="<%=systemPath%>/css/zxzx/images/ico2.jpg">开放时间：<s:property value='zxzxConfig.CSSZ_KSSJ_DM == null ? "--" : zxzxConfig.CSSZ_KSSJ_DM'/> 至  <s:property value='zxzxConfig.CSSZ_JSSJ_DM == null ? "不限" : zxzxConfig.CSSZ_JSSJ_DM'/></span></i>
							  </div>
							</div>
							</form>
							
							<s:form namespace="/zxzx/web" action="top" id="form_top" method="post" theme="simple">
							
							<div class="panel-body">
								
								<s:iterator value="zxwtList" id="zxwt" status="iterator">
								<div class="margin_b15" style="margin-left:25px">
									<label class="row"><img
										src="<%=systemPath%>/css/zxzx/images/q_ico.jpg" class="margin_r15">
										<a href="#" datatype="zxzx-kzdt" datavalue="${zxwt.zxid}" databkdm="${zxwt.bkdm}" >${iterator.index+1}、${zxwt.kzdt}</a></label>
									<div class="row" style="font-size:10px">
										<span>咨询人：<a href="#"><s:property value="#zxwt.zxr != null ? #zxwt.zxrModel.xm.substring(0,1)+'**' : '匿名'" /></a></span><span class="padding_l5">版块：<a
											href="#">${zxwt.kzdkModel.bkmc}</a></span><span class="padding_l5">咨询时间：<a
											href="#">${zxwt.zxsj}</a></span><span class="padding_l5">点击量：<a
											href="#">${zxwt.djl}</a></span>
									</div>
									<div class="row alert alert-warning" role="alert" datatype="zxzx-hfnr" datarelate="${zxwt.zxid}" style="display: none;margin:5px;">
										<div class="col-sm-12 margin_b15">
											<img src="<%=systemPath%>/css/zxzx/images/i_ico.jpg" class="margin_r15"><span
												style="font-size: 13px;">${zxwt.zxnr}</span>
										</div>
										<div class="col-sm-12 text-left margin_b15">
											<span style="color:red">【回复】</span>
										</div>
										<div class="col-sm-12 text-left" style="text-indent: 10px;">
											<span>${zxwt.zxhfModel.hfnr}</span>
										</div>
									</div>
								</div>
								</s:iterator>
							</div>
							</s:form>
						</div>
						</div>
						
</body>
</html>
