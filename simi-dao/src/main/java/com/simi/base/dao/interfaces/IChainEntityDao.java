package com.simi.base.dao.interfaces;

import com.simi.base.model.models.ChainEntity;


public interface IChainEntityDao<PKType extends Number, EntityType extends ChainEntity<PKType, EntityType>> extends IEnableEntityDao<PKType, EntityType> {

}
