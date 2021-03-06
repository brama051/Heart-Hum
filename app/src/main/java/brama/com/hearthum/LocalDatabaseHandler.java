package brama.com.hearthum;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABM on 19.07.2015..
 */
public class LocalDatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 9;

    // Database Name
    private static final String DATABASE_NAME = "HeartHum";

    // Contacts table name
    private static final String TABLE_RECODRINGS = "Recordings";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FILENAME = "fileName";
    private static final String KEY_FILEDIRECOTRY = "fileDirectory";
    private static final String KEY_FULLPATH = "fullPath";
    private static final String KEY_TIMERECORDED = "timeRecorded";
    private static final String KEY_HEARTPOSLIS = "heartPosLis";
    private static final String KEY_IS_UPLOADED = "isUploaded";
    public LocalDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECORDINGS_TABLE = "CREATE TABLE " + TABLE_RECODRINGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FILENAME + " TEXT NULL,"
                + KEY_FILEDIRECOTRY + " TEXT NULL,"
                + KEY_FULLPATH + " TEXT NULL,"
                + KEY_TIMERECORDED + " TEXT NULL,"
                + KEY_HEARTPOSLIS + " TEXT NULL,"
                + KEY_IS_UPLOADED + " INTEGER NULL" + ");";
        db.execSQL(CREATE_RECORDINGS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("DB", "Dropping table");

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECODRINGS);

        Log.w("DB", "Recreating table");
        // Create tables again
        onCreate(db);
    }



    public int updateRecord(Record record){
        SQLiteDatabase db = this.getWritableDatabase();
        int check = 0;
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        ContentValues values = new ContentValues();
            values.put(KEY_FILENAME, record.getFileName());
            values.put(KEY_FILEDIRECOTRY, record.getFileDirectory());
            values.put(KEY_FULLPATH, record.getFullPath());
            values.put(KEY_TIMERECORDED, df.format(record.getTimeRecorded()));
            values.put(KEY_HEARTPOSLIS, record.getHeartPositionListened());
            values.put(KEY_IS_UPLOADED, record.getIsUploaded());
        // Updating A Row
        check = db.update(TABLE_RECODRINGS, values, KEY_ID + " = ?", new String[]{String.valueOf(record.getID())});
        db.close(); // Closing database connection
        return check;
    }

    // Creates a record in a data table and returns the ID od a created record with blank data
    public int createRecord(){
        int id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        //Record record = new Record();
        ContentValues values = new ContentValues();
            values.put(KEY_FILENAME, ".");
            values.put(KEY_FILEDIRECOTRY, ".");
            values.put(KEY_FULLPATH, ".");
            values.put(KEY_TIMERECORDED, ".");
            values.put(KEY_HEARTPOSLIS, ".");
            values.put(KEY_IS_UPLOADED, 0);
        // Inserting Row
        db.insert(TABLE_RECODRINGS, null, values);
        db.close(); // Closing database connection

        id = getLastInsertedID();
        return id;
    }

    public int getLastInsertedID(){
        SQLiteDatabase db = this.getReadableDatabase();
        int id=0;
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_RECODRINGS, null);

        c.moveToLast();

        id = c.getInt(c.getColumnIndex(KEY_ID));
        c.close();
        db.close();
        return id;
    }

    public Record getRecord(int id) throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RECODRINGS, new String[] {
                        KEY_ID,
                        KEY_FILENAME,
                        KEY_FILEDIRECOTRY,
                        KEY_FULLPATH,
                        KEY_TIMERECORDED,
                        KEY_HEARTPOSLIS,
                        KEY_IS_UPLOADED}, KEY_ID + " = ?",
                        new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Record record = new Record(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                df.parse(cursor.getString(4)),
                cursor.getString(5),
                Integer.parseInt(cursor.getString(6)));
        cursor.close();
        db.close();
        // return contact
        return record;
    }

    public List<Record> getAllRecords() throws Exception{
        List<Record> recordList = new ArrayList<Record>();
        String selectAllQuerry = "SELECT * FROM " + TABLE_RECODRINGS;
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllQuerry,null);

        if (cursor.moveToFirst()){
            do{
                Record record = new Record(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        df.parse(cursor.getString(4)),
                        cursor.getString(5),
                        Integer.parseInt(cursor.getString(6)));
                recordList.add(record);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recordList;
    }

    public int getRecordsCount(){
        String selectAllQuerry = "SELECT * FROM " + TABLE_RECODRINGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllQuerry,null);
        int count = 0;
        count = cursor.getCount();
        cursor.close();
        db.close();

        return count;
    }

    public void deleteRecord(Record record){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECODRINGS, KEY_ID + " = ?", new String[]{String.valueOf(record.getID())});
        db.close();
    }

    public List<Record> getNotUploaded() throws Exception{
        List<Record> recordList = new ArrayList<Record>();
        String selectAllQuerry = "SELECT * FROM " + TABLE_RECODRINGS + " WHERE " + KEY_IS_UPLOADED + " = 0";
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllQuerry,null);

        if (cursor.moveToFirst()){
            do{
                Record record = new Record(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        df.parse(cursor.getString(4)),
                        cursor.getString(5),
                        Integer.parseInt(cursor.getString(6)));
                recordList.add(record);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recordList;
    }
}
