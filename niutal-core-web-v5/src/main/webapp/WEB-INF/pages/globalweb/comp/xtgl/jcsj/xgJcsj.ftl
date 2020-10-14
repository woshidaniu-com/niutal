<!doctype html>
<html>
	<head>
		 <script type="text/javascript">
			//扩展自己的方法
			jQuery(function($) {
				$("#ajaxForm").validateForm();
			})
		</script>
	</head>
	<body>
		<form action="modifyJcsj.zf" id="ajaxForm" data-toggle="validation" method="post" theme="simple" class="form-horizontal sl_all_form">
			<input type="hidden" id="pkValue" name="pkValue" value="${model.lxdm}${model.dm}" />
			<input type="hidden" id="lxdm" name="lxdm" value="${model.lxdm}" />
			<input type="hidden" id="dm" name="dm" value="${model.dm}" />
			<input type="hidden" id="lxmc" name="lxmc" value="${model.lxmc}" />
			<div class="row">
			  <div class="col-md-12 col-sm-12">
		        <div class="form-group">
		          <label for="" class="col-sm-3 control-label">类型</label>
		          <div class="col-sm-7">
		             <p class="form-control-static">${model.lxmc}</p>
		          </div>
		        </div>
		       </div>
		     </div>
			<div class="row">
			  <div class="col-md-12 col-sm-12">
		        <div class="form-group">
		          <label for="" class="col-sm-3 control-label">代码</label>
		          <div class="col-sm-7">
		            <p class="form-control-static">${model.dm}</p>
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
		              validate="{required:true,stringMaxLength:200}" value="${model.mc}"/>
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
  						<input type="radio" name="zt" id="zt_1" value="1" ${(model.zt=='1')?string('checked','')} /> <span class="label label-success"> 启 用 </span>
					</label>
					<label class="radio-inline">
  						<input type="radio" name="zt" id="zt_0" value="0" ${(model.zt=='0')?string('checked','')} /> <span class="label label-danger"> 停 用 </span>
					</label>
    				</div>
		      	 </div>
		      </div>
			</div>
		</form>
	</body>
</html>