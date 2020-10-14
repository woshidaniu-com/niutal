<!DOCTYPE html>
<html>
	<head>
	<title></title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="Copyright" content="woshidaniu" />	
	<link rel="icon" href="${base}/logo/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="${base}/logo/favicon.ico" type="image/x-icon" />
	[#include "/globalweb/head/niutal-ui-meta.ftl" /]
	[#include "/globalweb/head/niutal-ui-head.ftl" /]
	
	[#include "/globalweb/head/bsTable.ftl" /]
	[#include "/globalweb/head/niutal-core-dc.ftl" /]
	<script type="text/javascript" src="${base}/js/tjcx/comp/tjcxUtils.js?ver=${messageUtil("niutal.cssVersion")}"></script>
	<script type="text/javascript" src="${base}/js/tjcx/comp/tjcxPublic.js?ver=${messageUtil("niutal.cssVersion")}"></script>
	<script type="text/javascript" src="${base}/js/tjcx/comp/tjcxlb.js?ver=${messageUtil("niutal.cssVersion")}"></script>
	<style>
		#yxtj a{
			font-size:11px;
		}
	</style>
	</head>
	<body>		
    <form action="/niutal/tjcx/tjcx/export.zf" method="post" id="form1">
     	<input type="hidden" id="gnmk" name="gnmk" value="${gnmk}"/>
    	<input type="hidden" id="xmdm" name="xmdm" value="${xmdm}"/>
     	<input type="hidden" id="gltj" name="gltj" value="${gltj}"/>
     	<input type="hidden" id="gltjmc" name="gltjmc" value="${gltjmc}"/>
     	<input type="hidden" id="allGltj" name="allGltj" value="${gltj}">
       	<input type="hidden" id="newGltj" name="newGltj" value="">
       	<input type="hidden" id="tjlbFhbs" name="tjlbFhbs" value="1"/>
     	<input type="hidden" id="zdxsms" name="zdxsms" value="2"/>
        <input type="hidden" id="czy" name="czy" value="${czy}"/>
        <input type="hidden" id="tjcxlbxq" name="tjcxlbxq" value="${tjcxlbxq}"/>
        <input type="hidden" id="kzszid" name="kzszid" value="${kzszid}"/>
    </form>
       	
       	<div style="width: 100%; padding: 0px; margin: 0px;" id="bodyContainer">
			<div class="container container-func" style="width:100%;" style="width: 100%; min-height: 250px;">
				<div class="row">
					<div class="btn-group pull-right hidden-print" id="buttonbox">
						<a href="javascript:void(0);" id="btn_dc" class="btn btn-default">
						<span class="glyphicon glyphicon-download" aria-hidden="true"></span>
						导出</a>
						<!--<a href="javascript:void(0);" id="btn_sx" class="btn btn-default">
						<span class="glyphicon glyphicon-print" aria-hidden="true"></span>
						刷新</a>-->
					</div>
					
					
					<div class="panel panel-gray hidden-print gltj1_2" id="oldYxtjDiv" style="display:none;margin-top:10px" >
		        	<div class="panel-heading"><h3 class="panel-title" style="color:#fff">已选条件</h3></div>
			        <div class="panel-body">
			        	 <ul id="yxtj" class="tag-list tag-list1"></ul>
			        </div>
			    </div>
			    
			    <div class="panel panel-gray hidden-print tjxm_2" id="curTjxmDiv">
			   	 	<div class="panel-heading">
			   	 		<h3 class="panel-title" style="color:#fff">过滤条件<samll">  (条件块内的各字段为“并且”关系，条件块间为“或者”关系)</samll>
			   	 			<a href="javascript:;" class="pull-right" onclick="addCxzdTjk();" id="tjxmAddTjk"  style="color:#fff">增加条件块</a>
			   	 		</h3>
			   	 	</div>
			        <div class="panel-body gltj" id="cxzdDiv" ></div>
			        <div class="" style="text-align:right">
						<a href="javascript:;" class="tj btn btn-default " id="tjBtn" onclick="tjcx();">查  询</a>
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
			</div>
			
			<div class="row" id="data-row">
				<table id="tabGrid"></table>
			</div>
		</div>
	</div>
	</body>
</html>
