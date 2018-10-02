package translation;

import translation.util.TextFilter;

public abstract class AbstractTranslation implements Translation {

	
	public abstract String doFy(String q);
	
	@Override
	public String fy(String str) {
		return doFy(TextFilter.filter(str));
	}

	
}
