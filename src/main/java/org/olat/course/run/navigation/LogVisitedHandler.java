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
package org.olat.course.run.navigation;

import org.olat.core.logging.activity.CourseLoggingAction;
import org.olat.core.logging.activity.ThreadLocalUserActivityLogger;
import org.olat.course.nodes.CourseNode;
import org.olat.course.run.userview.UserCourseEnvironment;
import org.olat.util.logging.activity.LoggingResourceable;
import org.springframework.stereotype.Component;

/**
 * 
 * Initial date: 30 Aug 2019<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
@Component
public class LogVisitedHandler implements NodeVisitedListener {

	@Override
	public boolean onNodeVisited(CourseNode courseNode, UserCourseEnvironment userCourseEnvironment) {
		ThreadLocalUserActivityLogger.log(CourseLoggingAction.COURSE_NAVIGATION_NODE_ACCESS, getClass(),
				LoggingResourceable.wrap(courseNode));
		return false;
	}

}
