package translation;

import java.util.concurrent.ConcurrentHashMap;

import translation.baidu.BaiduTranslation;
import translation.youdao.YouDaoTranslation;

public class TranslationFactory {

	private static final ConcurrentHashMap<Translation.API, Translation> map = new ConcurrentHashMap<Translation.API, Translation>();

	public static Translation newIns(Translation.API t) {
		return getOrNew(t);
	}

	private static Translation getOrNew(Translation.API t) {
		Translation translation = map.get(t);
		if (translation != null) {
			return translation;
		} else {
			if (map.containsKey(t)) {
				return map.get(t);
			}

			switch (t) {
			case Y:
				return map.put(t, new YouDaoTranslation());
			case B:
				return map.put(t, new BaiduTranslation());
			case G:
				throw new RuntimeException("暂时不支持 google 翻译");
			default:
				throw new RuntimeException("没有选择第三方翻译API");
			}
		}
	}
}
