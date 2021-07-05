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
package org.olat.repository.ui.author;

import org.olat.core.gui.UserRequest;
import org.olat.core.gui.components.Component;
import org.olat.core.gui.components.velocity.VelocityContainer;
import org.olat.core.gui.control.Controller;
import org.olat.core.gui.control.Event;
import org.olat.core.gui.control.WindowControl;
import org.olat.core.gui.control.controller.BasicController;
import org.olat.core.util.coordinate.CoordinatorManager;
import org.olat.core.util.event.MultiUserEvent;
import org.olat.ims.lti13.LTI13Module;
import org.olat.ims.lti13.ui.LTI13ResourceAccessController;
import org.olat.repository.RepositoryEntry;
import org.olat.repository.RepositoryManager;
import org.olat.repository.RepositoryService;
import org.olat.repository.controllers.EntryChangedEvent;
import org.olat.repository.controllers.EntryChangedEvent.Change;
import org.olat.repository.ui.settings.ReloadSettingsEvent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Configuration of the BARG and access control of a learn resource.
 * 
 * 
 * Initial date: 06.05.2014<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class AuthoringEditAccessController extends BasicController {
	
	private VelocityContainer mainVC;
	
	private LTI13ResourceAccessController lti13AccessCtrl;
	private AuthoringEditAuthorAccessController authorAccessCtrl;
	private AuthoringEditAccessAndBookingController accessAndBookingCtrl;
	
	private RepositoryEntry entry;
	private final boolean readOnly;
	
	@Autowired
	private LTI13Module lti13Module;
	@Autowired
	private RepositoryManager repositoryManager;
	
	public AuthoringEditAccessController(UserRequest ureq, WindowControl wControl, RepositoryEntry entry, boolean readOnly) {
		super(ureq, wControl);
		this.entry = entry;
		this.readOnly = readOnly;
		
		mainVC = createVelocityContainer("editproptabpub");
		initAccessAndBooking(ureq);
		initAuthorAccess(ureq);
		if(lti13Module.isEnabled()) {
			initLTI13Access(ureq);
		}
		putInitialPanel(mainVC);
	}
	
	public RepositoryEntry getEntry() {
		return entry;
	}
	
	@Override
	protected void doDispose() {
		//
	}

	@Override
	protected void event(UserRequest ureq, Component source, Event event) {
		//
	}
	
	@Override
	protected void event(UserRequest ureq, Controller source, Event event) {
		if(accessAndBookingCtrl == source) {
			if(event == Event.DONE_EVENT) {
				doSaveAccessAndBooking(ureq);
				fireEvent(ureq, new ReloadSettingsEvent(true, true, false, false));
			} else if(event == Event.CANCELLED_EVENT) {
				initAccessAndBooking(ureq);
			}
		} else if(authorAccessCtrl == source) {
			if(event == Event.DONE_EVENT) {
				doSaveAuthorAccess(ureq);
				fireEvent(ureq, new ReloadSettingsEvent());
			} else if(event == Event.CANCELLED_EVENT) {
				initAuthorAccess(ureq);
			}
		}
		
		super.event(ureq, source, event);
	}
	
	private void doSaveAccessAndBooking(UserRequest ureq) {
		accessAndBookingCtrl.commitChanges();
		
		// inform anybody interested about this change
		MultiUserEvent modifiedEvent = new EntryChangedEvent(entry, getIdentity(), Change.modifiedAccess, "authoring");
		CoordinatorManager.getInstance().getCoordinator().getEventBus().fireEventToListenersOf(modifiedEvent, entry);
		CoordinatorManager.getInstance().getCoordinator().getEventBus().fireEventToListenersOf(modifiedEvent, RepositoryService.REPOSITORY_EVENT_ORES);
		fireEvent(ureq, Event.CHANGED_EVENT);
	}
	
	private void initAccessAndBooking(UserRequest ureq) {
		removeAsListenerAndDispose(accessAndBookingCtrl);
		
		accessAndBookingCtrl = new AuthoringEditAccessAndBookingController(ureq, getWindowControl(), entry, readOnly);
		listenTo(accessAndBookingCtrl);
		mainVC.put("accessAndBooking", accessAndBookingCtrl.getInitialComponent());
	}
	
	private void doSaveAuthorAccess(UserRequest ureq) {
		boolean canCopy = authorAccessCtrl.canCopy();
		boolean canReference = authorAccessCtrl.canReference();
		boolean canDownload = authorAccessCtrl.canDownload();
		entry = authorAccessCtrl.getEntry();
		entry = repositoryManager.setAccess(entry, canCopy, canReference, canDownload);
		
		// inform anybody interested about this change
		MultiUserEvent modifiedEvent = new EntryChangedEvent(entry, getIdentity(), Change.modifiedAccess, "authoring");
		CoordinatorManager.getInstance().getCoordinator().getEventBus().fireEventToListenersOf(modifiedEvent, entry);
		CoordinatorManager.getInstance().getCoordinator().getEventBus().fireEventToListenersOf(modifiedEvent, RepositoryService.REPOSITORY_EVENT_ORES);
		fireEvent(ureq, Event.CHANGED_EVENT);
	}
	
	private void initAuthorAccess(UserRequest ureq) {
		removeAsListenerAndDispose(authorAccessCtrl);
		
		authorAccessCtrl = new AuthoringEditAuthorAccessController(ureq, getWindowControl(), entry, readOnly);
		listenTo(authorAccessCtrl);
		mainVC.put("authorAccess", authorAccessCtrl.getInitialComponent());
	}
	
	private void initLTI13Access(UserRequest ureq) {
		removeAsListenerAndDispose(lti13AccessCtrl);
		
		lti13AccessCtrl = new LTI13ResourceAccessController(ureq, getWindowControl(), entry, readOnly);
		listenTo(lti13AccessCtrl);
		mainVC.put("lti13Access", lti13AccessCtrl.getInitialComponent());
	}
}
