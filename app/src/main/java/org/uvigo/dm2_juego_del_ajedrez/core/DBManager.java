package org.uvigo.dm2_juego_del_ajedrez.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.uvigo.dm2_juego_del_ajedrez.core.Achievement;
import org.uvigo.dm2_juego_del_ajedrez.core.History;
import org.uvigo.dm2_juego_del_ajedrez.core.Profile;

/** Maneja el acceso a la base de datos. */
public class DBManager extends SQLiteOpenHelper {
    public static final String DB_NAME = "Chess";
    public static final int DB_VERSION = 5;

    public static final String HISTORY_TABLE = "HISTORY";
    public static final String ACHIEVEMENTS_TABLE = "ACHIEVEMENTS";
    public static final String PROFILE_TABLE = "PROFILES";

    public static final String HISTORY_NAME = "_id";
    public static final String HISTORY_LOG = "log";
    public static final String HISTORY_PIECEPOS = "pos";

    public static final String ACHIEVEMENT_NAME = "_id";
    public static final String ACHIEVEMENTS_CLUE = "clue";

    public DBManager(Context context)
    {
        super( context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.e(  "DBManager",
                "CREATING DB " + DB_NAME + " v:" + DB_VERSION);

        onCreateAchievements(db);
        onCreateHistory(db);

    }

    /**Crea la tabla de achievements*/
    public void onCreateAchievements(SQLiteDatabase db)
    {
        Log.e(  "DBManager",
                "CREATING DB " + DB_NAME + " v:" + DB_VERSION);

        try {
            db.beginTransaction();
            db.execSQL( "CREATE TABLE IF NOT EXISTS " + ACHIEVEMENTS_TABLE + "( "
                    + ACHIEVEMENT_NAME + " string(255) PRIMARY KEY NOT NULL, "
                    + ACHIEVEMENTS_CLUE + " string NOT NULL)");
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

    /**Crea la tabla de historiales de partidas*/
    public void onCreateHistory(SQLiteDatabase db)
    {
        Log.e(  "DBManager",
                "CREATING DB " + DB_NAME + " v:" + DB_VERSION);

        try {
            db.beginTransaction();
            db.execSQL( "CREATE TABLE IF NOT EXISTS " + HISTORY_TABLE + "( "
                    + HISTORY_NAME + " string(255) PRIMARY KEY NOT NULL, "
                    + HISTORY_LOG + " string(255), "
                    + HISTORY_PIECEPOS + " string(255))");
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
        onUpgradeAchievements(db,oldVersion,newVersion);
        onUpgradeHistory(db,oldVersion,newVersion);

        this.onCreate( db );
    }

    /**Actualiza la tabla achievements*/
    public void onUpgradeAchievements(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i(  "DBManager",
                "DB: " + DB_NAME + ": v" + oldVersion + " -> v" + newVersion );

        try {
            db.beginTransaction();
            db.execSQL( "DROP TABLE IF EXISTS " + ACHIEVEMENTS_TABLE );
            db.setTransactionSuccessful();

        }  catch(SQLException exc) {
            Log.e( "DBManager.onUpgrade", exc.getMessage() );
        }
        finally {
            db.endTransaction();
        }

        this.onCreate( db );
    }

    /**Actualiza historiales*/
    public void onUpgradeHistory(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i(  "DBManager",
                "DB: " + DB_NAME + ": v" + oldVersion + " -> v" + newVersion );

        try {
            db.beginTransaction();
            db.execSQL( "DROP TABLE IF EXISTS " + HISTORY_TABLE );
            db.setTransactionSuccessful();

        }  catch(SQLException exc) {
            Log.e( "DBManager.onUpgrade", exc.getMessage() );
        }
        finally {
            db.endTransaction();
        }

        this.onCreate( db );
    }

    /**Añade un historial*/
    public boolean addHistory(History history){
        Log.e("WARN: ","INSERT HISTORY "+history.getName());
        Cursor cursor = null;
        boolean toret = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put( HISTORY_NAME, history.getName() );
        values.put( HISTORY_LOG, history.getPlainLog() );
        values.put( HISTORY_PIECEPOS, history.getPlainPos() );

        try {
            db.beginTransaction();

            cursor = db.query( HISTORY_TABLE,
                    null,
                    HISTORY_NAME + "=?",
                    new String[]{ history.getName() },
                    null, null, null, null );

            if ( cursor.getCount() > 0 ) {
                db.update( HISTORY_TABLE,
                        values, HISTORY_NAME + "= ?", new String[]{history.getName() } );
            } else {
                db.insert( HISTORY_TABLE, null, values );
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

    /**Inserta un achievement en la base de datos*/
    public boolean addAchievement(Achievement achievement){
        Log.e("WARN: ","INSERT ACHIVEMENT "+achievement.getName()+" "+achievement.getDescription());
        Cursor cursor = null;
        boolean toret = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put( ACHIEVEMENT_NAME, achievement.getName() );
        values.put( ACHIEVEMENTS_CLUE, achievement.getDescription() );

        try {
            db.beginTransaction();

            cursor = db.query( ACHIEVEMENTS_TABLE,
                    null,
                    ACHIEVEMENT_NAME + "=?",
                    new String[]{ achievement.getName() },
                    null, null, null, null );

            if ( cursor.getCount() > 0 ) {
                db.update( ACHIEVEMENTS_TABLE,
                        values, ACHIEVEMENT_NAME + "= ?", new String[]{achievement.getName() } );
            } else {
                db.insert( ACHIEVEMENTS_TABLE, null, values );
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

    /** Borra un historial por nombre*/
    public boolean deleteHistory(String name)
    {
        boolean toret = false;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.beginTransaction();
            db.delete( HISTORY_TABLE, HISTORY_NAME + "=?", new String[]{ name } );
            db.setTransactionSuccessful();
            toret = true;
        } catch(SQLException exc) {
            Log.e( "DBManager.delete", exc.getMessage() );
        } finally {
            db.endTransaction();
        }

        return toret;
    }

    /**Devuelve los historiales*/
    public Cursor getHistories(){
        return this.getReadableDatabase().query(HISTORY_TABLE,null,null,null,null,null,null);
    }

    /**Devuelve los historiales de las partidas en las que ha jugado el selectedProfile*/
    public Cursor getHistoriesByName(String profileName){
        Log.e("",profileName+" ######################");
        return this.getReadableDatabase().query(HISTORY_TABLE,null,HISTORY_NAME+" LIKE '%"+profileName+"%'",null,null,null,null);
    }

    /**Devuelve los logros*/
    public Cursor getAchievements(){
        return this.getReadableDatabase().query(ACHIEVEMENTS_TABLE,null,null,null,null,null,null);
    }

    /**Obtiene la descripcion de un logro para mostrarlo como pista*/
    public String getDescription(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= new String[]{ACHIEVEMENTS_CLUE};
        Cursor cursor= db.query(ACHIEVEMENTS_TABLE,columns,ACHIEVEMENT_NAME+"=?",new String[]{name},null,null,null);;
        if (!cursor.moveToFirst())
            cursor.moveToFirst();
        return cursor.getString(0);
    }

    /**Obtiene un history por nombre*/
    public Cursor getHistory(String name){

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= new String[]{HISTORY_NAME,HISTORY_LOG,HISTORY_PIECEPOS};

        Cursor cursor= db.query(HISTORY_TABLE,columns,HISTORY_NAME+"=?",new String[]{name},null,null,null);;
        if (!cursor.moveToFirst())
            cursor.moveToFirst();
        return cursor;
    }
}