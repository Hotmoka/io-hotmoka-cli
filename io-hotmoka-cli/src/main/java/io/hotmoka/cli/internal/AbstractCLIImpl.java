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

import java.io.IOException;
import java.util.function.Supplier;
import java.util.logging.LogManager;

import io.hotmoka.cli.AbstractCLI;
import io.hotmoka.cli.ResourceOpener;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Option;

/**
 * Partial implementation of a command-line tool. Subclasses specify arguments, options and execution.
 */
@Command(
	subcommands = {
		HelpCommand.class
	},
	showDefaultValues = true
)
public abstract class AbstractCLIImpl {

	@Option(names = { "--version" }, versionHelp = true, description = "print version information and exit")
	private boolean versionRequested;

	/**
	 * Builds the tool.
	 */
	protected AbstractCLIImpl() {}

	/**
	 * Entry point of a tool. This is typically called by the actual {@code main} method
	 * of the tool, providing its same supplier.
	 * 
	 * @param tool the supplier of an object of the tool that will be run
	 * @param args the command-line arguments passed to the tool
	 * @return the exit value that can be reported to the shell that ran this command, if any
	 */
	protected static int main(Supplier<AbstractCLI> tool, String[] args) {
		return new CommandLine(tool.get())
			.setExecutionExceptionHandler(new PrintExceptionMessageHandler())
			.execute(args);
	}

	/**
	 * Loads the Java logging configuration from the provided resource.
	 * If the property {@code java.util.logging.config.file} is defined,
	 * nothing will be loaded.
	 * 
	 * @param opener a specification of how the resource can be opened
	 */
	protected static void loadLoggingConfig(ResourceOpener opener) {
		String current = System.getProperty("java.util.logging.config.file");
		if (current == null) {
			// if the property is not set, we provide a default (if it exists)
			try (var is = opener.open()) {
				LogManager.getLogManager().readConfiguration(is);
			}
			catch (SecurityException | IOException e) {
				throw new RuntimeException("Cannot load the logging properties file", e);
			}
		}
	}
}