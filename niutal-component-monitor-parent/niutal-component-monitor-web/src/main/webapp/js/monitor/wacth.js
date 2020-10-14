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

	$.extend($.validator.messages, {
		"pattern" : "无效的预警周期；格式如：1s、1m、1h、1d；分别表示：1秒、1分钟、1小时、1天"
	});
	
	//===========================初始化图表对象==============================
	
	var jvm_chart = echarts.init(document.getElementById('jvm_chart'));
	var cpu_chart = echarts.init(document.getElementById('cpu_chart'));
	var ram_chart = echarts.init(document.getElementById('ram_chart'));
	
	var usage_chart = echarts.init(document.getElementById('usage_chart'));
	
	var jvm_memory = echarts.init(document.getElementById('jvm_memory'));
	var ram_memory = echarts.init(document.getElementById('ram_memory'));
	var swap_memory = echarts.init(document.getElementById('swap_memory'));
	//批量初始化
	$.each([jvm_chart, cpu_chart, ram_chart, usage_chart, jvm_memory,ram_memory,swap_memory],function(i,item){
		item.showLoading({
			text : "图表数据正在努力加载...",
			effect : 'whirling',
			//'spin' | 'bar' | 'ring' | 'whirling' | 'dynamicLine' | 'bubble' 				
			textStyle : {
				fontSize : 16
			}
		});
	});
	
	//访问历史数据
	$.post(locat + '/monitor/watch/info.zf', function(data){
		
		var jvm_total = [];
		var jvm_used = [];
		var jvm_free = [];
		var jvm_usage = [];
		
		var ram_total = [];
		var ram_used = [];
		var ram_free = [];
		var ram_usage = [];
		
		var swap_total = [];
		var swap_used = [];
		var swap_free = [];
		
		var cpu_usage = [];
		
		

		//历史使用率记录
		$.each(data["usageHistory"]||[],function(i,usage){
			
			var jvmUsage = Number(parseFloat(usage["jvm.memory.usage"] || 0) * 100).toFixed(0);
			var ramUsage = Number(parseFloat(usage["os.ram.usage"] || 0) * 100).toFixed(0);
			var cpuUsage = Number(parseFloat(usage["os.cpu.usage"] || 0) * 100).toFixed(0);
			
			//动态构建折线图使用率的数据
			jvm_usage.push(jvmUsage);
			ram_usage.push(ramUsage);
			cpu_usage.push(cpuUsage);
			
		});
		
		var max_data = {
			"total0" : 0,
			"total1" : 0,
			"total2" : 0
		};
		//历史使用量记录
		$.each(data["memoryHistory"]||[],function(i,mem){
			
			//JVM内存
			var jvmTotal = mem["jvm.memory.total"];
			var jvmUsed = mem["jvm.memory.used"];
			var jvmFree = mem["jvm.memory.free"];
			
			jvm_total.push(jvmTotal);
			jvm_used.push(jvmUsed);
			jvm_free.push(jvmFree);
			
			//物理内存
			var ramTotal = mem["os.ram.total"];
			var ramUsed = mem["os.ram.used"];
			var ramFree = mem["os.ram.free"];

			ram_total.push(ramTotal);
			ram_used.push(ramUsed);
			ram_free.push(ramFree);
			
			//虚拟内存
			var swapTotal = mem["os.swap.total"];
			var swapUsed = mem["os.swap.used"];
			var swapFree = mem["os.swap.free"];
			
			swap_total.push(swapTotal);
			swap_used.push(swapUsed);
			swap_free.push(swapFree);
			
			max_data["total0"] = Math.max(max_data["total0"],jvmTotal||0);
			max_data["total1"] = Math.max(max_data["total1"],ramTotal||0);
			max_data["total2"] = Math.max(max_data["total2"],swapTotal||0);
			
		});
		
		//数据补全；防止后台数据不能填充完整图表
		
		$.each([jvm_usage,ram_usage,cpu_usage],function(i,usage){
			for (var int = 0; int < (50 - usage.length); int++) {
				usage.unshift(0);
			}
		});
		$.each([jvm_total, ram_total, swap_total, jvm_used, ram_used, swap_used , jvm_free, ram_free, swap_free],function(i,usage){
			for (var int = 0; int < (15 - usage.length); int++) {
				usage.unshift(0);
			}
		});
		
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
					value : Number(parseFloat(data["usage"]["jvm.memory.usage"] || 0) * 100).toFixed(0),
					name : 'JVM使用率'
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
					value : Number(parseFloat(data["usage"]["os.cpu.usage"] || 0) * 100).toFixed(0),
					name : 'CPU使用率'
				} ]
			} ]
		};
		
		jvm_chart.setOption(one_option);
		cpu_chart.setOption(two_option);
		one_option.series[0].data[0].name = '内存使用率';
		one_option.series[0].data[0].value = Number(parseFloat(data["usage"]["os.ram.usage"] || 0) * 100).toFixed(0);
		one_option.series[0].pointer.color = '#428bca'
		ram_chart.setOption(one_option);
		
		//===========================使用率（折线图）==============================
		
		
		var now = new Date();
		var usage_xAxis = [];
		var len = 50;
		while (len--) {
			var time = now.toLocaleTimeString().replace(/^\D*/, '');
			time = time.substr(time.indexOf(":") + 1);
			usage_xAxis.unshift(time);
			now = new Date(now - 1000);
		}
		var usage_option = {
		    tooltip: {
		    	trigger: 'axis' 
		    },
			legend : {
				data : [ 'JVM内存使用率', '物理内存使用率', 'CPU使用率' ]
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
				data : usage_xAxis
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
					name : 'JVM内存使用率',
					type : 'line',
					itemStyle: {normal: {areaStyle: {type: 'default'}}},
					data : jvm_usage
				},
				{
					name : '物理内存使用率',
					type : 'line',
					itemStyle: {normal: {areaStyle: {type: 'default'}}},
					data : ram_usage
				},
				{
					name : 'CPU使用率',
					type : 'line',
					itemStyle: {normal: {areaStyle: {type: 'default'}}},
					data : cpu_usage
				} 
			]
		};
		usage_chart.setOption(usage_option);
		
		
		//===========================使用量（折线图）==============================
		
		var titles = ["JVM内存","物理内存","虚拟内存"];
		var units = ["MB","GB","GB"];
		var total_data = [jvm_total, ram_total, swap_total];
		var used_data = [jvm_used, ram_used, swap_used];
		var free_data = [jvm_free, ram_free, swap_free];
		var mem_xAxis = [];
		var now = new Date();
		var len = 20;
		while (len--) {
			var time = now.toLocaleTimeString().replace(/^\D*/, '');
			time = time.substr(time.indexOf(":") + 1);
			mem_xAxis.unshift(time);
			now = new Date(now - 1000);
		}
		//定义图表options 			
		var mem_option = {
			title: {
		        left: 'center'
		    },
		    tooltip : {
		        trigger: 'axis',
		        axisPointer: {
		            type: 'cross',
		            label: {
		                backgroundColor: '#6a7985'
		            }
		        }
		    },
		    legend: {
		        data:['已用内存'/*,'最大内存'*/]
		    },
		    grid : {
				x : 60,
				y : 30,
				x2 : 30,
				y2 : 35,
		        containLabel: true
			},
		    //x时间轴坐标
			xAxis : [
				{
					type : 'category',
					boundaryGap : false,
					data : mem_xAxis
				}
			],
			//y值坐标
			yAxis : [ {
					min : 0,
					max : 100,
					axisLabel : {
						formatter : '{value} MB'
					}
				} 
			],
		    series : [
		        {
		            name:'已用内存',
		            type:'line',
		            stack: '总量',
		            itemStyle: {normal: {areaStyle: {type: 'default'}}},
		            data:[]
		        }/*,
		        {
		            name:'最大内存',
		            type:'line',
		            stack: '总量',
		            itemStyle: {normal: {areaStyle: {type: 'default'}, position: 'top'}},
		            data:[]
		        }*/
		    ]
		};

		//批量初始化
		$.each([jvm_memory,ram_memory,swap_memory],function(i,item){
			
			console.log("max " + i + ":" + max_data["total" + i]);
			
			mem_option.title.text = titles[i];
			mem_option.series[0].data = used_data[i];
			//mem_option.series[1].data = total_data[i];
			mem_option.yAxis[0].axisLabel.formatter = '{value} ' + units[i];
			mem_option.yAxis[0].max =  max_data["total" + i];
			item.setOption(mem_option, true);
		});
		
		//历史数据初始化完成，清除加载状态
		$.each([jvm_chart, cpu_chart, ram_chart, usage_chart, jvm_memory,ram_memory,swap_memory],function(i,item){
			item.hideLoading();
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
			var jvm_total = [];
			var jvm_used = [];
			var jvm_free = [];
			var jvm_usage = [];
			
			var ram_total = [];
			var ram_used = [];
			var ram_free = [];
			var ram_usage = [];
			
			var swap_total = [];
			var swap_used = [];
			var swap_free = [];
			
			var cpu_usage = [];
			$.ajax({
				type : "POST",
				url : locat + '/monitor/watch/status.zf',
				async : false,
				dataType : 'json',
				success : function(json) {
					
					//JVM内存
					var jvmTotal = json["jvm.memory.total"];
					var jvmUsed = json["jvm.memory.used"];
					var jvmFree = json["jvm.memory.free"];
					var jvmUsage = Number(parseFloat(json["jvm.memory.usage"] || 0) * 100).toFixed(0);

					mem_option.title.text = titles[0];
					mem_option.series[0].data = used_data[0];
					//mem_option.series[1].data = total_data[i];
					mem_option.yAxis[0].axisLabel.formatter = '{value} ' + units[0];
					mem_option.yAxis[0].max =  Math.max(max_data["total0"],jvmTotal);
					jvm_memory.setOption(mem_option, true);
					
					//物理内存
					var ramTotal = json["os.ram.total"];
					var ramUsed = json["os.ram.used"];
					var ramFree = json["os.ram.free"];
					var ramUsage = Number(parseFloat(json["os.ram.usage"] || 0) * 100).toFixed(0);
					
					//虚拟内存
					var swapTotal = json["os.swap.total"];
					var swapUsed = json["os.swap.used"];
					var swapFree = json["os.swap.free"];
					
					//CPU
					var cpuUsage = Number(parseFloat(json["os.cpu.usage"] || 0) * 100).toFixed(0);
					
					//动态构建仪表盘使用率的数据
					
					one_option.series[0].data[0].value = jvmUsage;
					one_option.series[0].data[0].name = 'JVM使用率';
					one_option.series[0].pointer.color = '#FF0000'
					jvm_chart.setOption(one_option, true);

					two_option.series[0].data[0].value = cpuUsage;
					two_option.series[0].data[0].name = 'CPU使用率';
					cpu_chart.setOption(two_option, true);

					one_option.series[0].data[0].value = ramUsage;
					one_option.series[0].data[0].name = '内存使用率';
					one_option.series[0].pointer.color = '#428bca'
					ram_chart.setOption(one_option, true);
					
					//动态构建折线图使用率的数据
					jvm_usage.push(jvmUsage);
					ram_usage.push(ramUsage);
					cpu_usage.push(cpuUsage);
					usage_chart.addData([ [ 0, // 系列索引
	                    jvm_usage, // 新增数据
						false, // 新增数据是否从队列头部插入
						false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
					], [ 1, // 系列索引
						ram_usage, // 新增数据
						false, // 新增数据是否从队列头部插入
						false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
					], [ 2, // 系列索引
						cpu_usage, // 新增数据
						false, // 新增数据是否从队列头部插入
						false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
						axisData // 坐标轴标签
					] ]);
					
					//动态构建柱状图使用量的数据
					//jvm_total.push(jvmTotal);
					jvm_used.push(jvmUsed);
					jvm_memory.addData([ [ 0, // 系列索引
					   jvm_used, // 新增数据
	   					false, // 新增数据是否从队列头部插入
	   					false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
	   					axisData // 坐标轴标签
	   				]/*, [ 1, // 系列索引
	   				  jvm_total, // 新增数据
	   					false, // 新增数据是否从队列头部插入
	   					false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
	   				]*/]);
					
					//ram_total.push(ramTotal);
					ram_used.push(ramUsed);
					ram_memory.addData([ [ 0, // 系列索引
					    ram_used, // 新增数据
	   					false, // 新增数据是否从队列头部插入
	   					false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
	   					axisData // 坐标轴标签
	   				]/*, [ 1, // 系列索引
	   				  ram_total, // 新增数据
	   					false, // 新增数据是否从队列头部插入
	   					false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
	   				]*/]);
					//console.log("swapTotal:" + swapTotal);
					//console.log("swapUsed:" + swapUsed);
					//swap_total.push(swapTotal);
					swap_used.push(swapUsed);
					
					swap_memory.addData([ [ 0, // 系列索引
					    swap_used, // 新增数据
	   					false, // 新增数据是否从队列头部插入
	   					false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
	   					axisData // 坐标轴标签
	   				]/*, [ 1, // 系列索引
	   				  swap_total, // 新增数据
	   					false, // 新增数据是否从队列头部插入
	   					false, // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
	   				]*/]);
					
					$("#td_jvmUsage").html(jvmUsage);
					$("#td_serverUsage").html(ramUsage);
					$("#td_cpuUsage").html(cpuUsage);
					
				},
				error:function(){
					clearInterval(timeTicket);
				}
			});
		}, 3000);
		
	}, "json");
	
	$(document).off("click touchend", ".btn-watch").on("click touchend", ".btn-watch", function(e) {
		if($("#thresholdForm").valid()){
			$.ajax({
				async	: false,
				type	: "POST",
		        url		: _path + "/monitor/watch/threshold.zf",
		        data 	: {
		        	"key"		: $(this).data("key"),
		        	"value"		: $($(this).data("href")).val()
		        }, 
		        dataType: "json",
		        success	: function (data) {
		        	if(data["status"] == "success"){
						$.success(data["message"],function() {
							
						});
					}else if(data["status"] == "error"){
						$.error(data["message"]);
					}else{
						$.alert(data["message"]);
					}
		        }
			});
		}
	}).off("click touchend", ".btn-notice").on("click touchend", ".btn-notice", function(e) {
		submitForm("ajaxForm",function(responseData,statusText){
			// responseData 可能是 xmlDoc, jsonObj, html, text, 等等...
			// statusText 	描述状态的字符串（可能值："No Transport"、"timeout"、"notmodified"---304 "、"parsererror"、"success"、"error"
		   if(responseData["status"] == "success"){
				$.success(responseData["message"],function() {
					
				});
			}else if(responseData["status"] == "error"){
				$.error(responseData["message"]);
			}else{
				$.alert(responseData["message"]);
			}
		});
		/*var noticeType =  $("input[name='noticeType']").getChecked().val();
		console.log(noticeType);
		var dataList = new Array();  
			dataList.push({key: "watch.notice.type", value: noticeType});   
			dataList.push({key:"watch.mail.to",	value: $("#inputEmail").val()});   
			dataList.push({key:"watch.sms.to",	value: $("#inputSms").val()});   
		$.ajax({
			async	: false,
			type	: "POST",
	        url		: _path + "/monitor/watch/notice.zf",
	        data 	: JSON.stringify(dataList),//将对象序列化成JSON字符串  
	        dataType: "json",
	        success	: function (data) {
	        	if(data["status"] == "success"){
					$.success(data["message"],function() {
						
					});
				}else if(data["status"] == "error"){
					$.error(data["message"]);
				}else{
					$.alert(data["message"]);
				}
	        }
		});*/
	});	
	
	$("#thresholdForm").validateForm();
	$("#ajaxForm").validateForm({
		beforeSubmit : function(formData,jqForm,opts){
			
			//获取选中项的input
			var result = new Array();
			var checkeds = $("input[name='noticeType']").filter(":checked");
			if($(checkeds).size()>1){
				$(checkeds).each(function(i,checked){
					result.push($(checked).val());
				});
			}else{
				result.push($(checkeds[0]).val());
			}
			//item:name=watch.notice.type&value=sms&type=checkbox&required=false
			formData.push({"name":"watch.notice.type","value": result.join(",")});
			
			return true;
		}
	});
	
});