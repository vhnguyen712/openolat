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
package org.olat.selenium.page.course;

import java.io.File;

import org.olat.selenium.page.core.TinyPage;
import org.olat.selenium.page.graphene.OOGraphene;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 
 * Initial date: 11.11.2019<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class SinglePageConfigurationPage {
	
	private final WebDriver browser;
	
	public SinglePageConfigurationPage(WebDriver browser) {
		this.browser = browser;
	}
	
	public SinglePageConfigurationPage selectConfiguration() {
		By dialogConfigBy = By.cssSelector("fieldset.o_sel_single_page_file_settings");
		OOGraphene.selectTab("o_node_config", dialogConfigBy, browser);
		return this;
	}
	
	public SinglePageConfigurationPage newDefaultPage(String content) {
		By editBy = By.cssSelector("fieldset.o_sel_single_page_file_settings a.o_sel_filechooser_edit");
		OOGraphene.waitElement(editBy, browser);
		browser.findElement(editBy).click();
		OOGraphene.waitBusy(browser);
		OOGraphene.waitModalDialog(browser);
		
		OOGraphene.tinymce(content, browser);
		
		By saveAndCloseBy = By.cssSelector("div.o_htmleditor #o_button_saveclose a");
		OOGraphene.waitElement(saveAndCloseBy, browser);
		browser.findElement(saveAndCloseBy).click();
		
		OOGraphene.waitBusy(browser);
		OOGraphene.waitModalDialogDisappears(browser);
		
		return this;
	}
	
	public TinyPage openDefaultPage() {
		By editBy = By.cssSelector("fieldset.o_sel_single_page_file_settings a.o_sel_filechooser_edit");
		OOGraphene.waitElement(editBy, browser);
		browser.findElement(editBy).click();
		OOGraphene.waitBusy(browser);
		OOGraphene.waitModalDialog(browser);
		return new TinyPage(browser);
	}
	
	
	public SinglePageConfigurationPage uploadFile(File file) {
		By calloutBy = By.cssSelector("fieldset.o_sel_single_page_file_settings a.o_sel_filechooser_new");
		OOGraphene.waitElement(calloutBy, browser);
		browser.findElement(calloutBy).click();
		OOGraphene.waitCallout(browser);
		
		By uploadLinkBy = By.cssSelector("ul.o_dropdown .o_sel_filechooser_upload");
		OOGraphene.waitElement(uploadLinkBy, browser);
		browser.findElement(uploadLinkBy).click();
		
		OOGraphene.waitModalDialog(browser);
		
		By inputBy = By.cssSelector(".modal-body .o_fileinput input[type='file']");
		OOGraphene.uploadFile(inputBy, file, browser);
		OOGraphene.waitBusy(browser);
		By uploadedBy = By.cssSelector(".modal-body .o_sel_file_uploaded");
		OOGraphene.waitElement(uploadedBy, browser);
		
		By uploadBy = By.cssSelector(".modal-body .o_sel_upload_buttons button.btn-primary");
		OOGraphene.waitElement(uploadBy, browser);
		browser.findElement(uploadBy).click();
		OOGraphene.waitBusy(browser);
		OOGraphene.waitModalDialogDisappears(browser);
		
		return this;
	}
	
	public SinglePageConfigurationPage assertOnPreview() {
		By previewLink = By.cssSelector("fieldset.o_sel_single_page_file_settings a.o_preview.o_sel_filechooser_preview");
		OOGraphene.waitElement(previewLink, browser);
		return this;
	}

}
