package com.wokebryant.anythingdemo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * Created by libo on 15/11/16.
 * 使用的时候需要创建对象
 * 格式化JSON和Throwable对象输出
 * 可以保存到文件
 * <p/>
 * 参考https://github.com/ZhaoKaiQiang/KLog
 */
public class KLog {
    private final static String TAG = "KLog4Android";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    /**
     * 是否调试模式
     */
    private static final boolean DEBUG_MODE = true;
    /**
     * 子类可以修改TAG名称，便于按模块区分
     */
    private String sTag = TAG;
    /**
     * 日志前缀
     */
    private String msgPrefix = "KLOG==**  ";
    /**
     * 日志后缀
     */
    private String msgPostfix = "";
    /**
     * 保存日志的文件对象
     */
    private File saveFile = checkFile(null);
    /**
     * 是否打印LOG
     */
    private boolean showLog = DEBUG_MODE;

    /**
     * 是否打印当前行信息
     */
    private boolean showLineInfo = false;

    public KLog() {
        this(null, null, null, null);
    }

    public KLog(String tag) {
        this(tag, null, null, null);
    }

    public KLog(Object obj) {
        this(obj.getClass().getSimpleName());
    }

    public KLog(File file) {
        this(null, null, null, file);
    }

    public KLog(String tag, File file) {
        this(tag, null, null, file);
    }

    /**
     * 构造函数
     *
     * @param tag
     * @param prefix   前缀
     * @param postfix  后缀
     * @param saveFile 保存到的文件
     */
    public KLog(String tag, String prefix, String postfix, File saveFile) {
        if (!isEmpty(tag)) {
            this.sTag = tag;
        }

        if (isEmpty(prefix)) {
            this.msgPrefix = "KLOG==**  ";
        } else {
            this.msgPrefix = prefix;
        }

        if (isEmpty(postfix)) {
            this.msgPostfix = "";
        } else {
            this.msgPostfix = postfix;
        }

        if (saveFile != null) {
            this.saveFile = checkFile(saveFile);
        }
    }

    /**
     * 检查文件是否合法
     *
     * @param file
     * @return
     */
    protected static File checkFile(File file) {
        if (file == null) {
            return null;
        }

        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }

