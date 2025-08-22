/*
 * $Id: RtfCtrlWordMap.java 3580 2008-08-06 15:52:00Z howard_s $
 *
 * Copyright 2007 by Howard Shank
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
 * the Initial Developer are Copyright (C) 1999-2006 by Bruno Lowagie.
 * All Rights Reserved.
 * Co-Developer of the code is Paulo Soares. Portions created by the Co-Developer
 * are Copyright (C) 2000-2006 by Paulo Soares. All Rights Reserved.
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
package org.openrtf.text.rtf.parser.ctrlwords;

import java.util.HashMap;
import java.util.Map;
import org.openrtf.text.rtf.parser.RtfParser;
import org.openrtf.text.rtf.parser.properties.RtfProperty;

/**
 * <code>RtfCtrlWords</code> handles the creation of the control word wiring. It is a class
 * containing the hash map of the control words (key) and their associated class (value).
 *
 * @author Howard Shank (hgshank@yahoo.com)
 * @since 2.0.8
 */
final class RtfCtrlWordMap {

    // 1810 control words in Spec v1.9. might be a few more for other apps that implement
    // additional control words such as exchange, outlook, etc.
    // 1810/.9(loadfactor) = 2011.111111...
    // set approximate initial size to initial count / load factor.
    // HashMap default size is 16. Load Factor .75
    /** Control Word HashMap mapping object. */
    private final Map<String, RtfCtrlWordHandler> ctrlWords = new HashMap<>(2012, .9f);

