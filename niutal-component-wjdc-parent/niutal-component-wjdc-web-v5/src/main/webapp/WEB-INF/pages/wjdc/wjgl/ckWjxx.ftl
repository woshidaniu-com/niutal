<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>问卷预览</title>
		<link rel="stylesheet" type="text/css" href="${base}/css/wjdc/wjsj.css?ver=${messageUtil("niutal.cssVersion")}" />
		
		<script type="text/javascript">
			$(function(){
				 $('.wj-content>.item[data-type=2],.wj-content>.item[data-type=4]').each(function(i,n){
					 $(n).find(".wj-topic-content>.radio:last").append('<input type="text" name="text" class="no-sort" />');
					  $(n).find(".wj-topic-content>.checkbox:last").append('<input type="text" name="text" class="no-sort" />');
				 });
			});
		</script>
	</head>

	<body>
		<div class="no-response-container">
			<div class="preview type2 wj clearfix">
				<div class="wj-content">
					<div class="wj-content-title">
						<p class="text-center wj-blue-h3">${model.wjmc}</p>
						<p class="narrate">
						${model.jsy}
						</p>
					</div>
					<div class="wj-content">
						[#list stxxList as stxx]
							[#if stxx.stlx=='0']
								<div class="wj-topic-title">
									${stxx.stmc}
								</div>
							[#else]
								<div class="item"  data-type="${stxx.stlx}">
									<div class="wj-topic">
										${stxx.xssx}、${stxx.stmc} [#if stxx.sfbd == 'true']<span class="red">*</span>[/#if]
									</div>
									<div class="wj-topic-content">
										[#list xxxxList as item]
											[#if item.stid == stxx.stid]
												[#if stxx.stlx==1 || stxx.stlx==2]
												<div class="radio">
													<label>
													  <input type="radio" name="${item.stid}" value="${item_index}" >${item.xxmc}
													</label>
												</div>
												[#elseif stxx.stlx==3 || stxx.stlx==4]
												<div class="checkbox">
													<label>
													  <input type="checkbox" name="${item.stid}" value="${item_index}" >${item.xxmc}
													</label>
												</div>
												[/#if]
											[/#if]
										[/#list]
										[#if stxx.stlx==5]
											[#assign wbgd=(stxx.wbgd) ?number * 40] 
											<textarea name="${item.stid}" rows="1" class="form-control no-sort" style="height:${wbgd}px;"></textarea>
										[/#if]
									</div>
								</div>
							[/#if]
						[/#list]
					</div>
					<p class="narrate">
						${model.jwy}
					</p>
				</div>
			</div>
		</div>
	</body>
</html>