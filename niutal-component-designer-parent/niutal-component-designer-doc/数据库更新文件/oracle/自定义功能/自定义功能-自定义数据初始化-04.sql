/*删除元素关联查询条件字段数据*/
delete niutal_designer_query_fields a
where exists (select 'x' from niutal_designer_func_querys b 
   where a.query_guid = b.query_guid 
     and exists (select 'x' from niutal_designer_func_elements c
           where b.element_guid = c.element_guid
             and exists (select 'x' from niutal_designer_func t
                   where t.func_guid = c.func_guid)));
/*删除元素关联查询条件数据*/
delete niutal_designer_func_querys a
where exists (select 'x' from niutal_designer_func_elements b
   where a.element_guid = b.element_guid
     and exists (select 'x' from niutal_designer_func t
           where t.func_guid = b.func_guid));
/*删除元素关联JQGrid组件列数据*/
delete niutal_designer_jqgrid_fields a
where exists (select 'x' from niutal_designer_func_widgets b 
   where a.func_widget_guid = b.func_widget_guid 
     and exists (select 'x' from niutal_designer_func_elements c
           where b.element_guid = c.element_guid
             and exists (select 'x' from niutal_designer_func t
                   where t.func_guid = c.func_guid)));
/*删除元素关联组件数据*/
delete niutal_designer_func_widgets a
where exists (select 'x' from niutal_designer_func_elements b
   where a.element_guid = b.element_guid
     and exists (select 'x' from niutal_designer_func t
           where t.func_guid = b.func_guid));
/*删除功能关联元素数据*/
delete niutal_designer_func_elements a
where exists (select 'x' from niutal_designer_func t
     where t.func_guid = a.func_guid);
/*删除元素关联字段数据*/
delete niutal_designer_func_fields a
where exists (select 'x' from niutal_designer_func_elements b
   where a.element_guid = b.element_guid
     and exists (select 'x' from niutal_designer_func t
           where t.func_guid = b.func_guid));
/*删除自定义功能数据*/
delete niutal_designer_func t;

commit;
