$(function() {
	// <!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
	// Step:3 echarts & zrender as a Global Interface by the echarts-plain.js.
	// Step:3 echarts和zrender被echarts-plain.js写入为全局接口
	// onloadurl();
	var locat = (window.location + '').split('/');
	if ('tool' == locat[3]) {
		locat = locat[0] + '//' + locat[2];

	} else {
		locat = locat[0] + '//' + locat[2] + '/' + locat[3];
	};

	$('#accordionUsage, #accordionHits').find("div.panel").each(function (i,panel) {
		//缓存名称
		var cacheName = $(panel).data("cacheName");
		$("#" + cacheName + ($(panel).hasClass("cache-usage-element") ? "_usage_chart" : "_pct_chart") ).css("width",$("#accordionUsage").innerWidth() - 50);
	});
	
	
	//===========================初始化图表对象==============================
	
	var heap_usage_chart = echarts.init(document.getElementById('heap_usage_chart'));
	var offheap_usage_chart = echarts.init(document.getElementById('offheap_usage_chart'));
	var disk_usage_chart = echarts.init(document.getElementById('disk_usage_chart'));
	
	var in_memory_hit_chart = echarts.init(document.getElementById('in_memory_hit_chart'));
	var off_heap_hit_chart = echarts.init(document.getElementById('off_heap_hit_chart'));
	var cache_hit_chart = echarts.init(document.getElementById('cache_hit_chart'));
	var cache_misse_chart = echarts.init(document.getElementById('cache_misse_chart'));
	var on_disk_hit_chart = echarts.init(document.getElementById('on_disk_hit_chart'));
	
	var cache_usage_charts = [];
	var cache_hit_charts = [];
	
	function getChart(name,type){
		var chart = null;
		$.each([].concat(cache_usage_charts).concat(cache_hit_charts),function(i,item){
			if(item["name"] == name && item["type"] == type){
				chart = item["chart"];
				return false;
			}
		});
		return chart;
	}
	
	$("div.cache-usage-element").each(function(){
		//缓存名称
		var cacheName = $(this).data("cache-name");
		var percentage_chart = echarts.init(document.getElementById(cacheName + "_usage_chart"));
		//同时存储名称方便后面进行数据筛选
		cache_hit_charts.push({
			"name"  : cacheName,
			"type"  : "usage",
			"chart" : percentage_chart
		});
	});
	
	$("div.cache-hit-element").each(function(){
		//缓存名称
		var cacheName = $(this).data("cache-name");
		var percentage_chart = echarts.init(document.getElementById(cacheName + "_pct_chart"));
		//同时存储名称方便后面进行数据筛选
		cache_hit_charts.push({
			"name"  : cacheName,
			"type"  : "hit",
			"chart" : percentage_chart
		});
	});
	
	
	var cache_charts = [heap_usage_chart, offheap_usage_chart, disk_usage_chart, 
	        in_memory_hit_chart, off_heap_hit_chart, 
	        cache_hit_chart, cache_misse_chart, 
	        on_disk_hit_chart].concat(cache_usage_charts).concat(cache_hit_charts);
	
	//批量初始化
	$.each(cache_charts,function(i,item){
		var chart = $.isPlainObject(item) ? item["chart"] : item;
		chart.showLoading({
			text : "图表数据正在努力加载...",
			effect : 'whirling',
			//'spin' | 'bar' | 'ring' | 'whirling' | 'dynamicLine' | 'bubble' 				
			textStyle : {
				fontSize : 16
			}
		});
	});
	
	//访问历史数据
	$.post(locat + '/monitor/cache/info.zf', function(data){
		
		//X时间轴
		var now = new Date();
		var xAxis_arr = [];
		var len = 50;
		while (len--) {
			var time = now.toLocaleTimeString().replace(/^\D*/, '');
			time = time.substr(time.indexOf(":") + 1);
			xAxis_arr.unshift(time);
			now = new Date(now - 5000);
		}
		
		//===========================使用率（仪表盘）==============================
		
		var one_option = {
			tooltip : {
				formatter : "{a} <br/>{b} : {c}%"
			},
			series : [ {
				title : {
					show : true,
					offsetCenter : [ 0, "95%" ],
				},
				pointer : {
					color : '#FF0000'
				},
				name : '监控指标',
				radius : [ 0, '95%' ],
				axisLine : { // 坐标轴线
					lineStyle : { // 属性lineStyle控制线条样式
						width : 20
					}
				},
				detail : {
					formatter : '{value}%'
				},
				type : 'gauge',
				data : [ {
					value : 0,
					name : ''
				} ]
			} ]
		};
		var two_option = {
			tooltip : {
				formatter : "{a} <br/>{b} : {c}%"
			},
			series : [ {
				name : '监控指标',
				type : 'gauge',
				startAngle : 180,
				endAngle : 0,
				center : [ '50%', '90%' ], // 默认全局居中
				radius : 180,
				axisLine : { // 坐标轴线
					lineStyle : { // 属性lineStyle控制线条样式
						width : 140
					}
				},
				axisTick : { // 坐标轴小标记
					splitNumber : 10, // 每份split细分多少段
					length : 12, // 属性length控制线长
				},
				axisLabel : { // 坐标轴文本标签，详见axis.axisLabel

					textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
						color : '#fff',
						fontSize : 15,
						fontWeight : 'bolder'
					}
				},

				pointer : {
					width : 10,
					length : '80%',
					color : 'rgba(255, 255, 255, 0.8)'
				},
				title : {
					show : true,
					offsetCenter : [ 0, 15 ], // x, y，单位px
				/*
				 * textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE color: '#fff',
				 * fontSize: 25 }
				 */
				},
				detail : {
					show : true,
					backgroundColor : 'rgba(0,0,0,0)',
					borderWidth : 0,
					borderColor : '#ccc',
					offsetCenter : [ 0, -40 ], // x, y，单位px
					formatter : '{value}%',
					textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
						fontSize : 20
					}
				},
				data : [ {
					value : 0,
					name : ''
				} ]
			} ]
		};
		
		// '堆外内存使用率', '堆内内存使用率', '磁盘空间使用率'
		
		one_option.series[0].data[0].name = '堆外内存使用率 ';
		one_option.series[0].data[0].value = Number(parseFloat(data["ehcache"]["mean-off-heap-usage"] || 0) * 100).toFixed(0);
		one_option.series[0].pointer.color = '#428bca'
		offheap_usage_chart.setOption(one_option);
		
		two_option.series[0].data[0].name = '堆内内存使用率';
		two_option.series[0].data[0].value = Number(parseFloat(data["ehcache"]["mean-heap-usage"] || 0) * 100).toFixed(0);
		heap_usage_chart.setOption(two_option);
		
		one_option.series[0].data[0].name = '磁盘空间使用率';
		one_option.series[0].data[0].value = Number(parseFloat(data["ehcache"]["mean-disk-usage"] || 0) * 100).toFixed(0);
		one_option.series[0].pointer.color = '#428bca'
		disk_usage_chart.setOption(one_option);
		
		// '堆内命中率', '堆外命中率', '总缓存命中率', '总缓存丢失率', '磁盘命中率'
		
		one_option.series[0].data[0].name = '堆内平均命中率 ';
		one_option.series[0].data[0].value = Number(parseFloat(data["ehcache"]["mean-in-memory-hit-pct"] || 0) * 100).toFixed(0);
		one_option.series[0].pointer.color = '#428bca'
		in_memory_hit_chart.setOption(one_option);
		
		one_option.series[0].data[0].name = '堆外平均命中率';
		one_option.series[0].data[0].value = Number(parseFloat(data["ehcache"]["mean-off-heap-hit-pct"] || 0) * 100).toFixed(0);
		one_option.series[0].pointer.color = '#428bca'
		off_heap_hit_chart.setOption(one_option);
		
		//总缓存命中率
		two_option.series[0].data[0].name = '堆外平均命中率';
		two_option.series[0].data[0].value = Number(parseFloat(data["ehcache"]["mean-off-heap-hit-pct"] || 0) * 100).toFixed(0);
		cache_hit_chart.setOption(two_option);
		
		one_option.series[0].data[0].name = '缓存平均丢失率';
		one_option.series[0].data[0].value = Number(parseFloat(data["ehcache"]["mean-cache-misse-pct"] || 0) * 100).toFixed(0);
		one_option.series[0].pointer.color = '#428bca'
		cache_misse_chart.setOption(one_option);
		
		one_option.series[0].data[0].name = '磁盘平均命中率';
		one_option.series[0].data[0].value = Number(parseFloat(data["ehcache"]["mean-on-disk-hit-pct"] || 0) * 100).toFixed(0);
		one_option.series[0].pointer.color = '#428bca'
		on_disk_hit_chart.setOption(one_option); 
		
		//===========================使用率（折线图）==============================
		
		var usage_option = {
		    tooltip: {
		    	trigger: 'axis' 
		    },
			legend : {
				data : [ '堆外内存使用率', '堆内内存使用率', '磁盘空间使用率']
			},
			grid : {
				x : 40,
				y : 30,
				x2 : 10,
				y2 : 35,
				borderWidth : 0,
				borderColor : "#FFFFFF",
				shadowColor : '#FFFFFF', //默认透明
			},
			//X坐标轴信息
			xAxis : [ {
				axisLabel : {
					rotate : 40,
				},
				type : 'category',// 坐标轴类型，横轴默认为类目型'category'，纵轴默认为数值型'value'
				boundaryGap : false,
				data : xAxis_arr
			} ],
			//Y坐标轴信息
			yAxis : [ {
				min : 0,
				max : 100,
				axisLabel : {
					formatter : '{value}%'
				}
			} ],
			//初始数据
			series : [
				{
					name : '堆外内存使用率',
					type : 'line',
					data : []
				},
				{
					name : '堆内内存使用率',
					type : 'line',
					data : []
				},
				{ 
					name : '磁盘空间使用率',
					type : 'line',
					data : []
				}
			]
		};
		
		//批量初始化
		$("div.cache-usage-element").each(function(i, element){
			//缓存名称
			var cacheName = $(this).data("cacheName");
			var item_chart = getChart(cacheName,"usage");
			
			var off_heap_usage_arr = [];
			var heap_usage_arr = [];
			var disk_usage_arr = [];
			
			//循环所有的历史记录
			$.each(data["ehcacheHistory"]||[],function(h,history){
				//筛选出当前图表对应的历史数据
				$.each(history["caches"]||[],function(c,item){
					//比对缓存名称
					if(item["name"] = cacheName){
						off_heap_usage_arr.push(Number(parseFloat(item["off-heap-usage"] || 0) * 100).toFixed(2));
						heap_usage_arr.push(Number(parseFloat(item["heap-usage"] || 0) * 100).toFixed(2));
						disk_usage_arr.push(Number(parseFloat(item["disk-usage"] || 0) * 100).toFixed(2));
					}
				});
			});
			
			//数据补全；防止后台数据不能填充完整图表
			$.each([off_heap_usage_arr, heap_usage_arr, disk_usage_arr],function(i,arr){
				for (var int = 0; int < (100 - arr.length); int++) {
					arr.unshift(0);
				}
			});
			
			usage_option.series[0].data = off_heap_usage_arr;
			usage_option.series[1].data = heap_usage_arr;
			usage_option.series[2].data = disk_usage_arr;
			
			item_chart.setOption(usage_option);
			
		});
		
		//===========================命中率（折线图）==============================
		
		var hit_option = {
		    tooltip: {
		    	trigger: 'axis' 
		    },
			legend : {
				data : [ '堆内命中率', '堆外命中率', '总缓存命中率', '总缓存丢失率', '磁盘命中率' ]
			},
			grid : {
				x : 40,
				y : 30,
				x2 : 10,
				y2 : 35,
				borderWidth : 0,
				borderColor : "#FFFFFF",
				shadowColor : '#FFFFFF', //默认透明
			},
			//X坐标轴信息
			xAxis : [ {
				axisLabel : {
					rotate : 40,
				},
				type : 'category',// 坐标轴类型，横轴默认为类目型'category'，纵轴默认为数值型'value'
				boundaryGap : false,
				data : xAxis_arr
			} ],
			//Y坐标轴信息
			yAxis : [ {
				min : 0,
				max : 100,
				axisLabel : {
					formatter : '{value}%'
				}
			} ],
			//初始数据
			series : [
				{
					name : '堆内命中率',
					type : 'line',
					data : []
				},
				{
					name : '堆外命中率',
					type : 'line',
					data : []
				},
				{ 
					name : '总缓存命中率',
					type : 'line',
					data : []
				} ,
				{
					name : '总缓存丢失率',
					type : 'line',
					data : []
				} ,
				{
					name : '磁盘命中率',
					type : 'line',
					data : []
				} 
			]
		};
		
		$("div.cache-hit-element").each(function(i, element){
			//缓存名称
			var cacheName = $(this).data("cacheName");
			var item_chart = getChart(cacheName,"hit");
			
			var in_memory_hit_arr = [];
			var off_heap_hit_arr = [];
			var cache_hit_arr = [];
			var cache_misse_arr = [];
			var on_disk_hit_arr = [];
			
			//循环所有的历史记录
			$.each(data["ehcacheHistory"]||[],function(h,history){
				//筛选出当前图表对应的历史数据
				$.each(history["caches"]||[],function(c,item){
					//比对缓存名称
					if(item["name"] = cacheName){
						in_memory_hit_arr.push(Number(parseFloat(item["in-memory-hit-pct"] || 0) * 100).toFixed(2));
						off_heap_hit_arr.push(Number(parseFloat(item["off-heap-hit-pct"] || 0) * 100).toFixed(2));
						cache_hit_arr.push(Number(parseFloat(item["cache-hit-pct"] || 0) * 100).toFixed(2));
						cache_misse_arr.push(Number(parseFloat(item["cache-misse-pct"] || 0) * 100).toFixed(2));
						on_disk_hit_arr.push(Number(parseFloat(item["on-disk-hit-pct"] || 0) * 100).toFixed(2));
					}
				});
			});
			
			//数据补全；防止后台数据不能填充完整图表
			$.each([in_memory_hit_arr, off_heap_hit_arr, cache_hit_arr, cache_misse_arr, on_disk_hit_arr],function(i,arr){
				for (var int = 0; int < (100 - arr.length); int++) {
					arr.unshift(0);
				}
			});
			
			hit_option.series[0].data = in_memory_hit_arr;
			hit_option.series[1].data = off_heap_hit_arr;
			hit_option.series[2].data = cache_hit_arr;
			hit_option.series[3].data = cache_misse_arr;
			hit_option.series[4].data = on_disk_hit_arr;
			
			item_chart.resize();
			item_chart.setOption(hit_option);
			
		});
		
		//历史数据初始化完成，清除加载状态
		$.each(cache_charts,function(i,item){
			var chart = $.isPlainObject(item) ? item["chart"] : item;
			chart.hideLoading();
		});
		
		//解决Echarts隐藏元素高度异常问题
		$('#nav_tabs a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
			var href = $(this).attr('href');
			if("#hits" == href){
				$.each([in_memory_hit_chart, off_heap_hit_chart, 
				        cache_hit_chart, cache_misse_chart, 
				        on_disk_hit_chart],function(i,item_chart){
					item_chart.resize();
				});
			} else {
				$.each([heap_usage_chart, offheap_usage_chart, disk_usage_chart],function(i,item_chart){
					item_chart.resize();
				});
			}
		});
		
		$('#accordionUsage, #accordionHits').on('shown.bs.collapse', function () {
			$(this).find("div.panel").each(function (i,panel) {
				if($(panel).find("div.panel-collapse:eq(0)").hasClass("in")){
					//缓存名称
					var cacheName = $(panel).data("cacheName");
					var item_chart = getChart(cacheName, $(panel).hasClass("cache-usage-element") ? "usage" : "hit" );
						item_chart.resize();
				}
			});
		});
		 
		
		//===========================定时数据刷新==============================
		
		/*var sock = new SockJS( locat + '/monitor/watch/status.zf');
		sock.onopen = function() {
			console.log('open');
			sock.send('test');
		};

		sock.onmessage = function(e) {
		    console.log('message', e.data);
		    sock.close();
		};

		sock.onclose = function() {
		    console.log('close');
		};*/
		
		var axisData;
		clearInterval(timeTicket);
		var timeTicket = setInterval(function() {
			axisData = (new Date()).toLocaleTimeString().replace(/^\D*/, '');
			axisData = axisData.substr(axisData.indexOf(":") + 1);
			
			var off_heap_usage_arr = [];
			var heap_usage_arr = [];
			var disk_usage_arr = [];
			
			var in_memory_hit_arr = [];
			var off_heap_hit_arr = [];
			var on_disk_hit_arr = [];
			var cache_hit_arr = [];
			var cache_misse_arr = [];
			
			$.ajax({
				type : "POST",
				url : locat + '/monitor/cache/status.zf',
				async : false,
				dataType : 'json',
				success : function(json) {
					
					
					// '堆外内存使用率', '堆内内存使用率', '磁盘空间使用率'
					if($("#usage").is(":visible")){
						
						one_option.series[0].data[0].name = '堆外内存使用率 ';
						one_option.series[0].data[0].value = Number(parseFloat(data["ehcache"]["mean-off-heap-usage"] || 0) * 100).toFixed(0);
						one_option.series[0].pointer.color = '#428bca'
						offheap_usage_chart.setOption(one_option);
						
						two_option.series[0].data[0].name = '堆内内存使用率';
						two_option.series[0].data[0].value = Number(parseFloat(data["ehcache"]["mean-heap-usage"] || 0) * 100).toFixed(0);
						heap_usage_chart.setOption(two_option);
						
						one_option.series[0].data[0].name = '磁盘空间使用率';
						one_option.series[0].data[0].value = Number(parseFloat(data["ehcache"]["mean-disk-usage"] || 0) * 100).toFixed(0);
						one_option.series[0].pointer.color = '#428bca'
						disk_usage_chart.setOption(one_option);
						
					}
					
					
					//'堆内平均命中率', '堆外平均命中率', '缓存平均命中率', '缓存平均丢失率', '磁盘平均命中率'
					
					if($("#hits").is(":visible")){
						
						one_option.series[0].data[0].name = '堆内平均命中率 ';
						one_option.series[0].data[0].value = Number(parseFloat(json["mean-in-memory-hit-pct"] || 0) * 100).toFixed(0);
						in_memory_hit_chart.setOption(one_option, true);
						
						one_option.series[0].data[0].name = '堆外平均命中率';
						one_option.series[0].data[0].value = Number(parseFloat(json["mean-off-heap-hit-pct"] || 0) * 100).toFixed(0);
						cache_misse_chart.setOption(one_option, true);
					
					
						two_option.series[0].data[0].name = '缓存平均命中率';
						two_option.series[0].data[0].value = Number(parseFloat(json["mean-cache-hit-pct"] || 0) * 100).toFixed(0);
						cache_hit_chart.setOption(two_option, true);
						
						one_option.series[0].data[0].name = '缓存平均丢失率';
						one_option.series[0].data[0].value = Number(parseFloat(json["mean-cache-misse-pct"] || 0) * 100).toFixed(0);
						off_heap_hit_chart.setOption(one_option, true);
						
						one_option.series[0].data[0].name = '磁盘平均命中率';
						one_option.series[0].data[0].value = Number(parseFloat(json["mean-on-disk-hit-pct"] || 0) * 100).toFixed(0);
						on_disk_hit_chart.setOption(one_option, true);
					}
					
					//动态构建折线图使用率的数据
					
					$.each(json["caches"]||[],function(k,item){
						
						// '堆外内存使用率', '堆内内存使用率', '磁盘空间使用率'
						if($("#usage").is(":visible") && $("#" + item["name"] + "_usage_collapse").is(":visible")){
							
							off_heap_usage_arr.push(Number(parseFloat(item["off-heap-usage"] || 0) * 100).toFixed(2));
							heap_usage_arr.push(Number(parseFloat(item["heap-usage"] || 0) * 100).toFixed(2));
							disk_usage_arr.push(Number(parseFloat(item["disk-usage"] || 0) * 100).toFixed(2));
							
							var usage_chart = getChart(item["name"],"usage");
							
							usage_chart.addData([ 
							   [ 0, // 系列索引
							     off_heap_usage_arr, // 新增数据
							     false, // 新增数据是否从队列头部插入
							     false // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
							   ], 
							   [ 1,  heap_usage_arr, false, false ],
							   [ 2,  disk_usage_arr, false, false, axisData ]
							]);
							
						}
						
						//'堆内命中率', '堆外命中率', '总缓存命中率', '总缓存丢失率', '磁盘命中率'
						
						if($("#hits").is(":visible") && $("#" + item["name"] + "_hit_collapse").is(":visible")){
							
							in_memory_hit_arr.push(Number(parseFloat(item["in-memory-hit-pct"] || 0) * 100).toFixed(2));
							off_heap_hit_arr.push(Number(parseFloat(item["off-heap-hit-pct"] || 0) * 100).toFixed(2));
							on_disk_hit_arr.push(Number(parseFloat(item["on-disk-hit-pct"] || 0) * 100).toFixed(2));
							cache_hit_arr.push(Number(parseFloat(item["cache-hit-pct"] || 0) * 100).toFixed(2));
							cache_misse_arr.push(Number(parseFloat(item["cache-misse-pct"] || 0) * 100).toFixed(2));
							
							var hit_chart = getChart(item["name"],"hit");
							
							hit_chart.addData([ 
							   [ 0, // 系列索引
							     in_memory_hit_arr, // 新增数据
							     false, // 新增数据是否从队列头部插入
							     false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
							   ], 
							   [ 1,  off_heap_hit_arr, false, false ],
							   [ 2,  cache_hit_arr, false, false ],
							   [ 3,  cache_misse_arr, false, false ],
							   [ 4,  on_disk_hit_arr, false, false, axisData ]
							]);
						}
						
					});
					
				},
				error:function(){
					clearInterval(timeTicket);
				}
			});
		}, 5000);
		
	}, "json");
	

});