package eu.jbeernink.beandescriptor.generator.processor;

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
import javax.tools.StandardLocation;

import eu.jbeernink.beandescriptor.generator.annotations.BeanArchive;

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
							filer.createResource(StandardLocation.CLASS_OUTPUT, "", "META-INF/beans.xml");

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
