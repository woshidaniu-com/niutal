<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript"
			src="<%=systemPath%>/js/globalweb/comm/operation.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/jsgl.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/jsgl_fpyh.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
			jQuery(function(){
				var _jsdm = jQuery('input[name="jsdm"]').val();
				var WfpGrid = Class.create(BaseJqGrid,{  
						caption : "用户列表",
						rowNum : 15, // 每页显示记录数
						multiselect : true, // 是否支持多选
						pgbuttons:true,//是否显示用于翻页的按钮
						pginput:true,
						rowList : [15,30 , 50, 100], // 可调整每页显示的记录数	
						pager: "wfpPager", //分页工具栏  
				        url:  _path+'/xtgl/jsgl_fpyhWfpYhxx.html?jsdm='+_jsdm, //这是Action的请求地址  
				        colModel:[
				        	 {label:'用户名',name:'zgh', index: 'zgh',key:true,align:'center'},
						     {label:'姓 名',name:'xm', index: 'xm',align:'center'},
					      	 {label:'联系电话',name:'lxdh', index: 'lxdh',align:'center'},
					      	 {label:'所属机构',name:'jgmc', index: 'jgmc',align:'center'}
						],
						sortname: 'zgh', //首次加载要进行排序的字段 
			         	sortorder: "desc"});
	         	
				addOptionTitle();
				var wfpGrid=new WfpGrid();
				loadJqGrid("#wfpGrid","#wfpPager",wfpGrid);
			});
		</script>
	</head>
	<body>
		<div class="toolbox">
			<div class="buttonbox">	
				<ul>
					<li><a href="#" class="btn_sq" id="btn_bc" onclick="jsyhSqSubmit();return false;">授权</a></li>
				</ul>
			</div> 
			<div class="searchtab">
	          <s:form id="fpyhForm" method="post" action="/xtgl/yhgl_cxYhxx.html" theme="simple">
		          <input type="hidden" name="jsdm" value="<e:forHtmlAttribute value="${model.jsdm }"/>"/>   	
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
		                    <button class="btn_cx" id="search_go"
									onclick="searchWfpResult();return false;">
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
			<table id="wfpGrid" style="width: 100%"></table>
			<div id="wfpPager" style="width: 100%"></div>
		</div>
	</body>
</html>