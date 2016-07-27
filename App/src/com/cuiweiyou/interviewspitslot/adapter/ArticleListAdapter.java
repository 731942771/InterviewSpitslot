package com.cuiweiyou.interviewspitslot.adapter;

import java.util.List;

import com.cuiweiyou.interviewspitslot.R;
import com.cuiweiyou.interviewspitslot.app.RootApplication;
import com.cuiweiyou.interviewspitslot.bean.ArticleBean;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArticleListAdapter extends BaseAdapter {

	private List<ArticleBean> list;

	public ArticleListAdapter(List<ArticleBean> list) {
		this.list = list;
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
			convertView = View.inflate(RootApplication.getAppContext(), R.layout.item_list_article, null);
			holder.writer = (TextView) convertView.findViewById(R.id.writer);
			holder.praise = (TextView) convertView.findViewById(R.id.praise);
			holder.article = (TextView) convertView.findViewById(R.id.article);
			
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		ArticleBean bean = list.get(position);
		
		holder.writer.setText(bean.getUser_name());
		holder.praise.setText(bean.getPraise_count() + "");
		holder.article.setText(bean.getTitle());
		
		return convertView;
	}

	class Holder{
		TextView writer;
		TextView praise;
		TextView article;
	}
}
