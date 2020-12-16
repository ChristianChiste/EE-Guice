package at.uibk.dps.ee.guice.starter;

import org.opt4j.core.config.Starter;

import at.uibk.dps.ee.core.exception.FailureException;

/**
 * Class used to start the enactment without the activation of the interactive
 * configuration GUI.
 * 
 * @author Fedor Smirnov
 *
 */
public class EeStarter extends Starter {

	public static void main(String[] args) throws FailureException {
		EeStarter starter = new EeStarter();
		starter.execute(args);
	}

	@Override
	public void execute(String[] args) throws FailureException {
		try {
			execute(EeTask.class, args);
		} catch (Exception exception) {
			if (exception instanceof FailureException) {
				throw (FailureException) exception;
			} else {
				exception.printStackTrace();
				throw new IllegalStateException("Unexpected Exception when executing the EE task");
			}
		}
	}
}
