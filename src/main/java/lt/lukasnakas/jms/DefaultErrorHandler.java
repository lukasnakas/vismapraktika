package lt.lukasnakas.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

public class DefaultErrorHandler implements ErrorHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultErrorHandler.class);

	@Override
	public void handleError(Throwable throwable) {
		LOGGER.error(throwable.getCause().getMessage());
	}

}
