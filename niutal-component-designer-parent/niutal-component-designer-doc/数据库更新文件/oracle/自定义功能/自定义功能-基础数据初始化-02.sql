/*清空表结构*/
delete from niutal_query_fields;
delete from niutal_autocomplete_fields;
delete from niutal_widget_details;
delete from niutal_widget_parameters;
delete from niutal_widget_resources;
commit;

/*基础查询字段*/
insert into niutal_query_fields (FIELD_GUID, FIELD_ID, FIELD_NAME, FIELD_TEXT, FIELD_LIST, FIELD_LISTKEY, FIELD_LISTVALUE, FIELD_HEADERKEY, FIELD_HEADERVALUE, FIELD_SCOPE, FIELD_SOURCE, FIELD_CLASS, FIELD_DESC, FIELD_PLACEHOLDER, FIELD_NAME_WIDTH, FIELD_UPDATE_URL)
values ('143AFA8CE716FF2BE050007F01007721', 'jg_id', 'jg_id', '学院', 'jg_id_list', 'jg_id', 'jgmc', '', '全部', '1', 'APP:', '', '', '', '4', '');
insert into niutal_query_fields (FIELD_GUID, FIELD_ID, FIELD_NAME, FIELD_TEXT, FIELD_LIST, FIELD_LISTKEY, FIELD_LISTVALUE, FIELD_HEADERKEY, FIELD_HEADERVALUE, FIELD_SCOPE, FIELD_SOURCE, FIELD_CLASS, FIELD_DESC, FIELD_PLACEHOLDER, FIELD_NAME_WIDTH, FIELD_UPDATE_URL)
values ('143AFA8CE71BFF2BE050007F01007721', 'zyh_id', 'zyh_id', '专业', 'zyh_id_list', 'zyh_id', 'zymc', '', '全部', '1', 'APP:', '', '', '', '4', '/xtgl/comm_cxZydmList.html');
insert into niutal_query_fields (FIELD_GUID, FIELD_ID, FIELD_NAME, FIELD_TEXT, FIELD_LIST, FIELD_LISTKEY, FIELD_LISTVALUE, FIELD_HEADERKEY, FIELD_HEADERVALUE, FIELD_SCOPE, FIELD_SOURCE, FIELD_CLASS, FIELD_DESC, FIELD_PLACEHOLDER, FIELD_NAME_WIDTH, FIELD_UPDATE_URL)
values ('143AFA8CE720FF2BE050007F01007721', 'zyfx_id', 'zyfx_id', '专业方向', 'zyfx_id_list', 'zyfx_id', 'zyfxmc', '', '全部', '1', 'APP:', '', '', '', '4', '/xtgl/comm_cxZyfxList.html');
insert into niutal_query_fields (FIELD_GUID, FIELD_ID, FIELD_NAME, FIELD_TEXT, FIELD_LIST, FIELD_LISTKEY, FIELD_LISTVALUE, FIELD_HEADERKEY, FIELD_HEADERVALUE, FIELD_SCOPE, FIELD_SOURCE, FIELD_CLASS, FIELD_DESC, FIELD_PLACEHOLDER, FIELD_NAME_WIDTH, FIELD_UPDATE_URL)
values ('143AFA8CE725FF2BE050007F01007721', 'bh_id', 'bh_id', '班级', 'bh_id_list', 'bh_id', 'bj', '', '全部', '1', 'APP:', '', '', '', '4', '/xtgl/comm_cxBjdmList.html');
insert into niutal_query_fields (FIELD_GUID, FIELD_ID, FIELD_NAME, FIELD_TEXT, FIELD_LIST, FIELD_LISTKEY, FIELD_LISTVALUE, FIELD_HEADERKEY, FIELD_HEADERVALUE, FIELD_SCOPE, FIELD_SOURCE, FIELD_CLASS, FIELD_DESC, FIELD_PLACEHOLDER, FIELD_NAME_WIDTH, FIELD_UPDATE_URL)
values ('143AFA8CE70CFF2BE050007F01007721', 'xqh_id', 'xqh_id', '校区', 'xqh_id_list', 'xqh_id', 'xqmc', '', '全部', '1', 'APP:', '', '', '', '4', '');
insert into niutal_query_fields (FIELD_GUID, FIELD_ID, FIELD_NAME, FIELD_TEXT, FIELD_LIST, FIELD_LISTKEY, FIELD_LISTVALUE, FIELD_HEADERKEY, FIELD_HEADERVALUE, FIELD_SCOPE, FIELD_SOURCE, FIELD_CLASS, FIELD_DESC, FIELD_PLACEHOLDER, FIELD_NAME_WIDTH, FIELD_UPDATE_URL)
values ('143AFA8CE711FF2BE050007F01007721', 'njdm_id', 'njdm_id', '年级', 'njdm_id_list', 'njdm_id', 'njmc', '', '全部', '1', 'APP:', '', '', '', '4', '');
insert into niutal_query_fields (FIELD_GUID, FIELD_ID, FIELD_NAME, FIELD_TEXT, FIELD_LIST, FIELD_LISTKEY, FIELD_LISTVALUE, FIELD_HEADERKEY, FIELD_HEADERVALUE, FIELD_SCOPE, FIELD_SOURCE, FIELD_CLASS, FIELD_DESC, FIELD_PLACEHOLDER, FIELD_NAME_WIDTH, FIELD_UPDATE_URL)
values ('154DC0B44BD02138E050007F010033CE', 'xnm', 'xnm', '学年', 'xnm_list', 'xnm', 'xnmc', '', '全部', '1', 'APP:', '', '', '', '4', '');
insert into niutal_query_fields (FIELD_GUID, FIELD_ID, FIELD_NAME, FIELD_TEXT, FIELD_LIST, FIELD_LISTKEY, FIELD_LISTVALUE, FIELD_HEADERKEY, FIELD_HEADERVALUE, FIELD_SCOPE, FIELD_SOURCE, FIELD_CLASS, FIELD_DESC, FIELD_PLACEHOLDER, FIELD_NAME_WIDTH, FIELD_UPDATE_URL)
values ('154DC0B44BD52138E050007F010033CE', 'xqm', 'xqm', '学期', 'xqm_list', 'dm', 'mc', '', '全部', '1', 'APP:', '', '', '', '4', '');
insert into niutal_query_fields (FIELD_GUID, FIELD_ID, FIELD_NAME, FIELD_TEXT, FIELD_LIST, FIELD_LISTKEY, FIELD_LISTVALUE, FIELD_HEADERKEY, FIELD_HEADERVALUE, FIELD_SCOPE, FIELD_SOURCE, FIELD_CLASS, FIELD_DESC, FIELD_PLACEHOLDER, FIELD_NAME_WIDTH, FIELD_UPDATE_URL)
values ('154DC0B44BDA2138E050007F010033CE', 'kkbm_id', 'kkbm_id', '开课部门', 'kkbm_id_list', 'jg_id', 'jgmc', '', '全部', '1', 'APP:', '', '', '', '4', '');
insert into niutal_query_fields (FIELD_GUID, FIELD_ID, FIELD_NAME, FIELD_TEXT, FIELD_LIST, FIELD_LISTKEY, FIELD_LISTVALUE, FIELD_HEADERKEY, FIELD_HEADERVALUE, FIELD_SCOPE, FIELD_SOURCE, FIELD_CLASS, FIELD_DESC, FIELD_PLACEHOLDER, FIELD_NAME_WIDTH, FIELD_UPDATE_URL)
values ('154DC0B44BDF2138E050007F010033CE', 'xsxy_id', 'xsxy_id', '学生学院', 'xsxy_id_list', 'jg_id', 'jgmc', '', '全部', '1', 'APP:', '', '', '', '4', '');
insert into niutal_query_fields (FIELD_GUID, FIELD_ID, FIELD_NAME, FIELD_TEXT, FIELD_LIST, FIELD_LISTKEY, FIELD_LISTVALUE, FIELD_HEADERKEY, FIELD_HEADERVALUE, FIELD_SCOPE, FIELD_SOURCE, FIELD_CLASS, FIELD_DESC, FIELD_PLACEHOLDER, FIELD_NAME_WIDTH, FIELD_UPDATE_URL)
values ('154DC0B44BF22138E050007F010033CE', 'xsdm', 'xsdm', '学时', 'xsdm_list', 'xsdm', 'xsmc', '', '全部', '1', 'APP:', '', '', '', '4', '');
insert into niutal_query_fields (FIELD_GUID, FIELD_ID, FIELD_NAME, FIELD_TEXT, FIELD_LIST, FIELD_LISTKEY, FIELD_LISTVALUE, FIELD_HEADERKEY, FIELD_HEADERVALUE, FIELD_SCOPE, FIELD_SOURCE, FIELD_CLASS, FIELD_DESC, FIELD_PLACEHOLDER, FIELD_NAME_WIDTH, FIELD_UPDATE_URL)
values ('15C6A5915B969F8FE050007F01003AB8', 'xx_id', 'xmc', '系', 'xx_id_list', 'jg_id', 'jgmc', '', '全部', '1', 'APP:', '', '', '', '4', '/xtgl/comm_cxXxdmList.html');
/*自动完成字段*/
insert into niutal_autocomplete_fields (AUTO_GUID, FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('14749D58EA04E22FE050007F01004663', 'xs', '学号', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxXsxxPairPaged.html');
insert into niutal_autocomplete_fields (AUTO_GUID, FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('14749D58EA05E22FE050007F01004663', 'js', '教师', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxJsxxPairPaged.html');
insert into niutal_autocomplete_fields (AUTO_GUID, FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('14749D58EA06E22FE050007F01004663', 'xy', '学院', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxXyxxPairPaged.html');
insert into niutal_autocomplete_fields (AUTO_GUID, FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('14749D58EA07E22FE050007F01004663', 'xx', '系', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxXxxPairPaged.html');
insert into niutal_autocomplete_fields (AUTO_GUID, FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('14749D58EA08E22FE050007F01004663', 'jg', '机构', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxJgxxPairPaged.html');
insert into niutal_autocomplete_fields (AUTO_GUID, FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('14749D58EA09E22FE050007F01004663', 'kkbm', '开课部门', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxKkbmPairPaged.html');
insert into niutal_autocomplete_fields (AUTO_GUID, FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('14749D58EA0AE22FE050007F01004663', 'zy', '专业', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxZyxxPairPaged.html');
insert into niutal_autocomplete_fields (AUTO_GUID, FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('14749D58EA0BE22FE050007F01004663', 'zyfx', '专业方向', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxZyfxPairPaged.html');
insert into niutal_autocomplete_fields (AUTO_GUID, FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('14749D58EA0CE22FE050007F01004663', 'bj', '班级', '2', '10', '300', '2', '1', 'value（key）', '/query/query_cxBjxxPairPaged.html');
insert into niutal_autocomplete_fields (AUTO_GUID, FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('14749D58EA0DE22FE050007F01004663', 'kc', '课程', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxKcxxPairPaged.html');
insert into niutal_autocomplete_fields (AUTO_GUID, FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('14749D58EA0EE22FE050007F01004663', 'pyfan', '培养方案', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxPyfanPairPaged.html');
/*js组件基本信息*/
insert into niutal_widget_details (WIDGET_GUID, WIDGET_NAME, WIDGET_DESC)
values ('157A59AF34F1A4E4E050007F01007318', 'tab组件', 'bootstrap自带页签组件');
insert into niutal_widget_details (WIDGET_GUID, WIDGET_NAME, WIDGET_DESC)
values ('14D4A72A3716A0E0E050007F01000B0B', 'JQGrid组件', '网络开源js数据列表组件');
insert into niutal_widget_details (WIDGET_GUID, WIDGET_NAME, WIDGET_DESC)
values ('14D4A72A3717A0E0E050007F01000B0B', 'validation组件', '基于网络开源js校验框架二次封装的前段数据校验组件');
insert into niutal_widget_details (WIDGET_GUID, WIDGET_NAME, WIDGET_DESC)
values ('14D4A72A3718A0E0E050007F01000B0B', 'tooltip组件', '基于bootstrap组件tooltip插件修改的tooltip提示组件');
/*js组件基本参数信息*/
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFE247F9E050007F01000544', 'treeGrid', '启用或者禁用treegrid模式', 'false', 'treegrid模式');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFE347F9E050007F01000544', 'treeReader.level_field', '树形grid时数据划分级别的字段名称', '', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFE447F9E050007F01000544', 'treeReader.parent_id_field', '树形grid时数据中判断父级数据的字段名称', '', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFE547F9E050007F01000544', 'ExpandColumn', '树形grid时树形结构所在列的名称', '', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFE747F9E050007F01000544', 'rowNum', '每页显示记录数;用于设置Grid中一次显示的行数，默认值为15', '15', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFE847F9E050007F01000544', 'sortable', '是否可排序', 'true', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFE947F9E050007F01000544', 'multiSort', '是否可组合排序 ', 'false', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFEA47F9E050007F01000544', 'sortname', '默认的排序列。可以是列名称或者是一个数字，此参数会被提交到后台 ', '', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFEB47F9E050007F01000544', 'sortorder', '排序顺序，升序或者降序（asc or desc）', 'asc', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFEC47F9E050007F01000544', 'caption', '设置Grid表格的标题，如果未设置，则标题区域不显示', '', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFED47F9E050007F01000544', 'hidegrid', '启用或者禁用控制表格显示、隐藏的按钮，只有当caption 属性不为空时起效;默认:false', 'false', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFEE47F9E050007F01000544', 'hiddengrid', '是否只有当点击显示表格的那个按钮时才会去初始化表格数据;当为ture时，表格不会被显示，只显示表格的标题', 'false', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFEF47F9E050007F01000544', 'rownumbers', '是否显示行号;设置成false则不显示；否则反之', 'false', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFF047F9E050007F01000544', 'rownumWidth', '显示行数时，该列的宽度，单位px', '35', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFF147F9E050007F01000544', 'viewrecords', '定义是否在Pager Bar显示所有记录的总数', 'false', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFF247F9E050007F01000544', 'userDataOnFooter', '定义是否在Pager Bar显示所有记录的总数', 'false', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFF347F9E050007F01000544', 'shrinkToFit', '此选项用于根据width计算每列宽度的算法。默认值为true。
	 * 如果shrinkToFit为true且设置了width值，则每列宽度会根据 width成比例缩放；
	 * 如果shrinkToFit为false且设置了width值，则每列的宽度不会成比例缩放，而是保持原有设置，而Grid将会有 水平滚动条', 'false', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFF547F9E050007F01000544', 'width', 'Grid的宽度，如果未设置，则宽度应为所有列宽的之和；如果设置了宽度，则每列的宽度将会根据shrinkToFit选项的设置，进行设置', 'auto', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFF647F9E050007F01000544', 'autowidth', '设置是否自动调整宽度:默认值为false。如果设为true，则Grid的宽度会根据父容器的宽度自动重算', 'true', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFF747F9E050007F01000544', 'height', 'Grid的高度，可以接受数字、%值、auto，默认值为150', 'auto', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFF847F9E050007F01000544', 'minHeight', 'Grid的最小高度，该值只有在使用 $(selector).loadJqGrid(options)方式加载的JQGrid组件，参数才有效。', '', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFF947F9E050007F01000544', 'autoheight', '设置是否自动调整高度:默认值为false。如果设为true，则Grid的高度会根据父容器的高度自动调整；实际上调用$(selector).setGridHeight("auto");', 'false', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFFA47F9E050007F01000544', 'multiselect', '定义是否支持多选', 'true', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFFB47F9E050007F01000544', 'multiselectWidth', '当multiselect为true时设置multiselect列宽度;单位：px', '35', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFFC47F9E050007F01000544', 'multikey', '只有在multiselect设置为ture时起作用，定义使用那个key来做多选。shiftKey，altKey，ctrlKey', '', '');
insert into niutal_widget_parameters (WIDGET_GUID, PARAM_GUID, PARAM_NAME, PARAM_DESC, PARAM_DEFAULT, PARAM_TEXT)
values ('14D4A72A3716A0E0E050007F01000B0B', '14E7AF5FCFFD47F9E050007F01000544', 'multiboxonly', '只有当multiselect = true.起作用，当multiboxonly 为ture时只有选择checkbox才会起作用', 'false', '');
/*js组件依赖资源信息*/
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3717A0E0E050007F01000B0B', '14D4A72A3719A0E0E050007F01000B0B', 'screen.css', '/js/jquery/validation/css/screen.css', '0', 1);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3717A0E0E050007F01000B0B', '14D4A72A371AA0E0E050007F01000B0B', 'jquery.validate-min.js', '/js/jquery/validation/jquery.validate-min.js', '0', 2);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3717A0E0E050007F01000B0B', '14D4A72A371BA0E0E050007F01000B0B', 'jquery.metadata-min.js', '/js/jquery/validation/jquery.metadata-min.js', '0', 3);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3717A0E0E050007F01000B0B', '14D4A72A371CA0E0E050007F01000B0B', 'messages_zh.js', '/js/jquery/validation/messages_zh.js', '0', 4);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3717A0E0E050007F01000B0B', '14D4A72A371DA0E0E050007F01000B0B', 'jquery.form-min.js', '/js/jquery/validation/jquery.form-min.js', '0', 5);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3717A0E0E050007F01000B0B', '14D4A72A371EA0E0E050007F01000B0B', 'jquery.validate.methods.js', '/js/jquery/validation/jquery.validate.methods.js', '0', 6);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3717A0E0E050007F01000B0B', '14D4A72A371FA0E0E050007F01000B0B', 'uiwidget.validation-min.js', '/js/jquery/validation/uiwidget.validation-min.js', '0', 7);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3717A0E0E050007F01000B0B', '14D4A72A3720A0E0E050007F01000B0B', 'jquery.validate.setting.js', '/js/jquery/validation/jquery.validate.setting.js', '0', 8);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3718A0E0E050007F01000B0B', '14D4A72A3721A0E0E050007F01000B0B', 'uiwidget.tooltips-min.css'',', '/js/jquery/bootstrap/tooltip/css/uiwidget.tooltips-min.css', '0', 1);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3718A0E0E050007F01000B0B', '14D4A72A3722A0E0E050007F01000B0B', 'uiwidget.tooltips-min.js', '/js/jquery/bootstrap/tooltip/uiwidget.tooltips-min.js', '0', 2);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3716A0E0E050007F01000B0B', '14D4A72A3723A0E0E050007F01000B0B', 'jquery-ui-1.7.1', '/js/jquery/jqGrid4.6/css/jquery-ui-1.7.1.custom-min.css', '0', 1);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3716A0E0E050007F01000B0B', '14D4A72A3724A0E0E050007F01000B0B', 'jqgrid-min.css', '/js/jquery/jqGrid4.6/css/ui.jqgrid-min.css', '0', 2);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3716A0E0E050007F01000B0B', '14D4A72A3725A0E0E050007F01000B0B', 'ui.jqgrid-adapter-min.css', '/js/jquery/jqGrid4.6/css/ui.jqgrid-adapter-min.css', '0', 3);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3716A0E0E050007F01000B0B', '14D4A72A3726A0E0E050007F01000B0B', 'jquery-ui-custom.min.js', '/js/jquery/jquery-ui-custom.min.js', '0', 4);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3716A0E0E050007F01000B0B', '14D4A72A3727A0E0E050007F01000B0B', 'ui.multiselect.js', '/js/jquery/jqGrid4.6/ui.multiselect.js', '0', 5);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3716A0E0E050007F01000B0B', '14D4A72A3728A0E0E050007F01000B0B', 'jquery.jqGrid.src-min.js', '/js/jquery/jqGrid4.6/jquery.jqGrid.src-min.js', '0', 6);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3716A0E0E050007F01000B0B', '14D4A72A3729A0E0E050007F01000B0B', 'grid.locale-cn.js', '/js/jquery/jqGrid4.6/grid.locale-cn.js', '0', 7);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3716A0E0E050007F01000B0B', '14D4A72A372AA0E0E050007F01000B0B', 'jquery.jqGrid-adapter-min.js', '/js/jquery/jqGrid4.6/jquery.jqGrid-adapter-min.js', '0', 8);
insert into niutal_widget_resources (WIDGET_GUID, RESOURCE_GUID, RESOURCE_DESC, RESOURCE_HREF, RESOURCE_FROM, RESOURCE_ORDINAL)
values ('14D4A72A3716A0E0E050007F01000B0B', '14D4A72A372BA0E0E050007F01000B0B', 'jqGridCRUD.js', '/js/jquery/jqGrid4.6/jqGridCRUD.js', '0', 9);

commit;


