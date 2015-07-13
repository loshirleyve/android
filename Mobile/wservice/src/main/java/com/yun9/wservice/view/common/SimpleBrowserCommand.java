package com.yun9.wservice.view.common;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by huangbinglong on 7/11/15.
 */
public class SimpleBrowserCommand extends JupiterCommand{

    private String title;
    private String url;

    public SimpleBrowserCommand() {
    }

    public SimpleBrowserCommand(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public SimpleBrowserCommand setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public SimpleBrowserCommand setUrl(String url) {
        this.url = url;
        return this;
    }
}
