package at.uibk.dps.ee.guice.starter;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.inject.Module;

import at.uibk.dps.ee.guice.modules.EeModule;

public class EEConfigurationTest {

	protected abstract class MockEEModule extends EeModule {
	}

	protected abstract class MockNotEEModule implements Module{
	}

	@Test
	public void testModuleInclusion() {
		assertTrue(EeConfiguration.includeModuleClassInGui(MockEEModule.class));
		assertFalse(EeConfiguration.includeModuleClassInGui(MockNotEEModule.class));
	}
}
