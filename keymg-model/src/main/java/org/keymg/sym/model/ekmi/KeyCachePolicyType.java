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

import java.util.Date;


/**
 * 
 *                 The KeyCachePolicyType document is returned as part of
 *                 the response to a request for a key-caching policy from a
 *                 Symmetric Key Services (SKS) server.  The KCP tells the
 *                 SKMS client if it may cache symmetric keys, and if so, how 
 *                 many new and used keys it may cache, for how long, etc.
 *             
 * 
 * <p>Java class for KeyCachePolicyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="KeyCachePolicyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="KeyCachePolicyID" type="{http://docs.oasis-open.org/ekmi/2008/01}TwoPartIDType"/>
 *         &lt;element name="PolicyName">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *               &lt;whiteSpace value="preserve"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Description">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="2048"/>
 *               &lt;whiteSpace value="preserve"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="KeyClass" type="{http://docs.oasis-open.org/ekmi/2008/01}KeyClassType"/>
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="PolicyCheckInterval">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="2592000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Status" type="{http://docs.oasis-open.org/ekmi/2008/01}StatusType"/>
 *         &lt;element name="NewKeysCacheDetail" type="{http://docs.oasis-open.org/ekmi/2008/01}KeyCacheDetailType" minOccurs="0"/>
 *         &lt;element name="UsedKeysCacheDetail" type="{http://docs.oasis-open.org/ekmi/2008/01}KeyCacheDetailType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */ 
/**
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class KeyCachePolicyType 
{ 
	private TwoPartIDType keyCachePolicyID;
	
	private String policyName;
	
	private String description;
	
	private KeyClassType keyClassType;
	
	private Date startDate;
	
	private Date endDate;
	
	private NonNegativeInteger policyCheckInterval;
    
	private StatusType status;
	
	private KeyCacheDetailType newKeysCacheDetail;
	
	private KeyCacheDetailType usedKeysCacheDetail;

	public TwoPartIDType getKeyCachePolicyID() 
	{
		return keyCachePolicyID;
	}

	public void setKeyCachePolicyID(TwoPartIDType keyCachePolicyID) 
	{
		this.keyCachePolicyID = keyCachePolicyID;
	}

	public String getPolicyName() 
	{
		return policyName;
	}

	public void setPolicyName(String policyName) 
	{
		this.policyName = policyName;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public KeyClassType getKeyClassType() 
	{
		return keyClassType;
	}

	public void setKeyClassType(KeyClassType keyClassType) 
	{
		this.keyClassType = keyClassType;
	}

	public Date getStartDate() 
	{
		return startDate;
	}

	public void setStartDate(Date startDate) 
	{
		this.startDate = startDate;
	}

	public Date getEndDate() 
	{
		return endDate;
	}

	public void setEndDate(Date endDate) 
	{
		this.endDate = endDate;
	}

	public NonNegativeInteger getPolicyCheckInterval() 
	{
		return policyCheckInterval;
	}

	public void setPolicyCheckInterval(NonNegativeInteger policyCheckInterval) 
	{
		this.policyCheckInterval = policyCheckInterval;
	}

	public StatusType getStatus() 
	{
		return status;
	}

	public void setStatus(StatusType status) 
	{
		this.status = status;
	}

	public KeyCacheDetailType getNewKeysCacheDetail() 
	{
		return newKeysCacheDetail;
	}

	public void setNewKeysCacheDetail(KeyCacheDetailType newKeysCacheDetail) 
	{
		this.newKeysCacheDetail = newKeysCacheDetail;
	}

	public KeyCacheDetailType getUsedKeysCacheDetail() 
	{
		return usedKeysCacheDetail;
	}

	public void setUsedKeysCacheDetail(KeyCacheDetailType usedKeysCacheDetail) 
	{
		this.usedKeysCacheDetail = usedKeysCacheDetail;
	}
}