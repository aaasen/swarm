package co.devrandom.model.events.collision;

import co.devrandom.model.objects.Enemy;
import co.devrandom.model.objects.PhysicsObject;
import co.devrandom.model.objects.Projectile;

public class ProjectileEnemyContactHandler extends BaseContactHandler {

	public ProjectileEnemyContactHandler() {
		super(Projectile.class, Enemy.class);
	}

	@Override
	public void onCollsion(PhysicsObject a, PhysicsObject b) {
		Enemy target = (Enemy) (a instanceof Enemy ? a : b);
		Projectile bullet = (Projectile) (a instanceof Projectile ? a : b);
		
		bullet.getModel().remove(bullet);
		
		target.damage(1f);
	}
}
