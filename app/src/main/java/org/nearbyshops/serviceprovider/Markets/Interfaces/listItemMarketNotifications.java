package org.nearbyshops.serviceprovider.Markets.Interfaces;


import org.nearbyshops.serviceprovider.Markets.Model.ServiceConfigurationGlobal;

public interface listItemMarketNotifications
{
    void listItemClick(ServiceConfigurationGlobal configurationGlobal, int position);
    void selectMarketSuccessful(ServiceConfigurationGlobal configurationGlobal, int position);
    void showMessage(String message);
}