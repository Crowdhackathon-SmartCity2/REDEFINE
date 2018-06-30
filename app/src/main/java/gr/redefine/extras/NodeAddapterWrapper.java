package gr.redefine.extras;

import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.ViewRenderable;

import gr.redefine.adapters.MessageAdapter;
import uk.co.appoly.arcorelocation.LocationMarker;

public class NodeAddapterWrapper {
    private Node node;
    private MessageAdapter mAdapter;
    private ViewRenderable viewRenderable;
    private LocationMarker locationMarker;

    public NodeAddapterWrapper(Node node, ViewRenderable viewRenderable, MessageAdapter mAdapter) {
        this.node = node;
        this.mAdapter = mAdapter;
        this.viewRenderable = viewRenderable;
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

    public ViewRenderable getViewRenderable() {
        return viewRenderable;
    }

    public void setViewRenderable(ViewRenderable viewRenderable) {
        this.viewRenderable = viewRenderable;
    }

    public LocationMarker getLocationMarker() {
        return locationMarker;
    }

    public void setLocationMarker(LocationMarker locationMarker) {
        this.locationMarker = locationMarker;
    }
}
