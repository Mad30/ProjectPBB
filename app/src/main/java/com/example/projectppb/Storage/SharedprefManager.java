package com.example.projectppb.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.projectppb.Model.User;

public class SharedprefManager {
    public static final String SHARED = "Celengan_shared";
    private static SharedprefManager instance;
    private Context ctx;

    private SharedprefManager(Context ctx)
    {
      this.ctx = ctx;
    }

    public static synchronized SharedprefManager getInstance(Context ctx)
    {
        if (instance == null)
        {
            instance = new SharedprefManager(ctx);
        }
        return instance;
    }

    public void SaveUser(User user)
    {
        SharedPreferences sf = ctx.getSharedPreferences(SHARED,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        editor.putInt("id",user.getId());
        editor.putString("nama",user.getNama());
        editor.putString("email",user.getEmail());
        editor.apply();
    }

    public boolean login()
    {
        SharedPreferences sf =ctx.getSharedPreferences(SHARED,Context.MODE_PRIVATE);
        return sf.getInt("id",-1)!=-1;
    }
    public User getUser()
    {
        SharedPreferences sf =ctx.getSharedPreferences(SHARED,Context.MODE_PRIVATE);
        return new User(
                sf.getInt("id",-1),
                sf.getString("nama",null),
                sf.getString("email",null)
        );
    }
}
