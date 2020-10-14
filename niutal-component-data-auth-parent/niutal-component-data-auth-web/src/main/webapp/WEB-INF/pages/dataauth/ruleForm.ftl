<!doctype html>
<html>
<head>
</head>
<body>
	<form id="ajaxForm" method="post" data-toggle="validation"  data-widget='{"onkeyup": false}' 
		action="${base}/dataauth/data/rule/save.zf" theme="simple" class="form-horizontal ">
		<input type="hidden" name="ruleId" value="${rule.ruleId?default("")}"/>
    	<input type="hidden" name="groupId" value="${groupId?default("")}"/>
		<div class="panel-body">
			<div class="row">
				<div class="col-sm-12">
					<pre style="color:red;">
*设置拦截方法表达式时请注意同一个方法只能被拦截一次，如果是关联给不同类型（用户/角色）的权限则可被分别拦截一次且用户优先与角色
*SQL中可使用的占位信息及说明：
  {yhm} -- 当前用户名
  {jsdm} -- 当前角色代码
  {jsdms} -- 用户为多角色时的当前的角色代码串，例：'js1','js2'
  {items} -- 子选项值，例：'x1','x2'
					</pre>
				</div>
		        <div class="col-sm-12">
		            <div class="form-group">
		                <label>拦截方法正则表达式</label>
		                <input type="text" placeholder="拦截方法正则表达式(多个以逗号分隔)" name="methodRegexs" class="form-control"  value="${rule.methodRegexs?default("")}" data-rules='{"required":true}'>
		            </div>
		        </div>
		        <div class="col-sm-12">
			    	<label>前置SQL</label>
			        <textarea placeholder="前置SQL(被追加到源SQL之前)" name="prepositionSql" rows="3" class="form-control">${rule.prepositionSql?default("")}</textarea>
			    </div>
		        <div class="col-sm-12">
			    	<label>后置SQL</label>
			        <textarea placeholder="后置SQL(被追加到源SQL之后)" name="postpositionSql" rows="3" class="form-control">${rule.postpositionSql?default("")}</textarea>
			    </div>
			    <div class="col-sm-12">
		            <div class="form-group">
		                <label>替换SQL正则表达式</label>
		                <input type="text" placeholder="替换SQL正则表达式(多个以逗号分隔)" name="replaceRegexs" class="form-control"  value="${rule.replaceRegexs?default("")}">
		            </div>
		        </div>
		        <div class="col-sm-12">
			    	<label>替换的SQL</label>
			        <textarea placeholder="替换的SQL(替换源SQL,多个以逗号分隔)" name="replaceSqls" rows="3" class="form-control">${rule.replaceSqls?default("")}</textarea>
			    </div>
		    </div>
		</div>
	</form>
</body>
</html>