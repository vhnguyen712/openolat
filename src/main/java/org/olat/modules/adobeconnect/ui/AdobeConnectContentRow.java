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
package org.olat.modules.adobeconnect.ui;

import org.olat.core.gui.components.form.flexible.elements.DownloadLink;
import org.olat.modules.adobeconnect.model.AdobeConnectSco;

/**
 * 
 * Initial date: 18 avr. 2019<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class AdobeConnectContentRow {
	
	private final AdobeConnectSco sco;
	private DownloadLink openLink;
	
	public AdobeConnectContentRow(AdobeConnectSco sco) {
		this.sco = sco;
	}
	
	public AdobeConnectSco getSco() {
		return sco;
	}

	public DownloadLink getOpenLink() {
		return openLink;
	}

	public void setOpenLink(DownloadLink openLink) {
		this.openLink = openLink;
	}

	@Override
	public int hashCode() {
		return sco == null ? -21801 : sco.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		if(obj instanceof AdobeConnectContentRow) {
			AdobeConnectContentRow row = (AdobeConnectContentRow)obj;
			return sco != null && sco.equals(row.getSco());
		}
		return false;
	}
}