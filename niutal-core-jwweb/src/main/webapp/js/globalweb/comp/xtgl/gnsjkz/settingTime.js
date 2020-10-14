var map = {};
function showTabGrid(curPg, shCnt){
	map['gnmkmc'] = $("#gnmkmc").val();

	search('tabGrid',map);
}
//查询
function searchResult(){
	showTabGrid();
}

jQuery(function($){
	 
	// 验证结束时间是否大于开始时间
	$.validator.addMethod("checkSj", function(value, element, param) {
		var index = element.id.replace("kssj","").replace("jssj","");
		if($("#kssj"+index).val()=="" || $("#jssj"+index).val()==""){
			return true;
		}
		var isUnique = false;
		
		if($("#kssj"+index).val()<$("#jssj"+index).val()){
			isUnique = true;
		}
		return isUnique ;
		
	},"结束时间必须大于开始时间！");
		 
	$("#ajaxForm").validateForm();
	
	var index=0;
	var TempGrid = $.extend(true,{},BaseJqGrid,{
		resizeHandle:"#ajaxForm",
		multiselect: false,
		shrinkToFit: true,
	    url: _path+'/xtgl/timeSetting_cxSettingTimeIndex.html?doType=query', //这是Action的请求地址
	    postData:{},
	    colModel:[
			{label:'功能模块代码',name : 'gnmkdm', index : 'gnmkdm', key : true ,  hidden : true ,sortable : false,formatter: function(cellvalue, options, rowObject){
	        	 var html = [];
	        	 var zdm = jQuery.defined(rowObject.gnmkdm)?rowObject.gnmkdm:"";
	        	 var zdz = jQuery.defined(rowObject.gnmkdm)?rowObject.gnmkdm:"";
	        	 html.push('<input data-zddm="'+zdm+'" data-old="'+zdz+'" data-placement="right" name="gnmkdmList['+index+']" value="'+zdz+'" class="form-control input-sm" validate="{required:true}"/>');
	        	 return html.join("");
	         }},
			{label:'操作代码',name : 'czdm', index : 'czdm', key : true ,  hidden : true ,sortable : false,formatter: function(cellvalue, options, rowObject){
	        	 var html = [];
	        	 var zdm = jQuery.defined(rowObject.czdm)?rowObject.czdm:"";
	        	 var zdz = jQuery.defined(rowObject.czdm)?rowObject.czdm:"";
	        	 html.push('<input data-zddm="'+zdm+'" data-old="'+zdz+'" data-placement="right" name="czdmList['+index+']" value="'+zdz+'" class="form-control input-sm" validate="{required:true}"/>');
	        	 return html.join("");
	         }},
			{label:'功能模块名称',name : 'gnmkmc',index : 'gnmkmc',align:'left',width:'200px' , sortable : false},
			{label:'操作名称',name : 'czmc',index : 'czmc',align : 'center',width:'100px' , resizeable:false,sortable : false},
			{label:'开始时间',name : 'kssj',index : 'kssj',align : 'center',width:'200px' , resizeable:false,sortable : false,formatter: function(cellvalue, options, rowObject){
	        	 var html = [];
	        	 var zdm = jQuery.defined(rowObject.kssj)?rowObject.kssj:"";
	        	 var zdz = jQuery.defined(rowObject.kssj)?rowObject.kssj:"";
	        	 html.push('<input id="kssj'+index+'" data-zddm="'+zdm+'" data-old="'+zdz+'" data-placement="button" name="kssjList['+index+']" value="'+zdz
	        			 +'" onfocus="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',readOnly:true})" validate="{required:true,checkSj:true}" class="form-control input-sm" />');
	        	 return html.join("");
	         }}, 
			{label:'结束时间',name : 'jssj',index : 'jssj',align : 'center',width:'200px' , resizeable:false,sortable : false,formatter: function(cellvalue, options, rowObject){
	        	 var html = [];
	        	 var zdm = jQuery.defined(rowObject.jssj)?rowObject.jssj:"";
	        	 var zdz = jQuery.defined(rowObject.jssj)?rowObject.jssj:"";
	        	 html.push('<input id="jssj'+index+'" data-zddm="'+zdm+'" data-old="'+zdz+'" data-placement="right" name="jssjList['+index+']" value="'+zdz
		        			 +'" onfocus="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',readOnly:true})" validate="{required:true,checkSj:true}" class="form-control input-sm" />');
	        	 return html.join("");
	         }}, 
			{label:'是否使用',name : 'sfsy', index : 'sfsy',sortable : false  ,resizeable:false, formatter:function (cellvalue, options, rowObject){
	        	 var html = [];
	        	 var zdm = jQuery.defined(rowObject.sfsy)?rowObject.sfsy:"";
	        	 var zdz = jQuery.defined(rowObject.sfsy)?rowObject.sfsy:"";
	        	 html.push('<label class="radio-inline">');
					html.push('<input type="radio" data-zddm="'+zdm+'" data-old="'+zdz+'" data-placement="right" name="sfsyList['+index+']" value="1" '+(zdz ==  1 ? ' checked="checked" ' : "")+'>启用');
	             html.push('</label>');
	             html.push('<label class="radio-inline">');
					html.push('<input type="radio" data-zddm="'+zdm+'" data-old="'+zdz+'" data-placement="right" name="sfsyList['+index+']" value="0" '+(zdz ==  0 ? ' checked="checked" ' : "")+'>停用');
	             html.push('</label>');
	        	 return html.join("");
			}},
			{label:'提示信息',name : 'tsxx',index : 'tsxx',align : 'left',width:'200px' , sortable : false,formatter: function(cellvalue, options, rowObject){
	        	 var html = [];
	        	 var zdm = jQuery.defined(rowObject.tsxx)?rowObject.tsxx:"";
	        	 var zdz = jQuery.defined(rowObject.tsxx)?rowObject.tsxx:"";
	        	 html.push('<input data-zddm="'+zdm+'" data-old="'+zdz+'" data-placement="right" name="tsxxList['+index+']" value="'+zdz+'" class="form-control input-sm" validate="{required:true}"/>');
	        	 index++;
	        	 return html.join("");
	         }},
			{label:'备注',name : 'bz',index : 'bz',align : 'left',width:'200px' , sortable : false}
		],
		gridComplete:function(){
	    	index=0;
		}
	});
	
	$("#tabGrid").loadJqGrid(TempGrid);
	
	//判断字段是否有变化
	function checkIsChange(elements){
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
		//如果数据不相同，表示改动
		if( newVal && oldVal != newVal){
			return true;
		}
		return false;
	}
	
	$("#btn_bc").click(function(){
		var requestMap = {};
		//循环组织数据
		var index = 0;
		$("#tabGrid").find("tbody tr.jqgrow").each(function(i,tr){
			
			var elements1 = $(this).find(":input[name='kssjList["+i+"]']");
			var elements2 = $(this).find(":input[name='jssjList["+i+"]']");
			var elements3 = $(this).find(":input[name='sfsyList["+i+"]']");
			var elements4 = $(this).find(":input[name='tsxxList["+i+"]']");
			
			var isChange = checkIsChange(elements1);
			//如果没有变化，继续判断下一个
			if(!isChange){
				isChange = checkIsChange(elements2);
			}
			//如果没有变化，继续判断下一个
			if(!isChange){
				isChange = checkIsChange(elements3);
			}
			//如果没有变化，继续判断下一个
			if(!isChange){
				isChange = checkIsChange(elements4);
			}
			//如果与变化就把值插入
			if(isChange){
				requestMap['kssjList['+index+']'] = $(elements1[0]).val();
				requestMap['jssjList['+index+']'] = $(elements2[0]).val();
				var newVal = null;
				var checkeds = $(elements3).filter(":checked");
				if($(checkeds).size()>1){
					var result = new Array();
					$(checkeds).each(function(i,checked){
						result.push($(checked).val());
					});
					newVal = result.join(",");
				}else{
					newVal = $(elements3.filter(":checked")[0]).val();
				}
				requestMap['sfsyList['+index+']'] = newVal;
				requestMap['tsxxList['+index+']'] = $(elements4[0]).val();

				var elements5 = $(this).find(":input[name='gnmkdmList["+i+"]']");
				var elements6 = $(this).find(":input[name='czdmList["+i+"]']");
				requestMap['gnmkdmList['+index+']'] = $(elements5[0]).val();
				requestMap['czdmList['+index+']'] = $(elements6[0]).val();
				index += 1; 
			}
			
		});
		if(index > 0){
			$.ajax({
			   type		: "POST",
			   url		:  _path+'/xtgl/timeSetting_bcSettingTime.html',
			   data		: requestMap,
			   success: function(responseText,statusText,jqXHR){
					if(responseText.indexOf("成功") > -1){
						$.success(responseText,function() {
							search("tabGrid",{"gnmkmc":$("#gnmkmc").val()});
						});
					}else{
						$.error(responseText);
					}
			   },
			   error:function(jqXHR, textStatus, errorThrown){
				   
			   }
			});
		}else{
			$.alert("没有参数变化!");
		}
	});
});