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
package org.olat.course.learningpath.evaluation;

import java.util.List;

import org.olat.core.CoreSpringFactory;
import org.olat.course.learningpath.LearningPathService;
import org.olat.course.nodes.CourseNode;
import org.olat.course.run.scoring.AssessmentEvaluation;
import org.olat.course.run.scoring.DurationEvaluator;

/**
 * 
 * Initial date: 1 Sep 2019<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
public class ConfigDurationEvaluator implements DurationEvaluator {

	@Override
	public boolean isDependingOnCurrentNode() {
		return true;
	}

	@Override
	public Integer getDuration(CourseNode courseNode) {
		LearningPathService learningPathService = CoreSpringFactory.getImpl(LearningPathService.class);
		return learningPathService.getConfigs(courseNode).getDuration();
	}

	@Override
	public boolean isDependingOnChildNodes() {
		return false;
	}

	@Override
	public Integer getDuration(List<AssessmentEvaluation> children) {
		return null;
	}

}
