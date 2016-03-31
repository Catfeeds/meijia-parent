package com.simi.vo.record;

import java.util.List;

import com.simi.po.model.common.Imgs;
import com.simi.po.model.record.RecordAssetUse;


public class RecordAssetUseVo extends RecordAssetUse {
	
	private List<Imgs> imgs;

	public List<Imgs> getImgs() {
		return imgs;
	}

	public void setImgs(List<Imgs> imgs) {
		this.imgs = imgs;
	}

	
}
