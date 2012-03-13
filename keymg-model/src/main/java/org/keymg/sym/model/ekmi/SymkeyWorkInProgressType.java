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

/**
 * 
 * A response to an asynchronous request for a symmetric key, indicating that the request is being worked on by the SKS server.
 * 
 * 
 * <p>
 * Java class for SymkeyWorkInProgressType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SymkeyWorkInProgressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RequestedGlobalKeyID" type="{http://docs.oasis-open.org/ekmi/2008/01}GlobalKeyIDType"/>
 *         &lt;element name="RequestedKeyClass" type="{http://docs.oasis-open.org/ekmi/2008/01}KeyClassType" minOccurs="0"/>
 *         &lt;element name="SymkeyRequestID" type="{http://docs.oasis-open.org/ekmi/2008/01}SymkeyRequestIDType"/>
 *         &lt;element name="RequestCheckInterval" type="{http://docs.oasis-open.org/ekmi/2008/01}RequestCheckIntervalType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class SymkeyWorkInProgressType implements ValidResponseType {
    protected String requestedGlobalKeyID;
    protected String requestedKeyClass;
    protected String symkeyRequestID;
    protected int requestCheckInterval;

    /**
     * Gets the value of the requestedGlobalKeyID property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRequestedGlobalKeyID() {
        return requestedGlobalKeyID;
    }

    /**
     * Sets the value of the requestedGlobalKeyID property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setRequestedGlobalKeyID(String value) {
        this.requestedGlobalKeyID = value;
    }

    /**
     * Gets the value of the requestedKeyClass property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRequestedKeyClass() {
        return requestedKeyClass;
    }

    /**
     * Sets the value of the requestedKeyClass property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setRequestedKeyClass(String value) {
        this.requestedKeyClass = value;
    }

    /**
     * Gets the value of the symkeyRequestID property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSymkeyRequestID() {
        return symkeyRequestID;
    }

    /**
     * Sets the value of the symkeyRequestID property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSymkeyRequestID(String value) {
        this.symkeyRequestID = value;
    }

    /**
     * Gets the value of the requestCheckInterval property.
     * 
     */
    public int getRequestCheckInterval() {
        return requestCheckInterval;
    }

    /**
     * Sets the value of the requestCheckInterval property.
     * 
     */
    public void setRequestCheckInterval(int value) {
        this.requestCheckInterval = value;
    }

}
