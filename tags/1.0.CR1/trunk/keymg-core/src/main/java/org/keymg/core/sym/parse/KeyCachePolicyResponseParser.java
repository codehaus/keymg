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
import org.keymg.sym.model.ekmi.KeyCachePolicyResponseType;
import org.keymg.sym.model.ekmi.KeyCachePolicyType;

/**
 * @author anil@apache.org
 * @since May 23, 2011
 */
public class KeyCachePolicyResponseParser implements XMLParser
{
   private static Logger log = Logger.getLogger( KeyCachePolicyResponseParser.class.getCanonicalName() );
   
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
      KeyCachePolicyResponseType keyCachePolicyReq = (KeyCachePolicyResponseType) populateObject;

      try 
      {
         while(xmlEventReader.hasNext())
         {
            XMLEvent ev = xmlEventReader.nextEvent();

            switch(ev.getEventType())
            {
               case XMLStreamConstants.START_ELEMENT:
                  StartElement nextStartElement = (StartElement) ev;
                  QName elementName = nextStartElement.getName();

                  String localPart = elementName.getLocalPart();

                  if(localPart.equals( SymKeyConstants.KEY_CACHE_POLICY ))
                  {
                     KeyCachePolicyType kcp = new KeyCachePolicyType();
                     KeyCachePolicyParser parser = new KeyCachePolicyParser();
                     parser.handle(xmlEventReader, xmlEvent, kcp);
                     keyCachePolicyReq.add(kcp);
                  } 
                  else
                     throw new RuntimeException("Unknown element:" + localPart);
                  break;

               case XMLStreamConstants.END_ELEMENT: 

                  EndElement endElement = (EndElement) ev;
                  localPart = endElement.getName().getLocalPart();

                  if( localPart.equals( SymKeyConstants.KEY_CACHE_POLICY_RESPONSE ) )
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