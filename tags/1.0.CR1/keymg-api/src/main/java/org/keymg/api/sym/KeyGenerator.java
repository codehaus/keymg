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
 
import org.keymg.api.sym.exceptions.SymKeyGenerationException;
import org.keymg.core.sym.SymKeyProcessor;
import org.keymg.core.sym.config.KeymgConfigurationManager;
import org.keymg.core.sym.policy.SymKeyPolicyStore;
import org.keymg.core.sym.util.DocumentUtil;
import org.keymg.sym.model.ekmi.SymkeyResponse;
import org.keymg.sym.model.ekmi.ValidResponseType;
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
   
   protected String serverID = null;
   
   protected SymKeyPolicyStore policyStore = null;
   
   public String getServerID()
   {
      return serverID;
   }

   public void setServerID(String serverID)
   {
      this.serverID = serverID;
   } 

   public SymKeyPolicyStore getPolicyStore()
   {
      return policyStore;
   }

   public void setPolicyStore(SymKeyPolicyStore policyStore)
   {
      this.policyStore = policyStore;
      KeymgConfigurationManager.setPolicyStore(policyStore);
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
      if( keyID == null )
         throw new IllegalArgumentException( "keyID is null" );
      
      if( serverID == null )
         throw new IllegalArgumentException( "serverID is null" );
      
      if( policyStore == null )
         throw new IllegalArgumentException( "policyStore is null" );
      
      SymKeyProcessor processor = new SymKeyProcessor(configurationManager); 
      processor.setServerID(serverID); 
      ValidResponseType validResponseType = processor.generate(keyID);
      
      SymkeyResponse response = new SymkeyResponse();
      response.add(validResponseType);
      try
      {
         return DocumentUtil.create(response.toString());
      }
      catch (Exception e)
      {
         throw new SymKeyGenerationException(e);
      }
   } 
}