/*
Copyright 2024 Fausto Spoto

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package io.hotmoka.cli;

import java.net.URI;
import java.util.concurrent.TimeoutException;

import io.hotmoka.websockets.client.api.Remote;

/**
 * A supplier of a remote object.
 *
 * @param <R> the type of the remote object executing the Rpc calls
 * @param <E> the type of the exceptions thrown by the remote object if it is misbehaving
 */
public interface RemoteSupplier<R extends Remote<E>, E extends Exception> {

	/**
	 * Yields the remote.
	 * 
	 * @param uri the uri where the remote service is published
	 * @param timeout the threshold, in milliseconds, after which Rpc calls will timeout
	 * @return the remote
	 * @throws TimeoutException if the creation of the remote timeouts
	 * @throws InterruptedException if the current thread has been interrupted
	 * @throws E if the creation could not be performed correctly
	 */
	R get(URI uri, int timeout) throws E, TimeoutException, InterruptedException;
}