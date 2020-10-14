package com.woshidaniu.ms.event;

import java.net.Authenticator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.woshidaniu.api.event.XtcsUpdateEvent;
import com.woshidaniu.basicutils.BooleanUtils;
import com.woshidaniu.javamail.JVMNetProxy;
import com.woshidaniu.javamail.auth.ProxyAuthenticator;

/**
 * 
 *@类名称		： XtcsUpdateEventListener.java
 *@类描述		：系统参数更新事件监听，实现与业务代码解耦；比如开启关闭网络代理
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月20日 下午3:22:15
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@Component
@Async
public class XtcsUpdateEventListener implements ApplicationListener<XtcsUpdateEvent> {
	
	@Override
	public void onApplicationEvent(XtcsUpdateEvent event) {
		
		//第一步：获取绑定的参数数据
		Map<String, String> tempMap = event.getBind();
		
		//第二步：调用参数变化要调用的逻辑代码
		
		//网络代理开关
		//使用网络代理收发邮件
		String net_proxy_enable = tempMap.get("net." + JVMNetProxy.JVM_NET_PROXY_ENABLE);
		if(BooleanUtils.parse(net_proxy_enable)){
			
			//网络代理类型 nomoral,http,https,socks
			String net_proxy_type = tempMap.get("net." + JVMNetProxy.JVM_NET_PROXY_TYPE);
			String net_proxy_user = tempMap.get("net." + JVMNetProxy.JVM_NET_PROXY_USER);
			String net_proxy_password = tempMap.get("net." + JVMNetProxy.JVM_NET_PROXY_PASSWORD);
			// 网络代理服主机IP地址
			String net_proxy_host = tempMap.get("net." + JVMNetProxy.JVM_PROXY_HOST);
			// 网络代理服主机的端口
			String net_proxy_port = tempMap.get("net." + JVMNetProxy.JVM_PROXY_PORT);
			// 设置不需要通过代理服务器访问的主机，可以使用*通配符，多个地址用|分隔
			String net_proxy_non_hosts = tempMap.get("net." + JVMNetProxy.JVM_PROXY_NON_HOSTS);
			
			/*------------------------ JVM 网络代理设置 ------------------------*/
			
			Properties prop = System.getProperties();
			
			prop.setProperty(JVMNetProxy.JVM_NET_PROXY_ENABLE, "true");
			
			if("nomoral".equalsIgnoreCase(net_proxy_type)){
				
				prop.setProperty(JVMNetProxy.JVM_PROXY_HOST, net_proxy_host );
				prop.setProperty(JVMNetProxy.JVM_PROXY_PORT, net_proxy_port);
				prop.setProperty(JVMNetProxy.JVM_PROXY_NON_HOSTS, net_proxy_non_hosts );
				
			}
			// Http网络代理
			else if("http".equalsIgnoreCase(net_proxy_type)){
				
				prop.setProperty(JVMNetProxy.JVM_HTTP_PROXY_HOST, net_proxy_host );
				prop.setProperty(JVMNetProxy.JVM_HTTP_PROXY_PORT, net_proxy_port);
				prop.setProperty(JVMNetProxy.JVM_HTTP_PROXY_NON_HOSTS, net_proxy_non_hosts );
				prop.setProperty(JVMNetProxy.JVM_HTTP_PROXY_USER, "");
				prop.setProperty(JVMNetProxy.JVM_HTTP_PROXY_PASSWORD, "");
		        
				if(StringUtils.isNotEmpty(net_proxy_user)){
					// 设置登陆到代理服务器的用户名和密码
			        Authenticator.setDefault(new ProxyAuthenticator(net_proxy_user, net_proxy_password)); 
				}
				
			}
			// Https网络代理
			else if("https".equalsIgnoreCase(net_proxy_type)){
				
				prop.setProperty(JVMNetProxy.JVM_HTTPS_PROXY_PROXYHOST, net_proxy_host );
				prop.setProperty(JVMNetProxy.JVM_HTTPS_PROXY_PROXYHOST, net_proxy_port);
			}
			// Socks网络代理服务器的地址与端口
			else if("socks".equalsIgnoreCase(net_proxy_type)){
				
		 		prop.setProperty(JVMNetProxy.JVM_SOCKS_PROXY_HOST, net_proxy_host);
		 		prop.setProperty(JVMNetProxy.JVM_SOCKS_PROXY_PORT, net_proxy_port);
			}
			

		} else{
			
			/*------------------------ 取消 JVM 网络代理设置 ------------------------*/
			Properties prop = System.getProperties();
			prop.remove(JVMNetProxy.JVM_NET_PROXY_ENABLE);
			prop.remove(JVMNetProxy.JVM_PROXY_HOST);
			prop.remove(JVMNetProxy.JVM_PROXY_PORT);
			prop.remove(JVMNetProxy.JVM_PROXY_NON_HOSTS);
			prop.remove(JVMNetProxy.JVM_HTTP_PROXY_HOST);
			prop.remove(JVMNetProxy.JVM_HTTP_PROXY_PORT);
			prop.remove(JVMNetProxy.JVM_HTTP_PROXY_USER);
			prop.remove(JVMNetProxy.JVM_HTTP_PROXY_PASSWORD);
			prop.remove(JVMNetProxy.JVM_HTTP_PROXY_NON_HOSTS);
			prop.remove(JVMNetProxy.JVM_HTTPS_PROXY_PROXYHOST);
			prop.remove(JVMNetProxy.JVM_HTTPS_PROXY_PROXYHOST);
			prop.remove(JVMNetProxy.JVM_SOCKS_PROXY_HOST);
			prop.remove(JVMNetProxy.JVM_SOCKS_PROXY_PORT);
			
		}
		
		 
	}
	
}