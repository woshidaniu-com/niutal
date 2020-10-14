<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${base}/js/filemgr/index.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	<style type="text/css">
	.input-group-addon {
	    background-color: #FFF;
	    border-left: 0px;
	}
	
	.sl_add_btn .fa {
    	font-size: 14px;
	}
	
	.sl_add_btn .btn {
	    letter-spacing: 4px;
	}
	
	
	.thumbnail {
		cursor: pointer;
	}
	.thumbnail .text-center {
	   	margin-top: 8px;
	}
	.thumbnail .fa{
		font-size: 500%;
		color:#A5ACB3;
	}
	
	.thumbnail .fa-folder{
		color: #FFD659;
	}
	
	.thumbnail .caption {
	    padding: 0px;
	}
	.thumbnail .caption .text-center {
	   	margin-top: 0px;
	}
	
	.thumbnail .glyphicon-ok{
		display:none;
	}
	
	.thumbnail .glyphicon-ok{
		display:none;
	}
	
	#but_ancd .btn{
		display:none;
	}
	
	.thumbnail:hover {
	    background-color: #D3ECDE;
	    border: 1px solid #22ac38 !important;
	    position: relative !important;
	}
	
	.thumbnail:hover .glyphicon-ok{
	    display: block !important;
	    position: absolute !important;
	    right: 0px;
	    top: 0px;
	    color: #fff;
	    background: #22ac38;
	    padding: 3px;
	    font-size: 12px;
	}
	
	.thumbnail.selected {
	    background-color: #D3ECDE;
	    border: 1px solid #22ac38 !important;
	    position: relative !important;
	}
	
	.thumbnail.selected .glyphicon-ok{
	    display: block !important;
	    position: absolute !important;
	    right: 0px;
	    top: 0px;
	    color: #fff;
	    background: #22ac38;
	    padding: 3px;
	    font-size: 12px;
	}
			
	
	</style>
</head>
<body class="" style="">

	<div class="row sl_add_btn">
	    <div class="col-sm-12">
	    	<!-- 加载当前菜单栏目下操作    :   -->
	     	<div class="btn-toolbar" role="toolbar" style="float:left;">
	     		<button type="button" class="btn btn-primary text-left" href="javascript:void(0);" id="btn_zj"><i class="fa fa-upload" aria-hidden="true"></i>&nbsp;上传&nbsp;</button>
	    		&nbsp;
	     		<div class="btn-group" id="but_ancd">
	     			<button type="button" class="btn btn-default" href="javascript:void(0);" id="btn_download"><i class="fa fa-download" aria-hidden="true"></i> 下载</button>
	     			<button type="button" class="btn btn-default" href="javascript:void(0);" id="btn_del"><i class="fa fa-trash-o" aria-hidden="true"></i> 删除</button>
	     			<button type="button" class="btn btn-default" href="javascript:void(0);" id="btn_rename"><i class="fa fa-edit" aria-hidden="true"></i> 重命名</button>
	     		</div> 
	     	</div>
			<!-- 加载当前菜单栏目下操作 -->
	    </div>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<a href="#"> 返回上级 </a> | <a href="#"> 全部文件 </a> <span> &gt; </span> adasd
		</div>
		<div class="panel-body"  style="min-height: 500px;">
			<div class="row" id="filemgr_container" style="display:none;"></div>
    	 	<p class="text-muted text-center" id="filemgr_tip" style="display:none;">没有文件上传记录!</p>
		</div>
	</div>
	 
</body>
</html>
