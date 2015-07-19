package brama.com.hearthum;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ABM on 19.07.2015..
 */
public class LocalDatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

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
    private static final String KEY_HEARTPOSITIONLISTENED = "heartPositionListened";

    public LocalDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_RECODRINGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FILENAME + " TEXT NULL,"
                + KEY_FILEDIRECOTRY + " TEXT NULL"
                + KEY_FULLPATH + " TEXT NULL"
                + KEY_TIMERECORDED + " TEXT NULL"
                + KEY_HEARTPOSITIONLISTENED + " TEXT NULL"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECODRINGS);

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
            values.put(KEY_HEARTPOSITIONLISTENED, record.getHeartPositionListened());
        // Updating A Row
        check = db.update(TABLE_RECODRINGS, values, KEY_ID + " = ?", new String[]{String.valueOf(record.getID())});
        db.close(); // Closing database connection
        return check;
    }

    public int createRecord(){
        int id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Record record = new Record();
        ContentValues values = new ContentValues();
            values.put(KEY_FILENAME, "");
            values.put(KEY_FILEDIRECOTRY, "");
            values.put(KEY_FULLPATH, "");
            values.put(KEY_TIMERECORDED, "");
            values.put(KEY_HEARTPOSITIONLISTENED, "");
        // Inserting Row
        db.insert(TABLE_RECODRINGS, null, values);
        db.close(); // Closing database connection

        id = getLastInsertedID();
        return id;
    }

    public int getLastInsertedID(){
        SQLiteDatabase db = this.getReadableDatabase();
        int id=0;
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_RECODRINGS,null);
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
                        KEY_HEARTPOSITIONLISTENED}, KEY_ID + " = ?",
                        new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Record record = new Record(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                df.parse(cursor.getString(4)),
                cursor.getString(5));
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
                        cursor.getString(5));
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
}
