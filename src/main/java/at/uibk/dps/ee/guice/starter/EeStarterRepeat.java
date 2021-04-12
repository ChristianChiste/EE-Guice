package at.uibk.dps.ee.guice.starter;

import java.util.Arrays;

import org.opt4j.core.config.Starter;

import at.uibk.dps.ee.core.ExecutionData;
import at.uibk.dps.ee.core.exception.FailureException;

/**
 * Class used to start the enactment without the activation of the interactive
 * configuration GUI.
 * 
 * @author Christian Chisté
 *
 */
public class EeStarterRepeat extends Starter {

  /**
   * Starts the EE task with the provided arguments
   * 
   * @param args the arguments to start the task
   * @throws FailureException the failure exception which can be thrown by the EE
   */
  public static void main(final String[] args) throws FailureException {
    final EeStarter starter = new EeStarter();
    final int executions = Integer.valueOf(args[0]);
    final String workflowName = args[1].substring(0,17).split("-")[0];
    ExecutionData.workflowName = workflowName;
    for(int i = 0; i < executions; i++) {
      starter.execute(Arrays.copyOfRange(args, 1, args.length));
    }
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
