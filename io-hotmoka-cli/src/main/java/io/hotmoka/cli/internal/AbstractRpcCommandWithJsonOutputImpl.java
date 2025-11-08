/*
Copyright 2025 Fausto Spoto

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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

import io.hotmoka.cli.AbstractRpcCommand;
import io.hotmoka.cli.CommandException;
import io.hotmoka.websockets.beans.api.EncoderText;
import io.hotmoka.websockets.client.api.Remote;
import jakarta.websocket.EncodeException;
import picocli.CommandLine.Option;

/**
 * Shared code among the commands that connect to a remote object, perform Rpc calls to its API and
 * can provide their output in JSON format, if required.
 * 
 * @param <R> the type of the remote object executing the Rpc calls
 */
public abstract class AbstractRpcCommandWithJsonOutputImpl<R extends Remote> extends AbstractRpcCommand<R> {

	@Option(names = "--redirection", paramLabel = "<path>", description = "the path where the output must be redirected, if any; if missing, the output is printed to the standard output")
	private Path redirection;

	@Option(names = "--json", description = "print the output in JSON", defaultValue = "false")
	private boolean json;

	/**
	 * Determines if the output is required in JSON format.
	 * 
	 * @return true if and only if that condition holds
	 */
	protected final boolean json() {
		return json;
	}

	/**
	 * Reports on the given output of a command.
	 * 
	 * @param <O> the type of the output to report
	 * @param output the output to report
	 * @param encoder a supplier of a converter of the output into its JSON representation; this will
	 *                be used only if {@code json} is true
	 * @throws CommandException if reporting failed
	 */
	protected <O> void report(O output, Supplier<EncoderText<O>> encoder) throws CommandException {
		String textualOutput;

		if (json) {
			try {
				textualOutput = encoder.get().encode(output) + "\n";
			}
			catch (EncodeException e) {
				throw new CommandException("Cannot encode the output of the command in JSON format", e);
			}
		}
		else
			textualOutput = output.toString();

		if (redirection == null)
			System.out.print(textualOutput);
		else {
			try {
				Files.writeString(redirection, textualOutput);
			}
			catch (IOException e) {
				throw new CommandException("Cannot write the JSON output as \"" + redirection + "\": " + e.getMessage());
			}
		}
	}
}