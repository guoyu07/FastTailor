package com.jfinalshop.bean;

public class ConditionConfig {
	public static final ConditionConfig service = new ConditionConfig();
	private String conditions;
	private int max;
	
	public ConditionConfig buildCondition(String cfg,int max){
		ConditionConfig cond = new ConditionConfig();
		cond.setConditions(cfg);
		cond.setMax(max);
		return cond;
	}
	
	
	
	
	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	
}
