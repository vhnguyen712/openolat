/**
* OLAT - Online Learning and Training<br>
* http://www.olat.org
* <p>
* Licensed under the Apache License, Version 2.0 (the "License"); <br>
* you may not use this file except in compliance with the License.<br>
* You may obtain a copy of the License at
* <p>
* http://www.apache.org/licenses/LICENSE-2.0
* <p>
* Unless required by applicable law or agreed to in writing,<br>
* software distributed under the License is distributed on an "AS IS" BASIS, <br>
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. <br>
* See the License for the specific language governing permissions and <br>
* limitations under the License.
* <p>
* Copyright (c) since 2004 at Multimedia- & E-Learning Services (MELS),<br>
* University of Zurich, Switzerland.
* <hr>
* <a href="http://www.openolat.org">
* OpenOLAT - Online Learning and Training</a><br>
* This file has been modified by the OpenOLAT community. Changes are licensed
* under the Apache 2.0 license as the original file.  
* <p>
*/
package org.olat.instantMessaging.ui;

import org.olat.core.commons.persistence.DB;
import org.olat.core.gui.UserRequest;
import org.olat.core.gui.components.form.flexible.FormItem;
import org.olat.core.gui.components.form.flexible.FormItemContainer;
import org.olat.core.gui.components.form.flexible.elements.FormLink;
import org.olat.core.gui.components.form.flexible.elements.TextElement;
import org.olat.core.gui.components.form.flexible.impl.FormBasicController;
import org.olat.core.gui.components.form.flexible.impl.FormEvent;
import org.olat.core.gui.components.link.Link;
import org.olat.core.gui.control.Controller;
import org.olat.core.gui.control.Event;
import org.olat.core.gui.control.WindowControl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * <P>
 * Initial Date:  26.03.2007 <br />
 * @author guido
 */
public class SendMessageForm extends FormBasicController {

	private TextElement msg;
	private FormLink submit;
	private final String panelId;
	
	@Autowired
	private DB dbInstance;

	public SendMessageForm(UserRequest ureq, WindowControl wControl, String panelId) {
		super(ureq, wControl, "sendMessageForm");
		this.panelId = panelId;
		initForm(ureq);
	}

	@Override
	protected void doDispose() {
		//
	}

	@Override
	protected void formOK(UserRequest ureq) {
		fireEvent(ureq, Event.DONE_EVENT);
	}

	@Override
	protected void initForm(FormItemContainer formLayout, Controller listener, UserRequest ureq) {
		msg = uifactory.addTextElement("input_" + panelId, "msg", null, 1024, null, formLayout);
		msg.setFocus(true);//always focus to the message field
		msg.setDisplaySize(40);
		submit = uifactory.addFormLink("subm", "msg.send", "msg.send", formLayout, Link.BUTTON);
	}
	
	@Override
	protected void formInnerEvent (UserRequest ureq, FormItem source, FormEvent event) {
		if (source == submit) {
			flc.getRootForm().submit(ureq);
		}
	}

	public String getMessage() {
		return msg.getValue();
	}
	
	public void setErrorTextField() {
		if(dbInstance.isMySQL()) {
			msg.setErrorKey("error.message.mysql", null);
		} else {
			msg.setErrorKey("error.message", null);
		}
	}
	
	public void resetTextField() {
		msg.clearError();
		msg.setValue("");
	}
}
