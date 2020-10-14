/**
 * 无刷新查询+分页工具条
 * @author Penghui.Qu
 */
(function($) {
	$.dataGrid = $.dataGrid || {};

	$.extend($.dataGrid,{
		rowPop:{},
		params:{},
		loadTbody : function(dataGrid) {
			$.dataGrid.params[dataGrid["prmNames"]["page"]] = dataGrid["currentPage"];
			$.dataGrid.params[dataGrid["prmNames"]["rows"]] = dataGrid["rowNum"];
			$.dataGrid.params[dataGrid["prmNames"]["sort"]] = dataGrid["sortname"];
			$.dataGrid.params[dataGrid["prmNames"]["order"]] = dataGrid["sortorder"];

			var tbody = $("<tbody></tbody>");
			
			$.ajaxSetup({
				async : false
			});
			$.post(
				dataGrid["url"],
				$.dataGrid.params,
				function(data) {
					var items = data[dataGrid["jsonReader"]["root"]];
					var colModel = dataGrid["colModel"];

					if (items != null
							&& items.length > 0) {
						for ( var i = 0; i < items.length; i++) {
							var tr = $("<tr></tr>");
							var rowObject = {};
							var checkbox = $("<td style='text-align:center;'><input type='checkbox' value='"+i+"' id='datagrid-id-"+i+"'/></td>");
							
							for ( var j = 0; j < colModel.length; j++) {
								rowObject[colModel[j]["name"]] = items[i][colModel[j]["name"]];
								if (colModel[j]["key"]){
									checkbox.attr("text-align",items[i][colModel[j]["align"]]);
									checkbox.find("input").val(items[i][colModel[j]["name"]]);
									rowObject["id"]=items[i][colModel[j]["name"]];
								}
							}
							
							if ($.dataGrid["multiselect"]){
								tr.append(checkbox);
							}
							for ( var j = 0; j < colModel.length; j++) {
								var td = $("<td></td>");
									td.css("display",colModel[j]["hidden"] ? "none" : "");
									td.css("word-break","keep-all");
									td.css("white-space","nowrap");
									td.css("text-overflow","ellipsis");
									td.css("overflow","hidden");
								
								var content = items[i][colModel[j]["name"]];
								
								if (colModel[j].formatter != null){
									var formatterFun = colModel[j].formatter;
									var html = formatterFun(content,{},rowObject);
									td.html(html);
								} else {
									td.html(content);
								}
								td.attr("title",content);
								tr.append(td);
							}
							tbody.append(tr);
							$.dataGrid["rowPop"]["datagrid-id-"+i] = rowObject;
						}
					}

					var currentPage = data[dataGrid["jsonReader"]["page"]], 
						totalPage = data[dataGrid["jsonReader"]["total"]], 
						totalResult = data[dataGrid["jsonReader"]["records"]], 
						showCount = dataGrid["rowNum"];

					$.dataGrid.loadPagerGrid(
							currentPage, totalPage,
							totalResult, showCount,
							dataGrid["pager"]);

				}, "json");
			$.ajaxSetup({
				async : true
			});
			return tbody;
		},
		loadThead : function(colModel) {
			
			var thead = $("<thead></thead>");
			var tr = $("<tr></tr>");
			
			if ($.dataGrid["multiselect"]){
				tr.append("<td id='datagrid-select-td' style='width:25px;text-align:center;'><input type='checkbox' id='datagrid-thead-selectAll' onclick=''/></td>");
			}
			
			if (colModel != null && colModel.length > 0) {
				for ( var i = 0; i < colModel.length; i++) {
					var td = $("<td></td>");
						td.addClass("nowrap");
						td.attr("name",colModel[i]["index"]);
						td.css("display",colModel[i]["hidden"] ? "none": "");
						td.css("width",colModel[i]["width"]);
						td.css("text-align","center");
						
						if (colModel[i]["index"].indexOf($.dataGrid["sortname"]) > -1){
							var sortA = $('<a href="javascript:void(0);"></a>');
								sortA.attr("class",$.dataGrid["sortorder"]=="asc" ? "Sort_up" : "Sort_down");
								sortA.html(colModel[i]["label"]);
								td.html(sortA);
						} else {
							td.html(colModel[i]["label"]);
						}
					tr.append(td);
				}
			}
			thead.append(tr);
			return thead;
		},
		loadPagerGrid : function(currentPage, totalPage,
				totalResult, showCount, pagerId) {
			// 分页
			var pager = $("#" + pagerId);
			pager.attr("class", "pagination");
			var pageLeftHtml = "<div class='pageleft'>";
			pageLeftHtml += "<p class='pagenum'>";
			pageLeftHtml += "第<span class='red'>";
			pageLeftHtml += currentPage;
			pageLeftHtml += "</span>页 / 共<span class='red'>";
			pageLeftHtml += totalPage;
			pageLeftHtml += "</span>页， 跳转到   <input type='text' id='pageNumber' size='2' maxlength='2'";
			pageLeftHtml += " /> 页 ，共";
			pageLeftHtml += "<span class='red'>";
			pageLeftHtml += totalResult;
			pageLeftHtml += "</span>条记录";
			pageLeftHtml += "</p>";
			pageLeftHtml += "</div>";
			var pageRightHtml = "<div class='pageright'>";
			pageRightHtml += "<div id='pagediv' class='paging'>";
			pageRightHtml += "<span id='pagelist' class='pagelist'></span>";
			pageRightHtml += "<a href='javascript:void(0);' id='first' class='first' title='首页'>首 页</a>&nbsp;";
			pageRightHtml += "<a href='javascript:void(0);' id='prev' class='prev' title='上一页'>上一页</a>&nbsp;";
			pageRightHtml += "<a href='javascript:void(0);'id='next' class='next' title='下一页'>下一页</a>&nbsp;";
			pageRightHtml += "<a href='javascript:void(0);'id='last' class='last' title='末页'>末 页</a>";
			pageRightHtml += "</div></div>";
			pager.html(pageLeftHtml + pageRightHtml);

			$("#pageNumber").bind("blur", function() {
				if (this.value <= totalPage) {
					$.dataGrid.turnPage(this.value);
				}
			});

			$("#pageNumber").bind(
					"keyup",
					function() {
						this.value = this.value.replace(/[^\d]/g, '');
						if (window.event.keyCode == '13'
								&& this.value <= totalPage
								&& this.value != 0) {
							$.dataGrid.turnPage(this.value);
						}
					});

			if (Number(currentPage) == 1) {
				$("#first").attr("class", "disabled");
				$("#prev").attr("class", "disabled");
			};

			if (Number(currentPage) == Number(totalPage)) {
				$("#next").attr("class", "disabled");
				$("#last").attr("class", "disabled");
			};

			$("#first").bind("click", function() {
				$.dataGrid.turnPage(1);
			});

			$("#prev").bind("click", function() {
				$.dataGrid.turnPage(Number(currentPage) - 1);
			});

			$("#next").bind("click", function() {
				$.dataGrid.turnPage(Number(currentPage) + 1);
			});

			$("#last").bind("click", function() {
				$.dataGrid.turnPage(totalPage);
			});
		},
		turnPage : function(page) {
			$.dataGrid["currentPage"] = page;
			var tbody = $.dataGrid.loadTbody($.dataGrid);
			$.dataGrid.table.find("tbody").remove();
			$.dataGrid.table.append(tbody);
			$.dataGrid.addTbodyEvent($.dataGrid,$.dataGrid.table);
		},
		refDataGrid : function(params) {
			$.dataGrid.params = params;
			var tbody = $.dataGrid.loadTbody($.dataGrid);
			$.dataGrid.table.find("tbody").remove();
			$.dataGrid.table.append(tbody);
			$.dataGrid.addTbodyEvent($.dataGrid,$.dataGrid.table);
		},
		getChecked : function(){
			return $("input[type=checkbox]",$.dataGrid.table.find(".current"));
		},
		getRowData : function(input){
			return $.dataGrid.rowPop[$(input).attr("id")];
		},
		addTheadEvent : function(setting,table){
			
			table.find("thead").find("td[id != datagrid-select-td]").bind("click",function(){
				var aNode = $(this).find("a");
				if (aNode.html() != null){
					var aClass = aNode.attr("class");
					aNode.attr("class",aClass=="Sort_down" ? "Sort_up" : "Sort_down");
					$.dataGrid["sortorder"] = aClass == "Sort_up" ? "desc" : "asc";
				} else {
					var tempNode= table.find("thead").find("td").find("a");
					tempNode.parent("td").html(tempNode.html());
					
					$(this).wrapInner("<a href='javascript:void(0);' class='Sort_up'></a>");
					$.dataGrid["sortorder"] = "asc";
				}
				$.dataGrid["sortname"] = $(this).attr("name");
				var tbody = $.dataGrid.loadTbody($.dataGrid);
				$.dataGrid.table.find("tbody").remove();
				$.dataGrid.table.append(tbody);
				$.dataGrid.addTbodyEvent(setting,$.dataGrid.table);
			});
			$.dataGrid.addTbodyEvent(setting,$.dataGrid.table);
		},
		addTbodyEvent : function(setting,table){
			
			if (setting["multiselect"]){
				$("#datagrid-thead-selectAll").bind("click",function(){
					$(this).parents("table").find("input[type=checkbox]").attr("checked",$(this).attr("checked")=="checked");
					$(this).parents("table").find("tbody").find("tr").attr('class',$(this).attr("checked")=="checked" ? "current" : "");
				});
				table.find("tbody").find("tr").bind("click",function(){
					var trClass = $(this).attr("class")=="current";
					$(this).attr("class",trClass ? "" : "current");
					$(this).find("input[type=checkbox]").attr("checked",!trClass);
				});
			}
		}
	});
	

	var grid = {};
	
	$.fn.loadDataGrid = function(setting) {
		$.dataGrid.table = $(this);
		$.extend($.dataGrid, setting);
		var table = $(this);
		var colModel = setting["colModel"];
		
		table.css("table-layout","fixed");
		table.append($.dataGrid.loadThead(colModel));
		table.append($.dataGrid.loadTbody(setting));
		$.dataGrid.addTheadEvent(setting,table);
		
		grid[this.id] = $.dataGrid;
	};

	$.fn.getDataGrid = function() {
		return grid[this.id];
	};
})(jQuery);


function plcz(url,msg){
	
	var ids = jQuery("#tabGrid").getDataGrid().getChecked();
	if (ids.length == 0){
		alert('请选择您要'+msg+'的记录！');
		return;
	}
	
	var id="";
	for (var i = 0 ; i < ids.length ; i++){
		var row = jQuery("#tabGrid").getDataGrid().getRowData(ids[i]);
		id+=row.id;
		if (i != ids.length - 1){
			id+=",";
		}
	}
	
	var _do = function(){
		jQuery.ajaxSetup({async:false});
		jQuery.post(url,{ids:id},function(data){
			alert(data.toString());
			jQuery("#tabGrid").getDataGrid().refDataGrid({});
		},'json');
		jQuery.ajaxSetup({async:true});
	};
	
	showConfirmDivLayer('您确定要'+msg+'选择的记录吗？',{'okFun':_do});
}
