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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element ref="{http://docs.oasis-open.org/ekmi/2008/01}Symkey" maxOccurs="unbounded"/>
 *           &lt;element ref="{http://docs.oasis-open.org/ekmi/2008/01}SymkeyWorkInProgress" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{http://docs.oasis-open.org/ekmi/2008/01}SymkeyError" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element ref="{http://docs.oasis-open.org/ekmi/2008/01}SymkeyWorkInProgress" maxOccurs="unbounded"/>
 *           &lt;element ref="{http://docs.oasis-open.org/ekmi/2008/01}SymkeyError" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element ref="{http://docs.oasis-open.org/ekmi/2008/01}SymkeyError" maxOccurs="unbounded"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;any minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * @author anil@apache.org
 * @since Aug 24, 2009
 */ 
public class SymkeyResponse 
{
    protected List< ValidResponseType > responseList = new ArrayList< ValidResponseType >();
    
    public void addAll( Collection<ValidResponseType> responses)
    {
    	responseList.addAll( responses );
    }
    
    public void add( ValidResponseType validResponse )
    {
    	responseList.add( validResponse );
    }

    public List< ValidResponseType > getResponse() 
    {
        return Collections.unmodifiableList( this.responseList );
    }

	@Override
	public String toString() 
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<ekmi:SymkeyResponse ").append( "xmlns:ekmi=\'http://docs.oasis-open.org/ekmi/2008/01\' ");
		builder.append( "xmlns:xenc=\'http://www.w3.org/2001/04/xmlenc#\' ").append( " xmlns:xsi=\'http://www.w3.org/2001/XMLSchema-instance\'> ");
		
		for( ValidResponseType validResponseType : responseList )
		{
		   builder.append( validResponseType.toString() );
		}
		builder.append( "</ekmi:SymkeyResponse>" );
		return builder.toString();
	}   
}