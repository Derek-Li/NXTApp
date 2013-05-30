package com.example.slidingmenuexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.example.slidingmenuexample.ui.NXTRemoteControl;

public class MenuListPages extends SherlockListFragment {
	
	String[] menu_pages = {"Home", "Remote Control", "Sharing"};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return inflater.inflate(R.layout.menu, container, false);
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	    super.onListItemClick(l, v, position, id);
	    
	    switch(position) {
	    case 0: 
	    	startActivity(new Intent("com.example.slidingmenuexample.ui.MainActivity"));
			break;
	    case 1:
	    	getActivity().finish();
	    	startActivity(new Intent("com.example.slidingmenuexample.ui.NXTRemoteControl"));
			break;
	    case 2:
			startActivity(new Intent("com.example.slidingmenuexample.ui.SocialNetworking"));
			break;
		default:
			break;
	    }
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){ 
		super.onActivityCreated(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, menu_pages));
	}
	
}
