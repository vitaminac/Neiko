package org.noear.sited;

import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuety on 16/9/9.
 */

public class HttpMessage {
    public Map<String, String> header = new HashMap<>();
    public Map<String, String> form = new HashMap<>();
    public String url;

    public int tag;

    public HttpCallback callback;

    public SdNode config;


    //可由cfg实始化
    public String encode;
    public String ua;
    public String method;

    public HttpMessage() {

    }


    public HttpMessage(SdNode cfg, String url, int tag, Map<String, String> args) {
        this.config = cfg;
        this.url = url;
        this.tag = tag;

        if (args != null) {
            form = args;
        }

        rebuild(null);
    }

    public HttpMessage(SdNode cfg, String url) {
        this.config = cfg;
        this.url = url;

        rebuild(null);
    }

    public void rebuild(SdNode cfg) {
        if (cfg != null) {
            this.config = cfg;
        }

        ua = config.ua();
        encode = config.encode();
        method = config.method;

        header.putAll(config.getFullHeader(url));
    }


    public void rebuildForm(Map<String,String> data) {
        doBuildForm(true, 0, null, data);
    }

    public void rebuildForm(int page, String key) {
        doBuildForm(false, page, key, null);
    }

    private void doBuildForm(boolean isData, int page, String key, Map<String,String> data){
        if ("post".equals(config.method)) {
            String _strArgs = null;

            if(isData == false) {
                if (key != null) {
                    _strArgs = config.getArgs(url, key, page);
                } else {
                    _strArgs = config.getArgs(url, page);
                }
            }else{
                _strArgs = config.getArgs(url, data);
            }

            if (TextUtils.isEmpty(_strArgs) == false) {

                Log.v("Post.Args", _strArgs);

                for (String kv : _strArgs.split(";")) {
                    if (kv.length() > 3) {
                        String name = kv.split("=")[0];
                        String value = kv.split("=")[1];

                        if (value.equals("@key"))
                            form.put(name, key);
                        else if (value.equals("@page"))
                            form.put(name, page + "");
                        else
                            form.put(name, value);
                    }
                }
            }

        }
    }
}
