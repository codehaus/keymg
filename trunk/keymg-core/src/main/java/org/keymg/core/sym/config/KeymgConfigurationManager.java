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
package org.keymg.core.sym.config;

import org.keymg.core.sym.SymKeyConstants;
import org.keymg.sym.model.ekmi.KeyAlgorithmType;
import org.keymg.sym.model.ekmi.KeySizeType;
import org.keymg.sym.model.ekmi.KeyUsePolicyType;
import org.keymg.sym.model.ekmi.StatusType;

/**
 * <p> Acts as a central configuration manager.</p>
 * @author anil@apache.org
 * @since Jun 7, 2010
 */
public class KeymgConfigurationManager
{
   private String domainID = "1111";
   
   private String serverID = "1111";
   
   private String keyAlgorithm = SymKeyConstants.AES_ALGORITHM_URI;
   
   private int keySize = 256;

   public String getDomainID()
   {
      return domainID;
   }

   public void setDomainID(String domainID)
   {
      this.domainID = domainID;
   }

   public String getServerID()
   {
      return serverID;
   }

   public void setServerID(String serverID)
   {
      this.serverID = serverID;
   }

   public String getKeyAlgorithm()
   {
      return keyAlgorithm;
   }

   public void setKeyAlgorithm(String keyAlgorithm)
   {
      this.keyAlgorithm = keyAlgorithm;
   } 

   
   public int getKeySize()
   {
      return keySize;
   }

   public void setKeySize(int keySize)
   {
      this.keySize = keySize;
   }

   public KeyUsePolicyType getKeyUsePolicyType()
   {
      KeyUsePolicyType keyUsePolicy = new KeyUsePolicyType();
      keyUsePolicy.setKeyAlgorithm( KeyAlgorithmType.reverse( keyAlgorithm ) );
      keyUsePolicy.setKeySize( new KeySizeType( keySize ));
      keyUsePolicy.setStatus( StatusType.ACTIVE );
      return keyUsePolicy;
   }
}