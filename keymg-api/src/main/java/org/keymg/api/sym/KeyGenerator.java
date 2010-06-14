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
package org.keymg.api.sym;
 
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.StringTokenizer;

import org.keymg.api.sym.exceptions.SymKeyGenerationException;
import org.keymg.core.sym.SymKeyConstants;
import org.keymg.core.sym.config.KeymgConfigurationManager;
import org.keymg.core.sym.generators.SymKeyGenerator;
import org.keymg.core.sym.util.DocumentUtil;
import org.keymg.core.sym.util.SymKeyGenUtil;
import org.keymg.sym.model.ekmi.CipherDataType;
import org.keymg.sym.model.ekmi.EncryptionMethodType;
import org.keymg.sym.model.ekmi.GlobalKeyIDType;
import org.keymg.sym.model.ekmi.SymkeyResponse;
import org.keymg.sym.model.ekmi.SymkeyType;
import org.w3c.dom.Document;

/**
 * <p>
 * Key Generator.
 * </p>
 * @author anil@apache.org
 * @since Aug 24, 2009
 */ 
public class KeyGenerator 
{
   private KeymgConfigurationManager configurationManager = KeymgConfigurationManager.getInstance();
   
   private static int last = 0;
   
   /**
    * This is the last known id generated for the server
    * @param id
    */
   public static void setLastKeyID( int id )
   {
      last = id;
   }
   
   /**
    * <p>
    * Generate a symmetric key
    * </p>
    * @param keyID The Key ID
    * @return
    */
   public Document generate( String keyID ) throws SymKeyGenerationException
   {
      //Parse the keyid
      StringTokenizer stringTokenizer = new StringTokenizer( keyID, "-" );
      int tokens = stringTokenizer != null ? stringTokenizer.countTokens() : 0;

      if( tokens == 0 )
         throw new RuntimeException( "Invalid key id" + keyID );

      if( tokens != 3 )
         throw new RuntimeException( keyID + " needs 3 parts" );


      SymkeyResponse response = new SymkeyResponse();
      
      String tokenPart1 = stringTokenizer.nextToken();
      String domainID = tokenPart1;
      if( tokenPart1.equals( domainID ) == false )
         throw new IllegalArgumentException( "Domain ID of " + tokenPart1 + " does not match with expected " + domainID );
      String tokenPart2 = stringTokenizer.nextToken();
      String tokenPart3 = stringTokenizer.nextToken();

      if( tokenPart2.equals( "0") && tokenPart3.equals( "0") )
      {
         //new key request
         SymKeyGenerator symKeyGenerator = new SymKeyGenerator();
         try
         {
            String keyAlgorithm = SymKeyConstants.AES_ALGORITHM_URI;
            byte[] symmetricKey = symKeyGenerator.generate( keyAlgorithm );
            
            PublicKey publicKey = configurationManager.getPublicKeyForDomain( domainID );
            
            if( publicKey == null )
               throw new IllegalStateException( "no public key found for domain id:" + domainID );
            
            byte[] encryptedKey = symKeyGenerator.encrypt( symmetricKey, publicKey, keyAlgorithm );  
            
            String base64EncodedKey = SymKeyGenUtil.base64EncodeSymmetricKeyAsString( encryptedKey ); 
            
            CipherDataType cipherDataType = new CipherDataType();
            cipherDataType.setCipherValue(base64EncodedKey);
            
            SymkeyType symKey = new SymkeyType();
            symKey.setEncryptionMethod( EncryptionMethodType.RSA );
            symKey.setCipherData( cipherDataType );
            
            symKey.setGlobalKeyID( getGlobalKey( domainID ) ); 
            
            symKey.setKeyUsePolicy( configurationManager.getKeyUsePolicyType() ); 
            response.add( symKey );
         }
         catch (GeneralSecurityException e)
         {
            throw new SymKeyGenerationException( e );
         }
      } 
      
      String responseAsString = response.toString();
      try
      {
         return DocumentUtil.create(responseAsString);
      }
      catch ( Exception e )
      {
         throw new SymKeyGenerationException( e );
      }  
   }

   public Document generate( String keyID, String keyClass )
   {
      throw new RuntimeException( "NYI" );
   }
   
   private GlobalKeyIDType getGlobalKey( String domainID )
   {
      StringBuilder builder = new StringBuilder();
      builder.append( domainID ).append( "-" ).append( configurationManager.getServerID() );
      builder.append("-").append( ++last );
      return new GlobalKeyIDType( builder.toString() );
   }
}