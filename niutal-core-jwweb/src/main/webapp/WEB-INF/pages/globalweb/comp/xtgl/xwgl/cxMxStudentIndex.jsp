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
		          		校区
		          </label>
		          <div class="col-sm-7">
		             <s:select name="xqh_id" list="xqList" headerKey="" headerValue="---请选择---"
		              listKey="xqh_id" listValue="xqmc" id="xqh_id" cssClass="form-control chosen-select" onchange="queryStudentResult()"></s:select>
		              <SCRIPT type="text/javascript">
			    		jQuery('#xqh_id').trigger("chosen");
			    	 	</SCRIPT>
		          </div>
		        </div>
		      </div>
		      
		      <div class="col-sm-6">
		        <div class="form-group">
		          <label for="" class="col-sm-4 control-label">
		          		学院
		          </label>
		          <div class="col-sm-7">
		             <s:select name="jg_id" list="xyList" listKey="jg_id" listValue="jgmc" id="jg_id"
					  cssClass="form-control chosen-select"  headerKey="" headerValue="---请选择---" onchange="queryStudentResult()"></s:select>
					  <SCRIPT type="text/javascript">
			    		jQuery('#jg_id').trigger("chosen");
			    	 	</SCRIPT>
		          </div>
		        </div>
		      </div>
		      
		      <div class="col-sm-6">
		        <div class="form-group">
		          <label for="" class="col-sm-4 control-label">
		          		专业
		          </label>
		          <div class="col-sm-7">
		              <s:select list="#{}" listKey="zyh_id" listValue="zymc" headerKey="" headerValue="---请选择---" 
		        	 cssClass="form-control chosen-select" name="zyh_id"  id="zyh_id" onchange="queryStudentResult()"></s:select>
		        	 	<SCRIPT type="text/javascript">
			    		jQuery('#zyh_id').trigger("chosen");
			    	 	</SCRIPT>
		          </div>
		        </div>
		      </div>
		      
		      <div class="col-sm-6">
		        <div class="form-group">
		          <label for="" class="col-sm-4 control-label">
		          		年级
		          </label>
		          <div class="col-sm-7">
			           <s:select name="njdm_id" list="njList" listKey="njdm_id" listValue="njmc" id="njdm_id"
			            cssClass="form-control chosen-select"  headerKey="" headerValue="---请选择---" onchange="queryStudentResult()"></s:select>
			            <SCRIPT type="text/javascript">
			    		jQuery('#njdm_id').trigger("chosen");
			    	 	</SCRIPT>
		          </div>
		        </div>
		      </div>
		      
		      <div class="col-sm-6">
		        <div class="form-group">
		          <label for="" class="col-sm-4 control-label">
		          		班级
		          </label>
		          <div class="col-sm-7">
		             <s:select list="#{}" listKey="bh_id" listValue="bjmc" headerKey="" headerValue="---请选择---" 
		        	 cssClass="form-control chosen-select" name="bh_id"  id="bh_id" onchange="queryStudentResult()"></s:select>
		        	  <SCRIPT type="text/javascript">
			    		jQuery('#bh_id').trigger("chosen");
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
					  cssClass="form-control chosen-select"  headerKey="" headerValue="---请选择---" onchange="queryStudentResult()"></s:select>
					  <SCRIPT type="text/javascript">
			    		jQuery('#xbdm').trigger("chosen");
			    	 	</SCRIPT>
		          </div>
		        </div>
		      </div>
		      
		      <div class="col-sm-6">
		        <div class="form-group">
		          <label for="" class="col-sm-4 control-label">
		          		学生类别
		          </label>
		          <div class="col-sm-7">
		             <s:select name="xslbdm" list="xslbList" listKey="dm" listValue="mc" id="xslbdm"
					  cssClass="form-control chosen-select"  headerKey="" headerValue="---请选择---" onchange="queryStudentResult()"></s:select>
					  <SCRIPT type="text/javascript">
			    		jQuery('#xslbdm').trigger("chosen");
			    	 	</SCRIPT>
		          </div>
		        </div>
		      </div>
		      
		      <div class="col-sm-6">
		        <div class="form-group">
		          <label for="" class="col-sm-4 control-label">
		          		层次
		          </label>
		          <div class="col-sm-7">
		              <s:select name="pyccdm" list="pyccList" listKey="dm" listValue="mc" id="pyccdm"
					  cssClass="form-control chosen-select"  headerKey="" headerValue="---请选择---" onchange="queryStudentResult()"></s:select>
					  <SCRIPT type="text/javascript">
			    		jQuery('#pyccdm').trigger("chosen");
			    	 	</SCRIPT>
		          </div>
		        </div>
		      </div>
		      
		      <div class="col-sm-12">
			      	 <div  class="panel panel-default">
						<div class="panel-heading" id="title-h3">
							<h3 class="panel-title">
								<input id="szdxs" onclick="queryStudentResult()" type="checkbox">&nbsp;&nbsp;设置到学生
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
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/xwgl/cxMxStudentIndex.js?ver=<%=jsVersion%>"></script>
</html>