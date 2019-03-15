/* 
 ******************************************************************************
 * H2DbLib provides a simple connection pool for establishing connections to 
 * an embedded H2 database.
 * This file is part of H2DBLib.
 *  
 * Copyright (c) 2016 Robert W. Anderson.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Robert W. Anderson - initial API and implementation and/or initial documentation
 *    
 * EXCEPT AS EXPRESSLY SET FORTH IN THIS AGREEMENT, THE PROGRAM IS PROVIDED ON 
 * AN "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, EITHER 
 * EXPRESS OR IMPLIED INCLUDING, WITHOUT LIMITATION, ANY WARRANTIES OR 
 * CONDITIONS OF TITLE, NON-INFRINGEMENT, MERCHANTABILITY OR FITNESS FOR A 
 * PARTICULAR PURPOSE.   
 ******************************************************************************
 */
package com.bobman159.rundml.jdbc.resources;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	
	private static final String BUNDLE_NAME = "com.bobman159.rundml.jdbc.resources.messages";
	
	public static	String	initPoolMessage;
	public static	String	propCreateConfig;
	public static 	String	dataSourceAdded;
	
	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

}
