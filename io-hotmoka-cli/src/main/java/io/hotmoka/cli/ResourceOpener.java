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

import java.io.IOException;
import java.io.InputStream;

/**
 * A function that opens a resource.
 */
public interface ResourceOpener {

	/**
	 * Opens the resource.
	 * 
	 * @return the stream of the resource
	 * @throws IOException if the resource cannot be opened
	 */
	InputStream open() throws IOException;
}