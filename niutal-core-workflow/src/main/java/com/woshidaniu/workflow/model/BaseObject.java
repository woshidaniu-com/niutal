package com.woshidaniu.workflow.model;

import java.io.Serializable;
import com.woshidaniu.common.query.QueryModel;

/**
 * Base class for Model objects. Child objects should implement toString(), equals() and hashCode().
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public abstract class BaseObject implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public QueryModel         queryModel       = new QueryModel();

    private String            doType;

    private String            pkValue;

    /*
     * 错误码，0表示没有错误，其他值表示有错误
     */
    private Long              errorCode;
    /*
     * errorCode!=0时，表示与错误码相关的错误描述 errorCode=0时，如果errorMessage有值，那么是warning信息
     */
    private String            errorMessage;

    public void setErrorCode(Long errorCode) {
        this.errorCode = errorCode;
    }

    public Long getErrorCode() {
        return errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public QueryModel getQueryModel() {
        return queryModel;
    }

    public void setQueryModel(QueryModel queryModel) {
        this.queryModel = queryModel;
    }

    public String getDoType() {
        return doType;
    }

    public void setDoType(String doType) {
        this.doType = doType;
    }

    public String getPkValue() {
        return pkValue;
    }

    public void setPkValue(String pkValue) {
        this.pkValue = pkValue;
    }

    /**
     * Returns a multi-line String with key=value pairs.
     * 
     * @return a String representation of this class.
     */
    // public abstract String toString();

    /**
     * Compares object equality. When using Hibernate, the primary key should not be a part of this comparison.
     * 
     * @param o object to compare to
     * @return true/false based on equality tests
     */
    // public abstract boolean equals(Object o);

    /**
     * When you override equals, you should override hashCode. See "Why are equals() and hashCode() importation" for more
     * information: http://www.hibernate.org/109.html
     * 
     * @return hashCode
     */
    // public abstract int hashCode();
}
