<!DOCTYPE html>
<html>
	<head>
		<link type="text/css" rel="stylesheet"  href="${base}/css/wjdc/wdwj.css?ver=${messageUtil("niutal.cssVersion")}" />
	</head>
	<body>
	<div class="formbox">
			<!--标题start-->
			<h3 class="datetitle_01">
				<span>我的问卷列表</span>
			</h3>
			<!--标题end-->
			<!--pc表格-->
			<div class="showPc">
				<table width="100%" class="table table-hover table-bordered text-center">
					<thead>
						<tr>
							<td>问卷名称</td>
							<td>问卷状态</td>
							<td>过期时间</td>
							<td>答卷状态</td>
							<td>操作</td>
						</tr>
					</thead>
					<tbody>
						[#if wdwjList?size > 0]
							[#list wdwjList as item]
								<tr>
									<td>${item.wjmc}</td>
									<td>${item.wjzt.text}</td>
									<td>${item.gqsj}</td>
									<td>
										[#if item.djzt == 2]
											已答卷
										[#elseif item.djzt == 1]
											答卷中
										[#elseif item.djzt == 0]
											未答卷
										[#else]
											<!--未知状态-->
										[/#if]
									</td>
									<td>
										[#if item.djzt == 2 && item.wjzt=="RUN"]
											<!--已完成答卷-->
										[#elseif item.djzt == 1 && item.wjzt == "RUN"]
											<a class="btn btn-success btn-xs"  href="${base}/wjdc/wjgl/yhdj.zf?wjid=${item.wjid}" target="_blank">继续答卷</a>
										[#elseif item.djzt == 0 && item.wjzt == "RUN"]
											<a class="btn btn-success btn-xs"  href="${base}/wjdc/wjgl/yhdj.zf?wjid=${item.wjid}" target="_blank">开始答卷</a>
										[#else]										
											<!--其他情况不开启按钮-->
										[/#if]
									</td>
								</tr>
							[/#list]
						[/#if]
					</tbody>
				</table>
			</div>
			<!--移动表格-->
			<div class="tableList showMobile">
				[#if wdwjList?size > 0]
					[#list wdwjList as item]
						<div class="tableListItem">
							<h4><label>问卷名称：</label><span>${item.wjmc}</span></h4>
							<div class="zt">
								<p><label>问卷状态：</label><span>${item.wjzt.text}</span></p>
								<p>
									<label>答卷状态：</label>
									<span>
										[#if item.djzt == 2]
											已答卷
										[#elseif item.djzt == 1]
											答卷中
										[#elseif item.djzt == 0]
											未答卷
										[#else]
											<!--未知状态-->
										[/#if]
									</span>
								</p>
							</div>
							<p><label>过期时间：</label><span>${item.gqsj}</span></p>
							[#if item.djzt == 2 && item.wjzt=="RUN"]
								<!--已完成答卷-->
							[#elseif item.djzt == 1 && item.wjzt == "RUN"]
								<a class="btn btn-success btn-xs"  href="${base}/wjdc/wjgl/yhdj.zf?wjid=${item.wjid}" target="_blank">继续答卷</a>
							[#elseif item.djzt == 0 && item.wjzt == "RUN"]
								<a class="btn btn-success btn-xs"  href="${base}/wjdc/wjgl/yhdj.zf?wjid=${item.wjid}" target="_blank">开始答卷</a>
							[#else]										
								<!--其他情况不开启按钮-->
							[/#if]
						</div>
					[/#list]
				[/#if]
			</div>
		</div>
	</body>
</html>
