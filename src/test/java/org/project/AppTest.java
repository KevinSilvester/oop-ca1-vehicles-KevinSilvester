package org.project;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for Vehicles App.
 */
public class AppTest 
{
    // Test construction of Van object.
    @Test
    public void createVan()
    {
        Van van = new Van("Truck","Nissan","Urvan",4,"181MN6538107",
                6.00,2021,05,24,126000,53.2543,-6.4444,240);
        assertEquals("Nissan", van.getMake());
        assertEquals("Urvan", van.getModel());
        assertEquals("181MN6538107", van.getRegistration());
        assertEquals(6.00, van.getCostPerMile(),0.005);
        assertEquals(2021, van.getLastServicedDate().getYear());
        assertEquals(5, van.getLastServicedDate().getMonthValue());
        assertEquals(24, van.getLastServicedDate().getDayOfMonth());
        assertEquals(126000,van.getMileage());
        assertEquals(53.2543, van.getDepotGPSLocation().getLatitude(),0.00005);
        assertEquals(-6.4444, van.getDepotGPSLocation().getLongitude(),0.00005);
        assertEquals(240,van.getLoadSpace(),0.05);
    }

    @Test
    public void createCar()
    {
        Car car = new Car("Car","Nissan","Micra",4,"181MN6538107",
                6.00,2021,05,24,126000,53.2543,-6.4444,5);
        assertEquals("Nissan", car.getMake());
        assertEquals("Micra", car.getModel());
        assertEquals("181MN6538107", car.getRegistration());
        assertEquals(6.00, car.getCostPerMile(),0.005);
        assertEquals(2021, car.getLastServicedDate().getYear());
        assertEquals(5, car.getLastServicedDate().getMonthValue());
        assertEquals(24, car.getLastServicedDate().getDayOfMonth());
        assertEquals(126000,car.getMileage());
        assertEquals(53.2543, car.getDepotGPSLocation().getLatitude(),0.00005);
        assertEquals(-6.4444, car.getDepotGPSLocation().getLongitude(),0.00005);
        assertEquals(5,car.getSeats(),0.05);
    }

    @Test
    public void vehicleManagerSearch() {
        VehicleSearch searchType = VehicleSearch.MODEL;
        String searchQuery = "Fiat";
//        ArrayList<Vehicle> exp = new ArrayList<>({})
    }
}
