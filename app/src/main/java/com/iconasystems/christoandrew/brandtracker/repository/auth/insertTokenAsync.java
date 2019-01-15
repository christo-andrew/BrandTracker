package com.iconasystems.christoandrew.brandtracker.repository.auth;

import android.os.AsyncTask;

import com.iconasystems.christoandrew.brandtracker.db.AppDatabase;
import com.iconasystems.christoandrew.brandtracker.db.TokenDao;
import com.iconasystems.christoandrew.brandtracker.models.Token;

public class insertTokenAsync extends AsyncTask<Token, Void, Void> {
    private TokenDao asyncTokenDao;
    public insertTokenAsync(TokenDao tokenDao) {
        this.asyncTokenDao = tokenDao;
    }


    @Override
    protected Void doInBackground(Token... tokens) {
        asyncTokenDao.deleteAll();
        asyncTokenDao.insert(tokens[0]);
        return null;
    }
}
