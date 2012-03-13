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
package org.keymg.core.sym.config;

import java.io.File;
import java.net.URL;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.keymg.core.sym.SymKeyConstants;
import org.keymg.core.sym.pki.KeyStorePKIManager;
import org.keymg.core.sym.pki.PKIManager;
import org.keymg.core.sym.pki.PKIRepositoryException;
import org.keymg.core.sym.policy.SymKeyPolicyStore;
import org.keymg.core.sym.store.KeyStorage;
import org.keymg.core.sym.store.KeyStorageException;
import org.keymg.sym.model.ekmi.KeyAlgorithmType;
import org.keymg.sym.model.ekmi.KeySizeType;
import org.keymg.sym.model.ekmi.KeyUsePolicyType;
import org.keymg.sym.model.ekmi.StatusType;

/**
 * <p>
 * Acts as a central configuration manager.
 * </p>
 * <p>
 * This class is a singleton.
 * </p>
 * <p>
 * By default, the underlying {@code PKIManager} is a {@code KeyStorePKIManager} which looks for a keystore on the classpath at
 * "keystore/keymg.keystore" with a keypass of "keymg$"
 * </p>
 * <p>
 * Implementations should create their own instance of {@code PKIManager} and use the
 * {@code KeymgConfigurationManager#setPKIManager(PKIManager)} to set the {@code PKIManager} implementation.
 * </p>
 * 
 * @author anil@apache.org
 * @since Jun 7, 2010
 */
public class KeymgConfigurationManager implements SymKeyPolicyStore, PKIManager, KeyStorage {
    private static Logger log = Logger.getLogger(KeymgConfigurationManager.class.getName());

    private String serverID = "1111";

    private String keyAlgorithm = SymKeyConstants.AES_ALGORITHM_URI;

    private int keySize = 256;

    private static PKIManager pkiManager = null;

    private static SymKeyPolicyStore policyStore = null;

    private static KeyStorage keyStorage = null;

    private static KeymgConfigurationManager instance = new KeymgConfigurationManager();

    private KeymgConfigurationManager() {
    }

    /**
     * Only available method to create instance
     * @return
     */
    public static KeymgConfigurationManager getInstance() {
        return instance;
    }

    /**
     * Set the {@link PKIManager}
     * @param pki
     */
    public static void setPKIManager(PKIManager pki) {
        pkiManager = pki;
    }

    public static void setUp() {
        if (KeymgConfigurationManager.keyStorage == null) {
            log.log(Level.WARNING, "setUp called without a key storage");
            return;
        }

        try {
            KeymgConfigurationManager.keyStorage.initialize();
        } catch (KeyStorageException e) {
            log.log(Level.WARNING, "Cannot initialize key storage:", e);
        }
    }

    public static void destroy() {
        try {
            KeymgConfigurationManager.keyStorage.shutdown();
        } catch (KeyStorageException e) {
            log.log(Level.WARNING, "Cannot shutdown key storage:", e);
        }
    }

    /**
     * Set the {@link SymKeyPolicyStore}
     * @param policyStore
     */
    public static void setPolicyStore(SymKeyPolicyStore policyStore) {
        KeymgConfigurationManager.policyStore = policyStore;
    }

    /**
     * Set the {@link KeyStorage}
     * @param keyStorage
     */
    public static void setKeyStorage(KeyStorage keyStorage) {
        KeymgConfigurationManager.keyStorage = keyStorage;
    }

    /**
     * Get the Server ID
     * @return
     */
    public String getServerID() {
        return serverID;
    }

    /**
     * Set the Server ID
     * @param serverID
     */
    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    /**
     * Get the Key Algorithm
     * @return
     */
    public String getKeyAlgorithm() {
        return keyAlgorithm;
    }

    /**
     * Set the Key Algorithm
     * @param keyAlgorithm
     */
    public void setKeyAlgorithm(String keyAlgorithm) {
        this.keyAlgorithm = keyAlgorithm;
    }

    /**
     * Get the Key Size
     * @return
     */
    public int getKeySize() {
        return keySize;
    }

    /**
     * Set the Key Size
     * @param keySize
     */
    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    /**
     * Get the {@link KeyUsePolicyType}
     * @return
     */
    public KeyUsePolicyType getKeyUsePolicyType() {
        KeyUsePolicyType keyUsePolicy = new KeyUsePolicyType();
        keyUsePolicy.setKeyAlgorithm(KeyAlgorithmType.reverse(keyAlgorithm));
        keyUsePolicy.setKeySize(new KeySizeType(keySize));
        keyUsePolicy.setStatus(StatusType.ACTIVE);
        return keyUsePolicy;
    }

