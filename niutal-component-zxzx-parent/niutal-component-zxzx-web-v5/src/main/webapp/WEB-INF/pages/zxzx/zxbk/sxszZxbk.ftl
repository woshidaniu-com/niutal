<!doctype html>
<html>
	<head>
		<script type="text/javascript">
			jQuery(function() {
				jQuery("#selectUl").dragsort({
					dragSelector : "li",
					dragBetween : true,
					placeHolderTemplate : '<li class="list-group-item list-group-item-secondary"></li>'
				});
			});
		</script>
		
		<style>
			
		</style>
	</head>
	<body>

		<div class="alert alert-dark role="alert">
  			<strong>提示: </strong>点击拖拽咨询板块
		</div>
		<form id="ajaxForm_szsxkzdk" class="form-horizontal sl_all_form">
			<div class="row fieldlist" >
				
				<div class="col-md-12 col-sm-12">
					<ul id="selectUl" class="list-group">
						[#list kzdkList as item]
					  	<li class="list-group-item"  data-bkdm="${item.bkdm}" data-xsxs="${item.xsxs}" >
					  		<label>${item.bkmc}</label>
					  	</li>
					  	[/#list]
					</ul>
					
				</div>

			</div>	
		</form>
	</body>
</html>
