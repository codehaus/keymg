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
package org.keymg.core.sym.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.keymg.core.sym.Base64;
import org.keymg.core.sym.SymKeyConstants;

/**
 * <p>
 * Utility used in symmetric key generation.
 * </p>
 * 
 * @author anil@apache.org
 * @since Jun 7, 2010
 */
public class SymKeyGenUtil {
    /**
     * <p>
     * Given a symmetric key, base64 encode it, to satisfy the xml dsig/enc requirements
     * </p>
     * 
     * @param bytesToBeEncoded
     * @return
     */
    public static String base64EncodeSymmetricKeyAsString(byte[] bytesToBeEncoded) {
        return Base64.encodeBytes(bytesToBeEncoded);
    }

    /**
     * <p>
     * Given a symmetric key, base64 encode it, to satisfy the xml dsig/enc requirements
     * </p>
     * 
     * @param bytesToBeEncoded
     * @return
     */
    public static byte[] base64EncodeSymmetricKeyAsBytes(byte[] bytesToBeEncoded) {
        return Base64.encodeBytesToBytes(bytesToBeEncoded);
    }

    /**
     * <p>
     * Given a symmetric key, base64 decode it, to satisfy the xml dsig requirements
     * </p>
     * 
     * @param bytesToBeEncoded
     * @return
     */
    public static byte[] base64DecodeSymmetricKey(byte[] encodedBytes) {
        return Base64.encodeBytesToBytes(encodedBytes);
    }

    /**
     * Get the default RSA Key Pair
     * 
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair getRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(SymKeyConstants.EncryptionAlgorithms.RSA.get());
        return keyPairGenerator.generateKeyPair();
    }
}