    /**
     * Get the {@link PublicKey} for a domain
     * @param domainID the ID of the domain
     * @return the {@link PublicKey}
     * @throws PKIRepositoryException
     */
    public PublicKey getPublicKeyForDomain(String domainID) throws PKIRepositoryException {
        ensureKeyStore();
        return pkiManager.getPublicKey(domainID);
    }

    /**
     * Get the {@link KeyUsePolicyType}
     * @param keyUsePolicyID The id of the {@link KeyUsePolicyType}
     * @return {@link KeyUsePolicyType}
     */
    public KeyUsePolicyType getKeyUsePolicy(String keyUsePolicyID) {
        if (policyStore == null)
            throw new RuntimeException("Policy Store not set");
        return policyStore.getKeyUsePolicy(keyUsePolicyID);
    }

    /**
     * Get the {@link KeyUsePolicyType} given the domain id
     * @param domainID the id of the domain
     * @return {@link KeyUsePolicyType}
     */
    public KeyUsePolicyType getDefaultKeyUsePolicy(String domainID) {
        if (policyStore == null)
            throw new RuntimeException("Policy Store not set");
        return policyStore.getDefaultKeyUsePolicy(domainID);
    }

    /**
     * Get {@link KeyUsePolicyType} given a key class type
     * @param keyClassType the Key Class Type string
     * @return {@link KeyUsePolicyType}
     */
    public KeyUsePolicyType getKeyUsePolicyForKeyClassType(String keyClassType) {
        if (policyStore == null)
            throw new RuntimeException("Policy Store not set");
        return policyStore.getKeyUsePolicyForKeyClassType(keyClassType);
    }

    /**
     * Get the {@link KeyPair} given a domain
     * @param domainID the id of the domain for which we need to retrieve the {@link KeyPair}
     * @return {@link KeyPair}
     */
    public KeyPair getKeyPair(String domainID) throws PKIRepositoryException {
        ensureKeyStore();
        return KeymgConfigurationManager.pkiManager.getKeyPair(domainID);
    }

    /**
     * Get the {@link PublicKey} given a domain
     * @param domainID the id of the domain
     * @return {@link PublicKey}
     */
    public PublicKey getPublicKey(String domainID) throws PKIRepositoryException {
        ensureKeyStore();
        return KeymgConfigurationManager.pkiManager.getPublicKey(domainID);
    }

    /**
     * Register a x509 certificate for a domain
     * @param domainID the id of the domain
     * @param cert the certificate
     * @throws PKIRepositoryException
     */
    public void register(String domainID, Certificate cert) throws PKIRepositoryException {
        ensureKeyStore();
        KeymgConfigurationManager.pkiManager.register(domainID, cert);
    }

    /**
     * @see KeyStorage#initialize()
     */
    public void initialize() throws KeyStorageException {
        if (KeymgConfigurationManager.keyStorage == null)
            throw new KeyStorageException("Key Storage has not been set");
        KeymgConfigurationManager.keyStorage.initialize();
    }

    /**
     * @see KeyStorage#store(byte[], String)
     */
    public boolean store(byte[] symmetricKey, String globalKeyID) throws KeyStorageException {
        if (KeymgConfigurationManager.keyStorage == null)
            throw new KeyStorageException("Key Storage has not been set");
        return KeymgConfigurationManager.keyStorage.store(symmetricKey, globalKeyID);
    }

    /**
     * @see KeyStorage#retrieve(String)
     */
    public byte[] retrieve(String globalKeyID) throws KeyStorageException {
        if (KeymgConfigurationManager.keyStorage == null)
            throw new KeyStorageException("Key Storage has not been set");
        return KeymgConfigurationManager.keyStorage.retrieve(globalKeyID);
    }

    /**
     * @see KeyStorage#shutdown()
     */
    public void shutdown() throws KeyStorageException {
        if (KeymgConfigurationManager.keyStorage == null)
            throw new KeyStorageException("Key Storage has not been set");
        KeymgConfigurationManager.keyStorage.shutdown();
    }

    private void ensureKeyStore() throws PKIRepositoryException {
        if (KeymgConfigurationManager.pkiManager == null) {
            URL keyStoreURL = SecurityActions.loadResource(getClass(), "keystore/keymg.keystore");
            if (keyStoreURL == null)
                throw new PKIRepositoryException("keyStoreURL is null");

            // Let us build the default KeyStorePKIManager
            pkiManager = new KeyStorePKIManager(new File(keyStoreURL.getPath()), "keymg$".toCharArray());
        }
    }
}