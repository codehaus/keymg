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
package org.keymg.sym.model.ekmi;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element name="GlobalKeyID" type="{http://docs.oasis-open.org/ekmi/2008/01}GlobalKeyIDType" maxOccurs="unbounded"/>
 *           &lt;element name="KeyClasses" type="{http://docs.oasis-open.org/ekmi/2008/01}KeyClassesType" minOccurs="0"/>
 *           &lt;element name="X509EncryptionCertificate" type="{http://docs.oasis-open.org/ekmi/2008/01}X509CertificateType" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="SymkeyRequestID" type="{http://docs.oasis-open.org/ekmi/2008/01}SymkeyRequestIDType" maxOccurs="unbounded"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 *
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class SymkeyRequest {

    protected List<String> globalKeyID;
    protected KeyClassesType keyClasses;
    protected byte[] x509EncryptionCertificate;
    protected List<String> symkeyRequestID;

    /**
     * Gets the value of the globalKeyID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the globalKeyID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGlobalKeyID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getGlobalKeyID() {
        if (globalKeyID == null) {
            globalKeyID = new ArrayList<String>();
        }
        return this.globalKeyID;
    }

    /**
     * Gets the value of the keyClasses property.
     * 
     * @return
     *     possible object is
     *     {@link KeyClassesType }
     *     
     */
    public KeyClassesType getKeyClasses() {
        return keyClasses;
    }

    /**
     * Sets the value of the keyClasses property.
     * 
     * @param value
     *     allowed object is
     *     {@link KeyClassesType }
     *     
     */
    public void setKeyClasses(KeyClassesType value) {
        this.keyClasses = value;
    }

    /**
     * Gets the value of the x509EncryptionCertificate property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getX509EncryptionCertificate() {
        return x509EncryptionCertificate;
    }

    /**
     * Sets the value of the x509EncryptionCertificate property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setX509EncryptionCertificate(byte[] value) {
        this.x509EncryptionCertificate = ((byte[]) value);
    }

    /**
     * Gets the value of the symkeyRequestID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the symkeyRequestID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSymkeyRequestID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSymkeyRequestID() {
        if (symkeyRequestID == null) {
            symkeyRequestID = new ArrayList<String>();
        }
        return this.symkeyRequestID;
    }

}
