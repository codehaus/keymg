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
package org.keymg.test.sym.pki;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.URL;
import java.security.PublicKey;

import org.junit.Test;
import org.keymg.core.sym.pki.KeyStorePKIManager;

/**
 * <p>
 * Unit test the {@code PKIManager}
 * </p>
 * @author anil@apache.org
 * @since Jun 14, 2010
 */
public class KeyStorePKIManagerUnitTestCase
{
   @Test
   public void testPKIManager() throws Exception
   {
      String ksPath = "keystore/keymg_test.keystore";
      char[] keyPass = "testkeymg".toCharArray();
      URL ksURL = Thread.currentThread().getContextClassLoader().getResource( ksPath );
      File keyStoreFile = new File( ksURL.getPath() );
      KeyStorePKIManager pki = new KeyStorePKIManager(keyStoreFile, keyPass);
      
      PublicKey publicKey = pki.getPublicKey( "99999" );
      assertNotNull( "public key is not null" , publicKey );
   } 
}