package translation;

import translation.youdao.YouDaoTranslation;

public class TranslationFactory {


	
	public static Translation newIns(String t) {
		if("y".equalsIgnoreCase(t)) {
			return new YouDaoTranslation();
		}
		throw new RuntimeException("没有选择第三方翻译API");
	}
	
	public static Translation newIns(Translation.API t) {
		if("y".equalsIgnoreCase(t.name())) {
			return new YouDaoTranslation();
		}
		throw new RuntimeException("没有选择第三方翻译API");
	}
}
