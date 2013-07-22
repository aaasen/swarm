package co.devrandom.model.objects;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import co.devrandom.assets.textures.TextureAttributes;
import co.devrandom.assets.textures.TextureList;
import co.devrandom.model.Model;
import co.devrandom.model.objects.util.BodyDefBuilder;
import co.devrandom.model.objects.util.FixtureDefBuilder;
import co.devrandom.util.Vector;

public class Enemy extends PhysicsObject implements Destructible {
	private static final float DENSITY = 0.1f;
	private static final float FRICTION = 1f;
	private static final float RESTITUTION = 1f;
	private static final float GRAVITY = 1f;
	
	private float health;
	
	private static final BodyDef BD = new BodyDefBuilder()
		.type(BodyType.DYNAMIC)
		.gravityScale(GRAVITY)
		.build();
	
	private final static FixtureDefBuilder FD = new FixtureDefBuilder()
		.density(DENSITY)
		.friction(FRICTION)
		.restitution(RESTITUTION);
	
	public Enemy(Model model, Vector position, Vector size) {
		super(model, BodyDefBuilder.setPosition(BD, position),
				FD.shape(PhysicsObject.makeBoxShape(size)).build(),
				 new TextureAttributes(TextureList.ENEMY));
		
		health = size.x * size.y;
	}
	
	@Override
	public void destroy() {
		this.getModel().remove(this);
	}
	
	@Override
	public void onDestroy() {
		System.out.println("Enemy killed");
	}
	
	@Override
	public void damage(float amount) {
		health -= amount;
		
		if (health <= 0) {
			this.destroy();
			this.onDestroy();
		}
	}
}
