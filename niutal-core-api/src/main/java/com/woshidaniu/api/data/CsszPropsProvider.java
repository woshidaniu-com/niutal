package com.woshidaniu.api.data;

import java.util.List;
import java.util.Map;

public interface CsszPropsProvider {

	public List list(String ssmk, String ssgnmkdm, String zs, Map<String, String[]> params);
	
	public void update(String ssmk, String ssgnmkdm, String zs, Map<String, String[]> params);
	
}
