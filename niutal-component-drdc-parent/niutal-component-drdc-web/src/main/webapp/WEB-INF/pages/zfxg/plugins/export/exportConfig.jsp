<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/v5_url.ini"%>
	<%@ include file="/WEB-INF/pages/globalweb/head/dragsort.ini"%>
	<style type="text/css">
	.choose_yx{display: block;height: 13px;position: absolute;right: -3px;top: 2px;width: 13px;cursor:pointer;background: url("<%=systemPath %>/images/delete-icons-2.gif") no-repeat 0 0 !important;}
	.choose_wx{display: block;height: 13px;position: absolute;right: -3px;top: 2px;width: 13px;cursor:pointer;background: url("<%=systemPath %>/images/add-icons-1.gif") no-repeat 0 0 !important;}
	/*=======弹出窗口模式三(表格)=====sj====*/
	.fieldlist { margin:0 auto; margin-bottom:4px; background:#F6F9FE; }
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
	
	</style>
</head>

<body>
<s:if test=" message != null ">
<div class="row">
	<div class="col-md-12 col-sm-12 align-center" style="min-height: 300px;">
		<p class="form-control-static  red bigger-160" style="line-height: 200px;"><s:property value="message"/></p>
	</div>
</div>
</s:if>
<s:else>
<s:form cssClass="form-horizontal sl_all_form" role="form" id="exportForm" theme="simple">
	
	<s:hidden name="dcclbh" id="dcclbh"/>
	<div class="row fieldlist">
		<div class="col-md-6 col-sm-6" style="padding-left: 0px;padding-right: 5px;">
			<div class="tab_box">
				<h4>
					可选择导出列
				</h4>
				<div class="demo_college" style="height: 365px; width: 100%;overflow-y:scroll;*position:relative;*z-index:1">
					<div style="height: 365px; width: 100%;">
						<ul id="unselectUl" style="padding: 0px;">
							<s:iterator value="configList" id="c" status="config">
								<s:if test="#c.zt==0">
									<li style="position:relative" class="width-45">
										<label class="college_li college_checkbox width-100" >
											<s:property value="#c.zdmc" />
											<input name="unselectCol" type="hidden"
												value="<s:property value='#c.zd'/>@<s:property value='#c.zdmc'/>" />
										</label>
										<span class="choose_wx" onclick="select(this);"></span>
									</li>
								</s:if>
							</s:iterator>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-sm-6" style="padding-left: 5px;padding-right: 0px;">
			<div class="tab_box" >
				<h4>
					已选择导出列<font color="red">（拖拽可以排序）</font>
				</h4>
				<div class="demo_college" style="height: 365px; width: 100%;overflow-y:scroll;*position:relative;*z-index:1">
					<div style="height: 365px; width: 100%; ">
						<ul id="selectUl" style="padding: 0px">
							<s:iterator value="configList" id="c" status="config">
								<s:if test="#c.zt==1">
									<li style="position:relative" class="width-45">
										<label class="college_li college_checkbox width-100" >
											<s:property value="#c.zdmc" />
											<input name="selectCol" type="hidden"
												value="<s:property value='#c.zd'/>@<s:property value='#c.zdmc'/>" />
										</label>
										<span class="choose_yx" onclick="unselect(this);"></span>
									</li>
								</s:if>
							</s:iterator>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12 formlist"  id="below">
			<div class="form-group">
				<label for="" class="col-md-2 col-sm-2 control-label">
					<span class="red">*</span>导出类型：	
				</label>
				<div class="col-md-7 col-sm-7">
					<label class="radio-inline">
					   <input type="radio" name="exportWjgs" value="xls" checked="checked">EXCEL表格
				  	</label>
				  	<label class="radio-inline">
					   <input type="radio" name="exportWjgs" value="dbf" >DBF文件
				  	</label>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12 formlist"  id="below">
			<div class="form-group">
				<label for="" class="col-md-2 col-sm-2 control-label">
					自定义文件名：	
				</label>
				<div class="col-md-7 col-sm-7">
				   <input type="text" name="fileName" id="exportFileName" class="form-control input-sm" value="文件<%=new java.util.Date().getTime()%>"/>
				</div>
			</div>
		</div>
	</div>
</s:form>
<script type="text/javascript" src="<%=systemPath%>/js/zfxg/comp/export.js"></script>
<script type="text/javascript">
	var isModify = false;

	jQuery(function() {
		var count = 0;
		var bindInterval = window.setInterval(function(){
			if($.fn.dragsort){
				jQuery("#unselectUl, #selectUl").dragsort({
					dragSelector : "label",
					dragBetween : true,
					dragEnd : saveOrder,
					placeHolderTemplate : "<li class='width-45'><label class='college_li college_checkbox college_checkbox_template width-100'></label></li>"
				});
				
				window.clearInterval(bindInterval);
			}else{
				if(count > 10){
					window.clearInterval(bindInterval);
				}
			}
			count ++;
		}, 1000);
	});

	//拖动后
	function saveOrder() {
		isModify = true;
		jQuery("#unselectUl").find(":input").attr("name","unselectCol");
		
		var unspan = jQuery("#unselectUl").find(".choose_yx");
		unspan.parent().append("<span class='choose_wx' onclick='select(this)'></span>");
		unspan.remove();
		
		var span = jQuery("#selectUl").find(".choose_wx");
		span.parent().append("<span class='choose_yx' onclick='unselect(this)'></span>");
		span.remove();
	};
	
</script>
</s:else>
</body>
</html>
