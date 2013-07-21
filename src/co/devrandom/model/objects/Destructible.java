package co.devrandom.model.objects;

public interface Destructible {
	
	/**
	 * Damages the Destructible and returns whether or not it is now dead
	 * 
	 * @param amount
	 * @return
	 */
	public void damage(float amount);
	
	public void destroy();
	
	public void onDestroy();
	
}
