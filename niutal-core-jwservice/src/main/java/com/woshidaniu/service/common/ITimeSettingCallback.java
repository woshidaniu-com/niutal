package com.woshidaniu.service.common;

import com.woshidaniu.entities.TimeSettingModel;
/**
 * 
 *@类名称:ITimeSettingCallback.java
 *@类描述：时间控制逻辑判断接口，提供给开发人员自己实现的接口
 *@创建人：kangzhidong
 *@创建时间：2015-2-10 上午09:25:25
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface ITimeSettingCallback {
	
	public boolean doInTimeSetting(TimeSettingModel model);

}
