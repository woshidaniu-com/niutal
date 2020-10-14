[#assign zf=JspTaglibs["/woshidaniu-tags"] /]
<!DOCTYPE html>
<html>
	<head>
		
	</head>
	<body>
		<form action="saveCopyWjxx.zf" data-toggle="validation"   role="form" class="form-horizontal sl_all_form"  id="ajaxForm" method="post" >
			<input type="hidden" name="wjid" value="${model.wjid}"/>
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							<span class="red">*</span>问卷名称
						</label>
						<div class="col-sm-9">
							<input type="text" maxlength="20" name="wjmc" id="wjmc"  value="${model.wjmc}" class="form-control input-sm span2"  data-rules='{"required":true}'/>
						</div>
					</div>
				</div>
			</div>
			[#if enableSjyxj = 'true']
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							问卷优先级
						</label>
						<div class="col-sm-9">
							<input type="text" max="999" min="1" digits="true" name="wjyxj" id="wjyxj"  value="${model.wjyxj}" class="form-control input-sm span2"  data-rules='{"required":true}'/>
						</div>
					</div>
				</div>
			</div>
			[/#if]
			<!--
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							<span class="red">*</span>发布类型
						</label>
						<div class="col-sm-9">
							 [@zf.select id="fblx" list="#(0:普通,1:匿名)" defaultValue="${model.fblx!}" dataRules='{"required":true}' name="fblx"   listKey="bmdm_id" listValue="bmmc" cssClass="form-control input-sm chosen-select"/]
						</div>
					</div>
				</div>
			</div>
			-->
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label" >
							过期时间
						</label>
						<div class="col-sm-9">
							<input type="text" maxlength="20" name="gqsj" id="gqsj"  placeholder="不设置表示该问卷不会过期" value="${model.gqsj?replace('-','')}"
							readonly="true"  class="form-control input-sm span2"  onfocus="laydate({format:'YYYYMMDD'})"/>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label" >
							卷首语
						</label>
						<div class="col-sm-9">
							<textarea name="jsy" id="jsy" class="form-control " style="width:100%;height:50px" >${model.jsy}</textarea>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label" >
							卷尾语
						</label>
						<div class="col-sm-9">
							<textarea name="jwy" id="jwy" class="form-control "  style="width:100%;height:50px" >${model.jwy}</textarea>
						</div>
					</div>
				</div>
			</div>
		</form>
		<script type="text/javascript">
			$(function(){
				KindEditor.create('textarea[name="jsy"]', simpleOption);
				KindEditor.create('textarea[name="jwy"]', simpleOption);
			});	
		</script>
	</body>
</html>