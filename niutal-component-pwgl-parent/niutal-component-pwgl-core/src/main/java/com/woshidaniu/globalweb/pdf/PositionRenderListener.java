/**********************************************************************
 * <pre>
 * FILE : PositionRenderListener.java
 * CLASS : PositionRenderListener
 *
 * AUTHOR : caoxu-yiyang@qq.com
 *
 * FUNCTION : TODO
 *
 *
 *======================================================================
 * CHANGE HISTORY LOG
 *----------------------------------------------------------------------
 * MOD. NO.|   DATE   |   NAME  | REASON  | CHANGE REQ.
 *----------------------------------------------------------------------
 * 		    |2016年11月9日|caoxu-yiyang@qq.com| Created |
 * DESCRIPTION:
 * </pre>
 ***********************************************************************/

package com.woshidaniu.globalweb.pdf;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.awt.geom.Rectangle2D.Float;
import com.itextpdf.text.pdf.parser.GraphicsState;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.woshidaniu.globalweb.PWUtils;

/**
 * pdf渲染监听,当找到渲染的文本时，得到文本的坐标x,y,w,h
 * @user : caoxu-yiyang@qq.com
 * @date : 2016年11月9日
 */
public class PositionRenderListener implements RenderListener{

	private List<String> findText;

	private boolean flag;

	private ReplaceRegion r;

	private String defaultName = "default";

	///出现无法取到值的情况，默认为12
	private float defaultH;
	//可能出现无法完全覆盖的情况，提供修正的参数，默认为2
	private float fixHeight;

	private float fixWidth;

	private float fixX;

	private String fixText;

	private String aliasName;

	private int fixNum = 0;

	private int pn;

	public PositionRenderListener(List<String> findText, float defaultH, float fixHeight) {
		this.findText = findText;
		this.defaultH = defaultH;
		this.fixHeight = fixHeight;
	}

	public PositionRenderListener(List<String> findText) {
		this.findText = findText;
		defaultH = 12;
		fixHeight = 2;
	}

	public void setPn(int pn){
		this.pn = pn;
	}

	@Override
	public void beginTextBlock() {

	}

	@Override
	public void endTextBlock() {

	}

	@Override
	public void renderImage(ImageRenderInfo imageInfo) {
	}

	private Map<String, List<ReplaceRegion>> result = new HashMap<String, List<ReplaceRegion>>();

	@Override
	public void renderText(TextRenderInfo textInfo) {
		String text = textInfo.getText();
		if(null != text && !"".equals(text.trim())){
			//this.fixPlace(textInfo, text);
			this.fixPlace2(textInfo, text);
		}
	}

