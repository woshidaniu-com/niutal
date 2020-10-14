package com.woshidaniu.service.impl;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.daointerface.IKlwhDao;
import com.woshidaniu.entities.XsmmModel;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.security.algorithm.MD5Codec;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.KlwhService;

/**
 * 
 *@类名称:KlwhServiceImpl.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：2015-1-30 上午09:44:19
 *@版本号:v1.0
 */
@Service
public class KlwhServiceImpl extends CommonBaseServiceImpl<XsmmModel, IKlwhDao> implements KlwhService {
	
	@Resource
	private IKlwhDao dao;
	/**
	 * 批量初始化
	 */
	public boolean plCsh(XsmmModel model) throws Exception {
		boolean flag = false;
		int result =0;
		if(("1").equals(model.getGzlx())){//输入密码初始化
			model.setMm(MD5Codec.encrypt(model.getMm()));
			result = dao.updateForMany(model);
		}else{//根据身份号后刘位全部初始化
			List<XsmmModel> list = clmmBySfz(getXsxxList(model));
			result = dao.updateForManySFZ(list);
		}
		flag = result > 0 ? true : false;
		return flag;
	}
	/**
	 * 
	* 方法描述: 全部初始化
	* 参数 @return 参数说明
	* 返回类型  boolean 返回类型
	*/
	public boolean qbCsh(XsmmModel model)throws Exception
	{
		boolean flag = false;
		int result =0;
		if(("1").equals(model.getGzlx())){//输入密码初始化
			model.setMm(MD5Codec.encrypt(model.getMm()));
			result = dao.update(model);
		}else{//根据身份号后刘位全部初始化
			List<XsmmModel> list = clmmBySfz(getXsxxList(model));
			result = dao.updateForManySFZ(list);
		}
		flag = result > 0 ? true : false;
		return flag;
	}

	public List<XsmmModel> getXsxxList(XsmmModel model) throws Exception {
		return dao.getXsxxList(model);
	}
	/**
	 * 根据学生信息处理密码为身份证后6位，如果身份证不存在，则密码出初始化为6个0
	 * @param list
	 * @return Map
	 */
	private List<XsmmModel> clmmBySfz(List<XsmmModel>  list){
		String mm=null;
		String sfzh="";
		for (XsmmModel xsmmModel : list) {
			sfzh = xsmmModel.getSfzh();
			mm="";
			if(StringUtils.isNotEmpty(sfzh)&& sfzh.length()>=6)
			{
				mm = MD5Codec.encrypt(sfzh.substring(sfzh.length()-6,sfzh.length()));
			}
			else
			{
				mm = MD5Codec.encrypt("000000");
			}
			xsmmModel.setMm(mm);
		}
		
		return list;
	}
	//产生随机数
	public static String getRandomNum(int len){
		int i;
		int count = 0;
		char[] str = {'a','b','c','d','e','f','g','h','i','j','k','l','m',
				'n','o','p','q','r','s','t','u','v','w','x','y','z','0','1'
				,'2','3','4','5','6','7','8','9'};
		StringBuffer pwd = new StringBuffer();
		Random r = new Random();
		while(count<len){
			i=Math.abs(r.nextInt(36));
			if(i>=0&&i<str.length){
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}
}
