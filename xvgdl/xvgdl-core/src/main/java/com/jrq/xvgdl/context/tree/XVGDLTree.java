package com.jrq.xvgdl.context.tree;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class XVGDLTree {

    XVGDLNode root;

    public static XVGDLTree createXvgdlTree() {
        XVGDLTree theTree = new XVGDLTree();
        theTree.root = new XVGDLNode(XVGDLNodeType.ROOT);
        theTree.root.addChild(new XVGDLNode(XVGDLNodeType.STRUCTURE));
        theTree.root.addChild(new XVGDLNode(XVGDLNodeType.IA));
        theTree.root.addChild(new XVGDLNode(XVGDLNodeType.GRAPHICS));
        theTree.root.addChild(new XVGDLNode(XVGDLNodeType.MECHANICS));

        return theTree;
    }

    public void addXvgdlNode(XVGDLNodeType type, XVGDLNode node) {
        this.getRoot().getChild(type).addChild(node);
    }
}
