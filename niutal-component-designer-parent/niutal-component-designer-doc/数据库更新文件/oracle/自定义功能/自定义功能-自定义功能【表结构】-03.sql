
-- 系统自定义功能信息表：指定自定义功能的基本信息
-- Create table 
create table niutal_designer_func(
  	func_guid      	VARCHAR2(32) default sys_guid() not null,  
  	func_code		VARCHAR2(10) not null,  
  	opt_code		VARCHAR2(30) not null,
  	func_name    	VARCHAR2(50) not null,  
  	func_desc    	VARCHAR2(200),
  	func_type		CHAR(1) default '1' not null,
  	func_editable	CHAR(1) default '0' not null,
  	func_release	CHAR(1) default '0' not null 
);
-- Add comments to the table 
comment on table niutal_designer_func is '系统自定义功能信息表';
-- Add comments to the columns 
comment on column niutal_designer_func.func_guid is '系统自定义功能信息表ID';
comment on column niutal_designer_func.func_code  is '功能代码';
comment on column niutal_designer_func.opt_code  is '操作代码';
comment on column niutal_designer_func.func_name  is '【系统自定义功能】名称（如：个人信息校验）';
comment on column niutal_designer_func.func_desc  is '【系统自定义功能】描述';
comment on column niutal_designer_func.func_type  is '【系统自定义功能】类型；默认：数据展示; 可选值 ：1:数据展示,2:数据维护';
comment on column niutal_designer_func.func_editable  is '【系统自定义功能】可编辑状态；默认：不可编辑；0：不可编辑，1：可编辑';
comment on column niutal_designer_func.func_release  is '【系统自定义功能】是否发布；默认：否；0：否，1：是';


-- 功能页面自定义元素信息表:指定设计器生成的功能页面的元素信息，元素分基本html元素和js组件；
-- Create table  
create table niutal_designer_func_elements(
  	func_guid				VARCHAR2(32) not null,
  	element_guid      		VARCHAR2(32) default sys_guid() not null,  
  	element_id   			VARCHAR2(50) not null, 
  	element_related_guid	VARCHAR2(32), 
  	element_type			CHAR(1),
  	element_tooltip   		VARCHAR2(200) , 
  	element_desc   			VARCHAR2(500) not null, 
  	element_width       	VARCHAR2(2)  default '4' not null,
  	element_ordinal    		NUMBER not null
);
--- Add comments to the table 
comment on table niutal_designer_func_elements is '功能页面自定义元素信息表';
-- Add comments to the columns
comment on column niutal_designer_func_elements.func_guid is '系统自定义功能信息表ID（niutal_designer_func.func_guid）';
comment on column niutal_designer_func_elements.element_guid is '功能页面自定义元素信息表ID';
comment on column niutal_designer_func_elements.element_id  is '【功能页面元素】索引，作为页面元素ID使用';
comment on column niutal_designer_func_elements.element_related_guid  is '【功能页面元素】关联元素ID，指定受影响元素，如查询区域元素关联jqgrid结果区域元素';
comment on column niutal_designer_func_elements.element_type  is '【功能页面元素】类型; 可选值 ：1:查询条件,2:脚本控件,3:普通字段';
comment on column niutal_designer_func_elements.element_tooltip is '【功能页面元素】气泡提示信息';
comment on column niutal_designer_func_elements.element_desc is '【功能页面元素】描述:作为页面元素扩展描述信息';
comment on column niutal_designer_func_elements.element_width is '【功能页面元素】栅格占比：总数12，可选[1-12]的整数';
comment on column niutal_designer_func_elements.element_ordinal is '【功能页面元素】的显示顺';


--自定义查询条件设计表：指定可自定义字段的系统功能
-- Create table 
create table niutal_designer_func_querys(
	element_guid	VARCHAR2(32) not null,
  	query_guid      VARCHAR2(32) default sys_guid() not null,  
  	query_name    	VARCHAR2(50) not null, 
  	query_column 	VARCHAR2(2) not null,
  	query_desc    	VARCHAR2(500)
);
-- Add comments to the table 
comment on table niutal_designer_func_querys is '系统自定义功能信息表';
-- Add comments to the columns 
comment on column niutal_designer_func_querys.element_guid  is '功能页面自定义元素信息表ID(niutal_designer_func_elements.element_guid)';
comment on column niutal_designer_func_querys.query_guid is '系统自定义功能信息表ID(niutal_designer_func.)';
comment on column niutal_designer_func_querys.query_name  is '【自定义查询条件】名称（如：个人信息校验）';
comment on column niutal_designer_func_querys.query_column  is '【自定义查询条件】显示列数';
comment on column niutal_designer_func_querys.query_desc  is '【自定义查询条件】描述';


