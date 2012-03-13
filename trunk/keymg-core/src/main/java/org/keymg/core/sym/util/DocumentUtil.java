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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.keymg.core.sym.exceptions.DocumentProcessingException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Utility for dealing with DOM
 * 
 * @author anil@apache.org
 * @since Jun 7, 2010
 */
public class DocumentUtil {
    public static Document create(String xmlDocument) throws ParserConfigurationException, SAXException, IOException {
        if (xmlDocument == null)
            throw new IllegalArgumentException("xmlDocument is null");

        DocumentBuilderFactory factory = getFactory();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlDocument)));
    }

    public static String asString(Document doc) throws DocumentProcessingException {
        Source source = new DOMSource(doc);
        StringWriter sw = new StringWriter();

        Result streamResult = new StreamResult(sw);
        // Write the DOM document to the stream
        try {
            Transformer xformer = getTransformer();
            xformer.transform(source, streamResult);
        } catch (Exception e) {
            throw new DocumentProcessingException(e);
        }

        return sw.toString();
    }

    /**
     * Get Document from an inputstream
     * 
     * @param is
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Document getDocument(InputStream is) throws IOException {
        DocumentBuilderFactory factory = getFactory();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            return builder.parse(is);
        } catch (ParserConfigurationException e) {
            throw new IOException(e);
        } catch (SAXException e) {
            throw new IOException(e);
        }
    }

    /**
     * Stream a DOM Node as an input stream
     * 
     * @param node
     * @return
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerException
     */
    public static InputStream getNodeAsStream(Node node) throws IOException {
        return getSourceAsStream(new DOMSource(node));
    }

    /**
     * Get the {@link Source} as an {@link InputStream}
     * 
     * @param source
     * @return
     * @throws IOException
     * @throws ConfigurationException
     * @throws ProcessingException
     */
    public static InputStream getSourceAsStream(Source source) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Result streamResult = new StreamResult(baos);
        // Write the DOM document to the stream
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, streamResult);

            return new ByteArrayInputStream(baos.toByteArray());
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    private static DocumentBuilderFactory getFactory() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setXIncludeAware(true);
        return factory;
    }

    private static Transformer getTransformer() throws TransformerConfigurationException, TransformerFactoryConfigurationError {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        return transformer;
    }
}