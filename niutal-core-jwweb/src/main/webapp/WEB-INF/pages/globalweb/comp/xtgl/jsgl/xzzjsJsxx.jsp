<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
<form id="ajaxForm" action="<%=systemPath%>/xtgl/jsgl_xzzjsBcJsxx.html" method="post" data-toggle="validation" theme="simple"  role="form" class="form-horizontal sl_all_form">
	<input type="hidden" name="doType" value="save" />
	<div class="row">
		<s:if test="parent!=null">
			<div class="col-md-12 col-sm-12">
				<div class="form-group form-group-sm">
					<label for="" class="col-sm-2 control-label">
						<span class="red">*</span>父级角色
					</label>
					<div class="col-sm-8">
						<input type="hidden" name="fjsdm" value="<s:property value="parent.jsdm"/>"  />
						<p style="padding-top: 6px;"><s:property value="parent.jsmc"/></p>
					</div>
				</div>
			</div>
		</s:if>
		<div class="col-md-12 col-sm-12">
			<div class="form-group form-group-sm">
				<label for="" class="col-sm-2 control-label">
					<span class="red">*</span>角色名称
				</label>
				<div class="col-sm-8">
					<s:textfield name="jsmc" id="jsmc" cssClass="form-control  input-sm span3" validate="{required:true,stringMinLength:2,stringMaxLength:20,unique:['V0101']}"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group ">
				<label class="col-sm-2 control-label" >
					<span class="red">*</span>角色说明
				</label>
				<div class="col-sm-8">
					<textarea name="jssm" id="jssm"  class="form-control" rows="2" style="height: 50px;"  validate="{required:true,stringMaxLength:1000}"></textarea>
				</div>
			</div>
		</div>
		<s:if test="flag == 0 ">
			<input type="hidden" id="sfejsq" name="sfejsq" value="0" />
		</s:if>
		<s:else>
			<div class="col-md-12 col-sm-12">
				<div class="form-group form-group-sm">
					<label for="" class="col-sm-2 control-label" for="sjhm" >
						二级授权
					</label>
					<div class="col-sm-8">
						<s:iterator value="#{'0':'不允许','1':'允许'}" var="">
							<label class="radio-inline">
							    <input type="radio" id="sfejsq" name="sfejsq" <s:if test="key == '1'"> checked="checked"</s:if> value="<s:property value="key"/>" /><s:property value="value"/>
						  	</label>
						</s:iterator>
					</div>
				</div>
			</div>
		</s:else>
		<div class="col-md-12 col-sm-12">
			<div class="form-group form-group-sm">
				<label for="" class="col-sm-2 control-label" for="sjhm" >
					功能模块	
				</label>
				<div class="col-sm-8">
					<s:select id="gnmkdm" name="gnmkdm" list="gnmkList"
						listKey="gnmkdm" listValue="gnmkmc" headerKey=""
						headerValue="--请选择--" cssClass="form-control chosen-select input-sm span3"></s:select>
					<SCRIPT type="text/javascript">
			    		jQuery('#gnmkdm').trigger("chosen");
			    	</SCRIPT>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group form-group-sm">
				<label for="" class="col-sm-2 control-label">
					<span class="red">*</span>角色类型
				</label>
				<div class="col-sm-8" >
					<s:select list="jslxList"
						listKey="dm" listValue="mc" headerKey="" validate="{required:true}"
						headerValue="--请选择--" id="jslxdm" name="jslxdm" cssClass="form-control chosen-select input-sm span3"></s:select>
					<SCRIPT type="text/javascript">
			    		jQuery('#jslxdm').trigger("chosen");
			    	</SCRIPT>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group form-group-sm">
					<label for="" class="col-sm-2 control-label" for="sjhm" >
						是否公用
					</label>
					<div class="col-sm-3">
						<s:iterator value="isPublic" >
							<label class="radio-inline">
							    <input type="radio" id="jsgybj" name="jsgybj"  <s:if test="key == 0"> checked="checked"</s:if> value="<s:property value="key"/>" /><s:property value="value"/>
						  	</label>
						</s:iterator>
					</div>
					 <div class="col-sm-5">
		          		<div class="bg-warning" style="line-height:33px"><p class="">&gt;&gt;公用角色其他用户可以直接使用!</p></div>
		          </div>
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>