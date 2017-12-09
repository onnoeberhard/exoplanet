package onno.exoplanet;
 
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class LocalDatabaseHandler extends SQLiteOpenHelper {

    boolean close = false;

    public LocalDatabaseHandler(Context context) {
        this(context, false);
    }

    public LocalDatabaseHandler(Context context, boolean _close) {
        super(context, "exoplanet", null, 1);
        close = _close;
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE stuff(id INTEGER PRIMARY KEY, name TEXT, value TEXT)");
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS stuff");
        onCreate(db);
    }
 
    public String getStuffValue(String what) {
    	String result = "";
    	String countQuery = "SELECT * FROM `stuff` WHERE `name` = '" + what + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.getCount() > 0) {
	        cursor.moveToFirst();
	        result = cursor.getString(cursor.getColumnIndex("value"));
        } else
        	result = "DOES NOT EXIST";
        cursor.close();
        if(close) this.close();
    	return result;
    }
    
    public void setStuffValue(String name, String value) {
    	SQLiteDatabase db = getWritableDatabase();
		String sql = !(getStuffValue(name).equals("DOES NOT EXIST")) ? (value.equals("*DELETE*")) ?
				("DELETE FROM `stuff` WHERE `name` = '" + name + "'") :
				("UPDATE `stuff` SET `value` = '" + value + "' WHERE `name` = '" + name + "'") : 
				("INSERT INTO `stuff` (`name`, `value`)VALUES('" + name + "', '" + value + "')");
		db.execSQL(sql);
        if(close) this.close();
    }
    
    public String getFromDb(String table, String id_name, String id_value, String column) {
    	String result = "";
    	String countQuery = "SELECT * FROM `" + table + "` WHERE `" + id_name + "` = '" + id_value + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.getCount() > 0) {
	        cursor.moveToFirst();
	        result = cursor.getString(cursor.getColumnIndex(column));
        } else
        	result = "DOES NOT EXIST";
        cursor.close();
        if(close) this.close();
    	return result;
    }
    
    public ArrayList<String> getAllFromDb(String table, String column) {
    	ArrayList<String> result = new ArrayList<String>();
    	String countQuery = "SELECT * FROM `" + table + "`";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.getCount() > 0) {
	        cursor.moveToFirst();
    		result.add(cursor.getString(cursor.getColumnIndex(column)));
        	while(cursor.moveToNext()) {
        		result.add(cursor.getString(cursor.getColumnIndex(column)));
        	}
        } else
        	result = null;
        cursor.close();
        if(close) this.close();
    	return result;
    }
    
    public void smartInUp(String table, String id_name, String id_value, String column, String value) {
    	SQLiteDatabase db = getWritableDatabase();
    	String sql = !(getFromDb(table, id_name, id_value, "id").equals("DOES NOT EXIST")) ? (column.equals("") && value.equals("")) ? 
				("DELETE FROM `" + table + "` WHERE `" + id_name + "` = '" + id_value + "'") :  
					("UPDATE `" + table + "` SET `" + column  + "` = '" + value + "' WHERE `" + id_name + "` = '" + id_value + "'") :
				("INSERT INTO `" + table + "` (`" + id_name + "`, `" + column + "`)VALUES('" + id_value + "', '" + value + "')");
		db.execSQL(sql);
        if(close) this.close();
    }
    
    public void execSQL(String sql) {
    	SQLiteDatabase db = getWritableDatabase();
    	db.execSQL(sql);
        if(close) this.close();
    }
   
}