package com.cuiweiyou.interviewspitslot.adapter;

import java.util.List;

import com.cuiweiyou.interviewspitslot.R;
import com.cuiweiyou.interviewspitslot.app.RootApplication;
import com.cuiweiyou.interviewspitslot.bean.SpitslotBean;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpitslotAdapter extends BaseAdapter {

	private List<SpitslotBean> list;

	public SpitslotAdapter(List<SpitslotBean> spitslots) {
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
			convertView = View.inflate(RootApplication.getAppContext(), R.layout.item_spitslot, null);
			holder.company_name = (TextView) convertView.findViewById(R.id.company_name);
			holder.station_name = (TextView) convertView.findViewById(R.id.station_name);
			holder.address = (TextView) convertView.findViewById(R.id.address);
			holder.user = (TextView) convertView.findViewById(R.id.inter);
			holder.date_view = (TextView) convertView.findViewById(R.id.date_view);
			holder.description = (TextView) convertView.findViewById(R.id.description);
			
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		SpitslotBean sbean = list.get(position);
		
		holder.company_name.setText(sbean.getCompany_name());
		holder.station_name.setText(sbean.getStation_name());
		holder.address.setText(sbean.getAddress());
		holder.user.setText(sbean.getUser_name());
		holder.date_view.setText(sbean.getDate_view());
		holder.description.setText(sbean.getDescription());
		
		return convertView;
	}

	class Holder{
		TextView company_name;
		TextView station_name;
		TextView address;
		TextView user;
		TextView date_view;
		TextView description;
	}
}
