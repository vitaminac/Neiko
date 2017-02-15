package org.noear.sited;

import android.app.Application;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by yuety on 15/8/26. Y
 */
class SdJscript {
    private final SdNode require;
    public final String code;
    public final SdSource s;

    SdJscript(SdSource source, Element node) {
        s = source;

        if (node == null) {
            code = "";
            require = Util.createNode(source).buildForNode(null);
        } else {
            Element element = Util.getElement(node, "code");
            if (element != null) {
                code = element.getTextContent();
                require = Util.createNode(source).buildForNode(Util.getElement(node, "require"));
            } else {
                code = "";
                require = null;
            }
        }
    }

    void loadJs(Application app, JsEngine js) {
        if (!require.isEmpty()) {
            for (SdNode n1 : require.items()) {

                //1.如果本地可以加载并且没有出错
                if (!TextUtils.isEmpty(n1.lib)) {
                    if (loadLib(app, js, n1.lib))
                        continue;
                }

                //2.尝试网络加载
                Log.v("SdJscript", n1.url);

                if(n1.cache==0) {
                    n1.cache = 1; //长久缓存js文件 //默认长久缓存
                }

                HttpMessage msg = new HttpMessage(n1,n1.url);
                msg.callback = (code, sender, text, url302) -> {
                    if (code == 1) {
                        js.loadJs(text);
                    }
                };

                Util.http(s, false, msg);
            }
        }

        if (!TextUtils.isEmpty(code))
            js.loadJs(code);
    }

    //---------------------
    //
    private boolean loadLib(Application app, JsEngine js, String lib) {
        //for debug
        Resources asset = app.getResources();

        switch (lib) {
            case "md5":
                return tryLoadLibItem(asset, R.raw.md5, js);

            case "sha1":
                return tryLoadLibItem(asset, R.raw.sha1, js);

            case "base64":
                return tryLoadLibItem(asset, R.raw.base64, js);

            case "cheerio":
                return tryLoadLibItem(asset, R.raw.cheerio, js);
            default:
                return false;
        }
    }

    private boolean tryLoadLibItem(Resources asset, int resID, JsEngine js) {
        try {
            InputStream is = asset.openRawResource(resID);
            BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String code = doToString(in);
            js.loadJs(code);

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private String doToString(BufferedReader in) throws IOException {
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }
}