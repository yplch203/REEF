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
package com.microsoft.reef.activity;


import com.microsoft.reef.annotations.Provided;
import com.microsoft.reef.annotations.audience.EvaluatorSide;
import com.microsoft.reef.annotations.audience.Public;
import com.microsoft.reef.io.Message;

/**
 * A message sent from an Activity to a Driver.
 */
@EvaluatorSide
@Public
@Provided
public final class ActivityMessage implements Message {

  private final String messageSourceID;
  private final byte[] theBytes;

  private ActivityMessage(final String messageSourceID, final byte[] theBytes) {
    this.messageSourceID = messageSourceID;
    this.theBytes = theBytes;
  }

  /**
   * @return the message source identifier.
   */
  public String getMessageSourceID() {
    return this.messageSourceID;
  }

  /**
   * @return the message
   */
  @Override
  public byte[] get() {
    return this.theBytes;
  }

  /**
   * @param messageSourceID The message's sourceID. This will be accessible in the Driver for routing.
   * @param theBytes        The actual content of the message, serialized into a byte[]
   * @return a new ActivityMessage with the given content.
   */
  public static ActivityMessage from(final String messageSourceID, final byte[] theBytes) {
    assert (theBytes != null && messageSourceID != null);
    return new ActivityMessage(messageSourceID, theBytes);
  }


}
