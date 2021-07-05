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
package org.olat.course.editor;

/**
 * 
 * Initial date: 4 Sep 2019<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
public class ConditionAccessEditConfig {
	
	private static final ConditionAccessEditConfig CUSTOM = new ConditionAccessEditConfig(true, false);
	private static final ConditionAccessEditConfig REGULAR_SHOW_PASSWORD = new ConditionAccessEditConfig(false, true);
	private static final ConditionAccessEditConfig REGULAR_HIDE_PASSWORD = new ConditionAccessEditConfig(false, false);
	
	private final boolean customAccessConditionController;
	private final boolean showPassword;

	public static final ConditionAccessEditConfig custom() {
		return CUSTOM;
	}
	
	public static final ConditionAccessEditConfig regular(boolean showPassword) {
		return showPassword? REGULAR_SHOW_PASSWORD: REGULAR_HIDE_PASSWORD;
	}
	
	private ConditionAccessEditConfig(boolean customAccessConditionController, boolean showPassword) {
		this.customAccessConditionController = customAccessConditionController;
		this.showPassword = showPassword;
	}

	public boolean isCustomAccessConditionController() {
		return customAccessConditionController;
	}

	public boolean isShowPassword() {
		return showPassword;
	}

}
