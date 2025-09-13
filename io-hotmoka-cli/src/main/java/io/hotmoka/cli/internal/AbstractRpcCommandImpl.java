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
import io.hotmoka.cli.RpcCommandBody;
import io.hotmoka.websockets.api.FailedDeploymentException;
import io.hotmoka.websockets.client.api.Remote;
import io.hotmoka.websockets.client.api.RemoteSupplier;
import picocli.CommandLine.Option;

/**
 * Shared code among the command that connect to a remote object and perform Rpc calls to its API.
 * 
 * @param <R> the type of the remote object executing the Rpc calls
 */
public abstract class AbstractRpcCommandImpl<R extends Remote> extends AbstractCommand {

	@Option(names = "--timeout", paramLabel = "<milliseconds>", description = "the timeout of the connection", defaultValue = "90000")
	private int timeout;

	/**
	 * Creates the command.
	 */
	protected AbstractRpcCommandImpl() {}

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
	protected void execute(RemoteSupplier<R> supplier, RpcCommandBody<R> what, URI uri) throws CommandException {
		try (var remote = supplier.get(uri, timeout)) {
			what.run(remote);
		}
		catch (TimeoutException e) {
			throw new CommandException("Timeout: I waited for " + timeout + "ms but the remote service at " + uri + " didn't answer!", e);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new CommandException("The execution of the command has been interrupted while waiting for " + uri + "!", e);
		}
		catch (CommandException e) {
			throw e;
		}
		catch (FailedDeploymentException e) {
			throw new CommandException("Connection failed: are you sure that the remote service is actually published at " + uri + " and that it is initialized and accessible?", e);
		}
	}
}