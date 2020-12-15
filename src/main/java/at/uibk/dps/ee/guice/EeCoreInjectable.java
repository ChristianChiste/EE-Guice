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

	@Inject
	public EeCoreInjectable(InputDataProvider inputDataProvider, OutputDataHandler outputDataHandler,
			EnactableProvider enactableProvider, Set<EnactmentStateListener> stateListeners) {
		super(inputDataProvider, outputDataHandler, enactableProvider, stateListeners);
	}
}
