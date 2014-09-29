package vargas.dgsd22.android;

import java.util.List;

import vargas.dgsd22.android.db.ExpertContract;
import vargas.dgsd22.fetcher.ExpertFetcher;
import vargas.dgsd22.pojo.Expert;
import vargas.dgsd22.prop.PropertyManagerExpert;
import vargas.dgsd22.prop.PropertyManagerMatch;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class FirstActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void fetchData(View view){
		
		PropertyManagerExpert.init();
		PropertyManagerMatch.init();
	
		new Thread(){  
            public void run(){  
                new AnotherTask().execute();  
            }  
        }.start();
	}
	
	private class AnotherTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			
			//step1 fetch data
			ExpertFetcher fetcher = new ExpertFetcher();
			List<Expert> newRec = fetcher.getAllPojo();
			
			//step2 insert new record *
			ExpertContract ec = new ExpertContract(getApplicationContext());

			int len = newRec.size();
			String key;
			int newRecLen = 0;
			int cnt;
			
			String output;
			
			for(cnt = 0; cnt < len; cnt++){
				
				key = newRec.get(cnt).getUid() + "$" +
					newRec.get(cnt).getUname() + "$" +
					newRec.get(cnt).getDate() + "$" +
					newRec.get(cnt).getType() + "$" +
					newRec.get(cnt).getHost() + "$" +
					newRec.get(cnt).getGuest() + "$" +
					newRec.get(cnt).isResult() + "$" +
					newRec.get(cnt).getP();
					
				if(ec.searchExpert(newRec.get(cnt).getUid()).contains(key)){
					continue;
				}
				
				ec.addExpert(newRec.get(cnt));
				newRecLen = newRecLen + 1;
				output = newRec.get(cnt).getUid() + " | " +
						newRec.get(cnt).getUname() + " | " +
						newRec.get(cnt).getDate() + " | " +
						newRec.get(cnt).getType() + " | " +
						newRec.get(cnt).getHost() + " | " +
						newRec.get(cnt).getGuest() + " | " +
						newRec.get(cnt).isResult() + " | " +
						newRec.get(cnt).getP() + "";
				
				System.out.println(output);
						
			}
			ec.close();
			
			String result;
			result = "total fetch: " + newRecLen + " new records. ";
			
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			//step5 refresh view *
//			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.rowitem, output);
//			ListView list = (ListView)findViewById( R.id.ListView01 );
//			list.setAdapter(arrayAdapter);
			
			TextView tv = (TextView)findViewById(R.id.fetchResult);
			tv.setText(result);
		}
		
		
	}
}
