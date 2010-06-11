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
package org.keymg.sym.model.ekmi;

/**
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class KeyUsePolicyType 
{
   private TwoPartIDType keyUsePolicyID;
   private String policyName;
   private KeyClassType keyClass;
   private KeyAlgorithmType keyAlgorithm;
   private KeySizeType keySize;
   private StatusType status;
   private PermissionsType permissions;
   
   public TwoPartIDType getKeyUsePolicyID() 
   {
      return keyUsePolicyID;
   }
   
   public void setKeyUsePolicyID(TwoPartIDType keyUsePolicyID) 
   {
      this.keyUsePolicyID = keyUsePolicyID;
   }
   
   public String getPolicyName() 
   {
      return policyName;
   }
   
   public void setPolicyName(String policyName) 
   {
      this.policyName = policyName;
   }
   
   public KeyClassType getKeyClass() 
   {
      return keyClass;
   }
   
   public void setKeyClass(KeyClassType keyClass) 
   {
      this.keyClass = keyClass;
   }
   
   public KeyAlgorithmType getKeyAlgorithm() 
   {
      return keyAlgorithm;
   }
   
   public void setKeyAlgorithm(KeyAlgorithmType keyAlgorithm) 
   {
      this.keyAlgorithm = keyAlgorithm;
   }
   
   public KeySizeType getKeySize() 
   {
      return keySize;
   }
   
   public void setKeySize(KeySizeType keySize) 
   {
      this.keySize = keySize;
   }
   
   public StatusType getStatus() 
   {
      return status;
   }
   
   public void setStatus(StatusType status) 
   {
      this.status = status;
   }
   
   public PermissionsType getPermissions() 
   {
      return permissions;
   }
   
   public void setPermissions(PermissionsType permissions) 
   {
      this.permissions = permissions;
   }

   @Override
   public String toString()
   {
      StringBuilder builder = new StringBuilder();
      builder.append("<ekmi:KeyUsePolicy>");
      if( this.keyUsePolicyID != null )
         builder.append( keyUsePolicyID.toString() );
      if( this.policyName != null )
         builder.append( "<ekmi:PolicyName>" ).append( policyName ).append( "</ekmi:PolicyName>" );
      
      if( this.keyClass != null )
         builder.append( keyClass.toString() );
      
      builder.append( this.keyAlgorithm.toString() );
      
      if( this.keySize != null )
         builder.append( keySize.toString() );

      if( this.status != null )
         builder.append( status.toString() );
      if( this.permissions != null )
         builder.append( permissions.toString() );
      
      builder.append("</ekmi:KeyUsePolicy>");
      return builder.toString();
   } 
}