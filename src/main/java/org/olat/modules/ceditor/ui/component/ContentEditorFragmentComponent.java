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
package org.olat.modules.ceditor.ui.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.olat.core.gui.UserRequest;
import org.olat.core.gui.components.Component;
import org.olat.core.gui.components.ComponentEventListener;
import org.olat.core.gui.components.ComponentRenderer;
import org.olat.core.gui.components.form.flexible.impl.FormBaseComponentImpl;
import org.olat.core.gui.components.link.Link;
import org.olat.core.gui.components.velocity.VelocityContainer;
import org.olat.core.gui.control.Controller;
import org.olat.core.gui.control.ControllerEventListener;
import org.olat.core.gui.control.Event;
import org.olat.core.logging.Tracing;
import org.olat.modules.ceditor.PageElement;
import org.olat.modules.ceditor.PageElementEditorController;
import org.olat.modules.ceditor.ui.PageElementTarget;
import org.olat.modules.ceditor.ui.event.CloneElementEvent;
import org.olat.modules.ceditor.ui.event.DeleteElementEvent;
import org.olat.modules.ceditor.ui.event.DropToPageElementEvent;
import org.olat.modules.ceditor.ui.event.EditElementEvent;
import org.olat.modules.ceditor.ui.event.EditPageElementEvent;
import org.olat.modules.ceditor.ui.event.MoveDownElementEvent;
import org.olat.modules.ceditor.ui.event.MoveUpElementEvent;
import org.olat.modules.ceditor.ui.event.OpenAddElementEvent;
import org.olat.modules.ceditor.ui.event.PositionEnum;
import org.olat.modules.ceditor.ui.event.SaveElementEvent;

/**
 * 
 * Initial date: 6 déc. 2019<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class ContentEditorFragmentComponent extends FormBaseComponentImpl implements ContentEditorFragment, ComponentEventListener, ControllerEventListener {
	
	private static final Logger log = Tracing.createLoggerFor(ContentEditorFragmentComponent.class);
	private static final ContentEditorFragmentComponentRenderer RENDERER = new ContentEditorFragmentComponentRenderer();
	
	private boolean editMode = false;
	private boolean moveable = false;
	private boolean cloneable = false;
	private boolean deleteable = false;
	private Controller editorPart;
	private final PageElement pageElement;
	
	public ContentEditorFragmentComponent(String name, PageElement pageElement, Controller editorPart) {
		super(name);
		this.editorPart = editorPart;
		this.pageElement = pageElement;
		setDomReplacementWrapperRequired(false);
	}

	@Override
	public boolean isEditMode() {
		return editMode;
	}
	
	@Override
	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
		doEditFragment(null, editMode);
	}

	@Override
	public boolean isCloneable() {
		return cloneable;
	}

	@Override
	public void setCloneable(boolean cloneable) {
		this.cloneable = cloneable;
	}

	@Override
	public boolean isDeleteable() {
		return deleteable;
	}

	@Override
	public void setDeleteable(boolean enable) {
		deleteable = enable;
	}

	@Override
	public boolean isMoveable() {
		return moveable;
	}

	@Override
	public void setMoveable(boolean enable) {
		this.moveable = enable;
	}

	@Override
	public String getElementId() {
		return pageElement.getId();
	}
	
	@Override
	public PageElement getElement() {
		return pageElement;
	}
	
	public boolean isDropppable() {
		return false;
	}
	
	public Component getPageElementComponent() {
		return editorPart.getInitialComponent();
	}
	
	public List<Link> getAdditionalTools() {
		if(editorPart instanceof PageElementEditorController) {
			return ((PageElementEditorController)editorPart).getOptionLinks();
		}
		return Collections.emptyList();
	}
	
	@Override
	protected void doDispatchRequest(UserRequest ureq) {
		String cmd = ureq.getParameter(VelocityContainer.COMMAND_ID);
		String fragment = ureq.getParameter("fragment");
		if(cmd != null && fragment != null && getComponentName().equals(fragment)) {
			switch(cmd) {
				case "edit_fragment":
					doEditFragment(ureq, true);
					fireEvent(ureq, new EditElementEvent(pageElement.getId()));
					break;
				case "add_element_above":
					String aboveLinkId = "o_ccaab_".concat(getDispatchID());
					fireEvent(ureq, new OpenAddElementEvent(aboveLinkId, this, PageElementTarget.above));
					break;
				case "add_element_below":
					String belowLinkId = "o_ccabe_".concat(getDispatchID());
					fireEvent(ureq, new OpenAddElementEvent(belowLinkId, this, PageElementTarget.below));
					break;
				case "save_element":
					doCloseEditFragment();
					fireEvent(ureq, new SaveElementEvent(this));
					break;
				case "clone_element":
					fireEvent(ureq, new CloneElementEvent(this));
					break;
				case "delete_element":
					fireEvent(ureq, new DeleteElementEvent(this));
					break;
				case "move_up":
					fireEvent(ureq, new MoveUpElementEvent(this));
					break;
				case "move_down":
					fireEvent(ureq, new MoveDownElementEvent(this));
					break;
				case "drop_fragment":
					doDropFragment(ureq);
					break;
				default:
					log.info("Dispatch fragment: {} {}", fragment, cmd);
					break;
			}
		}
	}

	@Override
	public void dispatchEvent(UserRequest ureq, Controller source, Event event) {
		//
	}

	@Override
	public void dispatchEvent(UserRequest ureq, Component source, Event event) {
		//
	}
	
	private void doDropFragment(UserRequest ureq) {
		String sourceId = ureq.getParameter("source");
		String position = ureq.getParameter("position");
		fireEvent(ureq, new DropToPageElementEvent(sourceId, this,
				PositionEnum.valueOf(position, PositionEnum.bottom)));
	}
	
	private void doEditFragment(UserRequest ureq, boolean editMode) {
		this.editMode = editMode;
		if(editorPart instanceof PageElementEditorController) {
			PageElementEditorController editorCtrl = (PageElementEditorController)editorPart;
			if(editorCtrl.isEditMode() != editMode) {
				((PageElementEditorController)editorPart).setEditMode(editMode);
				setDirty(true);
				if(editMode) {
					fireEvent(ureq, new EditPageElementEvent(this));
				}
			}
		} else {
			setDirty(true);
			if(editMode) {
				fireEvent(ureq, new EditPageElementEvent(this));
			}
		}
	}
	
	private void doCloseEditFragment() {
		this.editMode = false;
		if(editorPart instanceof PageElementEditorController) {
			((PageElementEditorController)editorPart).setEditMode(false);
		}
		setDirty(true);
		
	}

	@Override
	public Component getComponent(String name) {
		for(Component cmp:getComponents()) {
			if(name.equals(cmp.getComponentName())) {
				return cmp;
			}
		}
		return null;
	}

	@Override
	public Iterable<Component> getComponents() {
		List<Component> components = new ArrayList<>();
		components.add(editorPart.getInitialComponent());
		if(editorPart instanceof PageElementEditorController) {
			components.addAll(((PageElementEditorController)editorPart).getOptionLinks());
		}
		return components;
	}

	@Override
	public ComponentRenderer getHTMLRendererSingleton() {
		return RENDERER;
	}
}
