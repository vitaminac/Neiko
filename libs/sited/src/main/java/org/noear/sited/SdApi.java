package org.noear.sited;

import android.util.Log;

import java.io.File;

/**
 * Created by yuety on 15/12/19.
 */
public class SdApi {

    protected static SdAdapter _adapter;

    public static void tryInit(SdAdapter adapter) {
        _adapter = adapter;
    }

    protected static void check() throws Exception {
        if (_adapter == null) {
            throw new Exception("未初始化");
        }
    }

    //-------------------------------
    //

    protected static void log(SdSource source, SdNode node, String url, String json, int tag) {
        log(source, node.name, "tag=" + tag);

        if (url == null)
            log(source, node.name, "url=null");
        else
            log(source, node.name, url);

        if (json == null)
            log(source, node.name, "json=null");
        else
            log(source, node.name, json);
    }

    protected static void log(SdSource source, String tag, String msg) {
        if (msg == null) {
            msg = "null";
        }

        try {
            Log.v(tag, msg);

            if (_adapter != null) {
                _adapter.log(source, tag, msg, null);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    protected static void log(SdSource source, String tag, Throwable tr) {
        if (tr == null) {
            return;
        }

        try {
            String msg = tr.getMessage();
            if (msg == null) {
                msg = "null";
            }

            Log.v(tag, msg);

            if (_adapter != null) {
                _adapter.log(source, tag, msg, tr);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected static void set(SdSource source, String key, String val) {
        Log.v("SiteD.set:", key + "=" + val);

        if (_adapter != null) {
            _adapter.set(source, key, val);
        }
    }

    protected static String get(SdSource source, String key) {
        if (_adapter != null) {
            String temp = _adapter.get(source, key);

            Log.v("SiteD.get:", key + "=" + temp);

            return temp;
        }

        return "";
    }

    //-------------
    //

    protected static File cacheRoot(){
        return _adapter.cacheRoot();
    }

    //-------------
    //
    protected static SdNode createNode(SdSource source) {
        if (_adapter == null)
            return new SdNode(source);
        else
            return _adapter.createNode(source);
    }

    protected static SdNodeSet createNodeSet(SdSource source) {
        if (_adapter == null)
            return new SdNodeSet(source);
        else
            return _adapter.createNodeSet(source);
    }
}
