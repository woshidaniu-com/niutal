<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Copyright" content="woshidaniu" />
<link rel="icon" href="${base}/logo/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${base}/logo/favicon.ico"
	type="image/x-icon" />
<script type="text/javascript">
	//全局变量
	var _path = "${base}";
	var _systemPath = "${base}";
	var _stylePath = "${messageUtil("system.stylePath")}";
</script>

[#include "/globalweb/head/niutal-ui-meta.ftl" /]
[#include "/globalweb/head/niutal-ui-head.ftl" /]

<script type="text/javascript" src="${base}/js/tjcx/comp/tjcxUtils.js?ver=${messageUtil("niutal.jsVersion")}"></script>
<script type="text/javascript" src="${base}/js/tjcx/comp/tjlb.js?ver=${messageUtil("niutal.jsVersion")}"></script>
<script type="text/javascript" src="${base}/js/tjcx/comp/tjcxPublic.js?ver=${messageUtil("niutal.jsVersion")}"></script>
<script type="text/javascript" src="${base}/js/tjcx/comp/tjlbForTb.js?ver=${messageUtil("niutal.jsVersion")}"></script>
<script type="text/javascript" src="${base}/js/tjcx/comp/tjlbForGltj.js?ver=${messageUtil("niutal.jsVersion")}"></script>

<style>
#tjlbDiv table {
	border: 1px solid #999;
}

#tjlbDiv table tr th {
	text-align: center;
	vertical-align: middle;
	font-weight: bold;
}

#tjlbDiv table tr td, th {
	white-space: nowrap;
}

#tjlbDiv table tr th {
	padding: 5px;
	border: 1px solid #999;
}

#tjlbDiv table tr td {
	padding: 5px;
	border: 1px solid #999;
	font: normal 15px/15px "宋体";
	text-align: right
}

#tjlbDiv table tr td.tjlbleft {
	text-align: left
}

#tjlbDiv table tr td.tjlbtotal {
	font: bold 15px/15px "宋体";
}

#tip {
	display: none;
	text-align: center;
	position: absolute;
	z-index: 100;
	width: 100%;
	height: 100%;
	background: transparent;
	background-color: #eee;
	opacity: 0.8;
	filter: alpha(opacity = 80);
	top: 0;
	padding-top: 250px
}

#tjlbDiv table tr td a {
	text-decoration: underline;
}

#tjlbDiv table tr td a:link {
	color: #0f5dc2
}

#tjlbDiv table tr td a:hover {
	color: #000
}

.tjxm_4 label {
	color: #155fbe;
	float: left;
	font-weight: bold;
	margin-top: 4px;
	margin-bottom: 4px;
	margin-right: 20px;
}

#yxtj a{
	font-size:11px;
}
</style>
</head>

<body>
  	<OBJECT id=wb height=0 width=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT> 
  	
  	<form action="${base}/niutal/tjcx/tjbb/export.zf" method="post" id="form1" >
     	<input type="hidden" id="bbhxl" name="bbhxl" value="${bbhxl}"/>
     	<input type="hidden" id="bbzxl" name="bbzxl" value="${bbzxl}"/>
     	<input type="hidden" id="tsx" name="tsx" value="${tsx}"/>
     	<input type="hidden" id="oldGltj" name="oldGltj" value="${gltj}">
       	<input type="hidden" id="newGltj" name="newGltj" value="">
    	<input type="hidden" id="tableHtml" name="tableHtml" value="">
     	<input type="hidden" id="zdxsms" name="zdxsms" value="2"/>
        <input type="hidden" id="czy" name="czy" value="${czy}"/>
    </form>
    
    <form action="${base}/niutal/tjcx/tjtb/tjtb.zf" method="post" id="form2" target="_blank" >
    	<input type="hidden" id="hxlHead" name="hxlHead" value="">
    	<input type="hidden" id="hxlContent" name="hxlContent" value="">
    	<input type="hidden" id="hxlContentCode" name="hxlContentCode" value="">
    	<input type="hidden" id="zxlContent" name="zxlContent" value="">
    	<input type="hidden" id="zxlContentCode" name="zxlContentCode" value="">
    	<input type="hidden" id="hxlhjContentJson" name="hxlhjContentJson" value="">
     	<input type="hidden" id="zxlhjContentJson" name="zxlhjContentJson" value="">
     	<input type="hidden" id="contentJson" name="contentJson" value="">
      	<input type="hidden" id="tjxJson" name="tjxJson" value="">
     	<input type="hidden" id="zxlHead" name="zxlHead" value="">
      	<input type="hidden" id="zxlHeadCode" name="zxlHeadCode" value="">
     	<input type="hidden" id="gnmk" name="gnmk" value="${gnmk}"/>
    	<input type="hidden" id="xmdm" name="xmdm" value="${xmdm}"/>
     	<input type="hidden" id="gltj" name="gltj" value="${gltj}"/>
      	<input type="hidden" id="gltjmc" name="gltjmc" value="${gltjmc}"/>
        <input type="hidden" id="curBbl" name="curBbl" value=""/>
       	<input type="hidden" id="curBblmc" name="curBblmc" value=""/>
    </form>
  	
  	
	<div id="bodyContainer">
		<div class="container container-func" style="width:100%;">
			<div class="row">
				<div class="col-md-12">
				<!-- 加载当前菜单栏目下操作     -->
				<div class="btn-group pull-right hidden-print" id="buttonbox">
					<a href="javascript:void(0);" id="btn_dc" class="btn btn-default">
						<span class="glyphicon glyphicon-download" aria-hidden="true"></span>
						导出</a>
					<a href="javascript:void(0);" id="btn_dy" class="btn btn-default">
						<span class="glyphicon glyphicon-print" aria-hidden="true"></span>
						打印</a>
					<a href="javascript:void(0);" id="btn_tj" class="btn btn-default">
						<span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
						生成图表</a>
						
