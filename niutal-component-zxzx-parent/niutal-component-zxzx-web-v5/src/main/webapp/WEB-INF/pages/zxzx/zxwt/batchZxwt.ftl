<!doctype html>
<html>
	<head>
		<style>
			.bootstrap-filestyle .form-control {
				height: 32px !important;
			}
			
			.has-error .input-group-btn {
			    border-color: #a94442;
			    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
			    box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
			}
		</style>
	</head>
	<body>
			<div class="row">
				 <div class="col-md-12 col-sm-12">
				 			<div class="tab-base tab-drop-tline">
								<ul class="nav nav-tabs">
									<li class="active">
										<a data-toggle="tab" href="#demo-dror-tab-1" aria-expanded="true">咨询导出</a>
									</li>
									<li class="">
										<a data-toggle="tab" href="#demo-dror-tab-2" aria-expanded="false">批量回复</a>
									</li>
								</ul>
					
								<div class="tab-content">
									<div id="demo-dror-tab-1" class="tab-pane fade active in">
										<form id="ajaxForm-dc" method="post" action="exportZxwt.zf" class="form-horizontal sl_all_form">
											<div class="row">
												<div class="col-md-12 col-sm-12">
													<div class="form-group">
														<div class="col-sm-9">
															<div class="col-sm-4">
			                                                    <input class="default-checkbox danger-checkbox" type="checkbox" name="layout" id="kzdk-all" checked="" value="">
			                                                    <label for="kzdk-all">全选</label>
			                                                </div>
		                                                </div>
													</div>
												</div>
											</div>
											<div class="row">
												 <div class="col-md-12 col-sm-12">
										 			<div class="form-group">
											          <div class="col-sm-9">
														 <div class="form-group check-box">
														 	[#list yhbkList as item]
															<div class="col-sm-4">
			                                                    <input class="default-checkbox primary-checkbox" type="checkbox" name="bkdm" id="kzdk-${item.bkdm}" checked="" value="${item.bkdm}">
			                                                    <label for="kzdk-${item.bkdm}">${item.bkmc}</label>
			                                                </div>
														 	[/#list]
			                                            </div>
			                                            
											          </div>
											          <div class="col-sm-2">
											          	<button class="btn zf-btn btn-primary btn-light-primary" type="button" id="zxwt-dc-btn">导出EXCEL</button>
											          </div>
											        </div>
											      </div>
											</div>	 
										</form>
									</div>
									
									<div id="demo-dror-tab-2" class="tab-pane fade">
										<form id="ajaxForm-dr" action="batchZxwtUpload.zf" method="post" class="form-horizontal sl_all_form">
											<div class="row">
												 <div class="col-md-12 col-sm-12">
										 			<div class="form-group" id="upload-block">
											          <div class="col-sm-9">
											          	<input type="file" name="uploadfile" id="zxwt-file">
											          </div>
											          <div class="col-sm-2">
											          	<button class="btn zf-btn btn-primary btn-light-primary" type="button" id="zxwt-dr-btn">导入</button>
											          </div>
											        </div>
											      </div>
											</div>	 
										</form>
									</div>
								</div>
							</div>
				 	
			      </div>
			</div>	 
	</body>
	<script>
		jQuery(function(){
		
			jQuery('#kzdk-all').off('click').on('click', function(data){
				jQuery(':checkbox[id^="kzdk-"]').not('#kzdk-all').attr('checked', this.checked);
			});
			
			jQuery(':checkbox[id^="kzdk-"]').not('#kzdk-all').off('click').on('click', function(){
				jQuery(':checkbox[id^="kzdk-"]').not('#kzdk-all').each(function(index, el){
					if(!el.checked){
						jQuery('#kzdk-all').attr('checked', el.checked);
					}
				});
			});
			
			jQuery('#zxwt-file').filestyle({
				buttonText: '文件',
				buttonName: 'btn-default'
			});

			jQuery('#zxwt-dc-btn').off('click').on('click', function(){
			
				if(jQuery(':checkbox[id^="kzdk-"]:checked').not('#kzdk-all').length == 0){
					jQuery.alert('请选择咨询板块！');
					return false;
				}
			
				jQuery("#ajaxForm-dc").submit();
			});
			
			jQuery('#zxwt-dr-btn').off('click').on('click', function(){
				var fileflag = false;
				$("input[name='uploadfile']").each(function(){
				    if($(this).val()!="") {
				        fileflag = true;
				        return false;
				    }
				});
				
				if(!fileflag){
					jQuery('#upload-block').addClass('has-error');
					return false;
				}
				
				jQuery('#zxwt-dr-btn').html('处理中...').attr("disabled","disabled");
				
		        jQuery.ajaxFileUpload({  
		            url:'batchZxwtUpload.zf',  
		            secureuri:false,  
		            fileElementId:'zxwt-file',
		            dataType: 'json',
		            data:{},
		            success: function (data, status) {
		            	if(data['status'] === 'success'){
		            		jQuery.success('批量处理成功！');
		            		$("input[name='uploadfile']").val('');
		            	}else{
		            		jQuery.alert('批量处理失败！');
		            	}
		            	jQuery('#zxwt-dr-btn').html('导入').removeAttr("disabled");
		            },  
		            error: function (data, status, e) {
		            	jQuery.alert('批量处理失败！');
		            	jQuery('#zxwt-dr-btn').html('导入').removeAttr("disabled");
		            }  
		        });  
				
				
			});
		});
	</script>
</html>