-- 功能功能对应页面自定义字段设计表:指定设计器生成的功能页面的页面字段信息；
-- Create table  
create table niutal_designer_query_fields(
	table_guid      	VARCHAR2(32) default sys_guid() not null,  
  	query_guid			VARCHAR2(32) not null,
  	field_guid 			VARCHAR2(32),
  	field_id   			VARCHAR2(50) not null, 
  	field_parent      	VARCHAR2(50),
  	field_mapper	  	VARCHAR2(50), 
  	field_name    		VARCHAR2(50) not null, 
  	field_alias			VARCHAR2(50),
  	field_name_width    VARCHAR2(2)  default '4' not null,
  	field_text   		VARCHAR2(50) not null, 
  	field_value			VARCHAR2(50), 
	field_shape			CHAR(1) default '1' not null, 
  	field_type			CHAR(1), 
  	field_attr   		VARCHAR2(100),
  	field_chosen		CHAR(1) default '0' not null,
	field_autocomplete	CHAR(1) default '0' not null,
  	field_class    		VARCHAR2(100), 
  	field_placeholder	VARCHAR2(100),
  	field_filtertype	CHAR(1) default '1' not null,
  	field_ordinal     	NUMBER not null
);
--- Add comments to the table 
comment on table niutal_designer_query_fields is '系统自定义功能信息表对应字段设计表';
-- Add comments to the columns
comment on column niutal_designer_query_fields.table_guid is '系统自定义功能信息表对应字段设计表ID';
comment on column niutal_designer_query_fields.query_guid is '系统自定义功能信息表 ID';
comment on column niutal_designer_query_fields.field_guid is '系统可自定义查询字段信息表ID（niutal_query_fields.field_guid）或 为空（不关联已有基础下拉字段）';
comment on column niutal_designer_query_fields.field_id  is '功能对应字段索引，作为页面元素ID使用';
comment on column niutal_designer_query_fields.field_parent  is '功能对应字段级联父级索引，如果有多个以","分割;';
comment on column niutal_designer_query_fields.field_mapper is '功能对应字段名称的映射名称:在作为其他字段父级条件，【name属性值】与级联字段父级条件【参数名称】不统一时作为参数名称使用';
comment on column niutal_designer_query_fields.field_name is '功能对应字段名称:作为页面元素Name使用';
comment on column niutal_designer_query_fields.field_alias is '功能对应字段别名：在字段生成查询条件SQL时用作别名';
comment on column niutal_designer_query_fields.field_name_width is '字段名称元素占比：总数12，可选[1-12]的整数';
comment on column niutal_designer_query_fields.field_text is '功能对应字段名称:作为页面元素前的文字标题使用';
comment on column niutal_designer_query_fields.field_value is '功能对应默认值:可以是固定的值，也可以是OGNL表达式，方便从struts上下文获取，如：#{dqxn}';
comment on column niutal_designer_query_fields.field_shape  is '功能对应字段页面展示的形状;默认 1,可选值 ：1：select,2：input,3：textarea';
comment on column niutal_designer_query_fields.field_type  is '功能对应字段作为input元素时的类型;默认 1,可选值 ：1：text,2：radio,3：checkbox,4：hidden';
comment on column niutal_designer_query_fields.field_attr is '字段元素的自定义属性';
comment on column niutal_designer_query_fields.field_chosen  is '字段作为select元素是否使用chosen字段美化组件;默认 0,可选值 ：1：使用,0：不使用';
comment on column niutal_designer_query_fields.field_autocomplete  is '字段作为文本输入框时是否使用自动完成组件;默认 0,可选值 ：1：使用,0：不使用';
comment on column niutal_designer_query_fields.field_class  is '功能对应字段元素的样式名称';
comment on column niutal_designer_query_fields.field_placeholder   is '功能对应字段元素内的占位描述信息：如input输入前的提示';
comment on column niutal_designer_query_fields.field_filtertype   is '功能对应字段作为筛选条件时的筛选类型：默认：等值筛选，可选：1：等值筛选;2:全模糊筛选;3:前缀模糊筛选;4:后缀模糊筛选;';
comment on column niutal_designer_query_fields.field_ordinal is '功能对应字段元素的显示顺';


