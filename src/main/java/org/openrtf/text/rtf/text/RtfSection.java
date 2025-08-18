/*
 * $Id: RtfSection.java 3373 2008-05-12 16:21:24Z xlv $
 *
 * Copyright 2001, 2002, 2003, 2004 by Mark Hall
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

package org.openrtf.text.rtf.text;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.openpdf.text.Chunk;
import org.openpdf.text.DocumentException;
import org.openpdf.text.Element;
import org.openpdf.text.Section;
import org.openrtf.text.rtf.RtfBasicElement;
import org.openrtf.text.rtf.RtfElement;
import org.openrtf.text.rtf.document.RtfDocument;
import org.openrtf.text.rtf.field.RtfTOCEntry;


/**
 * The RtfSection wraps a Section element.
 * INTERNAL CLASS
 *
 * @version $Id: RtfSection.java 3373 2008-05-12 16:21:24Z xlv $
 * @author Mark Hall (Mark.Hall@mail.room3b.eu)
 * @author Thomas Bickel (tmb99@inode.at)
 */
public class RtfSection extends RtfElement {

    /**
     * The title paragraph of this RtfSection
     */
    protected RtfParagraph title = null;
    /**
     * The sub-items of this RtfSection
     */
    protected final ArrayList<RtfBasicElement> items = new ArrayList<>();

    /**
     * Constructs a RtfSection for a given Section. If the autogenerateTOCEntries
     * property of the RtfDocument is set and the title is not empty then a TOC entry
     * is generated for the title.
     *
     * @param doc The RtfDocument this RtfSection belongs to
     * @param section The Section this RtfSection is based on
     */
    public RtfSection(RtfDocument doc, Section section) {
        super(doc);
        try {
            if(section.getTitle() != null) {
                this.title = (RtfParagraph) doc.getMapper().mapElement(section.getTitle())[0];
            }
            if(document.getAutogenerateTOCEntries()) {
                StringBuilder titleText = new StringBuilder();
                for (Element element : section.getTitle()) {
                    if(element.type() == Element.CHUNK) {
                        titleText.append(((Chunk) element).getContent());
                    }
                }
                if(titleText.toString().trim().length() > 0) {
                    RtfTOCEntry tocEntry = new RtfTOCEntry(titleText.toString());
                    tocEntry.setRtfDocument(this.document);
                    this.items.add(tocEntry);
                }
            }
            for (Element element : section) {
                RtfBasicElement[] rtfElements = doc.getMapper().mapElement(element);
                for (RtfBasicElement rbe : rtfElements) {
                    if (rbe != null) {
                        items.add(rbe);
                    }
                }
            }

            updateIndentation(section.getIndentationLeft(), section.getIndentationRight(), section.getIndentation());
        } catch(DocumentException de) {
            de.printStackTrace();
        }
    }

    /**
     * Write this RtfSection and its contents
     */
    public void writeContent(OutputStream result) throws IOException
    {
        result.write(RtfParagraph.PARAGRAPH);
        if(this.title != null) {
            this.title.writeContent(result);
        }
        for (RtfBasicElement rbe : items) {
            rbe.writeContent(result);
        }
    }


    /**
     * Sets whether this RtfSection is in a table. Sets the correct inTable setting for all
     * child elements.
     *
     * @param inTable <code>True</code> if this RtfSection is in a table, <code>false</code> otherwise
     */
    public void setInTable(boolean inTable) {
        super.setInTable(inTable);
        for (RtfBasicElement rbe : this.items) {
            rbe.setInTable(inTable);
        }
    }

    /**
     * Sets whether this RtfSection is in a header. Sets the correct inTable setting for all
     * child elements.
     *
     * @param inHeader <code>True</code> if this RtfSection is in a header, <code>false</code> otherwise
     */
    public void setInHeader(boolean inHeader) {
        super.setInHeader(inHeader);
        for (RtfBasicElement rbe : this.items) {
            rbe.setInHeader(inHeader);
        }
    }

    /**
     * Updates the left, right and content indentation of all RtfParagraph and RtfSection
     * elements that this RtfSection contains.
     *
     * @param indentLeft The left indentation to add.
     * @param indentRight The right indentation to add.
     * @param indentContent The content indentation to add.
     */
    private void updateIndentation(float indentLeft, float indentRight, float indentContent) {
        if(this.title != null) {
            this.title.setIndentLeft((int) (this.title.getIndentLeft() + indentLeft * RtfElement.TWIPS_FACTOR));
            this.title.setIndentRight((int) (this.title.getIndentRight() + indentRight * RtfElement.TWIPS_FACTOR));
        }
        for (RtfBasicElement rtfElement : this.items) {
            if(rtfElement instanceof RtfSection) {
                ((RtfSection) rtfElement).updateIndentation(indentLeft + indentContent, indentRight, 0);
            } else if(rtfElement instanceof RtfParagraph) {
                ((RtfParagraph) rtfElement).setIndentLeft((int) (((RtfParagraph) rtfElement).getIndentLeft() + (indentLeft + indentContent) * RtfElement.TWIPS_FACTOR));
                ((RtfParagraph) rtfElement).setIndentRight((int) (((RtfParagraph) rtfElement).getIndentRight() + indentRight * RtfElement.TWIPS_FACTOR));
            }
        }
    }
}
