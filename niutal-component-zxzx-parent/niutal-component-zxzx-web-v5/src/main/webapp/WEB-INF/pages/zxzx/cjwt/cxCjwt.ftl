<!doctype html>
<html>
	<head>
		<style type="text/css">
			.inline-w{
				display: inline-block;
    			width: 70%;
			}
		</style>
	</head>

	<body>
			<!-- tool bar-start  -->
			[#include "/globalweb/comm/buttons.ftl" /]
			<!-- tool bar-end  -->
			
				<form class="form-horizontal sl_all_form simple-search-form">
				<div class="row" id="searchBox">
					
				<div class="col-md-3 col-sm-4">
					<div class="form-group">
						<label for="" class="control-label">
							咨询板块
						</label>
						<div class="inline-w">
								<select  class="form-control  input-sm chosen-select" name="bkdm"  id="bkdm">
									<option value="">全部</option>
									[#list bkdmList as item]
										<option value="${item.bkdm}">${item.bkmc}</option>
									[/#list]
								</select>
								<SCRIPT type="text/javascript">
						    		jQuery('#bkdm').trigger("chosen");
			 			    	</SCRIPT>
						</div>
					</div>
				</div>


				
				<div class="col-md-3 col-sm-4">
					<div class="form-group">
						<label for="" class="control-label">
							启用状态
						</label>
						<div class="inline-w">
								<select  class="form-control  input-sm chosen-select" name="sffb"  id="sffb">
									<option value="">全部</option>
									<option value="1">启用</option>
									<option value="0">关闭</option>
								</select>
								<SCRIPT type="text/javascript">
						    		jQuery('#sffb').trigger("chosen");
			 			    	</SCRIPT>
						</div>
					</div>
				</div>


<!-- 				<div class="col-md-3 col-sm-4"> -->
<!-- 					<div class="form-group"> -->
<!-- 						<label for="" class="control-label"> -->
<!-- 							咨询主题 -->
<!-- 						</label> -->
<!-- 						<div class="inline width-70"> -->
<!-- 								<input type="text" class="form-control" id="wtbt" name="wtbt"/> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				
				<div class="col-sm-3">
				  	<div class="search-btn">
				  		<button type="button" class="btn btn-primary btn-sm" id="search_go"	> 查 询 </button>
				  	</div>
				</div>
							
				</div>
				</form>

		<!-- table-start  -->
			<table id="tabGrid"></table>
		<!-- table-end  -->
		[#include "/globalweb/head/niutal-ui-bsTable.ftl" /]
		<script type="text/javascript" src="${base}/js/zxzx/cjwt.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>
