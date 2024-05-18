package com.manager.cafe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.manager.cafe.POJO.Bill;
@Repository
public interface BillDao extends JpaRepository<Bill,Integer>{

    @Query("select b from Bill b order by b.id desc")
    List<Bill> getAllBills();

    @Query("select b from Bill b where b.createBy =: currentUser order by b.id desc")
    List<Bill> getBillByUserName(String currentUser);
    
}
