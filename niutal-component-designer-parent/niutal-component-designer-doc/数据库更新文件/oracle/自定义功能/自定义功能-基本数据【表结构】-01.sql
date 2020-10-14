--------------自定义查询字段----------------------------------------------
--1、系统可自定义查询字段信息表:存储系统中所有可用的查询字段信息
-- Create table
create table niutal_query_fields(
	field_guid      	VARCHAR2(32) default sys_guid() not null,
	field_id   			VARCHAR2(50) not null,
	field_name    		VARCHAR2(50),
	field_name_width    VARCHAR2(2) default '4' not null,
	field_text   		VARCHAR2(50) not null,
	field_list	 		VARCHAR2(50),
	field_listKey   	VARCHAR2(50),
	field_listValue   	VARCHAR2(50),
	field_headerKey   	VARCHAR2(50),
	field_headerValue 	VARCHAR2(50),
	field_scope			CHAR(1) default '1' not null,
	field_source		VARCHAR2(1000) default 'APP' not null,
	field_update_url    VARCHAR2(1000),
  	field_class       	VARCHAR2(100),
  	field_desc        	VARCHAR2(200),
  	field_placeholder 	VARCHAR2(100)
);
-- Add comments to the table 
comment on table niutal_query_fields is '系统可自定义查询字段信息表:存储系统中所有可用的查询字段信息';
-- Add comments to the columns 
comment on column niutal_query_fields.field_guid is '系统可自定义查询字段信息表ID';
comment on column niutal_query_fields.field_id  is '字段索引，作为页面元素ID使用';
comment on column niutal_query_fields.field_name is '字段代码:作为页面元素Name使用';
comment on column niutal_query_fields.field_name_width is '字段名称元素占比：总数12，可选[1-12]的整数';
comment on column niutal_query_fields.field_text is '字段名称:作为页面元素前的文字标题使用';
comment on column niutal_query_fields.field_list is '字段数据对象取值索引：struts的<s:select list=""></s:select>标签list对应的值';
comment on column niutal_query_fields.field_listKey is '字段key取值索引：struts的<s:select listKey=""></s:select>标签listKey对应的值';
comment on column niutal_query_fields.field_listValue is '字段value取值索引：struts的<s:select listValue=""></s:select>标签listValue对应的值';
comment on column niutal_query_fields.field_headerKey is '字段作为select元素默认option元素key值：struts的<s:select headerKey=""></s:select>标签headerKey对应的值';
comment on column niutal_query_fields.field_headerValue is '字段作为select元素默认option元素value值：struts的<s:select headerValue=""></s:select>标签headerValue对应的值';
comment on column niutal_query_fields.field_scope  is '字段所属级别：在系统中所属级别;默认 1, 1：全局,2：框架,3：局部';
comment on column niutal_query_fields.field_source
  is '字段值来源：
     程序设置       	：此时如果field_list值不为空且field_shape=2，需要开发者在进入页面前指定field_id对应的对象结果
     数据库         	：格式如 sql:查询SQL 例如 sql:select id as key,name as value from table_name
     xml数据        	：格式如 xml:(baseData.xml)文件中的id 例如 xml:isValid
     spring集合对象	：格式如 spring:文件中的id 例如 spring:field_list
     固定值         	：格式如 fixed:固定值1,固定值2,...(多个用,隔开) 例如  fixed:aaa,bbb,ccc';
comment on column niutal_query_fields.field_update_url  is '字段数据后台请求路径，即Ajax请求更新数据的路径： 返回的数据元素必须包含 field_listKey 和 field_listValue 指定属性';
comment on column niutal_query_fields.field_class  is '字段元素的样式名称';
comment on column niutal_query_fields.field_desc   is '字段元素的描述信息';
comment on column niutal_query_fields.field_placeholder   is '字段元素内的占位描述信息：如input输入前的提示';

--2、系统可自定义自动完成字段基础信息表:存储系统中所有可自动完成的字段信息
-- Create table
create table niutal_autocomplete_fields(
	auto_guid      		VARCHAR2(32) default sys_guid() not null,
	field_name    		VARCHAR2(50) not null,
	field_text   		VARCHAR2(50) not null,
	field_minLength    	VARCHAR2(10) default '1' not null,
	field_items   		VARCHAR2(10) default '10' not null,
	field_delay	 		VARCHAR2(10) default '300' not null,
	field_setvalue   	VARCHAR2(2) default '2' not null,
	field_realvalue   	VARCHAR2(2) default '1' not null,
	field_format   		VARCHAR2(50) default 'value（key）' not null,
	field_action	 	VARCHAR2(200) not null
);
-- Add comments to the table 
comment on table niutal_autocomplete_fields is '系统可自定义自动完成字段基础信息表:存储系统中所有可自动完成的字段信息';
-- Add comments to the columns 
comment on column niutal_autocomplete_fields.auto_guid is '系统可自定义自动完成字段基础信息表ID';
comment on column niutal_autocomplete_fields.field_name is '功能对应字段代码:作为页面元素Name使用';
comment on column niutal_autocomplete_fields.field_text is '功能对应字段名称:作为页面元素前的文字标题使用';
comment on column niutal_autocomplete_fields.field_minLength  is '当前文本输入框中字符串达到该属性值时才进行匹配处理，默认：1';
comment on column niutal_autocomplete_fields.field_items is '自动完成提示的最大结果集数量，默认：10';
comment on column niutal_autocomplete_fields.field_delay is '指定延时毫秒数后，才正真向后台请求数据，以防止输入过快导致频繁向后台请求，默认：300';
comment on column niutal_autocomplete_fields.field_setvalue is '此值在点击了某项后会将当前元素的值为此处指定的 key或value 对应的后台数据;默认：2;1：表示key,2：表示value ';
comment on column niutal_autocomplete_fields.field_realvalue is '此值在点击了某项后会将当前元素添加 real-value属性 ，值为此处指定的 key或value 对应的后台数据;默认：1;1：表示key,2：表示value';
comment on column niutal_autocomplete_fields.field_format is '此值在你需进行特殊的显示时，改变这个值可以改变组件每个元素显示的组合效果，默认是value（key）；此处处理用到正则替换value字符和key字符，所以可写 key[value],value【key】 等等的包含value和key字符的任意模板';
comment on column niutal_autocomplete_fields.field_action is '自动完成数据查询路径';

