package translation.youdao;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import translation.util.HttpUtil;
import translation.util.MD5Util;

public class YouDaoUtil {
	
	
	private static YouDaoUtil youDaoUtil;
	public final Map<String, String> reqData;
	private YouDaoUtil() {
		reqData = new HashMap<>();
		reqData.put("from", "英文");
		reqData.put("to", "中文");
		reqData.put("appKey", YouDaoConfig.APP_KEY);
	}
	
	public static YouDaoUtil getIns() {
		if(youDaoUtil == null) {
			synchronized (YouDaoUtil.class) {
				if(youDaoUtil == null) {
					youDaoUtil = new YouDaoUtil();
				}
			}
		}
		return youDaoUtil;
	}
	

	public  String get(String str) {
		reqData.put("q", str);
		sign();
		StringBuilder result = new StringBuilder();
		try {
			JSONObject parsedData = JSONObject.parseObject(HttpUtil.get(YouDaoConfig.REQ_URL, reqData));
			result.setLength(0);
			result.append("原    文:  ").append(str).append("\n");
			result.append("翻译结果:  ");
			if(str.trim().equals("") || parsedData.getJSONArray("translation") == null) {
				return result.toString();
			}
			parsedData.getJSONArray("translation").forEach((a) -> {
				result.append(a).append("\n");
			});
			
			JSONArray web = parsedData.getJSONArray("web");
			if(web != null) {
				result.append("  词    义:\n");
				for (int v = 0; v < web.size(); v++) {
					Object key = web.getJSONObject(v).get("key");
					Object value = web.getJSONObject(v).get("value");
					result.append("    ").append(key).append(": ").append(value).append("\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.append(e).append(e.getMessage());
		}
		return result.toString();
	}


	private  void sign() {
		String salt = String.valueOf(System.currentTimeMillis());
		reqData.put("salt", salt);
		String signStr = YouDaoConfig.APP_KEY + reqData.get("q") + salt + YouDaoConfig.APP_SECRET;
		reqData.put("sign", MD5Util.sign(signStr).toUpperCase());
	}

	
}
