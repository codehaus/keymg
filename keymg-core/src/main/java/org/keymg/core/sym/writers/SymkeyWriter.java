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
package org.keymg.core.sym.writers;

import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.keymg.core.sym.SymKeyConstants;
import org.keymg.sym.model.ekmi.ApplicationsType;
import org.keymg.sym.model.ekmi.CipherDataType;
import org.keymg.sym.model.ekmi.KeyClassType;
import org.keymg.sym.model.ekmi.KeyClassesType;
import org.keymg.sym.model.ekmi.KeyUsePolicyType;
import org.keymg.sym.model.ekmi.LevelClassificationType;
import org.keymg.sym.model.ekmi.PermissionsType;
import org.keymg.sym.model.ekmi.PermittedApplicationsType;
import org.keymg.sym.model.ekmi.PermittedDatesType;
import org.keymg.sym.model.ekmi.PermittedDurationType;
import org.keymg.sym.model.ekmi.PermittedLevelsType;
import org.keymg.sym.model.ekmi.PermittedDatesType.PermittedDate;
import org.keymg.sym.model.ekmi.PermittedDayType;
import org.keymg.sym.model.ekmi.PermittedDaysType;
import org.keymg.sym.model.ekmi.PermittedLocationsType;
import org.keymg.sym.model.ekmi.PermittedLocationsType.PermittedLocation;
import org.keymg.sym.model.ekmi.PermittedNumberOfTransactionsType;
import org.keymg.sym.model.ekmi.PermittedTimesType;
import org.keymg.sym.model.ekmi.PermittedTimesType.PermittedTime;
import org.keymg.sym.model.ekmi.PermittedUsesType;
import org.keymg.sym.model.ekmi.SymkeyRequest;
import org.keymg.sym.model.ekmi.SymkeyResponse;
import org.keymg.sym.model.ekmi.SymkeyType;
import org.keymg.sym.model.ekmi.ValidResponseType;

/**
 * Writer for the SymKey Object Model
 * 
 * @author anil@apache.org
 * @since Jul 14, 2011
 */
public class SymkeyWriter {
    XMLStreamWriter writer = null;

    public SymkeyWriter(OutputStream outStream) throws XMLStreamException {
        writer = getXMLStreamWriter(outStream);
    }