delete from niutal_autocomplete_fields;
commit;
insert into niutal_autocomplete_fields (FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('xs', '学号', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxXsxxPairPaged.html');
insert into niutal_autocomplete_fields (FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('js', '教师', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxJsxxPairPaged.html');
insert into niutal_autocomplete_fields (FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('xy', '学院', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxXyxxPairPaged.html');
insert into niutal_autocomplete_fields (FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('xx', '系', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxXxxPairPaged.html');
insert into niutal_autocomplete_fields (FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('jg', '机构', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxJgxxPairPaged.html');
insert into niutal_autocomplete_fields (FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('kkbm', '开课部门', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxKkbmPairPaged.html');
insert into niutal_autocomplete_fields (FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('zy', '专业', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxZyxxPairPaged.html');
insert into niutal_autocomplete_fields (FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('zyfx', '专业方向', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxZyfxPairPaged.html');
insert into niutal_autocomplete_fields (FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('bj', '班级', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxBjxxPairPaged.html');
insert into niutal_autocomplete_fields (FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('kc', '课程', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxKcxxPairPaged.html');
insert into niutal_autocomplete_fields (FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT, FIELD_ACTION)
values ('pyfan', '培养方案', '1', '10', '300', '2', '1', 'value（key）', '/query/query_cxPyfanPairPaged.html');
commit;



-- 功能js组件描述信息表:指定系统可用功能组件信息；
-- Create table  
create table niutal_widget_details(
  	widget_guid      	VARCHAR2(32) default sys_guid() not null,  
  	widget_name			VARCHAR2(50) not null,
  	widget_desc 		VARCHAR2(300) not null
);
--- Add comments to the table 
comment on table niutal_widget_details is '功能js组件描述信息表';
-- Add comments to the columns
comment on column niutal_widget_details.widget_guid is '功能js组件描述信息表ID';
comment on column niutal_widget_details.widget_name is '【功能js组件】名称（如：jqgrid列表）';
comment on column niutal_widget_details.widget_desc is '【功能js组件】详细描述';


-- 功能js组件初始化参数信息表：指定系统中的js组件初始化需要哪些参数，以及默认值
-- Create table
create table niutal_widget_parameters(
  	widget_guid			VARCHAR2(32) not null,  
  	param_guid   		VARCHAR2(32) default sys_guid() not null,  
  	param_name   		VARCHAR2(50) not null,
  	param_text   		VARCHAR2(50),
  	param_desc   		VARCHAR2(500),
  	param_default  		VARCHAR2(200)
);
-- Add comments to the table 
comment on table niutal_widget_parameters is '功能js组件初始化参数信息表';
-- Add comments to the columns 
comment on column niutal_widget_parameters.param_guid is '功能js组件初始化参数信息表ID';
comment on column niutal_widget_parameters.widget_guid  is '功能组件描述信息表ID（niutal_widget_details.widget_guid）';
comment on column niutal_widget_parameters.param_name  is '【功能js组件初始化参数】名称';
comment on column niutal_widget_parameters.param_text  is '【功能js组件初始化参数】中文简称';
comment on column niutal_widget_parameters.param_desc  is '【功能js组件初始化参数】描述';
comment on column niutal_widget_parameters.param_default  is '【功能js组件初始化参数】默认值';

-- 组件脚本样式资源信息表：指定系统中各个组件的资源信息js/css
-- Create table
create table niutal_widget_resources(
  	widget_guid			VARCHAR2(32) not null,  
  	resource_guid   	VARCHAR2(32) default sys_guid() not null,  
  	resource_desc   	VARCHAR2(200) not null,
  	resource_href   	VARCHAR2(200) not null,
  	resource_from   	CHAR(1) default '0' not null,
  	resource_ordinal    NUMBER not null
);
-- Add comments to the table 
comment on table niutal_widget_resources is '组件脚本样式资源信息表';
-- Add comments to the columns 
comment on column niutal_widget_resources.resource_guid is '组件脚本样式资源信息表ID';
comment on column niutal_widget_resources.widget_guid  is '功能组件描述信息表ID（niutal_widget_details.widget_guid）';
comment on column niutal_widget_resources.resource_desc  is '【组件脚本样式】描述';
comment on column niutal_widget_resources.resource_href  is '【组件脚本样式】资源请求路径';
comment on column niutal_widget_resources.resource_from  is '【组件脚本样式】来源;默认 0, 0：系统内（应用程序内）,1：系统外(v5样式服务)';
comment on column niutal_widget_resources.resource_ordinal is '【组件脚本样式】加载顺序';
