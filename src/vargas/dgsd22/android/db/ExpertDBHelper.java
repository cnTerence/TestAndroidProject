package vargas.dgsd22.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpertDBHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "Expert.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	public static final String DESC = " DESC";
	
	private static final String SQL_CREATE_EXPERT =
		    "CREATE TABLE " + ExpertContract.ExpertEntry.TABLE_NAME + " (" +
		    ExpertContract.ExpertEntry.COLUMN_NAME_UID + TEXT_TYPE + COMMA_SEP +
		    ExpertContract.ExpertEntry.COLUMN_NAME_UNAME + TEXT_TYPE + COMMA_SEP +
		    ExpertContract.ExpertEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
		    ExpertContract.ExpertEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
		    ExpertContract.ExpertEntry.COLUMN_NAME_HOST + TEXT_TYPE + COMMA_SEP +
		    ExpertContract.ExpertEntry.COLUMN_NAME_GUEST + TEXT_TYPE + COMMA_SEP +
		    ExpertContract.ExpertEntry.COLUMN_NAME_RESULT + TEXT_TYPE + COMMA_SEP +
		    ExpertContract.ExpertEntry.COLUMN_NAME_P + TEXT_TYPE + 
		    " )";
	private static final String SQL_DELETE_EXPERT =
		    "DROP TABLE IF EXISTS " + ExpertContract.ExpertEntry.TABLE_NAME;
	
	private static final String SQL_CREATE_EXPERT_LAST_REC =
		    "CREATE TABLE " + ExpertContract.ExpertLastRecEntry.TABLE_NAME + " (" +
		    ExpertContract.ExpertLastRecEntry.COLUMN_NAME_UID + TEXT_TYPE + COMMA_SEP +
		    ExpertContract.ExpertLastRecEntry.COLUMN_NAME_UNAME + TEXT_TYPE + COMMA_SEP +
		    ExpertContract.ExpertLastRecEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
		    ExpertContract.ExpertLastRecEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
		    ExpertContract.ExpertLastRecEntry.COLUMN_NAME_HOST + TEXT_TYPE + COMMA_SEP +
		    ExpertContract.ExpertLastRecEntry.COLUMN_NAME_GUEST + TEXT_TYPE + COMMA_SEP +
		    ExpertContract.ExpertLastRecEntry.COLUMN_NAME_RESULT + TEXT_TYPE + COMMA_SEP +
		    ExpertContract.ExpertLastRecEntry.COLUMN_NAME_P + TEXT_TYPE + 
		    " )";
	private static final String SQL_DELETE_EXPERT_LAST_REC =
		    "DROP TABLE IF EXISTS " + ExpertContract.ExpertLastRecEntry.TABLE_NAME;
	
	private static ExpertDBHelper m_instance = null;
	private int m_writableDatabaseCount = 0;
	
	synchronized static public ExpertDBHelper getInstance(Context context){
		if (m_instance == null ) {
			m_instance = 
					new ExpertDBHelper(
							context.getApplicationContext(), 
							DATABASE_NAME, 
							null, 
							DATABASE_VERSION);
		}
		return m_instance;
	}

	private ExpertDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	synchronized public SQLiteDatabase getWritableDatabase() {
		SQLiteDatabase db = super.getWritableDatabase();
		if (db != null) {
			++m_writableDatabaseCount;
		}
		return db;
	}

	synchronized public void closeWritableDatabase(SQLiteDatabase db) {
		if (m_writableDatabaseCount > 0 && db != null) {
			--m_writableDatabaseCount;
			if (m_writableDatabaseCount == 0) {
				db.close();
			}
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(SQL_CREATE_EXPERT);
		db.execSQL(SQL_CREATE_EXPERT_LAST_REC);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(SQL_DELETE_EXPERT);
		db.execSQL(SQL_DELETE_EXPERT_LAST_REC);
		onCreate(db);
	}
}
