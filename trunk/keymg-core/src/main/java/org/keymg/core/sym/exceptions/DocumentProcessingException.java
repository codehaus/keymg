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
package org.keymg.core.sym.exceptions;

import java.security.GeneralSecurityException;

/**
 * A {@link Exception} indicating problems with processing of a document
 * @author anil@apache.org
 * @since Jun 7, 2010
 */
public class DocumentProcessingException extends GeneralSecurityException {
    private static final long serialVersionUID = 1L;

    public DocumentProcessingException() {
        super();
    }

    public DocumentProcessingException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public DocumentProcessingException(String arg0) {
        super(arg0);
    }

    public DocumentProcessingException(Throwable arg0) {
        super(arg0);
    }
}