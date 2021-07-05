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
package org.olat.course.nodes.survey.ui;

import org.olat.core.gui.UserRequest;
import org.olat.core.gui.components.Component;
import org.olat.core.gui.components.velocity.VelocityContainer;
import org.olat.core.gui.control.Controller;
import org.olat.core.gui.control.Event;
import org.olat.core.gui.control.WindowControl;
import org.olat.core.gui.control.controller.BasicController;
import org.olat.course.ICourse;
import org.olat.course.groupsandrights.CourseGroupManager;
import org.olat.course.noderight.ui.NodeRightsController;
import org.olat.course.nodes.SurveyCourseNode;
import org.olat.repository.RepositoryEntry;

/**
 * 
 * Initial date: 26 Feb 2021<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
public class SurveyConfigsController extends BasicController {
	
	private Controller configCtrl;
	private Controller nodeRightCtrl;

	public SurveyConfigsController(UserRequest ureq, WindowControl wControl, SurveyCourseNode courseNode,
			ICourse course) {
		super(ureq, wControl);
		
		VelocityContainer mainVC = createVelocityContainer("configs");
		
		RepositoryEntry courseEntry = course.getCourseEnvironment().getCourseGroupManager().getCourseEntry();
		configCtrl = new SurveyConfigController(ureq, wControl, courseNode, courseEntry);
		listenTo(configCtrl);
		mainVC.put("config", configCtrl.getInitialComponent());
		
		CourseGroupManager courseGroupManager = course.getCourseEnvironment().getCourseGroupManager();
		nodeRightCtrl = new NodeRightsController(ureq, getWindowControl(), courseGroupManager,
				SurveyCourseNode.NODE_RIGHT_TYPES, courseNode.getModuleConfiguration(), null);
		listenTo(nodeRightCtrl);
		mainVC.put("rights", nodeRightCtrl.getInitialComponent());
		
		putInitialPanel(mainVC);
	}

	@Override
	protected void event(UserRequest ureq, Component source, Event event) {
		//
	}
	
	@Override
	protected void event(UserRequest ureq, Controller source, Event event) {
		if (source == configCtrl) {
			fireEvent(ureq, event);
		} else if (source == nodeRightCtrl) {
			fireEvent(ureq, event);
		}
		super.event(ureq, source, event);
	}

	@Override
	protected void doDispose() {
		
	}

}
