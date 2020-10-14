/**
 * created since Mar 12, 2009
 */
package com.woshidaniu.workflow.exception;

/**
 * 
 * 类描述：DAO层异常类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:38:45
 */
public class DaoException extends BaseException {
    private static final long serialVersionUID = -4131602370503139777L;
    /** 序列值 */

    private boolean printable=true;

    /**
     *
     */
    public DaoException() {
        super();
    }

    /**
     * @param message
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(Throwable cause,boolean printable){
        super(cause);
        this.printable=printable;
    }

    public DaoException(boolean printable) {
        super();
        this.printable=printable;
    }

    /**
     * @param message
     */
    public DaoException(String message,boolean printable) {
        super(message);
        this.printable=printable;
    }

    /**
     * @param message
     * @param cause
     */
    public DaoException(String message, Throwable cause,boolean printable) {
        super(message, cause);
        this.printable=printable;
    }

    /**
     * @return the printable
     */
    public boolean isPrintable() {
        return printable;
    }

    /**
     * @param printable the printable to set
     */
    public void setPrintable(boolean printable) {
        this.printable = printable;
    }
}
