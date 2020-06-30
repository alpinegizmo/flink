/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.runtime.state;

import org.apache.flink.api.common.state.TemporalValueState;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.runtime.state.internal.InternalValueState;

import java.io.IOException;

/**
 * Simple wrapper around value state.
 *
 * @param <T> The type of elements in the state.
 */
public class UserFacingTemporalValueState<T> implements ValueState<T>, TemporalValueState<T> {

	protected final ValueState<T> originalState;

	UserFacingTemporalValueState(ValueState<T> originalState) {
		this.originalState = originalState;
	}

	@Override
	public void setTime(long time) {
		InternalValueState internalState = (InternalValueState) originalState;
		internalState.setCurrentNamespace(time);
	}

	@Override
	public T value() throws IOException {
		return originalState.value();
	}

	@Override
	public void update(T value) throws IOException {
		originalState.update(value);
	}

	@Override
	public void clear() {
		originalState.clear();
	}
}
