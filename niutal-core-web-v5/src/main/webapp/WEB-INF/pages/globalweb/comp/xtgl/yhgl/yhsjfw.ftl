<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<style type="text/css">
			
			.bootbox-body{padding: 0px !important;}
			
			#nav_tabs li{ margin-top: 3px;}
			#nav_tabs li a{ border-top: 3px solid transparent;}
			#nav_tabs li.active a{ border-top:2px solid #0770cd;}
			.nav-tabs>li>a:hover {
			    border: none;
			    border-bottom: 2px solid #4a89dc;
			}
			.nav-tabs li{margin-bottom:0;}
			.nav-tabs li a{margin-right:0;font-weight: normal;border: none;border-bottom: 2px solid transparent;font-size: 12px;}
			.nav_buttom{
				font-size: 12px;
				height: 15px;
    			border-top-style: groove;
    			border-top-width: 1px;
			}
			.nav_buttom ul{
    			display: block;
    			list-style: none;
    			position: relative;
    			left: 100px;
			}
			.nav_buttom .active{
				background:#4a89dc;color:#fff;
			}
			.nav_buttom ul li{
				width:55px;
			    padding: 4px;
				float: left;
			}
			.nav_buttom ul li a{
				width:50px;
				display: block;
				text-align: center;
				border-style: groove;
    			border-width: 1px;
			}
			.tab-cnt{
				height: 200px;
				width:100%;
				padding:5px 0;
			}
			.tab-cnt .choose-item {
				display:inline-block;
				border:1px solid #ddd;
				margin:2px;
				padding:2px 5px;
			    background: #f0f0f0;
    			font-weight: 600;
    			font-size: 12px;
    			cursor:pointer;
			}
			.tab-cnt .choose-item.active{background:#4a89dc;color:#fff;}
			
			.searchInput{
				width: 250px;
			}
			.searchSelect{
				height: 25px;
				width: 60px;
			}
			.searchButton{
    			border-style: solid;
    			border-width: 1px;
    			margin-left: 10px;
    			width: 40px;
    			text-align: center;
    			float: right;
    			padding: 2px;
				cursor:pointer;
				background: #4a89dc;
				color: #fff;
			}
			
			#mainTagCnt{
				overflow-y: auto;
			}
		</style>
	</head>
	<body>
		<input type="hidden" value="${yhsjfwPageSize}" id="yhsjfwPageSize"/>
		<input type="hidden" value="${yhsjfwInitFullscreen}" id="yhsjfwInitFullscreen"/>
		<!-- 查询条件 start -->
		<input type="hidden" value="${ids}" id="param_id"/>
		<input type="hidden" value="" id="param_sjdm"/>
		<input type="hidden" value="" id="param_pinyin"/>
		<input type="hidden" value="" id="param_page_no"/>
		<input type="hidden" value="" id="param_page_size"/>
		<!-- 查询条件 end  -->
		
		<div class="row">
		    <div class="col-sm-12" >
		    	<div class="alert alert-success alert-dismissible" role="alert">授权用户：${ids}</div>
		    </div>
		</div>
		<ul class="nav nav-tabs" id="nav_type">
		 	[#if sjfwList?exists]
		 		[#list sjfwList as sjfw]
		 			<li [#if sjfw_index == 0]class="active"[/#if]>
					  	<a href="#${sjfw.sjdm}" data-toggle="tab" data-sjdm="${sjfw.sjdm}">${sjfw.sjmc}</a>
					</li>
		 		[/#list]
		 	[/#if]
		</ul>
			<div class="row" style="margin-top: 5px;">
				<div class="col-md-12 col-sm-12">
					<!-- Tab panes -->
					<div class="tab-content" id="tabContent">
						<div class="tab-pane chose-tag active" style="overflow-x:hidden; height: 300px;">
							<!-- 第一个ul -->
							<ul class="nav nav-tabs pinyinTag active" data-sjdm="${sjfw.sjdm}" id="nav_pinyin">
						    </ul>
						    <!-- 第二个ul -->
						    <ul class="nav nav-tabs">
						    	<li>
						    		<select class="searchSelect" id="param_select_type">
						    			<option value="">全部</option>
						    			<option value="1">已选</option>
						    			<option value="0">未选</option>
						    		</select>
									<input class="searchInput" id="param_name" value=""></input>
									<div class="searchButton" id="searchButton">搜索</div>
								</li>
						    </ul>
						    <!--填充内容-->
						    <div class="tab-cnt" id="mainTagCnt">
							</div>
							<!-- 第二个nav_buttom -->
							<div class="nav_buttom" id="mainPage">
								<ul class="page">
									<li><a href="javascript:(0)" class="firstPage">首页</a></li>
									<li><a href="javascript:(0)" class="prePage">上一页</a></li>
									<!--
									<li><a href="javascript:(0)" class="page_no">1</a></li>
									 -->
									<li><a href="javascript:(0)" class="nextPage">下一页</a></li>
									<li><a href="javascript:(0)" class="lastPage">末页</a></li>
									<li class="totalPage">共10页</li>
								</ul>
							</div>
				  		</div>
				    </div>
		      	</div>
			</div>
		<script type="text/javascript">
			var sjfwListJson = '${sjfwListJsonArray}';
			var sjfwList = JSON.parse(sjfwListJson) ;
		</script>
		<script type="text/javascript" src="${base}/js/globalweb/comp/xtgl/yhsjfw.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>
