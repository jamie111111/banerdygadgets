package com.banerdygadgets.controllers.route;

import com.banerdygadgets.model.PostcodeRange;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RouteWindowDialogController {
    @FXML TextField postcodeVanField;
    @FXML TextField postcodeTotField;

    public PostcodeRange getPostcodeRange() {

        int postcodeVan = Integer.parseInt(postcodeVanField.getText().trim()) ;
        int postcodeTot = Integer.parseInt(postcodeTotField.getText().trim());
        PostcodeRange postcodeRange = new PostcodeRange( postcodeVan,postcodeTot);
        return postcodeRange;
    }
}
