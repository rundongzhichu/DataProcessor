package com.dp.backend.dao.api;

import com.dp.backend.dao.bean.TestBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestBean, Integer> {


}
