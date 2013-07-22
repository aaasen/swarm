package co.devrandom.model.events.collision;

import co.devrandom.model.objects.PhysicsObject;

public interface ContactHandler {
	
	/**
	 * Called when a collision occurs that passes checkCollision
	 * 
	 * @param a
	 * @param b
	 */
	public void onCollsion(PhysicsObject a, PhysicsObject b);

	/**
	 * 
	 * 
	 * @param a The class of the first body involved
	 * @param b The class of the second body involved
	 * @return true if handler cares about the objects involved in the collision
	 */
	public boolean checkCollision(Class<?> a, Class<?> b);
	
}
