package com.simi.base.dao.interfaces;

import com.simi.base.model.models.SimpleEntity;

public interface ISimpleEntityDao<PKType extends Number, EntityType extends SimpleEntity<PKType>> extends IEntityDao<PKType, EntityType> {

}
