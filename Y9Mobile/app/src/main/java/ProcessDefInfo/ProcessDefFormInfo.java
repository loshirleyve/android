package ProcessDefInfo;

import java.io.Serializable;
import java.util.Map;

import com.yun9.mobile.framework.util.JsonUtil;

public class ProcessDefFormInfo implements Serializable {
	
	public class Device {
		public static final String APP = "APP";
		public static final String PC = "PC";
	}

	private static final long serialVersionUID = 1L;
	
	private String id;	// 表单ID
	private String name;	// 表单Name
	private Map<String,Object> value;	//表单配置
	private String device;	//设备 APP | PC
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String,Object> getValue() {
		return value;
	}
	public void setValue(Map<String,Object> value) {
		this.value = value;
	}
	public String getValueString() {
		return (getValue() == null)?null:JsonUtil.beanToJson(getValue());
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}

}
