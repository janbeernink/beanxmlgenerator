package eu.jbeernink.beandescriptor.generator.annotations;

import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({MODULE, PACKAGE})
@Retention(SOURCE)
public @interface BeanArchive {
	BeanDiscoveryMode beanDiscoveryMode() default BeanDiscoveryMode.ANNOTATED;

	enum BeanDiscoveryMode {
		NONE,
		ANNOTATED,
		ALL
	}
}
