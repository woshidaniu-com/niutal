<#escape x as x?html>  
<#noescape>
-- ${func_name?string}(初始化SQL脚本)

-- 功能菜单部分

<#if func_menu_list?exists && func_menu_list?size != 0>  
delete niutal_xtgl_jsgnmkdmb t
where t.gnmkdm in (select a.gnmkdm
   from niutal_xtgl_jsgnmkdmb a 
  start with a.gnmkdm = '${func_code?string}'
connect by prior a.gnmkdm = a.fjgndm);
commit;
<#list func_menu_list as menuModel>
insert into niutal_xtgl_jsgnmkdmb (GNMKDM, GNMKMC, FJGNDM, DYYM, XSSX, CDJB, GNSYDX,SFZDYMK)
values ('${menuModel.gnmkdm?string}', '${menuModel.gnmkmc?string}', '${menuModel.fjgndm?string}', '${menuModel.dyym?default('')}', '${menuModel.xssx?string}', ${menuModel.cdjb?string}, '${menuModel.gnsydx?default('')}','${menuModel.sfzdymk?default('0')}');
</#list>
commit;
</#if>

<#if func_btn_list?exists && func_btn_list?size != 0>  
delete niutal_xtgl_gnmkczb t
where t.gnmkdm in (select a.gnmkdm
   from niutal_xtgl_jsgnmkdmb a 
  start with a.gnmkdm = '${func_code?string}'
connect by prior a.gnmkdm = a.fjgndm);
commit;
<#list func_btn_list as btnModel>
insert into niutal_xtgl_gnmkczb(gnmkdm,czdm,sfxs,sfzdycz)values('${btnModel.gnmkdm?string}','${btnModel.czdm?string}','${btnModel.sfxs?default('0')}','${btnModel.sfzdycz?default('1')}');
</#list>
commit;
</#if>

<#if func_jsMenu_list?exists && func_jsMenu_list?size != 0>  
delete niutal_xtgl_jsgnmkczb t
where t.gnmkdm in (select a.gnmkdm
   from niutal_xtgl_jsgnmkdmb a 
  start with a.gnmkdm = '${func_code?string}'
connect by prior a.gnmkdm = a.fjgndm);
commit;
<#list func_jsMenu_list as jsMenuModel>
insert into niutal_xtgl_jsgnmkczb (jsdm,gnmkdm,czdm,czmc,dyym) values('${jsMenuModel.jsdm?string}','${jsMenuModel.gnmkdm?string}','${jsMenuModel.czdm?string}','${jsMenuModel.czmc?default('')}',''); 
</#list>
commit;
</#if>

-- 自定义数据部分
<#if func_list?exists && func_list?size != 0>  
/*删除自定义功能数据*/
delete niutal_designer_func t
where t.func_code in (select a.gnmkdm
   from niutal_xtgl_jsgnmkdmb a 
  start with a.gnmkdm = '${func_code?string}'
connect by prior a.gnmkdm = a.fjgndm);
commit;
/*增加自定义功能数据*/
<#list func_list as funcModel>
insert into niutal_designer_func (FUNC_GUID, FUNC_CODE, OPT_CODE, FUNC_NAME, FUNC_DESC, FUNC_EDITABLE, FUNC_RELEASE, FUNC_TYPE, REQUIRE_TYPE, FUNC_WIDTH, QUERY_TYPE)
values ('${funcModel.func_guid?string}', '${funcModel.func_code?string}', '${funcModel.opt_code?string}', '${funcModel.func_name?string}', '${funcModel.func_desc?default('')}', '${funcModel.func_editable?default('0')}', '${funcModel.func_release?default('0')}','${funcModel.func_type?default('1')}', '${funcModel.require_type?default('0')}','${funcModel.func_width?default('0')}','${funcModel.query_type?default('0')}');
</#list>
commit;
</#if>
           
<#if func_element_list?exists && func_element_list?size != 0>
/*删除功能关联元素数据*/
delete niutal_designer_func_elements a
 where exists (select 'x' from niutal_designer_func t
         where t.func_guid = a.func_guid
           and t.func_code in (select a.gnmkdm
			   from niutal_xtgl_jsgnmkdmb a 
			  start with a.gnmkdm = '${func_code?string}'
			connect by prior a.gnmkdm = a.fjgndm)
           );
