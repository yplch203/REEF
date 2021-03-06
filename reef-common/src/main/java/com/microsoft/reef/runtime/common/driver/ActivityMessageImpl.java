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
package com.microsoft.reef.runtime.common.driver;

import com.microsoft.reef.driver.activity.ActivityMessage;

final class ActivityMessageImpl implements ActivityMessage {
  private final byte[] theMessage;
  private final String theActivityID;
  private final String theContextID;
  private final String theMessageSourceId;

  ActivityMessageImpl(final byte[] theMessage, final String theActivityID, final String theContextID, final String theMessageSourceId) {
    this.theMessage = theMessage;
    this.theActivityID = theActivityID;
    this.theContextID = theContextID;
    this.theMessageSourceId = theMessageSourceId;
  }

  @Override
  public byte[] get() {
    return this.theMessage;
  }

  @Override
  public String getId() {
    return this.theActivityID;
  }

  @Override
  public String getContextId() {
    return this.theContextID;
  }

  @Override
  public final String getMessageSourceID() {
    return this.theMessageSourceId;
  }
}
