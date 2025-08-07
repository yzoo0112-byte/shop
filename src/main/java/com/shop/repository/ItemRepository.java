package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> /*<entity의 클래스 타입, 그 entity의 id가 갖는 타입>*/{

    List<Item> findByItemNm(String itemNm);/*?에 들어갈거*/
    /*
    SELECT *
    FROM item
    WHERE item_nm = ?
    */
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
    /*
    SELECT *
    FROM item
    WHERE item_nm = ? or item_detail=?
    */
    List<Item> findByPriceLessThan(Integer price);
    /*
    SELECT *
    FORM item
    WHERE price < ?*/
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
    /*
    SELECT *
    FROM item
    WHERE price < ?
    ORDER BY PRICE DESC*/
    @Query("SELECT i FROM Item i WHERE i.itemDetail LIKE %:itemDetail% ORDER BY i.price DESC")//Item은 entity명
    List<Item> findByItemDetail(@Param("itemDetail")String itemDetail);

    @Query(value = "SELECT * FROM item i WHERE i.item_Detail LIKE %:itemDetail% ORDER BY i.price DESC", nativeQuery = true)//item은 table(DB)명
    List<Item> findByItemDetailByNative(String itemDetail);
}
