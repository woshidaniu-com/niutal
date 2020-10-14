<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	</head>
	<body>
		<div class="readme">
			<h2>
				帮助说明
			</h2>
			<hr>
			<div class="readcon">
				<ul>
					<li>
						缓存:代表每个配置缓存的唯一标示
					</li>
					<li>
						内存：对象在内存中的最大个数
					</li>
					<li>
						是否使用磁盘：如果是true，则当对象数超过内存最大值时，缓存到磁盘
					</li>
					<li>
						永久：如果是true，则缓存永久有效，即失效时间不起作用
					</li>
					<li>
						最大磁盘：对象在磁盘中的最大个数
					</li>
					<li>
						Idle：当缓存超过Idle指定的时间没有访问时，使缓存失效
					</li>
					<li>
						Live：当缓存超过Live指定时间，使缓存失效
					</li>
					<li>
						驱逐策略：废除缓存的策略，FIFO (first in first out) ,LFU (less frequently used) ,LRU (last recently used)
					</li>
				</ul>
			</div>
		</div>
		<div class="formbox">
			<!--标题start-->
			<h3 class="datetitle_01">
				<span>缓存列表</span>
			</h3>
			<!--标题end-->
			<table width="100%" class="dateline" id="cache_table">
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
					<s:iterator value="cacheConfigList" var="cache" >
					<tr>
						<td>${cache.name}</td>
						<td>${cache.maxEntriesLocalHeap}</td>
						<td>${cache.overflowToDisk}</td>
						<td>${cache.eternal}</td>
						<td>${cache.maxElementsOnDisk}</td>
						<td>${cache.timeToIdleSeconds}</td>
						<td>${cache.timeToLiveSeconds}</td>
						<td>${cache.memoryStoreEvictionPolicy}</td>
						<td>
							<input name="cache_reload_trigger" type="button" class="btn_common" key="${cache.name}" value="刷  新" />
						</td>
					</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</body>
	<script type="text/javascript">
			jQuery(function(){
				jQuery('#cache_table input[name="cache_reload_trigger"]').click(function(){
					var cacheKey = jQuery(this).attr('key');
					if(cacheKey){
						jQuery.post(_path + "/system/cache_refreshCache.html",
									{"CACHE_KEY":cacheKey},
									function(data){alert(data);},
									"json");
					}
				});
			});
		</script>
</html>