-- 功能页面自定义元素关联字段信息表:指定设计器生成的功能页面元素的字段信息；
-- Create table  
create table niutal_designer_func_fields(
	table_guid			VARCHAR2(32) default sys_guid() not null,
  	element_guid		VARCHAR2(32) not null,
  	field_guid 			VARCHAR2(32),
  	field_id   			VARCHAR2(50) not null, 
  	field_parent      	VARCHAR2(50),
  	field_mapper	  	VARCHAR2(50),
  	field_name    		VARCHAR2(50), 
  	field_name_width    VARCHAR2(2)  default '4' not null,
  	field_text   		VARCHAR2(50) not null, 
  	field_value			VARCHAR2(50), 
  	field_shape			CHAR(1) default '1' not null, 
  	field_type			CHAR(1) default '1' not null, 
  	field_attr   		VARCHAR2(100),
	field_chosen		CHAR(1) default '0' not null,
	field_autocomplete	CHAR(1) default '0' not null,
  	field_class    		VARCHAR2(100), 
  	field_placeholder	VARCHAR2(100),
  	field_ordinal     	NUMBER not null
);
--- Add comments to the table 
comment on table niutal_designer_func_fields is '功能页面自定义元素关联字段信息表';
-- Add comments to the columns
comment on column niutal_designer_func_fields.table_guid is '功能页面自定义元素关联字段信息表ID';
comment on column niutal_designer_func_fields.element_guid is '功能页面自定义元素信息表ID(niutal_designer_func_elements.element_guid)';
comment on column niutal_designer_func_fields.field_guid is '系统可自定义查询字段信息表ID（niutal_query_fields.field_guid）可为空';
comment on column niutal_designer_func_fields.field_id  is '功能对应字段索引，作为页面元素ID使用';
comment on column niutal_designer_func_fields.field_parent  is '功能对应字段级联父级索引，如果有多个以","分割;';
comment on column niutal_designer_func_fields.field_mapper is '功能对应字段名称的映射名称:在作为其他字段父级条件，【name属性值】与级联字段父级条件【参数名称】不统一时作为参数名称使用';
comment on column niutal_designer_func_fields.field_name is '功能对应字段代码:作为页面元素Name使用';
comment on column niutal_designer_func_fields.field_name_width is '字段名称元素占比：总数12，可选[1-12]的整数';
comment on column niutal_designer_func_fields.field_text is '功能对应字段名称:作为页面元素前的文字标题使用';
comment on column niutal_designer_query_fields.field_value is '功能对应默认值:可以是固定的值，也可以是OGNL表达式，方便从struts上下文获取，如：#{dqxn}';
comment on column niutal_designer_func_fields.field_shape  is '功能对应字段页面展示的形状;默认 1,可选值 ：1：input,2：select,3：textarea';
comment on column niutal_designer_func_fields.field_type  is '功能对应字段作为input元素时的类型;默认 1,可选值 ：1：text,2：password,3：radio,4：checkbox,5：hidden,6：image,7：file ';
comment on column niutal_designer_func_fields.field_attr is '字段元素的自定义属性';
comment on column niutal_designer_query_fields.field_chosen  is '字段作为select元素是否使用chosen字段美化组件;默认 0,可选值 ：1：使用,0：不使用';
comment on column niutal_designer_query_fields.field_autocomplete  is '字段作为文本输入框时是否使用自动完成组件;默认 0,可选值 ：1：使用,0：不使用';
comment on column niutal_designer_func_fields.field_class  is '功能对应字段元素的样式名称';
comment on column niutal_designer_func_fields.field_placeholder   is '功能对应字段元素内的占位描述信息：如input输入前的提示';
comment on column niutal_designer_func_fields.field_ordinal is '功能对应字段元素的显示顺';

