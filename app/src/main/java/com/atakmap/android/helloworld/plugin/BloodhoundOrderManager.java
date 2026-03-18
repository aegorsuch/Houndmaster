package com.atakmap.android.helloworld.plugin;

import java.util.ArrayList;
import java.util.List;

public class BloodhoundOrderManager {
    private static final BloodhoundOrderManager INSTANCE = new BloodhoundOrderManager();
    private final List<BloodhoundOrder> orders = new ArrayList<>();

    // Listener interface and support
    public interface OrderChangeListener {
        void onOrdersChanged();
    }
    private final List<OrderChangeListener> listeners = new ArrayList<>();

    public void addOrderChangeListener(OrderChangeListener listener) {
        listeners.add(listener);
    }

    public void removeOrderChangeListener(OrderChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyOrderChange() {
        for (OrderChangeListener l : listeners) {
            l.onOrdersChanged();
        }
    }

    private BloodhoundOrderManager() {}

    public static BloodhoundOrderManager getInstance() {
        return INSTANCE;
    }

    public void addOrder(BloodhoundOrder order) {
        orders.add(order);
        notifyOrderChange();
    }

    public void removeOrder(BloodhoundOrder order) {
        orders.remove(order);
        notifyOrderChange();
    }

    public List<BloodhoundOrder> getOrders() {
        return orders;
    }

    public BloodhoundOrder findOrder(String mapItemTitle, String contact) {
        for (BloodhoundOrder order : orders) {
            if (order.getMapItemTitle().equals(mapItemTitle) && order.getContact().equals(contact)) {
                return order;
            }
        }
        return null;
    }
}
