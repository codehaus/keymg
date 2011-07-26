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

import java.security.GeneralSecurityException;

/**
 * An exception to indicate issue in storing the generated key
 * @author anil@apache.org
 * @since Jul 14, 2011
 */
public class KeyStorageException extends GeneralSecurityException
{
   private static final long serialVersionUID = 1L;

   public KeyStorageException()
   {
      super(); 
   }

   public KeyStorageException(String message, Throwable cause)
   {
      super(message, cause); 
   }

   public KeyStorageException(String msg)
   {
      super(msg); 
   }

   public KeyStorageException(Throwable cause)
   {
      super(cause); 
   }
}