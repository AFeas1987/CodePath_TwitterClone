package com.codepath.apps.twitterclone.ui.recView.util;
import android.arch.lifecycle.LiveData;

import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;

public class RealmLiveData<T extends RealmModel> extends LiveData<RealmResults<T>> {

    private RealmResults<T> results;

    private final RealmChangeListener<RealmResults<T>> listener = this::setValue;

    public RealmLiveData(RealmResults<T> realmResults) {results = realmResults;}

    public RealmResults<T> getResults () {return results;}

    @Override
    protected void onActive() {results.addChangeListener(listener);}

    @Override
    protected void onInactive() {results.removeChangeListener(listener);}
}
