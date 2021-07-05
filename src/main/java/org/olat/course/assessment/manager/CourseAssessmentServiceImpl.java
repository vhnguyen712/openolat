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
package org.olat.course.assessment.manager;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.Logger;
import org.olat.basesecurity.GroupRoles;
import org.olat.core.commons.persistence.DB;
import org.olat.core.gui.UserRequest;
import org.olat.core.gui.components.stack.BreadcrumbPanel;
import org.olat.core.gui.components.stack.TooledStackedPanel;
import org.olat.core.gui.control.Controller;
import org.olat.core.gui.control.WindowControl;
import org.olat.core.id.Identity;
import org.olat.core.id.IdentityEnvironment;
import org.olat.core.logging.Tracing;
import org.olat.course.CourseFactory;
import org.olat.course.ICourse;
import org.olat.course.assessment.AssessmentManager;
import org.olat.course.assessment.CourseAssessmentService;
import org.olat.course.assessment.handler.AssessmentConfig;
import org.olat.course.assessment.handler.AssessmentHandler;
import org.olat.course.assessment.handler.NonAssessmentHandler;
import org.olat.course.assessment.ui.tool.AssessmentCourseNodeController;
import org.olat.course.assessment.ui.tool.IdentityListCourseNodeController;
import org.olat.course.auditing.UserNodeAuditManager;
import org.olat.course.config.CourseConfig;
import org.olat.course.groupsandrights.CourseRights;
import org.olat.course.nodes.CourseNode;
import org.olat.course.properties.CoursePropertyManager;
import org.olat.course.run.environment.CourseEnvironment;
import org.olat.course.run.navigation.NodeVisitedListener;
import org.olat.course.run.scoring.AccountingEvaluators;
import org.olat.course.run.scoring.AssessmentEvaluation;
import org.olat.course.run.scoring.ScoreAccounting;
import org.olat.course.run.scoring.ScoreEvaluation;
import org.olat.course.run.userview.UserCourseEnvironment;
import org.olat.course.run.userview.UserCourseEnvironmentImpl;
import org.olat.group.BusinessGroup;
import org.olat.modules.assessment.AssessmentEntry;
import org.olat.modules.assessment.AssessmentService;
import org.olat.modules.assessment.Overridable;
import org.olat.modules.assessment.Role;
import org.olat.modules.assessment.model.AssessmentEntryStatus;
import org.olat.modules.assessment.model.AssessmentRunStatus;
import org.olat.modules.assessment.ui.AssessmentToolContainer;
import org.olat.modules.assessment.ui.AssessmentToolSecurityCallback;
import org.olat.repository.RepositoryEntry;
import org.olat.repository.RepositoryEntryRelationType;
import org.olat.repository.RepositoryEntrySecurity;
import org.olat.repository.RepositoryManager;
import org.olat.repository.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * Initial date: 20 Aug 2019<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
@Service
public class CourseAssessmentServiceImpl implements CourseAssessmentService, NodeVisitedListener {

	private static final Logger log = Tracing.createLoggerFor(CourseAssessmentServiceImpl.class);
	
	private static final String NON_ASSESSMENT_TYPE = NonAssessmentHandler.NODE_TYPE;
	
	@Autowired
	private DB dbInstance;
	@Autowired
	private AssessmentService assessmentService;
	@Autowired
	private RepositoryManager repositoryManager;
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private List<AssessmentHandler> loadedAssessmentHandlers;
	private Map<String, AssessmentHandler> assessmentHandlers = new HashMap<>();
	private AssessmentHandler nonAssessmentHandler;
	
	@PostConstruct
	void initProviders() {
		for (AssessmentHandler handler: loadedAssessmentHandlers) {
			if (NON_ASSESSMENT_TYPE.equals(handler.acceptCourseNodeType())) {
				nonAssessmentHandler = handler;
			} else {
				assessmentHandlers.put(handler.acceptCourseNodeType(), handler);
			}
		}
	}

	private  AssessmentHandler getAssessmentHandler(CourseNode courseNode) {
		AssessmentHandler handler = null;
		if (courseNode != null) {
			handler = assessmentHandlers.get(courseNode.getType());
		}
		if (handler == null) {
			handler = nonAssessmentHandler;
		}
		return handler;
	}