commit;          
/*增加功能关联元素数据*/
<#list func_element_list as elementModel>
insert into niutal_designer_func_elements (FUNC_GUID, ELEMENT_GUID, ELEMENT_DESC, ELEMENT_WIDTH, ELEMENT_ORDINAL, ELEMENT_ID, ELEMENT_RELATED_GUID, ELEMENT_TYPE)
values ('${elementModel.func_guid?string}', '${elementModel.element_guid?string}', '${elementModel.element_desc?default('')}', '${elementModel.element_width?default('12')}',${elementModel.element_ordinal?default('1')}, '${elementModel.element_id?default('')}', '${elementModel.element_related_guid?default('')}', '${elementModel.element_type?default('2')}');
</#list>
commit;
</#if>

<#if func_query_list?exists && func_query_list?size != 0>
/*删除元素关联查询条件数据*/
delete niutal_designer_func_querys a
 where exists (select 'x' from niutal_designer_func_elements b
         where a.element_guid = b.element_guid
           and exists (select 'x' from niutal_designer_func t
                 where t.func_guid = b.func_guid
                   and t.func_code in (select a.gnmkdm
				   from niutal_xtgl_jsgnmkdmb a 
				  start with a.gnmkdm = '${func_code?string}'
				connect by prior a.gnmkdm = a.fjgndm)
              ));
commit;                   
/*增加元素关联查询条件数据*/
<#list func_query_list as queryModel>
insert into niutal_designer_func_querys(query_guid,element_guid,query_name,query_column,query_desc)
values('${queryModel.query_guid?string}','${queryModel.element_guid?string}','${queryModel.query_name?string}','${queryModel.query_column?default('4')}','${queryModel.query_desc?default('')}');
</#list>
commit;
</#if>

<#if func_query_field_list?exists && func_query_field_list?size != 0>  
/*删除元素关联查询条件字段数据*/
delete niutal_designer_query_fields a
 where exists (select 'x' from niutal_designer_func_querys b 
         where a.query_guid = b.query_guid 
           and exists (select 'x' from niutal_designer_func_elements c
                 where b.element_guid = c.element_guid
                   and exists (select 'x' from niutal_designer_func t
                         where t.func_guid = c.func_guid
                           and t.func_code in (select a.gnmkdm
						   from niutal_xtgl_jsgnmkdmb a 
						  start with a.gnmkdm = '${func_code?string}'
						connect by prior a.gnmkdm = a.fjgndm)
                        )));
commit;                          
/*增加元素关联查询条件字段数据*/
<#list func_query_field_list as queryField>
insert into niutal_designer_query_fields(table_guid,query_guid,field_guid,field_id,field_parent,field_mapper,field_name,field_alias,field_name_width,field_text,field_value,field_shape,field_type,field_attr,field_chosen,field_autocomplete,field_class,field_placeholder,field_filtertype,field_required,field_ordinal)
values('${queryField.table_guid?string}','${queryField.query_guid?string}','${queryField.field_guid?default('')}','${queryField.field_id?string}','${queryField.field_parent?default('')}','${queryField.field_mapper?default('')}','${queryField.field_name?string}','${queryField.field_alias?default('')}','${queryField.field_name_width?default('')}','${queryField.field_text?default('')}','${queryField.field_value?default('')}','${queryField.field_shape?default('')}','${queryField.field_type?default('')}','${queryField.field_attr?default('')}','${queryField.field_chosen?default('1')}','${queryField.field_autocomplete?default('1')}','${queryField.field_class?default('')}','${queryField.field_placeholder?default('')}','${queryField.field_filtertype?default('1')}','${queryField.field_required?default('0')}','${queryField.field_ordinal?default('')}');
</#list>
commit;

<#if func_auto_field_list?exists && func_auto_field_list?size != 0> 
/*删除查询字段关联的自动完成查询数据*/
delete niutal_designer_auto_fields t
where exists (select 'x'
       from niutal_designer_query_fields a
      where exists (select 'x' from niutal_designer_func_querys b
              where a.query_guid = b.query_guid
                and exists (select 'x' from niutal_designer_func_elements c
                      where b.element_guid = c.element_guid
                        and exists (select 'x'
                               from niutal_designer_func t
                              where t.func_guid = c.func_guid
                                and t.func_code in (select a.gnmkdm
								   from niutal_xtgl_jsgnmkdmb a 
								  start with a.gnmkdm = '${func_code?string}'
								connect by prior a.gnmkdm = a.fjgndm)
                           )
                    )
            )
        and t.target_guid = a.table_guid
    );
commit;
/*增加查询字段关联的自动完成查询数据*/
<#list func_auto_query_field_list as autoModel>
insert into niutal_designer_auto_fields (TABLE_GUID, AUTO_GUID, TARGET_GUID, FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT)
values ('${autoModel.table_guid?string}', '${autoModel.auto_guid?string}', '${autoModel.target_guid?string}', '${autoModel.field_name?string}', '${autoModel.field_text?string}','${autoModel.field_minLength?string}','${autoModel.field_items?string}','${autoModel.field_delay?string}','${autoModel.field_setvalue?string}','${autoModel.field_realvalue?string}','${autoModel.field_format?string}' );
</#list>
commit;
</#if>
</#if>

