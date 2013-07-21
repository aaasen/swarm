package co.devrandom.model.objects;

public interface Destructible {

	public float getHealth();
	
	public boolean isDead();
	
	/**
	 * Damages the Destructible and returns whether or not it is now dead
	 * 
	 * @param amount
	 * @return
	 */
	public boolean damage(float amount);
	
}
