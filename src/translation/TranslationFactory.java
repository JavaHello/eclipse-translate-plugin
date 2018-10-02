package translation;

import translation.baidu.BaiduTranslation;
import translation.youdao.YouDaoTranslation;

public class TranslationFactory {


	
	
	public static Translation newIns(Translation.API t) {
		switch (t) {
		case Y:
			return new YouDaoTranslation();
		case B:
			return new BaiduTranslation();
		case G:
			throw new RuntimeException("暂时不支持 google 翻译");
		default:
			throw new RuntimeException("没有选择第三方翻译API");
		}
	}
}
