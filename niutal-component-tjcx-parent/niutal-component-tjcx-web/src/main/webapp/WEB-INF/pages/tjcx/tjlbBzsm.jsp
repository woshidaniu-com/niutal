<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
</head>
<body>
<div class="readme">
  <h2>目录</h2>
  <hr>
    <ul>
      <li><a href="#topic_1" style="color: red">1. &lt固定总数合计&gt 查询选项使用说明</a></li>
    </ul>
</div>
<div class="lanmu_main">
	<div class="text_help">
          <div class="con">
              <h3 id="topic_1" name="topic_1">1. &lt固定总数合计&gt 查询选项使用说明</h3>
              <p>当用户设置<strong> 过滤条件 </strong>和<strong> 报表列 </strong>，并在<strong> 数据展现模式 </strong>勾上<strong> 百分比 </strong>,<br>
			  <p>进入统计页面后输出的数据默认设置为基准数据,如果用户在过滤条件中添加筛选设置,该选项可以固定纵向总数列的分母基数计算百分比。<br>	
			  <b>如下图所示：</b></p>
			  <p style="font-size: 15px;font-weight: bold;">①</p>
			  <p><img src="<%=systemPath %>/images/tjcx/tjlb_bzsm_1.jpg"><br></p>
			  <p style="font-size: 15px;font-weight: bold;">②</p>
			  <p><img src="<%=systemPath %>/images/tjcx/tjlb_bzsm_2.jpg"><br></p>
			  <p style="font-size: 15px;font-weight: bold;">③</p>
			  <p><img src="<%=systemPath %>/images/tjcx/tjlb_bzsm_3.jpg"><br></p>
          </div>
     </div>
</div>
</body>
</html>