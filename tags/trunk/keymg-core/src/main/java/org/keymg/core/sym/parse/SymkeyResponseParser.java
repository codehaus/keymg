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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.keymg.core.sym.SymKeyConstants;
import org.keymg.sym.model.ekmi.SymkeyResponse;

/**
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class SymkeyResponseParser implements XMLParser
{
   private static Logger log = Logger.getLogger(SymkeyResponseParser.class.getCanonicalName());

   public boolean acceptsQName(QName qname) 
   {
      return false;
   }

   public QName[] getQNames() 
   {
      return null;
   }

   public void handle(XMLEventReader xmlEventReader, XMLEvent xmlEvent, Object populateObject) throws XMLStreamException 
   {
      SymkeyResponse symKeyResponse = (SymkeyResponse) populateObject;

      try 
      {
         while(xmlEventReader.hasNext())
         {
            XMLEvent ev = xmlEventReader.nextEvent();

            String localPart;

            switch(ev.getEventType())
            {
               case XMLStreamConstants.START_ELEMENT:
                  StartElement nextStartElement = (StartElement) ev;
                  QName elementName = nextStartElement.getName();
                  /*if("Symkey".equals(elementName.getLocalPart()))
					{ 

						String gid = xmlEventReader.getElementText();
						sy.getResponse().add(null);  
					} 
					else
					{*/
                  //this is xsd:any
                  String nsURI = elementName.getNamespaceURI();
                  XMLParser xmlParser = ParserFactory.getParser( elementName );
                  if(xmlParser == null)
                  {
                     log.log( Level.SEVERE, "Unable to locate any parser for the namespace:" + nsURI );
                  }
                  else
                  {
                     xmlParser.handle(xmlEventReader, nextStartElement, symKeyResponse);
                  }
                  //}
                  break;

               case XMLStreamConstants.END_ELEMENT:  

                  EndElement endElement = (EndElement) ev;
                  localPart = endElement.getName().getLocalPart();

                  if( localPart.equals( SymKeyConstants.SYMKEY_RESPONSE ) )
                     return;
                  break;
               case XMLStreamConstants.END_DOCUMENT:
                  return; 
            } 
         }
      } 
      catch (XMLStreamException e) 
      { 
         log.log( Level.SEVERE, "Unable to parse:" , e );
      } 
   }
}