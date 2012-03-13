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
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.keymg.core.sym.SymKeyConstants;
import org.keymg.sym.model.ekmi.KeyUsePolicyType;
import org.keymg.sym.model.ekmi.PermissionsType;
import org.keymg.sym.model.ekmi.PermittedApplicationsType;
import org.keymg.sym.model.ekmi.PermittedDatesType;
import org.keymg.sym.model.ekmi.PermittedDaysType;
import org.keymg.sym.model.ekmi.PermittedDurationType;
import org.keymg.sym.model.ekmi.PermittedLevelsType;
import org.keymg.sym.model.ekmi.PermittedLocationsType;
import org.keymg.sym.model.ekmi.PermittedNumberOfTransactionsType;
import org.keymg.sym.model.ekmi.PermittedTimesType;
import org.keymg.sym.model.ekmi.PermittedUsesType;

/**
 * An implementation of {@link XMLParser} to parse the {@link PermissionsType}
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class PermissionsParser implements XMLParser {
    private static Logger log = Logger.getLogger(PermissionsParser.class.getCanonicalName());

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
        KeyUsePolicyType keyUsePolicyType = (KeyUsePolicyType) populateObject;
        PermissionsType permissionsType = new PermissionsType();
        keyUsePolicyType.setPermissions(permissionsType);

        try {
            while (xmlEventReader.hasNext()) {
                XMLEvent ev = xmlEventReader.nextEvent();

                switch (ev.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement nextStartElement = (StartElement) ev;
                        QName elementName = nextStartElement.getName();
                        String localPart = elementName.getLocalPart();

                        if (SymKeyConstants.PERMITTED_APPLICATIONS.equals(localPart)) {
                            PermittedApplicationsType permittedApplicationsType = new PermittedApplicationsType();

                            permissionsType.setPermittedApplications(permittedApplicationsType);

                            PermittedApplicationsParser permittedApplicationsParser = new PermittedApplicationsParser();

                            Attribute anyAttribute = nextStartElement
                                    .getAttributeByName(SymKeyConstants.QNameConstants.ANY_QNAME.get());
                            String anyValue = anyAttribute.getValue();
                            permittedApplicationsType.setAny(anyValue);

                            if ("false".equalsIgnoreCase(anyValue)) {
                                permittedApplicationsParser.handle(xmlEventReader, xmlEvent, permittedApplicationsType);
                            }
                        } else if (SymKeyConstants.PERMITTED_DATES.equals(localPart)) {
                            PermittedDatesType permittedDatesType = new PermittedDatesType();

                            permissionsType.setPermittedDates(permittedDatesType);

                            Attribute anyAttribute = nextStartElement
                                    .getAttributeByName(SymKeyConstants.QNameConstants.ANY_QNAME.get());
                            String anyValue = anyAttribute.getValue();
                            permittedDatesType.setAny(anyValue);

                            if ("false".equalsIgnoreCase(anyValue)) {
                                PermittedDatesParser permittedDatesParser = new PermittedDatesParser();
                                permittedDatesParser.handle(xmlEventReader, xmlEvent, permittedDatesType);
                            }
                        } else if (SymKeyConstants.PERMITTED_DAYS.equals(localPart)) {
                            PermittedDaysType permittedDaysType = new PermittedDaysType();

                            permissionsType.setPermittedDays(permittedDaysType);

                            Attribute anyAttribute = nextStartElement
                                    .getAttributeByName(SymKeyConstants.QNameConstants.ANY_QNAME.get());
                            String anyValue = anyAttribute.getValue();
                            permittedDaysType.setAny(anyValue);

                            if ("false".equalsIgnoreCase(anyValue)) {
                                throw new RuntimeException("Not implemented");
                            }
                        } else if (SymKeyConstants.PERMITTED_DURATION.equals(localPart)) {
                            PermittedDurationType permittedDurationType = new PermittedDurationType();

                            permissionsType.setPermittedDuration(permittedDurationType);

                            Attribute anyAttribute = nextStartElement
                                    .getAttributeByName(SymKeyConstants.QNameConstants.ANY_QNAME.get());
                            String anyValue = anyAttribute.getValue();
                            permittedDurationType.setAny(anyValue);

                            if ("false".equalsIgnoreCase(anyValue)) {
                                throw new RuntimeException("Not implemented");
                            }
                        } else if (SymKeyConstants.PERMITTED_LEVELS.equals(localPart)) {
                            PermittedLevelsType permittedLevelsType = new PermittedLevelsType();

                            permissionsType.setPermittedLevels(permittedLevelsType);

                            Attribute anyAttribute = nextStartElement
                                    .getAttributeByName(SymKeyConstants.QNameConstants.ANY_QNAME.get());
                            String anyValue = anyAttribute.getValue();
                            permittedLevelsType.setAny(anyValue);

                            if ("false".equalsIgnoreCase(anyValue)) {
                                throw new RuntimeException("Not implemented");
                            }
                        } else if (SymKeyConstants.PERMITTED_LOCATIONS.equals(localPart)) {
                            PermittedLocationsType permittedLocationsType = new PermittedLocationsType();

                            permissionsType.setPermittedLocations(permittedLocationsType);

                            Attribute anyAttribute = nextStartElement
                                    .getAttributeByName(SymKeyConstants.QNameConstants.ANY_QNAME.get());
                            String anyValue = anyAttribute.getValue();
                            permittedLocationsType.setAny(anyValue);

                            if ("false".equalsIgnoreCase(anyValue)) {
                                throw new RuntimeException("Not implemented");
                            }
                        } else if (SymKeyConstants.PERMITTED_NUMBER_OF_TRANSACTIONS.equals(localPart)) {
                            PermittedNumberOfTransactionsType permittedTransactionsType = new PermittedNumberOfTransactionsType();

                            permissionsType.setPermittedNumberOfTransactions(permittedTransactionsType);

                            Attribute anyAttribute = nextStartElement
                                    .getAttributeByName(SymKeyConstants.QNameConstants.ANY_QNAME.get());
                            String anyValue = anyAttribute.getValue();
                            permittedTransactionsType.setAny(anyValue);

                            if ("false".equalsIgnoreCase(anyValue)) {
                                throw new RuntimeException("Not implemented");
                            }
                        } else if (SymKeyConstants.PERMITTED_USES.equals(localPart)) {
                            PermittedUsesType permittedUsesType = new PermittedUsesType();

                            permissionsType.setPermittedUses(permittedUsesType);

                            Attribute anyAttribute = nextStartElement
                                    .getAttributeByName(SymKeyConstants.QNameConstants.ANY_QNAME.get());
                            String anyValue = anyAttribute.getValue();
                            permittedUsesType.setAny(anyValue);

                            if ("false".equalsIgnoreCase(anyValue)) {
                                throw new RuntimeException("Not implemented");
                            }
                        } else if (SymKeyConstants.PERMITTED_TIMES.equals(localPart)) {
                            PermittedTimesType permittedTimesType = new PermittedTimesType();

                            permissionsType.setPermittedTimes(permittedTimesType);

                            Attribute anyAttribute = nextStartElement
                                    .getAttributeByName(SymKeyConstants.QNameConstants.ANY_QNAME.get());
                            String anyValue = anyAttribute.getValue();
                            permittedTimesType.setAny(anyValue);

                            if ("false".equalsIgnoreCase(anyValue)) {
                                PermittedTimesParser permittedTimesParser = new PermittedTimesParser();
                                permittedTimesParser.handle(xmlEventReader, xmlEvent, permittedTimesType);
                            }
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:

                        EndElement endElement = (EndElement) ev;
                        localPart = endElement.getName().getLocalPart();

                        if (localPart.equals(SymKeyConstants.PERMISSIONS))
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
