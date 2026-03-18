package com.atakmap.android.helloworld.recyclerview;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import com.atakmap.android.dropdown.DropDownReceiver;
import com.atakmap.android.helloworld.plugin.R;
import com.atakmap.android.maps.MapView;
import com.atakmap.android.util.time.TimeListener;
import com.atakmap.android.util.time.TimeViewUpdater;

/**
 * Drop-down menu to show only contacts in a RecyclerView, labeled "Contacts"
 */
public class ContactsRecyclerViewDropDown extends DropDownReceiver implements TimeListener {
    private final MapView _mapView;
    private final Context _plugin;
    private final TimeViewUpdater _timeUpdater;
    private final View _view;
    private final RecyclerView _rView;
    private final RecyclerViewAdapter _adapter;

    public ContactsRecyclerViewDropDown(MapView mapView, Context plugin) {
        super(mapView);
        _mapView = mapView;
        _plugin = plugin;
        _view = LayoutInflater.from(_plugin).inflate(R.layout.recycler_view, mapView, false);
        _rView = _view.findViewById(R.id.rView);
        _adapter = new RecyclerViewAdapter(_mapView, _plugin, true); // true = contacts only
        _rView.setAdapter(_adapter);
        _rView.setLayoutManager(new LinearLayoutManager(_plugin, LinearLayoutManager.VERTICAL, false));
        // Set label to "Contacts"
        View label = _view.findViewById(R.id.recyclerViewLabel);
        if (label instanceof android.widget.TextView) {
            ((android.widget.TextView) label).setText("Contacts");
        }
        _timeUpdater = new TimeViewUpdater(_mapView, 1000);
        _timeUpdater.register(this);
    }

    @Override
    public void disposeImpl() {
        _timeUpdater.unregister(this);
    }

    @Override
    public void onTimeChanged(com.atakmap.coremap.maps.time.CoordinatedTime ot, com.atakmap.coremap.maps.time.CoordinatedTime nt) {
        if (isVisible())
            _adapter.notifyDataSetChanged();
    }

    @Override
    public View getView() {
        return _view;
    }
}

