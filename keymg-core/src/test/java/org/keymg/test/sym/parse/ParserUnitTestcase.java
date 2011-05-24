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
package org.keymg.test.sym.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.keymg.core.sym.parse.Parser;
import org.keymg.sym.model.ekmi.ApplicationsType;
import org.keymg.sym.model.ekmi.EncryptionMethodType;
import org.keymg.sym.model.ekmi.KeyCacheDetailType;
import org.keymg.sym.model.ekmi.KeyCachePolicyRequestType;
import org.keymg.sym.model.ekmi.KeyCachePolicyResponseType;
import org.keymg.sym.model.ekmi.KeyCachePolicyType;
import org.keymg.sym.model.ekmi.KeyClassType;
import org.keymg.sym.model.ekmi.KeyClassesType;
import org.keymg.sym.model.ekmi.KeyUsePolicyType;
import org.keymg.sym.model.ekmi.NonNegativeInteger;
import org.keymg.sym.model.ekmi.PermissionsType;
import org.keymg.sym.model.ekmi.PermittedApplicationsType;
import org.keymg.sym.model.ekmi.PermittedDatesType;
import org.keymg.sym.model.ekmi.PermittedDatesType.PermittedDate;
import org.keymg.sym.model.ekmi.PermittedDayType;
import org.keymg.sym.model.ekmi.PermittedDaysType;
import org.keymg.sym.model.ekmi.PermittedDurationType;
import org.keymg.sym.model.ekmi.PermittedLevelsType;
import org.keymg.sym.model.ekmi.PermittedNumberOfTransactionsType;
import org.keymg.sym.model.ekmi.PermittedTimesType;
import org.keymg.sym.model.ekmi.PermittedTimesType.PermittedTime;
import org.keymg.sym.model.ekmi.PermittedUsesType;
import org.keymg.sym.model.ekmi.SymkeyErrorType;
import org.keymg.sym.model.ekmi.SymkeyRequest;
import org.keymg.sym.model.ekmi.SymkeyResponse;
import org.keymg.sym.model.ekmi.SymkeyType;
import org.keymg.sym.model.ekmi.SymkeyWorkInProgressType;
import org.keymg.sym.model.ekmi.ValidResponseType;

