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
 * Encapsulates the encrypted key 
 */
/**
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class CipherDataType {
    private String cipherValue;

    public String getCipherValue() {
        return cipherValue;
    }

    public void setCipherValue(String cipherValue) {
        this.cipherValue = cipherValue;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<xenc:CipherData><xenc:CipherValue>").append(cipherValue)
                .append("</xenc:CipherValue> </xenc:CipherData>");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cipherValue == null) ? 0 : cipherValue.hashCode());
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
        CipherDataType other = (CipherDataType) obj;
        if (cipherValue == null) {
            if (other.cipherValue != null)
                return false;
        } else if (!cipherValue.equals(other.cipherValue))
            return false;
        return true;
    }
}