
package gr.redefine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Frame;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.UnavailableException;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import gr.redefine.adapters.MessageAdapter;
import gr.redefine.extras.Location;
import gr.redefine.extras.NodeAddapterWrapper;
import uk.co.appoly.arcorelocation.LocationMarker;
import uk.co.appoly.arcorelocation.LocationScene;
import uk.co.appoly.arcorelocation.utils.ARLocationPermissionHelper;

/**
 * This is a simple example that shows how to create an augmented reality (AR) application using the
 * ARCore and Sceneform APIs.
 */
public class LocationActivity extends AppCompatActivity {
    private static final String TAG = "activity";
    private boolean installRequested;

    private Snackbar loadingMessageSnackbar = null;

    private ArSceneView arSceneView;

    // Renderables for this example


    // Our ARCore-Location scene
    private LocationScene locationScene;

    private Map<Location, NodeAddapterWrapper> locationToWrapperMap;


    private MessageAdapter mAdapter;

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sceneform);

        locationToWrapperMap = new HashMap<>();
        setRcoreLocation();

        FirebaseUtils.getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.e(TAG,dataSnapshot.getValue().toString());
                GenericTypeIndicator<Map<String, Message>> genericTypeIndicator =
                        new GenericTypeIndicator<Map<String, Message>>() {
                        };
                Map<String, Message> hashMap = dataSnapshot.getValue(genericTypeIndicator);
                if (hashMap == null) {
                    return;
                }
