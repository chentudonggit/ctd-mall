package com.ctd.mall.micro.service.user.repository.menu;

import com.ctd.mall.micro.service.user.domain.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * MenuRepository
 *
 * @author chentudong
 * @date 2020/3/9 9:23
 * @since 1.0
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, String>
{
}
