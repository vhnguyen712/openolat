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
package org.olat.course.wizard.provider.exam;

import org.olat.core.gui.UserRequest;
import org.olat.core.gui.components.form.flexible.impl.Form;
import org.olat.core.gui.control.WindowControl;
import org.olat.core.gui.control.generic.wizard.BasicStep;
import org.olat.core.gui.control.generic.wizard.PrevNextFinishConfig;
import org.olat.core.gui.control.generic.wizard.StepFormController;
import org.olat.core.gui.control.generic.wizard.StepsRunContext;
import org.olat.core.util.Util;
import org.olat.course.wizard.CourseWizardService;
import org.olat.course.wizard.ui.CertificateController;
import org.olat.repository.RepositoryEntry;

/**
 * 
 * Initial date: 11 Dec 2020<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
public class CertificateStep extends BasicStep {
	
	private final RepositoryEntry entry;
	
	public static BasicStep create(UserRequest ureq, RepositoryEntry entry, ExamCourseSteps examCourseSteps) {
		if (examCourseSteps.isCertificate()) {
			return new CertificateStep(ureq, entry, examCourseSteps);
		}
		return CoachesSelectionStep.create(ureq, examCourseSteps);
	}

	private CertificateStep(UserRequest ureq, RepositoryEntry entry, ExamCourseSteps examCourseSteps) {
		super(ureq);
		this.entry = entry;
		setTranslator(Util.createPackageTranslator(CourseWizardService.class, getLocale(), getTranslator()));
		setI18nTitleAndDescr("wizard.title.certificate", null);
		setNextStep(CoachesSelectionStep.create(ureq, examCourseSteps));
	}
	
	@Override
	public PrevNextFinishConfig getInitialPrevNextFinishConfig() {
		return PrevNextFinishConfig.BACK_NEXT;
	}

	@Override
	public StepFormController getStepController(UserRequest ureq, WindowControl windowControl,
			StepsRunContext stepsRunContext, Form form) {
		return new CertificateController(ureq, windowControl, form, stepsRunContext, entry);
	}

}
