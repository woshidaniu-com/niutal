$(function(){
	
	var yhsjfwInitFullscreen = $("#yhsjfwInitFullscreen").val();
	if(yhsjfwInitFullscreen){
		$(".bootbox-full").click();		
	}
	
	//根据数据代码获得拼音列表
	function getPinyinListBySjdm(sjdm){
		var i=0;
		for(i=0;i<sjfwList.length;i++){
			var sjdm_str = sjfwList[i].sjdm;
			if(sjdm_str == sjdm){
				return sjfwList[i].pinyingList;
			}
		}
		return null;
	}

	//初始化拼音搜索条件
	function init_param_pinyin(){
	
		$("#param_pinyin").val("");
		$("#param_name").val("");
		$("#param_select_type").val("");
	}
	
	//点击选择或取消选择
	function ajaxSubmitCheck($span){
		var sjdm = $span.attr("sjdm");
		var pinyin = $span.attr("pinyin");
		var value = $span.attr("value");
		var check = !$span.hasClass("active");
		//check = true 说明需要选中
		// check = false 说明需要删除
		var id = $("#param_id").val();
		$.ajax({
			async:false,
			type: "POST",
			url:"submitCheckYhsjfw.zf",
			dataType:"json",
			data:{
				"id":id,
				"sjdm":sjdm,
				"value":value,
				"check":check
			},
			success:function(data){
				if(data.status == "success"){
					$span.toggleClass("active");
				}else{
					alert(data.message);
				}
			}
		});
	};
	
	$(document).off('click', '#mainTagCnt>span.choose-item').on('click', '#mainTagCnt>span.choose-item', function(event) {
		event.stopPropagation();
		var $this = $(this);
		ajaxSubmitCheck($this);
	});
	
	//搜索
	function search(pageNo){
		var pageSize = $("#yhsjfwPageSize").val();
		var $mainTagCnt = $("#mainTagCnt");
		var $mainPageUl = $("#mainPage>ul");
		var id = $("#param_id").val();
		var param_sjdm = $("#param_sjdm").val();
		var param_pinyin = $("#param_pinyin").val();
		if(param_pinyin == "all"){
			param_pinyin = "";
		}
		var param_select_type = $("#param_select_type").val();
		var param_name = $("#param_name").val();
		var result = null;
		var query_params ={
				"id":id,
				"sjdm":param_sjdm,
				"pinyin":param_pinyin,
				"selectType":param_select_type,
				"name":param_name,
				"pageNo":pageNo,
				"pageSize":pageSize
		};
		$.ajax({
			async:false,
			type: "POST",
			url:"ajaxLoadYhsjfw.zf",
			dataType:"json",
			data:query_params,
			beforeSend:function(){
				//清空原来的
				$mainTagCnt.empty();
				//清空页码
				$mainPageUl.empty();
			},
			success:function(data){
				var datalist = data.list;
				for(i=0;i<datalist.length;i++){
					var html = "";
					if(datalist[i].selected){
						var html = "<span class='choose-item active' sjdm='"+ param_sjdm +"' pinyin='"+ datalist[i].pinyin +"' value='"+ datalist[i].key +"'>"+ datalist[i].value +"</span>";		
						var $newSpan = $(html);
						$mainTagCnt.append($newSpan);
					}else{
						var html = "<span class='choose-item' sjdm='"+ param_sjdm +"' pinyin='"+ datalist[i].pinyin +"' value='"+ datalist[i].key +"'>"+ datalist[i].value +"</span>";							
						var $newSpan = $(html);
						$mainTagCnt.append($newSpan);
					}
				}
				
				var page_no = data.pageNo;
				var page_size = data.pageSize;
				var totalPage = data.totalPage;
				
				//首页跳转
				var $firstPage = $("<li><a href='javascript:(0)' class='firstPage'>首页</a></li>");
				var $wrappfirstPage = $firstPage.wrap("li");
				$mainPageUl.append($wrappfirstPage);
				$firstPage.click(function(){
					search(1);
				});
				
				//上一页跳转
				var $prePage = $("<li><a href='javascript:(0)' class='prePage'>上一页</a></li>");
				var $wrappprePage = $prePage.wrap("li");
				$mainPageUl.append($wrappprePage);
				if(page_no <= 1){
					$prePage.attr("disabled","disabled");					
				}else{
					$prePage.click(function(){
						search(page_no-1);
					});
				}
				
				//下一页跳转
				var $nextPage = $("<li><a href='javascript:(0)' class='nextPage'>下一页</a></li>");
				var $wrappnextPage = $nextPage.wrap("li");
				$mainPageUl.append($wrappnextPage);
				if(page_no+1 > totalPage){
					$nextPage.attr("disabled","disabled");					
				}else{
					$nextPage.click(function(){
						search(page_no+1);
					});
				}
				
				//末页跳转
				var $lastPage = $("<li><a href='javascript:(0)' class='lastPage'>末页</a></li>");
				var $wrapplastPage = $lastPage.wrap("li");
				$mainPageUl.append($wrapplastPage);
				$lastPage.click(function(){
					search(totalPage);
				});
				
				//设置侧部页面跳转
				var $mainPage = $("#mainPage");
				//共x页
				var $totalPage = $mainPage.find(".totalPage");
				$totalPage.html("共"+data.totalPage+"页");
				
				//单独页面跳转列表
				//1.若是当前页，蓝色标注
				//2.最多出现5个跳转页
				//3.当前页的最大前2页，和当前页的最大后两页
				//清空之前的
				$mainPage.find(".page_no").parent().remove();
				var $prePage = $mainPage.find(".prePage");
				
				//页码数组
				var pageArray = page_label(page_no,totalPage,5);
				
				for(i=0;i < pageArray.length;i++){
					var page_index = pageArray[i];
					//
					var html = "";
					if(page_no == page_index){
						html = "<li><a href='javascript:(0)' class='page_no active'>"+ page_index +"</a></li>";							
					}else{
						html = "<li><a href='javascript:(0)' class='page_no'>"+ page_index +"</a></li>";
					}
					var $newPage = $(html);
					var $wrappNewPage = $newPage.wrap("li");
					if(i==0){
						$prePage.parent().after($wrappNewPage);
					}else{
						$mainPage.find(".page_no:last").parent().after($wrappNewPage);
					}
					$newPage.click(function(){
						var $this = $(this);
						var page = $this.text();
						search(page);
					});
				}
			}
		});
	}
	
	//页码计算函数
	//page_no 页码
	//total_page 总页码
	//limit 限制个数
	function page_label(page_no,total_page,limit){
	
		var pageArray = new Array();
		if(total_page <= limit) {
			var i = 0;
			for(i=1;i<=total_page;i++) {
				pageArray.push(""+i);
			}
		}else {
			if(page_no > limit) {//其余下面的页
			
				var label_count = Math.floor(total_page / limit);
				var last_label_count = total_page % limit;
				if(last_label_count > 0) {
					if(page_no < label_count * limit) {
						var i = page_no;
						var last = limit;
						while(i <= total_page && last > 0) {
							pageArray.push(""+i);
							i++;
							last --;
						}
					}else {
						var i = label_count*limit+1;
						var last = limit;
						while(i <= total_page && last > 0) {
							pageArray.push(""+i);
							i++;
							last --;
						}
					}
				}else {
					var i = page_no;
					var last = limit;
					while(i <= total_page && last > 0) {
						pageArray.push(""+i);
						i++;
						last --;
					}
				}
			}else {//第一页
				var i = 0;
				for(i=1;i<= limit;i++) {
					pageArray.push(""+i);
				}
			}
		}
		return pageArray;
	}

	//点击类型
	$("#nav_type a").click(function(){
		var $this = $(this);
		
		//数据代码同步查询条件表单
		var sjdm = $this.data("sjdm");
		$("#param_sjdm").val(sjdm);
		
		//加载拼音列表
		var pinyinList = getPinyinListBySjdm(sjdm);
		if(pinyinList){
			var $nav_pinyin = $("#nav_pinyin");
			$nav_pinyin.empty();
			$nav_pinyin.append("<li><a href='#' role='tab' data-toggle='tab' data-pinyin='all' data-sjdm='"+ sjdm +"'>全部</a></li>");
			var i=0;
			for(i=0;i<pinyinList.length;i++){
				var pinyin = pinyinList[i];
				$nav_pinyin.append("<li><a href='#' role='tab' data-toggle='tab' data-pinyin='"+ pinyin +"' data-sjdm='"+ sjdm +"'>"+ pinyin +"</a></li>");
			}
			
			//点击拼音
			$("#nav_pinyin a").click(function(){
			
				var $this = $(this);
				
				init_param_pinyin();
				
				var pinyin = $this.data("pinyin");
				$("#param_pinyin").val(pinyin);
				$("#searchButton").click();
			});		
			
			//点击第一个拼音
			$("#nav_pinyin a:first").click();				
		}
	});
	
	//点击搜索按钮
	$("#searchButton").click(function(){
		search(1);
	});
	
	//点击第一个类型
	$("#nav_type a:first").click();
});