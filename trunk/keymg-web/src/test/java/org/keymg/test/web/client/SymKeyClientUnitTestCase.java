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
package org.keymg.test.web.client;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyPair;
import java.security.KeyStore;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.keymg.api.sym.client.SymKeyClient;
import org.keymg.core.sym.SymKeyConstants;
import org.keymg.core.sym.generators.SymKeyGenerator;
import org.keymg.core.sym.parse.Parser;
import org.keymg.core.sym.util.DocumentUtil;
import org.keymg.core.sym.util.KeyStoreUtil;
import org.keymg.sym.model.ekmi.SymkeyResponse;
import org.keymg.sym.model.ekmi.SymkeyType;
import org.keymg.sym.model.ekmi.ValidResponseType;
import org.keymg.web.servlets.KeymgServlet;
import org.w3c.dom.Document;

/**
 * Unit test the {@link SymKeyClient}
 * 
 * @author anil@apache.org
 * @since Jul 15, 2011
 */
public class SymKeyClientUnitTestCase {
    private String url = "http://localhost:25000/keymg/";

    private static String keystorePassword = "keymg$";

    private static Server server = null;
    private static int port = 25000;

    private static KeyStore keystore;
    private static KeyPair keyPair;

    @BeforeClass
    public static void setup() throws Exception {
        server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new KeymgServlet()), "/keymg/*");
        server.start();

        keystore = KeyStoreUtil.get("keystore/keymg.keystore", keystorePassword.toCharArray());

        keyPair = KeyStoreUtil.getKeyPair(keystore, "10514", keystorePassword.toCharArray());
        assertNotNull(keyPair);
    }

    @AfterClass
    public static void destroy() throws Exception {
        server.stop();

        File theFile = new File("keystore.dat");
        if (theFile.exists()) {
            theFile.delete();
        }
    }

    @Test
    public void testEncryptDecrypt() throws Exception {
        SymKeyGenerator gen = new SymKeyGenerator();
        byte[] key = gen.generate(SymKeyConstants.AES_ALGORITHM_URI);
        byte[] enc = gen.encrypt(key, keyPair.getPublic());

        byte[] dec = gen.decrypt(enc, keyPair.getPrivate());
        assertArrayEquals(key, dec);
    }

    /**
     * Test the creation of a new symmetric key
     * 
     * @throws Exception
     */
    @Test
    public void testNew() throws Exception {
        SymKeyClient client = new SymKeyClient("10514", url, keyPair.getPrivate());

        SymkeyResponse symKeyResponse = client.createNew();
        System.out.println(symKeyResponse);
        byte[] key = client.getKey(symKeyResponse);
        assertNotNull(key);
    }

    @Test
    public void testServlet() throws Exception {
        String req = "<ekmi:SymkeyRequest  xmlns:ekmi=\'http://docs.oasis-open.org/ekmi/2008/01\'> <ekmi:GlobalKeyID>10514-0-0</ekmi:GlobalKeyID>"
                + "</ekmi:SymkeyRequest>";

        HttpURLConnection connection = null;
        OutputStreamWriter wr = null;
        BufferedReader rd = null;
        StringBuilder sb = null;
        String line = null;

        URL serverURL = null;

        try {
            serverURL = new URL(url);

            connection = (HttpURLConnection) serverURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);

            wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(req);
            wr.flush();

            // read the result from the server
            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();

            while ((line = rd.readLine()) != null) {
                sb.append(line + '\n');
            }

            System.out.println("Response from Key Server:");
            System.out.println(sb.toString());

            Parser parser = new Parser();
            Document responseDoc = DocumentUtil.create(sb.toString());
            parser.parse(DocumentUtil.getNodeAsStream(responseDoc));
            assertTrue(parser.getParsedObject() instanceof SymkeyResponse);

            SymkeyResponse symKeyResponse = (SymkeyResponse) parser.getParsedObject();
            List<ValidResponseType> list = symKeyResponse.getResponse();
            assertEquals(1, list.size());

            ValidResponseType validResponse = list.get(0);
            SymkeyType symKey = (SymkeyType) validResponse;
            assertNotNull(symKey.getGlobalKeyID().getValue());
            assertNotNull(symKey.getKeyUsePolicy().getKeyUsePolicyID().getValue());
        } finally {
            // close the connection, set all objects to null
            connection.disconnect();
            rd = null;
            sb = null;
            wr = null;
            connection = null;
        }
    }
}