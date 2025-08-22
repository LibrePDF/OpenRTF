/*
 * $Id: RtfInfoGroup.java 3580 2008-08-06 15:52:00Z howard_s $
 *
 * Copyright 2003, 2004 by Mark Hall
 *
 * The contents of this file are subject to the Mozilla Public License Version 1.1
 * (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the License.
 *
 * The Original Code is 'iText, a free JAVA-PDF library'.
 *
 * The Initial Developer of the Original Code is Bruno Lowagie. Portions created by
 * the Initial Developer are Copyright (C) 1999, 2000, 2001, 2002 by Bruno Lowagie.
 * All Rights Reserved.
 * Co-Developer of the code is Paulo Soares. Portions created by the Co-Developer
 * are Copyright (C) 2000, 2001, 2002 by Paulo Soares. All Rights Reserved.
 *
 * Contributor(s): all the names of the contributors are added in the source code
 * where applicable.
 *
 * Alternatively, the contents of this file may be used under the terms of the
 * LGPL license (the ?GNU LIBRARY GENERAL PUBLIC LICENSE?), in which case the
 * provisions of LGPL are applicable instead of those above.  If you wish to
 * allow use of your version of this file only under the terms of the LGPL
 * License and not to allow others to use your version of this file under
 * the MPL, indicate your decision by deleting the provisions above and
 * replace them with the notice and other provisions required by the LGPL.
 * If you do not delete the provisions above, a recipient may use your version
 * of this file under either the MPL or the GNU LIBRARY GENERAL PUBLIC LICENSE.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the MPL as stated above or under the terms of the GNU
 * Library General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library general Public License for more
 * details.
 *
 * If you didn't download this code from the following link, you should check if
 * you aren't using an obsolete version:
 * https://github.com/LibrePDF/openrtf
 */

package org.openrtf.text.rtf.document;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.openpdf.text.DocWriter;
import org.openrtf.text.rtf.RtfElement;


/**
 * The RtfInfoGroup stores information group elements.
 *
 * @version $Id: RtfInfoGroup.java 3580 2008-08-06 15:52:00Z howard_s $
 * @author Mark Hall (Mark.Hall@mail.room3b.eu)
 * @author Thomas Bickel (tmb99@inode.at)
 * @author Howard Shank (hgshank@yahoo.com)
 */
public class RtfInfoGroup extends RtfElement {
    /**
     * Information group starting tag
     */
    private static final byte[] INFO_GROUP = DocWriter.getISOBytes("\\info");

    /**
     * Constant for the password element.
     * Author: Howard Shank (hgshank@yahoo.com)
     * @since 2.1.1
     */
    private static final byte[] INFO_PASSWORD = DocWriter.getISOBytes("\\*\\password");

    /**
     * The RtfInfoElements that belong to this RtfInfoGroup
     */
    private final List<RtfInfoElement> infoElements = new ArrayList<>();

    /**
     * Constructs a RtfInfoGroup belonging to a RtfDocument
     *
     * @param doc The RtfDocument this RtfInfoGroup belongs to
     */
    public RtfInfoGroup(RtfDocument doc) {
        super(doc);
    }

    /**
     * Adds an RtfInfoElement to the RtfInfoGroup
     *
     * @param infoElement The RtfInfoElement to add
     */
    public void add(RtfInfoElement infoElement) {
        this.infoElements.add(infoElement);
    }

    /**
     * Writes the RTF information group and its elements.
     */
    public void writeContent(OutputStream result) throws IOException
    {
    	result.write(OPEN_GROUP);
		result.write(INFO_GROUP);
        for (RtfInfoElement infoElement : infoElements) {
			infoElement.writeContent(result);
		}

		// handle document protection
    	if(document.getDocumentSettings().isDocumentProtected()) {
	    	result.write(OPEN_GROUP);
			result.write(INFO_PASSWORD);
			result.write(DELIMITER);
			result.write(document.getDocumentSettings().getProtectionHashBytes());
			result.write(CLOSE_GROUP);
    	}

		result.write(CLOSE_GROUP);
		this.document.outputDebugLinebreak(result);
    }

}
