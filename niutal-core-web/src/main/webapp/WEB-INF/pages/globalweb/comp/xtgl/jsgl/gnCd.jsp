<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
</head>
<s:form action="/xtgl/jsgl_cxJsxx.html" method="post" theme="simple" id="gnsqForm">
<div class="main_function" id="mkzx">
  <input type="hidden" id="qx" name="qx" value="no" />
  <input type="hidden" id="jsdm" name="jsdm" value="<e:forHtmlAttribute value="${model.jsdm }"/>" />
  <input type="hidden" id="doType" name="doType" value="save" />
  <input type="hidden" id="pkValue" name="pkValue" value="<e:forHtmlAttribute value="${model.jsdm }"/>"/>
  
  <div class="function_list01">
    <h3><span>功能分配</span></h3>
    <ul>
		<s:iterator value="allGnmkList" id="gnmkOne" status="aa">
			  <li <s:if test="#aa.index==0">class="current"</s:if> >
			  	<input id="chec_${gnmkOne.gnmkdm }_lv1" value="${gnmkOne.gnmkdm }" type="checkbox" onclick="checkboxClick(this);"/>
			  	<a href="#" id="${gnmkOne.gnmkdm }" onclick="changeMk(this);return false;" >${gnmkOne.gnmkmc }</a>
			  </li>
		</s:iterator>
    </ul>
  </div>
  
  <div class="function_list02" id="" style="overflow: scroll;overflow-x:hidden; height: ${height }px">
  	<table width="100%" border="0">
  		<s:iterator value="allGnmkList" id="gnmkOne" status="index01">
  			<tbody id="tbody_${gnmkOne.gnmkdm }">
			<s:iterator value="#gnmkOne.childList"  id="gnmkTwo" status="index02">
				<s:iterator value="#gnmkTwo.childList" id="gnmkThr" status="index03">
					<tr id="${gnmkOne.gnmkdm }_${index03}" class="tr_02" 
					<s:if test="#index01.index!=0">style="display: none"</s:if> >
						<s:if test="#index03.index==0">
							<td style="width:120px" rowspan="${gnmkTwo.childSize }" class="list_02">
					            <label>
					              <input type="checkbox" value="${gnmkTwo.gnmkdm }" id="${gnmkOne.gnmkdm }_${gnmkTwo.gnmkdm }_lv2" class="${gnmkOne.gnmkdm }" onclick="checkboxClick(this);"/>
					              	${gnmkTwo.gnmkmc }
					       </td>
						</s:if>
						<td style="width:150px" class="list_03">
							<label>
					    	<input type="checkbox" value="${gnmkThr.gnmkdm }" name='sjgndmcbv' id="${gnmkTwo.gnmkdm }_${gnmkThr.gnmkdm }_lv3" class="${gnmkTwo.gnmkdm }" onclick="checkboxClick(this);" />
					        	${gnmkThr.gnmkmc }
					        </label>
					    </td>
					    
					    <td>
					    	<ul>
					    	
					    		<s:iterator value="#gnmkThr.btnList" id="btn">
						    		<li>
						    			<input type="checkbox" name="btns" value="${gnmkThr.gnmkdm }_${btn.czdm}" id="${gnmkThr.gnmkdm }_${btn.czdm}_lv4" onclick="checkboxClick(this);"
						    			 
						    			class="${gnmkThr.gnmkdm }"></input>${btn.czmc }
						    			
						    			<input type="hidden" id ="cz_btn"  name="cz_btn" value='${czans}'/>
						    			
						    	</li>
					    		</s:iterator>
					    			
					    	</ul>
					    </td>
					</tr>
				</s:iterator>
			</s:iterator>
			</tbody>
		</s:iterator>
  	</table>
  </div>
  <div class="function_list03"></div>
</div>
</s:form>
<script type="text/javascript" defer="defer">
	init();
</script>

