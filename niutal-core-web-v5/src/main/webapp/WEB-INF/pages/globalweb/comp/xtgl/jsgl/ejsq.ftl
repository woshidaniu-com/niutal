<!doctype html>
<html>
	<head>
		<style type="text/css">
			.function_list02 { width:100%; float:left; border-top:#4a9bdc 1px solid; padding-bottom:20px;height: auto; white-space:nowrap !important;}
			.function_list02 .list_01 { background:#e4f0fe; font-weight:bold;height: 100%;white-space:nowrap !important;}
			.function_list02 .list_02 { background:#fae3ad;border-bottom: 1px dashed gray; white-space:nowrap !important;}
			.function_list02 .list_03 { background:#eeeeee;white-space:nowrap !important; }
			.function_list02 ul{padding: 0px !important;}
			.function_list02 li { float:left; background:url('${messageUtil("system.stylePath")}/assets/images/fgx_06.gif') left center no-repeat; padding:0 10px; white-space:nowrap; }
			.function_list02 li:FIRST-CHILD {  background: none; }
			.function_list02 input {vertical-align: text-bottom;margin: 0px 0 0;}
			.function_list02 .label_01 {line-height: 32px;padding: 5px 0px;font-weight: normal;font-size: 16px;}
			.function_list02 .label_01 input{ vertical-align: middle; }
			.function_list02 .label_gnfx{ cursor: pointer; }
			.function_list02 .label_gnfx .btn { line-height: 1.3;font-weight: bold;}
			.function_list02 .label_02 {line-height: 18px;padding: 5px 0px;font-weight: normal;font-size: 14px;}
			.function_list02 .label_02 input {vertical-align: middle;margin: 0px 0 0;position:relative;}
			.active{font-weight: bolder;}
			.bootbox-body{padding: 0px !important;}
			#nav_tabs li{ margin-top: 3px;}
			#nav_tabs li a{ border-top: 3px solid transparent;}
			#nav_tabs li.active a{ border-top:2px solid #0770cd;}
		</style>
	</head>
	<body>
		<form id="ajaxForm" action="saveEjsq.zf" method="post" data-toggle="validation" role="form" class="form-horizontal sl_all_form">
			<div class="row" style="padding-top: 0px;">
				 <div class="col-sm-2 "  style="float:left;">
			 		<div style="float:left;" role="toolbar" class="btn-toolbar">
				 		<div id="but_ancd" class="btn-group">
				 			<button id="btn_qx" href="javascript:void(0);" class="btn btn-default btn_qx" type="button">
				 			<i class="bigger-100 glyphicon glyphicon-ok-circle"></i>&nbsp;全选
				 			</button>
				 			<button id="btn_cz" href="javascript:void(0);" class="btn btn-default btn_sx" type="button">
				 			<i class="bigger-100 glyphicon glyphicon-trash"></i>&nbsp;重置
				 			</button>
				 		</div> 
			 		</div> 
			    </div>
			    <div class="col-sm-10" style="padding-left: 0px;">
			    	<div class="alert alert-success alert-dismissible" role="alert" style="margin-bottom:0px;padding: 7px 15px !important;">
					  	<strong>授权角色</strong> ${model.jsmc!} &nbsp;<strong>授权用户</strong> ${yhglModel.xm!}
					</div>
			    </div>
			</div>
			<div class="row" style="margin-top: 10px;">
			   	<div class="col-md-12 col-sm-12">
					<!-- 功能模块Nav tabs -->
					 <ul class="nav nav-tabs " role="tablist" id="nav_tabs">
					 	[#assign t=0]
						[#list gnqxList as top]
					 		[#if top.gnmkdm != "N"]
						 		<li data-gnmkdm="${top.gnmkdm }" [#if t == 0]class="active"[/#if]>
								  	<a href="#gnmk_${top.gnmkdm}" role="tab" data-toggle="tab"  class="checkbox" style="margin-top: 0px !important;margin-bottom: 0px !important;line-height: 1"> 
										<label>
							  				<input  value="${top.gnmkdm }" name="gnmkdm" type="checkbox" />${top.gnmkmc }
							  			</label>
								  	</a>
								</li>
							[#else]
								[#list top.childrens as children]
									<li data-gnmkdm="${children.gnmkdm }" [#if t == 0]class="active"[/#if]>
									  	<a href="#gnmk_${children.gnmkdm}" role="tab" data-toggle="tab"  class="checkbox" style="margin-top: 0px !important;margin-bottom: 0px !important;line-height: 1"> 
											<label>
								  				<input  value="${children.gnmkdm }" name="gnmkdm" type="checkbox" />${children.gnmkmc }
								  			</label>
									  	</a>
									</li>
								[/#list]
							[/#if]
							[#assign t=t+1]
					 	[/#list]
				    </ul>
				</div>
				<div class="col-md-12 col-sm-12">
					<!-- Tab panes -->
					<div class="tab-content" id="tabContent">
					  <input type="hidden" id="zgh" name="zgh" value="${yhglModel.zgh}">
					   [#assign t=0]
					   [#list gnqxList as top]
						  	[#if top.gnmkdm != "N"]
					  		<div class="tab-pane [#if t == 0]active[/#if] function_list02" id="gnmk_${top.gnmkdm}" data-gnmkdm="${top.gnmkdm}" style="overflow: scroll;overflow-x:hidden; height: 500px;">
							     [#list top.childrens as children]
								    <div class="col-md-12 col-sm-12 list_00" style="padding-left: 0px; background-color: rgb(255, 255, 255); border-bottom: 1px dashed gray;">
									     <div class="col-md-3 col-sm-3 list_01" data-gnmkdm="${children.gnmkdm}" style="height: 94px;">
									        <label class="control-label label_01">
									          <input type="checkbox" name="gnmkdm" value="${children.gnmkdm}">&nbsp;${children.gnmkmc}
									         </label>
									     </div>
									     <div class="col-child col-md-9 col-sm-9" style="padding: 0px;">
									      	 [#list children.childrens as sjMenu]
									      	 	<div class="col-md-12 col-sm-12 list_00" style="padding-left: 0px;background-color: #FFFFFF;">
										          <div class="col-md-3 col-sm-3 list_02" data-gnmkdm="${sjMenu.gnmkdm}">
										            <label class="control-label label_01">
										            	<input type="checkbox" name="gnmkdm"  gnmkdm="${children.gnmkdm}" topGndm="${top.gnmkdm}"  value="${sjMenu.gnmkdm}">&nbsp;${sjMenu.gnmkmc}
										            </label>&nbsp;
										            <label class="label_gnfx"><button type="button" class="btn btn-default btn-xs red2 ">[反选]</button></label>
										          </div>
										          <div class="col-child col-md-9 col-sm-9" style="padding: 0px;">
										            <ul data-gnmkdm="${sjMenu.gnmkdm}">
										               [#if sjMenu.buttonList?exists]
										            		 [#list sjMenu.buttonList as button]
										            		 	<li>
													                 <div class="checkbox">
													                  	<label class="label_02"><input type="checkbox" name="gnczid"  gnmkdm="${sjMenu.gnmkdm}" value="${button.gnczid}">&nbsp;${button.czmc}</label>
													                 </div>
													            </li>
										            		 [/#list]
										            	[/#if]
										            </ul>
										          </div>
										        </div>
									      	 [/#list]
								         </div>
							        </div>
						        [/#list]
						      </div>
						    [#else]
						    	 [#list top.childrens as children]
						    	 	<div class="tab-pane [#if t == 0]active[/#if] function_list02" id="gnmk_${children.gnmkdm}" data-gnmkdm="${children.gnmkdm}" style="overflow: scroll;overflow-x:hidden; height: 500px;">
						    	 	 [#list children.childrens as sjMenu]
						    	 	 	<div class="col-md-12 col-sm-12 list_00" style="padding-left: 0px; background-color: rgb(255, 255, 255); border-bottom: 1px dashed gray;">
									      <div class="col-md-3 col-sm-3 list_01" data-gnmkdm="${sjMenu.gnmkdm}" style="height: 46px;">
									        <label class="control-label label_01">
									          	<input type="checkbox" name="gnmkdm" value="${sjMenu.gnmkdm}" topGndm="${children.gnmkdm}">&nbsp;${sjMenu.gnmkmc}
								            </label>
									        <label class="label_gnfx">
									          <button type="button" class="btn btn-default btn-xs red2 ">[反选]</button>
									         </label>
									      </div>
									      <div class="col-child col-md-9 col-sm-9" style="padding: 0px;">
									        <ul data-gnmkdm="${sjMenu.gnmkdm}">
									        	 [#if sjMenu.buttonList?exists]
								            		 [#list sjMenu.buttonList as button]
								            		 	<li style="border-bottom: 0px;">
												            <div class="checkbox">
												              <label class="label_02"><input type="checkbox" name="gnczid" gnmkdm="${sjMenu.gnmkdm}" value="${button.gnczid}">&nbsp;${button.czmc}</label>
												            </div>
											           </li>
								            		 [/#list]
								            	[/#if]
									        </ul>
									      </div>
									    </div>
						    	  	 [/#list]
					    	  	  </div>
						    	 [/#list]
						  	[/#if]
						  	[#assign t=t+1]
					 	 [/#list]
				    </div>
		      	</div>
			</div>
		<ul id="buttonListHidden" style="display:none;">
        	[#list buttonList as button]
        		<input class="buttonListHidden" type="hidden" value="${button.gnczid}" name="userButton"/>
        	[/#list]
        </ul>
		</form>
	</body>
	<script type="text/javascript" src="${base}/js/globalweb/comp/xtgl/jsgl_gnsq.js?ver=${messageUtil("niutal.jsVersion")}"></script>
</html>
