package pong.main;

public class BaseGameObject {

	protected static String $name = ">>uninitialized_BGO<<";

	protected Rectangle hBox;

	public BaseGameObject() {
		this(new Rectangle(0, 0, 0, 0));
	}

	public BaseGameObject(Rectangle hBox) {
		this.hBox = hBox;
	}

	public void render() {
	}

	public void update() {
	}

	public String getName() {
		return $name;
	}

	public Rectangle getRect() {
		return hBox;
	}

	public void collision(Collision col) {

	}


}
