
-- Create/Recreate primary, unique and foreign key constraints 
alter table niutal_QUERY_FIELDS add constraint P_niutal_QUERY_FIELDS primary key (FIELD_GUID);
alter table niutal_QUERY_FIELDS add constraint U_niutal_QUERY_FIELDS unique (FIELD_ID);

-- Create/Recreate primary, unique and foreign key constraints 
alter table niutal_AUTOCOMPLETE_FIELDS add constraint P_niutal_AUTOCOMPLETE_FIELDS primary key (AUTO_GUID);
alter table niutal_AUTOCOMPLETE_FIELDS add constraint U_niutal_AUTOCOMPLETE_FIELDS unique (FIELD_NAME);

-- Create/Recreate primary, unique and foreign key constraints 
alter table niutal_WIDGET_DETAILS add constraint P_niutal_WIDGET_DETAILS primary key (WIDGET_GUID);

-- Create/Recreate primary, unique and foreign key constraints 
alter table niutal_WIDGET_PARAMETERS add constraint P_niutal_widget_parameters primary key (PARAM_GUID);

-- Create/Recreate primary, unique and foreign key constraints 
alter table niutal_WIDGET_RESOURCES add constraint P_niutal_widget_resources primary key (RESOURCE_GUID);

-- Create/Recreate primary, unique and foreign key constraints 
alter table niutal_DESIGNER_FUNC  add constraint P_niutal_DESIGNER_FUNC primary key (FUNC_GUID);
alter table niutal_DESIGNER_FUNC  add constraint U_niutal_DESIGNER_FUNC unique (FUNC_CODE, OPT_CODE);

-- Create/Recreate primary, unique and foreign key constraints 
alter table niutal_DESIGNER_FUNC_ELEMENTS add constraint P_niutal_DESIGNER_FUNC_ELEMENTS primary key (ELEMENT_GUID);

-- Create/Recreate primary, unique and foreign key constraints 
alter table niutal_DESIGNER_FUNC_WIDGETS add constraint P_niutal_DESIGNER_FUNC_WIDGETS primary key (FUNC_WIDGET_GUID);

-- Create/Recreate primary, unique and foreign key constraints 
alter table niutal_DESIGNER_JQGRID_FIELDS add constraint P_niutal_DESIGNER_JQGRID_FIELDS primary key (FIELD_GUID);
 
-- Create/Recreate primary, unique and foreign key constraints 
alter table niutal_DESIGNER_FUNC_FIELDS add constraint P_niutal_DESIGNER_FUNC_FIELDS primary key (TABLE_GUID);
 
 -- Create/Recreate primary, unique and foreign key constraints 
alter table niutal_DESIGNER_FUNC_QUERYS add constraint P_niutal_DESIGNER_FUNC_QUERYS primary key (QUERY_GUID);

-- Create/Recreate primary, unique and foreign key constraints 
alter table niutal_DESIGNER_QUERY_FIELDS add constraint P_niutal_DESIGNER_QUERY_FIELDS primary key (TABLE_GUID);

-- Create/Recreate primary, unique and foreign key constraints 
alter table niutal_DESIGNER_AUTO_FIELDS add constraint P_niutal_DESIGNER_AUTO_FIELDS primary key (TABLE_GUID);
 
-- Create/Recreate primary, unique and foreign key constraints 
alter table niutal_REPORT_DETAILS add constraint P_niutal_REPORT_DETAILS primary key (REPORT_GUID);

 