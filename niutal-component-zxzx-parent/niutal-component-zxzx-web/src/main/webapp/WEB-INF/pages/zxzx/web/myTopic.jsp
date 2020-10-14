<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/WEB-INF/pages/zxzx/web/zxzx_pagehead.ini"%>
<script type="text/javascript">
	jQuery(function(){
		jQuery("a[datatype='zxzx-kzdt']").click(function(){
			var dataValue = jQuery(this).attr("datavalue");
			jQuery("div[datatype='zxzx-hfnr'][datarelate='"+ dataValue +"']").toggle();
		});
		
		/***/
		jQuery("#allShow").click(function(){
			var kgzt = jQuery(this).attr("kgzt");
			if(kgzt == '0'){
				jQuery(this).attr("kgzt","1");
				jQuery(this).text("全部收起");
				jQuery("div[datatype='zxzx-hfnr']").show();
			}else{
				jQuery(this).attr("kgzt","0");
				jQuery(this).text("全部展开");
				jQuery("div[datatype='zxzx-hfnr']").hide();
			}
			
		});
		
		<s:if test="openStatus.isOpen == true">
		/***/
		jQuery("#askQuestion").click(function(){
			jQuery.dialog({
				title:'',
				width: '750px',
			    height: '540px',
			    content: 'url:'+_path+'/zxzx/web/newTopic.html',
				max: false,
			    min: false,
			    resize: false});
		});
		</s:if>
		
		jQuery("#pagingSubmit").click(function(){
			return false;
		});
		
		jQuery("a[datatype='mytopid-delete']").click(function(){
			var dataValue = jQuery(this).attr("datavalue");
			jQuery.dialog.confirm('你确定要删除这条咨询吗？', function(){
			   jQuery.post(_path+"/zxzx/web/deleteMyTopic.html",{zxid:dataValue},function(data){
				   $.dialog.tips(data,1,null,function(){
					   window.location.reload();
				   });
				   
			   });
			});
		});
		
	});
</script>
</head>
<body>
	<div class="mainbody mainbody-new">
		<div class="mainframe mainframe_tm">
			<div class="mainframe_bgcolor"></div>
			<div class="left_qz02 left_myzq clear-after">
				<div class="top_title01">
					<ul class="xxk_01">
						<li><a href="<%=systemPath%>/zxzx/web/index.html">讨论区</a></li>
						<li><a href="<%=systemPath%>/zxzx/web/top.html">热门咨询</a></li>
						<li><a href="<%=systemPath%>/zxzx/web/faq.html">常见问题</a></li>
						<shiro:authenticated>
							<li class="cur"><a href="<%=systemPath%>/zxzx/web/myTopic.html">我的咨询</a></li>
						</shiro:authenticated>
					</ul>
				</div>
				<div class="myfd_its">
					<div class="reminder" style="word-break:break-all;"><span class="ico_01">提示：</span>
						<span class="grzl-show" id="noticeHtmlId">
							<span class="bt">开放时间：</span><span><s:property value='zxzxConfig.CSSZ_KSSJ_DM == null ? "--" : zxzxConfig.CSSZ_KSSJ_DM'/> 至  <s:property value='zxzxConfig.CSSZ_JSSJ_DM == null ? "不限" : zxzxConfig.CSSZ_JSSJ_DM'/></span>
						</span>
					</div>
					<s:form namespace="/zxzx/web" action="myTopic" id="form_index" method="post" theme="simple">
					<div class="circle_show clear-after">
						|<a id="allShow" kgzt="0" href="###">全部展开</a>| 
					</div>
					<div class="demo_frame_02">
						<div class="recommend_xxq">
							<ul>
								<s:iterator value="zxwtList" id="zxwt" status="iterator">
								<li>
									<div class="pic">
				                		<a href="#" class="title">
											${iterator.index+1}.
				                		</a>
				                	</div>
									<div class="text">
										<h4>
											<a href="###" datatype="zxzx-kzdt" datavalue="${zxwt.zxid}" class="title">${zxwt.kzdt}</a>
											<s:if test="#zxwt.zxhfModel.hfid == null or zxwt.zxhfModel.hfid !=''">
												<a href="#" class="join join_01" datatype="mytopid-delete" datavalue="${zxwt.zxid}">删除</a>
												<span class="whf">未回复</span>
											</s:if>
											<s:else>
												<span class="yhf">已回复</span>
											</s:else>
										</h4>
										<p>
											版块：<span>${zxwt.kzdkModel.bkmc} | </span>咨询时间：<span>${zxwt.zxsj} | </span>点击量：<span>${zxwt.djl}</span>
										</p>
									</div>
									
									<div datatype="zxzx-hfnr" datarelate="${zxwt.zxid}" class="answer hid">
										<div class="answer_bt">
											${zxwt.zxnr}
										</div>
										<div class="answer_a">
											<span class="text_red">【回复】</span>
											<p>${zxwt.zxhfModel.hfnr}</p>
										</div>
									</div>
								</li>
								</s:iterator>
							</ul>
						</div>
					</div>
						<s:submit id="pagingSubmit" style="display:none"/>
						<jsp:include page="/WEB-INF/pages/zxzx/web/pageFootMenu.jsp"></jsp:include>
					</s:form>
				</div>
			</div>
			<!--右边-->
			<div class="right_qz02">
				<div class="demo_xxq_right right_setcl">
					<s:if test="openStatus.isOpen == true">
						<button id="askQuestion" class="button-enabled">我要提问</button>
					</s:if>
					<s:else>
						<button  id="askQuestion_disabled" class="button-disabled">我要提问</button>
						<div>
							<span style="color:red;">(<s:property value="openStatus.messageValue"/>)</span>
						</div>
					</s:else>
				</div>
				
      			<div class="demo_xxq_right">
				 <div class="hot_fl" style="border:none;">
				     <div class="hot_fl_box clear-after" style=" margin-top:0;">
				        <h3>联系方式</h3>
				     	<div class="lxfs"><span class="bt">联系老师：</span><span><s:property value='zxzxConfig.CSSZ_LXR_DM'/></span></div>
				        <div class="lxfs"><span class="bt">电话：</span><span><s:property value='zxzxConfig.CSSZ_DH_DM'/></span></div>
				        <div class="lxfs"><span class="bt">邮箱：</span><span><s:property value='zxzxConfig.CSSZ_DZYX_DM'/></span></div>
				        <div class="lxfs"><span class="bt">地址：</span><span><s:property value='zxzxConfig.CSSZ_DZ_DM'/></span></div>
				     </div>
				 </div>
      			</div>
      			
			</div>
			
		</div>
		<!--底部-->
	</div>
</body>
</html>