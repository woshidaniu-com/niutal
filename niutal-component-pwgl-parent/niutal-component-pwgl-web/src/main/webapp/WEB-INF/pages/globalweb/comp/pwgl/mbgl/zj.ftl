[#-- 增加 --]
<form action="zjbc.zf" id="ajaxForm" data-toggle="validation"  role="form"
		class="form-horizontal sl_all_form" method="post" enctype="multipart/form-data">
	<div class="row">
		<div class="form-group col-sm-6">
			<label class="col-sm-6 control-label">模块：</label>
			<div class="col-sm-6">
				<select id="mkdm" name="mkdm" class="form-control chosen-select" validate="{required:true}">
					<option value="">---请选择---</option>
					[#list sjyList as item]
						<option value="${item.dm}">${item.mc}</option>
					[/#list]
				</select>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label class="col-sm-6 control-label">模块名称：</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" maxlength="100" name="drmbmc" validate="{required:true}" />
			</div>
		</div>
		<div class="form-group col-sm-12">
			<label class="col-sm-3 control-label">模板：</label>
			<div class="col-sm-9 files-up">
				<input type="file" class="filestyle" data-buttonText="选择模板" data-buttonName="btn-primary" name="file"/>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript" src="${base}/js/globalweb/comp/pwgl/mbgl/mbgl-do.js?ver=${messageUtil("niutal.jsVersion")}"></script>