/**
 * <a href="http://www.openolat.org">
 * OpenOLAT - Online Learning and Training</a><br>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); <br>
 * you may not use this file except in compliance with the License.<br>
 * You may obtain a copy of the License at the
 * <a href="http://www.apache.org/licenses/LICENSE-2.0">Apache homepage</a>
 * <p>
 * Unless required by applicable law or agreed to in writing,<br>
 * software distributed under the License is distributed on an "AS IS" BASIS, <br>
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. <br>
 * See the License for the specific language governing permissions and <br>
 * limitations under the License.
 * <p>
 * Initial code contributed and copyrighted by<br>
 * frentix GmbH, http://www.frentix.com
 * <p>
 */
package org.olat.modules.adobeconnect;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.olat.core.id.Identity;
import org.olat.course.nodes.adobeconnect.compatibility.MeetingCompatibilityDate;
import org.olat.group.BusinessGroup;
import org.olat.modules.adobeconnect.model.AdobeConnectErrors;
import org.olat.modules.adobeconnect.model.AdobeConnectSco;
import org.olat.repository.RepositoryEntry;

/**
 * 
 * Initial date: 1 mars 2019<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public interface AdobeConnectManager {
	
	public boolean checkConnection(String url, String login, String password, AdobeConnectErrors error);
	
	public void createMeeting(String name, String description, String templateId, boolean permanent,
			Date start, long leadTime, Date end, long followupTime, Locale locale, boolean allAccess,
			RepositoryEntry entry, String subIdent, BusinessGroup businessGroup,
			Identity actingIdentity, AdobeConnectErrors error);
	
	/**
	 * The method creates only the Adobe Connect Meeting without membership.
	 * 
	 * @param meeting The meeting reference
	 * @param locale The locale
	 * @param allAccess If guests has access without confirmation of the moderator
	 * @param error The error collector
	 * @return An updated meeting reference
	 */
	public AdobeConnectMeeting createAdobeMeeting(AdobeConnectMeeting meeting, Locale locale, boolean allAccess, AdobeConnectErrors error);
	
	/**
	 * 
	 * @param meeting The meeting to update
	 * @param name The name to update or null
	 * @param description The description to update or null
	 * @param startDate The date to update or null
	 * @param endDate The end date to update or null
	 * @param error
	 */
	public AdobeConnectMeeting updateMeeting(AdobeConnectMeeting meeting, String name, String description, String templateId,
			boolean permanent, Date startDate, long leadTime, Date endDate, long followupTime, AdobeConnectErrors error);
	
	public AdobeConnectMeeting shareDocuments(AdobeConnectMeeting meeting, List<AdobeConnectSco> documents);
	
	public void convert(List<MeetingCompatibilityDate> meetings, RepositoryEntry entry, String subIdent);
	
	/**
	 * Reload the meeting.
	 * 
	 * @param meeting The meeting to reload.
	 * @return A fresh meeting object
	 */
	public AdobeConnectMeeting getMeeting(AdobeConnectMeeting meeting);
	
	public List<AdobeConnectMeeting> getMeetingsBefore(Date date);
	
	public boolean deleteMeeting(AdobeConnectMeeting meeting, AdobeConnectErrors error);
	
	public List<AdobeConnectSco> getTemplates();
	
	public List<AdobeConnectSco> getRecordings(AdobeConnectMeeting meeting, AdobeConnectErrors error);
	
	/**
	 * 
	 * @param entry The repository entry
	 * @param subIdent The course element identifier
	 * @param businessGroup The business group
	 * @return The list of meetings of the course element or the group
	 */
	public List<AdobeConnectMeeting> getMeetings(RepositoryEntry entry, String subIdent, BusinessGroup businessGroup);
	
	public boolean hasMeetings(RepositoryEntry entry, String subIdent, BusinessGroup businessGroup);
	
	public List<AdobeConnectMeeting> getAllMeetings();
	
	/**
	 * Check if the user is already registered.
	 * 
	 * @param meeting The meeting
	 * @param identity The user to check
	 * @param error Collector of errors
	 * @return true if the user is registered as host or participant
	 */
	public boolean isRegistered(AdobeConnectMeeting meeting, Identity identity,
			AdobeConnectMeetingPermission permission, AdobeConnectErrors error);
	
	/**
	 * Register to a meeting in advance.
	 * 
	 * @param meeting The meeting
	 * @param identity The user to register
	 * @param error Collector of errors
	 * @return true if registration is successful
	 */
	public boolean registerFor(AdobeConnectMeeting meeting, Identity identity,
			AdobeConnectMeetingPermission permission, AdobeConnectErrors error);
	
	/**
	 * This open the meeting by setting the specified identity as host of the meeting.
	 * 
	 * @param meeting The meeting
	 * @param identity The user which want open the meeting
	 * @param error Collector of errors
	 * @return The URL to jump in Adobe Connect
	 */
	public String open(AdobeConnectMeeting meeting, Identity identity, AdobeConnectErrors error);
	
	public String join(AdobeConnectMeeting meeting, Identity identity, boolean moderator, AdobeConnectErrors error);
	
	public String linkTo(AdobeConnectSco content, Identity identity, AdobeConnectErrors error);
	
	public void delete(RepositoryEntry entry, String subIdent);

}
