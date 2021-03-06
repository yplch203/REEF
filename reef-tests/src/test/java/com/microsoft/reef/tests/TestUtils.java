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
package com.microsoft.reef.tests;

import com.microsoft.reef.client.LauncherStatus;
import org.junit.Assert;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class TestUtils {

  private static final Logger LOG = Logger.getLogger(TestUtils.class.getName());

  /**
   * Make sure the launcher status is FAILED and it has the specified exception in the stack.
   * @param status launcher status. Must be FAILED for test to pass.
   * @param clazz runtime exception that should be in the stack of exceptions of the launcher status.
   */
  public static void assertLauncherFailure(final LauncherStatus status, final Class<? extends Throwable> clazz) {
    Assert.assertEquals(LauncherStatus.FAILED, status);
    final Throwable ex = status.getError().orElse(null);
    if (!hasCause(ex, clazz)) {
      LOG.log(Level.WARNING, "Unexpected Error: " + status, status.getError().get());
      Assert.fail("Unexpected error: " + status.getError().orElse(null));
    }
  }

  /**
   * Return True if cause chain of exception ex contains
   * exception of class clazz (or one inherited from it).
   * @param ex exception to analyze (can be null)
   * @param clazz class inherited from type Throwable.
   * @return True if ex or any other exception in its cause chain is instance of class clazz.
   */
  public static boolean hasCause(Throwable ex, final Class<? extends Throwable> clazz) {
    for (; ex != null; ex = ex.getCause()) {
      if (clazz.isInstance(ex)) {
        return true;
      }
    }
    return false;
  }
}
