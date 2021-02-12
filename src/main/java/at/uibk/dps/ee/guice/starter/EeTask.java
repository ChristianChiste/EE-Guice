package at.uibk.dps.ee.guice.starter;

import org.opt4j.core.start.Opt4JTask;

import at.uibk.dps.ee.core.exception.FailureException;
import at.uibk.dps.ee.guice.EeCoreInjectable;

/**
 * The {@link EeTask} triggers the enactment of a workflow based on a
 * module-driven configuration of the enactment process.
 * 
 * @author Fedor Smirnov
 *
 */
public class EeTask extends Opt4JTask {

  protected EeCoreInjectable eeCore;

  @Override
  public void execute() throws FailureException {
    open();
    eeCore = injector.getInstance(EeCoreInjectable.class);
    eeCore.enactWorkflow();
    if (closeOnStop) {
      close();
    }
  }
}
