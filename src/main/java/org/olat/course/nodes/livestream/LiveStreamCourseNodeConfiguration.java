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

import java.util.Locale;

import org.olat.core.CoreSpringFactory;
import org.olat.core.gui.translator.Translator;
import org.olat.core.util.Util;
import org.olat.core.util.nodes.INode;
import org.olat.course.nodes.AbstractCourseNodeConfiguration;
import org.olat.course.nodes.CourseNode;
import org.olat.course.nodes.CourseNodeGroup;
import org.olat.course.nodes.LiveStreamCourseNode;
import org.olat.course.nodes.livestream.ui.LiveStreamEditController;

/**
 *
 * Initial date: 23.05.2019<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
public class LiveStreamCourseNodeConfiguration extends AbstractCourseNodeConfiguration {

	@Override
	public String getAlias() {
		return "livestream";
	}

	@Override
	public String getGroup() {
		return CourseNodeGroup.content.name();
	}

	@Override
	public CourseNode getInstance(INode parent) {
		return new LiveStreamCourseNode(parent);
	}

	@Override
	public String getLinkText(Locale locale) {
		Translator translator = Util.createPackageTranslator(LiveStreamEditController.class, locale);
		return translator.translate("link.text");
	}

	@Override
	public String getIconCSSClass() {
		return "o_livestream_icon";
	}

	@Override
	public boolean isEnabled() {
		LiveStreamModule module = CoreSpringFactory.getImpl(LiveStreamModule.class);
		return module.isEnabled();
	}

}
