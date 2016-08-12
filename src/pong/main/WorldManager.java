package pong.main;

import java.util.ArrayList;
import java.util.Random;

import pong.main.ObjectInstantiator.GameObjects;
import pong.main.game_objects.Ball;
import pong.main.game_objects.BaseGameObject;
import pong.main.game_objects.BaseScreenObject;
import pong.main.game_objects.OnlinePlayer;
import pong.main.game_objects.Player;
import pong.main.screens.Screen;
import pong.main.screens.ScreenText;
import pong.main.util.Couple;
import pong.main.util.KeyHandler;

public class WorldManager extends BaseScreenObject {

	private static WorldManager $instance = null;

	private ArrayList<BaseGameObject> wObjects = new ArrayList<BaseGameObject>();
	private ArrayList<BaseScreenObject> sObjects = new ArrayList<BaseScreenObject>();
	private KeyHandler keyHandler;

	// ================== SingleTon =======================
	private WorldManager(KeyHandler kHandler) {
		keyHandler = kHandler;
	}

	public static WorldManager getInstance(KeyHandler kHandler) {
		return ($instance == null ? $instance = new WorldManager(kHandler) : $instance);
	}

	// ========== Overridden Methods ========================
	@Override
	public void render() {
		if (wObjects.size() != 0)
			for (BaseGameObject bgO : wObjects)
				bgO.render();
		else if (sObjects.size() != 0)
			for (BaseScreenObject bsO : sObjects) {
				bsO.render();
			}
	}

	@Override
	public void update() {
		if (wObjects.size() != 0)
			for (BaseGameObject bgO : wObjects) {
				if (bgO instanceof Player)
					((Player) bgO).input(keyHandler.getAllPressedKeys());
				bgO.update();
			}
		else if (sObjects.size() != 0)
			for (BaseScreenObject bsO : sObjects) {
				if (bsO instanceof ScreenText)
					bsO.update();
			}
	}

	// ================ BaseGameObjects Functions ===================
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

	// ====================== Collision Handling =========================
	public void createCollision(BaseGameObject firstObj, BaseGameObject secObj,
			Couple<Couple<ArrayList<Object[]>>> data) {
		// >> The data we receive is divided to two and two: the first couple is
		// object one and two. and the second couple is functions and
		// variables.
		// TODO: Optimized
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

	// ==================== Input Handler ==================================
	public void setKeyHandler(KeyHandler newKHandler) {
		if (keyHandler == null)
			keyHandler = newKHandler;
	}

	public KeyHandler getKeyHandler() {
		return keyHandler;
	}

	// ===================================Online============================

	public void updateObjectLocation(String name, double posX, double posY) {
		BaseGameObject bgo = getObject(name);
		if (bgo instanceof Ball)
			((Ball) bgo).setPosition(posX, posY);
		else if (bgo instanceof OnlinePlayer)
			((OnlinePlayer) bgo).setPosition(posX, posY);
		else
			throw new RuntimeException("No object was found with the name: " + name);
	}

	// =================== World Managing ==================================

	@SuppressWarnings("unchecked")
	public void renderScreen(Screen newScreen) {
		Class<? extends Object> screenArrayListType = newScreen.getArrayListType();
		if (screenArrayListType == GameObjects.class) {
			sObjects.clear();
			wObjects.clear();
			createItems((ArrayList<GameObjects>) newScreen.getList());
		} else if (screenArrayListType == BaseScreenObject.class) {
			sObjects.clear();
			wObjects.clear();
			sObjects = new ArrayList<BaseScreenObject>();
		}
	}

	private void createItems(ArrayList<GameObjects> list) {
		byte side = (byte) new Random().nextInt(2);
		if (list.contains(GameObjects.ONLINE_PLAYER)) {
			
		} else
			for (GameObjects go : list) {
				if (go == GameObjects.BALL) {
					wObjects.add(ObjectInstantiator.CreateNewObject(go, -1));
				} else {
					wObjects.add(ObjectInstantiator.CreateNewObject(go, side));
					side = (byte) (side == 0 ? 1 : 0);
				}
			}
	}
}
