package translation.baidu;

import translation.AbstractTranslation;

public class BaiduTranslation extends AbstractTranslation {

	@Override
	public String doFy(String q) {
		return BaiduUtil.getIns().get(q);
	}


}
