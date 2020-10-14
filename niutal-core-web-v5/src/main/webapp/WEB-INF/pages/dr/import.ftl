<!DOCTYPE html>
<html>
	<head>
		<title>导入</title>
		<style>
			#rule_table tbody {  
	   			display:block;  
	   			overflow-y:scroll;
	   			height:250px;
			}    
			#rule_table thead,#rule_table tbody tr {
			    display:table;
			    width:100%;
			    table-layout:fixed;
			}
			#rule_table thead{  
	   			width: calc( 100% - 1em );
			}
		</style>
	</head>
	<body>
		<div class="export">
			<form name="importForm" id="importForm" action="niutal/dr/import/importData.zf" method="post" enctype="multipart/form-data">
				 <input type="hidden" id="drlpzs" name="drlpzs" />
				 <input type="hidden" id="drlpzids" name="drlpzids" />
				 <input type="hidden" id="crfs" name="crfs" />
				 <input type="hidden" id="drzx" name="drzx" />
				 <input type="hidden" id="version" name="version" value="${version}" />
				 <input type="hidden" id="resultFileId" name="resultFileId" />
				 <input type="hidden" id="totalUnAcceptRowSize" name="totalUnAcceptRowSize" />
				 <div class="export-title"><h5><i class="glyphicon glyphicon-import"></i> 导入数据</h5></div>
				 <table class="table table-bordered table-condensed table-import">
			        <tbody>
			            <tr>
			                <td class="text-right">模板名称</td>
			                <td>
			                 <#if importList!=null && importList?size == 1 >
			               	 	${importList[0].drmkmc}
								<input id="drmkdm" name="drmkdm" type="hidden" value="${importList[0].drmkdm}" />

			                <#elseif importList!=null && importList?size >1 >
								<select id="drmkdm" name="drmkdm">
								<#list importList as item>
									<option value="${item.drmkdm}">${item.drmkmc}</option>
								</#list>
								</select>
							<#else>
								导入模板未配置
							</#if>
			                </td>
			            </tr>
			            <tr>
			                <td class="text-right">模板下载</td>
			                <td>
			                	<a href="javascript:void(0);" onclick="downloadTemplate();" class="text-primary">下载excel模板</a>
			                </td>
			            </tr>
			            <tr>
			                <td class="text-right">上传数据</td>
			                <td>
			                <input type="file" name="importFile" id="importFile" style="width: 200px;display:inline-block;" />
			                <span class="red">提示：允许导入Excel文件</span></td>
			            </tr>
			        </tbody>
	    		</table>
	    		
	    		<table class="table table-bordered table-condensed table-import" id="dr_result">
	    			<tbody>
						<tr>
							<td>
								导入结果
							</td>
							<td id="importResult" style="font-weight: bold">
								&nbsp;&nbsp;无
							</td>
						</tr>
						<tr>
							<td>
								异常数据
							</td>
							<td id="cwsj">
								<font>无错误数据<font/>
							</td>
						</tr>
					</tbody>
				</table>
	    		
	    		<div class="export-title"><h5><i class="glyphicon glyphicon-info-sign"></i> 导入规则</h5></div>
				<div style="height:300px">
				<table class="table table-condensed table-import-rule" id="rule_table">
			        <thead>
			        	<tr>
			            	<th style="width:15%">列名称</th>
			            	<th style="width:10%;text-align:center">主键</th>
			            	<th style="width:10%;text-align:center" align="center">是否唯一</th>
			            	<th style="width:10%;text-align:center" align="center">不可为空</th>
			            	<th style="width:10%;text-align:center" align="center">最大长度</th>
			            	<th style="width:45%">数据格式</th>
			        	</tr>
			        </thead>
			        <tbody>
			        </tbody>
			    </table>
			    </div>
    		</form>
		</div>
	</body>
	<!--jQuery.ajaxFileupload -->
	<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/plugins/upload/ajaxfileupload-min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
	<script type="text/javascript" src="${base}/js/dr/import.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	<script type="text/javascript">
		$(function(){
			showRule();
			$('#dr_result,#dr_result_title').hide();
		});
	</script>
</html>
