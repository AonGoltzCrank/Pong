package pong.main;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class WorldManager extends BaseGameObject {

	private static WorldManager $instance = null;

	private float x = 395;
	private float y = 0;
	private final float width = 10;
	private final float height = 58;

	private final int nO_Lines = 10;

	public final byte TOP_BOT = 0;
	public final byte RIGHT_LEFT = 1;

	private ArrayList<BaseGameObject> wObjects = new ArrayList<BaseGameObject>();

	private KeyHandler keyHandler;

	private WorldManager(KeyHandler kHandler) {
		keyHandler = kHandler;
		hBox = new Rectangle(0, 0, 800, 600);
	}

	public static WorldManager getInstance(KeyHandler kHandler) {
		return ($instance == null ? $instance = new WorldManager(kHandler) : $instance);
	}

	@Override
	public void render() {
		for (int i = 0; i < nO_Lines; i++) {
			GL11.glBegin(GL11.GL_QUADS);
			{
				GL11.glColor3f(.5f, .5f, .5f);
				GL11.glVertex3f(x, y + (height * i + 20), 0);
				GL11.glVertex3f(x, y + (height * i + height), 0);
				GL11.glVertex3f(x + width, y + (height * i + height), 0);
				GL11.glVertex3f(x + width, y + (height * i + 20), 0);
			}
			GL11.glEnd();
		}
		for (BaseGameObject bgO : wObjects)
			bgO.render();
	}

	@Override
	public void update() {
		for (BaseGameObject bgO : wObjects) {
			if (bgO instanceof Player)
				((Player) bgO).input(keyHandler.getAllPressedKeys());
			bgO.update();
		}
	}

	/**
	 * Adds a new BaseGameObject to the world.<br>
	 * Only way to get it rendered.
	 * 
	 * @param obj
	 */
	public void addObject(BaseGameObject obj) {
		wObjects.add(obj);
	}

	public BaseGameObject getObject(String name) {
		for (BaseGameObject bgo : wObjects) {
			if (bgo.getName().equalsIgnoreCase(name))
				return bgo;
		}
		return null;
	}

	public ArrayList<BaseGameObject> getWorldObjects(ArrayList<BaseGameObject> bgO) {
		return (bgO.containsAll(wObjects) ? bgO : wObjects);
	}

	public void createCollision(BaseGameObject firstObj, BaseGameObject secObj,
			Couple<Couple<ArrayList<Object[]>>> data) {
		// >> The data we receive is divided to two and two: the first couple is
		// object one and two. and the second couple is functions and
		// variables.
		Collision firstCol = new Collision(firstObj, secObj);
		Collision secCol = new Collision(firstObj, secObj);
		Couple<ArrayList<Object[]>> firstColInstructSet = data.get(0);
		Couple<ArrayList<Object[]>> secColInstructSet = data.get(1);
		ArrayList<Object[]> firstCol_FUNC_InstructData = firstColInstructSet.get(0);
		ArrayList<Object[]> firstCol_VAR_InstructData = firstColInstructSet.get(1);
		ArrayList<Object[]> secCol_FUNC_InstructData = secColInstructSet.get(0);
		ArrayList<Object[]> secCol_VAR_InstructData = secColInstructSet.get(1);
		for (Object[] objArr : firstCol_FUNC_InstructData)
			firstCol.addInstruction((byte) 0, Collision.FUNCTION, objArr);
		for (Object[] objArr : firstCol_VAR_InstructData)
			firstCol.addInstruction((byte) 0, Collision.VARIABLE, objArr);
		for (Object[] objArr : secCol_FUNC_InstructData)
			secCol.addInstruction((byte) 1, Collision.FUNCTION, objArr);
		for (Object[] objArr : secCol_VAR_InstructData)
			secCol.addInstruction((byte) 1, Collision.VARIABLE, objArr);
		firstObj.collision(firstCol);
		secObj.collision(secCol);
	}

	public void setKeyHandler(KeyHandler newKHandler) {
		if (keyHandler == null)
			keyHandler = newKHandler;
	}

	public KeyHandler getKeyHandler() {
		return keyHandler;
	}

	public boolean outsideWorldPartially(BaseGameObject obj, byte dir) {
		Rectangle rect = obj.hBox;
		if (dir != TOP_BOT && dir != RIGHT_LEFT)
			throw new IllegalArgumentException("Must be 0 [Top or Bottom], or 1 [Right or Left].");
		// hBox.
		double objHigh = (dir == TOP_BOT ? rect.getHighestY() : rect.getFurthestX()),
				objLow = (dir == TOP_BOT ? rect.getLowestY() : rect.getClosestX());
		return (objHigh > (dir == TOP_BOT ? hBox.getHighestY() : hBox.getFurthestX())
				|| objLow < (dir == TOP_BOT ? hBox.getLowestY() : hBox.getClosestX()));
	}

	// ===================================Online===================

	public void updateObjectLocation(String name, double posX, double posY) {
		BaseGameObject bgo = getObject(name);
		if (bgo instanceof Ball)
			((Ball) bgo).setPosition(posX, posY);
		else if (bgo instanceof OnlinePlayer)
			((OnlinePlayer) bgo).setPosition(posX, posY);
		else
			throw new RuntimeException("No object was found with the name: " + name);
	}
}
