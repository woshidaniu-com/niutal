package com.woshidaniu.datarange.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Filtration {
	
	public String target() default "";
	public int type() default FiltrationType.LIMIT;
	public String kzdx() default "xs";
	
}