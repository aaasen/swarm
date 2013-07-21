package co.devrandom.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import co.devrandom.assets.level.LevelLoader;
import co.devrandom.main.GameState;
import co.devrandom.model.events.TimedEvent;
import co.devrandom.model.events.collision.CollisionListener;
import co.devrandom.model.objects.Enemy;
import co.devrandom.model.objects.PhysicsObject;
import co.devrandom.model.objects.Player;

public class Model implements Runnable {
	private static final int SLEEP_TIME = 10;

	private long elapsedTime;
	private long lastFrame;
	private List<PhysicsObject> physicsObjects;
	private HashMap<Body, PhysicsObject> bodyMap;
	private World world;
	private PriorityBlockingQueue<TimedEvent> events;
	private Player player;
	private CollisionListener collisionHandler;

	public Model() {
		elapsedTime = 0l;
		lastFrame = System.currentTimeMillis();
		physicsObjects = Collections.synchronizedList(new ArrayList<PhysicsObject>());
		bodyMap = new HashMap<Body, PhysicsObject>();
		world = new World(GameState.DEFAULT_GRAVITY);
		events = new PriorityBlockingQueue<TimedEvent>();
		collisionHandler = new CollisionListener();
		world.setContactListener(collisionHandler);
	}

	public void run() {
		LevelLoader loader = new LevelLoader(this, "block-field.svg");
		loader.loadObjects();
		
		while (true) {
			lastFrame = System.currentTimeMillis();

			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				System.err.println("Failed to sleep in Model loop. Error:");
				e.printStackTrace();
			}
			if (GameState.isModelRunning()) {
				checkEvents();
				try {
					for (PhysicsObject object : physicsObjects) {
						if (object instanceof Enemy) {
							((Enemy) object).goTo(player.getPosition());
						}
					}
					
					world.step(GameState.TIME_STEP, GameState.VELOCITY_ITERATIONS,
							GameState.POSITION_ITERATIONS);
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}

				elapsedTime += System.currentTimeMillis() - lastFrame;
			}
		}
	}

	public void checkEvents() {
		synchronized (events) {
			Iterator<TimedEvent> i = events.iterator();

			while (i.hasNext()) {
				TimedEvent currentEvent = (TimedEvent) i.next();

				if (currentEvent.isTriggered()) {
					currentEvent.trigger();
					i.remove();
				} else {
					break;
				}
			}
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	public World getWorld() {
		return world;
	}

	public ArrayList<PhysicsObject> getGameObjects() {
		return new ArrayList<PhysicsObject>(physicsObjects);
	}
	
	public PhysicsObject getPhysicsObject(Body b) {
		return bodyMap.get(b);
	}

	public void addPhysicsObject(PhysicsObject object) {
		this.physicsObjects.add(object);
		
		object.getBody().setUserData(object);
		this.bodyMap.put(object.getBody(), object);
	}

	public void removePhysicsObject(PhysicsObject object) {
		this.physicsObjects.remove(object);
		world.destroyBody(object.getBody());
		bodyMap.remove(object.getBody());
	}

	public void addTimedEvent(TimedEvent event) {
		this.events.add(event);
	}

	public long getElapsedTime() {
		return elapsedTime;
	}
}
