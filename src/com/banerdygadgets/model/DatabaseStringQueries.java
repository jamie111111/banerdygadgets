package com.banerdygadgets.model;

public class DatabaseStringQueries {
    private static final String RETOUR_TABLE = "retourorder";
    public static final String ID_COLUMN = "id";
    private static final String DATE_COLUMN = "datumAanmelding";
    private static final String STATUS_COLUMN = "status";
    private static final String REDEN_COLUMN = "reden";
    private static final String BESTEL_ID_COLUMN = "bestelId";
    public static final String KLANTNR_ID_COLUMN = "klantId";

    public static final String INSERT_RETOURORDER =
            "INSERT INTO " + RETOUR_TABLE + '(' + DATE_COLUMN + "," + STATUS_COLUMN + "," + REDEN_COLUMN + "," + BESTEL_ID_COLUMN + ", " + KLANTNR_ID_COLUMN + ") VALUES(?,?,?,?,?)";

    public static final String DELETE_RETOURORDER = "DELETE FROM " + RETOUR_TABLE + " WHERE " + ID_COLUMN + " = ?";
    public static final String UPDATE_STATUS_RETOURORDER =
            "UPDATE " + RETOUR_TABLE + " SET " + STATUS_COLUMN + " =? WHERE " + ID_COLUMN + " = ?";

}
