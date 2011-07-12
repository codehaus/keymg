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

import java.io.File;
import java.net.URL;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.cert.Certificate;

import org.keymg.core.sym.SymKeyConstants;
import org.keymg.core.sym.pki.KeyStorePKIManager;
import org.keymg.core.sym.pki.PKIManager;
import org.keymg.core.sym.pki.PKIRepositoryException;
import org.keymg.core.sym.policy.SymKeyPolicyStore;
import org.keymg.sym.model.ekmi.KeyAlgorithmType;
import org.keymg.sym.model.ekmi.KeySizeType;
import org.keymg.sym.model.ekmi.KeyUsePolicyType;
import org.keymg.sym.model.ekmi.StatusType;

/**
 * <p> Acts as a central configuration manager.</p>
 * <p> This class is a singleton. </p>
 * <p> By default, the underlying {@code PKIManager} is a {@code KeyStorePKIManager}
 * which looks for a keystore on the classpath at "keystore/keymg.keystore" with
 * a keypass of "keymg$" </p>
 * <p> Implementations should create their own instance of {@code PKIManager}
 * and use the {@code KeymgConfigurationManager#setPKIManager(PKIManager)} 
 * to set the {@code PKIManager} implementation.</p>
 * 
 * @author anil@apache.org
 * @since Jun 7, 2010
 */
public class KeymgConfigurationManager implements SymKeyPolicyStore,PKIManager
{   
   private String serverID = "1111";
   
   private String keyAlgorithm = SymKeyConstants.AES_ALGORITHM_URI;
   
   private int keySize = 256; 

   private static PKIManager pkiManager = null;
   
   private static SymKeyPolicyStore policyStore = null;
   
   private static KeymgConfigurationManager instance = new KeymgConfigurationManager();
   
   private KeymgConfigurationManager()
   {   
   } 
   
   public static KeymgConfigurationManager getInstance()
   {
      return instance;
   }
   
   public static void setPKIManager( PKIManager pki )
   {
      pkiManager = pki; 
   }
   
   public static void setPolicyStore(SymKeyPolicyStore policyStore)
   {
      KeymgConfigurationManager.policyStore = policyStore;
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
   
   public PublicKey getPublicKeyForDomain( String domainID ) throws PKIRepositoryException
   {
      ensureKeyStore();
      return pkiManager.getPublicKey( domainID );
   }

   public KeyUsePolicyType getKeyUsePolicy(String keyUsePolicyID)
   {
      if(policyStore == null)
         throw new RuntimeException("Policy Store not set");
      return policyStore.getKeyUsePolicy(keyUsePolicyID);
   }

   public KeyUsePolicyType getDefaultKeyUsePolicy(String domainID)
   {
      if(policyStore == null)
         throw new RuntimeException("Policy Store not set");
      return policyStore.getDefaultKeyUsePolicy(domainID);
   }

   public KeyUsePolicyType getKeyUsePolicyForKeyClassType(String keyClassType)
   {
      if(policyStore == null)
         throw new RuntimeException("Policy Store not set");
      return policyStore.getKeyUsePolicyForKeyClassType(keyClassType);
   }

   public KeyPair getKeyPair(String domainID) throws PKIRepositoryException
   {
      ensureKeyStore();
      return KeymgConfigurationManager.pkiManager.getKeyPair(domainID);
   }

   public PublicKey getPublicKey(String domainID) throws PKIRepositoryException
   {
      ensureKeyStore();
      return KeymgConfigurationManager.pkiManager.getPublicKey(domainID);
   }

   public void register(String domainID, Certificate cert) throws PKIRepositoryException
   {  
      ensureKeyStore();
      KeymgConfigurationManager.pkiManager.register(domainID, cert);
   } 
   
   private void ensureKeyStore() throws PKIRepositoryException
   {
      if( KeymgConfigurationManager.pkiManager == null)
      { 
         URL keyStoreURL = SecurityActions.loadResource(getClass(),  "keystore/keymg.keystore" );
         if(keyStoreURL == null)
            throw new PKIRepositoryException("keyStoreURL is null");
         
         //Let us build the default KeyStorePKIManager
         pkiManager = new KeyStorePKIManager( new File( keyStoreURL.getPath() ), "keymg$".toCharArray() );
      }
   }
}