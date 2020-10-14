<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
<!-- tool bar-start  -->
<div class="row sl_add_btn">
    <div class="col-sm-12">
    	<!-- 加载当前菜单栏目下操作    : N010901  -->
     	<%=IndexAction.cxButtons(request.getParameter("gnmkdm"))%>
		<!-- 加载当前菜单栏目下操作 -->
		<input type="hidden" id="dcclbh" name="dcclbh" value="JW_N010901_XTCZRZ" />
    </div>
</div>
<!-- tool bar-end  -->
	<form class="form-horizontal sl_all_form">
		<div id="searchBox" class="row">
			<div class="col-md-4 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">
						操作类型
					</label>
					<div class="col-sm-8">
						<select class="form-control chosen-select" name="czlx" id="czlx">
							<option value=''>
								全部
							</option>
							<option value="insert">
								新增
							</option>
							<option value="update">
								更改
							</option>
							<option value="delete">
								删除
							</option>
						</select>
						<SCRIPT type="text/javascript">
				    		jQuery('#czlx').trigger("chosen");
				    	</SCRIPT>
					</div>
				</div>
			</div>
			
			
			<div class="col-md-4 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">
						业务模块
					</label>
					<div class="col-sm-8">
						<input class="form-control" type="text" name="czmk" id="czmk" />
					</div>
				</div>
			</div>

			<div class="col-md-4 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">
						操作用户
					</label>
					<div class="col-sm-8">
						<input class="form-control" type="text" name="czr" id="czr" />
					</div>
				</div>
			</div>

			
			<div class="col-md-8 col-sm-12">
				<div class="col-sm-12 col-md-12 form-group none-margin ">
					<label for="" class="col-sm-2 col-md-2 control-label">
						操作日期
					</label>
					<div class="col-sm-10 col-md-10 " style="padding-left: 10px;">
						<div class="col-sm-10 col-md-10 input-group none-margin ">
							<input placeholder="点击选择日期" class="form-control pull-left  width-44 Wdate" id="czkssj" type="text" readonly="readonly" name="czkssj" onfocus="var czjssj=$dp.$('czjssj');WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,onpicked:function(){czjssj.focus();},maxDate:'#F{$dp.$D(\'czjssj\')}'})" />
							<span style="line-height: 25px;padding: 5px;" class="pull-left">至</span>
							<input placeholder="点击选择日期" class="form-control pull-left  width-44 Wdate" id="czjssj" type="text" readonly="readonly" name="czjssj" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'czkssj\')}',readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<!-- btn-start  -->
	<div class="row sl_aff_btn">
		<div class="col-sm-12">
			<button type="button" class="btn btn-primary btn-sm" id="search_go" onclick="searchResult();return false;">
				查 询
			</button>
		</div>
	</div>
	<!-- btn-end  -->
	<table id="tabGrid"></table>
	<div id="pager"></div>

</body>
<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid4.6.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/wdatePicker.ini"%>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/rzgl.js?ver=<%=jsVersion%>"></script>
</html>
