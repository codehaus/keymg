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
package org.keymg.core.sym;

import javax.xml.namespace.QName;

/**
 * @author anil@apache.org
 * @since Aug 24, 2009
 */
public interface SymKeyConstants 
{ 
	String EKMI_NAMESPACE_URI = "http://docs.oasis-open.org/ekmi/2008/01";  
	String AES_ALGORITHM_URI = "http://www.w3.org/2001/04/xmlenc#aes256-cbc";
	String TRIPLE_DES_ALGORITHM_URI = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
	
	String ALGORITHM = "Algorithm";
	String ANY = "any";
	String APPLICATION_ID = "ApplicationID";
	String APPLICATION_NAME = "ApplicationName";
	String APPLICATION_DIGEST_ALGORITHM = "ApplicationDigestAlgorithm";
	String APPLICATION_DIGEST_VALUE = "ApplicationDigestValue";
	String APPLICATION_VERSION = "ApplicationVersion"; 
	
    String ENCRYPTION_METHOD = "EncryptionMethod";
	String END_DATE = "EndDate"; 
	String END_TIME = "EndTime"; 
    String ERROR_CODE = "ErrorCode"; 
    String ERROR_MSG = "ErrorMessage"; 
	
	String GLOBAL_KEY_ID = "GlobalKeyID";
	
	String KEY_ALGORITHM = "KeyAlgorithm";
	String KEY_CLASS = "KeyClass";
	String KEY_CLASSES = "KeyClasses";
	String KEY_SIZE = "KeySize";
	String KEY_USE_POLICY = "KeyUsePolicy";
	String KEY_USE_POLICY_ID = "KeyUsePolicyID";
	String KEY_CACHE_POLICY_REQUEST = "KeyCachePolicyRequest";
	
	String PERMISSIONS = "Permissions";
	String PERMITTED_APPLICATIONS = "PermittedApplications";
	String PERMITTED_APPLICATION = "PermittedApplication";
	String PERMITTED_DATE = "PermittedDate";
	String PERMITTED_DATES = "PermittedDates";
	String PERMITTED_DAYS = "PermittedDays";
	String PERMITTED_DURATION = "PermittedDuration";
	String PERMITTED_LEVELS = "PermittedLevels";
	String PERMITTED_LOCATIONS = "PermittedLocations";
	String PERMITTED_NUMBER_OF_TRANSACTIONS = "PermittedNumberOfTransactions";
	String PERMITTED_TIMES = "PermittedTimes";
	String PERMITTED_TIME = "PermittedTime";
	String PERMITTED_USES = "PermittedUses";
	String POLICY_NAME = "PolicyName";
	
	String START_DATE = "StartDate";
	String START_TIME = "StartTime";
	String STATUS = "Status";
	String SYMKEY = "Symkey";
	
	String SYMKEY_ERROR = "SymkeyError";
	String SYMKEY_REQUEST = "SymkeyRequest";
	String SYMKEY_RESPONSE = "SymkeyResponse";
    String SYMKEY_WORK_IN_PROGRESS = "SymkeyWorkInProgress";
	
	String REQUESTED_GLOBAL_KEY_ID = "RequestedGlobalKeyID";
	String REQUESTED_KEY_CLASS = "RequestedKeyClass";
	String REQUEST_CHECKIN_INTERVAL = "RequestCheckInterval";
    
	String SYMKEY_REQUEST_ID = "SymkeyRequestID";
	
	String X509_ENCRYPTION_CERT = "X509EncryptionCertificate";
	
	public enum QNameConstants
	{
		ANY_QNAME( EKMI_NAMESPACE_URI, SymKeyConstants.ANY),
		KEYUSEPOLICY_QNAME( EKMI_NAMESPACE_URI, SymKeyConstants.KEY_USE_POLICY ),
		SYMKEY_QNAME( EKMI_NAMESPACE_URI, SymKeyConstants.SYMKEY );
		
        private QName qname;
		private QNameConstants(String ns, String localPart)
		{
			qname = new QName(ns, localPart); 
		}
		public QName get()
		{
			return this.qname;
		}
	}

	public enum EncryptionAlgorithms
	{
	   RSA( "RSA" );
	   
	   private String algoName;

       private EncryptionAlgorithms( String name)
	   {
	      this.algoName = name;
	   }
       
       public String get() { return this.algoName; }
	}
}