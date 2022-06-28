package com.jrq.xvgdl.context.tree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class XVGDLNode {

    private final XVGDLNodeType type;
    private Object value;
    private XVGDLNode parent;
    private List<XVGDLNode> children = new ArrayList<>();

    public void addChild(XVGDLNode child) {
        children.add(child);
    }

    public void removeChild(XVGDLNode child) {
        children.remove(child);
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean isLeave() {
        return this.children.isEmpty();
    }

    public XVGDLNode getChild(XVGDLNodeType type) {
        return this.children.stream().filter(n -> type.equals(n.type)).findFirst().orElse(null);
    }
}
