package com.atradeya.asharani;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database_Card_Save extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 8;

    // Database Name
    private static final String DATABASE_NAME = "card_items";


    public Database_Card_Save(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Model_Card_Details_Saved.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Model_Card_Details_Saved.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertNote(Model_Card_Details_Saved model_card_details_saved) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Model_Card_Details_Saved.COLUMN_CARD, model_card_details_saved.Card);
        values.put(Model_Card_Details_Saved.COLUMN_EXP_MONTH, model_card_details_saved.exp_month);
        values.put(Model_Card_Details_Saved.COLUMN_EXP_YEAR, model_card_details_saved.exp_year);
        values.put(Model_Card_Details_Saved.COLUMN_CVV, model_card_details_saved.cvv);
        values.put(Model_Card_Details_Saved.COLUMN_CARD_TYPE, model_card_details_saved.card_type);
        values.put(Model_Card_Details_Saved.COLUMN_LAST4DIGIT, model_card_details_saved.last4digit);

        Log.e("rohit",model_card_details_saved.card_type+"");
        Log.e("rohit",model_card_details_saved.Card+"");
        Log.e("rohit",model_card_details_saved.last4digit+"");
        Log.e("rohit",model_card_details_saved.exp_month+"");
        Log.e("rohit",model_card_details_saved.exp_year+"");
        Log.e("rohit",model_card_details_saved.cvv+"");

        // insert row
        long id = db.insert(Model_Card_Details_Saved.TABLE_NAME, null, values);
        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<Model_Card_Details_Saved> getAllCard() {
        List<Model_Card_Details_Saved> model_card_details_saveds = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Model_Card_Details_Saved.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Model_Card_Details_Saved modelCardDetailsSaved = new Model_Card_Details_Saved();
                modelCardDetailsSaved.setCard(cursor.getString(cursor.getColumnIndex(Model_Card_Details_Saved.COLUMN_CARD)));
                modelCardDetailsSaved.setExp_month(cursor.getString(cursor.getColumnIndex(Model_Card_Details_Saved.COLUMN_EXP_MONTH)));
                modelCardDetailsSaved.setId(cursor.getInt(cursor.getColumnIndex(Model_Card_Details_Saved.COLUMN_ID)));
                modelCardDetailsSaved.setExp_year(cursor.getString(cursor.getColumnIndex(Model_Card_Details_Saved.COLUMN_EXP_YEAR)));
                modelCardDetailsSaved.setCvv(cursor.getString(cursor.getColumnIndex(Model_Card_Details_Saved.COLUMN_CVV)));
                modelCardDetailsSaved.setCard_type(cursor.getString(cursor.getColumnIndex(Model_Card_Details_Saved.COLUMN_CARD_TYPE)));
                modelCardDetailsSaved.setLast4digit(cursor.getString(cursor.getColumnIndex(Model_Card_Details_Saved.COLUMN_LAST4DIGIT)));

                model_card_details_saveds.add(modelCardDetailsSaved);

            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return modelCartMenuArrayList list
        return model_card_details_saveds;
    }

    public void deleteNote(Model_Card_Details_Saved model_card_details_saved) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Model_Card_Details_Saved.TABLE_NAME, Model_Card_Details_Saved.COLUMN_ID + " = ?",
                new String[]{String.valueOf(model_card_details_saved.getId())});

        db.close();
    }


}
