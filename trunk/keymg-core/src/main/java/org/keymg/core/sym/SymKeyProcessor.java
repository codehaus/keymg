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
package org.keymg.core.sym;

import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.List;
import java.util.StringTokenizer;

import org.keymg.core.sym.config.KeymgConfigurationManager;
import org.keymg.core.sym.generators.SymKeyGenerator;
import org.keymg.core.sym.pki.PKIManager;
import org.keymg.core.sym.policy.SymKeyPolicyStore;
import org.keymg.core.sym.util.DocumentUtil;
import org.keymg.core.sym.util.SymKeyGenUtil;
import org.keymg.sym.model.ekmi.CipherDataType;
import org.keymg.sym.model.ekmi.EncryptionMethodType;
import org.keymg.sym.model.ekmi.GlobalKeyIDType;
import org.keymg.sym.model.ekmi.KeyClassType;
import org.keymg.sym.model.ekmi.KeyClassesType;
import org.keymg.sym.model.ekmi.KeyUsePolicyType;
import org.keymg.sym.model.ekmi.SymkeyRequest;
import org.keymg.sym.model.ekmi.SymkeyResponse;
import org.keymg.sym.model.ekmi.SymkeyType;
import org.w3c.dom.Document;

/**
 * Processor to process symkey requests
 * @author anil@apache.org
 * @since Jul 11, 2011
 */
public class SymKeyProcessor
{
   protected SymKeyPolicyStore policyStore;
   
   protected String serverID = null;

   private static int last = 0;
   
   /**
    * This is the last known id generated for the server
    * @param id
    */
   public static void setLastKeyID( int id )
   {
      last = id;
   }

   public SymKeyProcessor(SymKeyPolicyStore policyStore)
   {
      this.policyStore = policyStore;
   }
      
   public String getServerID()
   {
      return serverID;
   }

   public void setServerID(String serverID)
   {
      this.serverID = serverID;
   }

   public Document process(SymkeyRequest symKeyRequest)
   {
      if(symKeyRequest == null)
         throw new IllegalArgumentException("symKeyRequest is null");
      
      List<String> gids = symKeyRequest.getGlobalKeyID();
      
      if(gids.size() > 1)
         throw new RuntimeException("More than one gid unsupported");
      
      String gid = gids.get(0);
      
      int index = gid.indexOf('-');
      String domainID = gid.substring(0, index);
      
      KeyClassesType keyClasses = symKeyRequest.getKeyClasses();
      
      if(keyClasses != null )
      {
         KeyClassType[] keyClassTypeArr = keyClasses.getKeyClassType();
         
         for(KeyClassType keyClassType: keyClassTypeArr)
         {
            String keyClass = keyClassType.getValue();
            KeyUsePolicyType keyUse = policyStore.getKeyUsePolicyForKeyClassType(keyClass);
            if(keyUse == null)
               keyUse = policyStore.getDefaultKeyUsePolicy(domainID);
         } 
      }
      
      Document doc = null;
      
      if(gid.endsWith("0-0"))
         doc = requestNewKey(gid); 
      else
         doc = requestExistingKey(gid);
      
      return doc;
   }
   
   private Document requestNewKey(String keyID )
   { 
      return generate(keyID);
   }
   
   private Document requestExistingKey(String keyID)
   {
      if( keyID == null )
         throw new IllegalArgumentException( "keyID is null" );
      
      try
      {
         byte[] symmetricKey = KeymgConfigurationManager.getInstance().retrieve(keyID);
        
         //Parse the keyid
         StringTokenizer stringTokenizer = new StringTokenizer( keyID, "-" );
         int tokens = stringTokenizer != null ? stringTokenizer.countTokens() : 0;

         if( tokens == 0 )
            throw new RuntimeException( "Invalid key id" + keyID );

         if( tokens != 3 )
            throw new RuntimeException( keyID + " needs 3 parts" );
         
         String tokenPart1 = stringTokenizer.nextToken();
         String domainID = tokenPart1;
         return dealWithKey(symmetricKey, domainID, new GlobalKeyIDType(keyID));
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }
   
   /**
    * <p>
    * Generate a symmetric key
    * </p>
    * @param keyID The Key ID
    * @return
    */
   public Document generate( String keyID )  
   {
      if( keyID == null )
         throw new IllegalArgumentException( "keyID is null" );
      
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
            
            GlobalKeyIDType finalGlobalID = getGlobalKey(domainID);
            
            //Store the Key
            KeymgConfigurationManager.getInstance().store(symmetricKey, finalGlobalID.getValue());
            return dealWithKey(symmetricKey, domainID, finalGlobalID);
         }
         catch(Exception e)
         {
            throw new RuntimeException(e);
         }
      } 
      
      String responseAsString = response.toString();
      try
      {
         return DocumentUtil.create(responseAsString);
      }
      catch ( Exception e )
      {
         throw new RuntimeException( e );
      }  
   }

   public Document generate( String keyID, String keyClass )
   {
      throw new RuntimeException( "NYI" );
   }
   
   private Document dealWithKey(byte[] symmetricKey, String domainID, GlobalKeyIDType finalGlobalID) throws GeneralSecurityException
   {
      SymKeyGenerator symKeyGenerator = new SymKeyGenerator();
      PublicKey publicKey = null;
      
      if(policyStore instanceof PKIManager)
      {
         publicKey = ((PKIManager)policyStore).getPublicKey(domainID);
      }
      
      if( publicKey == null )
         throw new IllegalStateException( "no public key found for domain id:" + domainID );
      
      byte[] encryptedKey = symKeyGenerator.encrypt( symmetricKey, publicKey );  
      
      String base64EncodedKey = SymKeyGenUtil.base64EncodeSymmetricKeyAsString( encryptedKey ); 
      
      CipherDataType cipherDataType = new CipherDataType();
      cipherDataType.setCipherValue(base64EncodedKey);
      
      SymkeyType symKey = new SymkeyType();
      symKey.setEncryptionMethod( EncryptionMethodType.RSA );
      symKey.setCipherData( cipherDataType );
      
      symKey.setGlobalKeyID( finalGlobalID ); 
      
      symKey.setKeyUsePolicy( policyStore.getDefaultKeyUsePolicy(domainID) ); 

      SymkeyResponse response = new SymkeyResponse();
      response.add( symKey );
      String responseAsString = response.toString();
      try
      {
         return DocumentUtil.create(responseAsString);
      }
      catch ( Exception e )
      {
         throw new RuntimeException( e );
      }
   }
   
   private GlobalKeyIDType getGlobalKey( String domainID )
   {
      if(serverID == null)
         throw new IllegalStateException("serverID has not been set");
      StringBuilder builder = new StringBuilder();
      builder.append( domainID ).append( "-" ).append( serverID );
      builder.append("-").append( ++last );
      return new GlobalKeyIDType( builder.toString() );
   }
}