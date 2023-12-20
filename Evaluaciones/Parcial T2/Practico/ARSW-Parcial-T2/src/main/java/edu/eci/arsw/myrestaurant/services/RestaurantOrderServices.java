/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.myrestaurant.services;

import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.model.RestaurantProduct;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
public interface RestaurantOrderServices {

    void addNewOrderToTable(Order o) throws OrderServicesException;

    int calculateTableBill(int tableNumber) throws OrderServicesException;

    Set<String> getAvailableProductNames();

    RestaurantProduct getProductByName(String product) throws OrderServicesException;

    Order getTableOrder(int tableNumber);

    Set<Integer> getTablesWithOrders();

    void releaseTable(int tableNumber) throws OrderServicesException;
    
}
