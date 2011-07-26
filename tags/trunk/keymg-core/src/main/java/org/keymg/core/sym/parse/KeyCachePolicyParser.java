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

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
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
import org.keymg.sym.model.ekmi.KeyCacheDetailType;
import org.keymg.sym.model.ekmi.KeyCachePolicyType;
import org.keymg.sym.model.ekmi.KeyClassType;
import org.keymg.sym.model.ekmi.NonNegativeInteger;
import org.keymg.sym.model.ekmi.StatusType;
import org.keymg.sym.model.ekmi.TwoPartIDType;

/**
 * @author anil@apache.org
 * @since May 24, 2011
 */
public class KeyCachePolicyParser implements XMLParser
{ 
   private static Logger log = Logger.getLogger( KeyCachePolicyParser.class.getCanonicalName() );
   
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
      KeyCachePolicyType keyCachePolicyType = (KeyCachePolicyType) populateObject;

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

                  if( SymKeyConstants.KEY_CACHE_POLICY_ID.equals( localPart ))
                  {  
                     keyCachePolicyType.setKeyCachePolicyID(new TwoPartIDType(xmlEventReader.getElementText()));
                  }  
                  else if( SymKeyConstants.POLICY_NAME.equals( localPart ))
                  {  
                     keyCachePolicyType.setPolicyName(xmlEventReader.getElementText());
                  } 
                  else if( SymKeyConstants.DESCRIPTION.equals( localPart ))
                  {  
                     keyCachePolicyType.setDescription(xmlEventReader.getElementText());
                  }
                  else if( SymKeyConstants.KEY_CLASS.equals( localPart ))
                  {  
                     String str = xmlEventReader.getElementText();
                     keyCachePolicyType.setKeyClassType(new KeyClassType(str));
                  }
                  else if( SymKeyConstants.START_DATE.equals( localPart ))
                  { 
                     String startDateStr = xmlEventReader.getElementText();

                     Date startDate = null;
                     try
                     {
                        startDate = ParserUtil.parseDate( startDateStr );
                     }
                     catch (ParseException e)
                     {
                        throw new RuntimeException(e);
                     } 
                     keyCachePolicyType.setStartDate(startDate); 
                  }
                  else if( SymKeyConstants.END_DATE.equals( localPart ))
                  { 
                     String endDateStr = xmlEventReader.getElementText();

                     Date endDate = null;
                     try
                     {
                        endDate = ParserUtil.parseDate( endDateStr );
                     }
                     catch (ParseException e)
                     {
                        throw new RuntimeException(e);
                     } 
                     keyCachePolicyType.setEndDate(endDate); 
                  }
                  else if( SymKeyConstants.POLICY_CHECK_INTERVAL.equals( localPart ))
                  {  
                     Integer val = Integer.valueOf(xmlEventReader.getElementText());
                     keyCachePolicyType.setPolicyCheckInterval(new NonNegativeInteger(val));
                  }
                  else if( SymKeyConstants.STATUS.equals( localPart ))
                  { 
                     String statusStr = xmlEventReader.getElementText();
                     keyCachePolicyType.setStatus(StatusType.fromValue(statusStr));
                  }
                  else if( SymKeyConstants.NEW_KEYS_CACHE_DETAIL.equals( localPart ))
                  {  
                     KeyCacheDetailType kcd = new KeyCacheDetailType();
                     parseCacheDetail(xmlEventReader, kcd);
                     
                     keyCachePolicyType.setNewKeysCacheDetail(kcd);  
                  } 
                  else if( SymKeyConstants.USED_KEYS_CACHE_DETAIL.equals( localPart ))
                  {  
                     KeyCacheDetailType kcd = new KeyCacheDetailType();
                     parseCacheDetail(xmlEventReader, kcd);
                     
                     keyCachePolicyType.setUsedKeysCacheDetail(kcd); 
                  } 
                  break;

               case XMLStreamConstants.END_ELEMENT: 

                  EndElement endElement = (EndElement) ev;
                  localPart = endElement.getName().getLocalPart();

                  if( localPart.equals( SymKeyConstants.KEY_CACHE_POLICY ) )
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
   
   protected void parseCacheDetail( XMLEventReader xmlEventReader, KeyCacheDetailType kcd) throws XMLStreamException
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

               if( SymKeyConstants.MAXIMUM_KEYS.equals( localPart ))
               {  
                  String str = xmlEventReader.getElementText();
                  kcd.setMaximumKeys(BigInteger.valueOf(Long.valueOf(str)));
               }  
               else if( SymKeyConstants.MAXIMUM_DURATION.equals( localPart ))
               {  
                  String str = xmlEventReader.getElementText();
                  kcd.setMaximumDuration(BigInteger.valueOf(Long.valueOf(str)));
               } 
               break;

            case XMLStreamConstants.END_ELEMENT: 

               EndElement endElement = (EndElement) ev;
               localPart = endElement.getName().getLocalPart();

               if( localPart.equals( SymKeyConstants.NEW_KEYS_CACHE_DETAIL ) || 
                     localPart.equals( SymKeyConstants.USED_KEYS_CACHE_DETAIL ))
                  return;
               break;
            case XMLStreamConstants.END_DOCUMENT:
               return ; 
         } 
      }
   }
}