package translation.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtil {

	public static String get(String strUrl, Map<String, String> param) throws Exception {
		StringBuilder result = new StringBuilder();
		InputStream is = null;
		try {
			URL url = new URL(strUrl + mapToReqStr(param));
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(6000);
			is = connection.getInputStream();
			int len = -1;
			byte[] red = new byte[1024];
			while ((len = is.read(red)) != -1) {
				result.append(new String(red, 0, len, "UTF-8"));
			}
			return result.toString();
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static String mapToReqStr(Map<String, String> map) throws UnsupportedEncodingException {
		if (map == null || map.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> e : map.entrySet()) {
			sb.append(e.getKey()).append("=").append(URLEncoder.encode(e.getValue(), "UTF-8")).append("&");
		}
		return sb.toString().substring(0, sb.lastIndexOf("&"));
	}
}
