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

import java.util.concurrent.TimeoutException;

import io.hotmoka.websockets.client.api.Remote;

/**
 * The body of an Rpc command consuming the API of a remote object.
 * 
 * @param <R> the type of the remote object executing the Rpc calls
 */
public interface RpcCommandBody<R extends Remote> {

	/**
	 * Runs the body of the command.
	 * 
	 * @param remote the remote object
	 * @throws TimeoutException if the command timeouts
	 * @throws InterruptedException if the command was interrupted while waiting
	 * @throws CommandException if something erroneous must be logged and the user must be informed;
	 *                          throw this is the user provided a wrong argument to the command
	 */
	void run(R remote) throws TimeoutException, InterruptedException, CommandException;
}