package co.devrandom.model.events.collision;

import co.devrandom.model.objects.Enemy;
import co.devrandom.model.objects.PhysicsObject;
import co.devrandom.model.objects.Player;

public class EnemyPlayerContactHandler extends BaseContactHandler {

	public EnemyPlayerContactHandler() {
		super(Player.class, Enemy.class);
	}

	@Override
	public void onCollsion(PhysicsObject a, PhysicsObject b) {
		Player player = (Player) (a instanceof Player ? a : b);
		player.damage(0.01f);
		
		System.out.println("enemy and player collided");
	}
}
