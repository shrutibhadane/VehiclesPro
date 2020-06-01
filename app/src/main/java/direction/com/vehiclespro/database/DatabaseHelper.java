package direction.com.vehiclespro.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import direction.com.vehiclespro.model.DataModel;

public class DatabaseHelper extends SQLiteOpenHelper {

  // Database Version
  private static final int DATABASE_VERSION = 1;

  // Database Name
  private static final String DATABASE_NAME = "USERQUIZ_DB";
  private static final String TABLE_NAME = "USERQUIZ";

  //public static final String COL_1 = "AUTO_ID";
  private static final String COL_2 = "DATETIME";
  private static final String COL_3 = "NAME";
  private static final String COL_4 = "VEHICLE_NO";
  private static final String COL_5 = "MAKE";
  private static final String COL_6 = "MODEL";
  private static final String COL_7 = "VARIANT";
  private static final String COL_8 = "FUEL_TYPE";
  private static final String COL_9 = "IMAGE_DATA";

  private static DatabaseHelper sInstance = null;

  public static synchronized DatabaseHelper getInstance(Context context) {

    // Use the application context, which will ensure that you
    // don't accidentally leak an Activity's context.
    // See this article for more information: http://bit.ly/6LRzfx
    if (sInstance == null) {
      sInstance = new DatabaseHelper(context.getApplicationContext());
    }
    return sInstance;
  }

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  // Creating Tables
  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME
        + "(AUTO_ID INTEGER PRIMARY KEY AUTOINCREMENT , DATETIME INTEGER UNIQUE , " +
        "NAME TEXT DEFAULT NULL, VEHICLE_NO TEXT DEFAULT NULL, MAKE TEXT DEFAULT NULL," +
            "MODEL TEXT DEFAULT NULL, VARIANT TEXT DEFAULT NULL, FUEL_TYPE TEXT DEFAULT NULL, IMAGE_DATA TEXT DEFAULT NULL)");
  }

  // Upgrading database
  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
  }

  // Inserting values in database
  public boolean insertValue(String datetime, String name, String vehicle_no, String make, String model, String variant,
                             String fuel_type) {

    SQLiteDatabase database = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(COL_2, datetime);
    contentValues.put(COL_3, name);
    contentValues.put(COL_4, vehicle_no);
    contentValues.put(COL_5, make);
    contentValues.put(COL_6, model);
    contentValues.put(COL_7, variant);
    contentValues.put(COL_8, fuel_type);

    long result = database.insert(TABLE_NAME, null, contentValues);

    if (result == -1) {
      Log.d("nodatainserted", "insertValue: ");
      return false;
    } else {
      Log.d("datainserted", "insertValue: ");
      return true;
    }
  }


  // Updating values in database
  public boolean updateValue(String datetime, String name, String vehicle_no, String make, String model, String variant,
                             String fuel_type) {

    SQLiteDatabase database = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(COL_2, datetime);
    contentValues.put(COL_3, name);
    contentValues.put(COL_4, vehicle_no);
    contentValues.put(COL_5, make);
    contentValues.put(COL_6, model);
    contentValues.put(COL_7, variant);
    contentValues.put(COL_8, fuel_type);

    String[] args = new String[]{name};
    long result = database.update(TABLE_NAME, contentValues, DatabaseHelper.COL_3 + "=?", args);

    if (result == -1) {
      Log.d("nodataUpdated", "updateValue: ");
      return false;
    } else {
      Log.d("dataUpdated", "updateValue: ");
      return true;
    }
  }

  /*
   * drop the table data
   * */
  public void dropData() {
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("delete from " + TABLE_NAME);
  }

  /*
   * get all entries
   * */
  public List<DataModel> getData() {

    List<DataModel> data = new ArrayList<>();

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " ORDER BY AUTO_ID DESC", null);
    StringBuilder stringBuffer = new StringBuilder();
    DataModel dataModel = null;

    while (cursor.moveToNext()) {
      dataModel = new DataModel();
      String datetime = cursor.getString(cursor.getColumnIndexOrThrow("DATETIME"));
      String name = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
      String vehicleNo = cursor.getString(cursor.getColumnIndexOrThrow("VEHICLE_NO"));
      String make = cursor.getString(cursor.getColumnIndexOrThrow("MAKE"));
      String model = cursor.getString(cursor.getColumnIndexOrThrow("MODEL"));
      String variant = cursor.getString(cursor.getColumnIndexOrThrow("VARIANT"));
      String fuelType = cursor.getString(cursor.getColumnIndexOrThrow("FUEL_TYPE"));
      //String imageData = cursor.getString(cursor.getColumnIndexOrThrow("IMAGE_DATA"));

      dataModel.setDateT(datetime);
      dataModel.setName(name);
      dataModel.setVehicleNo(vehicleNo);
      dataModel.setMake(make);
      dataModel.setModel(model);
      dataModel.setVariant(variant);
      dataModel.setFuelType(fuelType);
      //dataModel.setPhoto(imageData);

      stringBuffer.append(dataModel);
      data.add(dataModel);
    }
    return data;
  }
}
