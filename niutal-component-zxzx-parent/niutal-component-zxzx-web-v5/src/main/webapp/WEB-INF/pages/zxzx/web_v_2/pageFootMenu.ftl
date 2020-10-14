<!--分页显示-->
<div class="pagination-box text-right">
	<div class="pageleft" style="display: none">
		<!-- 全选反选位置 -->
		<div class="choose">
		</div>
		<p class="pagenum">
			<input type="text" id="queryModel_currentPage" name="queryModel.currentPage" value="${model.queryModel.currentPage}" 
				style="color: red; text-align: center; width: 20px;" onkeypress="if(event.keyCode==13){submitEnterPage();}"/>
			/
			<span class="red">${model.queryModel.totalPage}</span>页， 每页显示
			<input type="text" id="queryModel_showCount" name="queryModel.showCount" style="width: 20px;" maxlength="2" 
			value="${model.queryModel.showCount}" onkeypress="if(event.keyCode==13){submitEnterPage();}"/>
			条 / 共
			<span class="red">${model.queryModel.totalResult}</span>条记录
		</p>
	</div>
	

	<nav>
	  <ul id="pagediv" class="pagination">
	    <li>
	      <a id="page_first"  href="javascript:submitFirstPage()" aria-label="Previous">
	        <span aria-hidden="true">首 页</span>
	      </a>
	    </li>
	    <li><a id="page_pre" href="javascript:submitPrePage()">上一页</a></li>
	    <li class="disabled">
	    	<a id="page_pre" href="#">${model.queryModel.currentPage}/${model.queryModel.totalPage}页(共：${model.queryModel.totalResult}条记录)</a>
	    </li>
	    <li><a id="page_next" href="javascript:submitNextPage()">下一页</a></li>
	    <li>
	      <a id="page_last" href="javascript:submitLastPage()" aria-label="Next">
	        <span aria-hidden="true">末 页</span>
	      </a>
	    </li>
	  </ul>
	</nav>

	
</div>

<script type="text/javascript">
	var init_showCount = parseInt("${model.queryModel.showCount}"); //每页显示记录数
	var init_totalPage = parseInt("${model.queryModel.totalPage}");		//总页数
	var init_currentPage = parseInt("${model.queryModel.currentPage}");	//当前页
	
	//更改当前页或每页显示记录数回车提交
	function submitEnterPage(){
		//处理当前页
		var currentPage=jQuery("#queryModel_currentPage").val();
		if(currentPage.match(/^[1-9][0-9]*$/)==null){
			jQuery("#queryModel_currentPage").val(1);
		}else{
			if(currentPage<1){
				jQuery("#queryModel_currentPage").val(1);
			}else if(currentPage>init_totalPage){
				jQuery("#queryModel_currentPage").val(init_totalPage);
			}
		}
		
		//处理每页显示记录数
		var showCount=jQuery("#queryModel_showCount").val();
		if(showCount.match(/^[1-9][0-9]?$/)==null){
			jQuery("#queryModel_showCount").val(init_showCount);
		}else{
			if(showCount<1){
				jQuery("#queryModel_showCount").val(1);
			}
		}
		submitPageForm();
	}
	
	//首页
	function submitFirstPage(){
		jQuery("#queryModel_currentPage").val(1);
		jQuery("#queryModel_showCount").val(init_showCount);
		submitPageForm();
	}
	//上一页
	function submitPrePage(){
		var currentPage=1;
		if(init_currentPage>1){
			currentPage=init_currentPage-1;
		}
		jQuery("#queryModel_currentPage").val(currentPage);
		jQuery("#queryModel_showCount").val(init_showCount);
		submitPageForm();
	}
	//下一页
	function submitNextPage(){
		var currentPage=1;
		if(init_currentPage<init_totalPage){
			currentPage=init_currentPage+1;
		}
		jQuery("#queryModel_currentPage").val(currentPage);
		jQuery("#queryModel_showCount").val(init_showCount);
		submitPageForm();
	}
	//末页
	function submitLastPage(){
		jQuery("#queryModel_currentPage").val(init_totalPage);
		jQuery("#queryModel_showCount").val(init_showCount);
		submitPageForm();
	}
	//提交分页表单
	function submitPageForm(){
		if(jQuery('#zxzx_web_form').find('input[name="layout"]').size() == 0){
			jQuery('#zxzx_web_form').append('<input type="hidden" name="layout" value="zxzxWebindexLayout"/>');
		}
		jQuery("#zxzx_web_form").submit();
	}
	
	//填补表格空行
	function appendSpaceRow(){
		var dataTable_class="dateline";
		var dataTable=jQuery(".dateline>tbody").first();
		if(!dataTable){
			dataTable=jQuery(".datelist>tbody").first();
			dataTable_class="datelist";
			if(!dataTable){
				return false;
			}
		}
		var colCount=jQuery("."+dataTable_class+">thead>tr").first().children("td:visible").length;
		var checkboxMark=false;
		if(jQuery("."+dataTable_class+">thead>tr").first().children("td:visible").first().children("input[type='checkbox']").length==1){
			checkboxMark=true;
		}
		var spaceRowHtml="";
		if(checkboxMark){
			spaceRowHtml+="<td><input type='checkbox' disabled='disabled'></td>";
			colCount--;
		}
		for(var i=0;i<colCount;i++){
			spaceRowHtml+="<td>&nbsp;</td>";
		}
		
		var rowStartIndex=jQuery(dataTable).children("tr:visible").length;
		for(var i=rowStartIndex;i<init_showCount;i++){
			jQuery(dataTable).append("<tr>"+spaceRowHtml+"</tr>");
		}
	}
	
	//初始化分页的按钮
	jQuery(function(){
		appendSpaceRow();
		if(init_currentPage<=1){
			window.document.getElementById('page_first').disabled = "disabled";
			document.getElementById('page_first').onclick = "";
			window.document.getElementById('page_pre').disabled = "disabled";
			document.getElementById('page_pre').onclick = "";
			document.getElementById('pagediv').style.display="none";
		}
		if(init_currentPage==init_totalPage){
			window.document.getElementById('page_next').disabled = "disabled";
			document.getElementById('page_next').onclick = "";
			window.document.getElementById('page_last').disabled = "disabled";
			document.getElementById('page_last').onclick = "";
		}
	})
</script>