package com.yun9.mobile.framework.model;

import java.io.Serializable;

public class FileByUserId implements Serializable
{
    private String createby;
    private String updateby;
    private long createdate;
    private long updatedate;
    private int disabled;
    private String remark;
    private String id;
    private String instid;
    private String sn;
    private String type;
    private String name;
    private String storagetype;
    private String bucket;
    private String level;

    public FileByUserId()
    {
    }

    public FileByUserId(String createby, String updateby, long createdate, long updatedate, int disabled, String remark, String id, String instid, String sn, String type, String name, String storagetype, String bucket)
    {
        this.createby = createby;
        this.updateby = updateby;
        this.createdate = createdate;
        this.updatedate = updatedate;
        this.disabled = disabled;
        this.remark = remark;
        this.id = id;
        this.instid = instid;
        this.sn = sn;
        this.type = type;
        this.name = name;
        this.storagetype = storagetype;
        this.bucket = bucket;
    }

    public String getCreateby()
    {
        return createby;
    }

    public void setCreateby(String createby)
    {
        this.createby = createby;
    }

    public String getUpdateby()
    {
        return updateby;
    }

    public void setUpdateby(String updateby)
    {
        this.updateby = updateby;
    }

    public long getCreatedate()
    {
        return createdate;
    }

    public void setCreatedate(long createdate)
    {
        this.createdate = createdate;
    }

    public long getUpdatedate()
    {
        return updatedate;
    }

    public void setUpdatedate(long updatedate)
    {
        this.updatedate = updatedate;
    }

    public int getDisabled()
    {
        return disabled;
    }

    public void setDisabled(int disabled)
    {
        this.disabled = disabled;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getInstid()
    {
        return instid;
    }

    public void setInstid(String instid)
    {
        this.instid = instid;
    }

    public String getSn()
    {
        return sn;
    }

    public void setSn(String sn)
    {
        this.sn = sn;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getStoragetype()
    {
        return storagetype;
    }

    public void setStoragetype(String storagetype)
    {
        this.storagetype = storagetype;
    }

    public String getBucket()
    {
        return bucket;
    }

    public void setBucket(String bucket)
    {
        this.bucket = bucket;
    }

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
}