    /**
     * Get the HashMap object containing the control words. Initializes the instance if this is the
     * first instantiation of RtfCtrlWords class.
     *
     * @since 2.0.8
     */
    public RtfCtrlWordHandler getCtrlWordHandler(String ctrlWord) {
        try {
            RtfCtrlWordHandler handler = null;
            if (ctrlWord == "cf") {
                handler = null;
            }
            if (ctrlWords.containsKey(ctrlWord)) {
                // add 1 to known control words
                return ctrlWords.get(ctrlWord);
            } else {
                // add 1 to unknown control words
                return ctrlWords.get("unknown");
            }
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Constructor
     *
     * @param rtfParser The parser object.
     * @since 2.0.8
     */
    RtfCtrlWordMap(RtfParser rtfParser) {
        /*
         * Parameters:
         * RtfParser rtfParser
         * String ctrlWord
         * int defaultParameterValue
         * boolean passDefaultParameterValue
         * RtfCtrlWordType ctrlWordType
         * String prefix
         * String suffix
         * String specialHandler =
         * 	If TOGGLE then the property name as String
         * 	If FLAG then the property name as String
         * 	If VALUE then the property name as String
         * 	If SYMBOL then the character to use for substitution as String
         * 	If DESTINATION|DESTINATION_EX then the RtfDestination class name as String
         */
        // starwriter
        ctrlWords.put(
                "aftnnrlc",
                new RtfCtrlWordHandler(rtfParser, "aftnnrlc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgdsctbl",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "pgdsctbl",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "pgdsc", new RtfCtrlWordHandler(rtfParser, "pgdsc", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pgdscuse",
                new RtfCtrlWordHandler(rtfParser, "pgdscuse", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pgdscnxt",
                new RtfCtrlWordHandler(rtfParser, "pgdscnxt", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pgdscno",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "pgdsctbl",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));

        // office
        ctrlWords.put("'", new RtfCtrlWordHandler(rtfParser, "'", 0, false, RtfCtrlWordType.SYMBOL, "\\", "", "'"));
        ctrlWords.put("*", new RtfCtrlWordHandler(rtfParser, "*", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", "*"));
        ctrlWords.put("-", new RtfCtrlWordHandler(rtfParser, "-", 0, false, RtfCtrlWordType.SYMBOL, "\\", "", "-"));
        ctrlWords.put(":", new RtfCtrlWordHandler(rtfParser, ":", 0, false, RtfCtrlWordType.SYMBOL, "\\", "", ":"));
        ctrlWords.put(
                "ApplyBrkRules",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "ApplyBrkRules",
                        0,
                        false,
                        RtfCtrlWordType.FLAG,
                        "\\",
                        " ",
                        null)); // "ApplyBrkRules",
        ctrlWords.put("\\", new RtfCtrlWordHandler(rtfParser, "\\", 0, false, RtfCtrlWordType.SYMBOL, "\\", "", "\\"));
        ctrlWords.put("_", new RtfCtrlWordHandler(rtfParser, "_", 0, false, RtfCtrlWordType.SYMBOL, "\\", "", "_"));
        ctrlWords.put(
                "ab",
                new RtfCtrlWordHandler(rtfParser, "ab", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null)); // "ab",
        ctrlWords.put(
                "absh",
                new RtfCtrlWordHandler(rtfParser, "absh", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null)); // "absh",
        ctrlWords.put(
                "abslock",
                new RtfCtrlWordHandler(
                        rtfParser, "abslock", 0, false, RtfCtrlWordType.FLAG, "", " ", null)); // "abslock",
        ctrlWords.put(
                "absnoovrlp",
                new RtfCtrlWordHandler(
                        rtfParser, "absnoovrlp", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null)); // "absnoovrlp",
        ctrlWords.put(
                "absw",
                new RtfCtrlWordHandler(rtfParser, "absw", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null)); // "absw",
        ctrlWords.put(
                "acaps",
                new RtfCtrlWordHandler(
                        rtfParser, "acaps", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null)); // "acaps",
        ctrlWords.put(
                "acccircle",
                new RtfCtrlWordHandler(
                        rtfParser, "acccircle", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null)); // "acccircle",
        ctrlWords.put(
                "acccomma",
                new RtfCtrlWordHandler(rtfParser, "acccomma", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "accdot",
                new RtfCtrlWordHandler(rtfParser, "accdot", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "accnone",
                new RtfCtrlWordHandler(rtfParser, "accnone", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "accunderdot",
                new RtfCtrlWordHandler(rtfParser, "accunderdot", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put("acf", new RtfCtrlWordHandler(rtfParser, "acf", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "additive",
                new RtfCtrlWordHandler(rtfParser, "additive", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "adeflang",
                new RtfCtrlWordHandler(rtfParser, "adeflang", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "adjustright",
                new RtfCtrlWordHandler(rtfParser, "adjustright", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("adn", new RtfCtrlWordHandler(rtfParser, "adn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "aenddoc",
                new RtfCtrlWordHandler(rtfParser, "aenddoc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aendnotes",
                new RtfCtrlWordHandler(rtfParser, "aendnotes", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aexpnd", new RtfCtrlWordHandler(rtfParser, "aexpnd", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("af", new RtfCtrlWordHandler(rtfParser, "af", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "afelev", new RtfCtrlWordHandler(rtfParser, "afelev", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "affixed",
                new RtfCtrlWordHandler(rtfParser, "affixed", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("afs", new RtfCtrlWordHandler(rtfParser, "afs", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "aftnbj", new RtfCtrlWordHandler(rtfParser, "aftnbj", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftncn",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "aftncn",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "aftnnalc",
                new RtfCtrlWordHandler(rtfParser, "aftnnalc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnnar",
                new RtfCtrlWordHandler(rtfParser, "aftnnar", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnnauc",
                new RtfCtrlWordHandler(rtfParser, "aftnnauc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnnchi",
                new RtfCtrlWordHandler(rtfParser, "aftnnchi", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnnchosung",
                new RtfCtrlWordHandler(rtfParser, "aftnnchosung", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnncnum",
                new RtfCtrlWordHandler(rtfParser, "aftnncnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnndbar",
                new RtfCtrlWordHandler(rtfParser, "aftnndbar", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnndbnum",
                new RtfCtrlWordHandler(rtfParser, "aftnndbnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnndbnumd",
                new RtfCtrlWordHandler(rtfParser, "aftnndbnumd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnndbnumk",
                new RtfCtrlWordHandler(rtfParser, "aftnndbnumk", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnndbnumt",
                new RtfCtrlWordHandler(rtfParser, "aftnndbnumt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnnganada",
                new RtfCtrlWordHandler(rtfParser, "aftnnganada", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnngbnum",
                new RtfCtrlWordHandler(rtfParser, "aftnngbnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnngbnumd",
                new RtfCtrlWordHandler(rtfParser, "aftnngbnumd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnngbnumk",
                new RtfCtrlWordHandler(rtfParser, "aftnngbnumk", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnngbnuml",
                new RtfCtrlWordHandler(rtfParser, "aftnngbnuml", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnnruc",
                new RtfCtrlWordHandler(rtfParser, "aftnnruc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnnzodiac",
                new RtfCtrlWordHandler(rtfParser, "aftnnzodiac", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnnzodiacd",
                new RtfCtrlWordHandler(rtfParser, "aftnnzodiacd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnnzodiacl",
                new RtfCtrlWordHandler(rtfParser, "aftnnzodiacl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnrestart",
                new RtfCtrlWordHandler(rtfParser, "aftnrestart", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnrstcont",
                new RtfCtrlWordHandler(rtfParser, "aftnrstcont", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aftnsep",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "aftnsep",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "aftnsepc",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "aftnsepc",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "aftnstart",
                new RtfCtrlWordHandler(rtfParser, "aftnstart", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "aftntj", new RtfCtrlWordHandler(rtfParser, "aftntj", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("ai", new RtfCtrlWordHandler(rtfParser, "ai", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "alang", new RtfCtrlWordHandler(rtfParser, "alang", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "allowfieldendsel",
                new RtfCtrlWordHandler(rtfParser, "allowfieldendsel", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "allprot",
                new RtfCtrlWordHandler(rtfParser, "allprot", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "alntblind",
                new RtfCtrlWordHandler(rtfParser, "alntblind", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("alt", new RtfCtrlWordHandler(rtfParser, "alt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "animtext",
                new RtfCtrlWordHandler(rtfParser, "animtext", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "annotation",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "annotation",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "annotprot",
                new RtfCtrlWordHandler(rtfParser, "annotprot", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ansi", new RtfCtrlWordHandler(rtfParser, "ansi", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ansicpg",
                new RtfCtrlWordHandler(rtfParser, "ansicpg", 1252, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "aoutl", new RtfCtrlWordHandler(rtfParser, "aoutl", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "ascaps",
                new RtfCtrlWordHandler(rtfParser, "ascaps", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "ashad", new RtfCtrlWordHandler(rtfParser, "ashad", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "asianbrkrule",
                new RtfCtrlWordHandler(rtfParser, "asianbrkrule", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "aspalpha",
                new RtfCtrlWordHandler(rtfParser, "aspalpha", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "aspnum",
                new RtfCtrlWordHandler(rtfParser, "aspnum", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "astrike",
                new RtfCtrlWordHandler(rtfParser, "astrike", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "atnauthor",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "atnauthor",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "atndate",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "atndate",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "atnicn",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "atnicn",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "atnid",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "atnid",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "atnparent",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "atnparent",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "atnref",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "atnref",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "atntime",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "atntime",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "atrfend",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "atrfend",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "atrfstart",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "atrfstart",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "aul", new RtfCtrlWordHandler(rtfParser, "aul", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "auld", new RtfCtrlWordHandler(rtfParser, "auld", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "auldb", new RtfCtrlWordHandler(rtfParser, "auldb", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "aulnone",
                new RtfCtrlWordHandler(rtfParser, "aulnone", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "aulw", new RtfCtrlWordHandler(rtfParser, "aulw", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put("aup", new RtfCtrlWordHandler(rtfParser, "aup", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "author",
                new RtfCtrlWordHandler(
                        rtfParser, "author", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationInfo"));
        ctrlWords.put(
                "autofmtoverride",
                new RtfCtrlWordHandler(rtfParser, "autofmtoverride", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "b",
                new RtfCtrlWordHandler(
                        rtfParser, "b", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", RtfProperty.CHARACTER_BOLD));
        ctrlWords.put(
                "background",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "background",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "bdbfhdr",
                new RtfCtrlWordHandler(rtfParser, "bdbfhdr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "bdrrlswsix",
                new RtfCtrlWordHandler(rtfParser, "bdrrlswsix", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "bgbdiag",
                new RtfCtrlWordHandler(rtfParser, "bgbdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "bgcross",
                new RtfCtrlWordHandler(rtfParser, "bgcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "bgdcross",
                new RtfCtrlWordHandler(rtfParser, "bgdcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "bgdkbdiag",
                new RtfCtrlWordHandler(rtfParser, "bgdkbdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "bgdkcross",
                new RtfCtrlWordHandler(rtfParser, "bgdkcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "bgdkdcross",
                new RtfCtrlWordHandler(rtfParser, "bgdkdcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "bgdkfdiag",
                new RtfCtrlWordHandler(rtfParser, "bgdkfdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "bgdkhoriz",
                new RtfCtrlWordHandler(rtfParser, "bgdkhoriz", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "bgdkvert",
                new RtfCtrlWordHandler(rtfParser, "bgdkvert", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "bgfdiag",
                new RtfCtrlWordHandler(rtfParser, "bgfdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "bghoriz",
                new RtfCtrlWordHandler(rtfParser, "bghoriz", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "bgvert", new RtfCtrlWordHandler(rtfParser, "bgvert", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("bin", new RtfCtrlWordHandler(rtfParser, "bin", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "binfsxn",
                new RtfCtrlWordHandler(rtfParser, "binfsxn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "binsxn", new RtfCtrlWordHandler(rtfParser, "binsxn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "bkmkcolf",
                new RtfCtrlWordHandler(rtfParser, "bkmkcolf", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "bkmkcoll",
                new RtfCtrlWordHandler(rtfParser, "bkmkcoll", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "bkmkend",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "bkmkend",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "bkmkpub",
                new RtfCtrlWordHandler(rtfParser, "bkmkpub", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "bkmkstart",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "bkmkstart",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "bliptag",
                new RtfCtrlWordHandler(rtfParser, "bliptag", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "blipuid",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "blipuid",
                        0,
                        false,
                        RtfCtrlWordType.VALUE,
                        "\\*\\",
                        " ",
                        "RtfDestinationShppict")); // "RtfDestinationBlipuid"));
        ctrlWords.put(
                "blipupi",
                new RtfCtrlWordHandler(
                        rtfParser, "blipupi", 0, true, RtfCtrlWordType.VALUE, "\\", " ", "RtfDestinationShppict"));
        ctrlWords.put(
                "blue", new RtfCtrlWordHandler(rtfParser, "blue", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "bookfold",
                new RtfCtrlWordHandler(rtfParser, "bookfold", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "bookfoldrev",
                new RtfCtrlWordHandler(rtfParser, "bookfoldrev", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "bookfoldsheets",
                new RtfCtrlWordHandler(rtfParser, "bookfoldsheets", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("box", new RtfCtrlWordHandler(rtfParser, "box", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrart",
                new RtfCtrlWordHandler(rtfParser, "brdrart", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "brdrb", new RtfCtrlWordHandler(rtfParser, "brdrb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrbar",
                new RtfCtrlWordHandler(rtfParser, "brdrbar", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrbtw",
                new RtfCtrlWordHandler(rtfParser, "brdrbtw", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrcf", new RtfCtrlWordHandler(rtfParser, "brdrcf", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "brdrdash",
                new RtfCtrlWordHandler(rtfParser, "brdrdash", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrdashd",
                new RtfCtrlWordHandler(rtfParser, "brdrdashd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrdashdd",
                new RtfCtrlWordHandler(rtfParser, "brdrdashdd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrdashdotstr",
                new RtfCtrlWordHandler(rtfParser, "brdrdashdotstr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrdashsm",
                new RtfCtrlWordHandler(rtfParser, "brdrdashsm", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrdb", new RtfCtrlWordHandler(rtfParser, "brdrdb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrdot",
                new RtfCtrlWordHandler(rtfParser, "brdrdot", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdremboss",
                new RtfCtrlWordHandler(rtfParser, "brdremboss", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrengrave",
                new RtfCtrlWordHandler(rtfParser, "brdrengrave", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrframe",
                new RtfCtrlWordHandler(rtfParser, "brdrframe", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrhair",
                new RtfCtrlWordHandler(rtfParser, "brdrhair", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrinset",
                new RtfCtrlWordHandler(rtfParser, "brdrinset", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrl", new RtfCtrlWordHandler(rtfParser, "brdrl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrnil",
                new RtfCtrlWordHandler(rtfParser, "brdrnil", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrnone",
                new RtfCtrlWordHandler(rtfParser, "brdrnone", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "brdroutset",
                new RtfCtrlWordHandler(rtfParser, "brdroutset", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrr", new RtfCtrlWordHandler(rtfParser, "brdrr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrs", new RtfCtrlWordHandler(rtfParser, "brdrs", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrsh", new RtfCtrlWordHandler(rtfParser, "brdrsh", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrt", new RtfCtrlWordHandler(rtfParser, "brdrt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrtbl",
                new RtfCtrlWordHandler(rtfParser, "brdrtbl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrth", new RtfCtrlWordHandler(rtfParser, "brdrth", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrthtnlg",
                new RtfCtrlWordHandler(rtfParser, "brdrthtnlg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrthtnmg",
                new RtfCtrlWordHandler(rtfParser, "brdrthtnmg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrthtnsg",
                new RtfCtrlWordHandler(rtfParser, "brdrthtnsg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrtnthlg",
                new RtfCtrlWordHandler(rtfParser, "brdrtnthlg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrtnthmg",
                new RtfCtrlWordHandler(rtfParser, "brdrtnthmg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrtnthsg",
                new RtfCtrlWordHandler(rtfParser, "brdrtnthsg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrtnthtnlg",
                new RtfCtrlWordHandler(rtfParser, "brdrtnthtnlg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrtnthtnmg",
                new RtfCtrlWordHandler(rtfParser, "brdrtnthtnmg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrtnthtnsg",
                new RtfCtrlWordHandler(rtfParser, "brdrtnthtnsg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrtriple",
                new RtfCtrlWordHandler(rtfParser, "brdrtriple", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrw", new RtfCtrlWordHandler(rtfParser, "brdrw", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "brdrwavy",
                new RtfCtrlWordHandler(rtfParser, "brdrwavy", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brdrwavydb",
                new RtfCtrlWordHandler(rtfParser, "brdrwavydb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brkfrm", new RtfCtrlWordHandler(rtfParser, "brkfrm", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "brsp", new RtfCtrlWordHandler(rtfParser, "brsp", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "bullet",
                new RtfCtrlWordHandler(rtfParser, "bullet", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", "\0x149"));
        ctrlWords.put(
                "buptim",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "buptim",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put("bxe", new RtfCtrlWordHandler(rtfParser, "bxe", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "caccentfive",
                new RtfCtrlWordHandler(rtfParser, "caccentfive", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "caccentfour",
                new RtfCtrlWordHandler(rtfParser, "caccentfour", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "caccentone",
                new RtfCtrlWordHandler(rtfParser, "caccentone", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "caccentsix",
                new RtfCtrlWordHandler(rtfParser, "caccentsix", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "caccentthree",
                new RtfCtrlWordHandler(rtfParser, "caccentthree", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "caccenttwo",
                new RtfCtrlWordHandler(rtfParser, "caccenttwo", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "cachedcolbal",
                new RtfCtrlWordHandler(rtfParser, "cachedcolbal", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "caps", new RtfCtrlWordHandler(rtfParser, "caps", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "category",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "category",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put("cb", new RtfCtrlWordHandler(rtfParser, "cb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "cbackgroundone",
                new RtfCtrlWordHandler(rtfParser, "cbackgroundone", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "cbackgroundtwo",
                new RtfCtrlWordHandler(rtfParser, "cbackgroundtwo", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "cbpat", new RtfCtrlWordHandler(rtfParser, "cbpat", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "cchs", new RtfCtrlWordHandler(rtfParser, "cchs", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "cell", new RtfCtrlWordHandler(rtfParser, "cell", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "cellx", new RtfCtrlWordHandler(rtfParser, "cellx", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("cf", new RtfCtrlWordHandler(rtfParser, "cf", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "cfollowedhyperlink",
                new RtfCtrlWordHandler(
                        rtfParser, "cfollowedhyperlink", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "cfpat", new RtfCtrlWordHandler(rtfParser, "cfpat", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "cgrid", new RtfCtrlWordHandler(rtfParser, "cgrid", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "charrsid",
                new RtfCtrlWordHandler(rtfParser, "charrsid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "charscalex",
                new RtfCtrlWordHandler(rtfParser, "charscalex", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "chatn", new RtfCtrlWordHandler(rtfParser, "chatn", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "chbgbdiag",
                new RtfCtrlWordHandler(rtfParser, "chbgbdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "chbgcross",
                new RtfCtrlWordHandler(rtfParser, "chbgcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "chbgdcross",
                new RtfCtrlWordHandler(rtfParser, "chbgdcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "chbgdkbdiag",
                new RtfCtrlWordHandler(rtfParser, "chbgdkbdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "chbgdkcross",
                new RtfCtrlWordHandler(rtfParser, "chbgdkcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "chbgdkdcross",
                new RtfCtrlWordHandler(rtfParser, "chbgdkdcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "chbgdkfdiag",
                new RtfCtrlWordHandler(rtfParser, "chbgdkfdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "chbgdkhoriz",
                new RtfCtrlWordHandler(rtfParser, "chbgdkhoriz", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "chbgdkvert",
                new RtfCtrlWordHandler(rtfParser, "chbgdkvert", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "chbgfdiag",
                new RtfCtrlWordHandler(rtfParser, "chbgfdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "chbghoriz",
                new RtfCtrlWordHandler(rtfParser, "chbghoriz", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "chbgvert",
                new RtfCtrlWordHandler(rtfParser, "chbgvert", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "chbrdr", new RtfCtrlWordHandler(rtfParser, "chbrdr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "chcbpat",
                new RtfCtrlWordHandler(rtfParser, "chcbpat", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "chcfpat",
                new RtfCtrlWordHandler(rtfParser, "chcfpat", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "chdate",
                new RtfCtrlWordHandler(rtfParser, "chdate", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "chdpa", new RtfCtrlWordHandler(rtfParser, "chdpa", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "chdpl", new RtfCtrlWordHandler(rtfParser, "chdpl", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "chftn", new RtfCtrlWordHandler(rtfParser, "chftn", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "chftnsep",
                new RtfCtrlWordHandler(rtfParser, "chftnsep", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "chftnsepc",
                new RtfCtrlWordHandler(rtfParser, "chftnsepc", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "chpgn", new RtfCtrlWordHandler(rtfParser, "chpgn", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "chshdng",
                new RtfCtrlWordHandler(rtfParser, "chshdng", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "chtime",
                new RtfCtrlWordHandler(rtfParser, "chtime", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "chyperlink",
                new RtfCtrlWordHandler(rtfParser, "chyperlink", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clFitText",
                new RtfCtrlWordHandler(rtfParser, "clFitText", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clNoWrap",
                new RtfCtrlWordHandler(rtfParser, "clNoWrap", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clbgbdiag",
                new RtfCtrlWordHandler(rtfParser, "clbgbdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clbgcross",
                new RtfCtrlWordHandler(rtfParser, "clbgcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clbgdcross",
                new RtfCtrlWordHandler(rtfParser, "clbgdcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clbgdkbdiag",
                new RtfCtrlWordHandler(rtfParser, "clbgdkbdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clbgdkcross",
                new RtfCtrlWordHandler(rtfParser, "clbgdkcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clbgdkdcross",
                new RtfCtrlWordHandler(rtfParser, "clbgdkdcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clbgdkfdiag",
                new RtfCtrlWordHandler(rtfParser, "clbgdkfdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clbgdkhor",
                new RtfCtrlWordHandler(rtfParser, "clbgdkhor", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clbgdkvert",
                new RtfCtrlWordHandler(rtfParser, "clbgdkvert", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clbgfdiag",
                new RtfCtrlWordHandler(rtfParser, "clbgfdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clbghoriz",
                new RtfCtrlWordHandler(rtfParser, "clbghoriz", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clbgvert",
                new RtfCtrlWordHandler(rtfParser, "clbgvert", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clbrdrb",
                new RtfCtrlWordHandler(rtfParser, "clbrdrb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clbrdrl",
                new RtfCtrlWordHandler(rtfParser, "clbrdrl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clbrdrr",
                new RtfCtrlWordHandler(rtfParser, "clbrdrr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clbrdrt",
                new RtfCtrlWordHandler(rtfParser, "clbrdrt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clcbpat",
                new RtfCtrlWordHandler(rtfParser, "clcbpat", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clcbpatraw",
                new RtfCtrlWordHandler(rtfParser, "clcbpatraw", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clcfpat",
                new RtfCtrlWordHandler(rtfParser, "clcfpat", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clcfpatraw",
                new RtfCtrlWordHandler(rtfParser, "clcfpatraw", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "cldel", new RtfCtrlWordHandler(rtfParser, "cldel", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "cldelauth",
                new RtfCtrlWordHandler(rtfParser, "cldelauth", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "cldeldttm",
                new RtfCtrlWordHandler(rtfParser, "cldeldttm", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "cldgll", new RtfCtrlWordHandler(rtfParser, "cldgll", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "cldglu", new RtfCtrlWordHandler(rtfParser, "cldglu", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clftsWidth",
                new RtfCtrlWordHandler(rtfParser, "clftsWidth", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clhidemark",
                new RtfCtrlWordHandler(rtfParser, "clhidemark", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clins", new RtfCtrlWordHandler(rtfParser, "clins", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clinsauth",
                new RtfCtrlWordHandler(rtfParser, "clinsauth", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clinsdttm",
                new RtfCtrlWordHandler(rtfParser, "clinsdttm", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clmgf", new RtfCtrlWordHandler(rtfParser, "clmgf", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clmrg", new RtfCtrlWordHandler(rtfParser, "clmrg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clmrgd", new RtfCtrlWordHandler(rtfParser, "clmrgd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clmrgdauth",
                new RtfCtrlWordHandler(rtfParser, "clmrgdauth", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clmrgddttm",
                new RtfCtrlWordHandler(rtfParser, "clmrgddttm", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clmrgdr",
                new RtfCtrlWordHandler(rtfParser, "clmrgdr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clpadb", new RtfCtrlWordHandler(rtfParser, "clpadb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clpadfb",
                new RtfCtrlWordHandler(rtfParser, "clpadfb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clpadfl",
                new RtfCtrlWordHandler(rtfParser, "clpadfl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clpadfr",
                new RtfCtrlWordHandler(rtfParser, "clpadfr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clpadft",
                new RtfCtrlWordHandler(rtfParser, "clpadft", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clpadl", new RtfCtrlWordHandler(rtfParser, "clpadl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clpadr", new RtfCtrlWordHandler(rtfParser, "clpadr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clpadt", new RtfCtrlWordHandler(rtfParser, "clpadt", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clshdng",
                new RtfCtrlWordHandler(rtfParser, "clshdng", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clshdngraw",
                new RtfCtrlWordHandler(rtfParser, "clshdngraw", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "clshdrawnil",
                new RtfCtrlWordHandler(rtfParser, "clshdrawnil", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clsplit",
                new RtfCtrlWordHandler(rtfParser, "clsplit", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clsplitr",
                new RtfCtrlWordHandler(rtfParser, "clsplitr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "cltxbtlr",
                new RtfCtrlWordHandler(rtfParser, "cltxbtlr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "cltxlrtb",
                new RtfCtrlWordHandler(rtfParser, "cltxlrtb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "cltxlrtbv",
                new RtfCtrlWordHandler(rtfParser, "cltxlrtbv", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "cltxtbrl",
                new RtfCtrlWordHandler(rtfParser, "cltxtbrl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "cltxtbrlv",
                new RtfCtrlWordHandler(rtfParser, "cltxtbrlv", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clvertalb",
                new RtfCtrlWordHandler(rtfParser, "clvertalb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clvertalc",
                new RtfCtrlWordHandler(rtfParser, "clvertalc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clvertalt",
                new RtfCtrlWordHandler(rtfParser, "clvertalt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clvmgf", new RtfCtrlWordHandler(rtfParser, "clvmgf", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clvmrg", new RtfCtrlWordHandler(rtfParser, "clvmrg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "clwWidth",
                new RtfCtrlWordHandler(rtfParser, "clwWidth", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "cmaindarkone",
                new RtfCtrlWordHandler(rtfParser, "cmaindarkone", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "cmaindarktwo",
                new RtfCtrlWordHandler(rtfParser, "cmaindarktwo", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "cmainlightone",
                new RtfCtrlWordHandler(rtfParser, "cmainlightone", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "cmainlighttwo",
                new RtfCtrlWordHandler(rtfParser, "cmainlighttwo", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "collapsed",
                new RtfCtrlWordHandler(rtfParser, "collapsed", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "colno", new RtfCtrlWordHandler(rtfParser, "colno", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "colorschememapping",
                new RtfCtrlWordHandler(
                        rtfParser, "colorschememapping", 0, false, RtfCtrlWordType.DESTINATION_EX, "\\*\\", " ", null));
        ctrlWords.put(
                "colortbl",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "colortbl",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationColorTable"));
        ctrlWords.put(
                "cols", new RtfCtrlWordHandler(rtfParser, "cols", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "colsr", new RtfCtrlWordHandler(rtfParser, "colsr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "colsx", new RtfCtrlWordHandler(rtfParser, "colsx", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "column",
                new RtfCtrlWordHandler(rtfParser, "column", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "colw", new RtfCtrlWordHandler(rtfParser, "colw", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "comment",
                new RtfCtrlWordHandler(
                        rtfParser, "comment", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationInfo"));
        ctrlWords.put(
                "company",
                new RtfCtrlWordHandler(
                        rtfParser, "company", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationInfo"));
        ctrlWords.put(
                "contextualspace",
                new RtfCtrlWordHandler(rtfParser, "contextualspace", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("cpg", new RtfCtrlWordHandler(rtfParser, "cpg", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "crauth", new RtfCtrlWordHandler(rtfParser, "crauth", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "crdate", new RtfCtrlWordHandler(rtfParser, "crdate", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "creatim",
                new RtfCtrlWordHandler(
                        rtfParser, "creatim", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationNull"));
        ctrlWords.put(
                "cs", new RtfCtrlWordHandler(rtfParser, "cs", 0, true, RtfCtrlWordType.VALUE, "\\*\\", " ", null));
        ctrlWords.put(
                "cshade", new RtfCtrlWordHandler(rtfParser, "cshade", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ctextone",
                new RtfCtrlWordHandler(rtfParser, "ctextone", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ctexttwo",
                new RtfCtrlWordHandler(rtfParser, "ctexttwo", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ctint", new RtfCtrlWordHandler(rtfParser, "ctint", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ctrl", new RtfCtrlWordHandler(rtfParser, "ctrl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("cts", new RtfCtrlWordHandler(rtfParser, "cts", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "cufi", new RtfCtrlWordHandler(rtfParser, "cufi", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "culi", new RtfCtrlWordHandler(rtfParser, "culi", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "curi", new RtfCtrlWordHandler(rtfParser, "curi", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "cvmme", new RtfCtrlWordHandler(rtfParser, "cvmme", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "datafield",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "datafield",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "datastore",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "datastore",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "date", new RtfCtrlWordHandler(rtfParser, "date", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dbch", new RtfCtrlWordHandler(rtfParser, "dbch", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "defchp",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "defchp",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "deff", new RtfCtrlWordHandler(rtfParser, "deff", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "defformat",
                new RtfCtrlWordHandler(rtfParser, "defformat", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "deflang",
                new RtfCtrlWordHandler(rtfParser, "deflang", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "deflangfe",
                new RtfCtrlWordHandler(rtfParser, "deflangfe", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "defpap",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "defpap",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "defshp", new RtfCtrlWordHandler(rtfParser, "defshp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "deftab", new RtfCtrlWordHandler(rtfParser, "deftab", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "deleted",
                new RtfCtrlWordHandler(rtfParser, "deleted", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "delrsid",
                new RtfCtrlWordHandler(rtfParser, "delrsid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dfrauth",
                new RtfCtrlWordHandler(rtfParser, "dfrauth", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dfrdate",
                new RtfCtrlWordHandler(rtfParser, "dfrdate", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dfrmtxtx",
                new RtfCtrlWordHandler(rtfParser, "dfrmtxtx", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dfrmtxty",
                new RtfCtrlWordHandler(rtfParser, "dfrmtxty", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dfrstart",
                new RtfCtrlWordHandler(rtfParser, "dfrstart", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dfrstop",
                new RtfCtrlWordHandler(rtfParser, "dfrstop", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dfrxst", new RtfCtrlWordHandler(rtfParser, "dfrxst", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dghorigin",
                new RtfCtrlWordHandler(rtfParser, "dghorigin", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dghshow",
                new RtfCtrlWordHandler(rtfParser, "dghshow", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dghspace",
                new RtfCtrlWordHandler(rtfParser, "dghspace", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dgmargin",
                new RtfCtrlWordHandler(rtfParser, "dgmargin", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dgsnap", new RtfCtrlWordHandler(rtfParser, "dgsnap", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dgvorigin",
                new RtfCtrlWordHandler(rtfParser, "dgvorigin", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dgvshow",
                new RtfCtrlWordHandler(rtfParser, "dgvshow", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dgvspace",
                new RtfCtrlWordHandler(rtfParser, "dgvspace", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dibitmap",
                new RtfCtrlWordHandler(rtfParser, "dibitmap", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("dn", new RtfCtrlWordHandler(rtfParser, "dn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dntblnsbdb",
                new RtfCtrlWordHandler(rtfParser, "dntblnsbdb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "do",
                new RtfCtrlWordHandler(
                        rtfParser, "do", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "dobxcolumn",
                new RtfCtrlWordHandler(rtfParser, "dobxcolumn", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dobxmargin",
                new RtfCtrlWordHandler(rtfParser, "dobxmargin", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dobxpage",
                new RtfCtrlWordHandler(rtfParser, "dobxpage", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dobymargin",
                new RtfCtrlWordHandler(rtfParser, "dobymargin", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dobypage",
                new RtfCtrlWordHandler(rtfParser, "dobypage", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dobypara",
                new RtfCtrlWordHandler(rtfParser, "dobypara", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "doccomm",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "doccomm",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "doctemp",
                new RtfCtrlWordHandler(rtfParser, "doctemp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "doctype",
                new RtfCtrlWordHandler(rtfParser, "doctype", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "docvar",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "docvar",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "dodhgt", new RtfCtrlWordHandler(rtfParser, "dodhgt", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dolock", new RtfCtrlWordHandler(rtfParser, "dolock", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "donotembedlingdata",
                new RtfCtrlWordHandler(
                        rtfParser, "donotembedlingdata", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "donotembedsysfont",
                new RtfCtrlWordHandler(
                        rtfParser, "donotembedsysfont", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "donotshowcomments",
                new RtfCtrlWordHandler(
                        rtfParser, "donotshowcomments", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "donotshowinsdel",
                new RtfCtrlWordHandler(rtfParser, "donotshowinsdel", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "donotshowmarkup",
                new RtfCtrlWordHandler(rtfParser, "donotshowmarkup", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "donotshowprops",
                new RtfCtrlWordHandler(rtfParser, "donotshowprops", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpaendhol",
                new RtfCtrlWordHandler(rtfParser, "dpaendhol", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpaendl",
                new RtfCtrlWordHandler(rtfParser, "dpaendl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpaendsol",
                new RtfCtrlWordHandler(rtfParser, "dpaendsol", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpaendw",
                new RtfCtrlWordHandler(rtfParser, "dpaendw", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dparc", new RtfCtrlWordHandler(rtfParser, "dparc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dparcflipx",
                new RtfCtrlWordHandler(rtfParser, "dparcflipx", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dparcflipy",
                new RtfCtrlWordHandler(rtfParser, "dparcflipy", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpastarthol",
                new RtfCtrlWordHandler(rtfParser, "dpastarthol", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpastartl",
                new RtfCtrlWordHandler(rtfParser, "dpastartl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpastartsol",
                new RtfCtrlWordHandler(rtfParser, "dpastartsol", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpastartw",
                new RtfCtrlWordHandler(rtfParser, "dpastartw", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpcallout",
                new RtfCtrlWordHandler(rtfParser, "dpcallout", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpcoa", new RtfCtrlWordHandler(rtfParser, "dpcoa", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpcoaccent",
                new RtfCtrlWordHandler(rtfParser, "dpcoaccent", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpcobestfit",
                new RtfCtrlWordHandler(rtfParser, "dpcobestfit", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpcoborder",
                new RtfCtrlWordHandler(rtfParser, "dpcoborder", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpcodabs",
                new RtfCtrlWordHandler(rtfParser, "dpcodabs", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpcodbottom",
                new RtfCtrlWordHandler(rtfParser, "dpcodbottom", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpcodcenter",
                new RtfCtrlWordHandler(rtfParser, "dpcodcenter", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpcodescent",
                new RtfCtrlWordHandler(rtfParser, "dpcodescent", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpcodtop",
                new RtfCtrlWordHandler(rtfParser, "dpcodtop", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpcolength",
                new RtfCtrlWordHandler(rtfParser, "dpcolength", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpcominusx",
                new RtfCtrlWordHandler(rtfParser, "dpcominusx", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpcominusy",
                new RtfCtrlWordHandler(rtfParser, "dpcominusy", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpcooffset",
                new RtfCtrlWordHandler(rtfParser, "dpcooffset", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpcosmarta",
                new RtfCtrlWordHandler(rtfParser, "dpcosmarta", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpcotdouble",
                new RtfCtrlWordHandler(rtfParser, "dpcotdouble", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpcotright",
                new RtfCtrlWordHandler(rtfParser, "dpcotright", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpcotsingle",
                new RtfCtrlWordHandler(rtfParser, "dpcotsingle", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpcottriple",
                new RtfCtrlWordHandler(rtfParser, "dpcottriple", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpcount",
                new RtfCtrlWordHandler(rtfParser, "dpcount", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpellipse",
                new RtfCtrlWordHandler(rtfParser, "dpellipse", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpendgroup",
                new RtfCtrlWordHandler(rtfParser, "dpendgroup", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpfillbgcb",
                new RtfCtrlWordHandler(rtfParser, "dpfillbgcb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpfillbgcg",
                new RtfCtrlWordHandler(rtfParser, "dpfillbgcg", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpfillbgcr",
                new RtfCtrlWordHandler(rtfParser, "dpfillbgcr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpfillbggray",
                new RtfCtrlWordHandler(rtfParser, "dpfillbggray", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpfillbgpal",
                new RtfCtrlWordHandler(rtfParser, "dpfillbgpal", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpfillfgcb",
                new RtfCtrlWordHandler(rtfParser, "dpfillfgcb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpfillfgcg",
                new RtfCtrlWordHandler(rtfParser, "dpfillfgcg", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpfillfgcr",
                new RtfCtrlWordHandler(rtfParser, "dpfillfgcr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpfillfggray",
                new RtfCtrlWordHandler(rtfParser, "dpfillfggray", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpfillfgpal",
                new RtfCtrlWordHandler(rtfParser, "dpfillfgpal", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpfillpat",
                new RtfCtrlWordHandler(rtfParser, "dpfillpat", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpgroup",
                new RtfCtrlWordHandler(rtfParser, "dpgroup", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpline", new RtfCtrlWordHandler(rtfParser, "dpline", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dplinecob",
                new RtfCtrlWordHandler(rtfParser, "dplinecob", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dplinecog",
                new RtfCtrlWordHandler(rtfParser, "dplinecog", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dplinecor",
                new RtfCtrlWordHandler(rtfParser, "dplinecor", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dplinedado",
                new RtfCtrlWordHandler(rtfParser, "dplinedado", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dplinedadodo",
                new RtfCtrlWordHandler(rtfParser, "dplinedadodo", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dplinedash",
                new RtfCtrlWordHandler(rtfParser, "dplinedash", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dplinedot",
                new RtfCtrlWordHandler(rtfParser, "dplinedot", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dplinegray",
                new RtfCtrlWordHandler(rtfParser, "dplinegray", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dplinehollow",
                new RtfCtrlWordHandler(rtfParser, "dplinehollow", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dplinepal",
                new RtfCtrlWordHandler(rtfParser, "dplinepal", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dplinesolid",
                new RtfCtrlWordHandler(rtfParser, "dplinesolid", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dplinew",
                new RtfCtrlWordHandler(rtfParser, "dplinew", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dppolycount",
                new RtfCtrlWordHandler(rtfParser, "dppolycount", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dppolygon",
                new RtfCtrlWordHandler(rtfParser, "dppolygon", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dppolyline",
                new RtfCtrlWordHandler(rtfParser, "dppolyline", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpptx", new RtfCtrlWordHandler(rtfParser, "dpptx", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dppty", new RtfCtrlWordHandler(rtfParser, "dppty", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dprect", new RtfCtrlWordHandler(rtfParser, "dprect", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dproundr",
                new RtfCtrlWordHandler(rtfParser, "dproundr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpshadow",
                new RtfCtrlWordHandler(rtfParser, "dpshadow", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dpshadx",
                new RtfCtrlWordHandler(rtfParser, "dpshadx", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpshady",
                new RtfCtrlWordHandler(rtfParser, "dpshady", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dptxbtlr",
                new RtfCtrlWordHandler(rtfParser, "dptxbtlr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dptxbx", new RtfCtrlWordHandler(rtfParser, "dptxbx", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dptxbxmar",
                new RtfCtrlWordHandler(rtfParser, "dptxbxmar", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dptxbxtext",
                new RtfCtrlWordHandler(
                        rtfParser, "dptxbxtext", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", null));
        ctrlWords.put(
                "dptxlrtb",
                new RtfCtrlWordHandler(rtfParser, "dptxlrtb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dptxlrtbv",
                new RtfCtrlWordHandler(rtfParser, "dptxlrtbv", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dptxtbrl",
                new RtfCtrlWordHandler(rtfParser, "dptxtbrl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "dptxtbrlv",
                new RtfCtrlWordHandler(rtfParser, "dptxtbrlv", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("dpx", new RtfCtrlWordHandler(rtfParser, "dpx", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpxsize",
                new RtfCtrlWordHandler(rtfParser, "dpxsize", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("dpy", new RtfCtrlWordHandler(rtfParser, "dpy", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dpysize",
                new RtfCtrlWordHandler(rtfParser, "dpysize", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dropcapli",
                new RtfCtrlWordHandler(rtfParser, "dropcapli", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dropcapt",
                new RtfCtrlWordHandler(rtfParser, "dropcapt", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("ds", new RtfCtrlWordHandler(rtfParser, "ds", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "dxfrtext",
                new RtfCtrlWordHandler(rtfParser, "dxfrtext", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("dy", new RtfCtrlWordHandler(rtfParser, "dy", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ebcend",
                new RtfCtrlWordHandler(rtfParser, "ebcend", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", null));
        ctrlWords.put(
                "ebcstart",
                new RtfCtrlWordHandler(rtfParser, "ebcstart", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", null));
        ctrlWords.put(
                "edmins", new RtfCtrlWordHandler(rtfParser, "edmins", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "embo", new RtfCtrlWordHandler(rtfParser, "embo", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "emdash",
                new RtfCtrlWordHandler(rtfParser, "emdash", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", "\0x151"));
        ctrlWords.put(
                "emfblip",
                new RtfCtrlWordHandler(rtfParser, "emfblip", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "emspace",
                new RtfCtrlWordHandler(rtfParser, "emspace", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "endash",
                new RtfCtrlWordHandler(rtfParser, "endash", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", "\0x150"));
        ctrlWords.put(
                "enddoc", new RtfCtrlWordHandler(rtfParser, "enddoc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "endnhere",
                new RtfCtrlWordHandler(rtfParser, "endnhere", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "endnotes",
                new RtfCtrlWordHandler(rtfParser, "endnotes", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "enforceprot",
                new RtfCtrlWordHandler(rtfParser, "enforceprot", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "enspace",
                new RtfCtrlWordHandler(rtfParser, "enspace", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "expnd", new RtfCtrlWordHandler(rtfParser, "expnd", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "expndtw",
                new RtfCtrlWordHandler(rtfParser, "expndtw", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "expshrtn",
                new RtfCtrlWordHandler(rtfParser, "expshrtn", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "f",
                new RtfCtrlWordHandler(
                        rtfParser, "f", 0, true, RtfCtrlWordType.VALUE, "\\", " ", RtfProperty.CHARACTER_FONT));
        ctrlWords.put(
                "faauto",
                new RtfCtrlWordHandler(rtfParser, "faauto", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "facenter",
                new RtfCtrlWordHandler(rtfParser, "facenter", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "facingp",
                new RtfCtrlWordHandler(rtfParser, "facingp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "factoidname",
                new RtfCtrlWordHandler(
                        rtfParser, "factoidname", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", null));
        ctrlWords.put(
                "fafixed",
                new RtfCtrlWordHandler(rtfParser, "fafixed", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "fahang", new RtfCtrlWordHandler(rtfParser, "fahang", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "falt",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "falt",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationFontTable")); // "RtfDestinationAlternateFont"));
        ctrlWords.put(
                "faroman",
                new RtfCtrlWordHandler(rtfParser, "faroman", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "favar", new RtfCtrlWordHandler(rtfParser, "favar", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fbias", new RtfCtrlWordHandler(rtfParser, "fbias", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "fbidi", new RtfCtrlWordHandler(rtfParser, "fbidi", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fbimajor",
                new RtfCtrlWordHandler(rtfParser, "fbimajor", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fbiminor",
                new RtfCtrlWordHandler(rtfParser, "fbiminor", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fchars",
                new RtfCtrlWordHandler(
                        rtfParser, "fchars", 0, false, RtfCtrlWordType.DESTINATION_EX, "\\*\\", " ", null));
        ctrlWords.put(
                "fcharset",
                new RtfCtrlWordHandler(rtfParser, "fcharset", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "fdbmajor",
                new RtfCtrlWordHandler(rtfParser, "fdbmajor", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fdbminor",
                new RtfCtrlWordHandler(rtfParser, "fdbminor", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fdecor", new RtfCtrlWordHandler(rtfParser, "fdecor", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "felnbrelev",
                new RtfCtrlWordHandler(rtfParser, "felnbrelev", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("fet", new RtfCtrlWordHandler(rtfParser, "fet", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "fetch", new RtfCtrlWordHandler(rtfParser, "fetch", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ffdefres",
                new RtfCtrlWordHandler(rtfParser, "ffdefres", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ffdeftext",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "ffdeftext",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "ffentrymcr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "ffentrymcr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "ffexitmcr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "ffexitmcr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "ffformat",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "ffformat",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "ffhaslistbox",
                new RtfCtrlWordHandler(rtfParser, "ffhaslistbox", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ffhelptext",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "ffhelptext",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "ffhps", new RtfCtrlWordHandler(rtfParser, "ffhps", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ffl",
                new RtfCtrlWordHandler(
                        rtfParser, "ffl", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "ffmaxlen",
                new RtfCtrlWordHandler(rtfParser, "ffmaxlen", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ffname",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "ffname",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "ffownhelp",
                new RtfCtrlWordHandler(rtfParser, "ffownhelp", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ffownstat",
                new RtfCtrlWordHandler(rtfParser, "ffownstat", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ffprot", new RtfCtrlWordHandler(rtfParser, "ffprot", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ffrecalc",
                new RtfCtrlWordHandler(rtfParser, "ffrecalc", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ffres", new RtfCtrlWordHandler(rtfParser, "ffres", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ffsize", new RtfCtrlWordHandler(rtfParser, "ffsize", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ffstattext",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "ffstattext",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "fftype", new RtfCtrlWordHandler(rtfParser, "fftype", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "fftypetxt",
                new RtfCtrlWordHandler(rtfParser, "fftypetxt", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "fhimajor",
                new RtfCtrlWordHandler(rtfParser, "fhimajor", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fhiminor",
                new RtfCtrlWordHandler(rtfParser, "fhiminor", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("fi", new RtfCtrlWordHandler(rtfParser, "fi", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("fid", new RtfCtrlWordHandler(rtfParser, "fid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "field",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "field",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "file",
                new RtfCtrlWordHandler(
                        rtfParser, "file", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "filetbl",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "filetbl",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "fittext",
                new RtfCtrlWordHandler(rtfParser, "fittext", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "fjgothic",
                new RtfCtrlWordHandler(rtfParser, "fjgothic", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "fjminchou",
                new RtfCtrlWordHandler(rtfParser, "fjminchou", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "fldalt", new RtfCtrlWordHandler(rtfParser, "fldalt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "flddirty",
                new RtfCtrlWordHandler(rtfParser, "flddirty", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fldedit",
                new RtfCtrlWordHandler(rtfParser, "fldedit", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fldinst",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "fldinst",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "fldlock",
                new RtfCtrlWordHandler(rtfParser, "fldlock", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fldpriv",
                new RtfCtrlWordHandler(rtfParser, "fldpriv", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fldrslt",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "fldrslt",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "fldtype",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "fldtype",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "flomajor",
                new RtfCtrlWordHandler(rtfParser, "flomajor", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "flominor",
                new RtfCtrlWordHandler(rtfParser, "flominor", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fmodern",
                new RtfCtrlWordHandler(rtfParser, "fmodern", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("fn", new RtfCtrlWordHandler(rtfParser, "fn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "fname",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "fname",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "fnetwork",
                new RtfCtrlWordHandler(rtfParser, "fnetwork", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fnil", new RtfCtrlWordHandler(rtfParser, "fnil", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fnonfilesys",
                new RtfCtrlWordHandler(rtfParser, "fnonfilesys", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fontemb",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "fontemb",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "fontfile",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "fontfile",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "fonttbl",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "fonttbl",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationFontTable"));
        ctrlWords.put(
                "footer",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "footer",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "footerf",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "footerf",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "footerl",
                new RtfCtrlWordHandler(rtfParser, "footerl", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", null));
        ctrlWords.put(
                "footerr",
                new RtfCtrlWordHandler(rtfParser, "footerr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "footery",
                new RtfCtrlWordHandler(rtfParser, "footery", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "footnote",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "footnote",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "forceupgrade",
                new RtfCtrlWordHandler(rtfParser, "forceupgrade", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "formdisp",
                new RtfCtrlWordHandler(rtfParser, "formdisp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "formfield",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "formfield",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "formprot",
                new RtfCtrlWordHandler(rtfParser, "formprot", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "formshade",
                new RtfCtrlWordHandler(rtfParser, "formshade", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fosnum", new RtfCtrlWordHandler(rtfParser, "fosnum", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "fprq", new RtfCtrlWordHandler(rtfParser, "fprq", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "fracwidth",
                new RtfCtrlWordHandler(rtfParser, "fracwidth", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "frelative",
                new RtfCtrlWordHandler(rtfParser, "frelative", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "frmtxbtlr",
                new RtfCtrlWordHandler(rtfParser, "frmtxbtlr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "frmtxlrtb",
                new RtfCtrlWordHandler(rtfParser, "frmtxlrtb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "frmtxlrtbv",
                new RtfCtrlWordHandler(rtfParser, "frmtxlrtbv", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "frmtxtbrl",
                new RtfCtrlWordHandler(rtfParser, "frmtxtbrl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "frmtxtbrlv",
                new RtfCtrlWordHandler(rtfParser, "frmtxtbrlv", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "froman", new RtfCtrlWordHandler(rtfParser, "froman", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fromhtml",
                new RtfCtrlWordHandler(rtfParser, "fromhtml", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fromtext",
                new RtfCtrlWordHandler(rtfParser, "fromtext", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("fs", new RtfCtrlWordHandler(rtfParser, "fs", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "fscript",
                new RtfCtrlWordHandler(rtfParser, "fscript", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fswiss", new RtfCtrlWordHandler(rtfParser, "fswiss", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftech", new RtfCtrlWordHandler(rtfParser, "ftech", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnalt", new RtfCtrlWordHandler(rtfParser, "ftnalt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnbj", new RtfCtrlWordHandler(rtfParser, "ftnbj", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftncn",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "ftncn",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "ftnil", new RtfCtrlWordHandler(rtfParser, "ftnil", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnlytwnine",
                new RtfCtrlWordHandler(rtfParser, "ftnlytwnine", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnnalc",
                new RtfCtrlWordHandler(rtfParser, "ftnnalc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnnar", new RtfCtrlWordHandler(rtfParser, "ftnnar", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnnauc",
                new RtfCtrlWordHandler(rtfParser, "ftnnauc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnnchi",
                new RtfCtrlWordHandler(rtfParser, "ftnnchi", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnnchosung",
                new RtfCtrlWordHandler(rtfParser, "ftnnchosung", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnncnum",
                new RtfCtrlWordHandler(rtfParser, "ftnncnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnndbar",
                new RtfCtrlWordHandler(rtfParser, "ftnndbar", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnndbnum",
                new RtfCtrlWordHandler(rtfParser, "ftnndbnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnndbnumd",
                new RtfCtrlWordHandler(rtfParser, "ftnndbnumd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnndbnumk",
                new RtfCtrlWordHandler(rtfParser, "ftnndbnumk", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnndbnumt",
                new RtfCtrlWordHandler(rtfParser, "ftnndbnumt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnnganada",
                new RtfCtrlWordHandler(rtfParser, "ftnnganada", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnngbnum",
                new RtfCtrlWordHandler(rtfParser, "ftnngbnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnngbnumd",
                new RtfCtrlWordHandler(rtfParser, "ftnngbnumd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnngbnumk",
                new RtfCtrlWordHandler(rtfParser, "ftnngbnumk", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnngbnuml",
                new RtfCtrlWordHandler(rtfParser, "ftnngbnuml", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnnrlc",
                new RtfCtrlWordHandler(rtfParser, "ftnnrlc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnnruc",
                new RtfCtrlWordHandler(rtfParser, "ftnnruc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnnzodiac",
                new RtfCtrlWordHandler(rtfParser, "ftnnzodiac", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnnzodiacd",
                new RtfCtrlWordHandler(rtfParser, "ftnnzodiacd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnnzodiacl",
                new RtfCtrlWordHandler(rtfParser, "ftnnzodiacl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnrestart",
                new RtfCtrlWordHandler(rtfParser, "ftnrestart", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnrstcont",
                new RtfCtrlWordHandler(rtfParser, "ftnrstcont", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnrstpg",
                new RtfCtrlWordHandler(rtfParser, "ftnrstpg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ftnsep",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "ftnsep",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "ftnsepc",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "ftnsepc",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "ftnstart",
                new RtfCtrlWordHandler(rtfParser, "ftnstart", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ftntj", new RtfCtrlWordHandler(rtfParser, "ftntj", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fttruetype",
                new RtfCtrlWordHandler(rtfParser, "fttruetype", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fvaliddos",
                new RtfCtrlWordHandler(rtfParser, "fvaliddos", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fvalidhpfs",
                new RtfCtrlWordHandler(rtfParser, "fvalidhpfs", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fvalidmac",
                new RtfCtrlWordHandler(rtfParser, "fvalidmac", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "fvalidntfs",
                new RtfCtrlWordHandler(rtfParser, "fvalidntfs", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "g",
                new RtfCtrlWordHandler(
                        rtfParser, "g", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put("gcw", new RtfCtrlWordHandler(rtfParser, "gcw", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "generator",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "generator",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationNull"));
        ctrlWords.put(
                "green", new RtfCtrlWordHandler(rtfParser, "green", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "gridtbl",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "gridtbl",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "gutter", new RtfCtrlWordHandler(rtfParser, "gutter", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "gutterprl",
                new RtfCtrlWordHandler(rtfParser, "gutterprl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "guttersxn",
                new RtfCtrlWordHandler(rtfParser, "guttersxn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "header",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "header",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "headerf",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "headerf",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "headerl",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "headerl",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "headerr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "headerr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "headery",
                new RtfCtrlWordHandler(rtfParser, "headery", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "hich", new RtfCtrlWordHandler(rtfParser, "hich", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "highlight",
                new RtfCtrlWordHandler(rtfParser, "highlight", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "hlfr", new RtfCtrlWordHandler(rtfParser, "hlfr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "hlinkbase",
                new RtfCtrlWordHandler(rtfParser, "hlinkbase", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "hlloc", new RtfCtrlWordHandler(rtfParser, "hlloc", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "hlsrc", new RtfCtrlWordHandler(rtfParser, "hlsrc", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "horzdoc",
                new RtfCtrlWordHandler(rtfParser, "horzdoc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "horzsect",
                new RtfCtrlWordHandler(rtfParser, "horzsect", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "horzvert",
                new RtfCtrlWordHandler(rtfParser, "horzvert", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("hr", new RtfCtrlWordHandler(rtfParser, "hr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "hsv",
                new RtfCtrlWordHandler(
                        rtfParser, "hsv", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "htmautsp",
                new RtfCtrlWordHandler(rtfParser, "htmautsp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "htmlbase",
                new RtfCtrlWordHandler(rtfParser, "htmlbase", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "htmlrtf",
                new RtfCtrlWordHandler(rtfParser, "htmlrtf", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "htmltag",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "htmltag",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "hwelev", new RtfCtrlWordHandler(rtfParser, "hwelev", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "hyphauto",
                new RtfCtrlWordHandler(rtfParser, "hyphauto", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "hyphcaps",
                new RtfCtrlWordHandler(rtfParser, "hyphcaps", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "hyphconsec",
                new RtfCtrlWordHandler(rtfParser, "hyphconsec", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "hyphhotz",
                new RtfCtrlWordHandler(rtfParser, "hyphhotz", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "hyphpar",
                new RtfCtrlWordHandler(rtfParser, "hyphpar", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put("i", new RtfCtrlWordHandler(rtfParser, "i", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put("id", new RtfCtrlWordHandler(rtfParser, "id", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ignoremixedcontent",
                new RtfCtrlWordHandler(
                        rtfParser, "ignoremixedcontent", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ilfomacatclnup",
                new RtfCtrlWordHandler(rtfParser, "ilfomacatclnup", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ilvl", new RtfCtrlWordHandler(rtfParser, "ilvl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "impr", new RtfCtrlWordHandler(rtfParser, "impr", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "indmirror",
                new RtfCtrlWordHandler(rtfParser, "indmirror", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "indrlsweleven",
                new RtfCtrlWordHandler(rtfParser, "indrlsweleven", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "info",
                new RtfCtrlWordHandler(
                        rtfParser, "info", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationInfo"));
        ctrlWords.put(
                "insrsid",
                new RtfCtrlWordHandler(rtfParser, "insrsid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "intbl", new RtfCtrlWordHandler(rtfParser, "intbl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ipgp", new RtfCtrlWordHandler(rtfParser, "ipgp", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "irow", new RtfCtrlWordHandler(rtfParser, "irow", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "irowband",
                new RtfCtrlWordHandler(rtfParser, "irowband", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "itap", new RtfCtrlWordHandler(rtfParser, "itap", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("ixe", new RtfCtrlWordHandler(rtfParser, "ixe", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "jclisttab",
                new RtfCtrlWordHandler(rtfParser, "jclisttab", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "jcompress",
                new RtfCtrlWordHandler(rtfParser, "jcompress", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "jexpand",
                new RtfCtrlWordHandler(rtfParser, "jexpand", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "jis", new RtfCtrlWordHandler(rtfParser, "jis", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "jpegblip",
                new RtfCtrlWordHandler(rtfParser, "jpegblip", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "jsksu", new RtfCtrlWordHandler(rtfParser, "jsksu", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "keep", new RtfCtrlWordHandler(rtfParser, "keep", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "keepn", new RtfCtrlWordHandler(rtfParser, "keepn", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "kerning",
                new RtfCtrlWordHandler(rtfParser, "kerning", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "keycode",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "keycode",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "keywords",
                new RtfCtrlWordHandler(
                        rtfParser, "keywords", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationInfo"));
        ctrlWords.put(
                "krnprsnet",
                new RtfCtrlWordHandler(rtfParser, "krnprsnet", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ksulang",
                new RtfCtrlWordHandler(rtfParser, "ksulang", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "landscape",
                new RtfCtrlWordHandler(rtfParser, "landscape", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "lang", new RtfCtrlWordHandler(rtfParser, "lang", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "langfe", new RtfCtrlWordHandler(rtfParser, "langfe", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "langfenp",
                new RtfCtrlWordHandler(rtfParser, "langfenp", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "langnp", new RtfCtrlWordHandler(rtfParser, "langnp", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "lastrow",
                new RtfCtrlWordHandler(rtfParser, "lastrow", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "latentstyles",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "latentstyles",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "lbr", new RtfCtrlWordHandler(rtfParser, "lbr", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "lchars",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "lchars",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "ldblquote",
                new RtfCtrlWordHandler(rtfParser, "ldblquote", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", "\0x147"));
        ctrlWords.put(
                "level", new RtfCtrlWordHandler(rtfParser, "level", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "levelfollow",
                new RtfCtrlWordHandler(rtfParser, "levelfollow", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "levelindent",
                new RtfCtrlWordHandler(rtfParser, "levelindent", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "leveljc",
                new RtfCtrlWordHandler(rtfParser, "leveljc", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "leveljcn",
                new RtfCtrlWordHandler(rtfParser, "leveljcn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "levellegal",
                new RtfCtrlWordHandler(rtfParser, "levellegal", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "levelnfc",
                new RtfCtrlWordHandler(rtfParser, "levelnfc", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "levelnfcn",
                new RtfCtrlWordHandler(rtfParser, "levelnfcn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "levelnorestart",
                new RtfCtrlWordHandler(rtfParser, "levelnorestart", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "levelnumbers",
                new RtfCtrlWordHandler(rtfParser, "levelnumbers", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        //		ctrlWords.put("levelnumbers", new RtfCtrlWordHandler(rtfParser, "levelnumbers", 0,
        // false,
        // RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationListTable"));
        ctrlWords.put(
                "levelold",
                new RtfCtrlWordHandler(rtfParser, "levelold", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "levelpicture",
                new RtfCtrlWordHandler(rtfParser, "levelpicture", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "levelprev",
                new RtfCtrlWordHandler(rtfParser, "levelprev", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "levelprevspace",
                new RtfCtrlWordHandler(rtfParser, "levelprevspace", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "levelspace",
                new RtfCtrlWordHandler(rtfParser, "levelspace", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "levelstartat",
                new RtfCtrlWordHandler(rtfParser, "levelstartat", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "leveltemplateid",
                new RtfCtrlWordHandler(rtfParser, "leveltemplateid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "leveltext",
                new RtfCtrlWordHandler(rtfParser, "leveltext", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("li", new RtfCtrlWordHandler(rtfParser, "li", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("lin", new RtfCtrlWordHandler(rtfParser, "lin", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "line", new RtfCtrlWordHandler(rtfParser, "line", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "linebetcol",
                new RtfCtrlWordHandler(rtfParser, "linebetcol", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "linecont",
                new RtfCtrlWordHandler(rtfParser, "linecont", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "linemod",
                new RtfCtrlWordHandler(rtfParser, "linemod", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "lineppage",
                new RtfCtrlWordHandler(rtfParser, "lineppage", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "linerestart",
                new RtfCtrlWordHandler(rtfParser, "linerestart", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "linestart",
                new RtfCtrlWordHandler(rtfParser, "linestart", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "linestarts",
                new RtfCtrlWordHandler(rtfParser, "linestarts", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "linex", new RtfCtrlWordHandler(rtfParser, "linex", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "linkself",
                new RtfCtrlWordHandler(rtfParser, "linkself", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "linkstyles",
                new RtfCtrlWordHandler(rtfParser, "linkstyles", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "linktoquery",
                new RtfCtrlWordHandler(rtfParser, "linktoquery", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "linkval",
                new RtfCtrlWordHandler(rtfParser, "linkval", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "lisa", new RtfCtrlWordHandler(rtfParser, "lisa", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "lisb", new RtfCtrlWordHandler(rtfParser, "lisb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "list", new RtfCtrlWordHandler(rtfParser, "list", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "listlevel",
                new RtfCtrlWordHandler(rtfParser, "listlevel", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "listhybrid",
                new RtfCtrlWordHandler(rtfParser, "listhybrid", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "listid", new RtfCtrlWordHandler(rtfParser, "listid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "listname",
                new RtfCtrlWordHandler(
                        rtfParser, "listname", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationNull"));
        ctrlWords.put(
                "listoverride",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "listoverride",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationNull"));
        ctrlWords.put(
                "listoverridecount",
                new RtfCtrlWordHandler(
                        rtfParser, "listoverridecount", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "listoverrideformat",
                new RtfCtrlWordHandler(
                        rtfParser, "listoverrideformat", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "listoverridestart",
                new RtfCtrlWordHandler(
                        rtfParser, "listoverridestart", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "listoverridestartat",
                new RtfCtrlWordHandler(
                        rtfParser, "listoverridestartat", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "listoverridetable",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "listoverridetable",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationNull"));
        ctrlWords.put(
                "listpicture",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "listpicture",
                        0,
                        true,
                        RtfCtrlWordType.DESTINATION,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "listrestarthdn",
                new RtfCtrlWordHandler(rtfParser, "listrestarthdn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "listsimple",
                new RtfCtrlWordHandler(rtfParser, "listsimple", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "liststyleid",
                new RtfCtrlWordHandler(rtfParser, "liststyleid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "liststylename",
                new RtfCtrlWordHandler(rtfParser, "liststylename", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "listtable",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "listtable",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationListTable"));
        ctrlWords.put(
                "listtemplateid",
                new RtfCtrlWordHandler(rtfParser, "listtemplateid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "listtext",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "listtext",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationNull")); // ignore this because we understand 97-2007
        ctrlWords.put(
                "lnbrkrule",
                new RtfCtrlWordHandler(rtfParser, "lnbrkrule", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "lndscpsxn",
                new RtfCtrlWordHandler(rtfParser, "lndscpsxn", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "lnongrid",
                new RtfCtrlWordHandler(rtfParser, "lnongrid", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "loch", new RtfCtrlWordHandler(rtfParser, "loch", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "lquote",
                new RtfCtrlWordHandler(rtfParser, "lquote", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", "\0x145"));
        ctrlWords.put("ls", new RtfCtrlWordHandler(rtfParser, "ls", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "lsdlocked",
                new RtfCtrlWordHandler(rtfParser, "lsdlocked", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "lsdlockeddef",
                new RtfCtrlWordHandler(rtfParser, "lsdlockeddef", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "lsdlockedexcept",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "lsdlockedexcept",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "lsdpriority",
                new RtfCtrlWordHandler(rtfParser, "lsdpriority", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "lsdprioritydef",
                new RtfCtrlWordHandler(rtfParser, "lsdprioritydef", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "lsdqformat",
                new RtfCtrlWordHandler(rtfParser, "lsdqformat", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "lsdqformatdef",
                new RtfCtrlWordHandler(rtfParser, "lsdqformatdef", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "lsdsemihidden",
                new RtfCtrlWordHandler(rtfParser, "lsdsemihidden", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "lsdsemihiddendef",
                new RtfCtrlWordHandler(rtfParser, "lsdsemihiddendef", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "lsdstimax",
                new RtfCtrlWordHandler(rtfParser, "lsdstimax", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "lsdunhideused",
                new RtfCtrlWordHandler(rtfParser, "lsdunhideused", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "lsdunhideuseddef",
                new RtfCtrlWordHandler(rtfParser, "lsdunhideuseddef", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ltrch", new RtfCtrlWordHandler(rtfParser, "ltrch", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ltrdoc", new RtfCtrlWordHandler(rtfParser, "ltrdoc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ltrmark",
                new RtfCtrlWordHandler(rtfParser, "ltrmark", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "ltrpar", new RtfCtrlWordHandler(rtfParser, "ltrpar", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ltrrow", new RtfCtrlWordHandler(rtfParser, "ltrrow", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ltrsect",
                new RtfCtrlWordHandler(rtfParser, "ltrsect", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "lvltentative",
                new RtfCtrlWordHandler(rtfParser, "lvltentative", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "lytcalctblwd",
                new RtfCtrlWordHandler(rtfParser, "lytcalctblwd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "lytexcttp",
                new RtfCtrlWordHandler(rtfParser, "lytexcttp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "lytprtmet",
                new RtfCtrlWordHandler(rtfParser, "lytprtmet", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "lyttblrtgr",
                new RtfCtrlWordHandler(rtfParser, "lyttblrtgr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("mac", new RtfCtrlWordHandler(rtfParser, "mac", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "macc",
                new RtfCtrlWordHandler(
                        rtfParser, "macc", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "maccpr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "maccpr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "macpict",
                new RtfCtrlWordHandler(rtfParser, "macpict", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mailmerge",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mailmerge",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "makebackup",
                new RtfCtrlWordHandler(rtfParser, "makebackup", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "maln",
                new RtfCtrlWordHandler(
                        rtfParser, "maln", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "malnscr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "malnscr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "manager",
                new RtfCtrlWordHandler(
                        rtfParser, "manager", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationInfo"));
        ctrlWords.put(
                "margb", new RtfCtrlWordHandler(rtfParser, "margb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "margbsxn",
                new RtfCtrlWordHandler(rtfParser, "margbsxn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "margl", new RtfCtrlWordHandler(rtfParser, "margl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "marglsxn",
                new RtfCtrlWordHandler(rtfParser, "marglsxn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "margmirror",
                new RtfCtrlWordHandler(rtfParser, "margmirror", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "margmirsxn",
                new RtfCtrlWordHandler(rtfParser, "margmirsxn", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "margpr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "margpr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "margr", new RtfCtrlWordHandler(rtfParser, "margr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "margrsxn",
                new RtfCtrlWordHandler(rtfParser, "margrsxn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "margsz", new RtfCtrlWordHandler(rtfParser, "margsz", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "margt", new RtfCtrlWordHandler(rtfParser, "margt", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "margtsxn",
                new RtfCtrlWordHandler(rtfParser, "margtsxn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mbar",
                new RtfCtrlWordHandler(
                        rtfParser, "mbar", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mbarpr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mbarpr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mbasejc",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mbasejc",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mbegchr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mbegchr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mborderbox",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mborderbox",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mborderboxpr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mborderboxpr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mbox",
                new RtfCtrlWordHandler(
                        rtfParser, "mbox", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mboxpr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mboxpr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mbrk", new RtfCtrlWordHandler(rtfParser, "mbrk", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mbrkbin",
                new RtfCtrlWordHandler(rtfParser, "mbrkbin", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mbrkbinsub",
                new RtfCtrlWordHandler(rtfParser, "mbrkbinsub", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mcgp", new RtfCtrlWordHandler(rtfParser, "mcgp", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mcgprule",
                new RtfCtrlWordHandler(rtfParser, "mcgprule", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mchr",
                new RtfCtrlWordHandler(
                        rtfParser, "mchr", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mcount",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mcount",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mcsp", new RtfCtrlWordHandler(rtfParser, "mcsp", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mctrlpr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mctrlpr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "md",
                new RtfCtrlWordHandler(
                        rtfParser, "md", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mdefjc", new RtfCtrlWordHandler(rtfParser, "mdefjc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mdeg",
                new RtfCtrlWordHandler(
                        rtfParser, "mdeg", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mdeghide",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mdeghide",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mden",
                new RtfCtrlWordHandler(
                        rtfParser, "mden", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mdiff",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mdiff",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mdispdef",
                new RtfCtrlWordHandler(rtfParser, "mdispdef", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mdpr",
                new RtfCtrlWordHandler(
                        rtfParser, "mdpr", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "me",
                new RtfCtrlWordHandler(
                        rtfParser, "me", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mendchr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mendchr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "meqarr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "meqarr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "meqarrpr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "meqarrpr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mf",
                new RtfCtrlWordHandler(
                        rtfParser, "mf", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mfname",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mfname",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mfpr",
                new RtfCtrlWordHandler(
                        rtfParser, "mfpr", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mfunc",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mfunc",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mfuncpr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mfuncpr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mgroupchr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mgroupchr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mgroupchrpr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mgroupchrpr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mgrow",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mgrow",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mhidebot",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mhidebot",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mhideleft",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mhideleft",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mhideright",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mhideright",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mhidetop",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mhidetop",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mhtmltag",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mhtmltag",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put("min", new RtfCtrlWordHandler(rtfParser, "min", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mintersp",
                new RtfCtrlWordHandler(rtfParser, "mintersp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mintlim",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mintlim",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mintrasp",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mintrasp",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mjc",
                new RtfCtrlWordHandler(
                        rtfParser, "mjc", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mlim",
                new RtfCtrlWordHandler(
                        rtfParser, "mlim", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mlimloc",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mlimloc",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mlimlow",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mlimlow",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mlimlowpr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mlimlowpr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mlimupp",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mlimupp",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mlimupppr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mlimupppr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mlit", new RtfCtrlWordHandler(rtfParser, "mlit", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mlmargin",
                new RtfCtrlWordHandler(rtfParser, "mlmargin", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mm",
                new RtfCtrlWordHandler(
                        rtfParser, "mm", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mmaddfieldname",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmaddfieldname",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmath",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmath",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmathfont",
                new RtfCtrlWordHandler(rtfParser, "mmathfont", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmathpara",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmathpara",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmathpict",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmathpict",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmathpr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmathpr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmattach",
                new RtfCtrlWordHandler(rtfParser, "mmattach", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmaxdist",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmaxdist",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmblanklines",
                new RtfCtrlWordHandler(rtfParser, "mmblanklines", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmblanklinks",
                new RtfCtrlWordHandler(rtfParser, "mmblanklinks", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmc",
                new RtfCtrlWordHandler(
                        rtfParser, "mmc", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mmcjc",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmcjc",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmconnectstrdata",
                new RtfCtrlWordHandler(
                        rtfParser, "mmconnectstrdata", 0, false, RtfCtrlWordType.DESTINATION_EX, "\\*\\", " ", null));
        ctrlWords.put(
                "mmcpr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmcpr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmcs",
                new RtfCtrlWordHandler(
                        rtfParser, "mmcs", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mmdatasource",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmdatasource",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmdatatypeaccess",
                new RtfCtrlWordHandler(rtfParser, "mmdatatypeaccess", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmdatatypeexcel",
                new RtfCtrlWordHandler(rtfParser, "mmdatatypeexcel", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmdatatypefile",
                new RtfCtrlWordHandler(rtfParser, "mmdatatypefile", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmdatatypeodbc",
                new RtfCtrlWordHandler(rtfParser, "mmdatatypeodbc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmdatatypeodso",
                new RtfCtrlWordHandler(rtfParser, "mmdatatypeodso", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmdatatypeqt",
                new RtfCtrlWordHandler(rtfParser, "mmdatatypeqt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmdefaultStructuredQueryLanguage",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmdefaultStructuredQueryLanguage",
                        0,
                        false,
                        RtfCtrlWordType.FLAG,
                        "\\",
                        " ",
                        null));
        ctrlWords.put(
                "mmdestemail",
                new RtfCtrlWordHandler(rtfParser, "mmdestemail", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmdestfax",
                new RtfCtrlWordHandler(rtfParser, "mmdestfax", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmdestnewdoc",
                new RtfCtrlWordHandler(rtfParser, "mmdestnewdoc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmdestprinter",
                new RtfCtrlWordHandler(rtfParser, "mmdestprinter", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmerrors",
                new RtfCtrlWordHandler(rtfParser, "mmerrors", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mmfttypeaddress",
                new RtfCtrlWordHandler(rtfParser, "mmfttypeaddress", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmfttypebarcode",
                new RtfCtrlWordHandler(rtfParser, "mmfttypebarcode", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmfttypedbcolumn",
                new RtfCtrlWordHandler(rtfParser, "mmfttypedbcolumn", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmfttypemapped",
                new RtfCtrlWordHandler(rtfParser, "mmfttypemapped", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmfttypenull",
                new RtfCtrlWordHandler(rtfParser, "mmfttypenull", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmfttypesalutation",
                new RtfCtrlWordHandler(
                        rtfParser, "mmfttypesalutation", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmheadersource",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmheadersource",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmjdsotype",
                new RtfCtrlWordHandler(rtfParser, "mmjdsotype", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mmlinktoquery",
                new RtfCtrlWordHandler(rtfParser, "mmlinktoquery", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmmailsubject",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmmailsubject",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmmaintypecatalog",
                new RtfCtrlWordHandler(
                        rtfParser, "mmmaintypecatalog", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmmaintypeemail",
                new RtfCtrlWordHandler(rtfParser, "mmmaintypeemail", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmmaintypeenvelopes",
                new RtfCtrlWordHandler(
                        rtfParser, "mmmaintypeenvelopes", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmmaintypefax",
                new RtfCtrlWordHandler(rtfParser, "mmmaintypefax", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmmaintypelabels",
                new RtfCtrlWordHandler(rtfParser, "mmmaintypelabels", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmmaintypeletters",
                new RtfCtrlWordHandler(
                        rtfParser, "mmmaintypeletters", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mmodso",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmodso",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmodsoactive",
                new RtfCtrlWordHandler(rtfParser, "mmodsoactive", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mmodsocoldelim",
                new RtfCtrlWordHandler(rtfParser, "mmodsocoldelim", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mmodsocolumn",
                new RtfCtrlWordHandler(rtfParser, "mmodsocolumn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mmodsodynaddr",
                new RtfCtrlWordHandler(rtfParser, "mmodsodynaddr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mmodsofhdr",
                new RtfCtrlWordHandler(rtfParser, "mmodsofhdr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mmodsofilter",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmodsofilter",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmodsofldmpdata",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmodsofldmpdata",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmodsofmcolumn",
                new RtfCtrlWordHandler(rtfParser, "mmodsofmcolumn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mmodsohash",
                new RtfCtrlWordHandler(rtfParser, "mmodsohash", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mmodsolid",
                new RtfCtrlWordHandler(rtfParser, "mmodsolid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mmodsomappedname",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmodsomappedname",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmodsoname",
                new RtfCtrlWordHandler(
                        rtfParser, "mmodsoname", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", null));
        ctrlWords.put(
                "mmodsorecipdata",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmodsorecipdata",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmodsosort",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmodsosort",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmodsosrc",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmodsosrc",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmodsotable",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmodsotable",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmodsoudldata",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmodsoudldata",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmodsouniquetag",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmodsouniquetag",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmpr",
                new RtfCtrlWordHandler(
                        rtfParser, "mmpr", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mmquery",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mmquery",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mmr",
                new RtfCtrlWordHandler(
                        rtfParser, "mmr", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mmreccur",
                new RtfCtrlWordHandler(rtfParser, "mmreccur", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mmshowdata",
                new RtfCtrlWordHandler(rtfParser, "mmshowdata", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mnary",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mnary",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mnarylim",
                new RtfCtrlWordHandler(rtfParser, "mnarylim", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mnarypr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mnarypr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mnobreak",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mnobreak",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mnor", new RtfCtrlWordHandler(rtfParser, "mnor", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mnum",
                new RtfCtrlWordHandler(
                        rtfParser, "mnum", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put("mo", new RtfCtrlWordHandler(rtfParser, "mo", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mobjdist",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mobjdist",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "momath",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "momath",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "momathpara",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "momathpara",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "momathparapr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "momathparapr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mopemu",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mopemu",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mphant",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mphant",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mphantpr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mphantpr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mplchide",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mplchide",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mpos",
                new RtfCtrlWordHandler(
                        rtfParser, "mpos", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mpostsp",
                new RtfCtrlWordHandler(rtfParser, "mpostsp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mpresp", new RtfCtrlWordHandler(rtfParser, "mpresp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mr",
                new RtfCtrlWordHandler(
                        rtfParser, "mr", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mrad",
                new RtfCtrlWordHandler(
                        rtfParser, "mrad", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mradpr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mradpr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mrmargin",
                new RtfCtrlWordHandler(rtfParser, "mrmargin", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mrpr",
                new RtfCtrlWordHandler(
                        rtfParser, "mrpr", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "mrsp", new RtfCtrlWordHandler(rtfParser, "mrsp", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mrsprule",
                new RtfCtrlWordHandler(rtfParser, "mrsprule", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mscr", new RtfCtrlWordHandler(rtfParser, "mscr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "msepchr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "msepchr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mshow",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mshow",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mshp",
                new RtfCtrlWordHandler(
                        rtfParser, "mshp", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "msize",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "msize",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "msmallfrac",
                new RtfCtrlWordHandler(rtfParser, "msmallfrac", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "msmcap", new RtfCtrlWordHandler(rtfParser, "msmcap", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mspre",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mspre",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "msprepr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "msprepr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mssub",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mssub",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mssubpr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mssubpr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mssubsup",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mssubsup",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mssubsuppr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mssubsuppr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mssup",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mssup",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mssuppr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mssuppr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mstrikebltr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mstrikebltr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mstrikeh",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mstrikeh",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mstriketlbr",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mstriketlbr",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mstrikev",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mstrikev",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "msty", new RtfCtrlWordHandler(rtfParser, "msty", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "msub",
                new RtfCtrlWordHandler(
                        rtfParser, "msub", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "msubhide",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "msubhide",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "msup",
                new RtfCtrlWordHandler(
                        rtfParser, "msup", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "msuphide",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "msuphide",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put("mt", new RtfCtrlWordHandler(rtfParser, "mt", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mtext",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mtext",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mtransp",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mtransp",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mtype",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mtype",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mvauth", new RtfCtrlWordHandler(rtfParser, "mvauth", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mvdate", new RtfCtrlWordHandler(rtfParser, "mvdate", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mvertjc",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mvertjc",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put("mvf", new RtfCtrlWordHandler(rtfParser, "mvf", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mvfmf",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mvfmf",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mvfml",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mvfml",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put("mvt", new RtfCtrlWordHandler(rtfParser, "mvt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mvtof",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mvtof",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mvtol",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mvtol",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mwrapindent",
                new RtfCtrlWordHandler(rtfParser, "mwrapindent", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mwrapindet",
                new RtfCtrlWordHandler(rtfParser, "mwrapindet", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "mwrapright",
                new RtfCtrlWordHandler(rtfParser, "mwrapright", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "mzeroasc",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mzeroasc",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mzerodesc",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mzerodesc",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "mzerowid",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "mzerowid",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "nestcell",
                new RtfCtrlWordHandler(rtfParser, "nestcell", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "nestrow",
                new RtfCtrlWordHandler(rtfParser, "nestrow", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "nesttableprops",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "nesttableprops",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "newtblstyruls",
                new RtfCtrlWordHandler(rtfParser, "newtblstyruls", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nextfile",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "nextfile",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "noafcnsttbl",
                new RtfCtrlWordHandler(rtfParser, "noafcnsttbl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nobrkwrptbl",
                new RtfCtrlWordHandler(rtfParser, "nobrkwrptbl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nocolbal",
                new RtfCtrlWordHandler(rtfParser, "nocolbal", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nocompatoptions",
                new RtfCtrlWordHandler(rtfParser, "nocompatoptions", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nocwrap",
                new RtfCtrlWordHandler(rtfParser, "nocwrap", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nocxsptable",
                new RtfCtrlWordHandler(rtfParser, "nocxsptable", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "noextrasprl",
                new RtfCtrlWordHandler(rtfParser, "noextrasprl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nofchars",
                new RtfCtrlWordHandler(rtfParser, "nofchars", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "nofcharsws",
                new RtfCtrlWordHandler(rtfParser, "nofcharsws", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "nofeaturethrottle",
                new RtfCtrlWordHandler(
                        rtfParser, "nofeaturethrottle", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nofpages",
                new RtfCtrlWordHandler(rtfParser, "nofpages", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "nofwords",
                new RtfCtrlWordHandler(rtfParser, "nofwords", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "nogrowautofit",
                new RtfCtrlWordHandler(rtfParser, "nogrowautofit", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "noindnmbrts",
                new RtfCtrlWordHandler(rtfParser, "noindnmbrts", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nojkernpunct",
                new RtfCtrlWordHandler(rtfParser, "nojkernpunct", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nolead", new RtfCtrlWordHandler(rtfParser, "nolead", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "noline", new RtfCtrlWordHandler(rtfParser, "noline", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nolnhtadjtbl",
                new RtfCtrlWordHandler(rtfParser, "nolnhtadjtbl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nonesttables",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "nonesttables",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));

        //		  new RtfCtrlWordHandler(rtfParser, "shppict", 0, false, RtfCtrlWordType.DESTINATION_EX,
        // "\\*\\", " ", "RtfDestinationShppict" ));//"RtfDestinationShppict"));
        ctrlWords.put(
                "nonshppict",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "nonshppict",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationNull")); // "RtfDestinationShppict"));
        ctrlWords.put(
                "nooverflow",
                new RtfCtrlWordHandler(rtfParser, "nooverflow", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "noproof",
                new RtfCtrlWordHandler(rtfParser, "noproof", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "noqfpromote",
                new RtfCtrlWordHandler(rtfParser, "noqfpromote", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nosectexpand",
                new RtfCtrlWordHandler(rtfParser, "nosectexpand", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nosnaplinegrid",
                new RtfCtrlWordHandler(rtfParser, "nosnaplinegrid", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nospaceforul",
                new RtfCtrlWordHandler(rtfParser, "nospaceforul", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nosupersub",
                new RtfCtrlWordHandler(rtfParser, "nosupersub", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "notabind",
                new RtfCtrlWordHandler(rtfParser, "notabind", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "notbrkcnstfrctbl",
                new RtfCtrlWordHandler(rtfParser, "notbrkcnstfrctbl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "notcvasp",
                new RtfCtrlWordHandler(rtfParser, "notcvasp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "notvatxbx",
                new RtfCtrlWordHandler(rtfParser, "notvatxbx", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nouicompat",
                new RtfCtrlWordHandler(rtfParser, "nouicompat", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "noultrlspc",
                new RtfCtrlWordHandler(rtfParser, "noultrlspc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nowidctlpar",
                new RtfCtrlWordHandler(rtfParser, "nowidctlpar", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nowrap", new RtfCtrlWordHandler(rtfParser, "nowrap", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "nowwrap",
                new RtfCtrlWordHandler(rtfParser, "nowwrap", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "noxlattoyen",
                new RtfCtrlWordHandler(rtfParser, "noxlattoyen", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "objalias",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "objalias",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "objalign",
                new RtfCtrlWordHandler(rtfParser, "objalign", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "objattph",
                new RtfCtrlWordHandler(rtfParser, "objattph", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "objautlink",
                new RtfCtrlWordHandler(rtfParser, "objautlink", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "objclass",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "objclass",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "objcropb",
                new RtfCtrlWordHandler(rtfParser, "objcropb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "objcropl",
                new RtfCtrlWordHandler(rtfParser, "objcropl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "objcropr",
                new RtfCtrlWordHandler(rtfParser, "objcropr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "objcropt",
                new RtfCtrlWordHandler(rtfParser, "objcropt", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "objdata",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "objdata",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "object",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "object",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "objemb", new RtfCtrlWordHandler(rtfParser, "objemb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "objh", new RtfCtrlWordHandler(rtfParser, "objh", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "objhtml",
                new RtfCtrlWordHandler(rtfParser, "objhtml", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "objicemb",
                new RtfCtrlWordHandler(rtfParser, "objicemb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "objlink",
                new RtfCtrlWordHandler(rtfParser, "objlink", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "objlock",
                new RtfCtrlWordHandler(rtfParser, "objlock", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "objname",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "objname",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "objocx", new RtfCtrlWordHandler(rtfParser, "objocx", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "objpub", new RtfCtrlWordHandler(rtfParser, "objpub", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "objscalex",
                new RtfCtrlWordHandler(rtfParser, "objscalex", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "objscaley",
                new RtfCtrlWordHandler(rtfParser, "objscaley", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "objsect",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "objsect",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "objsetsize",
                new RtfCtrlWordHandler(rtfParser, "objsetsize", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "objsub", new RtfCtrlWordHandler(rtfParser, "objsub", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "objtime",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "objtime",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "objtransy",
                new RtfCtrlWordHandler(rtfParser, "objtransy", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "objupdate",
                new RtfCtrlWordHandler(rtfParser, "objupdate", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "objw", new RtfCtrlWordHandler(rtfParser, "objw", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "oldas", new RtfCtrlWordHandler(rtfParser, "oldas", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "oldcprops",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "oldcprops",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "oldlinewrap",
                new RtfCtrlWordHandler(rtfParser, "oldlinewrap", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "oldpprops",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "oldpprops",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "oldsprops",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "oldsprops",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "oldtprops",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "oldtprops",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "oleclsid",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "oleclsid",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "operator",
                new RtfCtrlWordHandler(
                        rtfParser, "operator", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationInfo"));
        ctrlWords.put(
                "otblrul",
                new RtfCtrlWordHandler(rtfParser, "otblrul", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "outl", new RtfCtrlWordHandler(rtfParser, "outl", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "outlinelevel",
                new RtfCtrlWordHandler(rtfParser, "outlinelevel", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "overlay",
                new RtfCtrlWordHandler(rtfParser, "overlay", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "page", new RtfCtrlWordHandler(rtfParser, "page", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "pagebb", new RtfCtrlWordHandler(rtfParser, "pagebb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "panose",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "panose",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationNull"));
        ctrlWords.put(
                "paperh", new RtfCtrlWordHandler(rtfParser, "paperh", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "paperw", new RtfCtrlWordHandler(rtfParser, "paperw", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "par", new RtfCtrlWordHandler(rtfParser, "par", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", "\n"));
        ctrlWords.put(
                "pararsid",
                new RtfCtrlWordHandler(rtfParser, "pararsid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pard", new RtfCtrlWordHandler(rtfParser, "pard", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "passwordhash",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "passwordhash",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put("pc", new RtfCtrlWordHandler(rtfParser, "pc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("pca", new RtfCtrlWordHandler(rtfParser, "pca", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgbrdrb",
                new RtfCtrlWordHandler(rtfParser, "pgbrdrb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgbrdrfoot",
                new RtfCtrlWordHandler(rtfParser, "pgbrdrfoot", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgbrdrhead",
                new RtfCtrlWordHandler(rtfParser, "pgbrdrhead", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgbrdrl",
                new RtfCtrlWordHandler(rtfParser, "pgbrdrl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgbrdropt",
                new RtfCtrlWordHandler(rtfParser, "pgbrdropt", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pgbrdrr",
                new RtfCtrlWordHandler(rtfParser, "pgbrdrr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgbrdrsnap",
                new RtfCtrlWordHandler(rtfParser, "pgbrdrsnap", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgbrdrt",
                new RtfCtrlWordHandler(rtfParser, "pgbrdrt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pghsxn", new RtfCtrlWordHandler(rtfParser, "pghsxn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pgnbidia",
                new RtfCtrlWordHandler(rtfParser, "pgnbidia", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnbidib",
                new RtfCtrlWordHandler(rtfParser, "pgnbidib", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnchosung",
                new RtfCtrlWordHandler(rtfParser, "pgnchosung", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgncnum",
                new RtfCtrlWordHandler(rtfParser, "pgncnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgncont",
                new RtfCtrlWordHandler(rtfParser, "pgncont", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgndbnum",
                new RtfCtrlWordHandler(rtfParser, "pgndbnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgndbnumd",
                new RtfCtrlWordHandler(rtfParser, "pgndbnumd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgndbnumk",
                new RtfCtrlWordHandler(rtfParser, "pgndbnumk", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgndbnumt",
                new RtfCtrlWordHandler(rtfParser, "pgndbnumt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgndec", new RtfCtrlWordHandler(rtfParser, "pgndec", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgndecd",
                new RtfCtrlWordHandler(rtfParser, "pgndecd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnganada",
                new RtfCtrlWordHandler(rtfParser, "pgnganada", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgngbnum",
                new RtfCtrlWordHandler(rtfParser, "pgngbnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgngbnumd",
                new RtfCtrlWordHandler(rtfParser, "pgngbnumd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgngbnumk",
                new RtfCtrlWordHandler(rtfParser, "pgngbnumk", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgngbnuml",
                new RtfCtrlWordHandler(rtfParser, "pgngbnuml", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnhindia",
                new RtfCtrlWordHandler(rtfParser, "pgnhindia", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnhindib",
                new RtfCtrlWordHandler(rtfParser, "pgnhindib", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnhindic",
                new RtfCtrlWordHandler(rtfParser, "pgnhindic", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnhindid",
                new RtfCtrlWordHandler(rtfParser, "pgnhindid", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnhn", new RtfCtrlWordHandler(rtfParser, "pgnhn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pgnhnsc",
                new RtfCtrlWordHandler(rtfParser, "pgnhnsc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnhnsh",
                new RtfCtrlWordHandler(rtfParser, "pgnhnsh", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnhnsm",
                new RtfCtrlWordHandler(rtfParser, "pgnhnsm", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnhnsn",
                new RtfCtrlWordHandler(rtfParser, "pgnhnsn", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnhnsp",
                new RtfCtrlWordHandler(rtfParser, "pgnhnsp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnid", new RtfCtrlWordHandler(rtfParser, "pgnid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pgnlcltr",
                new RtfCtrlWordHandler(rtfParser, "pgnlcltr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnlcrm",
                new RtfCtrlWordHandler(rtfParser, "pgnlcrm", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnrestart",
                new RtfCtrlWordHandler(rtfParser, "pgnrestart", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnstart",
                new RtfCtrlWordHandler(rtfParser, "pgnstart", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pgnstarts",
                new RtfCtrlWordHandler(rtfParser, "pgnstarts", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pgnthaia",
                new RtfCtrlWordHandler(rtfParser, "pgnthaia", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnthaib",
                new RtfCtrlWordHandler(rtfParser, "pgnthaib", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnthaic",
                new RtfCtrlWordHandler(rtfParser, "pgnthaic", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnucltr",
                new RtfCtrlWordHandler(rtfParser, "pgnucltr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnucrm",
                new RtfCtrlWordHandler(rtfParser, "pgnucrm", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnvieta",
                new RtfCtrlWordHandler(rtfParser, "pgnvieta", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnx", new RtfCtrlWordHandler(rtfParser, "pgnx", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pgny", new RtfCtrlWordHandler(rtfParser, "pgny", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pgnzodiac",
                new RtfCtrlWordHandler(rtfParser, "pgnzodiac", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnzodiacd",
                new RtfCtrlWordHandler(rtfParser, "pgnzodiacd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgnzodiacl",
                new RtfCtrlWordHandler(rtfParser, "pgnzodiacl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pgp",
                new RtfCtrlWordHandler(
                        rtfParser, "pgp", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationNull"));
        ctrlWords.put(
                "pgptbl",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "pgptbl",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationNull"));
        ctrlWords.put(
                "pgwsxn", new RtfCtrlWordHandler(rtfParser, "pgwsxn", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "phcol", new RtfCtrlWordHandler(rtfParser, "phcol", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "phmrg", new RtfCtrlWordHandler(rtfParser, "phmrg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "phnthaia",
                new RtfCtrlWordHandler(rtfParser, "phnthaia", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "phpg", new RtfCtrlWordHandler(rtfParser, "phpg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "picbmp", new RtfCtrlWordHandler(rtfParser, "picbmp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "picbpp", new RtfCtrlWordHandler(rtfParser, "picbpp", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "piccropb",
                new RtfCtrlWordHandler(rtfParser, "piccropb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "piccropl",
                new RtfCtrlWordHandler(rtfParser, "piccropl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "piccropr",
                new RtfCtrlWordHandler(rtfParser, "piccropr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "piccropt",
                new RtfCtrlWordHandler(rtfParser, "piccropt", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pich", new RtfCtrlWordHandler(rtfParser, "pich", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pichgoal",
                new RtfCtrlWordHandler(rtfParser, "pichgoal", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "picprop",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "picprop",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationShppict"));
        ctrlWords.put(
                "picscaled",
                new RtfCtrlWordHandler(rtfParser, "picscaled", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "picscalex",
                new RtfCtrlWordHandler(rtfParser, "picscalex", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "picscaley",
                new RtfCtrlWordHandler(rtfParser, "picscaley", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pict",
                new RtfCtrlWordHandler(
                        rtfParser, "pict", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationShppict"));
        ctrlWords.put(
                "picw", new RtfCtrlWordHandler(rtfParser, "picw", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "picwgoal",
                new RtfCtrlWordHandler(rtfParser, "picwgoal", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pindtabqc",
                new RtfCtrlWordHandler(rtfParser, "pindtabqc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pindtabql",
                new RtfCtrlWordHandler(rtfParser, "pindtabql", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pindtabqr",
                new RtfCtrlWordHandler(rtfParser, "pindtabqr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "plain", new RtfCtrlWordHandler(rtfParser, "plain", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pmartabqc",
                new RtfCtrlWordHandler(rtfParser, "pmartabqc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pmartabql",
                new RtfCtrlWordHandler(rtfParser, "pmartabql", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pmartabqr",
                new RtfCtrlWordHandler(rtfParser, "pmartabqr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pmmetafile",
                new RtfCtrlWordHandler(rtfParser, "pmmetafile", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pn",
                new RtfCtrlWordHandler(
                        rtfParser, "pn", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "pnacross",
                new RtfCtrlWordHandler(rtfParser, "pnacross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnaiu", new RtfCtrlWordHandler(rtfParser, "pnaiu", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnaiud", new RtfCtrlWordHandler(rtfParser, "pnaiud", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnaiueo",
                new RtfCtrlWordHandler(rtfParser, "pnaiueo", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnaiueod",
                new RtfCtrlWordHandler(rtfParser, "pnaiueod", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnb", new RtfCtrlWordHandler(rtfParser, "pnb", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "pnbidia",
                new RtfCtrlWordHandler(rtfParser, "pnbidia", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnbidib",
                new RtfCtrlWordHandler(rtfParser, "pnbidib", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pncaps",
                new RtfCtrlWordHandler(rtfParser, "pncaps", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "pncard", new RtfCtrlWordHandler(rtfParser, "pncard", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pncf", new RtfCtrlWordHandler(rtfParser, "pncf", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pnchosung",
                new RtfCtrlWordHandler(rtfParser, "pnchosung", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pncnum", new RtfCtrlWordHandler(rtfParser, "pncnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pndbnum",
                new RtfCtrlWordHandler(rtfParser, "pndbnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pndbnumd",
                new RtfCtrlWordHandler(rtfParser, "pndbnumd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pndbnumk",
                new RtfCtrlWordHandler(rtfParser, "pndbnumk", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pndbnuml",
                new RtfCtrlWordHandler(rtfParser, "pndbnuml", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pndbnumt",
                new RtfCtrlWordHandler(rtfParser, "pndbnumt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pndec", new RtfCtrlWordHandler(rtfParser, "pndec", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pndecd", new RtfCtrlWordHandler(rtfParser, "pndecd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("pnf", new RtfCtrlWordHandler(rtfParser, "pnf", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pnfs", new RtfCtrlWordHandler(rtfParser, "pnfs", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pnganada",
                new RtfCtrlWordHandler(rtfParser, "pnganada", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pngblip",
                new RtfCtrlWordHandler(rtfParser, "pngblip", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pngbnum",
                new RtfCtrlWordHandler(rtfParser, "pngbnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pngbnumd",
                new RtfCtrlWordHandler(rtfParser, "pngbnumd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pngbnumk",
                new RtfCtrlWordHandler(rtfParser, "pngbnumk", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pngbnuml",
                new RtfCtrlWordHandler(rtfParser, "pngbnuml", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnhang", new RtfCtrlWordHandler(rtfParser, "pnhang", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pni", new RtfCtrlWordHandler(rtfParser, "pni", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "pnindent",
                new RtfCtrlWordHandler(rtfParser, "pnindent", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pniroha",
                new RtfCtrlWordHandler(rtfParser, "pniroha", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnirohad",
                new RtfCtrlWordHandler(rtfParser, "pnirohad", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnlcltr",
                new RtfCtrlWordHandler(rtfParser, "pnlcltr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnlcrm", new RtfCtrlWordHandler(rtfParser, "pnlcrm", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnlvl", new RtfCtrlWordHandler(rtfParser, "pnlvl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pnlvlblt",
                new RtfCtrlWordHandler(rtfParser, "pnlvlblt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnlvlbody",
                new RtfCtrlWordHandler(rtfParser, "pnlvlbody", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnlvlcont",
                new RtfCtrlWordHandler(rtfParser, "pnlvlcont", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnnumonce",
                new RtfCtrlWordHandler(rtfParser, "pnnumonce", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnord", new RtfCtrlWordHandler(rtfParser, "pnord", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnordt", new RtfCtrlWordHandler(rtfParser, "pnordt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnprev", new RtfCtrlWordHandler(rtfParser, "pnprev", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnqc", new RtfCtrlWordHandler(rtfParser, "pnqc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnql", new RtfCtrlWordHandler(rtfParser, "pnql", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnqr", new RtfCtrlWordHandler(rtfParser, "pnqr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnrauth",
                new RtfCtrlWordHandler(rtfParser, "pnrauth", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pnrdate",
                new RtfCtrlWordHandler(rtfParser, "pnrdate", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pnrestart",
                new RtfCtrlWordHandler(rtfParser, "pnrestart", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnrnfc", new RtfCtrlWordHandler(rtfParser, "pnrnfc", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pnrnot", new RtfCtrlWordHandler(rtfParser, "pnrnot", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnrpnbr",
                new RtfCtrlWordHandler(rtfParser, "pnrpnbr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pnrrgb", new RtfCtrlWordHandler(rtfParser, "pnrrgb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pnrstart",
                new RtfCtrlWordHandler(rtfParser, "pnrstart", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pnrstop",
                new RtfCtrlWordHandler(rtfParser, "pnrstop", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pnrxst", new RtfCtrlWordHandler(rtfParser, "pnrxst", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pnscaps",
                new RtfCtrlWordHandler(rtfParser, "pnscaps", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "pnseclvl",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "pnseclvl",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "pnsp", new RtfCtrlWordHandler(rtfParser, "pnsp", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pnstart",
                new RtfCtrlWordHandler(rtfParser, "pnstart", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "pnstrike",
                new RtfCtrlWordHandler(rtfParser, "pnstrike", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "pntext",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "pntext",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "pntxta",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "pntxta",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "pntxtb",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "pntxtb",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "pnucltr",
                new RtfCtrlWordHandler(rtfParser, "pnucltr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnucrm", new RtfCtrlWordHandler(rtfParser, "pnucrm", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnul", new RtfCtrlWordHandler(rtfParser, "pnul", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "pnuld", new RtfCtrlWordHandler(rtfParser, "pnuld", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnuldash",
                new RtfCtrlWordHandler(rtfParser, "pnuldash", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnuldashd",
                new RtfCtrlWordHandler(rtfParser, "pnuldashd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnuldashdd",
                new RtfCtrlWordHandler(rtfParser, "pnuldashdd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnuldb", new RtfCtrlWordHandler(rtfParser, "pnuldb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnulhair",
                new RtfCtrlWordHandler(rtfParser, "pnulhair", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnulnone",
                new RtfCtrlWordHandler(rtfParser, "pnulnone", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnulth", new RtfCtrlWordHandler(rtfParser, "pnulth", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnulw", new RtfCtrlWordHandler(rtfParser, "pnulw", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnulwave",
                new RtfCtrlWordHandler(rtfParser, "pnulwave", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnzodiac",
                new RtfCtrlWordHandler(rtfParser, "pnzodiac", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnzodiacd",
                new RtfCtrlWordHandler(rtfParser, "pnzodiacd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pnzodiacl",
                new RtfCtrlWordHandler(rtfParser, "pnzodiacl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "posnegx",
                new RtfCtrlWordHandler(rtfParser, "posnegx", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "posnegy",
                new RtfCtrlWordHandler(rtfParser, "posnegy", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "posx", new RtfCtrlWordHandler(rtfParser, "posx", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "posxc", new RtfCtrlWordHandler(rtfParser, "posxc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "posxi", new RtfCtrlWordHandler(rtfParser, "posxi", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "posxl", new RtfCtrlWordHandler(rtfParser, "posxl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "posxo", new RtfCtrlWordHandler(rtfParser, "posxo", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "posxr", new RtfCtrlWordHandler(rtfParser, "posxr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "posy", new RtfCtrlWordHandler(rtfParser, "posy", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "posyb", new RtfCtrlWordHandler(rtfParser, "posyb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "posyc", new RtfCtrlWordHandler(rtfParser, "posyc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "posyil", new RtfCtrlWordHandler(rtfParser, "posyil", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "posyin", new RtfCtrlWordHandler(rtfParser, "posyin", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "posyout",
                new RtfCtrlWordHandler(rtfParser, "posyout", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "posyt", new RtfCtrlWordHandler(rtfParser, "posyt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "prcolbl",
                new RtfCtrlWordHandler(rtfParser, "prcolbl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "printdata",
                new RtfCtrlWordHandler(rtfParser, "printdata", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "printim",
                new RtfCtrlWordHandler(
                        rtfParser, "printim", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationNull"));
        ctrlWords.put(
                "private",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "private",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "propname",
                new RtfCtrlWordHandler(rtfParser, "propname", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "proptype",
                new RtfCtrlWordHandler(rtfParser, "proptype", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "protend",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "protend",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "protlevel",
                new RtfCtrlWordHandler(rtfParser, "protlevel", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "protstart",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "protstart",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "protusertbl",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "protusertbl",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "psover", new RtfCtrlWordHandler(rtfParser, "psover", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("psz", new RtfCtrlWordHandler(rtfParser, "psz", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ptabldot",
                new RtfCtrlWordHandler(rtfParser, "ptabldot", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ptablmdot",
                new RtfCtrlWordHandler(rtfParser, "ptablmdot", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ptablminus",
                new RtfCtrlWordHandler(rtfParser, "ptablminus", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ptablnone",
                new RtfCtrlWordHandler(rtfParser, "ptablnone", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ptabluscore",
                new RtfCtrlWordHandler(rtfParser, "ptabluscore", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pubauto",
                new RtfCtrlWordHandler(rtfParser, "pubauto", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pvmrg", new RtfCtrlWordHandler(rtfParser, "pvmrg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pvpara", new RtfCtrlWordHandler(rtfParser, "pvpara", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pvpg", new RtfCtrlWordHandler(rtfParser, "pvpg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "pwd",
                new RtfCtrlWordHandler(
                        rtfParser, "pwd", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "pxe",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "pxe",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put("qc", new RtfCtrlWordHandler(rtfParser, "qc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("qd", new RtfCtrlWordHandler(rtfParser, "qd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("qj", new RtfCtrlWordHandler(rtfParser, "qj", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("qk", new RtfCtrlWordHandler(rtfParser, "qk", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("ql", new RtfCtrlWordHandler(rtfParser, "ql", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "qmspace",
                new RtfCtrlWordHandler(rtfParser, "qmspace", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put("qr", new RtfCtrlWordHandler(rtfParser, "qr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("qt", new RtfCtrlWordHandler(rtfParser, "qt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rawbgdkbdiag",
                new RtfCtrlWordHandler(rtfParser, "rawbgdkbdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rawclbgbdiag",
                new RtfCtrlWordHandler(rtfParser, "rawclbgbdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rawclbgcross",
                new RtfCtrlWordHandler(rtfParser, "rawclbgcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rawclbgdcross",
                new RtfCtrlWordHandler(rtfParser, "rawclbgdcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rawclbgdkbdiag",
                new RtfCtrlWordHandler(rtfParser, "rawclbgdkbdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rawclbgdkcross",
                new RtfCtrlWordHandler(rtfParser, "rawclbgdkcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rawclbgdkdcross",
                new RtfCtrlWordHandler(rtfParser, "rawclbgdkdcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rawclbgdkfdiag",
                new RtfCtrlWordHandler(rtfParser, "rawclbgdkfdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rawclbgdkhor",
                new RtfCtrlWordHandler(rtfParser, "rawclbgdkhor", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rawclbgdkvert",
                new RtfCtrlWordHandler(rtfParser, "rawclbgdkvert", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rawclbgfdiag",
                new RtfCtrlWordHandler(rtfParser, "rawclbgfdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rawclbghoriz",
                new RtfCtrlWordHandler(rtfParser, "rawclbghoriz", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rawclbgvert",
                new RtfCtrlWordHandler(rtfParser, "rawclbgvert", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rdblquote",
                new RtfCtrlWordHandler(rtfParser, "rdblquote", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", "\0x148"));
        ctrlWords.put(
                "readonlyrecommended",
                new RtfCtrlWordHandler(
                        rtfParser, "readonlyrecommended", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "readprot",
                new RtfCtrlWordHandler(rtfParser, "readprot", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("red", new RtfCtrlWordHandler(rtfParser, "red", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "relyonvml",
                new RtfCtrlWordHandler(rtfParser, "relyonvml", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rempersonalinfo",
                new RtfCtrlWordHandler(rtfParser, "rempersonalinfo", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "result",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "result",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "revauth",
                new RtfCtrlWordHandler(rtfParser, "revauth", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "revauthdel",
                new RtfCtrlWordHandler(rtfParser, "revauthdel", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "revbar", new RtfCtrlWordHandler(rtfParser, "revbar", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "revdttm",
                new RtfCtrlWordHandler(rtfParser, "revdttm", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "revdttmdel",
                new RtfCtrlWordHandler(rtfParser, "revdttmdel", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "revised",
                new RtfCtrlWordHandler(rtfParser, "revised", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "revisions",
                new RtfCtrlWordHandler(rtfParser, "revisions", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "revprop",
                new RtfCtrlWordHandler(rtfParser, "revprop", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "revprot",
                new RtfCtrlWordHandler(rtfParser, "revprot", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "revtbl",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "revtbl",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationNull"));
        ctrlWords.put(
                "revtim",
                new RtfCtrlWordHandler(
                        rtfParser, "revtim", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationNull"));
        ctrlWords.put("ri", new RtfCtrlWordHandler(rtfParser, "ri", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("rin", new RtfCtrlWordHandler(rtfParser, "rin", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "row", new RtfCtrlWordHandler(rtfParser, "row", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "rquote",
                new RtfCtrlWordHandler(rtfParser, "rquote", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", "\0x146"));
        ctrlWords.put(
                "rsid", new RtfCtrlWordHandler(rtfParser, "rsid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "rsidroot",
                new RtfCtrlWordHandler(rtfParser, "rsidroot", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "rsidtbl",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "rsidtbl",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationNull"));
        ctrlWords.put(
                "rsltbmp",
                new RtfCtrlWordHandler(rtfParser, "rsltbmp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rslthtml",
                new RtfCtrlWordHandler(rtfParser, "rslthtml", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rsltmerge",
                new RtfCtrlWordHandler(rtfParser, "rsltmerge", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rsltpict",
                new RtfCtrlWordHandler(rtfParser, "rsltpict", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rsltrtf",
                new RtfCtrlWordHandler(rtfParser, "rsltrtf", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rslttxt",
                new RtfCtrlWordHandler(rtfParser, "rslttxt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rtf",
                new RtfCtrlWordHandler(
                        rtfParser, "rtf", 1, true, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "rtlch", new RtfCtrlWordHandler(rtfParser, "rtlch", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rtldoc", new RtfCtrlWordHandler(rtfParser, "rtldoc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rtlgutter",
                new RtfCtrlWordHandler(rtfParser, "rtlgutter", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rtlmark",
                new RtfCtrlWordHandler(rtfParser, "rtlmark", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "rtlpar", new RtfCtrlWordHandler(rtfParser, "rtlpar", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rtlrow", new RtfCtrlWordHandler(rtfParser, "rtlrow", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rtlsect",
                new RtfCtrlWordHandler(rtfParser, "rtlsect", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "rxe",
                new RtfCtrlWordHandler(
                        rtfParser, "rxe", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put("s", new RtfCtrlWordHandler(rtfParser, "s", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("sa", new RtfCtrlWordHandler(rtfParser, "sa", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "saauto",
                new RtfCtrlWordHandler(rtfParser, "saauto", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "saftnnalc",
                new RtfCtrlWordHandler(rtfParser, "saftnnalc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnnar",
                new RtfCtrlWordHandler(rtfParser, "saftnnar", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnnauc",
                new RtfCtrlWordHandler(rtfParser, "saftnnauc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnnchi",
                new RtfCtrlWordHandler(rtfParser, "saftnnchi", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnnchosung",
                new RtfCtrlWordHandler(rtfParser, "saftnnchosung", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnncnum",
                new RtfCtrlWordHandler(rtfParser, "saftnncnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnndbar",
                new RtfCtrlWordHandler(rtfParser, "saftnndbar", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnndbnum",
                new RtfCtrlWordHandler(rtfParser, "saftnndbnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnndbnumd",
                new RtfCtrlWordHandler(rtfParser, "saftnndbnumd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnndbnumk",
                new RtfCtrlWordHandler(rtfParser, "saftnndbnumk", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnndbnumt",
                new RtfCtrlWordHandler(rtfParser, "saftnndbnumt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnnganada",
                new RtfCtrlWordHandler(rtfParser, "saftnnganada", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnngbnum",
                new RtfCtrlWordHandler(rtfParser, "saftnngbnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnngbnumd",
                new RtfCtrlWordHandler(rtfParser, "saftnngbnumd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnngbnumk",
                new RtfCtrlWordHandler(rtfParser, "saftnngbnumk", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnngbnuml",
                new RtfCtrlWordHandler(rtfParser, "saftnngbnuml", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnnrlc",
                new RtfCtrlWordHandler(rtfParser, "saftnnrlc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnnruc",
                new RtfCtrlWordHandler(rtfParser, "saftnnruc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnnzodiac",
                new RtfCtrlWordHandler(rtfParser, "saftnnzodiac", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnnzodiacd",
                new RtfCtrlWordHandler(rtfParser, "saftnnzodiacd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnnzodiacl",
                new RtfCtrlWordHandler(rtfParser, "saftnnzodiacl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnrestart",
                new RtfCtrlWordHandler(rtfParser, "saftnrestart", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnrstcont",
                new RtfCtrlWordHandler(rtfParser, "saftnrstcont", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saftnstart",
                new RtfCtrlWordHandler(rtfParser, "saftnstart", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sautoupd",
                new RtfCtrlWordHandler(rtfParser, "sautoupd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saveinvalidxml",
                new RtfCtrlWordHandler(rtfParser, "saveinvalidxml", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "saveprevpict",
                new RtfCtrlWordHandler(rtfParser, "saveprevpict", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("sb", new RtfCtrlWordHandler(rtfParser, "sb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "sbasedon",
                new RtfCtrlWordHandler(rtfParser, "sbasedon", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "sbauto",
                new RtfCtrlWordHandler(rtfParser, "sbauto", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "sbkcol",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "sbkcol",
                        RtfProperty.SBK_COLUMN,
                        false,
                        RtfCtrlWordType.FLAG,
                        "\\",
                        " ",
                        RtfProperty.SECTION_BREAK_TYPE));
        ctrlWords.put(
                "sbkeven",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "sbkeven",
                        RtfProperty.SBK_EVEN,
                        false,
                        RtfCtrlWordType.FLAG,
                        "\\",
                        " ",
                        RtfProperty.SECTION_BREAK_TYPE));
        ctrlWords.put(
                "sbknone",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "sbknone",
                        RtfProperty.SBK_NONE,
                        false,
                        RtfCtrlWordType.FLAG,
                        "\\",
                        " ",
                        RtfProperty.SECTION_BREAK_TYPE));
        ctrlWords.put(
                "sbkodd",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "sbkodd",
                        RtfProperty.SBK_ODD,
                        false,
                        RtfCtrlWordType.FLAG,
                        "\\",
                        " ",
                        RtfProperty.SECTION_BREAK_TYPE));
        ctrlWords.put(
                "sbkpage",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "sbkpage",
                        RtfProperty.SBK_PAGE,
                        false,
                        RtfCtrlWordType.FLAG,
                        "\\",
                        " ",
                        RtfProperty.SECTION_BREAK_TYPE));
        ctrlWords.put(
                "sbys", new RtfCtrlWordHandler(rtfParser, "sbys", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "scaps", new RtfCtrlWordHandler(rtfParser, "scaps", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "scompose",
                new RtfCtrlWordHandler(rtfParser, "scompose", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("sec", new RtfCtrlWordHandler(rtfParser, "sec", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "sect", new RtfCtrlWordHandler(rtfParser, "sect", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "sectd", new RtfCtrlWordHandler(rtfParser, "sectd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sectdefaultcl",
                new RtfCtrlWordHandler(rtfParser, "sectdefaultcl", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "sectexpand",
                new RtfCtrlWordHandler(rtfParser, "sectexpand", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "sectlinegrid",
                new RtfCtrlWordHandler(rtfParser, "sectlinegrid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "sectnum",
                new RtfCtrlWordHandler(rtfParser, "sectnum", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "sectrsid",
                new RtfCtrlWordHandler(rtfParser, "sectrsid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "sectspecifycl",
                new RtfCtrlWordHandler(rtfParser, "sectspecifycl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "sectspecifygen",
                new RtfCtrlWordHandler(rtfParser, "sectspecifygen", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "sectspecifyl",
                new RtfCtrlWordHandler(rtfParser, "sectspecifyl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "sectunlocked",
                new RtfCtrlWordHandler(rtfParser, "sectunlocked", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnbj", new RtfCtrlWordHandler(rtfParser, "sftnbj", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnnalc",
                new RtfCtrlWordHandler(rtfParser, "sftnnalc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnnar",
                new RtfCtrlWordHandler(rtfParser, "sftnnar", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnnauc",
                new RtfCtrlWordHandler(rtfParser, "sftnnauc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnnchi",
                new RtfCtrlWordHandler(rtfParser, "sftnnchi", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnnchosung",
                new RtfCtrlWordHandler(rtfParser, "sftnnchosung", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnncnum",
                new RtfCtrlWordHandler(rtfParser, "sftnncnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnndbar",
                new RtfCtrlWordHandler(rtfParser, "sftnndbar", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnndbnum",
                new RtfCtrlWordHandler(rtfParser, "sftnndbnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnndbnumd",
                new RtfCtrlWordHandler(rtfParser, "sftnndbnumd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnndbnumk",
                new RtfCtrlWordHandler(rtfParser, "sftnndbnumk", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnndbnumt",
                new RtfCtrlWordHandler(rtfParser, "sftnndbnumt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnnganada",
                new RtfCtrlWordHandler(rtfParser, "sftnnganada", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnngbnum",
                new RtfCtrlWordHandler(rtfParser, "sftnngbnum", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnngbnumd",
                new RtfCtrlWordHandler(rtfParser, "sftnngbnumd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnngbnumk",
                new RtfCtrlWordHandler(rtfParser, "sftnngbnumk", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnngbnuml",
                new RtfCtrlWordHandler(rtfParser, "sftnngbnuml", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnnrlc",
                new RtfCtrlWordHandler(rtfParser, "sftnnrlc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnnruc",
                new RtfCtrlWordHandler(rtfParser, "sftnnruc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnnzodiac",
                new RtfCtrlWordHandler(rtfParser, "sftnnzodiac", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnnzodiacd",
                new RtfCtrlWordHandler(rtfParser, "sftnnzodiacd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnnzodiacl",
                new RtfCtrlWordHandler(rtfParser, "sftnnzodiacl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnrestart",
                new RtfCtrlWordHandler(rtfParser, "sftnrestart", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnrstcont",
                new RtfCtrlWordHandler(rtfParser, "sftnrstcont", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnrstpg",
                new RtfCtrlWordHandler(rtfParser, "sftnrstpg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftnstart",
                new RtfCtrlWordHandler(rtfParser, "sftnstart", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sftntj", new RtfCtrlWordHandler(rtfParser, "sftntj", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "shad", new RtfCtrlWordHandler(rtfParser, "shad", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "shading",
                new RtfCtrlWordHandler(rtfParser, "shading", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "shidden",
                new RtfCtrlWordHandler(rtfParser, "shidden", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "shift", new RtfCtrlWordHandler(rtfParser, "shift", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "showplaceholdtext",
                new RtfCtrlWordHandler(
                        rtfParser, "showplaceholdtext", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "showxmlerrors",
                new RtfCtrlWordHandler(rtfParser, "showxmlerrors", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "shp",
                new RtfCtrlWordHandler(
                        rtfParser, "shp", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "shpbottom",
                new RtfCtrlWordHandler(rtfParser, "shpbottom", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "shpbxcolumn",
                new RtfCtrlWordHandler(rtfParser, "shpbxcolumn", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "shpbxignore",
                new RtfCtrlWordHandler(rtfParser, "shpbxignore", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "shpbxmargin",
                new RtfCtrlWordHandler(rtfParser, "shpbxmargin", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "shpbxpage",
                new RtfCtrlWordHandler(rtfParser, "shpbxpage", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "shpbyignore",
                new RtfCtrlWordHandler(rtfParser, "shpbyignore", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "shpbymargin",
                new RtfCtrlWordHandler(rtfParser, "shpbymargin", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "shpbypage",
                new RtfCtrlWordHandler(rtfParser, "shpbypage", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "shpbypara",
                new RtfCtrlWordHandler(rtfParser, "shpbypara", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "shpfblwtxt",
                new RtfCtrlWordHandler(rtfParser, "shpfblwtxt", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "shpfhdr",
                new RtfCtrlWordHandler(rtfParser, "shpfhdr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "shpgrp", new RtfCtrlWordHandler(rtfParser, "shpgrp", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "shpinst",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "shpinst",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "shpleft",
                new RtfCtrlWordHandler(rtfParser, "shpleft", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "shplid", new RtfCtrlWordHandler(rtfParser, "shplid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "shplockanchor",
                new RtfCtrlWordHandler(rtfParser, "shplockanchor", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "shppict",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "shppict",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationShppict")); // "RtfDestinationShppict"));
        ctrlWords.put(
                "shpright",
                new RtfCtrlWordHandler(rtfParser, "shpright", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "shprslt",
                new RtfCtrlWordHandler(
                        rtfParser, "shprslt", 0, true, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationNull"));
        ctrlWords.put(
                "shptop", new RtfCtrlWordHandler(rtfParser, "shptop", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "shptxt", new RtfCtrlWordHandler(rtfParser, "shptxt", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "shpwr", new RtfCtrlWordHandler(rtfParser, "shpwr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "shpwrk", new RtfCtrlWordHandler(rtfParser, "shpwrk", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "shpz", new RtfCtrlWordHandler(rtfParser, "shpz", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("sl", new RtfCtrlWordHandler(rtfParser, "sl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "slink", new RtfCtrlWordHandler(rtfParser, "slink", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "slmult", new RtfCtrlWordHandler(rtfParser, "slmult", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "slocked",
                new RtfCtrlWordHandler(rtfParser, "slocked", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("sn", new RtfCtrlWordHandler(rtfParser, "sn", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "snapgridtocell",
                new RtfCtrlWordHandler(rtfParser, "snapgridtocell", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "snaptogridincell",
                new RtfCtrlWordHandler(rtfParser, "snaptogridincell", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "snext", new RtfCtrlWordHandler(rtfParser, "snext", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "softcol",
                new RtfCtrlWordHandler(rtfParser, "softcol", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "softlheight",
                new RtfCtrlWordHandler(rtfParser, "softlheight", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "softline",
                new RtfCtrlWordHandler(rtfParser, "softline", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "softpage",
                new RtfCtrlWordHandler(rtfParser, "softpage", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("sp", new RtfCtrlWordHandler(rtfParser, "sp", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "spersonal",
                new RtfCtrlWordHandler(rtfParser, "spersonal", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "spltpgpar",
                new RtfCtrlWordHandler(rtfParser, "spltpgpar", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "splytwnine",
                new RtfCtrlWordHandler(rtfParser, "splytwnine", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "spp", new RtfCtrlWordHandler(rtfParser, "spp", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "spriority",
                new RtfCtrlWordHandler(rtfParser, "spriority", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "sprsbsp",
                new RtfCtrlWordHandler(rtfParser, "sprsbsp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sprslnsp",
                new RtfCtrlWordHandler(rtfParser, "sprslnsp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sprsspbf",
                new RtfCtrlWordHandler(rtfParser, "sprsspbf", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sprstsm",
                new RtfCtrlWordHandler(rtfParser, "sprstsm", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sprstsp",
                new RtfCtrlWordHandler(rtfParser, "sprstsp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("spv", new RtfCtrlWordHandler(rtfParser, "spv", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sqformat",
                new RtfCtrlWordHandler(rtfParser, "sqformat", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "sreply", new RtfCtrlWordHandler(rtfParser, "sreply", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ssemihidden",
                new RtfCtrlWordHandler(rtfParser, "ssemihidden", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "staticval",
                new RtfCtrlWordHandler(rtfParser, "staticval", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "stextflow",
                new RtfCtrlWordHandler(rtfParser, "stextflow", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "strike",
                new RtfCtrlWordHandler(rtfParser, "strike", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "striked1",
                new RtfCtrlWordHandler(rtfParser, "striked1", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "stshfbi",
                new RtfCtrlWordHandler(rtfParser, "stshfbi", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "stshfdbch",
                new RtfCtrlWordHandler(rtfParser, "stshfdbch", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "stshfhich",
                new RtfCtrlWordHandler(rtfParser, "stshfhich", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "stshfloch",
                new RtfCtrlWordHandler(rtfParser, "stshfloch", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "stylelock",
                new RtfCtrlWordHandler(rtfParser, "stylelock", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "stylelockbackcomp",
                new RtfCtrlWordHandler(
                        rtfParser, "stylelockbackcomp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "stylelockenforced",
                new RtfCtrlWordHandler(
                        rtfParser, "stylelockenforced", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "stylelockqfset",
                new RtfCtrlWordHandler(rtfParser, "stylelockqfset", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "stylelocktheme",
                new RtfCtrlWordHandler(rtfParser, "stylelocktheme", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "stylesheet",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "stylesheet",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationStylesheetTable"));
        ctrlWords.put(
                "stylesortmethod",
                new RtfCtrlWordHandler(rtfParser, "stylesortmethod", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "styrsid",
                new RtfCtrlWordHandler(rtfParser, "styrsid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("sub", new RtfCtrlWordHandler(rtfParser, "sub", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "subdocument",
                new RtfCtrlWordHandler(rtfParser, "subdocument", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "subfontbysize",
                new RtfCtrlWordHandler(rtfParser, "subfontbysize", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "subject",
                new RtfCtrlWordHandler(
                        rtfParser, "subject", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationInfo"));
        ctrlWords.put(
                "sunhideused",
                new RtfCtrlWordHandler(rtfParser, "sunhideused", 0, true, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "super", new RtfCtrlWordHandler(rtfParser, "super", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("sv", new RtfCtrlWordHandler(rtfParser, "sv", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "svb",
                new RtfCtrlWordHandler(
                        rtfParser, "svb", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "swpbdr", new RtfCtrlWordHandler(rtfParser, "swpbdr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tab", new RtfCtrlWordHandler(rtfParser, "tab", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "tabsnoovrlp",
                new RtfCtrlWordHandler(rtfParser, "tabsnoovrlp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "taprtl", new RtfCtrlWordHandler(rtfParser, "taprtl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("tb", new RtfCtrlWordHandler(rtfParser, "tb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tblind", new RtfCtrlWordHandler(rtfParser, "tblind", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tblindtype",
                new RtfCtrlWordHandler(rtfParser, "tblindtype", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tbllkbestfit",
                new RtfCtrlWordHandler(rtfParser, "tbllkbestfit", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tbllkborder",
                new RtfCtrlWordHandler(rtfParser, "tbllkborder", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tbllkcolor",
                new RtfCtrlWordHandler(rtfParser, "tbllkcolor", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tbllkfont",
                new RtfCtrlWordHandler(rtfParser, "tbllkfont", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tbllkhdrcols",
                new RtfCtrlWordHandler(rtfParser, "tbllkhdrcols", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tbllkhdrrows",
                new RtfCtrlWordHandler(rtfParser, "tbllkhdrrows", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tbllklastcol",
                new RtfCtrlWordHandler(rtfParser, "tbllklastcol", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tbllklastrow",
                new RtfCtrlWordHandler(rtfParser, "tbllklastrow", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tbllknocolband",
                new RtfCtrlWordHandler(rtfParser, "tbllknocolband", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tbllknorowband",
                new RtfCtrlWordHandler(rtfParser, "tbllknorowband", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tbllkshading",
                new RtfCtrlWordHandler(rtfParser, "tbllkshading", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tblrsid",
                new RtfCtrlWordHandler(rtfParser, "tblrsid", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tc",
                new RtfCtrlWordHandler(
                        rtfParser, "tc", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "tcelld", new RtfCtrlWordHandler(rtfParser, "tcelld", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("tcf", new RtfCtrlWordHandler(rtfParser, "tcf", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("tcl", new RtfCtrlWordHandler(rtfParser, "tcl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("tcn", new RtfCtrlWordHandler(rtfParser, "tcn", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tdfrmtxtBottom",
                new RtfCtrlWordHandler(rtfParser, "tdfrmtxtBottom", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tdfrmtxtLeft",
                new RtfCtrlWordHandler(rtfParser, "tdfrmtxtLeft", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tdfrmtxtRight",
                new RtfCtrlWordHandler(rtfParser, "tdfrmtxtRight", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tdfrmtxtTop",
                new RtfCtrlWordHandler(rtfParser, "tdfrmtxtTop", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "template",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "template",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "themedata",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "themedata",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "themelang",
                new RtfCtrlWordHandler(rtfParser, "themelang", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "themelangcs",
                new RtfCtrlWordHandler(rtfParser, "themelangcs", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "themelangfe",
                new RtfCtrlWordHandler(rtfParser, "themelangfe", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "time", new RtfCtrlWordHandler(rtfParser, "time", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "title",
                new RtfCtrlWordHandler(
                        rtfParser, "title", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationInfo"));
        ctrlWords.put(
                "titlepg",
                new RtfCtrlWordHandler(rtfParser, "titlepg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tldot", new RtfCtrlWordHandler(rtfParser, "tldot", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tleq", new RtfCtrlWordHandler(rtfParser, "tleq", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tlhyph", new RtfCtrlWordHandler(rtfParser, "tlhyph", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tlmdot", new RtfCtrlWordHandler(rtfParser, "tlmdot", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tlth", new RtfCtrlWordHandler(rtfParser, "tlth", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tlul", new RtfCtrlWordHandler(rtfParser, "tlul", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "toplinepunct",
                new RtfCtrlWordHandler(rtfParser, "toplinepunct", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tphcol", new RtfCtrlWordHandler(rtfParser, "tphcol", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tphmrg", new RtfCtrlWordHandler(rtfParser, "tphmrg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tphpg", new RtfCtrlWordHandler(rtfParser, "tphpg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tposnegx",
                new RtfCtrlWordHandler(rtfParser, "tposnegx", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tposnegy",
                new RtfCtrlWordHandler(rtfParser, "tposnegy", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tposx", new RtfCtrlWordHandler(rtfParser, "tposx", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tposxc", new RtfCtrlWordHandler(rtfParser, "tposxc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tposxi", new RtfCtrlWordHandler(rtfParser, "tposxi", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tposxl", new RtfCtrlWordHandler(rtfParser, "tposxl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tposxo", new RtfCtrlWordHandler(rtfParser, "tposxo", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tposxr", new RtfCtrlWordHandler(rtfParser, "tposxr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tposy", new RtfCtrlWordHandler(rtfParser, "tposy", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tposyb", new RtfCtrlWordHandler(rtfParser, "tposyb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tposyc", new RtfCtrlWordHandler(rtfParser, "tposyc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tposyil",
                new RtfCtrlWordHandler(rtfParser, "tposyil", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tposyin",
                new RtfCtrlWordHandler(rtfParser, "tposyin", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tposyout",
                new RtfCtrlWordHandler(rtfParser, "tposyout", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tposyoutv",
                new RtfCtrlWordHandler(rtfParser, "tposyoutv", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tposyt", new RtfCtrlWordHandler(rtfParser, "tposyt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tpvmrg", new RtfCtrlWordHandler(rtfParser, "tpvmrg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tpvpara",
                new RtfCtrlWordHandler(rtfParser, "tpvpara", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tpvpg", new RtfCtrlWordHandler(rtfParser, "tpvpg", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("tqc", new RtfCtrlWordHandler(rtfParser, "tqc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tqdec", new RtfCtrlWordHandler(rtfParser, "tqdec", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("tqr", new RtfCtrlWordHandler(rtfParser, "tqr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trackformatting",
                new RtfCtrlWordHandler(rtfParser, "trackformatting", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trackmoves",
                new RtfCtrlWordHandler(rtfParser, "trackmoves", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "transmf",
                new RtfCtrlWordHandler(rtfParser, "transmf", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trauth", new RtfCtrlWordHandler(rtfParser, "trauth", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trautofit",
                new RtfCtrlWordHandler(rtfParser, "trautofit", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "trbgbdiag",
                new RtfCtrlWordHandler(rtfParser, "trbgbdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbgcross",
                new RtfCtrlWordHandler(rtfParser, "trbgcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbgdcross",
                new RtfCtrlWordHandler(rtfParser, "trbgdcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbgdkbdiag",
                new RtfCtrlWordHandler(rtfParser, "trbgdkbdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbgdkcross",
                new RtfCtrlWordHandler(rtfParser, "trbgdkcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbgdkdcross",
                new RtfCtrlWordHandler(rtfParser, "trbgdkdcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbgdkfdiag",
                new RtfCtrlWordHandler(rtfParser, "trbgdkfdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbgdkhor",
                new RtfCtrlWordHandler(rtfParser, "trbgdkhor", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbgdkvert",
                new RtfCtrlWordHandler(rtfParser, "trbgdkvert", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbgfdiag",
                new RtfCtrlWordHandler(rtfParser, "trbgfdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbghoriz",
                new RtfCtrlWordHandler(rtfParser, "trbghoriz", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbgvert",
                new RtfCtrlWordHandler(rtfParser, "trbgvert", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbrdrb",
                new RtfCtrlWordHandler(rtfParser, "trbrdrb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbrdrh",
                new RtfCtrlWordHandler(rtfParser, "trbrdrh", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbrdrl",
                new RtfCtrlWordHandler(rtfParser, "trbrdrl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbrdrr",
                new RtfCtrlWordHandler(rtfParser, "trbrdrr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbrdrt",
                new RtfCtrlWordHandler(rtfParser, "trbrdrt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trbrdrv",
                new RtfCtrlWordHandler(rtfParser, "trbrdrv", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trcbpat",
                new RtfCtrlWordHandler(rtfParser, "trcbpat", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trcfpat",
                new RtfCtrlWordHandler(rtfParser, "trcfpat", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trdate",
                new RtfCtrlWordHandler(rtfParser, "trdate", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trftsWidth",
                new RtfCtrlWordHandler(rtfParser, "trftsWidth", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trftsWidthA",
                new RtfCtrlWordHandler(rtfParser, "trftsWidthA", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trftsWidthB",
                new RtfCtrlWordHandler(rtfParser, "trftsWidthB", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trgaph", new RtfCtrlWordHandler(rtfParser, "trgaph", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trhdr", new RtfCtrlWordHandler(rtfParser, "trhdr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trkeep", new RtfCtrlWordHandler(rtfParser, "trkeep", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trkeepfollow",
                new RtfCtrlWordHandler(rtfParser, "trkeepfollow", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trleft", new RtfCtrlWordHandler(rtfParser, "trleft", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trowd", new RtfCtrlWordHandler(rtfParser, "trowd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trpaddb",
                new RtfCtrlWordHandler(rtfParser, "trpaddb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trpaddfb",
                new RtfCtrlWordHandler(rtfParser, "trpaddfb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trpaddfl",
                new RtfCtrlWordHandler(rtfParser, "trpaddfl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trpaddfr",
                new RtfCtrlWordHandler(rtfParser, "trpaddfr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trpaddft",
                new RtfCtrlWordHandler(rtfParser, "trpaddft", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trpaddl",
                new RtfCtrlWordHandler(rtfParser, "trpaddl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trpaddr",
                new RtfCtrlWordHandler(rtfParser, "trpaddr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trpaddt",
                new RtfCtrlWordHandler(rtfParser, "trpaddt", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trpat", new RtfCtrlWordHandler(rtfParser, "trpat", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trqc", new RtfCtrlWordHandler(rtfParser, "trqc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trql", new RtfCtrlWordHandler(rtfParser, "trql", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trqr", new RtfCtrlWordHandler(rtfParser, "trqr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trrh", new RtfCtrlWordHandler(rtfParser, "trrh", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trshdng",
                new RtfCtrlWordHandler(rtfParser, "trshdng", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trspdb", new RtfCtrlWordHandler(rtfParser, "trspdb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trspdfb",
                new RtfCtrlWordHandler(rtfParser, "trspdfb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trspdfl",
                new RtfCtrlWordHandler(rtfParser, "trspdfl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trspdfr",
                new RtfCtrlWordHandler(rtfParser, "trspdfr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trspdft",
                new RtfCtrlWordHandler(rtfParser, "trspdft", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trspdl", new RtfCtrlWordHandler(rtfParser, "trspdl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trspdr", new RtfCtrlWordHandler(rtfParser, "trspdr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trspdt", new RtfCtrlWordHandler(rtfParser, "trspdt", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "truncatefontheight",
                new RtfCtrlWordHandler(
                        rtfParser, "truncatefontheight", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "truncex",
                new RtfCtrlWordHandler(rtfParser, "truncex", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "trwWidth",
                new RtfCtrlWordHandler(rtfParser, "trwWidth", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trwWidthA",
                new RtfCtrlWordHandler(rtfParser, "trwWidthA", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "trwWidthB",
                new RtfCtrlWordHandler(rtfParser, "trwWidthB", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("ts", new RtfCtrlWordHandler(rtfParser, "ts", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tsbgbdiag",
                new RtfCtrlWordHandler(rtfParser, "tsbgbdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbgcross",
                new RtfCtrlWordHandler(rtfParser, "tsbgcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbgdcross",
                new RtfCtrlWordHandler(rtfParser, "tsbgdcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbgdkbdiag",
                new RtfCtrlWordHandler(rtfParser, "tsbgdkbdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbgdkcross",
                new RtfCtrlWordHandler(rtfParser, "tsbgdkcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbgdkdcross",
                new RtfCtrlWordHandler(rtfParser, "tsbgdkdcross", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbgdkfdiag",
                new RtfCtrlWordHandler(rtfParser, "tsbgdkfdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbgdkhor",
                new RtfCtrlWordHandler(rtfParser, "tsbgdkhor", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbgdkvert",
                new RtfCtrlWordHandler(rtfParser, "tsbgdkvert", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbgfdiag",
                new RtfCtrlWordHandler(rtfParser, "tsbgfdiag", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbghoriz",
                new RtfCtrlWordHandler(rtfParser, "tsbghoriz", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbgvert",
                new RtfCtrlWordHandler(rtfParser, "tsbgvert", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbrdrb",
                new RtfCtrlWordHandler(rtfParser, "tsbrdrb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbrdrdgl",
                new RtfCtrlWordHandler(rtfParser, "tsbrdrdgl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbrdrdgr",
                new RtfCtrlWordHandler(rtfParser, "tsbrdrdgr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbrdrh",
                new RtfCtrlWordHandler(rtfParser, "tsbrdrh", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbrdrl",
                new RtfCtrlWordHandler(rtfParser, "tsbrdrl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbrdrr",
                new RtfCtrlWordHandler(rtfParser, "tsbrdrr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbrdrt",
                new RtfCtrlWordHandler(rtfParser, "tsbrdrt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsbrdrv",
                new RtfCtrlWordHandler(rtfParser, "tsbrdrv", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tscbandhorzeven",
                new RtfCtrlWordHandler(rtfParser, "tscbandhorzeven", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tscbandhorzodd",
                new RtfCtrlWordHandler(rtfParser, "tscbandhorzodd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tscbandsh",
                new RtfCtrlWordHandler(rtfParser, "tscbandsh", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tscbandsv",
                new RtfCtrlWordHandler(rtfParser, "tscbandsv", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tscbandverteven",
                new RtfCtrlWordHandler(rtfParser, "tscbandverteven", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tscbandvertodd",
                new RtfCtrlWordHandler(rtfParser, "tscbandvertodd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tscellcbpat",
                new RtfCtrlWordHandler(rtfParser, "tscellcbpat", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tscellcfpat",
                new RtfCtrlWordHandler(rtfParser, "tscellcfpat", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tscellpaddb",
                new RtfCtrlWordHandler(rtfParser, "tscellpaddb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tscellpaddfb",
                new RtfCtrlWordHandler(rtfParser, "tscellpaddfb", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tscellpaddfl",
                new RtfCtrlWordHandler(rtfParser, "tscellpaddfl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tscellpaddfr",
                new RtfCtrlWordHandler(rtfParser, "tscellpaddfr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tscellpaddft",
                new RtfCtrlWordHandler(rtfParser, "tscellpaddft", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tscellpaddl",
                new RtfCtrlWordHandler(rtfParser, "tscellpaddl", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tscellpaddr",
                new RtfCtrlWordHandler(rtfParser, "tscellpaddr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tscellpaddt",
                new RtfCtrlWordHandler(rtfParser, "tscellpaddt", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tscellpct",
                new RtfCtrlWordHandler(rtfParser, "tscellpct", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "tscellwidth",
                new RtfCtrlWordHandler(rtfParser, "tscellwidth", 0, true, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tscellwidthfts",
                new RtfCtrlWordHandler(rtfParser, "tscellwidthfts", 0, true, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tscfirstcol",
                new RtfCtrlWordHandler(rtfParser, "tscfirstcol", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tscfirstrow",
                new RtfCtrlWordHandler(rtfParser, "tscfirstrow", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsclastcol",
                new RtfCtrlWordHandler(rtfParser, "tsclastcol", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsclastrow",
                new RtfCtrlWordHandler(rtfParser, "tsclastrow", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tscnecell",
                new RtfCtrlWordHandler(rtfParser, "tscnecell", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tscnwcell",
                new RtfCtrlWordHandler(rtfParser, "tscnwcell", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tscsecell",
                new RtfCtrlWordHandler(rtfParser, "tscsecell", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tscswcell",
                new RtfCtrlWordHandler(rtfParser, "tscswcell", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("tsd", new RtfCtrlWordHandler(rtfParser, "tsd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsnowrap",
                new RtfCtrlWordHandler(rtfParser, "tsnowrap", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsrowd", new RtfCtrlWordHandler(rtfParser, "tsrowd", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsvertalb",
                new RtfCtrlWordHandler(rtfParser, "tsvertalb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsvertalc",
                new RtfCtrlWordHandler(rtfParser, "tsvertalc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "tsvertalt",
                new RtfCtrlWordHandler(rtfParser, "tsvertalt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "twoonone",
                new RtfCtrlWordHandler(rtfParser, "twoonone", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("tx", new RtfCtrlWordHandler(rtfParser, "tx", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "txbxtwalways",
                new RtfCtrlWordHandler(rtfParser, "txbxtwalways", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "txbxtwfirst",
                new RtfCtrlWordHandler(rtfParser, "txbxtwfirst", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "txbxtwfirstlast",
                new RtfCtrlWordHandler(rtfParser, "txbxtwfirstlast", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "txbxtwlast",
                new RtfCtrlWordHandler(rtfParser, "txbxtwlast", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "txbxtwno",
                new RtfCtrlWordHandler(rtfParser, "txbxtwno", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "txe",
                new RtfCtrlWordHandler(
                        rtfParser, "txe", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put("u", new RtfCtrlWordHandler(rtfParser, "u", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("uc", new RtfCtrlWordHandler(rtfParser, "uc", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "ud",
                new RtfCtrlWordHandler(
                        rtfParser, "ud", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put("ul", new RtfCtrlWordHandler(rtfParser, "ul", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put("ulc", new RtfCtrlWordHandler(rtfParser, "ulc", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("uld", new RtfCtrlWordHandler(rtfParser, "uld", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "uldash",
                new RtfCtrlWordHandler(rtfParser, "uldash", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "uldashd",
                new RtfCtrlWordHandler(rtfParser, "uldashd", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "uldashdd",
                new RtfCtrlWordHandler(rtfParser, "uldashdd", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "uldb", new RtfCtrlWordHandler(rtfParser, "uldb", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "ulhair",
                new RtfCtrlWordHandler(rtfParser, "ulhair", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "ulhwave",
                new RtfCtrlWordHandler(rtfParser, "ulhwave", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "ulldash",
                new RtfCtrlWordHandler(rtfParser, "ulldash", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "ulnone", new RtfCtrlWordHandler(rtfParser, "ulnone", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ulth", new RtfCtrlWordHandler(rtfParser, "ulth", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "ulthd", new RtfCtrlWordHandler(rtfParser, "ulthd", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "ulthdash",
                new RtfCtrlWordHandler(rtfParser, "ulthdash", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "ulthdashd",
                new RtfCtrlWordHandler(rtfParser, "ulthdashd", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "ulthdashdd",
                new RtfCtrlWordHandler(rtfParser, "ulthdashdd", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "ulthldash",
                new RtfCtrlWordHandler(rtfParser, "ulthldash", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "ululdbwave",
                new RtfCtrlWordHandler(rtfParser, "ululdbwave", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put("ulw", new RtfCtrlWordHandler(rtfParser, "ulw", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "ulwave",
                new RtfCtrlWordHandler(rtfParser, "ulwave", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put("up", new RtfCtrlWordHandler(rtfParser, "up", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "upr",
                new RtfCtrlWordHandler(
                        rtfParser, "upr", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "urtf",
                new RtfCtrlWordHandler(
                        rtfParser, "urtf", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put(
                "useltbaln",
                new RtfCtrlWordHandler(rtfParser, "useltbaln", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "usenormstyforlist",
                new RtfCtrlWordHandler(
                        rtfParser, "usenormstyforlist", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "userprops",
                new RtfCtrlWordHandler(
                        rtfParser, "userprops", 0, false, RtfCtrlWordType.DESTINATION_EX, "\\*\\", " ", null));
        ctrlWords.put(
                "usexform",
                new RtfCtrlWordHandler(rtfParser, "usexform", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "utinl", new RtfCtrlWordHandler(rtfParser, "utinl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("v", new RtfCtrlWordHandler(rtfParser, "v", 0, false, RtfCtrlWordType.TOGGLE, "\\", " ", null));
        ctrlWords.put(
                "validatexml",
                new RtfCtrlWordHandler(rtfParser, "validatexml", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "vern", new RtfCtrlWordHandler(rtfParser, "vern", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "version",
                new RtfCtrlWordHandler(rtfParser, "version", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "vertalb",
                new RtfCtrlWordHandler(rtfParser, "vertalb", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "vertalc",
                new RtfCtrlWordHandler(rtfParser, "vertalc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "vertalj",
                new RtfCtrlWordHandler(rtfParser, "vertalj", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "vertalt",
                new RtfCtrlWordHandler(rtfParser, "vertalt", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "vertdoc",
                new RtfCtrlWordHandler(rtfParser, "vertdoc", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "vertsect",
                new RtfCtrlWordHandler(rtfParser, "vertsect", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "viewbksp",
                new RtfCtrlWordHandler(rtfParser, "viewbksp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "viewkind",
                new RtfCtrlWordHandler(rtfParser, "viewkind", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "viewnobound",
                new RtfCtrlWordHandler(rtfParser, "viewnobound", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "viewscale",
                new RtfCtrlWordHandler(rtfParser, "viewscale", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "viewzk", new RtfCtrlWordHandler(rtfParser, "viewzk", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "wbitmap",
                new RtfCtrlWordHandler(rtfParser, "wbitmap", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "wbmbitspixel",
                new RtfCtrlWordHandler(rtfParser, "wbmbitspixel", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "wbmplanes",
                new RtfCtrlWordHandler(rtfParser, "wbmplanes", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "wbmwidthbytes",
                new RtfCtrlWordHandler(rtfParser, "wbmwidthbytes", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "webhidden",
                new RtfCtrlWordHandler(rtfParser, "webhidden", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "wgrffmtfilter",
                new RtfCtrlWordHandler(
                        rtfParser, "wgrffmtfilter", 0, false, RtfCtrlWordType.VALUE, "\\*\\", " ", null));
        ctrlWords.put(
                "widctlpar",
                new RtfCtrlWordHandler(rtfParser, "widctlpar", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "widowctrl",
                new RtfCtrlWordHandler(rtfParser, "widowctrl", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "windowcaption",
                new RtfCtrlWordHandler(rtfParser, "windowcaption", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "wmetafile",
                new RtfCtrlWordHandler(rtfParser, "wmetafile", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "wpeqn", new RtfCtrlWordHandler(rtfParser, "wpeqn", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "wpjst", new RtfCtrlWordHandler(rtfParser, "wpjst", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "wpsp", new RtfCtrlWordHandler(rtfParser, "wpsp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "wptab", new RtfCtrlWordHandler(rtfParser, "wptab", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "wraparound",
                new RtfCtrlWordHandler(rtfParser, "wraparound", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "wrapdefault",
                new RtfCtrlWordHandler(rtfParser, "wrapdefault", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "wrapthrough",
                new RtfCtrlWordHandler(rtfParser, "wrapthrough", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "wraptight",
                new RtfCtrlWordHandler(rtfParser, "wraptight", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "wraptrsp",
                new RtfCtrlWordHandler(rtfParser, "wraptrsp", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "writereservhash",
                new RtfCtrlWordHandler(
                        rtfParser, "writereservhash", 0, false, RtfCtrlWordType.DESTINATION_EX, "\\*\\", " ", null));
        ctrlWords.put(
                "wrppunct",
                new RtfCtrlWordHandler(rtfParser, "wrppunct", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "xe",
                new RtfCtrlWordHandler(
                        rtfParser, "xe", 0, false, RtfCtrlWordType.DESTINATION, "\\", " ", "RtfDestinationDocument"));
        ctrlWords.put("xef", new RtfCtrlWordHandler(rtfParser, "xef", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "xform",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "xform",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "xmlattr",
                new RtfCtrlWordHandler(rtfParser, "xmlattr", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "xmlattrname",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "xmlattrname",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "xmlattrns",
                new RtfCtrlWordHandler(rtfParser, "xmlattrns", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "xmlattrvalue",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "xmlattrvalue",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "xmlclose",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "xmlclose",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "xmlname",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "xmlname",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION,
                        "\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "xmlns", new RtfCtrlWordHandler(rtfParser, "xmlns", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put(
                "xmlnstbl",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "xmlnstbl",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "xmlopen",
                new RtfCtrlWordHandler(
                        rtfParser,
                        "xmlopen",
                        0,
                        false,
                        RtfCtrlWordType.DESTINATION_EX,
                        "\\*\\",
                        " ",
                        "RtfDestinationDocument"));
        ctrlWords.put(
                "xmlsdttcell",
                new RtfCtrlWordHandler(rtfParser, "xmlsdttcell", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "xmlsdttpara",
                new RtfCtrlWordHandler(rtfParser, "xmlsdttpara", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "xmlsdttregular",
                new RtfCtrlWordHandler(rtfParser, "xmlsdttregular", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "xmlsdttrow",
                new RtfCtrlWordHandler(rtfParser, "xmlsdttrow", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "xmlsdttunknown",
                new RtfCtrlWordHandler(rtfParser, "xmlsdttunknown", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put("yr", new RtfCtrlWordHandler(rtfParser, "yr", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("yts", new RtfCtrlWordHandler(rtfParser, "yts", 0, true, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("yxe", new RtfCtrlWordHandler(rtfParser, "yxe", 0, false, RtfCtrlWordType.FLAG, "\\", " ", null));
        ctrlWords.put(
                "zwbo", new RtfCtrlWordHandler(rtfParser, "zwbo", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "zwj", new RtfCtrlWordHandler(rtfParser, "zwj", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "zwnbo", new RtfCtrlWordHandler(rtfParser, "zwnbo", 0, false, RtfCtrlWordType.SYMBOL, "\\", " ", null));
        ctrlWords.put(
                "zwnj", new RtfCtrlWordHandler(rtfParser, "zwnj", 0, false, RtfCtrlWordType.VALUE, "\\", " ", null));
        ctrlWords.put("{", new RtfCtrlWordHandler(rtfParser, "{", 0, false, RtfCtrlWordType.SYMBOL, "\\", "", "{"));
        ctrlWords.put("|", new RtfCtrlWordHandler(rtfParser, "|", 0, false, RtfCtrlWordType.SYMBOL, "\\", "", "|"));
        ctrlWords.put("}", new RtfCtrlWordHandler(rtfParser, "}", 0, false, RtfCtrlWordType.SYMBOL, "\\", "", "}"));
        ctrlWords.put("~", new RtfCtrlWordHandler(rtfParser, "~", 0, false, RtfCtrlWordType.SYMBOL, "\\", "", "~"));
        ctrlWords.put(
                "unknown",
                new RtfCtrlWordHandler(rtfParser, "unknown", 0, false, RtfCtrlWordType.UNIDENTIFIED, "\\", " ", null));
    }
}
