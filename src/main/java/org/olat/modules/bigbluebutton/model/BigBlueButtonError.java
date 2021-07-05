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
package org.olat.modules.bigbluebutton.model;

import java.io.Serializable;

/**
 * 
 * Initial date: 18 mars 2020<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class BigBlueButtonError implements Serializable {

	private static final long serialVersionUID = 6820811247631455090L;
	private BigBlueButtonErrorCodes code;
	private String[] arguments;
	
	private String messageKey;
	private String message;
	
	public BigBlueButtonError(String message, String messageKey) {
		this.message = message;
		this.messageKey = messageKey;
	}
	
	public BigBlueButtonError(BigBlueButtonErrorCodes code) {
		this.code = code;
	}
	
	public String getMessageKey() {
		return messageKey;
	}

	public String getMessage() {
		return message;
	}

	public BigBlueButtonErrorCodes getCode() {
		return code;
	}
	
	public void setCode(BigBlueButtonErrorCodes code) {
		this.code = code;
	}

	public String[] getArguments() {
		return arguments;
	}

	public void setArguments(String[] arguments) {
		this.arguments = arguments;
	}
}
