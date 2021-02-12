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

  /**
   * Starts the EE task with the provided arguments
   * 
   * @param args the arguments to start the task
   * @throws FailureException the failure exception which can be thrown by the EE
   */
  public static void main(final String[] args) throws FailureException {
    final EeStarter starter = new EeStarter();
    starter.execute(args);
  }

  @Override
  public void execute(final String[] args) throws FailureException {
    try {
      execute(EeTask.class, args);
    } catch (FailureException failureExc) {
      throw (FailureException) failureExc;
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }
}
