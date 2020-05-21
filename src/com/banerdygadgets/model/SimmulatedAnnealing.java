package com.banerdygadgets.model;

public class SimmulatedAnnealing {
    public static final double RATE_OF_COOLING = 0.005;
    public static final double INITIAL_TEMPERATURE = 999;
    public static final double MIN_TEMPERATURE = 0.99;

    public Route findRoute(double temperature, Route currentRoute) {
        Route shortestRoute = new Route(currentRoute);
        Route adjacentRoute;
        while (temperature > MIN_TEMPERATURE) {
            System.out.print(currentRoute + " | " + currentRoute.getTotalStringDistance() + " | "
                    + String.format("%.2f", temperature));
            adjacentRoute = obtainAdjacentRoute(new Route(currentRoute));
            if (currentRoute.getTotalDistance() < shortestRoute.getTotalDistance())
                shortestRoute = new Route(currentRoute);
            if (acceptRoute(currentRoute.getTotalDistance(), adjacentRoute.getTotalDistance(), temperature))
                currentRoute = new Route(adjacentRoute);
            temperature *= 1 - RATE_OF_COOLING;
        }
        return shortestRoute;
    }

    private boolean acceptRoute(double currentDistance, double adjacentDistance, double temperature) {
        String decision = null;
        boolean shorterDistance = true;
        boolean acceptRouteFlag = false;
        double acceptanceProbability = 1.0;
        if (adjacentDistance >= currentDistance) {
            acceptanceProbability = Math.exp(-(adjacentDistance - currentDistance) / temperature);
            shorterDistance = false;
        }
        double randomNumb = Math.random();
        if (acceptanceProbability >= randomNumb)
            acceptRouteFlag = true;
        if (shorterDistance)
            decision = "Doorgaan (Kortere aangrenzende route)";
        else if (acceptRouteFlag)
            decision = "Doorgaan (Random nr <= Prob Function)";
        else
            decision = "Blijf (Random nr > Prob Function)";
        System.out.println("         | " + String.format("%.2f", acceptanceProbability) + "     " +
                "  " +
                "   " +
                "     " +
                "|  " +
                " "
                + String.format("%.2f", randomNumb) + "       | " + decision);
        return acceptRouteFlag;
    }

    private Route obtainAdjacentRoute(Route route) {
        int x1 = 0, x2 = 0;
        while (x1 == x2) {
            x1 = (int) (route.getCities().size() * Math.random());
            x2 = (int) (route.getCities().size() * Math.random());
        }
        Geolocation city1 = route.getCities().get(x1);
        Geolocation city2 = route.getCities().get(x2);
        route.getCities().set(x2, city1);
        route.getCities().set(x1, city2);
        return route;
    }
}
