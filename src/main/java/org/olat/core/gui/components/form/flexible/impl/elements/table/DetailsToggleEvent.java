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
package org.olat.core.gui.components.form.flexible.impl.elements.table;

import org.olat.core.gui.components.form.flexible.FormItem;
import org.olat.core.gui.components.form.flexible.impl.FormEvent;

/**
 * 
 * Initial date: 03.06.2021<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.comm
 *
 */
public class DetailsToggleEvent extends FormEvent {
	
	private static final long serialVersionUID = -1526403121496294576L;
	public static final String CMD = "details-toggle";
	
	private final int rowIndex;
	private final boolean visible;
	
	public DetailsToggleEvent(FormItem source, int columnIndex, boolean visible) {
		super(CMD, source, FormEvent.ONCLICK);
		this.rowIndex = columnIndex;
		this.visible = visible;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public boolean isVisible() {
		return visible;
	}

}
