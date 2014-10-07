package vargas.dgsd22.android.db;

import java.util.ArrayList;
import java.util.List;

import vargas.dgsd22.pojo.Expert;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class ExpertContract {
	
	public static abstract class ExpertEntry implements BaseColumns{
		public static final String TABLE_NAME = "expert";
		public static final String COLUMN_NAME_UID = "uid";
		public static final String COLUMN_NAME_UNAME = "uname";
		public static final String COLUMN_NAME_DATE = "date";
		public static final String COLUMN_NAME_TYPE = "type";
		public static final String COLUMN_NAME_HOST = "host";
		public static final String COLUMN_NAME_GUEST = "guest";
		public static final String COLUMN_NAME_RESULT = "result";
		public static final String COLUMN_NAME_P = "p";
	}

	private ExpertDBHelper m_helper;
	private SQLiteDatabase m_db;
	
	public ExpertContract(Context context) {
		m_helper = ExpertDBHelper.getInstance(context);
		if(m_helper != null) {
			m_db = m_helper.getWritableDatabase();
		}else{
			m_db = null;
		}
	}
	
	public void close() {
		m_db.close();
	}
	
	public void addExpert(Expert pojo) {
		ContentValues val = new ContentValues();
		val.put(ExpertEntry.COLUMN_NAME_UID, pojo.getUid());
		val.put(ExpertEntry.COLUMN_NAME_UNAME, pojo.getUname());
		val.put(ExpertEntry.COLUMN_NAME_DATE, pojo.getDate());
		val.put(ExpertEntry.COLUMN_NAME_TYPE, pojo.getType());
		val.put(ExpertEntry.COLUMN_NAME_HOST, pojo.getHost());
		val.put(ExpertEntry.COLUMN_NAME_GUEST, pojo.getGuest());
		val.put(ExpertEntry.COLUMN_NAME_RESULT, String.valueOf(pojo.isResult()));
		val.put(ExpertEntry.COLUMN_NAME_P, pojo.getP());
		
		m_db.insert(ExpertEntry.TABLE_NAME, null, val);
	}
	
	public List<String> searchExpert(String uid){
		
		Cursor c = 
				m_db.query(ExpertEntry.TABLE_NAME, 
					new String[]{
						ExpertEntry.COLUMN_NAME_UID,
						ExpertEntry.COLUMN_NAME_UNAME,
						ExpertEntry.COLUMN_NAME_DATE,
						ExpertEntry.COLUMN_NAME_TYPE,
						ExpertEntry.COLUMN_NAME_HOST,
						ExpertEntry.COLUMN_NAME_GUEST,
						ExpertEntry.COLUMN_NAME_RESULT,
						ExpertEntry.COLUMN_NAME_P
					}, 
					ExpertEntry.COLUMN_NAME_UID + " = ?", 
					new String[]{uid}, 
					null, 
					null, 
					ExpertEntry.COLUMN_NAME_UID + ExpertDBHelper.DESC);
		
		List<String> rtnData = new ArrayList<String>();
		String data;
		int cnt;
		int numRows = c.getCount();
		c.moveToFirst();
		for(cnt = 0; cnt < numRows; cnt++){
			data = c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_UID)) + "$" +
					c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_UNAME)) + "$" +
					c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_DATE)) + "$" +
					c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_TYPE)) + "$" +
					c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_HOST)) + "$" +
					c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_GUEST)) + "$" +
					c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_RESULT)) + "$" +
					c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_P));
			
			rtnData.add(data);
			c.moveToNext();
		}
		c.close();
		
		return rtnData;
	}
	
	public List<Expert> searchAllExpert() {
		Cursor c = 
			m_db.query(ExpertEntry.TABLE_NAME, 
				new String[]{
					ExpertEntry.COLUMN_NAME_UID,
					ExpertEntry.COLUMN_NAME_UNAME,
					ExpertEntry.COLUMN_NAME_DATE,
					ExpertEntry.COLUMN_NAME_TYPE,
					ExpertEntry.COLUMN_NAME_HOST,
					ExpertEntry.COLUMN_NAME_GUEST,
					ExpertEntry.COLUMN_NAME_RESULT,
					ExpertEntry.COLUMN_NAME_P
				}, 
				null, 
				null, 
				null, 
				null, 
				ExpertEntry.COLUMN_NAME_UID + ExpertDBHelper.DESC);
		
		List<Expert> rtnData = new ArrayList<Expert>();
		Expert data = null;
		int cnt;
		int numRows = c.getCount();
		c.moveToFirst();
		for(cnt = 0; cnt < numRows; cnt++){
			data = new Expert();
			data.setUid(c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_UID)));
			data.setUname(c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_UNAME)));
			data.setDate(c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_DATE)));
			data.setType(c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_TYPE)));
			data.setHost(c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_HOST)));
			data.setGuest(c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_GUEST)));
			data.setResult(Boolean.valueOf(c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_RESULT))));
			data.setP(c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_P)));
			
			rtnData.add(data);
			c.moveToNext();
		}
		c.close();
		
		return rtnData;
	}
	
	public List<Expert> searchExpertByType(String type) {
		Cursor c = 
			m_db.query(ExpertEntry.TABLE_NAME, 
				new String[]{
					ExpertEntry.COLUMN_NAME_UID,
					ExpertEntry.COLUMN_NAME_UNAME,
					ExpertEntry.COLUMN_NAME_DATE,
					ExpertEntry.COLUMN_NAME_TYPE,
					ExpertEntry.COLUMN_NAME_HOST,
					ExpertEntry.COLUMN_NAME_GUEST,
					ExpertEntry.COLUMN_NAME_RESULT,
					ExpertEntry.COLUMN_NAME_P
				}, 
				ExpertEntry.COLUMN_NAME_TYPE + " = ?", 
				new String[]{type}, 
				null, 
				null, 
				ExpertEntry.COLUMN_NAME_UID + ExpertDBHelper.DESC);
		
		List<Expert> rtnData = new ArrayList<Expert>();
		Expert data = null;
		int cnt;
		int numRows = c.getCount();
		c.moveToFirst();
		for(cnt = 0; cnt < numRows; cnt++){
			data = new Expert();
			data.setUid(c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_UID)));
			data.setUname(c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_UNAME)));
			data.setDate(c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_DATE)));
			data.setType(c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_TYPE)));
			data.setHost(c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_HOST)));
			data.setGuest(c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_GUEST)));
			data.setResult(Boolean.valueOf(c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_RESULT))));
			data.setP(c.getString(c.getColumnIndexOrThrow(ExpertEntry.COLUMN_NAME_P)));
			
			rtnData.add(data);
			c.moveToNext();
		}
		c.close();
		
		return rtnData;
	}
}
