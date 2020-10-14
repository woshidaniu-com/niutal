<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
<form id="ajaxForm" action="<%=systemPath%>/xtgl/jsgl_xgBcJsxx.html" data-toggle="validation" method="post" theme="simple" role="form" class="form-horizontal sl_all_form">
	<input type="hidden" name="jsdm" value="${model.jsdm }" />
	<input type="hidden" name="sfksc" id="sfksc" value="${model.sfksc }" />
	<input type="hidden" name="bkscgwjbdm" value="${model.gwjbdm }" />
	<input type="hidden" name="doType" value="save" />
	<input type="hidden" name="yhnum" value="${model.yhnum }" />
	<div class="row">
		<s:if test="parent!=null">
			<div class="col-md-12 col-sm-12">
				<div class="form-group form-group-sm">
					<label for="" class="col-sm-2 control-label">
						<span class="red">*</span>父级角色
					</label>
					<div class="col-sm-8">
						<p class="form-control-static"><s:property value="parent.jsmc"/></p>
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
					<s:textfield name="jsmc" id="jsmc" cssClass="form-control  input-sm span3"  validate="{required:true,stringMinLength:2,stringMaxLength:50,unique:['V0101','${jsmc}']}"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group ">
				<label class="col-sm-2 control-label" >
					<span class="red">*</span>角色说明
				</label>
				<div class="col-sm-8">
					<s:textarea name="jssm" id="jssm" validate="{required:true,stringMaxLength:1000}"  cssStyle="height: 50px;"  cssClass="form-control" rows="2" ></s:textarea>
				</div>
			</div>
		</div>
		<s:if test="flag == '0' ">
			<input type="hidden" id="sfejsq" name="sfejsq" value="0" />
		</s:if>
		<s:else>
			<div class="col-md-12 col-sm-12">
				<div class="form-group form-group-sm">
					<label for="" class="col-sm-2 control-label" for="sjhm" >
						二级授权
					</label>
					<div class="col-sm-8">
						<s:iterator value="#{0:'不允许',1:'允许'}" var="">
							<label class="radio-inline">
							    <input type="radio" id="sfejsq" name="sfejsq" <s:if test="key == sfejsq"> checked="checked" </s:if> value="<s:property value="key"/>" /><s:property value="value"/>
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
					<s:select id="gnmkdm" name="jsgnmkdm" list="gnmkList"
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
						headerValue="--请选择--"   id="jslxdm" name="jslxdm"  cssClass="form-control chosen-select input-sm span3"></s:select>
					<SCRIPT type="text/javascript">
			    		jQuery('#jslxdm').trigger("chosen");
			    	</SCRIPT>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group form-group-sm">
				<label for="" class="col-sm-2 control-label" for="sjhm" >
					是否公用
				</label>
				<div class="col-sm-3">
					<s:iterator value="isPublic">
						<label class="radio-inline">
							<s:if test="model.jsgybj == key">
								<input type="radio" id="jsgybj" name="jsgybj" checked="checked" value="<s:property value="key"/>" /><s:property value="value"/>
							</s:if>
							<s:else>
								<input type="radio" id="jsgybj" name="jsgybj" value="<s:property value="key"/>" /><s:property value="value"/>
							</s:else>
					  	</label>
					</s:iterator>
				</div>
				 <div class="col-sm-5">
		          		<div class="bg-warning" style="line-height:33px"><p class="">&gt;&gt;公用角色其他用户可以直接使用!</p></div>
		          </div>
			</div>
		</div>
	</div>
</form>
</body>
</html>