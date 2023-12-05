package dictionary.gui.control;

public class TabARUWordDefChangeHistory {
    private TabARUNode head;
    private TabARUNode tail;

    public TabARUWordDefChangeHistory() {
        this.head = null;
        this.tail = null;
    }

    public void append(String data) {
        TabARUNode newNode = new TabARUNode(data);
        head = newNode;
        tail = newNode;
    }

    public void insertAfter(TabARUNode prevNode, String data) {
        if (prevNode == null) {
            return;
        }

        TabARUNode newNode = new TabARUNode(data);

        prevNode.next = newNode;

        newNode.prev = prevNode;

        tail = newNode;
    }

    public void clear() {
        head = null;
        tail = null;
    }

    public TabARUNode getTail() {
        return this.tail;
    }
}
