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
package org.olat.course.nodes.livestream;

import org.olat.course.nodes.LiveStreamCourseNode;
import org.olat.course.run.userview.UserCourseEnvironment;
import org.olat.modules.ModuleConfiguration;

/**
 * 
 * Initial date: 24 May 2019<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
public class LiveStreamSecurityCallbackFactory {
	
	public static LiveStreamSecurityCallback createSecurityCallback(UserCourseEnvironment userCourseEnv,
			ModuleConfiguration config) {
		boolean canViewStreams = true;
		boolean canViewStatistic = userCourseEnv.isAdmin() || userCourseEnv.isCoach();
		boolean canEditStreams = 
				userCourseEnv.isAdmin() 
				|| (userCourseEnv.isCoach() && config.getBooleanSafe(LiveStreamCourseNode.CONFIG_COACH_CAN_EDIT));
		return createSecurityCallback(canViewStreams, canViewStatistic, canEditStreams);
	}

	public static LiveStreamSecurityCallback createSecurityCallback(boolean canViewStreams, boolean canViewStatistic,
			boolean canEditStreams) {
		LiveStreamSecurityCallbackImpl secCallback = new LiveStreamSecurityCallbackImpl();
		secCallback.setCanViewStreams(canViewStreams);
		secCallback.setCanViewStatistic(canViewStatistic);
		secCallback.setCanEditStreams(canEditStreams);
		return secCallback;
	}
	
	private static class LiveStreamSecurityCallbackImpl implements LiveStreamSecurityCallback {
		
		private boolean canViewStreams;
		private boolean canViewStatistic;
		private boolean canEditStreams;

		@Override
		public boolean canViewStreams() {
			return canViewStreams;
		}

		private void setCanViewStreams(boolean canViewStreams) {
			this.canViewStreams = canViewStreams;
		}
		
		@Override
		public boolean canViewStatistic() {
			return canViewStatistic;
		}

		private void setCanViewStatistic(boolean canViewStatistic) {
			this.canViewStatistic = canViewStatistic;
		}

		@Override
		public boolean canEditStreams() {
			return canEditStreams;
		}

		private void setCanEditStreams(boolean canEditStreams) {
			this.canEditStreams = canEditStreams;
		}
		
	}

}
