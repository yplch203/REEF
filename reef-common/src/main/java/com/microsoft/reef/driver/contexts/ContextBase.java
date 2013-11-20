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
package com.microsoft.reef.driver.contexts;

import com.microsoft.reef.annotations.Provided;
import com.microsoft.reef.annotations.audience.DriverSide;
import com.microsoft.reef.annotations.audience.Public;
import com.microsoft.reef.driver.catalog.NodeDescriptor;
import com.microsoft.reef.io.naming.Identifiable;
import com.microsoft.reef.util.Optional;

/**
 * A common base interface for Contexts, available or failed.
 */
@Public
@DriverSide
@Provided
public interface ContextBase extends Identifiable {

  /**
   * @return the ID of this EvaluatorContext.
   */
  @Override
  public String getId();

  /**
   * @return the identifier of the Evaluator this EvaluatorContext is instantiated on.
   */
  public String getEvaluatorId();

  /**
   * @return the ID of the parent context, if there is any.
   */
  public Optional<String> getParentId();

  /**
   * @return the node descriptor of the Evaluator this Context is active on.
   */
  public NodeDescriptor getNodeDescriptor();

}