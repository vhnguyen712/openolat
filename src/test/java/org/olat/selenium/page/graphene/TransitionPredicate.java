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
package org.olat.selenium.page.graphene;

import java.util.function.Function;

import org.apache.logging.log4j.Logger;
import org.olat.core.logging.Tracing;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * Check the navigation bar states, but it's not enough. It needs
 * a wait after that (100ms seems to make the thing)
 * 
 * Initial date: 04.07.2014<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class TransitionPredicate implements Function<WebDriver,Boolean> {

	private static final Logger log = Tracing.createLoggerFor(TransitionPredicate.class);
	
	@Override
	public Boolean apply(WebDriver driver) {
        try {
			Object busy = ((JavascriptExecutor)driver)
					.executeScript("return (window === undefined || !('OPOL' in window) || !('navbar' in window.OPOL) || !('state' in window.OPOL.navbar) || window.OPOL.navbar.state.sitesDirty || window.OPOL.navbar.state.tabsDirty || window.OPOL.navbar.state.toolsDirty)");
			return Boolean.FALSE.equals(busy);
		} catch (Exception e) {
			log.error("", e);
			return Boolean.FALSE;
		}
    }
}
