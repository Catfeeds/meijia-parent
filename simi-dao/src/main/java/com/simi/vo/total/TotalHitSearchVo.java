package com.simi.vo.total;

/**
 *
 * @author :hulj
 * @Date : 2016年5月18日下午5:47:02
 * @Description:
 *
 */
public class TotalHitSearchVo {
	
	private String linkType;	// 被点击  项目 的 类型，如 广告位= op_ad
	private Long linkId;		// 被点击 的具体 的Id(主键) , 如 工商注册广告位
	
	public String getLinkType() {
		return linkType;
	}
	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	public Long getLinkId() {
		return linkId;
	}
	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}
	
}
