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
package org.olat.course.nodes.iq;

import org.olat.core.gui.components.form.flexible.impl.elements.table.FlexiCellRenderer;
import org.olat.core.gui.components.form.flexible.impl.elements.table.FlexiTableComponent;
import org.olat.core.gui.render.Renderer;
import org.olat.core.gui.render.StringOutput;
import org.olat.core.gui.render.URLBuilder;
import org.olat.core.gui.translator.Translator;

/**
 * 
 * Initial date: 20 déc. 2017<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class ExtraTimeCellRenderer implements FlexiCellRenderer {

	@Override
	public void render(Renderer renderer, StringOutput target, Object cellValue, int row,
			FlexiTableComponent source, URLBuilder ubu, Translator translator) {
		if(cellValue instanceof ExtraInfos) {
			ExtraInfos infos = (ExtraInfos)cellValue;
			Integer extraTimeInSeconds = infos.getExtraTimeInSeconds();
			Integer compensationExtraTimeInSeconds = infos.getCompensationExtraTimeInSeconds();
	
			if(extraTimeInSeconds != null) {
				int extraTimeInMinutes = extraTimeInSeconds.intValue() / 60;
				target.append("<i class='o_icon o_icon_extra_time'> </i> ").append("+").append(extraTimeInMinutes).append("m");
			}
			if(compensationExtraTimeInSeconds != null) {
				int extraTimeInMinutes = compensationExtraTimeInSeconds.intValue() / 60;
				target.append("<i class='o_icon o_icon_disadvantage_compensation'> </i> ").append("+").append(extraTimeInMinutes).append("m");
			}
		}
	}
}
