package net.bobs.own.db.rundml.exception;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import net.bobs.own.db.rundml.resources.Messages;

/**
 * Convience class to display an error message from the database (RunDMLException) to the user.
 * @author Robert Anderson
 *
 */
public class ExceptionMessageDialogUtility {

	/**
	 * Asynchronously displays the cause of a RunDMLException.  This method is intended 
	 * for use in non-ui threads.
	 * @param rdex
	 */
	public static void openExceptionMessageDialog(RunDMLException rdex) {
	
		Display.getDefault().asyncExec(new Runnable() {
			@Override 
			public void run() {
			MessageDialog errMsg = new MessageDialog(new Shell(),
					Messages.RunDML_DBError_Title, null,
					rdex.getCause().getMessage(),
					MessageDialog.ERROR,new String[] { "Ok" },0);
			errMsg.open();		
			}
		});

	}
	
	public static void openExceptionMessageDialog(String title, RunDMLException rdex) {
		
		MessageDialog errMsg = new MessageDialog(Display.getCurrent().getActiveShell(),
				title, null,
				rdex.getCause().getMessage(),
				MessageDialog.ERROR,new String[] { "Ok" },0);
		errMsg.open();
	}
}
