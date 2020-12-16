package com.wokebryant.anythingdemo.dynamicsitem.decorate;

import com.wokebryant.anythingdemo.dynamicsitem.factory.DynamicsTypeFactory;

/**
 * @author wb-lj589732
 */
public interface DynamicsVisitable {

    int type(DynamicsTypeFactory typeFactory);
}
