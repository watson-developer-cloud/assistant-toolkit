/*
 Copyright 2024 IBM Corporation

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.ibm.watson.conversationalskills.sdk;

import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class State {
	public State(Locale locale, Map<String, Object> localVariables, Map<String, Object> sessionVariables) {
		this.locale = locale;
		this.localVariables = localVariables;
		this.sessionVariables = sessionVariables;
	}

	public Locale getLocale() {
		return this.locale;
	}

	public <T> T getLocalVariable(String key, Class<T> clazz) {
		var value = this.localVariables.get(key);

		if (value == null) {
			throw new AssertionError("Local variables do not contain value for key '" + key + "'");
		}

		return new ObjectMapper().convertValue(value, clazz);
	}

	public Map<String, Object> getLocalVariables() {
		return this.localVariables;
	}

	public Map<String, Object> getSessionVariables() {
		return this.sessionVariables;
	}

	private Locale locale;
	private Map<String, Object> localVariables;
	private Map<String, Object> sessionVariables;
}
