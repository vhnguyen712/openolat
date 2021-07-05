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
package org.olat.modules.grading.ui.wizard;

import java.util.List;
import java.util.Locale;

import org.olat.core.gui.components.form.flexible.impl.elements.table.DefaultFlexiTableDataModel;
import org.olat.core.gui.components.form.flexible.impl.elements.table.FlexiTableColumnModel;
import org.olat.core.id.Identity;
import org.olat.user.propertyhandlers.UserPropertyHandler;

/**
 * 
 * Initial date: 17 janv. 2020<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class ImportGraderOverviewDataModel extends DefaultFlexiTableDataModel<Identity> {
	
	private final Locale locale;
	private FlexiTableColumnModel columnModel;
	private final List<UserPropertyHandler> userPropertyHandlers;
	
	public ImportGraderOverviewDataModel(List<UserPropertyHandler> userPropertyHandlers,
			Locale locale, FlexiTableColumnModel columnModel) {
		super(columnModel);
		this.locale = locale;
		this.userPropertyHandlers = userPropertyHandlers;
	}

	@Override
	public Object getValueAt(int row, int col) {
		Identity identity = getObject(row);
		if(col >= 0 && col < userPropertyHandlers.size()) {
			UserPropertyHandler handler = userPropertyHandlers.get(col);
			return handler.getUserProperty(identity.getUser(), locale);
		}
		return "ERROR";
	}

	@Override
	public ImportGraderOverviewDataModel createCopyWithEmptyList() {
		return new ImportGraderOverviewDataModel(userPropertyHandlers, locale, columnModel);
	}
}
