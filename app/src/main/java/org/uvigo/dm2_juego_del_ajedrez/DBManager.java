package org.uvigo.dm2_juego_del_ajedrez;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** Maneja el acceso a la base de datos. */
public class DBManager extends SQLiteOpenHelper {
    public static final String DB_NAME = "Profiles";
    public static final int DB_VERSION = 2;

    public static final String PROFILE_TABLE = "PROFILE";

    public static final String PROFILE_NAME = "_id";
    public static final String PROFILE_IMAGE = "image_url";
    public static final String PROFILE_USED = "used";
    public static final String PROFILE_POINTS = "points";
    public static final String PROFILE_ACHIEVEMENTS = "achievements";
    public static final String PROFILE_BOARD_SKIN = "board_skin";
    public static final String PROFILE_PIECE_SKIN = "piece_skin";


    public DBManager(Context context)
    {
        super( context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.e(  "DBManager",
                "CREATING DB " + DB_NAME + " v:" + DB_VERSION);

        try {
            db.beginTransaction();
            db.execSQL( "CREATE TABLE IF NOT EXISTS " + PROFILE_TABLE + "( "
                    + PROFILE_NAME + " string(255) PRIMARY KEY NOT NULL, "
                    + PROFILE_IMAGE + " string(255) , "
                    + PROFILE_USED + " boolean , "
                    + PROFILE_POINTS + " int, "
                    + PROFILE_ACHIEVEMENTS + " string NOT NULL)");
            db.setTransactionSuccessful();
        }
        catch(SQLException exc)
        {
            Log.e( "DBManager.onCreate", exc.getMessage() );
        }
        finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i(  "DBManager",
                "DB: " + DB_NAME + ": v" + oldVersion + " -> v" + newVersion );

        try {
            db.beginTransaction();
            db.execSQL( "DROP TABLE IF EXISTS " + PROFILE_TABLE );
            db.setTransactionSuccessful();
        }  catch(SQLException exc) {
            Log.e( "DBManager.onUpgrade", exc.getMessage() );
        }
        finally {
            db.endTransaction();
        }

        this.onCreate( db );
    }

    public Cursor getProfiles()
    {
        return this.getReadableDatabase().query( PROFILE_TABLE,
                null, null, null, null, null, null );
    }

    public boolean addProfile(String name, String image, String used, int points, String achievementsList)
    {
        Log.e("WARN: ","INSERT PROFILE "+name);
        Cursor cursor = null;
        boolean toret = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put( PROFILE_NAME, name );
        values.put( PROFILE_IMAGE, image );
        values.put( PROFILE_USED, used );
        values.put( PROFILE_POINTS, points );
        values.put( PROFILE_ACHIEVEMENTS, achievementsList );

        try {
            db.beginTransaction();
            cursor = db.query( PROFILE_TABLE, null, PROFILE_NAME + "=?", new String[]{ name }, null, null, null, null );

            if ( cursor.getCount() > 0 ) {
                db.update( PROFILE_TABLE, values, PROFILE_NAME + "= ?", new String[]{ name } );
            } else {
                db.insert( PROFILE_TABLE, null, values );
            }

            db.setTransactionSuccessful();
            toret = true;

        } catch(SQLException exc)
        {
            Log.e( "DBManager.add", exc.getMessage() );
        }
        finally {
            if ( cursor != null ) {
                cursor.close();
            }

            db.endTransaction();
        }

        return toret;
    }

    public boolean deleteProfile(String name)
    {
        boolean toret = false;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.beginTransaction();
            db.delete( PROFILE_TABLE, PROFILE_NAME + "=?", new String[]{ name } );
            db.setTransactionSuccessful();
            toret = true;
        } catch(SQLException exc) {
            Log.e( "DBManager.delete", exc.getMessage() );
        } finally {
            db.endTransaction();
        }

        return toret;
    }
}