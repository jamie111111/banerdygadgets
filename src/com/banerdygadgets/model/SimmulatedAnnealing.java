package com.banerdygadgets.model;

import com.banerdygadgets.controllers.route.routeWindowFeedbackController;

import java.io.IOException;

public class SimmulatedAnnealing {
    public static final double RATE_OF_COOLING = 0.005;
    public static final double INITIAL_TEMPERATURE = 999999;
    public static final double MIN_TEMPERATURE = 0.99;
    public static String acceptanceProbabilityString;
    public static String randomNumber;
    public static String decisionString;
    private Route korsteRoute;


    public Route findRoute(double temperature, Route currentRoute) throws IOException {
        Route shortestRoute = new Route(currentRoute);
        Route adjacentRoute;
        while (temperature > MIN_TEMPERATURE) {
            System.out.print(currentRoute + " | " + currentRoute.getTotalStringDistance() + " | "
                    + String.format("%.2f", temperature));

            adjacentRoute = obtainAdjacentRoute(new Route(currentRoute));
            if (currentRoute.getTotalDistance() < shortestRoute.getTotalDistance())
                shortestRoute = new Route(currentRoute);
            routeWindowFeedbackController.getInstance().getFeedbackList().add(new RouteAlgoFeedback(currentRoute.toStringPlacesAndPostals(),
                    currentRoute.getTotalStringDistance(),String.format("%.2f", temperature),
                    acceptanceProbabilityString,randomNumber,decisionString));
            routeWindowFeedbackController.getInstance().loadData();


            if (acceptRoute(currentRoute.getTotalDistance(), adjacentRoute.getTotalDistance(), temperature))
                currentRoute = new Route(adjacentRoute);
            routeWindowFeedbackController.getInstance().getFeedbackList().add(new RouteAlgoFeedback(currentRoute.toString(),
                    currentRoute.getTotalStringDistance(),String.format("%.2f", temperature),
                    acceptanceProbabilityString,randomNumber,decisionString));
            routeWindowFeedbackController.getInstance().loadData();
            temperature *= 1 - RATE_OF_COOLING;
        }
        routeWindowFeedbackController.getInstance().loadData();
//        System.out.println("De meest optimale route = " + shortestRoute.toString()+ ", afstand: " + shortestRoute.getTotalStringDistance()) ;
        this.korsteRoute = shortestRoute;
        return shortestRoute;

    }

    private boolean acceptRoute(double currentDistance, double adjacentDistance, double temperature) {
        String decision = null;
        boolean shorterDistance = true;
        boolean acceptRouteFlag = false;
        double acceptanceProbability = 1.0;
        if (adjacentDistance >= currentDistance) {
            acceptanceProbability = Math.exp(-(adjacentDistance - currentDistance) / temperature);
            acceptanceProbabilityString = String.format("%.2f", acceptanceProbability);
            shorterDistance = false;
        }
        double randomNumb = Math.random();
        randomNumber = String.format("%.2f", randomNumb);

        if (acceptanceProbability >= randomNumb)
            acceptRouteFlag = true;
        if (shorterDistance) {
            decision = "Doorgaan (Kortere aangrenzende route)";
            decisionString = decision;
        }
        else if (acceptRouteFlag) {
            decision = "Doorgaan (Random nr <= Prob Function)";
            decisionString = decision;
        }
        else
            decision = "Blijf (Random nr > Prob Function)";
        decisionString = decision;
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
    public Route getKorsteRoute() {
       return  this.korsteRoute;
    }
}
