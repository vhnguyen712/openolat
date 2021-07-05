/**
 * <a href="https://www.openolat.org">
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
 * frentix GmbH, https://www.frentix.com
 * <p>
 */
package org.olat.core.gui.components.form.flexible.impl.elements.table;

/**
 * 
 * The StickyActionColumnModel represent a table column with the action link at
 * the right side of the column. If the table has overflow and the action menu
 * is scrolled to the invisible area, the action column with stick to the right
 * side of the table to make it always visible and accessible. In this case the
 * action column is rendered above the table in a hovering kind of style.
 * 
 * Initial date: 18 may 2021<br>
 * 
 * @author gnaegi, gnaegi@frentix.com, https://www.frentix.com
 *
 */
public class StickyActionColumnModel extends DefaultFlexiColumnModel {

	
	public StickyActionColumnModel(String headerKey, int columnIndex) {
		super(headerKey, columnIndex, false, null);
		setColumnCssClass("o_col_sticky_right o_col_action");
	}

	public StickyActionColumnModel(FlexiColumnDef def) {
		this(def.i18nHeaderKey(), def.ordinal());
	}	

}
