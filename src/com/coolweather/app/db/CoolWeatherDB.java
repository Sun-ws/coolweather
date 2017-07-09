package com.coolweather.app.db;


import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {

	/*
	 *  数据库名
	 */
	public static final String DB_NAME="cool_weather";
	/*
	 * 数据库版本
	 */
	public static final int VERSION=1;
	
	private static CoolWeatherDB coolWeatherDB;
	private SQLiteDatabase db;
	
	
	/*
	 * 将构造方法私有化
	 */
	private  CoolWeatherDB(Context context) {
		// TODO Auto-generated constructor stub
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db=dbHelper.getWritableDatabase();
	}
	
	/*
	 * 获取CoolWeatherDB的实例
	 */
	public synchronized static CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB==null)
		{
			coolWeatherDB=new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	/*
	 * Province实例存储到数据库
	 */
	public void saveProvince(Province province) {
		// TODO Auto-generated method stub
		if(province!=null){
			ContentValues values=new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	
	/*
	 * 从数据库读取全国所有省份信息
	 */
	public List<Province> loadProvinces(){
		List<Province> list=new ArrayList<Province>();
		Cursor cursor=db.query("Province",null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province=new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			}while(cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
	
	/*
	 * 将City实例存储到数据集
	 */
	public void saveCity(City city) {
		// TODO Auto-generated method stub
		if(city!=null){
			ContentValues values=new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code",city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	
	/*
	 *  从数据库中读取某个省中所有城市的信息
	 */
	public List<City> loadCities(int provinceId){
		List<City> list=new ArrayList<City>();
		Cursor cursor=db.query("City", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city =new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
				list.add(city);
			}while(cursor.moveToNext());
			if(cursor!=null){
				cursor.close();
			}
		}
		return list;
	}
	
	/*
	 * 将实例County存储到数据库
	 */
	public void saveCounty(County county) {
		// TODO Auto-generated method stub
		 if(county!=null){
			 ContentValues values=new ContentValues();
			 values.put("county_id", county.getId());
			 values.put("county_name", county.getCountyName());
			 values.put("county_code", county.getCountyCode());
			 values.put("city_id", county.getCityId());
			 db.insert("County", null, values);
		 }	
	}
	
	/*
	 * 从数据库中读取某个城市的所有县的信息
	 */
	public List<County> loadCounties(int cityId){
		List<County> list=new ArrayList<County>();
		Cursor cursor=db.query("County", null, "city_id", new String[]{String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()){
			County county =new County();
			county.setCityId(cursor.getInt(cursor.getColumnIndex("id")));
			county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
			county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
			county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
			list.add(county);
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
	
	
}























