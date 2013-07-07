package co.devrandom.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import org.jbox2d.dynamics.World;

import co.devrandom.main.GameState;
import co.devrandom.model.events.TimedEvent;
import co.devrandom.model.load.LevelLoader;
import co.devrandom.model.objects.PhysicsObject;
import co.devrandom.model.objects.Player;

public class Model implements Runnable {
	private static final int SLEEP_TIME = 10;

	private long elapsedTime;
	private long lastFrame;
	private List<PhysicsObject> physicsObjects;
	private World world;
	private PriorityBlockingQueue<TimedEvent> events;
	private Player player;

	public Model() {
		elapsedTime = 0l;
		lastFrame = System.currentTimeMillis();
		physicsObjects = Collections.synchronizedList(new ArrayList<PhysicsObject>());
		world = new World(GameState.DEFAULT_GRAVITY);
		events = new PriorityBlockingQueue<TimedEvent>();
	}

	public void run() {
		LevelLoader loader = new LevelLoader(this, "another_test.svg");
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
					currentEvent.onTrigger();
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

	public void addPhysicsObject(PhysicsObject object) {
		this.physicsObjects.add(object);
	}

	public void removePhysicsObject(PhysicsObject object) {
		this.physicsObjects.remove(object);
		world.destroyBody(object.getBody());
	}

	public void addTimedEvent(TimedEvent event) {
		this.events.add(event);
	}

	public long getElapsedTime() {
		return elapsedTime;
	}
}
