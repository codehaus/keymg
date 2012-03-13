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
import org.keymg.sym.model.ekmi.ApplicationsType;
import org.keymg.sym.model.ekmi.PermittedApplicationsType;

/**
 * An implementation of {@link XMLParser} to parse the {@link PermittedApplicationsType}
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class PermittedApplicationsParser implements XMLParser {
    private static Logger log = Logger.getLogger(PermittedApplicationsParser.class.getCanonicalName());

    /**
     * @see XMLParser#acceptsQName(QName)
     */
    public boolean acceptsQName(QName qname) {
        return false;
    }

    /**
     * @see XMLParser#getQNames()
     */
    public QName[] getQNames() {
        return null;
    }

    /**
     * @see XMLParser#handle(XMLEventReader, XMLEvent, Object)
     */
    public void handle(XMLEventReader xmlEventReader, XMLEvent xmlEvent, Object populateObject) throws XMLStreamException {
        PermittedApplicationsType permittedApplicationsType = (PermittedApplicationsType) populateObject;

        try {
            while (xmlEventReader.hasNext()) {
                XMLEvent ev = xmlEventReader.nextEvent();

                switch (ev.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement nextStartElement = (StartElement) ev;
                        QName elementName = nextStartElement.getName();
                        String localPart = elementName.getLocalPart();

                        if (SymKeyConstants.PERMITTED_APPLICATION.equals(localPart)) {
                            ApplicationsType applicationType = new ApplicationsType();
                            permittedApplicationsType.addPermittedApplication(applicationType);

                            PermittedApplicationParser permittedApplicationParser = new PermittedApplicationParser();
                            permittedApplicationParser.handle(xmlEventReader, xmlEvent, applicationType);
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:

                        EndElement endElement = (EndElement) ev;
                        localPart = endElement.getName().getLocalPart();

                        if (localPart.equals(SymKeyConstants.PERMITTED_APPLICATIONS))
                            return;
                        break;

                    case XMLStreamConstants.END_DOCUMENT:
                        return;
                }
            }
        } catch (XMLStreamException e) {
            log.log(Level.SEVERE, "Unable to parse:", e);
        }
    }
}