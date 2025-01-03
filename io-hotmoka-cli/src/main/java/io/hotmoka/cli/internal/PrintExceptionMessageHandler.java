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

import java.util.logging.Level;
import java.util.logging.Logger;

import io.hotmoka.cli.CommandException;
import picocli.CommandLine;
import picocli.CommandLine.IExecutionExceptionHandler;
import picocli.CommandLine.ParseResult;

public class PrintExceptionMessageHandler implements IExecutionExceptionHandler {

	private final static Logger LOGGER = Logger.getLogger(PrintExceptionMessageHandler.class.getName());

	@Override
	public int handleExecutionException(Exception e, CommandLine cmd, ParseResult parseResult) throws Exception {
		if (e instanceof CommandException) {
			Throwable cause = e;
			if (cause.getCause() != null)
				cause = cause.getCause();

			var message = e.getMessage();
			if (message.isEmpty())
				message = cause.getClass().getName() + ": " + cause.getMessage();

			LOGGER.warning("command threw exception: " + message);
			cmd.getErr().println(cmd.getColorScheme().errorText(message));

			var mapper = cmd.getExitCodeExceptionMapper();
			return mapper != null ? mapper.getExitCode(cause) : cmd.getCommandSpec().exitCodeOnExecutionException();
		}
		else {
			LOGGER.log(Level.SEVERE, "unexpected exception", e);
			throw e;
		}
    }
}