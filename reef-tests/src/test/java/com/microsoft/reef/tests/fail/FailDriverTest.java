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
package com.microsoft.reef.tests.fail;

import com.microsoft.reef.client.LauncherStatus;
import com.microsoft.reef.driver.activity.ActivityMessage;
import com.microsoft.reef.driver.activity.CompletedActivity;
import com.microsoft.reef.driver.activity.RunningActivity;
import com.microsoft.reef.driver.activity.SuspendedActivity;
import com.microsoft.reef.driver.context.ActiveContext;
import com.microsoft.reef.driver.evaluator.AllocatedEvaluator;
import com.microsoft.reef.driver.evaluator.CompletedEvaluator;
import com.microsoft.reef.tests.TestEnvironment;
import com.microsoft.reef.tests.TestEnvironmentFactory;
import com.microsoft.reef.tests.TestUtils;
import com.microsoft.reef.tests.exceptions.SimulatedDriverFailure;
import com.microsoft.reef.tests.fail.driver.FailClient;
import com.microsoft.reef.tests.fail.driver.FailDriver;
import com.microsoft.tang.Configuration;
import com.microsoft.tang.exceptions.BindException;
import com.microsoft.tang.exceptions.InjectionException;
import com.microsoft.wake.time.event.Alarm;
import com.microsoft.wake.time.event.StartTime;
import com.microsoft.wake.time.event.StopTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Run FailDriver with different types of failures.
 */
public class FailDriverTest {

  private final TestEnvironment testEnvironment = TestEnvironmentFactory.getNewTestEnvironment();

  @Before
  public void setUp() throws Exception {
    testEnvironment.setUp();
  }

  @After
  public void tearDown() throws Exception {
    this.testEnvironment.tearDown();
  }

  private void failOn(final Class<?> clazz) throws BindException, InjectionException {
    TestUtils.assertLauncherFailure(
        FailClient.run(clazz,
            this.testEnvironment.getRuntimeConfiguration(), this.testEnvironment.getTestTimeout()),
        SimulatedDriverFailure.class);
  }

  @Test
  public void testFailDriverConstructor() throws BindException, InjectionException {
    failOn(FailDriver.class);
  }

  @Test
  public void testFailDriverStart() throws BindException, InjectionException {
    failOn(StartTime.class);
  }

  @Test
  public void testFailDriverAllocatedEvaluator() throws BindException, InjectionException {
    failOn(AllocatedEvaluator.class);
  }

  @Test
  public void testFailDriverActiveContext() throws BindException, InjectionException {
    failOn(ActiveContext.class);
  }

  @Test
  public void testFailDriverRunningActivity() throws BindException, InjectionException {
    failOn(RunningActivity.class);
  }

  @Test
  public void testFailDriverActivityMessage() throws BindException, InjectionException {
    failOn(ActivityMessage.class);
  }

  @Test
  public void testFailDriverSuspendedActivity() throws BindException, InjectionException {
    failOn(SuspendedActivity.class);
  }

  @Test
  public void testFailDriverCompletedActivity() throws BindException, InjectionException {
    failOn(CompletedActivity.class);
  }

  @Test
  public void testFailDriverCompletedEvaluator() throws BindException, InjectionException {
    failOn(CompletedEvaluator.class);
  }

  @Test
  public void testFailDriverAlarm() throws BindException, InjectionException {
    failOn(Alarm.class);
  }

  @Test
  public void testFailDriverStop() throws BindException, InjectionException {
    failOn(StopTime.class);
  }

  @Test
  public void testDriverCompleted() throws BindException, InjectionException {
    final Configuration runtimeConfiguration = this.testEnvironment.getRuntimeConfiguration();
    // FailDriverTest can be replaced with any other class never used in FailDriver
    final LauncherStatus status = FailClient.run(
        FailDriverTest.class, runtimeConfiguration, this.testEnvironment.getTestTimeout());
    Assert.assertEquals(LauncherStatus.COMPLETED, status);
  }
}
