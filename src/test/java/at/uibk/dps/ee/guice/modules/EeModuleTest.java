package at.uibk.dps.ee.guice.modules;

import org.junit.Test;
import org.opt4j.core.IndividualStateListener;
import org.opt4j.core.optimizer.OptimizerIterationListener;
import org.opt4j.core.optimizer.OptimizerStateListener;

/**
 * Tests that the Opt4J methods result in exceptions.
 * 
 * @author Fedor Smirnov
 *
 */
public class EeModuleTest {

	protected class EeModuleMock extends EeModule {
		@Override
		protected void config() {
		}
	}

	@Test(expected = IllegalStateException.class)
	public void testIndiListener() {
		EeModuleMock tested = new EeModuleMock();
		tested.addIndividualStateListener(IndividualStateListener.class);
	}

	@Test(expected = IllegalStateException.class)
	public void testIterationListener() {
		EeModuleMock tested = new EeModuleMock();
		tested.addOptimizerIterationListener(OptimizerIterationListener.class);
	}

	@Test(expected = IllegalStateException.class)
	public void testOptimizerStateListener() {
		EeModuleMock tested = new EeModuleMock();
		tested.addOptimizerStateListener(OptimizerStateListener.class);
	}
}
