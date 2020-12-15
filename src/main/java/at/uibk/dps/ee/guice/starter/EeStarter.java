package at.uibk.dps.ee.guice.starter;

import org.opt4j.core.config.Starter;

/**
 * Class used to start the enactment without the activation of the interactive
 * configuration GUI.
 * 
 * @author Fedor Smirnov
 *
 */
public class EeStarter extends Starter {

	public static void main(String[] args) throws Exception{
		Starter starter = new EeStarter();
		starter.execute(args);
	}
	
	@Override
	public void execute(String[] args) throws Exception {
		addPlugins();
		execute(EeTask.class, args);
	}

}