<#if func_widget_list?exists && func_widget_list?size != 0>  
/*删除元素关联组件数据*/
delete niutal_designer_func_widgets a
 where exists (select 'x' from niutal_designer_func_elements b
         where a.element_guid = b.element_guid
           and exists (select 'x' from niutal_designer_func t
                 where t.func_guid = b.func_guid
                   and t.func_code in (select a.gnmkdm
					   from niutal_xtgl_jsgnmkdmb a 
					  start with a.gnmkdm = '${func_code?string}'
					connect by prior a.gnmkdm = a.fjgndm)
                   ));
commit;                
/*增加元素关联组件数据*/
<#list func_widget_list as widgetModel>
insert into niutal_designer_func_widgets(func_widget_guid,element_guid,widget_guid,widget_title,widget_desc,widget_params,widget_pageable,widget_cacheable, widget_data_url, widget_sql, widget_loadatonce)
values('${widgetModel.func_widget_guid?string}','${widgetModel.element_guid?string}','${widgetModel.widget_guid?string}','${widgetModel.widget_title?string}','${widgetModel.widget_desc?default('')}','${widgetModel.widget_params?default('{}')}','${widgetModel.widget_pageable?default('1')}','${widgetModel.widget_cacheable?default('0')}','${widgetModel.widget_data_url?default('')}','${widgetModel.widget_sql?default('')}','${widgetModel.widget_loadatonce?default('0')}');
</#list>
commit;
</#if>

<#if func_jqgrid_field_list?exists && func_jqgrid_field_list?size != 0> 
/*删除元素关联JQGrid组件列数据*/
delete niutal_designer_jqgrid_fields a
 where exists (select 'x' from niutal_designer_func_widgets b 
         where a.func_widget_guid = b.func_widget_guid 
           and exists (select 'x' from niutal_designer_func_elements c
                 where b.element_guid = c.element_guid
                   and exists (select 'x' from niutal_designer_func t
                         where t.func_guid = c.func_guid
                           and t.func_code in (select a.gnmkdm
						   from niutal_xtgl_jsgnmkdmb a 
						  start with a.gnmkdm = '${func_code?string}'
						connect by prior a.gnmkdm = a.fjgndm)
                           
                           )));
commit;                       
/*增加元素关联JQGrid组件列数据*/
<#list func_jqgrid_field_list as jqgridModel>
insert into niutal_designer_jqgrid_fields(func_widget_guid,field_align,field_cellattr,field_editable,field_editoptions,field_editrules,field_edittype,field_fixed,field_formatter,field_hidden,field_index,field_key,field_label,field_name,field_resizable,field_sortable,field_width,field_param,field_ordinal)
values('${jqgridModel.func_widget_guid?string}','${jqgridModel.field_align?default('center')}','${jqgridModel.field_cellattr?default('')}','${jqgridModel.field_editable?default('0')}','${jqgridModel.field_editoptions?default('')}','${jqgridModel.field_editrules?default('')}','${jqgridModel.field_edittype?default('')}','${jqgridModel.field_fixed?default('0')}','${jqgridModel.field_formatter?default('')}','${jqgridModel.field_hidden?string}','${jqgridModel.field_index?string}','${jqgridModel.field_key?default('0')}','${jqgridModel.field_label?string}','${jqgridModel.field_name?string}','${jqgridModel.field_resizable?default('0')}','${jqgridModel.field_sortable?default('0')}','${jqgridModel.field_width?default('150px')}','${jqgridModel.field_param?default('0')}','${jqgridModel.field_ordinal?string}');
</#list>
commit;
</#if>

<#if func_field_list?exists && func_field_list?size != 0> 
/*删除元素关联字段数据*/
delete niutal_designer_func_fields a
 where exists (select 'x' from niutal_designer_func_elements b
         where a.element_guid = b.element_guid
           and exists (select 'x' from niutal_designer_func t
                 where t.func_guid = b.func_guid
                   and t.func_code in (select a.gnmkdm
						   from niutal_xtgl_jsgnmkdmb a 
						  start with a.gnmkdm = '${func_code?string}'
						connect by prior a.gnmkdm = a.fjgndm)
                   
                   ));
