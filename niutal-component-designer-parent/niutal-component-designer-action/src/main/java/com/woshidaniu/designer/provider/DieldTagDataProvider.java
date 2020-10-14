package com.woshidaniu.designer.provider;

import org.apache.struts2.components.UIBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.woshidaniu.beanutils.reflection.ReflectionUtils;
import com.woshidaniu.struts2.provider.ITagDataProvider;
import com.woshidaniu.struts2.provider.TagDataPair;

/**
 * 
 *@类名称		： niutalTagDataProvider.java
 *@类描述		：
 *@创建人		：kangzhidong
 *@创建时间	：2017年3月27日 下午5:27:59
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@Component("fieldDataProvider")
public class DieldTagDataProvider implements ITagDataProvider {

	protected static Logger LOG = LoggerFactory.getLogger(DieldTagDataProvider.class);

	@Override
	public TagDataPair getBindData(UIBean uiBean,String stackKey) {
		try {
			//从struts上下文取值
			Object ret = ReflectionUtils.getAccessibleMethod(uiBean, "findValue", String.class).invoke(uiBean, stackKey);
			String listKey = "key";
			String listValue = "value";
			if("njList".equals(stackKey)){//年级
				listKey = "njdm_id";
				listValue = "njmc";
			}else if("xyList".equals(stackKey)){//学院
				listKey = "jg_id";
				listValue = "jgmc";
			}else if("xList".equals(stackKey)){//系
				listKey = "jg_id";
				listValue = "jgmc";
			}else if("zyList".equals(stackKey)){//专业
				listKey = "zyh_id";
				listValue = "zymc";
			}else if("zszyList".equals(stackKey)){//招生专业
				listKey = "zyh_id";
				listValue = "zymc";
			}else if("zyfxList".equals(stackKey)){//专业方向
				listKey = "zyfx_id";
				listValue = "zyfxmc";
			}else if("xqList".equals(stackKey)){//校区
				listKey = "xqh_id";
				listValue = "xqmc";
			}else if("bjList".equals(stackKey)){//班级
				listKey = "bh_id";
				listValue = "bj";
			}else if("xnmList".equals(stackKey)){//学年
				listKey = "xnm";
				listValue = "xnmc";
			}else if("xqmList".equals(stackKey)){//大学期
				listKey = "dm";
				listValue = "mc";
			}
			return new TagDataPair(ret,listKey,listValue);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return null;
	}
	
	
}
