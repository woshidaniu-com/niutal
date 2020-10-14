<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript">
			var YhxxGrid = Class.create(BaseJqGrid,{  
				caption : "已分配用户",
				pager: "pager", 
		        url:_path+'/zxzx/yhbk_fpBkyh.html?doType=query&bkdm='+jQuery('#bkdm').val(),
		        rowNum : 50,
		        colModel:[
		        	 {label:'用户名',name:'zgh', index: 'zgh',key:true},
		        	 {label:'姓名',name:'xm', index: 'xm',align:'center'},
		        	 {label:'联系电话',name:'lxdh', index: 'lxdh',align:'center'},
		        	 {label:'所属部门',name:'jgmc', index: 'jgmc',align:'center'}
				],
				sortname: 'xm',
		     	sortorder: "asc"
			});
			
			jQuery(function(){
				var yhxxGrid = new YhxxGrid();
				loadJqGrid("#tabGrid","#pager",yhxxGrid);
				
				jQuery('#btn_zj').click(function(){
					var url = _path+'/zxzx/yhbk_zjBkyh.html?bkdm='+jQuery('#bkdm').val();
					showWindow('添加用户',740,520,url);
				});

				jQuery('#btn_sc').click(function(){
					var ids = getChecked();
					if (ids.length > 0) {
						var params = {
							"zgh" : ids.toString(),
							"bkdm" : jQuery('#bkdm').val()
						};
						showConfirmDivLayer("是否删除选中用户?", {
							okFun : function() {
								jQuery.post(_path+'/zxzx/yhbk_scBcBkyh.html', params, function(data) {
										alert(data);
										jQuery("#search_go").click();
								}, 'text');
							}
						});
					} else {
						alert("请选择用户!");
					}
				});

				jQuery('#btn_fh').click(function(){
					refRightContent(_path+'/zxzx/yhbk_cxYhbk.html');
				});
			});
			
			function searchResult(){
				var map = {};
				map["zgh"] = jQuery('#zgh').val();
				map["xm"] = jQuery('#xm').val();
				map["jgdm"] = jQuery('#jgdm').val();
				search('tabGrid',map);
			}
		</script>
	</head>

	<body>
			<div class="toolbox">
				<div class="buttonbox">	
					<ul>
					<li><a class="btn_fh" id="btn_fh">返回</a></li>
					<li><a class="btn_zj" id="btn_zj">添加用户</a></li>
					<li><a class="btn_sc" id="btn_sc">删除用户</a></li>
					</ul>
				</div>
	        </div> 
			<div class="searchtab">
				<s:form namespace="/zxzx" action="kzdk_cxkzdk" theme="simple">
				<table width="100%" border="0" id="searchTab">
					<tbody>
						<tr>
							<th>用户名</th>
							<td>
								<s:textfield name="zgh" id="zgh" theme="simple"></s:textfield>
							</td>
							<th>姓名</th>
							<td>
								<s:textfield name="xm" id="xm" theme="simple"></s:textfield>
							</td>
							<th width="10%">所属机构</th>
			                <td width="18%">
			                	<s:select list="jgdmsList" id="jgdm" name="jgdm" listKey="bmdm_id" listValue="bmmc" headerKey="" headerValue="全部" cssStyle="width:180px"></s:select>
			                </td>
							<td>
								<div class="btn">
									<button type="button" class="btn_cx" id="search_go"
										onclick="searchResult();return false;">
										查 询
									</button>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<br />
				<table width="100%" border="0" style="border:0 !important;">
					<tbody>
						<tr>
							<th><b>版块切换:</b></th>
							<td>
								<s:select list="kzdkList" id="bkdm" name="bkdm" listKey="bkdm" listValue="bkmc"  cssStyle="width:180px;height:25px;background-color:#E8F0FB;"></s:select>
								<script type="text/javascript">
			               			jQuery('#bkdm').change(function(){
			               				refRightContent(_path+'/zxzx/yhbk_fpBkyh.html?bkdm='+jQuery(this).val());
			               			});
			               		</script>
							</td>
						</tr>
					</tbody>
				</table>
				</s:form>
			</div>

		<div class="formbox">
			<table id="tabGrid"></table>
			<div id="pager"></div>
		</div>
	</body>
</html>
