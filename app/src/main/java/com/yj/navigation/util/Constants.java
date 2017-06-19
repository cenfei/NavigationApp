/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.yj.navigation.util;

import com.yj.navigation.R;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public final class Constants {

	public static final String[] IMAGES = new String[] {
"http://img00.hc360.com/security/201304/201304030954539941.jpg",

			"http://image.bitauto.com/dealer/news/100055432/0cf43f46-f67a-4c5e-aab0-2a15419207af.jpg",
			"http://img00.hc360.com/security/201304/201304030954539941.jpg",

			"http://image.bitauto.com/dealer/news/100055432/0cf43f46-f67a-4c5e-aab0-2a15419207af.jpg",
			"http://img00.hc360.com/security/201304/201304030954539941.jpg",

			"http://image.bitauto.com/dealer/news/100055432/0cf43f46-f67a-4c5e-aab0-2a15419207af.jpg"

//			"http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E8%BF%9D%E7%AB%A0&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=2502641762,2957330171&os=1243048344,1213622914&simid=4218746932,663776269&pn=62&rn=1&di=12393501010&ln=1974&fr=&fmq=1482328286003_R&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=1e&objurl=http%3A%2F%2Fimg2.ph.126.net%2FiiJYs7uz5c2EhGKKus39IA%3D%3D%2F2639953806669339992.jpg&rpstart=0&rpnum=0&adpicid=0",
//			"http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E8%BF%9D%E7%AB%A0&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=2412786967,1783131174&os=4088341194,1393635669&simid=0,0&pn=54&rn=1&di=45640473560&ln=1974&fr=&fmq=1482328286003_R&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fimg1.gtimg.com%2Fauto%2Fpics%2Fhv1%2F139%2F154%2F2072%2F134771209.jpg&rpstart=0&rpnum=0&adpicid=0",
//			"http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E8%BF%9D%E7%AB%A0&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=1799106203,2472889764&os=2244941141,1776944965&simid=3374979465,175719487&pn=46&rn=1&di=88946179630&ln=1974&fr=&fmq=1482328286003_R&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fpic.jxgdw.com%2FEasyCms_Images%2F2014-10-03%2F853381.png&rpstart=0&rpnum=0&adpicid=0",
//			"http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E8%BF%9D%E7%AB%A0&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=429984632,3222574850&os=3681947643,3144492296&simid=0,0&pn=25&rn=1&di=210873529350&ln=1974&fr=&fmq=1482328286003_R&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fwww.dianliang8.com%2Fuploadfile%2F20150103112417786.jpg&rpstart=0&rpnum=0&adpicid=0",
//			"http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E8%BF%9D%E7%AB%A0&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=2665391673,1208836486&os=2784056301,2880570446&simid=3434717382,283088986&pn=15&rn=1&di=36701951220&ln=1974&fr=&fmq=1482328286003_R&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fwww.kctv.org.cn%2Fimgall%2Foazc42lnm4xggy3uozygsyzomnxw2%2Fphotoworkspace%2Fcontentimg%2F2014%2F04%2F25%2F2014042507354374791.jpg&rpstart=0&rpnum=0&adpicid=0",
//			"http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E8%BF%9D%E7%AB%A0&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=80856652,1454848394&os=3313241607,3876979097&simid=3439234764,434780174&pn=10&rn=1&di=201238783240&ln=1974&fr=&fmq=1482328286003_R&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fimg1.100ye.com%2Fimg2%2F4%2F300%2F467%2F9450467%2Fmsgpic%2Fad9cd9ec719dc2b4e92108547f330362.jpg&rpstart=0&rpnum=0&adpicid=0"
			// Heavy images
//			"http://7xlwdb.com1.z0.glb.clouddn.com/large_005yyi5Jjw1el0d9jnk04j30dw0iiwi5.jpg",
//			"http://7xlwdb.com1.z0.glb.clouddn.com/large_005yyi5Jjw1el0d9odr6yj30dw0et0us.jpg",
//			"http://7xlwdb.com1.z0.glb.clouddn.com/large_005yyi5Jjw1el0d9p9dswj30dw0ii40o.jpg",
//			"http://7xlwdb.com1.z0.glb.clouddn.com/large_005yyi5Jjw1el0d9uzdx3j30dw0j3goq.jpg",
//			"http://7xlwdb.com1.z0.glb.clouddn.com/large_005yyi5Jjw1el0d9vu2lqj30dw0ii76p.jpg",
//			"http://7xlwdb.com1.z0.glb.clouddn.com/large_005yyi5Jjw1el0d9wmg5mj30dw0iiadj.jpg",
//			"http://7xlwdb.com1.z0.glb.clouddn.com/large_005yyi5Jjw1el0d9xh0q1j30dw0iitb5.jpg",
//			"http://7xlwdb.com1.z0.glb.clouddn.com/large_005yyi5Jjw1el0d9yf1osj30dw0ii76i.jpg",
//			"http://7xlwdb.com1.z0.glb.clouddn.com/large_005yyi5Jjw1el0d9zffycj30ck0hs0uk.jpg",
//			"http://7xlwdb.com1.z0.glb.clouddn.com/large_005yyi5Jjw1el0da0gigej30dw0ii41b.jpg",

	};

	private Constants() {
	}

	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}
	
	public static class Extra {
		public static final String FRAGMENT_INDEX = "com.nostra13.example.universalimageloader.FRAGMENT_INDEX";
		public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";
	}
}
