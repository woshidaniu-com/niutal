<!DOCTYPE html>
<html>
	<head>
		[#include "/globalweb/head/niutal-ui-kindeditor.ftl" /]
		[#include "/globalweb/head/niutal-ui-dragsort.ftl" /]
		<script type="text/javascript" src="${base}/js/tjcx/comm/json/json.js?ver=${messageUtil("niutal.jsVersion")}"></script>
		<script type="text/javascript" src="${base}/js/tjcx/comp/tjcxUtils.js?ver=${messageUtil("niutal.jsVersion")}"></script>
		<script type="text/javascript" src="${base}/js/tjcx/comp/tjcxPublic.js?ver=${messageUtil("niutal.jsVersion")}"></script>
		<script type="text/javascript" src="${base}/js/tjcx/comp/tjcx.js?ver=${messageUtil("niutal.jsVersion")}"></script>
		<script type="text/javascript">
			jQuery(function() {
			onShowTjxm();
		});
		</script>
		<style>		
			.tjxm .tjxm_1 .more a {
				width: 25px;
			}	
		</style>				
	</head>
	<body>
	<form action="${base}/niutal/tjcx/tjcx/tjcxlb.zf" method="post" id="form1">
     	<input type="hidden" id="gnmk" name="gnmk" value="${model.gnmk}"/>
    	<input type="hidden" id="xmdm" name="xmdm" value="${model.xmdm}"/>
      	<input type="hidden" id="gltj" name="gltj" value="${model.gltj}"/>
     	<input type="hidden" id="bbhxl" name="bbhxl" value=""/>
     	<input type="hidden" id="bbzxl" name="bbzxl" value=""/>
     	<input type="hidden" id="tsx" name="tsx" value=""/>
     	<input type="hidden" id="gltjmc" name="gltjmc" value=""/>
       	<input type="hidden" id="fhbs" name="fhbs" value="${model.tjlbFhbs}"/>
      	<input type="hidden" id="kzlx" name="kzlx" value="1"/>
     	<input type="hidden" id="zdxsms" name="zdxsms" value="1"/>
       	<input type="hidden" id="czy" name="czy" value="${model.czy}"/>
       	<input type="hidden" id="kzszid" name="kzszid" value="${kzszid}"/>
    </form>

	<div class="row sl_all_bg">
		<ul id="tjxmUl" class="nav nav-tabs sl_nav_tabs" role="tablist">

		</ul>
		<div class="clearfix margin-b20"></div>

		<div class="panel panel-gray tjxm_1" id="cxkz1">
			<div class="panel-heading">
				<h3 class="panel-title">
					查询快照 <a href="javascript:;" class="pull-right"
						onclick="cxkzScBtn(this);" id="del">删除</a>
				</h3>
			</div>
			<div class="panel-body">
				<ul class="tag-list tag-list1" id="cxkzUl"></ul>
				<div class="clearfix"></div>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="panel panel-gray">
			<div class="panel-heading">
				<h3 class="panel-title" style="color:#fff">
					过滤条件 <small class="text-red">（条件块内的各字段为“并且”关系，条件块间为“或者”关系）</small>
					<a href="javascript:;" onclick="addCxzdTjk();" class="pull-right">增加条件块</a>
				</h3>
			</div>

			<div id="cxzdDiv"></div>
		</div>

		<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog"
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
						<button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary"
							onclick="cxzdDivQd(this);return false;">确定</button>
					</div>
				</div>
			</div>
		</div>
		
		<div class="text-right" style="display: none;">
			<button type="button" class="btn btn-primary" onclick="cxkzWindow();">保存快照</button>
			<button type="button" class="btn btn-default " id="tjBtn"
				onclick="tjcx();">查  询</button>
		</div>
	</div>
</body>
</html>