	@Override
	public AssessmentConfig getAssessmentConfig(CourseNode courseNode) {
		return getAssessmentHandler(courseNode).getAssessmentConfig(courseNode);
	}

	@Override
	public AccountingEvaluators getEvaluators(CourseNode courseNode, CourseConfig courseConfig) {
		return getAssessmentHandler(courseNode).getEvaluators(courseNode, courseConfig);
	}

	@Override
	public AssessmentEntry getAssessmentEntry(CourseNode courseNode, UserCourseEnvironment userCourseEnvironment) {
		return getAssessmentHandler(courseNode).getAssessmentEntry(courseNode, userCourseEnvironment);
	}
	
	@Override
	public AssessmentEvaluation toAssessmentEvaluation(AssessmentEntry assessmentEntry, AssessmentConfig assessmentConfig) {
		return AssessmentEvaluation.toAssessmentEvaluation(assessmentEntry, assessmentConfig);
	}
	
	@Override
	public AssessmentEvaluation toAssessmentEvaluation(AssessmentEntry assessmentEntry, CourseNode courseNode) {
		AssessmentConfig assessmentConfig = getAssessmentConfig(courseNode);
		return toAssessmentEvaluation(assessmentEntry, assessmentConfig);
	}

	@Override
	public AssessmentEvaluation getAssessmentEvaluation(CourseNode courseNode, UserCourseEnvironment userCourseEnvironment) {
		AssessmentEntry assessmentEntry = getAssessmentHandler(courseNode).getAssessmentEntry(courseNode, userCourseEnvironment);
		return toAssessmentEvaluation(assessmentEntry, courseNode);
	}
	
	@Override
	public void updateScoreEvaluation(CourseNode courseNode, ScoreEvaluation scoreEvaluation,
			UserCourseEnvironment userCourseEnvironment, Identity coachingIdentity, boolean incrementAttempts, Role by) {
		if (!userCourseEnvironment.isParticipant()) return;
		
		AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
		Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
		am.saveScoreEvaluation(courseNode, coachingIdentity, assessedIdentity, new ScoreEvaluation(scoreEvaluation),
				userCourseEnvironment, incrementAttempts, by);
	}

	@Override
	public Double getCurrentRunCompletion(CourseNode courseNode, UserCourseEnvironment userCourseEnvironment) {
		AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
		Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
		return am.getNodeCurrentRunCompletion(courseNode, assessedIdentity);
	}
	
	@Override
	public void updateCurrentCompletion(CourseNode courseNode, UserCourseEnvironment userCourseEnvironment,
			Date start, Double currentCompletion, AssessmentRunStatus runStatus, Role by) {
		if (!userCourseEnvironment.isParticipant()) return;
		
		AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
		Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
		am.updateCurrentCompletion(courseNode, assessedIdentity, userCourseEnvironment, start, currentCompletion,
				runStatus, by);
	}
	
	@Override
	public void updateCompletion(CourseNode courseNode, UserCourseEnvironment userCourseEnvironment, Double completion,
			AssessmentEntryStatus runStatus, Role by) {
		if (!userCourseEnvironment.isParticipant()) return;
		
		AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
		Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
		am.updateCompletion(courseNode, assessedIdentity, userCourseEnvironment, completion, runStatus, by);
	}
	
	@Override
	public void updateFullyAssessed(CourseNode courseNode, UserCourseEnvironment userCourseEnvironment,
			Boolean fullyAssessed, AssessmentEntryStatus status) {
		if (!userCourseEnvironment.isParticipant()) return;
		
		AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
		am.updateFullyAssessed(courseNode, userCourseEnvironment, fullyAssessed, status);
	}

	@Override
	public Integer getAttempts(CourseNode courseNode, UserCourseEnvironment userCourseEnv) {
		AssessmentManager am = userCourseEnv.getCourseEnvironment().getAssessmentManager();
		Identity assessedIdentity = userCourseEnv.getIdentityEnvironment().getIdentity();
		return am.getNodeAttempts(courseNode, assessedIdentity);
	}

