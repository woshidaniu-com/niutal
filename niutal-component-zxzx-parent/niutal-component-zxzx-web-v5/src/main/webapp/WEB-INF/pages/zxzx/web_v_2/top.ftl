<!doctype html>
<html>
<head>
<script type="text/javascript">
	jQuery(function(){
		$('.main-body-con>ul li').removeClass('active');
		$('#rmzx').parent('li').addClass('active');
	});
</script>

</head>
<body>
<form action="top.zf" id="zxzx_web_form" method="post">
<div class="panel-body">
	<div class="question-box">
		<ul>
			[#list zxwtList as zxwt]
			<li>
		        <div class="question-part question-part-answered" style="padding-left:0px;">
					<a href="javascript:void(0);" datatype="zxzx-kzdt" datavalue="${zxwt.zxid}" databkdm="${zxwt.bkdm}" class="text-primary">${zxwt.kzdt}</a>
		           	<div>
		           		<img src="${base}/css/zxzx/images/icon-question.png" class="margin_r15">
		            	<span style="font-size: 13px;">${zxwt.zxnr}</span>
		            </div>
		            <div class="question-part-desc">
		                <span>咨询人：<a href="javascript:void(0);">
						[#if zxwt.zxr != null]
								${zxwt.zxr}
						[#else]
							匿名
						[/#if]
						</a></span>
					<span class="padding_l5">版块：<a
						href="javascript:void(0);">${zxwt.kzdkModel.bkmc}</a></span><span class="padding_l5">咨询时间：<a
						href="javascript:void(0);">${zxwt.zxsj}</a></span>
		            </div>
		        </div>
		        <div class="answer-part">
		            <img src="${base}/css/zxzx/images/icon-answer.png" class="margin_r15">
		            <div class="answer-part-desc text-danger">
		              ${zxwt.zxhfModel.hfnr}
		            </div>
		        </div>
		    </li>
			[/#list]
		</ul>
	</div>
</div>
</form>
</body>
</html>
