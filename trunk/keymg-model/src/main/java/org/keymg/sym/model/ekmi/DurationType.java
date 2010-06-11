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

import java.math.BigInteger;

/**
 * 
   <xsd:simpleType name="DurationType">
        <xsd:annotation>
            <xsd:documentation>
                The number of seconds a symmetric key may be used for, once
                the client application starts using the key.
            </xsd:documentation>
        </xsd:annotation>
        <xsd:restriction base="xsd:positiveInteger">
            <xsd:minInclusive value="1"/>
            <xsd:maxInclusive value="18446744073709551615"/>
        </xsd:restriction>
    </xsd:simpleType>
 */
/**
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class DurationType 
{
	BigInteger duration;

	public BigInteger getDuration() 
	{
		return duration;
	}

	public void setDuration(BigInteger duration) 
	{
		this.duration = duration;
	}
	
}