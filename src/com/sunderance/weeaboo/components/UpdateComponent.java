/**
 * 
 */
package com.sunderance.weeaboo.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Component with an update method
 * 
 * @author Robert Berry
 */
abstract public class UpdateComponent extends Component {
	abstract public void update(GameContainer gc, StateBasedGame game, 
			int delta);
}
