(function($){
	var bigAutocomplete = new function(){
		currentInputText = null;//目前获得光标的输入框（解决一个页面多个输入框绑定自动补全功能）
		this.functionalKeyArray = [9,20,13,16,17,18,91,92,93,45,36,33,34,35,37,39,112,113,114,115,116,117,118,119,120,121,122,123,144,19,145,40,38,27];//键盘上功能键键值数组
		this.holdText = null;//输入框中原始输入的内容
		
		//初始化插入自动补全div，并在document注册mousedown，点击非div区域隐藏div
		this.init = function(){
			$("body").append("<div id='bigAutocompleteContent' class='bigautocomplete-layout'></div>");
			$(document).bind('mousedown',function(event){
				var $target = $(event.target);
				if((!($target.parents().andSelf().is('#bigAutocompleteContent'))) && (!$target.is($(currentInputText)))){
					bigAutocomplete.hideAutocomplete();
				}
			})
			
			//鼠标悬停时选中当前行
			$("#bigAutocompleteContent").delegate("tr", "mouseover", function() {
				$("#bigAutocompleteContent tr").removeClass("ct");
				$(this).addClass("ct");
			}).delegate("tr", "mouseout", function() {
				$("#bigAutocompleteContent tr").removeClass("ct");
			});		
			
			
			//单击选中行后，选中行内容设置到输入框中，并执行callback函数
			$("#bigAutocompleteContent").delegate("tr", "click", function() {
				$(currentInputText).val( $(this).find("div:last").html());
				var callback_ = $(currentInputText).data("config").callback;
				if($("#bigAutocompleteContent").css("display") != "none" && callback_ && $.isFunction(callback_)){
					callback_($(this).data("jsonData"));
					
				}				
				bigAutocomplete.hideAutocomplete();
			})			
			
		}
		
		this.autocomplete = function(param){
			
			if($("body").length > 0 && $("#bigAutocompleteContent").length <= 0){
				bigAutocomplete.init();//初始化信息
			}			
			var $this = this;//为绑定自动补全功能的输入框jquery对象
			
			var $bigAutocompleteContent = $("#bigAutocompleteContent");
			
			this.config = {
			               //width:下拉框的宽度，默认使用输入框宽度
			               width:0,
			               //url：格式url:""用来ajax后台获取数据，返回的数据格式为data参数一样
			               url:null,
			               //url参数获取回调方法
			               queryParamFn:null,
			               /*data：格式{data:[{MC:null,DM:{}},{MC:null,DM:{}}]}
			               url和data参数只有一个生效，data优先*/
			               data:null,
			               //callback：选中行后按回车或单击时回调的函数
			               callback:function(data){
                               $("#"+$this.config.setValue).val(data.DM);
							   $("#"+$this.config.setText).val(data.MC);
                           },
						   //size:显示个数，该属性仅针对ajax方式起作用
						   size:10,
						   setValue:null,
						   setText:null,
						   //isValidata:是否验证数据正确性
						   isValidata: true,
						   //parentId:父节点
						   parentId:null,
						   //secondParentId:第二父节点
						   secondParentId:null
						   };
			$.extend(this.config,param);
			
			$this.data("config",this.config);
			$this.css("background-image","url('../images/mohutip.png')");
			$this.css("background-position","bottom right");
			$this.css("background-repeat","no-repeat");
			$this.attr("autocomplete","off");
			
			//输入框keydown事件
			$this.keydown(function(event) {
				var node = event.currentTarget;
				switch (event.keyCode) {
				case 40://向下键
					
					if($bigAutocompleteContent.css("display") == "none")return;
					
					var $nextSiblingTr = $bigAutocompleteContent.find(".ct");
					if($nextSiblingTr.length <= 0){//没有选中行时，选中第一行
						$nextSiblingTr = $bigAutocompleteContent.find("tr:first");
					}else{
						$nextSiblingTr = $nextSiblingTr.next();
					}
					$bigAutocompleteContent.find("tr").removeClass("ct");
					
					if($nextSiblingTr.length > 0){//有下一行时（不是最后一行）
						$nextSiblingTr.addClass("ct");//选中的行加背景
						$(node).val($nextSiblingTr.find("div:last").html());//选中行内容设置到输入框中
						
						//div滚动到选中的行,jquery-1.6.1 $nextSiblingTr.offset().top 有bug，数值有问题
						$bigAutocompleteContent.scrollTop($nextSiblingTr[0].offsetTop - $bigAutocompleteContent.height() + $nextSiblingTr.height() );
						
					}else{
						$(node).val(bigAutocomplete.holdText);//输入框显示用户原始输入的值
					}
					
					
					break;
				case 38://向上键
					if($bigAutocompleteContent.css("display") == "none")return;
					
					var $previousSiblingTr = $bigAutocompleteContent.find(".ct");
					if($previousSiblingTr.length <= 0){//没有选中行时，选中最后一行行
						$previousSiblingTr = $bigAutocompleteContent.find("tr:last");
					}else{
						$previousSiblingTr = $previousSiblingTr.prev();
					}
					$bigAutocompleteContent.find("tr").removeClass("ct");
					
					if($previousSiblingTr.length > 0){//有上一行时（不是第一行）
						$previousSiblingTr.addClass("ct");//选中的行加背景
						$(node).val($previousSiblingTr.find("div:last").html());//选中行内容设置到输入框中
						
						//div滚动到选中的行,jquery-1.6.1 $$previousSiblingTr.offset().top 有bug，数值有问题
						$bigAutocompleteContent.scrollTop($previousSiblingTr[0].offsetTop - $bigAutocompleteContent.height() + $previousSiblingTr.height());
					}else{
						$(node).val(bigAutocomplete.holdText);//输入框显示用户原始输入的值
					}
					
					break;
				case 27://ESC键隐藏下拉框
					
					bigAutocomplete.hideAutocomplete();
					break;
				}
			});		
			
			//输入框keyup事件
			$this.keyup(function(event) {
				var k = event.keyCode;
				var node = event.currentTarget;
				var ctrl = event.ctrlKey;
				var isFunctionalKey = false;//按下的键是否是功能键
				for(var i=0;i<bigAutocomplete.functionalKeyArray.length;i++){
					if(k == bigAutocomplete.functionalKeyArray[i]){
						isFunctionalKey = true;
						break;
					}
				}
				//k键值不是功能键或是ctrl+c、ctrl+x时才触发自动补全功能
				if(!isFunctionalKey && (!ctrl || (ctrl && k == 67) || (ctrl && k == 88)) ){
					$bigAutocompleteContent.html("");
					var config = $(node).data("config");
					var parentId = config.parentId;
					var secondParentId = config.secondParentId;
					var offset = $(node).offset();
					if(config.width <=0){
						config.width  = $(node).outerWidth() - 2
					}
					$bigAutocompleteContent.width(config.width);
					var h = $(node).outerHeight() - 1;
					$bigAutocompleteContent.css({"top":offset.top + h,"left":offset.left});
					
					var data = config.data;
					var url = config.url;
					var showsize = config.size;
					var queryParamFn = config.queryParamFn;
					var keyword_ = $.trim($(node).val());
					if(data != null && $.isArray(data) ){
						var data_ = new Array();
						for(var i=0;i<data.length;i++){
							if(data[i].MC.indexOf(keyword_) > -1){
								data_.push(data[i]);
							}
						}
						
						makeContAndShow(data_);
					}else if(url != null && url != ""){//ajax请求数据
						if(parentId == null){
							var p1 = {keyword:keyword_,showsize:showsize};
							var p2 = {};
							if(queryParamFn){
								p2 = queryParamFn();
							}
							$.post(url,$.extend(p1,p2),function(result){
								makeContAndShow(result);
							},"json");
						}else{
							var p1 = {keyword:keyword_,showsize:showsize,parentValue:$("#"+parentId).val(),secondParentValue:$("#"+secondParentId).val()};
							var p2 = {};
							if(queryParamFn){
								p2 = queryParamFn();
							}
							$.post(url,$.extend(p1,p2),function(result){
								makeContAndShow(result);
							},"json");
						}
					}
					
					bigAutocomplete.holdText = $(node).val();
					if(keyword_ != ""){
						var cont = "&nbsp;按\"<font color='red'>" + keyword_ + "</font>\"检索：";
						cont += "<div class=\"spx\" style=\"height:1px;border-bottom:1px dashed #000;\"></div>";
						jQuery(".spx").width=$bigAutocompleteContent.width();
						$bigAutocompleteContent.prepend(cont);
					}
				}
				//回车键
				if(k == 13){
					var callback_ = $(node).data("config").callback;
					if($bigAutocompleteContent.css("display") != "none"){
						if(callback_ && $.isFunction(callback_)){
							callback_($bigAutocompleteContent.find(".ct").data("jsonData"));
						}
						$bigAutocompleteContent.hide();						
					}
				}
			});	
			
			//输入框focus事件
			//author wzg
			$this.focus(function(event) {
			    var k = event.keyCode;
				var node = event.currentTarget;
				var config = $(node).data("config");
					
				var offset = $(node).offset();
				if(config.width <=0){
					config.width  = $(node).outerWidth() - 2
				}
				$bigAutocompleteContent.width(config.width);
				var h = $(node).outerHeight() - 1;
				$bigAutocompleteContent.css({"top":offset.top + h,"left":offset.left});
				
				var data = config.data;
				var url = config.url;
				var parentId = config.parentId;
				var secondParentId = config.secondParentId;
				var showsize = config.size;
				var keyword_ = $.trim($(node).val());
				var queryParamFn = config.queryParamFn;
				$bigAutocompleteContent.html("");
				if(keyword_ == null || keyword_ == ""){
					if(data != null && $.isArray(data) ){		
						makeContAndShow(data);
					}else if(url != null && url != ""){//ajax请求数据
						if(parentId == null){
							var p1 = {keyword:keyword_,showsize:showsize};
							var p2 = {};
							if(queryParamFn){
								p2 = queryParamFn();
							}
							$.post(url,$.extend(p1,p2),function(result){
								makeContAndShow(result);
							},"json");
						}else{
							var p1 = {keyword:keyword_,showsize:showsize,parentValue:$("#"+parentId).val(),secondParentValue:$("#"+secondParentId).val()};
							var p2 = {};
							if(queryParamFn){
								p2 = queryParamFn();
							}
							$.post(url,$.extend(p1,p2),function(result){
								makeContAndShow(result);
							},"json");
						}
					}
				}else{
					var cont = "&nbsp;按\"<font color='red'>" + keyword_ + "</font>\"检索：";
					cont += "<div class=\"spx\" style=\"height:1px;border-bottom:1px dashed #000;\"></div>";
					jQuery(".spx").width=$bigAutocompleteContent.width();
					$bigAutocompleteContent.prepend(cont);
					if(data != null && $.isArray(data) ){
						var data_ = new Array();
						for(var i=0;i<data.length;i++){
							if(data[i].MC.indexOf(keyword_) > -1){
								data_.push(data[i]);
							}
						}
						
						makeContAndShow(data_);
					}else if(url != null && url != ""){//ajax请求数据
						if(parentId == null){
							var p1 = {keyword:keyword_,showsize:showsize};
							var p2 = {};
							if(queryParamFn){
								p2 = queryParamFn();
							}
							$.post(url,$.extend(p1,p2),function(result){
								makeContAndShow(result);
							},"json");
						}else{
							var p1 = {keyword:keyword_,showsize:showsize,parentValue:$("#"+parentId).val(),secondParentValue:$("#"+secondParentId).val()};
							var p2 = {};
							if(queryParamFn){
								p2 = queryParamFn();
							}
							
							$.post(url,$.extend(p1,p2),function(result){
								makeContAndShow(result);
							},"json");
						}
					}
					
					bigAutocomplete.holdText = $(node).val();
				}
				//回车键
				if(k == 13){
					var callback_ = $(node).data("config").callback;
					if($bigAutocompleteContent.css("display") != "none"){
						if(callback_ && $.isFunction(callback_)){
							callback_($bigAutocompleteContent.find(".ct").data("jsonData"));
						}
						$bigAutocompleteContent.hide();						
					}
				}
				
				
			});
			
			//输入框blur事件    验证数据正确性
			//author wzg
			$this.blur(function(event) {
				var node = event.currentTarget;
				var config = $(node).data("config");
				var keyword_ = $.trim($(node).val());
				
				var succeFlag = false;
				$bigAutocompleteContent.find("tr").each(function(index){
					if(keyword_ == $(this).data("jsonData").MC){
						succeFlag = true;
						var callback_ = config.callback;
						if($bigAutocompleteContent.css("display") != "none"){
							if(callback_ && $.isFunction(callback_) && $bigAutocompleteContent.find(".ct").data("jsonData")){
								callback_($bigAutocompleteContent.find(".ct").data("jsonData"));
							}else if(callback_ && $.isFunction(callback_)){
								callback_($(this).data("jsonData"));
							}
							$bigAutocompleteContent.hide();						
						}else{
							if(callback_ && $.isFunction(callback_)){
								callback_($(this).data("jsonData"));
							}
						}
						return false;
					}
				});
				if(config.isValidata){
					if(!succeFlag){
						$("#"+$this.config.setValue).val("");
						$("#"+$this.config.setText).val("");
					}
				} else {
					if(!succeFlag){
						$("#"+$this.config.setValue).val("");
					}
				}
			});
			
					
			//组装下拉框html内容并显示
			function makeContAndShow(data_){
				if(data_ == null || data_.length <=0 ){
					return;
				}
				
				var cont = "<table><tbody>";
				for(var i=0;i<data_.length;i++){
					if(data_[i].MS != undefined){
						cont += "<tr><td><div>" + data_[i].MC + "</div><font color='gray'>(" + data_[i].MS + ")</font></td></tr>";
					}else{
						cont += "<tr><td><div>" + data_[i].MC + "</div></td></tr>";
					}
				}
				cont += "</tbody></table>";
				$bigAutocompleteContent.empty();
				$bigAutocompleteContent.html(cont);
				$bigAutocompleteContent.show();
				
				//每行tr绑定数据，返回给回调函数
				$bigAutocompleteContent.find("tr").each(function(index){
					$(this).data("jsonData",data_[index]);
				})
			}			
					
			
			//输入框focus事件
			$this.focus(function(event){
				currentInputText = event.currentTarget;
			});
			
		}
		//隐藏下拉框
		this.hideAutocomplete = function(){
			var $bigAutocompleteContent = $("#bigAutocompleteContent");
			if($bigAutocompleteContent.css("display") != "none"){
				$bigAutocompleteContent.find("tr").removeClass("ct");
				$bigAutocompleteContent.hide();
			}			
		}
		
	};
	
	
	$.fn.bigAutocomplete = bigAutocomplete.autocomplete;
	
})(jQuery)