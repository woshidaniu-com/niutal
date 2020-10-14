<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript"
			src="<%=systemPath%>/js/globalweb/comp/xtgl/wdyy.js"></script>		
	</head>
	<body>


		<div class="mainbody type_mainbody">
			<!-- TOP菜单的加载 -->
			<div class="topframe" id="topframe">
				<jsp:include page="top.jsp" flush="true"></jsp:include>
			</div>

			<div class="type_mainframe">
				
			</div>

			<!-- 底部页面加载 -->
			<div class="botframe" id="botframe">
				<jsp:include page="bottom.jsp" flush="true"></jsp:include>
			</div>
		</div>

		<!-- 新消息提示div start -->
		<div id="znxContent" style="display: none;">
			<div id="pendingtask" class="openmessage"
				style="width: 240px; height: 127px; position: absolute; bottom: 0; z-index: 9999; right: 0px;">
				<div class="messagecon">
					<p class="mesnum">
						您有
						<em id="tasktotaldiv">0</em>条新消息
					</p>
					<p>
						<a href="#"
							onclick="refRightContent('<%=systemPath%>/zfxg!plugins/znxgl_sjxZnx.html');">马上处理</a>
						<a href="#" onclick="ymPrompt.close();">暂不处理</a>
					</p>
				</div>
			</div>
		</div>
		<!-- 新消息提示div end -->
		<script type="text/javascript">
			function getMessageCount(){
				
				jQuery.post("<%=systemPath%>/zfxg!plugins/znxgl_getMessageCount.html",{},function(data){
					if(data!=null && Number(data)>0){
						jQuery("#tasktotaldiv").html(data);
						//detect();
						ymPrompt.win({
							message:jQuery("#znxContent").html(),
							title:"消息提示",
							winPos:"rb",
							useSlide:true,
							showMask:false,
							width:240,
							height:147
						});
						
					}
				});
				
				//setTimeout("getMessageCount()",60000);
		   }
			jQuery(function(){
				//getMessageCount();
			});	
		</script>
	</body>
</html>