-- 功能页面组件初始化参数信息表:指定设计器生成的功能页面中该组件参数信息；
-- Create table  
create table niutal_designer_func_widgets(
  	func_widget_guid    VARCHAR2(32) default sys_guid() not null,  
  	element_guid 		VARCHAR2(32) not null,
  	widget_guid			VARCHAR2(32) not null,
  	widget_title		VARCHAR2(50) not null,
  	widget_desc			VARCHAR2(100) ,
  	widget_params		VARCHAR2(500) not null,
  	widget_pageable		CHAR(1) default 0,
  	widget_cacheable	CHAR(1) default 0,
  	widget_exportable	CHAR(1) default 0,
  	widget_printable	CHAR(1) default 0,
  	widget_data_url		VARCHAR2(200) ,
  	widget_sql			CLOB
);
--- Add comments to the table 
comment on table niutal_designer_func_widgets is '功能页面组件初始化参数信息表';
-- Add comments to the columns
comment on column niutal_designer_func_widgets.func_widget_guid is '功能页面组件初始化参数信息表ID';
comment on column niutal_designer_func_widgets.element_guid is '功能页面自定义元素信息表ID（niutal_designer_func_elements.element_guid）';
comment on column niutal_designer_func_widgets.widget_guid is '功能js组件描述信息表ID（niutal_widget_details.widget_guid）';
comment on column niutal_designer_func_widgets.widget_title is '【功能页面组件】在页面中展示的标题';
comment on column niutal_designer_func_widgets.widget_desc is '【功能页面组件】在页面中展示的描述信息';
comment on column niutal_designer_func_widgets.widget_params is '【功能页面组件】初始化参数；该参数未JSON数据格式;如：{"name":"test"}';
comment on column niutal_designer_func_widgets.widget_pageable is '【功能页面组件】数据查询是否分页，1:true,0:false;默认为 0';
comment on column niutal_designer_func_widgets.widget_cacheable is '【功能页面组件】是否使用缓存机制处理数据结果，1:true,0:false;默认为 0';
comment on column niutal_designer_func_widgets.widget_exportable is '【功能页面组件】是否可Excel输出';
comment on column niutal_designer_func_widgets.widget_printable is '【功能页面组件】是否可打印输出';
comment on column niutal_designer_func_widgets.widget_data_url is '【功能页面组件】数据查询URL；如果在录入界面未指定，则会自动生成默认路径：/design/funcData_cxFuncDataList.html?doType=query';
comment on column niutal_designer_func_widgets.widget_sql is '【功能页面组件】数据查询SQL';

