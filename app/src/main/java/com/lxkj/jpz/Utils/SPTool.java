package com.lxkj.jpz.Utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@SuppressLint({"CommitPrefEdits", "NewApi"})
public class SPTool {


    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    private SPTool() {
    }

    public static void init(Context context, String SpaceName) {
        sp = context.getSharedPreferences(SpaceName, 0);
        editor = sp.edit();
    }

    public static void addSessionMap(String key, String object) {
        if (editor != null) {
            if (key != null && !"".equals(key)) {
                editor.putString(key, object);
                editor.commit();
            }
        } else {
            throw new IllegalStateException("editor not initialized");
        }
    }
    public static void addSessionMap(String key, List<String> object) {
        if (editor != null) {
            if (key != null && !"".equals(key)) {
                Gson gson = new Gson();
                //转换成json数据，再保存
                editor = sp.edit();
                String Json = gson.toJson(object);
                editor.putString(key, Json);
                editor.commit();
            }
        } else {
            throw new IllegalStateException("editor not initialized");
        }
    }


    public static void addSessionMap(String key, int object) {
        if (editor != null) {
            if (key != null && !"".equals(key)) {
                editor.putInt(key, object);
                editor.commit();
            }
        } else {
            throw new IllegalStateException("editor not initialized");
        }
    }

    public static void addSessionMap(String key, boolean object) {
        if (editor != null) {
            if (key != null && !"".equals(key)) {
                editor.putBoolean(key, object);
                editor.commit();
            }
        } else {
            throw new IllegalStateException("editor not initialized");
        }
    }

    public static void addSessionMap(String key, float object) {
        if (editor != null) {
            if (key != null && !"".equals(key)) {
                editor.putFloat(key, object);
                editor.commit();
            }
        } else {
            throw new IllegalStateException("editor not initialized");
        }
    }

    public static void addSessionMap(String key, long object) {
        if (editor != null) {
            if (key != null && !"".equals(key)) {
                editor.putLong(key, object);
                editor.commit();
            }
        } else {
            throw new IllegalStateException("editor not initialized");
        }
    }

    public static void addSessionMap(String key, Set<String> object) {
        if (editor != null) {
            if (key != null && !"".equals(key)) {
                editor.putStringSet(key, object);
                editor.commit();
            }
        } else {
            throw new IllegalStateException("editor not initialized");
        }
    }

    public static String getSessionValue(String key) {
        return getSessionValue(key, "");

    }

    public static String getSessionValue(String key, String Default) {
        if (editor != null) {
            if (key != null && !"".equals(key) && key.contains(key)) {
                return sp.getString(key, Default);
            }
            return "";
        } else {
            throw new IllegalStateException("editor not initialized");
        }

    }
    public static List<String> getDataList(String tag) {
        List<String> datalist = new ArrayList<>();
        String Json = sp.getString(tag, null);
        if (null == Json) {
            return datalist;
        }

        Gson gson = new Gson();
        datalist.addAll((Collection<? extends String>) gson.fromJson(Json, new TypeToken<List<String>>() {

        }.getType()));
        return datalist;

    }

    public static int getSessionValue(String key, int Default) {
        if (editor != null) {
            if (key != null && !"".equals(key) && key.contains(key)) {
                return sp.getInt(key, Default);
            }
            return 0;
        } else {
            throw new IllegalStateException("editor not initialized");
        }

    }

    public static long getSessionValue(String key, long Default) {
        if (editor != null) {
            if (key != null && !"".equals(key) && key.contains(key)) {
                return sp.getLong(key, Default);
            }
            return 0;
        } else {
            throw new IllegalStateException("editor not initialized");
        }

    }

    public static float getSessionValue(String key, float Default) {
        if (editor != null) {
            if (key != null && !"".equals(key) && key.contains(key)) {
                return sp.getFloat(key, Default);
            }
            return 0f;
        } else {
            throw new IllegalStateException("editor not initialized");
        }

    }

    public static boolean getSessionValue(String key, boolean Default) {
        if (editor != null) {
            if (key != null && !"".equals(key) && key.contains(key)) {
                return sp.getBoolean(key, Default);
            }
            return false;
        } else {
            throw new IllegalStateException("editor not initialized");
        }

    }

    public static Set<String> getSessionValue(String key, Set<String> Default) {
        if (editor != null) {
            if (key != null && !"".equals(key) && key.contains(key)) {
                return sp.getStringSet(key, Default);
            }
            return null;
        } else {
            throw new IllegalStateException("editor not initialized");
        }

    }

}
