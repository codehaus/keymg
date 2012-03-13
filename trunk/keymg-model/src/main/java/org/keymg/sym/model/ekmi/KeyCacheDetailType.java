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
 * <p>Java class for KeyCacheDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="KeyCacheDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MaximumKeys">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="18446744073709551615"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MaximumDuration">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="18446744073709551615"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
/**
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class KeyCacheDetailType {
    protected BigInteger maximumKeys;
    protected BigInteger maximumDuration;

    /**
     * Gets the value of the maximumKeys property.
     * 
     * @return possible object is {@link BigInteger }
     * 
     */
    public BigInteger getMaximumKeys() {
        return maximumKeys;
    }

    /**
     * Sets the value of the maximumKeys property.
     * 
     * @param value allowed object is {@link BigInteger }
     * 
     */
    public void setMaximumKeys(BigInteger value) {
        this.maximumKeys = value;
    }

    /**
     * Gets the value of the maximumDuration property.
     * 
     * @return possible object is {@link BigInteger }
     * 
     */
    public BigInteger getMaximumDuration() {
        return maximumDuration;
    }

    /**
     * Sets the value of the maximumDuration property.
     * 
     * @param value allowed object is {@link BigInteger }
     * 
     */
    public void setMaximumDuration(BigInteger value) {
        this.maximumDuration = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maximumDuration == null) ? 0 : maximumDuration.hashCode());
        result = prime * result + ((maximumKeys == null) ? 0 : maximumKeys.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KeyCacheDetailType other = (KeyCacheDetailType) obj;
        if (maximumDuration == null) {
            if (other.maximumDuration != null)
                return false;
        } else if (!maximumDuration.equals(other.maximumDuration))
            return false;
        if (maximumKeys == null) {
            if (other.maximumKeys != null)
                return false;
        } else if (!maximumKeys.equals(other.maximumKeys))
            return false;
        return true;
    }
}