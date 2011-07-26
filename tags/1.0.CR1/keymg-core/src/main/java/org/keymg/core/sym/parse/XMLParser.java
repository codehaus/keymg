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

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public interface XMLParser 
{
   /**
    * Whether this parser supports the particular QName
    * @param qname
    * @return
    */
   boolean acceptsQName(QName qname);

   /**
    * Get the QNames this parser is able to handle
    * @return
    */
   QName[] getQNames();

   /**
    * Handle the xml event
    * @param reader
    * @param xmlEvent
    * @param populateObject Object that needs to be populated with the parser operation
    * @throws XMLStreamException
    */
   void handle(XMLEventReader reader, XMLEvent xmlEvent, Object populateObject) throws XMLStreamException;
}