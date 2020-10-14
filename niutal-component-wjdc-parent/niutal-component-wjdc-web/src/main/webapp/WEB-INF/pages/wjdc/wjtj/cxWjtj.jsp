<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/dateformat.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/textClue.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/select.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/jquery/jquery.form.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/dateUtils.js?_rv_=<%=resourceVersion%>"></script>
		<link rel="stylesheet" href="<%=systemPath %>/css/plugins/textClue.css?_rv_=<%=resourceVersion%>" type="text/css" media="all" />
		<script type="text/javascript">
			var WjxxGrid = Class.create(BaseJqGrid,{  
				caption : "问卷信息列表",
				pager: "pager", //分页工具栏  
			    url: _path+'/zfxg/wjdc/wjtj_cxWjtj.html?doType=query',  
			    colModel:[
			         {label:'ID',name:'wjid', index:'wjid', key:true, hidden:true},
				     {label:'问卷名称 ',name:'wjmc', index:'wjmc'},
				   //  {label:'问卷类型',name:'wjlxmc', index: 'wjlxmc',width:50},
			      	 {label:'问卷状态',name:'wjzt', index: 'wjzt',width:50},
			      	 {label:'发布类型',name:'fblxmc', index: 'fblxmc',width:50},
			      	 {name:'fblx', index: 'fblx',hidden:true},
			      	 {label:'发放人数',name:'ffrs', index: 'ffrs',width:50},
			      	 {label:'已回答人数',name:'yhdrs', index: 'yhdrs',width:50},
			      	 {label:'创建人',name:'cjrxm', index: 'cjrxm',width:50},
			      	 {label:'创建时间',name:'cjsj', index: 'cjsj',width:50}
				],
				sortname: 'wjmc' //首次加载要进行排序的字段 
			});
			//按钮绑定
			function bdan() {
				var btn_wjdjtj=jQuery("#btn_wjdjtj");//答卷统计
				var btn_wjdjxq=jQuery("#btn_wjdjxq");//答卷详情
				var btn_wjsttj=jQuery("#btn_wjsttj");//试题统计
				var btn_wjjctj=jQuery("#btn_wjjctj");//交叉统计
				var btn_wjjccspztj=jQuery("#btn_wjjccspztj");//交叉统计参数配置
				var btn_wjdjqkdc=jQuery("#btn_wjdjqkdc");//问卷答题情况导出
				
				
				if(btn_wjdjtj != null){btn_wjdjtj.click(function(){wjtj("djtj");});}
				if(btn_wjdjxq != null){btn_wjdjxq.click(function(){wjtj("djxq");});}
				if(btn_wjsttj != null){btn_wjsttj.click(function(){wjtj("sttj");});}
				if(btn_wjjctj != null){btn_wjjctj.click(function(){wjtj("jctj");});}
				if(btn_wjjccspztj != null){btn_wjjccspztj.click(function(){wjtj("jctjcspz");});}
				if(btn_wjdjqkdc != null){btn_wjdjqkdc.click(function(){wjdjqkdc();})}
			}
			//数据加载
			jQuery(function(){
				var wjxxGrid = new WjxxGrid();
				loadJqGrid("#tabGrid","#pager",wjxxGrid);
				bdan();
			});

			//问卷统计			
			function wjtj(url){
				var id = getChecked();
				if(id.length != 1){
					alert('请先选定一条记录!');
					return false;
				}
				var row = jQuery("#tabGrid").jqGrid('getRowData', id);
				var ffrs = row.ffrs;
				//发布类型，‘1’代表匿名方式
				var fblx = row.fblx;
				//修改目标，让答卷详情和答卷统计支持匿名问卷
				//if(url != 'sttj' && fblx == '1'){
				//	alert("该问卷为匿名问卷,只支持使用试题统计!");
				//	return false;
				//}
				if(ffrs == "0"){
					if(fblx == '1'){
						alert("该问卷暂无用户作答信息，不能统计!");
					}else{
						alert("该问卷未添加分发人，不能统计!");
					}
					return false;
				}
				
				if(fblx == '1' && url == 'djxq'){
					refRightContent(_path+'/zfxg/wjdc/wjtj_djxqNmwj.html?wjid='+id);
				}else if(fblx == '1' && url == 'djtj'){
					refRightContent(_path+'/zfxg/wjdc/wjtj_djtjNmwj.html?wjid='+id);
				}else if(fblx == '1' && url == 'jctj'){
					refRightContent(_path+'/zfxg/wjdc/wjtj_jctjNmwj.html?wjid='+id);
				}else{
					refRightContent(_path+'/zfxg/wjdc/wjtj_'+url+'.html?wjid='+id);
				}
			}

			//问卷答题情况导出
			function wjdjqkdc(){
				var id = getChecked();
				if(id.length != 1){
					alert('请先选定一条记录!');
					return false;
				}
				var row = jQuery("#tabGrid").jqGrid('getRowData', id);
				var yhdrs = row.yhdrs;
				//发布类型，‘1’代表匿名方式
				//var fblx = row.fblx;
				//if(fblx == '1'){
				//	alert("该问卷为匿名问卷,不能导出！");
				//	return false;
				//}
				if(yhdrs == "0"){
					alert("该问卷暂无用户作答信息，不能导出!");
					return false;
				}

				var jqFrom = jQuery("#wjtjForm");
				var url = _path + '/zfxg/wjdc/wjtj_wjdjxqdc.html?wjid='+id;
				jqFrom.attr("action", url);
				jqFrom.submit();
			}
			
			function searchResult() {
				var map = {};
				map["wjmc"] = jQuery('#wjmc').val();
			//	map["wjlx"] = jQuery('#wjlx').val();
				map["wjzt"] = jQuery('#wjzt').val();
				map["cjrxm"] = jQuery('#cjrxm').val();
				map["cjkssj"] = jQuery('#cjkssj').val();
				map["cjjssj"] = jQuery('#cjjssj').val();
				search('tabGrid',map);
			}
			//设置招聘信息链接
			function setLink(cellvalue, options, rowObject){
				var wjid = rowObject.wjid;
				var ffrs = rowObject.ffrs;
				return "<a target='_blank' href='<%=systemPath %>/zfxg/wjdc/wjffgl_cxWjffxx.html?wjid="+wjid+"'>"+cellvalue+"</a>";
			}
		</script>
	</head>

	<s:form namespace="/zfxg/wjdc" action="wjffgl_cxWjffxx" method="post" theme="simple" id="wjtjForm">
	<body>
		<div class="tab_cur">
			<p class="location">
				<em>您的当前位置:</em><a>问卷调查 - 问卷统计</a>
			</p>	
		</div>
		
		<div class="toolbox">
			<!-- 加载当前菜单栏目下操作     -->
			<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
			<!-- 加载当前菜单栏目下操作 -->

			<div class="searchtab">
		          <table width="100%">
		            <tfoot>
					<tr>
                <td colspan="6">
                 </td>
              </tr>
		            </tfoot>
		            <tbody>
		              <tr>
		                <th>问卷名称</th>
							<td>
								<s:textfield name="wjmc" id="wjmc"   cssStyle="width:150px" maxlength="20"></s:textfield>
							</td>
							<th>问卷状态</th>
							<td>
								<s:select list="#{'':'','草稿':'草稿','发布':'发布','运行':'运行','停止':'停止' }" cssStyle="width:150px" name="wjzt" id="wjzt" ></s:select>
							</td>
							<th></th>
							<td></td>
							<!-- 
							<th>问卷类型</th>
							<td>
								<s:select list="#{'':'','CPL':'测评类','DCL':'调查类'}" name="wjlx" cssStyle="width:150px" id="wjlx" ></s:select> 
							</td> 
							 -->
		              </tr>
		              <tr>
		               <th>创建人</th>
							<td>
								<s:textfield name="cjrxm" id="cjrxm"  cssStyle="width:150px" maxlength="10"></s:textfield>
							</td>
							<th>创建时间</th>
							<td colspan="2">
								<input type="text" name="cjkssj" value="" readonly="readonly" id="cjkssj" style="width:80px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/> 至 
								<input type="text" name="cjjssj" value="" readonly="readonly" id="cjjssj"  style="width:80px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
							</td>
							<td colspan="">
								 <div class="btn">
                   <button type="button" id="search_go"
										onclick="searchResult();" >
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
        	</div>

		</div>

		<div class="formbox">
			<table id="tabGrid"></table>
			<div id="pager"></div>
		</div>
	</body>
	</s:form>
</html>