	@Override
	public void incrementAttempts(CourseNode courseNode, UserCourseEnvironment userCourseEnvironment, Role by) {
		if (!userCourseEnvironment.isParticipant()) return;
		
		AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
		Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
		am.incrementNodeAttempts(courseNode, assessedIdentity, userCourseEnvironment, by);
	}

	@Override
	public void updateAttempts(CourseNode courseNode, Integer userAttempts, Date lastAttempt,
			UserCourseEnvironment userCourseEnvironment, Identity coachingIdentity, Role by) {
		if (!userCourseEnvironment.isParticipant())
			return;
		
		if (userAttempts != null) {
			AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
			Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
			am.saveNodeAttempts(courseNode, coachingIdentity, assessedIdentity, userAttempts, lastAttempt, by);
		}
	}

	@Override
	public String getUserComment(CourseNode courseNode, UserCourseEnvironment userCourseEnvironment) {
		AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
		Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
		return am.getNodeComment(courseNode, assessedIdentity);
	}

	@Override
	public void updatedUserComment(CourseNode courseNode, String userComment,
			UserCourseEnvironment userCourseEnvironment, Identity coachingIdentity) {
		if (!userCourseEnvironment.isParticipant()) return;
		
		if (userComment != null) {
			AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
			Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
			am.saveNodeComment(courseNode, coachingIdentity, assessedIdentity, userComment);
		}
	}

	@Override
	public String getCoachComment(CourseNode courseNode, UserCourseEnvironment userCourseEnvironment) {
		AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
		Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
		return am.getNodeCoachComment(courseNode, assessedIdentity);
	}

	@Override
	public void updateCoachComment(CourseNode courseNode, String coachComment,
			UserCourseEnvironment userCourseEnvironment) {
		if (!userCourseEnvironment.isParticipant()) return;
		
		if (coachComment != null) {
			AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
			Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
			am.saveNodeCoachComment(courseNode, assessedIdentity, coachComment);
		}
	}
	
	@Override
	public List<File> getIndividualAssessmentDocuments(CourseNode courseNode, UserCourseEnvironment userCourseEnvironment) {
		AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
		Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
		return am.getIndividualAssessmentDocuments(courseNode, assessedIdentity);
	}

	@Override
	public void addIndividualAssessmentDocument(CourseNode courseNode, File document, String filename,
			UserCourseEnvironment userCourseEnvironment, Identity coachingIdentity) {
		if (!userCourseEnvironment.isParticipant()) return;
		
		if (document != null) {
			AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
			Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
			am.addIndividualAssessmentDocument(courseNode, coachingIdentity, assessedIdentity, document, filename);
		}
	}

	@Override
	public void removeIndividualAssessmentDocument(CourseNode courseNode, File document,
			UserCourseEnvironment userCourseEnvironment, Identity coachingIdentity) {
		if (!userCourseEnvironment.isParticipant()) return;
		
		if (document != null) {
			AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
			Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
			am.removeIndividualAssessmentDocument(courseNode, coachingIdentity, assessedIdentity, document);
		}
	}

	@Override
	public void updateLastModifications(CourseNode courseNode, UserCourseEnvironment userCourseEnvironment,
			Identity identity2, Role by) {
		if (!userCourseEnvironment.isParticipant()) return;
		
		AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
		Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
		am.updateLastModifications(courseNode, assessedIdentity, userCourseEnvironment, by);
	}

	@Override
	public String getAuditLog(CourseNode courseNode, UserCourseEnvironment userCourseEnvironment) {
		UserNodeAuditManager am = userCourseEnvironment.getCourseEnvironment().getAuditManager();
		Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
		return am.getUserNodeLog(courseNode, assessedIdentity);
	}

	@Override
	public void saveScoreEvaluation(CourseNode courseNode, Identity identity, ScoreEvaluation scoreEvaluation,
			UserCourseEnvironment userCourseEnvironment, boolean incrementUserAttempts, Role by) {
		if (!userCourseEnvironment.isParticipant()) return;
		
		AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
		Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
		am.saveScoreEvaluation(courseNode, identity, assessedIdentity, scoreEvaluation, userCourseEnvironment,
				incrementUserAttempts, by);
	}
	
