package com.bk.hotel.controller;

import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Example of using dynamic test functionality in JUnit 5. Examples demonstrates
 * how dynamic test might be used to ensure POST endpoints consume a predefined
 * list of MediaTypes.
 * 
 * @author William.Korando
 *
 */
public class TestAcceptedMediaTypes {

	private static final MediaType[] SUPPORTED_MEDIA_TYPES = new MediaType[] { MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML };

	@TestFactory
	Collection<DynamicNode> testAcceptedMediaTypes() throws Exception {

		// Creating a classpath scanner to find all controller classes in project
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(new DefaultListableBeanFactory(),
				false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
		Set<BeanDefinition> beanDefinitons = scanner.findCandidateComponents("com.bk.hotel");
		Set<Object> controllers = new HashSet<>();
		Set<Class<?>> controllersClasses = new HashSet<>();

		// Instantiating controller classes
		for (BeanDefinition beanDefiniton : beanDefinitons) {
			String className = beanDefiniton.getBeanClassName();
			Class<?> controllerClazz = ClassLoader.getSystemClassLoader().loadClass(className);
			controllersClasses.add(controllerClazz);

			Constructor<?> constructor = controllerClazz.getDeclaredConstructors()[0];
			Object[] arguments = new Object[constructor.getParameterTypes().length];
			int i = 0;
			for (Class<?> parameterType : constructor.getParameterTypes()) {
				arguments[i] = mock(parameterType);
				i++;
			}
			controllers.add(constructor.newInstance(arguments));
		}

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controllers.toArray()).build();
		List<DynamicNode> containers = new ArrayList<>();

		// Check if controller has a POST endpoint and call it with all the different
		// mediatypes, throw an error in a 415 (unsupported media type) is returned
		for (Class<?> controllerClazz : controllersClasses) {
			RequestMapping mapping = controllerClazz.getAnnotationsByType(RequestMapping.class)[0];
			StringBuilder builder = new StringBuilder();
			builder.append(mapping.value()[0]);
			for (Method method : controllerClazz.getMethods()) {
				if (method.isAnnotationPresent(PostMapping.class)) {
					List<DynamicTest> dynamicTests = new ArrayList<>();
					for (MediaType mediaType : SUPPORTED_MEDIA_TYPES) {
						dynamicTests.add(dynamicTest(mediaType.toString(),
								() -> mockMvc.perform(post(builder.toString()).contentType(mediaType))
										.andExpect(status().is(IsNot.not(415)))));
					}
					//Group the test into containers so the test report will be separated by controller
					containers.add(dynamicContainer(builder.toString(), dynamicTests));
				}
			}
		}
		return containers;
	}
}