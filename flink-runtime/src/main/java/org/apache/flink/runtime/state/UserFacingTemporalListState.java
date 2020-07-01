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

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.TemporalListState;
import org.apache.flink.runtime.state.internal.InternalListState;

import java.util.Collections;
import java.util.List;

class UserFacingTemporalListState<T> implements TemporalListState<T> {

	protected final ListState<T> originalState;
	protected final InternalListState internalState;

	private final Iterable<T> emptyState = Collections.emptyList();

	UserFacingTemporalListState(ListState<T> originalState) {

		this.originalState = originalState;
		this.internalState = (InternalListState) originalState;
	}

	@Override
	public Iterable<T> get(long time) throws Exception {
		internalState.setCurrentNamespace(time);
		Iterable<T> original = originalState.get();
		return original != null ? original : emptyState;
	}

	@Override
	public void add(long time, T value) throws Exception {
		internalState.setCurrentNamespace(time);
		originalState.add(value);
	}

	@Override
	public void update(long time, List<T> values) throws Exception {
		internalState.setCurrentNamespace(time);
		originalState.update(values);
	}

	@Override
	public void addAll(long time, List<T> values) throws Exception {
		internalState.setCurrentNamespace(time);
		originalState.addAll(values);
	}

	@Override
	public void clear(long time) {
		internalState.setCurrentNamespace(time);
		originalState.clear();
	}
}