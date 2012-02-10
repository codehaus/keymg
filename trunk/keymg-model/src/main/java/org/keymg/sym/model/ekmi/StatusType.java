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
 * <p>Java class for StatusType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatusType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Active"/>
 *     &lt;enumeration value="Default"/>
 *     &lt;enumeration value="Inactive"/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public enum StatusType 
{

    /**
     * 
     *                         An active policy.  If this element is in the
     *                         KeyCachePolicy object, it indicates that symmetric 
     *                         keys may be cached with this KCP.  If it is in the
     *                         KeyUsePolicy object, it indicates that the symmetric
     *                         key may be used for encryption.
     *                     
     * 
     */
    ACTIVE("Active"),

    /**
     * 
     *                         The default policy in the absence of a named KCP or 
     *                         KUP.  It is automatically active, unless over-ridden.
     *                     
     * 
     */
    DEFAULT("Default"),

    /**
     * 
     *                         A policy that, in the case of the KCP, must not be 
     *                         used for caching any symmetric keys or in the case
     *                         of a KUP for encryption.
     *                     
     * 
     */
    INACTIVE("Inactive"),

    /**
     * 
     *                         A policy that has an implementation-specific action.  
     *                         Recommended only for test-use.
     *                     
     * 
     */
    OTHER("Other");
    private final String value;

    StatusType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatusType fromValue(String v) {
        for (StatusType c: StatusType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
    public String toString()
    {
    	StringBuilder builder = new StringBuilder();
    	builder.append("<ekmi:Status>").append(this.value).append("</ekmi:Status>");
    	return builder.toString();
    }

}