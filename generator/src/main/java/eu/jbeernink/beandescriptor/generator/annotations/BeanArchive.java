/*
 * Copyright 2024 Jan Beernink
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package eu.jbeernink.beandescriptor.generator.annotations;

import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/// Annotation to mark a module or JAR file as a bean archive.
///
/// Using this annotation will trigger a `beans.xml` file to be generated in the `META-INF` directory. This will mark
/// the module or jar file as a bean archive for CDI.
///
/// In a modular bean archive, the preferred approach is to use this annotation directly on the module descriptor.
///
/// For non-modular bean archives, a single `package-info` file should be annotated. If more than one package is
/// annotated, generating the `beans.xml` file will fail.
@Target({MODULE, PACKAGE})
@Retention(SOURCE)
public @interface BeanArchive {
	/// The bean discovery mode to use for this bean archive. Defaults to [BeanDiscoveryMode#ANNOTATED].
	BeanDiscoveryMode beanDiscoveryMode() default BeanDiscoveryMode.ANNOTATED;

	/// The bean discovery mode of a bean archive.
	enum BeanDiscoveryMode {
		/// No beans should be automatically discovered within the bean archive.
		NONE,
		/// Only beans annotated with bean defining annotations should be automatically discovered within the bean archive.
		ANNOTATED,
		/// All beans should be automatically discovered within the bean archive.
		ALL
	}
}
