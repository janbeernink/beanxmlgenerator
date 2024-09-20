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
package eu.jbeernink.beandescriptor.generator.processor;

import static javax.tools.StandardLocation.CLASS_OUTPUT;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;

import eu.jbeernink.beandescriptor.generator.annotations.BeanArchive;

/// Annotation processor for the [eu.jbeernink.beandescriptor.generator.annotations.BeanArchive] annotation.
@SupportedAnnotationTypes("eu.jbeernink.beandescriptor.generator.annotations.BeanArchive")
public class BeanArchiveProcessor extends AbstractProcessor {
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		annotations.stream().findFirst().ifPresent(annotation -> {
			try {
				Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(annotation);

				for (Element element : elementsAnnotatedWith) {
					BeanArchive beanArchive = element.getAnnotation(BeanArchive.class);
					Filer filer = processingEnv.getFiler();
					FileObject beanDescriptorFile =
							filer.createResource(CLASS_OUTPUT, "", "META-INF/beans.xml");

					try (Writer out = beanDescriptorFile.openWriter()) {
						generateBeansXml(out, beanArchive);
					}
				}
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		});

		return true;
	}

	private void generateBeansXml(Writer out, BeanArchive beanArchive) throws IOException {
		out.write("<beans bean-discovery-mode=\"%s\"></beans>".formatted(switch (beanArchive.beanDiscoveryMode()) {
			case NONE -> "none";
			case ANNOTATED -> "annotated";
			case ALL -> "all";
		}));
	}
}
