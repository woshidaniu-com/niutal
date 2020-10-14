<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<style type="text/css">
			.formlist td {vertical-align: top;} 
			 ul { margin:0px; padding:0px; margin-left:0px; }
			.ul1_class, .ul2_class { width:350px; list-style-type:none; margin:0px; }
			.ul1_class li, .ul2_class li { float:left; padding:5px; width:100px; height:25px; }
			.ul1_class div, .ul2_class div { width:90px; height:12px; border:solid 1px black; background-color:#E0E0E0; text-align:center; padding-top:3px; }
			.ul2_class { float:right; }
			.placeHolder div { background-color:white !important; border:dashed 1px gray !important; }
		</style>
		<script type="text/javascript" src="<%=systemPath %>/js/jquery/dragsort/jquery.dragsort-0.4.min.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/jquery/jquery.form.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
		function save(){
			 jQuery(".ul2_class").remove();
			 var url=_path+'/zfxg/wjdc/wjpz_saveWjpz.html';
			 ajaxSubForm("page_form",url);
			 //var form=document.getElementById("page_form");
			// form.action=url;
			 //form.submit();
		}
		
		function tz(lxid){
			jQuery("#lxid").val(lxid);
			var url=_path+'/zfxg/wjdc/wjpz_wjpz.html';
			//var form=document.getElementById("page_form");
			//form.action=url;
			//form.submit();
			url+="?lxid="+lxid
			refRightContent(url);
		}
		
		function saveOrder() {
			//var data = $("#list1 li").map(function() { return $(this).children().html(); }).get();
			//$("input[name=list1SortOrder]").val(data.join("|"));
		};
		
		jQuery(function(){
			var lxid=jQuery("#lxid").val();
			jQuery("#li_"+lxid).attr("class","ha");	
			
			//jQuery("ul:not(:first)").dragsort();	
			
			var gnlbdms=jQuery("input[name='gnlbdm']");
			for(var i=0;i<gnlbdms.length;i++){
				var gnlbdm=jQuery(gnlbdms[i]).val();
				jQuery("#ul1_cxtj_"+gnlbdm+", #ul2_cxtj_"+gnlbdm).dragsort({ dragSelector: "li", dragBetween: true, dragEnd: saveOrder, placeHolderTemplate: "<li class='placeHolder'><div></div></li>" });
				jQuery("#ul1_cxjg_"+gnlbdm+", #ul2_cxjg_"+gnlbdm).dragsort({ dragSelector: "li", dragBetween: true, dragEnd: saveOrder, placeHolderTemplate: "<li class='placeHolder'><div></div></li>" });
			}
		})
		
		</script>
	</head>

	<body>
		<div class="tab_cur">
			<p class="location">
				<em>您的当前位置:</em><a>${currentMenu}</a>
			</p>	
		</div>
		<form id="page_form" name="page_form" action="" method="post">
		
		<div class="compTab">
			<input type="hidden" id="lxid" name="lxid" value="${lxid}"/>
			<div class="comp_title">
				<ul>
					<s:iterator value="wjlxList" id="wjlx">
						<li id="li_${wjlx.lxid}">
							<a href="#" onclick="tz('${wjlx.lxid}')">
								<span>${wjlx.lxmc}</span>
						    </a>
						</li>
					</s:iterator>
				</ul>
			</div>
			<div class="comp_con">
				<s:iterator value="gnlbpzList" id="gnlbpz">
				<table width="100%" border="0" class="formlist">
					<thead>
						<tr>
							<th colspan="3">
								<span>
									<input type="hidden" name="gnlbdm" value="${gnlbdm}"/>
									${gnlbmc}
								</span>
							</th>
						</tr>
						<tr>
							<th></th>
							<th>已选择字段</th>
							<th>可选择字段</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th width="5%">
								查<br/>询<br/>条<br/>件<br/>配<br/>置
							</th>
							<td width="45%" valign="top">
								<ul id="ul1_cxtj_${gnlbdm}" class="ul1_class">
									<s:iterator value="cxtjpzList" id="cxtjpz">
										<s:if test="sfqy==1">
										<li>
											<div class="btn_common">
												<input name="cxtj_${gnlbdm}" type="hidden" value="${zd}"/>${zdmc}
											</div>
										</li>
										</s:if>
									</s:iterator>
								</ul>
							</td>
							<td width="45%" valign="top">
								<ul id="ul2_cxtj_${gnlbdm}" class="ul2_class">
									<s:iterator value="cxtjpzList" id="cxtjpz">
										<s:if test="sfqy!=1">
										<li>
											<div class="btn_common">
												<input name="cxtj_${gnlbdm}" type="hidden" value="${zd}"/>${zdmc}
											</div>
										</li>
										</s:if>
									</s:iterator>
								</ul>
							</td>
						</tr>
						<tr>
							<th width="5%">
								查<br/>询<br/>结<br/>果<br/>配<br/>置
							</th>
							<td width="45%" valign="top">
								<ul id="ul1_cxjg_${gnlbdm}" class="ul1_class">
									<s:iterator value="cxjgpzList" id="cxjgpz">
										<s:if test="sfqy==1">
										<li>
											<div class="btn_common">
												<input name="cxjg_${gnlbdm}" type="hidden" value="${zd}"/>${zdmc}
											</div>
										</li>
										</s:if>
									</s:iterator>
								</ul>
							</td>
							<td width="45%" valign="top">
								<ul id="ul2_cxjg_${gnlbdm}" class="ul2_class">
									<s:iterator value="cxjgpzList" id="cxjgpz">
										<s:if test="sfqy!=1">
										<li>
											<div class="btn_common">
												<input name="cxjg_${gnlbdm}" type="hidden" value="${zd}"/>${zdmc}
											</div>
										</li>
										</s:if>
									</s:iterator>
								</ul>
							</td>
						</tr>
						
					</tbody>
					<tfoot>
						<tr>
							<td colspan="4">
								<div class="bz">
									"
									<span class="red">*</span>"为必填项
								</div>
								<div class="btn">
									<button type="button" name="提交" onclick="save()">
										提 交
									</button>
								</div>
							</td>
						</tr>
					</tfoot>
				</table>
				</s:iterator>
				
<!--				<table width="100%" border="0" class="formlist">-->
<!--					-->
<!--				</table>-->
			</div>

		</div>
	</form>
	</body>
</html>
