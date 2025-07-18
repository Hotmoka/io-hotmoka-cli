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

/**
 * This module implements utility classes for the construction of command-line interfaces.
 */
module io.hotmoka.cli {
	exports io.hotmoka.cli;
	opens io.hotmoka.cli.internal to info.picocli; // for injecting CLI options and accessing the AbstractPropertyFileVersionProviderImpl

	requires transitive info.picocli;
	requires io.hotmoka.websockets.client.api;
	requires com.google.gson;
	requires java.logging;
	requires jakarta.websocket.client;
}