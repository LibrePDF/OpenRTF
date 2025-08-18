/*
 * Copyright 2015 Michael Joyce ubermichael@gmail.com
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
import org.openpdf.text.DocWriter;
import org.openrtf.text.rtf.RtfElement;
import org.openrtf.text.rtf.RtfExtendedElement;

/**
 * @author Michael Joyce (ubermichael@gmail.com)
 */
public class RtfFootnoteSetting extends RtfElement implements RtfExtendedElement {

    /** Constant for the page height */
    private static final byte[] FOOTNOTE_BOTTOM = DocWriter.getISOBytes("\\ftnbj");

    /** Constant for the page width */
    private static final byte[] FOOTNOTE_START = DocWriter.getISOBytes("\\ftnstart");

    /** Constant for the left margin */
    private static final byte[] FOOTNOTE_CONTINUOUS = DocWriter.getISOBytes("\\ftnrscont");

    /** Constant for the right margin */
    private static final byte[] FOOTNOTE_ARABIC = DocWriter.getISOBytes("\\ftnnar");

    private static final byte[] FOOTNOTE_SEP = DocWriter.getISOBytes("\\*\\ftnsep");

    private static final byte[] FOOTNOTE_ANCHOR = DocWriter.getISOBytes("\\chftnsep");

    /** The page width to use */
    private final int footnoteStart = 1;

    /**
     * Constructs a new RtfPageSetting object belonging to a RtfDocument.
     *
     * @param doc The RtfDocument this RtfPageSetting belongs to
     */
    public RtfFootnoteSetting(RtfDocument doc) {
        super(doc);
    }

    /** unused */
    @Override
    public void writeContent(OutputStream out) throws IOException {}

    /** Writes the page size / page margin definition */
    @Override
    public void writeDefinition(OutputStream result) throws IOException {
        result.write(FOOTNOTE_BOTTOM);
        result.write(FOOTNOTE_START);
        result.write(intToByteArray(footnoteStart));
        result.write(FOOTNOTE_CONTINUOUS);
        result.write(FOOTNOTE_ARABIC);
        result.write(OPEN_GROUP);
        result.write(FOOTNOTE_SEP);
        result.write(FOOTNOTE_ANCHOR);
        result.write(CLOSE_GROUP);
        this.document.outputDebugLinebreak(result);
    }
}
