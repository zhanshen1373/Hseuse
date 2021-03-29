package com.hd.hse.pc.phone.adapter;

public class PeopleInformation {
	/**
	 * 人员信息类型
	 */
	private String informationType;
	/**
	 * 人员信息详情
	 */
	private String detailInformation;

	public PeopleInformation() {

	}

	public PeopleInformation(String informationType,String detailInformation) {
		this.informationType=informationType;
		this.detailInformation=detailInformation;
	}

	public String getInformationType() {
		return informationType;
	}

	public void setInformationType(String informationType) {
		this.informationType = informationType;
	}

	public String getDetailInformation() {
		return detailInformation;
	}

	public void setDetailInformation(String detailInformation) {
		this.detailInformation = detailInformation;
	}

}
