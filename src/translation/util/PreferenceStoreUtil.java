package translation.util;

import java.io.IOException;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class PreferenceStoreUtil {
	private static final String PLUGIN_ID = "eclipse.translation";
	static ScopedPreferenceStore preferenceStore;

	static {
		preferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, PLUGIN_ID);
	}

	public static void save() {
		try {
			preferenceStore.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void add(String key, String value) {
		preferenceStore.setValue(key, value);
	}

	public static String get(String key) {
		return preferenceStore.getString(key);
	}

}
