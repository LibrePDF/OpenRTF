/*
 * $Id: RtfListTable.java 3580 2008-08-06 15:52:00Z howard_s $
 *
 * Copyright 2008 Howard Shank (hgshank@yahoo.com)
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

package org.openrtf.text.rtf.list;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.openpdf.text.DocWriter;
import org.openrtf.text.rtf.RtfElement;
import org.openrtf.text.rtf.RtfExtendedElement;
import org.openrtf.text.rtf.document.RtfDocument;

/**
 * The RtfListTable manages all RtfList objects and list override table in one RtfDocument.
 *
 * @version $Id: RtfListTable.java 3580 2008-08-06 15:52:00Z howard_s $
 * @author Mark Hall (Mark.Hall@mail.room3b.eu)
 * @author Howard Shank (hgshank@yahoo.com)
 */
public class RtfListTable extends RtfElement implements RtfExtendedElement {
    /** Constant for the list table */
    private static final byte[] LIST_TABLE = DocWriter.getISOBytes("\\*\\listtable");

    /** Constant for the list override table */
    private static final byte[] LIST_OVERRIDE_TABLE = DocWriter.getISOBytes("\\*\\listoverridetable");

    /** Constant for the list override */
    private static final byte[] LIST_OVERRIDE = DocWriter.getISOBytes("\\listoverride");

    /** Constant for the list override count */
    private static final byte[] LIST_OVERRIDE_COUNT = DocWriter.getISOBytes("\\listoverridecount");

    /** The RtfList lists managed by this RtfListTable */
    private final List<RtfList> lists = new ArrayList<>();

    /** The RtfPictureList lists managed by this RtfListTable */
    private final List<RtfPictureList> picturelists = new ArrayList<>();

    /**
     * Constructs a RtfListTable for a RtfDocument
     *
     * @param doc The RtfDocument this RtfListTable belongs to
     */
    public RtfListTable(RtfDocument doc) {
        super(doc);
    }

    /** unused */
    public void writeContent(OutputStream out) throws IOException {}

    /** Writes the list and list override tables. */
    public void writeDefinition(OutputStream result) throws IOException {
        result.write(OPEN_GROUP);
        result.write(LIST_TABLE);
        this.document.outputDebugLinebreak(result);

        for (RtfPictureList l : picturelists) {
            //        	l.setID(document.getRandomInt());
            l.writeDefinition(result);
            this.document.outputDebugLinebreak(result);
        }

        for (RtfList l : lists) {
            l.setID(document.getRandomInt());
            l.writeDefinition(result);
            this.document.outputDebugLinebreak(result);
        }
        result.write(CLOSE_GROUP);
        this.document.outputDebugLinebreak(result);

        result.write(OPEN_GROUP);
        result.write(LIST_OVERRIDE_TABLE);
        this.document.outputDebugLinebreak(result);

        // list override index values are 1-based, not 0.
        // valid list override index values \ls are 1 to 2000.
        // if there are more then 2000 lists, the result is undefined.
        for (RtfList list : lists) {
            result.write(OPEN_GROUP);
            result.write(LIST_OVERRIDE);
            result.write(RtfList.LIST_ID);
            result.write(intToByteArray(list.getID()));
            result.write(LIST_OVERRIDE_COUNT);
            result.write(intToByteArray(0)); // is this correct? Spec says valid values are 1 or 9.
            result.write(RtfList.LIST_NUMBER);
            result.write(intToByteArray(list.getListNumber()));
            result.write(CLOSE_GROUP);
            this.document.outputDebugLinebreak(result);
        }
        result.write(CLOSE_GROUP);
        this.document.outputDebugLinebreak(result);
    }

    /**
     * Gets the id of the specified RtfList. If the RtfList is not yet in the list of RtfList, then
     * it is added.
     *
     * @param list The RtfList for which to get the id.
     * @return The id of the RtfList.
     */
    public int getListNumber(RtfList list) {
        if (lists.contains(list)) {
            return lists.indexOf(list);
        } else {
            lists.add(list);
            return lists.size();
        }
    }

    /**
     * Remove a RtfList from the list of RtfList
     *
     * @param list The RtfList to remove.
     */
    public void freeListNumber(RtfList list) {
        int i = lists.indexOf(list);
        if (i >= 0) {
            lists.remove(i);
        }
    }
}
