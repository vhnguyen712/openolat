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
package org.olat.modules.forms.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * Initial date: 6 Apr 2021<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
public class EvaluationFormRuleHandlerProvider implements RuleHandlerProvider {
	
	private final List<RuleHandler> ruleHandlers;
	private final List<ConditionHandler> conditionHandlers;
	private final List<ActionHandler> actionHandlers;
	
	public EvaluationFormRuleHandlerProvider() {
		conditionHandlers = new ArrayList<>(1);
		conditionHandlers.add(new ChoiceSelectedHandler());
		
		actionHandlers = new ArrayList<>(1);
		actionHandlers.add(new VisibilityHandler());
		
		ruleHandlers = new ArrayList<>(conditionHandlers.size() + actionHandlers.size());
		ruleHandlers.addAll(conditionHandlers);
		ruleHandlers.addAll(actionHandlers);
	}

	@Override
	public List<RuleHandler> getRuleHandlers() {
		return ruleHandlers;
	}

	@Override
	public Collection<ConditionHandler> getConditionHandlers() {
		return conditionHandlers;
	}

	@Override
	public Collection<ActionHandler> getActionHandlers() {
		return actionHandlers;
	}

}
