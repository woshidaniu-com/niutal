<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="z" uri="/niutal-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
	<z:func-i18n fileURL="js/globalweb/comp/xtgl/xtsz/xtsz.js"/>
	<z:i18n modules="${gnmkdm}" />
	<style type="text/css">
	.radio, .checkbox {
	    margin-bottom: 0px !important;
	    margin-top: 0px !important;
	}
	.radio input[type="radio"], 
	.radio-inline input[type="radio"], 
	.checkbox input[type="checkbox"], 
	.checkbox-inline input[type="checkbox"] {
	  	margin-top: 2px !important;
	}
	</style>
</head>
<body>
	<!--操作按钮 开始 -->
	<div class="row sl_add_btn">
	    <div class="col-sm-12"  id="btn_box">
	      	<!-- 加载当前菜单栏目下操作     -->
      		<%=IndexAction.cxButtons(request.getParameter("gnmkdm"))%>
			<!-- 加载当前菜单栏目下操作 -->
	    </div>
	</div>
	<!--操作按钮 结束  -->
	<!-- search start -->
		<form class="form-horizontal sl_all_form">
			<div class="row">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							<s:text name="i18n.szx"/>
						</label>
						<div class="col-sm-8">
							<input class="form-control" id="zs" name="zs" placeholder="<s:text name="N0101.placeholder"/>" maxlength="20"/>
						</div>
					</div>
				</div>

			</div>
		</form>
		<div class="row sl_aff_btn">
			<div class="col-sm-12">
				<button type="button" class="btn btn-primary btn-sm" id="search_go" >
					<s:text name="i18n.query"/>
				</button>
			</div>
		</div>
	<!-- search-end  -->
	<s:form action="xtsz_bcXtsz.html"   id="ajaxForm1111"  role="form" name="form" method="post" onsubmit="return false;">
		<s:hidden id="ssgnmkdm" name="ssgnmkdm"></s:hidden>
		<s:hidden id="ssmk" name="ssmk"></s:hidden>
	   	<!-- table-start  -->
	   	<div class="ui-grid-handle" id="searchResult">&nbsp;</div>
		<table id="tabGrid"></table>
		<!-- table-end  -->
	</s:form>
</body>
<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid4.6.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/validation.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/wdatePicker.ini"%>
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/xtsz/xtsz.js?ver=<%=jsVersion%>"></script>	
</html>