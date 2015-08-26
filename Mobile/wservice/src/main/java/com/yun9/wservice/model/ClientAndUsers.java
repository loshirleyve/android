package com.yun9.wservice.model;

import java.util.List;

/**
 * Created by li on 2015/8/26.
 */
public class ClientAndUsers implements java.io.Serializable {
    private Client client;
    private List<ClientUser> clientUsers;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<ClientUser> getClientUsers() {
        return clientUsers;
    }

    public void setClientUsers(List<ClientUser> clientUsers) {
        this.clientUsers = clientUsers;
    }
}
