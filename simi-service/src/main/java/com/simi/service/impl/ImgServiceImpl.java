package com.simi.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.meijia.utils.ImgServerUtil;
import com.simi.service.ImgService;
import com.simi.vo.ImgSearchVo;
import com.simi.common.Constants;
import com.simi.po.dao.common.ImgsMapper;
import com.simi.po.model.common.Imgs;

@Service
public class ImgServiceImpl implements ImgService {

	@Autowired
	private ImgsMapper imgsMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return imgsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Long insert(Imgs record) {
		return imgsMapper.insert(record);
	}

	@Override
	public Long updateByPrimaryKey(Imgs record) {
		return imgsMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Imgs record) {
		return imgsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Imgs selectByPrimaryKey(Long id) {
		return imgsMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Imgs> selectBySearchVo(ImgSearchVo searchVo) {
		return imgsMapper.selectBySearchVo(searchVo);
	}

	@Override
	public Imgs initImg() {
		Imgs record = new Imgs();
		record.setImgId(0L);
		record.setUserId(0L);
		record.setLinkId(0L);
		record.setLinkType("");
		record.setImgUrl("");
		record.setAddTime(0L);

		return record;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insertImgs(MultipartFile[] imgs, Long userId, Long linkId, String linkType) {
		// 处理图片上传的信息.
		if (imgs != null && imgs.length > 0) {

			// 先删除指定的图片，再新增图片。
			ImgSearchVo searchVo = new ImgSearchVo();
			searchVo.setLinkId(linkId);
			searchVo.setLinkType(linkType);
			List<Imgs> imgList = selectBySearchVo(searchVo);
			if (!imgList.isEmpty()) {
				for (Imgs item : imgList) {
					deleteByPrimaryKey(item.getImgId());
				}
			}

			for (int i = 0; i < imgs.length; i++) {
				String url = Constants.IMG_SERVER_HOST + "/upload/";
				String fileName = imgs[i].getOriginalFilename();
				String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
				fileType = fileType.toLowerCase();
				String sendResult = null;
				try {
					sendResult = ImgServerUtil.sendPostBytes(url, imgs[i].getBytes(), fileType);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ObjectMapper mapper = new ObjectMapper();

				HashMap<String, Object> o = null;
				try {
					o = mapper.readValue(sendResult, HashMap.class);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				HashMap<String, String> info = (HashMap<String, String>) o.get("info");

				String imgUrl = Constants.IMG_SERVER_HOST + "/" + info.get("md5").toString();
				Imgs img = initImg();
				img.setLinkId(linkId);
				img.setUserId(userId);
				img.setLinkType(linkType);
				img.setImgUrl(imgUrl);
				insert(img);

			}
		}
	}

}
