<!DOCTYPE html>
<html>
<head>
	<style type="text/css">
	.has-error .ke-container {
		border-color: #a94442;
		box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
	}
	</style>
</head>
<body style="background: #fff;">
	<form class="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" data-toggle="validation" action="modifyXw.zf">
		<input type="hidden" name="token" id="token" value="${token}" />
		<input type="hidden" id="xwbh" name="xwbh" value="${model.xwbh}"/>
		<div class="row">
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="inputEmail3" class="col-sm-2 control-label">
						<span style="color:red;">*</span>通知标题
					</label>
					<div class="col-sm-9">
		           			<input type="text" id="xwbt" name="xwbt" maxlength="100" value="${model.xwbt}"
		           			class="form-control" validate="{required:true,stringMinLength:6,stringMaxLength:100}" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			 <div class="col-md-12 col-sm-12">
		        <div class="form-group">
		          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>发布时间</label>
		          <div class="col-sm-5">
		              <input type="text" id="fbsj" name="fbsj" readonly="true" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,minDate:'${.now}'})"
		              validate="{required:true}" class="form-control" value="${model.fbsj}"/>
		          </div>
		          <div class="col-sm-5">
		          		<div class="bg-warning" style="line-height:33px"><p class="">&gt;&gt;通知发布后最早可以查询到通知的时间!</p></div>
		          </div>
		        </div>
		      </div>
		</div>
		<div class="row">
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-2 control-label">
						<span style="color:red;">*</span>发布对象
					</label>
					<div class="col-sm-9">
						[#list fbdxList as item]
		          	 		<label class="checkbox-inline">
							 	<input type="checkbox" name="fbdxs"  validate="{required:true}" value="${item.key}" [#if fList?contains(item.key)]checked="checked"[/#if] /> ${item.value}
							 </label>
		          	 	[/#list]
					</div>
				</div>
			</div>
		</div>
		
		<div class="row">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>阅读权限</label>
			          <div class="col-sm-9">
			          	 	[#list ydqxList as item]
			          	 		<label class="radio-inline">
								 	<input type="radio" name="ydqx"  validate="{required:true}" value="${item.key}" [#if item.key == model.ydqx]checked="checked"[/#if]/> ${item.value}
								 </label>
			          	 	[/#list]
			          </div>
			        </div>
			      </div>
			</div>
		
		<div class="row">
			<div class="col-md-12 col-sm-12">

				<div class="form-group">
					<label class="col-sm-2 control-label">
						<span style="color:red;">*</span>是否发布
					</label>
					<div class="col-sm-9">
						 [#list sfList as item]
						 	<label class="radio-inline">
							 	<input type="radio" name="sffb" validate="{required:true}" value="${item.key}"  [#if item.key == model.sffb]checked="checked"[/#if] />${item.value}
							 </label>
						 [/#list]
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-2 control-label">
						<span style="color:red;">*</span>是否置顶
					</label>
					<div class="col-sm-9">
						[#list sfList as item]
						 	<label class="radio-inline">
							 	<input type="radio" name="sfzd"  validate="{required:true}" value="${item.key}"   [#if item.key == model.sfzd] checked="checked"[/#if]/>${item.value}
							 </label>
						 [/#list]
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-2 control-label">
						<span style="color:red;">*</span>通知内容
					</label>
					<div class="col-sm-9" id="ke_control">
						<textarea style="width:100%;height:300px" name="fbnr"
							id="fbnr" class="form-control">${model.fbnr}</textarea>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
<script language="javascript">
//你可以在这里继续使用$作为别名...
jQuery(function($) {
	
	var ke_container = $("#ke_control").find(".ke-container");
	KindEditor.remove('#fbnr');
	var editor = KindEditor.create('#fbnr', $.extend(true,{},newsOption,{
		afterCreate: function () {
		
			this.sync();
			this.readonly(false);
			this.focus();
			
			ke_container = $("#ke_control").find(".ke-container");
			$('#ajaxForm').validateForm( {
				//提交前的回调函数
				beforeSubmit : function(formData, jqForm, options) {

					editor.sync();
				
					var fbnr = editor.text();
					if (!$.founded(fbnr)) {
						$(ke_container).errorClass("通知内容必须填写!");
						return false;
					} else {
						$(ke_container).successClass();
						return true;
					}
				}
			});
	    },
	    afterBlur: function () {

	    	this.sync();
	    	 
			if (!$.founded(this.text())) {
				$(ke_container).errorClass("通知内容必须填写!");
			} else {
				$(ke_container).successClass();
			}
	       
	    } 
	}));

	window.setTimeout(function(){
		editor.readonly(false);
	}, 1000);

});
</script>
</html>
