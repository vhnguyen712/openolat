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
package org.olat.ims.lti13.manager;

import java.security.Key;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.olat.core.CoreSpringFactory;
import org.olat.core.logging.Tracing;
import org.olat.ims.lti13.LTI13Key;
import org.olat.ims.lti13.LTI13Service;
import org.olat.ims.lti13.LTI13Tool;
import org.springframework.beans.factory.annotation.Autowired;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolver;

/**
 * 
 * Initial date: 9 mars 2021<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class LTI13PlatformSigningPrivateKeyResolver implements SigningKeyResolver {
	
	private static final Logger log = Tracing.createLoggerFor(LTI13PlatformSigningPrivateKeyResolver.class);
	
	private LTI13Tool tool;
	
	@Autowired
	private LTI13Service lti13Service;

	public LTI13PlatformSigningPrivateKeyResolver() {
		CoreSpringFactory.autowireObject(this);
	}
	
	public LTI13Tool getTool() {
		return tool;
	}

	@Override
	public Key resolveSigningKey(JwsHeader header, Claims claims) {
		try {
			String kid = header.getKeyId();
			String alg = header.getAlgorithm();
			List<LTI13Key> keys = lti13Service.getPlatformKeys();
			for(LTI13Key key:keys) {
				if(key.getKeyId().equals(kid) && key.getAlgorithm().equals(alg)) {
					return key.getPrivateKey();
				}
			}
			return null;
		} catch (Exception e) {
			log.error("", e);
			return null;
		}
	}

	@Override
	public Key resolveSigningKey(JwsHeader header, String plaintext) {
		log.debug("resolveSigningKey plain: {} claims: {}", header, plaintext);
		return null;
	}
}
