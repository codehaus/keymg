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

import java.math.BigInteger;


/**
 * 
 *                 A complex-type to wrap the NumberOfTransactionsType with 
 *                 the "any" attribute.
 *             
 * 
 * <p>Java class for PermittedNumberOfTransactionsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PermittedNumberOfTransactionsType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://docs.oasis-open.org/ekmi/2008/01>NumberOfTransactionsType">
 *       &lt;attribute ref="{http://docs.oasis-open.org/ekmi/2008/01}any use="required""/>
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * @author anil@apache.org
 * @since Aug 24, 2009
 *
 */
public class PermittedNumberOfTransactionsType {

    protected BigInteger value;
    protected String any;

    /**
     * 
     *                 The number of encryption transactions permitted with this 
     *                 symmetric key.  It does not limit decryption transactions.
     *             
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setValue(BigInteger value) {
        this.value = value;
    }

    /**
     * Gets the value of the any property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAny() {
        return any;
    }

    /**
     * Sets the value of the any property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAny(String value) {
        this.any = value;
    }

}
