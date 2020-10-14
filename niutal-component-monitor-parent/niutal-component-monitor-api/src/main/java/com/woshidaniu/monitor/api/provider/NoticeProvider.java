package com.woshidaniu.monitor.api.provider;

import java.util.Map;

public interface NoticeProvider {

	public static String CUP_THRESHOLD = "watch.cup.threshold";
	public static String RAM_THRESHOLD = "watch.ram.threshold";
	public static String JVM_THRESHOLD = "watch.jvm.threshold";
	public static String NOTICE_STATUS = "watch.notice.status";
	public static String NOTICE_PERIOD = "watch.notice.period";
	public static String NOTICE_TYPE = "watch.notice.type";
	public static String MAIL_TO = "watch.mail.to";
	public static String SMS_TO = "watch.sms.to";
	
	void notice(Map<String, Double> usage);

}
