package com.yj.navigation.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 
 * 
 * @类名：StringUtils.java
 * 
 * @功能说明：字符串工具类
 * 
 * @创建人： foxcen
 * 
 * @创建日期： #build 2013-12-22 -0
 * 
 * @修改人： foxcen
 * 
 * @修改日期： #change 2013-12-22 -0
 * 
 * @版本号：1.00
 */
@SuppressLint("SimpleDateFormat")
public class MyStringUtils {


	public static String  getOpesTypeTitle(String opentypeStr){
		String description=null;

		Integer opentype=0;
		try {
			opentype=Integer.valueOf(opentypeStr);

		}catch (Exception e){

			return "";
		}


		switch (opentype){

			case 1:
				description = "一审中";
				break;
			case 2:
				description ="一审打回";
				break;
			case 3:
				description = "二审中";
				break;
			case 4:
				description = "二审打回";
				break;
			case 5:
				description = "复核中";
				break;
			case 6:
				description = "复核打回";
				break;
			case 7:
				description = "复核通过";
				break;
			default:
				break;
		}

		return description;




	}



	public static String  getOpesTypeContent(String opentypeStr,String operator,String remark){
		String description=null;
		if(TextUtils.isEmpty(operator)) operator="某某";
		if(TextUtils.isEmpty(remark)) remark="不详";
		Integer opentype=0;
			try {
				opentype=Integer.valueOf(opentypeStr);

			}catch (Exception e){

				return "";
			}


		switch (opentype){

		case 1:
		description = "您已经提交了一个工单,正在等待审核";
		break;
		case 2:
		description ="您的工单在初次审核中被审核员:"+operator+"驳回，原因如下："+ remark;
		break;
		case 3:
		description = "您工单被审核员:"+operator+"通过，等待二审";
		break;
		case 4:
		description = "您的工单在二次审核中被审核员:"+operator+"驳回，原因如下："+ remark;
		break;
		case 5:
		description = "您工单在二次审核中被审核员:"+operator+"通过，等待复核";
		break;
		case 6:
		description = "您的工单在复核中被审核员:"+operator+"驳回，原因如下："+remark;
		break;
		case 7:
		description = "复核通过，等待交警结算资金";
		break;
		default:
		break;
	}

	return description;




	}
	
	
	public static Bitmap decodeStreamBitmap(Context context,int drawId){
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		 opt.inSampleSize=2;
       opt.inPurgeable = true;

       opt.inInputShareable = true;

     
		 // 获取资源图片
		 InputStream is =
		 context.getResources().openRawResource(drawId);
		 return  BitmapFactory.decodeStream(is, null, opt);
	}
	/**
	 * 
	 * 
	 * @功能说明：字符串不为null且不为“”
	 * 
	 * 
	 * @返回类型：boolean
	 */
	public static boolean isNotNullAndEmpty(Object param) {

		if (null != param && !param.equals("") && !param.equals("null")) {
			return true;
		}

		return false;

	}

