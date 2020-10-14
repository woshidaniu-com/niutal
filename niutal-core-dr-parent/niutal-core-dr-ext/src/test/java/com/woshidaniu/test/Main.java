package com.woshidaniu.test;

import com.woshidaniu.drdcsj.drsj.comm.ImportDataException;

public class Main {
	
	private static void test_c(int i){
		if(i == 1){
			throw new ImportException();
		}else{
			throw new ImportDataException();			
		}
	}

	public static void main(String[] args) {
		try{
			test_c(2);
		}catch (ImportException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
