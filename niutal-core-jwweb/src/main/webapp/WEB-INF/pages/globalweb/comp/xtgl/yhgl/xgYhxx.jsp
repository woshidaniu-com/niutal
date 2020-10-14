<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
<form  id="ajaxForm" action="yhgl_xgBcYhxx.html" data-toggle="validation" role="form"  class="form-horizontal sl_all_form" method="post" theme="simple">
    <input type="hidden" name="doType" value="save"/>   
    <input type="hidden" id="jsdm" name="jsdm" value="${model.jsdm}"/>  
    <input type="hidden" id="jsmc" name="jsmc" value="${model.jsmc}"/>    
    <input type="hidden" id="yhm" name="yhm" value="${model.yhm}"/>      
    <div class="row">
		<div class="col-md-12 col-sm-12">
			<div class="form-group form-group-sm">
				<label for="" class="col-sm-2 control-label">
					用户名
				</label>
				<div class="col-sm-8 ">
					<p class="form-control-static">${model.yhm }</p>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group form-group-sm">
				<label for="" class="col-sm-2 control-label">
					<span class="red">*</span>姓名
				</label>
				<div class="col-sm-8">
					<input type="text" name="xm" id="xm" class="form-control input-sm" value="${model.xm}" validate="{required:true,stringMaxLength:20}">
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group form-group-sm">
				<label for="" class="col-sm-2 control-label"  for="dzyx">
					Email
				</label>
				<div class="col-sm-8">
					<input type="text" name="dzyx" id="dzyx" class="form-control input-sm" value="${model.dzyx}" validate="{email2:true}"  data-toggle="autoEmail">
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group form-group-sm" >
				<label for="" class="col-sm-2 control-label" for="sjhm" >
					联系电话	
				</label>
				<div class="col-sm-8">
					<input type="text" name="sjhm" id="sjhm" validate="{mobile:true}" value="${model.sjhm}"  class="form-control input-sm"/>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group form-group-sm" >
				<label for="" class="col-sm-2 control-label" for="jg_id">
					<span class="red">*</span>所属机构
				</label>
				<div class="col-sm-8">
					<input type="hidden" name="jgmc" id="jg_mc" value="${model.jgmc}">
					<s:select list="jgxxList" listKey="jg_id" listValue="jgmc" id="jg_id"  name="jg_id"  value="model.jgdm" headerKey=""  headerValue="---请选择---" cssClass="form-control  input-sm chosen-select"  validate="{required:true}"></s:select>
					<SCRIPT type="text/javascript">
			    		jQuery('#jg_id').trigger("chosen");
			    	</SCRIPT>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group form-group-sm">
				<label for="" class="col-sm-2 control-label">
					所属角色
				</label>
				<div class="col-sm-8">
					<div class="checkbox">
					<s:iterator value="jsxxList" status="substa">
						<div class="col-md-4 col-sm-4 ">
							<label class="checkbox-inline">
							<s:if test="model.js_id_list.contains(jsdm)">
								<input type="checkbox" style="cursor: pointer;" checked="checked" id="cbvjsxx" name="cbvjsxx" value="<s:property value="jsdm"/>" /><s:property value="jsmc"/> 
							</s:if>
							<s:else>
								<input type="checkbox" style="cursor: pointer;" id="cbvjsxx" name="cbvjsxx" value="<s:property value="jsdm"/>" /><s:property value="jsmc"/> 
							</s:else>
							</label>
					  	</div>
					</s:iterator>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-sm-2 control-label">
					是否启用
				</label>
				<div class="col-sm-8">
					<s:iterator value="isNot" status="substa">
						<label class="radio-inline">
							<s:if test="model.sfqy == key">
								<input type="radio" style="cursor: pointer;" checked="checked" id="sfqy" name="sfqy" value="<s:property value="key"/>" /><s:property value="value"/>
							</s:if>
							<s:else>
								<input type="radio" style="cursor: pointer;" id="sfqy" name="sfqy" value="<s:property value="key"/>" /><s:property value="value"/>
							</s:else>
					  	</label>
					</s:iterator>
				</div>
			</div>
		</div>
	</div>
</form>
</body>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/yhgl/yhgl_xgyhxx.js?ver=<%=jsVersion%>"></script>
</html>