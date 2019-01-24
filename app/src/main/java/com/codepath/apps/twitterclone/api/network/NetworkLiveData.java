package com.codepath.apps.twitterclone.api.network;


import android.arch.lifecycle.LiveData;

class NetworkLiveData extends LiveData<NetworkLiveData.NetworkStatus> {
    public enum NetworkStatus {LOADING, IDLE}
    private NetworkStatus status;

    public NetworkStatus getStatus() {return status;}
    public void setStatus(NetworkStatus status) {this.status = status;}
}
