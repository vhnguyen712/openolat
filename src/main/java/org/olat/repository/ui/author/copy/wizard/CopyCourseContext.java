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
package org.olat.repository.ui.author.copy.wizard;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.olat.core.id.Identity;
import org.olat.core.util.StringHelper;
import org.olat.course.ICourse;
import org.olat.course.assessment.AssessmentMode;
import org.olat.course.editor.overview.OverviewRow;
import org.olat.course.reminder.model.ReminderRow;
import org.olat.course.wizard.CourseDisclaimerContext;
import org.olat.group.ui.main.BGTableItem;
import org.olat.modules.lecture.model.LectureBlockRow;
import org.olat.repository.RepositoryEntry;
import org.olat.repository.model.RepositoryEntryLifecycle;
import org.olat.repository.ui.author.copy.wizard.additional.AssessmentModeCopyInfos;

/**
 * Initial date: 18.02.2021<br>
 *
 * @author aboeckle, alexander.boeckle@frentix.com, http://www.frentix.com
 */
public class CopyCourseContext {
	
	public static final String CONTEXT_KEY = CopyCourseContext.class.getSimpleName();
	
	// Executing identity
	private Identity executingIdentity;
	
	// Repository entry and course
	private RepositoryEntry repositoryEntry;
	private ICourse course;
	private List<OverviewRow> courseNodes;
	private boolean isLearningPath;
	private boolean hasWiki;
	private boolean hasBlog;
	private boolean hasFolder;
	private boolean hasDateDependantNodes;
	private boolean hasLectureBlocks;
	private boolean hasReminders;
	private boolean hasAssessmentModes;
	
	// ReferenceAndTitleStep
	private String displayName;
	private String externalRef;
	
	// MetadataStep
	private CopyType metadataCopyType;
	private String authors;
	
	// GroupStep
	private CopyType groupCopyType;
	private CopyType customGroupCopyType;
	private List<BGTableItem> groups;
	
	// OwnersStep
	private CopyType ownersCopyType;
	private CopyType customOwnersCopyType;
	private List<Identity> newOwners;
	
	// CoachesStep
	private CopyType coachesCopyType;
	private CopyType customCoachesCopyType;
	private List<Identity> newCoaches;
	
	// ExecutionStep
	private CopyType executionCopyType;
	private ExecutionType executionType;
	private Date beginDate;
	private Date endDate;
	private long dateDifference;
	private Long semesterKey;
	private String location;
	
	// CatalogStep
	private CopyType catalogCopyType;
	private CopyType customCatalogCopyType;
	
	// DisclaimerStep
	private CopyType disclaimerCopyType;
	private CopyType customDisclaimerCopyType;
	private CourseDisclaimerContext disclaimerCopyContext;
	
	// BlogStep
	private CopyType blogCopyType;
	private CopyType customBlogCopyType;
	
	// FolderStep
	private CopyType folderCopyType;
	private CopyType customFolderCopyType;
	
	// WikiStep
	private CopyType wikiCopyType;
	private CopyType customWikiCopyType;
	
	// ReminderStep
	private CopyType reminderCopyType;
	private CopyType customReminderCopyType;
	private List<ReminderRow> reminderRows;
	
	// AssessmentModeStep
	private CopyType assessmentModeCopyType;
	private CopyType customAssessmentModeCopyType;
	private List<AssessmentMode> assessmentModeRows;
	private Map<AssessmentMode, AssessmentModeCopyInfos> assessmentCopyInfos;
	
	// LectureBlockStep
	private CopyType lectureBlockCopyType;
	private CopyType customLectureBlockCopyType;
	private List<LectureBlockRow> lectureBlockRows;
	
	public Identity getExecutingIdentity() {
		return executingIdentity;
	}
	
	public void setExecutingIdentity(Identity executingIdentity) {
		this.executingIdentity = executingIdentity;
	}
	
	public RepositoryEntry getRepositoryEntry() {
		return repositoryEntry;
	}
	
	public void setRepositoryEntry(RepositoryEntry repositoryEntry) {
		this.repositoryEntry = repositoryEntry;
	}	
	
