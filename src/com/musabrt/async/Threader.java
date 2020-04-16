package com.musabrt.async;

import com.musabrt.Aes;
import com.musabrt.Md5;
import com.musabrt.TDes;

import javax.swing.*;

public class Threader {
    private ICallback callback;

    public Threader() {

    }

    public void registerCallback(ICallback callback) {
        this.callback = callback;
    }

    public void runAes(final String key, final String mesaj, TType t) {
        new Thread(() -> {
            Aes aes = new Aes();
            aes.setKey(key);
            if (callback != null) {
                if (t == TType.ENCODE){
                    aes.encrypt(mesaj);
                    callback.Callback(aes.getEncryptedString());
                }
                else {
                    aes.decrypt(mesaj);
                    callback.Callback(aes.getDecryptedString());
                }
            }
        }).start();
    }

    public void runTDes(final String mesaj, TType t) {
        new Thread(() -> {
           TDes td = new TDes();
           if (callback != null) {
               if (t == TType.ENCODE)
                   callback.Callback(td.encode(mesaj));
               else
                   callback.Callback(td.decode(mesaj));
           }
        }).start();
    }

    public void runMd5(final String key, final String mesaj, TType t) {
        new Thread(() -> {
            Md5 md = new Md5();
            if (callback != null) {
                try {
                    if (t == TType.ENCODE) {
                        callback.Callback(md.encrypt(key, mesaj));
                    }
                    else {
                        callback.Callback(md.decrypt(key, mesaj));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "A invalid error :(", "Error", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }).start();
    }

}