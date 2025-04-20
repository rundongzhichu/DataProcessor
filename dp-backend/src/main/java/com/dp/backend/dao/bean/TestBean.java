package com.dp.backend.dao.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class TestBean {

    @Id
    private int field1;

}
