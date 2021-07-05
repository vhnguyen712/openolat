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
package org.olat.course.condition;

import org.olat.course.nodes.CourseNode;
import org.olat.course.run.userview.CourseTreeModelBuilder;
import org.olat.course.run.userview.CourseTreeNode;
import org.olat.course.run.userview.NodeEvaluation;
import org.olat.course.run.userview.UserCourseEnvironment;

/**
 * 
 * Initial date: 25 Sep 2019<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
public class ConditionCourseTreeModelBuilder extends CourseTreeModelBuilder {

	protected ConditionCourseTreeModelBuilder(UserCourseEnvironment userCourseEnv) {
		super(userCourseEnv);
	}

	@Override
	protected CourseTreeNode createCourseTreeNode(CourseNode courseNode, CourseTreeNode parent, int treeLevel) {
		NodeEvaluation nodeEval = new NodeEvaluation();
		courseNode.calcAccessAndVisibility(userCourseEnv.getConditionInterpreter(), nodeEval);
		
		CourseTreeNode courseTreeNode = new CourseTreeNode(courseNode, treeLevel);
		courseTreeNode.setNodeEvaluation(nodeEval);
		boolean parentAccessible = parent == null || parent.isAccessible();
		courseTreeNode.setVisible(nodeEval.isVisible() && parentAccessible);
		courseTreeNode.setAccessible(nodeEval.isAtLeastOneAccessible());
		return courseTreeNode;
	}

}
