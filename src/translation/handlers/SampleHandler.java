package translation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.handlers.HandlerUtil;

import translation.ConsoleUtil;
import translation.Translation;
import translation.TranslationFactory;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getSelectionService().getSelection();
		if(selection instanceof TextSelection && !selection.isEmpty()) {
			TextSelection textSelection = (TextSelection)selection;
			String q = textSelection.getText();
			ConsoleUtil.getIns().show(TranslationFactory.newIns(Translation.API.Y).fy(q) + "\n");
		}
		return null;
	}
}
