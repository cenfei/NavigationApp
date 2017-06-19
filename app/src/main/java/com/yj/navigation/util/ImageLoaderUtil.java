package com.yj.navigation.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.yj.navigation.R;


/**
 * Created by Sam on 2015/8/4.
 */
public class ImageLoaderUtil {
    private static DisplayImageOptions avatarOptions = null;
    private static DisplayImageOptions bannerOptions = null;
    private static DisplayImageOptions personalBannerOptions = null;
    private static DisplayImageOptions wechatOptions = null;
    private static DisplayImageOptions serverOptions = null;
    private static DisplayImageOptions runAdOptions = null;
    private static DisplayImageOptions medalOptions = null;

    public static void initAdLoader(Context context){

        ImageLoaderConfiguration loaderConfig = new ImageLoaderConfiguration.Builder(context)
                .imageDownloader(new BaseImageDownloader(context,2000,3000))
                .build();


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) //default = device screen dimensions 内存缓存文件的最大长宽
//                .diskCacheExtraOptions(480, 800, Bitmap.CompressFormat.JPEG, 75, null)// 本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个
//                .taskExecutor(...)
//        .taskExecutorForCachedImages(...)
        .threadPoolSize(1) // default  线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 1) // default设置当前线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))//可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)// 内存缓存的最大值
                .memoryCacheSizePercentage(13) // default
//                .diskCache(new UnlimitedDiscCache(cacheDir)) // default 可以自定义缓存路径
                .diskCacheSize(50 * 1024 * 1024)// 50 Mb sd卡(本地)缓存的最大值
                .diskCacheFileCount(100)// 可以缓存的文件数量
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())//还可以用MD5(new Md5FileNameGenerator())加密
                .imageDownloader(new BaseImageDownloader(context)) // default
//                .imageDecoder(new BaseImageDecoder())// default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default 默认的显示图片配置，可以理解为一般客户的口味（可以理解为上面厨房比喻）
//                .writeDebugLogs()// 打印debug log
                .build();


        ImageLoader.getInstance().init(config);
    }

    public static void initDefaulLoader(Context context) {
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
    }

    public static DisplayImageOptions getAvatarOptionsInstance(){
        if(avatarOptions == null){
            avatarOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.rounded_gray_wallet_img) //设置图片在下载期间显示的图片
                    .showImageForEmptyUri(R.drawable.rounded_gray_wallet_img)//设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.rounded_gray_wallet_img)  //设置图片加载/解码过程中错误时候显示的图片
            .cacheInMemory(false)//设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
            .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
            .displayer(new SimpleBitmapDisplayer())

            .build();//构建完成
        }
        return  avatarOptions;
    }

    public static DisplayImageOptions getBannerOptionsInstance(){
        if(bannerOptions == null){
            bannerOptions = new DisplayImageOptions.Builder()
//                    .showImageOnLoading(R.drawable.car1) //设置图片在下载期间显示的图片
//                    .showImageForEmptyUri(R.drawable.car1)//设置图片Uri为空或是错误的时候显示的图片
//                    .showImageOnFail(R.drawable.car1)  //设置图片加载/解码过程中错误时候显示的图片
                    .cacheInMemory(false)//设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                    .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                    .displayer(new SimpleBitmapDisplayer())
                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT)

                    .build();//构建完成
        }
        return  bannerOptions;
    }

    public static DisplayImageOptions getPersonalBannerOptionsInstance(){
        if(personalBannerOptions == null){
            personalBannerOptions = new DisplayImageOptions.Builder()
//                    .showImageOnLoading(R.drawable.rounded_gray_wallet_img) //设置图片在下载期间显示的图片
//                    .showImageForEmptyUri(R.drawable.rounded_gray_wallet_img)//设置图片Uri为空或是错误的时候显示的图片
//                    .showImageOnFail(R.drawable.rounded_gray_wallet_img)  //设置图片加载/解码过程中错误时候显示的图片
                    .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                    .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                    .displayer(new SimpleBitmapDisplayer())

                    .build();//构建完成
        }
        return  personalBannerOptions;
    }

    public static DisplayImageOptions getRoundedOptionsInstance(int roundedNum){
        if(wechatOptions == null){
            wechatOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.rounded_gray_wallet_img) //设置图片在下载期间显示的图片
                    .showImageForEmptyUri(R.drawable.rounded_gray_wallet_img)//设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.rounded_gray_wallet_img)  //设置图片加载/解码过程中错误时候显示的图片
                    .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                    .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                    .displayer(new RoundedBitmapDisplayer(roundedNum))
                    .build();//构建完成
        }
        return  wechatOptions;
    }

    public static DisplayImageOptions getServerrOptionsInstance(){
        if(serverOptions == null){
            serverOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.rounded_gray_wallet_img) //设置图片在下载期间显示的图片
                    .showImageForEmptyUri(R.drawable.rounded_gray_wallet_img)//设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.rounded_gray_wallet_img)  //设置图片加载/解码过程中错误时候显示的图片
                    .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                    .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                    .displayer(new SimpleBitmapDisplayer())
                    .build();//构建完成
        }
        return  serverOptions;
    }

    public static DisplayImageOptions getRunAdOptionsInstance(){
        if(runAdOptions == null){
            runAdOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.rounded_gray_wallet_img) //设置图片在下载期间显示的图片
                    .showImageForEmptyUri(R.drawable.rounded_gray_wallet_img)//设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.rounded_gray_wallet_img)  //设置图片加载/解码过程中错误时候显示的图片
                    .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                    .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                    .displayer(new SimpleBitmapDisplayer())
                    .build();//构建完成
        }
        return  runAdOptions;
    }

    public static DisplayImageOptions getMedalOptionsInstance(){
        if(medalOptions == null){
            medalOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.rounded_gray_wallet_img) //设置图片在下载期间显示的图片
                    .showImageForEmptyUri(R.drawable.rounded_gray_wallet_img)//设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.rounded_gray_wallet_img)  //设置图片加载/解码过程中错误时候显示的图片
                    .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                    .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                    .displayer(new SimpleBitmapDisplayer())
                    .build();//构建完成
        }
        return  medalOptions;
    }
}