	@Override
	public Overridable<Boolean> getRootPassed(UserCourseEnvironment userCourseEnvironment) {
		if (!userCourseEnvironment.isParticipant()) return Overridable.empty();
		
		AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
		return am.getRootPassed(userCourseEnvironment);
	}

	@Override
	public Overridable<Boolean> overrideRootPassed(Identity coach, UserCourseEnvironment userCourseEnvironment, Boolean passed) {
		if (!userCourseEnvironment.isParticipant()) return Overridable.empty();
		
		AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
		return am.overrideRootPassed(coach, userCourseEnvironment, passed);
	}

	@Override
	public Overridable<Boolean> resetRootPassed(Identity coach, UserCourseEnvironment userCourseEnvironment) {
		if (!userCourseEnvironment.isParticipant()) return Overridable.empty();
		
		AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
		return am.resetRootPassed(coach, userCourseEnvironment);
	}

	@Override
	public Controller getDetailsEditController(UserRequest ureq, WindowControl wControl, BreadcrumbPanel stackPanel,
			CourseNode courseNode, UserCourseEnvironment coachCourseEnv,
			UserCourseEnvironment assessedUserCourseEnvironment) {
		return getAssessmentHandler(courseNode).getDetailsEditController(ureq, wControl, stackPanel, courseNode,
				coachCourseEnv, assessedUserCourseEnvironment);
	}

	@Override
	public AssessmentCourseNodeController getIdentityListController(UserRequest ureq, WindowControl wControl,
			TooledStackedPanel stackPanel, CourseNode courseNode, RepositoryEntry courseEntry, BusinessGroup group,
			UserCourseEnvironment coachCourseEnv, AssessmentToolContainer toolContainer,
			AssessmentToolSecurityCallback assessmentCallback, boolean showTitle) {
		if (getAssessmentHandler(courseNode).hasCustomIdentityList()) {
			return getAssessmentHandler(courseNode).getIdentityListController(ureq, wControl, stackPanel, courseNode,
					courseEntry, group, coachCourseEnv, toolContainer, assessmentCallback, showTitle);
		}
		return new IdentityListCourseNodeController(ureq, wControl, stackPanel, courseEntry, group, courseNode,
				coachCourseEnv, toolContainer, assessmentCallback, showTitle);
	}
	
	@Override
	public AssessmentCourseNodeController getCourseNodeRunController(UserRequest ureq, WindowControl wControl,
			TooledStackedPanel stackPanel, CourseNode courseNode, UserCourseEnvironment coachCourseEnv) {
		RepositoryEntry courseEntry = coachCourseEnv.getCourseEnvironment().getCourseGroupManager().getCourseEntry();
		AssessmentToolSecurityCallback assessmentCallback = createCourseNodeRunSecurityCallback(ureq, coachCourseEnv);
		return getIdentityListController(ureq, wControl, stackPanel, courseNode, courseEntry, null, coachCourseEnv,
				new AssessmentToolContainer(), assessmentCallback, false);
	}

	private AssessmentToolSecurityCallback createCourseNodeRunSecurityCallback(UserRequest ureq, UserCourseEnvironment userCourseEnv) {
		// see CourseRuntimeController.doAssessmentTool(ureq);
		GroupRoles role = userCourseEnv.isCoach()? GroupRoles.coach: GroupRoles.owner;
		boolean hasAssessmentRight = userCourseEnv.getCourseEnvironment().getCourseGroupManager()
				.hasRight(userCourseEnv.getIdentityEnvironment().getIdentity(), CourseRights.RIGHT_ASSESSMENT, role);

		RepositoryEntry courseEntry = userCourseEnv.getCourseEnvironment().getCourseGroupManager().getCourseEntry();
		RepositoryEntrySecurity reSecurity = repositoryManager.isAllowed(ureq, courseEntry);
		boolean admin = userCourseEnv.isAdmin() || hasAssessmentRight;

		boolean nonMembers = reSecurity.isEntryAdmin();
		List<BusinessGroup> coachedGroups = null;
		if (reSecurity.isGroupCoach()) {
			coachedGroups = userCourseEnv.getCoachedGroups();
		}
		return new AssessmentToolSecurityCallback(admin, nonMembers, reSecurity.isCourseCoach(),
				reSecurity.isGroupCoach(), reSecurity.isCurriculumCoach(), coachedGroups);
	}

