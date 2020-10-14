/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.io.utils.IOUtils;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.WebContext;
import com.woshidaniu.zxzx.aip.SqlCondition;
import com.woshidaniu.zxzx.dao.daointerface.ICjwtDao;
import com.woshidaniu.zxzx.dao.daointerface.IZxhfDao;
import com.woshidaniu.zxzx.dao.daointerface.IZxwtDao;
import com.woshidaniu.zxzx.dao.entities.CjwtModel;
import com.woshidaniu.zxzx.dao.entities.ZxhfModel;
import com.woshidaniu.zxzx.dao.entities.ZxwtEventModel;
import com.woshidaniu.zxzx.dao.entities.ZxwtModel;
import com.woshidaniu.zxzx.service.svcinterface.IZxwtService;

/**
 * @类名 ZxwtServiceImpl.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月24日 下午7:21:35
 * @功能描述 在线咨询-咨询问题
 * 
 */
@Service("zxzxZxwtService")
public class ZxwtServiceImpl extends BaseServiceImpl<ZxwtModel, IZxwtDao> implements IZxwtService {
	
	private static final Logger log = LoggerFactory.getLogger(ZxwtServiceImpl.class);
	
	private static final String TEMP_PATH_KEY = "export_temp_path";
	
	private static final String PAGED_LIST_CONDITION_CONFIG_KEY = "com.woshidaniu.zxzx.dao.daointerface.IZxwtDao.getPagedList.conditionSql";
	
	private static final String MAP_LIST_CONDITION_CONFIG_KEY = "com.woshidaniu.zxzx.dao.daointerface.IZxwtDao.getMapList.conditionSql";
	
	private Map<String,SqlCondition> sqlConditionMap = new HashMap<String,SqlCondition>(2);
	
	@Autowired
	@Qualifier("zxzxZxhfDao")
	private IZxhfDao zxhfDao;

	@Autowired
	@Qualifier("zxzxCjwtDao")
	private ICjwtDao cjwtDao;

	@Override
	@Autowired
	@Qualifier("zxzxZxwtDao")
	public void setDao(IZxwtDao dao) {
		this.dao = dao;
	}
	
	@PostConstruct
	public void init() {
		this.initConditionSql(PAGED_LIST_CONDITION_CONFIG_KEY);
		this.initConditionSql(MAP_LIST_CONDITION_CONFIG_KEY);
	}
	
	private void initConditionSql(String configKey) {
		String className = MessageUtil.getText(configKey);
		if(StringUtils.isEmpty(className)) {
			return;
		}
		try {
			Class<?> clazz = Class.forName(className);
			Object instance = clazz.newInstance();
			if(instance instanceof SqlCondition) {
				SqlCondition sqlCondition = (SqlCondition)instance;
				sqlConditionMap.put(configKey, sqlCondition);
			}else {
				log.warn("配置的类:{}不是SqlCondition的实现类,忽略",className);
			}
		} catch (Exception e) {
			log.error("",e);
		}
	}

