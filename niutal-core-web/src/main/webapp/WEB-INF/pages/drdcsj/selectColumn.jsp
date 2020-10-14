<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript"
			src="<%=systemPath %>/js/drdcsj/selectColumn.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
			jQuery(function() {
				loadSelectColumn();
			});
		</script>
		<style type="text/css">
.select {
	font: 12px "宋体";
	margin: 0 0 5px 5px;
	padding: 4px 5px;
	background: none repeat scroll 0 0 #fcfcfc;
	border: 1px solid #5d8cba;
	color: #666666;
	display: inline-block;
}

.select:hover {
	border: 1px solid #5d8cba;
	color: #5d8cba;
	text-decoration: none;
}

.noselect {
	font: 12px "宋体";
	margin: 0 0 5px 5px;
	padding: 4px 5px;
	background: none repeat scroll 0 0 #fcfcfc;
	border: 1px solid red !important;
	color: #666666;
	display: inline-block;
}

.noselect:hover {
	text-decoration: none;
	color: #666666;
}
.dis{
	font: 12px "宋体";
	margin: 0 0 5px 5px;
	padding: 4px 5px;
	background: url("<%=stylePath %>images/payment_sea_li.gif") no-repeat
		scroll right bottom #DBDBDB;
	border: 1px solid #5d8cba;;
	color: #666666;
	text-decoration: none;
	display: inline-block;
}
.dis:hover{
	text-decoration: none;
	color: #666666;
}
.cur {
	font: 12px "宋体";
	margin: 0 0 5px 5px;
	padding: 4px 5px;
	background: url("<%=stylePath %>images/payment_sea_li.gif") no-repeat
		scroll right bottom ;
	border: 1px solid #5d8cba;
	color: #5d8cba;
	text-decoration: none;
	display: inline-block;
}
.cur:hover{
	color: #5d8cba;
	text-decoration: none;
}
</style>
	</head>
	<body>
		<s:form name="importForm" id="importForm"
			action="niutal/dr/import_importData.html" method="post"
			enctype="multipart/form-data" theme="simple">
			<input type="hidden" id="drmkdm" name="drmkdm" value="${drmkdm}" />
			<div class="tab" style="overflow-x: hidden;">
				<table width="100%" border="0" class=" formlist" cellpadding="0"
					cellspacing="0">
					<tbody>
						<tr>
							<th style="width: 20%;">
								导入方式
							</th>
							<td name="crfstd">

							</td>
						</tr>
						<tr>
							<th align="center">
								操作
							</th>
							<td>
								<label style="cursor:pointer; ">
								<input type="radio" id="selectall" name="select" onclick="selectAll();" />
								全选
								</label>
								<label style="cursor:pointer; ">
								 <input type="radio" id="cleanselect" name="select" onclick="unselect();" /> 
								 清空
								 </label>
							</td>
						</tr>
						<tr>
							<th>
								待导入列
							</th>
							<td id="drl">
							</td>
						</tr>
						<tr>
							<th>
								提示
							</th>
							<td id="message">
								无
							</td>
						</tr>
						<tr>
							<th>帮助信息</th>
							<td bgcolor="#fcf7d9">
								默认选中&rarr;<a href="javascript:void(0);" class="dis">xx列名</a>&nbsp;&nbsp;
								可选列&rarr;<a href="javascript:void(0);"  class="select">xx列名</a>&nbsp;&nbsp;
								错误列&rarr;<a href="javascript:void(0);" class="noselect"><font color="red">xx列名</font></a>&nbsp;&nbsp;
								<br/>
								<br/>
								1.	导入方式为 ‘插入’ 或  ‘插入并更新’ 时，必填项默认全部选中，且选中状态不可取消，不必填项可选择插入或更新。
								<br/>
								<br/>
								2.	导入方式为 ‘更新’ 时，主键项默认选中，且选中状态不可取消, 其他项可选择更新。
								<br/>
								<br/>
								3.	当导入数据中存在其他错误数据列时，系统将提示 ‘没有对应列，此列不导入’， 并且该列不能导入。
								<br/>
								<br/>
								4. 当导入时，缺少必要的必填项列时，系统将提示 ‘必填列，列必须存在导入模板中’ 。
								
							</td>
						</tr>
					</tbody>
				</table>
				<div>
					<table width="100%" border="0" class="formlist" id="below"
						style="position: fixed; _position: absolute; bottom: 0;">
						<tfoot>
							<tr>
								<td colspan="2">
									<div class="btn">
										<button type="button" onclick="saveselect();" id="save">
											确定导入
										</button>
										<button type="button" onclick="iFClose();" id="buttonClose">
											关 闭
										</button>
									</div>
								</td>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>

		</s:form>
	</body>
</html>