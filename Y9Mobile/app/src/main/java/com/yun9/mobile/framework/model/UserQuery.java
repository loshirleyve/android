package com.yun9.mobile.framework.model;

import java.io.Serializable;

public class UserQuery implements Serializable
{
    String createby;
    String updateby;
    long createdate;
    String updatedate;
    long disabled;
    String remark;
    String id;
    String no;
    String name;
    String idcard;
    String sex;
    long birthday;
    String state;
    String onlinestate;
    long registerdate;
    String headerfileid;
	private String signature;

    public UserQuery(String createby, String updateby, long createdate, String updatedate, long disabled, String remark, String id, String no, String name, String idcard, String sex, long birthday, String state, String onlinestate, long registerdate, String headerfileid)
    {
        this.createby = createby;
        this.updateby = updateby;
        this.createdate = createdate;
        this.updatedate = updatedate;
        this.disabled = disabled;
        this.remark = remark;
        this.id = id;
        this.no = no;
        this.name = name;
        this.idcard = idcard;
        this.sex = sex;
        this.birthday = birthday;
        this.state = state;
        this.onlinestate = onlinestate;
        this.registerdate = registerdate;
        this.headerfileid = headerfileid;
    }

    public UserQuery()
    {

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

    public String getUpdatedate()
    {
        return updatedate;
    }

    public void setUpdatedate(String updatedate)
    {
        this.updatedate = updatedate;
    }

    public long getDisabled()
    {
        return disabled;
    }

    public void setDisabled(long disabled)
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

    public String getNo()
    {
        return no;
    }

    public void setNo(String no)
    {
        this.no = no;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIdcard()
    {
        return idcard;
    }

    public void setIdcard(String idcard)
    {
        this.idcard = idcard;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public long getBirthday()
    {
        return birthday;
    }

    public void setBirthday(long birthday)
    {
        this.birthday = birthday;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getOnlinestate()
    {
        return onlinestate;
    }

    public void setOnlinestate(String onlinestate)
    {
        this.onlinestate = onlinestate;
    }

    public long getRegisterdate()
    {
        return registerdate;
    }

    public void setRegisterdate(long registerdate)
    {
        this.registerdate = registerdate;
    }

    public String getHeaderfileid()
    {
        return headerfileid;
    }

    public void setHeaderfileid(String headerfileid)
    {
        this.headerfileid = headerfileid;
    }

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
}
