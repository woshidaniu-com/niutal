[#assign zf=JspTaglibs["/woshidaniu-tags"] /]
<!DOCTYPE html>
<html>
	<head>
	</head>
	<body>
		<form action="modifyWjzt.zf" data-toggle="validation"   role="form" class="form-horizontal sl_all_form"  id="ajaxForm" method="post" >
			<input type="hidden" name="wjid" value="${model.wjid}"/>
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-3 control-label">
							问卷名称
						</label>
						<div class="col-sm-8">
							${model.wjmc}
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-3 control-label">
							<span class="red">*</span>问卷状态
						</label>
						<div class="col-sm-8">
							 [@zf.select id="wjzt" list="#(DRAFT:草稿,RUN:运行,STOP:停止)"  dataRules='{"required":true}' 
							 name="wjzt"  defaultValue="${model.wjzt!}"  listKey="" listValue="" cssClass="form-control input-sm chosen-select"/]
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>