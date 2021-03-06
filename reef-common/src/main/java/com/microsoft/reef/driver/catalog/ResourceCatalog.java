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
package com.microsoft.reef.driver.catalog;

import com.microsoft.reef.annotations.Unstable;
import com.microsoft.reef.driver.capabilities.Capability;

import java.util.Collection;

/**
 * A catalog of the resources available to a REEF instance.
 * <p/>
 * This catalog contains static information about the resources and does not
 * reflect that dynamic availability of resources. In other words: Its entries
 * are an upper bound to what is available to a REEF {@link Driver} at any given
 * moment in time.
 */
@Unstable
public interface ResourceCatalog {

  public interface Descriptor {

    public String getName();

    /**
     * Access the list of {@link Capability}s of this Evaluator.
     * <p/>
     * Note: This list at least contains the capabilities requested for this
     * Evaluator upon allocation time, but a specific REEF implementation may
     * choose to add more capabilities based on the physical environment of the
     * Evaluator.
     *
     * @return the list of {@link Capability}s of this Evaluator.
     */
    public Collection<Capability> getCapabilities();

  }

  /**
   * The global list of resources.
   *
   * @return a list of all the static resources available. This is an upper
   *         bound.
   */
  public Collection<NodeDescriptor> getNodes();


  /**
   * The global list of racks
   *
   * @return list of all rack descriptors
   */
  public Collection<RackDescriptor> getRacks();

  /**
   * Get the node descriptor with the given identifier.
   *
   * @param id of the node.
   * @return the node descriptor assigned to the identifier.
   */
  public NodeDescriptor getNode(String nodeId);

  /**
   * Query interface.
   *
   * @param capabilities the set of {@link Capability}s queried for.
   * @return the list of entries supporting the given set of
   *         {@link Capability}s
   */
  public Collection<NodeDescriptor> withCapabilities(final Capability... capabilities);

}
