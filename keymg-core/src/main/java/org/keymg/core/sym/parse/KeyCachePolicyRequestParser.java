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

import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.keymg.core.sym.SymKeyConstants;
import org.keymg.sym.model.ekmi.KeyCachePolicyRequestType;
import org.w3c.dom.Element;

/**
 * @author anil@apache.org
 * @since May 23, 2011
 */
public class KeyCachePolicyRequestParser implements XMLParser
{
   private static Logger log = Logger.getLogger( KeyCachePolicyRequestParser.class.getCanonicalName() );
   
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
      KeyCachePolicyRequestType keyCachePolicyReq = (KeyCachePolicyRequestType) populateObject;

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

                  if(localPart.equals( "Signature" ))
                  {
                     Element elem = getSignatureElement(xmlEventReader, ev.asStartElement());
                     keyCachePolicyReq.setSignature(elem);
                  } 
                  else
                     throw new RuntimeException("Unknown element:" + localPart);
                  break;

               case XMLStreamConstants.END_ELEMENT: 

                  EndElement endElement = (EndElement) ev;
                  localPart = endElement.getName().getLocalPart();

                  if( localPart.equals( SymKeyConstants.SYMKEY_REQUEST ) )
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
   
   @SuppressWarnings("unchecked")
   protected Element getSignatureElement( XMLEventReader xmlEventReader, StartElement sigElement)
   {
      try
      {
         StringBuilder builder = new StringBuilder();
         QName sigName = sigElement.getName();
         String prefix = sigName.getPrefix();
         
         builder.append("<").append(prefix).append(":").append(sigName.getLocalPart());
         builder.append( " xmlns:").append(prefix).append("=\"http://www.w3.org/2000/09/xmldsig#\"").append(">");
         while(xmlEventReader.hasNext())
         {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            
            if( xmlEvent instanceof EndElement)
            {
               EndElement end = (EndElement) xmlEvent;
               QName name = end.getName();

               builder.append("</").append(name.getPrefix()).append(":").append(name.getLocalPart()).append(">");
               
               String localPart = name.getLocalPart(); 
               if( "Signature".equals(localPart))
               {
                  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                  factory.setValidating(false);
                  DocumentBuilder docBuilder = factory.newDocumentBuilder();
                  String str = builder.toString().trim();
                  InputSource source = new InputSource(new StringReader(str));
                  return docBuilder.parse(source).getDocumentElement();  
               }
            }
            
            else if( xmlEvent instanceof StartElement)
            {
               StartElement start = (StartElement) xmlEvent;
               QName name = start.getName();
               builder.append("<").append(name.getPrefix()).append(":").append(name.getLocalPart());
               
               Iterator<Attribute> iter = start.getAttributes();
               while(iter != null && iter.hasNext())
               {
                  Attribute attr = (Attribute) iter.next();
                  builder.append(" ");
                  builder.append(attr.toString());
               }
               builder.append(">");
            }
            else builder.append(xmlEvent.toString().trim());
         }
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      } 
      return null;
   }
}