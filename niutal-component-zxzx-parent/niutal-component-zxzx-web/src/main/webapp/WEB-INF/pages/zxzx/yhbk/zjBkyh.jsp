<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript">
			jQuery(function(){
				var bkdm = jQuery('input[name="bkdm"]').val();
				var WfpGrid = Class.create(BaseJqGrid,{  
						caption : "用户列表",
						rowNum : 15,
						multiselect : true,
						pager: "pager",
				        url:  _path+'/zxzx/yhbk_zjBkyh.html?doType=query&bkdm='+bkdm,
				        colModel:[
				        	 {label:'用户名',name:'zgh', index: 'zgh',key:true,align:'center'},
						     {label:'姓 名',name:'xm', index: 'xm',align:'center'},
					      	 {label:'联系电话',name:'lxdh', index: 'lxdh',align:'center'},
					      	 {label:'所属机构',name:'jgmc', index: 'jgmc',align:'center'}
						],
						sortname: 'zgh',
			         	sortorder: "desc"});
				var wfpGrid=new WfpGrid();
				loadJqGrid("#tabGrid","#pager",wfpGrid);
				
				/**保存*/
				jQuery('#btn_bc').click(function(){
					var ids = getChecked();
					if (ids.length > 0) {
						var params = {
							"zgh" : ids.toString(),
							"bkdm" : jQuery('input[name="bkdm"]').val()
						};
						showConfirmDivLayer("是否添加选中用户?", {
							okFun : function() {
								jQuery.post("_path+'/zxzx/yhbk_zjBcBkyh.html", params, function(data) {
										alert(data);
										jQuery("#tabGrid").jqGrid().trigger('reloadGrid');
										var api = frameElement.api, W = api.opener;
										W.jQuery("#search_go").click();
								}, 'text');
							}
						});
					} else {
						alert("请选择用户!");
					}
				});
				
				/**检索*/
				jQuery('#search_go').click(function(){
					var map = {};
					map["zgh"] = jQuery('#zgh').val();
					map["xm"] = jQuery('#xm').val();
					map["jgdm"] = jQuery('#jgdm').val();
					search('tabGrid',map);
					return false;
				});
			});
		</script>
	</head>
	<body>
		<div class="toolbox">
			<div class="buttonbox">	
				<ul>
					<li><a href="#" class="btn_sq" id="btn_bc">保存</a></li>
				</ul>
			</div> 
			<div class="searchtab">
	          <s:form id="fpyhForm" namespace="/zxzx" action="yhbk_zjBkyh" theme="simple">
		          <s:hidden name="bkdm" id="bkdm"></s:hidden>
		          <table width="100%">
		            <tbody>
		              <tr>
		                <th width="10%">用户名</th>
		                <td width="18%">
		                	<input type="text" name="zgh" id="zgh" style="width:130px"/>
		                </td>
		                <th width="10%">姓   名</th>
		                <td width="18%">
		                	<input type="text" name="xm" id="xm" style="width:130px"/>
		                </td>
		                <th width="10%">所属机构</th>
		                <td width="18%">
		                	<s:select list="jgdmsList" id="jgdm" name="jgdm" listKey="bmdm_id" listValue="bmmc" headerKey="" headerValue="全部" cssStyle="width:150px"></s:select>
		                </td>
		                 <td width="16%">
		                  <div class="btn">
		                    <button class="btn_cx" id="search_go">
								查 询
							</button>
		                  </div></td>
		              </tr>
		            </tbody>
		          </table>
	          </s:form>
        	</div>
		</div>
		<div class="formbox " style="width: 680px;margin-top: 2px">
			<table id="tabGrid" style="width: 100%"></table>
			<div id="pager" style="width: 100%"></div>
		</div>
	</body>
</html>