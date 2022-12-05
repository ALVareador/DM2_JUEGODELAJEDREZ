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
    public static final int DB_VERSION = 4;

    public static final String HISTORY_TABLE = "HISTORY";
    public static final String ACHIEVEMENTS_TABLE = "ACHIEVEMENTS";
    public static final String PROFILE_TABLE = "PROFILES";

    public static final String HISTORY_NAME = "_id";
    public static final String HISTORY_LOG = "log";
    public static final String HISTORY_PIECEPOS = "pos";

    public static final String ACHIEVEMENT_NAME = "_id";
    public static final String ACHIEVEMENTS_CLUE = "clue";

    public static final String PROFILE_NAME = "_id";
    public static final String PROFILE_IMAGE = "clue";
    public static final String PROFILE_BOARD = "board";
    public static final String PROFILE_PIECE = "piece";
    public static final String PROFILE_POINTS = "points";
    public static final String PROFILE_ACHIEVEMENTS = "achievement";
    public static final String PROFILE_FRIENDS = "friend";

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
        onCreateProfiles(db);
        onCreateHistory(db);

    }

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

    public void onCreateProfiles(SQLiteDatabase db)
    {
        Log.e(  "DBManager",
                "CREATING DB " + DB_NAME + " v:" + DB_VERSION);

        try {
            db.beginTransaction();
            db.execSQL( "CREATE TABLE IF NOT EXISTS " + PROFILE_TABLE + "( "
                    + PROFILE_NAME + " string(255) PRIMARY KEY NOT NULL, "
                    + PROFILE_IMAGE + " string(255), "
                    + PROFILE_BOARD + " string(255), "
                    + PROFILE_PIECE + " string(255), "
                    + PROFILE_POINTS + " int, "
                    + PROFILE_ACHIEVEMENTS + " string(255), "
                    + PROFILE_FRIENDS + " string (255))");
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
        onUpgradeProfiles(db,oldVersion,newVersion);

        this.onCreate( db );
    }

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

    public void onUpgradeProfiles(SQLiteDatabase db, int oldVersion, int newVersion)
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

    public boolean addProfile(Profile profile){
        Log.e("WARN: ","INSERT PROFILE "+profile.getName());
        Cursor cursor = null;
        boolean toret = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put( PROFILE_NAME, profile.getName() );
        values.put( PROFILE_IMAGE, profile.getImagePath() );
        values.put( PROFILE_BOARD, profile.getSkinBoardName() );
        values.put( PROFILE_PIECE, profile.getSkinPieceName() );
        values.put( PROFILE_POINTS, profile.getPoints() );
        values.put( PROFILE_ACHIEVEMENTS, profile.getAchievements().toString() );
        values.put( PROFILE_FRIENDS, profile.getFriends().toString() );

        try {
            db.beginTransaction();

            cursor = db.query( PROFILE_TABLE,
                    null,
                    HISTORY_NAME + "=?",
                    new String[]{ profile.getName() },
                    null, null, null, null );

            if ( cursor.getCount() > 0 ) {
                db.update( PROFILE_TABLE,
                        values, PROFILE_NAME + "= ?", new String[]{profile.getName() } );
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

    /** Borra un perfil */
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

    public Cursor getHistories(){
        return this.getReadableDatabase().query(HISTORY_TABLE,null,null,null,null,null,null);
    }

    public Cursor getProfiles(){
        return this.getReadableDatabase().query(PROFILE_TABLE,null,null,null,null,null,null);
    }

    public Cursor getRivals(String profileName){
        Log.e("",profileName+" ######################");
        return this.getReadableDatabase().query(PROFILE_TABLE,null,HISTORY_NAME+" ="+profileName,null,null,null,null);
    }

    public Cursor getHistoriesByName(String profileName){
        Log.e("",profileName+" ######################");
        return this.getReadableDatabase().query(HISTORY_TABLE,null,HISTORY_NAME+" LIKE '%"+profileName+"%'",null,null,null,null);
    }


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

    /**Obtiene la posicion de todas las piezas que quedan en un historial*/
    public String getPos(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= new String[]{HISTORY_PIECEPOS};
        Cursor cursor= db.query(HISTORY_TABLE,columns,HISTORY_NAME+"=?",new String[]{name},null,null,null);;
        if (!cursor.moveToFirst())
            cursor.moveToFirst();
        return cursor.getString(0);
    }
}