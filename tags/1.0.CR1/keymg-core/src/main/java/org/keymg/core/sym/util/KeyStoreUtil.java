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
package org.keymg.core.sym.util;

import java.net.URL;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.keymg.core.sym.SymKeyConstants;

/**
 * Utility dealing with the Java KeyStore
 * @author anil@apache.org
 * @since Jul 15, 2011
 */
public class KeyStoreUtil
{
   public static KeyStore get(String url, char[] keystorePassword) throws KeyStoreException
   {
      URL res = SecurityActions.loadResource(KeyStoreUtil.class, "keystore/keymg.keystore");
      
      KeyStore keystore = KeyStore.getInstance(SymKeyConstants.KEYSTORE_FORMAT);

      try
      {
         // Load the keystore contents 
         keystore.load(res.openStream(), keystorePassword);
      }
      catch (Exception e)
      {
         throw new KeyStoreException(e);
      }

      return keystore; 
   }
   
   
   public static KeyPair getKeyPair(KeyStore keystore, String alias, char[] password) throws Exception 
   { 
      // Get private key
      Key key = keystore.getKey(alias, password);
      if (key instanceof PrivateKey) 
      {
         // Get certificate of public key
         java.security.cert.Certificate cert = keystore.getCertificate(alias);

         // Get public key
         PublicKey publicKey = cert.getPublicKey();

         // Return a key pair
         return new KeyPair(publicKey, (PrivateKey)key);
      }
      return null;
   }  
}