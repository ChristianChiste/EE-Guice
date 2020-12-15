package at.uibk.dps.ee.guice;

import java.util.Set;

import com.google.inject.Inject;

import at.uibk.dps.ee.core.EeCore;
import at.uibk.dps.ee.core.EnactableProvider;
import at.uibk.dps.ee.core.InputDataProvider;
import at.uibk.dps.ee.core.OutputDataHandler;
import at.uibk.dps.ee.core.enactable.EnactmentStateListener;

/**
 * Class definition with the inject annotation (Apart from that identical with
 * {@link EeCore}).
 * 
 * @author Fedor Smirnov
 *
 */
public class EeCoreInjectable extends EeCore {

	/**
	 * This is the constructor which will be used during the dynamic dependency
	 * injection.
	 * 
	 * @param inputDataProvider the component providing the input processing data
	 * @param outputDataHandler the component handling the data produced when
	 *                          running the workflow
	 * @param enactableProvider the component providing the root enactable which is
	 *                          used to run the enactment
	 * @param stateListeners    the listeners which react to transitions between
	 *                          different states of the enactment
	 */
	@Inject
	public EeCoreInjectable(final InputDataProvider inputDataProvider, final OutputDataHandler outputDataHandler,
			final EnactableProvider enactableProvider, final Set<EnactmentStateListener> stateListeners) {
		super(inputDataProvider, outputDataHandler, enactableProvider, stateListeners);
	}
}
