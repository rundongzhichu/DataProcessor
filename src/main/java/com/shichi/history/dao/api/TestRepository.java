package com.shichi.history.dao.api;

import com.shichi.history.dao.bean.TestBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestBean, Integer> {


}
