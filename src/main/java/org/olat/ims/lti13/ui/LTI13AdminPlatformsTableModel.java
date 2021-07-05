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
package org.olat.ims.lti13.ui;

import java.util.List;
import java.util.Locale;

import org.olat.core.commons.persistence.SortKey;
import org.olat.core.gui.components.form.flexible.impl.elements.table.DefaultFlexiTableDataModel;
import org.olat.core.gui.components.form.flexible.impl.elements.table.FlexiSortableColumnDef;
import org.olat.core.gui.components.form.flexible.impl.elements.table.FlexiTableColumnModel;
import org.olat.core.gui.components.form.flexible.impl.elements.table.SortableFlexiTableDataModel;
import org.olat.core.gui.components.form.flexible.impl.elements.table.SortableFlexiTableModelDelegate;
import org.olat.ims.lti13.model.LTI13PlatformWithInfos;

/**
 * 
 * Initial date: 22 févr. 2021<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class LTI13AdminPlatformsTableModel extends DefaultFlexiTableDataModel<LTI13PlatformWithInfos>
implements SortableFlexiTableDataModel<LTI13PlatformWithInfos> {
	
	private static final PlatformsCols[] COLS = PlatformsCols.values();

	private final Locale locale;
	
	public LTI13AdminPlatformsTableModel(FlexiTableColumnModel columnsModel, Locale locale) {
		super(columnsModel);
		this.locale = locale;
	}

	@Override
	public void sort(SortKey orderBy) {
		if(orderBy != null) {
			List<LTI13PlatformWithInfos> rows = new SortableFlexiTableModelDelegate<>(orderBy, this, locale).sort();
			super.setObjects(rows);
		}
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		LTI13PlatformWithInfos tool = getObject(row);
		return getValueAt(tool, col);
	}

	@Override
	public Object getValueAt(LTI13PlatformWithInfos row, int col) {
		switch(COLS[col]) {
			case name: return row.getPlatform().getName();
			case issuer: return row.getPlatform().getIssuer();
			case clientId: return row.getPlatform().getClientId();
			case deployments: return row.getNumOfDeployments();
			default: return "ERROR";
		}
	}

	@Override
	public LTI13AdminPlatformsTableModel createCopyWithEmptyList() {
		return new LTI13AdminPlatformsTableModel(getTableColumnModel(), locale);
	}

	public enum PlatformsCols implements FlexiSortableColumnDef  {
		name("table.header.name"),
		issuer("table.header.issuer"),
		clientId("table.header.client.id"),
		deployments("table.header.num.deployments");
		
		private final String i18nHeaderKey;
		
		private PlatformsCols(String i18nHeaderKey) {
			this.i18nHeaderKey = i18nHeaderKey;
		}

		@Override
		public boolean sortable() {
			return true;
		}

		@Override
		public String sortKey() {
			return name();
		}

		@Override
		public String i18nHeaderKey() {
			return i18nHeaderKey;
		}
	}

}
