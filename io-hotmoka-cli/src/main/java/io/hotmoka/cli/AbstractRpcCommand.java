/*
Copyright 2023 Fausto Spoto

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

import io.hotmoka.cli.internal.AbstractRpcCommandImpl;
import io.hotmoka.websockets.client.api.Remote;

/**
 * Shared code among the command that connect to a remote object and perform Rpc calls to its API.
 * 
 * @param <R> the type of the remote object executing the Rpc calls
 * @param <E> the type of the exceptions thrown by the remote object if it is misbehaving
 */
public abstract class AbstractRpcCommand<R extends Remote<E>, E extends Exception> extends AbstractRpcCommandImpl<R, E> {

	/**
	 * Creates the command.
	 * 
	 * @param misbehavingExceptionClass the class of the exceptions thrown by the remote object if it is misbehaving
	 */
	protected AbstractRpcCommand(Class<E> misbehavingExceptionClass) {
		super(misbehavingExceptionClass);
	}
}