package com.wokebryant.anythingdemo.util.ImageCrop;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 */
public class FileUtil {

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 检查文件是否存在
     */
    public static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    private static final String TAG = "FileUtil";

    /**
     * 保存bitmap到SD卡
     *
     * @param bitmap
     * @param imagename
     */
    public static String saveBitmapToSDCard(Bitmap bitmap, String imagename, String type, long uid) {
        String name = "img-" + imagename + ".jpg";
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String path = "";
        if (type.equals("cover")) {
            path = absolutePath + File.separator + "LAIFENG" + File.separator + "ActorCover" + File.separator;
        } else if (type.equals("actorBg")) {
            path = absolutePath + File.separator + "LAIFENG" + File.separator + "ActorBg" + File.separator + String.valueOf(uid) + File.separator;
        }

        File file = new File(path);
        boolean newFile = file.mkdirs();
        Log.i(TAG, "saveBitmapToSDCard: newFile = " + newFile);

        path = path + name;
        FileOutputStream fos = null;
        Log.i(TAG, "saveBitmapToSDCard: path =" + path);
        try {
            File file1 = new File(path);
            fos = new FileOutputStream(path);
            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.close();
            }

            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String saveBitmapToSDCard(Bitmap bitmap, String imagename, String filepath) {
        String name = "img-" + imagename + ".jpg";
        File file = new File(filepath);
        boolean newFile = file.mkdirs();
        Log.i(TAG, "saveBitmapToSDCard: newFile = " + newFile);

        final String path = filepath + "/" + name;
        FileOutputStream fos = null;
        Log.i(TAG, "saveBitmapToSDCard: path =" + path);
        try {
            File file1 = new File(path);
            fos = new FileOutputStream(path);
            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.close();
            }

            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件大小
     */
    public static long getFileSize(String path) {
        if (TextUtils.isEmpty(path) || !new File(path).exists()) {
            return 0L;
        }
        return new File(path).length();
    }

    /**
     * 获取文件大小
     */
    public static String getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            Log.e("获取文件大小", "文件不存在!");
        }
        return toFileSize(size);
    }

    /**
     * 转换文件大小
     */
    public static String toFileSize(long fileS) {
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }

        if (fileS > 0 && fileS <= 20971520) {
            fileSizeString = "小于20M";
        } else {
            fileSizeString = "大于20M";
        }
        return fileSizeString;
    }


    /**
     * 获取SD卡指定目录所有图片路径
     */
    public static List<String> getImagePathFromSD(String filePath) {

        File fileAll = new File(filePath);
        if (!fileAll.exists() || !fileAll.isDirectory()) {
            return null;
        }
        List<String> imagePathList = new ArrayList<String>();
        File[] files = fileAll.listFiles();
        if(files == null) {
            return null;
        }
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsImageFile(file.getPath())) {
                imagePathList.add(file.getPath());
            }
        }
        return imagePathList;
    }

    /**
     * 检查扩展名，得到图片格式的文件
     */
    public static boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
            fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
            || FileEnd.equals("jpeg")|| FileEnd.equals("bmp") ) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }


}
