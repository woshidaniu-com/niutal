

-- 删除基础数据表

/*删除-系统可自定义查询字段信息表*/
drop table niutal_query_fields;
/*删除-系统可自定义自动完成字段基础信息表*/
drop table niutal_autocomplete_fields;
/*删除-功能js组件描述信息表*/
drop table niutal_widget_details;
/*删除-功能js组件初始化参数信息表*/
drop table niutal_widget_parameters;
/*删除-组件脚本样式资源信息表*/
drop table niutal_widget_resources;

-- 自定义功能表

/*删除-功能功能对应页面自定义字段设计表*/
drop table niutal_designer_query_fields;
/*删除-自定义查询条件设计表*/
drop table niutal_designer_func_querys;
/*删除-JQGrid组件数据列信息表*/
drop table niutal_designer_jqgrid_fields;
/*删除-功能页面组件初始化参数信息表*/
drop table niutal_designer_func_widgets;
/*删除-功能页面自定义元素信息表*/
drop table niutal_designer_func_elements;
/*删除-功能页面自定义元素关联字段信息表*/
drop table niutal_designer_func_fields;
/*删除-自定义高级报表设计表*/
drop table niutal_designer_reports;
/*删除-高级报表基本信息表 */
drop table niutal_report_details;
/*删除-系统自定义功能信息表*/
drop table niutal_designer_func;
drop table niutal_designer_auto_fields;
drop table niutal_designer_func_resources;
commit;

