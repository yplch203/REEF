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
package com.microsoft.reef.util;

import com.microsoft.reef.annotations.audience.Private;
import com.microsoft.wake.EventHandler;

/**
 * An ExceptionHandler that wraps another one, but catches all exceptions thrown by that one and forwards them
 * to an ExceptionHandler.
 *
 * @param <T> the event type handled
 */
@Private
public final class ExceptionHandlingEventHandler<T> implements EventHandler<T> {

  private final EventHandler<T> wrapped;
  private final EventHandler<Throwable> exceptionHandler;

  public ExceptionHandlingEventHandler(EventHandler<T> wrapped, EventHandler<Throwable> exceptionHandler) {
    this.wrapped = wrapped;
    this.exceptionHandler = exceptionHandler;
  }

  @Override
  public void onNext(T t) {
    try {
      wrapped.onNext(t);
    } catch (final Throwable throwable) {
      this.exceptionHandler.onNext(throwable);
    }
  }
}
