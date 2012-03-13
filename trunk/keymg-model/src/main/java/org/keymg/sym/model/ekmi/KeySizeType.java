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
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class KeySizeType {
    private int value;

    public KeySizeType(int value) {
        setValue(value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int val) {
        if (val == 128 || val == 256 || val == 192)
            return;

        throw new RuntimeException("value should be between 128 and 256");
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("<ekmi:KeySize>");
        builder.append(value);
        builder.append("</ekmi:KeySize>");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + value;
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
        KeySizeType other = (KeySizeType) obj;
        if (value != other.value)
            return false;
        return true;
    }
}