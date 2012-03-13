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
public enum EncryptionMethodType {
    RSA("http://www.w3.org/2001/04/xmlenc#rsa-1_5");

    private String uri;

    EncryptionMethodType(String uri) {
        this.uri = uri;
    }

    public String get() {
        return this.uri;
    }

    public static EncryptionMethodType reverse(String uri) {
        if (uri.equals(RSA.get()))
            return RSA;
        throw new RuntimeException(" Unknown uri:" + uri);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<ekmi:EncryptionMethod Algorithm=\'");
        builder.append(get()).append("\'  />");
        return builder.toString();
    }
}