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
package org.olat.search.service.document.file;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;
import org.olat.core.util.vfs.VFSLeaf;
import org.olat.test.VFSJavaIOFile;

/**
 * 
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 */
public class HtmlDocumentTest {
	
	@Test
	public void testHtmlDocument()
	throws DocumentException, DocumentAccessException, URISyntaxException, IOException {
		URL url = HtmlDocumentTest.class.getResource("test.html");
		Assert.assertNotNull(url);

		VFSLeaf doc = new VFSJavaIOFile("test.html", new File(url.toURI()));
		HtmlDocument document = new HtmlDocument();
		FileContent content =	document.readContent(doc);
		Assert.assertNotNull(content);
		Assert.assertEquals("Hello", content.getTitle().trim());
		String body = content.getContent();
		Assert.assertTrue(body.trim().contains("Hello, this is HTML"));
	}

}
