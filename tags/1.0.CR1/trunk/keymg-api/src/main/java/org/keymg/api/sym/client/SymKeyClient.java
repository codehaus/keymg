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
package org.keymg.api.sym.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.keymg.api.sym.exceptions.SymKeyGenerationException;
import org.keymg.core.sym.Base64;
import org.keymg.core.sym.generators.SymKeyGenerator;
import org.keymg.core.sym.parse.Parser;
import org.keymg.core.sym.writers.SymkeyWriter;
import org.keymg.sym.model.ekmi.SymkeyRequest;
import org.keymg.sym.model.ekmi.SymkeyResponse;
import org.keymg.sym.model.ekmi.SymkeyType;


/**
 * Client class for accessing the SKSML Servers
 * @author anil@apache.org
 * @since Jul 15, 2011
 */
public class SymKeyClient
{
   protected String serverURL;
   
   protected String domainID;

   private PrivateKey privateKey;

   /**
    * Create the client
    * @param domainID The Domain ID of the Client
    * @param serverURL The SKSML Server
    */
   public SymKeyClient(String domainID, String serverURL, PrivateKey privateKey)
   {
      super();
      this.domainID = domainID;
      this.serverURL = serverURL;
      this.privateKey = privateKey;
   }
   
   public SymkeyResponse createNew() throws SymKeyGenerationException
   {
      try
      {
         SymkeyRequest request = new SymkeyRequest();
         request.addGlobalKeyID(newGID());
         
         ByteArrayOutputStream bos = new ByteArrayOutputStream();
         SymkeyWriter writer = new SymkeyWriter(bos);
         writer.write(request);
         
         byte[] bytes = bos.toByteArray();
         InputStream is = new ByteArrayInputStream(bytes);
         
         HttpClient client = new DefaultHttpClient();
         HttpContext localContext = new BasicHttpContext();
         
         InputStreamEntity isEntity = new InputStreamEntity(is, bytes.length);
         HttpPost post = new HttpPost(serverURL);
         post.setEntity(isEntity);
         
         HttpResponse httpResponse = client.execute(post, localContext);
         InputStream responseStream = httpResponse.getEntity().getContent();
         
         Parser parser = new Parser();
         parser.parse(responseStream);
         return (SymkeyResponse) parser.getParsedObject();
      }
      catch (Exception e)
      {
         throw new SymKeyGenerationException(e);
      } 
   }
   
   public byte[] getKey(SymkeyResponse response) throws IOException, GeneralSecurityException, UnsupportedEncodingException
   {
      if(response == null)
         throw new IllegalArgumentException("response is null");
      
      if(privateKey == null)
         throw new IllegalStateException("Private Key has not been set");
      
      SymkeyType key = (SymkeyType) response.getResponse().get(0);
      String encryptedValue = key.getCipherData().getCipherValue();
      
      //base64 decode
      byte[] encryptedBytes = Base64.decode(encryptedValue.getBytes("UTF-8"));
      
      SymKeyGenerator gen = new SymKeyGenerator();
      return gen.decrypt(encryptedBytes, privateKey);
   }
   
   protected String newGID()
   {
      StringBuilder builder = new StringBuilder(domainID);
      builder.append("-0-0");
      return builder.toString();
   }
}