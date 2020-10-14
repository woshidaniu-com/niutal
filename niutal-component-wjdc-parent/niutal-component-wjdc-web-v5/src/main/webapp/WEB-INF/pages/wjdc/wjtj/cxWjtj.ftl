[#assign q=JspTaglibs["/niutal-search-tags"] /]
<!DOCTYPE html>
<html>
	<head>
		[#include "/globalweb/head/niutal-ui-laydate.ftl" /]
		
	</head>
	<body>
		<!--按钮 开始 -->
		[#include "/globalweb/comm/buttons.ftl" /]
		<!--查询条件  开始 -->
		[@q.panel theme="default"] 
		     [@q.input list="#(wjmc:问卷名称,cjrmc:创建人)"/] 
		     [@q.radio name="fblx" text="发布类型" list="#(0:普通,1:匿名)" listKey="name" listValue="text"/]
		     [@q.date name="cjsj" text="创建时间" format="YYYY-MM-DD"/]
		     [@q.date name="gqsj" text="过期时间" format="YYYY-MM-DD"/]
		[/@q.panel]
		<!--查询条件  结束  -->
		<div class="formbox">
            <table id = "tabGrid"></table>
		</div>
		[#include "/globalweb/head/bsTable.ftl" /]
		<script type="text/javascript" >
			var ops = {
				 url: 'getWjtjList.zf',         //请求后台的URL（*）\
				 uniqueId: "wjid",                     //每一行的唯一标识，一般为主键列
				 cookieIdTable:'wjdc-wjgl',
				 toolbar:  '#but_ancd',
				 cookie:true,
				 columns: [
		            {checkbox: true }, 
		            {field: 'wjmc', title: '问卷名称',sortable:true}, 
		            {field: 'fblx',title: '发布类型',sortable:true,formatter:function(v,r,i){
		            	var text = v == "0"  ? "普通" : "匿名";
		            	return text;
		            }}, 
		            {field: 'cjrmc',title: '创建人',sortable:true},
		            {field: 'cjsj',title: '创建时间',sortable:true},
		            {field: 'gqsj',title: '过期时间',sortable:true},
		            {field: 'ffzt',title: '发放状态',sortable:true,formatter:function(v,r,i){
		            	var text = v==0 ? "未发放" : "已发放";
		            	return text;
		            }}
		         ],
		         searchParams:function(){
		        	 return $.search.getSearchMap();
		         }
			};
			
			$('#tabGrid').loadGrid(ops);
			$("#search_button").bind("click",searchResult);
		</script>
		[#include "/globalweb/head/niutal-ui-kindeditor.ftl" /]
	</body>
</html>
