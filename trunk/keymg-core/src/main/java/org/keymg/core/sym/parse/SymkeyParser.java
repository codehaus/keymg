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

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.keymg.core.sym.SymKeyConstants;
import org.keymg.sym.model.ekmi.EncryptionMethodType;
import org.keymg.sym.model.ekmi.GlobalKeyIDType;
import org.keymg.sym.model.ekmi.SymkeyErrorType;
import org.keymg.sym.model.ekmi.SymkeyRequestIDType;
import org.keymg.sym.model.ekmi.SymkeyResponse;
import org.keymg.sym.model.ekmi.SymkeyType;
import org.keymg.sym.model.ekmi.SymkeyWorkInProgressType;

/**
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class SymkeyParser implements XMLParser 
{ 
   private static Logger log = Logger.getLogger(SymkeyParser.class.getCanonicalName());

   private final QName qname = SymKeyConstants.QNameConstants.SYMKEY_QNAME.get();

   public boolean acceptsQName(QName qname) 
   {
      String localPart = qname.getLocalPart();
      String ns = qname.getNamespaceURI();

      if(qname.getLocalPart().equals(localPart) && qname.getNamespaceURI().equals(ns))
         return true;

      return false;
   }

   public QName[] getQNames() 
   {
      return new QName[] { qname };
   }

   @SuppressWarnings("unchecked")
   public void handle(XMLEventReader xmlEventReader, XMLEvent xmlEvent, Object populateObject) throws XMLStreamException 
   { 
      SymkeyResponse response = (SymkeyResponse) populateObject;
      if( xmlEvent instanceof StartElement)
      {
         StartElement startEl = (StartElement) xmlEvent;
         String local = startEl.getName().getLocalPart();
         if( SymKeyConstants.SYMKEY_ERROR.equals(local))
         {
            SymkeyErrorType symErr = handleError(xmlEventReader, xmlEvent, populateObject);
            response.add(symErr);
            return;
         }
         if( SymKeyConstants.SYMKEY_WORK_IN_PROGRESS.equals(local))
         {
            SymkeyWorkInProgressType symWIP = handleWorkInProgress(xmlEventReader, xmlEvent, populateObject);
            response.add(symWIP);
            return;
         }
      }
      
      SymkeyType symKey = new SymkeyType();
      response.add(symKey);

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

                  if( SymKeyConstants.SYMKEY_REQUEST_ID.equals( localPart ))
                  { 
                     String requestID = xmlEventReader.getElementText().trim();
                     symKey.setSymkeyRequestID( new SymkeyRequestIDType( requestID )); 
                  }  
                  else if( SymKeyConstants.GLOBAL_KEY_ID.equals( localPart ))
                  {
                     String globalKeyId = xmlEventReader.getElementText().trim();
                     symKey.setGlobalKeyID( new GlobalKeyIDType( globalKeyId ));
                  } 
                  else if( SymKeyConstants.ENCRYPTION_METHOD.equals( localPart ))
                  {
                     Iterator<Attribute> attributes = nextStartElement.getAttributes(); 
                     while( attributes.hasNext() )
                     {
                        Attribute attribute = attributes.next();
                        if( attribute.getName().getLocalPart().equals(  SymKeyConstants.ALGORITHM ) )
                        {
                           String encUri = attribute.getValue();
                           symKey.setEncryptionMethod( EncryptionMethodType.reverse( encUri ));
                        } 
                     }  
                  }
                  else if( SymKeyConstants.KEY_USE_POLICY.equals( localPart ))
                  {
                     KeyUsePolicyParser keyUsePolicyParser = new KeyUsePolicyParser();
                     keyUsePolicyParser.handle(xmlEventReader, xmlEvent, symKey); 
                  }
                  break;

               case XMLStreamConstants.END_ELEMENT:  

                  EndElement endElement = (EndElement) ev;
                  localPart = endElement.getName().getLocalPart();

                  if( localPart.equals( SymKeyConstants.SYMKEY ) )
                     return;
                  break;

               case XMLStreamConstants.END_DOCUMENT:
                  return ; 
            } 
         }
      }
      catch (XMLStreamException e) 
      { 
         log.log( Level.SEVERE, "Unable to parse:" , e );
      }
   } 
   
   private SymkeyErrorType handleError(XMLEventReader xmlEventReader, XMLEvent xmlEvent, Object populateObject) throws XMLStreamException 
   {
      SymkeyErrorType symError = new SymkeyErrorType();
      
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

                  if( SymKeyConstants.SYMKEY_REQUEST_ID.equals( localPart ))
                  { 
                     String requestID = xmlEventReader.getElementText().trim();
                     symError.setSymkeyRequestID( requestID ); 
                  }  
                  else if( SymKeyConstants.REQUESTED_GLOBAL_KEY_ID.equals( localPart ))
                  {
                     String globalKeyId = xmlEventReader.getElementText().trim();
                     symError.setRequestedGlobalKeyID( globalKeyId );
                  } 
                  else if( SymKeyConstants.REQUESTED_KEY_CLASS.equals( localPart ))
                  {
                     String globalKeyClass = xmlEventReader.getElementText().trim();
                     symError.setRequestedKeyClass( globalKeyClass );
                  } 
                  else if( SymKeyConstants.ERROR_CODE.equals( localPart ))
                  {
                     String errorCode = xmlEventReader.getElementText().trim();
                     symError.setErrorCode( errorCode );
                  } 
                  else if( SymKeyConstants.ERROR_MSG.equals( localPart ))
                  {
                     String errorMsg = xmlEventReader.getElementText().trim();
                     symError.setErrorMessage( errorMsg );
                  } 
                  else
                     throw new RuntimeException("Unknown Element:"+localPart);
                  break;

               case XMLStreamConstants.END_ELEMENT:  

                  EndElement endElement = (EndElement) ev;
                  localPart = endElement.getName().getLocalPart();

                  if( localPart.equals( SymKeyConstants.SYMKEY ) )
                     break;
                  break;

               case XMLStreamConstants.END_DOCUMENT:
                  break; 
            } 
         }
      }
      catch (XMLStreamException e) 
      { 
         log.log( Level.SEVERE, "Unable to parse:" , e );
      }
      
      
      return symError;
   }
   
   private SymkeyWorkInProgressType handleWorkInProgress(XMLEventReader xmlEventReader, XMLEvent xmlEvent, Object populateObject) throws XMLStreamException 
   {
      SymkeyWorkInProgressType symWIP = new SymkeyWorkInProgressType();
      
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

                  if( SymKeyConstants.SYMKEY_REQUEST_ID.equals( localPart ))
                  { 
                     String requestID = xmlEventReader.getElementText().trim();
                     symWIP.setSymkeyRequestID( requestID ); 
                  }  
                  else if( SymKeyConstants.REQUESTED_GLOBAL_KEY_ID.equals( localPart ))
                  {
                     String globalKeyId = xmlEventReader.getElementText().trim();
                     symWIP.setRequestedGlobalKeyID( globalKeyId );
                  } 
                  else if( SymKeyConstants.REQUESTED_KEY_CLASS.equals( localPart ))
                  {
                     String reqKeyClass = xmlEventReader.getElementText().trim();
                     symWIP.setRequestedKeyClass( reqKeyClass );
                  } 
                  else if( SymKeyConstants.REQUEST_CHECKIN_INTERVAL.equals( localPart ))
                  {
                     String reqCheckInInt = xmlEventReader.getElementText().trim();
                     symWIP.setRequestCheckInterval(Integer.valueOf( reqCheckInInt ));
                  } 
                  else
                     throw new RuntimeException("Unknown Element:"+localPart);
                  break;

               case XMLStreamConstants.END_ELEMENT:  

                  EndElement endElement = (EndElement) ev;
                  localPart = endElement.getName().getLocalPart();

                  if( localPart.equals( SymKeyConstants.SYMKEY ) )
                     break;
                  break;

               case XMLStreamConstants.END_DOCUMENT:
                  break; 
            } 
         }
      }
      catch (XMLStreamException e) 
      { 
         log.log( Level.SEVERE, "Unable to parse:" , e );
      }
      
      
      return symWIP;
   }
}