	public ICourse getCourse() {
		return course;
	}
	
	public void setCourse(ICourse course) {
		this.course = course;
	}
	
	public List<OverviewRow> getCourseNodes() {
		return courseNodes;
	}
	
	public void setCourseNodes(List<OverviewRow> courseNodes) {
		this.courseNodes = courseNodes;
	}
	
	public boolean isLearningPath() {
		return isLearningPath;
	}
	
	public void setLearningPath(boolean isLearningPath) {
		this.isLearningPath = isLearningPath;
	}
	
	public boolean hasBlog() {
		return hasBlog;
	}
	
	public void setBlog(boolean hasBlog) {
		this.hasBlog = hasBlog;
	}
	
	public boolean hasFolder() {
		return hasFolder;
	}
	
	public void setFolder(boolean hasFolder) {
		this.hasFolder = hasFolder;
	}
	
	public boolean hasWiki() {
		return hasWiki;
	}
	
	public void setWiki(boolean hasWiki) {
		this.hasWiki = hasWiki;
	}
	
	public boolean hasNodeSpecificSettings() {
		return hasFolder || hasWiki || hasWiki;
	}
	
	public RepositoryEntryLifecycle getRepositoryLifeCycle() {
		if (repositoryEntry != null) {
			return repositoryEntry.getLifecycle();
		} else {
			return null;
		}
	}
	
	public boolean hasDateDependantNodes() {
		return hasDateDependantNodes;
	}
	
	public void setDateDependantNodes(boolean hasDateDependantNodes) {
		this.hasDateDependantNodes = hasDateDependantNodes;
	}
	
	public boolean hasAssessmentModes() {
		return hasAssessmentModes;
	}
	
	public void setAssessmentModes(boolean hasAssessmentModes) {
		this.hasAssessmentModes = hasAssessmentModes;
	}
	
	public boolean hasLectureBlocks() {
		return hasLectureBlocks;
	}
	
	public void setLectureBlocks(boolean hasLectureBlocks) {
		this.hasLectureBlocks = hasLectureBlocks;
	}
	
	public boolean hasAdditionalSettings() {
		return hasReminders || hasLectureBlocks || hasAssessmentModes;
	}
	
	public boolean hasReminders() {
		return hasReminders;
	}
	
	public void setReminders(boolean hasReminders) {
		this.hasReminders = hasReminders;
	}
	
