<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype>
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/v5_url.ini"%>
<s:if test="sfejsqFlag == 0">
<div class="row" style="padding-top: 0px;">
	<div class="col-md-12 col-sm-12">
		<div class="alert alert-error align-center bigger-180 red " style="line-height: 450px;">
	    	当前登录角色无二级授权权限，如有多个角色，可切换其他角色再次尝试！
	    </div>
	</div>
</div>
</s:if>
<s:else>
</head>
<body>
	
	<!-- ################以下是相应tab区域的条件，组件根据名称自动拷贝到组件中，并自动删除原html################################ -->
	<!-- 全校 -->
	<div aria-labelledby="SCHOOL" style="display: none;" data-droplist="false">
    	<!-- 数据范围box -->
		<div class="dataRangeBox1" name="SCHOOL" style="height:400px;">
			<ul class="item-list">
				<li >
					<input id="checkAll_qx" type="checkbox" value="-2" name="xxdm_" > <span>全校</span>
				</li>
				<li >
					<span style="clear: both;color:red;"><b>注意：</b></span>
					1.用户将拥有全校所有学院（部门）、专业、班级的数据权限
				</li>
			</ul>
		</div>
	</div>
	<!-- 学院/部门列表 -->
	<div aria-labelledby="niutal_xtgl_jgdmb"  style="display: none;" class="row">
		<!-- 筛选条件 -->
		<div class="col-md-6 col-sm-12">
		   <div class="form-group">
		     	<label for="" class="col-sm-4 control-label">机构类别</label>
		     	<div class="col-sm-8">
		     		<select name="cydm_id_bmlb" id="bmdm_bmlb"  class="form-control width-80 chosen-select" >
	     				<option value="">全部</option>
	     				<s:iterator value="bmlxs">
	     					<option value="<s:property value="key"/>"><s:property value="value"/></option>
	     				</s:iterator>
	     			</select>
		    	</div>
		  	</div>
		</div>
		<div class="col-md-6 col-sm-12">
		   <div class="form-group">
		     	<label for="" class="col-sm-4 control-label">年级</label>
		     	<div class="col-sm-8">
		     		<select name="ls_njdm" id="bmdm_njdm_id" class="form-control width-80 chosen-select">
	     				<option value="">全部</option>
	     				<s:iterator value="njdms">
	     					<option value="<s:property value="njdm_id"/>"><s:property value="njmc"/></option>
	     				</s:iterator>
	     			</select>
		    	</div>
		  	</div>
		</div>
	</div>
	<!-- 专业列表 -->
	<div aria-labelledby="niutal_xtgl_zydmb" style="display: none;" class="row">
		<!-- 筛选条件 -->
		<div class="col-md-6 col-sm-12">
		   <div class="form-group">
		     	<label for="" class="col-sm-4 control-label">学院</label>
		     	<div class="col-sm-8">
		     		<select name="ls_bmdm" id="zydm_jg_id"  class="form-control width-80 chosen-select" >
	     				<option value="">全部</option>
	     				<s:iterator value="jxbmdms">
	     					<option value="<s:property value="jg_id"/>"><s:property value="jgmc"/></option>
	     				</s:iterator>
	     			</select>
		    	</div>
		  	</div>
		</div>
		<div class="col-md-6 col-sm-12">
		   <div class="form-group">
		     	<label for="" class="col-sm-4 control-label">年级</label>
		     	<div class="col-sm-8">
		     		<select name="ls_njdm" id="zydm_njdm_id" class="form-control width-80 chosen-select">
	     				<option value="">全部</option>
	     				<s:iterator value="njdms">
	     					<option value="<s:property value="njdm_id"/>"><s:property value="njmc"/></option>
	     				</s:iterator>
	     			</select>
		    	</div>
		  	</div>
		</div>
	</div>
	<!-- 班级列表 -->
	<div aria-labelledby="niutal_xtgl_bjdmb" style="display: none;"  class="row">
		<!-- 筛选条件 -->
		<div class="col-md-4 col-sm-6">
		   <div class="form-group">
		     	<label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>学院</label>
		     	<div class="col-sm-8">
		     		<select name="ls_bmdm" id="bjdm_jg_id"  class="form-control width-95 chosen-select" >
	     				<option value="">全部</option>
	     				<s:iterator value="jxbmdms">
	     					<option value="<s:property value="jg_id"/>"><s:property value="jgmc"/></option>
	     				</s:iterator>
	     			</select>
		    	</div>
		  	</div>
		</div>
		<div class="col-md-3 col-sm-6">
		   <div class="form-group">
		     	<label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>专业</label>
		     	<div class="col-sm-8">
		     		<select name="ls_zydm" id="bjdm_zyh_id" class="form-control width-95 chosen-select">
	     				<option value="">全部</option>
	     				<s:iterator value="zydms">
	     					<option value="<s:property value="zyh_id"/>"><s:property value="zymc"/></option>
	     				</s:iterator>
	     			</select>
		    	</div>
		  	</div>
		</div>
		<div class="col-md-3 col-sm-6">
		   <div class="form-group">
		     	<label for="" class="col-sm-4 control-label">年级</label>
		     	<div class="col-sm-8">
		     		<select name="ls_njdm" id="bjdm_njdm_id" class="form-control width-95 chosen-select">
	     				<option value="">全部</option>
	     				<s:iterator value="njdms">
	     					<option value="<s:property value="njdm_id"/>"><s:property value="njmc"/></option>
	     				</s:iterator>
	     			</select>
		    	</div>
		  	</div>
		</div>
		<div class="col-md-2 col-sm-6 align-left pull-left"  style="padding-left: 25px;">
	     		<button type="button" class="btn btn-primary" id="bjdm_searchCheck" >查   询</button>
		</div>
	</div>
	<!-- 学生列表 -->
	<div aria-labelledby="view_xjgl_xsjbxxb" style="display: none;" class="row">
		<!-- 筛选条件 -->
		 <div class="col-md-4 col-sm-6">
		   <div class="form-group">
		     	<label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>年级</label>
		     	<div class="col-sm-8">
		     		<select name="ls_njdm" id="xs_njdm_id" class="form-control width-95 chosen-select">
	     				<option value="">全部</option>
	     				<s:iterator value="njdms">
	     					<option value="<s:property value="njdm_id"/>"><s:property value="njmc"/></option>
	     				</s:iterator>
	     			</select>
		    	</div>
		  	</div>
		</div>
		<div class="col-md-4 col-sm-6">
		   <div class="form-group">
		     	<label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>学院</label>
		     	<div class="col-sm-8">
		     		<select name="ls_bmdm" id="xs_jg_id"  class="form-control width-95 chosen-select" >
	     				<option value="">全部</option>
	     				<s:iterator value="jxbmdms">
	     					<option value="<s:property value="jg_id"/>"><s:property value="jgmc"/></option>
	     				</s:iterator>
	     			</select>
		    	</div>
		  	</div>
		</div>
		 <div class="col-md-4 col-sm-6">
		   <div class="form-group">
		     	<label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>专业</label>
		     	<div class="col-sm-8">
		     		<select name="ls_zydm" id="xs_zyh_id" class="form-control width-95 chosen-select">
	     				<option value="">全部</option>
	     				<s:iterator value="zydms">
	     					<option value="<s:property value="zyh_id"/>"><s:property value="zymc"/></option>
	     				</s:iterator>
	     			</select>
		    	</div>
		  	</div>
		</div>
		<div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">班级</label>
		      <div class="col-sm-8">
		      		<select name="ls_bjdm" id="xs_bh_id" class="form-control width-95 chosen-select">
	     				<option value="">全部</option>
	     				<s:iterator value="zydms">
	     					<option value="<s:property value="zyh_id"/>"><s:property value="zymc"/></option>
	     				</s:iterator>
	     			</select>
		      </div>
		    </div>
		 </div>
		<div class="col-md-4 col-sm-6">
		   <div class="form-group">
		     	<label for="" class="col-sm-4 control-label">学号/姓名</label>
		     	<div class="col-sm-8">
		     		<input name="ls_xh" id="xs_xh" class="form-control width-95" />
		    	</div>
		  	</div>
		</div>
		<div class="col-md-4 col-sm-6 align-right pull-right" style="padding-right: 25px;">
     		<button type="button" class="btn btn-primary" id="xs_searchCheck" >查   询</button>
		</div>
	</div>
	<!-- 学生性质 -->
	<div aria-labelledby="jw_xjgl_xsxzdmb"  style="display: none;" class="row">
	</div>	
	<s:hidden id="js_id" name="js_id"/>
	<s:hidden id="kzdx" name="kzdx"></s:hidden>
	<s:iterator value="yhmList">
		<input type="hidden" name="yhm" value="<s:property/>" />
	</s:iterator>
	<div id="dataRangeBox">
		
	</div>
	<script type="text/javascript">
	//取得的全局年级json
	var njdmsJson = "${njdmsJson}";
		njdmsJson = $.founded(njdmsJson) ? eval(njdmsJson) : [];
	</script>
	<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/sjfw/dataRangeSetting.js?ver=<%=jsVersion%>"></script>
</s:else>
</body>
</html>
