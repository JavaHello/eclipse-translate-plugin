package translation.ui;

import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.eclipse.ui.IWorkbenchWindow;

import translation.util.PreferenceStoreUtil;
import translation.youdao.YouDaoConfig;

public class ConfigDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	IWorkbenchWindow window;

	private JTextField jTextFieldYdK = new JTextField();

	private JTextField jTextFieldYdS = new JTextField();
	private JTextArea jTextArea = new JTextArea();
	private JButton jButton = new JButton();

	public ConfigDialog() {
		super();
		initialize();
	}

	private void initialize() {

		try {
			this.getContentPane().setLayout(null);
			this.add(jTextFieldYdK);
			this.add(jTextFieldYdS);
			this.add(jButton);
			this.add(jTextArea);

			jTextFieldYdK.setBounds(31, 10, 200, 24);
			jTextFieldYdK.setText(PreferenceStoreUtil.get(YouDaoConfig.APP_KEY_NAME));
			jTextFieldYdK.setToolTipText("app key");
			
			jTextFieldYdS.setBounds(31, 42, 200, 24);
			jTextFieldYdS.setText(PreferenceStoreUtil.get(YouDaoConfig.APP_SECRET_NAME));
			jTextFieldYdS.setToolTipText("app secret");

			jButton.setText("确认");
			jButton.setBounds(200, 100, 60, 32);
			jButton.addMouseListener(new MouseListenerAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String appKey = jTextFieldYdK.getText();
					String appSecret = jTextFieldYdS.getText();
					if (appKey != null && !appKey.trim().isEmpty() && appSecret != null
							&& !appSecret.trim().isEmpty()) {
						PreferenceStoreUtil.add(YouDaoConfig.APP_KEY_NAME, appKey.trim());
						PreferenceStoreUtil.add(YouDaoConfig.APP_SECRET_NAME, appSecret.trim());
						ConfigDialog.this.dispose();
						PreferenceStoreUtil.save();
						
					}
					jTextArea.setText("请配置 appKey 和 appSecret");
				}
			});
			
			jTextArea.setEditable(false);
			jTextArea.setBounds(31,90, 120, 28);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setTitle("配置翻译参数");
		this.setSize(400, 166);
		this.setResizable(false);

	}
	
	public void errorClean() {
		jTextArea.setText("");
	}

}