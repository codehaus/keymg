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
package org.keymg.core.sym.parse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class ParserUtil 
{
   public static Date parseDate( String dateStr ) throws ParseException
   { 
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd" ) ;
      return dateFormat.parse( dateStr );  
   } 

   public static Date parseTime( String timeStr ) throws ParseException
   {
      SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss" ) ;
      return dateFormat.parse( timeStr ); 
   } 
}
