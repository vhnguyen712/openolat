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

import java.util.ArrayList;
import java.util.List;

import org.olat.core.configuration.AbstractSpringModule;
import org.olat.core.configuration.ConfigOnOff;
import org.olat.core.util.StringHelper;
import org.olat.core.util.coordinate.CoordinatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 
 * Initial date: 13 janv. 2020<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
@Service("gradingModule")
public class GradingModule extends AbstractSpringModule implements ConfigOnOff {
	
	private static final String PROP_ENABLED = "grading.enabled";
	private static final String PROP_REAL_MINUTES_VISIBILITY = "grading.correction.real.minutes.visibility";

	@Value("${grading.enabled:true}")
	private boolean enabled;

	@Value("${grading.correction.real.minutes.visibility:administrator,learnresourcemanager,author,grader}")
	private String correctionRealMinutesVisibility;

	@Autowired
	public GradingModule(CoordinatorManager coordinatorManager) {
		super(coordinatorManager);
	}

	@Override
	public void init() {
		String enabledObj = getStringPropertyValue(PROP_ENABLED, true);
		if(StringHelper.containsNonWhitespace(enabledObj)) {
			enabled = "true".equals(enabledObj);
		}
		
		correctionRealMinutesVisibility = getStringPropertyValue(PROP_REAL_MINUTES_VISIBILITY, correctionRealMinutesVisibility);
	}

	@Override
	protected void initFromChangedProperties() {
		init();
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		setBooleanProperty(PROP_ENABLED, enabled, true);
	}

	public List<String> getCorrectionRealMinutesVisibility() {
		List<String> roles = new ArrayList<>(5);
		if(StringHelper.containsNonWhitespace(correctionRealMinutesVisibility)) {
			String[] arr = correctionRealMinutesVisibility.split("[,]");
			for(String r:arr) {
				if(StringHelper.containsNonWhitespace(r)) {
					roles.add(r);
				}
			}
		}
		return roles;
	}

	public void setCorrectionRealMinutesVisibility(List<String> roles) {
		StringBuilder sb = new StringBuilder();
		if(roles != null && !roles.isEmpty()) {
			for(String role:roles) {
				if(sb.length() > 0) sb.append(",");
				sb.append(role);
			}
		}
		correctionRealMinutesVisibility = sb.toString();
		setStringProperty(PROP_REAL_MINUTES_VISIBILITY, correctionRealMinutesVisibility, true);
	}
}