	public String getDisplayName() {
		return getValue(displayName, repositoryEntry.getDisplayname());
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getExternalRef() {
		return getValue(externalRef, repositoryEntry.getExternalRef());
	}
	
	public void setExternalRef(String externalRef) {
		this.externalRef = externalRef;
	}
	
	public long getDateDifference() {
		return dateDifference;
	}
	
	public void setDateDifference(long dateDifference) {
		this.dateDifference = dateDifference;
	}

	public CopyType getMetadataCopyType() {
		return metadataCopyType;
	}
	
	public void setMetadataCopyType(CopyType metadataCopyType) {
		this.metadataCopyType = metadataCopyType;
	}
	
	public String getAuthors() {
		return getValue(authors, repositoryEntry.getAuthors());
	}
	
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	
	public CopyType getGroupCopyType() {
		return groupCopyType;
	}
	
	public void setGroupCopyType(CopyType groupCopyType) {
		this.groupCopyType = groupCopyType;
	}
	
	public CopyType getCustomGroupCopyType() {
		return customGroupCopyType;
	}
	
	public void setCustomGroupCopyType(CopyType customGroupCopyType) {
		this.customGroupCopyType = customGroupCopyType;
	}
	
	public List<BGTableItem> getGroups() {
		return groups;
	}
	
	public void setGroups(List<BGTableItem> groups) {
		this.groups = groups;
	}
	
	public CopyType getOwnersCopyType() {
		return ownersCopyType;
	}
	
	public void setOwnersCopyType(CopyType ownersCopyType) {
		this.ownersCopyType = ownersCopyType;
	}
	
	public CopyType getCustomOwnersCopyType() {
		return customOwnersCopyType;
	}
	
	public void setCustomOwnersCopyType(CopyType customOwnersCopyType) {
		this.customOwnersCopyType = customOwnersCopyType;
	}
	
	public List<Identity> getNewOwners() {
		return newOwners;
	}
	
	public void setNewOwners(List<Identity> newOwners) {
		this.newOwners = newOwners;
	}
	
	public CopyType getCoachesCopyType() {
		return coachesCopyType;
	}
	
	public void setCoachesCopyType(CopyType coachesCopyType) {
		this.coachesCopyType = coachesCopyType;
	}
	
	public CopyType getCustomCoachesCopyType() {
		return customCoachesCopyType;
	}
	
	public void setCustomCoachesCopyType(CopyType customCoachesCopyType) {
		this.customCoachesCopyType = customCoachesCopyType;
	}
	
	public List<Identity> getNewCoaches() {
		return newCoaches;
	}
	
	public void setNewCoaches(List<Identity> newCoaches) {
		this.newCoaches = newCoaches;
	}
	
	public CopyType getExecutionCopyType() {
		return executionCopyType;
	}
	
	public void setExecutionCopyType(CopyType executionCopyType) {
		this.executionCopyType = executionCopyType;
	}
	
	public ExecutionType getExecutionType() {
		return executionType;
	}
	
	public void setExecutionType(ExecutionType executionType) {
		this.executionType = executionType;
	}
	
	public Date getBeginDate() {
		if (beginDate != null) {
			return beginDate;
		} else if (getRepositoryLifeCycle() != null) {
			return getRepositoryLifeCycle().getValidFrom();
		} else {
			return null;
		}
	}
	
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getEndDate() {
		if (endDate != null) {
			return endDate;
		} else if (getRepositoryLifeCycle() != null) {
			return getRepositoryLifeCycle().getValidTo();
		}
		
		return null;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Long getSemesterKey() {
		return semesterKey;
	}
	
	public void setSemesterKey(Long semesterKey) {
		this.semesterKey = semesterKey;
	}
	
	public String getLocation() {
		if (location != null) {
			return location;
		} else if (repositoryEntry != null) {
			return repositoryEntry.getLocation();
		} else {
			return null;
		}
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public CopyType getCatalogCopyType() {
		return catalogCopyType;
	}
	
	public void setCatalogCopyType(CopyType catalogCopyType) {
		this.catalogCopyType = catalogCopyType;
	}
	
	public CopyType getCustomCatalogCopyType() {
		return customCatalogCopyType;
	}
	
	public void setCustomCatalogCopyType(CopyType customCatalogCopyType) {
		this.customCatalogCopyType = customCatalogCopyType;
	}
	
	public CopyType getDisclaimerCopyType() {
		return disclaimerCopyType;
	}
	
	public void setDisclaimerCopyType(CopyType disclaimerCopyType) {
		this.disclaimerCopyType = disclaimerCopyType;
	}
	
	public CopyType getCustomDisclaimerCopyType() {
		return customDisclaimerCopyType;
	}
	
	public void setCustomDisclaimerCopyType(CopyType customDisclaimerCopyType) {
		this.customDisclaimerCopyType = customDisclaimerCopyType;
	}
	
	public CourseDisclaimerContext getDisclaimerCopyContext() {
		return disclaimerCopyContext;
	}
	
	public void setDisclaimerCopyContext(CourseDisclaimerContext disclaimerCopyContext) {
		this.disclaimerCopyContext = disclaimerCopyContext;
	}
	
	public CopyType getBlogCopyType() {
		return blogCopyType;
	}
	
	public void setBlogCopyType(CopyType blogCopyType) {
		this.blogCopyType = blogCopyType;
	}
	
	public CopyType getCustomBlogCopyType() {
		return customBlogCopyType;
	}
	
	public void setCustomBlogCopyType(CopyType customBlogCopyType) {
		this.customBlogCopyType = customBlogCopyType;
	}
	
	public CopyType getFolderCopyType() {
		return folderCopyType;
	}
	
	public void setFolderCopyType(CopyType folderCopyType) {
		this.folderCopyType = folderCopyType;
	}
	
	public CopyType getCustomFolderCopyType() {
		return customFolderCopyType;
	}
	
	public void setCustomFolderCopyType(CopyType customFolderCopyType) {
		this.customFolderCopyType = customFolderCopyType;
	}
	
	public CopyType getWikiCopyType() {
		return wikiCopyType;
	}
	
	public void setWikiCopyType(CopyType wikiCopyType) {
		this.wikiCopyType = wikiCopyType;
	}
	
	public CopyType getCustomWikiCopyType() {
		return customWikiCopyType;
	}
	
	public void setCustomWikiCopyType(CopyType customWikiCopyType) {
		this.customWikiCopyType = customWikiCopyType;
	}
	
	public CopyType getReminderCopyType() {
		return reminderCopyType;
	}
	
	public void setReminderCopyType(CopyType reminderCopyType) {
		this.reminderCopyType = reminderCopyType;
	}
	
	public CopyType getCustomReminderCopyType() {
		return customReminderCopyType;
	}
	
	public void setCustomReminderCopyType(CopyType customReminderCopyType) {
		this.customReminderCopyType = customReminderCopyType;
	}
	
	public List<ReminderRow> getReminderRows() {
		return reminderRows;
	}
	
	public void setReminderRows(List<ReminderRow> reminderRows) {
		this.reminderRows = reminderRows;
	}
	
	public CopyType getAssessmentModeCopyType() {
		return assessmentModeCopyType;
	}
	public void setAssessmentModeCopyType(CopyType assessmentModeCopyType) {
		this.assessmentModeCopyType = assessmentModeCopyType;
	}
	
	public CopyType getCustomAssessmentModeCopyType() {
		return customAssessmentModeCopyType;
	}
	
	public void setCustomAssessmentModeCopyType(CopyType customAssessmentModeCopyType) {
		this.customAssessmentModeCopyType = customAssessmentModeCopyType;
	}
	
	public List<AssessmentMode> getAssessmentModeRows() {
		return assessmentModeRows;
	}
	
	public void setAssessmentModeRows(List<AssessmentMode> assessmentModeRows) {
		this.assessmentModeRows = assessmentModeRows;
	}	
	
	public Map<AssessmentMode, AssessmentModeCopyInfos> getAssessmentCopyInfos() {
		return assessmentCopyInfos;
	}
	
	public void setAssessmentCopyInfos(Map<AssessmentMode, AssessmentModeCopyInfos> assessmentCopyInfos) {
		this.assessmentCopyInfos = assessmentCopyInfos;
	}
	
	public CopyType getLectureBlockCopyType() {
		return lectureBlockCopyType;
	}
	
	public void setLectureBlockCopyType(CopyType lectureBlockCopyType) {
		this.lectureBlockCopyType = lectureBlockCopyType;
	}
	
	public CopyType getCustomLectureBlockCopyType() {
		return customLectureBlockCopyType;
	}
	
	public void setCustomLectureBlockCopyType(CopyType customLectureBlockCopyType) {
		this.customLectureBlockCopyType = customLectureBlockCopyType;
	}
	
	public List<LectureBlockRow> getLectureBlockRows() {
		return lectureBlockRows;
	}
	
	public void setLectureBlockRows(List<LectureBlockRow> lectureBlockRows) {
		this.lectureBlockRows = lectureBlockRows;
	}
	
	
	
	// Helpers
	public enum CopyType {
		copy,
		replace,
		shift,
		reference,
		selectNew,
		createNew,
		ignore,
		custom;
	}
	
	public enum ExecutionType {
		none, 
		beginAndEnd,
		semester;
	}
	
	public String getValue(String newValue, String oldValue) {
		return StringHelper.containsNonWhitespace(newValue) ? newValue : oldValue;
	}
	
	public Date getValue(Date newValue, Date oldValue) {
		return newValue != null ? newValue : oldValue;
	}
	
	public CopyType getCopyType(String copyTypeString) {
		try {
			return CopyType.valueOf(copyTypeString);
		} catch(Exception e) {
			return null;
		}
	}
}
