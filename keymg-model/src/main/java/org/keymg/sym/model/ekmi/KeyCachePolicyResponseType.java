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
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * 
 * <xsd:element name="KeyCachePolicyResponse">
 *      <xsd:complexType>
 *       <xsd:sequence>
 *           <xsd:element name="KeyCachePolicy" type="ekmi:KeyCachePolicyType"
 *                                          minOccurs="1" maxOccurs="unbounded"/>
 *        </xsd:sequence>
 *      </xsd:complexType>
 *    </xsd:element>
 * </pre>
 * 
 * @author anil@apache.org
 * @since May 23, 2011
 */
public class KeyCachePolicyResponseType {
    protected List<KeyCachePolicyType> keyCachePolicies = new ArrayList<KeyCachePolicyType>();

    public void add(KeyCachePolicyType kcp) {
        this.keyCachePolicies.add(kcp);
    }

    public boolean remove(KeyCachePolicyType kcp) {
        return this.keyCachePolicies.remove(kcp);
    }

    public List<KeyCachePolicyType> policies() {
        return Collections.unmodifiableList(keyCachePolicies);
    }
}