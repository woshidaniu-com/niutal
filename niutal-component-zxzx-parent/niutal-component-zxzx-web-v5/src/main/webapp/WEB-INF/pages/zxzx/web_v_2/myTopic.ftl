<!doctype html>
<html>
<head>
	<script type="text/javascript">
		jQuery(function(){
			jQuery("a[datatype='zxzx-kzdt']").click(function(){
				var dataValue = jQuery(this).attr("datavalue");
				jQuery("div[datatype='zxzx-hfnr'][datarelate='"+ dataValue +"']").toggle("fast");
			});
			
	
			jQuery("span[datatype='mytopid-delete']").click(function(){
				var dataValue = jQuery(this).attr("datavalue");
				$.confirm('你确定要删除这条咨询吗？', function(result){
				   if(result){
				   	 jQuery.post(_path+"/zxzx/web/deleteMyTopic.zf",{zxid:dataValue},function(data){
						   window.location.reload();
				   	  });
				   }
				});
			});
			
			$('.main-body-con>ul li').removeClass('active');
			$('#wdzx').parent('li').addClass('active');
	});
</script>
</head>
<body>
	
	<form action="myTopic.zf" id="zxzx_web_form" method="post">
	
	<div class="panel-body">
		<div class="question-box">
			<ul>		
			[#list zxwtList as zxwt]
				<li>
			        <div class="question-part question-part-answered" style="padding-left:0px;">
						<a href="javascript:void(0);" datatype="zxzx-kzdt" datavalue="${zxwt.zxid}" databkdm="${zxwt.bkdm}" >${zxwt.kzdt}</a>
						 <div>
			            	<img src="${base}/css/zxzx/images/icon-question.png" class="margin_r15">
			            	<span style="font-size: 13px;" class="text-primary">${zxwt.zxnr}</span>
						 </div>
						 
						 <div class="question-part-desc">
			                <span class="padding_l5">版块：<a href="javascript:void(0);">${zxwt.kzdkModel.bkmc}</a></span>
							<span class="padding_l5">咨询时间：<a href="javascript:void(0);">${zxwt.zxsj}</a></span>
							[#if zxwt.zxhfModel.hfid == null || zxwt.zxhfModel.hfid =='']
								<span class="padding_l5">回复状态：<span class="label label-danger">未回复</span></span>
								<span class="padding_l5">操作：<span style="cursor:pointer" class="label label-warning" datatype="mytopid-delete" datavalue="${zxwt.zxid}">删除</span></span>
							[#else]
								<span class="padding_l5">回复状态：<span class="label label-success">已回复</span></span>
							[/#if]
				         </div>
			        </div>
			        [#if zxwt.zxhfModel.hfid == null || zxwt.zxhfModel.hfid =='']
			        <div class="answer-part clearfix">
						<img src="${base}/css/zxzx/images/icon-answer.png" class="margin_r15">
						<div class="answer-part-desc">
							<span class="text-danger">【暂未回复】</span>
						</div>
			        
			        [#else]
			        	<div class="answer-part clearfix">
			        	<img src="${base}/css/zxzx/images/icon-answer.png" class="margin_r15">
						<div class="answer-part-desc">
							<span class="text-success">${zxwt.zxhfModel.hfnr}</span>
						</div>
						</div>
			        [/#if]
			    </li>
			[/#list]
			</ul>
		</div>
		[#include "/zxzx/web_v_2/pageFootMenu.ftl" /]
	</div>
	</form>
						
</body>
</html>