commit;              
/*增加元素关联字段数据*/
<#list func_field_list as fieldModel>
insert into niutal_designer_func_fields(table_guid,element_guid,field_guid,field_id,field_parent,field_name,field_name_width,field_text,field_value,field_shape,field_type,field_attr,field_chosen,field_autocomplete,field_class,field_placeholder,field_ordinal)
values('${fieldModel.table_guid?string}','${fieldModel.element_guid?string}','${fieldModel.field_guid?default('')}','${fieldModel.field_id?string}','${fieldModel.field_parent?default('')}','${fieldModel.field_name?default('')}','${fieldModel.field_name_width?default('')}','${fieldModel.field_text?default('')}','${fieldModel.field_value?default('')}','${fieldModel.field_shape?default('')}','${fieldModel.field_type?default('')}','${fieldModel.field_attr?default('')}','${fieldModel.field_chosen?default('1')}','${fieldModel.field_autocomplete?default('1')}','${fieldModel.field_class?default('')}','${fieldModel.field_placeholder?default('')}','${fieldModel.field_ordinal?default('')}')
</#list>
commit;

<#if func_auto_field_list?exists && func_auto_field_list?size != 0> 
/*删除字段关联的自动完成查询数据*/
delete niutal_designer_auto_fields t
  where exists (select 'x'
           from niutal_designer_func_fields a
          where exists (select 'x' from niutal_designer_func_elements b
                  where a.element_guid = b.element_guid
                    and exists (select 'x' from niutal_designer_func t
                          where t.func_guid = b.func_guid
                            and t.func_code in (select a.gnmkdm
							   from niutal_xtgl_jsgnmkdmb a 
							  start with a.gnmkdm = '${func_code?string}'
							connect by prior a.gnmkdm = a.fjgndm)
                    )
            )
        and t.target_guid = a.table_guid
    );
commit;
/*增加字段关联的自动完成查询数据*/
<#list func_auto_field_list as autoModel>
insert into niutal_designer_auto_fields (TABLE_GUID, AUTO_GUID, TARGET_GUID, FIELD_NAME, FIELD_TEXT, FIELD_MINLENGTH, FIELD_ITEMS, FIELD_DELAY, FIELD_SETVALUE, FIELD_REALVALUE, FIELD_FORMAT)
values ('${autoModel.table_guid?string}', '${autoModel.auto_guid?string}', '${autoModel.target_guid?string}', '${autoModel.field_name?string}', '${autoModel.field_text?string}','${autoModel.field_minLength?string}','${autoModel.field_items?string}','${autoModel.field_delay?string}','${autoModel.field_setvalue?string}','${autoModel.field_realvalue?string}','${autoModel.field_format?string}' );
</#list>
commit;
</#if>
</#if>

<#if func_resource_list?exists && func_resource_list?size != 0> 
/*删除功能绑定的js,css资源数据*/
delete niutal_designer_func_resources t
 where exists (select 'x'
          from niutal_designer_func b
         where t.func_guid = b.func_guid
           and b.func_code in (select a.gnmkdm
			   from niutal_xtgl_jsgnmkdmb a 
			  start with a.gnmkdm = '${func_code?string}'
			connect by prior a.gnmkdm = a.fjgndm)
         );
commit;
/*增加功能绑定的js,css资源数据*/
<#list func_resource_list as resourceModel>
insert into niutal_designer_func_resources (FUNC_GUID, RESOURCE_GUID, WIDGET_GUID, AUTO_INSERT, RESOURCE_NAME, RESOURCE_TEXT, RESOURCE_ORDINAL, RESOURCE_TYPE)
values ('${resourceModel.func_guid?string}','${resourceModel.resource_guid?string}','${resourceModel.widget_guid?default('')}','${resourceModel.auto_insert?default('0')}','${resourceModel.resource_name?default('')}','${resourceModel.resource_text?default('')}','${resourceModel.resource_ordinal?default('')}','${resourceModel.resource_type?default('')}');
</#list>
commit;
</#if>	

<#if func_report_list?exists && func_report_list?size != 0> 
/*删除自定义高级报表设计表数据*/
delete niutal_designer_reports t
 where exists (select 'x'
          from niutal_designer_func b
         where t.func_guid = b.func_guid
            and b.func_code in (select a.gnmkdm
			   from niutal_xtgl_jsgnmkdmb a 
			  start with a.gnmkdm = '${func_code?string}'
			connect by prior a.gnmkdm = a.fjgndm)
           );
commit;
/*增加自定义高级报表设计表数据*/
<#list func_report_list as reportModel>
insert into niutal_designer_reports (FUNC_GUID, REPORT_GUID)
values ('${reportModel.func_guid?string}', '${reportModel.report_guid?string}');
</#list>
commit;
</#if>	
</#noescape>
</#escape>