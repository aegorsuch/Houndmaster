package com.atakmap.android.helloworld.plugin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.atakmap.android.dropdown.DropDownReceiver;
import com.atakmap.android.maps.MapView;

public class BloodhoundDashboardDropDown extends DropDownReceiver {
    private final View _view;
    private final MapView _mapView;
    private final BloodhoundOrderAdapter adapter;
    private final BloodhoundOrderManager orderManager;

    public BloodhoundDashboardDropDown(MapView mapView, Context plugin, BloodhoundOrderManager orderManager) {
        super(mapView);
        this._mapView = mapView;
        this.orderManager = orderManager;
        _view = LayoutInflater.from(plugin).inflate(R.layout.bloodhound_dashboard, mapView, false);
        RecyclerView rv = _view.findViewById(R.id.bloodhoundOrdersRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(plugin));
        adapter = new BloodhoundOrderAdapter(orderManager.getOrders(), orderManager::removeOrder);
        rv.setAdapter(adapter);
        // Listen for order changes
        orderManager.addOrderChangeListener(() -> adapter.notifyDataSetChanged());

        // Set up Add new Bloodhound order button
        Button addOrderButton = _view.findViewById(R.id.addBloodhoundOrderButton);
        addOrderButton.setOnClickListener(v -> {
            // Show the add order flow (show map items for selection)
            com.atakmap.android.helloworld.recyclerview.RecyclerViewDropDown dropDown = new com.atakmap.android.helloworld.recyclerview.RecyclerViewDropDown(_mapView, plugin);
            dropDown.show();
            dispose();
        });
        // Optionally set the title (not strictly needed, but for future dynamic title changes)
        TextView title = _view.findViewById(R.id.bloodhoundDashboardTitle);
        if (title != null) title.setText("Houndmaster");
    }

    public void show() {
        // Show the dashboard as a drop-down
        showDropDown(_view, HALF_WIDTH, FULL_HEIGHT, FULL_WIDTH, THIRD_HEIGHT);
    }

    public View getView() {
        return _view;
    }

    @Override
    protected void disposeImpl() {
        // Clean up resources if needed
    }

    @Override
    public void onReceive(Context context, android.content.Intent intent) {
        // No broadcast handling needed for this drop-down
    }
}
