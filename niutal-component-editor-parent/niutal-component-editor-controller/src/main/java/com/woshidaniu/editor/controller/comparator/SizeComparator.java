package com.woshidaniu.editor.controller.comparator;

import java.util.Comparator;
import java.util.Hashtable;


/**
 * 按文件大小排序
 */
@SuppressWarnings("rawtypes")
public class SizeComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable oldMap = (Hashtable) a;
		Hashtable newMap = (Hashtable) b;
		if (((Boolean) oldMap.get("is_dir")) && !((Boolean) newMap.get("is_dir"))) {
			return -1;
		} else if (!((Boolean) oldMap.get("is_dir")) && ((Boolean) newMap.get("is_dir"))) {
			return 1;
		} else {
			if (((Long) oldMap.get("filesize")) > ((Long) newMap .get("filesize"))) {
				return 1;
			} else if (((Long) oldMap.get("filesize")) < ((Long) newMap .get("filesize"))) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}

