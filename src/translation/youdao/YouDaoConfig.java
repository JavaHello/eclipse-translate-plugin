package translation.youdao;

import translation.util.PreferenceStoreUtil;

public class YouDaoConfig {

	public final static String REQ_URL = "http://openapi.youdao.com/api?";

	public final static String APP_KEY_NAME = "youdao.app.key";
	public final static String APP_SECRET_NAME = "youdao.app.secret";

	public static String getAppSecret() {
		return PreferenceStoreUtil.get(APP_SECRET_NAME);
	}

	public static String getAppKey() {
		return PreferenceStoreUtil.get(APP_KEY_NAME);
	}
}
