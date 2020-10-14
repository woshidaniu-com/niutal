<!DOCTYPE html>
<html>
	<head>
		
	</head>
	<body>
		<div class="lcgl">
		<form id="ajaxForm" method="post" data-toggle="validation" action="addModulerData.zf" theme="simple" class="form-horizontal sl_all_form">
			<div class="row">
		        <div class="form-group">
		          <label for="" class="col-sm-3 control-label"><span style="color:red;">*</span>流程名称</label>
		          <div class="col-sm-9">
		            	<input type="text" id="name" name="name" maxlength="100"
		           		class="form-control" validate="{required:true}" placeholder="请输入流程名称"/>
		          </div>
		        </div>
			</div>
			
			<div class="row">
		        <div class="form-group">
		          <label for="" class="col-sm-3 control-label">流程描述</label>
		          <div class="col-sm-9">
		            	<textarea type="text" id="description" name="description" placeholder="请输入流程描述" maxlength="200" rows="2" style="height:initial;" class="form-control"/>
		          </div>
		        </div>
			</div>
			
			<div class="row">
				
			
		        <div class="form-group">
		          <label for="" class="col-sm-3 control-label">业务属性组</label>
		          <div class="col-sm-9">
		            	<select class="form-control input-sm span3 chosen-select" name="biz" id="biz">
							<option value="">-- 无 --</option>
							[#list bizList as item]
						 		<option value="${item.id}"> ${item.name} </option>
						 	[/#list]
						</select>
						<SCRIPT type="text/javascript">
				    		jQuery('#biz').trigger("chosen");
	 			    	</SCRIPT>
		          </div>
		        </div>
		        
		        <div class="col-sm-9 col-sm-offset-3">
		          	<div class="alert alert-info" role="alert">
					  <strong>说明!</strong> 业务属性组用于流程设计设置流转条件使用。
					</div>
		          </div>
			</div>
			 
			<div class="row">
			<input type="hidden" name="editor" id="editor" value="advanced"> 
			        <!--<div class="form-group">
			          <label for="" class="col-sm-3 control-label">设计器</label>
			          <div class="col-sm-9">
			            	<div class="list-group">
			            	 
			            	  <a href="javascript:void(0);" class="list-group-item active" name="editor-link" data="advanced">
							    <h4 class="list-group-item-heading">高级设计器</h4>
							    <p class="list-group-item-text">BPMN2.0图形化建模环境，选择它可以用拖拽的方式使用组件。</p>
							  </a>
			            	  <a href="javascript:void(0);" class="list-group-item " name="editor-link" data="simple">
							    <h4 class="list-group-item-heading">简单设计器</h4>
							    <p class="list-group-item-text">一个直观的、基于表格样式的设计器，使用它可以快速开发简单流程。</p>
							  </a>
							</div>
			          </div>
			        </div>-->
			</div>
		</form>
		</div>
		<SCRIPT type="text/javascript">
			$('a[name="editor-link"]').click(function(){
				$('#ajaxForm #editor').val($(this).attr("data"));
				$('a[name="editor-link"]').removeClass('active');
				$(this).addClass('active');
			});
		</SCRIPT>
	</body>
</html>
