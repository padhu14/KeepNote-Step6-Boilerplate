package com.stackroute.keepnote.aspectj;

import java.time.LocalDateTime;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* Annotate this class with @Aspect and @Component */
@Aspect
public class LoggingAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

	/*
	 * Write loggers for each of the methods of Category controller, any particular
	 * method will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
	@Before(value = "*execution(com.stackroute.keepnote.controller.*)")
	public void logBefore() {
		LOGGER.info("Controller method executes start time " + LocalDateTime.now());
	}

	@After("execution(com.stackroute.keepnote.controller.*)")
	public void logAfter() {
		LOGGER.info("Controller method executes end time " + LocalDateTime.now());
	}

	@AfterThrowing(pointcut = "execution(com.stackroute.keepnote.controller.*)", throwing = "exception")
	public void logAfterThrowing(Exception exception) {
		LOGGER.error("Uncaught Exceptions ", exception);
	}

	@AfterReturning(pointcut = "execution(com.stackroute.keepnote.controller.*)", returning = "val")
	public void logAfterReturning(Object val) {
		LOGGER.info("Method return value:" + val);
		LOGGER.info("@AfterReturning:" + LocalDateTime.now());
	}
}
