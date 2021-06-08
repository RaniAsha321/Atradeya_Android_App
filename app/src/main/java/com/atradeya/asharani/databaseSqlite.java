package com.atradeya.asharani;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class databaseSqlite extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "cart_items1";


    public databaseSqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(ModelCartMenu.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + ModelCartMenu.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertNote(ModelCartMenu modelCartMenu) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ModelCartMenu.COLUMN_PRODCUT_ID, modelCartMenu.productid);
        values.put(ModelCartMenu.COLUMN_BUSINESS_ID, modelCartMenu.businessid);
        values.put(ModelCartMenu.COLUMN_PRODUCT_NAME, modelCartMenu.productname);
        values.put(ModelCartMenu.COLUMN_VARIATION_NAME, modelCartMenu.variation_name);
        values.put(ModelCartMenu.COLUMN_PRODCUT_IMAGE, modelCartMenu.productimage);
        values.put(ModelCartMenu.COLUMN_QUANTITY, modelCartMenu.quantity);
        values.put(ModelCartMenu.COLUMN_PRICE, modelCartMenu.price);
        values.put(ModelCartMenu.COLUMN_USER_ID, modelCartMenu.usersid);
        values.put(ModelCartMenu.COLUMN_VARIATION_ID,modelCartMenu.variationid);
        values.put(ModelCartMenu.COLUMN_INC_VAT,modelCartMenu.inc_vat);
        values.put(ModelCartMenu.COLUMN_PRO_CODE,modelCartMenu.add_product_code);

        // insert row
        long id = db.insert(ModelCartMenu.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    public ModelCartMenu getModelProductCartMenu(String id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ModelCartMenu.TABLE_NAME,
                new String[]{ModelCartMenu.COLUMN_ID, ModelCartMenu.COLUMN_PRODCUT_ID, ModelCartMenu.COLUMN_PRODUCT_NAME, ModelCartMenu.COLUMN_PRODCUT_IMAGE, ModelCartMenu.COLUMN_QUANTITY,ModelCartMenu.COLUMN_PRO_CODE, ModelCartMenu.COLUMN_PRICE, ModelCartMenu.COLUMN_USER_ID},
                ModelCartMenu.COLUMN_PRODUCT_NAME + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)

            cursor.moveToFirst();

        // prepare modelCartMenu object
        assert cursor != null;

        ModelCartMenu modelCartMenu = new ModelCartMenu();
        modelCartMenu.setId(cursor.getInt(cursor.getColumnIndex(ModelCartMenu.COLUMN_ID)));
        modelCartMenu.setProductname(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_PRODUCT_NAME)));
        modelCartMenu.setProductid(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_PRODCUT_ID)));
        modelCartMenu.setPrice(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_PRICE)));
        modelCartMenu.setQuantity(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_QUANTITY)));
        modelCartMenu.setProductimage(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_PRODCUT_IMAGE)));
        modelCartMenu.setAdd_product_code(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_PRO_CODE)));

        // close the db connection
        cursor.close();

        return modelCartMenu;
    }

    public ModelCartMenu getModelVCartMenu(String id, String variation_id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ModelCartMenu.TABLE_NAME,
                new String[]{ModelCartMenu.COLUMN_ID, ModelCartMenu.COLUMN_PRODCUT_ID,ModelCartMenu.COLUMN_VARIATION_NAME, ModelCartMenu.COLUMN_PRODUCT_NAME, ModelCartMenu.COLUMN_PRODCUT_IMAGE, ModelCartMenu.COLUMN_QUANTITY,ModelCartMenu.COLUMN_PRO_CODE, ModelCartMenu.COLUMN_PRICE, ModelCartMenu.COLUMN_USER_ID,ModelCartMenu.COLUMN_VARIATION_ID},
                ModelCartMenu.COLUMN_PRODCUT_ID + "=? AND " + ModelCartMenu.COLUMN_VARIATION_ID + "=? ",
                new String[]{String.valueOf(id), String.valueOf(variation_id)}, null, null, null, null);


        if (cursor != null)

            cursor.moveToFirst();

        // prepare modelCartMenu object
        assert cursor != null;

        ModelCartMenu modelCartMenu = new ModelCartMenu();
        modelCartMenu.setId(cursor.getInt(cursor.getColumnIndex(ModelCartMenu.COLUMN_ID)));
        modelCartMenu.setProductid(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_PRODCUT_ID)));
        modelCartMenu.setPrice(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_PRICE)));
        modelCartMenu.setQuantity(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_QUANTITY)));
        modelCartMenu.setProductimage(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_PRODCUT_IMAGE)));
        modelCartMenu.setVariationid(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_VARIATION_ID)));
        modelCartMenu.setVariation_name(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_VARIATION_NAME)));
        modelCartMenu.setAdd_product_code(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_PRO_CODE)));

        // close the db connection
        cursor.close();

        return modelCartMenu;
    }


    public List<ModelCartMenu> getAllModelCartMenu() {
        List<ModelCartMenu> modelCartMenuArrayList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ModelCartMenu.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelCartMenu modelCartMenu = new ModelCartMenu();
                modelCartMenu.setProductname(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_PRODUCT_NAME)));
                modelCartMenu.setVariation_name(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_VARIATION_NAME)));
                modelCartMenu.setId(cursor.getInt(cursor.getColumnIndex(ModelCartMenu.COLUMN_ID)));
                modelCartMenu.setProductid(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_PRODCUT_ID)));
                modelCartMenu.setPrice(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_PRICE)));
                modelCartMenu.setQuantity(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_QUANTITY)));
                modelCartMenu.setProductimage(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_PRODCUT_IMAGE)));
                modelCartMenu.setVariationid(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_VARIATION_ID)));
                modelCartMenu.setAdd_product_code(cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_PRO_CODE)));

                modelCartMenuArrayList.add(modelCartMenu);

            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return modelCartMenuArrayList list
        return modelCartMenuArrayList;
    }


    public double getAllPrices() {

        double price = 0;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ModelCartMenu.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String p = cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_PRICE));
                String qu = cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_QUANTITY));

                float original_price = Float.parseFloat(String.valueOf((Double.valueOf(p)) * (Double.valueOf(qu)) + price));
                price = Double.parseDouble( String.format("%.2f", original_price));

            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();
        // return modelCartMenuArrayList list
        return price;
    }


    public double getAllInc() {

        double price = 0;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + ModelCartMenu.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String p = cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_INC_VAT));
                String qu = cursor.getString(cursor.getColumnIndex(ModelCartMenu.COLUMN_QUANTITY));

                price = ((Double.valueOf(p)) * (Double.valueOf(qu)) + price);



            } while (cursor.moveToNext());
        }
       // close db connection
        db.close();

        return price;
    }



    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + ModelCartMenu.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public int updateNote(ModelCartMenu modelCartMenu) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Log.e("value112",modelCartMenu.getProductname()+"");
        Log.e("value112",modelCartMenu.getQuantity()+"");
        values.put(ModelCartMenu.COLUMN_QUANTITY, modelCartMenu.getQuantity());

        // updating row
        return db.update(ModelCartMenu.TABLE_NAME, values, ModelCartMenu.COLUMN_PRODUCT_NAME + " = ?" ,
                new String[]{String.valueOf(modelCartMenu.getProductname())});
    }

    public int updateVNote(ModelCartMenu modelCartMenu) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ModelCartMenu.COLUMN_QUANTITY, modelCartMenu.getQuantity());

        // updating row
        return db.update(ModelCartMenu.TABLE_NAME, values,  ModelCartMenu.COLUMN_VARIATION_ID + " = ? AND " + ModelCartMenu.COLUMN_PRODCUT_ID + " = ?",
                new String[]{String.valueOf(modelCartMenu.getVariationid()), String.valueOf(modelCartMenu.getProductid())});
    }


    public void deleteNote(ModelCartMenu modelCartMenu) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ModelCartMenu.TABLE_NAME, ModelCartMenu.COLUMN_ID + " = ?",
                new String[]{String.valueOf(modelCartMenu.getId())});

        db.close();
    }

    public boolean Exists(String searchItem) {

        String[] columns = { ModelCartMenu.COLUMN_PRODCUT_ID};
        String selection = ModelCartMenu.COLUMN_PRODCUT_ID  + " =?";
        String[] selectionArgs = { searchItem };
        String limit = "1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(ModelCartMenu.TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }


    public boolean Existsbusiness(String searchItem) {

        String[] columns = { ModelCartMenu.COLUMN_BUSINESS_ID};
        String selection = ModelCartMenu.COLUMN_BUSINESS_ID  + " =?";
        String[] selectionArgs = { searchItem };
        String limit = "1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(ModelCartMenu.TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }


    public boolean ExistsCartProduct(String searchItem) {

        String[] columns = { ModelCartMenu.COLUMN_PRODCUT_ID};
        String selection = ModelCartMenu.COLUMN_PRODUCT_NAME  + " =?";
        String[] selectionArgs = { searchItem };
        String limit = "1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(ModelCartMenu.TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public boolean ExistsVariation(String searchItem) {

        String[] columns = {ModelCartMenu.COLUMN_VARIATION_ID };
        String selection = ModelCartMenu.COLUMN_VARIATION_ID  + " =?";
        String[] selectionArgs = { searchItem };
        String limit = "1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(ModelCartMenu.TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }


    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ ModelCartMenu.TABLE_NAME);
        db.close();
    }




}
