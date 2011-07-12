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
package org.keymg.test.sym.processor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.junit.Test;
import org.keymg.core.sym.SymKeyProcessor;
import org.keymg.core.sym.config.KeymgConfigurationManager;
import org.keymg.core.sym.parse.Parser;
import org.keymg.core.sym.policy.InmemorySymKeyPolicyStore;
import org.keymg.sym.model.ekmi.SymkeyRequest;

/**
 * Unit test the {@link SymKeyProcessor}
 * @author anil@apache.org
 * @since Jul 11, 2011
 */
public class SymKeyProcessorUnitTestCase
{
   @Test
   public void testSymKeyRequest() throws Exception
   {
      ClassLoader tcl = Thread.currentThread().getContextClassLoader();
      InputStream inputStream = tcl.getResourceAsStream("ekmi/v1/symkeyrequest-01.xml");
      assertNotNull(inputStream);
      Parser parser = new Parser();
      parser.parse(inputStream);
      
      Object parsed = parser.getParsedObject();
      assertTrue(parsed instanceof SymkeyRequest);
      SymkeyRequest symKeyRequest = (SymkeyRequest) parsed;
      
      KeymgConfigurationManager configManager = KeymgConfigurationManager.getInstance();
      InmemorySymKeyPolicyStore store = new InmemorySymKeyPolicyStore();
      KeymgConfigurationManager.setPolicyStore(store);
      SymKeyProcessor processor = new SymKeyProcessor(configManager);
      processor.setServerID("1");
      processor.process(symKeyRequest);
   }
}