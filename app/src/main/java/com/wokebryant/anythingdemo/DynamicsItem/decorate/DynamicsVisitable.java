package com.wokebryant.anythingdemo.DynamicsItem.decorate;

import com.wokebryant.anythingdemo.DynamicsItem.factory.DynamicsTypeFactory;

/**
 * @author wb-lj589732
 */
public interface DynamicsVisitable {

    int type(DynamicsTypeFactory typeFactory);
}