<!-- 					<a href="javascript:void(0);" id="btn_cx" class="btn btn-default"> -->
<!-- 						<span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span> -->
<!-- 						使用说明</a> -->
				</div>
				<div style="margin-top:50px"></div>
				<div class="panel panel-gray hidden-print gltj1_2" id="oldYxtjDiv" style="display:none;">
		        	<div class="panel-heading">
		        		<h3 class="panel-title fwhite">已选条件</h3>
		        	</div>
			        <div class="panel-body">
			        	 <ul id="yxtj" class="tag-list tag-list1"></ul>
			        </div>
			    </div>
			    
			    <div class="panel panel-gray hidden-print tjxm_2" id="curTjxmDiv">
			   	 	<div class="panel-heading">
			   	 		<h3 class="panel-title" style="color:#fff">过滤条件<samll>  (条件块内的各字段为“并且”关系，条件块间为“或者”关系)</samll>
				   	 		<a href="javascript:;" class="pull-right" onclick="addCxzdTjk();" id="tjxmAddTjk" style="color:#fff">增加条件块</a>
				   	 	</h3>
			   	 	</div>
			        <div class="panel-body gltj" id="cxzdDiv" ></div>
			        <div class="panel-footer" style="text-align:right">
			        	<label class="fwhite">固定总数合计<input type="checkbox" value="1" name="lockInitial"></label>
			        	
			        	  
			        	
						<a href="javascript:;" class="tj btn zf-btn btn-default" id="tjBtn" onclick="tjlb();">统  计</a>
			        </div>
			  	</div>
			  	
			  	<div class="modal inmodal hidden-print" id="myModal" tabindex="-1" role="dialog"
					aria-hidden="true">
					<div class="modal-dialog">
						<div class="panel panel-default animated fadeInRightBig">
							<div class="panel-heading">
								<h3 class="panel-title" id="modalTitle"></h3>
							</div>
							<div class="panel-body" id="modalBody"></div>
							<div class="panel-footer text-right">
								<label class="checkbox-inline float_l padding-t10" id="checkAll">
									<input type="checkbox" onclick="cxzdDivQxBtn(this);"
									id="checkAllBox">全选
								</label>
								<button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>
								<button type="button" class="btn btn-primary"
									onclick="cxzdDivQd(this);return false;">确定</button>
							</div>
						</div>
					</div>
				</div>

				<br/>
				
				
				[#if kzms != null]
					<div id="tjbb_kzms" style="border: solid 1px; border-color: #dbe6f0; padding: 10px 10px 10px 10px;">
							${kzms}
					</div>
					<br/>
			  	[/#if]
				
				<div id="tjlbDiv" style="display:block; overflow-x:scroll;"></div>
			</div>
			</div>		
	   </div>
	</div>

 	
    
		
  	
  	
  	<div id="tip">
 		<img src="${base}/images/tjcx/loading.gif" width="100px" height="100px" />
 	</div>
  </body>
</html>
