import static eu.jbeernink.beandescriptor.generator.annotations.BeanArchive.BeanDiscoveryMode.NONE;

import eu.jbeernink.beandescriptor.generator.annotations.BeanArchive;

@BeanArchive(beanDiscoveryMode = NONE)
module eu.jbeernink.beandescriptor.generator.testing.none {
	requires eu.jbeernink.beandescriptorgenerator;
}