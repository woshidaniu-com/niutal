<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/znxgl.js?_rv_=<%=resourceVersion%>"/>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/dateformat.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/My97DatePicker/WdatePicker.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
			//收件箱
			var SjxGrid = Class.create(BaseJqGrid,{  
				caption : "收件箱列表",
				pager: "pager", //分页工具栏  
			    url: _path+'/zfxg!plugins/znxgl_sjxZnx.html?doType=query', //这是Action的请求地址  
			    colModel:[
				     {label:'ID',name:'fsbh', index:'fsbh', key:true, hidden:true},
				     {label:'主题',name:'zt', index: 'zt',formatter:setLink},
			      	 {label:'发送人',name:'fsrzgh', index: 'fsrzgh'},
				     {label:'收件时间',name:'fssj', index: 'fssj'},
				     {label:'接收人已读标记',name:'jsrydbj', index:'jsrydbj', key:true, hidden:true}
				],
				sortname: 'sfzd desc,jsrydbj,fssj', //首次加载要进行排序的字段 
			 	sortorder: "desc"
			});
			function setLink(cellvalue, options, rowObject){
				var fsbh = rowObject.fsbh;
				var jsrydbj=rowObject.jsrydbj;
				if("是"==jsrydbj){
					return "<a href='javascript:void(0);' class='jsrydbjClass' onclick=\"showWindow('',850,650,'<%=systemPath%>/zfxg!plugins/znxgl_ckSjxZnx.html?fsbh="+fsbh+"')\" style='text-decoration: underline;'>"+cellvalue+"</a>";
				}else{
					return "<a href='javascript:void(0);' class='jsrydbjClass' onclick=\"showWindow('',850,650,'<%=systemPath%>/zfxg!plugins/znxgl_ckSjxZnx.html?fsbh="+fsbh+"');delTagAStrong(this);\" style='text-decoration: underline;'><strong>"+cellvalue+"</strong></a>";
				}
			}
			function delTagAStrong(tag_a_obj){
				tag_a_obj.innerHTML=tag_a_obj.innerText;
			}
			jQuery(function(){
				var sjxGrid = new SjxGrid();
				loadJqGrid("#tabGrid","#pager",sjxGrid);
				bdan_sjx();
			})
		</script>
	</head>


	<body>
		<div class="tab_cur">
			<p class="location">
				<em>您的当前位置:</em><a>系统管理-系统设置-站内消息</a></div>
		<div class="comp_title">
      		<ul>
       			 <li class="ha"><a href="#" onclick="refRightContent(_path+'/zfxg!plugins/znxgl_sjxZnx.html');return false;"><span>收件箱</span></a></li>
       			 <li><a href="#" onclick="refRightContent(_path+'/zfxg!plugins/znxgl_fjxZnx.html');return false;"><span>发件箱</span></a></li>
     		 </ul>
    	</div>
		<div class="toolbox">
			<!-- 加载当前菜单栏目下操作     -->
<%--				<jsp:include page="/jsp/comm/ancd/ancd.jsp" flush="true" />--%>
				<!-- 按钮操作区 start -->
				<div class="buttonbox">
					<ul id="but_ancd">
							<li>
								<a href="javascript:void(0);" id="btn_xx" class="btn_xx" >写信 </a>
							</li>
							<li>
								<a href="javascript:void(0);" id="btn_hf" class="btn_sh" >回复</a>
							</li>
							<li>
								<a href="javascript:void(0);" id="btn_ck" class="btn_ck" >查看 </a>
							</li>
							<!-- 
							<li>
								<a href="javascript:void(0);" id="btn_bjyd" class="btn_shtg" >标记已读 </a>
							</li>
							 -->
							<li>
								<a href="javascript:void(0);" id="btn_sc" class="btn_sc" >删除 </a>
							</li>
					</ul>
				</div>
				<!-- 按钮操作区 end -->
			<!-- 加载当前菜单栏目下操作 -->

			<div class="searchtab">
				<s:form name="form" method="post" action="/zfxg!plugins/znxgl_sjxZnx.html" theme="simple">
				<table width="100%" border="0" id="searchTab">
					<tbody>
						<tr>
							<th>标题</th>
							<td>
								<input type="text" name="zt" id="zt" value="${zt}"/>
							</td>
							<th>收件日期</th>
							<td>
								<input type="text" name="fskssj" id="fskssj" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
								至
								<input type="text" name="fsjssj" id="fsjssj" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
							</td>
							<td></td>
						</tr>
						<tr>	
							<th>发送人</th>
							<td>
								<input type="text" name="fsrzgh" id="fsrzgh"/>
							</td>
							<th>是否已读</th>
							<td>
								<s:select name="jsrydbj" list="#{'是':'是','否':'否'}"  id="jsrydbj" headerKey="" headerValue="请选择" cssStyle="width:150px;">
								</s:select>
							</td>
							<td>
								<div class="btn">
									<button type="button" id="search_go"
										onclick="searchResult();return false;">
										查 询
									</button>
									<button type="reset" onclick="searchReset();return false;">
										重 置
									</button>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				</s:form>
			</div>
		</div>

		<div class="formbox">
			<!--<h3 class="datetitle_01">
				<span> 查询结果&nbsp;&nbsp; 
					<logic:notEmpty name="rs">
						<font color="blue">提示：单击表头可以排序</font>
					</logic:notEmpty> 
				</span>
			</h3>
			-->
			<table id="tabGrid"></table>
			<div id="pager"></div>
		</div>
	</body>
</html>
