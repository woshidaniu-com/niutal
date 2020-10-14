<!DOCTYPE html>
<html>
	<head>
		<script type="text/javascript" src="${base}/js/tjcx/comp/kzsz.js?ver=${messageUtil("niutal.jsVersion")}"></script>
		<script type="text/javascript">
			var DYH = "_DYHBS_";// 单引号
			var editor;//编辑器
			jQuery(function() {
				//KindEditor.remove('textarea[name="kzms"]');
				editor = KindEditor.create('textarea[name="kzms"]'); //初始化编辑器
				setDefaultCxkz();// 设置默认查询快照弹层显示，超级管理员可进行共享
			});
		</script>
	</head>
	<body>	
		<form id="ajaxForm" method="post" action="save.zf" class="form-horizontal sl_all_form">
			<div class="row">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>名称</label>
			          <div class="col-sm-9">
			            	<input type="text" id="szmcView" name="szmcView" maxlength="20"
			           		class="form-control" validate="{required:true,stringMinLength:1,stringMaxLength:20}" />
			          </div>
			        </div>
			      </div>
			</div>
			<div class="row" id="sfgy">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>是否共享</label>
			          <div class="col-sm-9">
							<label class="radio-inline">
								<input type="radio"  validate="{required:true}" name="sfgyView" value="0" checked="checked"/>私有
							</label>
							<label class="radio-inline">
								<input type="radio"  validate="{required:true}" name="sfgyView" value="1" />共享
							</label>
			          </div>
			        </div>
			      </div>
			</div>
			
			<div class="row hide" id="kzjsrTr">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label">快照接收人</label>
			          <div class="col-sm-9" id="ke_control">
		                 <textarea  class="form-control" name="kzjsr" id="kzjsr" rows="4" style="word-break:break-all;width:97%;height:100px;"></textarea>
						 <font color="red">请输入职工号，多个以逗号分割。若为空，则所有职工均可查看</font>
			          </div>
			        </div>
			      </div>
			</div>
			
			<div class="row">
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label">快照描述</label>
			          <div class="col-sm-9" id="ke_control">
		                 <textarea class="form-control" name="kzms" id="kzms" rows="15" style="word-break:break-all;width:97%"></textarea>
			          </div>
			        </div>
			      </div>
			</div>
		</form>	
</div>
</body>
</html>