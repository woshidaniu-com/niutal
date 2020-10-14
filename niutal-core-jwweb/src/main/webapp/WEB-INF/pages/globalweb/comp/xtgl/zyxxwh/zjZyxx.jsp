<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
	<head>
		<style type="text/css">
		.has-error .ke-container {
		    border-color: #a94442;
		    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
		}
		</style>
	</head>
<body>
	<s:form  cssClass="form-horizontal sl_all_form sl_all_bg" role="form" name="form" id="ajaxForm"  method="post" action="/xtgl/zyxx_zjBcZyxx.html" theme="simple" onsubmit="return false;">
		<s:hidden name="dlbs" value="zy"></s:hidden>
		<div class="row">
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>专业代码</label>
			      <div class="col-sm-8">
			      	  <s:textfield id="zyh" name="zyh" cssClass="form-control" validate="{required:true,stringMaxLength:10,chrnum:true,unique:['V1507','','违反唯一性约束，已存在相同的专业代码或大类代码']}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>专业名称</label>
			      <div class="col-sm-8">
			      		<s:textfield id="zymc" name="zymc" cssClass="form-control" validate="{required:true,stringMaxLength:60,unique:['V1507','','违反唯一性约束，已存在相同的专业代码或大类代码']}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">专业简称</label>
			      <div class="col-sm-8">
			      		<s:textfield id="zyjc" name="zyjc" cssClass="form-control" validate="{stringMaxLength:20}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">专业英文名称</label>
			      <div class="col-sm-8">
			      		<s:textfield id="zyywmc" name="zyywmc" cssClass="form-control" validate="{stringMaxLength:100}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">国标本专科专业</label>
			      <div class="col-sm-8">
				      	<s:select name="bzkzym" list="bzkzyList" listKey="BZKZYM" cssClass="form-control chosen-select" 
							listValue="BZKZYMC" id="bzkzym" headerKey="" headerValue="--请选择--">
						</s:select>
						<SCRIPT type="text/javascript">
							jQuery('#bzkzym').trigger("chosen");
			    		</SCRIPT>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">国标研究生专业</label>
			      <div class="col-sm-8">
				      	<s:select name="yjszym" list="yjszyList" listKey="YJSZYM" cssClass="form-control chosen-select" 
							listValue="YJSZYMC" id="yjszym" headerKey="" headerValue="--请选择--">
						</s:select>
						<script type="text/javascript">
							jQuery('#yjszym').trigger("chosen");
			    		</script>	
			      </div>
			    </div>
			  </div>
<%--			  <div class="col-md-6 col-sm-6">--%>
<%--			    <div class="form-group">--%>
<%--			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>大类标识</label>--%>
<%--			      <div class="col-sm-8">--%>
<%--						<s:select id="dlbs" name="dlbs" list="#{'dl':'大类','zy':'专业'}" headerKey="" headerValue="--请选择--" cssClass="form-control chosen-select" validate="{required:true}"></s:select>--%>
<%--			      </div>--%>
<%--			    </div>--%>
<%--			  </div>--%>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>学制</label>
			      <div class="col-sm-8">
			      		<s:textfield id="xz" name="xz" cssClass="form-control" validate="{required:true,stringMaxLength:2,chrnum:true}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">学科门类</label>
			      <div class="col-sm-8">
			      		<s:select name="cydm_id_xkml" list="xkmlList" listKey="cydm_id_xkml" cssClass="form-control chosen-select" 
							listValue="xkmlmc" id="cydm_id_xkml" headerKey="" headerValue="--请选择--">
						</s:select>
						<script type="text/javascript">
							jQuery('#cydm_id_xkml').trigger("chosen");
			    		</script>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>层次</label>
			      <div class="col-sm-8">
			      		<s:select name="ccdm" list="ccList" listKey="ccdm" cssClass="form-control chosen-select" validate="{required:true}"
							listValue="ccmc" id="ccdm" headerKey="" headerValue="--请选择--">
						</s:select>
						<script type="text/javascript">
							jQuery('#ccdm').trigger("chosen");
			    		</script>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>所属学院</label>
			      <div class="col-sm-8">
			      		<div class="input-group">
					      		<s:hidden id="jg_id" name="jg_id" ></s:hidden>
								<s:textfield id="jgmc" name="jgmc"  readonly ="true"  validate="{required:true}" cssClass="form-control"></s:textfield>
								<span class="input-group-btn">
									<button class="btn btn-default" data-toggle="modal" id="selectXy" type="button">></button>
									<button class="btn btn-default" data-toggle="clearfix" data-target="#jg_id,#jgmc" type="button">清空</button>
								</span>
						 </div>
			      </div>
			    </div>
			  </div>
		      <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">建立年月</label>
			      <div class="col-sm-8">
						<s:textfield name="jlny" id="jlny" placeholder="点击选择年月"  onfocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly="true" cssClass="form-control Wdate" ></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">优先级</label>
			      <div class="col-sm-8">
			      		<s:select name="yxj" list="#{'1':'特别','2':'优先','3':'普通','4':'最后'}" listKey="key" cssClass="form-control chosen-select" 
							listValue="value" id="yxj" headerKey="" headerValue="--请选择--">
						</s:select>
						<script type="text/javascript">
			    	  		jQuery('#yxj').trigger("chosen");
			    		</script>
			      </div>
			    </div>
			  </div> 
			  <div class="col-md-12 col-sm-12">
			    <div class="form-group">
			      <label for="" class="col-sm-2 control-label">备注</label>
			      <div class="col-sm-10">
			      		<s:textarea id="bz" name="bz" cssClass="form-control" validate="{stringMaxLength:100}" cssStyle="height:auto"></s:textarea>
			      </div>
			    </div>
			  </div>                     
		  </div>
			<div class="row">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label">专业简介</label>
			          <div class="col-sm-10">
		                 <s:textarea name="zyjj" id="zyjj"  cssClass="form-control">&nbsp;</s:textarea>
			          </div>
			        </div>
			      </div>
			</div>
	</s:form>
</body>
<script type="text/javascript">		
	jQuery(function($){
		$('#ajaxForm').validateForm();
		$("#selectXy").click(function(){
			$.showSelectDialog("3",{"multiselect":false},function(data){
				$("#jg_id").val(data[0].key);
				$("#jgmc").val(data[0].text).blur();
	  		});
			
		});
	});
</script>
</html>