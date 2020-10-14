package com.woshidaniu.editor.action.comparator;

import java.util.UUID;

public class UUIDDemo {
	public static void main(String[] args) {
	      // creating UUID      
	      UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");     
	      System.out.println("Random UUID value: "+uid.toString()); 
	      // checking the value of random UUID
	      System.out.println("Random UUID value: "+UUID.randomUUID().toString());    
	   }    
}
