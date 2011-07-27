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

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Privileged Blocks
 * @author anil@apache.org
 * @since Jul 11, 2011
 */
class SecurityActions
{
   static Class<?> load(final Class<?> theAskingClass, final String fqn)
   {
      return AccessController.doPrivileged(new PrivilegedAction<Class<?>>()
      { 
         public Class<?> run()
         { 
            Class<?> clazz = null;
            ClassLoader cl = theAskingClass.getClassLoader();
            try
            {
               clazz = cl.loadClass(fqn);
            }
            catch(Exception e)
            {
               cl = Thread.currentThread().getContextClassLoader();
               try
               {
                  clazz = cl.loadClass(fqn);
               }
               catch (Exception e1)
               { 
               }
            }
            return clazz;
         }
      }); 
   }

}