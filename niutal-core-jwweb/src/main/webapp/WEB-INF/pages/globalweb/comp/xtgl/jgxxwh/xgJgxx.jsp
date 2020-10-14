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
	<s:form  cssClass="form-horizontal sl_all_form sl_all_bg" role="form" name="form" id="ajaxForm"  method="post" action="/xtgl/jgxx_xgBcJgxx.html" theme="simple" onsubmit="return false;">
		<s:hidden name="jg_id"></s:hidden>
		<div class="row">
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>机构代码</label>
			      <div class="col-sm-8">
			      	  <s:textfield id="jgdm" name="jgdm" cssClass="form-control" validate="{required:true,stringMaxLength:10,chrnum:true,unique:['V0103','${jgdm}','违反唯一性约束，已存在相同的机构代码']}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>机构名称</label>
			      <div class="col-sm-8">
			      		<s:textfield id="jgmc" name="jgmc" cssClass="form-control" validate="{required:true,stringMaxLength:100,unique:['V0103','${jgmc}','违反唯一性约束，已存在相同的机构代码']}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">机构简称</label>
			      <div class="col-sm-8">
			      		<!-- ,unique:['V0103','${jgjc}'] -->
			      		<s:textfield id="jgjc" name="jgjc" cssClass="form-control" validate="{required:false,stringMaxLength:50}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">机构简拼</label>
			      <div class="col-sm-8">
			      		<s:textfield id="jgjp" name="jgjp" cssClass="form-control" validate="{required:false,stringMaxLength:20}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">机构英文名称</label>
			      <div class="col-sm-8">
			      		<!-- ,unique:['V0103','${jgywmc}'] -->
			      		<s:textfield id="jgywmc" name="jgywmc" cssClass="form-control" validate="{required:false,stringMaxLength:100}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">隶属上级机构</label>
			      <div class="col-sm-8">
				      	<s:select name="lssjjgid" list="jgList" listKey="jg_id" cssClass="form-control  chosen-select" 
							listValue="jgmc" id="lssjjgid" headerKey="" headerValue="--请选择--">
						</s:select>	
					<SCRIPT type="text/javascript">
		    	  		jQuery('#lssjjgid').trigger("chosen");
		    	    </SCRIPT>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">校区</label>
			      <div class="col-sm-8">
			        <s:select id="lsxqid" name="lsxqid" list="xqList" listKey="xqh_id" listValue="xqmc" headerKey="" headerValue="--请选择--" cssClass="form-control chosen-select"/>
			        <SCRIPT type="text/javascript">
		    	  		jQuery('#lsxqid').trigger("chosen");
		    	    </SCRIPT>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>是否有效</label>
			      <div class="col-sm-8">
			      		<s:select name="jgyxbs" list="#{'1':'是','0':'否'}" listKey="key" cssClass="form-control chosen-select"  validate="{required:true}"
							listValue="value" id="jgyxbs" headerKey="" headerValue="--请选择--">
						</s:select>	
						<SCRIPT type="text/javascript">
			    	  		jQuery('#jgyxbs').trigger("chosen");
			    	  	</SCRIPT>
			      </div>
			    </div>
			  </div> 
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>是否教学部门</label>
			      <div class="col-sm-8">
			      		<s:select name="sfjxbm" list="#{'1':'是','0':'否'}" listKey="key" cssClass="form-control chosen-select" validate="{required:true}"
							listValue="value" id="sfjxbm" headerKey="" headerValue="--请选择--">
						</s:select>	
						<SCRIPT type="text/javascript">
			    	  		jQuery('#sfjxbm').trigger("chosen");
			    	    </SCRIPT>
			      </div>
			    </div>
			  </div> 
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>是否实体</label>
			      <div class="col-sm-8">
			      		<s:select name="sfst" list="#{'1':'是','0':'否'}" listKey="key" cssClass="form-control chosen-select" validate="{required:true}"
							listValue="value" id="sfst" headerKey="" headerValue="--请选择--">
						</s:select>	
						<SCRIPT type="text/javascript">
			    	  		jQuery('#sfst').trigger("chosen");
			    	  	</SCRIPT>
			      </div>
			    </div>
			  </div> 
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">建立年月</label>
			      <div class="col-sm-8">
						<s:textfield name="jlny" id="jlny" placeholder="点击选择年月" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly="true" cssClass="form-control Wdate"  ></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">邮政编码</label>
			      <div class="col-sm-8">
			      	  <s:textfield id="jgyzbm" name="jgyzbm" cssClass="form-control" validate="{required:false,stringMaxLength:7,zipCode:true}"></s:textfield>
			      </div>
			    </div>
			  </div>
			<div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">负责人</label>
			      <div class="col-sm-8">
			      	<div class="input-group">
			      		<s:hidden id="fzrjgh" name="fzrjgh"></s:hidden>
						<s:textfield name="xm" id="xm"  cssClass="form-control" validate="{required:false}" readonly="true"></s:textfield>
					   	<span class="input-group-btn">
							<button id="selectJs" class="btn btn-default" data-toggle="modal" type="button">&gt;</button>
						</span>
					</div>
			      </div>
			    </div>
			  </div>
			 <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>开课学院</label>
			      <div class="col-sm-8">
			      		<s:select name="kkxy" list="#{'1':'开课学院','2':'学生学院','3':'既开课学院又学生学院'}" listKey="key" cssClass="form-control chosen-select" validate="{required:true}"
							listValue="value" id="kkxy" headerKey="" headerValue="--请选择--">
						</s:select>	
						<SCRIPT type="text/javascript">
			    	  		jQuery('#kkxy').trigger("chosen");
			    	  	</SCRIPT>
			      </div>
			    </div>
			  </div> 
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">机构类别</label>
			      <div class="col-sm-8">
			      		<s:select name="jglb" list="#{'1':'教学院系','2':'科研机构','3':'公共服务','4':'党务部门','5':'行政机构','6':'附属单位','7':'后勤部门','8':'校办产业','9':'其他'}" listKey="key" cssClass="form-control chosen-select" validate="{required:false}"
							listValue="value" id="jglb" headerKey="" headerValue="--请选择--">
						</s:select>	
						<SCRIPT type="text/javascript">
			    	  		jQuery('#jglb').trigger("chosen");
			    	  	</SCRIPT>
			      </div>
			    </div>
			  </div> 
			  <div class="col-md-12 col-sm-12">
			    <div class="form-group">
			      <label for="" class="col-sm-2 control-label">机构地址</label>
			      <div class="col-sm-10">
			      		<s:textarea id="jgdz" name="jgdz" cssClass="form-control" validate="{stringMaxLength:100}" cssStyle="height:50px;"></s:textarea>
			      </div>
			    </div>
			  </div>                     
		  </div>
		  	<div class="row">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label">机构简介</label>
			          <div class="col-sm-10">
		                 <s:textarea  name="jgjj" id="jgjj" cssStyle="height:100px;"  cssClass="form-control">&nbsp;</s:textarea>
			          </div>
			        </div>
			      </div>
			</div>
	</s:form>
</body>
<script type="text/javascript">		
	jQuery(function($){
		$('#ajaxForm').validateForm();
		/** 选择教师 */
		$("#selectJs").click(function(){
			jQuery.showSelectDialog("2",{"multiselect":false},function(data){
				$("#fzrjgh").val(data[0].JGH);
				$("#xm").val(data[0].XM);
				$("#xm").blur();
	 	});
			
		});
	});
</script>
</html>