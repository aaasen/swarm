package co.devrandom.model.events;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import co.devrandom.model.objects.Enemy;
import co.devrandom.model.objects.PhysicsObject;
import co.devrandom.model.objects.Player;

public class CollisionHandler implements ContactListener{
	//private class Collision
	
	@Override
	public void beginContact(Contact arg0) {
		PhysicsObject a = (PhysicsObject) arg0.getFixtureA().getBody().getUserData();
		PhysicsObject b = (PhysicsObject) arg0.getFixtureB().getBody().getUserData();
		
		if (a instanceof Enemy || b instanceof Enemy) {
			if (a instanceof Player || b instanceof Player) {
				Player player;
				Enemy enemy;
				
				if (a instanceof Player) {
					player = (Player) a;
					enemy = (Enemy) b;
				} else {
					player = (Player) b;
					enemy = (Enemy) a;
				}
				
				player.damage(0.1f);
				
			}
		}
	}

	@Override
	public void endContact(Contact arg0) {

	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		
	}

}
