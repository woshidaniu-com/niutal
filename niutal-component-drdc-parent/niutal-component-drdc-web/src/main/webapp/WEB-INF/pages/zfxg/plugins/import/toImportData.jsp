<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/import.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/progress.js"></script>
		<script type="text/javascript">
			jQuery(function(){
			});

			//导入数据
			function importData(){
				if(notNullImportTable() && notNullImportFile()){
					lock();
					var tips;
					setTimeout(function(){
						tips = alertTips(null,null,"数据执行中...");
					},100);
					
					var myDate = new Date();
					var barkey="import"+myDate.toLocaleString();
					jQuery("#importResult").html("<font color='red'>导入开始，请勿关闭本窗口！</font>");
					showProgress();
					//加载进度条					
				 	loadBar(barkey,function(progress){
				 		if(progress.finish){
				 			jQuery("#importResult").html("");
				 			if(progress.message!="-1"){
				 				alert(progress.message);
				 			}else{
					 			var data=progress.data[barkey];
					 			if(data != null){
									var drms = jQuery("#drms").val();
									setImportRsulte(data,drms,barkey);
								}else{
									setImportRsulte(null);
								}
								//回调导入函数
								callbackImportFunction();
							}
							unlock();
							finishBar(barkey);
							tips.close();
				 		}
				 		return true;
				 	});
					var jqFrom=jQuery("#importForm");
					var url = _path+"/niutal/drdc/import_importData.html";
					jqFrom.ajaxSubmit({
						url:url,
						type:"post",
						dataType:"json",
						async:true,
						data:{barkey:barkey},
						success:function(data){
						}
					});
				}
			}

			//返回导入函数
			function callbackImportFunction(){
				if(frameElement.api){
					var api = frameElement.api,W = api.opener;
					if(W.importFunction){
						W.importFunction();
					}else{
						W.jQuery('#search_go').click();
					}
				}
			}

		</script>
	</head>
	<body>
		<s:form name="importForm" id="importForm" action="/niutal/drdc/import_importData.html"
			method="post" enctype="multipart/form-data" theme="simple">
			<s:hidden name="drmkdm" id="drmkdm"></s:hidden>
			<!-- isImport: 0:导入数据  1:已导入数据 -->
			<input type="hidden" id="isImport" value="0" />
			<input type="hidden" id="drwjgs" name="drwjgs" value="" />
			<!-- 错误数据数量 -->
			<input type="hidden" id="errorDataNum"  name="errorDataNum" value="0" />
			<input type="hidden" id="drms" name="drms" value="0" />
			<input type="hidden" id="drzs" name="drzs" value="0" />
			<input type="hidden" id="cgs" name="cgs" value="0" />
			<input type="hidden" id="cws" name="cws" value="0" />
			<div class="tab">
				<table width="100%" border="0" class=" formlist" cellpadding="0"
					cellspacing="0">
					<thead>
						<tr>
							<td colspan="4">
								<span>导入数据</span>
							</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th width="20%">
								选择模板
							</th>
							<td colspan="3">
								<s:if test="impTableList != null">
									<s:if test="%{impTableList.size() == 1}">
										<s:property value="impTableList[0].drbzwmc"/>
										<s:hidden name="drbm" id="drbm" value="%{impTableList[0].drbm}"></s:hidden>
									</s:if>
									<s:else>
										<s:select list="impTableList" listKey="drbm" listValue="drbzwmc" name="drbm" id="drbm"
											headerKey="" headerValue="请选择" cssStyle="width:150px;"></s:select>
									</s:else>
								</s:if>
							</td>
						</tr>
						<tr>
							<th>
								导入模板
							</th>
							<td colspan="3">
								<a href="javascript:void(0);" class="name" onclick="downloadTemplate();" >下载EXCEL模板</a>
								&nbsp;&nbsp;
								<a href="javascript:void(0);" class="name" onclick="downloadTemplate('dbf');" >下载DBF模板</a>
							</td>
						</tr>
						<tr>
							<th>
								上传数据
							</th>
							<td colspan="3">
								<input type="file" name="importFile" id="importFile" style="width:300px;"/> 
							</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="4">
								<div class="btn">
									<button name="btn_tj" onclick="importData();" type="button">
										导 入
									</button>
								</div>
							</td>
						</tr>
					</tfoot>
				</table>
				
				<table width="100%" border="0" class=" formlist" cellpadding="0" style="margin-top: 5px;"
					cellspacing="0">
					<thead>
						<tr>
							<td colspan="4">
								<span>导入结果</span>
							</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th width="20%">
								结果统计
							</th>
							<td colspan="3" id="importResult">
								无
							</td>
						</tr>
						<tr>
							<th>
								失败数据
							</th>
							<td colspan="3">
								<a href="javascript:void(0);" class="name" onclick="downloadErrorData();">下载失败数据</a>
							</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="4">
								<div class="btn">
									<button name="btn_tj" onclick="iFClose();" type="button">
										关 闭
									</button>
								</div>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
			<%@include file="/WEB-INF/pages/zfxg/plugins/progress/progressBar.jsp"%>
			<s:if test="result != null && result != ''">
				<script type="text/javascript">
					jQuery(function() {
						alert('${result}', '', {
							clkFun : function() {
								refershParent();
							}
						});
					});
				</script>
			</s:if>
		</s:form>
	</body>
</html>
