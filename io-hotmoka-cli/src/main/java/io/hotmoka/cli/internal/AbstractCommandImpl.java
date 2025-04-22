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
import java.util.Scanner;
import java.util.concurrent.Callable;

import io.hotmoka.cli.CommandException;

/**
 * Partial implementation of all commands of a CLI tool.
 */
public abstract class AbstractCommandImpl implements Callable<Void> {

	/**
	 * Builds the command.
	 */
	protected AbstractCommandImpl() {}

	@Override
	public final Void call() throws CommandException {
		execute();
		return null;
	}

	/**
	 * Executes the command.
	 * 
	 * @param out the output stream to use as standard output of the command
	 * @throws CommandException if something erroneous must be logged and the user must be informed
	 */
	protected abstract void execute() throws CommandException;

	/**
	 * Asks the user for confirmation.
	 * 
	 * @param message the message proposed to the user
	 * @return true if and only if the user presses the key {@code Y}
	 */
	protected boolean answerIsYes(String message) {
		System.out.print(message);

		try (var keyboard = new Scanner(System.in)) {
			return "Y".equals(keyboard.nextLine());
		}
	}

	/**
	 * Waits for the user to press the enter key.
	 * 
	 * @throws CommandException of the access to the standard input fails
	 */
	protected void waitForEnterKey() throws CommandException {
		try {
			System.in.read();
		}
		catch (IOException e) {
			throw new CommandException("Error while waiting for an enter key press", e);
		}
	}
}