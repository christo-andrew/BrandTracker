package com.iconasystems.christoandrew.brandtracker.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.iconasystems.christoandrew.brandtracker.models.Token;
import com.iconasystems.christoandrew.brandtracker.repository.DataRepository;

public class TokenViewModel extends AndroidViewModel {
    private final DataRepository repository;
    private Token token;
    public TokenViewModel(@NonNull Application application) {
        super(application);
        this.repository = new DataRepository(application);
        token = this.repository.getToken();
    }

    public Token getToken() {
        return token;
    }

    public void insertToken(Token token){
        this.repository.insertToken(token);
    }
}
