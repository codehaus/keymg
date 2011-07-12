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
package org.keymg.test.web.servlets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.keymg.core.sym.parse.Parser;
import org.keymg.sym.model.ekmi.SymkeyResponse;
import org.keymg.sym.model.ekmi.SymkeyType;
import org.keymg.sym.model.ekmi.ValidResponseType;
import org.keymg.web.servlets.KeymgServlet;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * Unit test the {@link KeymgServlet}
 * @author anil@apache.org
 * @since Jul 11, 2011
 */
public class KeymgServletUnitTestCase
{
   String contentType = "text/xml;charset=utf-8";
   String request = "requests/symkeyrequest-01.xml";
   
   String url = "http://test/keymg";
   ServletRunner servletRunner = null;
   
   
   @Before
   public void setup() throws Exception
   {
      servletRunner = new ServletRunner();
      servletRunner.registerServlet("/keymg", KeymgServlet.class.getName());
      HttpUnitOptions.setScriptingEnabled(false); //Disable JS 
   }
   
   @Test
   public void testSymReq() throws Exception
   {
      ServletUnitClient sc = servletRunner.newClient();
 
      InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(request);
      assertNotNull(is);
      WebRequest postRequest = new PostMethodWebRequest(url, is, contentType);
      
      WebResponse response = sc.getResponse( postRequest ); 
      assertNotNull(response);
      String contentType = response.getContentType();
      assertEquals("text/xml", contentType);
      Parser parser = new Parser();
      parser.parse(response.getInputStream());
      Object parsed = parser.getParsedObject();
      assertTrue(parsed instanceof SymkeyResponse);
      SymkeyResponse symkeyResponse = (SymkeyResponse) parsed;
      List<ValidResponseType> responses = symkeyResponse.getResponse();
      assertEquals(1, responses.size());
      ValidResponseType validResponse = responses.get(0);
      assertNotNull(validResponse);
      assertTrue(validResponse instanceof SymkeyType);
      SymkeyType key = (SymkeyType) validResponse; 
      assertEquals("10514-1-1", key.getGlobalKeyID().getValue());
      System.out.println(key);
   }
}