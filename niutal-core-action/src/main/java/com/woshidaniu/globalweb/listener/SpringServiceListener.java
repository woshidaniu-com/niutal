package com.woshidaniu.globalweb.listener;

import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.dao.entities.XtszModel;
import com.woshidaniu.service.svcinterface.IXtszService;
import com.woshidaniu.util.base.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Administrator
 * 
 */
public class SpringServiceListener implements ServletContextListener {
	private static Logger log = LoggerFactory.getLogger(SpringServiceListener.class);

	public SpringServiceListener() {
		super();
	}

	/**
	 * 系统初始化
	 */
	public void contextInitialized(ServletContextEvent event) {
		try {
			final ServletContext application = event.getServletContext();
			clearTempFile(application);
		} catch (Exception e) {
			log.error("删除临时文件失败", e);
		}

		try {
			initSystemParameter(event);
			log.info("系统启动成功");
		} catch (Exception e) {
			log.error("系统启动失败", e);
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		log.info("系统停止");
	}

	/**
	 * 初始化系统参数
	 * 
	 * @param application
	 */
	private void initSystemParameter(ServletContextEvent event) {
		// 获取系统设置
		IXtszService xtszService = (IXtszService) ServiceFactory
				.getService("xtszService");
		XtszModel xtszModel = xtszService.getModel("");
		ServletContext application = event.getServletContext();
		// 初始化数据到 ServiletContext
		application.setAttribute("xxdm", xtszModel.getXxdm());
		application.setAttribute("xxmc", xtszModel.getXxmc());
	}

	/**
	 * 清除临时文件
	 * 
	 * @param application
	 */
	private void clearTempFile(final ServletContext application) {

		// 启动线程删除临时文件。
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Thread() {
			@Override
			public void run() {
				String tempPath = MessageUtil.getText("export_temp_path");
				File file = new File(application.getRealPath(tempPath));

				if (file.exists()) {
					File[] tempFiles = file.listFiles();
					if (tempFiles != null && tempFiles.length > 0) {
						Date curDate = new Date();
						for (File tempFile : tempFiles) {
							// 删除当之前创建的文件
							if (curDate.getTime() / 60 / 60 / 1000
									- tempFile.lastModified() / 60 / 60 / 1000 > 1) {
								log.debug("删除临时文件：" + tempFile.getName());
								tempFile.delete();
							}
						}
					}
				}
			}
		}, 1, 1, TimeUnit.HOURS);
		;
	}
}
