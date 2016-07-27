package com.cuiweiyou.interviewspitslot.util;

import com.cuiweiyou.interviewspitslot.R;
import com.cuiweiyou.interviewspitslot.activity.SpitslotAddActivity;
import com.cuiweiyou.interviewspitslot.app.RootApplication;
import com.cuiweiyou.interviewspitslot.back.CheckUserBack;
import com.cuiweiyou.interviewspitslot.task.UserCheckTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * <b>类名</b>: UserCheckUtil.java，检测用户状态  <br/>
 * <b>说明</b>: <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class UserCheckUtil {
	
	private UserCheckUtil(){}
	
	/**
	 * <b>功能</b>：checkUser，检测用户状态 <br/>
	 * <b>说明</b>: <br/>
	 * 
	 * @param ctx dialog的上下文，即其父activity
	 * @param aty 父activity
	 * @param back 回调器
	 * @param flag 标记。0为onresume时校验用户，1为发表口水时的校验 
	 */
	public static void checkUser(Context ctx, final Activity aty, final CheckUserBack back, final int flag) {
	    final AlertDialog dialog = new AlertDialog.Builder(ctx).create();
	    View view = View.inflate(ctx, R.layout.view_checkuser, null);
	    dialog.setView(view);
	    
	    final EditText username = (EditText) view.findViewById(R.id.username);
	    Button ok = (Button) view.findViewById(R.id.btn_adduser);
	    Button no = (Button) view.findViewById(R.id.btn_cancel);

	    ok.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            String name = username.getText().toString().replace(" ", "");
	            
	            if(null == name || "".equals(name)){
	            	Toast.makeText(RootApplication.getAppContext(), "要想红，请留名", 0).show();
	            	return;
	            } else {
	            	new UserCheckTask(aty, back, flag).execute(name);
	            
	            	dialog.dismiss();
	            }
	        }
	    });
	    no.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            dialog.dismiss();
	        }
	    });
	    
	    dialog.show();
	}

}
