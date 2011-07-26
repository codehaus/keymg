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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 
 *                 A list of physical locations of the client, where the key 
 *                 may be used.  This is specific to the application and may 
 *                 consist of GPS coordinates, Building numbers, secure rooms, 
 *                 cities, etc.  Its meaning is application-defined.
 *                 
 *                 When the "any" attribute is set to "true", no PermittedLocation
 *                 elements must appear in this element.
 *             
 * 
 * <p>Java class for PermittedLocationsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PermittedLocationsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PermittedLocation" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="LocationName">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="256"/>
 *                         &lt;whiteSpace value="preserve"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;group ref="{http://docs.oasis-open.org/ekmi/2008/01}LocationCoordinateGroup" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="Other" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://docs.oasis-open.org/ekmi/2008/01}any use="required""/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 *  
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class PermittedLocationsType {

    protected List<PermittedLocationsType.PermittedLocation> permittedLocation = new ArrayList<PermittedLocationsType.PermittedLocation>();
    protected String any;

    /**
     * Gets the value of the permittedLocation property. 
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PermittedLocationsType.PermittedLocation }
     * 
     * 
     */
    public List<PermittedLocationsType.PermittedLocation> getPermittedLocation() {  
        return Collections.unmodifiableList(this.permittedLocation);
    }
    
    public void addPermittedLocation(PermittedLocation pl)
    {
       permittedLocation.add(pl);
    }
    
    public boolean removePermittedLocation(PermittedLocation pl)
    {
       return permittedLocation.remove(pl);
    }

    /**
     * Gets the value of the any property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAny() {
        return any;
    }

    /**
     * Sets the value of the any property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAny(String value) {
        this.any = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="LocationName">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="256"/>
     *               &lt;whiteSpace value="preserve"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;group ref="{http://docs.oasis-open.org/ekmi/2008/01}LocationCoordinateGroup" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="Other" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */ 
    public static class PermittedLocation {
 
        protected String locationName;
         
        protected List<BigDecimal> locationCoordinateGroup = new ArrayList<BigDecimal>(); 
        protected Object other;

        public void addLocationCoordinationGroup(BigDecimal lcg)
        {
           locationCoordinateGroup.add(lcg);
        }
        /**
         * Gets the value of the locationName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLocationName() {
            return locationName;
        }

        /**
         * Sets the value of the locationName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLocationName(String value) {
            this.locationName = value;
        }

        /**
         * Gets the value of the locationCoordinateGroup property.
         *    
         */
        public List<BigDecimal> getLocationCoordinateGroup() { 
            return Collections.unmodifiableList(this.locationCoordinateGroup);
        }

        /**
         * Gets the value of the other property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getOther() {
            return other;
        }

        /**
         * Sets the value of the other property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setOther(Object value) {
            this.other = value;
        }
    } 
}