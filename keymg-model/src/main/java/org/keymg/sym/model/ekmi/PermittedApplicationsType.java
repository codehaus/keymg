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
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Java class for PermittedApplicationsType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PermittedApplicationsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PermittedApplication" type="{http://docs.oasis-open.org/ekmi/2008/01}ApplicationsType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://docs.oasis-open.org/ekmi/2008/01}any use="required""/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class PermittedApplicationsType {
    protected List<ApplicationsType> permittedApplication = new ArrayList<ApplicationsType>();
    protected String any;

    /**
     * Gets the value of the permittedApplication property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the
     * permittedApplication property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getPermittedApplication().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ApplicationsType }
     * 
     * 
     */
    public List<ApplicationsType> getPermittedApplication() {
        return Collections.unmodifiableList(this.permittedApplication);
    }

    public void addPermittedApplication(ApplicationsType applicationType) {
        this.permittedApplication.add(applicationType);
    }

    /**
     * Gets the value of the any property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAny() {
        return any;
    }

    /**
     * Sets the value of the any property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setAny(String value) {
        this.any = value;
    }

}
