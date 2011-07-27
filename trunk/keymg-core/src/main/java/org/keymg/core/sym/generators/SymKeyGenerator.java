package org.keymg.core.sym.generators;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.keymg.core.sym.SymKeyConstants;

/**
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class SymKeyGenerator
{ 
   /**
    * <p>
    * Generate a symmetric key
    * @param keyAlgorithm either SymKeyConstants.TRIPLE_DES_ALGORITHM_URI or
    *                      SymKeyConstants.AES_ALGORITHM_URI
    * @return
    * @throws NoSuchAlgorithmException
    * @throws {@link IllegalArgumentException} If the algorithm is not supported
    */
   public byte[] generate( String keyAlgorithm ) throws NoSuchAlgorithmException
   { 
      KeyGenerator kg = null;

      if( SymKeyConstants.TRIPLE_DES_ALGORITHM_URI.equals( keyAlgorithm ) )
      {
         // TripleDES 
         kg = KeyGenerator.getInstance("DESede");
         kg.init(168);
      }
      else if( SymKeyConstants.AES_ALGORITHM_URI.equals( keyAlgorithm ))
      {
         kg = KeyGenerator.getInstance("AES");
         kg.init( 256 ); //TODO: make this configurable for 128, 192
      }
      else throw new IllegalArgumentException( "Not supported algorithm:" + keyAlgorithm );

      SecretKey secretKey = kg.generateKey();

      return secretKey.getEncoded(); 
   }
   
   public byte[] encrypt( byte[] secretKey, Key publicKey) throws GeneralSecurityException
   {
      Cipher cipher = null;
      byte[] encryptedKey = null;

      cipher = Cipher.getInstance( "RSA" );  

      cipher.init( Cipher.ENCRYPT_MODE, publicKey ); 

      encryptedKey = cipher.doFinal( secretKey );  

      return encryptedKey; 
   }
   
   public byte[] decrypt( byte[] encryptedKey, Key privateKey ) throws GeneralSecurityException 
   {
      Cipher cipher = null;
      byte[] key = null;

      cipher = Cipher.getInstance( "RSA" ); 

      cipher.init(Cipher.DECRYPT_MODE, privateKey );

      key = cipher.doFinal( encryptedKey );  
      return key;       
   }
}