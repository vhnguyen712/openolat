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
package org.olat.modules.grading;

/**
 * 
 * Initial date: 22 janv. 2020<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public enum GradingAssignmentStatus {
	
	unassigned("o_grad_assignment_unassigned"),
	assigned("o_grad_assignment_assigned"),
	inProcess("o_grad_assignment_inprocess"),
	done("o_grad_assignment_done"),
	deactivated("o_grad_assignment_deactivated");
	
	private final String iconCssClass;
	
	private GradingAssignmentStatus(String iconCssClass) {
		this.iconCssClass = iconCssClass;
	}
	
	public String iconCssClass() {
		return iconCssClass;
	}
	
	public static boolean canGrade(GradingAssignmentStatus status) {
		return status == GradingAssignmentStatus.assigned
				|| status == GradingAssignmentStatus.inProcess;
	}

}
