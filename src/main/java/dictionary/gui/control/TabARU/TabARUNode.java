package dictionary.gui.control.TabARU;

public class TabARUNode {
        String data;
        TabARUNode prev;
        TabARUNode next;

        public TabARUNode(String data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
}
