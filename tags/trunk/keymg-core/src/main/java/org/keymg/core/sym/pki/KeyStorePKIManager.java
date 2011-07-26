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
package org.keymg.core.sym.pki;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

/**
 * <p>
 * KeyStore based implementation of the {@code PublicKeyManager}
 * </p>
 * @author anil@apache.org
 * @since Jun 14, 2010
 */
public class KeyStorePKIManager implements PKIManager
{
   private File keyStoreFile;
   private KeyStore keyStore;
   private char[] keyStorePassword;
   
   public static String KEYSTORE_TYPE = "JCEKS";
   
   public KeyStorePKIManager( File keyStoreFile, char[] keyPass) throws PKIRepositoryException
   {
      this.keyStoreFile = keyStoreFile;
      try
      {
         // Create an empty keystore object 
         keyStore = KeyStore.getInstance(KEYSTORE_TYPE); 
         // Load the keystore contents 
         FileInputStream in = new FileInputStream(keyStoreFile); 
         this.keyStore.load(in, keyPass); 
         in.close();
      }
      catch ( Exception e )
      {
         throw new PKIRepositoryException( e );
      }  
      this.keyStorePassword = keyPass;
   }
   
   /**
    * @see {@code PublicKeyManager#getPublicKey(String)}
    */
   public PublicKey getPublicKey(String domainID) throws PKIRepositoryException
   {
      PublicKey publicKey = null;
      try
      {
         Certificate cert = keyStore.getCertificate( domainID );
         if( cert != null )
            publicKey = cert.getPublicKey(); 
      }
      catch (KeyStoreException e)
      {
         throw new PKIRepositoryException( e );
      }
      
      return publicKey;
   }
   
   /**
    * @see {@code PublicKeyManager#register(String, Certificate)}
    */
   public void register(String domainID, Certificate cert) throws PKIRepositoryException
   {
      try
      {
         keyStore.setCertificateEntry(domainID, cert);

         // Save the new keystore contents 
         FileOutputStream out = new FileOutputStream(keyStoreFile); 
         keyStore.store(out, keyStorePassword); 
         out.close(); 
      }
      catch ( Exception e )
      {
         throw new PKIRepositoryException( e );
      } 
   }

   /**
    * @throws PKIRepositoryException 
    * @see {@code PKIManager#getKeyPair(String) }
    */
   public KeyPair getKeyPair(String domainID) throws PKIRepositoryException
   { 
      try
      {
         // Get private key 
         Key key = keyStore.getKey( domainID , keyStorePassword ); 
         if (key instanceof PrivateKey) 
         { 
            // Get certificate of public key 
            Certificate cert = keyStore.getCertificate( domainID ); 
            // Get public key 
            PublicKey publicKey = cert.getPublicKey(); 
            // Return a key pair 
            return new KeyPair(publicKey, (PrivateKey)key); 
         }
      }
      catch ( Exception e )
      {
         throw new PKIRepositoryException( e );
      } 
      return null;
   } 
}