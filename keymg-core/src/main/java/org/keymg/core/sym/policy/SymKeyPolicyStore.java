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
package org.keymg.core.sym.policy;

import org.keymg.sym.model.ekmi.KeyUsePolicyType;

/**
 * Interface for the various policies defining sym key gen
 * 
 * @author anil@apache.org
 * @since Jul 11, 2011
 */
public interface SymKeyPolicyStore {
    /**
     * Given the {@link String} keyusePolicyID, return the {@link KeyUsePolicyType}
     * 
     * @param keyUsePolicyID
     * @return
     */
    KeyUsePolicyType getKeyUsePolicy(String keyUsePolicyID);

    /**
     * Given the {@link String} domainID, return the {@link KeyUsePolicyType}
     * 
     * @param domainID
     * @return
     */
    KeyUsePolicyType getDefaultKeyUsePolicy(String domainID);

    /**
     * Return the {@link KeyUsePolicyType} applicable for a given keyClassType If there is not keyUsePolicyType, return null
     * 
     * @param keyClassType
     * @return
     */
    KeyUsePolicyType getKeyUsePolicyForKeyClassType(String keyClassType);
}