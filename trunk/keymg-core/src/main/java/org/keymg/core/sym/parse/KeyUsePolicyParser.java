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
import org.keymg.sym.model.ekmi.KeyAlgorithmType;
import org.keymg.sym.model.ekmi.KeyClassType;
import org.keymg.sym.model.ekmi.KeySizeType;
import org.keymg.sym.model.ekmi.KeyUsePolicyType;
import org.keymg.sym.model.ekmi.StatusType;
import org.keymg.sym.model.ekmi.SymkeyType;
import org.keymg.sym.model.ekmi.TwoPartIDType;

/**
 * An implementation of {@link XMLParser} to parse the {@link KeyUsePolicyType}
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class KeyUsePolicyParser implements XMLParser {
    private static Logger log = Logger.getLogger(KeyUsePolicyParser.class.getCanonicalName());

    private final QName qname = SymKeyConstants.QNameConstants.KEYUSEPOLICY_QNAME.get();

    /**
     * @see XMLParser#acceptsQName(QName)
     */
    public boolean acceptsQName(QName qname) {
        String localPart = qname.getLocalPart();
        String ns = qname.getNamespaceURI();

        if (qname.getLocalPart().equals(localPart) && qname.getNamespaceURI().equals(ns))
            return true;

        return false;
    }

    /**
     * @see XMLParser#getQNames()
     */
    public QName[] getQNames() {
        return new QName[] { qname };
    }
    
    /**
     * @see XMLParser#handle(XMLEventReader, XMLEvent, Object)
     */
    public void handle(XMLEventReader xmlEventReader, XMLEvent xmlEvent, Object populateObject) throws XMLStreamException {
        SymkeyType symKey = (SymkeyType) populateObject;
        KeyUsePolicyType keyUsePolicyType = new KeyUsePolicyType();
        symKey.setKeyUsePolicy(keyUsePolicyType);

        // We have already seen ekmi:KeyUsePolicy
        try {
            while (xmlEventReader.hasNext()) {
                XMLEvent ev = xmlEventReader.nextEvent();

                switch (ev.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement nextStartElement = (StartElement) ev;
                        QName elementName = nextStartElement.getName();
                        String localPart = elementName.getLocalPart();

                        if (SymKeyConstants.KEY_USE_POLICY_ID.equals(localPart)) {
                            String keyPolicyID = xmlEventReader.getElementText();
                            TwoPartIDType twoPartIdType = new TwoPartIDType(keyPolicyID);
                            keyUsePolicyType.setKeyUsePolicyID(twoPartIdType);
                        } else if (SymKeyConstants.POLICY_NAME.equals(localPart)) {
                            String policyName = xmlEventReader.getElementText();
                            keyUsePolicyType.setPolicyName(policyName);
                        } else if (SymKeyConstants.KEY_CLASS.equals(localPart)) {
                            String keyClass = xmlEventReader.getElementText();
                            keyUsePolicyType.setKeyClass(new KeyClassType(keyClass));
                        } else if (SymKeyConstants.KEY_ALGORITHM.equals(localPart)) {
                            String keyAlgorithm = xmlEventReader.getElementText();
                            keyUsePolicyType.setKeyAlgorithm(KeyAlgorithmType.reverse(keyAlgorithm));
                        } else if (SymKeyConstants.KEY_SIZE.equals(localPart)) {
                            String keySize = xmlEventReader.getElementText();
                            keyUsePolicyType.setKeySize(new KeySizeType(Integer.parseInt(keySize)));
                        } else if (SymKeyConstants.PERMISSIONS.equals(localPart)) {
                            PermissionsParser permissionsParser = new PermissionsParser();
                            permissionsParser.handle(xmlEventReader, xmlEvent, keyUsePolicyType);
                        } else if (SymKeyConstants.STATUS.equals(localPart)) {
                            String status = xmlEventReader.getElementText();
                            keyUsePolicyType.setStatus(StatusType.fromValue(status));
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:

                        EndElement endElement = (EndElement) ev;
                        localPart = endElement.getName().getLocalPart();

                        if (localPart.equals(SymKeyConstants.KEY_USE_POLICY))
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
