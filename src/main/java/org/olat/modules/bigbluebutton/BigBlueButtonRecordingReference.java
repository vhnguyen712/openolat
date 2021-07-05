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
package org.olat.modules.bigbluebutton;

import java.util.Date;

import org.olat.core.id.CreateInfo;
import org.olat.core.id.ModifiedInfo;

/**
 * 
 * Initial date: 7 août 2020<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public interface BigBlueButtonRecordingReference extends ModifiedInfo, CreateInfo {
	
	public Long getKey();
	
	public String getRecordingId();
	
	public Date getStartDate();

	public Date getEndDate();

	public String getUrl();

	public String getType();
	
	public Boolean getPermanent();
	
	public void setPermanent(Boolean permanent);
	
	public BigBlueButtonRecordingsPublishedRoles[] getPublishToEnum();
	
	public void setPublishToEnum(BigBlueButtonRecordingsPublishedRoles[] publishTo);
	
	public BigBlueButtonMeeting getMeeting();

}
