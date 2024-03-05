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

import io.hotmoka.cli.internal.AbstractPropertyFileVersionProviderImpl;

/**
 * A picocli dynamic version provider, that reads a property file, where the
 * version of the CLI tool is stored. That property file might be generated, for
 * instance, from the properties of a Maven POM file.
 */
public abstract class AbstractPropertyFileVersionProvider extends AbstractPropertyFileVersionProviderImpl {
}