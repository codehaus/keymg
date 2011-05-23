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

import java.io.InputStream;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;

import org.keymg.core.sym.SymKeyConstants;
import org.keymg.sym.model.ekmi.KeyCachePolicyRequestType;
import org.keymg.sym.model.ekmi.SymkeyRequest;
import org.keymg.sym.model.ekmi.SymkeyResponse;

/**
 * Main Parser for SKSML
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class Parser 
{
	private Object parsedObject;
	
	public Parser()
	{
		ParserFactory.addXMLParser(new SymkeyParser()); 
	}
	
	public void parse( Source source ) throws XMLStreamException
	{
	   XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance(); 
	   XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(source);
	   parseNow(xmlEventReader);
	}
	
	public void parse(InputStream inputStream) throws XMLStreamException
	{
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance(); 
		XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);
		parseNow(xmlEventReader);
	}
	
    public Object getParsedObject()
    {
        return this.parsedObject;
    }

   private void parseNow(XMLEventReader xmlEventReader) throws XMLStreamException
   {
      while(xmlEventReader.hasNext())
      {
         XMLEvent xmlEvent = xmlEventReader.nextEvent();
         if(xmlEvent instanceof StartElement)
         {
            StartElement startElement = (StartElement) xmlEvent;
            this.handleStartElement(xmlEventReader, startElement);
         }
      }
   }
	
	private void handleStartElement(XMLEventReader xmlEventReader, StartElement startElement) throws XMLStreamException
	{
		QName elementName = startElement.getName();
		String namespace = elementName.getNamespaceURI();
		String localPart = elementName.getLocalPart(); 
		
		if(namespace == null)
			throw new IllegalStateException("Namespace is null");
		
		if( SymKeyConstants.SYMKEY_REQUEST.equals(localPart) )
		{
			parsedObject = new SymkeyRequest();
			SymkeyRequestParser symKeyRequestParser = new SymkeyRequestParser();
			symKeyRequestParser.handle( xmlEventReader, startElement, parsedObject ); 
		} 
		else if( SymKeyConstants.SYMKEY_RESPONSE.equals(localPart) )
		{
			parsedObject = new SymkeyResponse();
			SymkeyResponseParser symKeyResponseParser = new SymkeyResponseParser();
			symKeyResponseParser.handle( xmlEventReader, startElement, parsedObject );  
		}
		else if( SymKeyConstants.KEY_CACHE_POLICY_REQUEST.equals(localPart) )
        {
           parsedObject = new KeyCachePolicyRequestType();
           KeyCachePolicyRequestParser kcpParser = new KeyCachePolicyRequestParser();
           kcpParser.handle( xmlEventReader, startElement, parsedObject );  
       }
	}
}