	public void fixPlace(TextRenderInfo textInfo, String text){
		boolean bb = false;
		fixText = null;
		char[] arr = text.toCharArray();
		//flag true表示已识别到开始符，后面的判断表示当前字符串存在开始符“【”
		if(null != text){
			for (String keyWord : findText) {
				if(flag && text.indexOf(PWUtils.END_FLAG) > -1){

					for(int i = 0; i < arr.length && keyWord.indexOf(PWUtils.END_FLAG) == i && i < arr.length - 1; i++){
						//结束符
						fixText = text.substring(i + 1, text.length());
						fixWidth = 0 - this.getStringWidth(textInfo, fixText);
						text = text.substring(0, i + 1);
						arr = text.toCharArray();
					}
				}else{
					for(int i = 0; i < arr.length ; i++){
						if(i > 0 && keyWord.indexOf(arr[i]) == 0){
							//起始符
							fixWidth = this.getStringWidth(textInfo, text.substring(0, i + 1));
							text = text.substring(i, text.length());
						}
					}
				}
				if (fixWidth > 0 || (!bb && keyWord.indexOf(text) > -1 && (!flag == (keyWord.indexOf(text) == 0))
						&& (null == r || defaultName.equals(r.getAliasName()) || keyWord.indexOf(aliasName) == 0))){
					bb = true;
					if(keyWord.indexOf(text) == 0){
						r = new ReplaceRegion(defaultName);
						r.setPn(pn);
						aliasName = text;
					}else if(!keyWord.equals(r.getAliasName()) && keyWord.indexOf(text) > 0){
						r.setAliasName(keyWord);
						aliasName += text;
					}else{
						aliasName += text;
					}
					flag = keyWord.indexOf(text) + text.length() == keyWord.length() ? false : true;

					Float bound = textInfo.getBaseline().getBoundingRectange();
					//高度
					if(null == r.getH()){
						r.setH(bound.height == 0 ? defaultH : bound.height);
					}
					//宽度
					if(null == r.getW()){
						r.setW(bound.width + fixWidth);
					}else{
						r.setW(bound.width + fixWidth + r.getW());
					}
					//X坐标
					if(null == r.getX()){
						r.setX(bound.x + fixWidth);
					}
					//Y坐标
					if(null == r.getY()){
						r.setY(bound.y - fixHeight);
					}
					if(!flag){
						if(null == result.get(keyWord)){
							List<ReplaceRegion> l = new ArrayList<ReplaceRegion>();
							l.add(r);
							result.put(keyWord, l);
						}else{
							List<ReplaceRegion> l = result.get(keyWord);
							l.add(r);
							result.put(keyWord, l);
						}
						//System.out.println(r.getAliasName() + "【W】:" + r.getW() + "; 【H】:" + r.getH() + "; 【X】:" + r.getX() + "; 【Y】：" + r.getY());
						r = null;
						aliasName = null;
						if(null != fixText){
							fixWidth = this.getStringWidth(textInfo, fixText);
							this.fixPlace(textInfo, fixText);
						}
						break;
					}
					fixWidth = 0;
					break;
				}
			}
		}
	}

	public void fixPlace2(TextRenderInfo textInfo, String text){
		fixText = null;
		//textInfo.getFont().getFontDictionary().get(PdfName.BASEFONT);
		char[] arr = text.toCharArray();
		//flag true表示已识别到开始符，后面的判断表示当前字符串存在开始符“【”
		if(null != text && (flag || text.indexOf(PWUtils.START_FLAG) > -1)){
			outForeach:
				for(int cn = 0; cn < arr.length; cn++){
					char c = arr[cn];
					for(; fixNum < findText.size(); fixNum++){
						if(findText.get(fixNum).indexOf(c) > -1 && (c == PWUtils.START_FLAG || null == aliasName
								|| findText.get(fixNum).startsWith(new StringBuilder(aliasName).append(String.valueOf(c)).toString()))){
							String keyWord = findText.get(fixNum);

							if(c == PWUtils.START_FLAG){
								//识别为开始符
								r = new ReplaceRegion(defaultName);
								r.setPn(pn);
								aliasName = String.valueOf(c);
								if(cn > 0){
									char[] cArr = new char[cn];
									System.arraycopy(arr, 0, cArr, 0, cn);
									fixX = this.getStringWidth(textInfo, cArr);
								}
							}else{
								aliasName = new StringBuilder(aliasName).append(String.valueOf(c)).toString();
							}

							if(cn == arr.length - 1){
								fixWidth = this.getStringWidth(textInfo, c) - this.getCharacterSpacing(textInfo);
							}else{
								fixWidth = this.getStringWidth(textInfo, c);
							}

							//false 表示完成闭环，即【……】匹配完成
							flag = keyWord.equals(aliasName) ? false : true;

							Float bound = textInfo.getBaseline().getBoundingRectange();
							//高度
							if(null == r.getH()){
								r.setH(bound.height == 0 ? defaultH : bound.height);
							}
							//宽度
							if(null == r.getW()){
								//r.setW(bound.width + fixWidth);
								r.setW(fixWidth);
							}else{
								//r.setW(bound.width + fixWidth + r.getW());
								r.setW(fixWidth + r.getW());
							}
							//X坐标
							if(null == r.getX()){
								r.setX(bound.x + fixX);
							}
							//Y坐标
							if(null == r.getY()){
								r.setY(bound.y - fixHeight);
							}
							if(!flag){
								r.setAliasName(keyWord);
								//识别到结束符
								if(null == result.get(keyWord)){
									List<ReplaceRegion> l = new ArrayList<ReplaceRegion>();
									l.add(r);
									result.put(keyWord, l);
								}else{
									List<ReplaceRegion> l = result.get(keyWord);
									l.add(r);
									result.put(keyWord, l);
								}
								//System.out.println(r.getAliasName() + "【W】:" + r.getW() + "; 【H】:" + r.getH() + "; 【X】:" + r.getX() + "; 【Y】：" + r.getY());
								//初始化
								this.init();
							}
							continue outForeach;
						}
					}
					this.init();
				}
		}
	}

