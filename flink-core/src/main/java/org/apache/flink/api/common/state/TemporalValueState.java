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

package org.apache.flink.api.common.state;

import org.apache.flink.annotation.PublicEvolving;

import java.io.IOException;

/**
 * An extension of the {@link ListState} interface that exposes the namespace in a limited way.
 *
 * @param <T> Type of values that this temporal list state keeps.
 */
@PublicEvolving
public interface TemporalValueState<T> extends TemporalState {

	/**
	 * Returns the current value for the state associated with the specified time.
	 *
	 * <p>If you didn't specify a default value when creating the {@link ValueStateDescriptor}
	 * this will return {@code null} when to value was previously set using {@link #update(long, Object)}.
	 *
	 * @return The state value corresponding to the current input.
	 *
	 * @throws IOException Thrown if the system cannot access the state.
	 */
	T value(long time) throws IOException;

	/**
	 * Updates the operator state accessible by {@link #value(long)} to the given
	 * value. The next time {@link #value(long)} is called (for the same state
	 * partition) the returned state will represent the updated value. When a
	 * partitioned state is updated with null, the state for the current key
	 * will be removed and the default value is returned on the next access.
	 *
	 * @param value The new value for the state.
	 *
	 * @throws IOException Thrown if the system cannot access the state.
	 */
	void update(long time, T value) throws IOException;
}
