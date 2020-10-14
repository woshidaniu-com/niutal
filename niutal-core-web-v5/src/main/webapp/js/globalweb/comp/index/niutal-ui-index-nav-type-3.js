jQuery(function($) {
	
	//计算tabs高度
	var pageHeight = $(window).height() - $('#tabs').outerHeight(true) - $('#footer').outerHeight(true) - $('#navbar').outerHeight(true);
	var boxHeight = $(window).height() - $('#footer').outerHeight(true);
	$('#boxed').height(boxHeight);
	
	$(window).resize(function() {
		var boxHeight = $(window).height() - $('#footer').outerHeight(true);
		$('#boxed').height(boxHeight);
		
	});
	
	//配置
	var max_height = window.innerHeight - 60 - 35;
	var left_width = 115;
	var mainnav_base_width = 190;
	var slideSpeed = 150;
	
	var topMenuGnmkdm = [];
	
	// 初始化一级菜单
	function initTop(){
		
		$.post(_path + "/func/nav/topMenuList.zf", {
			
		}, function(data) {
			if($.founded(data)){
				var html = [];
				$.each(data || [], function(i, item){
					html.push('<li class="tgl-menu-btn" data-gnmkdm="' + item["gnmkdm"] + '">');
						html.push('<a href="javascript:void(0);">');
							html.push('<i class="' + (item["tblj"]  || "demo-pli-layout-grid") + '"></i>');
							html.push('<span>' + item["gnmkmc"] + '</span>');
						html.push('</a>');
					html.push('</li>');
					
					topMenuGnmkdm.push(item["gnmkdm"]);
				});
				// 一级菜单较多的系统，不将一级菜单放置在
				$('#top-nav').empty().html(html.join(""));
			}
		}).done(function() {
			
			initAllLeft();
			
			$('#tabs').tabs({
				sortable: true,
				contextmenu:true,
				monitor: '#last_panel',
			});
			try {
				$('#mainnav-menu').metisMenu({ toggle: true });
				//绑定导航相关事件
				$.niftyNav('bind');
				$.niftyAside('bind');
				
	        }catch(err) {
	            console.error(err.message);
	        }
	        //事件
	        $(document).trigger("menuReady");
	        startSetMainnavLeft();
	        startCondTimer();
		});
	}
	initTop();
	
	function buildURL(requestURL,data){
		data = data || {};
		requestURL = _path +  requestURL;
		//在url上追加
		if(requestURL.indexOf("?") > -1){
			requestURL = requestURL + "&th=" + pageHeight + "&gnmkdmKey=" + data["gnmkdm"];
		}else{
			requestURL = requestURL + "?th=" + pageHeight + "&gnmkdmKey=" + data["gnmkdm"];
		}
		//alert("requestURL:" + requestURL);
		return requestURL;
	};
	
	function initAllLeft(){
		
		var $last_panel = $("#last_panel");
		$last_panel.empty();
		
		var i=0;
		for(i=0;i<topMenuGnmkdm.length;i++){
			
			var gnmkdm = topMenuGnmkdm[i];
			console.log("加载顶级菜单:"+gnmkdm+"下的菜单");
			//一级菜单在顶部
			$.ajax({
				  type: 'POST',
				  url: _path + "/func/nav/childMenuTreeList.zf",
				  data: {"parent":gnmkdm},
				  async:false,
				  success:function(data){
					  var $mainnav = $("<div id='mainnav' class='mainnav' style='width: "+ mainnav_base_width +"px;position:relative;display:none'></div>");
					  $last_panel.append($mainnav);
					  
					  var $ul = $("<ul id='mainnav-menu' class='list-group' style='max-height:"+ max_height +"px;overflow-y:auto'></ul>");
					  $mainnav.append($ul);
					  
					  if(!$.founded(data)){
						  return;
					  }
					  
					  var html = [];
					  $.each(data || [], function(i, m){
						  var tblj = "fa fa-th-list";
						  if(m["tblj"]){
							  tblj = m["tblj"];
						  }
						  var $li = $("<li class='first'><a href='javascript:void(0);'><i class='"+ tblj +"'></i><span class='menu-title'>"+ m["gnmkmc"] +"</span><i class='arrow'></i></a></li>");
						  $ul.append($li);
						  if(m.children){
							  var $children_ul = $("<ul class='collapse' aria-expanded='false'></ul>");
							  $li.append($children_ul);
							  
							  var j=0;
							  for(j=0;j<m.children.length;j++){
								  var menu = m.children[j];
								  var $children_ul_li = $("<li><a href='javascript:void(0);' title='"+ menu["gnmkmc"] +"' data-addtab='"+ menu["gnmkdm"] +"' data-src='"+ buildURL(menu["dyym"], menu) +"' data-tab-layout='default' data-blank-layout='default-tab'>"+ menu["gnmkmc"] +"</a></li>");
								  $children_ul.append($children_ul_li);
							  }
						  }
					  });
				  }
			});
		}
	}
	
	
	var condTimer;
	function startCondTimer(){
		var $mainnav = $(".last_panel > .mainnav");
		$mainnav.hover(function(){
			if(condTimer){
				clearTimeout(condTimer);			
			}
		},function(e){
			var $this = $(this);
			condTimer = setTimeout(function () {
				$this.slideUp(200,"swing");
			},150);
		});
	}
	//设置二级菜单所在的区域的相对左边的距离
	function startSetMainnavLeft(){
		var $lastPanel = $(".last_panel");
		var $all_mainnav = $lastPanel.find(".mainnav");
		var i=0;
		for(i=0;i<$all_mainnav.length;i++){
			var $mainnav = $($all_mainnav.get(i));
			var left = i * left_width;
			$mainnav.css({"left":left});
		}
	}
	
	$(document).off('click', '.list-group > li').on('click', '.list-group > li', function(e) {//点击某一个二级菜单，展开下面的三级菜单列表
		var $this = $(this);
		var $this_ul = $this.find("ul");
		
		var $other = $this.siblings();
		var $other_ul = $other.find("ul");
		
		if(!$this.hasClass("active")){
			
			$other.removeClass("active");
			$other_ul.removeClass("in");
			
			$this.addClass("active");
			$this_ul.addClass("in");
			
		}else{
			$this.removeClass("active");
			$this_ul.removeClass("in");
		}
	}).off('click', '.top-nav > li').on('click', '.top-nav > li', function(e) {//点击顶层第一级菜单，展开下面的菜单列表
		
		var $this = $(this);
		var index = $this.index();
		
		var $all_mainnav = $(".last_panel>.mainnav");
		var $current = $all_mainnav.eq(index);
		var $other = $current.siblings();
		
		$other.slideUp(slideSpeed);
		if($current.is(":visible")){
			$current.slideUp(slideSpeed);
		}else{
			$current.slideDown(slideSpeed);
		}
	}).off('click', '.collapse > li').on('click', '.collapse > li', function(e) {//点击某个具体菜单，隐藏菜单面板
		
		var $all_mainnav = $(".last_panel>.mainnav:visible");
		$all_mainnav.slideUp(slideSpeed);
		
	}).off('click','#xgmm').on('click','#xgmm',function(){
		$.showDialog(_path + "/xtgl/yhgl/xgMm.zf", '修改密码', $.extend({}, modifyConfig, {// 首页密码找回按钮事件
			"width": "500px",
			fullScreen: false
		}));
	});
});