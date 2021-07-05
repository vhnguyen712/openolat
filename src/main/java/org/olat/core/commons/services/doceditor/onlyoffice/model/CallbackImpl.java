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
package org.olat.core.commons.services.doceditor.onlyoffice.model;

import java.util.Arrays;
import java.util.List;

import org.olat.core.commons.services.doceditor.onlyoffice.Action;
import org.olat.core.commons.services.doceditor.onlyoffice.Callback;

/**
 * 
 * Initial date: 12 Apr 2019<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
public class CallbackImpl implements Callback {
	
	private List<ActionImpl> actions;
	private Integer forcesavetype;
	private String key;
	private int status;
	private String url;
	private String[] users;
	
	@Override
	public List<? extends Action> getActions() {
		return actions;
	}

	public void setActions(List<ActionImpl> actions) {
		this.actions = actions;
	}

	@Override
	public Integer getForcesavetype() {
		return forcesavetype;
	}

	public void setForcesavetype(Integer forcesavetype) {
		this.forcesavetype = forcesavetype;
	}

	@Override
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String[] getUsers() {
		return users;
	}

	public void setUsers(String[] users) {
		this.users = users;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CallbackImpl [actions=");
		builder.append(actions);
		builder.append(", forcesavetype=");
		builder.append(forcesavetype);
		builder.append(", key=");
		builder.append(key);
		builder.append(", status=");
		builder.append(status);
		builder.append(", url=");
		builder.append(url);
		builder.append(", users=");
		builder.append(Arrays.toString(users));
		builder.append("]");
		return builder.toString();
	}
	
}
