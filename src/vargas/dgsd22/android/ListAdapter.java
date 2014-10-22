package vargas.dgsd22.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
			convertView = inflater.inflate(R.layout.list_item, null);
			viewHolder.tvRightPct = (TextView)convertView.findViewById(R.id.textViewRightPct);
			viewHolder.tvRightP = (TextView)convertView.findViewById(R.id.textViewRightP);
			viewHolder.tvTop = (TextView)convertView.findViewById(R.id.textViewTop);
			viewHolder.tvBottomPct = (TextView)convertView.findViewById(R.id.textViewBottomPct);
			viewHolder.tvBottomP = (TextView)convertView.findViewById(R.id.textViewBottomP);
			convertView.setTag(viewHolder);
			
		}else{
			
			viewHolder = (ListItemViewHolder)convertView.getTag();
		}
		viewHolder.tvRightPct.setText(listData.get(position).get("RightPct").toString());
		viewHolder.tvRightP.setText(listData.get(position).get("RightP").toString());
		if(Double.valueOf(listData.get(position).get("RightP")).compareTo(1.7) > 0){
			viewHolder.tvRightP.setTextColor(Color.MAGENTA);
		}else if(Double.valueOf(listData.get(position).get("RightP")).compareTo(1.5) > 0){
			viewHolder.tvRightP.setTextColor(Color.BLUE);
		}else{
			viewHolder.tvRightP.setTextColor(Color.BLACK);
		}
		viewHolder.tvTop.setText(listData.get(position).get("Top").toString());
		viewHolder.tvTop.setTextColor(Color.rgb(35, 150, 85));
		viewHolder.tvBottomPct.setText(listData.get(position).get("BottomPct").toString());
		viewHolder.tvBottomP.setText(listData.get(position).get("BottomP").toString());
		
		if(Integer.valueOf(listData.get(position).get("BottomPct").split(" / ")[1]) >= 20){
			convertView.setBackgroundColor(Color.rgb(255, 255, 180));
		}else{
			convertView.setBackgroundColor(Color.TRANSPARENT);
		}
		
		return convertView;
	}
}
