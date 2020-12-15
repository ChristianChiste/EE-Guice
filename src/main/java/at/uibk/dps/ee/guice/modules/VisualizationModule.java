package at.uibk.dps.ee.guice.modules;

import org.opt4j.core.config.annotations.Category;
import org.opt4j.viewer.ToolBarService;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;

/**
 * Parent class for the modules configuring the visualization of the enactment.
 * 
 * @author Fedor Smirnov
 *
 */
@Category("Visualization")
public abstract class VisualizationModule extends EeModule {
	
	// Reference module in Opt4J:  org.opt4j.viewer.VisualizationModule
	
	/**
	 * Add a {@link ToolBarService}.
	 * 
	 * @param toolBarService
	 *            the tool bar service to be added
	 */
	public void addToolBarService(final Class<? extends ToolBarService> toolBarService) {
		final Multibinder<ToolBarService> multibinder = Multibinder.newSetBinder(binder(), ToolBarService.class);
		multibinder.addBinding().to(toolBarService);
	}

	/**
	 * Add a {@link ToolBarService}.
	 * 
	 * @param binder
	 *            the binder
	 * @param toolBarService
	 *            the tool bar service to be added
	 */
	public static void addToolBarService(final Binder binder, final Class<? extends ToolBarService> toolBarService) {
		final Multibinder<ToolBarService> multibinder = Multibinder.newSetBinder(binder, ToolBarService.class);
		multibinder.addBinding().to(toolBarService);
	}
	
	@Override
	protected void configure() {
		super.configure();
		multi(ToolBarService.class);
	}
}
