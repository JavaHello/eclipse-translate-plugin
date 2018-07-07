package translation.youdao;

import translation.Translation;

public class YouDaoTranslation implements Translation {

	@Override
	public String fy(String str) {
		return YouDaoUtil.get(str);
	}
}
