package com.jrq.xvgdl.pacman.model.object.ai;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.IGameObject;
import com.jrq.xvgdl.model.object.ai.IGameObjectAI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * Basic AI to move Pacman around the board
 *
 * 1. Get all items in the map
 * 2. Filter items to get a smaller map so we'll check the
 * best possible movement in a radius, not in the entire map
 * 3. Order those items and try to move to the first one
 *
 * @author jrquinones
 */
@Slf4j
public class PacmanAI implements IGameObjectAI {

    private static final int DEFAULT_RADIUS = 3;

    /**
     * This simple AI will try to move Pacman to the nearest item, but taking
     * into account the surrounding ghosts, penalising the movement if there's a ghost
     * near the item
     * @param gameContext Game Context
     * @param player
     */
    @Override
    public void applyAIonObject(GameContext gameContext, IGameObject player) {

        if (!player.getPosition().equals(player.getIntendedPosition())) {
            return;
        }

        PlayerNextMoveComparator playerNextMoveComparator = new PlayerNextMoveComparator(gameContext, DEFAULT_RADIUS);
        List<PlayerNextMoveComparator.ItemMovementSolution> solutions = playerNextMoveComparator.orderedSolutions();

        boolean moved = false;
        for (PlayerNextMoveComparator.ItemMovementSolution solution : solutions) {
            if (solution.getPath().size() >= 1) {
                Pair<Integer, Integer> nextNode = solution.getPath().get(1);
                player.moveTo(nextNode.getLeft(), nextNode.getRight(), player.getPosition().getZ());
                moved = true;
                break;
            }
        }
        if (!moved) {
            log.error("Player couldn't move!!!!");
        }
    }
}
