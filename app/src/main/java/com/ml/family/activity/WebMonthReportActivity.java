package com.ml.family.activity;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
import android.support.v4.os.CancellationSignal;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.dx.stock.ProxyBuilder;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.xml.simpleparser.SimpleXMLDocHandler;
import com.ml.family.R;
import com.ml.family.network.NetworkApi;
import com.ml.family.utils.DateUtil;
import com.ml.family.utils.ToastUtil;
import com.vondear.rxtools.RxFileTool;
import com.vondear.rxtools.RxWebViewTool;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gzq on 2018/3/13.
 */

public class WebMonthReportActivity extends BaseActivity {
    @BindView(R.id.web_month_report)
    WebView webMonthReport;
    private ProgressDialog myDialog;
    private ParcelFileDescriptor descriptor;
    private PageRange[] ranges;
    private PrintDocumentAdapter printAdapter;
    private File dexCacheFile;
    private int webviewHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_report);
        ButterKnife.bind(this);
        setHat();
        RxWebViewTool.initWebView(this, webMonthReport);
        initWebview();
        initProgress();
    }

    @SuppressLint("WrongConstant")
    private void initWebview() {
        WebSettings webSettings = webMonthReport.getSettings();
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setCacheMode(1);
        }

        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }

        if (Build.VERSION.SDK_INT >= 11) {
            webMonthReport.setLayerType(1, (Paint) null);
        }

        webMonthReport.setLayerType(2, (Paint) null);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setSavePassword(true);
        webSettings.setDomStorageEnabled(true);
        webMonthReport.setSaveEnabled(true);
        webMonthReport.setKeepScreenOn(true);
        webMonthReport.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webviewHeight = webMonthReport.getMeasuredHeight();
                if (!webMonthReport.getSettings().getLoadsImagesAutomatically()) {
                    webMonthReport.getSettings().setLoadsImagesAutomatically(true);
                }

            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.startsWith("http:") && !url.startsWith("https:")) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else {
                    view.loadUrl(url);
                    return false;
                }
            }
        });
        webMonthReport.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, String paramAnonymousString4, long paramAnonymousLong) {
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                intent.setData(Uri.parse(paramAnonymousString1));
//                startActivity(intent);
                ToastUtil.showShort("请点击右上角的下载按钮");
            }
        });

        webMonthReport.loadUrl(NetworkApi.Month_Report);
    }

    private void setHat() {
        setTopTitle("健康报告");
        setRightText("下载报告");

    }

    /**
     * 初始化识别进度框
     */
    private void initProgress() {
        myDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        myDialog.setIndeterminateDrawable(getResources().getDrawable(
                R.drawable.progress_ocr));
        myDialog.setMessage("正在下载...");
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.setCancelable(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onRightTextClick() {
        //将webview打印成PDF格式的文件
        if (webviewHeight == 0) {
            ToastUtil.showShort("页面未加载完成，还不能下载");
            return;
        }
        if (!myDialog.isShowing())
            myDialog.show();
        printPDFFile();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void printPDFFile() {
        int pageHeight = 413;
        String basePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "gcml"
                + File.separator + "pdf";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //创建DexMaker缓存目录
//            File dexCacheFile = new File(Environment.getDownloadCacheDirectory(),"gcml/dexfile/");
            dexCacheFile = getDir("dex", 0);
//            if (!dexCacheFile.exists()) {
//                dexCacheFile.mkdir();
//            }
            try {
                File pdfFile = new File(basePath + File.separator + "report_" + DateUtil.format(new Date(), "yyyyMMdd") + ".pdf");
                if (pdfFile.exists()) {
                    pdfFile.delete();
                }
                pdfFile.createNewFile();
                descriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_WRITE);
                // 设置打印参数
                PrintAttributes attributes = new PrintAttributes.Builder()
                        .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                        .setResolution(new PrintAttributes.Resolution("id", Context.PRINT_SERVICE, 300, 300))
                        .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
                        .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                        .build();
                // 计算webview打印需要的页数
                int numberOfPages = (webviewHeight / pageHeight) + 1;
                ranges = new PageRange[]{new PageRange(0, numberOfPages)};
                // 创建pdf文件缓存目录
                File cacheFolder = new File(basePath);
                if (!cacheFolder.exists()) {
                    cacheFolder.mkdir();
                }
                // 获取需要打印的webview适配器
                printAdapter = webMonthReport.createPrintDocumentAdapter();
                // 开始打印
                printAdapter.onStart();
                printAdapter.onLayout(attributes, attributes, new android.os.CancellationSignal(), getLayoutResultCallback(new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("onLayoutFinished")) {
                            // 监听到内部调用了onLayoutFinished()方法,即打印成功
                            onLayoutSuccess();
                        } else {
                            // 监听到打印失败或者取消了打印
                            ToastUtil.showShort("下载失败");
                        }
                        return null;
                    }
                }, dexCacheFile), new Bundle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onLayoutSuccess() throws IOException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            PrintDocumentAdapter.WriteResultCallback callback = getWriteResultCallback(new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    if (myDialog.isShowing())
                        myDialog.dismiss();
                    if (method.getName().equals("onWriteFinished")) {
//                        iNoteReadView.webViewPDFExtractComplete(pdfFilePath);
                        ToastUtil.showShort("下载成功");
                    } else {
//                        iNoteReadView.webViewPDFExtractComplete("");
                        ToastUtil.showShort("下载失败");
                    }
                    return null;
                }
            }, dexCacheFile);
            printAdapter.onWrite(ranges, descriptor, new android.os.CancellationSignal(), callback);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static PrintDocumentAdapter.LayoutResultCallback getLayoutResultCallback(InvocationHandler invocationHandler, File dexCacheDir) throws IOException {
        return ProxyBuilder.forClass(PrintDocumentAdapter.LayoutResultCallback.class)
                .dexCache(dexCacheDir)
                .handler(invocationHandler)
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static PrintDocumentAdapter.WriteResultCallback getWriteResultCallback(InvocationHandler invocationHandler, File dexCacheDir) throws IOException {
        return ProxyBuilder.forClass(PrintDocumentAdapter.WriteResultCallback.class)
                .dexCache(dexCacheDir)
                .handler(invocationHandler)
                .build();
    }

}
