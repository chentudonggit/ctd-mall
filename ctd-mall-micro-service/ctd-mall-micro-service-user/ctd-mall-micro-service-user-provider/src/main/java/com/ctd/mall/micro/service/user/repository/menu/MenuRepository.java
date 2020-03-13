package com.ctd.mall.micro.service.user.repository.menu;

import com.ctd.mall.micro.service.user.domain.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MenuRepository
 *
 * @author chentudong
 * @date 2020/3/9 9:23
 * @since 1.0
 */
public interface MenuRepository extends JpaRepository<Menu, String>
{
    /**
     * 名称查找
     *
     * @param name name
     * @return Menu
     */
    Menu findByName(String name);
}
