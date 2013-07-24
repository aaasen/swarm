package co.devrandom.model.objects;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import co.devrandom.assets.textures.TextureAttributes;
import co.devrandom.assets.textures.TextureList;
import co.devrandom.model.Model;
import co.devrandom.model.objects.util.BodyDefBuilder;
import co.devrandom.model.objects.util.FixtureDefBuilder;
import co.devrandom.util.Vector;

public class Projectile extends PhysicsObject {
	private static final float DENSITY = 0.1f;
	private static final float FRICTION = 1f;
	private static final float RESTITUTION = 1f;
	private static final float GRAVITY = 1f;
	
	private static final BodyDef BD = new BodyDefBuilder()
		.type(BodyType.DYNAMIC)
		.gravityScale(GRAVITY)
		.bullet(true)
		.build();
	
	private final static FixtureDefBuilder FD = new FixtureDefBuilder()
		.density(DENSITY)
		.friction(FRICTION)
		.restitution(RESTITUTION);
	
	private Vector force;
	
	public Projectile(Model model, Vector position, Vector size, Vector force) {
		super(model,
				BodyDefBuilder.setPosition(BD, position),
				FD.shape(PhysicsObject.makeBoxShape(size)).build(),
				 new TextureAttributes(TextureList.PLAYER));
		
		this.force = force;
	}
	
	void onInit() {
		this.getBody().applyForceToCenter(force.toVec2());	
	}
}
