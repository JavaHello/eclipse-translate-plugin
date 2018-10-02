package translation.util;

import java.util.regex.Pattern;

public class TextFilter {

	public final static Pattern COMPILE = java.util.regex.Pattern.compile("([A-Z]{1}[a-z]+)");
	public final static Pattern COMPILE_CHAR = java.util.regex.Pattern.compile("[-_\\n\\r\\t\\*]");
	public final static Pattern COMPILE_SPAN = java.util.regex.Pattern.compile("[\\ ]+");
	
	public static String filter(String q) {
		q = COMPILE.matcher(q).replaceAll(" $1");
		q = COMPILE_CHAR.matcher(q).replaceAll(" ");
		q = COMPILE_SPAN.matcher(q).replaceAll(" ");
		return q;
	}
}
