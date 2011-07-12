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
 * A simple file based {@link SymKeyPolicyStore}
 * @author anil@apache.org
 * @since Jul 11, 2011
 */
public class FileBasedSymKeyPolicyStore implements SymKeyPolicyStore
{ 
   public KeyUsePolicyType getKeyUsePolicy(String keyUsePolicyID)
   { 
      return null;
   }

   public KeyUsePolicyType getDefaultKeyUsePolicy(String domainID)
   { 
      return null;
   }

   public KeyUsePolicyType getKeyUsePolicyForKeyClassType(String keyClassType)
   { 
      return null;
   }
}