package gr.redefine.extras;

import com.google.ar.sceneform.Node;

import gr.redefine.adapters.MessageAdapter;

public class NodeAddapterWrapper {
    private Node node;
    private MessageAdapter mAdapter;

    public NodeAddapterWrapper(Node node, MessageAdapter mAdapter) {
        this.node = node;
        this.mAdapter = mAdapter;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public MessageAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(MessageAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }
}
