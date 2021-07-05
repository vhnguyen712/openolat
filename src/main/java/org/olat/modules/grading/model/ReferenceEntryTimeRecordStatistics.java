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
package org.olat.modules.grading.model;

import org.olat.repository.RepositoryEntryRef;

/**
 * 
 * Initial date: 15 févr. 2020<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class ReferenceEntryTimeRecordStatistics implements RepositoryEntryRef {

	private final Long entryKey;
	private final long time;
	private final long metadataTime;
	
	public ReferenceEntryTimeRecordStatistics(Long entryKey, long time, long metadataTime) {
		this.entryKey = entryKey;
		this.time = time;
		this.metadataTime = metadataTime;
	}
	
	@Override
	public Long getKey() {
		return entryKey;
	}
	
	public long getTime() {
		return time;
	}
	
	public long getMetadataTime() {
		return metadataTime;
	}

	@Override
	public int hashCode() {
		return entryKey == null ? 87237615 : entryKey.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj instanceof ReferenceEntryTimeRecordStatistics) {
			ReferenceEntryTimeRecordStatistics stats = (ReferenceEntryTimeRecordStatistics)obj;
			return entryKey != null && entryKey.equals(stats.entryKey);
		}
		return false;
	}
}