        // 传入的文件对象是一个目录，则自动在此目录下创建一个日志文件
        if (file.isDirectory()) {
            String newFilePath = "auto_create_" + System.currentTimeMillis()
                    + ".log";
            file = new File(file, newFilePath);
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
            }
        }

        return file;
    }

    /**
     * 设置标签
     *
     * @param tag
     */
    public void setTag(String tag) {
        if (!isEmpty(tag)) {
            this.sTag = tag;
        }
    }

    /**
     * 是否打印行数信息
     *
     * @param flag
     */
    public void setShowLineInfo(boolean flag) {
        this.showLineInfo = flag;
    }

    /**
     * 设置保存文件
     *
     * @param file
     */
    public void setFile(File file) {
        this.saveFile = checkFile(file);
    }

    /**
     * 字符串是否为空
     *
     * @param text
     * @return
     */
    private boolean isEmpty(String text) {
        return text == null || "".equalsIgnoreCase(text);

    }

    /**
     * 是否打印LOG
     *
     * @return
     */
    public boolean isShowLog() {
        return showLog;
    }

    /**
     * 关闭LOG
     *
     * @return
     */
    public KLog closeLog() {
        this.showLog = false;
        return this;
    }

    /**
     * 打开LOG
     *
     * @return
     */
    public KLog openLog() {
        this.showLog = true;
        return this;
    }

    /**
     * 注册未捕获异常的Log
     *
     * @param context
     */
    public void registerUncaughtExeptionLog(final Context context) {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                e("Process :" + getCurrentProcessName(context) + ", Thread :" + thread.getName() + " uncaught exception :\n");
                printStackTrace(thread);
                e(throwable);
            }
        });
    }

    public String getCurrentProcessName(Context context) {
        String currentProcName = "";
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return "";
        }

        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                currentProcName = processInfo.processName;
            }
        }

        return currentProcName;
    }

    /**
     * 打印当前线程名
     */
    public void printCurrentThreadName() {
        String tName = Thread.currentThread().getName();

        i("current thread name is : " + tName);
    }

    /**
     * 获取当前时间
     */
    private String getTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss : ");//设置日期格式
        return df.format(new Date());
    }

    /**
     * 获得当前LOG的文件和行数信息
     *
     * @param iteration
     * @return
     */
    private String getLineInfo(int iteration) {
        if (false == showLineInfo) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        try {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

            int index = iteration;
            String className = stackTrace[index].getFileName();
            String methodName = stackTrace[index].getMethodName();
            int lineNumber = stackTrace[index].getLineNumber();
            methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
            stringBuilder.append("[ (").append(className).append(":").append(lineNumber).append(")#").append(methodName).append(" ] ");

        } catch (Exception e) {
        }

        return stringBuilder.toString();
    }

    public void v(String log) {
        if (!isShowLog()) {
            return;
        }

        String showLog = generateLog(log, 5);
        Log.v(sTag, showLog);

        saveLogToFile("v", showLog);
    }

    public void i(String log) {
        if (!isShowLog()) {
            return;
        }

        String showLog = generateLog(log, 5);
        Log.i(sTag, showLog);

        saveLogToFile("i", showLog);
    }

    public void d(String log) {
        if (!isShowLog()) {
            return;
        }

        String showLog = generateLog(log, 5);
        Log.d(sTag, showLog);

        saveLogToFile("d", showLog);
    }

    public void w(String log) {
        if (!isShowLog()) {
            return;
        }

        String showLog = generateLog(log, 5);
        Log.w(sTag, showLog);

        saveLogToFile("w", showLog);
    }

    public void e(String log) {
        if (!isShowLog()) {
            return;
        }

        String showLog = generateLog(log, 5);
        Log.e(sTag, showLog);

        saveLogToFile("e", showLog);
    }

    private void printLine(boolean isTop) {
        if (isTop) {
            d("╔═══════════════════════════════════════════════════════════════════════════════════════", 7);
        } else {
            d("╚═══════════════════════════════════════════════════════════════════════════════════════", 7);
        }
    }

    private void d(String log, int iteration) {
        if (!isShowLog()) {
            return;
        }

        String showLog = generateLog(log, iteration);
        Log.d(sTag, showLog);

        saveLogToFile("d", showLog);
    }

    private void e(String log, int iteration) {
        if (!isShowLog()) {
            return;
        }

        String showLog = generateLog(log, iteration);
        Log.d(sTag, showLog);

        saveLogToFile("e", showLog);
    }

    private void printErrLine(boolean isTop) {
        if (isTop) {
            e("╔═══════════════════════════════════════════════════════════════════════════════════════", 7);
        } else {
            e("╚═══════════════════════════════════════════════════════════════════════════════════════", 7);
        }
    }

    /**
     * 打印JSON对象
     *
     * @param jsonObject
     */
    public void d(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }

        String message = null;
        try {
            message = jsonObject.toString();
        } catch (Exception e) {
            return;
        }

        printLine(true);
        String[] lines = message.split(LINE_SEPARATOR);
        StringBuilder jsonContent = new StringBuilder();
        for (String line : lines) {
            jsonContent.append("║ ").append(line).append(LINE_SEPARATOR);
        }

        d(jsonContent.toString(), 6);
        printLine(false);
    }

    /**
     * 打印异常对象
     *
     * @param throwable
     */
    public void e(Throwable throwable) {
        if (throwable == null) {
            return;
        }

        printErrLine(true);
        e("║ Throwable class is : " + throwable.getClass().getName(), 6);
        e("║ Throwable message is : " + throwable.getMessage(), 6);
        e(formatErrorStack(throwable), 6);
        printErrLine(false);
    }

    private String formatErrorStack(Throwable tr) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Writer result = new StringWriter(512);
            PrintWriter printWriter = new PrintWriter(result);
            Throwable cause = tr;
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            String stacktraceAsString = result.toString();
            printWriter.close();

            stringBuilder.append(stacktraceAsString);
        } catch (Exception e) {
            throw new AssertionError();
        }

        return "║ ";
    }

    /**
     * 保存到文件
     *
     * @param log
     */
    private void saveLogToFile(String type, String log) {
        String typeStr = "";
        if (isEmpty(type)) {
            typeStr = " -i- ";
        } else {
            typeStr = " -" + type + "- ";
        }

        String tagStr = "";
        if (!isEmpty(sTag)) {
            tagStr = " -" + sTag + "- ";
        }

        if (saveFile != null) {
            writeToFile(saveFile, getTime() + typeStr.toUpperCase() + tagStr + log);
        }
    }

    /**
     * 打印线程调用栈
     *
     * @param thread 为空的话，打印当前栈
     */
    public void printStackTrace(Thread thread) {
        if (!isShowLog()) {
            return;
        }

        try {
            Thread th = thread;
            if (th == null) {
                th = Thread.currentThread();
            }

            Map<Thread, StackTraceElement[]> ts = Thread.getAllStackTraces();
            StackTraceElement[] ste = ts.get(th);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            stringBuilder.append("╔═══════════════════════════════════════════════════════════════════════════════════════" + "\n");
            stringBuilder.append("║ " + "thread " + th.getName() + " stack :" + "\n");

            for (StackTraceElement s : ste) {
                stringBuilder.append("║ " + s.toString() + "\n");
            }
            stringBuilder.append("╚═══════════════════════════════════════════════════════════════════════════════════════");

            String showLog = stringBuilder.toString();
            Log.e(sTag, showLog);

            saveLogToFile("e", showLog);
        } catch (Exception e) {
        }
    }

    /**
     * 打印当前线程栈
     */
    public void printStackTrace() {
        printStackTrace(null);
    }

    /**
     * 打印所有线程栈
     */
    public void printAllStackTrace() {
        if (!isShowLog()) {
            return;
        }

        Map<Thread, StackTraceElement[]> ts = Thread.getAllStackTraces();

        for (Thread key : ts.keySet()) {
            printStackTrace(key);
        }
    }

    /**
     * 拼装出最终的Log形式
     */
    private String generateLog(String log, int iteration) {
        return getLineInfo(iteration) + msgPrefix + log + msgPostfix;
    }

    /**
     * 写入日志内容到文件
     *
     * @param saveFile 文件对象
     * @param log      日志内容
     */
    private void writeToFile(File saveFile, CharSequence log) {
        Writer writer = null;
        try {
            writer = new FileWriter(saveFile, true);
            writer.append(log); // 不是覆盖
            writer.append("\n");
            writer.flush();
        } catch (Exception e) {
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
                writer = null;
            }
        }
    }

}
