package co.devrandom.model.events.collision;

public abstract class BaseContactHandler implements ContactHandler {
	private Class<?> a;
	private Class<?> b;

	public BaseContactHandler(Class<?> a, Class<?> b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public boolean checkCollision(Class<?> a, Class<?> b) {
		return (a.getCanonicalName().equals(this.a.getCanonicalName()) ||
				b.getCanonicalName().equals(this.a.getCanonicalName())) &&
				(a.getCanonicalName().equals(this.b.getCanonicalName()) ||
						b.getCanonicalName().equals(this.b.getCanonicalName()));

	}
}
