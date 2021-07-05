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
package org.olat.ims.lti13;

import java.util.ArrayList;
import java.util.List;

import org.olat.core.configuration.AbstractSpringModule;
import org.olat.core.configuration.ConfigOnOff;
import org.olat.core.helpers.Settings;
import org.olat.core.util.StringHelper;
import org.olat.core.util.coordinate.CoordinatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 
 * Initial date: 17 févr. 2021<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
@Service
public class LTI13Module extends AbstractSpringModule implements ConfigOnOff {

	private static final String PROP_ENABLED = "lti13.enabled";
	private static final String PROP_PLATFORM_ISS = "lti13.platform.iss";
	private static final String PROP_MATCHING_BY_EMAIL = "lti13.platform.matching.by.email";
	private static final String PROP_DEFAULT_ORGANISATION = "lti13.default.organisation";
	private static final String PROP_DEPLOYMENT_ROLES_REPOSITORY_ENTRY = "lti13.deployment.roles.repositoryentry";
	private static final String PROP_DEPLOYMENT_ROLES_BUSINESS_GROUP = "lti13.deployment.roles.businessgroup";
	
	
	@Value("${lti13.enabled}")
	private boolean enabled;

	@Value("${lti13.platform.iss}")
	private String platformIss;
	
	@Value("${lti13.platform.matching.by.email:enabled}")
	private String matchingByEmail;
	
	@Value("${lti13.default.organisation}")
	private String defaultOrganisationKey;
	
	@Value("${lti13.deployment.roles.repositoryentry:user,author,learnresourcemanager,administrator}")
	private String deploymentRolesForRepositoryEntries;
	@Value("${lti13.deployment.roles.businessgroup:user,author,groupmanager,administrator}")
	private String deploymentRolesForBusinessGroups;
	
	@Autowired
	public LTI13Module(CoordinatorManager coordinatorManager) {
		super(coordinatorManager);
	}

	@Override
	public void init() {
		String enabledObj = getStringPropertyValue(PROP_ENABLED, true);
		if(StringHelper.containsNonWhitespace(enabledObj)) {
			enabled = "true".equals(enabledObj);
		}
		
		matchingByEmail = getStringPropertyValue(PROP_MATCHING_BY_EMAIL, matchingByEmail);
		defaultOrganisationKey = getStringPropertyValue(PROP_DEFAULT_ORGANISATION, defaultOrganisationKey);
		deploymentRolesForRepositoryEntries = getStringPropertyValue(PROP_DEPLOYMENT_ROLES_REPOSITORY_ENTRY, deploymentRolesForRepositoryEntries);
		deploymentRolesForBusinessGroups = getStringPropertyValue(PROP_DEPLOYMENT_ROLES_BUSINESS_GROUP, deploymentRolesForBusinessGroups);
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

	public String getPlatformIss() {
		return platformIss;
	}

	public void setPlatformIss(String platformIss) {
		this.platformIss = platformIss;
		setStringProperty(PROP_PLATFORM_ISS, platformIss, true);
	}
	
	public String getDefaultOrganisationKey() {
		return defaultOrganisationKey;
	}

	public void setDefaultOrganisationKey(String defaultOrganisationKey) {
		this.defaultOrganisationKey = defaultOrganisationKey;
		setStringProperty(PROP_DEFAULT_ORGANISATION, defaultOrganisationKey, true);
	}

	public boolean isMatchingByEmailEnabled() {
		return "enabled".equals(matchingByEmail);
	}

	public void setMatchByEmail(String enabled) {
		this.matchingByEmail = enabled;
		setStringProperty(PROP_MATCHING_BY_EMAIL, enabled, true);
	}

	public String getDeploymentRolesForRepositoryEntries() {
		return deploymentRolesForRepositoryEntries;
	}

	public void setDeploymentRolesForRepositoryEntries(String deploymentRoles) {
		this.deploymentRolesForRepositoryEntries = deploymentRoles;
		setStringProperty(PROP_DEPLOYMENT_ROLES_REPOSITORY_ENTRY, deploymentRoles, true);
	}
	
	public List<LTI13Roles> getDeploymentRolesListForRepositoryEntries() {
		return toRoles(getDeploymentRolesForRepositoryEntries());
	}

	public String getDeploymentRolesForBusinessGroups() {
		return deploymentRolesForBusinessGroups;
	}

	public void setDeploymentRolesForBusinessGroups(String deploymentRoles) {
		this.deploymentRolesForBusinessGroups = deploymentRoles;
		setStringProperty(PROP_DEPLOYMENT_ROLES_BUSINESS_GROUP, deploymentRoles, true);
	}
	
	public List<LTI13Roles> getDeploymentRolesListForBusinessGroups() {
		return toRoles(getDeploymentRolesForBusinessGroups());
	}
	
	private List<LTI13Roles> toRoles(String roles) {
		List<LTI13Roles> roleList = new ArrayList<>(5);
		if(StringHelper.containsNonWhitespace(roles)) {
			String[] values = roles.split("[,]");
			for(String value:values) {
				if(LTI13Roles.valid(value)) {
					roleList.add(LTI13Roles.valueOf(value));
				}
			}
		}
		return roleList;
	}

	public String getPlatformJwkSetUri() {
		return Settings.getServerContextPathURI() + LTI13Dispatcher.LTI_JWKSET_PATH;
	}
	
	public String getPlatformAuthorizationUri() {
		return Settings.getServerContextPathURI() + LTI13Dispatcher.LTI_AUTHORIZATION_PATH;
	}
	
	public String getPlatformTokenUri() {
		return Settings.getServerContextPathURI() + LTI13Dispatcher.LTI_TOKEN_PATH;
	}
	
	public String getToolLoginInitiationUri() {
		return Settings.getServerContextPathURI() + LTI13Dispatcher.LTI_LOGIN_INITIATION_PATH;
	}
	
	public String getToolLoginRedirectUri() {
		return Settings.getServerContextPathURI() + LTI13Dispatcher.LTI_LOGIN_REDIRECT_PATH;
	}

	
}
