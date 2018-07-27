package com.gjdl.carameaccept;

import android.graphics.Bitmap;
import android.util.LruCache;

public class BitmapLruCache extends LruCache<String, Bitmap>{


    // 构造方法传入当前应用可用最大内存的八分之一
    public BitmapLruCache() {
        super((int) (Runtime.getRuntime().maxMemory() /1024 /8));
    }



    // 重写sizeOf方法，并计算返回每个Bitmap对象占用的内存
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount() /1024;
    }

    // 当缓存被移除时调用，第一个参数是表明缓存移除的原因，true表示被LruCache移除，false表示被主动remove移除，可不重写
    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
    }


    // 当get方法获取不到缓存的时候调用，如果需要创建自定义默认缓存，可以在这里添加逻辑，可不重写
    @Override
    protected Bitmap create(String key) {
        return super.create(key);
    }
}
