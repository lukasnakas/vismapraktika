package lt.lukasnakas.configuration;

import lt.lukasnakas.jms.DefaultErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class JmsConfiguration implements JmsListenerConfigurer {

	public static final String PAYMENT_QUEUE = "inbound.queue";


	private final ConnectionFactory connectionFactory;

	public JmsConfiguration(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public void configureJmsListeners(JmsListenerEndpointRegistrar jmsListenerEndpointRegistrar) {
		jmsListenerEndpointRegistrar.setContainerFactory(containerFactory());
	}

	@Bean
	public JmsListenerContainerFactory<?> containerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setErrorHandler(new DefaultErrorHandler());
		factory.setMessageConverter(jacksonJmsMessageConverter());
		return factory;
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

}
