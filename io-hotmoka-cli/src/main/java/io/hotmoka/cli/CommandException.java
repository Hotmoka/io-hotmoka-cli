/*
Copyright 2021 Fausto Spoto

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

import java.util.Objects;

/**
 * An exception thrown during the execution of a CLI command.
 */
public class CommandException extends Exception {

	private static final long serialVersionUID = 2066756038127592236L;

	/**
	 * Creates the exception, with the given message.
	 * 
	 * @param message the message; this gets reported on the shell to the user of the command and gets logged
	 */
	public CommandException(String message) {
		super(Objects.requireNonNull(message));
	}

	/**
	 * Creates the exception, with the given message and cause.
	 * 
	 * @param message the message; this gets reported on the shell to the user of the command and gets logged
	 * @param cause the cause; this gets logged and its type and message reported to the user of the command
	 */
	public CommandException(String message, Throwable cause) {
		super(Objects.requireNonNull(message), Objects.requireNonNull(cause));
	}
}