	/**
	 * ryan
	 * 
	 * @功能说明：获取当前时间String"yyyyMMdd"
	 * 
	 * @返回类型：String "yyy-mm-dd"
	 */
	public static String getNowTimeFormat_ryan(String string) {

		String a = string.substring(0, 4) + "-" + string.substring(4, 6) + "-"
				+ string.substring(6, 8);

		return a;

	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间"yyyy-MM-dd"
	 * 
	 * @返回类型：String
	 */
	public static String getNowTimeFormat1(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟

		return sdf.format(date);

	}
	
	/**
	 * 
	 * 
	 * @功能说明：获取当前时间"yyyy-MM-dd"
	 * 
	 * @返回类型：String
	 */
	public static String getNowTimeFormat20(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 小写的mm表示的是分钟

		return sdf.format(date);

	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间"yyyyMMdd"
	 * 
	 * @返回类型：String
	 */
	public static String getNowTimeFormata(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");// 小写的mm表示的是分钟

		return sdf.format(date);

	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间"yyyy-MM-dd" 转成 yyyyMMdd
	 * 
	 * @返回类型：String
	 */
	public static String getTimeConvertTimeOther(String dateStr) {
		Date dateCon = getFormatDate(dateStr);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");// 小写的mm表示的是分钟

		return sdf.format(dateCon);

	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间---"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @返回类型：String
	 */
	public static String getNowTimeFormat2(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟

		return sdf.format(date);

	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间---"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @返回类型：String
	 */
	public static Date getNowTimeFormat10(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟

		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		return null;
	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间---"yyyyMMddHHmmss"
	 * 
	 * @返回类型：String
	 */
	public static String getNowTimeFormat7(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// 小写的mm表示的是分钟

		return sdf.format(date);

	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间---"yyyyMMddHHmmss"
	 * 
	 * @返回类型：String
	 */
	public static Date getNowTimeFormat9(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// 小写的mm表示的是分钟

		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		return null;
	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间---"yyyy-MM-dd HH:mm:ss" 转成 yyyy-MM-dd
	 * 
	 * @返回类型：String
	 */
	public static String getNowTimeFormat5(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟

		try {
			return getNowTimeFormat1(sdf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间---"yyyy-MM-dd HH:mm:ss" 转成 yyyy-MM-dd
	 * 
	 * @返回类型：String
	 */
	public static Date getNowTimeFormat8(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟

		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间---"yyyy-MM-dd HH:mm:ss" 转成 yyyy-MM-dd
	 * 
	 * @返回类型：String
	 */
	public static String getNowTimeFormat6(String date) {
		String returnStr = null;
		if (MyStringUtils.isNotNullAndEmpty(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟

			try {
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 小写的mm表示的是分钟

				returnStr = sdf2.format(sdf.parse(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return returnStr;
	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间---"yyyy-MM-dd"
	 * 
	 * @返回类型：String
	 */
	public static Date getFormatDate(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
		Date date0 = null;
		try {
			date0 = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date0;
	}

	public static Date getFormatDate2(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date0 = null;
		try {
			date0 = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date0;
	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间---"yyyyMMdd"
	 * 
	 * @返回类型：String
	 */
	public static Date lbgetFormatDate(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");// 小写的mm表示的是分钟
		Date date0 = null;
		try {
			date0 = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date0;
	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间---"yyyyMMdd"
	 * 
	 * @返回类型：String
	 */
	public static Date lbmonthgetFormatDate(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");// 小写的mm表示的是分钟
		Date date0 = null;
		try {
			date0 = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date0;
	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间yyyyMMddHHmmss转成---"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @返回类型：String
	 */
	public static String getFormatDate4(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// 小写的mm表示的是分钟
		Date date0 = null;
		try {
			date0 = sdf.parse(date);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getNowTimeFormat2(date0);
	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间"yyyy-MM-dd"转成yyyyMMdd
	 * 
	 * @返回类型：String
	 */
	public static String getDate1FormatDate2(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");// 小写的mm表示的是分钟
		Date date0 = null;
		try {
			date0 = sdf.parse(date);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdf2.format(date0);
	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间"yyyy-MM-dd"转成yyyyMMdd
	 * 
	 * @返回类型：String
	 */
	public static String getDate1FormatDate2Convert(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");// 小写的mm表示的是分钟
		Date date0 = null;
		try {
			date0 = sdf2.parse(date);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdf.format(date0);
	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间"yyyy-MM-dd"转成yyyyMMdd
	 * 
	 * @返回类型：String
	 */
	public static Date getDate2FormatDate1(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");// 小写的mm表示的是分钟
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
		Date date0 = null;
		try {
			date0 = sdf.parse(date);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getFormatDate(sdf2.format(date0));
	}

	/**
	 * 
	 * 
	 * @功能说明：获取当前时间---"yyyy-MM-dd"
	 * 
	 * @返回类型：String
	 */
	public static Date getFormatDate3(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");// 小写的mm表示的是分钟
		Date date0 = null;
		try {
			date0 = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date0;
	}

	/**
	 * 
	 * 
	 * @功能说明：将double型数据截取至dotNum位
	 * 
	 * @返回类型：double
	 */
	// public static double getDoubleDotValue(Double sameAgeDanger, Integer
	// dotNum) {
	//
	// System.out.println(Math.round(sameAgeDanger * dotNum) / dotNum);
	// return (double) ((Math.round(sameAgeDanger * dotNum)) / dotNum);
	// }

	/**
	 * 
	 * 
	 * @功能说明：两date相减的天数date1大
	 * 
	 * @返回类型：Integer
	 */
	public static Long getSubtractDate(Date date1, Date date2) {
		Calendar nowDate = Calendar.getInstance(), oldDate = Calendar
				.getInstance();

		nowDate.setTime(date1);// 设置为当前系统时间
		oldDate.setTime(date2);// 设置为1990年（6）月29日

		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);

		oldDate.set(Calendar.HOUR_OF_DAY, 0);
		oldDate.set(Calendar.MINUTE, 0);
		oldDate.set(Calendar.SECOND, 0);
		oldDate.set(Calendar.MILLISECOND, 0);

		long timeNow = nowDate.getTimeInMillis();
		long timeOld = oldDate.getTimeInMillis();
		long subDay = (timeNow - timeOld) / (1000 * 60 * 60 * 24);// 化为天
		System.out.println("相隔" + subDay + "天");
		return subDay;
	}

	/**
	 * 
	 * 
	 * @功能说明：两date相减的天数date1大
	 * 
	 * @返回类型：Integer
	 */
	public static Boolean getBigDate(Date date1, Date date2) {

		Boolean dateReturn = false;
		Calendar nowDate = Calendar.getInstance(), oldDate = Calendar
				.getInstance();
		nowDate.setTime(date1);
		oldDate.setTime(date2);
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);

		oldDate.set(Calendar.HOUR_OF_DAY, 0);
		oldDate.set(Calendar.MINUTE, 0);
		oldDate.set(Calendar.SECOND, 0);

		oldDate.set(Calendar.MILLISECOND, 0);

		if (nowDate.before(oldDate) ) {
			dateReturn = true;
		} else {
			dateReturn = false;
		}
		return dateReturn;
	}

	/**
	 * 
	 * 
	 * @功能说明：日期加减几天
	 * 
	 * @返回类型：Integer
	 */
	public static Date getMoveDate(Date date1, Integer dateNum) {
		Calendar nowDate = Calendar.getInstance();

		nowDate.setTime(date1);// 设置为当前系统时间

		nowDate.add(Calendar.DATE, dateNum);

		return nowDate.getTime();
	}

	/**
	 * 
	 * 
	 * @功能说明：日期加减几天 date1 yyyy-MM-dd
	 * 
	 * @返回类型：Integer
	 */
	public static String getMoveDateStr(String date1, Integer dateNum) {
		Calendar nowDate = Calendar.getInstance();

		nowDate.setTime(MyStringUtils.getFormatDate(date1));// 设置为当前系统时间

		nowDate.add(Calendar.DATE, dateNum);

		return MyStringUtils.getNowTimeFormat1(nowDate.getTime());
	}



	/**
	 * 
	 * 
	 * @功能说明：将double型数据截取至dotNum位,如：dotnum="0.00" 满足四舍五入
	 * 
	 * @返回类型：double
	 */
	public static Double getDoubleDecimal(Double downValue, String dotNum) {
		String db = null;
		try {

			// Double sameAgeDanger = Double.valueOf(upValue)
			// / Double.valueOf(downValue);
			DecimalFormat df = new DecimalFormat(dotNum);
			db = df.format(downValue);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getDoubleDecimal" + e);
		}
		return Double.valueOf(db);

	}
	public static String getDoubleDecimalString(Double downValue, String dotNum) {
		String db = null;
		try {

			// Double sameAgeDanger = Double.valueOf(upValue)
			// / Double.valueOf(downValue);
			DecimalFormat df = new DecimalFormat(dotNum);
			db = df.format(downValue);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getDoubleDecimal" + e);
		}
		return db;

	}
	/**
	 * 
	 * 
	 * @功能说明：将Float型数据截取至dotNum位,如：dotnum="0.00" 满足四舍五入
	 * 
	 * @返回类型：Float
	 */
	public static Float getFloatDecimal(Float downValue, String dotNum) {
		String db = null;
		try {

			// Double sameAgeDanger = Double.valueOf(upValue)
			// / Double.valueOf(downValue);
			DecimalFormat df = new DecimalFormat(dotNum);
			db = df.format(downValue);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getDoubleDecimal" + e);
		}
		return Float.valueOf(db);

	}

	/**
	 * 
	 * 
	 * @功能说明：将double型数据截取至dotNum位,如：dotnum="0.00" 满足四舍五入
	 * 
	 * @返回类型：double
	 */
	public static Integer getDoubleIntDecimal(Double downValue, String dotNum) {
		String db = null;
		try {

			// Double sameAgeDanger = Double.valueOf(upValue)
			// / Double.valueOf(downValue);
			DecimalFormat df = new DecimalFormat(dotNum);
			db = df.format(downValue);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("getDoubleDecimal" + e);
		}
		return Double.valueOf(db).intValue();

	}

	public static void main(String[] args) {
		Integer aaInteger = 2;
		Integer bbInteger = 7;

		Double cc = Double.valueOf(aaInteger) / Double.valueOf(bbInteger);
		cc=100.0;
		DecimalFormat df = new DecimalFormat("0.00");
		// double d = 123.9078;
		String db = df.format(cc);
		System.out.println(Double.valueOf(db));
		System.out.println(cc);
	}

	public static Integer xx(int a) {
		int x = a / 60;
		return x;
	}

	public static Integer yy(int a) {
		int x = a / 60;
		int y = a - x * 60;
		return y;
	}
//	/**
//	 * 功能：判断当前版本是否有可更新的固件 根据传入的设备版本号，决定更新的固件，117及以下的更新117 200以上的固件更新最新的210
//	 *
//	 * @param versionid
//	 * @return
//	 */
//	public static Map<String, String> isNewestVersion(String token,Integer versionid,Context context) {
//		Integer updateversionid=0;
//		Map<String, String> mapversion=new HashMap<String, String>();
////		if (versionid>0&&versionid < API.RING_FIRST_VERSION_NEWEST){
////			updateversionid=API.RING_FIRST_VERSION_NEWEST;
////		}else if (versionid > API.RING_FIRST_VERSION_NEWEST && versionid < API.RING_SECOND_VERSION_NEWEST) {
////			updateversionid=API.RING_SECOND_VERSION_NEWEST;
////		}
////		if(versionid==117){
////			updateversionid=116;
////		}
////		if(versionid==210){
////			updateversionid=208;
////		}
//		if(versionid==0){
//			return mapversion;
//		}
//
//
//		//在这里调用是否升级的接口
//		Map<String, String> parmaMap = new HashMap<String, String>();
//
//		parmaMap.put("withData","0");
//		parmaMap.put("firmwareVersion",versionid+"");
//		parmaMap.put("accessToken",token);
//
//		String result=null;
//		try{
//		 result = Caller.dPosta(API.UPDATE_DATA_DATE_VERSION_URL,
//                 parmaMap);
//		 GsonBuilder gsonBuilder = new GsonBuilder();
//			Gson gson = gsonBuilder.create();
//
//			 mapversion = gson.fromJson(result,
//					new TypeToken<Map<String, String>>() {
//					}.getType());
////		mapversion.get("upgradeVersion");
////			mapversion.get("currentVersion");
////			 firmwareData=mapversion.get("firmwareData");
//		}
//		catch(Exception e){
//			System.out.println("获取是否升级接口时错误"+e);
//			e.printStackTrace();
//		}
//
//		return mapversion;
//	}
	// fox新增
	public static byte[] readBinFileToByteArray(Context mContext,Integer isUpdateNewestVersion) {
		InputStream is = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			AssetManager am;
			am = mContext.getResources().getAssets();

			if(isUpdateNewestVersion==117){
				is = am.open("Q1-app-117.bin");
			}else if(isUpdateNewestVersion==210){
				is = am.open("P087A_V210_20150127.bin");
			}
			else if(isUpdateNewestVersion==208){
				is = am.open("P087A_V208_20150122.bin");
			}
			else if(isUpdateNewestVersion==116){
				is = am.open("Q1-app-116.bin");
			}
		

			byte[] b = new byte[1024];
			int n;
			while ((n = is.read(b)) != -1) {
				out.write(b, 0, n);
			}// end while
		} catch (Exception e) {
			Log.e("TimingMmsService.error", "TimingMmsService.error", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					Log.e("finally.error", "finally.error", e);
				}// end try
			}// end if
		}// end try

		return out.toByteArray();
	}


	public static String formatDate1(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟

		return sdf.format(date);

	}

//	public static String getPeriodDisplayString(Context context,String period){
//		String result = "";
//		if(period == null || period.isEmpty() || period.toUpperCase().equals("NULL"))
//			result = context.getResources().getString(R.string.never);
//		else{
//			boolean one = period.contains("一");
//			boolean two = period.contains("二");
//			boolean three = period.contains("三");
//			boolean four = period.contains("四");
//			boolean five = period.contains("五");
//			boolean six = period.contains("六");
//			boolean seven = period.contains("日");
//
//			if(one && two && three && four && five && six && seven){
//				result = context.getString(R.string.setting_period_everyday);
//			}else if(one && two && three && four && five && !six && !seven){
//				result = context.getString(R.string.setting_period_workingday);
//			}else if(!one && !two && !three && !four && !five && six && seven){
//				result = context.getString(R.string.setting_period_weekend);
//			}else{
//				result = period;
//			}
//		}
//		return result;
//	}

	/**
	 *
	 *
	 * @功能说明：获取当前时间---"yyyy-MM-dd HH:mm:ss" 转成 yyyy-MM-dd
	 *
	 * @返回类型：String
	 */
	public static long getNowTimeFormatToLong(String date) {
		long returnStr = System.currentTimeMillis();
		if (MyStringUtils.isNotNullAndEmpty(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟

			try {
				returnStr = sdf.parse(date).getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return returnStr;
		}else{
			return System.currentTimeMillis();
		}

	}

}
