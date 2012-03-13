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

import org.keymg.sym.model.ekmi.KeyAlgorithmType;
import org.keymg.sym.model.ekmi.KeySizeType;
import org.keymg.sym.model.ekmi.KeyUsePolicyType;
import org.keymg.sym.model.ekmi.StatusType;
import org.keymg.sym.model.ekmi.TwoPartIDType;

/**
 * An implementation of {@link SymKeyPolicyStore} that resides in memory
 * 
 * @author anil@apache.org
 * @since Jul 11, 2011
 */
public class InmemorySymKeyPolicyStore implements SymKeyPolicyStore {
    protected static KeyUsePolicyType keyUsePolicy;

    static {
        keyUsePolicy = new KeyUsePolicyType();
        keyUsePolicy.setKeyUsePolicyID(new TwoPartIDType("10514-4"));
        keyUsePolicy.setPolicyName("Default Key Use Policy Type");
        keyUsePolicy.setKeyAlgorithm(KeyAlgorithmType.AES_192_CBS);
        keyUsePolicy.setKeySize(new KeySizeType(192));
        keyUsePolicy.setStatus(StatusType.ACTIVE);
    };

    /*
     * @see org.keymg.core.sym.policy.SymKeyPolicyStore#getKeyUsePolicy(java.lang.String)
     */
    public KeyUsePolicyType getKeyUsePolicy(String keyUsePolicyID) {
        return keyUsePolicy;
    }

    /*
     * @see org.keymg.core.sym.policy.SymKeyPolicyStore#getDefaultKeyUsePolicy(java.lang.String)
     */
    public KeyUsePolicyType getDefaultKeyUsePolicy(String domainID) {
        return keyUsePolicy;
    }

    /*
     * @see org.keymg.core.sym.policy.SymKeyPolicyStore#getKeyUsePolicyForKeyClassType(java.lang.String)
     */
    public KeyUsePolicyType getKeyUsePolicyForKeyClassType(String keyClassType) {
        return keyUsePolicy;
    }
}