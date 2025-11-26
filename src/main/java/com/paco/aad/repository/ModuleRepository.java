package com.paco.aad.repository;

import com.paco.aad.model.Module;

public interface ModuleRepository {

    Module create(Module m);

    Module findById(Integer id);

    void delete(Integer id);
}
