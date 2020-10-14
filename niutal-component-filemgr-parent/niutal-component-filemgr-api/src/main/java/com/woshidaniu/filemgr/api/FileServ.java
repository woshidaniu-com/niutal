package com.woshidaniu.filemgr.api;

public enum FileServ {

	FTP("ftp"), SAMBA("samba"), LOCAL("local");

	protected String name;

	private FileServ(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public boolean compareTo(String name) {
		return this.getName().equalsIgnoreCase(name);
	}

}
