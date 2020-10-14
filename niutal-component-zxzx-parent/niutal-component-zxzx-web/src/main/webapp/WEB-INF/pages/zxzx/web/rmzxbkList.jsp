<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="demo_xxq_right">
	<h3 class="title_08">
		<span>热门咨询板块</span>
	</h3>
	<div class="recommend_xxq">
		<ul id="qunRankingId" class="rmbk">
			<s:iterator value="kzdkList" id="kzdk" status="iterator">
				<li><a href="###" datatype="zxzx-kzdk" datavalue="${kzdk.bkdm}">${kzdk.bkmc}【${kzdk.zxwtCount}】</a></li>
			</s:iterator>
		</ul>
	</div>
</div>