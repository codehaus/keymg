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
package org.keymg.core.sym.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Simple implementation of {@link KeyStorage}
 * 
 * @author anil@apache.org
 * @since Jul 14, 2011
 */
public class SimpleFileBasedKeyStorage implements KeyStorage {
    private static Logger log = Logger.getLogger(SimpleFileBasedKeyStorage.class.getName());

    private KeyEntry keys = null;

    private String fileName = "keystore.dat";

    /**
     * @see KeyStorage#store(byte[], String)
     */
    public synchronized boolean store(byte[] symmetricKey, String globalKeyID) throws KeyStorageException {
        keys.store(symmetricKey, globalKeyID);
        return true;
    }

    /**
     * @see KeyStorage#retrieve(String)
     */
    public byte[] retrieve(String globalKeyID) throws KeyStorageException {
        return keys.retrieve(globalKeyID);
    }

    /**
     * @see KeyStorage#initialize()
     */
    public void initialize() throws KeyStorageException {
        File theFile = new File(fileName);
        if (theFile.exists() == false) {
            URL storedFile = SecurityActions.loadResource(getClass(), fileName);
            if (storedFile == null) {
                keys = new KeyEntry();
                return;
            }
        }
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {

            if (theFile != null)
                fis = new FileInputStream(theFile);

            in = new ObjectInputStream(fis);
            keys = (KeyEntry) in.readObject();
            in.close();
        } catch (IOException ex) {
            log.log(Level.WARNING, "Unable to initialize the key storage", ex);
            throw new KeyStorageException("Unable to initialize the Key Storage");
        } catch (ClassNotFoundException ex) {
            log.log(Level.WARNING, "Unable to initialize the key storage", ex);
            throw new KeyStorageException("Unable to initialize the Key Storage");
        }
    }

    /**
     * @see KeyStorage#shutdown()
     */
    public void shutdown() throws KeyStorageException {
        File file = new File(fileName);
        if (file.exists() == false) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new KeyStorageException(e);
            }
        }

        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);
            out.writeObject(keys);
            out.close();
        } catch (IOException ex) {
            log.log(Level.WARNING, "Unable to shutdown the key storage", ex);
            throw new KeyStorageException();
        }
    }

    private static class KeyEntry implements Serializable {
        private static final long serialVersionUID = 1L;
        private Map<String, byte[]> keyMap = new ConcurrentHashMap<String, byte[]>();

        public boolean store(byte[] symmetricKey, String globalKeyID) throws KeyStorageException {
            keyMap.put(globalKeyID, symmetricKey);
            return true;
        }

        public byte[] retrieve(String globalKeyID) throws KeyStorageException {
            return keyMap.get(globalKeyID);
        }
    }
}