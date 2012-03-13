/* 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.keymg.core.sym.parse;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

/**
 * A factory to obtain instances of {@link XMLParser} by {@link QName}
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class ParserFactory {
    private static Set<XMLParser> parsers = new HashSet<XMLParser>();

    public synchronized static void addXMLParser(XMLParser xmlParser) {
        parsers.add(xmlParser);
    }

    public static XMLParser getParser(QName qname) {
        for (XMLParser parser : parsers) {
            if (parser.acceptsQName(qname))
                return parser;
        }
        return null;
    }
}