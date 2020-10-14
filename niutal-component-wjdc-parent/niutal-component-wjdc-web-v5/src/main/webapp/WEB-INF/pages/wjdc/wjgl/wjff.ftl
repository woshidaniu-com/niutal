<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${base}/css/wjdc/wjff.css?ver=${messageUtil("niutal.cssVersion")}" />
	</head>
	<body>
		<div class="mb5">
			<span class="red">*</span>调查对象
		</div>
		<form class="form-horizontal" role="form" data-toggle="validation" action="saveWjff.zf" id="ajaxForm" method="post">
			<input type="hidden" name="wjid" value="${model.wjid}"/>
			<input type="hidden" name="ffdx" value="" id="ffdx"/>
			<input type="hidden" name="gnid" value="" id="gnid"/>
			<div class="dc">
				<table class="table" id="table">
					[#list wjffList as wjff]
						<tr class="${wjff.dxid}">
							<td width="25%">
								<div class="checkbox">
									<label>
								      	<input type="checkbox" name="dxid" value="${wjff.dxid}">${wjff.dxmc}
								    </label>
								</div>
							</td>
							<td>
								<div class="clearfix condition">
									[#list wjff.fftjs as fftj]
										<!--条件列表 start-->
										<div class="item">
											<div class="lable">${fftj.name} <i class="glyphicon glyphicon-chevron-down"></i></div>
											<div class="isChecked  clearfix"></div>
											<div class="content">
												[#assign key="${fftj.key!}"]
												[#assign value="${fftj.value!}"]
												[#list fftj.itemList as item]
													<span class="block bg-set" data-dxid="${wjff.dxid}" data-tjid="${fftj.id}" data-key="${item[key]!}">${item[value]!}</span>
												[/#list]
											</div>
										</div>
										<!--条件列表 end-->
									[/#list]
								</div>
							</td>
						</tr>
					[/#list]
				</table>
			</div>
			<div>共<span class="red" name="ffrs"></span>位调查对象</div>
			<p class="font">
				完成调查问卷，才能使用如下功能：
			</p>
			<div class="clearfix">
				<div class="tab-cnt">
					[#list ywgnList as ywgn]
						<span class="item [#if wjgnList?contains(ywgn.gnid)]active[/#if]" data-gnid="${ywgn.gnid}">${ywgn.gnmc}</span>
					[/#list] 
				</div>
			</div>
			
			[#list ffmxList as ffmx]
				<span name="yff" data-ffdx="${ffmx.FFDX}" data-dxtj="${ffmx.DXTJ}" data-tjz="${ffmx.TJZ}"/>
			[/#list]
		</form>
		<div class="loadBanner">
			<img src="${base}/css/wjdc/images/icon/ico-loading.gif"></img>
		</div>
		<script type="text/javascript" src="${base}/js/wjdc/wjff.js?ver=${messageUtil("niutal.jsVersion")}3"></script>
	</body>
</html> 