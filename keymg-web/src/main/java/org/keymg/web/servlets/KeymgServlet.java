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
package org.keymg.web.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import org.keymg.core.sym.SymKeyProcessor;
import org.keymg.core.sym.config.KeymgConfigurationManager;
import org.keymg.core.sym.parse.Parser;
import org.keymg.core.sym.policy.InmemorySymKeyPolicyStore;
import org.keymg.core.sym.policy.SymKeyPolicyStore;
import org.keymg.core.sym.util.DocumentUtil;
import org.keymg.sym.model.ekmi.SymkeyRequest;
import org.w3c.dom.Document;

/**
 * Servlet that can take in KeyMG requests
 * @author anil@apache.org
 * @since Jul 11, 2011
 */
public class KeymgServlet extends HttpServlet
{
   private static final long serialVersionUID = 1L;
   
   protected ServletConfig config = null;
   
   protected SymKeyProcessor processor;
   
   protected String policyStoreName = InmemorySymKeyPolicyStore.class.getName();
   
   protected String serverID = "1";
 
   public void init(ServletConfig config) throws ServletException
   { 
      super.init(config);
      this.config = config;
      
      String psn = config.getInitParameter("policyStoreName");
      if(psn != null && psn.isEmpty() == false)
      {
         policyStoreName = psn;
      }

      Class<?> policyStoreClass =SecurityActions.load(getClass(), policyStoreName);
      if(policyStoreClass == null)
         throw new ServletException("Unable to load the policy Store");
      
      KeymgConfigurationManager configManager = KeymgConfigurationManager.getInstance();
       
      SymKeyPolicyStore policyStore = null;
      try
      {
         policyStore = (SymKeyPolicyStore) policyStoreClass.newInstance();
      }
      catch (Exception e)
      {
         throw new ServletException(e);
      }
      KeymgConfigurationManager.setPolicyStore(policyStore);
      
      //Set the ServerID
      String serverIDStr = config.getInitParameter("serverID");
      if(serverIDStr != null && !serverIDStr.isEmpty())
      {
         serverID = serverIDStr;
      }
      
      processor = new SymKeyProcessor(configManager);
      processor.setServerID(serverID);
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   { 
      InputStream inputStream = req.getInputStream();
      Document doc = DocumentUtil.getDocument(inputStream);
      //Decrypt the document if needed
      doc = decrypt(doc);
      
      //Process the SKSML Request
      Parser parser = new Parser();
      try
      {
         parser.parse(DocumentUtil.getNodeAsStream(doc));
      }
      catch (XMLStreamException e)
      {
         log("Exception handling request:",e);
         throw new ServletException();
      }
      
      Object parsedObject = parser.getParsedObject();
      if( parsedObject instanceof SymkeyRequest)
      {
         SymkeyRequest symKeyRequest = (SymkeyRequest) parsedObject;
         doc = processor.process(symKeyRequest);
      }
      
      //Encrypt the document
      doc = encrypt(doc);
      resp.setContentType("text/xml;charset=utf-8");;
      OutputStream os = resp.getOutputStream();
      InputStream docStream = DocumentUtil.getNodeAsStream(doc);
      int ch = 0;
      while((ch=docStream.read()) != -1)
      {
         os.write(ch);
      }
   } 
   
   protected Document decrypt(Document doc)
   {
      return doc;
   }
   
   protected Document encrypt(Document doc)
   {
      return doc;
   }
}