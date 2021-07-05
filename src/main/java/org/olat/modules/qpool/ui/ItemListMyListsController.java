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
package org.olat.modules.qpool.ui;

import static org.olat.core.gui.components.util.KeyValues.entry;

import java.util.List;
import java.util.Set;

import org.olat.core.gui.UserRequest;
import org.olat.core.gui.components.form.flexible.FormItem;
import org.olat.core.gui.components.form.flexible.FormItemContainer;
import org.olat.core.gui.components.form.flexible.elements.FormLink;
import org.olat.core.gui.components.form.flexible.elements.SingleSelection;
import org.olat.core.gui.components.form.flexible.impl.FormEvent;
import org.olat.core.gui.components.form.flexible.impl.elements.table.DefaultFlexiColumnModel;
import org.olat.core.gui.components.form.flexible.impl.elements.table.FlexiTableColumnModel;
import org.olat.core.gui.components.link.Link;
import org.olat.core.gui.components.util.KeyValues;
import org.olat.core.gui.control.WindowControl;
import org.olat.core.util.StringHelper;
import org.olat.modules.qpool.QPoolSecurityCallback;
import org.olat.modules.qpool.QuestionItemCollection;
import org.olat.modules.qpool.QuestionItemView;
import org.olat.modules.qpool.model.QItemType;
import org.olat.modules.qpool.ui.datasource.CollectionOfItemsSource;
import org.olat.modules.qpool.ui.datasource.EmptyItemsSource;
import org.olat.modules.qpool.ui.events.QItemViewEvent;

/**
 * 
 * Initial date: 6.03.2014<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class ItemListMyListsController extends AbstractItemListController {

	private FormLink selectLink;
    private SingleSelection myListEl;
    
	private List<QuestionItemCollection> myCollections;

	public ItemListMyListsController(UserRequest ureq, WindowControl wControl, QPoolSecurityCallback secCallback,
			String restrictToFormat, List<QItemType> excludeTypes) {
		super(ureq, wControl, secCallback, new EmptyItemsSource(), restrictToFormat, excludeTypes, "qti-select");
	}
	
	@Override
	protected void initButtons(UserRequest ureq, FormItemContainer formLayout) {
		getItemsTable().setMultiSelect(true);
		selectLink = uifactory.addFormLink("select-to-import", "select", null, formLayout, Link.BUTTON);

        myCollections = qpoolService.getCollections(getIdentity());
		int numOfCollections = myCollections.size();

		KeyValues listKV = new KeyValues();
		if (numOfCollections == 0) {
			listKV.add(entry("", ""));
		} else {
			for (QuestionItemCollection collection : myCollections) {
				listKV.add(entry(collection.getKey().toString(), collection.getName()));
			}
			listKV.sort(KeyValues.VALUE_ASC);
		}

        myListEl = uifactory.addDropdownSingleselect("source.selector", "my.list", formLayout, listKV.keys(), listKV.values(), null);
        myListEl.setDomReplacementWrapperRequired(false);
        myListEl.getLabelC().setDomReplaceable(false);
        myListEl.addActionListener(FormEvent.ONCHANGE);
        if(numOfCollections > 0) {
            myListEl.select(myListEl.getKey(0), true);
            doSelectCollection(ureq, Long.parseLong(myListEl.getKey(0)));
        } else {
			myListEl.setEnabled(false);
		}
	}
	
	@Override
	protected void initActionColumns(FlexiTableColumnModel columnsModel) {
		DefaultFlexiColumnModel selectCol = new DefaultFlexiColumnModel("select", translate("select"), "select-item");
		selectCol.setExportable(false);
		columnsModel.addFlexiColumnModel(selectCol);
	}

	@Override
	protected void formInnerEvent(UserRequest ureq, FormItem source, FormEvent event) {
		if(selectLink == source) {
			Set<Integer> selections = getItemsTable().getMultiSelectedIndex();
			if(!selections.isEmpty()) {
				List<QuestionItemView> items = getItemViews(selections);
				fireEvent(ureq, new QItemViewEvent("select-item", items));
			}
		} else if(myListEl == source) {
            String selectedCollKey = myListEl.getSelectedKey();
			if(StringHelper.isLong(selectedCollKey)) {
				Long collectionKey = Long.parseLong(selectedCollKey);
				doSelectCollection(ureq, collectionKey);
			}
        }
		super.formInnerEvent(ureq, source, event);
	}

	private void doSelectCollection(UserRequest ureq, Long collectionKey) {
		QuestionItemCollection myCollection = null;
		for(QuestionItemCollection coll: myCollections) {
			if(collectionKey.equals(coll.getKey())) {
				myCollection = coll;
			}
		}

		if(myCollection == null) {
			updateSource(new EmptyItemsSource());
		} else {
			CollectionOfItemsSource source = new CollectionOfItemsSource(myCollection, getIdentity(), ureq.getUserSession().getRoles(), getLocale());
			source.setRestrictToFormat(restrictToFormat);
			source.setExcludedItemTypes(excludeTypes);
			updateSource(source);
		}
	}
	
	@Override
	protected void doSelect(UserRequest ureq, ItemRow row) {
		if(row == null) {
			showWarning("error.select.one");
		} else {
			fireEvent(ureq, new QItemViewEvent("select-item", row));
		}
	}
}
