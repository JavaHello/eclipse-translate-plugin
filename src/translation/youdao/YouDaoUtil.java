package translation.youdao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import translation.util.HttpUtil;

public class YouDaoUtil {
	public final static Map<String, String> REQ_DATA = new HashMap<>();

	public final static Pattern COMPILE = java.util.regex.Pattern.compile("([A-Z]{1}[a-z]+)");
	public final static Pattern COMPILE_CHAR = java.util.regex.Pattern.compile("[-_\\n\\r\\t\\*]");
	public final static Pattern COMPILE_SPAN = java.util.regex.Pattern.compile("[\\ ]+");
	static {
		REQ_DATA.put("from", "英文");
		REQ_DATA.put("to", "中文");
		REQ_DATA.put("appKey", YouDaoConfig.APP_KEY);
	}

	public static String get(String str) {
		str = COMPILE.matcher(str).replaceAll(" $1");
		str = COMPILE_CHAR.matcher(str).replaceAll(" ");
		str = COMPILE_SPAN.matcher(str).replaceAll(" ");
		
		REQ_DATA.put("q", str);
		sign();
		StringBuilder result = new StringBuilder();
		try {
			JSONObject parsedData = JSONObject.parseObject(HttpUtil.get(YouDaoConfig.REQ_URL, REQ_DATA));
			result.setLength(0);
			result.append("原文:  ").append(str).append("\n");
			result.append("翻译结果:  ");
			if(str.trim().equals("") || parsedData.getJSONArray("translation") == null) {
				return result.toString();
			}
			parsedData.getJSONArray("translation").forEach((a) -> {
				result.append(a).append("\n");
			});
			
			JSONArray web = parsedData.getJSONArray("web");
			if(web != null) {
				result.append("  词义:\n");
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



	private static void sign() {
		String salt = UUID.randomUUID().toString().replaceAll("-", "");
		REQ_DATA.put("salt", salt);
		String signStr = YouDaoConfig.APP_KEY + REQ_DATA.get("q") + salt + YouDaoConfig.APP_SECRET;
		try {
			MessageDigest instance = java.security.MessageDigest.getInstance("MD5");
			byte[] digest = instance.digest(signStr.getBytes("UTF-8"));
			REQ_DATA.put("sign", bytesToHexString(digest).toUpperCase());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 数组转换成十六进制字符串
	 * 
	 * @param byte[]
	 * @return HexString
	 */
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}
}
