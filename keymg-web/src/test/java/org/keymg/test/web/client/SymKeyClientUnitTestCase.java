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
package org.keymg.test.web.client;
 
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

import java.security.KeyPair;
import java.security.KeyStore;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.keymg.api.sym.client.SymKeyClient;
import org.keymg.core.sym.SymKeyConstants;
import org.keymg.core.sym.generators.SymKeyGenerator;
import org.keymg.core.sym.util.KeyStoreUtil;
import org.keymg.sym.model.ekmi.SymkeyResponse;
import org.keymg.web.servlets.KeymgServlet;
 

/**
 * Unit test the {@link SymKeyClient}
 * @author anil@apache.org
 * @since Jul 15, 2011
 */
public class SymKeyClientUnitTestCase
{   
   private String url = "http://localhost:25000/keymg/"; 

   private static String keystorePassword = "keymg$";
   
   private static Server server = null;
   private static int port = 25000;
   
   private static KeyStore keystore;
   private static KeyPair keyPair;
   
   @BeforeClass
   public static void setup() throws Exception
   {  
      server = new Server(port);
      ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
      context.setContextPath("/");
      server.setHandler(context);

      context.addServlet(new ServletHolder(new KeymgServlet()),"/keymg/*");
      server.start();  

      keystore = KeyStoreUtil.get("keystore/keymg.keystore", keystorePassword.toCharArray());

      keyPair = KeyStoreUtil.getKeyPair(keystore, "10514", keystorePassword.toCharArray());
      assertNotNull(keyPair);
   }
   
   @AfterClass
   public static void destroy() throws Exception
   {
      server.stop();
   }
   
   @Test
   public void testEncryptDecrypt() throws Exception
   {  
      SymKeyGenerator gen = new SymKeyGenerator();
      byte[] key = gen.generate(SymKeyConstants.AES_ALGORITHM_URI);
      byte[] enc = gen.encrypt(key, keyPair.getPublic());
      
      byte[] dec = gen.decrypt(enc, keyPair.getPrivate());
      assertArrayEquals(key,dec);
   }
   
   /**
    * Test the creation of a new symmetric key
    * @throws Exception
    */
   @Test
   public void testNew() throws Exception
   {    
      SymKeyClient client = new SymKeyClient("10514", url, keyPair.getPrivate()); 
      
      SymkeyResponse symKeyResponse = client.createNew();
      System.out.println(symKeyResponse);
      byte[] key = client.getKey(symKeyResponse);
      assertNotNull(key);
   }  
}