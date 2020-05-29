package com.banerdygadgets.model;

public class FileDriver {
    public static void printHeading(Route route) {
        String headingColumn1 = "Route";
        String remainingHeadingColumns = "Afstand |  Temperatuur  | Probility function | Random " +
                "getal | Besluit       " +
                "   " +
                "                ";

        //Bereken de lengte van alle plaatsnamen
        int cityNamesLength = 0;
        for (int x = 0; x < route.getCities().size(); x++)
             cityNamesLength += route.getCities().get(x).getName().length();
        //Bereken de totale lengte van de array(komma's en spaties)
        int arrayLength = cityNamesLength + route.getCities().size()*2;
        //Bereken de helft van de lengte van de array
        int partialLength = (arrayLength - headingColumn1.length())/2;
        //Print spaties voor de helft van de lengte
        for (int x=0; x < partialLength; x++)
             System.out.print(" ");
        //Print heading Route uit
        System.out.print(headingColumn1);
        //Print spaties uit
        for (int x=0; x < partialLength; x++)
             System.out.print(" ");
        //Als remainder van arraylenght 0 is, print spatie
        if ((arrayLength % 2) == 0)
            System.out.print(" ");
        //Print de overig kolommen
        System.out.println(" | "+ remainingHeadingColumns);
        //Bereken totale lengte plaastnamen en overig kolommen + 3 spaties
        cityNamesLength += remainingHeadingColumns.length() + 3;
        //Print de lijn onder de headings
        for (int x=0; x < cityNamesLength+route.getCities().size()*2; x++)
             System.out.print("-");
        System.out.println("");
    }
    public static void printInfo() {
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("| Probability Function = exp(-(neighborDistance - currentDistance) / temperature) |");
        System.out.println("| newtemperature = oldtemperature *(1-RATE_OF_COOLING)                            |");
        System.out.print("| RATE_OF_COOLING = "+ SimmulatedAnnealing.RATE_OF_COOLING);
        System.out.println("                                                         |");
        System.out.println("----------------------------------------------------------------------------------");
    }
}