-- JQGrid组件数据列信息表:指定设计器生成的JQGrid组件数据列信息；
-- Create table  
create table niutal_designer_jqgrid_fields(
  	func_widget_guid		VARCHAR2(32) not null,
  	field_guid      		VARCHAR2(32) default sys_guid() not null,  
  	field_align				VARCHAR2(50) default 'center' not null, 
  	field_cellattr			VARCHAR2(500),
  	field_editable   		CHAR(1) default 0 not null, 
  	field_editoptions		VARCHAR2(500), 
  	field_editrules			VARCHAR2(500),
  	field_edittype			VARCHAR2(20),
  	field_fixed				CHAR(1) default 0 not null,
  	field_formatter			VARCHAR2(500),
  	field_hidden			CHAR(1) default 0 not null, 
  	field_index   			VARCHAR2(50) not null, 
	field_key   			CHAR(1) default 0 not null,
  	field_label   			VARCHAR2(100) not null, 
  	field_name    			VARCHAR2(50), 
  	field_resizable   		CHAR(1) default 1 not null, 
	field_sortable   		CHAR(1) default 1 not null, 
  	field_width    			VARCHAR2(100), 
  	field_ordinal     		NUMBER not null
);
--- Add comments to the table 
comment on table niutal_designer_jqgrid_fields is 'JQGrid组件数据列信息表';
-- Add comments to the columns
comment on column niutal_designer_jqgrid_fields.func_widget_guid is '功能页面自定义组件信息表ID（niutal_designer_func_widgets.widget_guid）';
comment on column niutal_designer_jqgrid_fields.field_guid is 'JQGrid组件数据列信息表ID';
comment on column niutal_designer_jqgrid_fields.field_align  is '【JQGrid组件】定义单元格对齐方式；可选值：left, center, right.;默认为center';
comment on column niutal_designer_jqgrid_fields.field_cellattr  is '【JQGrid组件】该参数扩展单元格td的属性，从而利用动态属性达到特定效果，如合并单元格!如果输入的字符代表函数则返回函数返回的结果，如果是字符串则作为属性直接使用。';
comment on column niutal_designer_jqgrid_fields.field_editable  is '【JQGrid组件】单元格是否可编辑，1:true,0:false;默认为 0';
comment on column niutal_designer_jqgrid_fields.field_editoptions  is '【JQGrid组件】编辑的一系列选项。如： {name:’myname’,index:’myname’,width:200,editable:true,edittype:’select’,editoptions: {dataUrl:”${ctx}/admin/deplistforstu.action”}},这个是演示动态从服务器端获取数据 
或者  {name:’myname’,index:’myname’,width:200,editable:true,edittype:’select’,editoptions: {value:"zy:专业;dl:大类"}},这个静态数据';
comment on column niutal_designer_jqgrid_fields.field_editrules  is '【JQGrid组件】编辑的规则;如：{name:’age’,index:’age’,editable:true,editrules: {edithidden:true,required:true,number:true,minValue:10,maxValue:100}},设定年龄的最大值为100，最小值为10，而且为数字类型，并且为必输字段';
comment on column niutal_designer_jqgrid_fields.field_edittype  is '【JQGrid组件】可以编辑的类型。可选值：text, textarea, select, checkbox, password, button, image, file ';
comment on column niutal_designer_jqgrid_fields.field_fixed  is '【JQGrid组件】列宽度是否要固定不可变，1:true,0:false;默认为 0';
comment on column niutal_designer_jqgrid_fields.field_formatter  is '【JQGrid组件】预设类型或用来格式化该列的自定义函数名。常用预设格式有：integer、date、currency、number等 ,select,自定义字符串;如： {name:’myname’, edittype:’select’, formatter:’select’, editoptions:{value:"1:One;2:Two"}} ';
comment on column niutal_designer_jqgrid_fields.field_hidden  is '【JQGrid组件】设置此列初始化时是否为隐藏状态，1:true,0:false;默认为 0';
comment on column niutal_designer_jqgrid_fields.field_index  is '【JQGrid组件】设置排序时所使用的索引名称，这个index名称会作为sidx参数（prmNames中设置的）传递到Server。';
comment on column niutal_designer_jqgrid_fields.field_key  is '【JQGrid组件】设置列是否主键列，1:true,0:false;;默认为 1';
comment on column niutal_designer_jqgrid_fields.field_label is '【JQGrid组件】列名称，当jqGrid的colNames选项数组为空时，为各列指定题头。如果colNames和此项都为空时，则name选项值会成为题头';
comment on column niutal_designer_jqgrid_fields.field_name is '【JQGrid组件】每个列设置唯一的名称，这是一个必需选项，其中保留字包括subgrid、cb、rn';
comment on column niutal_designer_jqgrid_fields.field_resizable is '【JQGrid组件】设置列是否可以变更尺寸，1:true,0:false;默认为 1';
comment on column niutal_designer_jqgrid_fields.field_sortable is '【JQGrid组件】设置该列是否可以排序，1:true,0:false;默认为 1';
comment on column niutal_designer_jqgrid_fields.field_width   is '【JQGrid组件】每列的宽度;如：150px';
comment on column niutal_designer_jqgrid_fields.field_ordinal is '【JQGrid组件】列显示的顺序';


