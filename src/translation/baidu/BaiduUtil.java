package translation.baidu;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import translation.util.HttpUtil;
import translation.util.MD5Util;

public class BaiduUtil {
	
	
	private static BaiduUtil youDaoUtil;
	public final Map<String, String> reqData;
	private BaiduUtil() {
		reqData = new HashMap<>();
		reqData.put("from", "英文");
		reqData.put("to", "中文");
		reqData.put("appid", BaiduConfig.APP_KEY);
	}
	
	public static BaiduUtil getIns() {
		if(youDaoUtil == null) {
			synchronized (BaiduUtil.class) {
				if(youDaoUtil == null) {
					youDaoUtil = new BaiduUtil();
				}
			}
		}
		return youDaoUtil;
	}
	

	public  String get(String str) {
		reqData.put("q", str);
		sign();
		StringBuilder result = null;
		try {
			JSONObject parsedData = JSONObject.parseObject(HttpUtil.get(BaiduConfig.REQ_URL, reqData));
			System.out.print(parsedData);
			if(!"52000".equals(parsedData.getString("error_code"))) {
				return "访问百度翻译错误";
			}
			result = new StringBuilder();
			result.setLength(0);
			result.append("原    文:  ").append(str).append("\n");
			result.append("翻译结果:  ");
			if(str.trim().equals("") || parsedData.getJSONArray("trans_result") == null
					|| parsedData.getJSONArray("trans_result").isEmpty()) {
				return result.toString();
			}
//			JSONArray dis = parsedData.getJSONArray("trans_result");
			
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
		String signStr = BaiduConfig.APP_KEY + reqData.get("q") + salt + BaiduConfig.APP_SECRET;
		reqData.put("sign", MD5Util.sign(signStr));
	}

	
}
