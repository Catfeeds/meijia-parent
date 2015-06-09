package com.simi.base.dao.daos;

import com.simi.base.dao.interfaces.ISimpleEntityDao;
import com.simi.base.model.models.SimpleEntity;


public abstract class SimpleEntityDao<PKType extends Number, EntityType extends SimpleEntity<PKType>>
	extends EntityDao<PKType, EntityType> implements ISimpleEntityDao<PKType, EntityType> {

}
