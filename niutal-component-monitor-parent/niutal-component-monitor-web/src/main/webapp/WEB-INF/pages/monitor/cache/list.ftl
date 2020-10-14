<div class="col-sm-12 col-md-12">
	<div class="panel panel-info">
		<div class="panel-heading">
			<i class="fa fa-th-list"></i> 缓存列表
		</div>
		<div class="panel-body">
			<div class="readme">
				<h4>帮助说明</h4>
				<hr>
				<div class="readcon">
					<p class="bg-warning sl_warning">
						<dl class="dl-horizontal">
						  <dt>缓存名称：</dt>
						  <dd>Cache的名称，必须是唯一的(ehcache会把这个cache放到HashMap里)</dd>
						  <dt>是否永不过期：</dt>
						  <dd>缓存的对象是否永远不过期。如果为true，则缓存的数据始终有效，如果为false那么还要根据Idle，Live判断。</dd>
						  <dt>是否使用磁盘：</dt>
						  <dd>如果内存中数据超过内存限制，是否要缓存到磁盘上。</dd>
						  <dt>是否持久化：</dt>
						  <dd>是否在磁盘上持久化。指重启服务后，数据是否有效。</dd>
						  <dt>堆内内存对象数：</dt>
						  <dd>在堆内内存中缓存的对象的最大数目，默认值为0，表示不限制。</dd>
						  <dt>堆外内存对象数：</dt>
						  <dd>在堆外内存中缓存的对象的最大数目，默认值为0，表示不限制。 </dd>
						  <dt>磁盘空间对象数：</dt>
						  <dd>在磁盘空间缓存的对象的最大数目，默认值为0，表示不限制。</dd>
						  <dt>磁盘空间大小：</dt>
						  <dd>DiskStore使用的磁盘大小，默认值30MB。</dd>
						  <dt>对象空闲时间：</dt>
						  <dd>对象空闲时间，指对象在多长时间没有被访问就会失效。当缓存超过Idle指定的时间没有访问时，使缓存失效。只对"是否永不过期"为false的有效。默认值0，表示一直可以访问。</dd>
						  <dt>对象存活时间：</dt>
						  <dd>对象存活时间，指对象从创建到失效所需要的时间,当缓存超过Live指定时间，使缓存失效。只对"是否永不过期"为false的有效。默认值0，表示一直可以访问。 </dd>
						  <dt>驱逐策略：</dt>
						  <dd>如果内存中数据超过内存限制，向磁盘缓存时的策略。默认值LRU，可选FIFO、LFU;</dd>
						  <dt>&nbsp;</dt>
						  <dd>FIFO ，First In First Out (先进先出).</dd>
						  <dt>&nbsp;</dt>
						  <dd>LFU ， Less Frequently Used (最少使用).意思是一直以来最少被使用的。缓存的元素有一个hit 属性，hit 值最小的将会被清出缓存。</dd>
						  <dt>&nbsp;</dt>
						  <dd>LRU ，Least Recently Used(最近最少使用). (ehcache 默认值).缓存的元素有一个时间戳，当缓存容量满了，而又需要腾出地方来缓存新的元素的时候，那么现有缓存元素中时间戳离当前时间最远的元素将被清出缓存。</dd>
						</dl>
					</p>
				</div>
			</div>
		</div>
		<table width="100%" class="table table-hover table-bordered text-center" id="cache_table">
			<thead>
				<tr>
					<td>&nbsp;缓存名称&nbsp;</td>
					<td>是否永不过期</td>
					<td>是否使用磁盘</td>
					<td>是否持久化</td>
					<td>堆内内存对象数（单位：个）</td>
					<td>堆外内存对象数（单位：个）</td>
					<td>磁盘空间对象数（单位：个）</td>
					<td>磁盘空间大小（单位：MB）</td>
					<td>对象空闲时间（单位：秒）</td>
					<td>对象存活时间（单位：秒）</td>
					<td>&nbsp;驱逐策略&nbsp;</td>
				</tr>
			</thead>
			<tbody>
				[#if cacheInfos?size > 0]
					[#list cacheInfos as item]
						<tr>
							<td class="text-left td-left">${item.name}</td>
							<td>${item.eternal?default('false')}</td>
							<td>${item.overflowToDisk?default('false')}</td>
							<td>${item.diskPersistent?default('false')}</td>
							<td class="text-right">${item.maxEntriesInCache}&nbsp;</td>
							<td class="text-right">${item.maxEntriesLocalHeap}&nbsp;</td>
							<td class="text-right">${item.maxEntriesLocalDisk}&nbsp;</td>
							<td class="text-right">${item.diskSpoolBufferSizeMB}&nbsp;</td>
							<td class="text-right">${item.timeToIdleSeconds?default('0')}&nbsp;</td>
							<td class="text-right">${item.timeToLiveSeconds?default('0')}&nbsp;</td>
							<td>${item.memoryStoreEvictionPolicy?default('LRU')}</td>
						</tr>
					[/#list]
				[/#if]
			</tbody>
		</table>
	</div>
</div>
 
<script type="text/javascript">
		jQuery(function(){
			jQuery('#cache_table input[name="cache_reload_trigger"]').click(function(){
				var cacheKey = jQuery(this).attr('key');
				if(cacheKey){
					jQuery.post( "refreshCache.zf",
								{"CACHE_KEY":cacheKey},
								function(data){$.alert(data["message"]);},
								"json");
				}
			});
		});
	</script>
