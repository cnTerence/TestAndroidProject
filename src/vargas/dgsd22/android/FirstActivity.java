package vargas.dgsd22.android;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vargas.dgsd22.android.db.ExpertContract;
import vargas.dgsd22.fetcher.ExpertFetcher;
import vargas.dgsd22.pojo.Expert;
import vargas.dgsd22.prop.PropertyManagerExpert;
import vargas.dgsd22.prop.PropertyManagerMatch;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class FirstActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.type_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
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
		
		//try{
			PropertyManagerExpert.init();
			PropertyManagerMatch.init();
		
			new Thread(){  
	            public void run(){  
	                new FetchDataTask().execute();  
	            }  
	        }.start();
		//}catch(Exception e){
		//	new AlertDialog.Builder(getApplicationContext()).setMessage(e.getMessage() + "\r\n" + e.getCause().toString()).setPositiveButton("ok", null).show();
		//}
	}
	
	private class FetchDataTask extends AsyncTask<String, Void, String>{

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
			tv.setMaxLines(10);
			tv.setMovementMethod(ScrollingMovementMethod.getInstance());
			tv.scrollTo(0, 0);
			
			//new AlertDialog.Builder(getApplicationContext()).setMessage("fetch complete").setPositiveButton("ok", null).show();
		}
	}
	
	public void sortData(View view){
		
		//try{
			ExpertContract ec = new ExpertContract(getApplicationContext());
			
			Spinner spinner = (Spinner)findViewById(R.id.spinner);
			if(spinner.getSelectedItem().toString().equals("ALL")){
				List<Expert> allExperts = ec.searchAllExpert();
				doSort(allExperts);
			}else{
				List<Expert> allExperts = ec.searchExpertByType(spinner.getSelectedItem().toString());
				doSort(allExperts);
			}
		//}catch(Exception e){
		//	new AlertDialog.Builder(getApplicationContext()).setMessage(e.getMessage() + "\r\n" + e.getCause().toString()).setPositiveButton("ok", null).show();
		//}
	}
	
	private void doSort(List<Expert> allExperts){
		int len = allExperts.size();
		int cnt;
		Map<String, SortResult> sortResult = new HashMap<String, FirstActivity.SortResult>();
		SortResult thisResult;
		Expert thisExpert;
		
		if(allExperts.size() == 0){
			TextView tv = (TextView)findViewById(R.id.fetchResult);
			tv.setText("no data to sort.");
			tv.setMaxLines(1);
			tv.setMovementMethod(ScrollingMovementMethod.getInstance()); 
			return;
		}
		
		for(cnt = 0; cnt < len; cnt++){
			thisExpert = allExperts.get(cnt);
			if(sortResult.containsKey(thisExpert.getUid())){
				thisResult = sortResult.get(thisExpert.getUid());
			}else{
				thisResult = new SortResult();
				thisResult.setUid(thisExpert.getUid());
				thisResult.setUname(thisExpert.getUname());
				thisResult.setPct(0);
				thisResult.setSum(0);
				thisResult.setWin(0);
				thisResult.setP(0);
				thisResult.setSumP(0);
			}
			
			if(thisExpert.isResult()){
				thisResult.setWin(thisResult.getWin() + 1);
				thisResult.setSumP(thisResult.getSumP() + Double.valueOf(thisExpert.getP().replace("SP:", "")));
				thisResult.setP(thisResult.getSumP() / thisResult.getWin());
			}
			thisResult.setSum(thisResult.getSum() + 1);
			thisResult.setPct((double)thisResult.getWin() / thisResult.getSum() * 100);
			
			sortResult.put(thisExpert.getUid(), thisResult);
		}
		
		List<SortResult> finalResult = Arrays.asList(sortResult.values().toArray(new SortResult[]{}));
		Collections.sort(finalResult);
		
		DecimalFormat dFormat = new DecimalFormat("#.00");
		String output = "";
		len = finalResult.size();
		for(cnt = 0; cnt < len; cnt++){
			output += finalResult.get(cnt).getUname() + "-|--" +
					dFormat.format(finalResult.get(cnt).getPct()) + "-|--" +
					finalResult.get(cnt).getWin() + "-|--" +
					finalResult.get(cnt).getSum() + "-|--" +
					dFormat.format(finalResult.get(cnt).getP()) + "-|--" +
					dFormat.format(finalResult.get(cnt).getSumP()) + "\r\n\r\n";
			
		}
		
		TextView tv = (TextView)findViewById(R.id.fetchResult);
		tv.setText(output);
		tv.setMaxLines(len * 2);
		tv.setMovementMethod(ScrollingMovementMethod.getInstance()); 
		tv.scrollTo(0, 0);
	}
	
	
	public class SortResult implements Comparable<SortResult>{
		private String uid;
		private String uname;
		private double pct;
		private int win;
		private int sum;
		private double p;
		private double sumP;
		
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getUname() {
			return uname;
		}
		public void setUname(String uname) {
			this.uname = uname;
		}
		public double getPct() {
			return pct;
		}
		public void setPct(double pct) {
			this.pct = pct;
		}
		public int getWin() {
			return win;
		}
		public void setWin(int win) {
			this.win = win;
		}
		public int getSum() {
			return sum;
		}
		public void setSum(int sum) {
			this.sum = sum;
		}
		public double getP() {
			return p;
		}
		public void setP(double p) {
			this.p = p;
		}
		public double getSumP() {
			return sumP;
		}
		public void setSumP(double sumP) {
			this.sumP = sumP;
		}
		@Override
		public int compareTo(SortResult another) {
			if(this.pct > another.pct){
				return -1;
			}else if(this.pct == another.pct){
				if(this.sum > another.sum){
					return -1;
				}else if(this.sum == another.sum){
					if(this.p > another.p){
						return -1;
					}else if(this.p == another.p){
						return 0;
					}else{
						return 1;
					}
				}else{
					return 1;
				}
			}else{
				return 1;
			}
		}
		
		
	}
}
