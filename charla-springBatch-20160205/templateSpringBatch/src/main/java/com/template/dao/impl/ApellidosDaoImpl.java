/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.template.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;
import com.template.dao.IApellidosDao;
import com.template.dto.Apellidos;

/**
 *
 * @author rmpinedo
 */
@Service("IApellidosDao")
public class ApellidosDaoImpl extends SqlMapClientDaoSupport implements IApellidosDao {

    @Autowired
    public ApellidosDaoImpl(@Qualifier("sqlMapClient") SqlMapClient s) {
        setSqlMapClient(s);
    }

    public void insertarBeanApellidos(Apellidos config) throws Exception {
        SqlMapClientTemplate sqlMapClientTemplate = getSqlMapClientTemplate();
        sqlMapClientTemplate.insert("insert-Apellidos", config);

    }

    public int deleteBBDD() throws Exception {

        int count = 0;
        SqlMapClientTemplate sqlMapClientTemplate = getSqlMapClientTemplate();
        count = sqlMapClientTemplate.delete("delete-Apellidos");
        
        return count;
    }

    public void insertarListaApellidos(HashMap hm) throws Exception {
        SqlMapClientTemplate sqlMapClientTemplate = getSqlMapClientTemplate();

        sqlMapClientTemplate.insert("insert-ListApellidos", hm);
    }
}
