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
								流程业务名称
							</label>
							<div class="width-70 inline">
								<input class="form-control" id="name" name="name
									maxlength="20"/>
							</div>
						</div>
					</div>
					
					<div class="col-lg-3 col-md-3 col-sm-6">
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
		
		<div class="alert alert-warning well" role="alert">
		  <strong> 说 明 </strong>
		     <p>【定义】业务功能维护了一组和一种业务相关的字段						</p>
		     <p>【场景】这些字段在流程中会被使用,用户业务处理，  流程流转条件处理等      	</p>
		     <p>【时间】一般在功能交付给客户之前就已经初始化，不需要客户自己设置		</p>
		     <p>【条件】单独开发此功能为了方便开发人员								</p>
		     <p>【设置】交付给及客户后，该功能应该设置为只读状态					</p>
		</div>
				
		<!--JQGrid 开始 -->
		<div class="formbox">
			<table id="tabGrid"></table>
		</div>
		<!--JQGrid 结束  -->
		[#include "/globalweb/head/niutal-ui-bsTable.ftl" /]
		<script type="text/javascript" src="${base}/js/processManagement/biz.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>
