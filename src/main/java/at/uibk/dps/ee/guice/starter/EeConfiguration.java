package at.uibk.dps.ee.guice.starter;

import java.util.Arrays;
import java.util.List;

import org.opt4j.core.config.ModuleAutoFinder;
import org.opt4j.core.config.ModuleList;
import org.opt4j.core.config.Task;
import org.opt4j.core.start.Opt4J;

import com.google.inject.Module;

import at.uibk.dps.ee.core.exception.FailureException;
import at.uibk.dps.ee.guice.modules.EeModule;


/**
 * The {@link EeConfiguration} is used to provide an interactive GUI for the
 * configuration of the enactment.
 * 
 * @author Fedor Smirnov
 *
 */
public class EeConfiguration extends Opt4J {

	protected static final List<Class<? extends Module>> CONFIGURABLE_MODULE_TYPES = Arrays.asList(EeModule.class);

	/**
	 * Main method opening up the gui
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws FailureException{
		if (args.length > 0 && "-s".equalsIgnoreCase(args[0])) {
			final String[] argumentArray = new String[args.length - 1];
			System.arraycopy(args, 1, argumentArray, 0, argumentArray.length);
			EeStarter.main(argumentArray);
		} else {
			searchModules();
			final EeConfiguration configurator = new EeConfiguration();
			configurator.start(args);
		}
	}

	/**
	 * Searches for the modules to display in the GUI
	 */
	protected static void searchModules() {
		// I may adjust this class to find only the classes relevant for scheduling
		final ModuleAutoFinder finder = new ModuleAutoFinder();
		for (final Class<? extends Module> module : finder.getModules()) {
			if (includeModuleClassInGui(module)) {
				moduleList.add(module);
			}
		}
	}

	/**
	 * Returns {@code true} iff the given class is the child of one of the classes
	 * (or actually has the same class) in the list of configurable module classes.
	 * 
	 * @param clazz the class of the module which is being tested
	 * @return {@code true} iff the given class is the child of one of the classes
	 *         (or actually has the same class) in the list of configurable module
	 *         classes
	 */
	protected static boolean includeModuleClassInGui(final Class<? extends Module> clazz) {
		for (final Class<? extends Module> configurableModule : CONFIGURABLE_MODULE_TYPES) {
			if (configurableModule.isAssignableFrom(clazz)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Starts the configuration GUI with an (optionally) provided path to a config
	 * path. (The method describes what happens when you hit the "run" button in the
	 * GUI).
	 * 
	 * @param args the first config argument is treated as the path of the config
	 *             file
	 */
	@Override
	public void start(final String[] args) {
		String filename = null;
		if (args.length > 0) {
			filename = args[0];
		}
		main(EeTask.class, filename);
	}

	/**
	 * Determines the bindings made automatically in addition to the one made based
	 * on the module config.
	 */
	@Override
	public Module getModule(final Class<? extends Task> taskClass) {
		return binder -> {
			binder.bind(Task.class).to(taskClass);
			binder.bind(ModuleList.class).toInstance(moduleList);
		};
	}
}