//                Map<Location, List<Message>> messagePerLoc = hashMap
//                        .entrySet()
//                        .stream()
//                        .map(Map.Entry::getValue)
//                        .collect(Collectors.groupingBy(Message::getLocation, Collectors.toList()));

                Map<Location, List<Message>> messagePerLoc = new HashMap<>();
                hashMap.forEach((key, value) -> {
                    Location loc = value.getLocation();
                    if (!messagePerLoc.containsKey(loc)) {
                        messagePerLoc.put(loc, new ArrayList<>());
                    }
                    messagePerLoc.get(loc).add(value);
                });
                messagePerLoc.forEach((key, value) -> {
                    // If we don't have view on this location, create it
                    if (!locationToWrapperMap.containsKey(key)) {
                        Log.e(TAG, key.toString());
                        createMarker(key).handle((a,b) -> {
                            locationToWrapperMap.get(key).getmAdapter().swapItems(value);
                            return null;
                        });
                    } else {
                        locationToWrapperMap.get(key).getmAdapter().swapItems(value);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Button button = findViewById(R.id.test);

        button.setOnClickListener(v -> {
           showAddItemDialog(this);
        });

    }


    @SuppressLint("ClickableViewAccessibility")
    private CompletableFuture<NodeAddapterWrapper> getExampleView() {
        CompletableFuture<ViewRenderable> exampleLayout =
                ViewRenderable.builder()
                        .setView(this, R.layout.example_layout)
                        .build();

        return CompletableFuture.allOf(exampleLayout)
                .handle((notUsed, throwable) -> {
                    if (throwable != null) {
                        DemoUtils.displayError(this, "Unable to load renderables", throwable);
                        return null;
                    }
                    ViewRenderable exampleLayoutRenderable = null;
                    Node base = new Node();
                    try {
                        exampleLayoutRenderable = exampleLayout.get();
                        base.setRenderable(exampleLayoutRenderable);
                        Context c = this;
                        // Add  listeners etc here
                        View eView = exampleLayoutRenderable.getView();
//        eView.setOnTouchListener((View v, MotionEvent event) -> {
//            Toast.makeText(
//                    c, "Location marker touched.", Toast.LENGTH_LONG)
//                    .show();
//            return false;
//        });


                        RecyclerView recyclerView = eView.findViewById(R.id.recycler_view_chats);
                        recyclerView.setHasFixedSize(true);

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(eView.getContext());
                        recyclerView.setLayoutManager(layoutManager);

                        mAdapter = new MessageAdapter(new ArrayList<>());
                        recyclerView.setAdapter(mAdapter);


                    } catch (InterruptedException | ExecutionException ex) {
                        DemoUtils.displayError(this, "Unable to load renderables", ex);
                    }
                    return new NodeAddapterWrapper(base, exampleLayoutRenderable, mAdapter);
                });
    }

    private synchronized void setRcoreLocation() {
        arSceneView = findViewById(R.id.ar_scene_view);

        // Set an update listener on the Scene that will hide the loading message once a Plane is
        // detected.
        arSceneView
                .getScene()
                .setOnUpdateListener(
                        (FrameTime frameTime) -> {
                            if (locationScene == null) {
                                // If our locationScene object hasn't been setup yet, this is a good time to do it
                                // We know that here, the AR components have been initiated.
                                locationScene = new LocationScene(this, this, arSceneView);
                            }

//                            addNewViews();
                            Frame frame = arSceneView.getArFrame();
                            if (frame == null) {
                                return;
                            }

                            if (frame.getCamera().getTrackingState() != TrackingState.TRACKING) {
                                return;
                            }

                            if (locationScene != null) {
                                locationScene.processFrame(frame);
                            }

                            if (loadingMessageSnackbar != null) {
                                for (Plane plane : frame.getUpdatedTrackables(Plane.class)) {
                                    if (plane.getTrackingState() == TrackingState.TRACKING) {
                                        hideLoadingMessage();
                                    }
                                }
                            }
                        });


        // Lastly request CAMERA & fine location permission which is required by ARCore-Location.
        ARLocationPermissionHelper.requestPermission(this);
    }

    private Boolean hasViews = false;

//    private void addNewViews() {
//        if (!hasViews) {
//            addMarker(new Location(37.977723, 23.686900));
//            hasViews = true;
//        }
//    }

    private synchronized CompletableFuture createMarker(Location location) {
        CompletableFuture<NodeAddapterWrapper> futureViewWrapper = getExampleView();
        return futureViewWrapper.handle((viewWrapper, b) -> {
            LocationMarker layoutLocationMarker = new LocationMarker(
                    location.getLongitude(),
                    location.getLatitude(),
                    viewWrapper.getNode()
            );
//                                layoutLocationMarker.setScaleAtDistance(true);
//                                layoutLocationMarker.setScaleModifier(0.4f);
            layoutLocationMarker.setRenderEvent(node -> {
                View eView = viewWrapper.getViewRenderable().getView();
                TextView distanceTextView = eView.findViewById(R.id.distance);
                distanceTextView.setText("Distance: " + node.getDistance() + "m");
            });
            locationToWrapperMap.put(location, viewWrapper);
            // Adding the marker
            if (locationScene != null) {
                locationScene.mLocationMarkers.add(layoutLocationMarker);
            }
            return null;
        });
    }

    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Add a new task")
                .setMessage("What do you want to do next?")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                        DatabaseReference db = FirebaseUtils.addNewMessage();
                        db.setValue(new Message(task, "user1", new Location(23.659263, 37.942026)));
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    /**
     * Make sure we call locationScene.resume();
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (locationScene != null) {
            locationScene.resume();
        }

        if (arSceneView == null) {
            return;
        }

        if (arSceneView.getSession() == null) {
            // If the session wasn't created yet, don't resume rendering.
            // This can happen if ARCore needs to be updated or permissions are not granted yet.
            try {
                Session session = DemoUtils.createArSession(this, installRequested);
                if (session == null) {
                    installRequested = ARLocationPermissionHelper.hasPermission(this);
                    return;
                } else {
                    arSceneView.setupSession(session);
                }
            } catch (UnavailableException e) {
                DemoUtils.handleSessionException(this, e);
            }
        }

        try {
            arSceneView.resume();
        } catch (CameraNotAvailableException ex) {
            DemoUtils.displayError(this, "Unable to get camera", ex);
            finish();
            return;
        }

        if (arSceneView.getSession() != null) {
            //showLoadingMessage();
        }
    }

    /**
     * Make sure we call locationScene.pause();
     */
    @Override
    public void onPause() {
        super.onPause();

        if (arSceneView == null) {
            return;
        }

        if (locationScene != null) {
            locationScene.pause();
        }

        arSceneView.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (arSceneView == null) {
            return;
        }

        arSceneView.destroy();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] results) {
        if (!ARLocationPermissionHelper.hasPermission(this)) {
            if (!ARLocationPermissionHelper.shouldShowRequestPermissionRationale(this)) {
                // Permission denied with checking "Do not ask again".
                ARLocationPermissionHelper.launchPermissionSettings(this);
            } else {
                Toast.makeText(
                        this, "Camera permission is needed to run this application", Toast.LENGTH_LONG)
                        .show();
            }
            finish();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // Standard Android full-screen functionality.
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    private void showLoadingMessage() {
        if (loadingMessageSnackbar != null && loadingMessageSnackbar.isShownOrQueued()) {
            return;
        }

        loadingMessageSnackbar =
                Snackbar.make(
                        LocationActivity.this.findViewById(android.R.id.content),
                        R.string.plane_finding,
                        Snackbar.LENGTH_INDEFINITE);
        loadingMessageSnackbar.getView().setBackgroundColor(0xbf323232);
        loadingMessageSnackbar.show();
    }

    private void hideLoadingMessage() {
        if (loadingMessageSnackbar == null) {
            return;
        }

        loadingMessageSnackbar.dismiss();
        loadingMessageSnackbar = null;
    }
}
