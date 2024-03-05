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
import java.io.InputStream;
import java.util.Properties;

import io.hotmoka.cli.ResourceOpener;
import picocli.CommandLine.IVersionProvider;

/**
 * A picocli dynamic version provider, that reads a property file, where the
 * version of the CLI tool is stored. That property file might be generated, for
 * instance, from the properties of a Maven POM file.
 */
public abstract class AbstractPropertyFileVersionProviderImpl implements IVersionProvider {

	/**
	 * Provides the version of the tool, by reading it from the given property file.
	 * That property file can, for instance, be generated from the POM file of the CLI tool.
	 * That property file must be readable as a resource from the concrete class that implements
	 * this abstract class.
	 * 
	 * @param propertiesFileOpener a specification of how the property file resource can be opened
	 * @param propertyName the name of the property reported in the property file
	 * @return the version's components
	 * @throws IOException if the {@code mavenPropertyFile} cannot be read
	 */
	protected String[] getVersion(ResourceOpener propertiesFileOpener, String propertyName) throws IOException {
		try (InputStream is = propertiesFileOpener.open()) {
			if (is == null)
				throw new IOException("The property file containing the CLI version could not be opened");

			var mavenProperties = new Properties();
			mavenProperties.load(is);
			String version = mavenProperties.getProperty(propertyName);
			if (version == null)
				throw new IOException("The property file does not contain a " + propertyName + " property");

			return new String[] { version };
		}
	}
}