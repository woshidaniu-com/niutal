package com.woshidaniu.filemgr.api.exception;

import java.io.IOException;

@SuppressWarnings("serial")
public class FilemgrException extends IOException {
	
    public FilemgrException() {
        super();
    }

    public FilemgrException(String s) {
        super(s);
    }


}
