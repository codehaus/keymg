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
 * A symmetric key object, which is the successful response
 * of a request for a key from an SKMS client to an SKS server.
 * 
 * <p>Java class for SymkeyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SymkeyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SymkeyRequestID" type="{http://docs.oasis-open.org/ekmi/2008/01}SymkeyRequestIDType"/>
 *         &lt;element name="GlobalKeyID" type="{http://docs.oasis-open.org/ekmi/2008/01}GlobalKeyIDType"/>
 *         &lt;element name="KeyUsePolicy" type="{http://docs.oasis-open.org/ekmi/2008/01}KeyUsePolicyType"/>
 *         &lt;element name="EncryptionMethod" type="{http://www.w3.org/2001/04/xmlenc#}EncryptionMethodType"/>
 *         &lt;element ref="{http://www.w3.org/2001/04/xmlenc#}CipherData"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre> 
 * 
 * @author anil@apache.org
 * @since Aug 24, 2009
 */ 
public class SymkeyType implements ValidResponseType
{

    protected SymkeyRequestIDType symkeyRequestID;
    protected GlobalKeyIDType globalKeyID;
    protected KeyUsePolicyType keyUsePolicy;
    protected EncryptionMethodType encryptionMethod;
    protected CipherDataType cipherData;

    /**
     * Gets the value of the symkeyRequestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public SymkeyRequestIDType getSymkeyRequestID() {
        return symkeyRequestID;
    }

    /**
     * Sets the value of the symkeyRequestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSymkeyRequestID(SymkeyRequestIDType value) {
        this.symkeyRequestID = value;
    }

    /**
     * Gets the value of the globalKeyID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public GlobalKeyIDType getGlobalKeyID() {
        return globalKeyID;
    }

    /**
     * Sets the value of the globalKeyID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlobalKeyID( GlobalKeyIDType value) {
        this.globalKeyID = value;
    }

    /**
     * Gets the value of the keyUsePolicy property.
     * 
     * @return
     *     possible object is
     *     {@link KeyUsePolicyType }
     *     
     */
    public KeyUsePolicyType getKeyUsePolicy() {
        return keyUsePolicy;
    }

    /**
     * Sets the value of the keyUsePolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link KeyUsePolicyType }
     *     
     */
    public void setKeyUsePolicy(KeyUsePolicyType value) {
        this.keyUsePolicy = value;
    }

    /**
     * Gets the value of the encryptionMethod property.
     * 
     * @return
     *     possible object is
     *     {@link EncryptionMethodType }
     *     
     */
    public EncryptionMethodType getEncryptionMethod() {
        return encryptionMethod;
    }

    /**
     * Sets the value of the encryptionMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncryptionMethodType }
     *     
     */
    public void setEncryptionMethod(EncryptionMethodType value) {
        this.encryptionMethod = value;
    }

    /**
     * 
     * The encrypted symmetric key.                    
     * 
     * @return
     *     possible object is
     *     {@link CipherDataType } 
     */
    public CipherDataType getCipherData() {
        return cipherData;
    }

    /**
     * Sets the value of the cipherData property.
     * 
     * @param value
     *     allowed object is
     *     {@link CipherDataType }
     *     
     */
    public void setCipherData(CipherDataType value) {
        this.cipherData = value;
    }
    
    public String toString()
    {
    	StringBuilder builder = new StringBuilder( );
    	builder.append("<ekmi:Symkey>" );
    	if( this.symkeyRequestID != null )
    	   builder.append( "<ekmi:SymkeyRequestID>" ).append( this.symkeyRequestID ).append( "</ekmi:SymkeyRequestID>" );
    	builder.append( "<ekmi:GlobalKeyID>" ).append( this.globalKeyID ).append( "</ekmi:GlobalKeyID>");
    	builder.append( this.keyUsePolicy.toString() );
    	builder.append( this.encryptionMethod.toString() );
    	builder.append( this.cipherData.toString() );

        builder.append("</ekmi:Symkey>" );
    	return builder.toString();
    }
}