<!DOCTYPE html>
<html>
<head>
	<style type="text/css">
	.choose_yx{display: block;height: 13px;position: absolute;right: -3px;top: 2px;width: 13px;cursor:pointer;background: url("${messageUtil("system.stylePath")}/assets/images/ico_90.gif") no-repeat 0 0 !important;}
	.choose_wx{display: block;height: 13px;position: absolute;right: -3px;top: 2px;width: 13px;cursor:pointer;background: url("${messageUtil("system.stylePath")}/assets/images/ico_91.gif") no-repeat 0 0 !important;}
	/*=======弹出窗口模式三(表格)=====sj====*/
	.fieldlist { margin:0 auto; margin-bottom:4px; }
	.tab_box { width:100%; border:#ccc 1px solid; background:#fff; }
	.tab_box h4 { 
		text-align:center; 
		height:35px; 
		line-height:35px; 
		width:100%; 
		font-weight:bold; 
		border-bottom:#ccc 1px solid; 
		background-color: #f7f7f9; 
		color:#999;
	}
	/*========教学单位==人事====*/
	.demo_college { margin-top:4px; clear:both; }
	/*标题*/
	.college_title { line-height:31px; background:#f7f7f9 url(../images/college_title.gif) no-repeat; height:31px; font-weight:normal; cursor:pointer }
	.college_title .title_name { float:left; padding-left:40px; font-weight:bold; color:#0255B9; height:31px; font-size:15px;}
	.college_title a { float:right; padding:0 15px; color:#155fbe; }
	.college_title a:hover { color:#4295ff; }
	.college_title a.up { background:url(../images/blue/ico_18.gif) left center no-repeat; }
	.college_title a.down { background:url(../images/blue/ico_19.gif) left center no-repeat; }
	/*内容*/
	.demo_college .con { border:#dedede 1px solid; margin-top:2px; padding-bottom:5px; min-height:30px; _height:30px; }
	.demo_college li { float:left; padding:5px 0 0 5px;margin-left:5px; }
	.demo_college li .college_li { 
		display:block; 
		height: 32px;
		*height:30px;
	    line-height: 25px !important;
		text-align:center; 
		text-overflow:ellipsis; 
		white-space:nowrap; 
		overflow:hidden; 
		padding:2px 10px;
		border: 2px dashed #999;
		background-color: #f7f7f9; 
		color: gray; 
		text-decoration:none;
		border-radius:4px;
	}
	.demo_college li .college_li span { display:block; width:100%; color:#666666!important; font-weight:normal!important;}
	.college_checkbox {
		 text-align:left!important; 
		 text-indent:10px; 
		 font-size:14px!important;
	 }
	 .college_checkbox_template{
	 	background-color: #f7f7f9; 
	 }
	 
	.demo_college li .college_li input { vertical-align:middle; }
	.demo_college li .college_li:hover {  
		border: 2px dashed #00569d; 
		background-color: #f7f7f9;
		color:#00569d!important;
	}
	.demo_college .college_li_disable a{ color:#9b9b9b!important;border:#dedede 1px solid!important; background:url(../images/blue/college_bg1.gif) left center repeat-x!important;}
	.demo_college .college_li_disable a span { color:#bbbbbb!important;}
	.demo_college .college_li_disable a:hover {color:#9b9b9b!important;border:#f7f7f9 2px dashed;}
	.demo_college li a.college_add { color:#a2a1a1; border:#cccccc 1px dotted; display:block; width:150px; height:36px; line-height:36px; text-align:center; }
	.demo_college li a.college_add:hover { color:#155fbe; background:none; border:#4194ff 1px dotted; }
	
	.button_base{
		background: #f0f0f0;
		cursor:pointer;
		border-style: solid;
		border-width: 1px;
	}
	/*左侧按钮*/
	.button_left{
    	right: 10px;
	}
	/*右侧按钮*/
	.button_right{
    	left: 10px;
	}
	/*全选和取消全选*/
	.button_select{
		font-size:14px;
		margin-top: 5px;
		line-height:20px;
		width: 60px;
    	position: absolute;
	}
	.button_ph{
		background: #f1f1f1;
    	color: #333333;
	}
	.button_ph .hover{
		background: #f1f1f1;
    	color: #333333;
	}
	.button_ph_choose{
		background-color: #42a5f5;
	}
	</style>
</head>
<body>
<form class="form-horizontal sl_all_form" id="exportForm">
<input name="dcclbh" id="dcclbh" type="hidden" value="${dcclbh}"></input>
<input name="exportPHID" id="exportPHID" type="hidden" value=""></input>
<input type="hidden" id="exportWjgs" name="exportWjgs" value="xls" />
	<div class="row fieldlist" id="dc-component-main">
		<div class="col-md-6 col-sm-6" style="padding-left: 0px;padding-right: 5px;">
			<div class="tab_box">
				<h4>
					可选择导出列<a class="button_left button_select button_base" style="line-height: 20px;width: 80px;">全选 <i class="fa fa-angle-double-right"></i></a>
				</h4>
				<div class="demo_college" style="height: 365px; width: 100%;overflow-y:scroll;*position:relative;*z-index:1">
					<div style="height: 365px; width: 100%;">
						<ul id="unselectUl" style="padding: 0px;">
							<#list configList as c>
								<#if c.sfmrzd == '0'>
								<li style="position:relative" class="width-45">
									<label class="defdblclick college_li college_checkbox width-100" >
										${c.zdmc}
										<input name="unselectCol" type="hidden" value="${c.zd}!_##_!${c.zdmc}" />
									</label>
									<span class="choose_wx"></span>
								</li>
								</#if>
							</#list>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-sm-6" style="padding-left: 5px;padding-right: 0px;">
			<div class="tab_box" >
				<h4>
					<a class="button_right button_select button_base" style="line-height: 20px;width: 80px;"><i class="fa fa-angle-double-left"></i> 取消</a>已选择导出列<font color="red">（拖拽可以排序）</font>
				</h4>
				<div class="demo_college" style="height: 365px; width: 100%;overflow-y:scroll;*position:relative;*z-index:1">
					<div style="height: 365px; width: 100%; ">
						<ul id="selectUl" style="padding: 0px">
							<#list configList as c>
								<#if c.sfmrzd == '1'>
								<li style="position:relative" class="width-45">
									<label class="defdblclick college_li college_checkbox width-100" >
										${c.zdmc}
										<input name="unselectCol" type="hidden" value="${c.zd}!_##_!${c.zdmc}" />
									</label>
									<span class="choose_yx"></span>
								</li>
								</#if>
							</#list>
						</ul>
					</div>
				</div>
			</div>
		</div>
		
		
		<div class="panel panel-default">
		  <div class="panel-body" style="background-color: rgb(246, 248, 250);color: rgb(117, 134, 151);">
		  	<div class="col-md-12 col-sm-12 formlist" style="margin-top:10px;padding-left: 0px;padding-right: 5px;"> 导 出 快 照 </div>
		    <div  class="col-md-12 col-sm-12 formlist" style="margin-top:10px;padding-left: 0px;padding-right: 5px;" id="niutal_dc_ph_ul">
			<#list phList as c>
			<div class="btn-group btn-group-sm" style="margin-top:7px;" id="${c.id}">
			  <button type="button" class="btn button_ph" export-phid="${c.id}">${c.mc}</button>
			  <button type="button" class="btn dropdown-toggle" data-toggle="dropdown">
			    <span class="caret"></span>
			    <span class="sr-only">Toggle Dropdown</span>
			  </button>
			  <ul class="dropdown-menu" role="menu">
			    <li><a href="javascript:void(0)" onclick="deletePh('${c.id}','${c.mc}',this);"> 删 除 </a></li>
			  </ul>
			</div>
			</#list>
		</div>
		  </div>
		</div>
	</div>
</form>
</body>
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/plugins/jquery.dragsort/jquery.dragsort.js?ver=${messageUtil("niutal.jsVersion")}"></script> 
<script type="text/javascript" src="${base}/js/dc/export.js?ver=${messageUtil("niutal.jsVersion")}"></script>
</html>