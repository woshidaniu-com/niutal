<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
				<!--分页显示-->
				<div class="pagination">
					<div class="pageleft">
						<!-- 全选反选位置 -->
						<div class="choose">
						</div>
						<p class="pagenum">
							<input type="text" id="queryModel_currentPage" name="queryModel.currentPage" value="${queryModel.currentPage}" 
								style="color: red; text-align: center; width: 20px;" onkeypress="if(event.keyCode==13){submitEnterPage();}"/>
							/
							<span class="red">${queryModel.totalPage}</span>页， 每页显示
							<input type="text" id="queryModel_showCount" name="queryModel.showCount" style="width: 20px;" maxlength="2" 
							value="${queryModel.showCount}" onkeypress="if(event.keyCode==13){submitEnterPage();}"/>
							条 / 共
							<span class="red">${queryModel.totalResult}</span>条记录
						</p>
					</div>

					<div class="pageright">
						<!-- 分页位置 -->
						<div id="pagediv" class="paging">
							<span id="pagelist" class="pagelist"></span>
							<a id="page_first" href="javascript:submitFirstPage()" class="first"
								title="首页">首 页</a>
							<a id="page_pre" href="javascript:submitPrePage()" class="prev"
								title="上一页">上一页</a>
							<a id="page_next" href="javascript:submitNextPage()"
								class="next" title="下一页">下一页</a>
							<a id="page_last" href="javascript:submitLastPage()" class="last"
								title="末页">末 页</a>
						</div>
					</div>
				</div>

				<script type="text/javascript">
					var init_showCount = parseInt("${queryModel.showCount}"); //每页显示记录数
					var init_totalPage = parseInt("${queryModel.totalPage}");		//总页数
					var init_currentPage = parseInt("${queryModel.currentPage}");	//当前页
					
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
						jQuery("#search_go").click();
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
						}
						if(init_currentPage==init_totalPage){
							window.document.getElementById('page_next').disabled = "disabled";
							document.getElementById('page_next').onclick = "";
							window.document.getElementById('page_last').disabled = "disabled";
							document.getElementById('page_last').onclick = "";
						}
					})
				</script>