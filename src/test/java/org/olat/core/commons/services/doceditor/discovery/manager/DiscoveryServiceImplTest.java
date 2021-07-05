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
package org.olat.core.commons.services.doceditor.discovery.manager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.olat.core.commons.services.doceditor.discovery.Action;
import org.olat.core.commons.services.doceditor.discovery.App;
import org.olat.core.commons.services.doceditor.discovery.NetZone;
import org.olat.core.commons.services.doceditor.discovery.model.ActionImpl;
import org.olat.core.commons.services.doceditor.discovery.model.AppImpl;
import org.olat.core.commons.services.doceditor.discovery.model.DiscoveryImpl;
import org.olat.core.commons.services.doceditor.discovery.model.NetZoneImpl;


/**
 * 
 * Initial date: 11 Mar 2019<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
public class DiscoveryServiceImplTest {
	
	private DiscoveryServiceImpl sut = new DiscoveryServiceImpl();

	@Test
	public void shouldGetAction() {
		ActionImpl editOdt = new ActionImpl();
		editOdt.setName("edit");
		editOdt.setExt("odt");
		ActionImpl viewOdt = new ActionImpl();
		viewOdt.setName("view");
		viewOdt.setExt("odt");
		ActionImpl editTxt = new ActionImpl();
		editTxt.setName("edit");
		editTxt.setExt("txt");
		ActionImpl viewTxt = new ActionImpl();
		viewTxt.setName("view");
		viewTxt.setExt("txt");
		
		List<Action> odtActions = Arrays.asList(editOdt, viewOdt);
		AppImpl odtApp = new AppImpl();
		odtApp.setActions(odtActions);
		List<Action> txtActions = Arrays.asList(editTxt, viewTxt);
		AppImpl txtApp = new AppImpl();
		txtApp.setActions(txtActions);
		
		List<App> apps = Arrays.asList(odtApp, txtApp);
		NetZoneImpl netZone = new NetZoneImpl();
		netZone.setApps(apps);

		List<NetZone> netZones = Arrays.asList(netZone);
		DiscoveryImpl discovery = new DiscoveryImpl();
		discovery.setNetZones(netZones);
		
		Action action = sut.getAction(discovery, "edit", "txt");
		
		assertThat(action).isEqualTo(editTxt);
	}
	
	@Test
	public void shouldGetActions() {
		ActionImpl editOdt = new ActionImpl();
		editOdt.setName("edit");
		editOdt.setExt("odt");
		ActionImpl viewOdt = new ActionImpl();
		viewOdt.setName("view");
		viewOdt.setExt("odt");
		ActionImpl editTxt = new ActionImpl();
		editTxt.setName("edit");
		editTxt.setExt("txt");
		ActionImpl viewTxt = new ActionImpl();
		viewTxt.setName("view");
		viewTxt.setExt("txt");
		
		List<Action> odtActions = Arrays.asList(editOdt, viewOdt);
		AppImpl odtApp = new AppImpl();
		odtApp.setActions(odtActions);
		List<Action> txtActions = Arrays.asList(editTxt, viewTxt);
		AppImpl txtApp = new AppImpl();
		txtApp.setActions(txtActions);
		
		List<App> apps = Arrays.asList(odtApp, txtApp);
		NetZoneImpl netZone = new NetZoneImpl();
		netZone.setApps(apps);

		List<NetZone> netZones = Arrays.asList(netZone);
		DiscoveryImpl discovery = new DiscoveryImpl();
		discovery.setNetZones(netZones);
		
		Collection<Action> actions = sut.getActions(discovery);
		
		assertThat(actions).containsExactlyInAnyOrder(
				editOdt,
				viewOdt,
				editTxt,
				viewTxt
				);
	}


}
