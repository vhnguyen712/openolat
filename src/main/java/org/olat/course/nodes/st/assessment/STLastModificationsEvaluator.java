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
package org.olat.course.nodes.st.assessment;

import java.util.Date;
import java.util.List;

import org.olat.core.util.DateUtils;
import org.olat.course.run.scoring.AssessmentEvaluation;
import org.olat.course.run.scoring.LastModificationsEvaluator;

/**
 * 
 * Initial date: 18 Sep 2019<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
public class STLastModificationsEvaluator implements LastModificationsEvaluator {

	@Override
	public LastModifications getLastModifications(AssessmentEvaluation currentEvaluation,
			List<AssessmentEvaluation> children) {
		Date lastUserModified = currentEvaluation.getLastUserModified();
		Date lastCoachModified = currentEvaluation.getLastCoachModified();
		for (AssessmentEvaluation child : children) {
			lastUserModified = DateUtils.getLater(lastUserModified, child.getLastUserModified());
			lastCoachModified = DateUtils.getLater(lastCoachModified, child.getLastCoachModified());
		}
		
		return LastModificationsEvaluator.of(lastUserModified, lastCoachModified);
	}

}
