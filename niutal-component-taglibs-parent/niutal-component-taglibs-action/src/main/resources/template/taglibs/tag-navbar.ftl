<#if parameters.func_list?exists && (parameters.func_list?size > 0 ) >
	<#if (parameters.func_list?size > 1) >
		<#list parameters.func_list as func> 
			<#-- 第一个元素 -->
			<#if (func_index == 0) >
			<div class="navbar-header">
				<button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
					<span class="sr-only"> ${parameters.ancdModel.gnmkmc?default('')}</span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span>
				</button>
				<a href="#" class="navbar-brand">${parameters.ancdModel.gnmkmc?default('')}</a>
				<script type="text/javascript">
					document.title="${parameters.ancdModel.gnmkmc?default('')}";
				</script>
			</div>
			<#-- 显示类型：0：菜单样式，1：页签样式 -->
			<#elseif (parameters.xslx != '1') && (func_index > 0) >
				<#if (func_index == 1) >
					<nav class="navbar-collapse bs-navbar-collapse collapse" role="navigation" style="">
						<ul class="nav navbar-nav" id="navbar-nav">
							<li class="active">
								<a href="#" id="topButton" onclick="onClickMenu.call(this,'${func.dyym?default('')}','${func.gnmkdm?default('')}')">
									${func.gnmkmc?default('')}
								</a>
							</li>
				<#else>
				<li><a href="#" onclick="onClickMenu.call(this,'${func.dyym?default('')}','${func.gnmkdm?default('')}')"> ${func.gnmkmc?default('')} </a></li>
				</#if>
				<#if ( (parameters.func_list?size - func_index) == 1 ) >
					</ul>
				</nav>
				</#if>
			</#if>						
		</#list>
	<#else>
		<div class="container">
			<div class="navbar-header">
				<button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
					<span class="sr-only"> ${parameters.ancdModel.gnmkmc?default('')}</span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span>
				</button>
				<a href="#" id="topButton" class="navbar-brand" onclick="onClickMenu('${parameters.ancdModel.dyym?default('')}','${parameters.ancdModel.gnmkdm?default('')}')">
					${parameters.ancdModel.gnmkmc?default('')}
				</a>
				<script type="text/javascript">
					document.title="${parameters.ancdModel.gnmkmc?default('')}";
				</script>
			</div>
		</div>
	</#if>
<#else>
	<div class="container">
		<div class="navbar-header">
			<button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
				<span class="sr-only"> ${parameters.ancdModel.gnmkmc?default('')}</span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</button>
			<a href="#" id="topButton" class="navbar-brand" onclick="onClickMenu('${parameters.ancdModel.dyym?default('')}','${parameters.ancdModel.gnmkdm?default('')}')">
				${parameters.ancdModel.gnmkmc?default('')}
			</a>
			<script type="text/javascript">
				document.title="${parameters.ancdModel.gnmkmc?default('')}";
			</script>
		</div>
	</div>
</#if> 
<!-- navbar-end  -->