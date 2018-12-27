package net.bobs.own.db.rundml.resources;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "net.bobs.own.db.rundml.resources.messages";
	
	public static  String	RunDML_DBError_Title;
	
	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
