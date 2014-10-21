package vargas.dgsd22.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R;
import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListAdapter extends BaseAdapter {
	
	List<Map<String, String>> listData = new ArrayList<Map<String,String>>();
	private LayoutInflater inflater;
	
	public ListAdapter(Context context, List<FirstActivity.SortResult> lst) {
		inflater = LayoutInflater.from(context);
		
		Map<String, String> map;
		for(int i = 0; i < lst.size(); i++){
			map = new HashMap<String, String>();
			map.put("RightPct", String.valueOf(lst.get(i).getPct()));
			map.put("RightP", String.valueOf(lst.get(i).getP()));
			map.put("Top", String.valueOf(lst.get(i).getUname()));
			map.put("BottomPct", String.valueOf(lst.get(i).getWin() + " / " + lst.get(i).getSum()));
			map.put("BottomP", String.valueOf(lst.get(i).getWin() + " / " + lst.get(i).getSumP()));
			
			listData.add(map);
		}
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ListItemViewHolder viewHolder;
		if(convertView == null){
			viewHolder = new ListItemViewHolder();
//			convertView = inflater.inflate(R.layout.)
			
		}
		
		return null;
	}
	
	

}
