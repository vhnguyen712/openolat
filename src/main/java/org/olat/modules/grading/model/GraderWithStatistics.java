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

import java.util.ArrayList;
import java.util.List;

import org.olat.core.id.Identity;
import org.olat.modules.grading.GraderStatus;
import org.olat.user.AbsenceLeave;

/**
 * 
 * Initial date: 20 janv. 2020<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class GraderWithStatistics {
	
	private final Identity grader;
	private final GraderStatistics statistics;
	private final List<GraderStatus> graderStatus = new ArrayList<>(4);
	private final List<AbsenceLeave> absenceLeaves = new ArrayList<>(4);
	private long recordedTimeInSeconds = 0l;
	private long recordedMetadataTimeInSeconds = 0l;
	
	public GraderWithStatistics(Identity grader, GraderStatistics statistics) {
		this.grader = grader;
		this.statistics = statistics == null ? GraderStatistics.empty(grader.getKey()) : statistics;
	}
	
	public Identity getGrader() {
		return grader;
	}
	
	public List<GraderStatus> getGraderStatus() {
		return graderStatus;
	}
	
	public void addGraderStatus(GraderStatus status) {
		if(!graderStatus.contains(status)) {
			graderStatus.add(status);
		}
	}
	
	public List<AbsenceLeave> getAbsenceLeaves() {
		return absenceLeaves;
	}
	
	public void addAbsenceLeave(AbsenceLeave absence) {
		absenceLeaves.add(absence);
	}

	public long getRecordedTimeInSeconds() {
		return recordedTimeInSeconds;
	}

	public void addRecordedTimeInSeconds(long seconds) {
		recordedTimeInSeconds += seconds;
	}
	
	public long getRecordedMetadataTimeInSeconds() {
		return recordedMetadataTimeInSeconds;
	}
	
	public void addRecordedMetadataTimeInSeconds(long seconds) {
		recordedMetadataTimeInSeconds += seconds;
	}

	public GraderStatistics getStatistics() {
		return statistics;
	}
}
