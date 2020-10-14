$(function() {

	$.ajaxSetup({
		statusCode	: {
	        //HTTP Status 901（HTTP 会话过期）会话过期或者注销
	        901: function () {
	        	//提示信息
	        	$.alert($.i18n.niutal["statusCode"]["901"],function(){
	        		location.reload();
	            });
	        }
	    }
	});
	
	//个性化验证的提示文字
	$.extend($.validator.messages, {
	    required: "此题为必答题"
	});
	
	var $items = $(".item");
	
	//启用require属性
	function enableRequireAttr($items){
		for(var i=0;i<$items.size();i++){
			var item = $items.get(i);
			var $item = $(item);
			//如果是必填，加上require属性
			var sfbd = $item.attr("sfbd");
			var $radioOrCheckboxs = $item.find(":radio,:checkbox");
			var $textareas = $item.find("textarea");
			if(sfbd){
				$radioOrCheckboxs.attr("required","required");
				$textareas.attr("required","required");
			}
		}
	}
	function addStFinish($item){
		$item.addClass("st_finish");
	}
	function removeStFinish($item){
		$item.removeClass("st_finish");
		deleteYhdjStxx_ajax($item);
	}
	//禁用require属性
	function disableRequireAttr($items){
		for(var i=0;i<$items.size();i++){
			var item = $items.get(i);
			var $item = $(item);
			//如果是必填，禁用require属性
			var sfbd = $item.attr("sfbd");
			var $radioOrCheckboxs = $item.find(":radio,:checkbox");
			var $textareas = $item.find("textarea");
			if(sfbd){
				$radioOrCheckboxs.removeAttr("required");
				$textareas.removeAttr("required");
			}
		}
	}
	
	//删除用户答卷试题选项
	//问卷id
	//试题id
	//选项id
	function deleteYhdjStxx_ajax($item){
		if("false" == useAsyncSubmit){
			return;
		}
		var wjid = $("#wjid").val();
		var stid = $item.attr("stid"); 
		var url = contextPath +"/wjdc/wjgl/ajaxDeleteYhdjStxx.zf";
	     $.ajax({
	         type: "POST",
	         url: url,
	         data: {
	        	 "wjid":wjid,
	        	 "stid":stid
	         },
	         dataType: "json",
	         success: function(responseData){
				if (responseData["status"] == "success"){
					console.log("ajax删除成功");
				} else if (responseData["status"] == "fail"){
					console.log("ajax保存失败:"+responseData["message"]);
				}else if(responseData["status"] == "error"){
					console.log("ajax保存失败:"+responseData["message"]);
				}
	         }
	     })
	}
	
	//ajax
	function saveYhdjStxx_ajax($item){
		if("false" == useAsyncSubmit){
			return;
		}
		var $newItem = $item.clone();
		var $ajaxSaveYhdjStxxForm_content = $("#ajaxSaveYhdjStxxForm_content");
		
		$ajaxSaveYhdjStxxForm_content.empty();
		$ajaxSaveYhdjStxxForm_content.append($newItem);
		var param = $("#ajaxSaveYhdjStxxForm").serialize();
		console.log("提交参数:"+param);
		var url = contextPath +"/wjdc/wjgl/ajaxSaveYhdjStxx.zf";
	     $.ajax({
	         type: "POST",
	         url: url,
	         data: param,
	         dataType: "json",
	         success: function(responseData){
				if (responseData["status"] == "success"){
					console.log("ajax保存成功");
				} else if (responseData["status"] == "fail"){
					console.log("ajax保存失败:"+responseData["message"]);
				}else if(responseData["status"] == "error"){
					console.log("ajax保存失败:"+responseData["message"]);
				}
	         }
	     })
	}
	
	function doRefresh($thisItem,tzstid){
		//如果存在跳转试题id
		if(tzstid){
			//找到那个题目的item，并隐藏，其余的item都显示
			var $nextItem = $thisItem.next(".item");
			while($nextItem.size() > 0){
				var stid = $nextItem.attr("stid");
				if(tzstid != stid){
					//隐藏
					$nextItem.hide("slow");
					//清空选择项
					//如果下一题就是需要跳转的试题，则不需要清空填入的选项
					var $radioOrCheckboxs = $nextItem.find(":radio,:checkbox");
					$radioOrCheckboxs.attr("checked",false);
					//清空text标签或textarea
					var $texts = $nextItem.find(":text");
					$texts.val("");
					var $textareas = $nextItem.find("textarea");
					$textareas.text("");
					//如果是必填，则去掉require属性
					disableRequireAttr($nextItem);
					
					removeStFinish($nextItem);
					//删除已经提交的试题数据
					deleteYhdjStxx_ajax($nextItem);
					
					$nextItem = $nextItem.next(".item");
					
				}else{
					$nextItem.show("slow");
					var $nextAllItem = $nextItem.nextAll(".item");
					$nextAllItem.show("slow");
					enableRequireAttr($nextAllItem);
					
					break;
				}
			}
		}else{
			//如果不存在跳转试题id，则后面的item全部显示
			//这里，如果后面的item有被选择的，那么只能展示到这里
			var $nextItems = $thisItem.nextAll(".item");

			if($nextItems.size() > 0){
				//是否提跳过不处理
				var skip = false;
				//上一个选中的试题将要跳转的stid
				var tzstid = null;
				
				for(var i = 0;i< $nextItems.size() ;i++){
					
					var $item = $($nextItems.get(i));
					var stid = $item.attr("stid");
					
					if(skip){
						if(stid != tzstid){
							skip = true;
						}else{
							skip = false;
							tzstid = null;
						}
					}else{
						$item.show("slow");
						enableRequireAttr($item);
						
						var $radio = $item.find(":radio:checked");
						var hasChoose = $radio.size() > 0;
						if(hasChoose){
							tzstid = $radio.attr("tzstid");
							skip = true;
						}
					}
				}
			}
		}
		//如果这个题目是单项组合题，那么需要清空text的内容
		if($thisItem.data("issue")=="dxzhe"){
			var $text = $thisItem.find(":text");
			$text.val("");
		}
	}
	
	//点击单选组合题
	//点击多项组合题
	$(document).off('click','.item[data-issue="dxxz"] :radio , .item[data-issue="dxzhe"] :radio').on('click','.item[data-issue="dxxz"] :radio , .item[data-issue="dxzhe"] :radio',function(){
		//点击类型为1或类型为2的选项时，准备隐藏下一题目
		var $radio = $(this);
		var tzstid = $radio.attr("tzstid");
		var $thisItem = $(this).closest(".item");
		addStFinish($thisItem);
		saveYhdjStxx_ajax($thisItem);
		doRefresh($thisItem,tzstid);
	}).off('click','#submitButton').on('click','#submitButton',function(){
		//检查所有必答题是否全部回答
		var acceptSubmit = $('[data-toggle*="validation"]').trigger("validation");
		if(acceptSubmit){
			submitForm("submitAjaxForm",function(responseData,statusText){
				if (responseData["status"] == "success"){
					$.success(responseData["message"] ,function() {
						window.location.reload();
					});
				} else if (responseData["status"] == "fail"){
					$.error(responseData["message"] );
				}else if(responseData["status"] == "error"){
					$.error(responseData["message"] );
				}
			});
		}
	}).off('change','.item[data-issue="duxxz"] :checkbox').on('change','.item[data-issue="duxxz"] :checkbox',function(){
		//点击多选选择题
		var $thisItem = $(this).closest(".item");
		var stid = $thisItem.attr("stid");
		var maxXxgs = $thisItem.attr("kxgs");
		var checkedbox = $thisItem.find(":checkbox:checked");
		var $checkbox = $(this);
		if(checkedbox.size() > maxXxgs){
			alert("此试题最多只能选择"+ maxXxgs +"项");
			setTimeout(function(){
				$checkbox.removeAttr("checked");
			},10);
			return;
		}
		//右侧排序框
		var sfyxpx = $thisItem.attr("sfyxpx");
		if("1" == sfyxpx){
			var $sortDiv = $thisItem.find(".sort_div");
			var $ul = $sortDiv.find("ul");
			var $lis = $ul.find("li");
			var addCheck = ($checkbox.attr("checked") == "checked");
			if(addCheck){//add
				var needAdd = true;
				var i = 0;
				var xxid_checked = $checkbox.attr("xxid");
				for(i=0;i<$lis.length;i++){
					var $l = $($lis.get(i));
					var xxid = $l.attr("xxid");
					if(xxid == xxid_checked){
						needAdd = false;
					}
				}
				if(needAdd){
					var $label = $checkbox.next();
					var text = $label.html();
					var html = "<li xxid='"+ xxid_checked +"'><input type='hidden' name='hdsx_"+ xxid_checked +"' value='0'/>"+ text +"</li>"
					$ul.append(html);
				}
				
				sortPageHdsx($ul);
				
			}else{//remove
				var i = 0;
				var xxid_unchecked = $checkbox.attr("xxid");
				for(i=0;i<$lis.length;i++){
					var $l = $($lis.get(i));
					var xxid = $l.attr("xxid");
					if(xxid == xxid_unchecked){
						$l.remove();
					}
				}
			}
			var $newLis = $ul.find("li");
			if($newLis.length < 1){
				$sortDiv.hide();
			}
			if($newLis.length >= 1){
				$sortDiv.show();
			}
		}
		if(checkedbox.size() > 0){
			addStFinish($thisItem);
		}else{
			removeStFinish($thisItem);
		}
		saveYhdjStxx_ajax($thisItem);
	}).off('change','.item[data-issue="duxzh"] :checkbox').on('change','.item[data-issue="duxzh"] :checkbox',function(){
		//点击多选组合题
		var $thisItem = $(this).closest(".item");
		var checkedbox = $thisItem.find(":checkbox:checked");
		if(checkedbox.size() > 0){
			addStFinish($thisItem);
		}else{
			removeStFinish($thisItem);
		}
		var maxXxgs = $thisItem.attr("kxgs");
		var checkedbox = $thisItem.find(":checkbox:checked");
		var $checkbox = $(this);
		if(checkedbox.size() > maxXxgs){
			alert("此试题最多只能选择"+ maxXxgs +"项");
			setTimeout(function(){
				$checkbox.removeAttr("checked");
			},10);
			return;
		}
		saveYhdjStxx_ajax($thisItem);
	}).off('change','.item[data-issue="dxzhe"] :text,.item[data-issue="duxzh"] :text').on('change','.item[data-issue="dxzhe"] :text,.item[data-issue="duxzh"] :text',function(){
		var $text = $(this);
		var $thisItem = $(this).closest(".item");
		
		var text = $text.val();
		if(text){
			//填入了文本，则需要清空选项，并展示后面所有的题目item
			var $radios = $thisItem.find(":radio");
			$radios.attr("checked",false);
			addStFinish($thisItem);
			//如果是必答题，则移除require属性
			disableRequireAttr($thisItem);
			
			saveYhdjStxx_ajax($thisItem);
		}else{
			removeStFinish($thisItem);
			saveYhdjStxx_ajax($thisItem);
			enableRequireAttr($thisItem);
		}
		var $nextItems = $thisItem.nextAll(".item");
		$nextItems.show("slow");
		enableRequireAttr($nextItems);
	}).off('change','.item[data-issue="wbt"] :input').on('change','.item[data-issue="wbt"] :input',function(){
		//文本题失去焦点事件
		var $thisItem = $(this).closest(".item");
		var val = $(this).val();
		if(val){
			addStFinish($thisItem);
		}else{
			removeStFinish($thisItem);
		}
		saveYhdjStxx_ajax($thisItem);
	}).off('click','.sort_div_left li').on('click','.sort_div_left li',function(){
		$(this).addClass("sort_choose");
		$(this).siblings().removeClass("sort_choose");
	}).off('click','.up').on('click','.up',function(){
		//多选题向上移动
		var $this = $(this);
		var $lis = $this.parent().prev().find("li");
		var $li = $lis.filter(".sort_choose");
		var $prev = $li.prev();
		$prev.before($li);
		var $newUl = $li.parent();
		sortPageHdsx($newUl)
		ajaxSubmitSort($newUl);
	}).off('click','.down').on('click','.down',function(){
		//多选题向下移动
		var $this = $(this);
		var $lis = $this.parent().prev().find("li");
		var $li = $lis.filter(".sort_choose");
		var $next = $li.next();
		$next.after($li);
		var $newUl = $li.parent();
		sortPageHdsx($newUl)
		ajaxSubmitSort($newUl);
	});
	loadDxSort();
	//加载多选题的排序
	function loadDxSort(){
		var $items = $(".item[data-issue='duxxz']");
		var i = 0;
		for(i=0;i<$items.length;i++){
			var $thisItem = $($items.get(i));
			var sfyxpx = $thisItem.attr("sfyxpx");
			if("1" == sfyxpx){
				var $checkboxs = $thisItem.find(":checkbox");
				
				var $sortDiv = $thisItem.find(".sort_div");
				var $ul = $sortDiv.find("ul");
				var stid = $ul.attr("stid");
				var dxSortedArray = queryDxSort(stid);
				var k = 0;
				if(dxSortedArray.length > 0){
					for(k=0;k<dxSortedArray.length;k++){
						var dx = dxSortedArray[k];
						var j= 0;
						for(j=0;j<$checkboxs.length;j++){
							var $check = $($checkboxs.get(j));
							var xxid = $check.attr("xxid");
							var $label = $check.next();
							var text = $label.html();
							if(dx.xxid == xxid){
								$ul.append("<li xxid='" +xxid+ "'>"+ text +"</li>")
							}
						}
					}
					$sortDiv.show();
				}
			}
		}
	}
	
	function queryDxSort(stid){
		var result = new Array();
		var wjid = $("#wjid").val();
		var url = contextPath +"/wjdc/wjgl/queryDxSort.zf";
	     $.ajax({
	         type: "POST",
	         url: url,
	         data: {
	        	 "wjid":wjid,
	        	 "stid":stid,
	         },
	         async:false,
	         dataType: "json",
	         success: function(responseData){
				var list = responseData;
				result = list;
	         }
	     })
	     return result;
	}
	
	function sortPageHdsx($ul){
		var $lis = $ul.find("li");
		var size = $lis.length;
		if(size <= 0){
			return;
		}
		for(var i = 0;i<size;i++){
			var index = i + 1;
			var $li = $($lis.get(i));
			$li.find("input").val(index);
		}
	}
	
	//提交排序
	function ajaxSubmitSort($newUl){
		var stid = $newUl.attr("stid");
		var $lis = $newUl.find("li");
		var wjid = $("#wjid").val();
		var xxids = new Array();
		var i = 0;
		for(i=0;i<$lis.length;i++){
			var $xx = $($lis.get(i));
			var xxid = $xx.attr("xxid");
			xxids.push(xxid);
		}
		var url = contextPath +"/wjdc/wjgl/ajaxSubmitSort.zf";
	     $.ajax({
	         type: "POST",
	         url: url,
	         data: {
	        	 "wjid":wjid,
	        	 "stid":stid,
	        	 "xxids":xxids.join(",")
	         },
	         dataType: "json",
	         success: function(responseData){
				if (responseData["status"] == "success"){
					console.log("ajax保存成功");
				} else if (responseData["status"] == "fail"){
					console.log("ajax保存失败:"+responseData["message"]);
				}else if(responseData["status"] == "error"){
					console.log("ajax保存失败:"+responseData["message"]);
				}
	         }
	     })
	}
	
	//将隐藏的题目的必填属性等移除
	var $hiddenItem = $items.filter(":hidden");
	console.log("隐藏的题目:"+$hiddenItem.size());
	if($hiddenItem.size() > 0){
		for(var i=0;i<$hiddenItem.size();i++){
			var hiddenItemDom = $hiddenItem.get(i);
			var $hiddenItemDom = $(hiddenItemDom);
			disableRequireAttr($hiddenItemDom);
		}		
	}
});