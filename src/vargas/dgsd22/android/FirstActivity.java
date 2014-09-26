package vargas.dgsd22.android;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import vargas.dgsd22.TestClass1;
import vargas.dgsd22.android.db.ExpertContract;
import vargas.dgsd22.fetcher.ExpertFetcher;
import vargas.dgsd22.pojo.Expert;
import vargas.dgsd22.prop.PropertyManagerExpert;
import vargas.dgsd22.prop.PropertyManagerMatch;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
	
		new Thread(
			new Runnable() {
				@Override
				public void run() {
					
					//step2 search last record *
					ExpertContract ec = new ExpertContract(getApplicationContext());
					Map<String, Expert> lastPubRec = ec.searchAllExpertLastRec();
					
					//step3 fetch data
					ExpertFetcher fetcher = new ExpertFetcher();
					List<Expert> newRec = fetcher.getAllPojo(lastPubRec);
					
					//step4 insert new record *
					Iterator<String> keySetIterator = lastPubRec.keySet().iterator();  
					while (keySetIterator.hasNext()) {  
						String key = keySetIterator.next();  
						ec.addExpertLastRec(lastPubRec.get(key));
					}  
					
					int len = newRec.size();
					String[] output = new String[len];
					
					int cnt;
					for(cnt = 0; cnt < len; cnt++){
						ec.addExpert(newRec.get(cnt));
						output[cnt] = newRec.get(cnt).getUid() + " \r\n" +
								newRec.get(cnt).getUname() + " \r\n" +
								newRec.get(cnt).getDate() + " \r\n" +
								newRec.get(cnt).getType() + " \r\n" +
								newRec.get(cnt).getHost() + " \r\n" +
								newRec.get(cnt).getGuest() + " \r\n" +
								newRec.get(cnt).isResult() + " \r\n" +
								newRec.get(cnt).getP() + " \r\n";
								
					}
					ec.close();
					
					//step5 refresh view *
//					ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.rowitem, output);
//					ListView list = (ListView)findViewById( R.id.ListView01 );
//					list.setAdapter(arrayAdapter);
					
					String result;
					result = "total fetch: " + cnt + " new records. ";
					
					TextView tv = (TextView)findViewById(R.id.fetchResult);
					tv.setText(result);
				}
			}
		).start();
	}
}
