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
package org.keymg.core.sym.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.keymg.core.sym.exceptions.DocumentProcessingException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author anil@apache.org
 * @since Jun 7, 2010
 */
public class DocumentUtil
{
   public static Document create( String xmlDocument ) throws ParserConfigurationException, SAXException, IOException
   {
      if( xmlDocument == null )
         throw new IllegalArgumentException( "xmlDocument is null" ); 
      
      DocumentBuilderFactory factory = getFactory();
      DocumentBuilder builder = factory.newDocumentBuilder();
      return builder.parse( new InputSource( new StringReader( xmlDocument ) ) ); 
   }
   
   public static String asString( Document doc ) throws DocumentProcessingException
   {
      Source source = new DOMSource( doc );
      StringWriter sw = new StringWriter();

      Result streamResult = new StreamResult(sw);
      // Write the DOM document to the stream
      try
      {
         Transformer xformer = getTransformer();
         xformer.transform(source, streamResult);
      }
      catch ( Exception e)
      {
         throw new DocumentProcessingException( e );
      } 

      return sw.toString();
   }
   
   private static DocumentBuilderFactory getFactory()
   {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware( true );
      factory.setXIncludeAware(true);
      return factory;
   } 
   

   private static Transformer getTransformer() throws TransformerConfigurationException, TransformerFactoryConfigurationError 
   {
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      
      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      transformer.setOutputProperty(OutputKeys.INDENT, "no");
      return transformer;
   }
}