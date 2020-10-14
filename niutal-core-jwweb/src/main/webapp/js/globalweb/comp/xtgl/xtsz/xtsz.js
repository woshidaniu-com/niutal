jQuery(function($){
	
	var index=0;
	var ssmk=$("#ssmk").val();
	//定义列表相关信息
	var TempGrid = $.extend(true,{},BaseJqGrid,{
		resizeHandle:"#ajaxForm1111",
		multiselect	: false,
		pager 		: null,
		rownumbers	: true, 
		rowNum 		: 10000,
	    url: _path+'/xtgl/xtsz_cxXtsz.html?doType=query', //这是Action的请求地址
	    postData:{"ssmk":ssmk},
	    colModel:[
	         {label:'主键',name:'xtsz_id', index: 'xtsz_id',key:true, hidden:true},
	         {label:'所属模块',name:'ssmk', index: 'ssmk',align:'center',width:'100px',sortable:false,formatter:'select',editoptions:{value:"CJMK:成绩模块;KSMK:考试模块;GGMK:公共模块;XJMK:学籍模块;XKMK:选课模块;JHMK:计划模块;PKGL:排课模块;PJMK:评价模块;BYMK:毕业模块"}},
	         {label:'设置项',name:'zs', index: 'zs',align:'left',width:'300px',sortable:false},
	         {label:'字段',name:'zdm', index: 'zdm',align:'left',width:'250px',hidden:true,sortable:false},
	         {label:'设置值',name:'zdz', index: 'zdz',align:'left',width:'250px',hidden:true,sortable:false},
	         {label:'设置值',name:'zdz_lr', index: 'zdz_lr',align:'center',width:'250px',sortable:false,formatter: function(cellvalue, options, rowObject){
	        	 var html = [];
	        	 var zdm = jQuery.defined(rowObject.zdm)?rowObject.zdm:"";
	        	 var zdz = jQuery.defined(rowObject.zdz)?rowObject.zdz:"";
	        	 if($.founded(rowObject["zdsjList"])){
	        		//字段类型： 1 ：表示 下拉框，2：表示输入框，3：表示单选框，4：表示多选框
	        	 	switch (parseInt(rowObject.zdlx||2)) {
						case 1:{
							html.push('<select data-zddm="'+zdm+'" data-old="'+zdz+'" data-placement="right" name="'+zdm+'" class="form-control" style="width:56%"  validate="'+(rowObject.zdzyq||"{}")+'" val="'+zdz+'">');
							html.push('<option value="">--请选择--</option>');
							//循环数据
							$.each(rowObject["zdsjList"],function(i,item){
								html.push('<option '+(zdz ==  item["key"] ? ' selected="selected" ' : "")+' value="'+item["key"]+'">'+item["value"]+'</option>');
							});
			        		html.push('</select>');
						};break;
						case 2:{
							 html.push('<input type="text" data-zddm="'+zdm+'" data-old="'+zdz+'" data-placement="right" name="'+zdm+'" value="'+zdz+'" class="form-control input-sm"  validate="'+(rowObject.zdzyq||"{}")+'"/>');
						};break;
						case 3:{
							html.push('<div class="radio pull-left">');
							//循环数据
							$.each(rowObject["zdsjList"],function(i,item){
								html.push('<label class="radio-inline">');
									html.push('<input type="radio" data-zddm="'+zdm+'" data-old="'+zdz+'" data-placement="right" name="'+zdm+'" value="'+item["key"]+'" '+(zdz ==  item["key"] ? ' checked="checked" ' : "")+' validate="'+(rowObject.zdzyq||"{}")+'">'+item["value"]);
					            html.push('</label>');
							});
							html.push(' </div>');
						};break;
						case 4:{
							var zdzs = zdz.split(",");
							html.push('<div class="checkbox pull-left">');
							//循环数据
							$.each(rowObject["zdsjList"],function(i,item){
								html.push('<label class="checkbox-inline">');
									html.push('<input type="checkbox" data-zddm="'+zdm+'" data-old="'+zdz+'" data-placement="right" name="'+zdm+'" value="'+item["key"]+'" '+( zdzs.indexOf(item["key"]) >= 0 ? ' checked="checked" ' : "")+' validate="'+(rowObject.zdzyq||"{}")+'">'+item["value"]);
					            html.push('</label>');
							});
							html.push('</div>');
						};break;
					}
	        	 }else{
	        		 html.push('<input data-zddm="'+zdm+'" data-old="'+zdz+'" data-placement="right" name="'+zdm+'" value="'+zdz+'" class="form-control input-sm" validate="'+(rowObject.zdzyq||"{}")+'"/>');
	        	 }
	        	 index++;
	        	 return html.join("");
	        	 
	        	 
	         }},
	         {label:'备注',name:'bz', index: 'bz',align:'left',width:'620px',sortable:false,formatter: function(cellvalue, options, rowObject){
	        	 	return '<p style="white-space: normal;">' +cellvalue+ '</p>';
	         	}
	         }
		],
		gridComplete:function(){
			$("select[name^='zdz']").each(function(index,item){
				$(item).setSelected($(item).attr("val"));
			});
			index=0;
			//grid加载完成后绑定校验
			$('#ajaxForm1111').validateForm({
				tipAtOnce	: true,
				beforeSubmit:function(){return false;}
			});
			//清除标题
			$("#tabGrid").find("tbody tr").each(function(){
				$(this).find("td:gt(1)").each(function(i){
					if(i < 4){
						$(this).attr("title","");
					}
				});
			});
			
		}
	});
	
	$("#tabGrid").loadJqGrid(TempGrid);

	//保存系统设置
	$(document).off("click touchend", "#btn_bc").on("click touchend", "#btn_bc", function(e) {
		jQuery('#ajaxForm1111').submit();
		if(jQuery('#ajaxForm1111').isValid()){
			var requestMap = {
				"ssgnmkdm"	:	$("#ssgnmkdm").val(),
					"ssmk"	:	$("#ssmk").val()
			};
			//循环组织数据
			var index = 0;
			//判断当前学年学期是否修改
			var xnxqCount = 0;
			//判断当前学年学期是否符合修改条件
			var xnbj = 0;
			var xqbj = 0;
			$("#tabGrid").find("tbody tr.jqgrow").each(function(i,tr){
				var elements = $(tr).find(":input");
				var oldVal	= $(elements[0]).data("old");
				var newVal	= null;
				if($(elements).is(":checkbox")){
					var checkeds = $(elements).filter(":checked");
					if($(checkeds).size()>1){
						var result = new Array();
						$(checkeds).each(function(i,checked){
							result.push($(checked).val());
						});
						newVal = result.join(",");
					}else{
						newVal = $(elements.filter(":checked")[0]).val();
					}
				}else if($(elements).is(":radio")){
					newVal = $(elements.filter(":checked")[0]).val();
				}else{
					//select,input
					newVal = $(elements[0]).val();
				}
				//console.log("oldVal:" + oldVal);
				//console.log("newVal:" + newVal);
				//如果数据不相同，表示改动
				if(  oldVal != newVal){
					if ("DQXNM" == $(elements[0]).data("zddm")) {
						xnxqCount ++;
						if (newVal < oldVal) {
							xnbj = 1;
						} else {
							xnbj = 2;
						}
					}
					if ("DQXQM" == $(elements[0]).data("zddm")) {
						xnxqCount ++;
						if (newVal < oldVal) {
							xqbj = 1;
						} 
					}
					requestMap['zdsjList['+index+'].key'] = $(elements[0]).data("zddm");
					requestMap['zdsjList['+index+'].value'] = newVal;
					index += 1; 
				}
			});
			if ((xnbj == 1) || (xnbj == 0 && xqbj == 1)) {
				$.alert("当前学年学期只能更新比现在大的值。");
				return false;
			}
			if(index > 0){
				if (xnxqCount > 0) {
					jQuery.confirm('当前学年学期更改后不能改回，您确定要更改当前学年学期吗？',function(isBoolean){
						if (isBoolean) {
							$.ajax({
							   type		: "POST",
							   url		:  _path+'/xtgl/xtsz_bcXtsz.html',
							   data		: requestMap,
							   success: function(data, statusText,jqXHR){
								   // data 可能是 xmlDoc, jsonObj, html, text, 等等;本函数中data是JSON对象
								   if(data["status"] == "success"){
										$.success(data["message"],function() {
											$("#tabGrid").searchGrid({
												"ssmk"		: $('#ssmk').val(),
												"zs"		: $('#zs').val()
											});
										});
									}else  if(data["status"] == "error"){
										$.error(data["message"]);
									}else{
										$.alert(data["message"]);
									}
							   },
							   error:function(jqXHR, textStatus, errorThrown){
								   
							   }
							});
						}
					});
				} else {
					$.ajax({
					   type		: "POST",
					   url		:  _path+'/xtgl/xtsz_bcXtsz.html',
					   data		: requestMap,
					   success: function(data,statusText,jqXHR){
						   	// data 可能是 xmlDoc, jsonObj, html, text, 等等;本函数中data是JSON对象
							if(data["status"] == "success"){
								$.success(data["message"],function() {
									$("#tabGrid").searchGrid({
										"ssmk"		: $('#ssmk').val(),
										"zs"		: $('#zs').val()
									});
								});
							}else{
								$.error(data["message"]);
							}
					   },
					   error:function(jqXHR, textStatus, errorThrown){
						   
					   }
					});
				}
			}else{
				$.alert("没有参数变化!");
			}
		}
	})
	//点击查询
	.off("click touchend", "#search_go").on("click touchend", "#search_go", function(e) {
		$("#tabGrid").searchGrid({
			"ssgnmkdm"	: $('#ssgnmkdm').val(),
			"ssmk"		: $('#ssmk').val(),
			"zs"		: $('#zs').val()
		});
	})
	
});