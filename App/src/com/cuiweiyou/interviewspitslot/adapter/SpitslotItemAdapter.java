package com.cuiweiyou.interviewspitslot.adapter;

import java.util.List;

import com.cuiweiyou.interviewspitslot.R;
import com.cuiweiyou.interviewspitslot.app.RootApplication;
import com.cuiweiyou.interviewspitslot.bean.SpitslotBean;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpitslotItemAdapter extends BaseAdapter {

	private List<SpitslotBean> list;

	public SpitslotItemAdapter(List<SpitslotBean> spitslots) {
		this.list = spitslots;
	}

	@Override
	public int getCount() {
		return list != null ? list.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(null == convertView){
			holder = new Holder();
			convertView = View.inflate(RootApplication.getAppContext(), R.layout.item_list_spitslot, null);
			holder.user = (TextView) convertView.findViewById(R.id.inter);
			holder.date_view = (TextView) convertView.findViewById(R.id.date_view);
			holder.description = (TextView) convertView.findViewById(R.id.description);
			
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		SpitslotBean sbean = list.get(position);
		
		holder.user.setText(sbean.getUser_name());
		holder.date_view.setText(sbean.getDate_view());
		holder.description.setText(sbean.getDescription());
		
		return convertView;
	}

	class Holder{
		TextView user;
		TextView date_view;
		TextView description;
	}
}
