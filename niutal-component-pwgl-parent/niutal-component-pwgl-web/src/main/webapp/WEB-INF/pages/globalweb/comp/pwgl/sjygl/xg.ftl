[#-- 修改 --]
<form action="xgbc.zf" id="ajaxForm" data-toggle="validation"  role="form" class="form-horizontal sl_all_form" method="post">
	<input type="hidden" name="id" value="${model.id}"/>
	<div class="row">
		<div class="form-group col-sm-6">
			<label class="col-sm-5 control-label">模块：</label>
			<div class="col-sm-7">${model.mkmc}</div>
		</div>
		<div class="form-group col-sm-6">
			<label class="col-sm-5 control-label">是否启用：</label>
			<div class="col-sm-7">
				[#list sfList as item]
					<div class="col-sm-6 p-l-0">
						<input type="radio" name="zt" id="zt_${item.dm}" class="default-radio primary-radio"
								validate="{required:true}" value="${item.dm}" [#if item.dm == model.zt]checked="true"[/#if]/>
						<label for="zt_${item.dm}" class="label_h [#if item.dm == '1']green[#else]red[/#if]">${item.mc}</label>
					</div>
				[/#list]
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label class="col-sm-5 control-label">字段名：</label>
			<div class="col-sm-7">${model.col}（${model.com}）</div>
		</div>
		<div class="form-group col-sm-6">
			<label class="col-sm-5 control-label">字段注释：</label>
			<div class="col-sm-7">
				<input type="text" class="form-control" maxlength="50" name="com" id="com"
						value="${model.com}" validate="{required:true}" />
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label class="col-sm-5 control-label">是否主键：</label>
			<div class="col-sm-7">
				[#list sfList as item]
					<div class="col-sm-6 p-l-0">
						<input type="radio" name="key" id="key_${item.dm}" class="default-radio primary-radio"
								validate="{required:true}" value="${item.dm}" [#if item.dm == model.key]checked="true"[/#if]/>
						<label for="key_${item.dm}" class="label_h [#if item.dm == '1']green[#else]red[/#if]">${item.mc}</label>
					</div>
				[/#list]
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label class="col-sm-5 control-label">是否文件名：</label>
			<div class="col-sm-7">
				[#list sfList as item]
					<div class="col-sm-6 p-l-0">
						<input type="radio" name="filename" id="filename_${item.dm}" class="default-radio primary-radio"
								validate="{required:true}" value="${item.dm}" [#if item.dm == model.filename]checked="true"[/#if]/>
						<label for="filename_${item.dm}" class="label_h [#if item.dm == '1']green[#else]red[/#if]">${item.mc}</label>
					</div>
				[/#list]
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label class="col-sm-5 control-label">显示顺序：</label>
			<div class="col-sm-7">
				<input type="text" class="form-control" maxlength="4" name="xssx" value="${model.xssx}" validate="{PositiveInteger:true}"/>
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label class="col-sm-5 control-label">换行字数：</label>
			<div class="col-sm-7">
				<input type="number" class="form-control" maxlength="2" name="br" value="${model.br}" validate="{UnNegativeInteger:true}" />
			</div>
		</div>
	</div>
</form>
<script type="text/javascript" src="${base}/js/globalweb/comp/pwgl/sjygl/sjygl-do.js?ver=${messageUtil("niutal.jsVersion")}"></script>