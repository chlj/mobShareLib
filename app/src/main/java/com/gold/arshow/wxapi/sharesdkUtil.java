package com.gold.arshow.wxapi;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gold.arshow.R;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * Created by Administrator on 2017/4/7.
 */

public class sharesdkUtil {

    /**
     *
     * @param context
     * @param title 标题
     * @param pic 网络图片  新版本2.8.2
     * @param url 点击后的链接网址
     * @param content 内容
     * @param mHandler
     */
    public static void ShareByCustomByMy(final Context context, final String title, final String pic, final String url, final String content, final Handler mHandler) {

        final AlertDialog dlg = new AlertDialog.Builder(context, R.style.dialog).create();
        dlg.setView(LayoutInflater.from(context).inflate(
                R.layout.view_sharesdk, null));
        dlg.setCanceledOnTouchOutside(true);
        dlg.show();
        final Window window = dlg.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setContentView(R.layout.view_sharesdk);
        WindowManager.LayoutParams layparam = window.getAttributes();
        layparam.width = context.getResources().getDisplayMetrics().widthPixels;//getWindowManager().getDefaultDisplay().getWidth();
        window.setWindowAnimations(R.style.commentAnimation); //设置动画
        window.setAttributes(layparam);



        final ImageView img_closed = (ImageView) window
                .findViewById(R.id.img_closed);
        img_closed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
        final LinearLayout lin_friend = (LinearLayout) window
                .findViewById(R.id.lin_friend);

        final LinearLayout lin_wxin = (LinearLayout) window
                .findViewById(R.id.lin_wxin);


        final LinearLayout lin_qq = (LinearLayout) window
                .findViewById(R.id.lin_qq);

        final LinearLayout lin_qzone = (LinearLayout) window
                .findViewById(R.id.lin_qzone);

        final LinearLayout lin_weibo = (LinearLayout) window
                .findViewById(R.id.lin_weibo);


        lin_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //延迟 只是为了看到share_press的效果
                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        //朋友圈
                        dlg.dismiss();
                        share(context,1,title,pic,url,content,mHandler);
                    }
                }, 200);


            }
        });
        lin_wxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        //微信
                        dlg.dismiss();
                        share(context,0,title,pic,url,content,mHandler);
                    }
                }, 200);

            }
        });

        lin_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        //QQ
                        dlg.dismiss();
                        share(context,3,title,pic,url,content,mHandler); //QQ好友
                    }
                }, 200);
            }
        });

        lin_qzone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        //QQ空间
                        dlg.dismiss();
                        share(context,4,title,pic,url,content,mHandler); //
                    }
                }, 200);

            }
        });
        lin_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        //新浪微博
                        dlg.dismiss();
                        share(context,2,title,pic,url,content,mHandler); //
                    }
                }, 200);

            }
        });


    }

    public static void  share(Context context, int position,String title, String pic,String url,String content, final Handler mHandler){
        String platformToShare = "";
        if (position == 0) {
            //微信好友
            platformToShare = "Wechat";
        } else if (position == 1) {
            //朋友圈
            platformToShare = "WechatMoments";
        } else if (position == 2) {
            //新浪微博
            platformToShare = "SinaWeibo";
        } else if (position == 3) {
            //QQ好友
            platformToShare = "QQ";
        } else if (position == 4) {
            //QQ空间
            platformToShare = "QZone";
        }
        final Message msg = Message.obtain();

        sharesdkUtil.ShareByCustom(context, platformToShare, title, pic, url, content, new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                msg.what = 1;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                msg.what = 2;
                msg.obj = throwable.toString().trim();
                mHandler.sendMessage(msg);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                msg.what = 3;
                mHandler.sendMessage(msg);
            }
        });
    }

    public static void ShareByCustom(final Context context, String platformToShare, final String title, String pic, final String url, final String content, PlatformActionListener platformActionListener) {
        ShareSDK.initSDK(context);

        //新浪微博网页端分享，第一次能获取到toast,第二次获取不到toast，解决方案：取消授权,重新登录
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        weibo.removeAccount(true);//取消授权,重新登录
        ShareSDK.removeCookieOnAuthorize(true);

        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(true);
        oks.setPlatform(platformToShare);
        // 关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
        // oks.setNotification(R.drawable.ic_launcher,
        // getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用

        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ、QQ空间使用 （QQ、QQ空间 可以作视频播放网址）
        oks.setTitleUrl(url);

        // text是分享文本，所有平台都需要这个字段

        oks.setText(content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数

        //oks.setImagePath(pic);// 确保SDcard下面存在此张图片 微信 、QQ、新浪微博、微信朋友圈
        //imageUrl是图片的网络路径，新浪微博、人人网、QQ空间和Linked-In支持此字段 */
        oks.setImageUrl(pic); //QQ空间图片可以出来 // 新版本2.8.2 的可以识别网络图片啦
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);

        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("分享");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);


        Log.i("Unity","shareUrl=" + url);
        //oks.setVideoUrl("http://www.meipai.com/media/509146433"); //低版本不支持
        // ok （VideoUrl 与Url 默认取 VideoUrl ,只能是视频播放网址，不能是视频源，该属性只有微信使用）

        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                //新浪微博只有图片和文字生效 title不显示的 setText （ 可追加视频播放网址）
                if ("SinaWeibo".equals(platform.getName())) {
                    paramsToShare.setUrl(null);
                    paramsToShare.setText(content + " " + url);
                }
                //微信朋友圈
                else if ("WechatMoments".equals(platform.getName())) {
                    paramsToShare.setTitle(content);
//                    String str=context.getString(R.string.slogen) +content;
//                    paramsToShare.setTitle(str);
                }
//                else if("Wechat".equals(platform.getName())){
//                    String str=context.getString(R.string.app_name);
//                    paramsToShare.setTitle(str+" "+ title);
//                    paramsToShare.setComment(context.getString(R.string.slogen) +" "+content);
//                }
            }
        });

        oks.setCallback(platformActionListener);
        oks.show(context);
    }



}