-- 系统可自定义自动完成自动信息表:存储系统中所有可自动完成的字段信息
-- Create table
create table niutal_designer_auto_fields(
	table_guid			VARCHAR2(32) default sys_guid() not null,
	auto_guid      		VARCHAR2(32) not null,
	target_guid   		VARCHAR2(32) not null,
	field_name    		VARCHAR2(50) not null,
	field_text   		VARCHAR2(50) not null,
	field_minLength    	VARCHAR2(10) default '1' not null,
	field_items   		VARCHAR2(10) default '10' not null,
	field_delay	 		VARCHAR2(10) default '300' not null,
	field_setvalue   	VARCHAR2(2) default '2' not null,
	field_realvalue   	VARCHAR2(2) default '1' not null,
	field_format   		VARCHAR2(50) default 'value（key）' not null
);
-- Add comments to the table 
comment on table niutal_designer_auto_fields is '系统可自定义自动完成自动信息表:存储系统中所有可自动完成的字段信息';
-- Add comments to the columns 
comment on column niutal_designer_auto_fields.table_guid is '系统可自定义自动完成自动信息表ID';
comment on column niutal_designer_auto_fields.auto_guid is '系统可自定义自动完成字段基础信息表ID';
comment on column niutal_designer_auto_fields.target_guid is '系统自定义功能信息表对应字段设计表ID(niutal_designer_query_fields.table_guid)或 功能功能对应页面自定义字段设计表ID【niutal_designer_func_fields.table_guid】';
comment on column niutal_designer_auto_fields.field_name is '功能对应字段代码:作为页面元素Name使用';
comment on column niutal_designer_auto_fields.field_text is '功能对应字段名称:作为页面元素前的文字标题使用';
comment on column niutal_designer_auto_fields.field_minLength  is '当前文本输入框中字符串达到该属性值时才进行匹配处理，默认：1';
comment on column niutal_designer_auto_fields.field_items is '自动完成提示的最大结果集数量，默认：10';
comment on column niutal_designer_auto_fields.field_delay is '指定延时毫秒数后，才正真向后台请求数据，以防止输入过快导致频繁向后台请求，默认：300';
comment on column niutal_designer_auto_fields.field_setvalue is '此值在点击了某项后会将当前元素的值为此处指定的 key或value 对应的后台数据;默认：2;1：表示key,2：表示value ';
comment on column niutal_designer_auto_fields.field_realvalue is '此值在点击了某项后会将当前元素添加 real-value属性 ，值为此处指定的 key或value 对应的后台数据;默认：1;1：表示key,2：表示value';
comment on column niutal_designer_auto_fields.field_format is '此值在你需进行特殊的显示时，改变这个值可以改变组件每个元素显示的组合效果，默认是value（key）；此处处理用到正则替换value字符和key字符，所以可写 key[value],value【key】 等等的包含value和key字符的任意模板';


-- 功能组件脚本样式资源信息表：指定功能页面引入的各个组件的资源信息js/css
-- Create table
create table niutal_designer_func_resources(
  	func_guid		VARCHAR2(32) not null,
  	widget_guid		VARCHAR2(32) not null
);
-- Add comments to the table 
comment on table niutal_designer_func_resources is '功能组件脚本样式资源信息表';
-- Add comments to the columns 
comment on column niutal_designer_func_resources.func_guid is '系统自定义功能信息表ID（niutal_designer_func.func_guid）';
comment on column niutal_designer_func_resources.widget_guid is '功能组件描述信息表ID（niutal_widget_details.widget_guid）';

/*-----创建时间：2015-05-28--创建人：大康 --备注： 功能页面组件表添加字段------------*/ 

-- Add/modify columns 
alter table niutal_DESIGNER_FUNC_WIDGETS add widget_loadAtOnce CHAR(1) default '0' not null;
-- Add comments to the columns 
comment on column niutal_DESIGNER_FUNC_WIDGETS.widget_loadAtOnce is '【功能页面组件】是否组件初始化完成后立刻根据data_url或者data_sql加载数据';

  -- Add/modify columns 
alter table niutal_DESIGNER_QUERY_FIELDS add field_required CHAR(1) default '0' not null;
-- Add comments to the columns 
comment on column niutal_DESIGNER_QUERY_FIELDS.field_required is '功能对应字段是否必填;默认 0,可选值 ：1：必填,0：非必填';

-- Add/modify columns 
alter table niutal_DESIGNER_FUNC_RESOURCES add auto_insert CHAR(1) default 0 not null;
-- Add comments to the columns 
comment on column niutal_DESIGNER_FUNC_RESOURCES.auto_insert is '功能组件关联脚本是否程序自动插入，1:true,0:false;默认为0';

-- Add/modify columns 
alter table niutal_DESIGNER_FUNC_RESOURCES add resource_name VARCHAR2(50);
alter table niutal_DESIGNER_FUNC_RESOURCES add resource_text clob;
-- Add comments to the columns 
comment on column niutal_DESIGNER_FUNC_RESOURCES.resource_name  is '功能自定义脚本/样式名称';
comment on column niutal_DESIGNER_FUNC_RESOURCES.resource_text  is '功能自定义脚本/样式文本数据';
  -- Add/modify columns 
