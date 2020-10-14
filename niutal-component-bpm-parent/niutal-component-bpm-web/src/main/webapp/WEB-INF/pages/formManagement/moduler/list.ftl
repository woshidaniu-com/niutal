<!DOCTYPE html>
<html>
	<head>
	</head>
	<body>
	
		<!--按钮 开始 -->
		[#include "/globalweb/comm/buttons.ftl" /]
		<!--查询条件  开始 -->
		<form class="form-horizontal sl_all_form simple-search-form">
				<div class="row">
					<div class="col-lg-3 col-md-3  col-sm-6">
						<div class="form-group">
							<label for="" class="control-label">
								表单名称
							</label>
							<div class="width-70 inline">
								<input class="form-control" id="searchModelName" name="searchModelName
									maxlength="20"/>
							</div>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-3  col-sm-6">
						<div class="form-group">
							<label for="" class="control-label">
								部署状态
							</label>
							<div class="width-70 inline">
								<select class="form-control input-sm span3 chosen-select" name="searchDeploymentState" id="searchDeploymentState">
									<option value=""> 全 部 </option>
									<option value="0"> 未部署 </option>
									<option value="1"> 已部署 </option>
								</select>
								<SCRIPT type="text/javascript">
						    		jQuery('#searchDeploymentState').trigger("chosen");
			 			    	</SCRIPT>
							</div>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-1 col-sm-6">
						<label for="" class="control-label">
							&nbsp;
						</label>
					  	<div class="search-btn">
					  		<button type="button" class="btn btn-primary btn-sm" id="search_go"	onclick="searchResult();return false;"> 查 询 </button>
					  	</div>
					</div>
				</div>
			</form>
		<!--查询条件  结束  -->
		<form id="model-export-form" method="post"></form>
		<!--JQGrid 开始 -->
		<div class="formbox">
			<table id="tabGrid"></table>
		</div>
		<!--JQGrid 结束  -->
		[#include "/globalweb/head/niutal-ui-bsTable.ftl" /]
		[#include "/globalweb/head/niutal-ui-filestyle.ftl" /]
		<script type="text/javascript" src="${base}/js/formManagement/moduler.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>
