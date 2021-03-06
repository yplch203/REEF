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
package com.microsoft.reef.runtime.common.utils;

import com.microsoft.wake.EventHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BroadCastEventHandler<E> implements EventHandler<E> {
  private final List<EventHandler<E>> handlers;

  public BroadCastEventHandler(Collection<EventHandler<E>> handlers) {
    this.handlers = new ArrayList<>(handlers);
  }

  @Override
  public void onNext(final E event) {
    for (EventHandler<E> handler : handlers)
      handler.onNext(event);
  }

  public void addEventHandler(final EventHandler<E> eventHandler) {
    this.handlers.add(eventHandler);
  }

}
