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

package io.hotmoka.cli.internal;

import java.net.URI;
import java.util.concurrent.TimeoutException;

import io.hotmoka.cli.AbstractCommand;
import io.hotmoka.cli.CommandException;
import io.hotmoka.cli.RemoteSupplier;
import io.hotmoka.cli.RpcCommandBody;
import io.hotmoka.websockets.client.api.Remote;
import picocli.CommandLine.Option;

/**
 * Shared code among the command that connect to a remote object and perform Rpc calls to its API.
 * 
 * @param <R> the type of the remote object executing the Rpc calls
 * @param <E> the type of the exceptions thrown by the remote object if it is misbehaving
 */
public abstract class AbstractRpcCommandImpl<R extends Remote<E>, E extends Exception> extends AbstractCommand {

	@Option(names = "--timeout", description = "the timeout of the connection, in milliseconds", defaultValue = "10000")
	private int timeout;

	@Option(names = "--json", description = "print the output in JSON", defaultValue = "false")
	private boolean json;

	/**
	 * The class of the exceptions thrown by the remote object if it is misbehaving.
	 */
	private final Class<E> misbehavingExceptionClass;

	/**
	 * Creates the command.
	 * 
	 * @param commandExceptionClass the class of the exceptions thrown by the remote object if it is misbehaving
	 */
	protected AbstractRpcCommandImpl(Class<E> commandExceptionClass) {
		this.misbehavingExceptionClass = commandExceptionClass;
	}

	/**
	 * Determines if the output must be reported in JSON format.
	 * 
	 * @return true if and only if that condition holds
	 */
	protected final boolean json() {
		return json;
	}

	/**
	 * Yields the timeout of the connection, in milliseconds.
	 * 
	 * @return the timeout
	 */
	protected final long timeout() {
		return timeout;
	}

	/**
	 * Opens a remote node connected to the public uri of the remote service and runs
	 * the given command body.
	 * 
	 * @param supplier the supplier of the remote node
	 * @param what the body
	 * @param uri the uri where the remote service can be contacted
	 * @throws CommandException if something erroneous must be logged and the user must be informed
	 */
	protected void execute(RemoteSupplier<R, E> supplier, RpcCommandBody<R, E> what, URI uri) throws CommandException {
		try (var remote = supplier.get(uri, timeout)) {
			what.run(remote);
		}
		catch (TimeoutException e) {
			throw new CommandException("Timeout: I waited for " + timeout + "ms but the remote service at " + uri + " didn't answer!", e);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new CommandException("Unexpected interruption while waiting for " + uri + "!", e);
		}
		catch (CommandException e) {
			throw e;
		}
		catch (Exception e) {
			if (misbehavingExceptionClass.isAssignableFrom(e.getClass()))
				throw new RuntimeException("The remote service is misbehaving: are you sure that it is actually published at " + uri + " and is accessible?", e);
			else
				throw (RuntimeException) e;
		}
	}
}