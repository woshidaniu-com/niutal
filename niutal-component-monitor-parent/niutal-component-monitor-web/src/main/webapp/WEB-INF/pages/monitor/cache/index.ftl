<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<meta name="decorator" content="default"/>

	[#include "/globalweb/head/niutal-ui-echarts.ftl" /]
	<!--WebSocket插件-->
	[#include "/globalweb/head/niutal-ui-sockjs.ftl" /]
	<script type="text/javascript" src="${base}/js/monitor/cache.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	<style type="text/css">
	.input-group-addon {
	    background-color: #FFF;
	    border-left: 0px;
	}
	
	.panel .table {
		margin-bottom: 0;
	}
	.table > tbody > tr > td, 
	.table > tbody > tr > th, 
	.table > tfoot > tr > td, 
	.table > tfoot > tr > th, 
	.table > thead > tr > td, 
	.table > thead > tr > th {
		padding: 2px;
	}
	
	.panel-group .panel {
	    margin-bottom: 5px;
	}

	.td-left{padding-left: 10px !important;}
	.td-left-val{width: 40%;}
	</style>
</head>
<body class="" style="">


	
	<!-- Nav tabs -->
	<ul class="nav nav-tabs sl_nav_tabs" role="tablist" id="nav_tabs"  >
		<li class="active">
			<a href="#usage" role="tab" data-toggle="tab">缓存容量监控</a>
		</li>
		<li>
			<a href="#hits" role="tab" data-toggle="tab">缓存命中率监控</a>
		</li>
		<li>
			<a href="#profile" role="tab" data-toggle="tab">缓存信息</a>
		</li>
	</ul>
	<!-- Tab panes -->
	<div class="tab-content">
		<div class="tab-pane active padding-t10" id="usage">
			<div class="row">
			 	<div class="col-sm-12 col-md-12">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<i class="glyphicon glyphicon-time "></i> 缓存容量使用率
						</div>
						<div class="panel-body">
							<div class="col-sm-4 col-md-4"><div id="offheap_usage_chart" style="height: 240px;"></div></div>
							<div class="col-sm-4 col-md-4"><div id="heap_usage_chart" style="height: 240px;"></div></div>
							<div class="col-sm-4 col-md-4"><div id="disk_usage_chart" style="height: 240px;"></div></div>
						</div>
					</div>
				</div>
				<div class="col-sm-12 col-md-12">
					<div class="panel-group" id="accordionUsage" role="tablist" aria-multiselectable="true">
						[#list cacheNames as cacheName]
					  	<div class="panel panel-info cache-usage-element" data-cache-name="${cacheName}">
					  		<div class="panel-heading" role="tab" id="${cacheName}_usage_heading">
								 <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordionUsage" href="#${cacheName}_usage_collapse" aria-expanded="false" aria-controls="${cacheName}_collapse">
	                				<i class="glyphicon glyphicon-floppy-disk"></i> 缓存 - ${cacheName} ：容量使用率</span>
	                			 </a>
							</div>
							<div id="${cacheName}_usage_collapse" class="panel-collapse collapse[#if cacheName_index == 0] in[/#if]" role="tabpanel" aria-labelledby="${cacheName}_usage_heading">
								<div class="panel-body">
									<div id="${cacheName}_usage_chart" style="height: 240px;"></div>
								</div> 
							</div>
						</div>
						[/#list] 
					</div>
				</div>
			</div>
		</div>
		<div class="tab-pane padding-t10" id="hits">
			<div class="row">
				<div class="col-sm-12 col-md-12">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<i class="glyphicon glyphicon-time "></i> 缓存平均命中率、丢失率
						</div>
						<div class="panel-body">
							<div class="col-sm-2 col-md-2"><div id="in_memory_hit_chart" style="height: 240px;"></div></div>
							<div class="col-sm-2 col-md-2"><div id="cache_misse_chart" style="height: 240px;"></div></div>
							<div class="col-sm-4 col-md-4"><div id="cache_hit_chart" style="height: 240px;"></div></div>
							<div class="col-sm-2 col-md-2"><div id="off_heap_hit_chart" style="height: 240px;"></div></div>
							<div class="col-sm-2 col-md-2"><div id="on_disk_hit_chart" style="height: 240px;"></div></div>
						</div>
					</div>
				</div>
				<div class="col-sm-12 col-md-12">
					<div class="panel-group" id="accordionHits" role="tablist" aria-multiselectable="true">
						[#list cacheNames as cacheName]
					  	<div class="panel panel-info cache-hit-element" data-cache-name="${cacheName}">
					  		<div class="panel-heading" role="tab" id="${cacheName}_hit_heading">
								 <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordionHits" href="#${cacheName}_hit_collapse" aria-expanded="false" aria-controls="${cacheName}_collapse">
	                				<i class="glyphicon glyphicon-floppy-disk"></i> 缓存 - ${cacheName} ：堆内、堆外、磁盘命中率</span>
	                			 </a>
							</div>
							<div id="${cacheName}_hit_collapse" class="panel-collapse collapse[#if cacheName_index == 0] in[/#if]" role="tabpanel" aria-labelledby="${cacheName}_hit_heading">
								<div class="panel-body">
									<div id="${cacheName}_pct_chart" style="height: 240px;"></div>
								</div> 
							</div>
						</div>
						[/#list]
					</div>
				</div>
			</div>
		</div>
		<div class="tab-pane padding-t10" id="profile">
			[#include "/monitor/cache/list.ftl" /]
		</div>
	</div>
</body>
</html>
