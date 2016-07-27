package com.cuiweiyou.interviewspitslot.adapter;

import java.util.List;

import com.cuiweiyou.interviewspitslot.R;
import com.cuiweiyou.interviewspitslot.adapter.SpitslotAdapter.Holder;
import com.cuiweiyou.interviewspitslot.app.RootApplication;
import com.cuiweiyou.interviewspitslot.bean.CompanyBean;
import com.cuiweiyou.interviewspitslot.bean.SpitslotBean;
import com.cuiweiyou.interviewspitslot.bean.StationBean;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StationAdapter extends BaseAdapter {

	private List<?> list;
	/** flag=0公司，1职位 */
	private int flag;

	/** flag=0公司，1职位 */
	public StationAdapter(List<?> list, int flag) {
		this.list = list;
		this.flag = flag;
	}

	@Override
	public int getCount() {
		return list.size();
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
			convertView = View.inflate(RootApplication.getAppContext(), R.layout.item_autoc, null);
			holder.station_name = (TextView) convertView.findViewById(R.id.item_autoc_name);
			
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		if(0 == flag){
			CompanyBean b = (CompanyBean) list.get(position);
			holder.station_name.setText(b.getName());
		} else {
			StationBean sbean = (StationBean) list.get(position);
			holder.station_name.setText(sbean.getName());
		}
		
		return convertView;
	}

	class Holder{
		TextView station_name;
	}
}
