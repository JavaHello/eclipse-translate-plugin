package translation.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import translation.ui.ConfigDialog;

public class SampleAction implements IWorkbenchWindowActionDelegate {
//    private IWorkbenchWindow window;
	ConfigDialog configDialog;

	/**
	 * The constructor.
	 */
	public SampleAction() {
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
//          MessageDialog.openInformation(
//                window.getShell(),
//                "Hello Plug-in",
//                "Hello, Eclipse world");
		if (configDialog == null) {
			configDialog = new ConfigDialog();

		}
		configDialog.setSize(320, 200);
		configDialog.setVisible(true);
		configDialog.setLocationRelativeTo(null);
		configDialog.requestFocus();
		configDialog.errorClean();

	}

	/**
	 * Selection in the workbench has been changed. We can change the state of the
	 * 'real' action here if we want, but this can only happen after the delegate
	 * has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell for
	 * the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
//          this.window = window;
		
	}
}