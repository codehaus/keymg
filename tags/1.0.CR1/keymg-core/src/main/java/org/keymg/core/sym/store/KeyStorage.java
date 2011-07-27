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
package org.keymg.core.sym.store;

/**
 * Store Symmetric Keys
 * @author anil@apache.org
 * @since Jul 14, 2011
 */
public interface KeyStorage
{
   /**
    * Initialize
    */
   void initialize() throws KeyStorageException;
   
   /**
    * Store a symmetric key
    * @param symmetricKey bytes representing the key
    * @param globalKeyID the global key id
    * @return
    * @throws KeyStorageException
    */
   boolean store(byte[] symmetricKey, String globalKeyID) throws KeyStorageException;
   
   /**
    * Retrieve the symmetric key
    * @param globalKeyID
    * @return
    * @throws KeyStorageException
    */
   byte[] retrieve(String globalKeyID) throws KeyStorageException;
   
   /**
    * Shutdown
    */
   void shutdown() throws KeyStorageException;
}