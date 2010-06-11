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
package org.keymg.test.api.sym;

import org.junit.Test;
import org.keymg.api.sym.KeyGenerator;
import org.keymg.core.sym.util.DocumentUtil;
import org.w3c.dom.Document;

/**
 * <p>
 * Unit Test the {@code KeyGenerator}
 * </p>
 * @author anil@apache.org
 * @since Jun 7, 2010
 */
public class KeyGeneratorUnitTestCase
{
   @Test
   public void testKeyGen() throws Exception
   {
      KeyGenerator gen = new KeyGenerator();
      Document doc = gen.generate( "1111-0-0" );
      System.out.println( DocumentUtil.asString(doc));
   } 
}