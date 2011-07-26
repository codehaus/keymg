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



/**
 * 
 *                 This is a required element that allows companies to define 
 *                 how the symmetric key may be used.  It will be included in 
 *                 the KUP object returned with the symmetric key.  Applications 
 *                 must parse through the permissions before using the key and 
 *                 only allow what is permitted for that permission type.  
 *                 
 *                 All "Permitted..." sub-elements - except for the Other element - 
 *                 are required.  However, if the attribute "any" for each of the 
 *                 "Permitted..." sub-elements is set to "true", then the xsi:nil 
 *                 attribute must also be set to "true" and the sub-element must 
 *                 be empty.  If the value of the "any" attribute for a specific
 *                 "Permitted..." sub-element is "false", then at least one child 
 *                 element must exist for the "Permitted..." sub-element and the
 *                 xsi:nil attribute must not exist.
 *             
 * 
 * <p>Java class for PermissionsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PermissionsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PermittedApplications" type="{http://docs.oasis-open.org/ekmi/2008/01}PermittedApplicationsType"/>
 *         &lt;element name="PermittedDates" type="{http://docs.oasis-open.org/ekmi/2008/01}PermittedDatesType"/>
 *         &lt;element name="PermittedDays" type="{http://docs.oasis-open.org/ekmi/2008/01}PermittedDaysType"/>
 *         &lt;element name="PermittedDuration" type="{http://docs.oasis-open.org/ekmi/2008/01}PermittedDurationType"/>
 *         &lt;element name="PermittedLevels" type="{http://docs.oasis-open.org/ekmi/2008/01}PermittedLevelsType"/>
 *         &lt;element name="PermittedLocations" type="{http://docs.oasis-open.org/ekmi/2008/01}PermittedLocationsType"/>
 *         &lt;element name="PermittedNumberOfTransactions" type="{http://docs.oasis-open.org/ekmi/2008/01}PermittedNumberOfTransactionsType"/>
 *         &lt;element name="PermittedTimes" type="{http://docs.oasis-open.org/ekmi/2008/01}PermittedTimesType"/>
 *         &lt;element name="PermittedUses" type="{http://docs.oasis-open.org/ekmi/2008/01}PermittedUsesType"/>
 *         &lt;element name="Other" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class PermissionsType 
{

    protected PermittedApplicationsType permittedApplications;
    protected PermittedDatesType permittedDates;
    protected PermittedDaysType permittedDays;
    protected PermittedDurationType permittedDuration;
    protected PermittedLevelsType permittedLevels;
    protected PermittedLocationsType permittedLocations;
    protected PermittedNumberOfTransactionsType permittedNumberOfTransactions;
    protected PermittedTimesType permittedTimes;
    protected PermittedUsesType permittedUses;
    protected Object other;

    /**
     * Gets the value of the permittedApplications property.
     * 
     * @return
     *     possible object is
     *     {@link PermittedApplicationsType }
     *     
     */
    public PermittedApplicationsType getPermittedApplications() {
        return permittedApplications;
    }

    /**
     * Sets the value of the permittedApplications property.
     * 
     * @param value
     *     allowed object is
     *     {@link PermittedApplicationsType }
     *     
     */
    public void setPermittedApplications(PermittedApplicationsType value) {
        this.permittedApplications = value;
    }

    /**
     * Gets the value of the permittedDates property.
     * 
     * @return
     *     possible object is
     *     {@link PermittedDatesType }
     *     
     */
    public PermittedDatesType getPermittedDates() {
        return permittedDates;
    }

    /**
     * Sets the value of the permittedDates property.
     * 
     * @param value
     *     allowed object is
     *     {@link PermittedDatesType }
     *     
     */
    public void setPermittedDates(PermittedDatesType value) {
        this.permittedDates = value;
    }

    /**
     * Gets the value of the permittedDays property.
     * 
     * @return
     *     possible object is
     *     {@link PermittedDaysType }
     *     
     */
    public PermittedDaysType getPermittedDays() {
        return permittedDays;
    }

    /**
     * Sets the value of the permittedDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link PermittedDaysType }
     *     
     */
    public void setPermittedDays(PermittedDaysType value) {
        this.permittedDays = value;
    }

    /**
     * Gets the value of the permittedDuration property.
     * 
     * @return
     *     possible object is
     *     {@link PermittedDurationType }
     *     
     */
    public PermittedDurationType getPermittedDuration() {
        return permittedDuration;
    }

    /**
     * Sets the value of the permittedDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link PermittedDurationType }
     *     
     */
    public void setPermittedDuration(PermittedDurationType value) {
        this.permittedDuration = value;
    }

    /**
     * Gets the value of the permittedLevels property.
     * 
     * @return
     *     possible object is
     *     {@link PermittedLevelsType }
     *     
     */
    public PermittedLevelsType getPermittedLevels() {
        return permittedLevels;
    }

    /**
     * Sets the value of the permittedLevels property.
     * 
     * @param value
     *     allowed object is
     *     {@link PermittedLevelsType }
     *     
     */
    public void setPermittedLevels(PermittedLevelsType value) {
        this.permittedLevels = value;
    }

    /**
     * Gets the value of the permittedLocations property.
     * 
     * @return
     *     possible object is
     *     {@link PermittedLocationsType }
     *     
     */
    public PermittedLocationsType getPermittedLocations() {
        return permittedLocations;
    }

    /**
     * Sets the value of the permittedLocations property.
     * 
     * @param value
     *     allowed object is
     *     {@link PermittedLocationsType }
     *     
     */
    public void setPermittedLocations(PermittedLocationsType value) {
        this.permittedLocations = value;
    }

    /**
     * Gets the value of the permittedNumberOfTransactions property.
     * 
     * @return
     *     possible object is
     *     {@link PermittedNumberOfTransactionsType }
     *     
     */
    public PermittedNumberOfTransactionsType getPermittedNumberOfTransactions() {
        return permittedNumberOfTransactions;
    }

    /**
     * Sets the value of the permittedNumberOfTransactions property.
     * 
     * @param value
     *     allowed object is
     *     {@link PermittedNumberOfTransactionsType }
     *     
     */
    public void setPermittedNumberOfTransactions(PermittedNumberOfTransactionsType value) {
        this.permittedNumberOfTransactions = value;
    }

    /**
     * Gets the value of the permittedTimes property.
     * 
     * @return
     *     possible object is
     *     {@link PermittedTimesType }
     *     
     */
    public PermittedTimesType getPermittedTimes() {
        return permittedTimes;
    }

    /**
     * Sets the value of the permittedTimes property.
     * 
     * @param value
     *     allowed object is
     *     {@link PermittedTimesType }
     *     
     */
    public void setPermittedTimes(PermittedTimesType value) {
        this.permittedTimes = value;
    }

    /**
     * Gets the value of the permittedUses property.
     * 
     * @return
     *     possible object is
     *     {@link PermittedUsesType }
     *     
     */
    public PermittedUsesType getPermittedUses() {
        return permittedUses;
    }

    /**
     * Sets the value of the permittedUses property.
     * 
     * @param value
     *     allowed object is
     *     {@link PermittedUsesType }
     *     
     */
    public void setPermittedUses(PermittedUsesType value) {
        this.permittedUses = value;
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
