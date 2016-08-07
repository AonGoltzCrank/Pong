package pong.main;

import java.util.ArrayList;

public class CollisionsManager extends BaseGameObject {

	private static CollisionsManager $instance = null;

	private WorldManager wManager;

	private ArrayList<BaseGameObject> bgO = new ArrayList<>();

	public static CollisionsManager getInstance(WorldManager manager) {
		return ($instance == null ? $instance = new CollisionsManager(manager) : $instance);
	}

	private CollisionsManager(WorldManager manager) {
		if (manager == null)
			throw new RuntimeException("WorldManager passed into CollisionsManager was not initialized!");
		wManager = manager;
	}

	@Override
	public void update() {
		bgO = wManager.getWorldObjects(bgO);
		BaseGameObject ball = findBGO("Ball");
		BaseGameObject player = findBGO("Player");
		byte side = ((Player) player).getSide();
		BaseGameObject ai = findBGO("AI");
		BaseGameObject onlinePlayer = findBGO("OnlinePlayer");
		// wManager.
		Collision col;
		// ===========================Ball====================================
		if (ball.hBox.touchingEdges_Num(wManager.hBox, Rectangle.UP, false)
				|| wManager.outsideWorldPartially(ball, wManager.TOP_BOT)) {
			col = new Collision(ball, wManager);
			col.addInstruction((byte) 0, Collision.FUNCTION, new Object[] { "hitObject", false, 0.0 });
			wManager.createCollision(ball, wManager, col.getCompleteObject());
		}
		if (ball.hBox.touchingEdges_Num(wManager.hBox, Rectangle.DOWN, false)
				|| wManager.outsideWorldPartially(ball, wManager.TOP_BOT)) {
			col = new Collision(ball, wManager);
			col.addInstruction((byte) 0, Collision.FUNCTION, new Object[] { "hitObject", false, 0.0 });
			wManager.createCollision(ball, wManager, col.getCompleteObject());
		}
		// ==========================Player==================================
		if (player.hBox.touchingEdges_Num(wManager.hBox, Rectangle.UP, false)) {
			col = new Collision(player, wManager);
			col.addInstruction((byte) 0, Collision.VARIABLE, new Object[] { "movAllowedUp", false });
			wManager.createCollision(player, wManager, col.getCompleteObject());
		} else if (player.hBox.touchingEdges_Num(wManager.hBox, Rectangle.DOWN, false)) {
			col = new Collision(player, wManager);
			col.addInstruction((byte) 0, Collision.VARIABLE, new Object[] { "movAllowedDown", false });
			wManager.createCollision(player, wManager, col.getCompleteObject());
		}
		if (player.hBox.touchingEdges_Num(ball.hBox, (side == 0 ? Rectangle.RIGHT : Rectangle.LEFT), true)
				|| player.hBox.inEdges(ball.hBox)) {
			col = new Collision(player, ball);
			col.addInstruction((byte) 1, Collision.FUNCTION,
					new Object[] { "hitObject", true, player.hBox.getDistanceFromCenterHit(ball.hBox) });
			wManager.createCollision(player, ball, col.getCompleteObject());
		}
		if (player.hBox.touchingEdges_Num(ball.hBox, Rectangle.UP, true)) {
			col = new Collision(player, ball);
			col.addInstruction((byte) 1, Collision.FUNCTION,
					new Object[] { "hit_PlayerOrAI_TopOrBottom", Rectangle.UP });
			wManager.createCollision(player, ball, col.getCompleteObject());
		} else if (player.hBox.touchingEdges_Num(ball.hBox, Rectangle.DOWN, true)) {
			col = new Collision(player, ball);
			col.addInstruction((byte) 1, Collision.FUNCTION,
					new Object[] { "hit_PlayerOrAI_TopOrBottom", Rectangle.DOWN });
			wManager.createCollision(player, ball, col.getCompleteObject());
		}
		// ==============================AI=====================================
		if (!Main.isOnline) {
			System.out.println("Up");
			if (ai.hBox.touchingEdges_Num(wManager.hBox, Rectangle.UP, false)) {
				col = new Collision(ai, wManager);
				col.addInstruction((byte) 0, Collision.VARIABLE, new Object[] { "movAllowedUp", false });
				wManager.createCollision(ai, wManager, col.getCompleteObject());
			} else if (ai.hBox.touchingEdges_Num(wManager.hBox, Rectangle.DOWN, false)) {
				col = new Collision(ai, wManager);
				col.addInstruction((byte) 0, Collision.VARIABLE, new Object[] { "movAllowedDown", false });
				wManager.createCollision(ai, wManager, col.getCompleteObject());
			}
			if (ai.hBox.touchingEdges_Num(ball.hBox, (side == 0 ? Rectangle.LEFT : Rectangle.RIGHT), true)
					|| ai.hBox.inEdges(ball.hBox)) {
				col = new Collision(ai, ball);
				col.addInstruction((byte) 1, Collision.FUNCTION,
						new Object[] { "hitObject", true, ai.hBox.getDistanceFromCenterHit(ball.hBox) });
				wManager.createCollision(ai, ball, col.getCompleteObject());
			}
			if (ai.hBox.touchingEdges_Num(ball.hBox, Rectangle.UP, true)) {
				col = new Collision(ai, ball);
				col.addInstruction((byte) 1, Collision.FUNCTION,
						new Object[] { "hit_PlayerOrAI_TopOrBottom", Rectangle.UP });
				wManager.createCollision(ai, ball, col.getCompleteObject());
			} else if (ai.hBox.touchingEdges_Num(ball.hBox, Rectangle.DOWN, true)) {
				col = new Collision(ai, ball);
				col.addInstruction((byte) 1, Collision.FUNCTION,
						new Object[] { "hit_PlayerOrAI_TopOrBottom", Rectangle.DOWN });
				wManager.createCollision(ai, ball, col.getCompleteObject());
			}
		}
		// =========================Online Player=============================
		if (Main.isOnline) {
			if (onlinePlayer.hBox.touchingEdges_Num(ball.hBox, (side == 0 ? Rectangle.LEFT : Rectangle.RIGHT), true)
					|| onlinePlayer.hBox.inEdges(ball.hBox)) {
				col = new Collision(onlinePlayer, ball);
				col.addInstruction((byte) 1, Collision.FUNCTION,
						new Object[] { "hitObject", true, onlinePlayer.hBox.getDistanceFromCenterHit(ball.hBox) });
				wManager.createCollision(onlinePlayer, ball, col.getCompleteObject());
			}
			if (onlinePlayer.hBox.touchingEdges_Num(ball.hBox, Rectangle.UP, true)) {
				col = new Collision(onlinePlayer, ball);
				col.addInstruction((byte) 1, Collision.FUNCTION,
						new Object[] { "hit_PlayerOrAI_TopOrBottom", Rectangle.UP });
				wManager.createCollision(onlinePlayer, ball, col.getCompleteObject());
			} else if (onlinePlayer.hBox.touchingEdges_Num(ball.hBox, Rectangle.DOWN, true)) {
				col = new Collision(onlinePlayer, ball);
				col.addInstruction((byte) 1, Collision.FUNCTION,
						new Object[] { "hit_PlayerOrAI_TopOrBottom", Rectangle.DOWN });
				wManager.createCollision(onlinePlayer, ball, col.getCompleteObject());
			}
		}
		// =========================World Manager==============================
		if (!wManager.hBox.containing(ball.hBox)) {
		}
	}

	private BaseGameObject findBGO(String name) {
		for (BaseGameObject bgo : bgO) {
			if (bgo.getName().equalsIgnoreCase(name))
				return bgo;
		}
		return null;
	}
}
