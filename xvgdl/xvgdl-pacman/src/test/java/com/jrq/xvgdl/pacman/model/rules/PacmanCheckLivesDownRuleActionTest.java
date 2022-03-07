package com.jrq.xvgdl.pacman.model.rules;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.GameObject;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.GamePlayer;
import org.junit.Assert;
import org.junit.Test;

public class PacmanCheckLivesDownRuleActionTest {

    PacmanCheckLivesDownRuleAction pacmanCheckLivesDownRuleAction = new PacmanCheckLivesDownRuleAction();

    @Test
    public void testLivesNotDownWithJustTheLastItemInSamePosition() {
        GameContext mockContext = mockContextItemAndPlayerSamePosition();

        pacmanCheckLivesDownRuleAction.executeGameRuleAction(mockContext, mockContext.getCurrentGamePlayer(), null);

        Assert.assertEquals(1, mockContext.getCurrentGamePlayer().getLives().intValue());
    }

    @Test
    public void testLivesDownWithJustTheLastItemInDifferentPosition() {
        GameContext mockContext = mockContextItemAndPlayerDifferentPosition();

        pacmanCheckLivesDownRuleAction.executeGameRuleAction(mockContext, mockContext.getCurrentGamePlayer(), mockContext.getCurrentGamePlayer());

        Assert.assertEquals(0, mockContext.getCurrentGamePlayer().getLives().intValue());
    }

    @Test
    public void testLivesDownWithMoreThanOneLastItem() {
        GameContext mockContext = mockContextItemAndPlayerSamePosition();
        GameObject item = new GameObject();
        item.setObjectType(GameObjectType.ITEM);
        item.setPosition(2, 2, 2);
        mockContext.addObject(item);

        pacmanCheckLivesDownRuleAction.executeGameRuleAction(mockContext, mockContext.getCurrentGamePlayer(), mockContext.getCurrentGamePlayer());

        Assert.assertEquals(0, mockContext.getCurrentGamePlayer().getLives().intValue());
    }

    @Test
    public void testLivesNotDownWithZeroItem() {
        GameContext mockContext = mockContextNoItems();

        pacmanCheckLivesDownRuleAction.executeGameRuleAction(mockContext, mockContext.getCurrentGamePlayer(), mockContext.getCurrentGamePlayer());

        Assert.assertEquals(1, mockContext.getCurrentGamePlayer().getLives().intValue());
    }

    private GameContext mockContextItemAndPlayerSamePosition() {
        GameObject item = new GameObject();
        item.setObjectType(GameObjectType.ITEM);
        item.setPosition(1, 1, 1);

        GamePlayer player = new GamePlayer();
        player.setObjectType(GameObjectType.PLAYER);
        player.setPosition(1,1,1);
        player.setLives(1);
        player.setCurrentPlayer(true);

        GameContext gameContext = new GameContext();
        gameContext.addObject(item);
        gameContext.addObject(player);
        return gameContext;
    }

    private GameContext mockContextItemAndPlayerDifferentPosition() {
        GameObject item = new GameObject();
        item.setObjectType(GameObjectType.ITEM);
        item.setPosition(2, 2, 2);

        GamePlayer player = new GamePlayer();
        player.setObjectType(GameObjectType.PLAYER);
        player.setPosition(1,1,1);
        player.setLives(1);
        player.setCurrentPlayer(true);

        GameContext gameContext = new GameContext();
        gameContext.addObject(item);
        gameContext.addObject(player);
        return gameContext;
    }

    private GameContext mockContextNoItems() {
        GamePlayer player = new GamePlayer();
        player.setObjectType(GameObjectType.PLAYER);
        player.setPosition(1,1,1);
        player.setLives(1);
        player.setCurrentPlayer(true);

        GameContext gameContext = new GameContext();
        gameContext.addObject(player);
        return gameContext;
    }
}