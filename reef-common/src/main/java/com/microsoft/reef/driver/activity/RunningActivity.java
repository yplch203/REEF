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
package com.microsoft.reef.driver.activity;

import com.microsoft.reef.annotations.Provided;
import com.microsoft.reef.annotations.audience.DriverSide;
import com.microsoft.reef.annotations.audience.Public;
import com.microsoft.reef.driver.context.ActiveContext;
import com.microsoft.reef.io.naming.Identifiable;

/**
 * Represents a running Activity
 */
@DriverSide
@Public
@Provided
public interface RunningActivity extends Identifiable, AutoCloseable {


  /**
   * @return the context the activity is running on.
   */
  public ActiveContext getActiveContext();

  /**
   * Sends the message to the running activity.
   *
   * @param message to be sent to the running activity
   */
  public void onNext(final byte[] message);

  /**
   * Signal the activity to suspend.
   *
   * @param message a message that is sent to the Activity.
   */
  public void suspend(final byte[] message);

  /**
   * Signal the activity to suspend.
   */
  public void suspend();

  /**
   * Signal the activity to shut down.
   *
   * @param message a message that is sent to the Activity.
   */
  public void close(final byte[] message);

  /**
   * Signal the activity to shut down.
   */
  @Override
  public void close();
}
