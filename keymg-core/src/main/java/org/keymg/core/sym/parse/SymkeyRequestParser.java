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
import org.keymg.sym.model.ekmi.KeyClassType;
import org.keymg.sym.model.ekmi.KeyClassesType;
import org.keymg.sym.model.ekmi.SymkeyRequest;
import org.keymg.sym.model.ekmi.SymkeyRequestIDType;


/**
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class SymkeyRequestParser implements XMLParser
{
   private static Logger log = Logger.getLogger( SymkeyRequestParser.class.getCanonicalName() );

   public boolean acceptsQName(QName qname) 
   {
      return false;
   }

   public QName[] getQNames() 
   {
      return null;
   }

   public void handle(XMLEventReader xmlEventReader, XMLEvent xmlEvent,
         Object populateObject) throws XMLStreamException 
   {
      SymkeyRequest symKeyRequest = (SymkeyRequest) populateObject;

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

                  if(localPart.equals( SymKeyConstants.GLOBAL_KEY_ID ))
                  {
                     String gid = xmlEventReader.getElementText();
                     symKeyRequest.addGlobalKeyID(gid); 
                  }
                  else if( localPart.equals( SymKeyConstants.KEY_CLASSES ))
                  {
                     KeyClassesParser keyClassesParser = new KeyClassesParser();
                     keyClassesParser.handle( xmlEventReader, nextStartElement, symKeyRequest ); 
                  }
                  else if( localPart.equals( SymKeyConstants.X509_ENCRYPTION_CERT ))
                  {
                     String cert = xmlEventReader.getElementText().trim();
                     symKeyRequest.setX509EncryptionCertificate(cert.getBytes());
                  }
                  else if( SymKeyConstants.SYMKEY_REQUEST_ID.equals( localPart ))
                  { 
                     String requestID = xmlEventReader.getElementText().trim();
                     symKeyRequest.addSymkeyRequestID( requestID ); 
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


   private static class KeyClassesParser implements XMLParser
   {

      public boolean acceptsQName(QName qname) 
      {
         return false;
      }

      public QName[] getQNames() 
      {
         return null;
      }

      public void handle(XMLEventReader xmlEventReader, XMLEvent xmlEvent,
            Object populateObject) throws XMLStreamException 
      {
         SymkeyRequest symKeyRequest = (SymkeyRequest) populateObject;
         KeyClassesType keyClassesType = new KeyClassesType();
         symKeyRequest.setKeyClasses(keyClassesType);

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

                     if(localPart.equals( SymKeyConstants.KEY_CLASS ))
                     {
                        String gid = xmlEventReader.getElementText();
                        KeyClassType keyClassType = new KeyClassType(gid);
                        keyClassesType.add(keyClassType); 
                     } 
                     break;

                  case XMLStreamConstants.END_ELEMENT: break;
                  case XMLStreamConstants.END_DOCUMENT:
                     return ; 
               }

            }
         } 
         catch (XMLStreamException e) 
         { 
            e.printStackTrace();
         }
      }
   }
}