    /**
     * Write the {@link SymkeyRequest}
     * @param request
     * @throws XMLStreamException
     */
    public void write(SymkeyRequest request) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.PREFIX, SymKeyConstants.SYMKEY_REQUEST, SymKeyConstants.EKMI_NAMESPACE_URI);
        writer.writeNamespace(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI);
        List<String> globalKeyIDs = request.getGlobalKeyID();
        for (String globalKeyID : globalKeyIDs) {
            writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.GLOBAL_KEY_ID);
            writer.writeCharacters(globalKeyID);
            writer.writeEndElement();
        }

        KeyClassesType keyClassesType = request.getKeyClasses();
        if (keyClassesType != null) {
            write(keyClassesType);
        }

        writer.writeEndElement();
        writer.flush();
    }

    /**
     * Write the {@link SymkeyResponse}
     * @param response
     * @throws XMLStreamException
     */
    public void write(SymkeyResponse response) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.PREFIX, SymKeyConstants.SYMKEY_RESPONSE, SymKeyConstants.EKMI_NAMESPACE_URI);
        writer.writeNamespace(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI);
        writer.writeNamespace(SymKeyConstants.XENC_PREFIX, SymKeyConstants.XENC_NAMESPACE_URI);
        writer.writeNamespace(SymKeyConstants.XSI_PREFIX, SymKeyConstants.XSI_NAMESPACE_URI);

        List<ValidResponseType> responseTypes = response.getResponse();
        for (ValidResponseType responseType : responseTypes) {
            write(responseType);
        }
        writer.writeEndElement();
        writer.flush();
    }

    /**
     * Write the {@link ValidResponseType}
     * @param valid
     * @throws XMLStreamException
     */
    public void write(ValidResponseType valid) throws XMLStreamException {
        if (valid instanceof SymkeyType) {
            write((SymkeyType) valid);
            return;
        }
        throw new RuntimeException("Should not have reached here:" + valid.toString());
    }

    /**
     * Write the {@link SymkeyType}
     * @param symKey
     * @throws XMLStreamException
     */
    public void write(SymkeyType symKey) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.PREFIX, SymKeyConstants.SYMKEY, SymKeyConstants.EKMI_NAMESPACE_URI);

        writer.writeStartElement(SymKeyConstants.PREFIX, SymKeyConstants.SYMKEY_REQUEST_ID, SymKeyConstants.EKMI_NAMESPACE_URI);
        writer.writeCharacters(symKey.getSymkeyRequestID().getValue());
        writer.writeEndElement();

        writer.writeStartElement(SymKeyConstants.PREFIX, SymKeyConstants.GLOBAL_KEY_ID, SymKeyConstants.EKMI_NAMESPACE_URI);
        writer.writeCharacters(symKey.getGlobalKeyID().getValue());
        writer.writeEndElement();

        KeyUsePolicyType keyUsePolicy = symKey.getKeyUsePolicy();
        if (keyUsePolicy != null) {
            write(keyUsePolicy);
        }

        writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.ENCRYPTION_METHOD);
        writer.writeCharacters(symKey.getEncryptionMethod().get());
        writer.writeEndElement();

        CipherDataType cipherData = symKey.getCipherData();
        write(cipherData);

        writer.writeEndElement();
        writer.flush();
    }

    /**
     * Write the {@link KeyClassesType}
     * @param keyClassesType
     * @throws XMLStreamException
     */
    public void write(KeyClassesType keyClassesType) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.KEY_CLASSES);
        KeyClassType[] keyClassTypeArr = keyClassesType.getKeyClassType();
        for (KeyClassType keyClassType : keyClassTypeArr) {
            write(keyClassType);
        }
        writer.writeEndElement();
        writer.flush();
    }

    /**
     * Write the {@link KeyClassType}
     * @param keyClassType
     * @throws XMLStreamException
     */
    public void write(KeyClassType keyClassType) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.KEY_CLASS);
        writer.writeCharacters(keyClassType.getValue());
        writer.writeEndElement();
    }

    /**
     * Write the {@link KeyUsePolicyType}
     * @param keyUsePolicy
     * @throws XMLStreamException
     */
    public void write(KeyUsePolicyType keyUsePolicy) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.KEY_USE_POLICY);

        writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.KEY_USE_POLICY_ID);
        writer.writeCharacters(keyUsePolicy.getKeyUsePolicyID().getValue());
        writer.writeEndElement();

        writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.POLICY_NAME);
        writer.writeCharacters(keyUsePolicy.getPolicyName());
        writer.writeEndElement();

        writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.KEY_CLASS);
        writer.writeCharacters(keyUsePolicy.getKeyClass().getValue());
        writer.writeEndElement();

        writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.KEY_ALGORITHM);
        writer.writeCharacters(keyUsePolicy.getKeyAlgorithm().get());
        writer.writeEndElement();

        writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.KEY_SIZE);
        writer.writeCharacters(keyUsePolicy.getKeySize().getValue() + "");
        writer.writeEndElement();

        writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.STATUS);
        writer.writeCharacters(keyUsePolicy.getStatus().value());
        writer.writeEndElement();

        PermissionsType permissions = keyUsePolicy.getPermissions();
        if (permissions != null) {
            write(permissions);
        }

        writer.writeEndElement();
    }

    /**
     * Write the {@link PermissionsType}
     * @param permissions
     * @throws XMLStreamException
     */
    public void write(PermissionsType permissions) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMISSIONS);
        PermittedApplicationsType permittedApplications = permissions.getPermittedApplications();
        if (permittedApplications != null) {
            write(permittedApplications);
        }
        PermittedDatesType permittedDates = permissions.getPermittedDates();
        if (permittedDates != null) {
            write(permittedDates);
        }
        PermittedDaysType permittedDays = permissions.getPermittedDays();
        if (permittedDays != null) {
            write(permittedDays);
        }
        PermittedDurationType permittedDurations = permissions.getPermittedDuration();
        if (permittedDurations != null) {
            write(permittedDurations);
        }
        PermittedLevelsType permittedLevels = permissions.getPermittedLevels();
        if (permittedLevels != null) {
            write(permittedLevels);
        }
        PermittedLocationsType permittedLocations = permissions.getPermittedLocations();
        if (permittedLocations != null) {
            write(permittedLocations);
        }
        PermittedNumberOfTransactionsType permittedNumTxs = permissions.getPermittedNumberOfTransactions();
        if (permittedNumTxs != null) {
            write(permittedNumTxs);
        }
        PermittedTimesType permittedTimes = permissions.getPermittedTimes();
        if (permittedTimes != null) {
            write(permittedTimes);
        }
        PermittedUsesType permittedUses = permissions.getPermittedUses();
        if (permittedUses != null) {
            write(permittedUses);
        }
        writer.writeEndElement();
    }

    /**
     * Write the {@link PermittedApplicationsType}
     * @param permittedApplications
     * @throws XMLStreamException
     */
    public void write(PermittedApplicationsType permittedApplications) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMITTED_APPLICATIONS);

        List<ApplicationsType> permittedAppList = permittedApplications.getPermittedApplication();
        if (permittedAppList.size() > 0) {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "false");
        } else {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "true");
        }

        for (ApplicationsType app : permittedAppList) {
            write(app);
        }
        writer.writeEndElement();
    }

    /**
     * Write the {@link PermittedDatesType}
     * @param permittedDates
     * @throws XMLStreamException
     */
    public void write(PermittedDatesType permittedDates) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMITTED_DATES);
        List<PermittedDate> permittedDatesList = permittedDates.getPermittedDate();
        if (permittedDatesList.size() > 0) {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "false");
        } else {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "true");
        }
        for (PermittedDate app : permittedDatesList) {
            write(app);
        }
        writer.writeEndElement();
    }

    /**
     * Write the {@link PermittedDaysType}
     * @param permittedDays
     * @throws XMLStreamException
     */
    public void write(PermittedDaysType permittedDays) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMITTED_DAYS);
        List<PermittedDayType> permittedDaysList = permittedDays.getPermittedDay();
        if (permittedDaysList.size() > 0) {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "false");
        } else {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "true");
        }
        for (PermittedDayType app : permittedDaysList) {
            write(app);
        }
        writer.writeEndElement();
    }

    /**
     * Write the {@link PermittedDurationType}
     * @param permittedDuration
     * @throws XMLStreamException
     */
    public void write(PermittedDurationType permittedDuration) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMITTED_DURATION);
        BigInteger duration = permittedDuration.getValue();
        if (duration != null) {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "false");
        } else {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "true");
        }
        if (duration != null) {
            writer.writeCharacters(duration.toString());
        }
        writer.writeEndElement();
    }

    /**
     * Write the {@link PermittedLevelsType}
     * @param permittedLevels
     * @throws XMLStreamException
     */
    public void write(PermittedLevelsType permittedLevels) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMITTED_LEVELS);
        List<LevelClassificationType> permittedLevelsList = permittedLevels.getPermittedLevel();
        if (permittedLevelsList.size() > 0) {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "false");
        } else {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "true");
        }
        for (LevelClassificationType app : permittedLevelsList) {
            write(app);
        }
        writer.writeEndElement();
    }

    /**
     * Write the {@link PermittedLocationsType}
     * @param permittedLocations
     * @throws XMLStreamException
     */
    public void write(PermittedLocationsType permittedLocations) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMITTED_LOCATIONS);
        List<PermittedLocation> permittedLocationsList = permittedLocations.getPermittedLocation();
        if (permittedLocationsList.size() > 0) {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "false");
        } else {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "true");
        }
        for (PermittedLocation app : permittedLocationsList) {
            write(app);
        }
        writer.writeEndElement();
    }

    /**
     * Write the {@link PermittedNumberOfTransactionsType}
     * @param permittedApplications
     * @throws XMLStreamException
     */
    public void write(PermittedNumberOfTransactionsType permittedApplications) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMITTED_NUMBER_OF_TRANSACTIONS);

        BigInteger tx = permittedApplications.getValue();
        if (tx != null) {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "false");
            writer.writeCharacters(tx.toString());
        } else {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "true");
        }
        writer.writeEndElement();
    }

    /**
     * Write the {@link PermittedTimesType}
     * @param permittedtimes
     * @throws XMLStreamException
     */
    public void write(PermittedTimesType permittedtimes) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMITTED_TIMES);

        List<PermittedTime> permittedAppList = permittedtimes.getPermittedTime();
        if (permittedAppList.size() > 0) {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "false");
        } else {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "true");
        }

        for (PermittedTime app : permittedAppList) {
            write(app);
        }
        writer.writeEndElement();
    }

    /**
     * Write the {@link PermittedUsesType}
     * @param permittedUses
     * @throws XMLStreamException
     */
    public void write(PermittedUsesType permittedUses) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMITTED_USES);

        List<Serializable> permittedAppList = permittedUses.getContent();
        if (permittedAppList.size() > 0) {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "false");
        } else {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "true");
        }

        for (Serializable app : permittedAppList) {
            writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMITTED_USE);
            writer.writeCharacters(app.toString());
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }

    /**
     * Write the {@link PermittedDayType}
     * @param permittedDay
     * @throws XMLStreamException
     */
    public void write(PermittedDayType permittedDay) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMITTED_DATE);
        writer.writeCharacters(permittedDay.value());
        writer.writeEndElement();
    }

    /**
     * Write the {@link LevelClassificationType}
     * @param permittedLevel
     * @throws XMLStreamException
     */
    public void write(LevelClassificationType permittedLevel) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMITTED_LEVEL);
        writer.writeCharacters(permittedLevel.get());
        writer.writeEndElement();
    }

    /**
     * Write the {@link ApplicationsType}
     * @param permittedApplication
     * @throws XMLStreamException
     */
    public void write(ApplicationsType permittedApplication) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMITTED_APPLICATION);

        writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.APPLICATION_ID);
        writer.writeCharacters(permittedApplication.getApplicationID());
        writer.writeEndElement();

        writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.APPLICATION_NAME);
        writer.writeCharacters(permittedApplication.getApplicationName());
        writer.writeEndElement();

        writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.APPLICATION_VERSION);
        writer.writeCharacters(permittedApplication.getVersion());
        writer.writeEndElement();

        writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.APPLICATION_DIGEST_ALGORITHM);
        writer.writeCharacters(permittedApplication.getDigestAlgorithm());
        writer.writeEndElement();

        writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.APPLICATION_DIGEST_VALUE);
        writer.writeCharacters(new String(permittedApplication.getDigestValue()));
        writer.writeEndElement();

        writer.writeEndElement();
    }

    /**
     * Write the {@link PermittedLocation}
     * @param permittedLocation
     * @throws XMLStreamException
     */
    public void write(PermittedLocation permittedLocation) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMITTED_LOCATION);
        String locationName = permittedLocation.getLocationName();

        if (locationName != null) {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "false");

            writer.writeStartElement(SymKeyConstants.EKMI_NAMESPACE_URI, SymKeyConstants.LOCATION_NAME);
            writer.writeCharacters(locationName);
            writer.writeEndElement();
        } else {
            writer.writeAttribute(SymKeyConstants.PREFIX, SymKeyConstants.EKMI_NAMESPACE_URI, "any", "true");
        }

        // TODO: write other

        writer.writeEndElement();
    }

    /**
     * Write the {@link PermittedDate}
     * @param permittedDate
     * @throws XMLStreamException
     */
    public void write(PermittedDate permittedDate) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMITTED_DATE);

        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.START_DATE);
        writer.writeCharacters(permittedDate.getStartDate().toString());
        writer.writeEndElement();

        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.END_DATE);
        writer.writeCharacters(permittedDate.getEndDate().toString());
        writer.writeEndElement();

        writer.writeEndElement();
    }

    /**
     * Write the {@link PermittedTime}
     * @param permittedTime
     * @throws XMLStreamException
     */
    public void write(PermittedTime permittedTime) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.PERMITTED_TIME);

        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.START_TIME);
        writer.writeCharacters(permittedTime.getStartTime().toString());
        writer.writeEndElement();

        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.END_TIME);
        writer.writeCharacters(permittedTime.getEndTime().toString());
        writer.writeEndElement();

        writer.writeEndElement();
    }

    /**
     * Write the {@link CipherDataType}
     * @param cipherData
     * @throws XMLStreamException
     */
    public void write(CipherDataType cipherData) throws XMLStreamException {
        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.XENCConstants.CIPHER_DATA);

        writer.writeStartElement(SymKeyConstants.XENC_NAMESPACE_URI, SymKeyConstants.XENCConstants.CIPHER_VALUE);
        writer.writeCharacters(cipherData.getCipherValue());
        writer.writeEndElement();

        writer.writeEndElement();
    }

    /**
     * Get an {@code XMLEventWriter}
     * 
     * @param outStream
     * @return
     * @throws XMLStreamException
     * @throws ProcessingException
     */
    private XMLStreamWriter getXMLStreamWriter(final OutputStream outStream) throws XMLStreamException {
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        return xmlOutputFactory.createXMLStreamWriter(outStream, "UTF-8");
    }
}