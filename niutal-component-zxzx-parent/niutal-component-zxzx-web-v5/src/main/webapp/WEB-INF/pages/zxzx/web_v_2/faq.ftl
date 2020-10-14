<!doctype html>
<html>
<head>

</head>
<body>
	<form action="faq.zf?layout=zxzxWebindexLayout" id="zxzx_web_form" method="post">
	<input type="hidden" name="webSearchValue" id="webSearchValue" value="${webSearchValue}" />
	<input type="hidden" name="webSearchBkdmValue" id="webSearchBkdmValue" value="${webSearchBkdmValue}" />
	<div class="panel-body">
		 <div class="question-box">
         	<ul>
				[#list cjwtList as cjwt]
				<li>
			        <div class="question-part question-part-answered" style="padding-left:0px;">
			            <a href="javascript:void(0);" datatype="zxzx-kzdt" datavalue="${cjwt.wtid}" class="title" class="text-primary">${cjwt.wtbt}</a>
			            <div>
			            	<img src="${base}/css/zxzx/images/icon-question.png" class="margin_r15">
			            	<span style="font-size: 13px;" class="text-primary">${cjwt.wtnr}</span>
						</div>
				        <div class="question-part-desc">
			                <span class="padding_l5">版块：<a href="#">${cjwt.bkmc}</a></span>
							<span class="padding_l5">创建时间：<a href="#">${cjwt.cjsj}</a></span>
				         </div>
			        </div>
			        <div class="answer-part">
				            <img src="${base}/css/zxzx/images/icon-answer.png" class="margin_r15">
				            <div class="answer-part-desc">
				              <span class="text-danger">${cjwt.wthf}</span>
				            </div>
				    </div>
			    </li>
				[/#list]
			</ul>
		</div>
		
		[#include "/zxzx/web_v_2/pageFootMenu.ftl" /]
		<script type="text/javascript">
			jQuery(function(){
				/***/
				jQuery("a[datatype='zxzx-kzdt']").click(function(){
					var dataValue = jQuery(this).attr("datavalue");
					jQuery("div[datatype='zxzx-hfnr'][datarelate='"+ dataValue +"']").toggle("fast");
				});
				
				$('.main-body-con>ul li').removeClass('active');
				$('#cjwt').parent('li').addClass('active');
				
			});
		</script>
	</div>
	</form>				
</body>
</html>