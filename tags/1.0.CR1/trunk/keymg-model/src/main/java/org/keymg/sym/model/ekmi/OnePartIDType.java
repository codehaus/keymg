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

import java.util.regex.Pattern;

import org.keymg.sym.exceptions.StringFormatException;

/**
 * 
 <xsd:simpleType name="OnePartIDType">
        <xsd:annotation>
            <xsd:documentation>
                An identifier type that consists of only a 
                single ASCII decimal element that ranges from 
                the value 1 to 18446744073709551615.
            </xsd:documentation>
        </xsd:annotation>
        <xsd:restriction base="xsd:string">
            <xsd:maxLength value="20"/>
            <xsd:pattern value="[1-9][0-9]{0,19}"/>
            <xsd:whiteSpace value="collapse"/>
        </xsd:restriction>
    </xsd:simpleType>
 */
/**
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class OnePartIDType 
{
	private String value;

	public OnePartIDType()
	{ 	
	}
	
	public OnePartIDType(String value)
	{
		validate(value);
		this.value = value;
	}
	
	public String getValue()
	{
		return this.value;
	}
	
	public void setValue( String value )
	{
		validate( value );
		this.value = value;
	}
	
	/**
	 * Validate the input string to the constraints
	 * @param val
	 */
	protected void validate(String val)
	{
		if(val == null)
			throw new IllegalArgumentException( " val is null" );
		
		val = val.trim();
		
		int length = val.length();
		
		if( length < 3 )
			throw new StringFormatException( "Need at least 3 characters:" + length );
		
		if( length > 41 )
		    throw new StringFormatException( "At most 41 character allowed:" + length );
		
		//Validate the String pattern 
		if( Pattern.matches( "[1-9][0-9]{0,19}",  val ) == false )
			throw new StringFormatException( "pattern does not match [1-9][0-9]{0,19}:" + val );
	}

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((value == null) ? 0 : value.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      OnePartIDType other = (OnePartIDType) obj;
      if (value == null)
      {
         if (other.value != null)
            return false;
      }
      else if (!value.equals(other.value))
         return false;
      return true;
   }
}