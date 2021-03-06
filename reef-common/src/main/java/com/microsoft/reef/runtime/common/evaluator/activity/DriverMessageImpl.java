/**
 * Copyright (C) 2013 Microsoft Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.microsoft.reef.runtime.common.evaluator.activity;

import com.microsoft.reef.activity.events.DriverMessage;
import com.microsoft.reef.util.Optional;

final class DriverMessageImpl implements DriverMessage {
  private final Optional<byte[]> value;

  DriverMessageImpl() {
    this.value = Optional.empty();
  }

  DriverMessageImpl(final byte[] theBytes) {
    this.value = Optional.ofNullable(theBytes);
  }

  @Override
  public Optional<byte[]> get() {
    return this.value;
  }

  @Override
  public String toString() {
    return "DriverMessage{value=" + this.value + '}';
  }
}