package co.devrandom.model.events.collision;

import java.util.ArrayList;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import co.devrandom.model.objects.PhysicsObject;

public class ContactListener implements org.jbox2d.callbacks.ContactListener{
	private ArrayList<BaseContactHandler> listeners;
	
	public ContactListener() {
		listeners = new ArrayList<BaseContactHandler>();
	}
	
	@Override
	public void beginContact(Contact arg0) {
		for (BaseContactHandler listener : listeners) {
			Object a = arg0.getFixtureA().getBody().getUserData();
			Object b = arg0.getFixtureB().getBody().getUserData();
			
			if (listener.checkCollision(a.getClass(), b.getClass())) {
				listener.onCollsion((PhysicsObject) a, (PhysicsObject) b);
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
	
	public void addListener(BaseContactHandler listener) {
		listeners.add(listener);
	}
}
