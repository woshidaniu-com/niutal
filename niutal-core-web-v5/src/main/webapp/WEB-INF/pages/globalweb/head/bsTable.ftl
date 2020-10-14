<link rel="stylesheet" type="text/css" href="${messageUtil("system.stylePath")}/assets/plugins/bootstrap-table/bootstrap-table.min.css?ver=${messageUtil("niutal.cssVersion")}" />
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/plugins/bootstrap-table/bootstrap-table.min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/plugins/bootstrap-table/bootstrap-table-zh-CN.min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/plugins/bootstrap-table/extensions/cookie/bootstrap-table-cookie.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/plugins/bootstrap-table/extensions/bootstrap-table-contextmenu.min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/plugins/bootstrap-table/bootstrap.table.additional.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>

<script type="text/javascript">
	$(function($){
		//搜索按钮查询时禁用
		bindSearchBtn();
		
		function bindSearchBtn(){
			var $btn = arguments[0] == null ? $("button[name='search_button']") : $(arguments[0]);
			var $tab = arguments[1] == null ? $("#tabGrid") : $(arguments[1]);
			
			if($btn.length > 0 && $tab.length > 0){
			
				$btn.disabled();
			
				$(document).off("load-success.bs.table load-error.bs.table", $tab).on("load-success.bs.table load-error.bs.table", $tab, function(e){
					$btn.enabled();
				}).off("refresh.bs.table", $tab).on("refresh.bs.table", $tab, function(e){
					$btn.disabled();
				});
			}
		}
	});
</script>