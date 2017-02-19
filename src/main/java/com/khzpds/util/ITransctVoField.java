package com.khzpds.util;

import java.beans.PropertyDescriptor;
import org.springframework.beans.BeanWrapper;

public interface ITransctVoField
{

    public abstract int transctVo(BeanWrapper beanwrapper, PropertyDescriptor propertydescriptor);
}