	private void setConditionSql(String configKey,ZxwtModel model) {
		//提前清理,防止SQL注入
		model.setConditionSql("");
		SqlCondition sqlCondition = sqlConditionMap.get(configKey);
		if(sqlCondition != null) {
			String condition = sqlCondition.getSqlCondition();
			log.debug("从配置类:{}获得ConditionSql:{}",configKey,condition);
			model.setConditionSql(condition);
		}
	}
	/**
	 * 分页查询
	 * @param t
	 * @return
	 */
	@Override
	public List<ZxwtModel> getPagedList(ZxwtModel model){
		this.setConditionSql(PAGED_LIST_CONDITION_CONFIG_KEY, model);
		return this.dao.getPagedList(model);
	}
	/**
	 * 点击事件记录
	 * 
	 * @param model
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean clickEventRecord(ZxwtModel model) {
		boolean result = Boolean.TRUE;
		ZxwtModel updateModel = new ZxwtModel();
		updateModel.setDjl(model.getDjl());
		updateModel.setZxid(model.getZxid());
		dao.updateDjl(model);

		ZxwtEventModel eventModel = new ZxwtEventModel();
		eventModel.setBkdm(model.getBkdm());
		eventModel.setZxid(model.getZxid());
		eventModel.setDjsj(DateUtils.format(System.currentTimeMillis()));
		dao.insertEvent(eventModel);

		return result;
	}

	/**
	 * 设置为常见问题
	 * 
	 * @param model
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean szCjwt(ZxwtModel model) {
		// 先插入常见问题表一条数据
		CjwtModel cjwtModel = new CjwtModel();
		cjwtModel.setZxid(model.getZxid());
		cjwtModel.setCjsj(DateUtils.format(System.currentTimeMillis()));
		cjwtModel.setWtid(UniqID.getInstance().getUniqIDHash());
		cjwtModel.setBkdm(model.getkzdkModel().getBkdm());
		cjwtModel.setWtbt(model.getkzdt());
		cjwtModel.setSffb("1");
		cjwtModel.setWthf(model.getZxhfModel().getHfnr());
		cjwtModel.setWtnr(model.getZxnr());
		// 更新咨询问题的是否是常见问题字段为1
		ZxwtModel zxwtModel = new ZxwtModel();
		zxwtModel.setZxid(model.getZxid());
		zxwtModel.setCjwt("1");
		return (cjwtDao.insert(cjwtModel) + dao.update(zxwtModel)) > 1;
	}

	/**
	 * 提交回复信息
	 * 
	 * @param model
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean submitHfxx(ZxwtModel model) {
		// 更新咨询表的是否公开字段
		dao.update(model);
		// 删除老的回复信息
		zxhfDao.delete(model.getZxhfModel());
		// 新增回复表记录
		zxhfDao.insert(model.getZxhfModel());
		return true;
	}

	/**
	 * 用于导出
	 * 
	 * @param model
	 * @return
	 */
	@Override
	public List<Map<String, String>> getExportData(ZxwtModel model) {
		this.setConditionSql(MAP_LIST_CONDITION_CONFIG_KEY, model);
		return dao.getMapList(model);
	}

	/**
	 * web端查询
	 * 
	 * @param model
	 * @return
	 */
	@Override
	public List<ZxwtModel> getPagedListWeb(ZxwtModel model) {
		return dao.getPagedListWeb(model);
	}

	/**
	 * 用于web端我的咨询查询
	 * 
	 * @param model
	 * @return
	 */
	@Override
	public List<ZxwtModel> getPagedListMytopicWeb(ZxwtModel model) {
		return dao.getPagedListMytopicWeb(model);
	}

	/**
	 * 获取热门咨询
	 * 
	 * @param num
	 * @return
	 */
	@Override
	public List<ZxwtModel> getTopWeb(String bkdm, int fs, int sjd, int ts) {
		if (fs == 2) {
			String endData = DateUtils.format(DateUtils.DATE_FORMAT_FOUR);

			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -sjd);

			String startDate = DateUtils.format(cal.getTime(), DateUtils.DATE_FORMAT_FOUR);
			return dao.getTopListWebAsActive(bkdm, ts, startDate, endData);
		} else {
			return dao.getTopListWeb(bkdm, ts);
		}
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateDjl(ZxwtModel model) {
		return dao.updateDjl(model);
	}

