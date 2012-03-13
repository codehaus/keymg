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
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public enum KeyAlgorithmType {
    TRIPLE_DES("http://www.w3.org/2001/04/xmlenc#tripledes-cbc"), AES_128_CBS("http://www.w3.org/2001/04/xmlenc#aes128-cbc"), AES_192_CBS(
            "http://www.w3.org/2001/04/xmlenc#aes192-cbc"), AES_256_CBS("http://www.w3.org/2001/04/xmlenc#aes256-cbc");

    private String uri;

    KeyAlgorithmType(String uri) {
        this.uri = uri;
    }

    public String get() {
        return this.uri;
    }

    public static KeyAlgorithmType reverse(String uri) {
        uri = uri.trim();
        if (uri.equals(TRIPLE_DES.uri))
            return TRIPLE_DES;
        if (uri.equals(AES_128_CBS.uri))
            return AES_128_CBS;
        if (uri.equals(AES_192_CBS.uri))
            return AES_192_CBS;
        if (uri.equals(AES_256_CBS.uri))
            return AES_256_CBS;

        throw new RuntimeException("Unknown uri:" + uri);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("<ekmi:KeyAlgorithm>");
        builder.append(uri);
        builder.append("</ekmi:KeyAlgorithm>");
        return builder.toString();
    }
}