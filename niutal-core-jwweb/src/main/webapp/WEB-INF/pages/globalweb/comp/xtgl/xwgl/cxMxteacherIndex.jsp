<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	
 	<%@ include file="/WEB-INF/pages/globalweb/head/v5_url.ini"%> 
</head>
<body>
	<s:form id="ajaxForm" action="" theme="simple" cssClass="form-horizontal sl_all_form">
		<div class="row">
			<div class="col-sm-6">
		        <div class="form-group">
		          <label for="" class="col-sm-4 control-label">
		          		<font color="red">*</font>限制类别
		          </label>
		          <div class="col-sm-7">
		            	<s:select list="#{'mx':'面向','xz':'限制'}" listKey="key" listValue="value"
		            	 cssClass="form-control chosen-select" id="xzlb" name="xzlb"></s:select>
		            	 <SCRIPT type="text/javascript">
				    		jQuery('#xzlb').trigger("chosen");
				    	 </SCRIPT>
		          </div>
		        </div>
		      </div>
		       <div class="col-sm-6">
		        <div class="form-group">
		          <label for="" class="col-sm-4 control-label">
		          		<font color="red">*</font>学年
		          </label>
		          <div class="col-sm-7">
		              <s:select name="xnm" list="xnmList" listKey="xnm"	 cssClass="form-control chosen-select"
		          	listValue="xnmc" id="xnm" headerKey="" headerValue="---请选择---"></s:select>
		          		<SCRIPT type="text/javascript">
				    		jQuery('#xnm').trigger("chosen");
				    	 </SCRIPT>
		          </div>
		        </div>
		      </div>
		      
		       <div class="col-sm-6">
		        <div class="form-group">
		          <label for="" class="col-sm-4 control-label">
		          		<font color="red">*</font>学期
		          </label>
		          <div class="col-sm-7">
		               <s:select name="xqm" list="xqmList" listKey="dm" listValue="mc" id="xqm" cssClass="form-control chosen-select"
		               headerKey="" headerValue="---请选择---"></s:select>
		               <SCRIPT type="text/javascript">
				    		jQuery('#xqm').trigger("chosen");
				    	 </SCRIPT>
		          </div>
		        </div>
		      </div>
		      
		      
		      <div class="col-sm-6">
		        <div class="form-group">
		          <label for="" class="col-sm-4 control-label">
		          		校区
		          </label>
		          <div class="col-sm-7">
		             <s:select name="xqh_id" list="xqList" headerKey="" headerValue="---请选择---"
		              listKey="xqh_id" listValue="xqmc" id="xqh_id" cssClass="form-control chosen-select" onchange="queryTeachResult()"></s:select>
		               <SCRIPT type="text/javascript">
			    		jQuery('#xqh_id').trigger("chosen");
			    	 	</SCRIPT>
		          </div>
		        </div>
		      </div>
		      
		      <div class="col-sm-6">
		        <div class="form-group">
		          <label for="" class="col-sm-4 control-label">
		          		教师所在学院
		          </label>
		          <div class="col-sm-7">
		             <s:select name="jg_id" list="xyList" listKey="jg_id" listValue="jgmc" id="jg_id"
					  cssClass="form-control chosen-select"  headerKey="" headerValue="---请选择---" onchange="queryTeachResult()"></s:select>
					  <SCRIPT type="text/javascript">
			    		jQuery('#jg_id').trigger("chosen");
			    	 	</SCRIPT>
		          </div>
		        </div>
		      </div>
		       <div class="col-sm-6">
		        <div class="form-group">
		          <label for="" class="col-sm-4 control-label">
		          		开课学院
		          </label>
		          <div class="col-sm-7">
		             <s:select name="kkbm_id" list="xyList" listKey="jg_id" listValue="jgmc" id="kkbm_id"
					  cssClass="form-control chosen-select"  headerKey="" headerValue="---请选择---" onchange="queryTeachResult()"></s:select>
					  <SCRIPT type="text/javascript">
			    		jQuery('#kkbm_id').trigger("chosen");
			    	 	</SCRIPT>
		          </div>
		        </div>
		      </div>
		      <div class="col-sm-6">
		        <div class="form-group">
		          <label for="" class="col-sm-4 control-label">
		          		性别
		          </label>
		          <div class="col-sm-7">
		           	  <s:select name="xbdm" list="xbList" listKey="dm" listValue="mc" id="xbdm"
					  cssClass="form-control chosen-select"  headerKey="" headerValue="---请选择---" onchange="queryTeachResult()"></s:select>
					  <SCRIPT type="text/javascript">
			    		  jQuery('#xbdm').trigger("chosen");
			    	  </SCRIPT>
		          </div>
		        </div>
		      </div>
		      <div class="col-sm-12">
			      	 <div  class="panel panel-default">
						<div class="panel-heading" id="title-h3">
							<h3 class="panel-title">
								<input id="szdjs" onclick="queryTeachResult()" type="checkbox">&nbsp;&nbsp;设置到教师
							</h3>
						</div>
						<div class="panel-body" style="padding:0px;">
							 <table id="tabGrid1"></table>
							 <div id="pager1"></div>
						</div>
					</div>
		      </div>
		</div>
	</s:form>
</body>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/xwgl/cxMxteacherIndex.js?ver=<%=jsVersion%>"></script>
</html>