	/**
	 * 用户删除数据web
	 * 
	 * @param list
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean batchDeleteWeb(List list, String yhm) {
		return dao.batchDeleteWeb(list, yhm) > 0;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean updateWeb(ZxwtModel model) {
		return dao.updateWeb(model) > 0;
	}

	private String getCellStringVal(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        switch (cellType) {
            case NUMERIC:
                return cell.getStringCellValue();
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            case ERROR:
                return String.valueOf(cell.getErrorCellValue());
            default:
                return StringUtils.EMPTY;
        }
    }
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void handleBatchZxwtFile(InputStream fileSteam, String zgh) {
		//读取文件数据
		if(null == fileSteam) {
			throw new RuntimeException("上传文件为空");			
		}
		//处理数据
		Workbook book = null;
		// 获取文件工作表
		try {
			book = new XSSFWorkbook(fileSteam);
			Sheet sheet = book.getSheetAt(0);
			ZxwtModel updateModel = null;
			ZxhfModel zxhfModel = null;
			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                Cell zxidCell = row.getCell(0);
                Cell hfnrCell = row.getCell(6);
                Cell sfgkCell = row.getCell(5);
               
                DataFormatter formatter = new DataFormatter();
                String hfnr = formatter.formatCellValue(hfnrCell);
                
                String zxid = zxidCell.getStringCellValue();
                //String hfnr = hfnrCell.getStringCellValue();
                String sfgk = sfgkCell.getStringCellValue();
                
                if(StringUtils.isBlank(zxid) || StringUtils.isBlank(hfnr))
                	continue;
                
                if(StringUtils.equals("否", sfgk)) {
                	sfgk = "0";                	
                }else {
                	sfgk = "1";                	
                }
                
                updateModel = new ZxwtModel();
    			zxhfModel = new ZxhfModel();;
    			updateModel.setZxhfModel(zxhfModel);
    			
    			zxhfModel.setHfid(UniqID.getInstance().getUniqIDHash());
    			zxhfModel.setHfr(zgh);
    			zxhfModel.setHfnr(hfnr);
    			zxhfModel.setHfsj(DateUtils.format(new Date()));
    			zxhfModel.setZxid(zxid);
    			updateModel.setZxhfModel(zxhfModel);
    			updateModel.setSfgk(sfgk);
    			updateModel.setZxid(zxid);
    			submitHfxx(updateModel);
            }
			
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException("批量导入回复失败！");
		}finally{
			if (book != null) {
				try {
					book.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
		}
	}

	@Override
	public List<ZxwtModel> getBatchDataList(String[] bkdms) {
		return dao.getBatchDataList(bkdms);
	}
	
	@Override
	public File getBatchZxwtFile(String[] bkdms, String zgh) {
		List<ZxwtModel> batchDataList = null;
		if (null == bkdms || bkdms.length == 0) {
			batchDataList = new ArrayList<ZxwtModel>();
		}else{
			// 查询数据
			batchDataList = dao.getBatchDataList(bkdms);
		}
		
		// 生成excel文件
		// 导出文件存放 的临时目录
		File tempDir = new File(WebContext.getServletContext().getRealPath(MessageUtil.getText(TEMP_PATH_KEY)));

		if (!tempDir.exists()) {
			tempDir.mkdir();
		}
		// 创建导出文件
		File file = new File(tempDir.getPath() + "/zxwt-dc-" + String.valueOf(System.currentTimeMillis()) + ".xlsx");
		file.setWritable(true);
		Workbook wb = null;
		CellStyle headCellStyle = null;
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(file);
			wb = new SXSSFWorkbook(100);
			//设置头单元格显示格式
			if (headCellStyle == null) {
				CellStyle lockStyle = wb.createCellStyle();
				CellStyle createCellStyle = lockStyle;
				headCellStyle = createCellStyle;
				
				Font headCellFont = wb.createFont();
				headCellFont.setBold(true);
				headCellFont.setFontHeightInPoints((short) 10);
				headCellFont.setColor(IndexedColors.WHITE.index);
				headCellStyle.setFont(headCellFont);
				headCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				headCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				headCellStyle.setAlignment(HorizontalAlignment.CENTER);
			}
			Sheet sheet = wb.createSheet("sheet-1");
			
			Row headRow = sheet.createRow(0);
			SXSSFCell sxssfCell1 = (SXSSFCell) headRow.createCell(0, CellType.STRING);
			sxssfCell1.setCellStyle(headCellStyle);
			sxssfCell1.setCellValue("咨询编号(请勿修改！)");
			SXSSFCell sxssfCell2 = (SXSSFCell) headRow.createCell(1, CellType.STRING);
			sxssfCell2.setCellStyle(headCellStyle);
			sxssfCell2.setCellValue("板块");
			SXSSFCell sxssfCell3 = (SXSSFCell) headRow.createCell(2, CellType.STRING);
			sxssfCell3.setCellStyle(headCellStyle);
			sxssfCell3.setCellValue("咨询人");
			SXSSFCell sxssfCell4 = (SXSSFCell) headRow.createCell(3, CellType.STRING);
			sxssfCell4.setCellStyle(headCellStyle);
			sxssfCell4.setCellValue("咨询时间");
			SXSSFCell sxssfCell5 = (SXSSFCell) headRow.createCell(4, CellType.STRING);
			sxssfCell5.setCellStyle(headCellStyle);
			sxssfCell5.setCellValue("咨询内容");
			SXSSFCell sxssfCell6 = (SXSSFCell) headRow.createCell(5, CellType.STRING);
			sxssfCell6.setCellStyle(headCellStyle);
			sxssfCell6.setCellValue("公开(必填)");
			SXSSFCell sxssfCell7 = (SXSSFCell) headRow.createCell(6, CellType.STRING);
			sxssfCell7.setCellStyle(headCellStyle);
			sxssfCell7.setCellValue("回复(必填)");
			
			sheet.setColumnWidth(0, 35 * 256);
			sheet.setColumnWidth(1, 20 * 256);
			sheet.setColumnWidth(3, 20 * 256);
			sheet.setColumnWidth(4, 60 * 256);
			sheet.setColumnWidth(6, 60 * 256);
			
			CellStyle lockStyle = wb.createCellStyle();
			lockStyle.setLocked(true);
			lockStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			lockStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			
			int rowNum = 1;
			for (ZxwtModel model : batchDataList) {
				Row createRow = sheet.createRow(rowNum);
				Cell zxidCell = createRow.createCell(0, CellType.STRING);
				zxidCell.setCellStyle(lockStyle);
				zxidCell.setCellValue(model.getZxid());
				createRow.createCell(1, CellType.STRING).setCellValue(model.getkzdkModel().getBkmc());
				createRow.createCell(2, CellType.STRING).setCellValue(model.getZxrModel().getXm());
				createRow.createCell(3, CellType.STRING).setCellValue(model.getZxsj());
				createRow.createCell(4, CellType.STRING).setCellValue(model.getZxnr());
				
				DataValidationHelper helper = sheet.getDataValidationHelper();
				CellRangeAddressList addressList = new CellRangeAddressList(rowNum, rowNum, 5, 5);  
				String[] isOpenOrNot = {"是","否"};
				DataValidationConstraint constraint = helper.createExplicitListConstraint(isOpenOrNot);
				DataValidation dataValidation = helper.createValidation(constraint, addressList);  
				if(dataValidation instanceof XSSFDataValidation) {  
		            dataValidation.setSuppressDropDownArrow(true);  
		            dataValidation.setShowErrorBox(true);  
		        }else {  
		            dataValidation.setSuppressDropDownArrow(false);  
		        }
				sheet.addValidationData(dataValidation);
				
				createRow.createCell(5, CellType.STRING).setCellValue(isOpenOrNot[0]);
				createRow.createCell(6, CellType.STRING);
				rowNum++;
			}
			
			wb.write(stream);
		} catch (Exception ex) {
			log.error("", ex);
			file = null;
		}finally{
			try {
				IOUtils.closeQuietly(stream);
			} catch (Exception ex) {
				log.error("close workbook failed", ex);
			}
			if(wb != null) {
				try {
					wb.close();
				} catch (Exception e) {
					log.error("close workbook failed", e);
				}
			}
		}
		return file;
	}

}