	public void init(){
		fixNum = 0;
		fixX = 0;
		r = null;
		aliasName = null;
		flag = false;
	}

	public Map<String, List<ReplaceRegion>> getResult() {
		//补充没有找到的数据
		for (String key : findText) {
			if(result.get(key) == null){
				result.put(key, null);
			}
		}
		return result;
	}

	/**
	 * Gets the width of a String in text space units
	 * @param string    the string that needs measuring
	 * @return          the width of a String in text space units
	 */
	private float getStringWidth(TextRenderInfo textInfo, String string){
		Field field;
		GraphicsState gs = null;
		try {
			//textInfo.getClass().getDeclaredField("gs");
			field = textInfo.getClass().getDeclaredField("gs");
			field.setAccessible(true);
			gs = (GraphicsState) field.get(textInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		float totalWidth = 0;
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			float w = gs.getFont().getWidth(c) / 1000.0f;
			float wordSpacing = c == 32 ? gs.getWordSpacing() : 0f;
			totalWidth += (w * gs.getFontSize() + gs.getCharacterSpacing() + wordSpacing) * gs.getHorizontalScaling();
		}
		return totalWidth;
	}

	/**
	 * Gets the width of a String in text space units
	 * @param string    the string that needs measuring
	 * @return          the width of a String in text space units
	 */
	private float getStringWidth(TextRenderInfo textInfo, char[] arr){
		Field field;
		GraphicsState gs = null;
		try {
			//textInfo.getClass().getDeclaredField("gs");
			field = textInfo.getClass().getDeclaredField("gs");
			field.setAccessible(true);
			gs = (GraphicsState) field.get(textInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		float totalWidth = 0;
		for (int i = 0; i < arr.length; i++) {
			char c = arr[i];
			float w = gs.getFont().getWidth(c) / 1000.0f;
			float wordSpacing = c == 32 ? gs.getWordSpacing() : 0f;
			totalWidth += (w * gs.getFontSize() + gs.getCharacterSpacing() + wordSpacing) * gs.getHorizontalScaling();
		}
		return totalWidth;
	}

	/**
	 * Gets the width of a String in text space units
	 * @param string    the string that needs measuring
	 * @return          the width of a String in text space units
	 */
	private float getStringWidth(TextRenderInfo textInfo, char c){
		Field field;
		GraphicsState gs = null;
		try {
			//textInfo.getClass().getDeclaredField("gs");
			field = textInfo.getClass().getDeclaredField("gs");
			field.setAccessible(true);
			gs = (GraphicsState) field.get(textInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		float w = gs.getFont().getWidth(c) / 1000.0f;
		float wordSpacing = c == 32 ? gs.getWordSpacing() : 0f;
		return (w * gs.getFontSize() + gs.getCharacterSpacing() + wordSpacing) * gs.getHorizontalScaling();
	}

	/**
	 * Gets the width of a String in text space units
	 * @param string    the string that needs measuring
	 * @return          the width of a String in text space units
	 */
	private float getCharacterSpacing(TextRenderInfo textInfo){
		Field field;
		GraphicsState gs = null;
		try {
			//textInfo.getClass().getDeclaredField("gs");
			field = textInfo.getClass().getDeclaredField("gs");
			field.setAccessible(true);
			gs = (GraphicsState) field.get(textInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gs.getCharacterSpacing();
	}
}
