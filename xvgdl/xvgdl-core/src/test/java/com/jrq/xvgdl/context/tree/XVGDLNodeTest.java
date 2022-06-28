package com.jrq.xvgdl.context.tree;

import org.junit.Test;

import static org.junit.Assert.*;

public class XVGDLNodeTest {

    @Test
    public void createXvgdlNode() {
        XVGDLNode node = new XVGDLNode(XVGDLNodeType.GRAPHICS);

        assertNotNull(node);
        assertNotNull(node.getChildren());
    }

    @Test
    public void testIsRoot() {
        XVGDLNode node = new XVGDLNode(XVGDLNodeType.GRAPHICS);

        assertNotNull(node);
        assertTrue(node.isRoot());
    }

    @Test
    public void testIsNotRoot() {
        XVGDLNode node = new XVGDLNode(XVGDLNodeType.GRAPHICS);
        node.setParent(new XVGDLNode(XVGDLNodeType.ROOT));

        assertNotNull(node);
        assertFalse(node.isRoot());
    }

    @Test
    public void testIsLeave() {
        XVGDLNode node = new XVGDLNode(XVGDLNodeType.GRAPHICS);

        assertNotNull(node);
        assertTrue(node.isLeave());
    }

    @Test
    public void testIsNotLeave() {
        XVGDLNode node = new XVGDLNode(XVGDLNodeType.GRAPHICS);
        node.addChild(new XVGDLNode(XVGDLNodeType.VALUE));

        assertNotNull(node);
        assertFalse(node.isLeave());
    }

    @Test
    public void testGetChildOfType() {
        XVGDLNode node = new XVGDLNode(XVGDLNodeType.GRAPHICS);
        node.addChild(new XVGDLNode(XVGDLNodeType.VALUE));

        assertNotNull(node);
        assertNotNull(node.getChild(XVGDLNodeType.VALUE));
    }

    @Test
    public void testNotExistingChildOfType() {
        XVGDLNode node = new XVGDLNode(XVGDLNodeType.GRAPHICS);
        node.addChild(new XVGDLNode(XVGDLNodeType.VALUE));

        assertNotNull(node);
        assertNull(node.getChild(XVGDLNodeType.IA));
    }


}