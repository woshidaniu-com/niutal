
---字符拼接func_concat：以下SQL需要依次执行；不可一次性全部执行
--1:定义类型
CREATE OR REPLACE TYPE STRING_AGG_TYPE as
  object(total varchar2(4000), static function ODCIAggregateInitialize(sctx IN OUT string_agg_type) return number, member function ODCIAggregateIterate(self IN OUT string_agg_type, value IN varchar2) return number, member function ODCIAggregateTerminate(self IN string_agg_type, returnValue OUT varchar2, flags IN number) return number, member function ODCIAggregateMerge(self IN OUT string_agg_type, ctx2 IN string_agg_type) return number);

  create or replace type body string_agg_type is
    static function ODCIAggregateInitialize(sctx IN OUT string_agg_type) return number is
  begin
    sctx := string_agg_type(''); return ODCIConst.Success;
  end; 
  member function ODCIAggregateIterate(self IN OUT string_agg_type, value IN varchar2) return number is
  begin
    self.total := self.total || ';' || value; return ODCIConst.Success;
  end; 
  member function ODCIAggregateTerminate(self IN string_agg_type, returnValue OUT varchar2, flags IN number) return number is
  begin
    returnValue := ltrim(self.total, ';'); return ODCIConst.Success;
  end; 
  member function ODCIAggregateMerge(self IN OUT string_agg_type, ctx2 IN string_agg_type) return number is
  begin
    self.total := self.total || ctx2.total; return ODCIConst.Success;
  end;
end;

--2：定义行数  
CREATE OR REPLACE FUNCTION func_concat(input varchar2) RETURN varchar2
PARALLEL_ENABLE AGGREGATE USING string_agg_type;


--select func_concat(t.jgmc)  from niutal_xtgl_jgdmb t