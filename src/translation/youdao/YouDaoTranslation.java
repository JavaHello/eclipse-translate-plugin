package translation.youdao;

import translation.AbstractTranslation;

public class YouDaoTranslation extends AbstractTranslation {


	@Override
	public String doFy(String q) {
		return YouDaoUtil.getIns().get(q);
	}
}
