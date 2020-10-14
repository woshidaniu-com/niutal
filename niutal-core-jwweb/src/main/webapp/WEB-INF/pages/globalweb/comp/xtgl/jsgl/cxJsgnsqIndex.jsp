<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/v5_url.ini"%>
	<style type="text/css">
	.function_list02 { width:100%; float:left; border-top:#4a9bdc 1px solid; padding-bottom:20px;height: auto; white-space:nowrap !important;}
	.function_list02 .list_01 { background:#e4f0fe; font-weight:bold;height: 100%;white-space:nowrap !important;}
	.function_list02 .list_02 { background:#fae3ad;border-bottom: 1px dashed gray; white-space:nowrap !important;}
	.function_list02 .list_03 { background:#eeeeee;white-space:nowrap !important; }
	.function_list02 ul{padding: 0px !important;}
	.function_list02 li { float:left; background:url('<%=stylePath %>/assets/images/fgx_06.gif') left center no-repeat; padding:0 10px; white-space:nowrap; }
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
<html>
<body>
<s:if test="sfejsqFlag == 0">
<div class="row" style="padding-top: 0px;">
	<div class="col-md-12 col-sm-12">
		<div class="alert alert-error align-center bigger-180 red " style="margin:160px 0px;padding: 15px 150px ;">
	    	当前登录角色无二级授权权限，如有多个角色，可切换其他角色再次尝试！
	    </div>
	</div>
</div>
</s:if>
<s:else>
	<s:if test="sfejsqFlag == 2">
	<div class="row" style="padding-top: 0px;">
		<div class="col-md-12 col-sm-12">
			<div class="alert alert-error align-left bigger-180 red " style="margin:160px 0px;padding: 15px 150px ;">
		    	选择的角色所属的父级角色【所有级别】中有可二级授权的角色，该角色的功能授权将由其可二级授权的父级角色的用户设置！
		    </div>
		</div>
	</div>
	</s:if>
	<s:else>
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
			  	<strong>角色名称</strong> ${model.jsmc}
			  	<strong>已分配用户数</strong> ${model.yhnum}
			  	<s:if test="model.fjsdm!=null">
			  	<strong>父级角色</strong> <s:property value="model.fjsmc"/>
			  	</s:if>
			</div>
	    </div>
	</div>
	<div class="row" style="margin-top: 10px;">
	   	<div class="col-md-12 col-sm-12">
			<!-- 功能模块Nav tabs -->
			 <ul class="nav nav-tabs " role="tablist" id="nav_tabs">
				<s:iterator value="allGnmkList" id="gnmkOne" status="aa">
				<li data-gnmkdm="${gnmkOne.gnmkdm }" <s:if test="#aa.index==0">class="active"</s:if> >
				  	<a href="#gnmk_${gnmkOne.gnmkdm}" role="tab" data-toggle="tab"  class="checkbox" style="margin-top: 0px !important;margin-bottom: 0px !important;line-height: 1"> 
						<label>
			  				<input  value="${gnmkOne.gnmkdm }" type="checkbox" />${gnmkOne.gnmkmc }
			  			</label>
				  	</a>
				</li>
				</s:iterator>
		    </ul>
		</div>
		<div class="col-md-12 col-sm-12">
			<!-- Tab panes -->
			<div class="tab-content" id="tabContent" >
				<input type="hidden" id="qx" name="qx" value="no" />
			  	<input type="hidden" id="jsdm" name="jsdm" value='${model.jsdm}' />
			    <s:iterator value="allGnmkList" id="gnmkOne" status="index01">
			  	<div class="tab-pane <s:if test="#index01.index==0">active</s:if> function_list02" id="gnmk_${gnmkOne.gnmkdm}" data-gnmkdm="${gnmkOne.gnmkdm }" style="overflow: scroll;overflow-x:hidden; height: 500px;"></div>
			  	</s:iterator>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/jsgl/jsgl_gnsq.js?ver=<%=jsVersion%>"></script>
	</s:else>
</s:else>
</body>
</html>
