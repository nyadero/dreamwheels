package com.dreamwheels.dreamwheels.configuration.adapters;

public interface EntityAdapter<Entity, Dto>  {
    Dto toBusiness(Entity entity);
}
