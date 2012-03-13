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
 * =========
 * 
 * <xsd:complexType name="KeyUsePolicyType" mixed="true"> <xsd:annotation> <xsd:documentation> The KeyUsePolicyType document is
 * returned as part of the response to a request for a symmetric key from a Symmetric Key Services (SKS) server. The KUP tells
 * the client how it must use the associated symmetric key. At least one permission-type will provide the policy definition.
 * </xsd:documentation> </xsd:annotation> <xsd:sequence> <xsd:element name="KeyUsePolicyID" type="tns:TwoPartIDType">
 * <xsd:annotation> <xsd:documentation> The unique Policy ID of the KeyUsePolicy is a concatenation of the DomainID and the
 * unique key-use policy ID within that domain. It is a 41-byte ASCII decimal value. </xsd:documentation> </xsd:annotation>
 * </xsd:element> <xsd:element name="PolicyName"> <xsd:annotation> <xsd:documentation> The name of the KeyUsePolicy, as defined
 * by the enterprise running this Symmetric Key Management System (SKMS). </xsd:documentation> </xsd:annotation>
 * <xsd:simpleType> <xsd:restriction base="xsd:string"> <xsd:maxLength value="255"/> </xsd:restriction> </xsd:simpleType>
 * </xsd:element> <xsd:element name="KeyClass" type="tns:KeyClassType"> <xsd:annotation> <xsd:documentation> A user-defined
 * class for keys generated with this KeyUsePolicy. </xsd:documentation> </xsd:annotation> </xsd:element> <xsd:element
 * name="KeyAlgorithm" type="tns:EncryptionAlgorithmType"> <xsd:annotation> <xsd:documentation> The type of algorithm used by
 * this symmetric key policy. </xsd:documentation> </xsd:annotation> </xsd:element> <xsd:element name="KeySize"
 * type="tns:KeySizeType"> <xsd:annotation> <xsd:documentation> The size of the symmetric encryption key. </xsd:documentation>
 * </xsd:annotation> </xsd:element> <xsd:element name="Status" type="tns:StatusType"> <xsd:annotation> <xsd:documentation> An
 * indicator if the KeyUsePolicy is currently "Active", "Default", "Inactive" or "Other". </xsd:documentation> </xsd:annotation>
 * </xsd:element> <xsd:element name="Permissions" type="tns:PermissionsType" minOccurs="1"> <xsd:annotation> <xsd:documentation>
 * The permissions that define the policy for how this symmetric key may be used. </xsd:documentation> </xsd:annotation>
 * </xsd:element> </xsd:sequence> </xsd:complexType> ==================================
 * 
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class KeyUsePolicyType {
    private TwoPartIDType keyUsePolicyID;
    private String policyName;
    private KeyClassType keyClass;
    private KeyAlgorithmType keyAlgorithm;
    private KeySizeType keySize;
    private StatusType status;
    private PermissionsType permissions;

    public TwoPartIDType getKeyUsePolicyID() {
        return keyUsePolicyID;
    }

    public void setKeyUsePolicyID(TwoPartIDType keyUsePolicyID) {
        this.keyUsePolicyID = keyUsePolicyID;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public KeyClassType getKeyClass() {
        return keyClass;
    }

    public void setKeyClass(KeyClassType keyClass) {
        this.keyClass = keyClass;
    }

    public KeyAlgorithmType getKeyAlgorithm() {
        return keyAlgorithm;
    }

    public void setKeyAlgorithm(KeyAlgorithmType keyAlgorithm) {
        this.keyAlgorithm = keyAlgorithm;
    }

    public KeySizeType getKeySize() {
        return keySize;
    }

    public void setKeySize(KeySizeType keySize) {
        this.keySize = keySize;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public PermissionsType getPermissions() {
        return permissions;
    }

    public void setPermissions(PermissionsType permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<ekmi:KeyUsePolicy>");
        if (this.keyUsePolicyID != null)
            builder.append("<ekmi:KeyUsePolicyID>").append(keyUsePolicyID.getValue()).append("</ekmi:KeyUsePolicyID>");
        if (this.policyName != null)
            builder.append("<ekmi:PolicyName>").append(policyName).append("</ekmi:PolicyName>");

        if (this.keyClass != null)
            builder.append(keyClass.toString());

        builder.append(this.keyAlgorithm.toString());

        if (this.keySize != null)
            builder.append(keySize.toString());

        if (this.status != null)
            builder.append(status.toString());
        if (this.permissions != null)
            builder.append(permissions.toString());

        builder.append("</ekmi:KeyUsePolicy>");
        return builder.toString();
    }
}