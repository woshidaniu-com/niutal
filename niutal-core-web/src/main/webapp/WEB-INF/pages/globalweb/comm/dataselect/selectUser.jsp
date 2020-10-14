<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript">
			function toggleTitle(_this,bmdm,fn){
				var $v = jQuery(_this);
				$v.toggleClass('ico_down');
				$v.parent().toggleClass('current');
				var display_status = jQuery('#bm_'+bmdm).attr('display-status');
				var data_loaded = jQuery('#bm_'+bmdm).attr('data-loaded');
				//如果是閉合狀態，需要展示數據
				if(display_status == '0' && data_loaded == '0'){
					jQuery('#bm_'+bmdm).attr('display-status', '1');
					//服務器請求部門人員信息
					if(jQuery('#data_'+bmdm).size() == 0){
						var initialData = _createDataHtml(bmdm);
						$v.parent().after(initialData);
						jQuery.post("commons/q_selectUser.html", { "q_type": "1","q_bmdm": bmdm},
						   function(data){
							 jQuery('#data_'+bmdm).empty();
					   		 if(data!=null && data.length > 0){
						   		jQuery.each(data,function(i,n){
						   			jQuery('#data_'+bmdm).append(_createLiData(n));
							   	});
					   			jQuery('#bm_'+bmdm).attr('data-loaded','1');
							  }else{
								  jQuery('#data_'+bmdm).append(_createNodataHtml());
								  jQuery('#bm_'+bmdm).attr('data-loaded','1');
							   }

						    if(fn != undefined && typeof fn == 'function'){
								fn();
							}
						   }, "json");
						
					}else{
						jQuery('#data_'+bmdm).show();
					}
				}else if(display_status == '0'){
					jQuery('#bm_'+bmdm).attr('display-status', '1');
					jQuery('#data_'+bmdm).show();
				}else if(display_status == '1'){
					jQuery('#bm_'+bmdm).attr('display-status', '0');
					jQuery('#data_'+bmdm).hide();
				}
				
			}

			function toggleTitleCheckbox(_this,bmdm){
				var $v = jQuery(_this);
				var checked = $v.attr('checked');
				var display_status = $v.attr('display-status');
				var data_loaded = $v.attr('data-loaded');
				var fn = function(){
					jQuery('#data_'+bmdm).find('input[data-bmdm]').attr('checked',true);
				}
				if(checked){
					if(data_loaded == '0'){
						toggleTitle($v.prev(),bmdm,fn);
					}else{
						fn();
					}
				}else{
					jQuery('#data_'+bmdm).find('input[data-bmdm]').attr('checked',false);
				}
				
			}

			function dataSelect(){
				var $v = jQuery('#selectedItemList');
				var selectedData = $v.data('SelectDataList');
				if(selectedData == undefined){
					selectedData =  new Array();
					$v.data('SelectDataList', selectedData);
				}
				jQuery('#forSelectedDataItems input[name=p_dataitem]:checked').each(function(i,n){
					if(jQuery.inArray(jQuery(this).data('dataObj')['ZGH'],selectedData) < 0 ){
						$v.append(_createSelectLiItem(jQuery(this).data('dataObj')));
						selectedData.push(jQuery(this).data('dataObj')['ZGH']);
					}
				});
				jQuery('#selected_number').text(selectedData.length);
			}

			function dataDeSelect(){
				var $v = jQuery('#selectedItemList');
				var selectedData = $v.data('SelectDataList');
				jQuery('#selectedItemList li.current').each(function(i,n){
					selectedData.splice(jQuery.inArray(jQuery(this).attr('data-bmdm'),selectedData),1);
					jQuery(this).empty().remove();
				});
				jQuery('#selected_number').text(selectedData.length);
			}

			function getSelectResult(){
				//var api = frameElement.api,W = api.opener;
				var $v = jQuery('#selectedItemList');
				var selectedData = $v.data('SelectDataList');
				if(selectedData!=null&&selectedData.length > 0){
					getParentDialogWin().selectUserCallback(selectedData);
				}
				iFClose();
			}
			
			//創建loading html
			function _createDataHtml(bmdm){
				return jQuery('<ul class="itemlist" id="data_' + bmdm + '"></ul>').append(_createLoadingHtml())
			}

			function _createLoadingHtml(){
				return '<li class="dataloading"><a href="#" style="font-weight: normal;">数据载入中...</a></li>';
			}

			function _createNodataHtml(){
				return '<li class="dataloading"><a href="#" style="font-weight: normal;">--无用户信息--</a></li>';
			}

			function _createSelectLiItem(data){
				return '<li onclick="javascript:jQuery(this).toggleClass(\'current\');" data-bmdm="'+data['ZGH']+'"><a href="#"><span style="background: none;"></span>'+data['XM']+'</a></li>';
			}
			
			function _createLiData(item){
				
				var zgh = item['ZGH'];
				var xm = item['XM'];
				var bmdm = item['BMDM'];
				var li = jQuery('<li></li>').append().append();
				var a = jQuery('<a href="#"></a>');
				var checkbox = jQuery('<input name="p_dataitem" type="checkbox" value="'+ zgh +'" data-bmdm="'+bmdm+'" style="cursor:default;"></input>');
				checkbox.data('dataObj',item);
				a.append(checkbox).append(xm);
				return li.append(a);
			}
		</script>
		<style type="text/css">
			.titlelist .itemlist{
				padding-left: 35px;
			}
		</style>
	</head>
	<body>
		<div style="width: 100%; margin: 5px auto;">
			<table class="fieldlist" width="100%;">
				<tbody>
					<tr>
						<td width="55%">
							<div class="tab_box" style="height: 400px">
								<h3>部门</h3>
								<div class="titlelist" style="height:370px;overflow-y: scroll;overflow-x:hidden;" id="">
									<ul id="forSelectedDataItems">
										<s:if test="bmList != null && bmList.size() > 0">
											<s:iterator id="bm" value="bmList" status="sta">
										 		<li>
													<a href="#">
														<span class="ico" onclick="javascript:toggleTitle(this,'${bm.BMDM_ID}');" ></span>
														<input id="bm_${bm.BMDM_ID}" name="p_bmdm" type="checkbox"  
															onclick="javascript:toggleTitleCheckbox(this,'${bm.BMDM_ID}');" 
															style="cursor: default;"  
															display-status="0" 
															data-loaded="0" 
															data-bmdm="${bm.BMDM_ID}" 
															data-bmrs="${bm.RS}"/>
														${bm.BMMC}[${bm.RS}]
													</a>
												</li>
											</s:iterator>
										</s:if>
									</ul>
								</div>
							</div>
						</td>
						<td width="10%">
							<div class="btn_choose">
								<button class="right" title="单个右移" id="select" onclick="javascript:dataSelect();"></button>
								<br />
								<button class="left" title="单个左移" id="deselect" onclick="javascript:dataDeSelect();"></button>
							</div>
						</td>
						<td width="35%">
							<div class="tab_box" style="height: 400px">
							<h3>已选 [<span id="selected_number">0</span>]</h3>
							<div style="height:370px;overflow-y: scroll;overflow-x:hidden;" class="titlelist">
								<ul id="selectedItemList" >
									<%--<li><a href="#" ><span style="background:none;">1111</span></a></li>
								--%></ul>
							</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<table width="100%" border="0" class="formlist">
			<tfoot>
				<tr>
					<td colspan="4">
						<div class="btn">
							<button type="button" onclick="getSelectResult();return false;" name="btn_tj">
								确 定
							</button>
							<button id="btn_gb" onclick="iFClose(); returnfalse;">
								关 闭
							</button>
						</div>
					</td>
				</tr>
			</tfoot>
		</table></body>
</html>