	@Override
	public boolean onNodeVisited(CourseNode courseNode, UserCourseEnvironment userCourseEnvironment) {
		if (!userCourseEnvironment.isParticipant()) return false;
		
		AssessmentManager am = userCourseEnvironment.getCourseEnvironment().getAssessmentManager();
		Identity assessedIdentity = userCourseEnvironment.getIdentityEnvironment().getIdentity();
		am.updateLastVisited(courseNode, assessedIdentity, new Date());
		return false;
	}

	@Override
	public void evaluateAll(ICourse course) {
		log.debug("Evaluate all score accountings for course {}", course);
		CourseEnvironment courseEnv = course.getCourseEnvironment();
		RepositoryEntry courseEntry = courseEnv.getCourseGroupManager().getCourseEntry();
		CoursePropertyManager pm = courseEnv.getCoursePropertyManager();
		
		Set<Identity> identities = new HashSet<>();
		List<Identity> assessedIdentities = pm.getAllIdentitiesWithCourseAssessmentData(null);
		identities.addAll(assessedIdentities);
		List<Identity> members = repositoryService.getMembers(courseEntry, RepositoryEntryRelationType.all, GroupRoles.participant.name());
		identities.addAll(members);
		
		for(Identity identity: identities) {
			evaluateAll(courseEnv, identity);
			log.debug("Evaluated score accounting in course {} for {}", course, identity);
			dbInstance.commitAndCloseSession();
		}
	}

	private void evaluateAll(CourseEnvironment courseEnv, Identity assessedIdentity) {
		IdentityEnvironment identityEnv = new IdentityEnvironment();
		identityEnv.setIdentity(assessedIdentity);
		UserCourseEnvironment userCourseEnv = new UserCourseEnvironmentImpl(identityEnv, courseEnv);
		
		RepositoryEntry courseEntry = courseEnv.getCourseGroupManager().getCourseEntry();
		CourseNode rootNode = courseEnv.getRunStructure().getRootNode();
		AssessmentEntry rootAssessmentEntry = assessmentService.loadAssessmentEntry(assessedIdentity, courseEntry, rootNode.getIdent());
		Boolean previousPassed = rootAssessmentEntry != null
				? rootAssessmentEntry.getPassedOverridable().getCurrent()
				: null;
		
		ScoreAccounting scoreAccounting = userCourseEnv.getScoreAccounting();
		scoreAccounting.evaluateAll(true);
		
		AssessmentEvaluation rootAssessmentEvaluation = scoreAccounting.evalCourseNode(rootNode);
		Boolean currentPassed = rootAssessmentEvaluation.getPassed();
		
		// Save root score evaluation to propagate to efficiency statement
		if (!Objects.equals(previousPassed, currentPassed)) {
			saveScoreEvaluation(rootNode, null, rootAssessmentEvaluation, userCourseEnv, false, null);
		}
	}

	@Override
	public void evaluateStartOver(Date start) {
		List<AssessmentEntry> rootEntries = assessmentService.getRootEntriesWithStartOverSubEntries(start);
		for (AssessmentEntry rootEntry : rootEntries) {
			try {
				tryEvaluateStartOver(rootEntry);
			} catch (Exception e) {
				log.warn("Error when evaluate assessment entries after start over. {}", rootEntry);
			}
		}
	}

	private void tryEvaluateStartOver(AssessmentEntry rootEntry) {
		ICourse course = CourseFactory.loadCourse(rootEntry.getRepositoryEntry());
		CourseEnvironment courseEnv = course.getCourseEnvironment();
		Identity assessedIdentity = rootEntry.getIdentity();
		evaluateAll(courseEnv, assessedIdentity);
		log.debug("Evaluated score accounting after start over in course {} for {}", course, assessedIdentity);
		dbInstance.commitAndCloseSession();
	}
}
