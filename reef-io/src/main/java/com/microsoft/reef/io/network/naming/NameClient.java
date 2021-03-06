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
package com.microsoft.reef.io.network.naming;

import com.microsoft.reef.io.naming.Naming;
import com.microsoft.reef.io.network.Cache;
import com.microsoft.reef.io.network.naming.exception.NamingRuntimeException;
import com.microsoft.reef.io.network.naming.serialization.NamingLookupResponse;
import com.microsoft.reef.io.network.naming.serialization.NamingMessage;
import com.microsoft.reef.io.network.naming.serialization.NamingRegisterResponse;
import com.microsoft.wake.EventHandler;
import com.microsoft.wake.Identifier;
import com.microsoft.wake.IdentifierFactory;
import com.microsoft.wake.Stage;
import com.microsoft.wake.impl.SyncStage;
import com.microsoft.wake.remote.Codec;
import com.microsoft.wake.remote.NetUtils;
import com.microsoft.wake.remote.impl.TransportEvent;
import com.microsoft.wake.remote.transport.Transport;
import com.microsoft.wake.remote.transport.netty.NettyMessagingTransport;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Naming client
 */
public class NameClient implements Stage, Naming {
  private static final Logger LOG = Logger.getLogger(NameClient.class.getName());

  private NameLookupClient lookupClient;
  private NameRegistryClient registryClient;
  private Transport transport;

  /**
   * Constructs a naming client
   * 
   * @param serverAddr a server address
   * @param serverPort a server port number
   * @param factory an identifier factory
   * @param cache a cache
   */
  public NameClient(String serverAddr, int serverPort, IdentifierFactory factory, Cache<Identifier, InetSocketAddress> cache) {
    this(serverAddr, serverPort, 10000, factory, cache);
  }
  
  /**
   * Constructs a naming client
   * 
   * @param serverAddr a server address
   * @param serverPort a server port number
   * @param timeout timeout in ms
   * @param factory an identifier factory
   * @param cache a cache
   */
  public NameClient(String serverAddr, int serverPort, long timeout, IdentifierFactory factory, Cache<Identifier, InetSocketAddress> cache) {
    BlockingQueue<NamingLookupResponse> replyLookupQueue = new LinkedBlockingQueue<NamingLookupResponse>();
    BlockingQueue<NamingRegisterResponse> replyRegisterQueue = new LinkedBlockingQueue<NamingRegisterResponse>();
    Codec<NamingMessage> codec = NamingCodecFactory.createFullCodec(factory);
    transport = new NettyMessagingTransport(NetUtils.getLocalAddress(), 0, 
        new SyncStage<TransportEvent>(new NamingClientEventHandler(new NamingResponseHandler(replyLookupQueue, replyRegisterQueue), codec)), null);
    
    lookupClient = new NameLookupClient(serverAddr, serverPort, timeout, factory, replyLookupQueue, transport, cache);
    registryClient = new NameRegistryClient(serverAddr, serverPort, timeout, factory, replyRegisterQueue, transport);
  }

  /**
   * Registers an (identifier, address) mapping
   * 
   * @param id an identifier
   * @param addr an Internet socket address
   */
  @Override
  public void register(Identifier id, InetSocketAddress addr)
      throws Exception {
    LOG.log(Level.FINE, id + " " + addr);
    registryClient.register(id, addr);
  }

  /**
   * Unregisters an identifier
   * 
   * @param id an identifier
   */
  @Override
  public void unregister(Identifier id) throws IOException {
    registryClient.unregister(id);
  }

  /**
   * Finds an address for an identifier
   * 
   * @param id an identifier
   * @return an Internet socket address
   */
  @Override
  public InetSocketAddress lookup(Identifier id) throws Exception {
    return lookupClient.lookup(id);
  }


  /**
   * Retrieves an address for an identifier remotely
   * 
   * @param id an identifier
   * @return an Internet socket address
   * @throws Exception
   */
  public InetSocketAddress remoteLookup(Identifier id) throws Exception {
    return lookupClient.remoteLookup(id);
  }
  
  /**
   * Closes resources
   */
  @Override
  public void close() throws Exception {
    if(lookupClient!=null)
      lookupClient.close();
    if(registryClient!=null)
      registryClient.close();
    if(transport!=null)
      transport.close();
  }

}

/**
 * Naming client transport event handler
 */
class NamingClientEventHandler implements EventHandler<TransportEvent> {
  private static final Logger LOG = Logger.getLogger(NamingClientEventHandler.class.getName());

  private final EventHandler<NamingMessage> handler;
  private final Codec<NamingMessage> codec;
  
  public NamingClientEventHandler(EventHandler<NamingMessage> handler, Codec<NamingMessage> codec) {
    this.handler = handler;
    this.codec = codec;
  }
  
  @Override
  public void onNext(TransportEvent value) {
    LOG.log(Level.FINE, value.toString());
    handler.onNext(codec.decode(value.getData()));
  }
  
}

/**
 * Naming response message handler
 */
class NamingResponseHandler implements EventHandler<NamingMessage> {

  private final BlockingQueue<NamingLookupResponse> replyLookupQueue;
  private final BlockingQueue<NamingRegisterResponse> replyRegisterQueue;

  NamingResponseHandler(BlockingQueue<NamingLookupResponse> replyLookupQueue, 
      BlockingQueue<NamingRegisterResponse> replyRegisterQueue) {
    this.replyLookupQueue = replyLookupQueue;
    this.replyRegisterQueue = replyRegisterQueue;
  }

  @Override
  public void onNext(NamingMessage value) {
    if (value instanceof NamingLookupResponse) {
      replyLookupQueue.offer((NamingLookupResponse)value);
    } else if (value instanceof NamingRegisterResponse) {
      replyRegisterQueue.offer((NamingRegisterResponse)value);
    } else {
      throw new NamingRuntimeException("Unknown naming response message");
    }
    
  }
  
}
