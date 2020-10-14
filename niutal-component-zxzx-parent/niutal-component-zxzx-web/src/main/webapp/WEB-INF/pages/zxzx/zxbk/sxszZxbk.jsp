<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath%>/js/jquery/dragsort/jquery.dragsort-0.4.min.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
			jQuery(function() {
				jQuery("#selectUl").dragsort({
					dragSelector : "label",
					dragBetween : true,
					placeHolderTemplate : "<li><label class='college_li college_checkbox' style='border:#155FBE 1px dotted;background:#CBE4F8;height:20px;line-height:20px!important;*height:28px;width:140px;padding:3px 0px;text-indent: 15px;'></label></li>"
				});
			});
		
			function kzdk_bc() {
				var postData = {};
				jQuery("#selectUl li").each(function(){
					var dm = jQuery(this).attr("data-bkdm");
					var index = jQuery(this).attr("data-itemidx");
					postData['postData[\''+dm+'\']'] = parseInt(index) + 1;
				});
				console.debug(postData);
				jQuery.post("zxzx/kzdk_sxszBckzdk.html",postData,function(data){
					var api = frameElement.api, W = api.opener;
					alertMessage(data, function() {
						this.close();
						W.jQuery("#search_go").click();
					});
				});
			};
		</script>
	</head>
	<body>
		<div class="toolbox">
			<div class="buttonbox">	
				<ul>
					<li><a href="#" class="btn_sq" id="btn_bc" onclick="kzdk_bc();return false;">保存</a></li>
					<li><a href="#" class="btn_sx" id="btn_sx" onclick="kzdk_refresh();return false;">重置</a></li>
				</ul>
			</div> 
		</div>
		<s:form id="sxszkzdkForm" theme="simple" style="margin-left:5px; margin-right:5px">
			<div class="tab_box" >
				<h3>
					拖拽排序
				</h3>
				<div class="demo_college" style="*position:relative;*z-index:1">
					<div style="height: 365px;">
						<ul id="selectUl">
							<s:iterator value="kzdkList" id="c" status="config">
								<li style="position:relative" data-bkdm="<s:property value="#c.bkdm"/>" data-xsxs="<s:property value="#c.xsxs"/>">
									<label class="college_li college_checkbox" style="height:20px;line-height:20px!important;font-size:12px!important;*height:28px;width:140px;padding:3px 0px;text-indent: 15px;">
										<s:property value="#c.bkmc" />
									</label>
								</li>
							</s:iterator>
						</ul>
					</div>
				</div>
				
				
			</div>	
		</s:form>
		<div class="prompt">
			<h3>
				<span>提示：</span>
			</h3>
			<p>
				<font color="red">
					1.	操作方式：点击拖拽咨询板块；</br>
					2.	顺序说明：从左往右，从上到下排序；
				</font>
			</p>
		</div>
	</body>
</html>
