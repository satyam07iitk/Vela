package com.vela.app.mobile.datamanager;


public class DataManager {



	private static DataManager dManager;

	public static DataManager getInstance() {

		if (dManager != null)
			return dManager;

		else {
			dManager = new DataManager();
			return dManager;
		}
	}
	public static void getInstance_1() {

		if (dManager != null)
			dManager=null;


	}

}
