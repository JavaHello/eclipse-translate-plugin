package translation.youdao;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import translation.util.HttpUtil;
import translation.util.MD5Util;

public class YouDaoUtil {
	
	private static final Map<String, String> ERROR_CODE = new HashMap<String, String>();
	
	static {
		ERROR_CODE.put("101", "缺少必填的参数");
		ERROR_CODE.put("102", "不支持的语言类型");
		ERROR_CODE.put("103", "翻译文本过长");
		ERROR_CODE.put("104", "不支持的API类型");
		ERROR_CODE.put("105", "不支持的签名类型");
		ERROR_CODE.put("106", "不支持的响应类型");
		ERROR_CODE.put("107", "不支持的传输加密类型");
		ERROR_CODE.put("108", "appKey无效");
		ERROR_CODE.put("109", "batchLog格式不正确");
		ERROR_CODE.put("110", "无相关服务的有效实例");
		ERROR_CODE.put("111", "开发者账号无效");
		ERROR_CODE.put("113", "q不能为空");
		ERROR_CODE.put("201", "解密失败，可能为DES,BASE64,URLDecode的错误");
//		ERROR_CODE.put("202", "签名检验失败");
		ERROR_CODE.put("202", "appKey 或 appSecret 配置错误");
		ERROR_CODE.put("203", "访问IP地址不在可访问IP列表");
		ERROR_CODE.put("205", "请求的接口与应用的平台类型不一致");
		ERROR_CODE.put("301", "辞典查询失败");
		ERROR_CODE.put("302", "翻译查询失败");
		ERROR_CODE.put("303", "服务端的其它异常");
		ERROR_CODE.put("401", "账户已经欠费");
		ERROR_CODE.put("411", "访问频率受限,请稍后访问");
		ERROR_CODE.put("412", "长请求过于频繁，请稍后访问");
	}
	
	
	private static YouDaoUtil youDaoUtil;
	public final Map<String, String> reqData;
	private YouDaoUtil() {
		reqData = new HashMap<>();
		reqData.put("from", "英文");
		reqData.put("to", "中文");
		reqData.put("appKey", YouDaoConfig.getAppKey());
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
			String errorCode = parsedData.getString("errorCode");
			if(errorCode != null && !errorCode.isEmpty() && !"0".equals(errorCode)) {
				result.setLength(0);
				result.append("【有道翻译】")
					.append(ERROR_CODE.get(errorCode))
					.append("\n");
				if("108".equals(errorCode) || "202".equals(errorCode)) {
					result.append("AppKey:").append(YouDaoConfig.getAppKey()).append("\n");
					result.append("AppSecret:").append(YouDaoConfig.getAppSecret()).append("\n");
				}
				return result.toString();
			}
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
		String signStr = YouDaoConfig.getAppKey() + reqData.get("q") + salt + YouDaoConfig.getAppSecret();
		reqData.put("sign", MD5Util.sign(signStr).toUpperCase());
	}

	
}
