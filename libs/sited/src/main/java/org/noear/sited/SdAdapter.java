package org.noear.sited;

import java.io.File;

/**
 * Created by yuety on 2017/3/19.
 */

public class SdAdapter {
    public SdNode createNode(SdSource source) {
        return new SdNode(source);
    }

    public SdNodeSet createNodeSet(SdSource source) {
        return new SdNodeSet(source);
    }

    public File cacheRoot(){
        return null;
    }

    public void log(SdSource source, String tag, String msg, Throwable tr){

    }

    public void set(SdSource source, String key, String val){

    }

    public String get(SdSource source, String key){
        return "";
    }
}
