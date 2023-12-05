package dictionary.gui.control;

public class TabARUWordDefChangeNode {
    private TabARUNode node;

    public TabARUWordDefChangeNode() {
        this.node = null;
    }

    public void append(String data) {
        node = new TabARUNode(data);
    }

    public void insertAfter(TabARUNode prevNode, String data) {
        if (prevNode == null) {
            return;
        }

        TabARUNode newNode = new TabARUNode(data);

        prevNode.next = newNode;

        newNode.prev = prevNode;

        node = newNode;
    }

    public void clear() {
        node = null;
    }

    public TabARUNode getNode() {
        return this.node;
    }
}
