<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
	<div class="readme">
		<h3>
			帮助说明
		</h3>
		<hr>
		<div class="readcon">
			<p class="bg-warning sl_warning">
				缓存:代表每个配置缓存的唯一标示
			</p>
			<p class="bg-warning sl_warning">
				内存：对象在内存中的最大个数
			</p>
			<p class="bg-warning sl_warning">
				是否使用磁盘：如果是true，则当对象数超过内存最大值时，缓存到磁盘
			</p>
			<p class="bg-warning sl_warning">
				永久：如果是true，则缓存永久有效，即失效时间不起作用
			</p>
			<p class="bg-warning sl_warning">
				最大磁盘：对象在磁盘中的最大个数
			</p>
			<p class="bg-warning sl_warning">
				Idle：当缓存超过Idle指定的时间没有访问时，使缓存失效
			</p>
			<p class="bg-warning sl_warning">
				Live：当缓存超过Live指定时间，使缓存失效
			</p>
			<p class="bg-warning sl_warning">
				驱逐策略：废除缓存的策略，FIFO (first in first out) ,LFU (less frequently used) ,LRU (last recently used)
			</p>
		</div>
	</div>
	<hr>
	<div class="formbox">
		<!--标题start-->
		<h3 class="datetitle_01">
			<span>缓存列表</span>
		</h3>
		<!--标题end-->
		<table width="100%" class="table table-hover table-bordered text-center" id="cache_table">
			<thead>
				<tr>
					<td>缓存</td>
					<td>内存</td>
					<td>是否使用磁盘</td>
					<td>永久</td>
					<td>最大磁盘</td>
					<td>Idle</td>
					<td>Live</td>
					<td>驱逐策略</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<s:if test="cacheConfigList != null && cacheConfigList.size() > 0">
					<s:iterator id="item" value="cacheConfigList" status="sta">
						<tr>
							<td>${item.name}</td>
							<td>${item.maxEntriesLocalHeap}</td>
							<td>${item.overflowToDisk}</td>
							<td>${item.eternal}</td>
							<td>${item.maxElementsOnDisk}</td>
							<td>${item.timeToIdleSeconds}</td>
							<td>${item.timeToLiveSeconds}</td>
							<td>${item.memoryStoreEvictionPolicy}</td>
							<td>
								<input name="cache_reload_trigger" type="button" class="btn_common" key="${item.name}" value="刷  新" />
							</td>
						</tr>
					</s:iterator>
				</s:if>
			</tbody>
		</table>
	</div>
	<script type="text/javascript">
		jQuery(function($){
			jQuery('#cache_table input[name="cache_reload_trigger"]').click(function(){
				var cacheKey = $(this).attr('key');
				if(cacheKey){
					$.post( _path +  "/xtgl/cache_refreshCache.html", {"CACHE_KEY":cacheKey},
						function(data){$.alert(data["message"]);},
					"json");
				}
			});
		});
	</script>
	</body>
</html>