/**
 * Unit Test the Parser
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public class ParserUnitTestcase
{
	@Test
	public void testSymKeyRequest01() throws Exception
	{
		ClassLoader tcl = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = tcl.getResourceAsStream("ekmi/v1/symkeyrequest-01.xml");
		assertNotNull(inputStream);
		Parser parser = new Parser();
		parser.parse(inputStream);
		
		Object parsed = parser.getParsedObject();
		assertTrue(parsed instanceof SymkeyRequest);
		SymkeyRequest symKeyRequest = (SymkeyRequest) parsed;
		assertEquals("10514-0-0", symKeyRequest.getGlobalKeyID().get(0));
	}
	
	@Test
	public void testSymKeyRequest02() throws Exception
	{
		ClassLoader tcl = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = tcl.getResourceAsStream("ekmi/v1/symkeyrequest-02.xml");
		assertNotNull(inputStream);
		Parser parser = new Parser();
		parser.parse(inputStream);
		
		Object parsed = parser.getParsedObject();
		assertTrue(parsed instanceof SymkeyRequest);
		SymkeyRequest symKeyRequest = (SymkeyRequest) parsed;
		assertEquals("10514-0-0", symKeyRequest.getGlobalKeyID().get(0));
		KeyClassesType keyClassesType = symKeyRequest.getKeyClasses();
		assertNotNull( "keyClassesType is null?", keyClassesType);
		
		KeyClassType[] keyClassTypeArr = keyClassesType.getKeyClassType();
		assertEquals( 1, keyClassTypeArr.length );
		KeyClassType keyClassType = keyClassTypeArr[0];
		assertEquals( "HR-Class", keyClassType.getValue());
	}

	@Test
	public void testSymKeyResponse01() throws Exception
	{
		ClassLoader tcl = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = tcl.getResourceAsStream("ekmi/v1/symkeyresponse-01.xml");
		assertNotNull(inputStream);
		Parser parser = new Parser();
		parser.parse(inputStream);

		Object parsed = parser.getParsedObject();
		assertTrue(parsed instanceof SymkeyResponse);
		SymkeyResponse resp = (SymkeyResponse) parsed; 
		List<ValidResponseType> responses = resp.getResponse();
		assertNotNull( responses );
		assertEquals( 1, responses.size() );
		
		SymkeyType symKey = (SymkeyType) responses.get( 0 );
		assertEquals( "10514-1-7476", symKey.getSymkeyRequestID().getValue() );
		assertEquals( "10514-1-235", symKey.getGlobalKeyID().getValue() );
		assertEquals( EncryptionMethodType.RSA, symKey.getEncryptionMethod());
		
		KeyUsePolicyType keyUsePolicyType = symKey.getKeyUsePolicy();
		assertNotNull("KeyUsePolicyType is null?", keyUsePolicyType);
		assertEquals( "KeyUsePolicyID" , "10514-4", keyUsePolicyType.getKeyUsePolicyID().getValue() );
		assertEquals( "ekmi:PolicyName" , "DES-EDE KeyUsePolicy", keyUsePolicyType.getPolicyName() );
		assertEquals( "KeyClass" , "HR-Class", keyUsePolicyType.getKeyClass().getValue() );
		assertEquals( "ekmi:KeyAlgorithm" , "http://www.w3.org/2001/04/xmlenc#tripledes-cbc", keyUsePolicyType.getKeyAlgorithm().get() );
		assertEquals( "ekmi:KeySize" , 192, keyUsePolicyType.getKeySize().getValue());
		assertEquals( "ekmi:Status" , "Active", keyUsePolicyType.getStatus().value() ); 
		
		PermissionsType permissionsType = keyUsePolicyType.getPermissions();
		assertNotNull( "Permissions is not null?", permissionsType );
		
		PermittedApplicationsType permittedApplications = permissionsType.getPermittedApplications();
		assertNotNull( "PermittedApplicationsType is not null?", permittedApplications);
		
		List<ApplicationsType> permittedAppList = permittedApplications.getPermittedApplication();
		assertEquals( 1, permittedAppList.size() );
		
		ApplicationsType app = permittedAppList.get(0);
		assertEquals( "<ekmi:ApplicationID>", "10514-23", app.getApplicationID() );
		assertEquals( "<ekmi:ApplicationName>", "Payroll Application", app.getApplicationName() );
		assertEquals( "<ekmi:ApplicationVersion>", "1.0", app.getVersion() );
		assertEquals( "http://www.w3.org/2000/09/xmldsig#sha1", app.getDigestAlgorithm());

		assertEquals( "NIG4bKkt4cziEqFFuOoBTM81efU=", new String( app.getDigestValue() ));
		
		//PermittedDates
		PermittedDatesType permittedDates = permissionsType.getPermittedDates();
		assertNotNull(permittedDates);
		
		List<PermittedDate> permittedDatesList = permittedDates.getPermittedDate();
		assertEquals(1, permittedDatesList.size());
		PermittedDate permittedDate = permittedDatesList.get(0);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		assertEquals( sdf.parse("2008-01-01"), permittedDate.getStartDate() );
		assertEquals( sdf.parse("2008-12-31"), permittedDate.getEndDate() );
		
		//Permitted Days
		PermittedDaysType permittedDays = permissionsType.getPermittedDays();
		assertNotNull( permittedDays );
		
		List<PermittedDayType> permittedDayList = permittedDays.getPermittedDay();
		assertEquals( 0 , permittedDayList.size() );
		
		//PermittedDuration
		PermittedDurationType permittedDuration = permissionsType.getPermittedDuration();
		assertNotNull( permittedDuration );
		
		//PermittedLevels
		PermittedLevelsType permittedLevels = permissionsType.getPermittedLevels();
		assertNotNull( permittedLevels );
		
		//PermittedNumberOfTransactions
		PermittedNumberOfTransactionsType permittedNumOfTransactions = permissionsType.getPermittedNumberOfTransactions();
		assertNotNull( permittedNumOfTransactions );
		
		//PermittedTimes
		PermittedTimesType permittedTimes = permissionsType.getPermittedTimes();
		assertNotNull( permittedTimes );
		List<PermittedTime> permittedTimeList = permittedTimes.getPermittedTime();
		assertEquals( 1, permittedTimeList.size() );
		
		PermittedTime permittedTime = permittedTimeList.get( 0 );
	    sdf = new SimpleDateFormat("HH:mm:ss"); 
		assertEquals( sdf.parse("07:00:00"), permittedTime.getStartTime() );
		assertEquals( sdf.parse("19:00:00"), permittedTime.getEndTime() );
		
		//PermittedUses
		PermittedUsesType permittedUses = permissionsType.getPermittedUses();
		assertNotNull( permittedUses );
	}

	@Test
	public void testSymKeyResponseWithIETF() throws Exception
	{
		ClassLoader tcl = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = tcl.getResourceAsStream("ekmi/v1/symkeyresponseWithIETF-01.xml");
		assertNotNull(inputStream);
		Parser parser = new Parser();
		parser.parse(inputStream);

		Object parsed = parser.getParsedObject();
		assertTrue(parsed instanceof SymkeyResponse);
		SymkeyResponse symKeyResponse = (SymkeyResponse) parsed; 
		assertNotNull(symKeyResponse);
	}
	
	@Test
	public void testSymKeyMultipleNewKeys() throws Exception
	{
	   ClassLoader tcl = Thread.currentThread().getContextClassLoader();
       InputStream inputStream = tcl.getResourceAsStream("ekmi/v1/req-multiple-new-keys-01.xml");
       assertNotNull(inputStream);
       Parser parser = new Parser();
       parser.parse(inputStream);

       Object parsed = parser.getParsedObject();
       assertTrue(parsed instanceof SymkeyRequest);
       SymkeyRequest symKeyReq = (SymkeyRequest) parsed; 
       assertNotNull(symKeyReq);
       KeyClassesType keyClasses = symKeyReq.getKeyClasses();
       KeyClassType keys[] = keyClasses.getKeyClassType();
       assertEquals(9, keys.length);
       
       List<KeyClassType> list = Arrays.asList(keys);
       assertTrue(list.contains(new KeyClassType("EHR-CDC")));
       assertTrue(list.contains(new KeyClassType("EHR-CRO")));
       assertTrue(list.contains(new KeyClassType("EHR-DEF")));
       assertTrue(list.contains(new KeyClassType("EHR-EMT")));
       assertTrue(list.contains(new KeyClassType("EHR-HOS")));
       assertTrue(list.contains(new KeyClassType("EHR-INS")));
       assertTrue(list.contains(new KeyClassType("EHR-NUR")));
       assertTrue(list.contains(new KeyClassType("EHR-PAT")));
       assertTrue(list.contains(new KeyClassType("EHR-PHY")));
	}
	
	@Test
    public void testSymKeyResponseForMultipleNewKeys() throws Exception
    {
        ClassLoader tcl = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = tcl.getResourceAsStream("ekmi/v1/resp-multiple-new-keys-01.xml");
        assertNotNull(inputStream);
        Parser parser = new Parser();
        parser.parse(inputStream);

        Object parsed = parser.getParsedObject();
        assertTrue(parsed instanceof SymkeyResponse);
        SymkeyResponse symKeyResponse = (SymkeyResponse) parsed; 
        assertNotNull(symKeyResponse);
        
        List<ValidResponseType> valList = symKeyResponse.getResponse();
        assertEquals(9, valList.size());
    }
	
	@Test
    public void testRequestEncCertificate() throws Exception
    {
        ClassLoader tcl = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = tcl.getResourceAsStream("ekmi/v1/req-enc-cert.xml");
        assertNotNull(inputStream);
        Parser parser = new Parser();
        parser.parse(inputStream);
        
        Object parsed = parser.getParsedObject();
        assertTrue(parsed instanceof SymkeyRequest);
        SymkeyRequest symKeyRequest = (SymkeyRequest) parsed;
        byte[] encCert = symKeyRequest.getX509EncryptionCertificate();
        assertNotNull(encCert);
    }
	
	@Test
    public void testSymKeyResponseWithError() throws Exception
    {
        ClassLoader tcl = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = tcl.getResourceAsStream("ekmi/v1/resp-error.xml");
        assertNotNull(inputStream);
        Parser parser = new Parser();
        parser.parse(inputStream);

        Object parsed = parser.getParsedObject();
        assertTrue(parsed instanceof SymkeyResponse);
        SymkeyResponse symKeyResponse = (SymkeyResponse) parsed; 
        assertNotNull(symKeyResponse);
        List<ValidResponseType> resp = symKeyResponse.getResponse();
        assertEquals( 1, resp.size());
        
        ValidResponseType vrt = resp.get(0);
        assertTrue( vrt instanceof SymkeyErrorType);
        SymkeyErrorType err = (SymkeyErrorType) vrt;
        assertEquals("10514-2-1044", err.getSymkeyRequestID());
        assertEquals("10514-2-22", err.getRequestedGlobalKeyID());
        assertEquals("Payroll", err.getRequestedKeyClass());
        assertEquals("SKS-100004", err.getErrorCode());
        assertEquals("Unauthorized request for key", err.getErrorMessage());
    }
	
	@Test
    public void testSymKeyResponseKeyPlusError() throws Exception
    {
        ClassLoader tcl = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = tcl.getResourceAsStream("ekmi/v1/resp-key-plus-error.xml");
        assertNotNull(inputStream);
        Parser parser = new Parser();
        parser.parse(inputStream);

        Object parsed = parser.getParsedObject();
        assertTrue(parsed instanceof SymkeyResponse);
        SymkeyResponse symKeyResponse = (SymkeyResponse) parsed; 
        assertNotNull(symKeyResponse);
        List<ValidResponseType> resp = symKeyResponse.getResponse();
        assertEquals( 2, resp.size());
        
        ValidResponseType krt = resp.get(0);
        assertTrue( krt instanceof SymkeyType);
        SymkeyType symKey = (SymkeyType) krt;
        assertEquals( "10514-1-7476", symKey.getSymkeyRequestID().getValue() );
        assertEquals( "10514-1-235", symKey.getGlobalKeyID().getValue() );
        assertEquals( EncryptionMethodType.RSA, symKey.getEncryptionMethod());
        
        ValidResponseType vrt = resp.get(1);
        assertTrue( vrt instanceof SymkeyErrorType);
        SymkeyErrorType err = (SymkeyErrorType) vrt;
        assertEquals("10514-2-1044", err.getSymkeyRequestID());
        assertEquals("10514-2-22", err.getRequestedGlobalKeyID());
        assertEquals("Payroll", err.getRequestedKeyClass());
        assertEquals("SKS-100004", err.getErrorCode());
        assertEquals("Unauthorized request for key", err.getErrorMessage());
    }
	
	@Test
    public void testSymKeyResponseWorkInProgress() throws Exception
    {
        ClassLoader tcl = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = tcl.getResourceAsStream("ekmi/v1/resp-work-in-progress.xml");
        assertNotNull(inputStream);
        Parser parser = new Parser();
        parser.parse(inputStream);

        Object parsed = parser.getParsedObject();
        assertTrue(parsed instanceof SymkeyResponse);
        SymkeyResponse symKeyResponse = (SymkeyResponse) parsed; 
        assertNotNull(symKeyResponse);
        List<ValidResponseType> resp = symKeyResponse.getResponse();
        assertEquals( 1, resp.size());
        ValidResponseType vrt = resp.get(0);
        SymkeyWorkInProgressType wip = (SymkeyWorkInProgressType) vrt;
        assertEquals( "10514-0-0", wip.getRequestedGlobalKeyID());
        assertEquals("10514-4-7235", wip.getSymkeyRequestID());
    }
	
	@Test
    public void testRequestPendingID() throws Exception
    {
        ClassLoader tcl = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = tcl.getResourceAsStream("ekmi/v1/req-pending-wip.xml");
        assertNotNull(inputStream);
        Parser parser = new Parser();
        parser.parse(inputStream);
        
        Object parsed = parser.getParsedObject();
        assertTrue(parsed instanceof SymkeyRequest);
        SymkeyRequest symKeyRequest = (SymkeyRequest) parsed;
        assertEquals( 1, symKeyRequest.getSymkeyRequestIDList().size());
        assertEquals("10514-4-7235", symKeyRequest.getSymkeyRequestIDList().get(0));
    }
	
	@Test
    public void testKeyCachePolicyRequest() throws Exception
    {
        ClassLoader tcl = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = tcl.getResourceAsStream("ekmi/v1/req-keycachepolicy.xml");
        assertNotNull(inputStream);
        Parser parser = new Parser();
        parser.parse(inputStream);
        
        Object parsed = parser.getParsedObject();
        assertTrue(parsed instanceof KeyCachePolicyRequestType);
        KeyCachePolicyRequestType kcpRequest = (KeyCachePolicyRequestType) parsed;
        assertNotNull(kcpRequest.getSignature());
    }
	
	@Test
    public void testResponseKeyCachePolicy() throws Exception
    {
        ClassLoader tcl = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = tcl.getResourceAsStream("ekmi/v1/resp-keycachepolicy.xml");
        assertNotNull(inputStream);
        Parser parser = new Parser();
        parser.parse(inputStream);

        Object parsed = parser.getParsedObject();
        assertTrue(parsed instanceof KeyCachePolicyResponseType);
        KeyCachePolicyResponseType kcpResponse = (KeyCachePolicyResponseType) parsed; 
        assertNotNull(kcpResponse);
        List<KeyCachePolicyType> resp = kcpResponse.policies();
        assertEquals( 1, resp.size());
        KeyCachePolicyType kcp = resp.get(0);
        
        assertEquals("10514-1", kcp.getKeyCachePolicyID().getValue());
        assertEquals("No Caching Policy", kcp.getPolicyName());
        assertEquals("NoCachingClass", kcp.getKeyClassType().getValue());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd" ) ;
        assertEquals(dateFormat.parse("2008-01-01T00:00:01.0"), kcp.getStartDate());
        assertEquals(dateFormat.parse("1969-01-01T00:00:00.0"), kcp.getEndDate());
        assertEquals(new NonNegativeInteger(Integer.valueOf("2592000")), kcp.getPolicyCheckInterval());
        assertEquals("Active", kcp.getStatus().value());
    }
	
	@Test
    public void testResponseKeyCachePolicyExtended() throws Exception
    {
        ClassLoader tcl = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = tcl.getResourceAsStream("ekmi/v1/resp-keycachepolicy-ext.xml");
        assertNotNull(inputStream);
        Parser parser = new Parser();
        parser.parse(inputStream);

        Object parsed = parser.getParsedObject();
        assertTrue(parsed instanceof KeyCachePolicyResponseType);
        KeyCachePolicyResponseType kcpResponse = (KeyCachePolicyResponseType) parsed; 
        assertNotNull(kcpResponse);
        List<KeyCachePolicyType> resp = kcpResponse.policies();
        assertEquals( 1, resp.size());
        KeyCachePolicyType kcp = resp.get(0);
        
        assertEquals("10514-17", kcp.getKeyCachePolicyID().getValue());
        assertEquals("Corporate Laptop Key Caching Policy", kcp.getPolicyName());
        assertEquals("LaptopKeysCachingClass", kcp.getKeyClassType().getValue());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd" ) ;
        assertEquals(dateFormat.parse("2008-01-01T00:00:01.0"), kcp.getStartDate());
        assertEquals(dateFormat.parse("2008-12-31T00:00:01.0"), kcp.getEndDate());
        assertEquals(new NonNegativeInteger(Integer.valueOf("2592000")), kcp.getPolicyCheckInterval());
        assertEquals("Active", kcp.getStatus().value());
        
        KeyCacheDetailType newKCD = kcp.getNewKeysCacheDetail();
        assertNotNull(newKCD);
        assertEquals(BigInteger.valueOf(3), newKCD.getMaximumKeys());
        assertEquals(BigInteger.valueOf(7776000), newKCD.getMaximumDuration());
        
        KeyCacheDetailType usedKCD = kcp.getUsedKeysCacheDetail();
        assertNotNull(usedKCD);
        assertEquals(BigInteger.valueOf(6), usedKCD.getMaximumKeys());
        assertEquals(BigInteger.valueOf(7776000), usedKCD.getMaximumDuration());
    }
}