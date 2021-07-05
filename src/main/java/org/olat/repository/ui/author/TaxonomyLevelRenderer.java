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
package org.olat.repository.ui.author;

import java.util.List;

import org.olat.core.gui.components.form.flexible.impl.elements.table.FlexiCellRenderer;
import org.olat.core.gui.components.form.flexible.impl.elements.table.FlexiTableComponent;
import org.olat.core.gui.render.Renderer;
import org.olat.core.gui.render.StringOutput;
import org.olat.core.gui.render.URLBuilder;
import org.olat.core.gui.translator.Translator;
import org.olat.modules.taxonomy.TaxonomyLevel;

/**
 * 
 * Initial date: 7 Sep 2020<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
public class TaxonomyLevelRenderer implements FlexiCellRenderer {

	@Override
	public void render(Renderer renderer, StringOutput target, Object cellValue, int row, FlexiTableComponent source,
			URLBuilder ubu, Translator translator) {
		if (cellValue != null) {
			@SuppressWarnings("unchecked")
			List<TaxonomyLevel> taxonomyLevels = (List<TaxonomyLevel>)cellValue;
			taxonomyLevels.sort((l1, l2) -> l1.getDisplayName().compareToIgnoreCase(l2.getDisplayName())); 
			for (TaxonomyLevel taxonomyLevel : taxonomyLevels) {
				target.append("<div class='o_nowrap'>").append(taxonomyLevel.getDisplayName()).append("</div>");
			}
		}
	}

}
