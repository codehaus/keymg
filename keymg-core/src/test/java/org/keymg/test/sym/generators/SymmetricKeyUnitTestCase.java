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

package org.keymg.test.sym.generators;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import org.junit.After;
import org.junit.Test;
import org.keymg.core.sym.Base64;
import org.keymg.core.sym.SymKeyConstants;
import org.keymg.core.sym.generators.SymKeyGenerator;

/**
 * Unit test the symmetric key generation and encoding etc
 *
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class SymmetricKeyUnitTestCase
{
   @Test
   public void testKeyWithAES() throws Exception
   {
      validate( SymKeyConstants.AES_ALGORITHM_URI ); 
      validate( SymKeyConstants.TRIPLE_DES_ALGORITHM_URI );
   }
   
   private void validate( String keyAlgorithm ) throws Exception
   {
      SymKeyGenerator generator = new SymKeyGenerator();
      byte[] key = generator.generate( keyAlgorithm );
      assertNotNull( key ); 
      
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance( SymKeyConstants.EncryptionAlgorithms.RSA.get() );  
      KeyPair keyPair = keyPairGenerator.generateKeyPair();
      
      byte[] encryptedKey = generator.encrypt( key, keyPair.getPublic() );  
    
      byte[] base64EncodedEncryptedKey = Base64.encodeBytesToBytes( encryptedKey );
      byte[] base64DecodedEncryptedKey = Base64.decode( base64EncodedEncryptedKey );
      
      assertArrayEquals( encryptedKey , base64DecodedEncryptedKey );
      
      byte[] unencryptedKey = generator.decrypt( encryptedKey, keyPair.getPrivate() );  
      
      assertArrayEquals( key, unencryptedKey ); 
   }
   

   @After
   public void end() throws Exception
   {
      File theFile = new File("keystore.dat");
      if(theFile.exists())
      {
         theFile.delete();
      }
   }
}