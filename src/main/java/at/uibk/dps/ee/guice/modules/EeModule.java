package at.uibk.dps.ee.guice.modules;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import org.opt4j.core.IndividualStateListener;
import org.opt4j.core.config.Property;
import org.opt4j.core.config.PropertyModule;
import org.opt4j.core.optimizer.OptimizerIterationListener;
import org.opt4j.core.optimizer.OptimizerStateListener;
import org.opt4j.core.start.Opt4JModule;

import com.google.inject.Binder;
import com.google.inject.BindingAnnotation;
import com.google.inject.ConfigurationException;
import com.google.inject.binder.ConstantBindingBuilder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.spi.Message;

import at.uibk.dps.ee.core.ControlStateListener;
import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.enactable.EnactmentStateListener;


/**
 * Parent class of all modules used for the configuration of the enactment
 * process.
 * 
 * @author Fedor Smirnov
 *
 */
public abstract class EeModule extends Opt4JModule {

	protected static final String Opt4JExceptionMessage = "This method is from Opt4J and must not be called by a module of the enactment engine. (Sorry for this ugly hack).";

	/**
	 * Adds a {@link EnactableStateListener} to be triggered during the enactment.
	 * 
	 * @param enactableStateListener the enactable state listener
	 */
	public void addEnactableStateListener(final Class<? extends EnactableStateListener> enactableStateListener) {
		addEnactableStateListener(binder(), enactableStateListener);
	}

	/**
	 * Adds a {@link EnactableStateListener} to be triggered during the enactment.
	 * 
	 * @param binder                 the binder
	 * @param enactableStateListener the enactable state listener
	 */
	public static void addEnactableStateListener(Binder binder,
			final Class<? extends EnactableStateListener> enactableStateListener) {
		Multibinder<EnactableStateListener> multiBinder = Multibinder.newSetBinder(binder,
				EnactableStateListener.class);
		multiBinder.addBinding().to(enactableStateListener);
	}

	/**
	 * Adds a {@link EnactmentStateListener} to be triggered throughout the
	 * enactment process.
	 * 
	 * @param stateListener the state listener to add
	 */
	public void addEnactmentStateListener(final Class<? extends EnactmentStateListener> stateListener) {
		addEnactmentStateListener(binder(), stateListener);
	}

	/**
	 * Adds a {@link EnactmentStateListener} to be triggered throughout the
	 * enactment process.
	 * 
	 * @param binder        the binder
	 * @param stateListener stateListener the state listener to add
	 */
	public static void addEnactmentStateListener(Binder binder,
			final Class<? extends EnactmentStateListener> stateListener) {
		Multibinder<EnactmentStateListener> multiBinder = Multibinder.newSetBinder(binder,
				EnactmentStateListener.class);
		multiBinder.addBinding().to(stateListener);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void configure() {
		// this partial copying from Opt4JModule is quite ugly,
		// but I want the annotation stuff
		// while I don't want the listener stuff :(

		/**
		 * Configure injected constants.
		 */
		PropertyModule module = new PropertyModule(this);
		for (Property property : module.getProperties()) {
			for (Annotation annotation : property.getAnnotations()) {
				if (annotation.annotationType().getAnnotation(BindingAnnotation.class) != null) {
					Class<?> type = property.getType();
					Object value = property.getValue();

					ConstantBindingBuilder builder = bindConstant(annotation);
					if (type.equals(Integer.TYPE)) {
						builder.to((Integer) value);
					} else if (type.equals(Long.TYPE)) {
						builder.to((Long) value);
					} else if (type.equals(Double.TYPE)) {
						builder.to((Double) value);
					} else if (type.equals(Float.TYPE)) {
						builder.to((Float) value);
					} else if (type.equals(Byte.TYPE)) {
						builder.to((Byte) value);
					} else if (type.equals(Short.TYPE)) {
						builder.to((Short) value);
					} else if (type.equals(Boolean.TYPE)) {
						builder.to((Boolean) value);
					} else if (type.equals(Character.TYPE)) {
						builder.to((Character) value);
					} else if (type.equals(String.class)) {
						builder.to((String) value);
					} else if (type.equals(Class.class)) {
						builder.to((Class<?>) value);
					} else if (value instanceof Enum<?>) {
						builder.to((Enum) value);
					} else {
						String message = "Constant type not bindable: " + type + " of field " + property.getName()
								+ " in module " + this.getClass().getName();
						throw new ConfigurationException(Arrays.asList(new Message(message)));
					}
				}
			}
		}

		multi(EnactmentStateListener.class);
		multi(EnactableStateListener.class);
		multi(ControlStateListener.class);
		
		config();
	}

	@Override
	public void addIndividualStateListener(Class<? extends IndividualStateListener> listener) {
		throwOpt4JException();
	}

	@Override
	public void addOptimizerIterationListener(Class<? extends OptimizerIterationListener> listener) {
		throwOpt4JException();
	}

	@Override
	public void addOptimizerStateListener(Class<? extends OptimizerStateListener> listener) {
		throwOpt4JException();
	}

	/**
	 * Throws the exception for the methods from within Opt4J.
	 */
	protected final void throwOpt4JException() {
		throw new IllegalStateException(Opt4JExceptionMessage);
	}

}
