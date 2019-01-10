package translation;

import java.util.HashMap;
import java.util.Map;

import translation.baidu.BaiduTranslation;
import translation.youdao.YouDaoTranslation;

public class TranslationFactory {

	private static final Map<Translation.API, Translation> T_MAP = new HashMap<Translation.API, Translation>();

	public static Translation newIns(Translation.API t) {
		return getOrNew(t);
	}

	private static Translation getOrNew(Translation.API t) {
		Translation translation = T_MAP.get(t);
		if (translation != null) {
			return translation;
		} else {
			synchronized (t) {
				if (T_MAP.containsKey(t)) {
					return T_MAP.get(t);
				}
				
				switch (t) {
				case Y:
					translation =  new YouDaoTranslation();
					break;
				case B:
					translation = new BaiduTranslation();
					break;
				case G:
					throw new RuntimeException("暂时不支持 google 翻译");
				default:
					throw new RuntimeException("没有选择第三方翻译API");
				}
				T_MAP.put(t, translation);
			}
		}
		return translation;
	}
}
