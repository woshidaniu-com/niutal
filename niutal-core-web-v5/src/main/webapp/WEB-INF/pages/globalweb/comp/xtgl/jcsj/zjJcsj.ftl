<!doctype html>
<html>
	<head>
			<script type="text/javascript" src="${base}/js/globalweb/comp/xtgl/jcsj_zj.js?ver=${messageUtil("niutal.jsVersion")}"></script>
			<script type="text/javascript">
			//扩展自己的方法
			jQuery(function($) {
				$("#ajaxForm").validateForm();
			})
		</script>
	</head>
	<body>
		<form action="saveJcsj.zf" id="ajaxForm" data-toggle="validation" method="post" theme="simple" class="form-horizontal sl_all_form">
			<div class="row">
			  <div class="col-md-12 col-sm-12">
		        <div class="form-group">
		          <label for="" class="col-sm-3 control-label"><span style="color:red;">*</span>类型</label>
		          <div class="col-sm-7">
							<select name="lxdm" id="lxdm" class="form-control input-sm span3 chosen-select"  validate="{required:true}">
								<option value=""></option>
								[#list lxdmList as item]
									<option value="${item.lxdm}">${item.lxmc}</option>
								[/#list]
							</select>
							<SCRIPT type="text/javascript">
					    		jQuery('#lxdm').trigger("chosen");
		 			    	</SCRIPT>
		          </div>
		        </div>
		      </div>
		      </div>
		     <div class="row"> 
		      <div class="col-md-12 col-sm-12">
		        <div class="form-group">
		          <label for="" class="col-sm-3 control-label"><span style="color:red;">*</span>代码</label>
		          <div class="col-sm-7">
		             	<input type="text" maxlength="20" name="dm" id="dm" class="form-control"
		             	validate="{required:true,chrnum:true,jcsjUnique:true,stringMaxLength:20}"/>
		          </div>
		        </div>
		      </div>
		       </div>
		      <div class="row"> 
		      <div class="col-md-12 col-sm-12">
		        <div class="form-group">
		          <label for="" class="col-sm-3 control-label"><span style="color:red;">*</span>名称</label>
		          <div class="col-sm-7">
		              <input type="text" maxlength="50"  name="mc" id="mc" class="form-control"
		              validate="{required:true,stringMaxLength:200}"/>
		          </div>
		        </div>
		      </div>
			</div>
			<div class="row"> 
		      <div class="col-md-12 col-sm-12">
		      	 <div class="form-group">
		      	  	<label for="" class="col-sm-3 control-label"><span style="color:red;">*</span>启用状态</label>
		      	 	<div class="col-sm-9">
      				<label class="radio-inline">
  						<input type="radio" name="zt" id="zt_1" value="1" checked /> <span class="label label-success"> 启 用 </span>
					</label>
					<label class="radio-inline">
  						<input type="radio" name="zt" id="zt_0" value="0" /> <span class="label label-danger"> 停 用 </span>
					</label>
    				</div>
		      	 </div>
		      </div>
			</div>
			
			
			</form>
	</body>
</html>