alter table niutal_DESIGNER_FUNC_RESOURCES add resource_guid VARCHAR2(32) default sys_guid() not null;
-- Add comments to the columns 
comment on column niutal_DESIGNER_FUNC_RESOURCES.resource_guid is '自定义功能组件脚本样式资源信息表ID';
  -- Add/modify columns 
alter table niutal_DESIGNER_FUNC_RESOURCES add resource_ordinal number;
-- Add comments to the columns 
comment on column niutal_DESIGNER_FUNC_RESOURCES.resource_ordinal  is '自定义功能组件脚本样式资源加载顺序';

comment on column niutal_designer_func_widgets.widget_exportable is '【功能页面组件】是否可Excel输出';
comment on column niutal_designer_func_widgets.widget_printable is '【功能页面组件】是否可打印输出';

/*-----创建时间：2015-06-03--创建人：大康 --备注：表结构调整------------*/ 

-- Add/modify columns 
alter table niutal_DESIGNER_FUNC_RESOURCES add RESOURCE_TYPE char(1) default 0;
-- Add comments to the columns 
comment on column niutal_DESIGNER_FUNC_RESOURCES.RESOURCE_GUID is '自定义功能脚本样式资源信息表ID';
comment on column niutal_DESIGNER_FUNC_RESOURCES.RESOURCE_ORDINAL is '自定义功能脚本样式资源加载顺序';
comment on column niutal_DESIGNER_FUNC_RESOURCES.RESOURCE_TYPE is '自定义功能资源类型：1：js脚本,2:css样式';

-- Add comments to the columns 
comment on column niutal_DESIGNER_FUNC.FUNC_TYPE is '【系统自定义功能】功能类型；默认：数据展示; 可选值 ：1:数据展示,2:数据维护,3:报表打印,4:数据导出,5:数据删除';

-- Add/modify columns 
alter table niutal_DESIGNER_FUNC add require_type CHAR(1) default 0 not null;
-- Add comments to the columns 
comment on column niutal_DESIGNER_FUNC.require_type is '【系统自定义功能】功能按钮点击前JQGrid列表选择数据行检查类型：默认：否；0：不检查，1：只能选一行，2：至少选择一行';
  
-- Add/modify columns 
alter table niutal_DESIGNER_FUNC add func_width VARCHAR2(10) default 800 not null;
-- Add comments to the columns 
comment on column niutal_DESIGNER_FUNC.func_width  is '【系统自定义功能】作为按钮绑定的自定义功能时，弹窗宽度值；单位px';
  
-- Add/modify columns 
alter table niutal_DESIGNER_FUNC add query_type CHAR(1) default 0 not null;
-- Add comments to the columns 
comment on column niutal_DESIGNER_FUNC.query_type is '【系统自定义功能】查询条件类型;默认 0,可选值  ：0：无查询条件,1：普通查询,2：高级查询';

/*-----创建时间：2015-06-10--创建人：大康 --备注：表结构调整------------*/ 

-- Drop columns 
alter table niutal_DESIGNER_FUNC_WIDGETS drop column WIDGET_PRINTABLE;
alter table niutal_DESIGNER_FUNC_WIDGETS drop column WIDGET_EXPORTABLE;

-- Add/modify columns 
alter table niutal_DESIGNER_JQGRID_FIELDS add FIELD_PARAM CHAR(1) default 0;
-- Add comments to the columns 
comment on column niutal_DESIGNER_JQGRID_FIELDS.FIELD_PARAM   is '【JQGrid组件】列是否作为弹窗的参数，1:true,0:false;默认为 0';

-- Add/modify columns 
alter table niutal_DESIGNER_FUNC add release_time VARCHAR2(20);
-- Add comments to the columns 
comment on column niutal_DESIGNER_FUNC.release_time
  is '【系统自定义功能】最后一次发布的时间戳，该值将作为自定义功能相关js，css的版本号';
-- Add/modify columns 
alter table niutal_DESIGNER_FUNC modify func_type VARCHAR2(2);
-- Add comments to the columns 
comment on column niutal_DESIGNER_FUNC.func_type
  is '【系统自定义功能】默认：数据展示; 可选值 ：1:''数据展示'',2:''数据维护'',3:''预览打印'',41:''快速打印(Applet模式)'',42:''快速打印(Flash模式)'',43:''快速打印(PDF模式)'',5:''数据导出'',6:''数据删除'' ';

