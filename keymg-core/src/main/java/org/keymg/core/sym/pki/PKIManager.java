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
package org.keymg.core.sym.pki;

import java.security.KeyPair;
import java.security.PublicKey;
import java.security.cert.Certificate;

/**
 * <p>
 * Obtain the {@code PublicKey} for a domain id
 * </p>
 * @author anil@apache.org
 * @since Jun 14, 2010
 */
public interface PKIManager
{
   /**
    * Given a domain id, get the key pair
    * @param domainID
    * @return
    * @throws PKIRepositoryException
    */
   KeyPair getKeyPair( String domainID )  throws PKIRepositoryException;
   
   /**
    * Given a domain id, return the public key
    * @param domainID
    * @return
    * @throws PKIRepositoryException
    */
   PublicKey getPublicKey( String domainID ) throws PKIRepositoryException;
   
   /**
    * Register a x509 certificate for a domain id
    * @param domainID
    * @param cert
    * @throws PKIRepositoryException
    */
   void register( String domainID, Certificate cert ) throws PKIRepositoryException;
}